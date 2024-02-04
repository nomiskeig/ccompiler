package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AEWhile extends AEExpression {
    private AEExpression condExpression;
    private AEExpression bodyExpression;

    public AEWhile(AEExpression condExpression,  AEExpression bodyExpression) {
        this.condExpression = condExpression;
        this.bodyExpression = bodyExpression;

    }

    public AEExpression getBodyExpression() {
        return bodyExpression;
    }

    public AEExpression getCondExpression() {
        return condExpression;
    }

    public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitWhile(this);
        this.condExpression.acceptVisitor(visitor);
        this.bodyExpression.acceptVisitor(visitor);

    }

    @Override
    public String toString() {
        return "while " + this.condExpression.toString() + " loop " + this.bodyExpression.toString() + " pool";
    }

    @Override
    public String getGraphRepresentation() {
        return "while";
    }

}
