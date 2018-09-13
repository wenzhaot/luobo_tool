package com.umeng.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.stub.StubApp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.entity.UMessage;
import com.umeng.message.entity.UNotificationItem;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import org.json.JSONObject;

public class NotificationProxyBroadcastReceiver extends BroadcastReceiver {
    public static final int EXTRA_ACTION_CLICK = 10;
    public static final int EXTRA_ACTION_DISMISS = 11;
    public static final int EXTRA_ACTION_NOT_EXIST = -1;
    public static final String EXTRA_KEY_ACTION = "ACTION";
    public static final String EXTRA_KEY_MESSAGE_ID = "MESSAGE_ID";
    public static final String EXTRA_KEY_MSG = "MSG";
    public static final String EXTRA_KEY_NOTIFICATION_ID = "NOTIFICATION_ID";
    public static final String EXTRA_KEY_TASK_ID = "TASK_ID";
    public static final int LOCAL_ACTION_CLICK = 12;
    private static final String a = NotificationProxyBroadcastReceiver.class.getName();
    private UHandler b;

    static {
        StubApp.interface11(8528);
    }

    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_KEY_MSG);
        try {
            int intExtra = intent.getIntExtra(EXTRA_KEY_ACTION, -1);
            UMLog uMLog = UMConfigure.umDebugLog;
            String str = a;
            String[] strArr = new String[1];
            strArr[0] = String.format("onReceive[msg=%s, action=%d]", new Object[]{stringExtra, Integer.valueOf(intExtra)});
            UMLog.mutlInfo(str, 2, strArr);
            if (intExtra == 12) {
                a(context);
                return;
            }
            UMessage uMessage = new UMessage(new JSONObject(stringExtra));
            int intExtra2 = intent.getIntExtra(EXTRA_KEY_NOTIFICATION_ID, -1);
            uMessage.message_id = intent.getStringExtra(EXTRA_KEY_MESSAGE_ID);
            uMessage.task_id = intent.getStringExtra(EXTRA_KEY_TASK_ID);
            UMLog uMLog2;
            switch (intExtra) {
                case 10:
                    uMLog2 = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(a, 2, "点击通知");
                    UTrack.getInstance(context).setClearPrevMessage(true);
                    UTrack.getInstance(context).trackMsgClick(uMessage);
                    this.b = PushAgent.getInstance(context).getNotificationClickHandler();
                    if (this.b != null) {
                        uMessage.clickOrDismiss = true;
                        this.b.handleMessage(context, uMessage);
                        break;
                    }
                    break;
                case 11:
                    uMLog2 = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(a, 2, "忽略通知");
                    UTrack.getInstance(context).setClearPrevMessage(true);
                    UTrack.getInstance(context).trackMsgDismissed(uMessage);
                    this.b = PushAgent.getInstance(context).getNotificationClickHandler();
                    if (this.b != null) {
                        uMessage.clickOrDismiss = false;
                        this.b.handleMessage(context, uMessage);
                        break;
                    }
                    break;
            }
            MessageNotificationQueue.getInstance().remove(new UNotificationItem(intExtra2, uMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(Context context) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        UMLog uMLog;
        if (launchIntentForPackage == null) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "找不到应用: " + context.getPackageName());
            return;
        }
        launchIntentForPackage.setPackage(null);
        launchIntentForPackage.addFlags(CommonNetImpl.FLAG_AUTH);
        context.startActivity(launchIntentForPackage);
        uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "打开应用: " + context.getPackageName());
    }
}
