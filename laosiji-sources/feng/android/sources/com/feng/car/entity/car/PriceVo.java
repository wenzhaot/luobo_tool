package com.feng.car.entity.car;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.FengUtil;
import java.math.BigDecimal;
import org.json.JSONObject;

public class PriceVo extends BaseInfo {
    public double maxprice;
    public double minprice;
    public int ownernum;
    public double ownerprice;
    public double preferential;
    public int rank = 0;

    public boolean isPreferential() {
        return this.preferential - 100.0d < 0.0d;
    }

    public boolean hasPreferential() {
        return this.preferential != 0.0d;
    }

    public String getOwernum() {
        return this.ownernum + "条成交价";
    }

    public String getOwnerPrice() {
        if (this.ownerprice <= 0.0d) {
            return "暂无";
        }
        return FengUtil.numberFormatCar(this.ownerprice);
    }

    public String getOwnerPriceNew() {
        if (this.ownerprice <= 0.0d) {
            return "暂无成交价";
        }
        return FengUtil.numberFormatCarNew(this.ownerprice);
    }

    public String getOwnerPriceNew2() {
        if (this.ownerprice <= 0.0d) {
            return "暂无成交价";
        }
        return FengUtil.numberFormatCar(this.ownerprice);
    }

    public String getOwnerPriceTip() {
        if (this.ownerprice <= 0.0d) {
            return "";
        }
        return "成交价";
    }

    public String getPreferentialTip() {
        if (this.preferential - 100.0d > 0.0d) {
            return "加价";
        }
        if (this.preferential == 0.0d) {
            return "";
        }
        return "优惠";
    }

    public String getRank() {
        return "全国排名第" + this.rank;
    }

    public String getPreferentialText() {
        if (this.preferential == 0.0d) {
            return "近期优惠/加价";
        }
        if (this.preferential - 100.0d > 0.0d) {
            return "近期加价";
        }
        return "近期优惠";
    }

    public String getPreferentialTextNew() {
        if (this.preferential == 0.0d) {
            return "暂无成交价";
        }
        if (this.preferential == 100.0d) {
            return "优惠";
        }
        if (this.preferential - 100.0d > 0.0d) {
            return "加价";
        }
        return "优惠";
    }

    public String getPreferentialTextNew2() {
        if (this.preferential == 0.0d) {
            return "暂无成交价";
        }
        if (this.preferential == 100.0d) {
            return "无优惠";
        }
        if (this.preferential - 100.0d > 0.0d) {
            return "加价";
        }
        return "优惠";
    }

    public String getPreferentialForNew() {
        StringBuilder sb = new StringBuilder();
        if (this.preferential == 100.0d) {
            sb.append("无优惠");
        } else if (this.preferential == 0.0d) {
            sb.append("暂无成交价");
        } else {
            sb.append(new BigDecimal(Math.abs(this.preferential - 100.0d)).setScale(2, 5));
        }
        return sb.toString();
    }

    public String getPreferential() {
        StringBuilder sb = new StringBuilder();
        if (this.preferential == 100.0d) {
            sb.append("无优惠");
        } else if (this.preferential == 0.0d) {
            sb.append("暂无");
        } else {
            sb.append(new BigDecimal(Math.abs(this.preferential - 100.0d)).setScale(2, 5));
            sb.append("%");
        }
        return sb.toString();
    }

    public String getPreferentialDetail() {
        StringBuilder sb = new StringBuilder();
        if (this.preferential == 0.0d) {
            sb.append("");
        } else if (this.preferential == 100.0d) {
            sb.append("0%");
        } else {
            sb.append(new BigDecimal(Math.abs(this.preferential - 100.0d)).setScale(2, 5));
            sb.append("%");
        }
        return sb.toString();
    }

    public String getPreferentialDetail2() {
        StringBuilder sb = new StringBuilder();
        if (this.preferential == 0.0d) {
            sb.append("");
        } else if (this.preferential == 100.0d) {
            sb.append("");
        } else {
            sb.append(new BigDecimal(Math.abs(this.preferential - 100.0d)).setScale(2, 5));
            sb.append("%");
        }
        return sb.toString();
    }

    public String getCarsOwnerPrice() {
        StringBuilder sb = new StringBuilder();
        sb.append(FengUtil.numberFormatCar(this.minprice));
        sb.append("-");
        sb.append(FengUtil.numberFormatCar(this.maxprice));
        return sb.toString();
    }

    public boolean hasCarxOwnerPrice() {
        return this.ownerprice > 0.0d;
    }

    public String getCarxOwnerPrice() {
        if (hasCarxOwnerPrice()) {
            return FengUtil.numberFormatCar(this.ownerprice);
        }
        return "暂无";
    }

    public void parser(JSONObject object) {
        try {
            if (object.has("preferential")) {
                this.preferential = object.getDouble("preferential");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("minprice")) {
                this.minprice = object.getDouble("minprice");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("maxprice")) {
                this.maxprice = object.getDouble("maxprice");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("ownerprice")) {
                this.ownerprice = object.getDouble("ownerprice");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("ownernum")) {
                this.ownernum = object.getInt("ownernum");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("rank")) {
                this.rank = object.getInt("rank");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
    }
}
