package me.bausano.tsp.ProblemSolver.DepthFirst;

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
     * Brute force solving of Travelling Salesman problem.
     *
     * O(n!)
     *
     * @param matrix Matrix of distances between the cities.
     *
     * @return Minimum distance one has to travel in order to visit all descendants.
     */
    @Override
    public double findShortestPath(double[][] matrix) {
        this.matrix = matrix;

        // Since visiting all descendants is cycling and it doesn't matter what descendant we start from, we always
        // start from descendant i = 0.
        List<Integer> visited = new ArrayList<>();
        visited.add(0);

        return recursiveSearch(0d, visited);
    }

    /**
     * Recursively permutes the matrix.
     *
     * @param traveled Distance that has been already traveled.
     * @param visited List of descendants that have been already visited.
     *
     * @return The distance which would get us to that descendant.
     */
    private Double recursiveSearch(double traveled, List<Integer> visited) {
        // Gets last descendant visited which will be used to compute the distance from this to other descendants.
        Integer lastVisited = visited.get(visited.size() - 1);

        // If we have visited all descendants, return the total distance.
        if (visited.size() == matrix.length) {
            return traveled + matrix[lastVisited][0];
        }

        List<Double> distances = new ArrayList<>();
        for (Integer descendant = 1; descendant < matrix.length; descendant++) {
            // If this descendant has already been visited, skip.
            if (visited.contains(descendant)) {
                continue;
            }

            // Copy current visited list and add this descendant to it.
            List<Integer> visitedExpanded = new ArrayList<>(visited);
            visitedExpanded.add(descendant);

            distances.add(recursiveSearch(traveled + matrix[lastVisited][descendant], visitedExpanded));
        }

        // Returns the minimum distance.
        return Collections.min(distances);
    }
}
