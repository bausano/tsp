package me.bausano.tsp.IO;

import me.bausano.tsp.Enum.Algorithm;
import me.bausano.tsp.Exception.InvalidAlgorithmChoiceException;
import me.bausano.tsp.IO.InputParser.InputParser;

public class Eloquent {
    private InputParser parser;

    public Eloquent (InputParser parser) {
        this.parser = parser;
    }

    public void requestAlgorithm() throws InvalidAlgorithmChoiceException {

    }

    public void requestData() throws Exception {

    }

    public Algorithm getAlgorithm() {
        return Algorithm.BRUTE_FORCE;
    }

    public int[][] getMap() {
        return new int[1][1];
    }
}
