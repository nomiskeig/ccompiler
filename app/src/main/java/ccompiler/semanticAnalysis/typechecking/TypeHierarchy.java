package ccompiler.semanticAnalysis.typechecking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ccompiler.CompilerException;

public class TypeHierarchy {
    // Maps type to supertype
    private Map<Type, Type> hierarchy;

    public TypeHierarchy() {
        this.hierarchy = new HashMap<>();
        this.addType(new Type("Object"));
        this.addType(new Type("Int"), new Type("Object"));
        this.addType(new Type("String"), new Type("Object"));
        this.addType(new Type("Bool"));
        this.addType(new Type("IO"), new Type("Object"));
        this.addType(new Type("SELF_TYPE"));

    }

    public void addType(Type type) {
        this.addType(type, new Type("Object"));

    }

    public void addType(Type type, Type parentType) {
        this.hierarchy.put(type, parentType);

    }

    public List<Type> getSuperClasses(Type type) {
        List<Type> superTypes = new ArrayList<>();
        Type currentType = type;
        superTypes.add(currentType);
        while (true) {
            Type superType = this.hierarchy.get(currentType);
            if (superType.equals(currentType)) {
                break;
            }
            superTypes.add(superType);
            currentType = superType;

        }
        return superTypes;

    }

    /**
     * Returns true if superClass is a superclass of the baseClass
     * 
     * @param baseClass
     * @param superClass
     * @return
     */
    public boolean isSuperClass(Type baseClass, Type superClass) {
        List<Type> superTypes = this.getSuperClasses(baseClass);
        
        return superTypes.contains(superClass);

    }

    /**
     * Returns true if subClass is a subclass of the baseClass
     * 
     * @param subClass
     * @param baseClass
     * @return
     */
    public boolean isSubClass(Type subClass, Type baseClass) {
        List<Type> superTypes = this.getSuperClasses(subClass);
        return superTypes.contains(baseClass);

    }

    public Type getCommonSuperclass(Type typeA, Type typeB) throws CompilerException {
        List<Type> superTypesA = this.getSuperClasses(typeA);
        List<Type> superTypesB = this.getSuperClasses(typeB);
        for (Type type : superTypesA) {
            if (superTypesB.contains(type)) {
                return type;
            }
        }
        throw new CompilerException(typeA + " and " + typeB
                + " do not have a common parent. This is a bug in the compiler as Object is parent of all types.");

    }

    @Override
    public String toString() {
        String res = "";
        for (Map.Entry<Type, Type> entry : this.hierarchy.entrySet()) {
            res += entry.getKey() + ", parent: " + entry.getValue() + "\n";
        }
        return res;

    }

}
