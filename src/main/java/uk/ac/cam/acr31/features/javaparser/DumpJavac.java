package uk.ac.cam.acr31.features.javaparser;

import com.github.javaparser.GeneratedJavaParserConstants;
import com.github.javaparser.JavaParser;
import com.github.javaparser.JavaToken;
import com.github.javaparser.Range;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;
import com.google.common.collect.ImmutableList;
import com.google.devtools.common.options.OptionsParser;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class DumpJavac {

  public static void main(String[] args) throws IOException {

    OptionsParser parser = OptionsParser.newOptionsParser(Options.class);
    parser.parseAndExitUponError(args);
    Options options = parser.getOptions(Options.class);

    ImmutableList<Path> sourceFiles = FileUtil.collectRecursively(options.srcDir, ".java");
    if (options.maxFiles > 0) {
      sourceFiles = sourceFiles.subList(0, options.maxFiles);
    }

    JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

    StandardJavaFileManager fileManager =
        javaCompiler.getStandardFileManager(diagnostics, null, null);

    Iterable<? extends JavaFileObject> sourceUnits =
        fileManager.getJavaFileObjectsFromPaths(sourceFiles);

    List<String> javacOptions = new ArrayList<>();
    javacOptions.add("-classpath");
    ImmutableList<Path> jarFiles = FileUtil.collectRecursively(options.libDir, ".jar");
    javacOptions.add(jarFiles.stream().map(Path::toString).collect(Collectors.joining(":")));

    JavacTask task =
        (JavacTask) javaCompiler.getTask(null, null, diagnostics, javacOptions, null, sourceUnits);
    Iterable<? extends CompilationUnitTree> parses = task.parse();
    task.analyze();
    JavacTreePathScanner scanner = new JavacTreePathScanner();
    for (CompilationUnitTree parse : parses) {
      Path sourcePath = Paths.get(parse.getSourceFile().toUri());
      System.out.println(sourcePath.toString());
      TreeNode root = new TreeNode();
      parse.accept(scanner, root);
      Path destinationFile = options.outputDir.resolve(options.srcDir.relativize(sourcePath));
      Files.createDirectories(destinationFile.getParent());
      Files.write(destinationFile, root.toString().getBytes(StandardCharsets.UTF_8));
    }
  }
}
