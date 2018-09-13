package com.feng.car.entity.car;

import android.text.TextUtils;
import com.feng.car.entity.BaseInfo;
import org.json.JSONObject;

public class DealerInfo extends BaseInfo {
    public double baidulat;
    public double baidulon;
    public String dealeraddress = "";
    public String dealerlinkman = "";
    public String dealermobile = "";
    private String dealername = "";
    public int id = 0;
    public double localDistance = 0.0d;
    public String localDistanceTips = "";
    public int ontop = 0;
    private String shortname = "";

    public String getShortname() {
        if (TextUtils.isEmpty(this.shortname)) {
            return this.dealername;
        }
        return this.shortname;
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
            if (object.has("dealername")) {
                this.dealername = object.getString("dealername");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("shortname")) {
                this.shortname = object.getString("shortname");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("dealeraddress")) {
                this.dealeraddress = object.getString("dealeraddress");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("dealermobile")) {
                this.dealermobile = object.getString("dealermobile");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("ontop")) {
                this.ontop = object.getInt("ontop");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("baidulon")) {
                this.baidulon = object.getDouble("baidulon");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("baidulat")) {
                this.baidulat = object.getDouble("baidulat");
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("dealerlinkman")) {
                this.dealerlinkman = object.getString("dealerlinkman");
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
    }
}
