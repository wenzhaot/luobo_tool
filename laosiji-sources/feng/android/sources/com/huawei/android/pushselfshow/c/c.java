package com.huawei.android.pushselfshow.c;

import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.Builder;
import android.app.Notification.InboxStyle;
import android.app.Notification.Style;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.huawei.android.pushagent.PushReceiver.ACTION;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import com.huawei.android.pushselfshow.b.a;
import com.huawei.android.pushselfshow.utils.d;
import java.security.SecureRandom;
import java.util.Date;

public class c {
    public static Builder a(Context context, Builder builder, int i, a aVar, Bitmap bitmap) {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "Notification addStyle");
        if (context == null || builder == null || aVar == null) {
            return builder;
        }
        a aVar2 = a.STYLE_1;
        if (aVar.L >= 0 && aVar.L < a.values().length) {
            aVar2 = a.values()[aVar.L];
        }
        switch (aVar2) {
            case STYLE_2:
                builder.setContent(a(context, i, bitmap, aVar));
                return builder;
            case STYLE_4:
                builder.setContent(b(context, i, bitmap, aVar));
                return builder;
            case STYLE_5:
                a(context, builder, i, bitmap, aVar);
                return builder;
            case STYLE_6:
                return !b(context, builder, i, bitmap, aVar) ? null : builder;
            case STYLE_7:
                builder.setContent(c(context, i, bitmap, aVar));
                return builder;
            case STYLE_8:
                RemoteViews a = a(context, bitmap, aVar);
                if (a == null) {
                    return null;
                }
                builder.setContent(a);
                return builder;
            default:
                return builder;
        }
    }

    public static Notification a(Context context, Notification notification, int i, a aVar, Bitmap bitmap) {
        if (notification == null || aVar == null) {
            return notification;
        }
        a aVar2 = a.STYLE_1;
        if (aVar.L >= 0 && aVar.L < a.values().length) {
            aVar2 = a.values()[aVar.L];
        }
        switch (aVar2) {
            case STYLE_2:
                notification.contentView = a(context, i, bitmap, aVar);
                return notification;
            case STYLE_4:
                notification.contentView = b(context, i, bitmap, aVar);
                return notification;
            case STYLE_7:
                notification.contentView = c(context, i, bitmap, aVar);
                return notification;
            case STYLE_8:
                RemoteViews a = a(context, bitmap, aVar);
                if (a == null) {
                    return null;
                }
                notification.contentView = a;
                return notification;
            default:
                return notification;
        }
    }

    private static PendingIntent a(Context context, int i, String str) {
        Intent flags = new Intent(ACTION.ACTION_NOTIFICATION_MSG_CLICK).setPackage(context.getPackageName()).setFlags(32);
        flags.putExtra(KEY_TYPE.PUSH_KEY_NOTIFY_ID, i);
        flags.putExtra(KEY_TYPE.PUSH_KEY_CLICK_BTN, str);
        int hashCode = (context.getPackageName() + str + new SecureRandom().nextInt() + new Date().toString()).hashCode();
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "getPendingIntent,requestCode:" + hashCode);
        return PendingIntent.getBroadcast(context, hashCode, flags, 134217728);
    }

    private static RemoteViews a(Context context, int i, Bitmap bitmap, a aVar) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), d.c(context, "hwpush_layout2"));
        a(context, bitmap, remoteViews);
        a(context, i, remoteViews, aVar);
        remoteViews.setTextViewText(d.e(context, "title"), a(context, aVar));
        remoteViews.setTextViewText(d.e(context, "text"), aVar.q);
        return remoteViews;
    }

    private static RemoteViews a(Context context, Bitmap bitmap, a aVar) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), d.a(context, "layout", "hwpush_layout8"));
        Bitmap a = !TextUtils.isEmpty(aVar.T) ? new com.huawei.android.pushselfshow.utils.c.a().a(context, aVar.T) : null;
        if (a == null) {
            return null;
        }
        remoteViews.setViewVisibility(d.a(context, "id", "big_pic"), 0);
        remoteViews.setImageViewBitmap(d.a(context, "id", "big_pic"), a);
        return remoteViews;
    }

    private static String a(Context context, a aVar) {
        if (context == null || aVar == null) {
            return "";
        }
        if (!TextUtils.isEmpty(aVar.s)) {
            return aVar.s;
        }
        return context.getResources().getString(context.getApplicationInfo().labelRes);
    }

    private static void a(Context context, int i, RemoteViews remoteViews, a aVar) {
        if (context == null || remoteViews == null || aVar == null) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "showRightBtn error");
        } else if ((a.STYLE_2.ordinal() == aVar.L || a.STYLE_3.ordinal() == aVar.L || a.STYLE_4.ordinal() == aVar.L) && !TextUtils.isEmpty(aVar.N[0]) && !TextUtils.isEmpty(aVar.P[0])) {
            int a = d.a(context, "id", "right_btn");
            remoteViews.setViewVisibility(a, 0);
            remoteViews.setTextViewText(a, aVar.N[0]);
            remoteViews.setOnClickPendingIntent(a, a(context, i, aVar.P[0]));
        }
    }

    private static void a(Context context, Builder builder, int i, Bitmap bitmap, a aVar) {
        if (aVar == null || aVar.q == null) {
            com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "msg is null");
        } else if (!TextUtils.isEmpty(aVar.q) && aVar.q.contains("##")) {
            builder.setTicker(aVar.q.replace("##", "，"));
            if (com.huawei.android.pushselfshow.utils.a.c()) {
                builder.setLargeIcon(bitmap);
                builder.setContentTitle(a(context, aVar));
                Style inboxStyle = new InboxStyle();
                String[] split = aVar.q.split("##");
                int length = split.length;
                if (length > 4) {
                    length = 4;
                }
                if (!TextUtils.isEmpty(aVar.S)) {
                    inboxStyle.setBigContentTitle(aVar.S);
                    builder.setContentText(aVar.S);
                    if (4 == length) {
                        length--;
                    }
                }
                for (int i2 = 0; i2 < length; i2++) {
                    inboxStyle.addLine(split[i2]);
                }
                if (aVar.N != null && aVar.N.length > 0) {
                    length = 0;
                    while (length < aVar.N.length) {
                        if (!(TextUtils.isEmpty(aVar.N[length]) || TextUtils.isEmpty(aVar.P[length]))) {
                            builder.addAction(0, aVar.N[length], a(context, i, aVar.P[length]));
                        }
                        length++;
                    }
                }
                builder.setStyle(inboxStyle);
                return;
            }
            builder.setContentText(aVar.q.replace("##", "，"));
        }
    }

    private static void a(Context context, Bitmap bitmap, RemoteViews remoteViews) {
        if (context != null && remoteViews != null && com.huawei.android.pushselfshow.utils.a.b()) {
            if (bitmap == null) {
                int i = context.getApplicationInfo().icon;
                if (i == 0) {
                    i = context.getResources().getIdentifier("btn_star_big_on", "drawable", "android");
                    if (i == 0) {
                        i = 17301651;
                    }
                }
                remoteViews.setImageViewResource(d.a(context, "id", "icon"), i);
                return;
            }
            remoteViews.setImageViewBitmap(d.a(context, "id", "icon"), bitmap);
        }
    }

    private static RemoteViews b(Context context, int i, Bitmap bitmap, a aVar) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), d.c(context, "hwpush_layout4"));
        a(context, bitmap, remoteViews);
        a(context, i, remoteViews, aVar);
        remoteViews.setTextViewText(d.e(context, "title"), a(context, aVar));
        if (aVar.R == null || aVar.R.length <= 0) {
            return remoteViews;
        }
        com.huawei.android.pushselfshow.utils.c.a aVar2 = new com.huawei.android.pushselfshow.utils.c.a();
        remoteViews.removeAllViews(d.e(context, "linear_icons"));
        Bitmap bitmap2 = null;
        for (int i2 = 0; i2 < aVar.R.length; i2++) {
            RemoteViews remoteViews2 = new RemoteViews(context.getPackageName(), d.a(context, "layout", "hwpush_icons_layout"));
            if (!TextUtils.isEmpty(aVar.R[i2])) {
                bitmap2 = aVar2.a(context, aVar.R[i2]);
            }
            if (bitmap2 != null) {
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "rescale bitmap success");
                remoteViews2.setImageViewBitmap(d.a(context, "id", "smallicon"), bitmap2);
                remoteViews.addView(d.a(context, "id", "linear_icons"), remoteViews2);
            }
        }
        return remoteViews;
    }

    private static boolean b(Context context, Builder builder, int i, Bitmap bitmap, a aVar) {
        builder.setContentTitle(a(context, aVar));
        builder.setContentText(aVar.q);
        builder.setLargeIcon(bitmap);
        if (!com.huawei.android.pushselfshow.utils.a.c()) {
            return true;
        }
        com.huawei.android.pushselfshow.utils.c.a aVar2 = new com.huawei.android.pushselfshow.utils.c.a();
        Bitmap bitmap2 = null;
        if (!TextUtils.isEmpty(aVar.T)) {
            bitmap2 = aVar2.a(context, aVar.T);
        }
        if (bitmap2 == null) {
            return false;
        }
        Style bigPictureStyle = new BigPictureStyle();
        bigPictureStyle.bigPicture(bitmap2);
        int i2 = 0;
        while (i2 < aVar.N.length) {
            if (!(TextUtils.isEmpty(aVar.N[i2]) || TextUtils.isEmpty(aVar.P[i2]))) {
                builder.addAction(0, aVar.N[i2], a(context, i, aVar.P[i2]));
            }
            i2++;
        }
        builder.setStyle(bigPictureStyle);
        return true;
    }

    private static RemoteViews c(Context context, int i, Bitmap bitmap, a aVar) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), d.a(context, "layout", "hwpush_layout7"));
        a(context, bitmap, remoteViews);
        remoteViews.setTextViewText(d.a(context, "id", "title"), a(context, aVar));
        remoteViews.setTextViewText(d.a(context, "id", "text"), aVar.q);
        if (aVar.O == null || aVar.O.length <= 0 || aVar.P == null || aVar.P.length <= 0 || aVar.O.length != aVar.P.length) {
            return remoteViews;
        }
        com.huawei.android.pushselfshow.utils.c.a aVar2 = new com.huawei.android.pushselfshow.utils.c.a();
        remoteViews.removeAllViews(d.a(context, "id", "linear_buttons"));
        int i2 = 0;
        while (i2 < aVar.O.length) {
            RemoteViews remoteViews2 = new RemoteViews(context.getPackageName(), d.a(context, "layout", "hwpush_buttons_layout"));
            Bitmap bitmap2 = null;
            if (!TextUtils.isEmpty(aVar.O[i2])) {
                bitmap2 = aVar2.a(context, aVar.O[i2]);
            }
            if (!(bitmap2 == null || TextUtils.isEmpty(aVar.P[i2]))) {
                int a = d.a(context, "id", "small_btn");
                remoteViews2.setImageViewBitmap(a, bitmap2);
                remoteViews2.setOnClickPendingIntent(a, a(context, i, aVar.P[i2]));
                remoteViews.addView(d.a(context, "id", "linear_buttons"), remoteViews2);
            }
            i2++;
        }
        return remoteViews;
    }
}
