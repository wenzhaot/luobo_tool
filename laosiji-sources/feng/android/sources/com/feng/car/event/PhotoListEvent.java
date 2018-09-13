package com.feng.car.event;

public class PhotoListEvent {
    private int carPhotoType;

    public PhotoListEvent(int carPhotoType) {
        this.carPhotoType = carPhotoType;
    }

    public int getCarPhotoType() {
        return this.carPhotoType;
    }
}
