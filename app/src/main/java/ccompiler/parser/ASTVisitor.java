package ccompiler.parser;

import ccompiler.parser.expression.AEDivide;
import ccompiler.parser.expression.AEEnclosedExpression;
import ccompiler.parser.expression.AEEquals;
import ccompiler.parser.expression.AEExpressionBlock;
import ccompiler.parser.expression.AEIfElse;
import ccompiler.parser.expression.AEIsVoid;
import ccompiler.parser.expression.AELet;
import ccompiler.parser.expression.AEMinus;
import ccompiler.parser.expression.AEMultiply;
import ccompiler.parser.expression.AEPlus;
import ccompiler.parser.expression.AEWhile;
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
	void visitPlus(AEPlus aePlus);
	void visitMinus(AEMinus aeMinus);
	void visitMultiply(AEMultiply aeMultiply);
	void visitDivide(AEDivide aeDivide);
    void visitWhile(AEWhile aeWhile);
    void visitExpressionBlock(AEExpressionBlock aeExpressionBlock);
    void visitEquals(AEEquals aeEquals);
    void visitLet(AELet aeLet);
    void visitVoid(AEIsVoid aeIsVoid);

}
