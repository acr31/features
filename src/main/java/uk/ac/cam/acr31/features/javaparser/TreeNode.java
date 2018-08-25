package uk.ac.cam.acr31.features.javaparser;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TreeNode {

  final ImmutableList<String> contents;
  final List<TreeNode> children;

  TreeNode() {
    this(ImmutableList.of());
  }

  TreeNode(Iterable<String> contents) {
    this.contents = ImmutableList.copyOf(contents);
    this.children = new ArrayList<>();
  }

  TreeNode(String... contents) {
    this(Arrays.asList(contents));
  }

  @Override
  public String toString() {
    return String.format(
        "(%s)",
        Stream.concat(contents.stream(), children.stream().map(Object::toString))
            .filter(Objects::nonNull)
            .collect(Collectors.joining(",")));
  }
}
