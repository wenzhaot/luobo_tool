package com.feng.car.entity.home;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class HomeLabelInfo extends BaseInfo {
    public int categoryid = 0;
    public int communityid = 0;
    public int id = 0;
    public int ishighlight = 0;
    public String name = "";

    public HomeLabelInfo(int id, String name, int ishighlight, int categoryid, int communityid) {
        this.id = id;
        this.name = name;
        this.ishighlight = ishighlight;
        this.categoryid = categoryid;
        this.communityid = communityid;
    }

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
            if (object.has("ishighlight")) {
                this.ishighlight = object.getInt("ishighlight");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.CATEGORYID)) {
                this.categoryid = object.getInt(HttpConstant.CATEGORYID);
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.COMMUNITY_ID)) {
                this.communityid = object.getInt(HttpConstant.COMMUNITY_ID);
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
    }
}
