package ccompiler.semanticAnalysis.typechecking;

import org.junit.jupiter.api.Test;

import ccompiler.CompilerException;
import ccompiler.lexer.Lexer;
import ccompiler.parser.AEProgram;
import ccompiler.parser.Parser;
import ccompiler.parser.expression.AEInteger;
import ccompiler.parser.expression.AEPlus;
import ccompiler.parser.expression.AEString;

import static org.junit.jupiter.api.Assertions.*;

class TypeCheckerTest {
    @Test
    void validatesPlus() throws CompilerException {
        AEPlus plus = new AEPlus(new AEInteger("123"), new AEInteger("456"));
        TypeChecker tc = new TypeChecker();
        assertDoesNotThrow(() -> tc.validatePlus(plus));
        AEPlus wrongPlus = new AEPlus(new AEInteger("124"), new AEString("ahdf"));
        assertThrows(CompilerException.class, () -> tc.validatePlus(wrongPlus));
    }

    @Test
    void validatesSimpleClass() throws CompilerException {
        String programCode = """
                    class A {
                        local1: String <- "abc";
                    };
                """;
        Lexer lexer = new Lexer(programCode);
        Parser parser = new Parser(lexer);
        AEProgram program = parser.parseProgram();
        TypeChecker tc = new TypeChecker();
        tc.validateProgram(program);
    }

    @Test
    void checksWrongTypesInAttribute() throws CompilerException {
        String programCode = """
                    class A {
                        local1: Int;
                        local1: String <- "abc";
                    };
                """;
        AEProgram program = this.createProgram(programCode);
        TypeChecker tc = new TypeChecker();
        assertThrows(CompilerException.class, (() -> tc.validateProgram(program)));
    }

    @Test
    void checksWrongFunctionReturnType() throws CompilerException {
        String programCode = """
                    class A {
                        function(x: Int) : Int {
                            true
                        };
                    };
        """;
        AEProgram program = this.createProgram(programCode);
        TypeChecker tc = new TypeChecker();
        Exception ex = assertThrows(CompilerException.class, () -> tc.validateProgram(program));
        System.out.println(ex.getMessage());

        

    } 


    private AEProgram createProgram(String programCode) throws CompilerException {
        Lexer lexer = new Lexer(programCode);
        Parser parser = new Parser(lexer);
        AEProgram program = parser.parseProgram();
        return program;

    }
}
