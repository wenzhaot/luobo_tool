package com.feng.car.entity;

import android.databinding.ObservableField;
import org.json.JSONObject;

public class BaseModel extends BaseInfo {
    public int id = 0;
    public final ObservableField<String> name = new ObservableField("");

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
        }
        try {
            if (object.has("name")) {
                this.name.set(object.getString("name"));
            }
        } catch (Exception e2) {
            this.name.set("");
        }
    }
}
