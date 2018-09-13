package com.feng.car.event;

public class ReadyToScrollEvent {
    public int carModelID;
    public int carSeriesID;

    public ReadyToScrollEvent(int mCarSeriesID, int mCarModelID) {
        this.carSeriesID = mCarSeriesID;
        this.carModelID = mCarModelID;
    }
}
