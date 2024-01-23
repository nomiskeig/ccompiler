package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AEIfElse extends AEExpression {
    private AEExpression condExpression;
    private AEExpression thenExpression;
    private AEExpression elseExpression;

    public AEIfElse(AEExpression condExpression, AEExpression thenExpression, AEExpression elseExpression) {
        this.condExpression = condExpression;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;

    }

	public AEExpression getCondExpression() {
		return condExpression;
	}

	public AEExpression getThenExpression() {
		return thenExpression;
	}

	public AEExpression getElseExpression() {
		return elseExpression;
	}

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitIfElse(this);
        this.condExpression.acceptVisitor(visitor);
        this.thenExpression.acceptVisitor(visitor);
        this.elseExpression.acceptVisitor(visitor);

	}
    @Override
    public String toString() {
        return "if else";
    }

}
