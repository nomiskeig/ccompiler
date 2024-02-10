package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEEnclosedExpression extends AEExpression {
    private AEExpression expression;

    public AEEnclosedExpression(AEExpression expression) {
        this.expression = expression;
    }

    public AEExpression getExpression() {
        return expression;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitEnclosedExpression(this);
	}
    @Override
    public String toString() {
        return "(" + this.expression.toString() + ")";
    }

    @Override
    public String getGraphRepresentation() {
        return "( )";
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

}
