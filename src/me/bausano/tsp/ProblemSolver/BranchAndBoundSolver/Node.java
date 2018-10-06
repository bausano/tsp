package me.bausano.tsp.ProblemSolver.BranchAndBoundSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Node implements Comparable<Node>{
    private Integer index;

    private Tuple<Double> tuple;

    private final Double reduction;

    private Double shadowCost = 0d;

    Node(Integer index, Tuple<Double> tuple, Double reduction) {
        this.index = index;
        this.tuple = tuple;
        this.reduction = reduction;
    }

    List<Integer> getDescendants() {
       List<Integer> descendants = new ArrayList<>();

       Double[][] matrix = tuple.getMatrix();

        for (Integer k = 1; k < matrix.length; k++) {
            if (Objects.equals(matrix[k][0], BranchAndBoundSolver.INFINITY)) {
                continue;
            }

            descendants.add(k);
        }

       return descendants;
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

    @Override
    public int compareTo(Node node) {
        Double diff = this.getReduction() - node.getReduction();

        return diff > 0 ? 1 : diff < 0 ? -1 : 0;
    }
}
