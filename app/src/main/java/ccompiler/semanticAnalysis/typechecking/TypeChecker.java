package ccompiler.semanticAnalysis.typechecking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ccompiler.CompilerException;
import ccompiler.ExceptionHandler;
import ccompiler.parser.AEClass;
import ccompiler.parser.AEFormal;
import ccompiler.parser.AEProgram;
import ccompiler.parser.AElement;
import ccompiler.parser.ASTVisitor;
import ccompiler.parser.expression.AEAssignment;
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
import ccompiler.parser.expression.AENew;
import ccompiler.parser.expression.AENot;
import ccompiler.parser.expression.AEPlus;
import ccompiler.parser.expression.AESmaller;
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

    public TypeChecker(MethodEnvironment methods, ObjectEnvironment objects, TypeHierarchy hierarchy) {
        this.methods = methods;
        this.objects = objects;
        this.typeHierarchy = hierarchy;
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
        this.objects.addSubstitution(this.currentClass, new AEIdentifier("self", 0, 0), new Type("SELF_TYPE"));
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
        this.objects.addSubstitution(this.currentClass, new AEIdentifier("self", 0, 0), new Type("SELF_TYPE"));
        expr.acceptVisitor(this);
        this.objects.clearSubstitutions(this.currentClass);
        Type exprType = expr.getType();
        if (!this.typeHierarchy.isSubClass(exprType, attribute.getType())) {
            ExceptionHandler.throwWrongTypeError(expr, attribute.getType(), attribute.getRow(), attribute.getColumn());

        }
    }

    @Override
    public void visitFormal(AEFormal aeFormal) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFormal'");
    }

    @Override
    public void visitIfElse(AEIfElse ifElse) throws CompilerException {
        AEExpression condExpr = ifElse.getCondExpression();
        AEExpression thenExpr = ifElse.getThenExpression();
        AEExpression elseExpr = ifElse.getElseExpression();
        condExpr.acceptVisitor(this);
        if (!condExpr.getType().equals(new Type("Bool"))) {
            throw this.createCompilerException(condExpr, new Type("Bool"));
        }
        thenExpr.acceptVisitor(this);
        elseExpr.acceptVisitor(this);
        Type thenType = thenExpr.getType();
        Type elseType = elseExpr.getType();
        ifElse.setType(this.typeHierarchy.getCommonSuperclass(thenType, elseType));
    }

    @Override
    public void visitEnclosedExpression(AEEnclosedExpression aeEnclosedExpression) throws CompilerException {
        aeEnclosedExpression.getExpression().acceptVisitor(this);

    }

    @Override
    public void visitPlus(AEPlus aePlus) throws CompilerException {
        AEExpression leftSide = aePlus.getLeftSide();
        AEExpression rightSide = aePlus.getRightSide();
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
        Type leftType = leftSide.getType();
        Type rightType = rightSide.getType();
        if (!leftType.equals(new Type("Int"))) {
            throw this.createCompilerException(leftSide, new Type("Int"));
        }
        if (!rightType.equals(new Type("Int"))) {
            throw this.createCompilerException(rightSide, new Type("Int"));
        }
        aePlus.setType(new Type("Int"));
    }

    @Override
    public void visitMinus(AEMinus aeMinus) throws CompilerException {
        AEExpression leftSide = aeMinus.getLeftSide();
        AEExpression rightSide = aeMinus.getRightSide();
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
        Type leftType = leftSide.getType();
        Type rightType = rightSide.getType();
        if (!leftType.equals(new Type("Int"))) {
            throw this.createCompilerException(leftSide, new Type("Int"));
        }
        if (!rightType.equals(new Type("Int"))) {
            throw this.createCompilerException(rightSide, new Type("Int"));
        }
        aeMinus.setType(new Type("Int"));
    }

    @Override
    public void visitMultiply(AEMultiply aeMultiply) throws CompilerException {
        AEExpression leftSide = aeMultiply.getLeftSide();
        AEExpression rightSide = aeMultiply.getRightSide();
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
        Type leftType = leftSide.getType();
        Type rightType = rightSide.getType();
        if (!leftType.equals(new Type("Int"))) {
            ExceptionHandler.throwWrongTypeError(leftSide, new Type("Int"), leftSide.getRow(),
                    leftSide.getColumn());
        }
        if (!rightType.equals(new Type("Int"))) {
            ExceptionHandler.throwWrongTypeError(rightSide, new Type("Int"), rightSide.getRow(),
                    rightSide.getColumn());
        }
        aeMultiply.setType(new Type("Int"));
    }

    @Override
    public void visitDivide(AEDivide aeDivide) throws CompilerException {
        AEExpression leftSide = aeDivide.getLeftSide();
        AEExpression rightSide = aeDivide.getRightSide();
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
        Type leftType = leftSide.getType();
        Type rightType = rightSide.getType();
        if (!leftType.equals(new Type("Int"))) {
            throw this.createCompilerException(leftSide, new Type("Int"));
        }
        if (!rightType.equals(new Type("Int"))) {
            throw this.createCompilerException(rightSide, new Type("Int"));
        }
        aeDivide.setType(new Type("Int"));
    }

    @Override
    public void visitWhile(AEWhile aeWhile) throws CompilerException {
        AEExpression cond = aeWhile.getCondExpression();
        AEExpression body = aeWhile.getBodyExpression();
        body.acceptVisitor(this);
        cond.acceptVisitor(this);

        Type condType = cond.getType();
        if (!condType.equals(new Type("Bool"))) {
            ExceptionHandler.throwWrongTypeError(cond, new Type("Int"));
        }
        aeWhile.setType(new Type("Object"));
    }

    @Override
    public void visitExpressionBlock(AEExpressionBlock aeExpressionBlock) throws CompilerException {
        for (AEExpression expr : aeExpressionBlock.getExpressions()) {
            expr.acceptVisitor(this);
        }
        aeExpressionBlock.setType(aeExpressionBlock.getExpressions().getLast().getType());

    }

    @Override
    public void visitEquals(AEEquals aeEquals) throws CompilerException {
        AEExpression leftSide = aeEquals.getLeftSide();
        AEExpression rightSide = aeEquals.getRightSide();
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
        Type leftType = leftSide.getType();
        Type rightType = rightSide.getType();
        if (!isFreelyComparable(leftType) || !isFreelyComparable(rightType)) {
            if (!leftType.equals(rightType)) {
                ExceptionHandler.throwEqualsError(leftSide, rightSide);
            }
        }
        aeEquals.setType(new Type("Bool"));
    }

    @Override
    public void visitLet(AELet aeLet) throws CompilerException {
        // TODO: this is missing the case without init
        List<AEAttribute> attributes = aeLet.getAttributes();
        if (attributes.size() > 1) {
            // construbct a new aeLet and validate it;
            AELet innerLet = new AELet(attributes.subList(1, attributes.size()), aeLet.getExpression(), aeLet.getRow(),
                    aeLet.getColumn());
            AELet outerLet = new AELet(attributes.subList(0, 1), innerLet, aeLet.getRow(), aeLet.getColumn());
            this.visitLet(outerLet);
            aeLet.setType(outerLet.getType());
            return;
        }
        if (attributes.size() == 1) {
            // only one attribute
            AEAttribute attribute = attributes.getFirst();
            AEExpression expr = attribute.getExpression();
            expr.acceptVisitor(this);
            Type exprType = expr.getType();
            if (!this.typeHierarchy.isSubClass(exprType, attribute.getType())) {
                ExceptionHandler.throwWrongTypeError(expr, attribute.getType());
            }
            this.objects.addSubstitution(this.currentClass, attribute.getIdentifier(), attribute.getType());
            AEExpression bodyExpr = aeLet.getExpression();
            bodyExpr.acceptVisitor(this);
            this.objects.clearSubstitutions(this.currentClass);
            Type bodyType = bodyExpr.getType();
            System.out.println("set the type + "+  bodyType);
            aeLet.setType(bodyType);
        }
    }

    @Override
    public void visitVoid(AEIsVoid aeIsVoid) throws CompilerException {
        AEExpression expr = aeIsVoid.getExpression();
        expr.acceptVisitor(this);
        aeIsVoid.setType(new Type("Bool"));
    }

    @Override
    public void visitTrue(AETrue aeTrue) throws CompilerException {
    }

    @Override
    public void visitFalse(AEFalse aeFalse) throws CompilerException {
    }

    @Override
    public void visitAssignment(AEAssignment aeAssignment) throws CompilerException {
        Type varType = this.objects.getObjects(this.currentClass).get(aeAssignment.getIdentifier());
        aeAssignment.getExpression().acceptVisitor(this);
        Type exprType = aeAssignment.getExpression().getType();
        if (!this.typeHierarchy.isSubClass(exprType, varType)) {
            ExceptionHandler.throwWrongTypeError(aeAssignment.getExpression(), varType, aeAssignment.getRow(),
                    aeAssignment.getColumn());
        }
        aeAssignment.setType(exprType);

    }

    @Override
    public void visitIdentifier(AEIdentifier identifier) throws CompilerException {
        Type type = this.objects.getObjects(this.currentClass).get(identifier);
        if (type == null) {
            throw new CompilerException("Attribute " + identifier + " is not defined");
        }

        identifier.setType(type);

    }

    @Override
    public void visitSmaller(AESmaller aeSmaller) throws CompilerException {
        AEExpression leftSide = aeSmaller.getLeftSide();
        AEExpression rightSide = aeSmaller.getRightSide();
        leftSide.acceptVisitor(this);
        rightSide.acceptVisitor(this);
        Type leftType = leftSide.getType();
        Type rightType = rightSide.getType();
        if (!leftType.equals(new Type("Int"))) {
            throw this.createCompilerException(leftSide, new Type("Int"));
        }
        if (!rightType.equals(new Type("Int"))) {
            throw this.createCompilerException(rightSide, new Type("Int"));
        }
        aeSmaller.setType(new Type("Bool"));
    }

    @Override
    public void visitNew(AENew aeNew) throws CompilerException {
        // TODO: this does not handle SELF_TYPE
        Type newType = aeNew.getType();
        aeNew.setType(newType);

    }

    @Override
    public void visitNot(AENot eaNot) throws CompilerException {
        AEExpression expression = eaNot.getExpression();
        expression.acceptVisitor(this);
        Type exprType = expression.getType();
        if (!exprType.equals(new Type("Bool"))) {
            ExceptionHandler.throwWrongTypeError(expression, new Type("Bool"));
        }
        eaNot.setType(new Type("Bool"));
    }

    private boolean isFreelyComparable(Type type) {
        return !type.equals(new Type("Bool")) && !type.equals(new Type("Int")) && !type.equals(new Type("String"));

    }

}
