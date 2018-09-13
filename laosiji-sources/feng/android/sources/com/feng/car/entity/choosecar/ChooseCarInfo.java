package com.feng.car.entity.choosecar;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.utils.HttpConstant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChooseCarInfo extends BaseInfo {
    public List<CarSeriesInfo> cars = new ArrayList();
    public int id;
    public ImageInfo image = new ImageInfo();
    public transient int localtype = 0;
    public String publishtime = "";
    public String title = "";
    public int totaldiscusscount = 0;
    public int totalreadcount = 0;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("totaldiscusscount")) {
                this.totaldiscusscount = object.getInt("totaldiscusscount");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("totalreadcount")) {
                this.totalreadcount = object.getInt("totalreadcount");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("publishtime")) {
                this.publishtime = object.getString("publishtime");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("title")) {
                this.title = object.getString("title");
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
        try {
            if (object.has(HttpConstant.CARS)) {
                JSONArray jsonArray = object.getJSONArray(HttpConstant.CARS);
                int size = jsonArray.length();
                this.cars.clear();
                for (int i = 0; i < size; i++) {
                    CarSeriesInfo carSeriesInfo = new CarSeriesInfo();
                    carSeriesInfo.parser(jsonArray.getJSONObject(i));
                    this.cars.add(carSeriesInfo);
                }
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
    }
}
