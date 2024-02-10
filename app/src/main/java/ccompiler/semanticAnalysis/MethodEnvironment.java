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

        public Map<AEIdentifier, List<Type>> getMethods() {
            return this.methodMapping;
        }

        @Override
        public String toString() {
            String res = "";
            for (Map.Entry<AEIdentifier, List<Type>> entry : this.methodMapping.entrySet()) {
                res += "    " + entry.getKey().toString() + "(";
                for (Type type : entry.getValue().subList(0, entry.getValue().size() - 1)) {
                    res += type.toString() + ", ";
                }
                if (res.endsWith(", ")) {
                    res = res.substring(0, res.length() - 2);
                }
                res += ") : " + entry.getValue().getLast().toString();
            }
            return res;
        }

    }

    public void addMethod(Type classType, AEIdentifier methodName, List<Type> parameterTypes) throws CompilerException {
        if (this.classMapping.get(classType) == null) {
            this.classMapping.put(classType, new MethodMapping(classType));
        }
        this.classMapping.get(classType).addMethod(methodName, parameterTypes);

    }


    public Map<AEIdentifier, List<Type>> getMethods(Type classType) {
        return this.classMapping.get(classType).getMethods();

    }

    @Override
    public String toString() {
        String res = "Methods:\n";
        for (Map.Entry<Type, MethodMapping> entry : this.classMapping.entrySet()) {
            res += "  class " + entry.getKey().toString() + "\n";
            res += entry.getValue().toString();
        }
        return res;

    }

}
