package me.bausano.tsp.Enum;

public enum Algorithm {
    BRUTE_FORCE(1),
    BRUTE_FORCE_CUT(2),
    BRANCH_AND_BOUND(3)
    ;

    final int algorithm;

    Algorithm(int algorithm) {
        this.algorithm = algorithm;
    }
}
