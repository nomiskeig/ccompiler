package ccompiler.parser.expression;

import java.util.List;

import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEMethodCall extends AEExpression {
    private AEIdentifier identifier;
    private List<AEExpression> expressions;

    public AEMethodCall(AEIdentifier identifier,
            List<AEExpression> expressions, int row, int column) {
        this.identifier = identifier;
        this.expressions = expressions;
        this.row = row;
        this.column = column;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
    }

    @Override
    public String getGraphRepresentation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGraphRepresentation'");
    }

}
