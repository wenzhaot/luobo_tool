package com.feng.car.entity.circle;

import com.feng.car.entity.BaseInfo;
import org.json.JSONObject;

public class CircleSubclassInfo extends BaseInfo {
    public String createtime = "";
    public int id = 0;
    public String name = "";
    public int type = 0;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("name")) {
                this.name = object.getString("name");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("type")) {
                this.type = object.getInt("type");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("createtime") && !object.isNull("createtime")) {
                this.createtime = object.getString("createtime");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
    }
}
