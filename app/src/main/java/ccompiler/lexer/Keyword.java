package ccompiler.lexer;


public enum Keyword {
    CLASS(matchesCaseInsensitive("class")),
    ELSE(matchesCaseInsensitive("else")),
    FALSE(matchesFirstLower("false")),
    FI(matchesCaseInsensitive("fi")),
    IF(matchesCaseInsensitive("if")),
    IN(matchesCaseInsensitive("in")),
    INHERITS(matchesCaseInsensitive("inherits")),
    ISVOID(matchesCaseInsensitive("isvoid")),
    LET(matchesCaseInsensitive("let")),
    LOOP(matchesCaseInsensitive("loop")),
    POOL(matchesCaseInsensitive("pool")),
    THEN(matchesCaseInsensitive("then")),
    WHILE(matchesCaseInsensitive("while")),
    CASE(matchesCaseInsensitive("case")),
    ESAC(matchesCaseInsensitive("esac")),
    NEW(matchesCaseInsensitive("new")),
    OF(matchesCaseInsensitive("of")),
    NOT(matchesCaseInsensitive("not")),
    TRUE(matchesFirstLower("true"));

    private final MatchFunction matchFunction;

    static private MatchFunction matchesCaseInsensitive(String compare) {
        return (s) -> s.toLowerCase().equals(compare);

    }

    static private MatchFunction matchesFirstLower(String compare) {
        return (s) -> {
            if (!Character.isLowerCase(s.charAt(0))) {
                return false;
            }
            return  s.toLowerCase().equals(compare);
        };

    }

    private Keyword(MatchFunction matches) {
        this.matchFunction = matches;

    }
    public boolean matches(String s) {
        return this.matchFunction.matches(s);
    }

    private interface MatchFunction {
        public boolean matches(String s);

    }
}
