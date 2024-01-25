package ccompiler.parser;

import java.util.ArrayList;
import java.util.List;

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
import ccompiler.parser.expression.AEType;
import ccompiler.parser.feature.AEFunction;
import ccompiler.parser.feature.AEAttribute;
import ccompiler.parser.feature.AEFeature;

/**
 * 
 */
public class Parser {
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public AEProgram parseInput() throws ParseException {
        return this.parseProgram();

    }

    public AEProgram parseProgram() throws ParseException {
        List<AEClass> classes = new ArrayList<>();
        // TODO: this needs a better conidition
        while (this.lexer.hasMoreTokens()) {
            classes.add(this.parseClass());
            expect(LexerTokenType.SEMI_COLON);
        }
        return new AEProgram(classes);

    }

    private AEClass parseClass() throws ParseException {
        this.expect(Keyword.CLASS);
        LexerToken token = expect(LexerTokenType.TYPE);
        AEType classType = new AEType(token.getValue());
        token = this.lexer.peek();
        AEType inheritType = null;
        if (token.getType() == LexerTokenType.KEYWORD && token.getKeyword() == Keyword.INHERITS) {
            this.lexer.getNextToken();
            token = expect(LexerTokenType.TYPE);
            inheritType = new AEType(token.getValue());
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
        return new AEClass(classType, inheritType, features);

    }

    private AEFeature parseFeature() throws ParseException {
        LexerToken idToken = expect(LexerTokenType.ID);
        LexerToken decisionToken = this.lexer.getNextToken();
        if (decisionToken.getType() == LexerTokenType.COLON) {
            return this.parseAttribute(idToken);
        }
        if (decisionToken.getType() == LexerTokenType.LEFT_PARANTHESIS) {
            return this.parseFunction(idToken);
        }
        throw new ParseException(
                "Missing colon or paranthesis after identifier"
                        + decisionToken.getRow() + " , Column: " + decisionToken.getColumn() + ".");
    }

    private AEFunction parseFunction(LexerToken IDToken) throws ParseException {
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
        return new AEFunction(new AEIdentifier(IDToken.getValue()), formals, new AEType(typeToken.getValue()),
                expression);

    }

    private AEAttribute parseAttribute(LexerToken IDToken) throws ParseException {
        LexerToken typeToken = expect(LexerTokenType.TYPE);
        AEExpression expression = null;
        if (this.lexer.peek().getType() == LexerTokenType.ASSIGNMENT) {
            expect(LexerTokenType.ASSIGNMENT);
            expression = this.parseExpression();
        }
        return new AEAttribute(new AEIdentifier(IDToken.getValue()), new AEType(typeToken.getValue()), expression);

    }

    private AEExpression parseExpression() throws ParseException {
        LexerToken startToken = this.lexer.peek();
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
                    return new AEIfElse(condExpression, thenExpression, elseExpression);
                }
                if (startToken.getKeyword() == Keyword.WHILE) {
                    expect(Keyword.WHILE);
                    AEExpression condExpression = this.parseExpression();
                    expect(Keyword.LOOP);
                    AEExpression bodyExpression = this.parseExpression();
                    expect(Keyword.POOL);
                    // TODO: this is missing a return statement
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
                    return new AENew(new AEType(type.getValue()));
                }
                if (startToken.getKeyword() == Keyword.ISVOID) {
                    this.expect(Keyword.ISVOID);
                    return new AEIsVoid(this.parseExpression());
                }
                if (startToken.getKeyword() == Keyword.NOT) {
                    this.expect(Keyword.NOT);
                    return new AENot(this.parseExpression());
                }
                if (startToken.getKeyword() == Keyword.TRUE) {
                    return new AETrue();
                }
                if (startToken.getKeyword() == Keyword.FALSE) {
                    return new AEFalse();
                }
            }

            case STRING: {
                this.lexer.getNextToken();
                if (!this.canFollowExpr(this.lexer.peek())) {
                    return new AEString(startToken.getValue());
                }
                return this.parseRecursiveExpression(new AEString(startToken.getValue()));
            }
            case INTEGER: {
                this.lexer.getNextToken();
                if (!this.canFollowExpr(this.lexer.peek())) {
                    System.out.println("parsing non recursive integer");
                    return new AEInteger(startToken.getValue());
                }
                System.out.println("parssing recursive integer");
                return this.parseRecursiveExpression(new AEInteger(startToken.getValue()));

            }
            case ID: {
                this.lexer.getNextToken();
                if (!this.canFollowExpr(this.lexer.peek())) {
                    return new AEIdentifier(startToken.getValue());
                }
                return this.parseRecursiveExpression(new AEIdentifier(startToken.getValue()));
            }
            case TILDE: {
                this.lexer.getNextToken();
                return new AEInversion(this.parseExpression());
            }
            case LEFT_PARANTHESIS: {
                this.lexer.getNextToken();
                AEExpression expression = this.parseExpression();
                expect(LexerTokenType.RIGHT_PARANTHESIS);
                return new AEEnclosedExpression(expression);

            }
            case LEFT_CURLY_BRACKET: {
                return this.parseExpressionBlock();
            }
            default:
                                     System.out.println("default path");
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

    private AECase parseCase() throws ParseException {
        expect(Keyword.CASE);
        AEExpression expression = this.parseExpression();
        expect(Keyword.OF);
        List<AECaseBranch> branches = new ArrayList<>();
        branches.add(this.parseCaseBranch());
        while (this.lexer.peek().getType() == LexerTokenType.ID) {
            branches.add(this.parseCaseBranch());
        }
        expect(Keyword.ESAC);
        return new AECase(expression, branches);

    }

    private AECaseBranch parseCaseBranch() throws ParseException {
        LexerToken idToken = expect(LexerTokenType.ID);
        expect(LexerTokenType.COLON);
        LexerToken typeToken = expect(LexerTokenType.TYPE);
        expect(LexerTokenType.RIGHT_ARROW);
        AEExpression expression = this.parseExpression();
        expect(LexerTokenType.SEMI_COLON);
        return new AECaseBranch(new AEIdentifier(idToken.getValue()), new AEType(typeToken.getValue()), expression);

    }

    private AEExpression parseLet() throws ParseException {
        expect(Keyword.LET);
        List<AEAttribute> attributes = new ArrayList<>();
        attributes.add(this.parseAttribute());
        while (this.lexer.peek().getType() == LexerTokenType.COMMA) {
            this.expect(LexerTokenType.COMMA);
            attributes.add(this.parseAttribute());
        }
        expect(Keyword.IN);
        AEExpression expression = this.parseExpression();
        return new AELet(attributes, expression);

    }

    /**
     * Parses ID:TYPE [ <- expr]
     * 
     * @return
     */
    private AEAttribute parseAttribute() throws ParseException {
        LexerToken idToken = expect(LexerTokenType.ID);
        expect(LexerTokenType.COLON);
        LexerToken typeToken = expect(LexerTokenType.TYPE);
        AEExpression expression = null;
        if (this.lexer.peek().getType() == LexerTokenType.ASSIGNMENT) {
            this.expect(LexerTokenType.ASSIGNMENT);
            expression = this.parseExpression();

        }
        return new AEAttribute(new AEIdentifier(idToken.getValue()), new AEType(typeToken.getValue()), expression);

    }

    /*
     * private AEExpression parseExprExpression() throws ParseException {
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
     * @throws ParseException
     */
    private AEExpression parseMethodCall(LexerToken idToken) throws ParseException {
        this.expect(LexerTokenType.LEFT_PARANTHESIS);
        List<AEExpression> expressions = new ArrayList<>();
        if (this.lexer.peek().getType() != LexerTokenType.RIGHT_PARANTHESIS) {
            expressions.add(this.parseExpression());
        }
        while (this.lexer.peek().getType() == LexerTokenType.COMMA) {
            this.expect(LexerTokenType.COMMA);
            expressions.add(this.parseExpression());
        }
        return new AEMethodCall(new AEIdentifier(idToken.getValue()), expressions);

    }

    /**
     * Parses { [[expr;]]+ }
     * 
     * @return
     * @throws ParseException
     */
    private AEExpression parseExpressionBlock() throws ParseException {
        List<AEExpression> expressions = new ArrayList<>();
        expect(LexerTokenType.LEFT_CURLY_BRACKET);
        expressions.add(this.parseExpression());
        expect(LexerTokenType.SEMI_COLON);
        while (this.lexer.peek().getType() != LexerTokenType.RIGHT_CURLY_BRACKET) {
            expressions.add(this.parseExpression());
            expect(LexerTokenType.SEMI_COLON);
        }
        expect(LexerTokenType.RIGHT_CURLY_BRACKET);
        return new AEExpressionBlock(expressions);
    }

    private AEExpression parseRecursiveExpression(AEExpression startExpression) throws ParseException {
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
                System.out.println("parsing plus");
                return new AEPlus(startExpression, this.parseExpression());

            }
            case MINUS: {
                expect(LexerTokenType.MINUS);
                return new AEMinus(startExpression, this.parseExpression());

            }
            case MULTIPLY: {
                expect(LexerTokenType.MULTIPLY);
                return new AEMultiply(startExpression, this.parseExpression());
            }
            case DIVIDE: {
                expect(LexerTokenType.DIVIDE);
                return new AEDivide(startExpression, this.parseExpression());
            }
            case SMALLER: {
                expect(LexerTokenType.SMALLER);
                return new AESmaller(startExpression, this.parseExpression());
            }
            case SMALLER_EQUALS: {
                expect(LexerTokenType.SMALLER_EQUALS);
                return new AESmallerEquals(startExpression, this.parseExpression());

            }
            case EQUALS: {
                expect(LexerTokenType.EQUALS);
                return new AEEquals(startExpression, this.parseExpression());

            }

            default:
                throw new ParseException("Error parsing expression at line  " + decisionToken.getRow() + ", column "
                        + decisionToken.getColumn());
        }

    }

    private AEExpression parseObjectMethodCall(AEExpression startExpression) throws ParseException {
        AEType type = null;
        if (this.lexer.peek().getType() == LexerTokenType.AT) {
            expect(LexerTokenType.AT);
            type = new AEType(expect(LexerTokenType.TYPE).getValue());
        }
        expect(LexerTokenType.DOT);
        AEIdentifier identifier = new AEIdentifier(expect(LexerTokenType.ID).getValue());
        expect(LexerTokenType.LEFT_PARANTHESIS);
        List<AEExpression> expressions = new ArrayList<>();
        expressions.add(this.parseExpression());
        while (this.lexer.peek().getType() == LexerTokenType.COMMA) {
            expect(LexerTokenType.COMMA);
            expressions.add(this.parseExpression());
        }
        expect(LexerTokenType.RIGHT_PARANTHESIS);
        return new AEObjectMethodCall(startExpression, type, identifier, expressions);

    }

    private AEFormal parseFormal() throws ParseException {
        LexerToken idToken = expect(LexerTokenType.ID);
        expect(LexerTokenType.COLON);
        LexerToken typeToken = expect(LexerTokenType.TYPE);
        return new AEFormal(new AEIdentifier(idToken.getValue()), new AEType(typeToken.getValue()));

    }

    private LexerToken expect(LexerTokenType type) throws ParseException {
        LexerToken token = this.lexer.getNextToken();
        if (token.getType() != type) {
            throw new ParseException(
                    "Expected type " + type.toString() + " but got " + token.getType().toString() + ".\nLine: "
                            + token.getRow() + " , Column: " + token.getColumn() + ".");

        }
        return token;

    }

    private LexerToken expect(Keyword keyword) throws ParseException {
        LexerToken token = this.lexer.getNextToken();
        if (token.getType() != LexerTokenType.KEYWORD) {
            throw new ParseException(
                    "Expected keyword " + keyword.toString() + " but got tokenType " + token.getType() + ".\nLine: "
                            + token.getRow() + " , Column: " + token.getColumn() + ".");
        }
        if (token.getType() != LexerTokenType.KEYWORD || token.getKeyword() != keyword) {
            throw new ParseException(
                    "Expected keyword " + keyword.toString() + " but got " + token.getKeyword().toString() + ".\nLine: "
                            + token.getRow() + " , Column: " + token.getColumn() + ".");
        }
        return token;

    }

}
