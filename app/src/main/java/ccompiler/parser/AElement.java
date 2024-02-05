package ccompiler.parser;

import ccompiler.parser.typechecker.Type;

public interface AElement extends GraphElement {
    void acceptVisitor(ASTVisitor visitor);
    Type getType();
}
