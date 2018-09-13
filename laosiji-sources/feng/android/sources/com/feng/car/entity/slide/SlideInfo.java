package com.feng.car.entity.slide;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class SlideInfo extends BaseInfo {
    public int id;
    public ImageInfo image = new ImageInfo();
    public int snsid;
    public int snstype;
    public int type;
    public String url;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.SNS_ID)) {
                this.snsid = object.getInt(HttpConstant.SNS_ID);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("snstype")) {
                this.snstype = object.getInt("snstype");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("type")) {
                this.type = object.getInt("type");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("url")) {
                this.url = object.getString("url");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("image") && !object.isNull("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
    }
}
