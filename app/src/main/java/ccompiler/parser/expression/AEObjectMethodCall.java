package ccompiler.parser.expression;

import java.util.List;

import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEObjectMethodCall extends AEExpression {
    private AEExpression startExpression;
    private Type atType;
    private AEIdentifier identifier;
    private List<AEExpression> expressions;

    public AEObjectMethodCall(AEExpression startExpression, Type type, AEIdentifier identifier,
            List<AEExpression> expressions) {
        this.startExpression = startExpression;
        this.type = type;
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
