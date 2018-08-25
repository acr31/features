package uk.ac.cam.acr31.features.javaparser;

import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.modules.ModuleExportsStmt;
import com.github.javaparser.ast.modules.ModuleOpensStmt;
import com.github.javaparser.ast.modules.ModuleProvidesStmt;
import com.github.javaparser.ast.modules.ModuleRequiresStmt;
import com.github.javaparser.ast.modules.ModuleUsesStmt;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntryStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.types.ResolvedType;

class SerializerVisitor extends VoidVisitorAdapter<TreeNode> {

  @Override
  public void visit(Name n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getIdentifier()));
  }

  @Override
  public void visit(SimpleName n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getIdentifier()));
  }

  @Override
  public void visit(PrimitiveType n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getType().name()));
  }

  @Override
  public void visit(ArrayType n, TreeNode arg) {
    super.visit(n, createNode(arg, getName(n.getComponentType()) + "[]"));
  }

  @Override
  public void visit(ClassOrInterfaceType n, TreeNode arg) {
    TreeNode result = createNode(arg, getName(n));
    n.getScope().ifPresent(l -> l.accept(this, result));
    n.getTypeArguments().ifPresent(l -> l.forEach(v -> v.accept(this, result)));
    n.getAnnotations().forEach(p -> p.accept(this, result));
    n.getComment().ifPresent(l -> l.accept(this, result));
  }

  @Override
  public void visit(AnnotationDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(AnnotationMemberDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ArrayAccessExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ArrayCreationExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ArrayInitializerExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(AssertStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(AssignExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(BinaryExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(BlockComment n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(BlockStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(BooleanLiteralExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(BreakStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(CastExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(CatchClause n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(CharLiteralExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ClassExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ClassOrInterfaceDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(CompilationUnit n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ConditionalExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ConstructorDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ContinueStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(DoStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(DoubleLiteralExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(EmptyStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(EnclosedExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(EnumConstantDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(EnumDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ExplicitConstructorInvocationStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ExpressionStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(FieldAccessExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(FieldDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ForeachStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ForStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(IfStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(InitializerDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(InstanceOfExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(IntegerLiteralExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(JavadocComment n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(LabeledStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(LineComment n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(LongLiteralExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(MarkerAnnotationExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(MemberValuePair n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(MethodCallExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(MethodDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(NameExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(NormalAnnotationExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(NullLiteralExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ObjectCreationExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(PackageDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(Parameter n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ArrayCreationLevel n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(IntersectionType n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(UnionType n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ReturnStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(SingleMemberAnnotationExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(StringLiteralExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(SuperExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(SwitchEntryStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(SwitchStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(SynchronizedStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ThisExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ThrowStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(TryStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(LocalClassDeclarationStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(TypeParameter n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(UnaryExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(UnknownType n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(VariableDeclarationExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(VariableDeclarator n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(VoidType n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(WhileStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(WildcardType n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(LambdaExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(MethodReferenceExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(TypeExpr n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ImportDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ModuleDeclaration n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ModuleRequiresStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ModuleExportsStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ModuleProvidesStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ModuleUsesStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ModuleOpensStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(UnparsableStmt n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(ReceiverParameter n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  @Override
  public void visit(VarType n, TreeNode arg) {
    super.visit(n, createNode(arg, n.getClass().getSimpleName()));
  }

  private static TreeNode createNode(TreeNode arg, String simpleName) {
    TreeNode result = new TreeNode(simpleName);
    arg.children.add(result);
    return result;
  }

  private String getName(Type t) {
    ResolvedType resolve;
    try {
      resolve = t.resolve();
    } catch (UnsolvedSymbolException | UnsupportedOperationException e) {
      return t.asString();
    }
    if (resolve.isReferenceType()) {
      return resolve.asReferenceType().getQualifiedName();
    }
    if (resolve.isPrimitive()) {
      return resolve.asPrimitive().name();
    }
    if (resolve.isVoid()) {
      return "void";
    }
    throw new IllegalArgumentException("Other");
  }
}
