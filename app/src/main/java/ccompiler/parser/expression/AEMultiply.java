package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AEMultiply extends AEExpression {
    private AEExpression leftSide;
    private AEExpression rightSide;

    public AEMultiply(AEExpression leftSide, AEExpression rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

	public AEExpression getLeftSide() {
		return leftSide;
	}

	public AEExpression getRightSide() {
		return rightSide;
	}

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitMultiply(this);
        this.leftSide.acceptVisitor(visitor);
        this.rightSide.acceptVisitor(visitor);
	}


    @Override
    public String toString() {
        return this.leftSide.toString() + " * "  +this.rightSide.toString();
    }

    @Override
    public String getGraphRepresentation() {
        return "*";
    }
}
