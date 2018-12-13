package me.bausano.tsp;

import me.bausano.tsp.Enum.Algorithm;
import me.bausano.tsp.IO.Eloquent;
import me.bausano.tsp.IO.InputParser.HardCodedParser;
import me.bausano.tsp.IO.InputParser.InputParser;
import me.bausano.tsp.IO.InputParser.PointDistanceParser;
import me.bausano.tsp.IO.Referee;
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

        Algorithm algorithm = eloquent.getAlgorithm();
        double[][] matrix = eloquent.getMap();
        ProblemSolver solver = matchSolver(algorithm);

        try {
            Referee.start();
            double cost = solver.findShortestPath(matrix);
            Referee.stop();

            System.out.printf("Shortest path of %f has been found in %dns.",
                    cost, Referee.getTime());
        } catch (Exception e) {
            System.out.println("Shortest path could not be found. Reason:");
            throw e;
        }
    }

    private static void introduction() {
        System.out.println("==== Travelling Salesman Problem ====");
        System.out.println("==== by Michael Bausano");
        System.out.println("==== In order to select an algorithm, write it's name in the cmd line.");
        System.out.println("==== Default algorithm is BRANCH_AND_BOUND_WITH_NEIGHBOUR");
        System.out.println("==== Options:");
        System.out.println("==== DEPTH_FIRST");
        System.out.println("==== DEPTH_FIRST_CUT");
        System.out.println("==== BREADTH_FIRST_CUT");
        System.out.println("==== BRANCH_AND_BOUND");
        System.out.println("==== BRANCH_AND_BOUND_WITH_NEIGHBOUR");
        System.out.println("==== DYNAMIC_PROGRAMMING");
        System.out.println("==== === === === === === === === ====");
        System.out.println("Afterwards, input the source of you file relative to current working directory.");
    }

    private static ProblemSolver matchSolver(Algorithm algorithm) {
        switch (algorithm) {
            case DEPTH_FIRST:
                System.out.println("You have selected DEPTH_FIRST. " +
                        "This algorithm is experimental and should not be used for marking");
                return new me.bausano.tsp.ProblemSolver.DepthFirst.Solver();
            case DEPTH_FIRST_CUT:
                System.out.println("You have selected DEPTH_FIRST_CUT. " +
                        "This algorithm is experimental and should not be used for marking");
                return new me.bausano.tsp.ProblemSolver.DepthFirstCut.Solver();
            case BRANCH_AND_BOUND:
                System.out.println("You have selected BRANCH_AND_BOUND. " +
                        "This algorithm is experimental and should not be used for marking");
                return new me.bausano.tsp.ProblemSolver.BranchAndBound.Solver();
            case BREADTH_FIRST_CUT:
                System.out.println("You have selected BREADTH_FIRST_CUT. " +
                        "This algorithm is experimental and should not be used for marking");
                return new me.bausano.tsp.ProblemSolver.BreadthFirstCut.Solver();
            case DYNAMIC_PROGRAMMING:
                System.out.println("You have selected DYNAMIC_PROGRAMMING.");
                return new me.bausano.tsp.ProblemSolver.DynamicProgramming.Solver();
            case BRANCH_AND_BOUND_WITH_NEIGHBOUR:
                System.out.println("You have selected BRANCH_AND_BOUND_WITH_NEIGHBOUR.");
                return new me.bausano.tsp.ProblemSolver.BranchAndBoundWithNeighbour.Solver();
        }

        return null;
    }
}
