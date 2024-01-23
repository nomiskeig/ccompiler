package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AEInteger extends AEExpression {
    private String value;

    public AEInteger(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
	}

    @Override
    public String toString() {
        return this.value;
    }

}
