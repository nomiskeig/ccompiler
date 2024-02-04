package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AENot extends AEExpression {
    private AEExpression expression;

    public AENot(AEExpression expression) {
        this.expression = expression;
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
