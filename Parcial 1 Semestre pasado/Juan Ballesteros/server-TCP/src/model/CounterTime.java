package model;

import exception.TimeException;

public class CounterTime {
    
    private long startTime;
    private long stopTime;

    public CounterTime() {
    }

    public void startTime(){
        startTime = System.currentTimeMillis();
    }

    public void stopTime(){
        stopTime = System.currentTimeMillis();
    }

    public long getTime() throws TimeException{
        if(startTime == 0 || stopTime == 0){
            throw new TimeException();
        } else {
            return (stopTime - startTime)/1000;
        }
    }
}
