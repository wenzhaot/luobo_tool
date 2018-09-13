package com.umeng.message;

import android.content.Intent;
import android.os.Bundle;
import com.taobao.agoo.BaseNotifyClickActivity;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.entity.UMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class UmengNotifyClickActivity extends BaseNotifyClickActivity {
    private static final String a = UmengNotifyClickActivity.class.getName();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    protected void onStart() {
        super.onStart();
    }

    public void onMessage(Intent intent) {
        String stringExtra = intent.getStringExtra("body");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "onMessage():[" + stringExtra + "]");
        try {
            UMessage uMessage = new UMessage(new JSONObject(stringExtra));
            uMessage.message_id = intent.getStringExtra("id");
            uMessage.task_id = intent.getStringExtra("task_id");
            UTrack.getInstance(this).trackMiPushMsgClick(uMessage);
        } catch (JSONException e) {
            e.printStackTrace();
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, e.toString());
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
