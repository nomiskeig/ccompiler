package ccompiler.parser.typechecker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TypeHierarchieTest {
    @Test
    void getsDefaultSuperClass() {
        TypeHierarchy th = new TypeHierarchy();
        List<Type> objectSuper = th.getSuperClasses(new Type("Object"));
        assertEquals(1, objectSuper.size());
        assertTrue(objectSuper.contains(new Type("Object")));
        List<Type> integerSuper = th.getSuperClasses(new Type("Int"));
        assertEquals(2, integerSuper.size());
        assertTrue(integerSuper.contains(new Type("Object")));
        assertTrue(integerSuper.contains(new Type("Int")));

    }
    @Test
    void getsExpandedSuperClass() {
        TypeHierarchy th = new TypeHierarchy();
        th.addType(new Type("A"));
        th.addType(new Type("B"), new Type("A"));
        th.addType(new Type("C"), new Type("B"));
        List<Type> types = th.getSuperClasses(new Type("C"));
        assertEquals(4, types.size());
        assertTrue(types.contains(new Type("C")));
        assertTrue(types.contains(new Type("B")));
        assertTrue(types.contains(new Type("A")));
        assertTrue(types.contains(new Type("Object")));

    }

}
