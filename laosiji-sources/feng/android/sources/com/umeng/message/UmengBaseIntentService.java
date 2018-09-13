package com.umeng.message;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.text.TextUtils;
import com.stub.StubApp;
import com.taobao.agoo.TaobaoBaseIntentService;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.m;
import org.json.JSONObject;

public abstract class UmengBaseIntentService extends TaobaoBaseIntentService {
    private static final String a = UmengBaseIntentService.class.getName();

    protected void onMessage(Context context, Intent intent) {
        if (Process.getElapsedCpuTime() < 3000) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "应用程序通过推送消息启动");
            PushAgent.setAppLaunchByMessage();
        }
        String stringExtra = intent.getStringExtra("body");
        UMLog uMLog2 = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "onMessage():[" + stringExtra + "]");
        try {
            UMessage uMessage = new UMessage(new JSONObject(stringExtra));
            uMessage.message_id = intent.getStringExtra("id");
            uMessage.task_id = intent.getStringExtra("task_id");
            UTrack.getInstance(StubApp.getOrigApplicationContext(getApplicationContext())).trackMsgArrival(uMessage);
            m.a(context).a(uMessage.message_id, uMessage.task_id, uMessage.display_type);
            if (TextUtils.equals(UMessage.DISPLAY_TYPE_AUTOUPDATE, uMessage.display_type)) {
                String stringExtra2 = intent.getStringExtra("id");
                String stringExtra3 = intent.getStringExtra("task_id");
                Intent intent2 = new Intent();
                intent2.setPackage(context.getPackageName());
                intent2.setAction(MsgConstant.MESSAGE_AUTOUPDATE_HANDLER_ACTION);
                intent2.putExtra("body", stringExtra);
                intent2.putExtra("id", stringExtra2);
                intent2.putExtra("task_id", stringExtra3);
                context.startService(intent2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            uMLog2 = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e.toString());
        }
    }

    protected void onError(Context context, String str) {
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 0, "onError()[" + str + "]");
    }

    protected void onRegistered(Context context, String str) {
    }

    protected void onUnregistered(Context context, String str) {
    }
}
