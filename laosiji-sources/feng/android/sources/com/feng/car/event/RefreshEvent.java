package com.feng.car.event;

import com.feng.car.entity.car.CarModelInfo;

public class RefreshEvent {
    public static final int REFRESH_BATCH_FOLLOW = 2;
    public static final int REFRESH_CAR_CONFIGURATION_WEB = 1;
    public static final int REFRESH_CAR_CONFIGURATION_WEB_DEL = 3;
    public CarModelInfo carModelInfo;
    public int type = 0;

    public RefreshEvent(int type) {
        this.type = type;
    }

    public RefreshEvent(int type, CarModelInfo carModelInfo) {
        this.type = type;
        this.carModelInfo = carModelInfo;
    }
}
