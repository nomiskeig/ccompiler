package ccompiler.parser.expression;

import java.util.List;

import ccompiler.parser.ASTVisitor;

public class AEMethodCall extends AEExpression {
    private AEIdentifier identifier;
    private List<AEExpression> expressions;

    public AEMethodCall(AEIdentifier identifier,
            List<AEExpression> expressions) {
        this.identifier = identifier;
        this.expressions = expressions;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}

}
