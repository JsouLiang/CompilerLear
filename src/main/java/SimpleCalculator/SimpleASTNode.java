package SimpleCalculator;

import AST.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 一个简单的AST节点的实现。
 * 属性包括：类型、文本值、父节点、子节点。
 */
public class SimpleASTNode implements ASTNode {
    private SimpleASTNode parent;
    private List<ASTNode> children;
    // read-only children
    private List<ASTNode> roChildren;
    private ASTNodeType type;
    private String text;

    public SimpleASTNode(ASTNodeType type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public ASTNode getParent() {
        return parent;
    }

    @Override
    public List<ASTNode> getChildren() {
        return roChildren;
    }

    @Override
    public ASTNodeType getType() {
        return type;
    }

    @Override
    public String getText() {
        return text;
    }

    public void addChild(SimpleASTNode child) {
        if (children == null) {
            children = new ArrayList<>();
            roChildren = Collections.unmodifiableList(children);
        }
        children.add(child);
        child.parent = this;
    }
}
