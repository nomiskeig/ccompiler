package ccompiler.lexer;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;

class LexerTest {
    @Test  void parsesInteger() {
        Lexer lexer = new Lexer("123");
        LexerToken token = lexer.getNextToken();
        assertEquals(LexerTokenType.INTEGER, token.getType());
        assertEquals("123", token.getValue());
    }

    @Test void parsesType() {
        Lexer lexer =new Lexer("Type");
        LexerToken token = lexer.getNextToken();
        assertEquals(LexerTokenType.TYPE, token.getType());
        assertEquals("Type", token.getValue());

    }
    @Test void parsesIntTypeTest() {
        Lexer lexer = new Lexer("Int");
        LexerToken token = lexer.getNextToken();
        assertEquals(LexerTokenType.TYPE, token.getType());
        assertEquals("Int", token.getValue());
    }
    @Test void parsesObject() {
        Lexer lexer =new Lexer("type");
        LexerToken token = lexer.getNextToken();
        assertEquals(LexerTokenType.ID, token.getType());
        assertEquals("type", token.getValue());

    }
    @Test void parsesParantheses() {
        Lexer lexer = new Lexer("( )");
        LexerToken token1 = lexer.getNextToken();
        assertEquals(LexerTokenType.LEFT_PARANTHESIS, token1.getType());
        assertEquals(1, token1.getRow());
        assertEquals(1, token1.getColumn());
        LexerToken token2 = lexer.getNextToken();
        assertEquals(LexerTokenType.RIGHT_PARANTHESIS, token2.getType());
        assertEquals(1, token2.getRow());
        assertEquals(3, token2.getColumn());
    }

    @Test void parsesString() {
        Lexer lexer = new Lexer("\"teststring\"");
        LexerToken token = lexer.getNextToken();
        assertEquals(LexerTokenType.STRING, token.getType());
        assertEquals(1, token.getRow());
        assertEquals(1, token.getColumn());

    }
    @Test void parsesClassAndElse() {
        Lexer lexer = new Lexer("class else");
        LexerToken token1 = lexer.getNextToken();
        assertEquals(LexerTokenType.KEYWORD, token1.getType());
        assertEquals(Keyword.CLASS, token1.getKeyword());
        assertEquals(1, token1.getRow());
        assertEquals(1, token1.getColumn());
        LexerToken token2 = lexer.getNextToken();
        assertEquals(LexerTokenType.KEYWORD, token2.getType());
        assertEquals(Keyword.ELSE, token2.getKeyword());
        assertEquals(1, token2.getRow());
        assertEquals(7, token2.getColumn());
    }

    @Test  void parsesTrueAndFalse() {
        Lexer lexer = new Lexer("true False");
        LexerToken token1 = lexer.getNextToken();
        assertEquals(LexerTokenType.KEYWORD, token1.getType());
        assertEquals(Keyword.TRUE, token1.getKeyword());
        assertEquals(1, token1.getRow());
        assertEquals(1, token1.getColumn());
        LexerToken token2 = lexer.getNextToken();
        assertEquals(LexerTokenType.TYPE, token2.getType());
        assertEquals(1, token2.getRow());
        assertEquals(6, token2.getColumn());

    }

}
