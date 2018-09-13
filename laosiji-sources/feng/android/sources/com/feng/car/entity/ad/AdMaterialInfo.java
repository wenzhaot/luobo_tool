package com.feng.car.entity.ad;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.HttpConstant;
import com.umeng.message.MsgConstant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdMaterialInfo extends BaseInfo {
    public List<ImageInfo> image = new ArrayList();
    public String label = "";
    public String title = "";
    public UserInfo user = new UserInfo();

    public void resetData() {
        this.image.clear();
        this.label = "";
        this.title = "";
    }

    public void parser(JSONObject object) {
        try {
            if (object.has("image")) {
                JSONArray jsonArray = object.getJSONArray("image");
                int size = jsonArray.length();
                this.image.clear();
                for (int i = 0; i < size; i++) {
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.parser(jsonArray.getJSONObject(i));
                    this.image.add(imageInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.USER) && !object.isNull(HttpConstant.USER)) {
                this.user.parser(object.getJSONObject(HttpConstant.USER));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("title")) {
                this.title = object.getString("title");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has(MsgConstant.INAPP_LABEL)) {
                this.label = object.getString(MsgConstant.INAPP_LABEL);
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
    }
}
