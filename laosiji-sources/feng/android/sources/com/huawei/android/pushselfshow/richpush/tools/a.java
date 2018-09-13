package com.huawei.android.pushselfshow.richpush.tools;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.widget.Toast;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.d;
import java.io.File;
import org.android.agoo.common.AgooConstants;

public class a {
    public Resources a;
    public Activity b;
    private com.huawei.android.pushselfshow.b.a c = null;

    public a(Activity activity) {
        this.b = activity;
        this.a = activity.getResources();
    }

    public void a() {
        try {
            c.a("PushSelfShowLog", "creat shortcut");
            Intent intent = new Intent();
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            Parcelable decodeResource = BitmapFactory.decodeResource(this.b.getResources(), d.g(this.b, "hwpush_main_icon"));
            intent.putExtra("android.intent.extra.shortcut.NAME", this.b.getResources().getString(d.a(this.b, "hwpush_msg_collect")));
            intent.putExtra("android.intent.extra.shortcut.ICON", decodeResource);
            intent.putExtra(AgooConstants.MESSAGE_DUPLICATE, false);
            Object intent2 = new Intent("com.huawei.android.push.intent.RICHPUSH");
            intent2.putExtra("type", "favorite");
            intent2.addFlags(1476395008);
            String str = "com.huawei.android.pushagent";
            if (com.huawei.android.pushselfshow.utils.a.b(this.b, str)) {
                intent2.setPackage(str);
            } else {
                intent2.setPackage(this.b.getPackageName());
            }
            intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
            this.b.sendBroadcast(intent);
        } catch (Throwable e) {
            c.d("PushSelfShowLog", "creat shortcut error", e);
        }
    }

    public void a(com.huawei.android.pushselfshow.b.a aVar) {
        this.c = aVar;
    }

    public void b() {
        try {
            if (this.c == null || this.c.D == null) {
                Toast.makeText(this.b, com.huawei.android.pushselfshow.utils.a.a(this.b, "内容保存失败", "Save Failed"), 0).show();
                return;
            }
            c.e("PushSelfShowLog", "the rpl is " + this.c.D);
            String str = "";
            str = this.c.D.startsWith("file://") ? this.c.D.substring(7) : this.c.D;
            c.e("PushSelfShowLog", "filePath is " + str);
            if ("text/html_local".equals(this.c.F)) {
                File parentFile = new File(str).getParentFile();
                if (parentFile != null && parentFile.isDirectory() && this.c.D.contains("richpush")) {
                    str = parentFile.getAbsolutePath();
                    String replace = str.replace("richpush", "shotcut");
                    c.b("PushSelfShowLog", "srcDir is %s ,destDir is %s", str, replace);
                    if (com.huawei.android.pushselfshow.utils.a.a(str, replace)) {
                        this.c.D = Uri.fromFile(new File(replace + File.separator + "index.html")).toString();
                    } else {
                        Toast.makeText(this.b, com.huawei.android.pushselfshow.utils.a.a(this.b, "内容保存失败", "Save Failed"), 0).show();
                        return;
                    }
                }
            }
            c.a("PushSelfShowLog", "insert data into db");
            a();
            boolean a = com.huawei.android.pushselfshow.utils.a.c.a(this.b, this.c.r, this.c);
            c.e("PushSelfShowLog", "insert result is " + a);
            if (a) {
                com.huawei.android.pushselfshow.utils.a.a(this.b, AgooConstants.ACK_PACK_NOBIND, this.c);
            } else {
                c.d("PushSelfShowLog", "save icon fail");
            }
        } catch (Throwable e) {
            c.c("PushSelfShowLog", "SaveBtnClickListener error ", e);
        }
    }
}
