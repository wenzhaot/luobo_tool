package com.feng.car.entity.car;

import android.databinding.ObservableInt;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class CarImageInfo extends BaseInfo {
    public static final int CAR_PHOTO_APPEARANCE_TYPE = 1;
    public static final int CAR_PHOTO_CENTER_CONTROL_TYPE = 2;
    public static final int CAR_PHOTO_DETAILS_TYPE = 4;
    public static final int CAR_PHOTO_SEAT_TYPE = 3;
    public String carxname = "";
    public String dealer4sname = "";
    public String dealer4stel = "";
    public String fromdescn = "";
    public String fromurl = "";
    public ImageInfo image = new ImageInfo();
    public CarImageListInfo imagelist = new CarImageListInfo();
    public ObservableInt position = new ObservableInt(0);
    public int type = 1;

    public void parser(JSONObject object) {
        try {
            if (object.has("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("fromurl")) {
                this.fromurl = object.getString("fromurl");
            }
        } catch (Exception e2) {
        }
        try {
            if (object.has("fromdescn") && !object.isNull("fromdescn")) {
                this.fromdescn = object.getString("fromdescn");
            }
        } catch (Exception e3) {
        }
        try {
            if (object.has("dealer4sname") && !object.isNull("dealer4sname")) {
                this.dealer4sname = object.getString("dealer4sname");
            }
        } catch (Exception e4) {
        }
        try {
            if (object.has("dealer4stel") && !object.isNull("dealer4stel")) {
                this.dealer4stel = object.getString("dealer4stel");
            }
        } catch (Exception e5) {
        }
        try {
            if (object.has(HttpConstant.IMAGELIST)) {
                this.imagelist.parser(object.getJSONObject(HttpConstant.IMAGELIST));
            }
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        try {
            if (object.has("carxname")) {
                this.carxname = object.getString("carxname");
            }
        } catch (Exception e7) {
        }
        try {
            if (object.has("type")) {
                this.type = object.getInt("type");
            }
        } catch (Exception e8) {
        }
    }
}
