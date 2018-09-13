package com.feng.car.entity.car;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class SeriesPriceVo extends BaseInfo {
    public CarModelInfo carx = new CarModelInfo();
    public int cityid;
    public String dealtime;
    public int dealtype;
    public int id;
    public boolean isOtherCity = false;
    public double price;
    public String remark;
    public double totalprice;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.PRICE)) {
                this.price = object.getDouble(HttpConstant.PRICE);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.CARX)) {
                this.carx.parser(object.getJSONObject(HttpConstant.CARX));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.CITYID)) {
                this.cityid = object.getInt(HttpConstant.CITYID);
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
            if (object.has(HttpConstant.DEALTYPE)) {
                this.dealtype = object.getInt(HttpConstant.DEALTYPE);
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.REMARK)) {
                this.remark = object.getString(HttpConstant.REMARK);
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.DEALTIME)) {
                this.dealtime = object.getString(HttpConstant.DEALTIME);
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
    }

    public String getPriceString() {
        if (this.price > 0.0d) {
            return FengUtil.numberFormatCar(this.price);
        }
        return "车主未提供";
    }

    public String getTotalPriceString() {
        if (this.totalprice > 0.0d) {
            return FengUtil.numberFormatCar(this.totalprice);
        }
        return "车主未提供";
    }

    public String getDealtypeString() {
        if (this.dealtype == 1) {
            return "贷款";
        }
        if (this.dealtype == 2) {
            return "全款";
        }
        return "车主未提供";
    }
}
