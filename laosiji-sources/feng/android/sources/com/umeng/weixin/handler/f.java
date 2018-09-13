package com.umeng.weixin.handler;

import com.umeng.socialize.bean.SHARE_MEDIA;

/* synthetic */ class f {
    static final /* synthetic */ int[] a = new int[SHARE_MEDIA.values().length];

    static {
        try {
            a[SHARE_MEDIA.WEIXIN.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[SHARE_MEDIA.WEIXIN_CIRCLE.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[SHARE_MEDIA.WEIXIN_FAVORITE.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
    }
}
