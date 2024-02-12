package ccompiler.parser.expression;

import java.util.List;

import ccompiler.CompilerException;
import ccompiler.parser.ASTVisitor;
import ccompiler.parser.feature.AEAttribute;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AELet extends AEExpression {
    private List<AEAttribute> attributes;
    private AEExpression expression;

    public AELet(List<AEAttribute> attributes, AEExpression expression, int row, int column) {
        this.attributes = attributes;
        this.expression = expression;
        this.row = row;
        this.column = column;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitLet(this);
    }

    public List<AEAttribute> getAttributes() {
        return attributes;
    }

    public AEExpression getExpression() {
        return expression;
    }
    @Override
    public String toString() {
        String res = "let ";
        for (AEAttribute attr : this.attributes) {
            res += attr.toString()  + ", ";
        }
        if (res.endsWith(", "));
        res = res.substring(0, res.length()-2);
        res += " in ";
        res += this.expression.toString();
        return res;
    }

    @Override
    public String getGraphRepresentation() {
        return "let";
    }


}
