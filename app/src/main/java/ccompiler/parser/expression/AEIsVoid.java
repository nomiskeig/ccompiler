package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public  class AEIsVoid extends AEExpression {
    private AEExpression expression;
    public AEIsVoid(AEExpression expression) {
        this.expression = expression;
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
    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

}
