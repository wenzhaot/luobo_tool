package com.taobao.accs.a;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Environment;
import android.text.TextUtils;
import com.taobao.accs.client.AccsConfig.ACCS_GROUP;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.client.GlobalConfig;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.internal.ServiceImpl;
import com.taobao.accs.internal.b;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.c;
import com.taobao.accs.utl.i;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Taobao */
public class a {
    public static final String ACTION_ACCS_CUSTOM_ELECTION = ("com.taobao.accs.intent.action." + GlobalConfig.mGroup + "ELECTION");
    public static final String ACTION_ACCS_ELECTION = "com.taobao.accs.intent.action.ELECTION";
    public static final String TAG = "ElectionServiceUtil";
    public static String a = null;
    public static boolean b = false;
    private static File c = null;
    private static File d = null;
    private static long e = 0;
    private static final Random f = new Random();

    /* compiled from: Taobao */
    public static class a {
        public String a = "";
        public int b = 0;
    }

    public static void a() {
        try {
            c = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + d());
            ALog.i(TAG, "path=" + c, new Object[0]);
            d = new File(c, b.ELECTION_SERVICE_ID);
            a = d.getPath();
        } catch (Throwable th) {
            ALog.e(TAG, TAG, th, new Object[0]);
        }
    }

    public static final void a(Context context, a aVar) {
        if (aVar != null) {
            GlobalClientInfo.getInstance(context).setElectionReslt(aVar);
            ThreadPoolExecutorFactory.execute(new b(context, aVar));
        }
    }

    public static final a a(Context context) {
        if (!b()) {
            return new a();
        }
        a electionResult = GlobalClientInfo.getInstance(context).getElectionResult();
        if (electionResult == null) {
            return b(context);
        }
        ALog.i(TAG, "getElectionResult from mem", "host", electionResult.a, "retry", Integer.valueOf(electionResult.b));
        return electionResult;
    }

    public static final boolean b() {
        if (!b) {
            return i.c();
        }
        ALog.e(TAG, "try Election Fail, disable election!!", new Object[0]);
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b4 A:{SYNTHETIC, Splitter: B:34:0x00b4} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c0 A:{SYNTHETIC, Splitter: B:40:0x00c0} */
    public static final com.taobao.accs.a.a.a b(android.content.Context r10) {
        /*
        r3 = new com.taobao.accs.a.a$a;
        r3.<init>();
        r1 = 0;
        r0 = c;	 Catch:{ Throwable -> 0x00a5 }
        if (r0 != 0) goto L_0x000d;
    L_0x000a:
        a();	 Catch:{ Throwable -> 0x00a5 }
    L_0x000d:
        r0 = new java.io.File;	 Catch:{ Throwable -> 0x00a5 }
        r2 = a;	 Catch:{ Throwable -> 0x00a5 }
        r0.<init>(r2);	 Catch:{ Throwable -> 0x00a5 }
        r2 = r0.exists();	 Catch:{ Throwable -> 0x00a5 }
        if (r2 == 0) goto L_0x0067;
    L_0x001a:
        r2 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x00a5 }
        r2.<init>(r0);	 Catch:{ Throwable -> 0x00a5 }
        r0 = r2.available();	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r0 = new byte[r0];	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r2.read(r0);	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r1 = new java.lang.String;	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r4 = "UTF-8";
        r1.<init>(r0, r4);	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r0 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r0.<init>(r1);	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        if (r0 == 0) goto L_0x009e;
    L_0x0037:
        r1 = "package";
        r1 = r0.getString(r1);	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r4 = com.taobao.accs.utl.UtilityImpl.packageExist(r10, r1);	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        if (r4 == 0) goto L_0x004f;
    L_0x0044:
        r3.a = r1;	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r1 = "lastFlushTime";
        r4 = r0.getLong(r1);	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        e = r4;	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
    L_0x004f:
        r4 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r6 = e;	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r8 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r6 = r6 + r8;
        r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r1 >= 0) goto L_0x009a;
    L_0x005d:
        r1 = "retry";
        r0 = r0.getInt(r1);	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r3.b = r0;	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
        r1 = r2;
    L_0x0067:
        r0 = "ElectionServiceUtil";
        r2 = "getElectionResult";
        r4 = 4;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x00a5 }
        r5 = 0;
        r6 = "host";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x00a5 }
        r5 = 1;
        r6 = r3.a;	 Catch:{ Throwable -> 0x00a5 }
        r4[r5] = r6;	 Catch:{ Throwable -> 0x00a5 }
        r5 = 2;
        r6 = "retry";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x00a5 }
        r5 = 3;
        r6 = r3.b;	 Catch:{ Throwable -> 0x00a5 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ Throwable -> 0x00a5 }
        r4[r5] = r6;	 Catch:{ Throwable -> 0x00a5 }
        com.taobao.accs.utl.ALog.i(r0, r2, r4);	 Catch:{ Throwable -> 0x00a5 }
        r0 = com.taobao.accs.client.GlobalClientInfo.getInstance(r10);	 Catch:{ Throwable -> 0x00a5 }
        r0.setElectionReslt(r3);	 Catch:{ Throwable -> 0x00a5 }
        if (r1 == 0) goto L_0x0099;
    L_0x0096:
        r1.close();	 Catch:{ IOException -> 0x00a0 }
    L_0x0099:
        return r3;
    L_0x009a:
        r0 = 0;
        e = r0;	 Catch:{ Throwable -> 0x00cc, all -> 0x00c9 }
    L_0x009e:
        r1 = r2;
        goto L_0x0067;
    L_0x00a0:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0099;
    L_0x00a5:
        r0 = move-exception;
    L_0x00a6:
        r2 = "ElectionServiceUtil";
        r4 = "readFile is error";
        r5 = 0;
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x00bd }
        com.taobao.accs.utl.ALog.e(r2, r4, r0, r5);	 Catch:{ all -> 0x00bd }
        if (r1 == 0) goto L_0x0099;
    L_0x00b4:
        r1.close();	 Catch:{ IOException -> 0x00b8 }
        goto L_0x0099;
    L_0x00b8:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0099;
    L_0x00bd:
        r0 = move-exception;
    L_0x00be:
        if (r1 == 0) goto L_0x00c3;
    L_0x00c0:
        r1.close();	 Catch:{ IOException -> 0x00c4 }
    L_0x00c3:
        throw r0;
    L_0x00c4:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00c3;
    L_0x00c9:
        r0 = move-exception;
        r1 = r2;
        goto L_0x00be;
    L_0x00cc:
        r0 = move-exception;
        r1 = r2;
        goto L_0x00a6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.a.a.b(android.content.Context):com.taobao.accs.a.a$a");
    }

    public static final void a(Context context, byte[] bArr) {
        if (bArr == null || c == null) {
            ALog.e(TAG, "saveBlackList null", "data", bArr, "path", c);
            return;
        }
        ALog.i(TAG, "saveBlackList", "path", c + "/accs_blacklist");
        GlobalClientInfo.getInstance(context).setElectionBlackList(a(bArr));
        ThreadPoolExecutorFactory.execute(new c(context, bArr, r0));
    }

    public static final void c(Context context) {
        ALog.i(TAG, "clearBlackList", new Object[0]);
        GlobalClientInfo.getInstance(context).setElectionBlackList(null);
        if (c == null) {
            ALog.e(TAG, "clearBlackList path null", new Object[0]);
            return;
        }
        try {
            File file = new File(c + "accs_blacklist");
            if (file.exists()) {
                file.delete();
            }
        } catch (Throwable th) {
            ALog.e(TAG, "clearBlackList", th, new Object[0]);
        }
    }

    public static final Map<String, Set<Integer>> d(Context context) {
        Map<String, Set<Integer>> electionBlackList = GlobalClientInfo.getInstance(context).getElectionBlackList();
        if (electionBlackList != null) {
            ALog.i(TAG, "getBlackList from mem", electionBlackList.toString());
            return electionBlackList;
        } else if (c == null) {
            ALog.e(TAG, "getBlackList path null", new Object[0]);
            return null;
        } else {
            try {
                byte[] a = c.a(new File(c + "accs_blacklist"));
                if (a != null) {
                    electionBlackList = a(a);
                }
            } catch (Throwable th) {
                ALog.e(TAG, "getBlackList", th, new Object[0]);
            }
            GlobalClientInfo.getInstance(context).setElectionBlackList(electionBlackList);
            return electionBlackList;
        }
    }

    private static Map<String, Set<Integer>> a(byte[] bArr) {
        Throwable th;
        Map<String, Set<Integer>> map = null;
        if (bArr != null) {
            try {
                JSONArray jSONArray = new JSONObject(new String(bArr)).getJSONArray(b.ELECTION_KEY_BLACKLIST);
                if (jSONArray != null && jSONArray.length() > 0) {
                    Map<String, Set<Integer>> hashMap = new HashMap();
                    int i = 0;
                    while (i < jSONArray.length()) {
                        try {
                            JSONObject jSONObject = jSONArray.getJSONObject(i);
                            JSONArray jSONArray2 = jSONObject.getJSONArray(b.ELECTION_KEY_SDKVS);
                            String string = jSONObject.getString(Constants.KEY_ELECTION_PKG);
                            if (jSONArray2 != null && jSONArray2.length() > 0) {
                                Set hashSet = new HashSet();
                                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                    hashSet.add(Integer.valueOf(jSONArray2.getInt(i)));
                                }
                                hashMap.put(string, hashSet);
                            }
                            i++;
                        } catch (Throwable th2) {
                            Throwable th3 = th2;
                            map = hashMap;
                            th = th3;
                            ALog.e(TAG, "praseBlackList", th, new Object[0]);
                            return map;
                        }
                    }
                    map = hashMap;
                }
                String str = TAG;
                String str2 = "praseBlackList";
                Object[] objArr = new Object[2];
                objArr[0] = b.ELECTION_KEY_BLACKLIST;
                objArr[1] = map == null ? "null" : map.toString();
                ALog.i(str, str2, objArr);
            } catch (Throwable th4) {
                th = th4;
            }
        }
        return map;
    }

    public static final boolean a(Context context, String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean z;
        if (i != 1) {
            try {
                ALog.w(TAG, "checkApp election version not match", com.umeng.message.common.a.c, str, "require", Integer.valueOf(i), "self ver", Integer.valueOf(1));
                return false;
            } catch (Throwable th) {
                ALog.e(TAG, "checkApp error", th, new Object[0]);
                z = false;
            }
        } else if (ServiceImpl.a(str)) {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo == null) {
                ALog.w(TAG, "checkApp applicaton info null", com.umeng.message.common.a.c, str);
                return false;
            } else if (applicationInfo.enabled) {
                Map d = d(context);
                if (d != null) {
                    ALog.i(TAG, "checkApp", "blackList", d.toString());
                    Set set = (Set) d.get(str);
                    if (set == null || !(set.contains(Integer.valueOf(Constants.SDK_VERSION_CODE)) || set.contains(Integer.valueOf(-1)))) {
                        set = (Set) d.get("*");
                        if (set != null && (set.contains(Integer.valueOf(Constants.SDK_VERSION_CODE)) || set.contains(Integer.valueOf(-1)))) {
                            ALog.w(TAG, "checkApp in blacklist *", com.umeng.message.common.a.c, str, "sdkv", Integer.valueOf(Constants.SDK_VERSION_CODE));
                            return false;
                        }
                    }
                    ALog.w(TAG, "checkApp in blacklist", com.umeng.message.common.a.c, str, "sdkv", Integer.valueOf(Constants.SDK_VERSION_CODE));
                    return false;
                }
                z = true;
                return z;
            } else {
                ALog.i(TAG, "checkApp is disabled", com.umeng.message.common.a.c, str);
                return false;
            }
        } else {
            ALog.w(TAG, "checkApp is unbinded", com.umeng.message.common.a.c, str);
            return false;
        }
    }

    public static String a(Context context, Map<String, Integer> map) {
        if (map == null || map.size() <= 0) {
            ALog.e(TAG, "localElection failed, packMap null", new Object[0]);
            return null;
        }
        List arrayList = new ArrayList();
        long j = -1;
        for (Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            long intValue = (long) ((Integer) entry.getValue()).intValue();
            if (intValue > j) {
                arrayList.clear();
                j = intValue;
            }
            if (intValue == j) {
                arrayList.add(str);
            }
        }
        String str2 = (String) arrayList.get(f.nextInt(10000) % arrayList.size());
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        ALog.i(TAG, "localElection localResult null, user curr app", Constants.KEY_ELECTION_PKG, context.getPackageName());
        return context.getPackageName();
    }

    public static final String e(Context context) {
        Throwable th;
        String str;
        try {
            ResolveInfo resolveService = context.getPackageManager().resolveService(new Intent(c()), 0);
            if (resolveService == null) {
                ALog.e(TAG, "getResolveService resolveInfo null", new Object[0]);
                return null;
            }
            ServiceInfo serviceInfo = resolveService.serviceInfo;
            if (serviceInfo == null || !serviceInfo.isEnabled()) {
                ALog.e(TAG, "getResolveService serviceinfo null or disabled", new Object[0]);
                return null;
            }
            str = serviceInfo.packageName;
            if (TextUtils.isEmpty(str)) {
                ALog.e(TAG, "getResolveService clientPack null", new Object[0]);
                return null;
            }
            try {
                ALog.i(TAG, "getResolveService", com.umeng.message.common.a.c, str);
                return str;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            str = null;
            th = th4;
            ALog.e(TAG, "getResolveService error", th, new Object[0]);
            return str;
        }
    }

    public static String c() {
        if (GlobalConfig.mGroup == ACCS_GROUP.TAOBAO) {
            return "com.taobao.accs.intent.action.ELECTION";
        }
        return ACTION_ACCS_CUSTOM_ELECTION;
    }

    public static String d() {
        if (GlobalConfig.mGroup == ACCS_GROUP.TAOBAO) {
            return "/accs/accs_main/1";
        }
        return "/accs/" + GlobalConfig.mGroup + "/" + 1;
    }
}
