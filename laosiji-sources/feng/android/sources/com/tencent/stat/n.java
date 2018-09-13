package com.tencent.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.os.Handler;
import android.os.HandlerThread;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.tencent.stat.a.e;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.k;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class n {
    private static StatLogger e = k.b();
    private static n f = null;
    Handler a = null;
    volatile int b = 0;
    DeviceInfo c = null;
    private w d;
    private HashMap<String, String> g = new HashMap();

    private n(Context context) {
        try {
            HandlerThread handlerThread = new HandlerThread("StatStore");
            handlerThread.start();
            e.w("Launch store thread:" + handlerThread);
            this.a = new Handler(handlerThread.getLooper());
            Context origApplicationContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
            this.d = new w(origApplicationContext);
            this.d.getWritableDatabase();
            this.d.getReadableDatabase();
            b(origApplicationContext);
            c();
            f();
            this.a.post(new o(this));
        } catch (Object th) {
            e.e(th);
        }
    }

    public static synchronized n a(Context context) {
        n nVar;
        synchronized (n.class) {
            if (f == null) {
                f = new n(context);
            }
            nVar = f;
        }
        return nVar;
    }

    public static n b() {
        return f;
    }

    private synchronized void b(int i) {
        try {
            if (this.b > 0 && i > 0) {
                e.i("Load " + Integer.toString(this.b) + " unsent events");
                List arrayList = new ArrayList();
                List<x> arrayList2 = new ArrayList();
                if (i == -1 || i > StatConfig.a()) {
                    i = StatConfig.a();
                }
                this.b -= i;
                c(arrayList2, i);
                e.i("Peek " + Integer.toString(arrayList2.size()) + " unsent events.");
                if (!arrayList2.isEmpty()) {
                    b((List) arrayList2, 2);
                    for (x xVar : arrayList2) {
                        arrayList.add(xVar.b);
                    }
                    d.b().b(arrayList, new u(this, arrayList2, i));
                }
            }
        } catch (Object th) {
            e.e(th);
        }
        return;
    }

    private synchronized void b(e eVar, c cVar) {
        if (StatConfig.getMaxStoreEventCount() > 0) {
            try {
                this.d.getWritableDatabase().beginTransaction();
                if (this.b > StatConfig.getMaxStoreEventCount()) {
                    e.warn("Too many events stored in db.");
                    this.b -= this.d.getWritableDatabase().delete("events", "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)", null);
                }
                ContentValues contentValues = new ContentValues();
                String c = k.c(eVar.d());
                contentValues.put("content", c);
                contentValues.put("send_count", PushConstants.PUSH_TYPE_NOTIFY);
                contentValues.put("status", Integer.toString(1));
                contentValues.put("timestamp", Long.valueOf(eVar.b()));
                if (this.d.getWritableDatabase().insert("events", null, contentValues) == -1) {
                    e.error("Failed to store event:" + c);
                } else {
                    this.b++;
                    this.d.getWritableDatabase().setTransactionSuccessful();
                    if (cVar != null) {
                        cVar.a();
                    }
                }
                try {
                    this.d.getWritableDatabase().endTransaction();
                } catch (Throwable th) {
                }
            } catch (Throwable th2) {
            }
        }
        return;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00e1 A:{SYNTHETIC, Splitter: B:35:0x00e1} */
    private synchronized void b(com.tencent.stat.b r14) {
        /*
        r13 = this;
        r9 = 1;
        r10 = 0;
        r8 = 0;
        monitor-enter(r13);
        r11 = r14.a();	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r0 = com.tencent.stat.common.k.a(r11);	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r12 = new android.content.ContentValues;	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r12.<init>();	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r1 = "content";
        r2 = r14.b;	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r12.put(r1, r2);	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r1 = "md5sum";
        r12.put(r1, r0);	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r14.c = r0;	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r0 = "version";
        r1 = r14.d;	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r12.put(r0, r1);	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r0 = r13.d;	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r0 = r0.getReadableDatabase();	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        r1 = "config";
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
    L_0x0044:
        r0 = r1.moveToNext();	 Catch:{ Throwable -> 0x00ce }
        if (r0 == 0) goto L_0x00ea;
    L_0x004a:
        r0 = 0;
        r0 = r1.getInt(r0);	 Catch:{ Throwable -> 0x00ce }
        r2 = r14.a;	 Catch:{ Throwable -> 0x00ce }
        if (r0 != r2) goto L_0x0044;
    L_0x0053:
        r0 = r9;
    L_0x0054:
        if (r9 != r0) goto L_0x0099;
    L_0x0056:
        r0 = r13.d;	 Catch:{ Throwable -> 0x00ce }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x00ce }
        r2 = "config";
        r3 = "type=?";
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ Throwable -> 0x00ce }
        r5 = 0;
        r6 = r14.a;	 Catch:{ Throwable -> 0x00ce }
        r6 = java.lang.Integer.toString(r6);	 Catch:{ Throwable -> 0x00ce }
        r4[r5] = r6;	 Catch:{ Throwable -> 0x00ce }
        r0 = r0.update(r2, r12, r3, r4);	 Catch:{ Throwable -> 0x00ce }
        r2 = (long) r0;	 Catch:{ Throwable -> 0x00ce }
    L_0x0073:
        r4 = -1;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 != 0) goto L_0x00b4;
    L_0x0079:
        r0 = e;	 Catch:{ Throwable -> 0x00ce }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00ce }
        r2.<init>();	 Catch:{ Throwable -> 0x00ce }
        r3 = "Failed to store cfg:";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x00ce }
        r2 = r2.append(r11);	 Catch:{ Throwable -> 0x00ce }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x00ce }
        r0.e(r2);	 Catch:{ Throwable -> 0x00ce }
    L_0x0092:
        if (r1 == 0) goto L_0x0097;
    L_0x0094:
        r1.close();	 Catch:{ all -> 0x00da }
    L_0x0097:
        monitor-exit(r13);
        return;
    L_0x0099:
        r0 = "type";
        r2 = r14.a;	 Catch:{ Throwable -> 0x00ce }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ Throwable -> 0x00ce }
        r12.put(r0, r2);	 Catch:{ Throwable -> 0x00ce }
        r0 = r13.d;	 Catch:{ Throwable -> 0x00ce }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x00ce }
        r2 = "config";
        r3 = 0;
        r2 = r0.insert(r2, r3, r12);	 Catch:{ Throwable -> 0x00ce }
        goto L_0x0073;
    L_0x00b4:
        r0 = e;	 Catch:{ Throwable -> 0x00ce }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00ce }
        r2.<init>();	 Catch:{ Throwable -> 0x00ce }
        r3 = "Sucessed to store cfg:";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x00ce }
        r2 = r2.append(r11);	 Catch:{ Throwable -> 0x00ce }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x00ce }
        r0.d(r2);	 Catch:{ Throwable -> 0x00ce }
        goto L_0x0092;
    L_0x00ce:
        r0 = move-exception;
    L_0x00cf:
        r2 = e;	 Catch:{ all -> 0x00e5 }
        r2.e(r0);	 Catch:{ all -> 0x00e5 }
        if (r1 == 0) goto L_0x0097;
    L_0x00d6:
        r1.close();	 Catch:{ all -> 0x00da }
        goto L_0x0097;
    L_0x00da:
        r0 = move-exception;
        monitor-exit(r13);
        throw r0;
    L_0x00dd:
        r0 = move-exception;
        r1 = r8;
    L_0x00df:
        if (r1 == 0) goto L_0x00e4;
    L_0x00e1:
        r1.close();	 Catch:{ all -> 0x00da }
    L_0x00e4:
        throw r0;	 Catch:{ all -> 0x00da }
    L_0x00e5:
        r0 = move-exception;
        goto L_0x00df;
    L_0x00e7:
        r0 = move-exception;
        r1 = r8;
        goto L_0x00cf;
    L_0x00ea:
        r0 = r10;
        goto L_0x0054;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.b(com.tencent.stat.b):void");
    }

    private synchronized void b(List<x> list) {
        try {
            e.i("Delete " + list.size() + " sent events in thread:" + Thread.currentThread());
            try {
                this.d.getWritableDatabase().beginTransaction();
                for (x xVar : list) {
                    this.b -= this.d.getWritableDatabase().delete("events", "event_id = ?", new String[]{Long.toString(xVar.a)});
                }
                this.d.getWritableDatabase().setTransactionSuccessful();
                this.b = (int) DatabaseUtils.queryNumEntries(this.d.getReadableDatabase(), "events");
                this.d.getWritableDatabase().endTransaction();
            } catch (Throwable th) {
                try {
                    this.d.getWritableDatabase().endTransaction();
                } catch (Exception e) {
                    e.e(e);
                }
                throw th;
            }
        } catch (Exception e2) {
            e.e(e2);
        } catch (Throwable th2) {
            throw th2;
        }
    }

    private synchronized void b(List<x> list, int i) {
        try {
            e.i("Update " + list.size() + " sending events to status:" + i + " in thread:" + Thread.currentThread());
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("status", Integer.toString(i));
                this.d.getWritableDatabase().beginTransaction();
                for (x xVar : list) {
                    if (xVar.d + 1 > StatConfig.getMaxSendRetryCount()) {
                        this.b -= this.d.getWritableDatabase().delete("events", "event_id=?", new String[]{Long.toString(xVar.a)});
                    } else {
                        contentValues.put("send_count", Integer.valueOf(xVar.d + 1));
                        e.i("Update event:" + xVar.a + " for content:" + contentValues);
                        int update = this.d.getWritableDatabase().update("events", contentValues, "event_id=?", new String[]{Long.toString(xVar.a)});
                        if (update <= 0) {
                            e.e("Failed to update db, error code:" + Integer.toString(update));
                        }
                    }
                }
                this.d.getWritableDatabase().setTransactionSuccessful();
                this.b = (int) DatabaseUtils.queryNumEntries(this.d.getReadableDatabase(), "events");
                this.d.getWritableDatabase().endTransaction();
            } catch (Throwable th) {
                try {
                    this.d.getWritableDatabase().endTransaction();
                } catch (Exception e) {
                    e.e(e);
                }
                throw th;
            }
        } catch (Exception e2) {
            e.e(e2);
        } catch (Throwable th2) {
            throw th2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0063  */
    private void c(java.util.List<com.tencent.stat.x> r11, int r12) {
        /*
        r10 = this;
        r9 = 0;
        r0 = r10.d;	 Catch:{ Throwable -> 0x006d, all -> 0x0060 }
        r0 = r0.getReadableDatabase();	 Catch:{ Throwable -> 0x006d, all -> 0x0060 }
        r1 = "events";
        r2 = 0;
        r3 = "status=?";
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ Throwable -> 0x006d, all -> 0x0060 }
        r5 = 0;
        r6 = 1;
        r6 = java.lang.Integer.toString(r6);	 Catch:{ Throwable -> 0x006d, all -> 0x0060 }
        r4[r5] = r6;	 Catch:{ Throwable -> 0x006d, all -> 0x0060 }
        r5 = 0;
        r6 = 0;
        r7 = "event_id";
        r8 = java.lang.Integer.toString(r12);	 Catch:{ Throwable -> 0x006d, all -> 0x0060 }
        r7 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Throwable -> 0x006d, all -> 0x0060 }
    L_0x0026:
        r0 = r7.moveToNext();	 Catch:{ Throwable -> 0x004d, all -> 0x0067 }
        if (r0 == 0) goto L_0x005a;
    L_0x002c:
        r0 = 0;
        r2 = r7.getLong(r0);	 Catch:{ Throwable -> 0x004d, all -> 0x0067 }
        r0 = 1;
        r0 = r7.getString(r0);	 Catch:{ Throwable -> 0x004d, all -> 0x0067 }
        r4 = com.tencent.stat.common.k.d(r0);	 Catch:{ Throwable -> 0x004d, all -> 0x0067 }
        r0 = 2;
        r5 = r7.getInt(r0);	 Catch:{ Throwable -> 0x004d, all -> 0x0067 }
        r0 = 3;
        r6 = r7.getInt(r0);	 Catch:{ Throwable -> 0x004d, all -> 0x0067 }
        r1 = new com.tencent.stat.x;	 Catch:{ Throwable -> 0x004d, all -> 0x0067 }
        r1.<init>(r2, r4, r5, r6);	 Catch:{ Throwable -> 0x004d, all -> 0x0067 }
        r11.add(r1);	 Catch:{ Throwable -> 0x004d, all -> 0x0067 }
        goto L_0x0026;
    L_0x004d:
        r0 = move-exception;
        r1 = r7;
    L_0x004f:
        r2 = e;	 Catch:{ all -> 0x006a }
        r2.e(r0);	 Catch:{ all -> 0x006a }
        if (r1 == 0) goto L_0x0059;
    L_0x0056:
        r1.close();
    L_0x0059:
        return;
    L_0x005a:
        if (r7 == 0) goto L_0x0059;
    L_0x005c:
        r7.close();
        goto L_0x0059;
    L_0x0060:
        r0 = move-exception;
    L_0x0061:
        if (r9 == 0) goto L_0x0066;
    L_0x0063:
        r9.close();
    L_0x0066:
        throw r0;
    L_0x0067:
        r0 = move-exception;
        r9 = r7;
        goto L_0x0061;
    L_0x006a:
        r0 = move-exception;
        r9 = r1;
        goto L_0x0061;
    L_0x006d:
        r0 = move-exception;
        r1 = r9;
        goto L_0x004f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.c(java.util.List, int):void");
    }

    private void e() {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("status", Integer.valueOf(1));
            this.d.getWritableDatabase().update("events", contentValues, "status=?", new String[]{Long.toString(2)});
            this.b = (int) DatabaseUtils.queryNumEntries(this.d.getReadableDatabase(), "events");
            e.i("Total " + this.b + " unsent events.");
        } catch (Object th) {
            e.e(th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0040  */
    private void f() {
        /*
        r9 = this;
        r8 = 0;
        r0 = r9.d;	 Catch:{ Throwable -> 0x0046, all -> 0x003c }
        r0 = r0.getReadableDatabase();	 Catch:{ Throwable -> 0x0046, all -> 0x003c }
        r1 = "keyvalues";
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x0046, all -> 0x003c }
    L_0x0014:
        r0 = r1.moveToNext();	 Catch:{ Throwable -> 0x002a }
        if (r0 == 0) goto L_0x0036;
    L_0x001a:
        r0 = r9.g;	 Catch:{ Throwable -> 0x002a }
        r2 = 0;
        r2 = r1.getString(r2);	 Catch:{ Throwable -> 0x002a }
        r3 = 1;
        r3 = r1.getString(r3);	 Catch:{ Throwable -> 0x002a }
        r0.put(r2, r3);	 Catch:{ Throwable -> 0x002a }
        goto L_0x0014;
    L_0x002a:
        r0 = move-exception;
    L_0x002b:
        r2 = e;	 Catch:{ all -> 0x0044 }
        r2.e(r0);	 Catch:{ all -> 0x0044 }
        if (r1 == 0) goto L_0x0035;
    L_0x0032:
        r1.close();
    L_0x0035:
        return;
    L_0x0036:
        if (r1 == 0) goto L_0x0035;
    L_0x0038:
        r1.close();
        goto L_0x0035;
    L_0x003c:
        r0 = move-exception;
        r1 = r8;
    L_0x003e:
        if (r1 == 0) goto L_0x0043;
    L_0x0040:
        r1.close();
    L_0x0043:
        throw r0;
    L_0x0044:
        r0 = move-exception;
        goto L_0x003e;
    L_0x0046:
        r0 = move-exception;
        r1 = r8;
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.f():void");
    }

    public int a() {
        return this.b;
    }

    void a(int i) {
        this.a.post(new v(this, i));
    }

    void a(e eVar, c cVar) {
        if (StatConfig.isEnableStatService()) {
            try {
                if (Thread.currentThread().getId() == this.a.getLooper().getThread().getId()) {
                    b(eVar, cVar);
                } else {
                    this.a.post(new r(this, eVar, cVar));
                }
            } catch (Object th) {
                e.e(th);
            }
        }
    }

    void a(b bVar) {
        if (bVar != null) {
            this.a.post(new s(this, bVar));
        }
    }

    void a(List<x> list) {
        try {
            if (Thread.currentThread().getId() == this.a.getLooper().getThread().getId()) {
                b((List) list);
            } else {
                this.a.post(new q(this, list));
            }
        } catch (Exception e) {
            e.e(e);
        }
    }

    void a(List<x> list, int i) {
        try {
            if (Thread.currentThread().getId() == this.a.getLooper().getThread().getId()) {
                b((List) list, i);
            } else {
                this.a.post(new p(this, list, i));
            }
        } catch (Object th) {
            e.e(th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:77:0x01ec A:{SYNTHETIC, Splitter: B:77:0x01ec} */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01e1 A:{SYNTHETIC, Splitter: B:69:0x01e1} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01ec A:{SYNTHETIC, Splitter: B:77:0x01ec} */
    public synchronized com.tencent.stat.DeviceInfo b(android.content.Context r20) {
        /*
        r19 = this;
        monitor-enter(r19);
        r0 = r19;
        r2 = r0.c;	 Catch:{ all -> 0x01e5 }
        if (r2 == 0) goto L_0x000d;
    L_0x0007:
        r0 = r19;
        r2 = r0.c;	 Catch:{ all -> 0x01e5 }
    L_0x000b:
        monitor-exit(r19);
        return r2;
    L_0x000d:
        r11 = 0;
        r0 = r19;
        r2 = r0.d;	 Catch:{ Throwable -> 0x01d8, all -> 0x01e8 }
        r2 = r2.getReadableDatabase();	 Catch:{ Throwable -> 0x01d8, all -> 0x01e8 }
        r3 = "user";
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r5 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ Throwable -> 0x01d8, all -> 0x01e8 }
        r2 = 0;
        r3 = "";
        r3 = r5.moveToNext();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r3 == 0) goto L_0x0122;
    L_0x002e:
        r2 = 0;
        r10 = r5.getString(r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r7 = com.tencent.stat.common.k.d(r10);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = 1;
        r9 = r5.getInt(r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = 2;
        r3 = r5.getString(r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = 3;
        r12 = r5.getLong(r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r6 = 1;
        r14 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r16 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r14 = r14 / r16;
        r2 = 1;
        if (r9 == r2) goto L_0x020d;
    L_0x0052:
        r16 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r12 = r12 * r16;
        r2 = com.tencent.stat.common.k.a(r12);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r12 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r12 = r12 * r14;
        r4 = com.tencent.stat.common.k.a(r12);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = r2.equals(r4);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r2 != 0) goto L_0x020d;
    L_0x0067:
        r2 = 1;
    L_0x0068:
        r4 = com.tencent.stat.common.k.r(r20);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r3 = r3.equals(r4);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r3 != 0) goto L_0x020a;
    L_0x0072:
        r2 = r2 | 2;
        r8 = r2;
    L_0x0075:
        r2 = ",";
        r11 = r7.split(r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = 0;
        if (r11 == 0) goto L_0x01a4;
    L_0x007f:
        r2 = r11.length;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r2 <= 0) goto L_0x01a4;
    L_0x0082:
        r2 = 0;
        r3 = r11[r2];	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r3 == 0) goto L_0x008f;
    L_0x0087:
        r2 = r3.length();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r12 = 11;
        if (r2 >= r12) goto L_0x0202;
    L_0x008f:
        r2 = com.tencent.stat.common.k.l(r20);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r2 == 0) goto L_0x01fe;
    L_0x0095:
        r12 = r2.length();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r13 = 10;
        if (r12 <= r13) goto L_0x01fe;
    L_0x009d:
        r3 = 1;
    L_0x009e:
        r4 = r7;
        r7 = r2;
    L_0x00a0:
        if (r11 == 0) goto L_0x01b1;
    L_0x00a2:
        r2 = r11.length;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r12 = 2;
        if (r2 < r12) goto L_0x01b1;
    L_0x00a6:
        r2 = 1;
        r2 = r11[r2];	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4.<init>();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = r4.append(r7);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r11 = ",";
        r4 = r4.append(r11);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = r4.append(r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
    L_0x00c1:
        r11 = new com.tencent.stat.DeviceInfo;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r11.<init>(r7, r2, r8);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r0 = r19;
        r0.c = r11;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = new android.content.ContentValues;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2.<init>();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = com.tencent.stat.common.k.c(r4);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r7 = "uid";
        r2.put(r7, r4);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = "user_type";
        r7 = java.lang.Integer.valueOf(r8);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2.put(r4, r7);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = "app_ver";
        r7 = com.tencent.stat.common.k.r(r20);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2.put(r4, r7);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = "ts";
        r7 = java.lang.Long.valueOf(r14);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2.put(r4, r7);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r3 == 0) goto L_0x0110;
    L_0x00f9:
        r0 = r19;
        r3 = r0.d;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r3 = r3.getWritableDatabase();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = "user";
        r7 = "uid=?";
        r11 = 1;
        r11 = new java.lang.String[r11];	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r12 = 0;
        r11[r12] = r10;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r3.update(r4, r2, r7, r11);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
    L_0x0110:
        if (r8 == r9) goto L_0x01fb;
    L_0x0112:
        r0 = r19;
        r3 = r0.d;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r3 = r3.getWritableDatabase();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = "user";
        r7 = 0;
        r3.replace(r4, r7, r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = r6;
    L_0x0122:
        if (r2 != 0) goto L_0x0199;
    L_0x0124:
        r3 = com.tencent.stat.common.k.b(r20);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = com.tencent.stat.common.k.c(r20);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r4 == 0) goto L_0x01f8;
    L_0x012e:
        r2 = r4.length();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r2 <= 0) goto L_0x01f8;
    L_0x0134:
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2.<init>();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r6 = ",";
        r2 = r2.append(r6);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
    L_0x014c:
        r6 = 0;
        r8 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r10 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r8 = r8 / r10;
        r7 = com.tencent.stat.common.k.r(r20);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r10 = new android.content.ContentValues;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r10.<init>();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = com.tencent.stat.common.k.c(r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r11 = "uid";
        r10.put(r11, r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = "user_type";
        r11 = java.lang.Integer.valueOf(r6);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r10.put(r2, r11);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = "app_ver";
        r10.put(r2, r7);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = "ts";
        r7 = java.lang.Long.valueOf(r8);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r10.put(r2, r7);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r0 = r19;
        r2 = r0.d;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = r2.getWritableDatabase();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r7 = "user";
        r8 = 0;
        r2.insert(r7, r8, r10);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2 = new com.tencent.stat.DeviceInfo;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r2.<init>(r3, r4, r6);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r0 = r19;
        r0.c = r2;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
    L_0x0199:
        if (r5 == 0) goto L_0x019e;
    L_0x019b:
        r5.close();	 Catch:{ all -> 0x01e5 }
    L_0x019e:
        r0 = r19;
        r2 = r0.c;	 Catch:{ all -> 0x01e5 }
        goto L_0x000b;
    L_0x01a4:
        r3 = com.tencent.stat.common.k.b(r20);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = 1;
        r7 = r3;
        r18 = r4;
        r4 = r3;
        r3 = r18;
        goto L_0x00a0;
    L_0x01b1:
        r2 = com.tencent.stat.common.k.c(r20);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r2 == 0) goto L_0x00c1;
    L_0x01b7:
        r11 = r2.length();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        if (r11 <= 0) goto L_0x00c1;
    L_0x01bd:
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r3.<init>();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r3 = r3.append(r7);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = ",";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r3 = r3.append(r2);	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r4 = r3.toString();	 Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        r3 = 1;
        goto L_0x00c1;
    L_0x01d8:
        r2 = move-exception;
        r3 = r11;
    L_0x01da:
        r4 = e;	 Catch:{ all -> 0x01f2 }
        r4.e(r2);	 Catch:{ all -> 0x01f2 }
        if (r3 == 0) goto L_0x019e;
    L_0x01e1:
        r3.close();	 Catch:{ all -> 0x01e5 }
        goto L_0x019e;
    L_0x01e5:
        r2 = move-exception;
        monitor-exit(r19);
        throw r2;
    L_0x01e8:
        r2 = move-exception;
        r5 = r11;
    L_0x01ea:
        if (r5 == 0) goto L_0x01ef;
    L_0x01ec:
        r5.close();	 Catch:{ all -> 0x01e5 }
    L_0x01ef:
        throw r2;	 Catch:{ all -> 0x01e5 }
    L_0x01f0:
        r2 = move-exception;
        goto L_0x01ea;
    L_0x01f2:
        r2 = move-exception;
        r5 = r3;
        goto L_0x01ea;
    L_0x01f5:
        r2 = move-exception;
        r3 = r5;
        goto L_0x01da;
    L_0x01f8:
        r2 = r3;
        goto L_0x014c;
    L_0x01fb:
        r2 = r6;
        goto L_0x0122;
    L_0x01fe:
        r2 = r3;
        r3 = r4;
        goto L_0x009e;
    L_0x0202:
        r18 = r3;
        r3 = r4;
        r4 = r7;
        r7 = r18;
        goto L_0x00a0;
    L_0x020a:
        r8 = r2;
        goto L_0x0075;
    L_0x020d:
        r2 = r9;
        goto L_0x0068;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.b(android.content.Context):com.tencent.stat.DeviceInfo");
    }

    void c() {
        this.a.post(new t(this));
    }
}
