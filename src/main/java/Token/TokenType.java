package Token;

public enum TokenType {
    /**
     *  +
     */
    Plus,
    /**
     *  -
     */
    Minus,
    /**
     *  *
     */
    Star,
    /**
     *  /
     */
    Slash,

    /**
     *  >=
     */
    GE,
    /**
     *  >
     */
    GT,
    /**
     *  ==
     */
    EQ,
    /**
     *  <=
     */
    LE,
    /**
     *  <
     */
    LT,

    /**
     *  ;
     */
    SemiColon,
    /**
     *  (
     */
    LeftParen,
    /**
     *  )
     */
    RightParen,

    /**
     *  =
     */
    Assignment,

    If,
    Else,

    Int,

    /**
     * 标识符
     */
    Identifier,

    /**
     * 整型字面量
     */
    IntLiteral,
    /**
     * 字符串字面量
     */
    StringLiteral
}
