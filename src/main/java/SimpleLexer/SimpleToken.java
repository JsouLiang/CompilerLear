package SimpleLexer;

import Token.*;

public class SimpleToken implements Token {
    TokenType type;
    String text;

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public String getText() {
        return text;
    }
}
