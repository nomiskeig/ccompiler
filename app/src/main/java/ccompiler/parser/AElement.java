package ccompiler.parser;

public interface AElement {
    void acceptVisitor(ASTVisitor visitor);
    String getGraphRepresentation();
}
