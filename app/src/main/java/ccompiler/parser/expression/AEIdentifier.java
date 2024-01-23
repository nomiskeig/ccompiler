package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public class AEIdentifier extends AEExpression {
    private String value;
    public AEIdentifier(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}


}
