package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.stub.StubApp;
import com.umeng.commonsdk.proguard.b;
import com.umeng.commonsdk.proguard.u;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback;
import com.umeng.commonsdk.statistics.internal.d;
import com.umeng.commonsdk.statistics.proto.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ImprintHandler {
    private static final String a = "ImprintHandler";
    private static Object b = new Object();
    private static final String c = ".imprint";
    private static final byte[] d = "pbl0".getBytes();
    private static Map<String, ArrayList<UMImprintChangeCallback>> f = new HashMap();
    private static Object g = new Object();
    private static ImprintHandler j = null;
    private static Context k;
    private d e;
    private a h = new a();
    private com.umeng.commonsdk.statistics.proto.d i = null;

    public static class a {
        private Map<String, String> a = new HashMap();

        a() {
        }

        public synchronized void a(String str) {
            if (this.a != null && this.a.size() > 0 && !TextUtils.isEmpty(str) && this.a.containsKey(str)) {
                this.a.remove(str);
            }
        }

        a(com.umeng.commonsdk.statistics.proto.d dVar) {
            a(dVar);
        }

        public void a(com.umeng.commonsdk.statistics.proto.d dVar) {
            if (dVar != null) {
                b(dVar);
            }
        }

        private synchronized void b(com.umeng.commonsdk.statistics.proto.d dVar) {
            if (dVar != null) {
                try {
                    if (dVar.e()) {
                        Map c = dVar.c();
                        for (String str : c.keySet()) {
                            if (!TextUtils.isEmpty(str)) {
                                e eVar = (e) c.get(str);
                                if (eVar != null) {
                                    Object b = eVar.b();
                                    if (!TextUtils.isEmpty(b)) {
                                        this.a.put(str, b);
                                        if (AnalyticsConstants.UM_DEBUG) {
                                            Log.i(ImprintHandler.a, "imKey is " + str + ", imValue is " + b);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                }
            }
        }

        public synchronized String a(String str, String str2) {
            if (!TextUtils.isEmpty(str) && this.a.size() > 0) {
                String str3 = (String) this.a.get(str);
                if (!TextUtils.isEmpty(str3)) {
                    str2 = str3;
                }
            }
            return str2;
        }
    }

    private ImprintHandler(Context context) {
        k = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public static synchronized ImprintHandler getImprintService(Context context) {
        ImprintHandler imprintHandler;
        synchronized (ImprintHandler.class) {
            if (j == null) {
                j = new ImprintHandler(context);
                j.e();
            }
            imprintHandler = j;
        }
        return imprintHandler;
    }

    private static void a(String str, UMImprintChangeCallback uMImprintChangeCallback) {
        int i = 0;
        synchronized (g) {
            try {
                if (f.containsKey(str)) {
                    ArrayList arrayList = (ArrayList) f.get(str);
                    int size = arrayList.size();
                    com.umeng.commonsdk.statistics.common.e.c("--->>> addCallback: before add: callbacks size is: " + size);
                    while (i < size) {
                        if (uMImprintChangeCallback == arrayList.get(i)) {
                            com.umeng.commonsdk.statistics.common.e.c("--->>> addCallback: callback has exist, just exit");
                            return;
                        }
                        i++;
                    }
                    arrayList.add(uMImprintChangeCallback);
                    com.umeng.commonsdk.statistics.common.e.c("--->>> addCallback: after add: callbacks size is: " + arrayList.size());
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                int size2 = arrayList2.size();
                com.umeng.commonsdk.statistics.common.e.c("--->>> addCallback: before add: callbacks size is: " + size2);
                for (int i2 = 0; i2 < size2; i2++) {
                    if (uMImprintChangeCallback == arrayList2.get(i2)) {
                        com.umeng.commonsdk.statistics.common.e.c("--->>> addCallback: callback has exist, just exit");
                        return;
                    }
                }
                arrayList2.add(uMImprintChangeCallback);
                com.umeng.commonsdk.statistics.common.e.c("--->>> addCallback: after add: callbacks size is: " + arrayList2.size());
                f.put(str, arrayList2);
                return;
            } catch (Throwable th) {
                b.a(k, th);
            }
        }
    }

    private static void b(String str, UMImprintChangeCallback uMImprintChangeCallback) {
        if (!TextUtils.isEmpty(str) && uMImprintChangeCallback != null) {
            synchronized (g) {
                try {
                    if (f.containsKey(str)) {
                        ArrayList arrayList = (ArrayList) f.get(str);
                        if (uMImprintChangeCallback != null && arrayList.size() > 0) {
                            int size = arrayList.size();
                            com.umeng.commonsdk.statistics.common.e.c("--->>> removeCallback: before remove: callbacks size is: " + size);
                            for (int i = 0; i < size; i++) {
                                if (uMImprintChangeCallback == arrayList.get(i)) {
                                    com.umeng.commonsdk.statistics.common.e.c("--->>> removeCallback: remove index " + i);
                                    arrayList.remove(i);
                                    break;
                                }
                            }
                            com.umeng.commonsdk.statistics.common.e.c("--->>> removeCallback: after remove: callbacks size is: " + arrayList.size());
                            if (arrayList.size() == 0) {
                                com.umeng.commonsdk.statistics.common.e.c("--->>> removeCallback: remove key from map: key = " + str);
                                f.remove(str);
                            }
                        }
                    }
                } catch (Throwable th) {
                    b.a(k, th);
                }
            }
            return;
        }
        return;
    }

    public void registImprintCallback(String str, UMImprintChangeCallback uMImprintChangeCallback) {
        if (!TextUtils.isEmpty(str) && uMImprintChangeCallback != null) {
            a(str, uMImprintChangeCallback);
        }
    }

    public void unregistImprintCallback(String str, UMImprintChangeCallback uMImprintChangeCallback) {
        if (!TextUtils.isEmpty(str) && uMImprintChangeCallback != null) {
            b(str, uMImprintChangeCallback);
        }
    }

    public void a(d dVar) {
        this.e = dVar;
    }

    public String a(com.umeng.commonsdk.statistics.proto.d dVar) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : new TreeMap(dVar.c()).entrySet()) {
            stringBuilder.append((String) entry.getKey());
            if (((e) entry.getValue()).d()) {
                stringBuilder.append(((e) entry.getValue()).b());
            }
            stringBuilder.append(((e) entry.getValue()).e());
            stringBuilder.append(((e) entry.getValue()).h());
        }
        stringBuilder.append(dVar.b);
        return HelperUtils.MD5(stringBuilder.toString()).toLowerCase(Locale.US);
    }

    private boolean c(com.umeng.commonsdk.statistics.proto.d dVar) {
        if (!dVar.i().equals(a(dVar))) {
            return false;
        }
        for (e eVar : dVar.c().values()) {
            byte[] reverseHexString = DataHelper.reverseHexString(eVar.h());
            byte[] a = a(eVar);
            for (int i = 0; i < 4; i++) {
                if (reverseHexString[i] != a[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    public byte[] a(e eVar) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(null);
        allocate.putLong(eVar.e());
        byte[] array = allocate.array();
        byte[] bArr = d;
        byte[] bArr2 = new byte[4];
        for (int i = 0; i < 4; i++) {
            bArr2[i] = (byte) (array[i] ^ bArr[i]);
        }
        return bArr2;
    }

    public void b(com.umeng.commonsdk.statistics.proto.d dVar) {
        String str = null;
        if (dVar == null) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.d("Imprint is null");
            }
        } else if (c(dVar)) {
            String i;
            Object obj;
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.d("Imprint is ok");
            }
            Map hashMap = new HashMap();
            synchronized (this) {
                com.umeng.commonsdk.statistics.proto.d d;
                com.umeng.commonsdk.statistics.proto.d dVar2 = this.i;
                i = dVar2 == null ? null : dVar2.i();
                if (dVar2 == null) {
                    d = d(dVar);
                } else {
                    d = a(dVar2, dVar, hashMap);
                }
                this.i = d;
                if (d != null) {
                    str = d.i();
                }
                if (a(i, str)) {
                    obj = null;
                } else {
                    obj = 1;
                }
            }
            if (this.i != null) {
                if (AnalyticsConstants.UM_DEBUG) {
                }
                if (obj != null) {
                    this.h.a(this.i);
                    if (this.e != null) {
                        this.e.onImprintChanged(this.h);
                    }
                }
            }
            if (hashMap.size() > 0) {
                synchronized (g) {
                    for (Entry entry : hashMap.entrySet()) {
                        i = (String) entry.getKey();
                        String str2 = (String) entry.getValue();
                        if (!TextUtils.isEmpty(i) && f.containsKey(i)) {
                            com.umeng.commonsdk.statistics.common.e.c("--->>> target imprint key is: " + i + "; value is: " + str2);
                            ArrayList arrayList = (ArrayList) f.get(i);
                            if (arrayList != null) {
                                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                    ((UMImprintChangeCallback) arrayList.get(i2)).onImprintValueChanged(i, str2);
                                }
                            }
                        }
                    }
                }
            }
        } else if (AnalyticsConstants.UM_DEBUG) {
            MLog.e("Imprint is not valid");
        }
    }

    private boolean a(String str, String str2) {
        if (str != null) {
            return str.equals(str2);
        }
        if (str2 != null) {
            return false;
        }
        return true;
    }

    private com.umeng.commonsdk.statistics.proto.d a(com.umeng.commonsdk.statistics.proto.d dVar, com.umeng.commonsdk.statistics.proto.d dVar2, Map<String, String> map) {
        if (dVar2 != null) {
            Map c = dVar.c();
            String str = "";
            str = "";
            for (Entry entry : dVar2.c().entrySet()) {
                if (((e) entry.getValue()).d()) {
                    c.put(entry.getKey(), entry.getValue());
                    synchronized (g) {
                        str = (String) entry.getKey();
                        String str2 = ((e) entry.getValue()).a;
                        if (!(TextUtils.isEmpty(str) || !f.containsKey(str) || ((ArrayList) f.get(str)) == null)) {
                            map.put(str, str2);
                        }
                    }
                } else {
                    String str3 = (String) entry.getKey();
                    c.remove(str3);
                    this.h.a(str3);
                }
            }
            dVar.a(dVar2.f());
            dVar.a(a(dVar));
        }
        return dVar;
    }

    private com.umeng.commonsdk.statistics.proto.d d(com.umeng.commonsdk.statistics.proto.d dVar) {
        Map c = dVar.c();
        List<String> arrayList = new ArrayList(c.size() / 2);
        for (Entry entry : c.entrySet()) {
            if (!((e) entry.getValue()).d()) {
                arrayList.add(entry.getKey());
            }
        }
        for (String remove : arrayList) {
            c.remove(remove);
        }
        return dVar;
    }

    public synchronized com.umeng.commonsdk.statistics.proto.d a() {
        return this.i;
    }

    public a b() {
        return this.h;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002c A:{SYNTHETIC, Splitter: B:13:0x002c} */
    private void e() {
        /*
        r4 = this;
        r2 = 0;
        r0 = new java.io.File;
        r1 = k;
        r1 = r1.getFilesDir();
        r3 = ".imprint";
        r0.<init>(r1, r3);
        r3 = b;
        monitor-enter(r3);
        r0 = r0.exists();	 Catch:{ all -> 0x0042 }
        if (r0 != 0) goto L_0x001a;
    L_0x0018:
        monitor-exit(r3);	 Catch:{ all -> 0x0042 }
    L_0x0019:
        return;
    L_0x001a:
        r0 = k;	 Catch:{ Exception -> 0x0045, all -> 0x004e }
        r1 = ".imprint";
        r1 = r0.openFileInput(r1);	 Catch:{ Exception -> 0x0045, all -> 0x004e }
        r2 = com.umeng.commonsdk.statistics.common.HelperUtils.readStreamToByteArray(r1);	 Catch:{ Exception -> 0x005b }
        com.umeng.commonsdk.statistics.common.HelperUtils.safeClose(r1);	 Catch:{ all -> 0x0042 }
    L_0x002a:
        if (r2 == 0) goto L_0x0040;
    L_0x002c:
        r0 = new com.umeng.commonsdk.statistics.proto.d;	 Catch:{ Exception -> 0x0053 }
        r0.<init>();	 Catch:{ Exception -> 0x0053 }
        r1 = new com.umeng.commonsdk.proguard.o;	 Catch:{ Exception -> 0x0053 }
        r1.<init>();	 Catch:{ Exception -> 0x0053 }
        r1.a(r0, r2);	 Catch:{ Exception -> 0x0053 }
        r4.i = r0;	 Catch:{ Exception -> 0x0053 }
        r1 = r4.h;	 Catch:{ Exception -> 0x0053 }
        r1.a(r0);	 Catch:{ Exception -> 0x0053 }
    L_0x0040:
        monitor-exit(r3);	 Catch:{ all -> 0x0042 }
        goto L_0x0019;
    L_0x0042:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0042 }
        throw r0;
    L_0x0045:
        r0 = move-exception;
        r1 = r2;
    L_0x0047:
        r0.printStackTrace();	 Catch:{ all -> 0x0058 }
        com.umeng.commonsdk.statistics.common.HelperUtils.safeClose(r1);	 Catch:{ all -> 0x0042 }
        goto L_0x002a;
    L_0x004e:
        r0 = move-exception;
    L_0x004f:
        com.umeng.commonsdk.statistics.common.HelperUtils.safeClose(r2);	 Catch:{ all -> 0x0042 }
        throw r0;	 Catch:{ all -> 0x0042 }
    L_0x0053:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0042 }
        goto L_0x0040;
    L_0x0058:
        r0 = move-exception;
        r2 = r1;
        goto L_0x004f;
    L_0x005b:
        r0 = move-exception;
        goto L_0x0047;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.idtracking.ImprintHandler.e():void");
    }

    public void c() {
        if (this.i != null) {
            OutputStream fileOutputStream;
            try {
                synchronized (b) {
                    byte[] a = new u().a(this.i);
                    fileOutputStream = new FileOutputStream(new File(k.getFilesDir(), c));
                    fileOutputStream.write(a);
                    fileOutputStream.flush();
                    HelperUtils.safeClose(fileOutputStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable th) {
                HelperUtils.safeClose(fileOutputStream);
            }
        }
    }

    public boolean d() {
        return new File(k.getFilesDir(), c).delete();
    }
}
