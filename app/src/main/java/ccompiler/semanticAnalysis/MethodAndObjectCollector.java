package ccompiler.semanticAnalysis;

import java.util.ArrayList;
import java.util.List;

import ccompiler.CompilerException;
import ccompiler.parser.AEClass;
import ccompiler.parser.AEFormal;
import ccompiler.parser.AEProgram;
import ccompiler.parser.ASTVisitor;
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
import ccompiler.parser.feature.AEFeature;
import ccompiler.parser.feature.AEFunction;
import ccompiler.semanticAnalysis.typechecking.Type;

public class MethodAndObjectCollector implements ASTVisitor{
    private MethodEnvironment methods;
    private ObjectEnvironment objects;
    private Type currentClass;

    public MethodAndObjectCollector(MethodEnvironment methods, ObjectEnvironment objects) {
        this.methods = methods;
        this.objects = objects;
        this.currentClass = null;

    }

    @Override
    public void visitProgram(AEProgram program) throws CompilerException{
        for (AEClass aeClass : program.getClasses()) {
            this.currentClass = aeClass.getType();
            aeClass.acceptVisitor(this);

        }
    }

    @Override
    public void visitClass(AEClass c) throws CompilerException{
        for (AEFeature feature : c.getFeatures()) {
            feature.acceptVisitor(this);
        }
    }

    @Override
    public void visitFunction(AEFunction function) throws CompilerException {
        List<AEFormal> formals = function.getFormals();
        List<Type> parameterTypes = new ArrayList<>();
        for (AEFormal formal : formals) {
            parameterTypes.add(formal.getType());
        }
        parameterTypes.add(function.getType());
        this.methods.addMethod(this.currentClass, function.getIdentifier(), parameterTypes);
    }

    @Override
    public void visitAttribute(AEAttribute attribute) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitAttribute'");
    }

    @Override
    public void visitFormal(AEFormal aeFormal) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFormal'");
    }

    @Override
    public void visitIfElse(AEIfElse aeIfElse) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitIfElse'");
    }

    @Override
    public void visitEnclosedExpression(AEEnclosedExpression aeEnclosedExpression) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitEnclosedExpression'");
    }

    @Override
    public void visitPlus(AEPlus aePlus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitPlus'");
    }

    @Override
    public void visitMinus(AEMinus aeMinus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitMinus'");
    }

    @Override
    public void visitMultiply(AEMultiply aeMultiply) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitMultiply'");
    }

    @Override
    public void visitDivide(AEDivide aeDivide) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitDivide'");
    }

    @Override
    public void visitWhile(AEWhile aeWhile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitWhile'");
    }

    @Override
    public void visitExpressionBlock(AEExpressionBlock aeExpressionBlock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitExpressionBlock'");
    }

    @Override
    public void visitEquals(AEEquals aeEquals) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitEquals'");
    }

    @Override
    public void visitLet(AELet aeLet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitLet'");
    }

    @Override
    public void visitVoid(AEIsVoid aeIsVoid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitVoid'");
    }

    @Override
    public void visitTrue(AETrue aeTrue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTrue'");
    }

    @Override
    public void visitFalse(AEFalse aeFalse) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFalse'");
    }
}
