package com.umeng.commonsdk;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMLogDataProtocol.UMBusinessType;
import com.umeng.commonsdk.framework.UMModuleRegister;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.framework.b;
import com.umeng.commonsdk.statistics.common.e;

/* compiled from: UMConfigureInternation */
public class a {
    private static boolean a = false;

    public static synchronized void a(final Context context) {
        synchronized (a.class) {
            if (context != null) {
                try {
                    if (!a) {
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Object a = b.a(context);
                                    CharSequence packageName = context.getPackageName();
                                    if (!TextUtils.isEmpty(a) && !TextUtils.isEmpty(packageName) && a.equals(packageName)) {
                                        try {
                                            if (UMEnvelopeBuild.isReadyBuild(context, UMBusinessType.U_INTERNAL)) {
                                                UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.m, com.umeng.commonsdk.internal.b.a(context).a(), null);
                                            }
                                        } catch (Throwable th) {
                                        }
                                    }
                                } catch (Throwable th2) {
                                }
                            }
                        }).start();
                        a = true;
                    }
                } catch (Throwable th) {
                    e.c(UMModuleRegister.INNER, "e is " + th.getMessage());
                }
            }
        }
        return;
    }
}
