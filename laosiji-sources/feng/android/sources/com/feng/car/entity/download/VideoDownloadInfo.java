package com.feng.car.entity.download;

import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.thread.MediaInfo;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.JsonUtil;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoDownloadInfo {
    public int _id;
    public int downloadId;
    public long downloadTime;
    public boolean hasWatch = false;
    public int hasWatchProgress;
    public MediaInfo mediaInfo = new MediaInfo();
    public int mediaid = -1;
    public String path = "";
    public long progress;
    public long size;
    public SnsInfo snsInfo = new SnsInfo();
    public int status = 0;
    public String tips;
    public String url = "";

    public void parse(JSONObject json) {
        try {
            if (json.has("url")) {
                this.url = json.getString("url");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (json.has("downloadId")) {
                this.downloadId = json.getInt("downloadId");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        try {
            if (json.has("snsInfo")) {
                if (this.snsInfo == null) {
                    this.snsInfo = new SnsInfo();
                }
                this.snsInfo.parser(new JSONObject(json.getString("snsInfo")));
            }
        } catch (JSONException e22) {
            e22.printStackTrace();
        }
        try {
            if (json.has("mediaInfo")) {
                if (this.mediaInfo == null) {
                    this.mediaInfo = new MediaInfo();
                }
                this.mediaInfo.parser(json.getJSONObject("mediaInfo"));
            }
        } catch (JSONException e222) {
            e222.printStackTrace();
        }
        try {
            if (json.has("hasWatch")) {
                this.hasWatch = json.getBoolean("hasWatch");
            }
        } catch (JSONException e2222) {
            e2222.printStackTrace();
        }
        try {
            if (json.has(FengConstant.SIZE)) {
                this.size = json.getLong(FengConstant.SIZE);
            }
        } catch (JSONException e22222) {
            e22222.printStackTrace();
        }
        try {
            if (json.has("path")) {
                this.path = json.getString("path");
            }
        } catch (JSONException e222222) {
            e222222.printStackTrace();
        }
        try {
            if (json.has("hasWatchProgress")) {
                this.hasWatchProgress = json.getInt("hasWatchProgress");
            }
        } catch (JSONException e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (json.has("downloadTime")) {
                this.downloadTime = json.getLong("downloadTime");
            }
        } catch (JSONException e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (json.has("status")) {
                this.status = json.getInt("status");
            }
        } catch (JSONException e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (json.has("mediaid")) {
                this.mediaid = json.getInt("mediaid");
            }
        } catch (JSONException e2222222222) {
            e2222222222.printStackTrace();
        }
        try {
            if (json.has("tips")) {
                this.tips = json.getString("tips");
            }
        } catch (JSONException e22222222222) {
            e22222222222.printStackTrace();
        }
    }

    public String toJson() {
        Object map = new HashMap();
        map.put("url", this.url);
        map.put("mediaid", Integer.valueOf(this.mediaid));
        map.put("downloadId", Integer.valueOf(this.downloadId));
        map.put("snsInfo", this.snsInfo.dataJson);
        map.put("mediaInfo", this.mediaInfo);
        map.put("hasWatch", Boolean.valueOf(this.hasWatch));
        map.put(FengConstant.SIZE, Long.valueOf(this.size));
        map.put("path", this.path);
        map.put("hasWatchProgress", Integer.valueOf(this.hasWatchProgress));
        map.put("status", Integer.valueOf(this.status));
        map.put("tips", this.tips);
        return JsonUtil.toJson(map);
    }
}
