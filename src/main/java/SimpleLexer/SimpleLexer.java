package SimpleLexer;

import Token.*;

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.List;

import static Utils.Utils.*;

enum DFAState {
    Initial,

    If, Id_if1, Id_if2, Else, Id_else1, Id_else2, Id_else3, Id_else4, Int, Id_int1, Id_int2, Id_int3, Id, GT, GE,

    Assignment,

    Plus, Minus, Star, Slash,

    SemiColon,
    LeftParen,
    RightParen,

    IntLiteral

}

public class SimpleLexer {
    // 临时保存 Token 的文本
    private StringBuffer tokenText;
    // 保存解析出来的 Token
    private List<Token> tokens;
    // 当前正在解析的 Token
    SimpleToken token;

    SimpleTokenReader tokenizer(String code) {
        tokens = new ArrayList<Token>();
        CharArrayReader reader = new CharArrayReader(code.toCharArray());
        tokenText = new StringBuffer();
        token = new SimpleToken();
        // iCh 和 ch 表示当前读到的字符
        int iCh = 0;
        char ch = 0;
        DFAState state = DFAState.Initial;
        try {
            while ((iCh = reader.read()) != -1) {
                ch = (char)iCh;
                switch (state) {
                    case Initial:
                    case GE:
                    case Assignment:
                    case Plus:
                    case Minus:
                    case Star:
                    case Slash:
                    case SemiColon:
                    case LeftParen:
                    case RightParen:
                        state = initToken(ch);      //重新确定后续状态
                    break;
                    case Id:
                        if (isAlpha(ch) || isDigit(ch)) {
                            tokenText.append(ch);//保持标识符状态
                        } else {
                            state = initToken(ch);//退出标识符状态，并保存Token
                        }
                    case GT:
                        if (ch == '=') {
                            token.type = TokenType.GE;  //转换成GE
                            state = DFAState.GE;
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);      //退出GT状态，并保存Token
                        }
                        break;
                    //退出当前状态，并保存Token
                    case IntLiteral:
                        if (isDigit(ch)) {
                            tokenText.append(ch);       //继续保持在数字字面量状态
                        } else {
                            state = initToken(ch);      //退出当前状态，并保存Token
                        }
                        break;
                    case Id_int1:
                        if (ch == 'n') {
                            state = DFAState.Id_int2;
                            tokenText.append(ch);
                        }
                        else if (isDigit(ch) || isAlpha(ch)){
                            state = DFAState.Id;    //切换回Id状态
                            tokenText.append(ch);
                        }
                        else {
                            state = initToken(ch);
                        }
                        break;
                    case Id_int2:
                        if (ch == 't') {
                            state = DFAState.Id_int3;
                            tokenText.append(ch);
                        }
                        else if (isDigit(ch) || isAlpha(ch)){
                            state = DFAState.Id;    //切换回id状态
                            tokenText.append(ch);
                        }
                        else {
                            state = initToken(ch);
                        }
                        break;
                    case Id_int3:
                        if (isBlank(ch)) {
                            token.type = TokenType.Int;
                            state = initToken(ch);
                        }
                        else{
                            state = DFAState.Id;    //切换回Id状态
                            tokenText.append(ch);
                        }
                        break;
                    default:

                }

            }
            // 把最后一个token送进去
            if (tokenText.length() > 0) {
                initToken(ch);
            }
        } catch (Exception e) {

        }
        return new SimpleTokenReader(tokens);
    }

    private DFAState initToken(char ch) {
        if (tokenText.length() > 0) {
            token.text = tokenText.toString();
            tokens.add(token);

            tokenText = new StringBuffer();
            token = new SimpleToken();
        }
        DFAState newState = DFAState.Initial;
        if (isAlpha(ch)) {              //第一个字符是字母
            if (ch == 'i') {
                newState = DFAState.Id_int1;
            } else {
                newState = DFAState.Id; //进入Id状态
            }
            token.type = TokenType.Identifier;
            tokenText.append(ch);
        } else if (isDigit(ch)) {       //第一个字符是数字
            newState = DFAState.IntLiteral;
            token.type = TokenType.IntLiteral;
            tokenText.append(ch);
        } else if (ch == '>') {         //第一个字符是>
            newState = DFAState.GT;
            token.type = TokenType.GT;
            tokenText.append(ch);
        } else if (ch == '+') {
            newState = DFAState.Plus;
            token.type = TokenType.Plus;
            tokenText.append(ch);
        } else if (ch == '-') {
            newState = DFAState.Minus;
            token.type = TokenType.Minus;
            tokenText.append(ch);
        } else if (ch == '*') {
            newState = DFAState.Star;
            token.type = TokenType.Star;
            tokenText.append(ch);
        } else if (ch == '/') {
            newState = DFAState.Slash;
            token.type = TokenType.Slash;
            tokenText.append(ch);
        } else if (ch == ';') {
            newState = DFAState.SemiColon;
            token.type = TokenType.SemiColon;
            tokenText.append(ch);
        } else if (ch == '(') {
            newState = DFAState.LeftParen;
            token.type = TokenType.LeftParen;
            tokenText.append(ch);
        } else if (ch == ')') {
            newState = DFAState.RightParen;
            token.type = TokenType.RightParen;
            tokenText.append(ch);
        } else if (ch == '=') {
            newState = DFAState.Assignment;
            token.type = TokenType.Assignment;
            tokenText.append(ch);
        } else {
            newState = DFAState.Initial; // skip all unknown patterns
        }
        return newState;
    }
}

