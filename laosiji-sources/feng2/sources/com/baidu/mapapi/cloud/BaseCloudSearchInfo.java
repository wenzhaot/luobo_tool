package com.baidu.mapapi.cloud;

import anet.channel.request.Request;
import anet.channel.strategy.dispatch.DispatchConstants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class BaseCloudSearchInfo extends BaseSearchInfo {
    public String filter;
    public int pageIndex;
    public int pageSize = 10;
    public String q;
    public String sortby;
    public String tags;

    String a() {
        StringBuilder stringBuilder = new StringBuilder();
        if (super.a() == null) {
            return null;
        }
        stringBuilder.append(super.a());
        if (!(this.q == null || this.q.equals("") || this.q.length() > 45)) {
            stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            stringBuilder.append("q");
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(this.q, Request.DEFAULT_CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (!(this.tags == null || this.tags.equals("") || this.tags.length() > 45)) {
            stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            stringBuilder.append("tags");
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(this.tags, Request.DEFAULT_CHARSET));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        if (!(this.sortby == null || this.sortby.equals(""))) {
            stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            stringBuilder.append("sortby");
            stringBuilder.append("=");
            stringBuilder.append(this.sortby);
        }
        if (!(this.filter == null || this.filter.equals(""))) {
            stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            stringBuilder.append("filter");
            stringBuilder.append("=");
            stringBuilder.append(this.filter);
        }
        if (this.pageIndex >= 0) {
            stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            stringBuilder.append("page_index");
            stringBuilder.append("=");
            stringBuilder.append(this.pageIndex);
        }
        if (this.pageSize >= 0 && this.pageSize <= 50) {
            stringBuilder.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
            stringBuilder.append("page_size");
            stringBuilder.append("=");
            stringBuilder.append(this.pageSize);
        }
        return stringBuilder.toString();
    }
}
