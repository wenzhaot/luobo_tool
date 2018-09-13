package com.feng.car.entity.car;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class AvgPriceVo extends BaseInfo {
    public CarModelInfo carx = new CarModelInfo();
    public int cityid;
    public int id = 0;
    public int insurancevehicletax;
    public double price;
    public int pricetype;
    public double totalprice;
    public int vehiclepurchasetax;

    public void reset() {
        this.vehiclepurchasetax = 0;
        this.insurancevehicletax = 0;
        this.insurancevehicletax = 0;
        this.totalprice = 0.0d;
        this.pricetype = 0;
        this.price = 0.0d;
        this.cityid = 131;
    }

    public void parser(JSONObject object) {
        try {
            if (object.has(HttpConstant.CITYID)) {
                this.cityid = object.getInt(HttpConstant.CITYID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.CARX) && !object.isNull(HttpConstant.CARX)) {
                this.carx.parser(object.getJSONObject(HttpConstant.CARX));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("vehiclepurchasetax")) {
                this.vehiclepurchasetax = object.getInt("vehiclepurchasetax");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("insurancevehicletax")) {
                this.insurancevehicletax = object.getInt("insurancevehicletax");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.TOTALPRICE)) {
                this.totalprice = object.getDouble(HttpConstant.TOTALPRICE);
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("pricetype")) {
                this.pricetype = object.getInt("pricetype");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.PRICE)) {
                this.price = object.getDouble(HttpConstant.PRICE);
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
    }

    public String getPriceStringTwo() {
        if (this.price > 0.0d) {
            return FengUtil.numberFormatCar(this.price);
        }
        return "暂无";
    }

    public String getPriceString() {
        if (this.price > 0.0d) {
            return FengUtil.numberFormatCar(this.price);
        }
        return "暂无";
    }

    public String getTotalPriceString() {
        if (this.totalprice > 0.0d) {
            return FengUtil.numberFormatCar(this.totalprice);
        }
        return "暂无";
    }
}
