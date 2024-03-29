package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AENew extends AEExpression {
    private Type type;

    public AENew(Type type, int row, int column) {
        this.type = type;
        this.row = row;
        this.column = column;
    }

	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitNew(this);
	}

    @Override
    public String toString() {
        return "new " + this.type.toString();
    }

    @Override
    public String getGraphRepresentation() {
        return "new " + this.type.toString();
    }

    public Type getType() {
        return type;
    }

}
