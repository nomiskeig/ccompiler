package ccompiler.semanticAnalysis;

import ccompiler.parser.expression.AEIdentifier;
import ccompiler.semanticAnalysis.typechecking.Type;

public class Substitution {
    private AEIdentifier identifier;
    private Type type;
    public Substitution(AEIdentifier identifier, Type type) {
        this.identifier = identifier;
        this.type = type;
    }
    public AEIdentifier getIdentifier() {
        return identifier;
    }
    public Type getType() {
        return type;
    }
}
