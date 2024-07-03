package ccompiler;

import ccompiler.parser.expression.AEExpression;
import ccompiler.semanticAnalysis.typechecking.Type;

public final class ExceptionHandler {
    private static String sourceCode;

    public static void setSourceCode(String source) {
        sourceCode = source;
    }

    public static void throwWrongTypeError(AEExpression expression, Type shouldBe) throws CompilerException {
        throwWrongTypeError(expression, shouldBe, expression.getRow(), expression.getColumn());
    }
    public static void throwWrongTypeError(AEExpression expression, Type shouldBe, int row, int column) 
            throws CompilerException {
        String message = "Expression " + expression.toString() + " is of type " + expression.getType().toString()
                + " but should be of type " + shouldBe.toString();
        message += "\n\n"  + createSourceAnnotation(row, column);
        throw new CompilerException(message);
    }

    private static String createSourceAnnotation(int row, int column) {
        if (sourceCode == null) {
            throw new NullPointerException("Sourcecode in ExceptionHandler is not set.");

        }
        String modifiedCode = "";
        int currentRow = 1;
        boolean added = false;
        for (int i = 0; i < sourceCode.length(); i++) {
            char c = sourceCode.charAt(i);
            if (currentRow > row - 2 && currentRow < row + 2) {
                modifiedCode += c;
            };
            if (c == '\n') {
                currentRow += 1;
            }
            if (!added && currentRow == row + 1) {
                for (int j = 0; j < column - 1; j++) {
                    modifiedCode += ' ';
                }
                modifiedCode += '^';
                modifiedCode += '\n';
                added = true;

            }

        }
        return modifiedCode;

    }

}
