package com.umeng.socialize.common;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UrlUtil;
import java.util.HashMap;
import java.util.Map;

public final class ResContainer {
    private static ResContainer R = null;
    private static String mPackageName = "";
    private Context context = null;
    private Map<String, SocializeResource> mResources;
    private Map<String, Integer> map = new HashMap();

    public static class SocializeResource {
        public int mId;
        public boolean mIsCompleted = false;
        public String mName;
        public String mType;

        public SocializeResource(String str, String str2) {
            this.mType = str;
            this.mName = str2;
        }
    }

    private ResContainer(Context context) {
        this.context = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public static synchronized ResContainer get(Context context) {
        ResContainer resContainer;
        synchronized (ResContainer.class) {
            if (R == null) {
                R = new ResContainer(context);
            }
            resContainer = R;
        }
        return resContainer;
    }

    public int layout(String str) {
        return getResourceId(this.context, "layout", str);
    }

    public int id(String str) {
        return getResourceId(this.context, "id", str);
    }

    public int drawable(String str) {
        return getResourceId(this.context, "drawable", str);
    }

    public int style(String str) {
        return getResourceId(this.context, "style", str);
    }

    public int string(String str) {
        return getResourceId(this.context, "string", str);
    }

    public int color(String str) {
        return getResourceId(this.context, "color", str);
    }

    public int dimen(String str) {
        return getResourceId(this.context, "dimen", str);
    }

    public int raw(String str) {
        return getResourceId(this.context, "raw", str);
    }

    public int anim(String str) {
        return getResourceId(this.context, "anim", str);
    }

    public int styleable(String str) {
        return getResourceId(this.context, "styleable", str);
    }

    public ResContainer(Context context, Map<String, SocializeResource> map) {
        this.mResources = map;
        this.context = context;
    }

    public static int getResourceId(Context context, String str, String str2) {
        Resources resources = context.getResources();
        if (TextUtils.isEmpty(mPackageName)) {
            mPackageName = context.getPackageName();
        }
        int identifier = resources.getIdentifier(str2, str, mPackageName);
        if (identifier > 0) {
            return identifier;
        }
        throw new RuntimeException(UmengText.errorWithUrl(UmengText.resError(mPackageName, str, str2), UrlUtil.ALL_NO_RES));
    }

    public static String getString(Context context, String str) {
        return context.getString(getResourceId(context, "string", str));
    }

    public synchronized Map<String, SocializeResource> batch() {
        Map<String, SocializeResource> map;
        if (this.mResources == null) {
            map = this.mResources;
        } else {
            for (String str : this.mResources.keySet()) {
                SocializeResource socializeResource = (SocializeResource) this.mResources.get(str);
                socializeResource.mId = getResourceId(this.context, socializeResource.mType, socializeResource.mName);
                socializeResource.mIsCompleted = true;
            }
            map = this.mResources;
        }
        return map;
    }
}
