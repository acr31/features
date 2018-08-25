package uk.ac.cam.acr31.features.javaparser;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

class FileUtil {

  static ImmutableList<Path> collectRecursively(Path root, String extension) throws IOException {
    ImmutableList.Builder<Path> builder = ImmutableList.builder();
    Files.walkFileTree(
        root,
        new SimpleFileVisitor<>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (file.toString().endsWith(extension)) {
              builder.add(file);
            }
            return FileVisitResult.CONTINUE;
          }
        });
    return builder.build();
  }
}
