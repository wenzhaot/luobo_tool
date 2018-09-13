package com.feng.car.event;

public class CommentEvent {
    public String commentJson;
    public int threadId = 0;

    public CommentEvent(String commentJson, int threadId) {
        this.commentJson = commentJson;
        this.threadId = threadId;
    }
}
