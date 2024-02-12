package ccompiler.lexer;

import ccompiler.ExceptionHandler;

public class Lexer {
    private String sourceCode;
    private int currentCharIndex;
    private int currentLine;
    private int currentColumn;
    private LexerToken nextToken;

    public Lexer(String sourceCode) {
        this.sourceCode = sourceCode;
        this.currentCharIndex = 0;
        this.currentLine = 1;
        this.currentColumn = 1;
        this.nextToken = null;
        ExceptionHandler.setSourceCode(this.sourceCode);
    }

    public boolean hasMoreTokens() {
        return this.peek() != null;
    }

    public LexerToken peek() {
        if (this.nextToken == null) {
            this.nextToken = this.getNextToken();
        }
        return this.nextToken;

    }

    public String getSourceCode() {
        return this.sourceCode;
    }
    public LexerToken getNextToken() {
        if (this.nextToken != null) {
            LexerToken token = this.nextToken;
            this.nextToken = null;
            return token;
        }
        skipWhiteSpace();
        if (this.currentCharIndex >= this.sourceCode.length()) {
            return null;
        }
        char c = this.sourceCode.charAt(currentCharIndex);
        // parse single char chars
        if (c == '(') {
            LexerToken token = this.createToken(LexerTokenType.LEFT_PARANTHESIS, "");
            this.advancePosition();
            return token;
        }
        if (c == ')') {
            LexerToken token = this.createToken(LexerTokenType.RIGHT_PARANTHESIS, "");
            this.advancePosition();
            return token;
        }
        if (c == ':') {
            this.advancePosition();
            return this.createToken(LexerTokenType.COLON, "");
        }
        if (c == ';') {
            this.advancePosition();
            return this.createToken(LexerTokenType.SEMI_COLON, "");
        }

        if (c == '{') {
            this.advancePosition();
            return this.createToken(LexerTokenType.LEFT_CURLY_BRACKET, "");
        }
        if (c == '}') {
            this.advancePosition();
            return this.createToken(LexerTokenType.RIGHT_CURLY_BRACKET, "");
        }
        if (c == ',') {
            this.advancePosition();
            return this.createToken(LexerTokenType.COMMA, "");
        }
        if (c == '~') {
            this.advancePosition();
            return this.createToken(LexerTokenType.TILDE, "");
        }

        if (c == '+') {
            this.advancePosition();
            return this.createToken(LexerTokenType.PLUS, "");
        }
        if (c == '-') {
            this.advancePosition();
            return this.createToken(LexerTokenType.MINUS, "");
        }
        if (c == '*') {
            this.advancePosition();
            return this.createToken(LexerTokenType.MULTIPLY, "");
        }
        if (c == '/') {
            this.advancePosition();
            return this.createToken(LexerTokenType.DIVIDE, "");
        }
        if (c == '<') {
            this.advancePosition();
            char nextChar = this.sourceCode.charAt(currentCharIndex);
            if (nextChar == '=') {
                this.advancePosition();
                return this.createToken(LexerTokenType.SMALLER_EQUALS, "");
            }
            if (nextChar == '-') {
                this.advancePosition();
                return this.createToken(LexerTokenType.ASSIGNMENT, "");
            }
            return this.createToken(LexerTokenType.SMALLER, "");
        }
        if (c == '=') {
            this.advancePosition();
            if (this.sourceCode.charAt(currentCharIndex) == '>') {
                this.advancePosition();
                return this.createToken(LexerTokenType.RIGHT_ARROW, "");
            }
            return this.createToken(LexerTokenType.EQUALS, "");
        }

        if (c == '"') {
            return this.parseString();
        }

        if (Character.isDigit(c)) {
            return this.parseWithCondition((ch) -> Character.isDigit(ch), LexerTokenType.INTEGER);
        }

        LexerToken token = this.tryToParseKeyWord();
        if (token != null) {
            return token;
        }

        if (Character.isLetter(c) && Character.isUpperCase(c)) {
            return this.parseWithCondition(ch -> Character.isDigit(ch) || Character.isLetter(ch) || ch == '_',
                    LexerTokenType.TYPE);
        }
        if (Character.isLetter(c) && Character.isLowerCase(c)) {
            return this.parseWithCondition(ch -> Character.isDigit(ch) || Character.isLetter(ch) || ch == '_',
                    LexerTokenType.ID);
        }

        return new LexerToken(LexerTokenType.UNKNOWN, "", this.currentLine, this.currentColumn);

    }

    private LexerToken createToken(LexerTokenType type, String value) {

        return new LexerToken(type, value, this.currentLine, this.currentColumn);
    }

    private void skipWhiteSpace() {
        while (this.sourceCode.length() > this.currentCharIndex
                && isWhiteSpace(this.sourceCode.charAt(currentCharIndex))) {
            this.advancePosition();

        }

    }

    private boolean isWhiteSpace(char c) {
        return (c == ' ' || c == '\n' || c == '\f' || c == '\r' || c == '\t');

    }

    private void advancePosition() {
        this.currentColumn += 1;
        if (this.sourceCode.charAt(currentCharIndex) == '\n') {
            this.currentLine += 1;
            this.currentColumn = 1;
        }
        this.currentCharIndex += 1;
    }

    private LexerToken parseString() {
        String value = "";
        int row = this.currentLine;
        int column = this.currentColumn;

        advancePosition();
        while (this.sourceCode.charAt(currentCharIndex) != '"') {
            value += this.sourceCode.charAt(currentCharIndex);
            advancePosition();

        }
        advancePosition();

        return new LexerToken(LexerTokenType.STRING, value, row, column);

    }

    private LexerToken tryToParseKeyWord() {
        String potential = "";
        while (this.currentCharIndex + potential.length() < this.sourceCode.length()) {
            char nextChar = this.sourceCode.charAt(this.currentCharIndex + potential.length());
            if (isWhiteSpace(nextChar) || nextChar == ';') {
                break;
            }
            potential += this.sourceCode.charAt(this.currentCharIndex + potential.length());
        }

        for (Keyword keyword : Keyword.values()) {
            int length = keyword.name().length();
            if (this.currentCharIndex + length > this.sourceCode.length()) {

                continue;
            }
            // String potential = this.sourceCode.substring(this.currentCharIndex,
            // this.currentCharIndex + length);
            if (keyword.matches(potential)) {
                int row = this.currentLine;
                int column = this.currentColumn;

                while (length > 0) {
                    this.advancePosition();
                    length -= 1;
                }
                return new LexerToken(LexerTokenType.KEYWORD, keyword, row, column);
            }

        }
        return null;

    }

    private interface parseCondition {
        boolean canBeParsed(char c);
    }

    private LexerToken parseWithCondition(parseCondition cond, LexerTokenType type) {
        String value = "";
        int row = this.currentLine;
        int column = this.currentColumn;
        boolean matching = true;
        do {
            if (this.currentCharIndex >= this.sourceCode.length()) {
                break;
            }
            char currentChar = this.sourceCode.charAt(currentCharIndex);
            if (!cond.canBeParsed(currentChar)) {
                break;
            }
            value += currentChar;
            this.advancePosition();

        } while (matching);
        return new LexerToken(type, value, row, column);

    }

}
