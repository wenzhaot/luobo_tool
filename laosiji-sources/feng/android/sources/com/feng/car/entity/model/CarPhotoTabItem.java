package com.feng.car.entity.model;

public class CarPhotoTabItem {
    public int carModelId = 0;
    public int carSeriesId = 0;
    public String title = "";
    public String year = "";

    public CarPhotoTabItem(String title, int carSeriesId, int carModelId, String year) {
        this.carSeriesId = carSeriesId;
        this.carModelId = carModelId;
        this.year = year;
        this.title = title;
    }
}
