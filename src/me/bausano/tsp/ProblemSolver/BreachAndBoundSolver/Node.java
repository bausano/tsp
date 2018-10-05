package me.bausano.tsp.ProblemSolver.BreachAndBoundSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Node implements Comparable<Node>{
    private Integer index;

    private Tuple<Double> tuple;

    private Double reduction;

    private Double shadowCost = 0d;

    private Node parent;

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

       for (Integer k = 0; k < len; k++) {
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

        List<Integer> visited = new ArrayList<>();

        if (getParent() != null) {
            visited.addAll(parent.getVisited());
        }

        visited.add(getIndex());

        System.out.println(visited);

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
