package com.feng.car.entity;

public class DialogItemEntity {
    public int iconid = 0;
    public boolean isHighLightShow;
    public String itemContent;
    public int itemTag;

    public void setIconId(int id) {
        this.iconid = id;
    }

    public DialogItemEntity(String itemContent, boolean isHighLightShow) {
        this.itemContent = itemContent;
        this.isHighLightShow = isHighLightShow;
    }

    public DialogItemEntity(String itemContent, boolean isHighLightShow, int itemTag) {
        this.itemContent = itemContent;
        this.isHighLightShow = isHighLightShow;
        this.itemTag = itemTag;
    }
}
