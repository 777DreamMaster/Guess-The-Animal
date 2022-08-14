package KnowledgeTree;

import Utils.LanguageRules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Tree {
    private Node<String> root;
    private Node<String> current;

    public Tree() {
        root = null;
    }

    public void reset() {
        current = root;
    }

    public Node<String> getRoot() {
        return root;
    }

    public void setRoot(Node<String> root) {
        this.root = root;
    }

    public void setRoot(String value) {
        root = new Node<>(value);
        current = root;
    }

    public Node<String> getCurrent() {
        return current;
    }

    public List<String> findAnimalFacts(String value) {
        return findAnimalFacts(root, value, new ArrayList<>(), true);
    }

    private List<String> findAnimalFacts(Node<String> node, String value,
                                         List<String> facts, boolean isRight) {
        if (!isRight)
            facts.set(facts.size() - 1,
                    LanguageRules.applyRules("negative", facts.get(facts.size() - 1)));
        if (Objects.equals(node.getValue(), value)) return facts;
        if (node.isStatement()) {
            facts.add(node.getValue());
            List<String> left = findAnimalFacts(node.getLeft(), value,
                    new ArrayList<>(facts), false);
            if (left != null) return left;
            return findAnimalFacts(node.getRight(), value, new ArrayList<>(facts), true);
        }
        return null;
    }

    public List<String> findAllAnimals() {
        List<String> list = findAllAnimals(root, new ArrayList<>());
        Collections.sort(list);
        return list;
    }

    private List<String> findAllAnimals(Node<String> node, List<String> animals) {
        if (node.isAnimal()) animals.add(node.getValue());
        if (node.isStatement()) {
            findAllAnimals(node.getLeft(), animals);
            findAllAnimals(node.getRight(), animals);
        }
        return animals;
    }

    public List<String> findAllStatements() {
        return findAllStatements(root, new ArrayList<>());
    }
    private List<String> findAllStatements(Node<String> node, List<String> statements) {
        if (node.isStatement()) {
            statements.add(node.getValue());
            findAllStatements(node.getLeft(), statements);
            findAllStatements(node.getRight(), statements);
        }
        return statements;
    }

    public List<Integer> findAnimalsDepths() {
        return findAnimalsDepths(root, new ArrayList<>(), 0);
    }

    private List<Integer> findAnimalsDepths(Node<String> node, List<Integer> depths, int depth) {
        if (node.isAnimal()) depths.add(depth);
        if (node.isStatement()){
            findAnimalsDepths(node.getLeft(), depths, depth + 1);
            findAnimalsDepths(node.getRight(), depths, depth + 1);
        }
        return depths;
    }

    public List<Object> getStatistics() {
        List<Object> stats = new ArrayList<>();
        List<String> animalNodes = findAllAnimals();
        List<String> statementNodes = findAllStatements();
        List<Integer> depths = findAnimalsDepths();

        stats.add(root.getValue());
        stats.add(animalNodes.size() + statementNodes.size());
        stats.add(animalNodes.size());
        stats.add(statementNodes.size());

        stats.add(getStream(depths).max().orElse(0));
        stats.add(getStream(depths).min().orElse(0));
        stats.add(getStream(depths).average().orElse(0));

        return stats;
    }

    private static IntStream getStream(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue);
    }

    public void move(boolean isRight) {
        current = isRight ? current.getRight() : current.getLeft();
    }

    public void addAnimal(String animal, String statement, boolean isRight) {
        Node<String> oldAnimal = new Node<>(current.getValue());
        Node<String> newAnimal = new Node<>(animal);

        current.setValue(statement);
        current.setRight(isRight ? newAnimal : oldAnimal);
        current.setLeft(isRight ? oldAnimal : newAnimal);
    }

}
