package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEInversion extends AEExpression {
    private AEExpression value;

    public AEInversion(AEExpression value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
    }

    public AEExpression getValue() {
        return value;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}

    @Override
    public String getGraphRepresentation() {
        return "~";
    }


    @Override
    public String toString() {
        return "~" + this.value.toString();

    }


    

}
