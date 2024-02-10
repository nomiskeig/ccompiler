package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;
import ccompiler.parser.typechecker.Type;

public class AEEquals extends AEExpression {
    private AEExpression leftSide;
    private AEExpression rightSide;

    public AEEquals(AEExpression leftSide, AEExpression rightSide) {
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
        visitor.visitEquals(this);
	}


    @Override
    public String toString() {
        return this.leftSide.toString() + " = "  +this.rightSide.toString();
    }

    @Override
    public String getGraphRepresentation() {
        return "=";
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }
}
