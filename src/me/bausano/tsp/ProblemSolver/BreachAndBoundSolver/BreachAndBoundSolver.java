package me.bausano.tsp.ProblemSolver.BreachAndBoundSolver;

import me.bausano.tsp.ProblemSolver.ProblemSolver;

import java.util.List;
import java.util.Objects;

public class BreachAndBoundSolver implements ProblemSolver {
    static final Double INFINITY = -1d;

    /**
     * Symmetric matrix with distances.
     */
    private Double[][] matrix;

    /**
     * Upper bound starts with INFINITY.
     */
    private Double upper = BreachAndBoundSolver.INFINITY;

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

        Tuple<Double> patientZeroTuple = reduceMatrix(this.matrix.clone());
        Node patientZero = new Node(0, patientZeroTuple, patientZeroTuple.getReduction());

        List<Integer> descendants = patientZero.getDescendants();

        descendants.forEach((descendant) -> {
            Node parent = patientZero;
            Integer parentIndex = parent.getIndex();
            Double[][] parentMatrix = parent.getTuple().getMatrix();
            Double[][] descendantDescribed = describeRelationInMatrix(parentMatrix, parentIndex, descendant);
            Tuple<Double> tuple = reduceMatrix(descendantDescribed);
            Double reduction = parent.getReduction() + tuple.getReduction() + parentMatrix[parentIndex][descendant];

            Node node = new Node(descendant, tuple, );
        });

        return 0d;
    }

    recursiveSearch(Tuple<Double> parent, List<Integer> visited) {

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
        Double[][] clone = original.clone();

        for (Integer k = 0; k < clone.length; k++) {
            clone[parent][k] = BreachAndBoundSolver.INFINITY;
            clone[k][child] = BreachAndBoundSolver.INFINITY;
        }

        clone[parent][child] = BreachAndBoundSolver.INFINITY;
        clone[child][parent] = BreachAndBoundSolver.INFINITY;

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
                } else if (Objects.equals(d, BreachAndBoundSolver.INFINITY)) {
                    continue;
                }

                min = Math.min(min, d);
            }

            // Updates the data.
            for (int j = 0; j < matrix.length; j++) {
                if (Objects.equals(matrix[k][j], BreachAndBoundSolver.INFINITY)) {
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
                } else if (Objects.equals(d[k], BreachAndBoundSolver.INFINITY)) {
                    continue;
                }

                min = Math.min(min, d[k]);
            }

            for (int j = 0; j < matrix.length; j++) {
                if (Objects.equals(matrix[j][k], BreachAndBoundSolver.INFINITY)) {
                    continue;
                }

                Double reduced = matrix[j][k] - min;
                reduction += reduced;
                matrix[j][k] = reduced;
            }
        }

        return reduction;
    }
}
