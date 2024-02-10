package ccompiler.semanticAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ccompiler.CompilerException;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.semanticAnalysis.typechecking.Type;

public class ObjectEnvironment {
    private Map<Type, ObjectMapping> classMapping;

    public ObjectEnvironment() {
        this.classMapping = new HashMap<>();
    }

    private class ObjectMapping {
        private Map<AEIdentifier, Type> objectMapping;
        private Map<AEIdentifier, Boolean> isSubstitution;
        private Type className;

        public ObjectMapping(Type className) {

            this.className = className;
            this.objectMapping = new HashMap<>();
            this.isSubstitution = new HashMap<>();
        }

        public void addObject(AEIdentifier objectName, Type objectType, boolean isSubstitution)
                throws CompilerException {
            if (this.objectMapping.get(objectName) != null) {
                throw new CompilerException(
                        "Object " + objectName + " in class " + this.className + " is defined multiple times");
            }
            this.objectMapping.put(objectName, objectType);
            this.isSubstitution.put(objectName, true);

        }

        public Map<AEIdentifier, Type> getObjects() {
            return this.objectMapping;
        }

        public void clearSubstitutions() {
            List<AEIdentifier> toRemove = new ArrayList<>();
            for (AEIdentifier identifier : this.objectMapping.keySet()) {
                if (this.isSubstitution.get(identifier)) {
                    toRemove.add(identifier);
                }
            }
            for (AEIdentifier identifier : toRemove) {
                this.isSubstitution.remove(identifier);
                this.objectMapping.remove(identifier);

            }

        }

        @Override
        public String toString() {
            String res = "";
            for (Map.Entry<AEIdentifier, Type> entry : this.objectMapping.entrySet()) {
                res += "    " + entry.getKey().toString() + " : " + entry.getValue().toString();
            }
            return res;
        }

    }

    public void clearSubstitutions(Type classType) {
        if (this.classMapping.get(classType) == null) {
            return;
        }
        this.classMapping.get(classType).clearSubstitutions();

    }

    public void addSubstitution(Type classType, AEIdentifier objectName, Type objectType) throws CompilerException {
        if (this.classMapping.get(classType) == null) {
            this.classMapping.put(classType, new ObjectMapping(classType));
        }
        this.classMapping.get(classType).addObject(objectName, objectType, true);
    }

    public void addObject(Type classType, AEIdentifier objectName, Type objectType) throws CompilerException {
        if (this.classMapping.get(classType) == null) {
            this.classMapping.put(classType, new ObjectMapping(classType));
        }
        this.classMapping.get(classType).addObject(objectName, objectType, false);

    }

    public Map<AEIdentifier, Type> getObjects(Type classType) {
        return this.classMapping.get(classType).getObjects();

    }

    @Override
    public String toString() {
        String res = "Methods:\n";
        for (Map.Entry<Type, ObjectMapping> entry : this.classMapping.entrySet()) {
            res += "  class " + entry.getKey().toString() + "\n";
            res += entry.getValue().toString();
        }
        return res;

    }

}
