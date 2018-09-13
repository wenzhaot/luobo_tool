package com.feng.car.event;

import com.feng.car.entity.ImageInfo;

public class RequestImageLocationEvent {
    public String imageHash = "";
    public ImageInfo imageInfo;
    public int position = 0;

    public RequestImageLocationEvent(int pos, String hash) {
        this.position = pos;
        this.imageHash = hash;
    }

    public RequestImageLocationEvent(int position, String imageHash, ImageInfo imageInfo) {
        this.position = position;
        this.imageHash = imageHash;
        this.imageInfo = imageInfo;
    }
}
