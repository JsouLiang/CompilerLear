package SimpleLexer;

import Token.Token;
import Token.TokenReader;

import java.util.List;

public class SimpleTokenReader implements TokenReader {
    private List<Token> tokens;

    private int pos;

    public SimpleTokenReader(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    @Override
    public Token read() {
        if (pos < tokens.size()) {
            return tokens.get(pos++);
        }
        return null;
    }

    @Override
    public Token peek() {
        if (pos < tokens.size()) {
            return tokens.get(pos);
        }
        return null;
    }

    @Override
    public void unread() {
        if (pos > 0) {
            pos--;
        }
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public void setPosition(int position) {
        if (position > 0 && position < tokens.size()) {
            pos = position;
        }
    }

    @Override
    public void dump() {
        Token token;
        while ((token = read()) != null) {
            System.out.println(token.getText() + "\t\t" + token.getType());
        }
    }
}
