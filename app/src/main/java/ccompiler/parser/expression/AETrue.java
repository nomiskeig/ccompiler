package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AETrue extends AEExpression {

	@Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitTrue(this);
	}

    @Override
    public String getGraphRepresentation() {
        return "True";
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }
    @Override
    public String toString() {
        return "true";

    }


}
