package com.talkingdata.sdk;

import android.app.Activity;
import android.os.Message;

/* compiled from: td */
final class ag {
    private ag() {
    }

    static void a(Activity activity, a aVar) {
        try {
            if (aVar.name().equals("APP") || aVar.name().equals("FINTECH")) {
                if (ab.n == 2) {
                    bo.execute(new ah(aVar));
                }
                ab.n = 0;
                zz.b().removeMessages(0);
                if (activity != null && (activity.getChangingConfigurations() & 128) == 128) {
                    aq.iForDeveloper("Ignore page changing during screen switch");
                    zz.c = true;
                    return;
                }
            }
            bo.execute(new ai(aVar));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    static void b(Activity activity, a aVar) {
        try {
            if (aVar.name().equals("APP") || aVar.name().equals("APP_SQL") || aVar.name().equals("FINTECH")) {
                ab.n = 1;
                zz.b().removeMessages(0);
                Message obtain = Message.obtain();
                obtain.obj = aVar;
                obtain.what = 0;
                zz.b().sendMessageDelayed(obtain, 30000);
            }
            bo.execute(new aj(aVar, activity));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }
}
