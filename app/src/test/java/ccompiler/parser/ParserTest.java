package ccompiler.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ccompiler.lexer.Lexer;

import java.io.File;

class ParserTest {
    @Test
    void parsesProgram() throws ParseException {
        String programCode = "class Int inherits Object {};class Other {};";
        File file = new File("/home/simon/dev/ccompiler/graphs/graph.dot");
        this.printProgram(programCode, file);

    }

    @Test
    public void parsesBiggerProgramTest() throws ParseException {
        String programCode = """
                    class A {
                        local1: String <- 456;
                        fun1(arg1:Type1, arg2: Type2): String {
                            123
                        };
                        fun2(): Type2 {
                            if 12123 then "ABC" else (new Type3) fi
                        };
                    };
                """;

        File file = new File("/home/simon/dev/ccompiler/graphs/biggraph.dot");
        this.printProgram(programCode, file);
    }

    @Test
    void parsesMath() throws ParseException {
        String programCode = """
                class Test {
                    fun(): Integer {
                        123 + ((44/2) * 48)
                    };
                };
                """;

        File file = new File("/home/simon/dev/ccompiler/graphs/mathgraph.dot");
        this.printProgram(programCode, file);

    }

    @Test
    void parsesPlus() throws ParseException {
        String programCode = """
                class Test {
                    fun(): Integer {
                        123 + 456
                };
                };
                """;
        File file = new File("/home/simon/dev/ccompiler/graphs/plusgraph.dot");
        this.printProgram(programCode, file);
    }

    @Test
    void parsesLargerProgram() throws ParseException {
        String programCode = """
                    class Test1 {
                        fun1(): Type1 {

                        {
                            while (5 = 3) loop 5 pool;
                            let abc: Type2 <- 5, def: Type57 <- 5+3 in isvoid "abc";
                        }
                        };

                };
                  """;
        File file = new File("/home/simon/dev/ccompiler/graphs/largeProgram.dot");
        this.printProgram(programCode, file);
    }

    private void printProgram(String programCode, File file) throws ParseException {
        Lexer lexer = new Lexer(programCode);
        Parser parser = new Parser(lexer);
        AEProgram program = parser.parseProgram();
        ASTPrinter printer = new ASTPrinter();
        program.acceptVisitor(printer);
        printer.exportToDOT(file);
        //System.out.println("Reduced source:\n" + reduceProgram(programCode));
        //System.out.println("Reduced compiled:\n" + reduceProgram(program.toString()));
        

        assertTrue(reduceProgram(programCode).equals(reduceProgram(program.toString())));

    }

    private String reduceProgram(String program) {
        String res = "";
        for (int i = 0; i < program.length(); i++) {
            char c = program.charAt(i);
            if (c != '\n' && c != ' ' && c != ';') {
                res += c;
            }
        }
        return res;

    }

}
