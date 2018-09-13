package com.feng.car.event;

public class UploadPriceSelectEvent {
    public int id;
    public String imageUrl = "";
    public String name;
    public int type = 1;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UploadPriceSelectEvent(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
