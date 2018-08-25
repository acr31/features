package uk.ac.cam.acr31.features.javaparser;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;
import java.nio.file.Path;

public class Options extends OptionsBase {

  @Option(
      name = "lib_dir",
      help = "Directory containing dependencies (in jar files)",
      defaultValue = ".",
      converter = PathConverter.class)
  public Path libDir;

  @Option(
      name = "src_dir",
      help = "Directory containing source files to process",
      defaultValue = ".",
      converter = PathConverter.class)
  public Path srcDir;

  @Option(
      name = "output_dir",
      help = "Directory to write output files to",
      defaultValue = ".",
      converter = PathConverter.class)
  public Path outputDir;

  @Option(
      name = "max_files",
      help = "Maximum number of files to process (default: unlimited)",
      defaultValue = "-1")
  public int maxFiles;

  @Option(
      name = "blacklist_file",
      help = "File containing list of files to not process",
      defaultValue = "blacklist.txt",
      converter = PathConverter.class)
  public Path blacklistFile;
}
