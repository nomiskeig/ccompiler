package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AETrue extends AEExpression {
    public AETrue(int row, int column) {
        this.type = new Type("Bool");
        this.row = row;
        this.column = column;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitTrue(this);
	}

    @Override
    public String getGraphRepresentation() {
        return "True";
    }

    @Override
    public String toString() {
        return "true";

    }


}
