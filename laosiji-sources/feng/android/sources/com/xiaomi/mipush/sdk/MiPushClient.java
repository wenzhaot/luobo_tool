package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PermissionInfo;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.analytics.pro.j.a;
import com.umeng.message.MsgConstant;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.string.c;
import com.xiaomi.xmpush.thrift.f;
import com.xiaomi.xmpush.thrift.i;
import com.xiaomi.xmpush.thrift.j;
import com.xiaomi.xmpush.thrift.o;
import com.xiaomi.xmpush.thrift.q;
import com.xiaomi.xmpush.thrift.s;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public abstract class MiPushClient {
    public static final String ACCEPT_TIME_SEPARATOR = ",";
    public static final String COMMAND_REGISTER = "register";
    public static final String COMMAND_SET_ACCEPT_TIME = "accept-time";
    public static final String COMMAND_SET_ACCOUNT = "set-account";
    public static final String COMMAND_SET_ALIAS = "set-alias";
    public static final String COMMAND_SUBSCRIBE_TOPIC = "subscribe-topic";
    public static final String COMMAND_UNSET_ACCOUNT = "unset-account";
    public static final String COMMAND_UNSET_ALIAS = "unset-alias";
    public static final String COMMAND_UNSUBSCRIBE_TOPIC = "unsubscibe-topic";
    private static boolean awakeService = true;
    private static Context sContext;
    private static long sCurMsgId = System.currentTimeMillis();

    @Deprecated
    public static abstract class MiPushClientCallback {
        private String category;

        protected String getCategory() {
            return this.category;
        }

        public void onCommandResult(String str, long j, String str2, List<String> list) {
        }

        public void onInitializeResult(long j, String str, String str2) {
        }

        public void onReceiveMessage(MiPushMessage miPushMessage) {
        }

        public void onReceiveMessage(String str, String str2, String str3, boolean z) {
        }

        public void onSubscribeResult(long j, String str, String str2) {
        }

        public void onUnsubscribeResult(long j, String str, String str2) {
        }

        protected void setCategory(String str) {
            this.category = str;
        }
    }

    private static boolean acceptTimeSet(Context context, String str, String str2) {
        return TextUtils.equals(context.getSharedPreferences("mipush_extra", 0).getString("accept_time", ""), str + ACCEPT_TIME_SEPARATOR + str2);
    }

    public static long accountSetTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("account_" + str, -1);
    }

    static synchronized void addAcceptTime(Context context, String str, String str2) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putString("accept_time", str + ACCEPT_TIME_SEPARATOR + str2).commit();
        }
    }

    static synchronized void addAccount(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("account_" + str, System.currentTimeMillis()).commit();
        }
    }

    static synchronized void addAlias(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("alias_" + str, System.currentTimeMillis()).commit();
        }
    }

    private static void addPullNotificationTime(Context context) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("last_pull_notification", System.currentTimeMillis()).commit();
    }

    private static void addRegRequestTime(Context context) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("last_reg_request", System.currentTimeMillis()).commit();
    }

    static synchronized void addTopic(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("topic_" + str, System.currentTimeMillis()).commit();
        }
    }

    public static long aliasSetTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("alias_" + str, -1);
    }

    private static void awakePushServices(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        if (System.currentTimeMillis() - 600000 >= sharedPreferences.getLong("wake_up", 0)) {
            sharedPreferences.edit().putLong("wake_up", System.currentTimeMillis()).commit();
            new Thread(new d(context)).start();
        }
    }

    public static void checkManifest(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), a.d);
            checkReceivers(context);
            checkServices(context, packageInfo);
            checkPermissions(context, packageInfo);
        } catch (Throwable e) {
            b.a(e);
        }
    }

    private static void checkNotNull(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException("param " + str + " is not nullable");
        }
    }

    private static void checkPermissions(Context context, PackageInfo packageInfo) {
        int i;
        Set hashSet = new HashSet();
        hashSet.addAll(Arrays.asList(new String[]{MsgConstant.PERMISSION_INTERNET, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE, context.getPackageName() + ".permission.MIPUSH_RECEIVE", MsgConstant.PERMISSION_ACCESS_WIFI_STATE, MsgConstant.PERMISSION_READ_PHONE_STATE, MsgConstant.PERMISSION_GET_TASKS, "android.permission.VIBRATE"}));
        if (packageInfo.permissions != null) {
            for (PermissionInfo permissionInfo : packageInfo.permissions) {
                if (r4.equals(permissionInfo.name)) {
                    i = 1;
                    break;
                }
            }
        }
        i = 0;
        if (i == 0) {
            throw new a(String.format("<permission android:name=\"%1$s\" /> is undefined.", new Object[]{r4}), null);
        }
        if (packageInfo.requestedPermissions != null) {
            for (CharSequence charSequence : packageInfo.requestedPermissions) {
                if (!TextUtils.isEmpty(charSequence) && hashSet.contains(charSequence)) {
                    hashSet.remove(charSequence);
                    if (hashSet.isEmpty()) {
                        break;
                    }
                }
            }
        }
        if (!hashSet.isEmpty()) {
            throw new a(String.format("<use-permission android:name=\"%1$s\" /> is missing.", new Object[]{hashSet.iterator().next()}), null);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ab A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0093 A:{SYNTHETIC, EDGE_INSN: B:29:0x0093->B:17:0x0093 ?: BREAK  } */
    private static void checkReceivers(android.content.Context r9) {
        /*
        r6 = 2;
        r2 = 0;
        r3 = 1;
        r1 = r9.getPackageManager();
        r4 = r9.getPackageName();
        r0 = new android.content.Intent;
        r5 = "android.net.conn.CONNECTIVITY_CHANGE";
        r0.<init>(r5);
        r0.setPackage(r4);
        r5 = com.xiaomi.push.service.receivers.NetworkStatusReceiver.class;
        r6 = new java.lang.Boolean[r6];
        r7 = java.lang.Boolean.valueOf(r3);
        r6[r2] = r7;
        r7 = java.lang.Boolean.valueOf(r3);
        r6[r3] = r7;
        findAndCheckReceiverInfo(r1, r0, r5, r6);
        r0 = new android.content.Intent;
        r5 = com.xiaomi.push.service.c.o;
        r0.<init>(r5);
        r0.setPackage(r4);
        r5 = "com.xiaomi.push.service.receivers.PingReceiver";
        r5 = java.lang.Class.forName(r5);	 Catch:{ ClassNotFoundException -> 0x009f }
        r6 = 2;
        r6 = new java.lang.Boolean[r6];	 Catch:{ ClassNotFoundException -> 0x009f }
        r7 = 0;
        r8 = 1;
        r8 = java.lang.Boolean.valueOf(r8);	 Catch:{ ClassNotFoundException -> 0x009f }
        r6[r7] = r8;	 Catch:{ ClassNotFoundException -> 0x009f }
        r7 = 1;
        r8 = 0;
        r8 = java.lang.Boolean.valueOf(r8);	 Catch:{ ClassNotFoundException -> 0x009f }
        r6[r7] = r8;	 Catch:{ ClassNotFoundException -> 0x009f }
        findAndCheckReceiverInfo(r1, r0, r5, r6);	 Catch:{ ClassNotFoundException -> 0x009f }
    L_0x0050:
        r0 = new android.content.Intent;
        r5 = "com.xiaomi.mipush.RECEIVE_MESSAGE";
        r0.<init>(r5);
        r0.setPackage(r4);
        r4 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
        r0 = r1.queryBroadcastReceivers(r0, r4);
        r4 = r0.iterator();
        r1 = r2;
    L_0x0066:
        r0 = r4.hasNext();
        if (r0 == 0) goto L_0x00ae;
    L_0x006c:
        r0 = r4.next();
        r0 = (android.content.pm.ResolveInfo) r0;
        r0 = r0.activityInfo;
        if (r0 == 0) goto L_0x00a4;
    L_0x0076:
        r5 = r0.name;	 Catch:{ ClassNotFoundException -> 0x00a6 }
        r5 = android.text.TextUtils.isEmpty(r5);	 Catch:{ ClassNotFoundException -> 0x00a6 }
        if (r5 != 0) goto L_0x00a4;
    L_0x007e:
        r5 = com.xiaomi.mipush.sdk.PushMessageReceiver.class;
        r6 = r0.name;	 Catch:{ ClassNotFoundException -> 0x00a6 }
        r6 = java.lang.Class.forName(r6);	 Catch:{ ClassNotFoundException -> 0x00a6 }
        r5 = r5.isAssignableFrom(r6);	 Catch:{ ClassNotFoundException -> 0x00a6 }
        if (r5 == 0) goto L_0x00a4;
    L_0x008c:
        r0 = r0.enabled;	 Catch:{ ClassNotFoundException -> 0x00a6 }
        if (r0 == 0) goto L_0x00a4;
    L_0x0090:
        r0 = r3;
    L_0x0091:
        if (r0 == 0) goto L_0x00ab;
    L_0x0093:
        if (r0 != 0) goto L_0x00ad;
    L_0x0095:
        r0 = new com.xiaomi.mipush.sdk.MiPushClient$a;
        r1 = "Receiver: none of the subclasses of PushMessageReceiver is enabled or defined.";
        r2 = 0;
        r0.<init>(r1, r2);
        throw r0;
    L_0x009f:
        r0 = move-exception;
        com.xiaomi.channel.commonutils.logger.b.a(r0);
        goto L_0x0050;
    L_0x00a4:
        r0 = r2;
        goto L_0x0091;
    L_0x00a6:
        r0 = move-exception;
        com.xiaomi.channel.commonutils.logger.b.a(r0);
        r0 = r1;
    L_0x00ab:
        r1 = r0;
        goto L_0x0066;
    L_0x00ad:
        return;
    L_0x00ae:
        r0 = r1;
        goto L_0x0093;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.MiPushClient.checkReceivers(android.content.Context):void");
    }

    private static void checkServices(Context context, PackageInfo packageInfo) {
        Map hashMap = new HashMap();
        hashMap.put("com.xiaomi.push.service.XMPushService", new Boolean[]{Boolean.valueOf(true), Boolean.valueOf(false)});
        hashMap.put(PushMessageHandler.class.getCanonicalName(), new Boolean[]{Boolean.valueOf(true), Boolean.valueOf(true)});
        hashMap.put(MessageHandleService.class.getCanonicalName(), new Boolean[]{Boolean.valueOf(true), Boolean.valueOf(false)});
        if (packageInfo.services != null) {
            for (PackageItemInfo packageItemInfo : packageInfo.services) {
                if (!TextUtils.isEmpty(packageItemInfo.name) && hashMap.containsKey(packageItemInfo.name)) {
                    Boolean[] boolArr = (Boolean[]) hashMap.remove(packageItemInfo.name);
                    if (boolArr[0].booleanValue() != packageItemInfo.enabled) {
                        throw new a(String.format("Wrong attribute: %n    <service android:name=\"%1$s\" .../> android:enabled should be %<b.", new Object[]{packageItemInfo.name, boolArr[0]}), packageItemInfo);
                    } else if (boolArr[1].booleanValue() != packageItemInfo.exported) {
                        throw new a(String.format("Wrong attribute: %n    <service android:name=\"%1$s\" .../> android:exported should be %<b.", new Object[]{packageItemInfo.name, boolArr[1]}), packageItemInfo);
                    } else if (hashMap.isEmpty()) {
                        break;
                    }
                }
            }
        }
        if (!hashMap.isEmpty()) {
            throw new a(String.format("<service android:name=\"%1$s\" /> is missing or disabled.", new Object[]{hashMap.keySet().iterator().next()}), null);
        }
    }

    protected static void clearExtras(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        long j = sharedPreferences.getLong("wake_up", 0);
        Editor edit = sharedPreferences.edit();
        edit.clear();
        if (j > 0) {
            edit.putLong("wake_up", j);
        }
        edit.commit();
    }

    public static void clearLocalNotificationType(Context context) {
        g.a(context).e();
    }

    public static void clearNotification(Context context) {
        g.a(context).a(-1);
    }

    public static void clearNotification(Context context, int i) {
        g.a(context).a(i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0084 A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x006e  */
    private static void findAndCheckReceiverInfo(android.content.pm.PackageManager r7, android.content.Intent r8, java.lang.Class<?> r9, java.lang.Boolean[] r10) {
        /*
        r6 = 2;
        r1 = 1;
        r2 = 0;
        r0 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
        r0 = r7.queryBroadcastReceivers(r8, r0);
        r3 = r0.iterator();
    L_0x000d:
        r0 = r3.hasNext();
        if (r0 == 0) goto L_0x0085;
    L_0x0013:
        r0 = r3.next();
        r0 = (android.content.pm.ResolveInfo) r0;
        r0 = r0.activityInfo;
        if (r0 == 0) goto L_0x000d;
    L_0x001d:
        r4 = r9.getCanonicalName();
        r5 = r0.name;
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x000d;
    L_0x0029:
        r3 = r10[r2];
        r3 = r3.booleanValue();
        r4 = r0.enabled;
        if (r3 == r4) goto L_0x004a;
    L_0x0033:
        r3 = new com.xiaomi.mipush.sdk.MiPushClient$a;
        r4 = "Wrong attribute: %n    <receiver android:name=\"%1$s\" .../> android:enabled should be %<b.";
        r5 = new java.lang.Object[r6];
        r6 = r0.name;
        r5[r2] = r6;
        r2 = r10[r2];
        r5[r1] = r2;
        r1 = java.lang.String.format(r4, r5);
        r3.<init>(r1, r0);
        throw r3;
    L_0x004a:
        r3 = r10[r1];
        r3 = r3.booleanValue();
        r4 = r0.exported;
        if (r3 == r4) goto L_0x006b;
    L_0x0054:
        r3 = new com.xiaomi.mipush.sdk.MiPushClient$a;
        r4 = "Wrong attribute: %n    <receiver android:name=\"%1$s\" .../> android:exported should be %<b.";
        r5 = new java.lang.Object[r6];
        r6 = r0.name;
        r5[r2] = r6;
        r2 = r10[r1];
        r5[r1] = r2;
        r1 = java.lang.String.format(r4, r5);
        r3.<init>(r1, r0);
        throw r3;
    L_0x006b:
        r0 = r1;
    L_0x006c:
        if (r0 != 0) goto L_0x0084;
    L_0x006e:
        r0 = new com.xiaomi.mipush.sdk.MiPushClient$a;
        r3 = "<receiver android:name=\"%1$s\" /> is missing or disabled.";
        r1 = new java.lang.Object[r1];
        r4 = r9.getCanonicalName();
        r1[r2] = r4;
        r1 = java.lang.String.format(r3, r1);
        r2 = 0;
        r0.<init>(r1, r2);
        throw r0;
    L_0x0084:
        return;
    L_0x0085:
        r0 = r2;
        goto L_0x006c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.MiPushClient.findAndCheckReceiverInfo(android.content.pm.PackageManager, android.content.Intent, java.lang.Class, java.lang.Boolean[]):void");
    }

    protected static synchronized String generatePacketID() {
        String str;
        synchronized (MiPushClient.class) {
            str = c.a(4) + sCurMsgId;
            sCurMsgId++;
        }
        return str;
    }

    public static List<String> getAllAlias(Context context) {
        List<String> arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("alias_")) {
                arrayList.add(str.substring("alias_".length()));
            }
        }
        return arrayList;
    }

    public static List<String> getAllTopic(Context context) {
        List<String> arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("topic_") && !str.contains("**ALL**")) {
                arrayList.add(str.substring("topic_".length()));
            }
        }
        return arrayList;
    }

    public static List<String> getAllUserAccount(Context context) {
        List<String> arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("account_")) {
                arrayList.add(str.substring("account_".length()));
            }
        }
        return arrayList;
    }

    public static String getRegId(Context context) {
        return a.a(context).i() ? a.a(context).e() : null;
    }

    @Deprecated
    public static void initialize(Context context, String str, String str2, MiPushClientCallback miPushClientCallback) {
        boolean z = false;
        checkNotNull(context, com.umeng.analytics.pro.b.M);
        checkNotNull(str, "appID");
        checkNotNull(str2, "appToken");
        try {
            sContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
            if (sContext == null) {
                sContext = context;
            }
            if (miPushClientCallback != null) {
                PushMessageHandler.a(miPushClientCallback);
            }
            if (a.a(sContext).m() != Constants.a()) {
                z = true;
            }
            if (z || shouldSendRegRequest(sContext)) {
                if (z || !a.a(sContext).a(str, str2) || a.a(sContext).n()) {
                    String a = c.a(6);
                    a.a(sContext).h();
                    a.a(sContext).a(Constants.a());
                    a.a(sContext).a(str, str2, a);
                    clearExtras(sContext);
                    j jVar = new j();
                    jVar.a(generatePacketID());
                    jVar.b(str);
                    jVar.e(str2);
                    jVar.d(context.getPackageName());
                    jVar.f(a);
                    jVar.c(a.a(context, context.getPackageName()));
                    jVar.g("2_2_22");
                    jVar.a(20222);
                    g.a(sContext).a(jVar, z);
                } else {
                    if (1 == PushMessageHelper.getPushMode(context)) {
                        checkNotNull(miPushClientCallback, "callback");
                        miPushClientCallback.onInitializeResult(0, null, a.a(context).e());
                    } else {
                        List arrayList = new ArrayList();
                        arrayList.add(a.a(context).e());
                        PushMessageHelper.sendCommandMessageBroadcast(sContext, PushMessageHelper.generateCommandMessage("register", arrayList, 0, null, null));
                    }
                    g.a(context).a();
                    if (a.a(sContext).a()) {
                        i iVar = new i();
                        iVar.b(a.a(context).c());
                        iVar.c("client_info_update");
                        iVar.a(generatePacketID());
                        iVar.h = new HashMap();
                        iVar.h.put("app_version", a.a(sContext, sContext.getPackageName()));
                        iVar.h.put("push_sdk_vn", "2_2_22");
                        iVar.h.put("push_sdk_vc", Integer.toString(20222));
                        CharSequence g = a.a(sContext).g();
                        if (!TextUtils.isEmpty(g)) {
                            iVar.h.put("deviceid", g);
                        }
                        g.a(context).a(iVar, com.xiaomi.xmpush.thrift.a.i, false, null);
                    }
                    if (!com.xiaomi.channel.commonutils.android.a.a(sContext, "update_devId", false)) {
                        updateIMEI();
                        com.xiaomi.channel.commonutils.android.a.b(sContext, "update_devId", true);
                    }
                    if (shouldUseMIUIPush(sContext) && shouldPullNotification(sContext)) {
                        i iVar2 = new i();
                        iVar2.b(a.a(sContext).c());
                        iVar2.c("pull");
                        iVar2.a(generatePacketID());
                        iVar2.a(false);
                        g.a(sContext).a(iVar2, com.xiaomi.xmpush.thrift.a.i, false, null, false);
                        addPullNotificationTime(sContext);
                    }
                }
                if (awakeService) {
                    awakePushServices(sContext);
                }
                addRegRequestTime(sContext);
                return;
            }
            g.a(context).a();
            b.a("Could not send  register message within 5s repeatly .");
        } catch (Throwable th) {
            b.a(th);
        }
    }

    public static void pausePush(Context context, String str) {
        setAcceptTime(context, 0, 0, 0, 0, str);
    }

    static void reInitialize(Context context) {
        if (a.a(context).i()) {
            String a = c.a(6);
            String c = a.a(context).c();
            String d = a.a(context).d();
            a.a(context).h();
            a.a(context).a(c, d, a);
            j jVar = new j();
            jVar.a(generatePacketID());
            jVar.b(c);
            jVar.e(d);
            jVar.f(a);
            jVar.d(context.getPackageName());
            jVar.c(a.a(context, context.getPackageName()));
            g.a(context).a(jVar, false);
        }
    }

    public static void registerPush(Context context, String str, String str2) {
        new Thread(new b(context, str, str2)).start();
    }

    static synchronized void removeAccount(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().remove("account_" + str).commit();
        }
    }

    static synchronized void removeAlias(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().remove("alias_" + str).commit();
        }
    }

    static synchronized void removeTopic(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().remove("topic_" + str).commit();
        }
    }

    static void reportIgnoreRegMessageClicked(Context context, String str, com.xiaomi.xmpush.thrift.c cVar, String str2, String str3) {
        i iVar = new i();
        if (TextUtils.isEmpty(str3)) {
            b.c("do not report clicked message");
            return;
        }
        iVar.b(str3);
        iVar.c("bar:click");
        iVar.a(str);
        iVar.a(false);
        g.a(context).a(iVar, com.xiaomi.xmpush.thrift.a.i, false, true, cVar, true, str2, str3);
    }

    public static void reportMessageClicked(Context context, MiPushMessage miPushMessage) {
        com.xiaomi.xmpush.thrift.c cVar = new com.xiaomi.xmpush.thrift.c();
        cVar.a(miPushMessage.getMessageId());
        cVar.b(miPushMessage.getTopic());
        cVar.d(miPushMessage.getDescription());
        cVar.c(miPushMessage.getTitle());
        cVar.c(miPushMessage.getNotifyId());
        cVar.a(miPushMessage.getNotifyType());
        cVar.b(miPushMessage.getPassThrough());
        cVar.a(miPushMessage.getExtra());
        reportMessageClicked(context, miPushMessage.getMessageId(), cVar, null);
    }

    @Deprecated
    public static void reportMessageClicked(Context context, String str) {
        reportMessageClicked(context, str, null, null);
    }

    static void reportMessageClicked(Context context, String str, com.xiaomi.xmpush.thrift.c cVar, String str2) {
        i iVar = new i();
        if (!TextUtils.isEmpty(str2)) {
            iVar.b(str2);
        } else if (a.a(context).b()) {
            iVar.b(a.a(context).c());
        } else {
            b.c("do not report clicked message");
            return;
        }
        iVar.c("bar:click");
        iVar.a(str);
        iVar.a(false);
        g.a(context).a(iVar, com.xiaomi.xmpush.thrift.a.i, false, cVar);
    }

    public static void resumePush(Context context, String str) {
        setAcceptTime(context, 0, 0, 23, 59, str);
    }

    public static void setAcceptTime(Context context, int i, int i2, int i3, int i4, String str) {
        if (i < 0 || i >= 24 || i3 < 0 || i3 >= 24 || i2 < 0 || i2 >= 60 || i4 < 0 || i4 >= 60) {
            throw new IllegalArgumentException("the input parameter is not valid.");
        }
        long rawOffset = (long) (((TimeZone.getTimeZone("GMT+08").getRawOffset() - TimeZone.getDefault().getRawOffset()) / 1000) / 60);
        long j = ((((long) ((i * 60) + i2)) + rawOffset) + 1440) % 1440;
        rawOffset = ((rawOffset + ((long) ((i3 * 60) + i4))) + 1440) % 1440;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format("%1$02d:%2$02d", new Object[]{Long.valueOf(j / 60), Long.valueOf(j % 60)}));
        arrayList.add(String.format("%1$02d:%2$02d", new Object[]{Long.valueOf(rawOffset / 60), Long.valueOf(rawOffset % 60)}));
        List arrayList2 = new ArrayList();
        arrayList2.add(String.format("%1$02d:%2$02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        arrayList2.add(String.format("%1$02d:%2$02d", new Object[]{Integer.valueOf(i3), Integer.valueOf(i4)}));
        if (!acceptTimeSet(context, (String) arrayList.get(0), (String) arrayList.get(1))) {
            setCommand(context, COMMAND_SET_ACCEPT_TIME, arrayList, str);
        } else if (1 == PushMessageHelper.getPushMode(context)) {
            PushMessageHandler.a(context, str, COMMAND_SET_ACCEPT_TIME, 0, null, arrayList2);
        } else {
            PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(COMMAND_SET_ACCEPT_TIME, arrayList2, 0, null, null));
        }
    }

    public static void setAlias(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            setCommand(context, COMMAND_SET_ALIAS, str, str2);
        }
    }

    protected static void setCommand(Context context, String str, String str2, String str3) {
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
        }
        if (!COMMAND_SET_ALIAS.equalsIgnoreCase(str) || System.currentTimeMillis() - aliasSetTime(context, str2) >= 3600000) {
            if (COMMAND_UNSET_ALIAS.equalsIgnoreCase(str) && aliasSetTime(context, str2) < 0) {
                b.a("Don't cancel alias for " + arrayList + " is unseted");
            } else if (!COMMAND_SET_ACCOUNT.equalsIgnoreCase(str) || System.currentTimeMillis() - accountSetTime(context, str2) >= 3600000) {
                if (!COMMAND_UNSET_ACCOUNT.equalsIgnoreCase(str) || accountSetTime(context, str2) >= 0) {
                    setCommand(context, str, arrayList, str3);
                } else {
                    b.a("Don't cancel account for " + arrayList + " is unseted");
                }
            } else if (1 == PushMessageHelper.getPushMode(context)) {
                PushMessageHandler.a(context, str3, str, 0, null, arrayList);
            } else {
                PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(COMMAND_SET_ACCOUNT, arrayList, 0, null, null));
            }
        } else if (1 == PushMessageHelper.getPushMode(context)) {
            PushMessageHandler.a(context, str3, str, 0, null, arrayList);
        } else {
            PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(COMMAND_SET_ALIAS, arrayList, 0, null, null));
        }
    }

    protected static void setCommand(Context context, String str, ArrayList<String> arrayList, String str2) {
        if (!TextUtils.isEmpty(a.a(context).c())) {
            f fVar = new f();
            fVar.a(generatePacketID());
            fVar.b(a.a(context).c());
            fVar.c(str);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                fVar.d((String) it.next());
            }
            fVar.f(str2);
            fVar.e(context.getPackageName());
            g.a(context).a(fVar, com.xiaomi.xmpush.thrift.a.j, null);
        }
    }

    public static void setLocalNotificationType(Context context, int i) {
        g.a(context).b(i & -1);
    }

    public static void setUserAccount(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            setCommand(context, COMMAND_SET_ACCOUNT, str, str2);
        }
    }

    private static boolean shouldPullNotification(Context context) {
        return System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_pull_notification", -1) > 300000;
    }

    private static boolean shouldSendRegRequest(Context context) {
        return System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_reg_request", -1) > 5000;
    }

    public static boolean shouldUseMIUIPush(Context context) {
        return g.a(context).b();
    }

    public static void subscribe(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(a.a(context).c()) && !TextUtils.isEmpty(str)) {
            if (System.currentTimeMillis() - topicSubscribedTime(context, str) > 86400000) {
                o oVar = new o();
                oVar.a(generatePacketID());
                oVar.b(a.a(context).c());
                oVar.c(str);
                oVar.d(context.getPackageName());
                oVar.e(str2);
                g.a(context).a(oVar, com.xiaomi.xmpush.thrift.a.c, null);
            } else if (1 == PushMessageHelper.getPushMode(context)) {
                PushMessageHandler.a(context, str2, 0, null, str);
            } else {
                List arrayList = new ArrayList();
                arrayList.add(str);
                PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(COMMAND_SUBSCRIBE_TOPIC, arrayList, 0, null, null));
            }
        }
    }

    public static long topicSubscribedTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("topic_" + str, -1);
    }

    public static void unregisterPush(Context context) {
        if (a.a(context).b()) {
            q qVar = new q();
            qVar.a(generatePacketID());
            qVar.b(a.a(context).c());
            qVar.c(a.a(context).e());
            qVar.e(a.a(context).d());
            qVar.d(context.getPackageName());
            g.a(context).a(qVar);
            PushMessageHandler.a();
            a.a(context).k();
            clearExtras(context);
            clearLocalNotificationType(context);
            clearNotification(context);
        }
    }

    public static void unsetAlias(Context context, String str, String str2) {
        setCommand(context, COMMAND_UNSET_ALIAS, str, str2);
    }

    public static void unsetUserAccount(Context context, String str, String str2) {
        setCommand(context, COMMAND_UNSET_ACCOUNT, str, str2);
    }

    public static void unsubscribe(Context context, String str, String str2) {
        if (!a.a(context).b()) {
            return;
        }
        if (topicSubscribedTime(context, str) < 0) {
            b.a("Don't cancel subscribe for " + str + " is unsubscribed");
            return;
        }
        s sVar = new s();
        sVar.a(generatePacketID());
        sVar.b(a.a(context).c());
        sVar.c(str);
        sVar.d(context.getPackageName());
        sVar.e(str2);
        g.a(context).a(sVar, com.xiaomi.xmpush.thrift.a.d, null);
    }

    private static void updateIMEI() {
        new Thread(new c()).start();
    }
}
