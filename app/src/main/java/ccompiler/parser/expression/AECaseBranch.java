package ccompiler.parser.expression;

import ccompiler.semanticAnalysis.typechecking.Type;

public class AECaseBranch {
	private AEIdentifier identifier;
    private Type type;
    private AEExpression expression;
    public AECaseBranch(AEIdentifier identifier, Type type, AEExpression expression) {
        this.identifier = identifier;
        this.type = type;
        this.expression = expression;
	}


}
