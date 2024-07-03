package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;

public  class AEIsVoid extends AEExpression {
    private AEExpression expression;
    public AEIsVoid(AEExpression expression, int row, int column) {
        this.expression = expression;
        this.row = row;
        this.column = column;
    }
	public AEExpression getExpression() {
        return expression;
    }
    @Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitVoid(this);

	}

    @Override
    public String toString() {
        return "isvoid " + this.expression.toString();
    }
    @Override
    public String getGraphRepresentation() {
        return "isVoid";
    }

}
