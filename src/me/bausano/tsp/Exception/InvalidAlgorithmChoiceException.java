package me.bausano.tsp.Exception;

public class InvalidAlgorithmChoiceException extends TravellingSalesmanException {
    @Override
    public String getMessage() {
        return "Invalid algorithm choice. Please refer to the program description.";
    }
}
