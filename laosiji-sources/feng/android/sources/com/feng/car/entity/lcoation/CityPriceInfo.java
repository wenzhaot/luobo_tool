package com.feng.car.entity.lcoation;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.car.PriceVo;
import org.json.JSONObject;

public class CityPriceInfo extends BaseInfo {
    public CityInfo city = new CityInfo();
    public PriceVo price = new PriceVo();

    public void parser(JSONObject object) {
        try {
            if (object.has("territoryvo")) {
                this.city.parser(object.getJSONObject("territoryvo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("carpricevo")) {
                this.price.parser(object.getJSONObject("carpricevo"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
