package com.feng.car.entity.car.carconfig;

import com.feng.car.entity.BaseInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CarConfigureParent extends BaseInfo {
    public List<CarConfigureItemInfo> items = new ArrayList();
    public int spec_id = 0;
    public String spec_name = "";

    public void parser(JSONObject object) {
        try {
            if (object.has("spec_id")) {
                this.spec_id = object.getInt("spec_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("spec_name")) {
                this.spec_name = object.getString("spec_name");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("items")) {
                JSONArray jsonArray = object.getJSONArray("items");
                int size = jsonArray.length();
                if (size > 0) {
                    this.items.clear();
                    for (int i = 0; i < size; i++) {
                        CarConfigureItemInfo configsInfo = new CarConfigureItemInfo();
                        configsInfo.parser(jsonArray.getJSONObject(i));
                        this.items.add(configsInfo);
                    }
                }
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
    }
}
