package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;

public class AESmaller extends AEExpression {
    private AEExpression leftSide;
    private AEExpression rightSide;

    public AESmaller(AEExpression leftSide, AEExpression rightSide, int row, int column) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.row = row;
        this.column = column;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException{
        visitor.visitSmaller(this);
	}

    @Override
    public String getGraphRepresentation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGraphRepresentation'");
    }

    public AEExpression getLeftSide() {
        return leftSide;
    }

    public AEExpression getRightSide() {
        return rightSide;
    }

    @Override
    public String toString() {
        return this.leftSide.toString() + " < " + this.rightSide.toString();
    }

}
