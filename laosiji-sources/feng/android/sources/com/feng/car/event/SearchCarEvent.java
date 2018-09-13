package com.feng.car.event;

public class SearchCarEvent {
    public static final int BRAND_TYPE = 1;
    public static final int LEVEL_TYPE = 3;
    public static final int MORE_TYPE = 4;
    public static final int PRICE_TYPE = 2;
    public int type = -1;

    public SearchCarEvent(int type) {
        this.type = type;
    }
}
