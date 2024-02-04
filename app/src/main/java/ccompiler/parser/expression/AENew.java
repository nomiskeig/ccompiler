package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AENew extends AEExpression {
    private AEType type;

    public AENew(AEType type) {
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
}
