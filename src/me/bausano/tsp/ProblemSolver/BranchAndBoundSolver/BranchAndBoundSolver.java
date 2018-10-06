package me.bausano.tsp.ProblemSolver.BranchAndBoundSolver;

import me.bausano.tsp.ProblemSolver.ProblemSolver;

import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class BranchAndBoundSolver implements ProblemSolver {
    static final Double INFINITY = -1d;

    /**
     * Symmetric matrix with distances.
     */
    private Double[][] matrix;

    /**
     * Lower bound starts with INFINITY.
     */
    private Double lower = INFINITY;

    /**
     * Best ranking leaf node so far.
     */
    private Node min;

    /**
     * Branch-n-bound approach to Travelling Salesman problem.
     *
     * @param matrix Matrix of distances between the cities.
     *
     * @return Minimum distance one has to travel in order to visit all points.
     */
    @Override
    public Double findShortestPath(Double[][] matrix) {
        this.matrix = matrix;

        Tuple<Double> rootTuple = reduceMatrix(deepClone(matrix));
        Node root = new Node(0, rootTuple, 0d);

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(root);

        search(queue);

        return this.min.getShadowCost() + matrix[this.min.getIndex()][0];
    }

    private void search(PriorityQueue<Node> queue) {
        Node parent;

        while ((parent = queue.poll()) != null) {
            if (!Objects.equals(lower, INFINITY) && parent.getReduction() > lower) {
                continue;
            }

            List<Integer> descendants = parent.getDescendants();
            if (descendants.size() == 0) {
                if ((Objects.equals(lower, INFINITY) || parent.getReduction() < lower)) {
                    this.lower = parent.getReduction();
                    this.min = parent;
                }

                continue;
            }

            for (Integer descendant : descendants) {
                Integer parentIndex = parent.getIndex();
                Double[][] parentMatrix = parent.getTuple().getMatrix();
                Double[][] descendantDescribed = describeRelationInMatrix(parentMatrix, parentIndex, descendant);
                Tuple<Double> descendantTuple = reduceMatrix(descendantDescribed);
                Double reduction = parent.getReduction() + descendantTuple.getReduction() + parentMatrix[parentIndex][descendant];

                if (!Objects.equals(lower, INFINITY) && lower < reduction) {
                    continue;
                }

                Node child = new Node(descendant, descendantTuple, reduction);
                child.incrementShadowCost(parent.getShadowCost() + matrix[parentIndex][descendant]);
                queue.add(child);
            }
        }
    }

    /**
     * Makes a copy of a matrix with a column of the child node and row of the parent node
     * are set to infinity to address the fact that they have been searched in.
     *
     * @param original Original matrix.
     * @param parent Parent node index.
     * @param child Child node index.
     *
     * @return New matrix.
     */
    private Double[][] describeRelationInMatrix(Double[][] original, Integer parent, Integer child) {
        Double[][] clone = deepClone(original);

        for (Integer k = 0; k < clone.length; k++) {
            clone[parent][k] = INFINITY;
            clone[k][child] = INFINITY;
        }

        clone[child][0] = INFINITY;

        return clone;
    }

    /**
     * Reduces given matrix and returns a tuple containing a newly reduced matrix and its reduction cost.
     *
     * @param matrix Original matrix will stay untouched.
     *
     * @return Tuple
     */
    private Tuple<Double> reduceMatrix(Double[][] matrix) {
        Double reduction = 0d;

        reduction += reduceMatrixRows(matrix);
        reduction += reduceMatrixColumns(matrix);

        return new Tuple<>(reduction, matrix);
    }

    /**
     * Reduces all rows on a matrix.
     *
     * @param source Matrix to be reduced.
     *
     * @return The reduction of reduction.
     */
    private Double reduceMatrixRows(Double[][] source) {
        Double reduction = 0d;

        outer:
        for (int row = 0; row < source.length; row++) {
            // Finds the minimum cost.
            Double min = Double.MAX_VALUE;
            for (Double cell : source[row]) {
                if (cell == 0)  continue outer;
                else if (Objects.equals(cell, INFINITY)) continue;

                min = Math.min(min, cell);
            }

            if (min == Double.MAX_VALUE) {
                continue;
            }

            // Updates the data.
            for (int col = 0; col < source.length; col++) {
                if (Objects.equals(source[row][col], INFINITY)) {
                    continue;
                }

                source[row][col] = source[row][col] - min;
            }

            reduction += min;
        }

        return reduction;
    }

    /**
     * Reduces all columns on a matrix.
     *
     * @param source Matrix to be reduced.
     *
     * @return The reduction of reduction.
     */
    private Double reduceMatrixColumns(Double[][] source) {
        Double reduction = 0d;

        outer:
        for (int col = 0; col < source.length; col++) {
            Double min = Double.MAX_VALUE;
            // Finds minimum in a column.
            for (Double[] row : source) {
                if (row[col] == 0)  continue outer;
                else if (Objects.equals(row[col], INFINITY)) continue;

                min = Math.min(min, row[col]);
            }

            // If all cells are INFINITY, skip.
            if (min == Double.MAX_VALUE) {
                continue;
            }

            // Subtract minimum from all non-infinity cells.
            for (int row = 0; row < source.length; row++) {
                if (Objects.equals(source[row][col], INFINITY)) {
                    continue;
                }

                source[row][col] = source[row][col] - min;
            }

            reduction += min;
        }

        return reduction;
    }

    /**
     * Deep clones a matrix.
     *
     * @param original Original matrix to be cloned.
     *
     * @return Fresh instance of a matrix object.
     */
    private Double[][] deepClone(Double[][] original) {
        Double[][] clone = new Double[original.length][original.length];

        for (Integer k = 0; k < original.length; k++) {
            System.arraycopy(original[k], 0, clone[k], 0, original.length);
        }

        return clone;
    }
}
