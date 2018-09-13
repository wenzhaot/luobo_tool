package com.feng.car.entity.dealer;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.HttpConstant;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CommodityServeInfo extends BaseInfo {
    @Expose
    public List<String> contents = new ArrayList();
    @Expose
    public String price = "";

    public void parser(JSONObject object) {
        try {
            if (object.has(HttpConstant.PRICE)) {
                this.price = object.getString(HttpConstant.PRICE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("contents")) {
                this.contents.clear();
                JSONArray jsonArray = object.getJSONArray("contents");
                int size = jsonArray.length();
                for (int i = 0; i < size; i++) {
                    this.contents.add(jsonArray.getString(i));
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
