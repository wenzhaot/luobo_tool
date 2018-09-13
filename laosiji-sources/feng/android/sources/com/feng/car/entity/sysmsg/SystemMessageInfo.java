package com.feng.car.entity.sysmsg;

import com.feng.car.entity.BaseInfo;
import org.json.JSONObject;

public class SystemMessageInfo extends BaseInfo {
    public String data;
    public String time;

    public void parser(JSONObject object) {
        try {
            if (object.has("data")) {
                this.data = object.getString("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("time")) {
                this.time = object.getString("time");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
