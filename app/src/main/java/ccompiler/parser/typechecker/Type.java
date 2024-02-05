package ccompiler.parser.typechecker;

import ccompiler.parser.AElement;
import ccompiler.parser.ASTVisitor;
import ccompiler.parser.GraphElement;

public class Type implements GraphElement{
    private String name;

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Type)) {
            return false;
        }
        Type other = (Type) o;
        return this.name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return 31 + (this.name == null ? 0 : this.name.hashCode());
    }


    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getGraphRepresentation() {
        return this.name;
    }

}
