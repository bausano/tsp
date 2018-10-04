package me.bausano.tsp.IO;

import java.util.Date;

public class Referee {
    private Double path;

    private Long start;

    private Long end;

    public void setPath(Double path) {
        this.path = path;
    }

    public Double getPath() {
        return path;
    }

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
