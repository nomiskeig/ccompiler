package ccompiler.parser.expression;

import java.util.List;

import ccompiler.parser.ASTVisitor;

public class AECase extends AEExpression {
    private AEExpression expression;
    private List<AECaseBranch> branches;

    public AECase(AEExpression expression, List<AECaseBranch> branches) {
        this.expression = expression;
        this.branches = branches;
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
