package uk.ac.cam.acr31.features.javaparser;

import com.google.devtools.common.options.Converter;
import com.google.devtools.common.options.OptionsParsingException;
import java.io.File;
import java.nio.file.Path;

public class PathConverter implements Converter {
  @Override
  public Path convert(String s) throws OptionsParsingException {
    try {
      return new File(s).toPath();
    } catch (IllegalArgumentException e) {
      throw new OptionsParsingException("Failed to convert " + s, e);
    }
  }

  @Override
  public String getTypeDescription() {
    return "a path";
  }
}
