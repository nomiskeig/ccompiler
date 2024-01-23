package ccompiler.parser;

import ccompiler.parser.expression.AEIdentifier;
import ccompiler.parser.expression.AEType;

public class AEFormal implements AElement{
    private AEIdentifier identifier;
    private AEType type;

	public AEFormal(AEIdentifier identifier, AEType type) {
        this.identifier = identifier;
        this.type = type;

	}

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitFormal(this);
	}

    @Override
    public String toString() {
        return "formal " + this.identifier.toString() + " : " + this.type.toString();
    }

}
