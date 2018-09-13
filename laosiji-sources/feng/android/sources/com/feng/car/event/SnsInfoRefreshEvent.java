package com.feng.car.event;

import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.thread.GratuityRecordInfo;

public class SnsInfoRefreshEvent {
    public static final int TYPE_SNS_COLLECTION_REFRESH = 2001;
    public static final int TYPE_SNS_COMMENT_DELETE = 2006;
    public static final int TYPE_SNS_COMMENT_REFRESH = 2003;
    public static final int TYPE_SNS_DELETE_REFRESH = 2005;
    public static final int TYPE_SNS_PRAISE_REFRESH = 2004;
    public CommentInfo commentInfo;
    public int commentid = 0;
    public boolean deleterefresh = false;
    public GratuityRecordInfo gratuityRecordInfo;
    public int refreshType;
    public SnsInfo snsInfo;

    public SnsInfoRefreshEvent(SnsInfo snsInfo, int refreshType) {
        this.snsInfo = snsInfo;
        this.refreshType = refreshType;
    }

    public SnsInfoRefreshEvent(SnsInfo snsInfo, GratuityRecordInfo gratuityRecordInfo, int refreshType) {
        this.snsInfo = snsInfo;
        this.refreshType = refreshType;
        this.gratuityRecordInfo = gratuityRecordInfo;
    }

    public SnsInfoRefreshEvent(SnsInfo snsInfo, int refreshType, boolean deleterefresh) {
        this.snsInfo = snsInfo;
        this.refreshType = refreshType;
        this.deleterefresh = deleterefresh;
    }

    public SnsInfoRefreshEvent(SnsInfo snsInfo, CommentInfo commentInfo) {
        this.snsInfo = snsInfo;
        this.refreshType = 2003;
        this.commentInfo = commentInfo;
    }
}
