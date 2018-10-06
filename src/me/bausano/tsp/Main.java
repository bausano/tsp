package me.bausano.tsp;

import me.bausano.tsp.Enum.Algorithm;
import me.bausano.tsp.IO.Eloquent;
import me.bausano.tsp.IO.InputParser.HardCodedParser;
import me.bausano.tsp.IO.InputParser.InputParser;
import me.bausano.tsp.IO.InputParser.PointDistanceParser;
import me.bausano.tsp.IO.Referee;
import me.bausano.tsp.ProblemSolver.BranchAndBoundSolver.BranchAndBoundSolver;
import me.bausano.tsp.ProblemSolver.BreadthFirstCut.BreadthFirstCut;
import me.bausano.tsp.ProblemSolver.DepthFirstCutSolver.DepthFirstCutSolver;
import me.bausano.tsp.ProblemSolver.DepthFirstSolver.DepthFirstSolver;
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

            System.out.print(System.getProperty("user.dir") + "/");
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

        System.out.printf("Shortest path %f has been found in %dms.",
                path, referee.getTime());
    }

    private static void introduction() {
        System.out.println("==== Travelling Salesman Problem ====");
        System.out.println("==== by Michael Bausano");
        System.out.println("==== In order to select an algorithm, write it's name in the cmd line.");
        System.out.println("==== Options:");
        System.out.println("==== DEPTH_FIRST");
        System.out.println("==== DEPTH_FIRST_CUT");
        System.out.println("==== BRANCH_AND_BOUND");
        System.out.println("==== BREADTH_FIRST_CUT");
        System.out.println("==== === === === === === === === ====");
        System.out.println("Afterwards, input the source of you file relative to current working directory.");
    }

    private static ProblemSolver matchSolver(Algorithm algorithm) {
        switch (algorithm) {
            case DEPTH_FIRST:
                return new DepthFirstSolver();
            case DEPTH_FIRST_CUT:
                return new DepthFirstCutSolver();
            case BRANCH_AND_BOUND:
                return new BranchAndBoundSolver();
            case BREADTH_FIRST_CUT:
                return new BreadthFirstCut();
        }

        return null;
    }
}
