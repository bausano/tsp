package me.bausano.tsp.IO;

import java.util.Date;

public class Referee {
    private Long start;

    private Long end;

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        end = System.nanoTime();
    }

    public Long getTime() {
        return (end - start) / 1000000;
    }
}
