package me.bausano.tsp.IO;

public class Referee {
    private static Long start;

    private static Long end;

    public static void start() {
        start = System.nanoTime();
    }

    public static void stop() {
        end = System.nanoTime();
    }

    public static Long getTime() {
        return end - start;
    }

    public static Long getTimeSoFar() {
        return System.nanoTime() - start;
    }
}
