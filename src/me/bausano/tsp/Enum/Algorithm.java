package me.bausano.tsp.Enum;

public enum Algorithm {
    BRUTE_FORCE(1)
    ;

    private final int algorithm;

    private Algorithm(int algorithm) {
        this.algorithm = algorithm;
    }
}
