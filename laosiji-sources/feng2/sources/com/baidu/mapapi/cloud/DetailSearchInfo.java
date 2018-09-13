package com.baidu.mapapi.cloud;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.http.HttpClient;

public class DetailSearchInfo extends BaseSearchInfo {
    public String poiId;
    public int uid;

    public DetailSearchInfo() {
        if (HttpClient.isHttpsEnable) {
            this.a = "https://api.map.baidu.com/geosearch/v2/detail/";
        } else {
            this.a = "http://api.map.baidu.com/geosearch/v2/detail/";
        }
    }

    String a() {
        if (this.uid == 0 && (this.poiId == null || this.poiId.equals(""))) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.a);
        if (this.poiId == null || this.poiId.equals("")) {
            stringBuilder.append(this.uid).append('?');
        } else {
            stringBuilder.append(this.poiId).append('?');
        }
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
