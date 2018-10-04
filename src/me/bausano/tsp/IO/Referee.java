package me.bausano.tsp.IO;

import java.util.Date;

public class Referee {
    private Long start;

    private Long end;

    public void start() {
        start = (new Date()).getTime();
    }

    public void stop() {
        end = (new Date()).getTime();
    }

    public Long getTime() {
        return end - start;
    }
}
