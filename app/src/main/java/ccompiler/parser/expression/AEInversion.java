package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AEInversion extends AEExpression {
    private AEExpression value;

    public AEInversion(AEExpression value) {
        this.value = value;
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
