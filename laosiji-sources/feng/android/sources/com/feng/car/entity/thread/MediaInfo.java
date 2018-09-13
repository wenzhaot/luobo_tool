package com.feng.car.entity.thread;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.utils.FengUtil;
import java.io.Serializable;
import org.json.JSONObject;

public class MediaInfo extends BaseInfo implements Serializable {
    public static final int PALY_STATE = 2;
    public static final int STOP_STATE = 1;
    public int commentcount;
    public int defination = 1;
    public String descn;
    public int downloadDefination = 2;
    public boolean hasChangeDefinition = false;
    public int id = 0;
    public ImageInfo image = new ImageInfo();
    public boolean isComplete = false;
    public String key = "";
    public String path;
    public int play_state = 2;
    public int playcount = 0;
    public int playtime = 0;
    public int position = 0;
    public int type = 1;
    public VideoUrl videourl = new VideoUrl();

    public String getFormatCommentcount() {
        return FengUtil.numberFormat(this.commentcount);
    }

    public String getSDUrl() {
        return this.videourl.sdinfo.url;
    }

    public String getSDName() {
        return this.videourl.sdinfo.name;
    }

    public long getSDSize() {
        return this.videourl.sdinfo.size;
    }

    public String getHDUrl() {
        return this.videourl.hdinfo.url;
    }

    public String getHDName() {
        return this.videourl.hdinfo.name;
    }

    public long getHDSize() {
        return this.videourl.hdinfo.size;
    }

    public String getFHDUrl() {
        return this.videourl.fhdinfo.url;
    }

    public long getFHDSize() {
        return this.videourl.fhdinfo.size;
    }

    public String getFHDName() {
        return this.videourl.fhdinfo.name;
    }

    public String getBDUrl() {
        return this.videourl.bdinfo.url;
    }

    public long getBDSize() {
        return this.videourl.bdinfo.size;
    }

    public String getBDName() {
        return this.videourl.bdinfo.name;
    }

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("type")) {
                this.type = object.getInt("type");
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
            if (object.has("videourl")) {
                this.videourl.parser(object.getJSONObject("videourl"));
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("descn")) {
                this.descn = object.getString("descn");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("commentcount")) {
                this.commentcount = object.getInt("commentcount");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("key")) {
                this.key = object.getString("key");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("path")) {
                this.path = object.getString("path");
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("downloadDefination")) {
                this.downloadDefination = object.getInt("downloadDefination");
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("position")) {
                this.position = object.getInt("position");
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (object.has("playcount")) {
                this.playcount = object.getInt("playcount");
            }
        } catch (Exception e2222222222) {
            e2222222222.printStackTrace();
        }
        try {
            if (object.has("playtime")) {
                this.playtime = object.getInt("playtime");
            }
        } catch (Exception e22222222222) {
            e22222222222.printStackTrace();
        }
        try {
            if (object.has("isComplete")) {
                this.isComplete = object.getBoolean("isComplete");
            }
        } catch (Exception e222222222222) {
            e222222222222.printStackTrace();
        }
    }
}
