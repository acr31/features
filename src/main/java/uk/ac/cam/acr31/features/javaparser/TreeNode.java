package uk.ac.cam.acr31.features.javaparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TreeNode {

  final String contents;
  final List<TreeNode> children;

  TreeNode() {
      this(null);
  }

  TreeNode(String contents) {
    this.contents = contents;
    this.children = new ArrayList<>();
  }

  @Override
  public String toString() {
    return String.format(
        "(%s)",
        Stream.concat(Stream.of(contents), children.stream().map(Object::toString)).filter(Objects::nonNull)
            .collect(Collectors.joining(",")));
  }
}
