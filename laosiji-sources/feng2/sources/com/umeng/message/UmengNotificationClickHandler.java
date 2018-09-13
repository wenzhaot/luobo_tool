package com.umeng.message;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import anet.channel.util.HttpConstant;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.common.UmengMessageDeviceConfig;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.c;
import com.umeng.message.proguard.m;
import java.lang.reflect.Method;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class UmengNotificationClickHandler implements UHandler {
    private static final String a = UmengNotificationClickHandler.class.getName();

    public void handleMessage(Context context, UMessage uMessage) {
        try {
            if (!uMessage.clickOrDismiss) {
                dismissNotification(context, uMessage);
            } else if (TextUtils.equals("autoupdate", uMessage.display_type) && PushAgent.getInstance(context).isIncludesUmengUpdateSDK()) {
                autoUpdate(context, uMessage);
            } else {
                if (!TextUtils.isEmpty(uMessage.after_open)) {
                    if ("notificationpullapp".equals(uMessage.display_type)) {
                        if (TextUtils.equals("go_appurl", uMessage.after_open)) {
                            a(context, uMessage);
                            return;
                        }
                    } else if (TextUtils.equals("go_url", uMessage.after_open)) {
                        openUrl(context, uMessage);
                        return;
                    } else if (TextUtils.equals("go_activity", uMessage.after_open)) {
                        openActivity(context, uMessage);
                        return;
                    } else if (TextUtils.equals("go_custom", uMessage.after_open)) {
                        dealWithCustomAction(context, uMessage);
                        return;
                    } else if (TextUtils.equals("go_app", uMessage.after_open)) {
                        launchApp(context, uMessage);
                        return;
                    }
                }
                if (!"notificationpullapp".equals(uMessage.display_type)) {
                    if (uMessage.url != null && !TextUtils.isEmpty(uMessage.url.trim())) {
                        openUrl(context, uMessage);
                    } else if (uMessage.activity != null && !TextUtils.isEmpty(uMessage.activity.trim())) {
                        openActivity(context, uMessage);
                    } else if (uMessage.custom == null || TextUtils.isEmpty(uMessage.custom.trim())) {
                        launchApp(context, uMessage);
                    } else {
                        dealWithCustomAction(context, uMessage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissNotification(Context context, UMessage uMessage) {
    }

    public void autoUpdate(Context context, UMessage uMessage) {
        try {
            Object g = m.a(context).g();
            Class cls = Class.forName("com.umeng.update.UmengUpdateAgent");
            Class cls2 = Class.forName("com.umeng.update.UpdateResponse");
            Method method = cls.getMethod("showUpdateDialog", new Class[]{Context.class, cls2});
            if (g != null) {
                method.invoke(cls, new Object[]{context, cls2.cast(g)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(Context context, UMessage uMessage) {
        try {
            String str = uMessage.custom;
            if (str != null && !str.equals("")) {
                JSONObject jSONObject = new JSONObject(str);
                String optString = jSONObject.optString("p");
                str = jSONObject.optString("pu");
                String optString2 = jSONObject.optString("ju");
                int optInt = jSONObject.optInt("en");
                Intent intent = new Intent();
                String[] split = str.split(HttpConstant.SCHEME_SPLIT);
                if (split == null || split.length >= 2) {
                    split = split[1].split("/");
                    if (split == null || split.length >= 1) {
                        String str2 = split[0];
                        String str3 = "";
                        if (optInt == 1) {
                            String[] split2 = str.split(str2 + "/");
                            try {
                                StringBuilder stringBuilder = new StringBuilder();
                                if (split2 != null && split2.length >= 2) {
                                    stringBuilder.append(split2[1]);
                                }
                                stringBuilder.append("&umessage=");
                                stringBuilder.append(uMessage.getRaw().toString());
                                stringBuilder.append("&thirdkey=");
                                stringBuilder.append(PushAgent.getInstance(context).getMessageAppkey());
                                UMLog uMLog = UMConfigure.umDebugLog;
                                UMLog.mutlInfo(a, 2, new String[]{"url:" + stringBuilder.toString()});
                                str = c.a(stringBuilder.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                                str = str3;
                            }
                            str = split2[0] + str2 + "/" + str;
                        }
                        intent.setData(Uri.parse(str));
                        intent.setPackage(optString);
                        intent.addFlags(268435456);
                        Intent intent2;
                        if (UmengMessageDeviceConfig.getDataData(optString)) {
                            try {
                                if (UmengMessageDeviceConfig.isIntentExist(context, str, optString)) {
                                    UTrack.getInstance(context).trackMsgPulled(uMessage, 62);
                                    context.startActivity(intent);
                                    return;
                                }
                                UTrack.getInstance(context).trackMsgPulled(uMessage, 61);
                                if (optString2 != null && !TextUtils.isEmpty(optString2.trim())) {
                                    intent2 = new Intent("android.intent.action.VIEW", Uri.parse(optString2));
                                    intent2.addFlags(268435456);
                                    context.startActivity(intent2);
                                    return;
                                }
                                return;
                            } catch (Exception e2) {
                                return;
                            }
                        }
                        UTrack.getInstance(context).trackMsgPulled(uMessage, 60);
                        if (optString2 != null && !TextUtils.isEmpty(optString2.trim())) {
                            intent2 = new Intent("android.intent.action.VIEW", Uri.parse(optString2));
                            intent2.addFlags(268435456);
                            context.startActivity(intent2);
                        }
                    }
                }
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
    }

    public void openUrl(Context context, UMessage uMessage) {
        if (uMessage.url != null && !TextUtils.isEmpty(uMessage.url.trim())) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, new String[]{"打开链接: " + uMessage.url});
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uMessage.url));
            a(intent, uMessage);
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    public void openActivity(Context context, UMessage uMessage) {
        if (uMessage.activity != null && !TextUtils.isEmpty(uMessage.activity.trim())) {
            Intent intent = new Intent();
            a(intent, uMessage);
            intent.setClassName(context, uMessage.activity);
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    public void launchApp(Context context, UMessage uMessage) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        UMLog uMLog;
        if (launchIntentForPackage == null) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, new String[]{"找不到应用: " + context.getPackageName()});
            return;
        }
        launchIntentForPackage.setPackage(null);
        launchIntentForPackage.addFlags(268435456);
        a(launchIntentForPackage, uMessage);
        context.startActivity(launchIntentForPackage);
        uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 0, new String[]{"启动应用: " + context.getPackageName()});
    }

    public void dealWithCustomAction(Context context, UMessage uMessage) {
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
