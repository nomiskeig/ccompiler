package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEMultiply extends AEExpression {
    private AEExpression leftSide;
    private AEExpression rightSide;

    public AEMultiply(AEExpression leftSide, AEExpression rightSide, int row, int column) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.row = row;
        this.column = column;
    }

	public AEExpression getLeftSide() {
		return leftSide;
	}

	public AEExpression getRightSide() {
		return rightSide;
	}

	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitMultiply(this);
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
