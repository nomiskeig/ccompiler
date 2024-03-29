package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEIdentifier extends AEExpression {
    private String value;

    public AEIdentifier(String value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
    }

    public AEIdentifier(String value) {
        this.value= value;
        this.row = -1;
        this.column = -1;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) throws CompilerException{
        visitor.visitIdentifier(this);
    }

    @Override
    public String getGraphRepresentation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGraphRepresentation'");
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AEIdentifier)) {
            return false;
        }
        AEIdentifier other = (AEIdentifier) o;
        return this.value.equals(other.getValue());
    }

    @Override
    public int hashCode() {
        return 31 + (this.value == null ? 0 : this.value.hashCode());
    }

}
