package ccompiler.semanticAnalysis.typechecking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ccompiler.CompilerException;
import ccompiler.parser.AEClass;
import ccompiler.parser.AEProgram;
import ccompiler.parser.expression.AEExpression;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.parser.expression.AEPlus;
import ccompiler.parser.feature.AEAttribute;
import ccompiler.parser.feature.AEFeature;

public class TypeChecker {
    private Map<Type, Map<AEIdentifier, Type>> objectEnvironment;
    private Map<Type, Map<AEIdentifier, Type>> methodEnvironment;
    private TypeHierarchy typeHierarchy;

    public TypeChecker() {
        this.objectEnvironment = new HashMap<>();
        this.methodEnvironment = new HashMap<>();
        this.typeHierarchy = new TypeHierarchy();
    }

    public void validateClass(AEClass aeClass) throws CompilerException {
        for (AEFeature feat : aeClass.getFeatures()) {
            if (feat instanceof AEAttribute) {
                this.validateAttribute((AEAttribute) feat, aeClass.getType());

            }

        }

    }

    private void validateAttribute(AEAttribute attr, Type classType) throws CompilerException {
        Type xType = this.getObjectMapping(classType, attr.getIdentifier(), attr.getType());
        // TODO : getType should take the objectEnvironment and the methodEnvironment of
        // the class


        Type eType = attr.getType();
        if (!this.typeHierarchy.isSubClass(eType, xType)) {
            throw new CompilerException("Cannot assign " + attr.getExpression().toString() + " to "
                    + attr.getIdentifier().toString() + " because " + attr.getIdentifier() + " is of type "
                    + xType.toString()
                    + ", which is not compatible with type " + attr.getExpression().getType() + ".");
        }

    }

    public void validatePlus(AEPlus plus) throws CompilerException {
        if (!plus.getRightSide().getType().equals(new Type("Int"))) {
            throw createCompilerException(plus.getRightSide(), new Type("Int"));
        }
        if (!plus.getLeftSide().getType().equals(new Type("Int"))) {
            throw createCompilerException(plus.getLeftSide(), new Type("Int"));
        }

    }

    private CompilerException createCompilerException(AEExpression expression, Type shouldBe) {
        return new CompilerException(
                "Expression " + expression.toString() + " is of type " + expression.getType().toString()
                        + " but should be of type " + shouldBe.toString());

    }

    public void validateProgram(AEProgram program) throws CompilerException {
        for (AEClass aeClass : program.getClasses()) {
            this.validateClass(aeClass);
        }
    }

    // adds object mapping if not present
    private void addObjectMapping(Type classType, AEIdentifier id, Type type) {
        if (this.objectEnvironment.get(classType) == null) {
            this.objectEnvironment.put(classType, new HashMap<>());
        }
        if (this.objectEnvironment.get(classType).get(id) == null) {
            this.objectEnvironment.get(classType).put(id, type);
        }

    }

    // checks that classType -> id has expected type, stores the expected type if
    // not present
    private Type getObjectMapping(Type classType, AEIdentifier id, Type expected) {
        this.addObjectMapping(classType, id, expected);
        return this.objectEnvironment.get(classType).get(id);

    }

}
