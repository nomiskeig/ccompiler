package ccompiler.parser;

import java.util.List;

import ccompiler.CompilerException;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEProgram extends AElement {
    private List<AEClass> classes;

    public AEProgram(List<AEClass> classes, int row, int column) {
        this.classes = classes;
        this.row = row;
        this.column = column;
    }

    public List<AEClass> getClasses() {
        return this.classes;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitProgram(this);
    }


    @Override
    public String toString() {
        String res = "";
        for (AEClass clas : this.classes) {
            res += clas.toString() + "\n \n";

        }
        return res;
    }

    @Override
    public String getGraphRepresentation() {
        return "Program";
    }

    @Override
    public Type getType() {
        // TODO: Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

}
