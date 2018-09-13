package com.feng.car.entity.car;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class OldDriverVoiceInfo extends BaseInfo {
    public UserInfo authoruser = new UserInfo();
    public CarSeriesInfo cars = new CarSeriesInfo();
    public CarModelInfo carx = new CarModelInfo();
    public Carxaudio carxaudio = new Carxaudio();

    public void parser(JSONObject object) {
        try {
            if (object.has("carxaudio")) {
                this.carxaudio.parser(object.getJSONObject("carxaudio"));
            }
        } catch (Exception e) {
        }
        try {
            if (object.has(HttpConstant.CARX)) {
                this.carx.parser(object.getJSONObject(HttpConstant.CARX));
            }
        } catch (Exception e2) {
        }
        try {
            if (object.has(HttpConstant.CARS)) {
                this.cars.parser(object.getJSONObject(HttpConstant.CARS));
            }
        } catch (Exception e3) {
        }
        try {
            if (object.has("authoruser")) {
                this.authoruser.parser(object.getJSONObject("authoruser"));
            }
        } catch (Exception e4) {
        }
    }
}
