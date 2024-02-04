package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public  class AEIsVoid extends AEExpression {
    private AEExpression expression;
    public AEIsVoid(AEExpression expression) {
        this.expression = expression;
    }
	public AEExpression getExpression() {
        return expression;
    }
    @Override
	public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitVoid(this);
        this.expression.acceptVisitor(visitor);

	}

    @Override
    public String toString() {
        return "isvoid " + this.expression.toString();
    }
    @Override
    public String getGraphRepresentation() {
        return "isVoid";
    }

}
