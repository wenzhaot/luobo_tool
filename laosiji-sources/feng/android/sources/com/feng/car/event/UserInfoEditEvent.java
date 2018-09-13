package com.feng.car.event;

import com.feng.car.entity.user.UserInfo;

public class UserInfoEditEvent {
    public UserInfo mInfo = new UserInfo();

    public UserInfoEditEvent(UserInfo userInfo) {
        if (userInfo != null) {
            this.mInfo.name.set(userInfo.name.get());
            this.mInfo.signature.set(userInfo.signature.get());
        }
    }
}
