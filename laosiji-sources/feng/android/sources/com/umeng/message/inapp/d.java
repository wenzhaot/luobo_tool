package com.umeng.message.inapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.socialize.net.dplus.CommonNetImpl;

/* compiled from: UmengInAppClickHandler */
class d implements UInAppHandler {
    private static final String a = d.class.getName();
    private String b = null;
    private String c = null;
    private String d = null;

    d() {
    }

    public final void handleInAppMessage(Activity activity, UInAppMessage uInAppMessage, int i) {
        switch (i) {
            case 16:
                this.b = uInAppMessage.action_type;
                this.c = uInAppMessage.action_activity;
                this.d = uInAppMessage.action_url;
                break;
            case 17:
                this.b = uInAppMessage.bottom_action_type;
                this.c = uInAppMessage.bottom_action_activity;
                this.d = uInAppMessage.bottom_action_url;
                break;
            case 18:
                this.b = uInAppMessage.plainTextActionType;
                this.c = uInAppMessage.plainTextActivity;
                this.d = uInAppMessage.plainTextUrl;
                break;
            case 19:
                this.b = uInAppMessage.customButtonActionType;
                this.c = uInAppMessage.customButtonActivity;
                this.d = uInAppMessage.customButtonUrl;
                break;
        }
        if (!TextUtils.isEmpty(this.b)) {
            if (TextUtils.equals("go_activity", this.b)) {
                a(activity);
            } else if (TextUtils.equals("go_url", this.b)) {
                b(activity);
            } else {
                if (TextUtils.equals("go_app", this.b)) {
                }
            }
        }
    }

    private void a(Activity activity) {
        try {
            if (this.c != null && !TextUtils.isEmpty(this.c.trim())) {
                Intent intent = new Intent();
                intent.setClassName(activity, this.c);
                intent.setFlags(CommonNetImpl.FLAG_SHARE);
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void b(Activity activity) {
        try {
            if (this.d != null && !TextUtils.isEmpty(this.d.trim())) {
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(a, 2, "打开链接: " + this.d);
                activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.d)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
