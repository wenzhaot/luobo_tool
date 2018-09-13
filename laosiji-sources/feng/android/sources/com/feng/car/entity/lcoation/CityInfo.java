package com.feng.car.entity.lcoation;

import android.text.TextUtils;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.car.PriceVo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class CityInfo extends BaseInfo {
    public String abc;
    public int id;
    public String initial;
    public String name;
    public String pinyin;
    public int posflag;
    public PriceVo priceVo = new PriceVo();
    public int provinceid;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.PROVINCEID)) {
                this.provinceid = object.getInt(HttpConstant.PROVINCEID);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("initial")) {
                this.initial = object.getString("initial");
                this.abc = this.initial.toUpperCase();
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("name")) {
                this.name = cityNameFormat(object.getString("name"));
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("pinyin")) {
                this.pinyin = object.getString("pinyin");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
    }

    private String cityNameFormat(String cityName) {
        if (!TextUtils.isEmpty(cityName) && cityName.lastIndexOf("市") == cityName.length() - 1) {
            return cityName.substring(0, cityName.length() - 1);
        }
        return cityName;
    }
}
