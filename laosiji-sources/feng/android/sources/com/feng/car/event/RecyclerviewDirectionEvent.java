package com.feng.car.event;

public class RecyclerviewDirectionEvent {
    public boolean directionUp = false;
    public boolean isHandOperation = false;

    public RecyclerviewDirectionEvent(boolean directionUp) {
        this.directionUp = directionUp;
    }

    public RecyclerviewDirectionEvent(boolean directionUp, boolean isHandOperation) {
        this.directionUp = directionUp;
        this.isHandOperation = isHandOperation;
    }
}
