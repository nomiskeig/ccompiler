package ccompiler.parser;

import ccompiler.CompilerException;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEFormal extends AElement{
    private AEIdentifier identifier;
    private Type type;

	public AEFormal(AEIdentifier identifier, Type type, int row, int column) {
        this.identifier = identifier;
        this.type = type;
        this.row = row;
        this.column = column;
	}

	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
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
        return this.type;
    }

    public AEIdentifier getIdentifier() {
        return identifier;
    }


}
