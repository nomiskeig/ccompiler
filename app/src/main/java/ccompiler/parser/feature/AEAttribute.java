package ccompiler.parser.feature;

import ccompiler.parser.ASTVisitor;
import ccompiler.parser.expression.AEExpression;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.parser.expression.AEType;
import ccompiler.parser.typechecker.Type;

public class AEAttribute extends AEFeature {
    private AEIdentifier identifier;
    private Type type;
    private AEExpression expression;

    public AEAttribute(AEIdentifier id, Type type, AEExpression expression) {
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
        String baseString =  this.identifier.toString() + " : " + this.type.toString();
        if (this.expression != null) {
            baseString += " <- " + this.expression.toString();
        }
        return baseString;

    }

	public AEIdentifier getIdentifier() {
		return identifier;
	}


    public Type getType() {
        return this.type;
    }
    

	public AEExpression getExpression() {
		return expression;
	}

    @Override
    public String getGraphRepresentation() {
        return "attribute " + this.identifier.toString() + " : " + this.type.toString();
    }
}
