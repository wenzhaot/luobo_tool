package com.feng.car.entity;

import com.feng.car.utils.FengConstant;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import org.json.JSONObject;

public class QiNiuInfo extends BaseInfo {
    public String commonurl = "";
    public String hash = "";
    public int height = 0;
    public int id = 0;
    public String mime = "";
    public String outsideurl = "";
    public String size = "";
    public int time = 0;
    public String url = "";
    public int videodefinition = 0;
    public int width = 0;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            this.id = 0;
        }
        try {
            if (object.has("hash")) {
                this.hash = object.getString("hash");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("url")) {
                this.url = object.getString("url");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has(IMediaFormat.KEY_MIME)) {
                this.mime = object.getString(IMediaFormat.KEY_MIME);
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("width")) {
                this.width = object.getInt("width");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("height")) {
                this.height = object.getInt("height");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has(FengConstant.SIZE)) {
                this.size = object.getString(FengConstant.SIZE);
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("time")) {
                this.time = object.getInt("time");
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("videodefinition")) {
                this.videodefinition = object.getInt("videodefinition");
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("outsideurl")) {
                this.outsideurl = object.getString("outsideurl");
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (object.has("commonurl")) {
                this.commonurl = object.getString("commonurl");
            }
        } catch (Exception e2222222222) {
            e2222222222.printStackTrace();
        }
    }
}
