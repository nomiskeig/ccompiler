package ccompiler.lexer;

public class LexerToken {
    private LexerTokenType type;
    private String value;
    private int row;
    private int column;
    private Keyword keyword;

    public LexerToken(LexerTokenType type, String value, int row, int column) {
        this.type = type;
        this.value = value;
        this.row = row;
        this.column = column;
    }

    public LexerToken(LexerTokenType type, Keyword keyword, int row, int column) {
        this.type = type;
        this.keyword = keyword;
        this.row = row;
        this.column = column;
    }

    public LexerTokenType getType() {
        return type;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public String getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "LexerToken at row " + this.row + ", column " + this.column + " of type " + this.type;
    }

}
