package uk.ac.cam.acr31.features.javaparser;

import com.sun.source.tree.AnnotatedTypeTree;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ErroneousTree;
import com.sun.source.tree.ExportsTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.IntersectionTypeTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.LambdaExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberReferenceTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.ModuleTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.OpensTree;
import com.sun.source.tree.PackageTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.ProvidesTree;
import com.sun.source.tree.RequiresTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.UnionTypeTree;
import com.sun.source.tree.UsesTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import com.sun.source.tree.WildcardTree;
import com.sun.source.util.TreePathScanner;

public class JavacTreePathScanner extends TreePathScanner<Void, TreeNode> {

  @Override
  public Void scan(Tree tree, TreeNode treeNode) {
    if (tree == null) {
      treeNode.children.add(new TreeNode());
    } else {
      tree.accept(this, treeNode);
    }
    return null;
  }

  @Override
  public Void scan(Iterable<? extends Tree> nodes, TreeNode treeNode) {
    TreeNode result = new TreeNode();
    treeNode.children.add(result);
    for (Tree node : nodes) {
      scan(node, result);
    }
    return null;
  }

  @Override
  public Void visitCompilationUnit(CompilationUnitTree node, TreeNode treeNode) {
    TreeNode result = new TreeNode(String.valueOf(node.getKind()));
    treeNode.children.add(result);
    return super.visitCompilationUnit(node, result);
  }

  @Override
  public Void visitPackage(PackageTree node, TreeNode treeNode) {
    TreeNode result = new TreeNode(String.valueOf(node.getKind()));
    treeNode.children.add(result);
    return super.visitPackage(node, result);
  }

  @Override
  public Void visitImport(ImportTree node, TreeNode treeNode) {
    return super.visitImport(node, treeNode);
  }

  @Override
  public Void visitClass(ClassTree node, TreeNode treeNode) {
    return super.visitClass(node, treeNode);
  }

  @Override
  public Void visitMethod(MethodTree node, TreeNode treeNode) {
    return super.visitMethod(node, treeNode);
  }

  @Override
  public Void visitVariable(VariableTree node, TreeNode treeNode) {
    return super.visitVariable(node, treeNode);
  }

  @Override
  public Void visitEmptyStatement(EmptyStatementTree node, TreeNode treeNode) {
    return super.visitEmptyStatement(node, treeNode);
  }

  @Override
  public Void visitBlock(BlockTree node, TreeNode treeNode) {
    return super.visitBlock(node, treeNode);
  }

  @Override
  public Void visitDoWhileLoop(DoWhileLoopTree node, TreeNode treeNode) {
    return super.visitDoWhileLoop(node, treeNode);
  }

  @Override
  public Void visitWhileLoop(WhileLoopTree node, TreeNode treeNode) {
    return super.visitWhileLoop(node, treeNode);
  }

  @Override
  public Void visitForLoop(ForLoopTree node, TreeNode treeNode) {
    return super.visitForLoop(node, treeNode);
  }

  @Override
  public Void visitEnhancedForLoop(EnhancedForLoopTree node, TreeNode treeNode) {
    return super.visitEnhancedForLoop(node, treeNode);
  }

  @Override
  public Void visitLabeledStatement(LabeledStatementTree node, TreeNode treeNode) {
    return super.visitLabeledStatement(node, treeNode);
  }

  @Override
  public Void visitSwitch(SwitchTree node, TreeNode treeNode) {
    return super.visitSwitch(node, treeNode);
  }

  @Override
  public Void visitCase(CaseTree node, TreeNode treeNode) {
    return super.visitCase(node, treeNode);
  }

  @Override
  public Void visitSynchronized(SynchronizedTree node, TreeNode treeNode) {
    return super.visitSynchronized(node, treeNode);
  }

  @Override
  public Void visitTry(TryTree node, TreeNode treeNode) {
    return super.visitTry(node, treeNode);
  }

  @Override
  public Void visitCatch(CatchTree node, TreeNode treeNode) {
    return super.visitCatch(node, treeNode);
  }

  @Override
  public Void visitConditionalExpression(ConditionalExpressionTree node, TreeNode treeNode) {
    return super.visitConditionalExpression(node, treeNode);
  }

  @Override
  public Void visitIf(IfTree node, TreeNode treeNode) {
    return super.visitIf(node, treeNode);
  }

  @Override
  public Void visitExpressionStatement(ExpressionStatementTree node, TreeNode treeNode) {
    return super.visitExpressionStatement(node, treeNode);
  }

  @Override
  public Void visitBreak(BreakTree node, TreeNode treeNode) {
    return super.visitBreak(node, treeNode);
  }

  @Override
  public Void visitContinue(ContinueTree node, TreeNode treeNode) {
    return super.visitContinue(node, treeNode);
  }

  @Override
  public Void visitReturn(ReturnTree node, TreeNode treeNode) {
    return super.visitReturn(node, treeNode);
  }

  @Override
  public Void visitThrow(ThrowTree node, TreeNode treeNode) {
    return super.visitThrow(node, treeNode);
  }

  @Override
  public Void visitAssert(AssertTree node, TreeNode treeNode) {
    return super.visitAssert(node, treeNode);
  }

  @Override
  public Void visitMethodInvocation(MethodInvocationTree node, TreeNode treeNode) {
    return super.visitMethodInvocation(node, treeNode);
  }

  @Override
  public Void visitNewClass(NewClassTree node, TreeNode treeNode) {
    return super.visitNewClass(node, treeNode);
  }

  @Override
  public Void visitNewArray(NewArrayTree node, TreeNode treeNode) {
    return super.visitNewArray(node, treeNode);
  }

  @Override
  public Void visitLambdaExpression(LambdaExpressionTree node, TreeNode treeNode) {
    return super.visitLambdaExpression(node, treeNode);
  }

  @Override
  public Void visitParenthesized(ParenthesizedTree node, TreeNode treeNode) {
    return super.visitParenthesized(node, treeNode);
  }

  @Override
  public Void visitAssignment(AssignmentTree node, TreeNode treeNode) {
    return super.visitAssignment(node, treeNode);
  }

  @Override
  public Void visitCompoundAssignment(CompoundAssignmentTree node, TreeNode treeNode) {
    return super.visitCompoundAssignment(node, treeNode);
  }

  @Override
  public Void visitUnary(UnaryTree node, TreeNode treeNode) {
    return super.visitUnary(node, treeNode);
  }

  @Override
  public Void visitBinary(BinaryTree node, TreeNode treeNode) {
    return super.visitBinary(node, treeNode);
  }

  @Override
  public Void visitTypeCast(TypeCastTree node, TreeNode treeNode) {
    return super.visitTypeCast(node, treeNode);
  }

  @Override
  public Void visitInstanceOf(InstanceOfTree node, TreeNode treeNode) {
    return super.visitInstanceOf(node, treeNode);
  }

  @Override
  public Void visitArrayAccess(ArrayAccessTree node, TreeNode treeNode) {
    return super.visitArrayAccess(node, treeNode);
  }

  @Override
  public Void visitMemberSelect(MemberSelectTree node, TreeNode treeNode) {
    return super.visitMemberSelect(node, treeNode);
  }

  @Override
  public Void visitMemberReference(MemberReferenceTree node, TreeNode treeNode) {
    return super.visitMemberReference(node, treeNode);
  }

  @Override
  public Void visitIdentifier(IdentifierTree node, TreeNode treeNode) {
    return super.visitIdentifier(node, treeNode);
  }

  @Override
  public Void visitLiteral(LiteralTree node, TreeNode treeNode) {
    return super.visitLiteral(node, treeNode);
  }

  @Override
  public Void visitPrimitiveType(PrimitiveTypeTree node, TreeNode treeNode) {
    return super.visitPrimitiveType(node, treeNode);
  }

  @Override
  public Void visitArrayType(ArrayTypeTree node, TreeNode treeNode) {
    return super.visitArrayType(node, treeNode);
  }

  @Override
  public Void visitParameterizedType(ParameterizedTypeTree node, TreeNode treeNode) {
    return super.visitParameterizedType(node, treeNode);
  }

  @Override
  public Void visitUnionType(UnionTypeTree node, TreeNode treeNode) {
    return super.visitUnionType(node, treeNode);
  }

  @Override
  public Void visitIntersectionType(IntersectionTypeTree node, TreeNode treeNode) {
    return super.visitIntersectionType(node, treeNode);
  }

  @Override
  public Void visitTypeParameter(TypeParameterTree node, TreeNode treeNode) {
    return super.visitTypeParameter(node, treeNode);
  }

  @Override
  public Void visitWildcard(WildcardTree node, TreeNode treeNode) {
    return super.visitWildcard(node, treeNode);
  }

  @Override
  public Void visitModifiers(ModifiersTree node, TreeNode treeNode) {
    return super.visitModifiers(node, treeNode);
  }

  @Override
  public Void visitAnnotation(AnnotationTree node, TreeNode treeNode) {
    return super.visitAnnotation(node, treeNode);
  }

  @Override
  public Void visitAnnotatedType(AnnotatedTypeTree node, TreeNode treeNode) {
    return super.visitAnnotatedType(node, treeNode);
  }

  @Override
  public Void visitModule(ModuleTree node, TreeNode treeNode) {
    return super.visitModule(node, treeNode);
  }

  @Override
  public Void visitExports(ExportsTree node, TreeNode treeNode) {
    return super.visitExports(node, treeNode);
  }

  @Override
  public Void visitOpens(OpensTree node, TreeNode treeNode) {
    return super.visitOpens(node, treeNode);
  }

  @Override
  public Void visitProvides(ProvidesTree node, TreeNode treeNode) {
    return super.visitProvides(node, treeNode);
  }

  @Override
  public Void visitRequires(RequiresTree node, TreeNode treeNode) {
    return super.visitRequires(node, treeNode);
  }

  @Override
  public Void visitUses(UsesTree node, TreeNode treeNode) {
    return super.visitUses(node, treeNode);
  }

  @Override
  public Void visitOther(Tree node, TreeNode treeNode) {
    return super.visitOther(node, treeNode);
  }

  @Override
  public Void visitErroneous(ErroneousTree node, TreeNode treeNode) {
    return super.visitErroneous(node, treeNode);
  }
}
