package com.feng.car.utils;

import android.content.Context;
import android.widget.Toast;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.Map;

public abstract class MyUmengAuthImpl implements UMAuthListener {
    private Context mContext;

    public abstract void onAuthCancel();

    public abstract void onAuthComplete();

    public abstract void onAuthError();

    public abstract void onAuthStart();

    public MyUmengAuthImpl(Context context) {
        this.mContext = context;
    }

    public void onStart(SHARE_MEDIA share_media) {
        onAuthStart();
    }

    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
        Toast.makeText(this.mContext, 2131230743, 0).show();
        onAuthComplete();
    }

    public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        Toast.makeText(this.mContext, 2131230742, 0).show();
        onAuthError();
    }

    public void onCancel(SHARE_MEDIA platform, int action) {
        Toast.makeText(this.mContext, 2131230741, 0).show();
        onAuthCancel();
    }
}
