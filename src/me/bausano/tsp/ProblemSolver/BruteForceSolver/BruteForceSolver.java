package me.bausano.tsp.ProblemSolver.BruteForceSolver;

import me.bausano.tsp.ProblemSolver.ProblemSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BruteForceSolver implements ProblemSolver {
    /**
     * Symmetric matrix with distances.
     */
    private Double[][] matrix;

    /**
     * Brute force solving of Travelling Salesman problem.
     *
     * @param matrix Matrix of distances between the cities.
     *
     * @return Minimum distance one has to travel in order to visit all points.
     */
    @Override
    public Double findShortestPath(Double[][] matrix) {
        this.matrix = matrix;

        List<Integer> visited = new ArrayList<>();
        visited.add(0);

        return recursivelySearch(0d, visited);
    }

    private Double recursivelySearch(Double traveled, List<Integer> visited) {
        if (visited.size() == matrix.length) {
            return traveled;
        }

        List<Double> distances = new ArrayList<>();
        Integer lastVisited = visited.get(visited.size() - 1);
        for (Integer point = 1; point < matrix.length; point++) {
            if (visited.contains(point)) {
                continue;
            }

            List<Integer> visitedExpanded = new ArrayList<>(visited);
            visitedExpanded.add(point);

            distances.add(recursivelySearch(traveled + matrix[lastVisited][point], visitedExpanded));
        }

        return Collections.min(distances);
    }
}
