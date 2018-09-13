package com.feng.car.event;

public class NewSearchSwitchEvent {
    public static final int SEARCH_TOPIC_TYPE = 2;
    public static final int SEARCH_USER_TYPE = 5;
    public int type = 0;

    public NewSearchSwitchEvent(int type) {
        this.type = type;
    }
}
