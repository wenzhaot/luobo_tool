package com.feng.car.entity.model;

public class UploadQiNiuLocalPathInfo {
    public String path = "";
    public int type = 2;
    public String videocoverpath = "";
    public long videotime = 0;

    public UploadQiNiuLocalPathInfo(int type, String path) {
        this.type = type;
        this.path = path;
    }

    public UploadQiNiuLocalPathInfo(int type, String path, long videotime) {
        this.type = type;
        this.path = path;
        this.videotime = videotime;
    }

    public UploadQiNiuLocalPathInfo(int type, String path, String videocoverpath) {
        this.type = type;
        this.path = path;
        this.videocoverpath = videocoverpath;
    }
}
