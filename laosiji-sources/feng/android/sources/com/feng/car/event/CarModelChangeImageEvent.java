package com.feng.car.event;

import com.feng.car.entity.car.CarModelInfo;

public class CarModelChangeImageEvent {
    public static int TYPE_CAR_DEALER = 1;
    public static int TYPE_CAR_IMAGE_LSIT = 0;
    public static int TYPE_CAR_OWNERS_PRICES = 2;
    public CarModelInfo info;
    public int type;

    public CarModelChangeImageEvent(CarModelInfo info, int type) {
        this.info = info;
        this.type = type;
    }
}
