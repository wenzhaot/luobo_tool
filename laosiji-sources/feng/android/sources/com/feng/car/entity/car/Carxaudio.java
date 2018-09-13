package com.feng.car.entity.car;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.HttpConstant;
import java.text.DecimalFormat;
import org.json.JSONObject;

public class Carxaudio extends BaseInfo {
    public int carsid = 0;
    public int carxid = 0;
    public String descn;
    public int forwardcount;
    public String hash;
    public int id = 0;
    public int islisten;
    public int ismy;
    public int ispraise;
    public int listenercount;
    public String orderprice;
    public int paystatus;
    public int playcount;
    public long playtime;
    public int praisecount;
    public String realprice;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.CARS_ID)) {
                this.carsid = object.getInt(HttpConstant.CARS_ID);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.CARX_ID)) {
                this.carxid = object.getInt(HttpConstant.CARX_ID);
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("forwardcount")) {
                this.forwardcount = object.getInt("forwardcount");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("orderprice")) {
                this.orderprice = new DecimalFormat("######0.00").format(object.getDouble("orderprice"));
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("realprice")) {
                this.realprice = new DecimalFormat("######0.00").format(object.getDouble("realprice"));
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("descn")) {
                this.descn = object.getString("descn");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("playcount")) {
                this.playcount = object.getInt("playcount");
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("listenercount")) {
                this.listenercount = object.getInt("listenercount");
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("praisecount")) {
                this.praisecount = object.getInt("praisecount");
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (object.has("playtime")) {
                this.playtime = object.getLong("playtime");
            }
        } catch (Exception e2222222222) {
            e2222222222.printStackTrace();
        }
        try {
            if (Double.valueOf(this.realprice).doubleValue() <= 0.0d) {
                this.paystatus = 0;
            } else if (object.has("paystatus")) {
                this.paystatus = object.getInt("paystatus");
            }
        } catch (Exception e3) {
        }
        try {
            if (object.has("ispraise")) {
                this.ispraise = object.getInt("ispraise");
            }
        } catch (Exception e4) {
        }
        try {
            if (object.has("islisten")) {
                this.islisten = object.getInt("islisten");
            }
        } catch (Exception e5) {
        }
        try {
            if (object.has("ismy")) {
                this.ismy = object.getInt("ismy");
                if (this.ismy == 1) {
                    this.paystatus = 0;
                }
            }
        } catch (Exception e6) {
        }
        try {
            if (object.has("hash")) {
                this.hash = object.getString("hash");
            }
        } catch (Exception e7) {
        }
    }
}
