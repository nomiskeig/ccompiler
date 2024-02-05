package ccompiler.parser.typechecker;

import org.junit.jupiter.api.Test;

import ccompiler.lexer.Lexer;
import ccompiler.parser.AEProgram;
import ccompiler.parser.ParseException;
import ccompiler.parser.Parser;
import ccompiler.parser.expression.AEInteger;
import ccompiler.parser.expression.AEPlus;
import ccompiler.parser.expression.AEString;

import static org.junit.jupiter.api.Assertions.*;

class TypeCheckerTest {
    @Test
    void validatesPlus() throws TypeException {
        AEPlus plus = new AEPlus(new AEInteger("123"), new AEInteger("456"));
        TypeChecker tc = new TypeChecker();
        assertDoesNotThrow(() -> tc.validatePlus(plus));
        AEPlus wrongPlus = new AEPlus(new AEInteger("124"), new AEString("ahdf"));
        assertThrows(TypeException.class, () -> tc.validatePlus(wrongPlus));
    }

    @Test
    void validatesSimpleClass() throws TypeException, ParseException {
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
    void checksWrongTypesInAttribute() throws TypeException, ParseException {
        String programCode = """
                    class A {
                        local1: Int;
                        local1: String <- "abc";
                    };
                """;
        AEProgram program = this.createProgram(programCode);
        TypeChecker tc = new TypeChecker();
        assertThrows(TypeException.class, (() -> tc.validateProgram(program)));
    }

    private AEProgram createProgram(String programCode) throws ParseException {
        Lexer lexer = new Lexer(programCode);
        Parser parser = new Parser(lexer);
        AEProgram program = parser.parseProgram();
        return program;

    }
}
