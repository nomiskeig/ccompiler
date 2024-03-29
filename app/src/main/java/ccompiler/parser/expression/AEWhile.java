package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEWhile extends AEExpression {
    private AEExpression condExpression;
    private AEExpression bodyExpression;

    public AEWhile(AEExpression condExpression,  AEExpression bodyExpression, int row, int column) {
        this.condExpression = condExpression;
        this.bodyExpression = bodyExpression;
        this.row = row;
        this.column = column;
    }

    public AEExpression getBodyExpression() {
        return bodyExpression;
    }

    public AEExpression getCondExpression() {
        return condExpression;
    }

    public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitWhile(this);

    }

    @Override
    public String toString() {
        return "while " + this.condExpression.toString() + " loop " + this.bodyExpression.toString() + " pool";
    }

    @Override
    public String getGraphRepresentation() {
        return "while";
    }


}
