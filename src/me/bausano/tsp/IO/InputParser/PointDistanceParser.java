package me.bausano.tsp.IO.InputParser;

import me.bausano.tsp.Exception.InvalidInputDataFormat;

import java.util.ArrayList;

public class PointDistanceParser implements InputParser {
    /**
     * Aggressively parses string to matrix of distances.
     *
     * @param lines List of lines read from a file.
     *
     * @return Matrix of distances.
     *
     * @throws InvalidInputDataFormat If the file format isn't correct, fails.
     */
    public double[][] matrixFromArray(ArrayList<String> lines) throws InvalidInputDataFormat {
        try {
            double[][] points = new double[lines.size()][2];

            for (int i = 0; i < lines.size(); i++) {
                String[] raw = lines.get(i).split(" ");
                // Gets the x and y coordinate of a city.
                points[i] = new double[]{Double.parseDouble(raw[1]), Double.parseDouble(raw[2])};
            }

            return pointsToMatrix(points);
        } catch (Exception e) {
            throw new InvalidInputDataFormat();
        }
    }

    /**
     * Transforms a list of points into a matrix of distances.
     *
     * @param points List of s representing cities.
     *
     * @return Matrix of distances.
     */
    private double[][] pointsToMatrix(double[][] points) {
        double[][] matrix = new double[points.length][points.length];
        for (int k = 0; k < points.length; k++) {
            for (int j = k; j < points.length; j++) {
                if (j == k) {
                    matrix[j][j] = Double.POSITIVE_INFINITY;
                    continue;
                }

                Double distance = calcDistance(points[k], points[j]);
                matrix[j][k] = distance;
                matrix[k][j] = distance;
            }
        }

        return matrix;
    }

    /**
     * Calculates the distance of two points based on pythagorean theorem.
     *
     * @param a First point.
     * @param b Second point.
     *
     * @return Distance between two points in cartesian coordinate system.
     */
    private double calcDistance(double[] a, double[] b) {
        return Math.hypot(a[0] - b[0], a[1] - b[1]);
    }
}
