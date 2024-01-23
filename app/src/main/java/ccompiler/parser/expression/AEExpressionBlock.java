package ccompiler.parser.expression;

import java.util.List;

import ccompiler.parser.ASTVisitor;

public class AEExpressionBlock extends AEExpression {
    private List<AEExpression> expressions;

    public AEExpressionBlock(List<AEExpression> expressions) {
        this.expressions = expressions;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}

}
