package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AEEquals extends AEExpression {
    private AEExpression leftSide;
    private AEExpression rightSide;

    public AEEquals(AEExpression leftSide, AEExpression rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}
}