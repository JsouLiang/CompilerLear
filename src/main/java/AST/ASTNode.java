package AST;

import java.util.List;



public interface ASTNode {
    // 父节点
    ASTNode getParent();
    // 子节点
    List<ASTNode> getChildren();
    // AST 节点类型
    ASTNodeType getType();
    // 文本值
    String getText();
}
