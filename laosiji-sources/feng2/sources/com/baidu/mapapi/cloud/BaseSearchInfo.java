package com.baidu.mapapi.cloud;

import anet.channel.strategy.dispatch.DispatchConstants;

public abstract class BaseSearchInfo {
    String a;
    public String ak;
    public int geoTableId;
    public String sn;

    String a() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.a);
        stringBuilder.append("?");
        if (this.ak == null || this.ak.equals("") || this.ak.length() > 50) {
            return null;
        }
        stringBuilder.append("ak");
        stringBuilder.append("=");
        stringBuilder.append(this.ak);
        if (this.geoTableId == 0) {
            return null;
        }
        stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        stringBuilder.append("geotable_id");
        stringBuilder.append("=");
        stringBuilder.append(this.geoTableId);
        if (!(this.sn == null || this.sn.equals("") || this.sn.length() > 50)) {
            stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            stringBuilder.append("sn");
            stringBuilder.append("=");
            stringBuilder.append(this.sn);
        }
        return stringBuilder.toString();
    }
}
