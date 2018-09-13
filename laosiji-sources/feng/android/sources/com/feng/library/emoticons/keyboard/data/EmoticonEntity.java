package com.feng.library.emoticons.keyboard.data;

public class EmoticonEntity {
    private String mContent;
    private long mEventType;
    private String mIconUri;

    public long getEventType() {
        return this.mEventType;
    }

    public void setEventType(long eventType) {
        this.mEventType = eventType;
    }

    public String getIconUri() {
        return this.mIconUri;
    }

    public void setIconUri(String iconUri) {
        this.mIconUri = iconUri;
    }

    public void setIconUri(int iconUri) {
        this.mIconUri = "" + iconUri;
    }

    public String getContent() {
        return this.mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public EmoticonEntity(long eventType, String iconUri, String content) {
        this.mEventType = eventType;
        this.mIconUri = iconUri;
        this.mContent = content;
    }

    public EmoticonEntity(String iconUri, String content) {
        this.mIconUri = iconUri;
        this.mContent = content;
    }

    public EmoticonEntity(String content) {
        this.mContent = content;
    }
}
