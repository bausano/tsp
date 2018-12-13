package me.bausano.tsp.ProblemSolver.BreadthFirstCut;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>{
    /**
     * Node index.
     */
    private Integer index;

    /**
     * Cost of getting to node.
     */
    private final Double cost;

    /**
     * Node parent which serves to get visited list.
     */
    private Node parent = null;

    /**
     * List of visited nodes so far.
     */
    private List<Integer> visited = null;

    Node(Integer index) {
        this(index, 0d, null);
    }

    Node(Integer index, Double cost, Node parent) {
        this.index = index;
        this.cost = cost;
        this.parent = parent;
    }

    /**
     * Generates list of descendants.
     *
     * @param len Max level of node.
     *
     * @return List with indexes which can be extended by node.
     */
    List<Integer> getDescendants(Integer len) {
        List<Integer> descendants = new ArrayList<>();
        List<Integer> visitedNodes = getVisited();

        for (Integer k = 1; k < len; k++) {
            if (visitedNodes.contains(k)) {
                continue;
            }

            descendants.add(k);
        }

        return descendants;
    }

    /**
     * Gets list of already visited indexes including self.
     *
     * @return A list of indexes.
     */
    private List<Integer> getVisited() {
        if (this.visited != null) {
            return this.visited;
        }

        List<Integer> visited = getParent() != null
                ? new ArrayList<>(parent.getVisited())
                : new ArrayList<>();

        visited.add(getIndex());

        this.visited = visited;

        return visited;
    }

    /**
     * Index getter.
     *
     * @return Index
     */
    Integer getIndex() {
        return index;
    }

    /**
     * Cost getter.
     *
     * @return Cost
     */
    double getCost() {
        return cost;
    }

    /**
     * Parent getter.
     *
     * @return Parent
     */
    private Node getParent() {
        return parent;
    }

    @Override
    public int compareTo(Node node) {
        double diff = this.getCost() - node.getCost();

        return diff > 0 ? 1 : diff < 0 ? -1 : 0;
    }
}
