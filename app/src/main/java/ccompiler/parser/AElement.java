package ccompiler.parser;

import ccompiler.CompilerException;
import ccompiler.semanticAnalysis.typechecking.Type;

public interface AElement extends GraphElement {
    void acceptVisitor(ASTVisitor visitor) throws CompilerException;
    Type getType();
}
