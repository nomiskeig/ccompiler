package ccompiler.parser.expression;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AECaseBranch extends AEExpression{
	private AEIdentifier identifier;
    private Type type;
    private AEExpression expression;
    public AECaseBranch(AEIdentifier identifier, Type type, AEExpression expression, int row, int column) {
        this.identifier = identifier;
        this.type = type;
        this.expression = expression;
        this.row = row;
        this.column = column;
	}
    @Override
    public String getGraphRepresentation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGraphRepresentation'");
    }
    @Override
    public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
    }


}
