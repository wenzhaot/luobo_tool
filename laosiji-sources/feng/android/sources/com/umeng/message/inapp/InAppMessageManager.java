package com.umeng.message.inapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.feng.library.utils.DateUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.talkingdata.sdk.ab;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.MsgConstant;
import com.umeng.message.common.d;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.message.provider.a;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class InAppMessageManager {
    static boolean a = false;
    static int b = ab.I;
    static int c = 1000;
    private static final String d = InAppMessageManager.class.getName();
    @SuppressLint({"StaticFieldLeak"})
    private static InAppMessageManager e = null;
    private static final String h = "tempkey";
    private static final String i = "tempvalue";
    private Context f;
    private String g;

    private InAppMessageManager(Context context) {
        this.f = context;
    }

    public static InAppMessageManager getInstance(Context context) {
        if (e == null) {
            synchronized (InAppMessageManager.class) {
                if (e == null) {
                    e = new InAppMessageManager(StubApp.getOrigApplicationContext(context.getApplicationContext()));
                }
            }
        }
        return e;
    }

    public void showCardMessage(Activity activity, String str, IUmengInAppMsgCloseCallback iUmengInAppMsgCloseCallback) {
        new b(activity, str, iUmengInAppMsgCloseCallback).a();
    }

    public void setInAppMsgDebugMode(boolean z) {
        a = z;
    }

    public void setMainActivityPath(String str) {
        this.g = str;
    }

    String a() {
        return this.g;
    }

    public void setPlainTextSize(int i, int i2, int i3) {
        if (i <= 0 || i2 <= 0 || i3 <= 0) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(d, 0, "纯文本字体大小不能小于0");
            return;
        }
        b(MsgConstant.KEY_PLAIN_TEXT_SIZE, i + MiPushClient.ACCEPT_TIME_SEPARATOR + i2 + MiPushClient.ACCEPT_TIME_SEPARATOR + i3);
    }

    String[] b() {
        Object a = a(MsgConstant.KEY_PLAIN_TEXT_SIZE, "");
        if (TextUtils.isEmpty(a)) {
            return null;
        }
        return a.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
    }

    void c() {
        b(MsgConstant.KEY_SPLASH_TS, System.currentTimeMillis() + "");
    }

    long d() {
        return Long.valueOf(a(MsgConstant.KEY_SPLASH_TS, PushConstants.PUSH_TYPE_NOTIFY)).longValue();
    }

    void a(String str) {
        b("KEY_CARD_TS_" + str, System.currentTimeMillis() + "");
    }

    long b(String str) {
        return Long.valueOf(a("KEY_CARD_TS_" + str, PushConstants.PUSH_TYPE_NOTIFY)).longValue();
    }

    void a(UInAppMessage uInAppMessage) {
        if (uInAppMessage == null) {
            b(MsgConstant.KEY_LAST_SPLASH_ID, "");
        } else if (uInAppMessage.getRaw() != null) {
            b(MsgConstant.KEY_LAST_SPLASH_ID, uInAppMessage.getRaw().toString());
        }
    }

    String e() {
        return a(MsgConstant.KEY_LAST_SPLASH_ID, "");
    }

    void a(UInAppMessage uInAppMessage, String str) {
        if (uInAppMessage == null) {
            b("KEY_LAST_CARD_ID_" + str, "");
        } else if (uInAppMessage.getRaw() != null) {
            b("KEY_LAST_CARD_ID_" + str, uInAppMessage.getRaw().toString());
        }
    }

    String c(String str) {
        return a("KEY_LAST_CARD_ID_" + str, "");
    }

    void a(String str, int i) {
        if (i == 0) {
            b(str, PushConstants.PUSH_TYPE_NOTIFY);
        }
        if (i == 1) {
            b(str, (j(str) + 1) + "");
        }
    }

    private int j(String str) {
        return Integer.valueOf(a(str, PushConstants.PUSH_TYPE_NOTIFY)).intValue();
    }

    void d(String str) {
        b(MsgConstant.KEY_CARD_LABEL_LIST, str);
    }

    String f() {
        return a(MsgConstant.KEY_CARD_LABEL_LIST, "");
    }

    void e(String str) {
        b(MsgConstant.KEY_LAST_VERSION_CODE, str);
    }

    String g() {
        return a(MsgConstant.KEY_LAST_VERSION_CODE, "");
    }

    void h() {
        b(MsgConstant.KEY_LAST_SHOW_SPLASH_TS, System.currentTimeMillis() + "");
    }

    long i() {
        return Long.parseLong(a(MsgConstant.KEY_LAST_SHOW_SPLASH_TS, PushConstants.PUSH_TYPE_NOTIFY));
    }

    void f(String str) {
        b("KEY_LAST_SHOW_CARD_TS_" + str, System.currentTimeMillis() + "");
    }

    long g(String str) {
        return Long.parseLong(a("KEY_LAST_SHOW_CARD_TS_" + str, PushConstants.PUSH_TYPE_NOTIFY));
    }

    boolean b(UInAppMessage uInAppMessage) {
        try {
            if (System.currentTimeMillis() < new SimpleDateFormat(DateUtil.dateFormatYMDHMS, Locale.CHINA).parse(uInAppMessage.expire_time).getTime()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean c(UInAppMessage uInAppMessage) {
        if (uInAppMessage.show_times != 0 && j(uInAppMessage.msg_id) >= uInAppMessage.show_times) {
            return false;
        }
        return true;
    }

    void a(String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (!TextUtils.isEmpty(str)) {
            final String str2 = str;
            final int i9 = i;
            final int i10 = i2;
            final int i11 = i3;
            final int i12 = i4;
            final int i13 = i5;
            final int i14 = i6;
            final int i15 = i7;
            final int i16 = i8;
            d.a(new Runnable() {
                public void run() {
                    try {
                        a a = InAppMessageManager.this.k(str2);
                        a aVar;
                        if (a != null) {
                            aVar = new a(str2, i9, i10 + a.d, i11 + a.e, i12 + a.f, i13 + a.g, i14 + a.h, i15 + a.i, a.j);
                            String[] strArr = new String[]{str2};
                            ContentResolver contentResolver = InAppMessageManager.this.f.getContentResolver();
                            a.a(InAppMessageManager.this.f);
                            contentResolver.update(a.k, aVar.a(), "MsgId=?", strArr);
                        } else {
                            aVar = new a(str2, i9, i10, i11, i12, i13, i14, i15, i16);
                            ContentResolver contentResolver2 = InAppMessageManager.this.f.getContentResolver();
                            a.a(InAppMessageManager.this.f);
                            contentResolver2.insert(a.k, aVar.a());
                        }
                        UMLog uMLog = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(InAppMessageManager.d, 2, "store in app cache log success");
                    } catch (Exception e) {
                        UMLog uMLog2 = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(InAppMessageManager.d, 0, "store in app cache log fail");
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private a k(String str) {
        a aVar = null;
        String[] strArr = new String[]{str};
        ContentResolver contentResolver = this.f.getContentResolver();
        a.a(this.f);
        Cursor query = contentResolver.query(a.k, null, "MsgId=?", strArr, null);
        if (query.moveToFirst()) {
            aVar = new a(query);
        }
        if (query != null) {
            query.close();
        }
        return aVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003b A:{SYNTHETIC, Splitter: B:16:0x003b} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0048  */
    java.util.ArrayList<com.umeng.message.inapp.a> j() {
        /*
        r8 = this;
        r6 = 0;
        r7 = new java.util.ArrayList;
        r7.<init>();
        r0 = r8.f;	 Catch:{ Exception -> 0x0037, all -> 0x0044 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0037, all -> 0x0044 }
        r1 = r8.f;	 Catch:{ Exception -> 0x0037, all -> 0x0044 }
        com.umeng.message.provider.a.a(r1);	 Catch:{ Exception -> 0x0037, all -> 0x0044 }
        r1 = com.umeng.message.provider.a.k;	 Catch:{ Exception -> 0x0037, all -> 0x0044 }
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0037, all -> 0x0044 }
        r0 = 0;
        if (r1 == 0) goto L_0x0022;
    L_0x001e:
        r0 = r1.moveToFirst();	 Catch:{ Exception -> 0x004e }
    L_0x0022:
        if (r0 == 0) goto L_0x0031;
    L_0x0024:
        r0 = new com.umeng.message.inapp.a;	 Catch:{ Exception -> 0x004e }
        r0.<init>(r1);	 Catch:{ Exception -> 0x004e }
        r7.add(r0);	 Catch:{ Exception -> 0x004e }
        r0 = r1.moveToNext();	 Catch:{ Exception -> 0x004e }
        goto L_0x0022;
    L_0x0031:
        if (r1 == 0) goto L_0x0036;
    L_0x0033:
        r1.close();
    L_0x0036:
        return r7;
    L_0x0037:
        r0 = move-exception;
        r1 = r6;
    L_0x0039:
        if (r0 == 0) goto L_0x003e;
    L_0x003b:
        r0.printStackTrace();	 Catch:{ all -> 0x004c }
    L_0x003e:
        if (r1 == 0) goto L_0x0036;
    L_0x0040:
        r1.close();
        goto L_0x0036;
    L_0x0044:
        r0 = move-exception;
        r1 = r6;
    L_0x0046:
        if (r1 == 0) goto L_0x004b;
    L_0x0048:
        r1.close();
    L_0x004b:
        throw r0;
    L_0x004c:
        r0 = move-exception;
        goto L_0x0046;
    L_0x004e:
        r0 = move-exception;
        goto L_0x0039;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.inapp.InAppMessageManager.j():java.util.ArrayList<com.umeng.message.inapp.a>");
    }

    boolean h(String str) {
        String[] strArr = new String[]{str};
        ContentResolver contentResolver = this.f.getContentResolver();
        a.a(this.f);
        if (contentResolver.delete(a.k, "MsgId=?", strArr) == 1) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005f  */
    private java.lang.String a(java.lang.String r9, java.lang.String r10) {
        /*
        r8 = this;
        r6 = 0;
        r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r0.<init>();	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r1 = "tempkey";
        r0.put(r1, r9);	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r3 = "tempkey=?";
        r0 = 1;
        r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r0 = 0;
        r4[r0] = r9;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r0 = r8.f;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r1 = r8.f;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        com.umeng.message.provider.a.a(r1);	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r1 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r5 = 0;
        r7 = "tempvalue";
        r2[r5] = r7;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        if (r1 != 0) goto L_0x0038;
    L_0x0032:
        if (r1 == 0) goto L_0x0037;
    L_0x0034:
        r1.close();
    L_0x0037:
        return r10;
    L_0x0038:
        r0 = r1.moveToFirst();	 Catch:{ Exception -> 0x0066 }
        if (r0 == 0) goto L_0x0049;
    L_0x003e:
        r0 = "tempvalue";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x0066 }
        r10 = r1.getString(r0);	 Catch:{ Exception -> 0x0066 }
    L_0x0049:
        if (r1 == 0) goto L_0x0037;
    L_0x004b:
        r1.close();
        goto L_0x0037;
    L_0x004f:
        r0 = move-exception;
        r1 = r6;
    L_0x0051:
        if (r0 == 0) goto L_0x0056;
    L_0x0053:
        r0.printStackTrace();	 Catch:{ all -> 0x0063 }
    L_0x0056:
        if (r1 == 0) goto L_0x0037;
    L_0x0058:
        r1.close();
        goto L_0x0037;
    L_0x005c:
        r0 = move-exception;
    L_0x005d:
        if (r6 == 0) goto L_0x0062;
    L_0x005f:
        r6.close();
    L_0x0062:
        throw r0;
    L_0x0063:
        r0 = move-exception;
        r6 = r1;
        goto L_0x005d;
    L_0x0066:
        r0 = move-exception;
        goto L_0x0051;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.inapp.InAppMessageManager.a(java.lang.String, java.lang.String):java.lang.String");
    }

    private void b(final String str, final String str2) {
        d.a(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:22:0x00cd  */
            public void run() {
                /*
                r8 = this;
                r6 = 0;
                r3 = "tempkey=?";
                r0 = 1;
                r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r0 = 0;
                r1 = r2;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r4[r0] = r1;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r0 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r0 = r0.f;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r1 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r1 = r1.f;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                com.umeng.message.provider.a.a(r1);	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r1 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r2 = 1;
                r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r5 = 0;
                r7 = "tempvalue";
                r2[r5] = r7;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r5 = 0;
                r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                if (r1 != 0) goto L_0x0064;
            L_0x0031:
                r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x0090 }
                r0.<init>();	 Catch:{ Exception -> 0x0090 }
                r2 = "tempkey";
                r3 = r2;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r3);	 Catch:{ Exception -> 0x0090 }
                r2 = "tempvalue";
                r3 = r3;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r3);	 Catch:{ Exception -> 0x0090 }
                r2 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.f;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.getContentResolver();	 Catch:{ Exception -> 0x0090 }
                r3 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x0090 }
                r3 = r3.f;	 Catch:{ Exception -> 0x0090 }
                com.umeng.message.provider.a.a(r3);	 Catch:{ Exception -> 0x0090 }
                r3 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x0090 }
                r2.insert(r3, r0);	 Catch:{ Exception -> 0x0090 }
            L_0x005e:
                if (r1 == 0) goto L_0x0063;
            L_0x0060:
                r1.close();
            L_0x0063:
                return;
            L_0x0064:
                r0 = r1.moveToFirst();	 Catch:{ Exception -> 0x0090 }
                if (r0 == 0) goto L_0x009c;
            L_0x006a:
                r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x0090 }
                r0.<init>();	 Catch:{ Exception -> 0x0090 }
                r2 = "tempvalue";
                r5 = r3;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r5);	 Catch:{ Exception -> 0x0090 }
                r2 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.f;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.getContentResolver();	 Catch:{ Exception -> 0x0090 }
                r5 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x0090 }
                r5 = r5.f;	 Catch:{ Exception -> 0x0090 }
                com.umeng.message.provider.a.a(r5);	 Catch:{ Exception -> 0x0090 }
                r5 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x0090 }
                r2.update(r5, r0, r3, r4);	 Catch:{ Exception -> 0x0090 }
                goto L_0x005e;
            L_0x0090:
                r0 = move-exception;
            L_0x0091:
                if (r0 == 0) goto L_0x0096;
            L_0x0093:
                r0.printStackTrace();	 Catch:{ all -> 0x00ca }
            L_0x0096:
                if (r1 == 0) goto L_0x0063;
            L_0x0098:
                r1.close();
                goto L_0x0063;
            L_0x009c:
                r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x0090 }
                r0.<init>();	 Catch:{ Exception -> 0x0090 }
                r2 = "tempkey";
                r3 = r2;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r3);	 Catch:{ Exception -> 0x0090 }
                r2 = "tempvalue";
                r3 = r3;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r3);	 Catch:{ Exception -> 0x0090 }
                r2 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.f;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.getContentResolver();	 Catch:{ Exception -> 0x0090 }
                r3 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x0090 }
                r3 = r3.f;	 Catch:{ Exception -> 0x0090 }
                com.umeng.message.provider.a.a(r3);	 Catch:{ Exception -> 0x0090 }
                r3 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x0090 }
                r2.insert(r3, r0);	 Catch:{ Exception -> 0x0090 }
                goto L_0x005e;
            L_0x00ca:
                r0 = move-exception;
            L_0x00cb:
                if (r1 == 0) goto L_0x00d0;
            L_0x00cd:
                r1.close();
            L_0x00d0:
                throw r0;
            L_0x00d1:
                r0 = move-exception;
                r1 = r6;
                goto L_0x00cb;
            L_0x00d4:
                r0 = move-exception;
                r1 = r6;
                goto L_0x0091;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.inapp.InAppMessageManager.2.run():void");
            }
        });
    }

    void i(final String str) {
        d.a(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:15:0x006c  */
            public void run() {
                /*
                r7 = this;
                r6 = 0;
                r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0.<init>();	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r1 = "tempkey";
                r2 = r2;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0.put(r1, r2);	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0 = r0.f;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r1 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r1 = r1.f;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                com.umeng.message.provider.a.a(r1);	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r1 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r2 = 1;
                r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r3 = 0;
                r4 = "tempvalue";
                r2[r3] = r4;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r3 = 0;
                r4 = 0;
                r5 = 0;
                r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                if (r1 != 0) goto L_0x003b;
            L_0x0035:
                if (r1 == 0) goto L_0x003a;
            L_0x0037:
                r1.close();
            L_0x003a:
                return;
            L_0x003b:
                r0 = "tempkey=?";
                r2 = 1;
                r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x005f }
                r3 = 0;
                r4 = r2;	 Catch:{ Exception -> 0x005f }
                r2[r3] = r4;	 Catch:{ Exception -> 0x005f }
                r3 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x005f }
                r3 = r3.f;	 Catch:{ Exception -> 0x005f }
                r3 = r3.getContentResolver();	 Catch:{ Exception -> 0x005f }
                r4 = com.umeng.message.inapp.InAppMessageManager.this;	 Catch:{ Exception -> 0x005f }
                r4 = r4.f;	 Catch:{ Exception -> 0x005f }
                com.umeng.message.provider.a.a(r4);	 Catch:{ Exception -> 0x005f }
                r4 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x005f }
                r3.delete(r4, r0, r2);	 Catch:{ Exception -> 0x005f }
                goto L_0x0035;
            L_0x005f:
                r0 = move-exception;
            L_0x0060:
                r0.printStackTrace();	 Catch:{ all -> 0x0070 }
                if (r1 == 0) goto L_0x003a;
            L_0x0065:
                r1.close();
                goto L_0x003a;
            L_0x0069:
                r0 = move-exception;
            L_0x006a:
                if (r6 == 0) goto L_0x006f;
            L_0x006c:
                r6.close();
            L_0x006f:
                throw r0;
            L_0x0070:
                r0 = move-exception;
                r6 = r1;
                goto L_0x006a;
            L_0x0073:
                r0 = move-exception;
                r1 = r6;
                goto L_0x0060;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.inapp.InAppMessageManager.3.run():void");
            }
        });
    }

    void a(final File file) {
        d.a(new Runnable() {
            public void run() {
                if (file != null && file.exists() && file.canWrite() && file.isDirectory()) {
                    for (File file : file.listFiles()) {
                        if (!file.isDirectory()) {
                            file.delete();
                        }
                    }
                    file.delete();
                }
            }
        });
    }
}
