package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AESmallerEquals extends AEExpression {
    private AEExpression leftSide;
    private AEExpression rightSide;

    public AESmallerEquals(AEExpression leftSide, AEExpression rightSide, int row, int column) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.row = row;
        this.column = column;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}

    @Override
    public String getGraphRepresentation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGraphRepresentation'");
    }

}
