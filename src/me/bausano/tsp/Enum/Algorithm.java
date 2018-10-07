package me.bausano.tsp.Enum;

public enum Algorithm {
    DEPTH_FIRST(1),
    DEPTH_FIRST_CUT(2),
    BREADTH_FIRST_CUT(3),
    BRANCH_AND_BOUND(4),
    BRANCH_AND_BOUND_WITH_NEIGHBOUR(6),
    DYNAMIC_PROGRAMMING(7)
    ;

    final int algorithm;

    Algorithm(int algorithm) {
        this.algorithm = algorithm;
    }
}
