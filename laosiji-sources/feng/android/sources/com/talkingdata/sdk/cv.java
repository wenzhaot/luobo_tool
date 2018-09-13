package com.talkingdata.sdk;

import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.talkingdata.sdk.zz.a;
import com.umeng.analytics.pro.b;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/* compiled from: td */
public class cv {
    private static volatile cv a = null;

    public final void onTDEBEventSession(a aVar) {
        if (aVar != null && aVar.paraMap != null) {
            try {
                int parseInt = Integer.parseInt(String.valueOf(aVar.paraMap.get("apiType")));
                if (parseInt == 10) {
                    b(aVar.paraMap);
                } else if (parseInt == 11) {
                    c(aVar.paraMap);
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    private void a(HashMap hashMap) {
        try {
            ar.c(Long.parseLong(String.valueOf(hashMap.get("occurTime"))), (a) hashMap.get("service"));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private final void b(HashMap hashMap) {
        try {
            a aVar = (a) hashMap.get("service");
            long parseLong = Long.parseLong(String.valueOf(hashMap.get("occurTime")));
            long c = ar.c(aVar);
            long e = ar.e(aVar);
            if (e <= c) {
                e = c;
            }
            if (parseLong - e > 30000) {
                a(aVar);
                a(parseLong, aVar);
                ar.setLastActivity("");
                return;
            }
            aq.iForDeveloper("[Session] - Same session as before!");
            dl.a().setSessionId(ar.a(aVar));
            dl.a().setSessionStartTime(c);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void a(a aVar) {
        try {
            String a = ar.a(aVar);
            if (a != null && !a.trim().isEmpty()) {
                long c = ar.c(aVar);
                long e = ar.e(aVar) - c;
                if ((aVar.name().equals("APP") || aVar.name().equals("APP_SQL") || aVar.name().equals("TRACKING") || aVar.name().equals("FINTECH")) && e < 500) {
                    e = -1000;
                }
                dd ddVar = new dd();
                ddVar.b = b.ac;
                ddVar.c = "end";
                Map treeMap = new TreeMap();
                treeMap.put(Parameters.SESSION_ID, a);
                treeMap.put("start", Long.valueOf(c));
                treeMap.put("duration", Long.valueOf(e / 1000));
                ddVar.d = treeMap;
                ddVar.a = aVar;
                br.a().post(ddVar);
                b(aVar);
                ar.a(null, aVar);
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void a(long j, a aVar) {
        long j2 = 0;
        try {
            aq.iForDeveloper("[Session] - New session!");
            String uuid = UUID.randomUUID().toString();
            aq.iForDeveloper("[Session] - Id: " + uuid);
            long e = ar.e(aVar);
            long j3 = j - e;
            if (0 != e) {
                j2 = j3;
            }
            ar.a(uuid, aVar);
            ar.a(j, aVar);
            ar.b(uuid, aVar);
            dl.a().setSessionId(uuid);
            dl.a().setSessionStartTime(j);
            dd ddVar = new dd();
            ddVar.b = b.ac;
            ddVar.c = "begin";
            Map treeMap = new TreeMap();
            treeMap.put(Parameters.SESSION_ID, uuid);
            treeMap.put("interval", Long.valueOf(j2 / 1000));
            ddVar.d = treeMap;
            ddVar.a = aVar;
            br.a().post(ddVar);
            ab.J.set(false);
            b(aVar);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private final void c(HashMap hashMap) {
        try {
            a aVar = (a) hashMap.get("service");
            long parseLong = Long.parseLong(String.valueOf(hashMap.get("occurTime")));
            if (hashMap.containsKey("sessionEnd")) {
                a(aVar);
                return;
            }
            if (hashMap.containsKey("pageName")) {
                ar.setLastActivity(String.valueOf(hashMap.get("pageName")));
            }
            b(aVar);
            ar.c(parseLong, aVar);
            ab.C = null;
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void b(a aVar) {
        dc dcVar = new dc();
        dcVar.a = aVar;
        dcVar.b = dc.a.IMMEDIATELY;
        br.a().post(dcVar);
    }

    public static cv a() {
        if (a == null) {
            synchronized (cv.class) {
                if (a == null) {
                    a = new cv();
                }
            }
        }
        return a;
    }

    private cv() {
    }

    static {
        try {
            br.a().register(a());
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }
}
