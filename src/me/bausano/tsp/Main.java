package me.bausano.tsp;

public class Main {

    public static void main(String[] args) {
        // Program description here.

        try {
            inputParser.requestAlgorithm();

            inputParser.requestData();
        } catch (InvalidAlgorithmChoiceException e) {
            System.out.println("Invalid algorithm choice. Please refer to program description.");
        } catch (InvalidInputDataFormat e) {
            System.out.println("Input data are not in correct format. Please refer to coursework 1 sheet.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Referee referee = new Referee();
        Algorithm algorithm = inputParser.getAlgorithm();
        int[][] map = inputParser.getMap();
        ProblemSolver solver;

        switch (algorithm) {
            case Algorithm.BRUTE_FORCE:
                solver = new BruteForceSolver(referee);
                break;
        }

        solver.findShortestPath(map);

        System.out.print("Problem shortest path %d has been found in %dms.",
                referee.getPath(), referee.getTime());
    }
}
