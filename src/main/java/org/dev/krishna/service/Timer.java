package org.dev.krishna.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class Timer {
    private LocalDateTime startTime;
    public Timer(){
        this.startTime = LocalDateTime.now();
    }
    public String readTimer(){
        LocalDateTime endTime = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
        long seconds = ChronoUnit.SECONDS.between(startTime, endTime);
        return " [ Time taken : " + minutes + " minutes " + seconds + " nanoseconds ] ";
    }
}
