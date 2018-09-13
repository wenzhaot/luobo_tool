package com.umeng.socialize.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.stub.StubApp;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.SinaSimplyHandler;
import com.umeng.socialize.utils.SLog;

public class WBShareCallBackActivity extends Activity implements WbShareCallback {
    protected SinaSimplyHandler a = null;
    private final String b = WBShareCallBackActivity.class.getSimpleName();

    static {
        StubApp.interface11(8876);
    }

    protected native void onCreate(Bundle bundle);

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        SLog.I("WBShareCallBackActivity onNewIntent");
        this.a = (SinaSimplyHandler) UMShareAPI.get(StubApp.getOrigApplicationContext(getApplicationContext())).getHandler(SHARE_MEDIA.SINA);
        if (this.a == null) {
            finish();
            return;
        }
        this.a.onCreate(this, PlatformConfig.getPlatform(SHARE_MEDIA.SINA));
        if (this.a.getmWeiboShareAPI() != null) {
            SLog.I("WBShareCallBackActivity 分发回调");
            this.a.getmWeiboShareAPI().doResultIntent(intent, this);
        }
        this.a.release();
        finish();
    }

    public void onWbShareCancel() {
        if (this.a != null) {
            this.a.onCancel();
        }
    }

    public void onWbShareFail() {
        if (this.a != null) {
            this.a.onError();
        }
    }

    public void onWbShareSuccess() {
        if (this.a != null) {
            this.a.onSuccess();
        }
    }
}
