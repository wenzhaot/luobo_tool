package com.feng.car.event;

import com.qiniu.android.dns.NetworkInfo;

public class RechargeEvent {
    public static final int CANCEL = 3;
    public static final int FAIL = 0;
    public static final int ORDER_CLOSE = 4;
    public static final int SUCCESS = 1;
    public static final int UNKNOW = 2;
    public int errCode = NetworkInfo.ISP_OTHER;
    public String errDesc = "";
    public int status = -1;

    public RechargeEvent(int status) {
        this.status = status;
    }

    public RechargeEvent(int status, int errCode, String errDesc) {
        this.status = status;
        this.errCode = errCode;
        this.errDesc = errDesc;
    }
}
