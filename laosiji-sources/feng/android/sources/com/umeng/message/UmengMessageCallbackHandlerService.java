package com.umeng.message;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import com.stub.StubApp;
import com.umeng.analytics.pro.b;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild;
import com.umeng.message.common.d;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.h;
import com.umeng.message.provider.a;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class UmengMessageCallbackHandlerService extends IntentService {
    public static final String TAG = UmengMessageCallbackHandlerService.class.getName();
    private Context a = this;

    static {
        StubApp.interface11(8561);
    }

    public UmengMessageCallbackHandlerService() {
        super("UmengMessageCallbackHandlerService");
    }

    protected void onHandleIntent(Intent intent) {
        String a = h.a(this.a, Process.myPid());
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(TAG, 2, "进程名：" + a);
        if (intent != null && intent.getAction() != null) {
            UMLog uMLog2;
            boolean booleanExtra;
            IUmengCallback callback;
            if (intent.getAction().equals(MsgConstant.MESSAGE_REGISTER_CALLBACK_ACTION)) {
                try {
                    a = intent.getStringExtra("registration_id");
                    boolean booleanExtra2 = intent.getBooleanExtra("status", false);
                    uMLog2 = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TAG, 2, "注册：" + a + "，状态：" + booleanExtra2);
                    IUmengRegisterCallback registerCallback = PushAgent.getInstance(this.a).getRegisterCallback();
                    if (booleanExtra2) {
                        d.a(new Runnable() {
                            public void run() {
                                try {
                                    String deviceToken = MessageSharedPrefs.getInstance(UmengMessageCallbackHandlerService.this.a).getDeviceToken();
                                    if (!(a == null || deviceToken == null || a.equals(deviceToken))) {
                                        MessageSharedPrefs.getInstance(UmengMessageCallbackHandlerService.this.a).setHasResgister(false);
                                        MessageSharedPrefs.getInstance(UmengMessageCallbackHandlerService.this.a).setDeviceToken(a);
                                        UmengMessageCallbackHandlerService.this.a(UmengMessageCallbackHandlerService.this.a, a);
                                        ContentResolver contentResolver = UmengMessageCallbackHandlerService.this.a.getContentResolver();
                                        a.a(UmengMessageCallbackHandlerService.this.a);
                                        contentResolver.delete(a.e, null, null);
                                        MessageSharedPrefs.getInstance(UmengMessageCallbackHandlerService.this.a).resetTags();
                                    }
                                } catch (Exception e) {
                                    if (e != null) {
                                        e.printStackTrace();
                                    }
                                }
                                UTrack.getInstance(UmengMessageCallbackHandlerService.this.a).a();
                                PushAgent.getInstance(UmengMessageCallbackHandlerService.this.a).onAppStart();
                            }
                        });
                        if (registerCallback != null) {
                            registerCallback.onSuccess(a);
                        }
                    } else if (registerCallback != null) {
                        registerCallback.onFailure(intent.getStringExtra("s"), intent.getStringExtra("s1"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (intent.getAction().equals(MsgConstant.MESSAGE_ENABLE_CALLBACK_ACTION)) {
                try {
                    booleanExtra = intent.getBooleanExtra("status", false);
                    callback = PushAgent.getInstance(this.a).getCallback();
                    uMLog2 = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TAG, 2, "开启状态:" + booleanExtra);
                    if (booleanExtra) {
                        if (callback != null) {
                            callback.onSuccess();
                        }
                    } else if (callback != null) {
                        callback.onFailure(intent.getStringExtra("s"), intent.getStringExtra("s1"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else if (intent.getAction().equals(MsgConstant.MESSAGE_DISABLE_CALLBACK_ACTION)) {
                try {
                    booleanExtra = intent.getBooleanExtra("status", false);
                    callback = PushAgent.getInstance(this.a).getCallback();
                    uMLog2 = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TAG, 2, "关闭状态:" + booleanExtra);
                    if (booleanExtra) {
                        if (callback != null) {
                            callback.onSuccess();
                        }
                    } else if (callback != null) {
                        callback.onFailure(intent.getStringExtra("s"), intent.getStringExtra("s1"));
                    }
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
            } else if (intent.getAction().equals(MsgConstant.MESSAGE_MESSAGE_HANDLER_ACTION)) {
                try {
                    UHandler adHandler;
                    UMessage uMessage = new UMessage(new JSONObject(intent.getStringExtra("body")));
                    uMessage.message_id = intent.getStringExtra("id");
                    uMessage.task_id = intent.getStringExtra("task_id");
                    if (UMessage.DISPLAY_TYPE_NOTIFICATIONPULLAPP.equals(uMessage.display_type)) {
                        adHandler = PushAgent.getInstance(this.a).getAdHandler();
                    } else {
                        adHandler = PushAgent.getInstance(this.a).getMessageHandler();
                    }
                    if (adHandler != null) {
                        adHandler.handleMessage(this.a, uMessage);
                    }
                } catch (Exception e222) {
                    if (e222 != null && e222.getMessage() != null) {
                        uMLog = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(TAG, 2, "MESSAGE_HANDLER_ACTION:" + e222.getMessage());
                    }
                }
            } else if (intent.getAction().equals(MsgConstant.MESSAGE_MESSAGE_SEND_ACTION)) {
                try {
                    a = intent.getStringExtra(MsgConstant.KEY_SENDMESSAGE);
                    String stringExtra = intent.getStringExtra(MsgConstant.KEY_UMPX_PATH);
                    JSONObject jSONObject = new JSONObject(a);
                    JSONObject jSONObject2 = jSONObject.getJSONObject("jsonHeader");
                    jSONObject = jSONObject.getJSONObject("jsonBody");
                    jSONObject2 = new UMSLEnvelopeBuild().buildSLEnvelope(this.a, jSONObject2, jSONObject, stringExtra);
                    if (jSONObject2 != null && !jSONObject2.has(b.ao)) {
                        a(jSONObject.getJSONArray("push"));
                    }
                } catch (Exception e2222) {
                    if (e2222 != null && e2222.getMessage() != null) {
                        uMLog = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(TAG, 2, "MESSAGE_SEND_ACTION:" + e2222.getMessage());
                    }
                }
            }
        }
    }

    private void a(Context context, String str) throws Exception {
        File file = new File(context.getExternalFilesDir(null).getPath() + "/deviceToken");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(str.getBytes());
        fileOutputStream.close();
    }

    private void a(JSONArray jSONArray) {
        if (jSONArray != null) {
            try {
                ArrayList arrayList = new ArrayList();
                if (jSONArray.length() > 0) {
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                        String optString = jSONObject.optString("msg_id");
                        String[] strArr = new String[]{optString, jSONObject.optInt(MsgConstant.KEY_ACTION_TYPE) + ""};
                        a.a(this.a);
                        arrayList.add(ContentProviderOperation.newDelete(a.f).withSelection("MsgId=? And ActionType=?", strArr).build());
                        if (jSONObject.optInt(MsgConstant.KEY_ACTION_TYPE) != 0) {
                            String[] strArr2 = new String[]{optString};
                            a.a(this.a);
                            arrayList.add(ContentProviderOperation.newDelete(a.g).withSelection("MsgId=?", strArr2).build());
                        }
                    }
                }
                ContentResolver contentResolver = this.a.getContentResolver();
                a.a(this.a);
                contentResolver.applyBatch(a.a, arrayList);
            } catch (Exception e) {
                if (e != null) {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TAG, 2, "remove cache log:" + e.getMessage());
                }
            }
        }
    }
}
