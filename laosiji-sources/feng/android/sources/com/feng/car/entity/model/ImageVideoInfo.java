package com.feng.car.entity.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import com.meizu.cloud.pushsdk.constants.PushConstants;

public class ImageVideoInfo extends BaseObservable {
    public String bucketId = "";
    public long date_modified = 0;
    public int hight = 0;
    public String id = "";
    public final ObservableBoolean isSelected = new ObservableBoolean(false);
    public String mimeType = "";
    private int selectIndex = 0;
    public String size = "";
    public long time = 0;
    public String url = "";
    public int width = 0;

    public boolean isVideoType() {
        return this.mimeType.indexOf("video") == 0;
    }

    @Bindable
    public int getSelectIndex() {
        return this.selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyPropertyChanged(52);
    }

    public ImageVideoInfo(String id) {
        this.id = id;
    }

    public ImageVideoInfo(String url, String size, boolean isSelected, int width, int hight, int time) {
        this.url = url;
        if (size == null) {
            size = PushConstants.PUSH_TYPE_NOTIFY;
        }
        this.size = size;
        this.isSelected.set(isSelected);
        this.width = width;
        this.hight = hight;
        this.time = (long) time;
    }
}
