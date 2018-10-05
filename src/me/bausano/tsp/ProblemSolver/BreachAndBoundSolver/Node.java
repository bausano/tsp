package me.bausano.tsp.ProblemSolver.BreachAndBoundSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Node implements Comparable<Node>{
    private Integer index;

    private Tuple<Double> tuple;

    private Double reduction;

    private Double shadowCost = 0d;

    private Integer visited;

    Node(Integer index, Tuple<Double> tuple, Double reduction) {
        this(index, tuple, reduction, 0);
    }

    Node(Integer index, Tuple<Double> tuple, Double reduction, Integer visited) {
        this.index = index;
        this.tuple = tuple;
        this.reduction = reduction;
        this.visited = visited;
    }

    List<Integer> getDescendants() {
       List<Integer> descendants = new ArrayList<>();

       Double[][] matrix = tuple.getMatrix();

       for (Integer k = 0; k < matrix.length; k++) {
           if (Objects.equals(matrix[index][k], BreachAndBoundSolver.INFINITY)) {
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

    public Integer getVisited() {
        return visited;
    }
}
