package ccompiler.semanticAnalysis.typechecking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ccompiler.CompilerException;
import ccompiler.parser.AEClass;
import ccompiler.parser.AEFormal;
import ccompiler.parser.AEProgram;
import ccompiler.parser.ASTVisitor;
import ccompiler.parser.expression.AEDivide;
import ccompiler.parser.expression.AEEnclosedExpression;
import ccompiler.parser.expression.AEEquals;
import ccompiler.parser.expression.AEExpression;
import ccompiler.parser.expression.AEExpressionBlock;
import ccompiler.parser.expression.AEFalse;
import ccompiler.parser.expression.AEIdentifier;
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
import ccompiler.semanticAnalysis.MethodEnvironment;
import ccompiler.semanticAnalysis.ObjectEnvironment;

public class TypeChecker implements ASTVisitor {
    private MethodEnvironment methods;
    private ObjectEnvironment objects;
    private TypeHierarchy typeHierarchy;
    private Type currentClass;

    public TypeChecker(MethodEnvironment methods, ObjectEnvironment objects) {
        this.methods = methods;
        this.objects = objects;
        this.typeHierarchy = new TypeHierarchy();
        this.currentClass = null;
    }

    private CompilerException createCompilerException(AEExpression expression, Type shouldBe) {
        return new CompilerException(
                "Expression " + expression.toString() + " is of type " + expression.getType().toString()
                        + " but should be of type " + shouldBe.toString());

    }

    @Override
    public void visitProgram(AEProgram program) throws CompilerException {
        for (AEClass aeClass : program.getClasses()) {
            currentClass = aeClass.getType();
            aeClass.acceptVisitor(this);
        }
    }

    @Override
    public void visitClass(AEClass c) throws CompilerException {
        for (AEFeature feature : c.getFeatures()) {
            feature.acceptVisitor(this);
        }
    }

    @Override
    public void visitFunction(AEFunction function) throws CompilerException {
        AEExpression expr = function.getExpression();
        for (AEFormal formal : function.getFormals()) {
            this.objects.addSubstitution(this.currentClass, formal.getIdentifier(), formal.getType());
        }
        this.objects.addSubstitution(this.currentClass, new AEIdentifier("self"), new Type("SELF_TYPE"));
        expr.acceptVisitor(this);
        this.objects.clearSubstitutions(this.currentClass);
        Type exprType = expr.getType();
        // TODO: there is are two cases in the doc
        if (!this.typeHierarchy.isSubClass(exprType, function.getType())) {
            throw this.createCompilerException(function.getExpression(), function.getType());
        }
    }


    @Override
    public void visitAttribute(AEAttribute attribute) throws CompilerException {
        AEExpression expr = attribute.getExpression();
        if (expr == null) {
            return;
        }
        this.objects.addSubstitution(this.currentClass, new AEIdentifier("self"), new Type("SELF_TYPE"));
        expr.acceptVisitor(this);
        this.objects.clearSubstitutions(this.currentClass);
        Type exprType = expr.getType();
        if (!this.typeHierarchy.isSubClass(exprType, attribute.getType())) {
            throw this.createCompilerException(expr, attribute.getType());
        }
    }

    @Override
    public void visitFormal(AEFormal aeFormal) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFormal'");
    }

    @Override
    public void visitIfElse(AEIfElse aeIfElse) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitIfElse'");
    }

    @Override
    public void visitEnclosedExpression(AEEnclosedExpression aeEnclosedExpression) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitEnclosedExpression'");
    }

    @Override
    public void visitPlus(AEPlus aePlus) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitPlus'");
    }

    @Override
    public void visitMinus(AEMinus aeMinus) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitMinus'");
    }

    @Override
    public void visitMultiply(AEMultiply aeMultiply) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitMultiply'");
    }

    @Override
    public void visitDivide(AEDivide aeDivide) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitDivide'");
    }

    @Override
    public void visitWhile(AEWhile aeWhile) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitWhile'");
    }

    @Override
    public void visitExpressionBlock(AEExpressionBlock aeExpressionBlock) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitExpressionBlock'");
    }

    @Override
    public void visitEquals(AEEquals aeEquals) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitEquals'");
    }

    @Override
    public void visitLet(AELet aeLet) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitLet'");
    }

    @Override
    public void visitVoid(AEIsVoid aeIsVoid) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitVoid'");
    }

    @Override
    public void visitTrue(AETrue aeTrue) throws CompilerException {
    }

    @Override
    public void visitFalse(AEFalse aeFalse) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFalse'");
    }

}
