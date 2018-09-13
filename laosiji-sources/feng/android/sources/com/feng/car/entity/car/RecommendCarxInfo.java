package com.feng.car.entity.car;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class RecommendCarxInfo extends BaseInfo {
    public CarModelInfo carx = new CarModelInfo();
    public SnsInfo carxaudio = new SnsInfo();
    public int isrecommend = 0;
    public int paystatus = 0;

    public void parser(JSONObject object) {
        try {
            if (object.has(HttpConstant.CARX)) {
                this.carx.parser(object.getJSONObject(HttpConstant.CARX));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("carxaudio")) {
                this.carxaudio.parser(object.getJSONObject("carxaudio"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("paystatus")) {
                this.paystatus = object.getInt("paystatus");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("isrecommend")) {
                this.isrecommend = object.getInt("isrecommend");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
    }
}
