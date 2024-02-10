package ccompiler.parser;

import ccompiler.CompilerException;
import ccompiler.parser.expression.AEDivide;
import ccompiler.parser.expression.AEEnclosedExpression;
import ccompiler.parser.expression.AEEquals;
import ccompiler.parser.expression.AEExpressionBlock;
import ccompiler.parser.expression.AEFalse;
import ccompiler.parser.expression.AEIfElse;
import ccompiler.parser.expression.AEIsVoid;
import ccompiler.parser.expression.AELet;
import ccompiler.parser.expression.AEMinus;
import ccompiler.parser.expression.AEMultiply;
import ccompiler.parser.expression.AEPlus;
import ccompiler.parser.expression.AETrue;
import ccompiler.parser.expression.AEWhile;
import ccompiler.parser.feature.AEAttribute;
import ccompiler.parser.feature.AEFunction;

public interface ASTVisitor {
    void visitProgram(AEProgram program) throws CompilerException;

    void visitClass(AEClass c) throws CompilerException;

    void visitFunction(AEFunction function) throws CompilerException;

    void visitAttribute(AEAttribute attribute) throws CompilerException;

    void visitFormal(AEFormal aeFormal) throws CompilerException;

    void visitIfElse(AEIfElse aeIfElse) throws CompilerException;

    void visitEnclosedExpression(AEEnclosedExpression aeEnclosedExpression) throws CompilerException;

    void visitPlus(AEPlus aePlus) throws CompilerException;

    void visitMinus(AEMinus aeMinus) throws CompilerException;

    void visitMultiply(AEMultiply aeMultiply) throws CompilerException;

    void visitDivide(AEDivide aeDivide) throws CompilerException;

    void visitWhile(AEWhile aeWhile) throws CompilerException;

    void visitExpressionBlock(AEExpressionBlock aeExpressionBlock) throws CompilerException;

    void visitEquals(AEEquals aeEquals) throws CompilerException;

    void visitLet(AELet aeLet) throws CompilerException;

    void visitVoid(AEIsVoid aeIsVoid) throws CompilerException;

    void visitTrue(AETrue aeTrue) throws CompilerException;

    void visitFalse(AEFalse aeFalse) throws CompilerException;

}
