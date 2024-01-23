package ccompiler.parser;

import java.util.List;

public class AEProgram implements AElement {
    private List<AEClass> classes;

    public AEProgram(List<AEClass> classes) {
        this.classes = classes;
    }

    public List<AEClass> getClasses() {
        return this.classes;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitProgram(this);
        for (AEClass c : classes) {
            c.acceptVisitor(visitor);
        }
    }


    @Override
    public String toString() {
        return "Program";
    }

}