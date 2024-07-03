package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;

public class AENot extends AEExpression {
    private AEExpression expression;

    public AEExpression getExpression() {
		return expression;
	}

	public AENot(AEExpression expression, int row, int column) {
        this.expression = expression;
        this.row = row;
        this.column = column;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException{
        visitor.visitNot(this);
	}

    @Override
    public String getGraphRepresentation() {
        return "not";

    }


}
