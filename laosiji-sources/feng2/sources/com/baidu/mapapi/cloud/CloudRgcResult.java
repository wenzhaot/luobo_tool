package com.baidu.mapapi.cloud;

import android.support.v4.app.NotificationCompat;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.util.CoordTrans;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudRgcResult {
    public AddressCompents addressCompents;
    public String customLocationDescription;
    public List<CloudPoiInfo> customPois;
    public String formattedAddress;
    public LatLng location;
    public String message;
    public List<PoiInfo> pois;
    public String recommendedLocationDescription;
    public int status;

    public class AddressCompents {
        public int adminAreaCode;
        public String city;
        public String country;
        public String countryCode;
        public String district;
        public String province;
        public String street;
        public String streetNumber;

        void a(JSONObject jSONObject) throws JSONException {
            if (jSONObject != null) {
                this.country = jSONObject.optString("country");
                this.province = jSONObject.optString("province");
                this.city = jSONObject.optString("city");
                this.district = jSONObject.optString("district");
                this.street = jSONObject.optString("street");
                this.streetNumber = jSONObject.optString("street_number");
                this.adminAreaCode = jSONObject.optInt("admin_area_code");
                this.countryCode = jSONObject.optString("country_code");
            }
        }
    }

    public class PoiInfo {
        public String address;
        public String direction;
        public int distance;
        public LatLng location;
        public String name;
        public String tag;
        public String uid;

        public void parseFromJSON(JSONObject jSONObject) throws JSONException {
            if (jSONObject != null) {
                this.name = jSONObject.optString("name");
                this.uid = jSONObject.optString("id");
                this.address = jSONObject.optString("address");
                this.tag = jSONObject.optString("tag");
                JSONObject optJSONObject = jSONObject.optJSONObject("location");
                if (optJSONObject != null) {
                    this.location = new LatLng(optJSONObject.optDouble(DispatchConstants.LATITUDE), optJSONObject.optDouble(DispatchConstants.LONGTITUDE));
                    if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                        this.location = CoordTrans.baiduToGcj(this.location);
                    }
                }
                this.direction = jSONObject.optString("direction");
                this.distance = jSONObject.optInt("distance");
            }
        }
    }

    public void parseFromJSON(JSONObject jSONObject) throws JSONException {
        int i = 0;
        try {
            this.status = jSONObject.optInt(NotificationCompat.CATEGORY_STATUS);
            this.message = jSONObject.optString("message");
            if (this.status == 6 || this.status == 7 || this.status == 8 || this.status == 9) {
                this.status = 1;
            }
            if (this.status == 0) {
                JSONObject optJSONObject = jSONObject.optJSONObject("location");
                if (optJSONObject != null) {
                    this.location = new LatLng(optJSONObject.optDouble(DispatchConstants.LATITUDE), optJSONObject.optDouble(DispatchConstants.LONGTITUDE));
                    if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                        this.location = CoordTrans.baiduToGcj(this.location);
                    }
                }
                optJSONObject = jSONObject.optJSONObject("address_component");
                if (optJSONObject != null) {
                    this.addressCompents = new AddressCompents();
                    this.addressCompents.a(optJSONObject);
                }
                this.formattedAddress = jSONObject.optString("formatted_address");
                JSONArray optJSONArray = jSONObject.optJSONArray("pois");
                if (optJSONArray != null) {
                    this.pois = new ArrayList();
                    for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                        JSONObject optJSONObject2 = optJSONArray.optJSONObject(i2);
                        if (optJSONObject2 != null) {
                            PoiInfo poiInfo = new PoiInfo();
                            poiInfo.parseFromJSON(optJSONObject2);
                            this.pois.add(poiInfo);
                        }
                    }
                }
                JSONArray optJSONArray2 = jSONObject.optJSONArray("custom_pois");
                if (optJSONArray2 != null) {
                    this.customPois = new ArrayList();
                    while (i < optJSONArray2.length()) {
                        JSONObject optJSONObject3 = optJSONArray2.optJSONObject(i);
                        if (optJSONObject3 != null) {
                            CloudPoiInfo cloudPoiInfo = new CloudPoiInfo();
                            cloudPoiInfo.b(optJSONObject3);
                            this.customPois.add(cloudPoiInfo);
                        }
                        i++;
                    }
                }
                this.customLocationDescription = jSONObject.optString("custom_location_description");
                this.recommendedLocationDescription = jSONObject.optString("recommended_location_description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
