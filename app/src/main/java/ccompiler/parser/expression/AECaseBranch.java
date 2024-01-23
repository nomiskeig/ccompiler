package ccompiler.parser.expression;


public class AECaseBranch {
	private AEIdentifier identifier;
    private AEType type;
    private AEExpression expression;
    public AECaseBranch(AEIdentifier identifier, AEType type, AEExpression expression) {
        this.identifier = identifier;
        this.type = type;
        this.expression = expression;
	}


}
