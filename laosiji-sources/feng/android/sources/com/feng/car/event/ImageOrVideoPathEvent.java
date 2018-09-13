package com.feng.car.event;

import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import java.util.List;

public class ImageOrVideoPathEvent {
    public boolean isIncludeVideo = false;
    public boolean isOriginal = false;
    public List<UploadQiNiuLocalPathInfo> list;
    public int type = 2;

    public ImageOrVideoPathEvent(int type, List<UploadQiNiuLocalPathInfo> listPath) {
        this.type = type;
        this.list = listPath;
    }

    public ImageOrVideoPathEvent(boolean isIncludeVideo, List<UploadQiNiuLocalPathInfo> listPath) {
        this.isIncludeVideo = isIncludeVideo;
        this.list = listPath;
    }
}
