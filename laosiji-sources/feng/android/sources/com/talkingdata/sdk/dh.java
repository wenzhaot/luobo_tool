package com.talkingdata.sdk;

import com.qiniu.android.common.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: td */
public class dh {
    static HashMap a = new HashMap();
    static HashMap b = new HashMap();
    static String c = Constants.UTF_8;
    static byte[] d;
    private static volatile dh e = null;

    static {
        try {
            br.a().register(b());
        } catch (Throwable th) {
        }
    }

    public final synchronized void onTDEBEventDataStore(dd ddVar) {
        if (ddVar != null) {
            try {
                dn dnVar;
                if (ddVar.a != null && ddVar.a.name().equals("ENV")) {
                    dnVar = new dn(ddVar.b, ddVar.c);
                    dnVar.setData(ddVar.d);
                    dj.a().a(new di(bx.a(ed.a().a(dnVar, true, ddVar.a, ddVar.e).toString().getBytes())), ddVar);
                } else if (ddVar.a == null || !ddVar.a.name().equals("BG")) {
                    dnVar = new dn(ddVar.b, ddVar.c);
                    dnVar.setData(ddVar.d);
                    dj.a().a(new di(bx.a(ed.a().a(dnVar, true, ddVar.a, ddVar.e).toString().getBytes())), ddVar);
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
        return;
    }

    public synchronized List a(a aVar) {
        List arrayList;
        arrayList = new ArrayList();
        try {
            byte[] b;
            List<byte[]> a = dj.a().a(aVar, 100, null);
            if (a.size() > 0) {
                for (byte[] b2 : a) {
                    try {
                        arrayList.add(new String(bo.b(b2, d)));
                    } catch (Throwable th) {
                    }
                }
            }
            int size = a.size();
            if (size < 100) {
                List<byte[]> a2 = dj.a().a(aVar, 100 - size, dj.a);
                if (a2.size() > 0) {
                    for (byte[] b22 : a2) {
                        try {
                            b22 = bx.b(b22);
                            if (!(b22 == null || b22.length == 0)) {
                                arrayList.add(new String(b22));
                            }
                        } catch (Throwable th2) {
                        }
                    }
                }
            }
        } catch (Throwable th3) {
            cs.postSDKError(th3);
        }
        return arrayList;
    }

    public void sendMessageSuccess(a aVar) {
        try {
            dj.a().confirmRead(aVar);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public static void a() {
    }

    public static dh b() {
        if (e == null) {
            synchronized (dh.class) {
                if (e == null) {
                    e = new dh();
                }
            }
        }
        return e;
    }

    private dh() {
        String c = bo.c(ab.g.getPackageName());
        if (ab.g == null || c == null) {
            d = ab.class.getSimpleName().getBytes();
        } else {
            d = c.getBytes();
        }
    }
}
