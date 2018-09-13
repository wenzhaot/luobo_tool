package com.feng.car.entity.dealer;

import android.databinding.ObservableBoolean;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class CommodityInfo extends BaseInfo {
    public String description = "";
    public int id = 0;
    public ImageInfo imageinfo = new ImageInfo();
    public transient ObservableBoolean local_select = new ObservableBoolean(false);
    public int originalprice = 0;
    public int price = 0;
    public int shopid = 0;
    public int status = 0;
    public String title = "";
    public int viewcount = 0;

    public CommodityInfo() {
        this.local_select.set(false);
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
            if (object.has("shopid")) {
                this.shopid = object.getInt("shopid");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("title")) {
                this.title = object.getString("title");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.DESCRIPTION)) {
                this.description = object.getString(HttpConstant.DESCRIPTION);
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.PRICE)) {
                this.price = object.getInt(HttpConstant.PRICE);
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("status")) {
                this.status = object.getInt("status");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("originalprice")) {
                this.originalprice = object.getInt("originalprice");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("viewcount")) {
                this.viewcount = object.getInt("viewcount");
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("imageinfo") && !object.isNull("imageinfo")) {
                this.imageinfo.parser(object.getJSONObject("imageinfo"));
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
    }

    public boolean equals(Object obj) {
        return this.id == ((CommodityInfo) obj).id;
    }
}
