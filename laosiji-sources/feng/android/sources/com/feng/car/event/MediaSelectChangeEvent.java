package com.feng.car.event;

import com.feng.car.entity.model.ImageVideoInfo;

public class MediaSelectChangeEvent {
    public ImageVideoInfo info;
    public int nextState = 0;

    public MediaSelectChangeEvent(ImageVideoInfo info) {
        this.info = info;
    }

    public MediaSelectChangeEvent(int nextState, ImageVideoInfo info) {
        this.info = info;
        this.nextState = nextState;
    }
}
