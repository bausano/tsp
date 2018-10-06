package me.bausano.tsp.IO;

import me.bausano.tsp.Enum.Algorithm;
import me.bausano.tsp.Exception.InvalidAlgorithmChoiceException;
import me.bausano.tsp.IO.InputParser.InputParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Eloquent {
    /**
     * Computes the distances from file lines.
     */
    private InputParser parser;

    /**
     * Chosen algorithm.
     */
    private Algorithm algorithm;

    /**
     * Matrix of distances between the cities.
     */
    private Double[][] matrix;

    /**
     * Class constructor.
     *
     * @param parser Parses the lines from a file into a matrix.
     */
    public Eloquent (InputParser parser) {
        this.parser = parser;
    }

    /**
     * Lets user select the algorithm which is going to solve the problem.
     *
     * @throws InvalidAlgorithmChoiceException Is thrown if the algorithm doesn't exist.
     */
    public void requestAlgorithm() throws InvalidAlgorithmChoiceException {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine().trim();

        try {
            algorithm = Algorithm.valueOf(input);
        } catch (Exception e) {
            throw new InvalidAlgorithmChoiceException();
        }
    }

    /**
     * Asks for data from user and attempts to parse them.
     *
     * @throws Exception Any exception with input data.
     */
    public void requestData() throws Exception {
        Scanner scanner = new Scanner(System.in);

        String path = scanner.nextLine().trim();
        ArrayList<String> lines = parseFile(path);

        matrix = parser.matrixFromArray(lines);
    }

    /**
     * @return Chosen algorithm.
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Double[][] getMap() {
        return matrix;
    }

    /**
     * Reads file line by line.
     *
     * @param path File path relative to the working directory.
     *
     * @return List of lines.
     *
     * @throws Exception Any IO Exception.
     */
    private ArrayList<String> parseFile(String path) throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        String line;

        FileReader fileReader = new FileReader(path);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
            lines.add(line.trim());
        }

        bufferedReader.close();

        return lines;
    }
}
