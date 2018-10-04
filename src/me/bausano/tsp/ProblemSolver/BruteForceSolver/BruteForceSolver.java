package me.bausano.tsp.ProblemSolver.BruteForceSolver;

import me.bausano.tsp.ProblemSolver.ProblemSolver;

public class BruteForceSolver implements ProblemSolver {
    @Override
    public Double findShortestPath(Double[][] matrix) {
        return matrix[0][1];
    }
}
