package me.bausano.tsp.ProblemSolver.BranchAndBoundWithNeighbour;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Node implements Comparable<Node> {
    /**
     * City index.
     */
    private Integer index;

    /**
     * Tuple with matrix and it's reduction.
     */
    private Tuple tuple;

    /**
     * Total reduction so far.
     */
    private final double reduction;

    /**
     * Parent node.
     */
    private Node parent;

    private List<Integer> children = null;

    /**
     * Class constructor.
     *
     * @param index City index.
     * @param tuple Node information.
     * @param reduction Lower bound.
     */
    Node(Integer index, Tuple tuple, double reduction, Node parent) {
        this.index = index;
        this.tuple = tuple;
        this.reduction = reduction;
        this.parent = parent;
    }

    /**
     * Generates list of indexes ought to be explored.
     *
     * @return List of integers.
     */
    List<Integer> getDescendants() {
        if (this.children != null) {
            return this.children;
        }

        List<Integer> children = new ArrayList<>();

        double[][] matrix = tuple.getMatrix();

        // All non-infinite cells in first column are added to the list.
        // Great and quick reference as to which nodes were not explored yet.
        for (Integer k = 1; k < matrix.length; k++) {
            if (Objects.equals(matrix[k][0], Solver.INFINITY)) continue;

            children.add(k);
        }

        this.children = children;

       return children;
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
     * Tuple getter.
     *
     * @return Tuple
     */
    Tuple getTuple() {
        return tuple;
    }

    /**
     * Lower bound getter.
     *
     * @return Reduction
     */
    double getReduction() {
        return reduction;
    }

    /**
     * Comparator for priority queue. We shall always explore the node with the least cost.
     * We prioritize those who are closer to a full cycle as
     * having the lower bound is important for pruning.
     *
     * @param node Other node in the queue.
     *
     * @return Whether this node is of a greater importance or not.
     */
    @Override
    public int compareTo(Node node) {
        double diff = this.getReduction() - node.getReduction();

        return diff > 0 ? 1 : diff < 0 ? -1 : 0;
    }

    /**
     * Prints the node path.
     *
     * @return
     */
    @Override
    public String toString() {
        if (parent == null) {
            return "[" + index + ", ";
        }

        return parent.toString() + index + ", ";
    }
}
