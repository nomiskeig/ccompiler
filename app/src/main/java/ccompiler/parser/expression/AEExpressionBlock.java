package ccompiler.parser.expression;

import java.util.List;

import ccompiler.parser.ASTVisitor;

public class AEExpressionBlock extends AEExpression {
    private List<AEExpression> expressions;

    public List<AEExpression> getExpressions() {
        return expressions;
    }

    public AEExpressionBlock(List<AEExpression> expressions) {
        this.expressions = expressions;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitExpressionBlock(this);
        for (AEExpression expr : this.expressions) {
            expr.acceptVisitor(visitor);
        }

	}

    @Override
    public String toString() {
        String res =  "{\n";
        for (AEExpression expr : this.expressions) {
            res += expr.toString() + "\n";
        }
        res += "}";
        return res;
    }

    @Override
    public String getGraphRepresentation() {
        return "{ }";
    }

}
