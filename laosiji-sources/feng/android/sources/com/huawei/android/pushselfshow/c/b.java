package com.huawei.android.pushselfshow.c;

import android.R.drawable;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.b.a;
import com.huawei.android.pushselfshow.utils.d;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.rtmp.TXLiveConstants;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.Date;
import java.util.HashMap;

public class b {
    public static HashMap a = new HashMap();
    private static int b = 0;

    /* JADX WARNING: Missing block: B:6:0x0019, code:
            if (r1 > r0) goto L_0x001b;
     */
    @android.annotation.SuppressLint({"InlinedApi"})
    private static float a(android.content.Context r4) {
        /*
        r0 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r0 = com.huawei.android.pushselfshow.utils.a.a(r4, r0);
        r1 = (float) r0;
        r0 = r4.getResources();	 Catch:{ Exception -> 0x0036 }
        r2 = 17104901; // 0x1050005 float:2.4428256E-38 double:8.450944E-317;
        r0 = r0.getDimension(r2);	 Catch:{ Exception -> 0x0036 }
        r2 = 0;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x0041;
    L_0x0017:
        r2 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1));
        if (r2 <= 0) goto L_0x0041;
    L_0x001b:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "getRescaleBitmapSize:";
        r2 = r2.append(r3);
        r2 = r2.append(r0);
        r2 = r2.toString();
        com.huawei.android.pushagent.a.a.c.a(r1, r2);
        return r0;
    L_0x0036:
        r0 = move-exception;
        r2 = "PushSelfShowLog";
        r0 = r0.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r0);
    L_0x0041:
        r0 = r1;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.c.b.a(android.content.Context):float");
    }

    public static int a(Context context, String str, String str2, Object obj) {
        int i;
        Throwable e;
        Throwable th;
        try {
            Class cls;
            String str3 = context.getPackageName() + ".R";
            c.a("PushSelfShowLog", "try to refrect " + str3 + " typeName is " + str2);
            Class[] classes = Class.forName(str3).getClasses();
            c.a("PushSelfShowLog", "sonClassArr length " + classes.length);
            i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= classes.length) {
                    cls = null;
                    break;
                }
                cls = classes[i2];
                c.a("PushSelfShowLog", "sonTypeClass,query sonclass is  %s", cls.getName().substring(str3.length() + 1) + " sonClass.getName() is" + cls.getName());
                if (str2.equals(cls.getName().substring(str3.length() + 1))) {
                    break;
                }
                i = i2 + 1;
            }
            if (cls != null) {
                i = cls.getField(str).getInt(obj);
                try {
                    c.a("PushSelfShowLog", " refect res id is %s", "" + i);
                } catch (ClassNotFoundException e2) {
                    e = e2;
                } catch (NoSuchFieldException e3) {
                    e = e3;
                    c.c("PushSelfShowLog", "NoSuchFieldException failed,", e);
                    return i;
                } catch (IllegalAccessException e4) {
                    e = e4;
                    c.c("PushSelfShowLog", "IllegalAccessException failed,", e);
                    return i;
                } catch (IllegalArgumentException e5) {
                    e = e5;
                    c.c("PushSelfShowLog", "IllegalArgumentException failed,", e);
                    return i;
                } catch (IndexOutOfBoundsException e6) {
                    e = e6;
                    c.c("PushSelfShowLog", "IndexOutOfBoundsException failed,", e);
                    return i;
                } catch (Exception e7) {
                    e = e7;
                    c.c("PushSelfShowLog", "  failed,", e);
                    return i;
                }
                return i;
            }
            c.a("PushSelfShowLog", "sonTypeClass is null");
            String str4 = context.getPackageName() + ".R$" + str2;
            c.a("PushSelfShowLog", "try to refrect 2 " + str4 + " typeName is " + str2);
            i = Class.forName(str4).getField(str).getInt(obj);
            c.a("PushSelfShowLog", " refect res id 2 is %s", "" + i);
            return i;
        } catch (Throwable e8) {
            th = e8;
            i = 0;
            e = th;
        } catch (Throwable e82) {
            th = e82;
            i = 0;
            e = th;
            c.c("PushSelfShowLog", "NoSuchFieldException failed,", e);
            return i;
        } catch (Throwable e822) {
            th = e822;
            i = 0;
            e = th;
            c.c("PushSelfShowLog", "IllegalAccessException failed,", e);
            return i;
        } catch (Throwable e8222) {
            th = e8222;
            i = 0;
            e = th;
            c.c("PushSelfShowLog", "IllegalArgumentException failed,", e);
            return i;
        } catch (Throwable e82222) {
            th = e82222;
            i = 0;
            e = th;
            c.c("PushSelfShowLog", "IndexOutOfBoundsException failed,", e);
            return i;
        } catch (Throwable e822222) {
            th = e822222;
            i = 0;
            e = th;
            c.c("PushSelfShowLog", "  failed,", e);
            return i;
        }
        c.c("PushSelfShowLog", "ClassNotFound failed,", e);
        return i;
    }

    public static Notification a(Context context, a aVar, int i, int i2, int i3) {
        Notification notification = new Notification();
        notification.icon = b(context, aVar);
        int i4 = context.getApplicationInfo().labelRes;
        notification.tickerText = aVar.q;
        notification.when = System.currentTimeMillis();
        notification.flags |= 16;
        notification.defaults |= 1;
        PendingIntent a = a(context, aVar, i, i2);
        notification.contentIntent = a;
        notification.deleteIntent = b(context, aVar, i, i3);
        if (aVar.s == null || "".equals(aVar.s)) {
            notification.setLatestEventInfo(context, context.getResources().getString(i4), aVar.q, a);
        } else {
            notification.setLatestEventInfo(context, aVar.s, aVar.q, a);
        }
        i4 = context.getResources().getIdentifier("icon", "id", "android");
        Bitmap d = d(context, aVar);
        if (!(i4 == 0 || notification.contentView == null || d == null)) {
            notification.contentView.setImageViewBitmap(i4, d);
        }
        return c.a(context, notification, i, aVar, d);
    }

    private static PendingIntent a(Context context, a aVar, int i, int i2) {
        Intent intent = new Intent("com.huawei.intent.action.PUSH");
        intent.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", PushConstants.PUSH_TYPE_THROUGH_MESSAGE).putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(CommonNetImpl.FLAG_AUTH);
        return PendingIntent.getBroadcast(context, i2, intent, 134217728);
    }

    public static void a(Context context, int i) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(UMessage.DISPLAY_TYPE_NOTIFICATION);
            if (notificationManager != null) {
                notificationManager.cancel(i);
            }
        } catch (Exception e) {
            c.d("PushSelfShowLog", "removeNotifiCationById err:" + e.toString());
        }
    }

    public static void a(Context context, Intent intent) {
        int i = 0;
        try {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            if (intent.hasExtra("selfshow_notify_id")) {
                i = intent.getIntExtra("selfshow_notify_id", 0) + 3;
            }
            c.a("PushSelfShowLog", "setDelayAlarm(cancel) alarmNotityId " + i + " and intent is " + intent.toURI());
            Intent intent2 = new Intent("com.huawei.intent.action.PUSH");
            intent2.setPackage(context.getPackageName()).setFlags(32);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, i, intent2, CommonNetImpl.FLAG_SHARE);
            if (broadcast != null) {
                c.a("PushSelfShowLog", "  alarm cancel");
                alarmManager.cancel(broadcast);
                return;
            }
            c.a("PushSelfShowLog", "alarm not exist");
        } catch (Exception e) {
            c.d("PushSelfShowLog", "cancelAlarm err:" + e.toString());
        }
    }

    public static void a(Context context, Intent intent, long j, int i) {
        try {
            c.a("PushSelfShowLog", "enter setDelayAlarm(intent:" + intent.toURI() + " interval:" + j + "ms, context:" + context);
            ((AlarmManager) context.getSystemService("alarm")).set(0, System.currentTimeMillis() + j, PendingIntent.getBroadcast(context, i, intent, 134217728));
        } catch (Throwable e) {
            c.a("PushSelfShowLog", "set DelayAlarm error", e);
        }
    }

    public static synchronized void a(Context context, a aVar) {
        synchronized (b.class) {
            if (!(context == null || aVar == null)) {
                try {
                    int i;
                    int i2;
                    int i3;
                    int i4;
                    c.a("PushSelfShowLog", " showNotification , the msg id = " + aVar.m);
                    com.huawei.android.pushselfshow.utils.a.a(2, (int) TXLiveConstants.RENDER_ROTATION_180);
                    if (b == 0) {
                        b = (context.getPackageName() + new Date().toString()).hashCode();
                    }
                    int i5;
                    if (TextUtils.isEmpty(aVar.a)) {
                        i = b + 1;
                        b = i;
                        i2 = b + 1;
                        b = i2;
                        i3 = b + 1;
                        b = i3;
                        i4 = b + 1;
                        b = i4;
                        i5 = i4;
                        i4 = i3;
                        i3 = i2;
                        i2 = i5;
                    } else {
                        i = (aVar.n + aVar.a).hashCode();
                        i2 = b + 1;
                        b = i2;
                        i3 = b + 1;
                        b = i3;
                        i4 = b + 1;
                        b = i4;
                        i5 = i4;
                        i4 = i3;
                        i3 = i2;
                        i2 = i5;
                    }
                    c.a("PushSelfShowLog", "notifyId:" + i + ",openNotifyId:" + i3 + ",delNotifyId:" + i4 + ",alarmNotifyId:" + i2);
                    Notification b = com.huawei.android.pushselfshow.utils.a.b() ? b(context, aVar, i, i3, i4) : a(context, aVar, i, i3, i4);
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(UMessage.DISPLAY_TYPE_NOTIFICATION);
                    if (!(notificationManager == null || b == null)) {
                        notificationManager.notify(i, b);
                        if (aVar.f > 0) {
                            Intent intent = new Intent("com.huawei.intent.action.PUSH");
                            intent.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", "-1").putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(32);
                            a(context, intent, (long) aVar.f, i2);
                            c.a("PushSelfShowLog", "setDelayAlarm alarmNotityId" + i2 + " and intent is " + intent.toURI());
                        }
                        com.huawei.android.pushselfshow.utils.a.a(context, PushConstants.PUSH_TYPE_NOTIFY, aVar);
                    }
                } catch (Exception e) {
                    c.d("PushSelfShowLog", "showNotification error " + e.toString());
                }
            }
        }
        return;
    }

    private static int b(Context context, a aVar) {
        int i = 0;
        if (context == null || aVar == null) {
            c.b("PushSelfShowLog", "enter getSmallIconId, context or msg is null");
            return 0;
        }
        if (aVar.t != null && aVar.t.length() > 0) {
            i = a(context, aVar.t, "drawable", new drawable());
            c.a("PushSelfShowLog", context.getPackageName() + "  msg.statusIcon is " + aVar.t + ",and icon is " + i);
            if (i == 0) {
                i = context.getResources().getIdentifier(aVar.t, "drawable", "android");
            }
            c.a("PushSelfShowLog", "msg.statusIcon is " + aVar.t + ",and icon is " + i);
        }
        if (i != 0) {
            return i;
        }
        i = context.getApplicationInfo().icon;
        if (i != 0) {
            return i;
        }
        i = context.getResources().getIdentifier("btn_star_big_on", "drawable", "android");
        c.a("PushSelfShowLog", "icon is btn_star_big_on ");
        if (i != 0) {
            return i;
        }
        c.a("PushSelfShowLog", "icon is sym_def_app_icon ");
        return 17301651;
    }

    public static Notification b(Context context, a aVar, int i, int i2, int i3) {
        Builder builder = new Builder(context);
        if (!com.huawei.android.pushselfshow.utils.a.e(context) || "com.huawei.android.pushagent".equals(aVar.n)) {
            builder.setSmallIcon(b(context, aVar));
        } else {
            c.b("PushSelfShowLog", "get small icon from " + aVar.n);
            Icon c = c(context, aVar);
            if (c != null) {
                builder.setSmallIcon(c);
            } else {
                builder.setSmallIcon(b(context, aVar));
            }
        }
        int i4 = context.getApplicationInfo().labelRes;
        builder.setTicker(aVar.q);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setDefaults(1);
        String str = aVar.n + aVar.a;
        if (!TextUtils.isEmpty(aVar.a)) {
            c.a("PushSelfShowLog", "groupMap key is " + str);
            if (a.containsKey(str)) {
                a.put(str, Integer.valueOf(((Integer) a.get(str)).intValue() + 1));
                c.a("PushSelfShowLog", "groupMap.size:" + a.get(str));
            } else {
                a.put(str, Integer.valueOf(1));
            }
        }
        if (aVar.s == null || "".equals(aVar.s)) {
            builder.setContentTitle(context.getResources().getString(i4));
        } else {
            builder.setContentTitle(aVar.s);
        }
        if (TextUtils.isEmpty(aVar.a) || ((Integer) a.get(str)).intValue() == 1) {
            builder.setContentText(aVar.q);
        } else {
            builder.setContentText(context.getResources().getQuantityString(d.b(context, "hwpush_message_hint"), r0, new Object[]{Integer.valueOf(((Integer) a.get(str)).intValue())}));
        }
        builder.setContentIntent(a(context, aVar, i, i2));
        builder.setDeleteIntent(b(context, aVar, i, i3));
        Bitmap d = d(context, aVar);
        if (d != null) {
            builder.setLargeIcon(d);
        }
        if ("com.huawei.android.pushagent".equals(context.getPackageName())) {
            Bundle bundle = new Bundle();
            Object obj = aVar.n;
            if (PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(aVar.p) || "cosa".equals(aVar.p)) {
                obj = aVar.A;
            }
            if (!TextUtils.isEmpty(obj)) {
                bundle.putString("hw_origin_sender_package_name", obj);
                builder.setExtras(bundle);
            }
        }
        return c.a(context, builder, i, aVar, d) == null ? null : builder.getNotification();
    }

    private static PendingIntent b(Context context, a aVar, int i, int i2) {
        Intent intent = new Intent("com.huawei.intent.action.PUSH");
        intent.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", PushConstants.PUSH_TYPE_UPLOAD_LOG).putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(CommonNetImpl.FLAG_AUTH);
        return PendingIntent.getBroadcast(context, i2, intent, 134217728);
    }

    private static Icon c(Context context, a aVar) {
        try {
            return Icon.createWithBitmap(((BitmapDrawable) context.getPackageManager().getApplicationIcon(aVar.n)).getBitmap());
        } catch (NameNotFoundException e) {
            c.d("PushSelfShowLog", e.toString());
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x008c A:{SYNTHETIC, Splitter: B:18:0x008c} */
    /* JADX WARNING: Missing block: B:26:0x010b, code:
            if (r1 == null) goto L_0x010d;
     */
    private static android.graphics.Bitmap d(android.content.Context r7, com.huawei.android.pushselfshow.b.a r8) {
        /*
        r0 = 0;
        if (r7 == 0) goto L_0x0005;
    L_0x0003:
        if (r8 != 0) goto L_0x0006;
    L_0x0005:
        return r0;
    L_0x0006:
        r1 = r8.r;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        if (r1 == 0) goto L_0x010d;
    L_0x000a:
        r1 = r8.r;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r1 = r1.length();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        if (r1 <= 0) goto L_0x010d;
    L_0x0012:
        r2 = new com.huawei.android.pushselfshow.utils.c.a;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r2.<init>();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r1 = 0;
        r3 = r8.r;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = new java.lang.StringBuilder;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4.<init>();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r5 = "";
        r4 = r4.append(r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r5 = r8.a();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = r4.append(r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = r4.toString();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r3 = r3.equals(r4);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        if (r3 != 0) goto L_0x007f;
    L_0x0038:
        r1 = r8.r;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r3 = "drawable";
        r4 = new android.R$drawable;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4.<init>();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r1 = a(r7, r1, r3, r4);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        if (r1 != 0) goto L_0x0058;
    L_0x0048:
        r1 = r7.getResources();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r3 = r8.r;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = "drawable";
        r5 = "android";
        r1 = r1.getIdentifier(r3, r4, r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
    L_0x0058:
        r3 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4.<init>();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r5 = "msg.notifyIcon is ";
        r4 = r4.append(r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r5 = r8.r;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = r4.append(r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r5 = ",and defaultIcon is ";
        r4 = r4.append(r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = r4.append(r1);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = r4.toString();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        com.huawei.android.pushagent.a.a.c.a(r3, r4);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
    L_0x007f:
        if (r1 == 0) goto L_0x00c5;
    L_0x0081:
        r2 = r7.getResources();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r0 = android.graphics.BitmapFactory.decodeResource(r2, r1);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r1 = r0;
    L_0x008a:
        if (r1 != 0) goto L_0x015c;
    L_0x008c:
        r0 = "com.huawei.android.pushagent";
        r2 = r8.n;	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r0 = r0.equals(r2);	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        if (r0 != 0) goto L_0x015c;
    L_0x0097:
        r0 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r2.<init>();	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r3 = "get left bitmap from ";
        r2 = r2.append(r3);	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r3 = r8.n;	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r2 = r2.append(r3);	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r2 = r2.toString();	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        com.huawei.android.pushagent.a.a.c.b(r0, r2);	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r0 = r7.getPackageManager();	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r2 = r8.n;	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r0 = r0.getApplicationIcon(r2);	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r0 = (android.graphics.drawable.BitmapDrawable) r0;	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        r0 = r0.getBitmap();	 Catch:{ NameNotFoundException -> 0x0157, Exception -> 0x0152 }
        goto L_0x0005;
    L_0x00c5:
        r1 = r8.r;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r1 = r2.a(r7, r1);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r3 = "PushSelfShowLog";
        r4 = "get bitmap from new downloaded ";
        com.huawei.android.pushagent.a.a.c.a(r3, r4);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        if (r1 == 0) goto L_0x010b;
    L_0x00d6:
        r3 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4.<init>();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r5 = "height:";
        r4 = r4.append(r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r5 = r1.getHeight();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = r4.append(r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r5 = ",width:";
        r4 = r4.append(r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r5 = r1.getWidth();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = r4.append(r5);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r4 = r4.toString();	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        com.huawei.android.pushagent.a.a.c.a(r3, r4);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r3 = a(r7);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
        r1 = r2.a(r7, r1, r3, r3);	 Catch:{ NameNotFoundException -> 0x0110, Exception -> 0x0131 }
    L_0x010b:
        if (r1 != 0) goto L_0x008a;
    L_0x010d:
        r1 = r0;
        goto L_0x008a;
    L_0x0110:
        r1 = move-exception;
    L_0x0111:
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x0005;
    L_0x0131:
        r1 = move-exception;
    L_0x0132:
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x0005;
    L_0x0152:
        r0 = move-exception;
        r6 = r0;
        r0 = r1;
        r1 = r6;
        goto L_0x0132;
    L_0x0157:
        r0 = move-exception;
        r6 = r0;
        r0 = r1;
        r1 = r6;
        goto L_0x0111;
    L_0x015c:
        r0 = r1;
        goto L_0x0005;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.c.b.d(android.content.Context, com.huawei.android.pushselfshow.b.a):android.graphics.Bitmap");
    }
}
