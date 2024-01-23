package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;

public  class AEIsVoid extends AEExpression {
    private AEExpression expression;
    public AEIsVoid(AEExpression expression) {
        this.expression = expression;
    }
	@Override
	public void acceptVisitor(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}

}
