package me.bausano.tsp.ProblemSolver.BranchAndBoundSolver;

import java.util.ArrayList;
import java.util.List;

class Node implements Comparable<Node>{
    private Integer index;

    private Tuple<Double> tuple;

    private final Double reduction;

    private Double shadowCost = 0d;

    private Node parent = null;

    private List<Integer> visited = null;

    Node(Integer index, Tuple<Double> tuple, Double reduction) {
        this(index, tuple, reduction, null);
    }

    Node(Integer index, Tuple<Double> tuple, Double reduction, Node parent) {
        this.index = index;
        this.tuple = tuple;
        this.reduction = reduction;
        this.parent = parent;
    }

    List<Integer> getDescendants() {
       List<Integer> descendants = new ArrayList<>();
       List<Integer> visitedNodes = getVisited();

       Integer len = tuple.getMatrix().length;

        for (Integer k = 1; k < len; k++) {
            if (visitedNodes.contains(k)) {
                continue;
            }

            descendants.add(k);
        }

       return descendants;
    }

    List<Integer> getVisited() {
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

    Integer getIndex() {
        return index;
    }

    Tuple<Double> getTuple() {
        return tuple;
    }

    Double getReduction() {
        return reduction;
    }

    void incrementShadowCost(Double shadowCost) {
        this.shadowCost += shadowCost;
    }

    Double getShadowCost() {
        return shadowCost;
    }

    private Node getParent() {
        return parent;
    }

    @Override
    public int compareTo(Node node) {
        Double diff = this.getReduction() - node.getReduction();

        return diff > 0 ? 1 : diff < 0 ? -1 : 0;
    }
}
