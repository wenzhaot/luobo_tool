package com.feng.car.entity.thread;

import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsPostResources;

public class ArticleInfo {
    public static final int COMMENT_TYPE = 2;
    public static final int CONTENT_TYPE = 1;
    public static final int EMPTY_TEXT_TYPE = 6;
    public static final int EMPTY_TYPE = 7;
    public static final int FORWARD_TYPE = 4;
    public static final int PRAISE_TYPE = 3;
    public static final int TAB_TYPE = 5;
    public CommentInfo commentInfo;
    public GratuityRecordInfo gratuityInfo;
    public boolean isextend = false;
    public SnsPostResources resources;
    public SnsInfo snsInfo;
    public int type = 1;
    public int viewhight;
}
