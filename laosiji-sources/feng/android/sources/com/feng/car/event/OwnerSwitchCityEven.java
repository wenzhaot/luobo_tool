package com.feng.car.event;

import com.feng.car.entity.lcoation.CityInfo;

public class OwnerSwitchCityEven {
    public int carModelID = 0;
    public int carSeriesID = 0;
    public CityInfo cityInfo;

    public OwnerSwitchCityEven(CityInfo cityInfo, int carSeriesID, int carModelID) {
        this.cityInfo = cityInfo;
        this.carSeriesID = carSeriesID;
        this.carModelID = carModelID;
    }
}
