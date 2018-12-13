package me.bausano.tsp.ProblemSolver.BranchAndBoundWithNeighbour;

import me.bausano.tsp.IO.Referee;
import me.bausano.tsp.ProblemSolver.ProblemSolver;

import java.util.*;
import java.util.List;

public class Solver implements ProblemSolver {
    static final double INFINITY = Double.POSITIVE_INFINITY;

    /**
     * Symmetric matrix with distances.
     */
    private double[][] matrix;

    /**
     * Lower bound starts with INFINITY.
     */
    private static double lower = INFINITY;

    /**
     * Best ranking leaf node so far.
     */
    private Node min;

    /**
     * Branch-n-bound approach to Travelling Salesman problem.
     * To save memory, we try will to prune the queue.
     *
     * @param matrix Matrix of distances between the cities.
     *
     * @return Minimum distance one has to travel in order to visit all points.
     */
    @Override
    public double findShortestPath(double[][] matrix) {
        this.matrix = matrix;
        // Presets lower bound with a first path found.
        double nearestNeighbour = nearestNeighbour(0d, new ArrayList<Integer>(){{ add(0); }});
        double linearSearch = linearSearch();

        lower = Math.min(nearestNeighbour, linearSearch);

        // Creates patient zero.
        Tuple rootTuple = reduceMatrix(deepClone(matrix));
        Node root = new Node(0, rootTuple, rootTuple.getReduction(), null);

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(root);

        search(queue);

        System.out.println("Shortest found path:");
        System.out.print(this.min);
        System.out.println("0]");

        return this.min.getReduction();
    }

    /**
     * Performs the search.
     *
     * @param queue Priority queue.
     */
    private void search(PriorityQueue<Node> queue) {
        Node parent;

        while ((parent = queue.poll()) != null) {
            if (!Objects.equals(lower, INFINITY) && parent.getReduction() > lower) {
                continue;
            }

            List<Integer> descendants = parent.getDescendants();

            // If all cities have been visited, compares the result to current lower bound.
            if (descendants.size() == 0) {
                if ((Objects.equals(lower, INFINITY) || parent.getReduction() < lower)) {
                    lower = parent.getReduction();
                    this.min = parent;
                    System.out.printf("Path %s0] with cost of %f has been found in %dns. Continuing search ...\n",
                            this.min, lower, Referee.getTimeSoFar());
                }

                continue;
            }

            // Attempts to spawn all children of parent.
            for (int descendant : descendants) {
                Node child = spawnChild(descendant, parent);

                if (child != null) queue.add(child);
            }
        }
    }

    private Double nearestNeighbour(Double traveled, List<Integer> visited) {
        // Gets last point visited which will be used to compute the distance from this to other points.
        int lastVisited = visited.get(visited.size() - 1);

        // If we have visited all points, return the total distance.
        if (visited.size() == matrix.length) {
            return traveled + matrix[lastVisited][0];
        }

        // Finds the minimum value.
        Double nearestNeighbourDistance = Double.MAX_VALUE;
        Integer nearestNeighbour = null;
        for (int child = 1; child < matrix.length; child++) {
            if (visited.contains(child) || nearestNeighbourDistance < matrix[lastVisited][child]) {
                continue;
            }

            nearestNeighbourDistance = matrix[lastVisited][child];
            nearestNeighbour = child;
        }

        visited.add(nearestNeighbour);

        return nearestNeighbour(traveled + nearestNeighbourDistance, visited);
    }

    /**
     * Finds the cost of a the simplest path.
     * 0 - 1 - 2 - ... - n - 0
     *
     * @return Cost of going from first city to second and so on.
     */
    private double linearSearch() {
        double cost = 0d;

        for (int x = 1; x < matrix.length; x++) {
            cost += matrix[x - 1][x];
        }

        return cost + matrix[matrix.length - 1][0];
    }

    /**
     * Spawns child parent node.
     *
     * @param index Child index.
     * @param parent Node the child is coming from.
     *
     * @return New node or null.
     */
    private Node spawnChild(Integer index, Node parent) {
        Integer parentIndex = parent.getIndex();
        double[][] parentMatrix = parent.getTuple().getMatrix();
        // Reduces parent matrix to produce child.
        double[][] childDescribed = describeRelationInMatrix(parentMatrix, parentIndex, index);
        Tuple childTuple = reduceMatrix(childDescribed);
        // Calculates lower bound cost with following formula:
        // R = cost of parent + cost of step from parent to child + child matrix reduction
        double reduction = parent.getReduction() + childTuple.getReduction() + parentMatrix[parentIndex][index];

        if (!Objects.equals(lower, INFINITY) && lower < reduction) {
            return null;
        }

        return new Node(index, childTuple, reduction, parent);
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
    private double[][] describeRelationInMatrix(double[][] original, Integer parent, Integer child) {
        double[][] clone = deepClone(original);

        for (int k = 0; k < clone.length; k++) {
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
    private Tuple reduceMatrix(double[][] matrix) {
        double reduction = 0d;

        reduction += reduceMatrixRows(matrix);
        reduction += reduceMatrixColumns(matrix);

        return new Tuple(reduction, matrix);
    }

    /**
     * Reduces all rows on a matrix.
     *
     * @param source Matrix to be reduced.
     *
     * @return The reduction of reduction.
     */
    private double reduceMatrixRows(double[][] source) {
        double reduction = 0d;

        outer:
        for (int row = 0; row < source.length; row++) {
            // Finds the minimum cost.
            double min = Double.MAX_VALUE;
            for (double cell : source[row]) {
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
    private double reduceMatrixColumns(double[][] source) {
        double reduction = 0d;

        outer:
        for (int col = 0; col < source.length; col++) {
            double min = Double.MAX_VALUE;
            // Finds minimum in a column.
            for (double[] row : source) {
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
    private double[][] deepClone(double[][] original) {
        double[][] clone = new double[original.length][original.length];

        for (int k = 0; k < original.length; k++) {
            System.arraycopy(original[k], 0, clone[k], 0, original.length);
        }

        return clone;
    }
}
