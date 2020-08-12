package SimpleCalculator;

import AST.ASTNode;
import AST.ASTNodeType;
import Token.*;
import Token.TokenReader;

public class SimpleCalculator {

    /**
     * 整形变量声明：
     * int a;
     * int b = 2 * 3;
     * @param tokens
     * @return
     * @throws Exception
     */
    private SimpleASTNode intDeclare(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        // 预读
        Token token = tokens.peek();
        // 匹配 int
        if (token != null && token.getType() == TokenType.Int) {
            token = tokens.read();  // 消耗掉 int
            // 变量声明
            if (tokens.peek().getType() == TokenType.Identifier) {
                tokens.read();      // 消耗变量声明
                // 创建 int AST 节点，节点内容就是变量名
                node = new SimpleASTNode(ASTNodeType.IntDeclaration, token.getText());
                token = tokens.peek();
                // 赋值 token
                if (token != null && token.getType() == TokenType.Assignment) {
                    tokens.read();  // 消耗掉 =
                    SimpleASTNode child = additive(tokens);
                    if (child == null) {
                        throw new Exception("invalide variable initialization");
                    } else {
                        node.addChild(child);
                    }
                }
            } else {
                throw new Exception("variable name expected");
            }

        }
        return node;
    }

    /**
     * 加法表达式
     * 通过文法嵌套实现优先级：
     * 把算数表达式分成两级：第一级是加法，第二级是乘法，把乘法规则作为加法规则子规则，这样在解析 AST 时，乘法一定是
     * 加法节点的子节点，从而被优先计算
     *
     * additive = multiplicative | additive + multiplicative
     * multiplicative = int | multiplicative * int
     *
     * 存在左递归问题
     *
     * 这里暂时修改左递归为：
     * additive = multiplicative | multiplicative + additive
     *
     * @param tokens
     * @return
     * @throws Exception
     */
    SimpleASTNode additive(TokenReader tokens) throws Exception {
        SimpleASTNode multiplicative = multiplicative(tokens);
        SimpleASTNode node = multiplicative;
        Token token = tokens.peek();
        // 如果包含第二个节点即加号
        if (multiplicative != null && token != null) {
            if (token.getType() == TokenType.Plus) {    // +
                token = tokens.read();
                SimpleASTNode additive = additive(tokens);  // 递归解析第二个节点
                if (additive != null) {
                    // rootNode:       +
                    // children: value1 value2
                    node = new SimpleASTNode(ASTNodeType.AdditiveExp, token.getText());
                    node.addChild(multiplicative);
                    node.addChild(additive);
                } else {
                    throw new Exception("invalid additive expression, expecting the right part.");
                }
            }
        }
        return node;
    }

    SimpleASTNode multiplicative(TokenReader tokens) throws Exception {
        SimpleASTNode primary = primary(tokens);
        SimpleASTNode node = primary;
        Token token = tokens.peek();
        if (primary != null && token != null) {
            // token: * 或者 /
            if (token.getType() == TokenType.Star || token.getType() == TokenType.Slash) {
                token = tokens.read();
                SimpleASTNode multiplicative = multiplicative(tokens);
                if (multiplicative != null) {
                    node = new SimpleASTNode(ASTNodeType.Multiplicative, token.getText());
                    node.addChild(primary);
                    node.addChild(multiplicative);
                } else {
                    throw new Exception("invalid multiplicative expression, expecting the right part.");
                }
            }
        }
        return node;
    }

    /**
     * primary: 数字 | 变量 | (加法表达式)
     * @param tokens
     * @return
     * @throws Exception
     */
    SimpleASTNode primary(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        Token token = tokens.peek();
        if (token != null) {
            // 整形字面量
            if (token.getType() == TokenType.IntLiteral) {
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntLiteral, token.getText());
            } else if (token.getType() == TokenType.Identifier) {
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.Identifier, token.getText());
            } else if (token.getType() == TokenType.LeftParen) {
                // ( + )  带括号的加法表达式
                tokens.read();
                node = additive(tokens);
                if (node != null) {
                    token = tokens.peek();
                    if (token != null && token.getType() == TokenType.RightParen) {
                        tokens.read();
                    } else {
                        throw new Exception("expecting right parenthesis");
                    }
                } else {
                    throw new Exception("expecting an additive expression inside parenthesis");
                }
            }
        }
        return node;
    }

    public static void main(String[] args) {

    }
}
