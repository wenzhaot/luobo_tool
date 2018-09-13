package com.feng.car.event;

import com.feng.car.entity.sns.SnsInfo;

public class UserDisLikeEvent {
    public SnsInfo snsInfo;

    public UserDisLikeEvent(SnsInfo snsInfo) {
        this.snsInfo = snsInfo;
    }
}
