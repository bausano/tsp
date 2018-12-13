package me.bausano.tsp.ProblemSolver.DepthFirstCut;

import me.bausano.tsp.ProblemSolver.ProblemSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver implements ProblemSolver {
    /**
     * Symmetric matrix with distances.
     */
    private double[][] matrix;

    /**
     * Minimum is preset to the highest value Double can hold.
     */
    private double bestResult = Double.MAX_VALUE;

    /**
     * Brute force solving of Travelling Salesman problem.
     *
     * O(n!)
     *
     * @param matrix Matrix of distances between the cities.
     *
     * @return Minimum distance one has to travel in order to visit all points.
     */
    @Override
    public double findShortestPath(double[][] matrix) {
        this.matrix = matrix;

        // Since visiting all points is cycling and it doesn't matter what point we start from, we always
        // start from point i = 0.
        List<Integer> visited = new ArrayList<>();
        visited.add(0);

        return recursiveSearch(0d, visited);
    }

    /**
     * Recursively permutes the matrix.
     *
     * @param traveled Distance that has been already traveled.
     * @param visited List of points that have been already visited.
     *
     * @return The distance which would get us to that point.
     */
    private double recursiveSearch(double traveled, List<Integer> visited) {
        // If the cost is already higher than previous best result, stop exploring this branch.
        if (traveled > bestResult) {
            return traveled;
        }

        // Gets last point visited which will be used to compute the distance from this to other points.
        Integer lastVisited = visited.get(visited.size() - 1);

        // If we have visited all points, return the total distance.
        if (visited.size() == matrix.length) {
            double cost = traveled + matrix[lastVisited][0];
            bestResult = Math.min(cost, bestResult);

            return cost;
        }

        List<Double> distances = new ArrayList<>();
        for (Integer point = 1; point < matrix.length; point++) {
            // If this point has already been visited, skip.
            if (visited.contains(point)) {
                continue;
            }

            // Copy current visited list and add this point to it.
            List<Integer> visitedExpanded = new ArrayList<>(visited);
            visitedExpanded.add(point);

            distances.add(recursiveSearch(traveled + matrix[lastVisited][point], visitedExpanded));
        }

        // Returns the minimum distance.
        return Collections.min(distances);
    }
}
