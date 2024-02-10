package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;
import ccompiler.parser.typechecker.Type;

public class AEMinus extends AEExpression {
    private AEExpression leftSide;
    private AEExpression rightSide;

    public AEMinus(AEExpression leftSide, AEExpression rightSide) {
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
        visitor.visitMinus(this);
	}


    @Override
    public String toString() {
        return "-";
    }

    @Override
    public String getGraphRepresentation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGraphRepresentation'");
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }
}
