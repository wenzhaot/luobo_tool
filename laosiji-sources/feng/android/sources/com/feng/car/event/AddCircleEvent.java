package com.feng.car.event;

import com.feng.car.entity.circle.CircleInfo;
import java.util.List;

public class AddCircleEvent {
    public static int ATTENTION_CANCEL_CIRCLE_TYPE = 3;
    public static int MULTI_CIRCLE_TYPE = 2;
    public static int SINGLE_CIRCLE_TYPE = 1;
    public CircleInfo info;
    public List<CircleInfo> list;
    public int type = SINGLE_CIRCLE_TYPE;

    public AddCircleEvent(CircleInfo info) {
        this.info = info;
        this.type = SINGLE_CIRCLE_TYPE;
    }

    public AddCircleEvent(List<CircleInfo> list) {
        this.list = list;
        this.type = MULTI_CIRCLE_TYPE;
    }

    public AddCircleEvent(int type, CircleInfo info) {
        this.info = info;
        this.type = type;
    }
}
