package com.taobao.accs.internal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.strategy.StrategyCenter;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.stub.StubApp;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.ILoginInfo;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.data.Message;
import com.taobao.accs.data.Message.ReqType;
import com.taobao.accs.data.d;
import com.taobao.accs.net.b;
import com.taobao.accs.net.k;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.i;
import com.taobao.accs.utl.i.a;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public class ACCSManagerImpl implements IACCSManager {
    public b a;
    private int b = 0;
    private String c;
    private String d = "ACCSMgrImpl_";

    public ACCSManagerImpl(Context context, String str) {
        GlobalClientInfo.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.a = new k(GlobalClientInfo.a, 1, str);
        this.c = str;
        this.d += this.a.m;
    }

    public void bindApp(Context context, String str, String str2, IAppReceiver iAppReceiver) {
        bindApp(context, str, "accs", str2, iAppReceiver);
    }

    public void bindApp(Context context, String str, String str2, String str3, IAppReceiver iAppReceiver) {
        boolean z = true;
        if (context != null) {
            ALog.d(this.d, "bindApp APPKEY:" + str, new Object[0]);
            Message a = Message.a(context.getPackageName(), 1);
            if (UtilityImpl.getFocusDisableStatus(context)) {
                ALog.e(this.d, "accs disabled, try enable", new Object[0]);
                UtilityImpl.focusEnableService(context);
            }
            if (this.a.k() && TextUtils.isEmpty(this.a.i.getAppSecret())) {
                this.a.b(a, -15);
            } else if (TextUtils.isEmpty(str)) {
                this.a.b(a, -14);
            } else {
                this.a.a = str3;
                this.a.b = str;
                UtilityImpl.saveAppKey(context, str, this.a.i.getAppSecret());
                if (iAppReceiver != null) {
                    GlobalClientInfo.getInstance(context).setAppReceiver(this.c, iAppReceiver);
                }
                UtilityImpl.enableService(context);
                Intent a2 = a(context, 1);
                if (a2 != null) {
                    try {
                        String str4 = GlobalClientInfo.getInstance(context).getPackageInfo().versionName;
                        if (!(UtilityImpl.appVersionChanged(context) || UtilityImpl.utdidChanged(Constants.SP_FILE_NAME, context))) {
                            z = false;
                        }
                        if (z) {
                            a2.putExtra(Constants.KEY_FOUCE_BIND, true);
                        }
                        a2.putExtra(Constants.KEY_APP_KEY, str);
                        a2.putExtra(Constants.KEY_TTID, str3);
                        a2.putExtra(Constants.KEY_APP_VERSION, str4);
                        a2.putExtra("app_sercet", this.a.i.getAppSecret());
                        if (UtilityImpl.isMainProcess(context)) {
                            a(context, Message.a(this.a, context, a2), 1, z);
                        }
                        this.a.b(StubApp.getOrigApplicationContext(context.getApplicationContext()));
                        i.a(new String[]{"accs"}, new a());
                        i.f();
                        i.e();
                    } catch (Throwable th) {
                        ALog.e(this.d, "bindApp exception", th, new Object[0]);
                    }
                }
            }
        }
    }

    private void a(android.content.Context r9, com.taobao.accs.data.Message r10, int r11, boolean r12) {
        /*
        r8 = this;
        r7 = 2;
        r6 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r2 = 1;
        r1 = 0;
        r0 = r8.a;
        r0.a();
        if (r10 != 0) goto L_0x0025;
    L_0x000c:
        r0 = r8.d;
        r2 = "message is null";
        r1 = new java.lang.Object[r1];
        com.taobao.accs.utl.ALog.e(r0, r2, r1);
        r0 = r9.getPackageName();
        r0 = com.taobao.accs.data.Message.a(r0, r11);
        r1 = r8.a;
        r2 = -2;
        r1.b(r0, r2);
    L_0x0024:
        return;
    L_0x0025:
        switch(r11) {
            case 1: goto L_0x0046;
            case 2: goto L_0x007e;
            case 3: goto L_0x00b5;
            default: goto L_0x0028;
        };
    L_0x0028:
        r0 = r2;
    L_0x0029:
        if (r0 == 0) goto L_0x0024;
    L_0x002b:
        r0 = r8.d;
        r3 = "sendControlMessage";
        r4 = new java.lang.Object[r7];
        r5 = "command";
        r4[r1] = r5;
        r1 = java.lang.Integer.valueOf(r11);
        r4[r2] = r1;
        com.taobao.accs.utl.ALog.i(r0, r3, r4);
        r0 = r8.a;
        r0.b(r10, r2);
        goto L_0x0024;
    L_0x0046:
        r0 = r8.a;
        r0 = r0.j();
        r3 = r10.f();
        r0 = r0.c(r3);
        if (r0 == 0) goto L_0x0028;
    L_0x0056:
        if (r12 != 0) goto L_0x0028;
    L_0x0058:
        r0 = r8.d;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = r10.f();
        r3 = r3.append(r4);
        r4 = " isAppBinded";
        r3 = r3.append(r4);
        r3 = r3.toString();
        r4 = new java.lang.Object[r1];
        com.taobao.accs.utl.ALog.i(r0, r3, r4);
        r0 = r8.a;
        r0.b(r10, r6);
        r0 = r1;
        goto L_0x0029;
    L_0x007e:
        r0 = r8.a;
        r0 = r0.j();
        r3 = r10.f();
        r0 = r0.d(r3);
        if (r0 == 0) goto L_0x0028;
    L_0x008e:
        r0 = r8.d;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = r10.f();
        r3 = r3.append(r4);
        r4 = " isAppUnbinded";
        r3 = r3.append(r4);
        r3 = r3.toString();
        r4 = new java.lang.Object[r1];
        com.taobao.accs.utl.ALog.i(r0, r3, r4);
        r0 = r8.a;
        r0.b(r10, r6);
        r0 = r1;
        goto L_0x0029;
    L_0x00b5:
        r0 = r8.a;
        r0 = r0.j();
        r3 = r10.f();
        r4 = r10.E;
        r0 = r0.b(r3, r4);
        if (r0 == 0) goto L_0x0028;
    L_0x00c7:
        if (r12 != 0) goto L_0x0028;
    L_0x00c9:
        r0 = r8.d;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = r10.f();
        r3 = r3.append(r4);
        r4 = "/";
        r3 = r3.append(r4);
        r4 = r10.E;
        r3 = r3.append(r4);
        r4 = " isUserBinded";
        r3 = r3.append(r4);
        r3 = r3.toString();
        r4 = new java.lang.Object[r7];
        r5 = "isForceBind";
        r4[r1] = r5;
        r5 = java.lang.Boolean.valueOf(r12);
        r4[r2] = r5;
        com.taobao.accs.utl.ALog.i(r0, r3, r4);
        r0 = r8.a;
        r0.b(r10, r6);
        r0 = r1;
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.internal.ACCSManagerImpl.a(android.content.Context, com.taobao.accs.data.Message, int, boolean):void");
    }

    public void unbindApp(Context context) {
        ALog.e(this.d, "unbindApp" + UtilityImpl.getStackMsg(new Exception()), new Object[0]);
        if (!UtilityImpl.getFocusDisableStatus(context)) {
            Intent a = a(context, 2);
            if (a == null) {
                a(context, 2, null, null);
            } else if (UtilityImpl.isMainProcess(context)) {
                a(context, Message.b(this.a, context, a), 2, false);
            }
        }
    }

    public void bindUser(Context context, String str) {
        bindUser(context, str, false);
    }

    public void bindUser(Context context, String str, boolean z) {
        try {
            ALog.i(this.d, "bindUser", Parameters.SESSION_USER_ID, str);
            if (UtilityImpl.getFocusDisableStatus(context)) {
                ALog.e(this.d, "accs disabled", new Object[0]);
                return;
            }
            Intent a = a(context, 3);
            if (a == null) {
                ALog.e(this.d, "intent null", new Object[0]);
                a(context, 3, null, null);
                return;
            }
            Object i = this.a.i();
            if (TextUtils.isEmpty(i)) {
                ALog.e(this.d, "appKey null", new Object[0]);
                return;
            }
            if (UtilityImpl.appVersionChanged(context) || z) {
                ALog.i(this.d, "force bind User", new Object[0]);
                a.putExtra(Constants.KEY_FOUCE_BIND, true);
                z = true;
            }
            a.putExtra(Constants.KEY_APP_KEY, i);
            a.putExtra(Constants.KEY_USER_ID, str);
            if (UtilityImpl.isMainProcess(context)) {
                a(context, Message.c(this.a, a), 3, z);
            }
            this.a.b(StubApp.getOrigApplicationContext(context.getApplicationContext()));
        } catch (Throwable th) {
            ALog.e(this.d, "bindUser", th, new Object[0]);
        }
    }

    public void unbindUser(Context context) {
        if (!UtilityImpl.getFocusDisableStatus(context) && !UtilityImpl.getFocusDisableStatus(context)) {
            Intent a = a(context, 4);
            if (a == null) {
                a(context, 4, null, null);
                return;
            }
            Object i = this.a.i();
            if (!TextUtils.isEmpty(i)) {
                a.putExtra(Constants.KEY_APP_KEY, i);
                if (UtilityImpl.isMainProcess(context)) {
                    a(context, Message.d(this.a, a), 4, false);
                }
            }
        }
    }

    public void bindService(Context context, String str) {
        if (!UtilityImpl.getFocusDisableStatus(context) && !UtilityImpl.getFocusDisableStatus(context)) {
            Intent a = a(context, 5);
            if (a == null) {
                a(context, 5, str, null);
                return;
            }
            Object i = this.a.i();
            if (!TextUtils.isEmpty(i)) {
                a.putExtra(Constants.KEY_APP_KEY, i);
                a.putExtra(Constants.KEY_SERVICE_ID, str);
                if (UtilityImpl.isMainProcess(context)) {
                    a(context, Message.a(this.a, a), 5, false);
                }
                this.a.b(StubApp.getOrigApplicationContext(context.getApplicationContext()));
            }
        }
    }

    public void unbindService(Context context, String str) {
        if (!UtilityImpl.getFocusDisableStatus(context)) {
            Intent a = a(context, 6);
            if (a == null) {
                a(context, 6, str, null);
                return;
            }
            Object i = this.a.i();
            if (!TextUtils.isEmpty(i)) {
                a.putExtra(Constants.KEY_APP_KEY, i);
                a.putExtra(Constants.KEY_SERVICE_ID, str);
                if (UtilityImpl.isMainProcess(context)) {
                    a(context, Message.b(this.a, a), 6, false);
                }
            }
        }
    }

    public String sendData(Context context, String str, String str2, byte[] bArr, String str3) {
        return sendData(context, str, str2, bArr, str3, null);
    }

    public String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4) {
        return sendData(context, str, str2, bArr, str3, str4, null);
    }

    public String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url) {
        return sendData(context, new AccsRequest(str, str2, bArr, str3, str4, url, null));
    }

    public String sendData(Context context, AccsRequest accsRequest) {
        try {
            boolean focusDisableStatus = UtilityImpl.getFocusDisableStatus(context);
            if (!UtilityImpl.isMainProcess(context)) {
                ALog.e(this.d, "send data not in mainprocess", new Object[0]);
                return null;
            } else if (focusDisableStatus || accsRequest == null) {
                if (focusDisableStatus) {
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "accs disable");
                } else {
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "data null");
                }
                ALog.e(this.d, "send data dataInfo null or disable:" + focusDisableStatus, new Object[0]);
                return null;
            } else {
                if (TextUtils.isEmpty(accsRequest.dataId)) {
                    synchronized (ACCSManagerImpl.class) {
                        this.b++;
                        accsRequest.dataId = this.b + "";
                    }
                }
                if (TextUtils.isEmpty(this.a.i())) {
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "data appkey null");
                    ALog.e(this.d, "send data appkey null dataid:" + accsRequest.dataId, new Object[0]);
                    return null;
                }
                this.a.a();
                Message a = Message.a(this.a, context, context.getPackageName(), accsRequest);
                if (a.e() != null) {
                    a.e().onSend();
                }
                this.a.b(a, true);
                return accsRequest.dataId;
            }
        } catch (Throwable th) {
            com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "data " + th.toString());
            ALog.e(this.d, "send data dataid:" + accsRequest.dataId, th, new Object[0]);
        }
    }

    public String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4) {
        return sendRequest(context, str, str2, bArr, str3, str4, null);
    }

    public String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url) {
        return sendRequest(context, new AccsRequest(str, str2, bArr, str3, str4, url, null));
    }

    public String sendRequest(Context context, AccsRequest accsRequest, String str, boolean z) {
        if (accsRequest == null) {
            try {
                ALog.e(this.d, "sendRequest request null", new Object[0]);
                com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, null, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "request null");
                return null;
            } catch (Throwable th) {
                if (accsRequest != null) {
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "request " + th.toString());
                    ALog.e(this.d, "sendRequest dataid:" + accsRequest.dataId, th, new Object[0]);
                }
            }
        } else if (!UtilityImpl.isMainProcess(context)) {
            ALog.e(this.d, "send data not in mainprocess", new Object[0]);
            return null;
        } else if (UtilityImpl.getFocusDisableStatus(context)) {
            ALog.e(this.d, "sendRequest disable", new Object[0]);
            com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "accs disable");
            return null;
        } else {
            if (TextUtils.isEmpty(accsRequest.dataId)) {
                synchronized (ACCSManagerImpl.class) {
                    this.b++;
                    accsRequest.dataId = this.b + "";
                }
            }
            if (TextUtils.isEmpty(this.a.i())) {
                com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "request appkey null");
                ALog.e(this.d, "sendRequest appkey null dataid:" + accsRequest.dataId, new Object[0]);
                return null;
            }
            this.a.a();
            if (str == null) {
                str = context.getPackageName();
            }
            Message b = Message.b(this.a, context, str, accsRequest, z);
            if (b.e() != null) {
                b.e().onSend();
            }
            this.a.b(b, true);
            return accsRequest.dataId;
        }
    }

    public String sendRequest(Context context, AccsRequest accsRequest) {
        return sendRequest(context, accsRequest, null, true);
    }

    public String sendPushResponse(Context context, AccsRequest accsRequest, ExtraInfo extraInfo) {
        boolean z = true;
        if (context == null || accsRequest == null) {
            try {
                ALog.e(this.d, "sendPushResponse input null", com.umeng.analytics.pro.b.M, context, "response", accsRequest, "extraInfo", extraInfo);
                com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "sendPushResponse null");
            } catch (Throwable th) {
                com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "push response " + th.toString());
                ALog.e(this.d, "sendPushResponse dataid:" + accsRequest.dataId, th, new Object[0]);
            }
        } else {
            com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "push response total");
            if (UtilityImpl.getFocusDisableStatus(context)) {
                com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "sendPushResponse accs disable");
            } else {
                String i = this.a.i();
                if (TextUtils.isEmpty(i)) {
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "sendPushResponse appkey null");
                    ALog.e(this.d, "sendPushResponse appkey null dataid:" + accsRequest.dataId, new Object[0]);
                } else {
                    if (TextUtils.isEmpty(accsRequest.dataId)) {
                        synchronized (ACCSManagerImpl.class) {
                            this.b++;
                            accsRequest.dataId = this.b + "";
                        }
                    }
                    if (extraInfo == null) {
                        extraInfo = new ExtraInfo();
                    }
                    accsRequest.host = null;
                    extraInfo.fromPackage = context.getPackageName();
                    if (extraInfo.connType == 0 || extraInfo.fromHost == null) {
                        extraInfo.connType = 0;
                        ALog.w(this.d, "pushresponse use channel", "host", extraInfo.fromHost);
                        z = false;
                    }
                    ALog.i(this.d, "sendPushResponse", "sendbyInapp", Boolean.valueOf(z), "host", extraInfo.fromHost, Constants.KEY_ELECTION_PKG, extraInfo.fromPackage, Constants.KEY_DATA_ID, accsRequest.dataId);
                    Intent intent;
                    if (z) {
                        ALog.i(this.d, "sendPushResponse inapp by", PushConstants.EXTRA_APPLICATION_PENDING_INTENT, extraInfo.fromPackage);
                        accsRequest.host = new URL(extraInfo.fromHost);
                        if (context.getPackageName().equals(extraInfo.fromPackage) && UtilityImpl.isMainProcess(context)) {
                            sendRequest(context, accsRequest, context.getPackageName(), false);
                        } else {
                            intent = new Intent(Constants.ACTION_SEND);
                            intent.setClassName(extraInfo.fromPackage, com.taobao.accs.utl.a.msgService);
                            intent.putExtra(Constants.KEY_PACKAGE_NAME, context.getPackageName());
                            intent.putExtra(Constants.KEY_SEND_REQDATA, accsRequest);
                            intent.putExtra(Constants.KEY_APP_KEY, i);
                            intent.putExtra(Constants.KEY_CONFIG_TAG, this.c);
                            context.startService(intent);
                        }
                    } else {
                        intent = a(context, 100);
                        if (intent == null) {
                            com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "push response intent null");
                            a(context, 100, accsRequest.serviceId, accsRequest.dataId);
                            ALog.e(this.d, "sendPushResponse input null", com.umeng.analytics.pro.b.M, context, "response", accsRequest, "extraInfo", extraInfo);
                        } else {
                            ALog.i(this.d, "sendPushResponse channel by", PushConstants.EXTRA_APPLICATION_PENDING_INTENT, extraInfo.fromPackage);
                            intent.setClassName(extraInfo.fromPackage, com.taobao.accs.utl.a.channelService);
                            intent.putExtra(Constants.KEY_SEND_TYPE, ReqType.REQ);
                            intent.putExtra(Constants.KEY_APP_KEY, i);
                            intent.putExtra(Constants.KEY_USER_ID, accsRequest.userId);
                            intent.putExtra(Constants.KEY_SERVICE_ID, accsRequest.serviceId);
                            intent.putExtra("data", accsRequest.data);
                            intent.putExtra(Constants.KEY_DATA_ID, accsRequest.dataId);
                            intent.putExtra(Constants.KEY_CONFIG_TAG, this.c);
                            if (!TextUtils.isEmpty(accsRequest.businessId)) {
                                intent.putExtra(Constants.KEY_BUSINESSID, accsRequest.businessId);
                            }
                            if (!TextUtils.isEmpty(accsRequest.tag)) {
                                intent.putExtra(Constants.KEY_EXT_TAG, accsRequest.tag);
                            }
                            if (accsRequest.target != null) {
                                intent.putExtra(Constants.KEY_TARGET, accsRequest.target);
                            }
                            context.startService(intent);
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean isNetworkReachable(Context context) {
        return UtilityImpl.isNetworkConnected(context);
    }

    private Intent a(Context context, int i) {
        if (i == 1 || UtilityImpl.getServiceEnabled(context)) {
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_COMMAND);
            intent.setClassName(context.getPackageName(), com.taobao.accs.utl.a.channelService);
            intent.putExtra(Constants.KEY_PACKAGE_NAME, context.getPackageName());
            intent.putExtra("command", i);
            intent.putExtra(Constants.KEY_APP_KEY, this.a.b);
            intent.putExtra(Constants.KEY_CONFIG_TAG, this.c);
            return intent;
        }
        ALog.e(this.d, "getIntent null command:" + i + " serviceEnable:" + UtilityImpl.getServiceEnabled(context), new Object[0]);
        return null;
    }

    public void forceDisableService(Context context) {
        UtilityImpl.focusDisableService(context);
    }

    public void forceEnableService(Context context) {
        UtilityImpl.focusEnableService(context);
    }

    @Deprecated
    public void setMode(Context context, int i) {
        ACCSClient.setEnvironment(context, i);
    }

    private void a(Context context, int i, String str, String str2) {
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(context.getPackageName());
        intent.putExtra("command", i);
        intent.putExtra(Constants.KEY_SERVICE_ID, str);
        intent.putExtra(Constants.KEY_DATA_ID, str2);
        intent.putExtra(Constants.KEY_APP_KEY, this.a.b);
        intent.putExtra(Constants.KEY_CONFIG_TAG, this.c);
        intent.putExtra(Constants.KEY_ERROR_CODE, i == 2 ? 200 : 300);
        d.a(context, intent);
    }

    public void setProxy(Context context, String str, int i) {
        Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
        if (!TextUtils.isEmpty(str)) {
            edit.putString(Constants.KEY_PROXY_HOST, str);
        }
        edit.putInt(Constants.KEY_PROXY_PORT, i);
        edit.apply();
    }

    public void startInAppConnection(Context context, String str, String str2, IAppReceiver iAppReceiver) {
        startInAppConnection(context, str, null, str2, iAppReceiver);
    }

    public void startInAppConnection(Context context, String str, String str2, String str3, IAppReceiver iAppReceiver) {
        GlobalClientInfo.getInstance(context).setAppReceiver(this.c, iAppReceiver);
        if (UtilityImpl.isMainProcess(context)) {
            ALog.d(this.d, "startInAppConnection APPKEY:" + str, new Object[0]);
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.equals(this.a.i(), str)) {
                    this.a.a = str3;
                    this.a.b = str;
                    UtilityImpl.saveAppKey(context, str, this.a.i.getAppSecret());
                }
                this.a.a();
                return;
            }
            return;
        }
        ALog.d(this.d, "inapp only init in main process!", new Object[0]);
    }

    public void setLoginInfo(Context context, ILoginInfo iLoginInfo) {
        GlobalClientInfo.getInstance(context).setLoginInfoImpl(this.a.m, iLoginInfo);
    }

    public void clearLoginInfo(Context context) {
        GlobalClientInfo.getInstance(context).clearLoginInfoImpl();
    }

    public boolean cancel(Context context, String str) {
        return this.a.a(str);
    }

    public Map<String, Boolean> getChannelState() throws Exception {
        String userUnit = getUserUnit();
        b bVar = this.a;
        GlobalClientInfo.getContext();
        String b = bVar.b(null);
        Map<String, Boolean> hashMap = new HashMap();
        hashMap.put(userUnit, Boolean.valueOf(false));
        hashMap.put(b, Boolean.valueOf(false));
        Session throwsException = SessionCenter.getInstance(this.a.i.getAppKey()).getThrowsException(b, OkHttpUtils.DEFAULT_MILLISECONDS);
        Session throwsException2 = SessionCenter.getInstance(this.a.i.getAppKey()).getThrowsException(userUnit, OkHttpUtils.DEFAULT_MILLISECONDS);
        if (throwsException != null) {
            hashMap.put(b, Boolean.valueOf(true));
        }
        if (throwsException2 != null) {
            hashMap.put(userUnit, Boolean.valueOf(true));
        }
        ALog.d(this.d, "getChannelState " + hashMap.toString(), new Object[0]);
        return hashMap;
    }

    public Map<String, Boolean> forceReConnectChannel() throws Exception {
        SessionCenter.getInstance(this.a.i.getAppKey()).forceRecreateAccsSession();
        return getChannelState();
    }

    public String getUserUnit() {
        Context context = GlobalClientInfo.getContext();
        if (context == null) {
            ALog.e(this.d, "context is null", new Object[0]);
            return null;
        }
        String b = this.a.b(StrategyCenter.getInstance().getUnitPrefix(GlobalClientInfo.getInstance(context).getUserId(this.a.m), UtilityImpl.getDeviceId(context)));
        if (!ALog.isPrintLog(Level.D)) {
            return b;
        }
        ALog.d(this.d, "getUserUnit " + b, new Object[0]);
        return b;
    }

    public boolean isChannelError(int i) {
        return ErrorCode.isChannelError(i);
    }

    public void registerSerivce(Context context, String str, String str2) {
        GlobalClientInfo.getInstance(context).registerService(str, str2);
    }

    public void unRegisterSerivce(Context context, String str) {
        GlobalClientInfo.getInstance(context).unRegisterService(str);
    }

    public void registerDataListener(Context context, String str, AccsAbstractDataListener accsAbstractDataListener) {
        GlobalClientInfo.getInstance(context).registerListener(str, accsAbstractDataListener);
    }

    public void unRegisterDataListener(Context context, String str) {
        GlobalClientInfo.getInstance(context).unregisterListener(str);
    }

    public void sendBusinessAck(String str, String str2, String str3, short s, String str4, Map<Integer, String> map) {
        this.a.b(Message.a(this.a, str, str2, str3, true, s, str4, map), true);
    }

    public void updateConfig(AccsClientConfig accsClientConfig) {
        if (this.a instanceof k) {
            ((k) this.a).a(accsClientConfig);
        }
    }
}
