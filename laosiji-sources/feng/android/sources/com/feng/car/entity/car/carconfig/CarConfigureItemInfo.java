package com.feng.car.entity.car.carconfig;

import com.feng.car.entity.BaseInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CarConfigureItemInfo extends BaseInfo {
    public List<CarConfigureInfo> confs = new ArrayList();
    public String name = "";

    public void parser(JSONObject object) {
        try {
            if (object.has("name")) {
                this.name = object.getString("name");
            }
        } catch (Exception e) {
        }
        try {
            if (object.has("confs")) {
                JSONArray jsonArray = object.getJSONArray("confs");
                int size = jsonArray.length();
                if (size > 0) {
                    this.confs.clear();
                    for (int i = 0; i < size; i++) {
                        CarConfigureInfo configsInfo = new CarConfigureInfo();
                        configsInfo.parser(jsonArray.getJSONObject(i));
                        this.confs.add(configsInfo);
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
