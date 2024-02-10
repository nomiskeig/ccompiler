package ccompiler.parser.feature;

import java.util.List;

import ccompiler.CompilerException;
import ccompiler.parser.AEFormal;
import ccompiler.parser.ASTVisitor;
import ccompiler.parser.expression.AEExpression;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.semanticAnalysis.typechecking.Type;

/**
 * 
 */
public class AEFunction extends AEFeature {
    private AEIdentifier identifier;
    private Type type;
    private List<AEFormal> formals;
    private AEExpression expression;

    public AEFunction(AEIdentifier identifier, List<AEFormal> formals, Type type, AEExpression expression) {
        this.identifier = identifier;
        this.type = type;
        this.formals = formals;
        this.expression = expression;

    }

    public AEIdentifier getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return type;
    }

    public List<AEFormal> getFormals() {
        return formals;
    }

    public AEExpression getExpression() {
        return expression;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitFunction(this);

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
