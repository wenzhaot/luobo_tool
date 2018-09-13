package com.feng.car.event;

import com.feng.car.entity.user.UserInfo;

public class UserInfoRefreshEvent {
    public static final int TYPE_FOLLOW = 2;
    public static final int TYPE_INFO = 1;
    public static final int TYPE_SEX = 3;
    public int type = 2;
    public UserInfo userInfo;

    public UserInfoRefreshEvent(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
