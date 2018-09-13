package com.feng.car.entity.lcoation;

import android.text.TextUtils;
import com.feng.car.entity.BaseInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class ProvinceInfo extends BaseInfo {
    public int id;
    public String name;

    public void parser(JSONObject object) {
        if (object.has("id")) {
            try {
                this.id = object.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (object.has("name")) {
            try {
                this.name = cityNameFormat(object.getString("name"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private String cityNameFormat(String cityName) {
        if (!TextUtils.isEmpty(cityName) && cityName.lastIndexOf("市") == cityName.length() - 1) {
            return cityName.substring(0, cityName.length() - 1);
        }
        return cityName;
    }
}
