package com.feng.car.event;

public class SendCommentStartSlideEvent {
    public int dy;
    public boolean isFinish = false;
    public String key = "";

    public SendCommentStartSlideEvent(int dy, String key) {
        this.dy = dy;
        this.key = key;
    }

    public SendCommentStartSlideEvent(int dy, String key, boolean isFinish) {
        this.dy = dy;
        this.key = key;
        this.isFinish = isFinish;
    }
}
