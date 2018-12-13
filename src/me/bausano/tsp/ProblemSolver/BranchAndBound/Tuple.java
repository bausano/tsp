package me.bausano.tsp.ProblemSolver.BranchAndBound;

class Tuple {
    private final double reduction;
    private final double[][] matrix;

    Tuple(double arg1, double[][] arg2) {
        super();
        this.reduction = arg1;
        this.matrix = arg2;
    }

    double getReduction() {
        return reduction;
    }

    double[][] getMatrix() {
        return matrix;
    }
}
