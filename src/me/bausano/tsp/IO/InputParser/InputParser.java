package me.bausano.tsp.IO.InputParser;

import me.bausano.tsp.Exception.InvalidInputDataFormat;

import java.util.ArrayList;

public interface InputParser {
    public Double[][] matrixFromArray(ArrayList<String> lines) throws InvalidInputDataFormat;
}
