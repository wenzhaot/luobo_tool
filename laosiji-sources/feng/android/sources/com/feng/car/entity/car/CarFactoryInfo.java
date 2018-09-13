package com.feng.car.entity.car;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import org.json.JSONObject;

public class CarFactoryInfo extends BaseInfo {
    public int factoryPosition = 0;
    public int id;
    public ImageInfo image = new ImageInfo();
    public String name;

    public void parser(JSONObject object) {
        try {
            if (object.has("id") && !object.isNull("id")) {
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
            this.name = "";
        }
        try {
            if (object.has("image") && !object.isNull("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
    }
}
