package com.huawei.android.pushselfshow.richpush.html.a;

import android.os.Looper;
import android.widget.Toast;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.a;

class e extends Thread {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    public void run() {
        try {
            Looper.prepare();
            Toast.makeText(this.a.f, a.a(this.a.f, "手机上没有安装应用市场，建议安装智汇云应用市场", "Not Found App Market"), 0).show();
            Looper.loop();
            try {
                Looper.myLooper().quit();
            } catch (Throwable e) {
                c.c("PushSelfShowLog", e.toString(), e);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            try {
                Looper.myLooper().quit();
            } catch (Throwable e3) {
                c.c("PushSelfShowLog", e3.toString(), e3);
            }
        } catch (Throwable e32) {
            try {
                Looper.myLooper().quit();
            } catch (Throwable e4) {
                c.c("PushSelfShowLog", e4.toString(), e4);
            }
            throw e32;
        }
    }
}
