package com.umeng.message;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;

public abstract class UmengMessageService extends IntentService {
    private static final String a = UmengMessageService.class.getSimpleName();

    public abstract void onMessage(Context context, Intent intent);

    public UmengMessageService() {
        super("UmengMessageService");
    }

    public void onCreate() {
        super.onCreate();
    }

    protected void onHandleIntent(Intent intent) {
        onMessage(this, intent);
        String stringExtra = intent.getStringExtra("body");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "message:" + stringExtra);
    }
}
