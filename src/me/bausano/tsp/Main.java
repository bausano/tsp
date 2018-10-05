package me.bausano.tsp;

import me.bausano.tsp.Enum.Algorithm;
import me.bausano.tsp.IO.Eloquent;
import me.bausano.tsp.IO.InputParser.InputParser;
import me.bausano.tsp.IO.InputParser.PointDistanceParser;
import me.bausano.tsp.IO.Referee;
import me.bausano.tsp.ProblemSolver.BreachAndBoundSolver.BreachAndBoundSolver;
import me.bausano.tsp.ProblemSolver.BruteForceCutSolver.BruteForceCutSolver;
import me.bausano.tsp.ProblemSolver.BruteForceSolver.BruteForceSolver;
import me.bausano.tsp.ProblemSolver.ProblemSolver;

public class Main {

    public static void main(String[] args) {
        introduction();

        // Prepares Eloquent class.
        InputParser parser = new PointDistanceParser();
        Eloquent eloquent = new Eloquent(parser);

        // Asks user for inputs.
        try {
            eloquent.requestAlgorithm();

            eloquent.requestData();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            System.exit(1);
        }

        Referee referee = new Referee();
        Algorithm algorithm = eloquent.getAlgorithm();
        Double[][] matrix = eloquent.getMap();
        ProblemSolver solver = matchSolver(algorithm);

        referee.start();
        Double path = solver.findShortestPath(matrix);
        referee.stop();

        System.out.printf("Problem shortest path %f has been found in %dms.",
                path, referee.getTime());
    }

    private static void introduction() {
        System.out.println("==== Travelling Salesman Problem ====");
        System.out.println("==== by Michael Bausano");
        System.out.println("==== In order to select an algorithm, write it's name in the cmd line.");
        System.out.println("==== Options:");
        System.out.println("==== BRUTE_FORCE");
        System.out.println("==== BRUTE_FORCE_CUT");
        System.out.println("==== BREACH_AND_BOUND");
        System.out.println("==== === === === === === === === ====");
        System.out.println("Afterwards, input the source of you file relative to current working directory.");
    }

    private static ProblemSolver matchSolver(Algorithm algorithm) {
        switch (algorithm) {
            case BRUTE_FORCE:
                return new BruteForceSolver();
            case BRUTE_FORCE_CUT:
                return new BruteForceCutSolver();
            case BREACH_AND_BOUND:
                return new BreachAndBoundSolver();
        }

        return null;
    }
}
