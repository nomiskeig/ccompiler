package ccompiler.semanticAnalysis.typechecking;

import org.junit.jupiter.api.Test;

import ccompiler.CompilerException;
import ccompiler.Utils;
import ccompiler.lexer.Lexer;
import ccompiler.parser.AEProgram;
import ccompiler.parser.Parser;
import ccompiler.parser.expression.AEInteger;
import ccompiler.parser.expression.AEPlus;
import ccompiler.parser.expression.AEString;
import ccompiler.semanticAnalysis.MethodAndObjectCollector;
import ccompiler.semanticAnalysis.MethodEnvironment;
import ccompiler.semanticAnalysis.ObjectEnvironment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class TypeCheckerTest {
    private MethodEnvironment methods;
    private ObjectEnvironment objects;
    private MethodAndObjectCollector collector;
    private TypeChecker tc;

    @BeforeEach
    void beforeEach() {
        this.methods = new MethodEnvironment();
        this.objects = new ObjectEnvironment();
        this.collector = new MethodAndObjectCollector(this.methods, this.objects);
        this.tc = new TypeChecker(this.methods, this.objects);
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
        AEProgram program = this.createProgram(programCode);
        program.acceptVisitor(this.collector);
        Exception ex = assertThrows(CompilerException.class, () -> program.acceptVisitor(this.tc));
        System.out.println(ex.getMessage());

    }

    private AEProgram createProgram(String programCode) throws CompilerException {
        Lexer lexer = new Lexer(programCode);
        Parser parser = new Parser(lexer);
        AEProgram program = parser.parseProgram();
        return program;

    }
}
