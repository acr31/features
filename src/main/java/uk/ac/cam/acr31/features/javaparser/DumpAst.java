package uk.ac.cam.acr31.features.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.devtools.common.options.OptionsParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class DumpAst {

  private static ImmutableSet<String> loadBlacklist(Path blacklistFile) {
    try {
      return ImmutableSet.copyOf(Files.readAllLines(blacklistFile));
    } catch (IOException e) {
      return ImmutableSet.of();
    }
  }

  public static void main(String[] args) throws IOException {

    OptionsParser parser = OptionsParser.newOptionsParser(Options.class);
    parser.parseAndExitUponError(args);
    Options options = parser.getOptions(Options.class);

    ImmutableList<Path> sourceFiles = FileUtil.collectRecursively(options.srcDir, ".java");
    if (options.maxFiles > 0) {
      sourceFiles = sourceFiles.subList(0, options.maxFiles);
    }
    ImmutableSet<String> blacklist = loadBlacklist(options.blacklistFile);

    AstWork work = new AstWork(options);

    for (Path sourceFile : sourceFiles) {
      if (blacklist.contains(sourceFile)) {
        System.out.println("BLACKLIST\t" + sourceFile.toString());
        continue;
      }

      Path destinationFile = options.outputDir.resolve(options.srcDir.relativize(sourceFile));
      if (destinationFile.toFile().exists()) {
        System.out.println("SKIPPED\t" + sourceFile.toString());
        continue;
      }

      Optional<String> result = processFile(sourceFile, work, Duration.ofSeconds(5));

      if (result.isPresent()) {
        System.out.println("SUCCESS\t" + sourceFile.toString());
        Files.createDirectories(destinationFile.getParent());
        Files.write(
            destinationFile,
            result.get().getBytes(StandardCharsets.UTF_8),
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING);
      } else {
        System.out.println("FAIL\t" + sourceFile.toString());
        Files.write(
            options.blacklistFile,
            (sourceFile.toString() + "\n").getBytes(StandardCharsets.UTF_8),
            StandardOpenOption.APPEND,
            StandardOpenOption.CREATE);
      }
    }
  }

  static class AstWork implements Work {

    AstWork(Options options) throws IOException {
      CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
      for (Path p : FileUtil.collectRecursively(options.libDir, ".jar")) {
        combinedTypeSolver.add(new JarTypeSolver(p));
      }
      combinedTypeSolver.add(new ReflectionTypeSolver());
      combinedTypeSolver.add(new JavaParserTypeSolver(options.srcDir));

      JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
      JavaParser.getStaticConfiguration().setSymbolResolver(symbolSolver);
    }

    @Override
    public String call(Path sourceFile) throws Exception {
      CompilationUnit parse = JavaParser.parse(sourceFile.toFile());
      TreeNode root = new TreeNode("root");
      SerializerVisitor visitor = new SerializerVisitor();
      visitor.visit(parse, root);
      return root.toString();
    }
  }

  interface Work {
    String call(Path sourceFile) throws Exception;
  }

  private static Optional<String> processFile(Path sourceFile, Work work, Duration timeout) {

    AtomicReference<String> result = new AtomicReference<>();
    AtomicReference<Boolean> completed = new AtomicReference<>(false);
    Thread runner =
        new Thread(
            () -> {
              try {
                result.set(work.call(sourceFile));
                synchronized (completed) {
                  completed.set(true);
                  completed.notifyAll();
                }
              } catch (Throwable t) {
                System.out.println(t);
                synchronized (completed) {
                  completed.set(false);
                  completed.notifyAll();
                }
                // ignore
              }
            });

    runner.start();

    synchronized (completed) {
      if (!completed.get()) {
        try {
          completed.wait(timeout.toMillis());
        } catch (InterruptedException e) {
          // ignore
        }
      }
    }

    if (!completed.get()) {
      runner.interrupt();
    }

    synchronized (completed) {
      if (!completed.get()) {
        try {
          completed.wait(timeout.toMillis());
        } catch (InterruptedException e) {
          // ignore
        }
      }
    }

    if (!completed.get()) {
      runner.stop();
    }

    return completed.get() ? Optional.of(result.get()) : Optional.empty();
  }
}
