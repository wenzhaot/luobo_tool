package com.feng.car.entity.car;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CarSeriesInfo extends BaseInfo {
    public int autodiscusscount = 0;
    public int commoditycount = 0;
    public CircleInfo communityinfo = new CircleInfo();
    private int dealernum = 0;
    public CarFactoryInfo factory = new CarFactoryInfo();
    public int guidecount = 0;
    public int hasautovoice = 0;
    public int id;
    public ImageInfo image = new ImageInfo();
    public final ObservableInt imagecount = new ObservableInt(0);
    public final ObservableInt isfavorite = new ObservableInt(0);
    public String level = "";
    public transient int local_flag = 0;
    public transient List<CarModelInfo> local_models = new ArrayList();
    public int maxprice = 0;
    public int minprice = 0;
    public final ObservableField<String> name = new ObservableField("");
    public int posfirstflag = 0;
    public int poslastflag = 0;
    public double preferential = 0.0d;
    public int pricecount = 0;
    public int speccount;
    public ArrayList<Integer> speclist = new ArrayList();
    public int state = 0;

    public String getPricecount() {
        return "全国 " + this.pricecount + "条成交价";
    }

    public String getPriceCountSubject() {
        return this.pricecount + "条成交价";
    }

    public String getGoodsCountSubject() {
        return this.commoditycount + "台在售";
    }

    public String getSpecCountSubject() {
        return this.speccount + "款车型";
    }

    public String getAutoDiscussCountSubject() {
        return this.autodiscusscount + "篇";
    }

    public String getDealerCountSubject() {
        return getDealernum() + "家";
    }

    public String getImageCountSubject() {
        return this.imagecount.get() + "张";
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

    public String getPreferentialNew() {
        StringBuilder sb = new StringBuilder();
        if (this.preferential == 100.0d) {
            sb.append("无优惠");
        } else if (this.preferential == 0.0d) {
            sb.append("暂无成交价");
        } else {
            if (this.preferential - 100.0d > 0.0d) {
                sb.append("加价");
            } else {
                sb.append("优惠");
            }
            sb.append(new BigDecimal(Math.abs(this.preferential - 100.0d)).setScale(2, 5));
            sb.append("%");
        }
        return sb.toString();
    }

    public String getAverageText() {
        if (this.preferential - 100.0d > 0.0d) {
            return "近期加价";
        }
        return "近期优惠";
    }

    public int getDealernum() {
        if (this.state == 40) {
            return 0;
        }
        return this.dealernum;
    }

    public String getCarPrice() {
        if (this.minprice == 0 && this.maxprice == 0) {
            return "暂无指导价";
        }
        if (this.minprice <= 0 || this.maxprice <= 0 || this.minprice == this.maxprice) {
            return FengUtil.numberFormatCar((double) Math.max(this.minprice, this.maxprice));
        }
        String minPriceStr = FengUtil.numberFormatCar((double) this.minprice);
        if (minPriceStr.indexOf("万") > 0) {
            minPriceStr = minPriceStr.replace("万", "");
        }
        return minPriceStr + "-" + FengUtil.numberFormatCar((double) this.maxprice);
    }

    public String getCarPriceText() {
        if (this.state == 10) {
            return "预售价：";
        }
        return "指导价: ";
    }

    public String getCarPriceText2() {
        if (this.state == 10) {
            return "预售价";
        }
        return "指导价";
    }

    public CarSeriesInfo() {
        this.name.set("");
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
                this.name.set(object.getString("name"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
            if (TextUtils.isEmpty(this.image.url)) {
                this.image.url = "res://com.feng.car/2130838367";
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("isfavorite")) {
                this.isfavorite.set(object.getInt("isfavorite"));
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("imagecount")) {
                this.imagecount.set(object.getInt("imagecount"));
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("minprice")) {
                this.minprice = object.getInt("minprice");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("maxprice")) {
                this.maxprice = object.getInt("maxprice");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("state")) {
                this.state = object.getInt("state");
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("hasautovoice")) {
                this.hasautovoice = object.getInt("hasautovoice");
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("level") && !object.isNull("level")) {
                this.level = object.getString("level");
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (object.has("factory") && !object.isNull("factory")) {
                this.factory.parser(object.getJSONObject("factory"));
            }
        } catch (Exception e2222222222) {
            e2222222222.printStackTrace();
        }
        try {
            if (object.has("speccount")) {
                this.speccount = object.getInt("speccount");
            }
        } catch (Exception e22222222222) {
            e22222222222.printStackTrace();
        }
        try {
            if (object.has("autodiscusscount")) {
                this.autodiscusscount = object.getInt("autodiscusscount");
            }
        } catch (Exception e222222222222) {
            e222222222222.printStackTrace();
        }
        try {
            if (object.has("communityinfo") && !object.isNull("communityinfo")) {
                this.communityinfo.parser(object.getJSONObject("communityinfo"));
            }
        } catch (Exception e2222222222222) {
            e2222222222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.SPECLIST)) {
                JSONArray jsonArray = object.getJSONArray(HttpConstant.SPECLIST);
                int size = jsonArray.length();
                this.speclist.clear();
                for (int i = 0; i < size; i++) {
                    this.speclist.add(Integer.valueOf(jsonArray.getInt(i)));
                }
            }
        } catch (Exception e22222222222222) {
            e22222222222222.printStackTrace();
        }
        try {
            if (object.has("guidecount") && !object.isNull("guidecount")) {
                this.guidecount = object.getInt("guidecount");
            }
        } catch (Exception e222222222222222) {
            e222222222222222.printStackTrace();
        }
        try {
            if (object.has("dealernum")) {
                this.dealernum = object.getInt("dealernum");
            }
        } catch (Exception e2222222222222222) {
            e2222222222222222.printStackTrace();
        }
        try {
            if (object.has("commoditycount")) {
                this.commoditycount = object.getInt("commoditycount");
            }
        } catch (Exception e22222222222222222) {
            e22222222222222222.printStackTrace();
        }
        try {
            if (object.has("pricecount")) {
                this.pricecount = object.getInt("pricecount");
            }
        } catch (Exception e222222222222222222) {
            e222222222222222222.printStackTrace();
        }
        try {
            if (object.has("preferential")) {
                this.preferential = object.getDouble("preferential");
            }
        } catch (Exception e2222222222222222222) {
            e2222222222222222222.printStackTrace();
        }
    }
}
