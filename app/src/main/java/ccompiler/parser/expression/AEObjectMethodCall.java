package ccompiler.parser.expression;

import java.util.List;

import ccompiler.parser.ASTVisitor;

public class AEObjectMethodCall extends AEExpression {
    private AEExpression startExpression;
    private AEType atType;
    private AEIdentifier identifier;
    private List<AEExpression> expressions;

    public AEObjectMethodCall(AEExpression startExpression, AEType atType, AEIdentifier identifier,
            List<AEExpression> expressions) {
        this.startExpression = startExpression;
        this.atType = atType;
        this.identifier = identifier;
        this.expressions = expressions;
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
