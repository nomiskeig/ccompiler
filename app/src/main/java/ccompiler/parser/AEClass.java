package ccompiler.parser;

import java.util.List;

import ccompiler.parser.expression.AEType;
import ccompiler.parser.feature.AEFeature;

public class AEClass implements AElement {
    AEType classType;
    AEType inheritType;
    List<AEFeature> features;

    public AEClass(AEType classType, AEType inheritType, List<AEFeature> features) {
        this.classType = classType;
        this.inheritType = inheritType;
        this.features = features;
    }

    @Override
    public void acceptVisitor(ASTVisitor visitor) {
        visitor.visitClass(this);
        for (AEFeature f : this.features) {
            f.acceptVisitor(visitor);
        }
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
}
