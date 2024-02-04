package ccompiler.parser.feature;

import java.util.List;

import ccompiler.parser.AEFormal;
import ccompiler.parser.ASTVisitor;
import ccompiler.parser.expression.AEExpression;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.parser.expression.AEType;

/**
 * 
 */
public class AEFunction extends AEFeature {
    private AEIdentifier identifier;
    private AEType type;
    private List<AEFormal> formals;
    private AEExpression expression;

    public AEFunction(AEIdentifier identifier, List<AEFormal> formals, AEType type, AEExpression expression) {
        this.identifier = identifier;
        this.type = type;
        this.formals = formals;
        this.expression = expression;

    }

    public AEIdentifier getIdentifier() {
        return identifier;
    }

    public AEType getType() {
        return type;
    }

    public List<AEFormal> getFormals() {
        return formals;
    }

    public AEExpression getExpression() {
        return expression;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitFunction(this);
        this.expression.acceptVisitor(visitor);

    }

    @Override
    public String toString() {
        String res = this.identifier.toString() + "(";
        for (AEFormal formal : this.formals) {
            res += formal.toString() + ", ";
        }
        if (res.endsWith(", ")) {
            res = res.substring(0, res.length() - 2);
        }
        res += ") : " + this.type.toString() + " {\n" + this.expression.toString() + "\n}";
        return res;

    }

    @Override
    public String getGraphRepresentation() {
        return "function " + this.identifier.toString() + " : " + this.type.toString();
    }

}
