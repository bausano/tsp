package me.bausano.tsp.ProblemSolver.DynamicProgramming;

import me.bausano.tsp.ProblemSolver.ProblemSolver;

import java.util.*;

public class Solver implements ProblemSolver {
    /**
     * Matrix of distances.
     */
    private double[][] matrix;

    /**
     * Number of nodes.
     */
    private int cities;

    /**
     * State of an integer when all cities have been visited;
     */
    private int fullCycle;

    /**
     * Reference matrix where we will store computed costs.
     */
    private double[][] ref;

    /**
     * Dynamic programming approach to Travelling Salesman problem.
     * To make this as effective as possible, I will be using bit operations.
     * Obviously, I didn't come up with this algorithm myself over the weekend.
     * I have watched some youtube videos and started creating first version using a hashed map.
     * Halfway through coding my solution, I have found this video:
     * https://www.youtube.com/watch?v=cY4HiiFHO1o
     * With an incredibly neat way to store the information in a matrix.
     * I have referred to the video and came up with the main computing loop, however I have struggled
     * to create a correct algorithm that would generate all variations of the paths.
     * My attempts so far were all imperfect, missing subsets of possible paths.
     * I have fixed the code using the video.
     *
     * O(n*n*2^n)
     *
     * @param matrix Matrix of distances between the cities.
     *
     * @return Minimum distance one has to travel in order to visit all points.
     */
    @Override
    public double findShortestPath(double[][] matrix) {
        this.matrix = matrix;

        // Seeds reference matrix where we are going to store already computed paths.
        seedReferenceMatrix();

        // We have already preseeded matrix with paths from 0 - each node.
        // Therefore we start from 3 (in other words, computing solutions for 3 nodes).
        for (int paths = 3; paths <= cities; paths++) {
            computeVariations(paths).forEach(this::calculateCost);
        }

        return findMinimumPath();
    }

    /**
     * Init function that preseeds a reference matrix with all vertexes coming out of the
     * starting node 0.
     */
    private void seedReferenceMatrix() {
        this.cities = matrix.length;
        // 1 << n is equivalent to 2^n.
        // TODO: Check what happens when n >= 31.
        this.fullCycle = 1 << matrix.length;

        ref = new double[cities][fullCycle];

        for (int i = 1; i < cities; i++) {
            // We set the first bit and i-th bit to 1.
            ref[i][1 | (1 << i)] = matrix[0][i];
        }
    }

    private List<Integer> computeVariations(int variations) {
        List<Integer> list = new ArrayList<>();

        computeVariations(0, 0, variations, list);

        return list;
    }

    private void computeVariations(int source, int pos, int variations, List<Integer> list) {
        if (cities - pos < variations) return;

        if (variations == 0) {
            list.add(source);

            return;
        }

        for (int i = pos; i < cities; i++) {
            source |= 1 << i;
            computeVariations(source, i + 1, variations - 1, list);
            source &= ~(1 << i);
        }
    }

    /**
     * Calculates costs for path by checking all places it can be visited from and putting the cost as the
     * minimum of those paths.
     * The formula is:
     * cost(k) = min(cost(j) + step(j, k), ...)
     * Where j is any set not containing k.
     *
     * @param path A binary path encoded in an integer.
     */
    private void calculateCost(Integer path) {
        // TODO: There is an easy way to avoid this condition if we generate less variations and prepend them with 1.
        if ((1 & path) == 0) return;
        for (int city = 1; city < cities; city++) {
            // Checks if the city has been visited in
            if (((1 << city) & path) == 0) continue;

            // This is the neatest line of the whole algorithm.
            // We basically select the column that is the same as current path,
            // WITH one bit different: the index of the current city.
            // Hence we can say that we can get to the current path from this path
            // AND it has been already computed for us, so we avoid wasting the resources.
            int visitingFrom = path ^ (1 << city);
            // Now we select the minimum value from the column.
            Double min = Double.MAX_VALUE;
            for (int neighbour = 1; neighbour < cities; neighbour++) {
                // We have to skip any city that has not been visited yet in current path.
                if (neighbour == city || ((1 << neighbour) & path) == 0) continue;

                // And we add already computed cost from reference matrix to the cost of the step
                // of getting from a neighbour to the current city (or vice-verca, it does not matter as the
                // input is going to be Euclidean TSP).
                min = Math.min(min, ref[neighbour][visitingFrom] + matrix[neighbour][city]);
            }

            ref[city][path] = min;
        }
    }

    /**
     * Connects each row's last element to a starting node and returns the minimum value of all.
     * This value is also the optimal solution to TSP.
     *
     * @return Optimal solution.
     */
    private double findMinimumPath() {
        double shortest = Double.MAX_VALUE;

        for (int i = 1; i < cities; i++) {
            shortest = Math.min(shortest, ref[i][fullCycle - 1] + matrix[i][0]);
        }

        System.out.println("Shortest found path:");
        System.out.println(backtracePath());

        return shortest;
    }

    /**
     * Back-traces the shortest path.
     *
     * @return Ordered list of city indices.
     */
    private List<Integer> backtracePath() {
        // Now we have to rebuild the path by starting in city 0.
        int last = 0;
        int state = this.fullCycle - 1;

        List<Integer> path = new ArrayList<>();
        // We start at city with index 0.
        path.add(0);

        for (int i = 1; i < cities; i++) {
            // Reset the index.
            int index = -1;
            for (int j = 0; j < cities; j++) {
                // If this is the starting city or the city has already been visited, skip.
                if (j == 0 || ((1 << j) & state) == 0) continue;
                // If this is the first city we're visiting from city i, it is the city with
                // the shortest path (because there hasn't been any other city visited yet).
                if (index == -1) index = j;
                // If new distance is shortest then previous best found, set the index to city j.
                index = ref[index][state] + matrix[index][last] < ref[j][state] + matrix[j][last] ? j : index;
            }

            path.add(index);
            // Mark city "index" as travelled.
            state = state ^ (1 << index);
            last = index;
        }

        // In order to make full cycle, we have to finish at the same city we started in.
        path.add(0);

        return path;
    }
}
