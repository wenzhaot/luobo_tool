package com.feng.car.entity.sns;

import com.google.gson.annotations.Expose;
import com.meizu.cloud.pushsdk.constants.PushConstants;

public class SnsRecommendRecord {
    public static final int RECOMMEND_ALREADY_BROWSE = 15;
    public static final int RECOMMEND_ALREADY_CLICK = 20;
    @Expose
    public String feedstatus = "5";
    @Expose
    public String feedtype = "20";
    @Expose
    public String snsid = PushConstants.PUSH_TYPE_NOTIFY;

    public boolean equals(Object obj) {
        return this.snsid.equals(((SnsRecommendRecord) obj).snsid);
    }
}
