package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AEEnclosedExpression extends AEExpression {
    private AEExpression expression;

    public AEEnclosedExpression(AEExpression expression) {
        this.expression = expression;
    }

    public AEExpression getExpression() {
        return expression;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitEnclosedExpression(this);
        this.expression.acceptVisitor(visitor);
	}
    @Override
    public String toString() {
        return "(" + this.expression.toString() + ")";
    }

    @Override
    public String getGraphRepresentation() {
        return "( )";
    }

}
