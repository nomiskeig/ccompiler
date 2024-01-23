package ccompiler.parser.feature;

import ccompiler.parser.ASTVisitor;
import ccompiler.parser.expression.AEExpression;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.parser.expression.AEType;

public class AEAttribute extends AEFeature {
    private AEIdentifier identifier;
    private AEType type;
    private AEExpression expression;

    public AEAttribute(AEIdentifier id, AEType type, AEExpression expression) {
        this.identifier = id;
        this.type = type;
        this.expression = expression;

    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitAttribute(this);
        if (this.expression != null) {
            this.expression.acceptVisitor(visitor);
        }
    }

    @Override
    public String toString() {
        return "attribute " + this.identifier.toString() + " : " + this.type.toString();
    }

	public AEIdentifier getIdentifier() {
		return identifier;
	}

	public AEType getType() {
		return type;
	}

	public AEExpression getExpression() {
		return expression;
	}
}
