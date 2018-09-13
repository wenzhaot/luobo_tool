package com.feng.car.entity.wallet;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class WalletDetailInfo extends BaseInfo {
    public double award;
    public String createtime;
    public String info;

    public String getAwardString() {
        return "¥" + Math.abs(this.award);
    }

    public void parser(JSONObject object) {
        try {
            if (object.has("award")) {
                this.award = object.getDouble("award");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("createtime")) {
                this.createtime = object.getString("createtime");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.INFO)) {
                this.info = object.getString(HttpConstant.INFO);
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
    }
}
