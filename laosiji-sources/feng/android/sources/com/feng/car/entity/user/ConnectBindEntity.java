package com.feng.car.entity.user;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.FengConstant;
import org.json.JSONObject;

public class ConnectBindEntity extends BaseInfo {
    public int login_5x = 0;
    public String logourl = "";
    public int qq = 0;
    public int weibo = 0;
    public int weixin = 0;

    public void parser(JSONObject object) {
        try {
            if (object.has(FengConstant.LOGIN_PLATFORM_QQ)) {
                this.qq = object.getInt(FengConstant.LOGIN_PLATFORM_QQ);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(FengConstant.LOGIN_PLATFORM_WEIXIN)) {
                this.weixin = object.getInt(FengConstant.LOGIN_PLATFORM_WEIXIN);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(FengConstant.LOGIN_PLATFORM_WEIBO)) {
                this.weibo = object.getInt(FengConstant.LOGIN_PLATFORM_WEIBO);
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("logourl")) {
                this.logourl = object.getString("logourl");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
    }
}
