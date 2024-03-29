package ccompiler.parser;

import java.util.ArrayList;
import java.util.List;

import ccompiler.CompilerException;
import ccompiler.lexer.Keyword;
import ccompiler.lexer.Lexer;
import ccompiler.lexer.LexerToken;
import ccompiler.lexer.LexerTokenType;
import ccompiler.parser.expression.AEAssignment;
import ccompiler.parser.expression.AECase;
import ccompiler.parser.expression.AECaseBranch;
import ccompiler.parser.expression.AEDivide;
import ccompiler.parser.expression.AEEnclosedExpression;
import ccompiler.parser.expression.AEEquals;
import ccompiler.parser.expression.AEExpression;
import ccompiler.parser.expression.AEExpressionBlock;
import ccompiler.parser.expression.AEFalse;
import ccompiler.parser.expression.AEIdentifier;
import ccompiler.parser.expression.AEIfElse;
import ccompiler.parser.expression.AEInteger;
import ccompiler.parser.expression.AEInversion;
import ccompiler.parser.expression.AEIsVoid;
import ccompiler.parser.expression.AELet;
import ccompiler.parser.expression.AEMethodCall;
import ccompiler.parser.expression.AEMinus;
import ccompiler.parser.expression.AEMultiply;
import ccompiler.parser.expression.AENew;
import ccompiler.parser.expression.AENot;
import ccompiler.parser.expression.AEObjectMethodCall;
import ccompiler.parser.expression.AEPlus;
import ccompiler.parser.expression.AESmaller;
import ccompiler.parser.expression.AESmallerEquals;
import ccompiler.parser.expression.AEString;
import ccompiler.parser.expression.AETrue;
import ccompiler.parser.expression.AEWhile;
import ccompiler.parser.feature.AEFunction;
import ccompiler.semanticAnalysis.typechecking.Type;
import ccompiler.parser.feature.AEAttribute;
import ccompiler.parser.feature.AEFeature;

/**
 * asdfsdjfk
 */
public class Parser {
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;

    }

    public AEProgram parseInput() throws CompilerException {
        return this.parseProgram();

    }

    public AEProgram parseProgram() throws CompilerException {
        List<AEClass> classes = new ArrayList<>();
        while (this.lexer.hasMoreTokens()) {
            classes.add(this.parseClass());
            expect(LexerTokenType.SEMI_COLON);
        }
        return new AEProgram(classes, 0,0);

    }

    private AEClass parseClass() throws CompilerException {
        this.expect(Keyword.CLASS);
        LexerToken token = expect(LexerTokenType.TYPE);
        Type classType = new Type(token.getValue());
        token = this.lexer.peek();
        Type inheritType = null;
        if (token.getType() == LexerTokenType.KEYWORD && token.getKeyword() == Keyword.INHERITS) {
            this.lexer.getNextToken();
            token = expect(LexerTokenType.TYPE);
            inheritType = new Type(token.getValue());
        }
        expect(LexerTokenType.LEFT_CURLY_BRACKET);
        List<AEFeature> features = new ArrayList<>();
        while (this.lexer.peek().getType() != LexerTokenType.RIGHT_CURLY_BRACKET) {
            // parse the feature
            AEFeature feature = this.parseFeature();
            features.add(feature);
            expect(LexerTokenType.SEMI_COLON);
        }
        expect(LexerTokenType.RIGHT_CURLY_BRACKET);
        return new AEClass(classType, inheritType, features, token.getRow(), token.getColumn());

    }

    private AEFeature parseFeature() throws CompilerException {
        LexerToken idToken = expect(LexerTokenType.ID);
        LexerToken decisionToken = this.lexer.getNextToken();
        if (decisionToken.getType() == LexerTokenType.COLON) {
            return this.parseAttribute(idToken);
        }
        if (decisionToken.getType() == LexerTokenType.LEFT_PARANTHESIS) {
            return this.parseFunction(idToken);
        }
        throw new CompilerException(
                "Missing colon or paranthesis after identifier"
                        + decisionToken.getRow() + " , Column: " + decisionToken.getColumn() + ".");
    }

    private AEFunction parseFunction(LexerToken IDToken) throws CompilerException {
        List<AEFormal> formals = new ArrayList<>();
        if (this.lexer.peek().getType() != LexerTokenType.RIGHT_PARANTHESIS) {
            formals.add(parseFormal());

        }
        while (this.lexer.peek().getType() == LexerTokenType.COMMA) {
            expect(LexerTokenType.COMMA);
            formals.add(parseFormal());
        }
        expect(LexerTokenType.RIGHT_PARANTHESIS);
        expect(LexerTokenType.COLON);
        LexerToken typeToken = expect(LexerTokenType.TYPE);
        expect(LexerTokenType.LEFT_CURLY_BRACKET);
        AEExpression expression = this.parseExpression();
        expect(LexerTokenType.RIGHT_CURLY_BRACKET);
        return new AEFunction(new AEIdentifier(IDToken.getValue(), IDToken.getRow(), IDToken.getColumn()), formals,
                new Type(typeToken.getValue()),
                expression, IDToken.getRow(), IDToken.getColumn());

    }

    private AEAttribute parseAttribute(LexerToken IDToken) throws CompilerException {
        LexerToken typeToken = expect(LexerTokenType.TYPE);
        AEExpression expression = null;
        if (this.lexer.peek().getType() == LexerTokenType.ASSIGNMENT) {
            expect(LexerTokenType.ASSIGNMENT);
            expression = this.parseExpression();
        }
        return new AEAttribute(new AEIdentifier(IDToken.getValue(), IDToken.getRow(), IDToken.getColumn()),
                new Type(typeToken.getValue()), expression,
                IDToken.getRow(), IDToken.getColumn());

    }

    private AEExpression parseExpression() throws CompilerException {
        LexerToken startToken = this.lexer.peek();
        if (startToken.getType() == LexerTokenType.TYPE) {
            throw new CompilerException(startToken.getValue() + " can not be an expression");
        }
        switch (startToken.getType()) {
            case KEYWORD: {
                if (startToken.getKeyword() == Keyword.IF) {
                    expect(Keyword.IF);
                    AEExpression condExpression = this.parseExpression();
                    expect(Keyword.THEN);
                    AEExpression thenExpression = this.parseExpression();
                    expect(Keyword.ELSE);
                    AEExpression elseExpression = this.parseExpression();
                    expect(Keyword.FI);
                    return new AEIfElse(condExpression, thenExpression, elseExpression, startToken.getRow(),
                            startToken.getRow());
                }
                if (startToken.getKeyword() == Keyword.WHILE) {
                    expect(Keyword.WHILE);
                    AEExpression condExpression = this.parseExpression();
                    expect(Keyword.LOOP);
                    AEExpression bodyExpression = this.parseExpression();
                    expect(Keyword.POOL);
                    return new AEWhile(condExpression, bodyExpression, startToken.getRow(), startToken.getColumn());
                }
                if (startToken.getKeyword() == Keyword.LET) {
                    return this.parseLet();

                }
                if (startToken.getKeyword() == Keyword.CASE) {
                    return this.parseCase();
                }
                if (startToken.getKeyword() == Keyword.NEW) {
                    this.expect(Keyword.NEW);
                    LexerToken type = expect(LexerTokenType.TYPE);
                    return new AENew(new Type(type.getValue()), startToken.getRow(), startToken.getColumn());
                }
                if (startToken.getKeyword() == Keyword.ISVOID) {
                    this.expect(Keyword.ISVOID);
                    return new AEIsVoid(this.parseExpression(), startToken.getRow(), startToken.getColumn());
                }
                if (startToken.getKeyword() == Keyword.NOT) {
                    this.expect(Keyword.NOT);
                    return new AENot(this.parseExpression(), startToken.getRow(), startToken.getColumn());
                }
                if (startToken.getKeyword() == Keyword.TRUE) {
                    this.expect(Keyword.TRUE);
                    return new AETrue(startToken.getRow(), startToken.getColumn());
                }
                if (startToken.getKeyword() == Keyword.FALSE) {
                    this.expect(Keyword.FALSE);
                    return new AEFalse(startToken.getRow(), startToken.getColumn());
                }
            }

            case STRING: {
                this.lexer.getNextToken();
                if (!this.canFollowExpr(this.lexer.peek())) {
                    return new AEString(startToken.getValue(), startToken.getRow(), startToken.getColumn());
                }
                return this.parseRecursiveExpression(
                        new AEString(startToken.getValue(), startToken.getRow(), startToken.getColumn()));
            }
            case INTEGER: {
                this.lexer.getNextToken();
                if (!this.canFollowExpr(this.lexer.peek())) {
                    return new AEInteger(startToken.getValue(), startToken.getRow(), startToken.getColumn());
                }
                return this.parseRecursiveExpression(
                        new AEInteger(startToken.getValue(), startToken.getRow(), startToken.getColumn()));

            }
            case ID: {
                this.lexer.getNextToken();
                if (!this.canFollowExpr(this.lexer.peek())) {
                    return new AEIdentifier(startToken.getValue(), startToken.getRow(), startToken.getColumn());
                }
                return this.parseRecursiveExpression(
                        new AEIdentifier(startToken.getValue(), startToken.getRow(), startToken.getColumn()));
            }
            case TILDE: {
                this.lexer.getNextToken();
                return new AEInversion(this.parseExpression(), startToken.getRow(), startToken.getColumn());
            }
            case LEFT_PARANTHESIS: {
                this.lexer.getNextToken();
                AEExpression expression = this.parseExpression();
                if (this.lexer.peek().getType() != LexerTokenType.RIGHT_PARANTHESIS) {
                    expression = this.parseRecursiveExpression(expression);

                }
                expect(LexerTokenType.RIGHT_PARANTHESIS);
                return new AEEnclosedExpression(expression, startToken.getRow(), startToken.getColumn());

            }
            case LEFT_CURLY_BRACKET: {
                return this.parseExpressionBlock();
            }
            default:
                return this.parseRecursiveExpression(null);

        }

    }

    private boolean canFollowExpr(LexerToken peek) {
        return peek.getType() == LexerTokenType.PLUS || peek.getType() == LexerTokenType.MINUS
                || peek.getType() == LexerTokenType.DIVIDE || peek.getType() == LexerTokenType.MULTIPLY
                || peek.getType() == LexerTokenType.SMALLER || peek.getType() == LexerTokenType.SMALLER_EQUALS
                || peek.getType() == LexerTokenType.EQUALS || peek.getType() == LexerTokenType.AT
                || peek.getType() == LexerTokenType.ASSIGNMENT || peek.getType() == LexerTokenType.LEFT_PARANTHESIS;
    }

    private AECase parseCase() throws CompilerException {
        LexerToken caseToken = expect(Keyword.CASE);
        AEExpression expression = this.parseExpression();
        expect(Keyword.OF);
        List<AECaseBranch> branches = new ArrayList<>();
        branches.add(this.parseCaseBranch());
        while (this.lexer.peek().getType() == LexerTokenType.ID) {
            branches.add(this.parseCaseBranch());
        }
        expect(Keyword.ESAC);
        return new AECase(expression, branches, caseToken.getRow(), caseToken.getColumn());

    }

    private AECaseBranch parseCaseBranch() throws CompilerException {
        LexerToken idToken = expect(LexerTokenType.ID);
        expect(LexerTokenType.COLON);
        LexerToken typeToken = expect(LexerTokenType.TYPE);
        expect(LexerTokenType.RIGHT_ARROW);
        AEExpression expression = this.parseExpression();
        expect(LexerTokenType.SEMI_COLON);
        return new AECaseBranch(new AEIdentifier(idToken.getValue(), idToken.getRow(), idToken.getColumn()),
                new Type(typeToken.getValue()), expression, idToken.getRow(), idToken.getColumn());

    }

    private AEExpression parseLet() throws CompilerException {
        LexerToken expectToken = expect(Keyword.LET);
        List<AEAttribute> attributes = new ArrayList<>();
        attributes.add(this.parseAttribute());
        while (this.lexer.peek().getType() == LexerTokenType.COMMA) {
            this.expect(LexerTokenType.COMMA);
            attributes.add(this.parseAttribute());
        }
        expect(Keyword.IN);
        AEExpression expression = this.parseExpression();
        return new AELet(attributes, expression, expectToken.getRow(), expectToken.getColumn());

    }

    /**
     * Parses ID:TYPE [ <- expr]
     * 
     * @return
     */
    private AEAttribute parseAttribute() throws CompilerException {
        LexerToken idToken = expect(LexerTokenType.ID);
        expect(LexerTokenType.COLON);
        LexerToken typeToken = expect(LexerTokenType.TYPE);
        AEExpression expression = null;
        if (this.lexer.peek().getType() == LexerTokenType.ASSIGNMENT) {
            this.expect(LexerTokenType.ASSIGNMENT);
            expression = this.parseExpression();

        }
        return new AEAttribute(new AEIdentifier(idToken.getValue(), idToken.getRow(), idToken.getColumn()),
                new Type(typeToken.getValue()), expression, idToken.getRow(), idToken.getColumn());

    }

    /*
     * private AEExpression parseExprExpression() throws CompilerException {
     * LexerToken IDToken = expect(LexerTokenType.ID);
     * LexerToken decisionToken = this.lexer.peek();
     * switch (decisionToken.getType()) {
     * case LEFT_PARANTHESIS:
     * return this.parseMethodCall(IDToken);
     * case ASSIGNMENT:
     * this.expect(LexerTokenType.ASSIGNMENT);
     * AEExpression expression = this.parseExpression();
     * return new AEAssignment(new AEIdentifier(IDToken.getValue()), expression);
     * default:
     * this.lexer.getNextToken();
     * return new AEIdentifier(IDToken.getValue());
     * }
     * 
     * }
     */

    /**
     * Parses ID([expr [[, expr]]*])
     * 
     * @param idToken
     * @return
     * @throws CompilerException
     */
    private AEExpression parseMethodCall(LexerToken idToken) throws CompilerException {
        this.expect(LexerTokenType.LEFT_PARANTHESIS);
        List<AEExpression> expressions = new ArrayList<>();
        if (this.lexer.peek().getType() != LexerTokenType.RIGHT_PARANTHESIS) {
            expressions.add(this.parseExpression());
        }
        while (this.lexer.peek().getType() == LexerTokenType.COMMA) {
            this.expect(LexerTokenType.COMMA);
            expressions.add(this.parseExpression());
        }
        return new AEMethodCall(new AEIdentifier(idToken.getValue(), idToken.getRow(), idToken.getColumn()),
                expressions, idToken.getRow(), idToken.getColumn());

    }

    /**
     * Parses { [[expr;]]+ }
     * 
     * @return
     * @throws CompilerException
     */
    private AEExpression parseExpressionBlock() throws CompilerException {
        List<AEExpression> expressions = new ArrayList<>();
        LexerToken expressionBlockToken = expect(LexerTokenType.LEFT_CURLY_BRACKET);
        expressions.add(this.parseExpression());
        expect(LexerTokenType.SEMI_COLON);
        while (this.lexer.peek().getType() != LexerTokenType.RIGHT_CURLY_BRACKET) {
            expressions.add(this.parseExpression());
            expect(LexerTokenType.SEMI_COLON);
        }
        expect(LexerTokenType.RIGHT_CURLY_BRACKET);
        return new AEExpressionBlock(expressions, expressionBlockToken.getRow(), expressionBlockToken.getColumn());
    }

    private AEExpression parseRecursiveExpression(AEExpression startExpression) throws CompilerException {
        if (startExpression == null) {
            startExpression = this.parseExpression();
        }
        LexerToken decisionToken = this.lexer.peek();
        switch (decisionToken.getType()) {
            case AT:
            case DOT: {
                return this.parseObjectMethodCall(startExpression);
            }
            case PLUS: {
                expect(LexerTokenType.PLUS);
                return new AEPlus(startExpression, this.parseExpression(), decisionToken.getRow(),
                        decisionToken.getColumn());

            }
            case MINUS: {
                expect(LexerTokenType.MINUS);
                return new AEMinus(startExpression, this.parseExpression(), decisionToken.getRow(),
                        decisionToken.getColumn());

            }
            case MULTIPLY: {
                expect(LexerTokenType.MULTIPLY);
                return new AEMultiply(startExpression, this.parseExpression(), decisionToken.getRow(),
                        decisionToken.getColumn());
            }
            case DIVIDE: {
                expect(LexerTokenType.DIVIDE);
                return new AEDivide(startExpression, this.parseExpression(), decisionToken.getRow(),
                        decisionToken.getColumn());
            }
            case SMALLER: {
                expect(LexerTokenType.SMALLER);
                return new AESmaller(startExpression, this.parseExpression(), decisionToken.getRow(),
                        decisionToken.getColumn());
            }
            case SMALLER_EQUALS: {
                expect(LexerTokenType.SMALLER_EQUALS);
                return new AESmallerEquals(startExpression, this.parseExpression(), decisionToken.getRow(),
                        decisionToken.getColumn());

            }
            case EQUALS: {
                expect(LexerTokenType.EQUALS);
                return new AEEquals(startExpression, this.parseExpression(), decisionToken.getRow(),
                        decisionToken.getColumn());

            }
            case ASSIGNMENT: {
                expect(LexerTokenType.ASSIGNMENT);
                return new AEAssignment((AEIdentifier) startExpression, this.parseExpression(), decisionToken.getRow(),
                        decisionToken.getColumn());
            }
            // TODO: Function calls are not parsed correctly

            default:

                this.printBetterMessage(this.lexer.getSourceCode(), decisionToken.getRow(), decisionToken.getColumn());
                throw new CompilerException("Error parsing expression at line  " + decisionToken.getRow() + ", column "
                        + decisionToken.getColumn() + ", got " + decisionToken.getType());
        }

    }

    private AEExpression parseObjectMethodCall(AEExpression startExpression) throws CompilerException {
        Type type = null;
        if (this.lexer.peek().getType() == LexerTokenType.AT) {
            expect(LexerTokenType.AT);
            type = new Type(expect(LexerTokenType.TYPE).getValue());
        }
        expect(LexerTokenType.DOT);
        LexerToken identiferToken = expect(LexerTokenType.ID);
        AEIdentifier identifier = new AEIdentifier(identiferToken.getValue(), identiferToken.getRow(),
                identiferToken.getColumn());
        expect(LexerTokenType.LEFT_PARANTHESIS);
        List<AEExpression> expressions = new ArrayList<>();
        expressions.add(this.parseExpression());
        while (this.lexer.peek().getType() == LexerTokenType.COMMA) {
            expect(LexerTokenType.COMMA);
            expressions.add(this.parseExpression());
        }
        expect(LexerTokenType.RIGHT_PARANTHESIS);
        return new AEObjectMethodCall(startExpression, type, identifier, expressions, identiferToken.getRow(),
                identiferToken.getColumn());

    }

    private AEFormal parseFormal() throws CompilerException {
        LexerToken idToken = expect(LexerTokenType.ID);
        expect(LexerTokenType.COLON);
        LexerToken typeToken = expect(LexerTokenType.TYPE);
        return new AEFormal(new AEIdentifier(idToken.getValue(), idToken.getRow(), idToken.getColumn()),
                new Type(typeToken.getValue()), idToken.getRow(), idToken.getColumn());

    }

    private LexerToken expect(LexerTokenType type) throws CompilerException {
        LexerToken token = this.lexer.getNextToken();
        if (token.getType() != type) {
            printBetterMessage(this.lexer.getSourceCode(), token.getRow(), token.getColumn());
            throw new CompilerException(
                    "Expected type " + type.toString() + " but got " + token.getType().toString() + ".\nLine: "
                            + token.getRow() + " , Column: " + token.getColumn() + ".");

        }
        return token;

    }

    private LexerToken expect(Keyword keyword) throws CompilerException {
        LexerToken token = this.lexer.getNextToken();
        if (token.getType() != LexerTokenType.KEYWORD) {
            printBetterMessage(this.lexer.getSourceCode(), token.getRow(), token.getColumn());
            throw new CompilerException(
                    "Expected keyword " + keyword.toString() + " but got tokenType " + token.getType() + ".\nLine: "
                            + token.getRow() + " , Column: " + token.getColumn() + ".");
        }
        if (token.getType() != LexerTokenType.KEYWORD || token.getKeyword() != keyword) {
            printBetterMessage(this.lexer.getSourceCode(), token.getRow(), token.getColumn());
            throw new CompilerException(
                    "Expected keyword " + keyword.toString() + " but got " + token.getKeyword().toString() + ".\nLine: "
                            + token.getRow() + " , Column: " + token.getColumn() + ".");
        }
        return token;

    }

    private void printBetterMessage(String code, int row, int column) {
        String modifiedCode = "";
        int currentRow = 1;
        boolean added = false;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            modifiedCode += c;
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
        System.out.println(modifiedCode);

    }

}
