package ccompiler.parser.expression;

import java.util.List;

import ccompiler.parser.ASTVisitor;
import ccompiler.parser.feature.AEAttribute;

public class AELet extends AEExpression {
    private List<AEAttribute> attributes;
    private AEExpression expression;

    public AELet(List<AEAttribute> attributes, AEExpression expression) {
        this.attributes = attributes;
        this.expression = expression;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
	}

}
