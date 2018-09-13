package com.feng.car.event;

import com.feng.car.entity.sns.SnsInfo;

public class UserPointRefreshEvent {
    public static final int TYPE_USER_POINT_DELETE = 2002;
    public static final int TYPE_USER_POINT_SEND = 2001;
    public SnsInfo snsInfo;
    public int type;

    public UserPointRefreshEvent(int type, SnsInfo snsInfo) {
        this.snsInfo = snsInfo;
        this.type = type;
    }
}
