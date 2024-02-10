package ccompiler.semanticAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ccompiler.CompilerException;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.semanticAnalysis.typechecking.Type;

public class MethodEnvironment {
    private Map<Type, MethodMapping> classMapping;

    public MethodEnvironment() {
        this.classMapping = new HashMap<>();
    }

    private class MethodMapping {
        private Map<AEIdentifier, List<Type>> methodMapping;
        private Type className;

        public MethodMapping(Type className) {
            this.methodMapping = new HashMap<>();
        }

        public void addMethod(AEIdentifier methodName, List<Type> parameterTypes) throws CompilerException {
            if (this.methodMapping.get(methodName) != null) {
                throw new CompilerException(
                        "Method " + methodName + " in class " + this.className + " is defined multiple times");
            }
            if (this.methodMapping.get(methodName) == null) {
                this.methodMapping.put(methodName, parameterTypes);
            }

        }

    }

    public void addMethod(Type classType, AEIdentifier methodName, List<Type> parameterTypes) throws CompilerException {
        if (this.classMapping.get(classType) == null) {
            this.classMapping.put(classType, new MethodMapping(classType));
        }
        this.classMapping.get(classType).addMethod(methodName, parameterTypes);

    }

    

}
