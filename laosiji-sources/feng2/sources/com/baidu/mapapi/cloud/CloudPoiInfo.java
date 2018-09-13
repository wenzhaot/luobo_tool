package com.baidu.mapapi.cloud;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.util.CoordTrans;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudPoiInfo {
    public String address;
    public String city;
    public String direction;
    public int distance;
    public String district;
    public Map<String, Object> extras;
    public int geotableId;
    public double latitude;
    public double longitude;
    public String poiId;
    public String province;
    public String tags;
    public String title;
    public int uid;
    public int weight;

    void a(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.uid = jSONObject.optInt("uid");
            this.poiId = jSONObject.optString("uid");
            jSONObject.remove("uid");
            this.geotableId = jSONObject.optInt("geotable_id");
            jSONObject.remove("geotable_id");
            this.title = jSONObject.optString("title");
            jSONObject.remove("title");
            this.address = jSONObject.optString("address");
            jSONObject.remove("address");
            this.province = jSONObject.optString("province");
            jSONObject.remove("province");
            this.city = jSONObject.optString("city");
            jSONObject.remove("city");
            this.district = jSONObject.optString("district");
            jSONObject.remove("district");
            JSONArray optJSONArray = jSONObject.optJSONArray("location");
            if (optJSONArray != null) {
                this.longitude = optJSONArray.optDouble(0);
                this.latitude = optJSONArray.optDouble(1);
                if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                    LatLng baiduToGcj = CoordTrans.baiduToGcj(new LatLng(this.latitude, this.longitude));
                    this.longitude = baiduToGcj.longitude;
                    this.latitude = baiduToGcj.latitude;
                }
            }
            jSONObject.remove("location");
            this.tags = jSONObject.optString("tags");
            jSONObject.remove("tags");
            this.distance = jSONObject.optInt("distance");
            jSONObject.remove("distance");
            this.weight = jSONObject.optInt("weight");
            jSONObject.remove("weight");
            this.extras = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                this.extras.put(str, jSONObject.opt(str));
            }
        }
    }

    void b(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.title = jSONObject.optString("name");
            this.address = jSONObject.optString("address");
            this.tags = jSONObject.optString("tag");
            JSONObject optJSONObject = jSONObject.optJSONObject("location");
            if (optJSONObject != null) {
                this.longitude = optJSONObject.optDouble(DispatchConstants.LONGTITUDE);
                this.latitude = optJSONObject.optDouble(DispatchConstants.LATITUDE);
                if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                    LatLng baiduToGcj = CoordTrans.baiduToGcj(new LatLng(this.latitude, this.longitude));
                    this.longitude = baiduToGcj.longitude;
                    this.latitude = baiduToGcj.latitude;
                }
            }
            this.direction = jSONObject.optString("direction");
            this.distance = jSONObject.optInt("distance");
        }
    }
}
