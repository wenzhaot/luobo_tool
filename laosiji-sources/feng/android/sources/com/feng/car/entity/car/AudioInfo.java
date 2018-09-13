package com.feng.car.entity.car;

import com.feng.car.entity.BaseInfo;
import org.json.JSONObject;

public class AudioInfo extends BaseInfo {
    public String hash;
    public int mine;
    public int playtime;
    public String url;

    public void parser(JSONObject object) {
        try {
            if (object.has("hash")) {
                this.hash = object.getString("hash");
            }
        } catch (Exception e) {
        }
        try {
            if (object.has("mine")) {
                this.mine = object.getInt("mine");
            }
        } catch (Exception e2) {
        }
        try {
            if (object.has("playtime")) {
                this.playtime = object.getInt("playtime");
            }
        } catch (Exception e3) {
        }
        try {
            if (object.has("url")) {
                this.url = object.getString("url");
            }
        } catch (Exception e4) {
        }
    }
}
