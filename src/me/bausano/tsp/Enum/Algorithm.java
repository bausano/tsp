package me.bausano.tsp.Enum;

public enum Algorithm {
    BRUTE_FORCE(1),
    BRUTE_FORCE_CUT(2),
    BREACH_AND_BOUND(3)
    ;

    private final int algorithm;

    private Algorithm(int algorithm) {
        this.algorithm = algorithm;
    }
}
