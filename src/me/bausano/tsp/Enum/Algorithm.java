package me.bausano.tsp.Enum;

public enum Algorithm {
    DEPTH_FIRST(1),
    DEPTH_FIRST_CUT(2),
    BRANCH_AND_BOUND(3),
    BREADTH_FIRST_CUT(4)
    ;

    final int algorithm;

    Algorithm(int algorithm) {
        this.algorithm = algorithm;
    }
}
