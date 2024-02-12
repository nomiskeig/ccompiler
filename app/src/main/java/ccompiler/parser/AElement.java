package ccompiler.parser;

import ccompiler.CompilerException;
import ccompiler.semanticAnalysis.typechecking.Type;

public abstract class AElement implements GraphElement {
    protected int row;
    protected int column;
    abstract public void acceptVisitor(ASTVisitor visitor) throws CompilerException;

    abstract public Type getType();

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
}
