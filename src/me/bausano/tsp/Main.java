package me.bausano.tsp;

import me.bausano.tsp.Enum.Algorithm;
import me.bausano.tsp.Exception.InvalidAlgorithmChoiceException;
import me.bausano.tsp.Exception.InvalidInputDataFormat;
import me.bausano.tsp.IO.Eloquent;
import me.bausano.tsp.IO.InputParser.InputParser;
import me.bausano.tsp.IO.InputParser.PointDistanceParser;
import me.bausano.tsp.IO.Referee;
import me.bausano.tsp.ProblemSolver.BruteForceSolver.BruteForceSolver;
import me.bausano.tsp.ProblemSolver.ProblemSolver;

public class Main {

    public static void main(String[] args) {
        InputParser parser = new PointDistanceParser();
        Eloquent eloquent = new Eloquent(parser);

        try {
            eloquent.requestAlgorithm();

            eloquent.requestData();
        } catch (InvalidAlgorithmChoiceException e) {
            System.out.println("Invalid algorithm choice. Please refer to program description.");
        } catch (InvalidInputDataFormat e) {
            System.out.println("Input data are not in correct format. Please refer to coursework 1 sheet.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Referee referee = new Referee();
        Algorithm algorithm = eloquent.getAlgorithm();
        int[][] matrix = eloquent.getMap();
        ProblemSolver solver = matchSolver(algorithm);

        referee.start();
        solver.findShortestPath(matrix);
        referee.stop();

        System.out.printf("Problem shortest path %f has been found in %dms.",
                referee.getPath(), referee.getTime());
    }

    private static ProblemSolver matchSolver(Algorithm algorithm) {
        switch (algorithm) {
            case BRUTE_FORCE:
                return new BruteForceSolver();
        }
    }
}
