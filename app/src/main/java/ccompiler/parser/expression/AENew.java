package ccompiler.parser.expression;

import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AENew extends AEExpression {
    private Type type;

    public AENew(Type type) {
        this.type = type;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) {
	}

    @Override
    public String toString() {
        return "new " + this.type.toString();
    }

    @Override
    public String getGraphRepresentation() {
        return "new " + this.type.toString();
    }

}
