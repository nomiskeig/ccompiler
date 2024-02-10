package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;
import ccompiler.parser.typechecker.Type;

public class AEFalse extends AEExpression {

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitFalse(this);
	}

    @Override
    public String getGraphRepresentation() {
        return "False";
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public String toString() {
        return "false";

    }

}
