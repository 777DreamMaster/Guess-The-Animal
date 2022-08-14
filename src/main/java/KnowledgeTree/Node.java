package KnowledgeTree;

import Utils.LanguageRules;
import Utils.StringOperations;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node<T> {
    private T value;
    private Node<T> left;
    private Node<T> right;

    public Node() {
    }

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }
    @JsonIgnore
    public boolean isAnimal() {
        return isLeaf();
    }
    @JsonIgnore
    public boolean isStatement() {
        return !isAnimal();
    }
    @JsonIgnore
    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "└ ", "  ");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(
                this.isStatement()
                        ? StringOperations.capitalize(LanguageRules.applyRules("question", value.toString())) + "?"
                        : value
        );
        buffer.append("\n");
        List<Node<T>> children = new ArrayList<>();
        if (right != null) children.add(right);
        if (left != null) children.add(left);
        for (Iterator<Node<T>> it = children.iterator(); it.hasNext();) {
            Node<T> next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├ ", childrenPrefix + "│");
            } else {
                next.print(buffer, childrenPrefix + "└ ", childrenPrefix + " ");
            }
        }
    }

}
