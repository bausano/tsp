package me.bausano.tsp.IO.InputParser;

import me.bausano.tsp.Exception.InvalidInputDataFormat;

import java.util.ArrayList;

public class HardCodedParser implements InputParser {
    @Override
    public Double[][] matrixFromArray(ArrayList<String> lines) throws InvalidInputDataFormat {
        return matrixThree();
    }

    /**
     * Testing data.
     * Shortest path: 19.
     *
     * @return Matrix with testing data.
     */
    private Double[][] matrixOne() {
        return new Double[][]{
                {-1d, 1d, 11d, 3d, 3d, 6d},
                {1d, -1d, 7d, 4d, 2d, 5d},
                {11d, 7d, -1d, 10d, 5d, 8d},
                {3d, 4d, 10d, -1d, 1d, 10d},
                {3d, 2d, 5d, 1d, -1d, 7d},
                {6d, 5d, 8d, 10d, 7d, -1d}
        };
    }

    /**
     * Testing data.
     * Shortest path: 11.
     *
     * @return Matrix with testing data.
     */
    private Double[][] matrixTwo() {
        return new Double[][]{
                {-1d, 1d, 11d, 3d, 3d},
                {1d, -1d, 7d, 4d, 2d},
                {11d, 7d, -1d, 10d, 5d},
                {3d, 4d, 10d, -1d, 1d},
                {3d, 2d, 5d, 1d, -1d}
        };
    }

    /**
     * Testing data.
     * Shortest path: 18.
     *
     * @return Matrix with testing data.
     */
    private Double[][] matrixThree() {
        return new Double[][]{
                {-1d, 1d, 11d, 3d},
                {1d, -1d, 7d, 4d},
                {11d, 7d, -1d, 10d},
                {3d, 4d, 10d, -1d}
        };
    }

    /**
     * Testing data.
     * Shortest path: 25.
     *
     * @return Matrix with testing data.
     */
    private Double[][] matrixFour() {
        return new Double[][]{
                {-1d, 20d, 30d, 10d, 11d},
                {15d, -1d, 16d, 4d, 2d},
                {3d, 5d, -1d, 2d, 4d},
                {19d, 6d, 18d, -1d, 3d},
                {16d, 4d, 7d, 16d, -1d}
        };
    }

    /**
     * Testing data.
     * Shortest path: 27.
     *
     * @return Matrix with testing data.
     */
    private Double[][] matrixFive() {
        return new Double[][]{
                {-1d, 10d, 8d, 9d, 7d},
                {10d, -1d, 10d, 5d, 6d},
                {8d, 10d, -1d, 8d, 9d},
                {9d, 5d, 8d, -1d, 6d},
                {7d, 6d, 9d, 6d, -1d}
        };
    }
}
