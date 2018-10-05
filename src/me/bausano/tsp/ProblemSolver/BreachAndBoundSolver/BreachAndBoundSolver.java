package me.bausano.tsp.ProblemSolver.BreachAndBoundSolver;

import me.bausano.tsp.ProblemSolver.ProblemSolver;

import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class BreachAndBoundSolver implements ProblemSolver {
    static final Double INFINITY = -1d;

    /**
     * Symmetric matrix with distances.
     */
    private Double[][] matrix;

    /**
     * Upper bound starts with INFINITY.
     */
    private Double upper = INFINITY;

    /**
     * Best ranking leaf node so far.
     */
    private Node min;

    /**
     * Breach-n-bound approach to Travelling Salesman problem.
     *
     * @param matrix Matrix of distances between the cities.
     *
     * @return Minimum distance one has to travel in order to visit all points.
     */
    @Override
    public Double findShortestPath(Double[][] matrix) {
        this.matrix = matrix;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        Tuple<Double> patientZeroTuple = reduceMatrix(deepClone(matrix));
        Node patientZero = new Node(0, patientZeroTuple, patientZeroTuple.getReduction());
        queue.add(patientZero);

        search(queue);

        System.out.println(matrix[this.min.getIndex()][patientZero.getIndex()]);

        return this.min != null ? this.min.getShadowCost() : INFINITY;
    }

    private void search(PriorityQueue<Node> queue) {
        Node parent;

        while ((parent = queue.poll()) != null) {
            System.out.println(parent.getVisited());

            List<Integer> descendants = parent.getDescendants();
            if (descendants.size() == 0) {
                if ((Objects.equals(this.upper, INFINITY) || parent.getReduction() < this.upper) && parent.getVisited().size() == matrix.length) {
                    this.upper = parent.getReduction();
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

                if (!Objects.equals(this.upper, INFINITY) && this.upper < reduction) {
                    return;
                }

                Node child = new Node(descendant, descendantTuple, reduction, parent);
                child.incrementShadowCost(parent.getShadowCost() + this.matrix[parentIndex][descendant]);
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

        clone[parent][child] = INFINITY;
        clone[child][parent] = INFINITY;

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
     * @param matrix Matrix to be reduced.
     *
     * @return The reduction of reduction.
     */
    private Double reduceMatrixRows (Double[][] matrix) {
        Double reduction = 0d;

        outer:
        for (int k = 0; k < matrix.length; k++) {
            // Finds the minimum cost.
            Double min = Double.MAX_VALUE;
            for (Double d : matrix[k]) {
                if (d == 0) {
                    continue outer;
                } else if (Objects.equals(d, INFINITY)) {
                    continue;
                }

                min = Math.min(min, d);
            }

            // Updates the data.
            for (int j = 0; j < matrix.length; j++) {
                if (Objects.equals(matrix[k][j], INFINITY)) {
                    continue;
                }

                Double reduced = matrix[k][j] - min;
                reduction += reduced;
                matrix[k][j] = reduced;
            }
        }

        return reduction;
    }

    /**
     * Reduces all columns on a matrix.
     *
     * @param matrix Matrix to be reduced.
     *
     * @return The reduction of reduction.
     */
    private Double reduceMatrixColumns(Double[][] matrix) {
        Double reduction = 0d;

        outer:
        for (int k = 0; k < matrix.length; k++) {
            Double min = Double.MAX_VALUE;
            for (Double[] d : matrix) {
                if (d[k] == 0) {
                    continue outer;
                } else if (Objects.equals(d[k], INFINITY)) {
                    continue;
                }

                min = Math.min(min, d[k]);
            }

            for (int j = 0; j < matrix.length; j++) {
                if (Objects.equals(matrix[j][k], INFINITY)) {
                    continue;
                }

                Double reduced = matrix[j][k] - min;
                reduction += reduced;
                matrix[j][k] = reduced;
            }
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
    private Double[][] deepClone (Double[][] original) {
        Double[][] clone = new Double[original.length][original.length];

        for (Integer k = 0; k < original.length; k++) {
            System.arraycopy(original[k], 0, clone[k], 0, original.length);
        }

        return clone;
    }
}
