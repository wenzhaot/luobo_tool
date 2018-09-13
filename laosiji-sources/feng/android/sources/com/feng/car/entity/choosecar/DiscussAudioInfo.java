package com.feng.car.entity.choosecar;

import com.feng.car.entity.BaseInfo;
import org.json.JSONObject;

public class DiscussAudioInfo extends BaseInfo {
    public String hash = "";
    public int id = 0;
    public long playtime = 0;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            this.id = 0;
        }
        try {
            if (object.has("hash") && !object.isNull("hash")) {
                this.hash = object.getString("hash");
            }
        } catch (Exception e2) {
            this.hash = "";
        }
        try {
            if (object.has("playtime")) {
                this.playtime = object.getLong("playtime");
            }
        } catch (Exception e3) {
            this.playtime = 0;
        }
    }
}
