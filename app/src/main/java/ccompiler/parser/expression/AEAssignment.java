package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;

public class AEAssignment extends AEExpression {
    private AEIdentifier identifier;
    private AEExpression expression;

    public AEAssignment(AEIdentifier id, AEExpression expression) {
        this.identifier = id;
        this.expression = expression;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException{
        visitor.visitAssignment(this);
	}

    @Override
    public String getGraphRepresentation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGraphRepresentation'");
    }

    public AEIdentifier getIdentifier() {
        return identifier;
    }

    public AEExpression getExpression() {
        return expression;
    }


    @Override
    public String toString() {
        return this.identifier + " <- " + this.expression.toString();
    }


}
