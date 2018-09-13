package com.feng.car.video;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.FengConstant;
import com.feng.library.utils.StringUtil;
import org.json.JSONObject;

public class VideoUrlInfo extends BaseInfo {
    public String name = "";
    public long size;
    public String type = "";
    public String url = "";

    public void update(VideoUrlInfo info) {
        if (info != null && !StringUtil.isEmpty(info.url)) {
            this.type = info.type;
            this.name = info.name;
            this.size = info.size;
            this.url = info.url;
        }
    }

    public void parser(JSONObject object) {
        try {
            if (object.has("type")) {
                this.type = object.getString("type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("name")) {
                this.name = object.getString("name");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(FengConstant.SIZE)) {
                this.size = object.getLong(FengConstant.SIZE);
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("url")) {
                this.url = object.getString("url");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
    }
}
