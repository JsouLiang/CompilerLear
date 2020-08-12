package Token;

public interface TokenReader {
    /**
     * 读取 Token 流中当前 Token，并从中取出 Token
     * 如果 Token 流已空，返回 null
     * @return
     */
    Token read();

    /**
     * 返回 Token 流中当前 Token, 但不从中取出 Token
     * 如果 Token 流已空返回 null
     * @return
     */
    Token peek();

    /**
     * Token 流回退一步，回复原来的 Token
     * @return
     */
    void unread();

    /**
     * 获取Token流当前的读取位置
     * @return
     */
    int getPosition();

    /**
     * 设置Token流当前的读取位置
     * @param position
     */
    void setPosition(int position);

    void dump();
}
