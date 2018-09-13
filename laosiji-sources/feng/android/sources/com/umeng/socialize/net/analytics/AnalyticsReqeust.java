package com.umeng.socialize.net.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.URequest.RequestMethod;
import com.umeng.socialize.utils.SocializeUtils;

public class AnalyticsReqeust extends SocializeRequest {
    private static final String a = "/share/multi_add/";
    private static final int b = 9;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private UMediaObject j;

    public AnalyticsReqeust(Context context, String str, String str2) {
        super(context, "", AnalyticsResponse.class, 9, RequestMethod.POST);
        this.mContext = context;
        this.d = str;
        this.i = str2;
    }

    public void setPlatform(String str) {
        this.d = str;
    }

    public void setUID(String str) {
        this.e = str;
    }

    public void setType(String str) {
        this.f = str;
    }

    public void setText(String str) {
        this.i = str;
    }

    public void setmUsid(String str) {
        this.c = str;
    }

    public void setMedia(UMediaObject uMediaObject) {
        if (uMediaObject instanceof UMImage) {
            this.j = uMediaObject;
        } else if (uMediaObject instanceof UMusic) {
            this.g = ((UMusic) uMediaObject).getTitle();
            this.h = ((UMusic) uMediaObject).toUrl();
            this.i = ((UMusic) uMediaObject).getDescription();
            this.j = ((UMusic) uMediaObject).getThumbImage();
        } else if (uMediaObject instanceof UMVideo) {
            this.g = ((UMVideo) uMediaObject).getTitle();
            this.h = ((UMVideo) uMediaObject).toUrl();
            this.i = ((UMVideo) uMediaObject).getDescription();
            this.j = ((UMVideo) uMediaObject).getThumbImage();
        } else if (uMediaObject instanceof UMWeb) {
            this.g = ((UMWeb) uMediaObject).getTitle();
            this.h = ((UMWeb) uMediaObject).toUrl();
            this.i = ((UMWeb) uMediaObject).getDescription();
            this.j = ((UMWeb) uMediaObject).getThumbImage();
        } else if (uMediaObject instanceof UMMin) {
            this.g = ((UMMin) uMediaObject).getTitle();
            this.h = ((UMMin) uMediaObject).toUrl();
            this.i = ((UMMin) uMediaObject).getDescription();
            this.j = ((UMMin) uMediaObject).getThumbImage();
        }
    }

    public void onPrepareRequest() {
        super.onPrepareRequest();
        String str = "{\"%s\":\"%s\"}";
        Object[] objArr = new Object[2];
        objArr[0] = this.d;
        objArr[1] = this.c == null ? "" : this.c;
        String format = String.format(str, objArr);
        str = SocializeUtils.getAppkey(this.mContext);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DESCRIPTOR, Config.Descriptor);
        addStringParams("to", format);
        addStringParams("sns", format);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_AK, str);
        addStringParams("type", this.f);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_SHARE_USID, this.c);
        addStringParams("ct", this.i);
        if (!TextUtils.isEmpty(this.h)) {
            addStringParams("url", this.h);
        }
        if (!TextUtils.isEmpty(this.g)) {
            addStringParams("title", this.g);
        }
        addMediaParams(this.j);
    }

    protected String getPath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(a);
        stringBuilder.append(SocializeUtils.getAppkey(this.mContext));
        stringBuilder.append("/").append(Config.EntityKey).append("/");
        return stringBuilder.toString();
    }
}
