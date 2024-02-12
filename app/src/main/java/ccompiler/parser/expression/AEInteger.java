package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEInteger extends AEExpression {
    private String value;

    public AEInteger(String value, int row, int column) {
        this.value = value;
        this.type = new Type("Int");
        this.row = row;
        this.column = column;
    }

    public String getValue() {
        return value;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
	}

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public String getGraphRepresentation() {
        return this.value;
    }


}
