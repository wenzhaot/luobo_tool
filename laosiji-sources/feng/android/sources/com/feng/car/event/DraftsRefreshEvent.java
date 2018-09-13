package com.feng.car.event;

import com.feng.car.entity.sns.SnsInfo;

public class DraftsRefreshEvent {
    public SnsInfo snsInfo;
    public String threadID;
    public int type;

    public DraftsRefreshEvent(String threadID, int type) {
        this.threadID = threadID;
        this.type = type;
    }

    public DraftsRefreshEvent(String threadID, int type, SnsInfo info) {
        this.threadID = threadID;
        this.type = type;
        this.snsInfo = info;
    }
}
