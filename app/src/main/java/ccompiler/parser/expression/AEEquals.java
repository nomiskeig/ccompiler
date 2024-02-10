package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEEquals extends AEExpression {
    private AEExpression leftSide;
    private AEExpression rightSide;

    public AEEquals(AEExpression leftSide, AEExpression rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

	public AEExpression getLeftSide() {
        return leftSide;
    }

    public AEExpression getRightSide() {
        return rightSide;
    }

    @Override
	public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitEquals(this);
	}


    @Override
    public String toString() {
        return this.leftSide.toString() + " = "  +this.rightSide.toString();
    }

    @Override
    public String getGraphRepresentation() {
        return "=";
    }

}
