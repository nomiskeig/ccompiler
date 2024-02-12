package ccompiler.parser.expression;

import ccompiler.parser.AElement;
import ccompiler.semanticAnalysis.typechecking.Type;

public abstract class AEExpression extends AElement {
    Type type = null;

    @Override
    public Type getType() {
        if (this.type == null) {
            throw new UnsupportedOperationException("Got type null");
        }
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
