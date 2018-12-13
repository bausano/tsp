package me.bausano.tsp.ProblemSolver.BreadthFirstCut;

import me.bausano.tsp.ProblemSolver.ProblemSolver;

import java.util.*;

public class Solver implements ProblemSolver {

    /**
     * Symmetric matrix with distances.
     */
    private double[][] matrix;

    /**
     * Upper bound starts with INFINITY.
     */
    private double upper = Double.MAX_VALUE;

    /**
     * Breadth First approach to Travelling Salesman problem with backtracking.
     *
     * O(n!)
     *
     * @param matrix Matrix of distances between the cities.
     *
     * @return Minimum distance one has to travel in order to visit all points.
     */
    @Override
    public double findShortestPath(double[][] matrix) {
        this.matrix = matrix;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node root = new Node(0);
        queue.add(root);

        search(queue);

        return upper;
    }

    /**
     * Searches given matrix.
     *
     * @param queue Sorted queue of nodes.
     */
    private void search(PriorityQueue<Node> queue) {
        Node parent;

        while ((parent = queue.poll()) != null) {
            if (parent.getCost() > upper) {
                continue;
            }

            List<Integer> descendants = parent.getDescendants(matrix.length);
            if (descendants.size() == 0) {
                double cost = parent.getCost() + matrix[parent.getIndex()][0];
                if (cost < upper) {
                    this.upper = cost;
                }

                continue;
            }

            spawnChildren(parent, descendants, queue);
        }
    }

    /**
     * Spawns children to parent
     *
     * @param parent Node that should be extended.
     * @param descendants List of children indexes.
     * @param queue Queue where should the children be added to.
     */
    private void spawnChildren(Node parent, List<Integer> descendants, Queue<Node> queue) {
        for (int descendant : descendants) {
            double cost = parent.getCost() + matrix[parent.getIndex()][descendant];

            if (upper < cost) continue;

            queue.add(new Node(descendant, cost, parent));
        }
    }
}
