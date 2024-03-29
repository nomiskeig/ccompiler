package ccompiler.parser;

import java.util.List;

import ccompiler.CompilerException;
import ccompiler.parser.feature.AEFeature;
import ccompiler.semanticAnalysis.typechecking.Type;

public class AEClass extends AElement {
    Type classType;
    Type inheritType;
    List<AEFeature> features;

    public AEClass(Type classType, Type inheritType, List<AEFeature> features, int row, int column) {
        this.classType = classType;
        this.inheritType = inheritType;
        this.features = features;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) throws CompilerException {
        visitor.visitClass(this);
    }

    @Override
    public String toString() {
        String res = "class " + this.classType.toString();
        if (this.inheritType != null) {
            res += " inherits " + this.inheritType.toString();
        }
        res += " { ";
        for (AEFeature feature : this.features) {
            res += feature.toString() + "\n";
        }
        return res + "}";

    }

    public List<AEFeature> getFeatures() {
        return this.features;
    }

    @Override
    public String getGraphRepresentation() {
        String res = "class " + this.classType.toString();
        if (this.inheritType != null) {
            res += " inherits " + this.inheritType.toString();
        }
        return res;
    }

    @Override
    public Type getType() {
        return this.classType;
    }


    public Type getInheritType() {
        return inheritType;
    }
}
