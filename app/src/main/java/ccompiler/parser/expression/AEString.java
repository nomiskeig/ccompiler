package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;
import ccompiler.parser.typechecker.Type;

public class AEString extends AEExpression {
    private String value;

    public AEString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) {
    }

    @Override
    public String toString() {
        return "\"" + this.value + "\"";
    }

    @Override
    public String getGraphRepresentation() {
        return this.value;
    }

    @Override
    public Type getType() {
        return new Type("String");
    }

}
