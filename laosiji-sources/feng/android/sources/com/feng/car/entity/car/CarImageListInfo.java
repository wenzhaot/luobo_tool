package com.feng.car.entity.car;

import com.feng.car.entity.ImageInfo;
import org.json.JSONObject;

public class CarImageListInfo {
    public ImageInfo p1920 = new ImageInfo();
    public ImageInfo p240 = new ImageInfo();
    public ImageInfo p640 = new ImageInfo();

    public void parser(JSONObject object) {
        try {
            if (object.has("p240")) {
                this.p240.parser(object.getJSONObject("p240"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("p640")) {
                this.p640.parser(object.getJSONObject("p640"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("p1920")) {
                this.p1920.parser(object.getJSONObject("p1920"));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
    }
}
