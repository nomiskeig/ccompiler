package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEFalse extends AEExpression {

    public AEFalse(int row, int column) {
        this.type = new Type("Bool");
        this.row = row;
        this.column = column;
    }
	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitFalse(this);
	}

    @Override
    public String getGraphRepresentation() {
        return "False";
    }


    @Override
    public String toString() {
        return "false";

    }

}
