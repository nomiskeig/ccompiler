package ccompiler.semanticAnalysis.typechecking;

import org.junit.jupiter.api.Test;

import ccompiler.CompilerException;
import ccompiler.Utils;
import ccompiler.lexer.Lexer;
import ccompiler.parser.AEProgram;
import ccompiler.parser.Parser;
import ccompiler.semanticAnalysis.MethodAndObjectCollector;
import ccompiler.semanticAnalysis.MethodEnvironment;
import ccompiler.semanticAnalysis.ObjectEnvironment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class TypeCheckerTest {
    private MethodEnvironment methods;
    private ObjectEnvironment objects;
    private MethodAndObjectCollector collector;
    private TypeHierarchy hierarchy;
    private TypeChecker tc;

    @BeforeEach
    void beforeEach() {
        this.methods = new MethodEnvironment();
        this.objects = new ObjectEnvironment();
        this.hierarchy = new TypeHierarchy();
        this.collector = new MethodAndObjectCollector(this.methods, this.objects, this.hierarchy);
        this.tc = new TypeChecker(this.methods, this.objects, this.hierarchy);
    }

    @Test
    void validatesSimpleClass() throws CompilerException {
        String programCode = """
                    class A {
                        local1: String <- "abc";
                    };
                """;
        AEProgram program = Utils.createProgram(programCode);
        program.acceptVisitor(this.collector);
        program.acceptVisitor(this.tc);
    }

    @Test
    void checksWrongTypesInAttribute() throws CompilerException {
        String programCode = """
                    class A {
                        local1: Int;
                        local2: String <- 5;
                    };
                """;
        AEProgram program = Utils.createProgram(programCode);
        program.acceptVisitor(this.collector);
        CompilerException ex = assertThrows(CompilerException.class, () -> program.acceptVisitor(this.tc));
        System.out.println(ex.getMessage());
    }

    @Test
    void checksWrongFunctionReturnType() throws CompilerException {
        String programCode = """
                            class A {
                                function(x: Int) : Int {
                                    "asdf"
                                };
                            };
                """;
        AEProgram program = Utils.createProgram(programCode);
        program.acceptVisitor(this.collector);
        Exception ex = assertThrows(CompilerException.class, () -> program.acceptVisitor(this.tc));
        System.out.println(ex.getMessage());

    }

    @Test
    void validatesExpressions() throws CompilerException {
        String programCode = """
                    class A {
                       function(x: Int) : Int  {
                            {
                                x <- 4;
                                if x < 4 then x <- 5 else x <- 3 fi;
                            }
                        };
                    };

                """;
        AEProgram program = Utils.createProgram(programCode);
        program.acceptVisitor(this.collector);
        assertDoesNotThrow(() -> program.acceptVisitor(this.tc));
    }

    @Test
    void validatesInheritanceIf() throws CompilerException {
        String programCode = """
                    class A {
                };
                class B inherits A {

                };
                class C inherits A {

                };
                class D {
                    a: A;
                    b: B;
                    c: C;
                    function1() : A {
                        if 1 < 2 then b else c fi
                };
                    function2() : A {
                        {
                            a <- new B;
                            b;

        }
        };

                };


                """;
        AEProgram program = Utils.createProgram(programCode);
        program.acceptVisitor(this.collector);
        assertDoesNotThrow(() -> program.acceptVisitor(this.tc));

    }


    @Test
    void validatesWhile() throws CompilerException {
        String programCode = """
            class A {
               function() : Object {
                    while 1 < 2 loop 5 pool
        };
                
            };
            """;
        AEProgram program = Utils.createProgram(programCode);
        program.acceptVisitor(this.collector);
        assertDoesNotThrow(() -> program.acceptVisitor(this.tc));

    }

}
