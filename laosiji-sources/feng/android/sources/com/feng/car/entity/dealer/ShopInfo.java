package com.feng.car.entity.dealer;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class ShopInfo extends BaseInfo {
    public int dealcount = 0;
    public int id = 0;
    public String position = "";
    public String realname = "";
    public int salecount = 0;
    public int saletype = 0;
    public String shopname = "";
    public int viewcount = 0;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.REAL_NAME)) {
                this.realname = object.getString(HttpConstant.REAL_NAME);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("shopname")) {
                this.shopname = object.getString("shopname");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("position")) {
                this.position = object.getString("position");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("viewcount")) {
                this.viewcount = object.getInt("viewcount");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("salecount")) {
                this.salecount = object.getInt("salecount");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("dealcount")) {
                this.dealcount = object.getInt("dealcount");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("saletype")) {
                this.saletype = object.getInt("saletype");
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
    }
}
