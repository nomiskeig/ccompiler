package ccompiler.parser.expression;

public class AEType{
    private String value;
    public AEType(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
