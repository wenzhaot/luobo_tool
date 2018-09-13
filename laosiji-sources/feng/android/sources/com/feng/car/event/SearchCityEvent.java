package com.feng.car.event;

import com.feng.car.entity.lcoation.CityInfo;

public class SearchCityEvent {
    public CityInfo cityInfo;

    public SearchCityEvent(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }
}
