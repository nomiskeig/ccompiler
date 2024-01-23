package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AEAssignment extends AEExpression {
    private AEIdentifier identifier;
    private AEExpression expression;

    public AEAssignment(AEIdentifier id, AEExpression expression) {
        this.identifier = id;
        this.expression = expression;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}
}
