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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}

}
