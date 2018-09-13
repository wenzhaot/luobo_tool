package com.feng.car.entity.car;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class CarModelInfo extends BaseInfo {
    public double avgprice;
    public String engine = "";
    public double guideprice;
    public int id;
    public int imagecount = 0;
    public int isconfig = 0;
    public boolean localshowyear = false;
    public String name = "";
    public int ownernum = 0;
    public int posfirstflag;
    public int poslastflag;
    public int state = 0;
    public String year = "";

    public boolean hasGuideprice() {
        return this.guideprice > 0.0d;
    }

    public String getGuidePrice() {
        if (hasGuideprice()) {
            return FengUtil.numberFormatCar(this.guideprice);
        }
        return "暂无";
    }

    public String getAvgPrice() {
        if (this.avgprice <= 0.0d) {
            return "暂无";
        }
        return FengUtil.numberFormatCar(this.avgprice);
    }

    public String getOwnerNum() {
        return this.ownernum + "条成交价";
    }

    public String getCarPriceText() {
        if (this.state == 10) {
            return "预售价：";
        }
        return "指导价：";
    }

    public String getCarPriceText2() {
        if (this.state == 10) {
            return "预售价";
        }
        return "指导价";
    }

    public String getYear() {
        return this.year + "款";
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
            if (object.has("name")) {
                this.name = object.getString("name");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.YEAR)) {
                this.year = object.getString(HttpConstant.YEAR);
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("guideprice")) {
                this.guideprice = object.getDouble("guideprice");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("avgprice")) {
                this.avgprice = object.getDouble("avgprice");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("state")) {
                this.state = object.getInt("state");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("isconfig")) {
                this.isconfig = object.getInt("isconfig");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("engine")) {
                this.engine = object.getString("engine");
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("imagecount")) {
                this.imagecount = object.getInt("imagecount");
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("ownernum")) {
                this.ownernum = object.getInt("ownernum");
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
    }

    public boolean equals(Object obj) {
        return this.id == ((CarModelInfo) obj).id;
    }
}
