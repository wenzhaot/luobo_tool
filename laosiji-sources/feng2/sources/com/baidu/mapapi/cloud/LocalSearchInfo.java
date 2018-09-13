package com.baidu.mapapi.cloud;

import anet.channel.request.Request;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.http.HttpClient;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LocalSearchInfo extends BaseCloudSearchInfo {
    public String region;

    public LocalSearchInfo() {
        if (HttpClient.isHttpsEnable) {
            this.a = "https://api.map.baidu.com/geosearch/v2/local";
        } else {
            this.a = "http://api.map.baidu.com/geosearch/v2/local";
        }
    }

    String a() {
        StringBuilder stringBuilder = new StringBuilder();
        if (super.a() == null) {
            return null;
        }
        stringBuilder.append(super.a());
        if (this.region == null || this.region.equals("") || this.region.length() > 25) {
            return null;
        }
        stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        stringBuilder.append("region");
        stringBuilder.append("=");
        try {
            stringBuilder.append(URLEncoder.encode(this.region, Request.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
