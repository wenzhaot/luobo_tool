package com.feng.car.event;

import com.feng.car.entity.sns.SnsInfo;

public class SendDelArticleSuccess {
    public static final int DEL_TYPE = 2;
    public static final int SEND_TYPE = 1;
    public SnsInfo snsInfo;
    public int type = 1;

    public SendDelArticleSuccess(int type, SnsInfo snsInfo) {
        this.type = type;
        this.snsInfo = snsInfo;
    }
}
