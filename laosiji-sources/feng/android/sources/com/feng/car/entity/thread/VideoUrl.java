package com.feng.car.entity.thread;

import com.feng.car.video.VideoUrlInfo;
import com.feng.library.utils.StringUtil;
import org.json.JSONObject;

public class VideoUrl {
    public VideoUrlInfo bdinfo = new VideoUrlInfo();
    public VideoUrlInfo fhdinfo = new VideoUrlInfo();
    public String hash = "";
    public String hd = "";
    public VideoUrlInfo hdinfo = new VideoUrlInfo();
    public String sd = "";
    public VideoUrlInfo sdinfo = new VideoUrlInfo();

    public void parser(JSONObject object) {
        try {
            if (object.has("hash") && !object.isNull("hash")) {
                this.hash = object.getString("hash");
            }
        } catch (Exception e) {
            this.hash = "";
        }
        try {
            if (object.has("sdinfo")) {
                this.sdinfo.parser(object.getJSONObject("sdinfo"));
                if (!StringUtil.isEmpty(this.sdinfo.url) && StringUtil.isEmpty(this.sdinfo.name)) {
                    this.sdinfo.name = "标清";
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("hdinfo")) {
                this.hdinfo.parser(object.getJSONObject("hdinfo"));
                if (!StringUtil.isEmpty(this.hdinfo.url) && StringUtil.isEmpty(this.hdinfo.name)) {
                    this.hdinfo.name = "高清";
                }
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("fhdinfo")) {
                this.fhdinfo.parser(object.getJSONObject("fhdinfo"));
                if (!StringUtil.isEmpty(this.fhdinfo.url) && StringUtil.isEmpty(this.fhdinfo.name)) {
                    this.hdinfo.name = "超清";
                }
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("bdinfo")) {
                this.bdinfo.parser(object.getJSONObject("bdinfo"));
                if (!StringUtil.isEmpty(this.bdinfo.url) && StringUtil.isEmpty(this.bdinfo.name)) {
                    this.bdinfo.name = "蓝光";
                }
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
    }
}
