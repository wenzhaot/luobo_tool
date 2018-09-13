package com.feng.car.entity.car;

import android.databinding.ObservableField;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import org.json.JSONObject;

public class CarBrandInfo extends BaseInfo {
    public String abc;
    public int id = 0;
    public ImageInfo image = new ImageInfo();
    public final ObservableField<String> name = new ObservableField("");
    public int posflag;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("name")) {
                this.name.set(object.getString("name"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("abc")) {
                this.abc = object.getString("abc");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
    }
}
