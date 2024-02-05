package ccompiler.parser;

import ccompiler.parser.expression.AEIdentifier;
import ccompiler.parser.expression.AEType;
import ccompiler.parser.typechecker.Type;

public class AEFormal implements AElement{
    private AEIdentifier identifier;
    private Type type;

	public AEFormal(AEIdentifier identifier, Type type) {
        this.identifier = identifier;
        this.type = type;

	}

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitFormal(this);
	}

    @Override
    public String toString() {
        return this.identifier.toString() + " : " + this.type.toString();
    }

    @Override
    public String getGraphRepresentation() {
        return "formal " + this.identifier.toString() + " : " + this.type.toString();
    }

    @Override
    public Type getType() {
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

}
