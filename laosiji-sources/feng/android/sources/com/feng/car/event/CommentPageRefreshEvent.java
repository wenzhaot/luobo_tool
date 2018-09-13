package com.feng.car.event;

public class CommentPageRefreshEvent {
    public int commentId;
    public boolean isTop = false;
    public int topNum;

    public CommentPageRefreshEvent(int commentId) {
        this.commentId = commentId;
    }

    public CommentPageRefreshEvent(int commentId, int topNum, boolean isTop) {
        this.commentId = commentId;
        this.isTop = isTop;
        this.topNum = topNum;
    }
}
