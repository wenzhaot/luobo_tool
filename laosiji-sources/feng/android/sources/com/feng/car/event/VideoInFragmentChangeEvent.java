package com.feng.car.event;

public class VideoInFragmentChangeEvent {
    public static int FOLLOW_PAGE_SLIDE = 3;
    public static int HOME_PAGE_SLIDE = 1;
    public static int VEHIC_PAGE_SLIDE = 2;
    public int index;
    public int type;

    public VideoInFragmentChangeEvent(int type, int index) {
        this.type = type;
        this.index = index;
    }
}
