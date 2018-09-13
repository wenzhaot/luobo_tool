package com.feng.car.entity.search;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class AssociatedSearchInfo extends BaseInfo {
    public HotShowInfo hotshow = new HotShowInfo();
    public CarSeriesInfo series = new CarSeriesInfo();
    public SnsInfo sns = new SnsInfo();
    public int type = 0;
    public UserInfo user = new UserInfo();

    public void parser(JSONObject object) {
        try {
            if (object.has("type")) {
                this.type = object.getInt("type");
            }
        } catch (Exception e) {
        }
        try {
            if (object.has(HttpConstant.USER) && !object.isNull(HttpConstant.USER)) {
                this.user.parser(object.getJSONObject(HttpConstant.USER));
            }
        } catch (Exception e2) {
        }
        try {
            if (object.has("series") && !object.isNull("series")) {
                this.series.parser(object.getJSONObject("series"));
            }
        } catch (Exception e3) {
        }
        try {
            if (object.has("sns") && !object.isNull("sns")) {
                this.sns.parser(object.getJSONObject("sns"));
            }
        } catch (Exception e4) {
        }
        try {
            if (object.has(HttpConstant.HOTSHOW) && !object.isNull(HttpConstant.HOTSHOW)) {
                this.hotshow.parser(object.getJSONObject(HttpConstant.HOTSHOW));
            }
        } catch (Exception e5) {
        }
    }
}
