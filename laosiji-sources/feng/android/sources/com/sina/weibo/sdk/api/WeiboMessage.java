package com.sina.weibo.sdk.api;

import android.os.Bundle;

public final class WeiboMessage {
    public BaseMediaObject mediaObject;

    public WeiboMessage(Bundle bundle) {
        toBundle(bundle);
    }

    public boolean checkArgs() {
        return this.mediaObject == null ? false : this.mediaObject == null || this.mediaObject.checkArgs();
    }

    public Bundle toBundle(Bundle bundle) {
        if (this.mediaObject != null) {
            bundle.putParcelable("_weibo_message_media", this.mediaObject);
            bundle.putString("_weibo_message_media_extra", this.mediaObject.toExtraMediaString());
        }
        return bundle;
    }

    public WeiboMessage toObject(Bundle bundle) {
        this.mediaObject = (BaseMediaObject) bundle.getParcelable("_weibo_message_media");
        if (this.mediaObject != null) {
            this.mediaObject.toExtraMediaObject(bundle.getString("_weibo_message_media_extra"));
        }
        return this;
    }
}
