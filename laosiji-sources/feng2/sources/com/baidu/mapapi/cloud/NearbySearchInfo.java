package com.baidu.mapapi.cloud;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.util.CoordTrans;

public class NearbySearchInfo extends BaseCloudSearchInfo {
    public String location;
    public int radius;

    public NearbySearchInfo() {
        if (HttpClient.isHttpsEnable) {
            this.a = "https://api.map.baidu.com/geosearch/v2/nearby";
        } else {
            this.a = "http://api.map.baidu.com/geosearch/v2/nearby";
        }
        this.radius = 1000;
    }

    String a() {
        StringBuilder stringBuilder = new StringBuilder();
        if (super.a() == null) {
            return null;
        }
        stringBuilder.append(super.a());
        if (this.location == null || this.location.equals("")) {
            return null;
        }
        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
            String[] split = this.location.split(",");
            try {
                LatLng gcjToBaidu = CoordTrans.gcjToBaidu(new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0])));
                this.location = gcjToBaidu.longitude + "," + gcjToBaidu.latitude;
            } catch (Exception e) {
            }
        }
        stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        stringBuilder.append("location");
        stringBuilder.append("=");
        stringBuilder.append(this.location);
        if (this.radius >= 0) {
            stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            stringBuilder.append("radius");
            stringBuilder.append("=");
            stringBuilder.append(this.radius);
        }
        return stringBuilder.toString();
    }
}
