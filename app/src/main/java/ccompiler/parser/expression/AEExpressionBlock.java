package ccompiler.parser.expression;

import java.util.List;

import ccompiler.CompilerException;
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
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitExpressionBlock(this);
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
