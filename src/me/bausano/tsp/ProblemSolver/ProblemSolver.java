package me.bausano.tsp.ProblemSolver;

public interface ProblemSolver {
    /**
     * Runs the algorithm.
     *
     * @param matrix Matrix of distances between the cities.
     */
    void findShortestPath(Double[][] matrix);
}
