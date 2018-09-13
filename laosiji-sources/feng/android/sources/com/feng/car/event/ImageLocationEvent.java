package com.feng.car.event;

import com.feng.car.entity.model.LocationInfo;

public class ImageLocationEvent {
    public LocationInfo info;

    public ImageLocationEvent(LocationInfo info) {
        this.info = info;
    }
}
