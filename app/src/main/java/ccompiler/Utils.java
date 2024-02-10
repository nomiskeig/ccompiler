package ccompiler;

import ccompiler.lexer.Lexer;
import ccompiler.parser.AEProgram;
import ccompiler.parser.Parser;

public final class Utils {

    public static AEProgram createProgram(String programCode) throws CompilerException {
        Lexer lexer = new Lexer(programCode);
        Parser parser = new Parser(lexer);
        AEProgram program = parser.parseProgram();
        return program;

    }
}
