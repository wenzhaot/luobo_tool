package com.feng.car.event;

import com.feng.car.entity.sns.SnsInfo;

public class SnsInfoModifyEvent {
    public SnsInfo snsInfo;

    public SnsInfoModifyEvent(SnsInfo snsInfo) {
        this.snsInfo = snsInfo;
    }
}
