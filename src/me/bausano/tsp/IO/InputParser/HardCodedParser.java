package me.bausano.tsp.IO.InputParser;

import me.bausano.tsp.Exception.InvalidInputDataFormat;

import java.util.ArrayList;

public class HardCodedParser implements InputParser {
    @Override
    public double[][] matrixFromArray(ArrayList<String> lines) throws InvalidInputDataFormat {
        return matrixSix();
    }

    /**
     * Testing data.
     * Shortest path: 19.
     *
     * @return Matrix with testing data.
     */
    private double[][] matrixOne() {
        return new double[][]{
                {Double.POSITIVE_INFINITY, 1d, 11d, 3d, 3d, 6d},
                {1d, Double.POSITIVE_INFINITY, 7d, 4d, 2d, 5d},
                {11d, 7d, Double.POSITIVE_INFINITY, 10d, 5d, 8d},
                {3d, 4d, 10d, Double.POSITIVE_INFINITY, 1d, 10d},
                {3d, 2d, 5d, 1d, Double.POSITIVE_INFINITY, 7d},
                {6d, 5d, 8d, 10d, 7d, Double.POSITIVE_INFINITY}
        };
    }

    /**
     * Testing data.
     * Shortest path: 11.
     *
     * @return Matrix with testing data.
     */
    private double[][] matrixTwo() {
        return new double[][]{
                {Double.POSITIVE_INFINITY, 1d, 11d, 3d, 3d},
                {1d, Double.POSITIVE_INFINITY, 7d, 4d, 2d},
                {11d, 7d, Double.POSITIVE_INFINITY, 10d, 5d},
                {3d, 4d, 10d, Double.POSITIVE_INFINITY, 1d},
                {3d, 2d, 5d, 1d, Double.POSITIVE_INFINITY}
        };
    }

    /**
     * Testing data.
     * Shortest path: 18.
     *
     * @return Matrix with testing data.
     */
    private double[][] matrixThree() {
        return new double[][]{
                {Double.POSITIVE_INFINITY, 1d, 11d, 3d},
                {1d, Double.POSITIVE_INFINITY, 7d, 4d},
                {11d, 7d, Double.POSITIVE_INFINITY, 10d},
                {3d, 4d, 10d, Double.POSITIVE_INFINITY}
        };
    }

    /**
     * Testing data.
     * Shortest path: 25.
     *
     * @return Matrix with testing data.
     */
    private double[][] matrixFour() {
        return new double[][]{
                {Double.POSITIVE_INFINITY, 20d, 30d, 10d, 11d},
                {15d, Double.POSITIVE_INFINITY, 16d, 4d, 2d},
                {3d, 5d, Double.POSITIVE_INFINITY, 2d, 4d},
                {19d, 6d, 18d, Double.POSITIVE_INFINITY, 3d},
                {16d, 4d, 7d, 16d, Double.POSITIVE_INFINITY}
        };
    }

    /**
     * Testing data.
     * Shortest path: 27.
     *
     * @return Matrix with testing data.
     */
    private double[][] matrixFive() {
        return new double[][]{
                {Double.POSITIVE_INFINITY, 10d, 8d, 9d, 7d},
                {10d, Double.POSITIVE_INFINITY, 10d, 5d, 6d},
                {8d, 10d, Double.POSITIVE_INFINITY, 8d, 9d},
                {9d, 5d, 8d, Double.POSITIVE_INFINITY, 6d},
                {7d, 6d, 9d, 6d, Double.POSITIVE_INFINITY}
        };
    }

    /**
     * Testing data.
     * Shortest path: 11.
     *
     * @return Matrix with testing data.
     */
    private double[][] matrixSix() {
        return new double[][]{
                {Double.POSITIVE_INFINITY, 2d, 8d, 4d},
                {2d, Double.POSITIVE_INFINITY, 7d, 6d},
                {8d, 7d, Double.POSITIVE_INFINITY, 5d},
                {4d, 6d, 5d, Double.POSITIVE_INFINITY}
        };
    }
}
