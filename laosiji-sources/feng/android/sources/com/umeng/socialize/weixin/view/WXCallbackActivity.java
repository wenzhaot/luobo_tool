package com.umeng.socialize.weixin.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.stub.StubApp;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SLog;
import com.umeng.weixin.handler.UmengWXHandler;
import com.umeng.weixin.umengwx.a;
import com.umeng.weixin.umengwx.b;
import com.umeng.weixin.umengwx.e;

public abstract class WXCallbackActivity extends Activity implements e {
    protected UmengWXHandler a = null;
    private final String b = WXCallbackActivity.class.getSimpleName();

    protected void a(Intent intent) {
        this.a.getWXApi().handleIntent(intent, this);
    }

    public void a(a aVar) {
        if (this.a != null) {
            this.a.getWXEventHandler().a(aVar);
        }
        finish();
    }

    public void a(b bVar) {
        SLog.I("WXCallbackActivity 分发回调");
        if (!(this.a == null || bVar == null)) {
            try {
                this.a.getWXEventHandler().a(bVar);
            } catch (Throwable e) {
                SLog.error(e);
            }
        }
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SLog.I("WXCallbackActivity onCreate");
        UMShareAPI uMShareAPI = UMShareAPI.get(StubApp.getOrigApplicationContext(getApplicationContext()));
        SLog.I("WXCallbackActivity mWxHandler：" + this.a);
        this.a = (UmengWXHandler) uMShareAPI.getHandler(SHARE_MEDIA.WEIXIN);
        this.a.onCreate(StubApp.getOrigApplicationContext(getApplicationContext()), PlatformConfig.getPlatform(SHARE_MEDIA.WEIXIN));
        a(getIntent());
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SLog.I("WXCallbackActivity onNewIntent");
        setIntent(intent);
        this.a = (UmengWXHandler) UMShareAPI.get(StubApp.getOrigApplicationContext(getApplicationContext())).getHandler(SHARE_MEDIA.WEIXIN);
        this.a.onCreate(StubApp.getOrigApplicationContext(getApplicationContext()), PlatformConfig.getPlatform(SHARE_MEDIA.WEIXIN));
        a(intent);
    }
}
