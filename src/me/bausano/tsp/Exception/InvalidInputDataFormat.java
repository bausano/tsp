package me.bausano.tsp.Exception;

public class InvalidInputDataFormat extends TravellingSalesmanException {
    @Override
    public String getMessage() {
        return "Input data are not in correct format. Please refer to coursework 1 sheet.";
    }
}
