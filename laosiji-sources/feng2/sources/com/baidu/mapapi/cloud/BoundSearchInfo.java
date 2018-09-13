package com.baidu.mapapi.cloud;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.util.CoordTrans;

public class BoundSearchInfo extends BaseCloudSearchInfo {
    public String bound;

    public BoundSearchInfo() {
        if (HttpClient.isHttpsEnable) {
            this.a = "https://api.map.baidu.com/geosearch/v2/bound";
        } else {
            this.a = "http://api.map.baidu.com/geosearch/v2/bound";
        }
    }

    String a() {
        StringBuilder stringBuilder = new StringBuilder();
        if (super.a() == null) {
            return null;
        }
        stringBuilder.append(super.a());
        if (this.bound == null || this.bound.equals("")) {
            return null;
        }
        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
            try {
                String[] split = this.bound.split(";");
                String[] split2 = split[0].split(",");
                split = split[1].split(",");
                LatLng latLng = new LatLng(Double.parseDouble(split2[1]), Double.parseDouble(split2[0]));
                LatLng latLng2 = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                LatLng gcjToBaidu = CoordTrans.gcjToBaidu(latLng);
                latLng2 = CoordTrans.gcjToBaidu(latLng2);
                this.bound = gcjToBaidu.longitude + "," + gcjToBaidu.latitude + ";" + latLng2.longitude + "," + latLng2.latitude;
            } catch (Exception e) {
            }
        }
        stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        stringBuilder.append("bounds");
        stringBuilder.append("=");
        stringBuilder.append(this.bound);
        return stringBuilder.toString();
    }
}
