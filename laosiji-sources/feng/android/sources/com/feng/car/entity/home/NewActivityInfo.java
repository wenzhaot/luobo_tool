package com.feng.car.entity.home;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class NewActivityInfo extends BaseInfo {
    public int award = 15;
    public CircleInfo community = new CircleInfo();
    public int endtime = 22;
    public int starttime = 0;
    public String url = "";

    public String getAward() {
        return "新人领现金" + this.award + "元";
    }

    public String getAward2() {
        return "领" + this.award + "元";
    }

    public void parser(JSONObject object) {
        try {
            if (object.has(HttpConstant.COMMUNITY)) {
                this.community.parser(object.getJSONObject(HttpConstant.COMMUNITY));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("url")) {
                this.url = object.getString("url");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("starttime")) {
                this.starttime = object.getInt("starttime");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("endtime")) {
                this.endtime = object.getInt("endtime");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("award")) {
                this.award = object.getInt("award");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
    }
}
