package com.umeng.message;

import android.content.Context;
import android.content.Intent;
import com.stub.StubApp;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.umeng.message.common.UmengMessageDeviceConfig;
import com.umeng.message.entity.UMessage;
import java.util.Map.Entry;
import org.json.JSONObject;

public class UmengIntentService extends UmengBaseIntentService {
    private static final String a = UmengIntentService.class.getName();

    static {
        StubApp.interface11(8557);
    }

    protected void onMessage(final Context context, Intent intent) {
        super.onMessage(context, intent);
        String str = "";
        str = "";
        str = "";
        try {
            str = intent.getStringExtra("body");
            String stringExtra = intent.getStringExtra("id");
            String stringExtra2 = intent.getStringExtra("task_id");
            final UMessage uMessage = new UMessage(new JSONObject(str));
            Intent intent2;
            if (UMessage.DISPLAY_TYPE_PULLAPP.equals(uMessage.display_type)) {
                if (uMessage.isAction) {
                    uMessage.pulled_service = UmengMessageDeviceConfig.getServiceName(this, uMessage.pulled_service, uMessage.pulled_package);
                }
                if (UmengMessageDeviceConfig.isServiceWork(context, uMessage.pulled_service, uMessage.pulled_package)) {
                    UTrack.getInstance(context).trackMsgPulled(uMessage, 52);
                } else if (UmengMessageDeviceConfig.getDataData(uMessage.pulled_package)) {
                    Intent intent3 = new Intent();
                    intent3.setClassName(uMessage.pulled_package, uMessage.pulled_service);
                    a(intent3, uMessage);
                    startService(intent3);
                    ThreadPoolExecutorFactory.execute(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (UmengMessageDeviceConfig.isServiceWork(context, uMessage.pulled_service, uMessage.pulled_package)) {
                                UTrack.getInstance(context).trackMsgPulled(uMessage, 51);
                            } else {
                                UTrack.getInstance(context).trackMsgPulled(uMessage, 50);
                            }
                        }
                    });
                } else {
                    UTrack.getInstance(context).trackMsgPulled(uMessage, 53);
                }
            } else if (UMessage.DISPLAY_TYPE_NOTIFICATIONPULLAPP.equals(uMessage.display_type)) {
                intent2 = new Intent();
                intent2.setPackage(context.getPackageName());
                intent2.setAction(MsgConstant.MESSAGE_MESSAGE_HANDLER_ACTION);
                intent2.putExtra("body", str);
                intent2.putExtra("id", stringExtra);
                intent2.putExtra("task_id", stringExtra2);
                context.startService(intent2);
            } else {
                String pushIntentServiceClass = MessageSharedPrefs.getInstance(context).getPushIntentServiceClass();
                if (pushIntentServiceClass.equalsIgnoreCase("")) {
                    intent2 = new Intent();
                    intent2.setPackage(context.getPackageName());
                    intent2.setAction(MsgConstant.MESSAGE_MESSAGE_HANDLER_ACTION);
                    intent2.putExtra("body", str);
                    intent2.putExtra("id", stringExtra);
                    intent2.putExtra("task_id", stringExtra2);
                    context.startService(intent2);
                    return;
                }
                Intent intent4 = new Intent();
                intent4.setClassName(context, pushIntentServiceClass);
                intent4.setPackage(context.getPackageName());
                intent4.putExtra("body", str);
                intent4.putExtra("id", stringExtra);
                intent4.putExtra("task_id", stringExtra2);
                context.startService(intent4);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    private Intent a(Intent intent, UMessage uMessage) {
        if (!(intent == null || uMessage == null || uMessage.extra == null)) {
            for (Entry entry : uMessage.extra.entrySet()) {
                String str = (String) entry.getKey();
                String str2 = (String) entry.getValue();
                if (str != null) {
                    intent.putExtra(str, str2);
                }
            }
        }
        return intent;
    }
}
