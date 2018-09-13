package com.feng.car.entity.car.carconfig;

import com.feng.car.entity.BaseInfo;
import org.json.JSONObject;

public class CarConfigureInfo extends BaseInfo {
    public String item = "";
    public int local_type = 0;
    public int spec_id = 0;
    public String sub = "";
    public String value = "";

    public void parser(JSONObject object) {
        try {
            if (object.has("spec_id")) {
                this.spec_id = object.getInt("spec_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("value")) {
                this.value = object.getString("value");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("sub")) {
                this.sub = object.getString("sub");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("item")) {
                this.item = object.getString("item");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
    }
}
