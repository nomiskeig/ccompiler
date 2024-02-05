package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;
import ccompiler.parser.typechecker.Type;

public class AENew extends AEExpression {
    private Type type;

    public AENew(Type type) {
        this.type = type;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
	}

    @Override
    public String toString() {
        return "new " + this.type.toString();
    }

    @Override
    public String getGraphRepresentation() {
        return "new " + this.type.toString();
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }
}
