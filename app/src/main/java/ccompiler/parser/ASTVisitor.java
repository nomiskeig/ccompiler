package ccompiler.parser;

import ccompiler.parser.expression.AEEnclosedExpression;
import ccompiler.parser.expression.AEIfElse;
import ccompiler.parser.feature.AEAttribute;
import ccompiler.parser.feature.AEFunction;

public interface ASTVisitor {
    void visitProgram(AEProgram program);
    void visitClass(AEClass c);
    void visitFunction(AEFunction function);
    void visitAttribute(AEAttribute attribute);
	void visitFormal(AEFormal aeFormal);
	void visitIfElse(AEIfElse aeIfElse);
	void visitEnclosedExpression(AEEnclosedExpression aeEnclosedExpression);

}
