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
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DumpTokens {

  public static void main(String[] args) throws IOException {

    OptionsParser parser = OptionsParser.newOptionsParser(Options.class);
    parser.parseAndExitUponError(args);
    Options options = parser.getOptions(Options.class);

    ImmutableList<Path> sourceFiles = FileUtil.collectRecursively(options.srcDir, ".java");
    if (options.maxFiles > 0) {
      sourceFiles = sourceFiles.subList(0, options.maxFiles);
    }

    for (Path sourceFile : sourceFiles) {
      Path destinationFile = options.outputDir.resolve(options.srcDir.relativize(sourceFile));
      Files.createDirectories(destinationFile.getParent());
      try (FileWriter w = new FileWriter(destinationFile.toFile())) {
        processFile(sourceFile, w);
      }
    }
  }

  private static void processFile(Path file, Writer output) throws IOException {
    CompilationUnit parse = JavaParser.parse(file);
    List<Comment> comments = parse.getAllContainedComments();
    TokenRange javaTokens =
        parse
            .getTokenRange()
            .orElseThrow(() -> new IOException("Failed to get tokens from " + file));

    for (JavaToken token : javaTokens) {
      Range position =
          token
              .getRange()
              .orElseThrow(
                  () ->
                      new IOException(
                          "Failed to get token range for " + token.getText() + " from " + file));

      boolean inRootComment =
          parse
              .getComment()
              .flatMap(c -> c.getRange().map(r -> r.contains(position)))
              .orElse(false);

      boolean inOtherComments =
          comments
              .stream()
              .anyMatch(c -> c.getRange().map(r -> r.contains(position)).orElse(false));

      if (!inRootComment && !inOtherComments) {
        switch (token.getKind()) {
          case GeneratedJavaParserConstants.IDENTIFIER:
            output.write(String.format("\"<IDENTIFIER:%s>\" ", token.getText()));
            break;
          case GeneratedJavaParserConstants.SPACE:
            break;
          default:
            output.write(GeneratedJavaParserConstants.tokenImage[token.getKind()] + " ");
        }
      }
    }
  }
}
