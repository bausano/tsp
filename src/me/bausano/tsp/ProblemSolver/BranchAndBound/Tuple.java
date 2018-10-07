package me.bausano.tsp.ProblemSolver.BranchAndBound;

class Tuple<Double> {
    private final Double reduction;
    private final Double[][] matrix;

    Tuple(Double arg1, Double[][] arg2) {
        super();
        this.reduction = arg1;
        this.matrix = arg2;
    }

    Double getReduction() {
        return reduction;
    }

    Double[][] getMatrix() {
        return matrix;
    }
}
