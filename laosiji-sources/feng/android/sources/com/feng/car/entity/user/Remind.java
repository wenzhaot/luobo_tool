package com.feng.car.entity.user;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class Remind extends BaseInfo {
    public int comment = 0;
    public int fins = 0;
    public int praise = 0;
    public int received = 0;
    public int system = 0;

    public void parser(JSONObject object) {
        try {
            if (object.has(HttpConstant.SYSTEM)) {
                this.system = object.getInt(HttpConstant.SYSTEM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.COMMENT)) {
                this.comment = object.getInt(HttpConstant.COMMENT);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("received")) {
                this.received = object.getInt("received");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.PRAISE)) {
                this.praise = object.getInt(HttpConstant.PRAISE);
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("fins")) {
                this.fins = object.getInt("fins");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
    }
}
