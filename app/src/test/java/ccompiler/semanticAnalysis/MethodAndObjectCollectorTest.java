package ccompiler.semanticAnalysis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ccompiler.CompilerException;
import ccompiler.Utils;
import ccompiler.parser.AEProgram;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.semanticAnalysis.typechecking.Type;
import ccompiler.semanticAnalysis.typechecking.TypeHierarchy;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class MethodAndJobjectCollectorTest {
    private MethodEnvironment methods;
    private ObjectEnvironment objects;
    private MethodAndObjectCollector collector;
    private TypeHierarchy hierarchy;

    @BeforeEach
    void beforeEach() {
        this.methods = new MethodEnvironment();
        this.objects = new ObjectEnvironment();
        this.hierarchy  =new TypeHierarchy();
        this.collector = new MethodAndObjectCollector(this.methods, this.objects, this.hierarchy);
    }

    @Test
    void collectsMethods() throws CompilerException {
        String programCode = """
                                    class A {
                                        function1(a: Int, b: String) : String {
                                        4
                                };
                                        function2(): Bool {
                                            true

                };
                                };
                                    class B {
                                        function3(adsf: Abc) : String {abck};
                        };
                                """;
        AEProgram program = Utils.createProgram(programCode);
        program.acceptVisitor(this.collector);
        Map<AEIdentifier, List<Type>> methods = this.methods.getMethods(new Type("A"));
        assertEquals(2, methods.size());
        List<Type> function1 = methods.get(new AEIdentifier("function1"));
        assertNotNull(function1);
        assertEquals(3, function1.size());
        assertEquals(List.of(new Type("Int"), new Type("String"), new Type("String")), function1);
        List<Type> function2 = methods.get(new AEIdentifier("function2"));
        assertNotNull(function2);
        assertEquals(1, function2.size());
        assertEquals(List.of(new Type("Bool")), function2);
        Map<AEIdentifier, List<Type>> methodsB = this.methods.getMethods(new Type("B"));
        assertEquals(1, methodsB.size());
        List<Type> function3 = methodsB.get(new AEIdentifier("function3"));
        assertNotNull(function3);
        assertEquals(2, function3.size());
        assertEquals(List.of(new Type("Abc"), new Type("String")), function3);

    }


    @Test
    void collectsObjects() throws CompilerException {
        String programCode = """
                class A {
                    object1: String;
                    object2: Int;
                
        };
        """;
        AEProgram program = Utils.createProgram(programCode);
        program.acceptVisitor(this.collector);
        Map<AEIdentifier, Type> objectsA = this.objects.getObjects(new Type("A"));
        assertEquals(2, objectsA.size());
    }

}
