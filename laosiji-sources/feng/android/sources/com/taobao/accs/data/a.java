package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.antibrush.AntiBrush;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message.ReqType;
import com.taobao.accs.flowcontrol.FlowControl;
import com.taobao.accs.net.b;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.ut.monitor.TrafficsMonitor;
import com.taobao.accs.ut.statistics.d;
import com.taobao.accs.ut.statistics.e;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.h;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.zip.GZIPInputStream;
import org.json.JSONObject;

/* compiled from: Taobao */
public class a {
    public ConcurrentMap<String, ScheduledFuture<?>> a = new ConcurrentHashMap();
    public int b;
    protected TrafficsMonitor c;
    public FlowControl d;
    public AntiBrush e;
    public String f = "";
    private ConcurrentMap<String, Message> g = new ConcurrentHashMap();
    private boolean h = false;
    private Context i;
    private d j;
    private Message k;
    private b l;
    private String m = "MsgRecv_";
    private LinkedHashMap<String, String> n = new MessageHandler$1(this);
    private Runnable o = new c(this);

    public a(Context context, b bVar) {
        this.i = context;
        this.l = bVar;
        this.c = new TrafficsMonitor(this.i);
        this.d = new FlowControl(this.i);
        this.e = new AntiBrush(this.i);
        this.m = bVar == null ? this.m : this.m + bVar.m;
        h();
        g();
    }

    public void a(byte[] bArr) throws IOException {
        a(bArr, null);
    }

    public void a(byte[] bArr, String str) throws IOException {
        int i = 0;
        h hVar = new h(bArr);
        try {
            int a = hVar.a();
            int i2 = (a & 240) >> 4;
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.m, "version:" + i2, new Object[0]);
            }
            a &= 15;
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.m, "compress:" + a, new Object[0]);
            }
            hVar.a();
            int b = hVar.b();
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.m, "totalLen:" + b, new Object[0]);
            }
            while (i < b) {
                int b2 = hVar.b();
                i += 2;
                if (b2 > 0) {
                    byte[] bArr2 = new byte[b2];
                    hVar.read(bArr2);
                    if (ALog.isPrintLog(Level.D)) {
                        ALog.d(this.m, "buf len:" + bArr2.length, new Object[0]);
                    }
                    i += bArr2.length;
                    a(a, bArr2, str, i2);
                } else {
                    throw new IOException("data format error");
                }
            }
        } catch (Throwable th) {
            com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", PushConstants.PUSH_TYPE_THROUGH_MESSAGE, this.b + th.toString());
            ALog.e(this.m, "", th, new Object[0]);
        } finally {
            hVar.close();
        }
    }

    private void a(int i, byte[] bArr, String str, int i2) throws IOException {
        h hVar = new h(bArr);
        long b = (long) hVar.b();
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(this.m, "flag:" + Integer.toHexString((int) b), new Object[0]);
        }
        String a = hVar.a(hVar.a());
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(this.m, "target:" + a, new Object[0]);
        }
        String a2 = hVar.a(hVar.a());
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(this.m, "source:" + a2, new Object[0]);
        }
        try {
            int read;
            boolean z;
            Message message;
            String a3 = hVar.a(hVar.a());
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.m, "dataId:" + a3, new Object[0]);
            }
            String str2 = a2 + a3;
            byte[] bArr2 = null;
            Map map = null;
            if (hVar.available() > 0) {
                if (i2 == 2) {
                    map = a(hVar);
                }
                if (i == 0) {
                    bArr2 = hVar.c();
                } else if (i == 1) {
                    GZIPInputStream gZIPInputStream = new GZIPInputStream(hVar);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        byte[] bArr3 = new byte[8192];
                        while (true) {
                            read = gZIPInputStream.read(bArr3);
                            if (read <= 0) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr3, 0, read);
                        }
                        bArr2 = byteArrayOutputStream.toByteArray();
                        if (gZIPInputStream != null) {
                            try {
                                gZIPInputStream.close();
                            } catch (Exception e) {
                            }
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    } catch (Exception e2) {
                        ALog.e(this.m, "uncompress data error " + e2.toString(), new Object[0]);
                        com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", PushConstants.PUSH_TYPE_THROUGH_MESSAGE, this.b + " uncompress data error " + e2.toString());
                        if (gZIPInputStream != null) {
                            try {
                                gZIPInputStream.close();
                            } catch (Exception e3) {
                            }
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    } catch (Throwable th) {
                        if (gZIPInputStream != null) {
                            try {
                                gZIPInputStream.close();
                            } catch (Exception e4) {
                            }
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    }
                }
            }
            hVar.close();
            if (bArr2 == null) {
                try {
                    ALog.d(this.m, "oriData is null", new Object[0]);
                } catch (Exception e22) {
                    ALog.e(this.m, e22.toString(), new Object[0]);
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", PushConstants.PUSH_TYPE_THROUGH_MESSAGE, this.b + e22.toString());
                    e22.printStackTrace();
                    return;
                }
            } else if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.m, "oriData:" + String.valueOf(bArr2), new Object[0]);
            }
            int a4 = Message.b.a((int) ((b >> 15) & 1));
            ReqType valueOf = ReqType.valueOf((int) ((b >> 13) & 3));
            read = (int) ((b >> 12) & 1);
            int a5 = com.taobao.accs.data.Message.a.a((int) ((b >> 11) & 1));
            if (((int) ((b >> 6) & 1)) == 1) {
                z = true;
            } else {
                z = false;
            }
            ALog.i(this.m, this.b + " dataId", a3, "type", Message.b.b(a4), "reqType", valueOf.name(), "resType", com.taobao.accs.data.Message.a.b(a5), Constants.KEY_TARGET, a);
            if (a4 == 1 && (valueOf == ReqType.ACK || valueOf == ReqType.RES)) {
                Message message2 = (Message) this.g.remove(a3);
                if (message2 != null) {
                    ALog.d(this.m, "reqMessage not null", new Object[0]);
                    int i3 = 200;
                    if (read == 1) {
                        try {
                            i3 = new JSONObject(new String(bArr2)).getInt("code");
                        } catch (Exception e5) {
                            i3 = -3;
                        }
                    }
                    if (message2.e() != null) {
                        message2.e().onRecAck();
                    }
                    if (valueOf == ReqType.RES) {
                        a(message2, i3, valueOf, bArr2, map);
                    } else {
                        a(message2, i3, map);
                    }
                    a(new com.taobao.accs.ut.monitor.TrafficsMonitor.a(message2.F, GlobalAppRuntimeInfo.isAppBackground(), str, (long) bArr.length));
                } else {
                    ALog.e(this.m, this.b + " data ack/res reqMessage is null," + a3, new Object[0]);
                }
            }
            if (a4 == 0 && valueOf == ReqType.RES) {
                message = (Message) this.g.remove(a3);
                if (message != null) {
                    a(message, bArr2, bArr, str);
                    return;
                }
                ALog.e(this.m, this.b + " contorl ACK reqMessage is null" + a3, new Object[0]);
                if (ALog.isPrintLog(Level.D)) {
                    ALog.d(this.m, "Message not handled, body:" + new String(bArr2), new Object[0]);
                }
            }
            if (a4 == 1 && valueOf == ReqType.DATA && a != null) {
                String[] split = a.split("\\|");
                if (split != null && split.length >= 2) {
                    ALog.d(this.m, "onPush", new Object[0]);
                    if (this.j != null) {
                        this.j.commitUT();
                    }
                    this.j = new d();
                    this.j.c = String.valueOf(System.currentTimeMillis());
                    if (UtilityImpl.packageExist(this.i, split[1])) {
                        String str3 = split.length >= 3 ? split[2] : null;
                        this.j.e = str3;
                        if (c(str2)) {
                            ALog.e(this.m, this.b + " msg duplicate" + a3, new Object[0]);
                            this.j.h = true;
                        } else {
                            d(str2);
                            if ("accs".equals(str3)) {
                                ALog.e(this.m, this.b + " try deliver msg to " + split[1] + "/" + str3, Constants.KEY_DATA_ID, a3);
                            } else {
                                ALog.i(this.m, this.b + " try deliver msg to " + split[1] + "/" + str3, new Object[0]);
                            }
                            Intent intent = new Intent(Constants.ACTION_RECEIVE);
                            intent.setPackage(split[1]);
                            intent.putExtra("command", 101);
                            if (split.length >= 3) {
                                intent.putExtra(Constants.KEY_SERVICE_ID, split[2]);
                            }
                            String str4 = "";
                            if (split.length >= 4) {
                                str4 = split[3];
                                intent.putExtra(Constants.KEY_USER_ID, str4);
                            }
                            intent.putExtra("data", bArr2);
                            intent.putExtra(Constants.KEY_DATA_ID, a3);
                            intent.putExtra(Constants.KEY_PACKAGE_NAME, this.i.getPackageName());
                            intent.putExtra("host", str);
                            intent.putExtra(Constants.KEY_CONN_TYPE, this.b);
                            intent.putExtra(Constants.KEY_NEED_BUSINESS_ACK, z);
                            intent.putExtra(Constants.KEY_APP_KEY, this.l.i());
                            intent.putExtra(Constants.KEY_CONFIG_TAG, this.l.m);
                            a(map, intent);
                            if (z) {
                                a(intent, a2, a, (short) ((int) b));
                            }
                            d.a(this.i, intent);
                            UTMini.getInstance().commitEvent(66001, "MsgToBussPush", "commandId=101", "serviceId=" + str3 + " dataId=" + a3, Integer.valueOf(Constants.SDK_VERSION_CODE));
                            com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_POINT_TO_BUSS, "1commandId=101serviceId=" + str3, 0.0d);
                            this.j.b = a3;
                            this.j.i = str4;
                            this.j.f = (bArr2 == null ? 0 : bArr2.length) + "";
                            this.j.a = UtilityImpl.getDeviceId(this.i);
                            this.j.d = String.valueOf(System.currentTimeMillis());
                            a(new com.taobao.accs.ut.monitor.TrafficsMonitor.a(str3, GlobalAppRuntimeInfo.isAppBackground(), str, (long) bArr.length));
                        }
                        if (a5 == 1) {
                            if ("accs".equals(str3)) {
                                ALog.e(this.m, this.b + " try to send ack dataId " + a3, new Object[0]);
                            } else {
                                ALog.i(this.m, this.b + " try to send ack dataId " + a3, new Object[0]);
                            }
                            message = Message.a(this.l, a, a2, a3, false, (short) ((int) b), str, map);
                            this.l.b(message, true);
                            a(message.q, str3);
                            if (z) {
                                com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_ACK, "", 0.0d);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    ALog.e(this.m, "package " + split[1] + " not exist, unbind it", new Object[0]);
                    this.l.b(Message.a(this.l, split[1]), true);
                }
            }
        } catch (Exception e222) {
            ALog.e(this.m, "dataId read error " + e222.toString(), new Object[0]);
            hVar.close();
            com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", PushConstants.PUSH_TYPE_THROUGH_MESSAGE, this.b + "data id read error" + e222.toString());
        }
    }

    private void a(com.taobao.accs.data.Message r12, byte[] r13, byte[] r14, java.lang.String r15) {
        /*
        r11 = this;
        r3 = 0;
        r1 = 0;
        r2 = -8;
        r0 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x00c8 }
        r4 = new java.lang.String;	 Catch:{ Throwable -> 0x00c8 }
        r4.<init>(r13);	 Catch:{ Throwable -> 0x00c8 }
        r0.<init>(r4);	 Catch:{ Throwable -> 0x00c8 }
        r4 = com.taobao.accs.utl.ALog.Level.D;	 Catch:{ Throwable -> 0x00c8 }
        r4 = com.taobao.accs.utl.ALog.isPrintLog(r4);	 Catch:{ Throwable -> 0x00c8 }
        if (r4 == 0) goto L_0x0035;
    L_0x0015:
        r4 = r11.m;	 Catch:{ Throwable -> 0x00c8 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00c8 }
        r5.<init>();	 Catch:{ Throwable -> 0x00c8 }
        r6 = "parse Json:";
        r5 = r5.append(r6);	 Catch:{ Throwable -> 0x00c8 }
        r6 = r0.toString();	 Catch:{ Throwable -> 0x00c8 }
        r5 = r5.append(r6);	 Catch:{ Throwable -> 0x00c8 }
        r5 = r5.toString();	 Catch:{ Throwable -> 0x00c8 }
        r6 = 0;
        r6 = new java.lang.Object[r6];	 Catch:{ Throwable -> 0x00c8 }
        com.taobao.accs.utl.ALog.d(r4, r5, r6);	 Catch:{ Throwable -> 0x00c8 }
    L_0x0035:
        r4 = "code";
        r2 = r0.getInt(r4);	 Catch:{ Throwable -> 0x00c8 }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r2 != r4) goto L_0x0124;
    L_0x0040:
        r4 = r12.t;	 Catch:{ Throwable -> 0x00c8 }
        r4 = r4.intValue();	 Catch:{ Throwable -> 0x00c8 }
        switch(r4) {
            case 1: goto L_0x0062;
            case 2: goto L_0x00fb;
            case 3: goto L_0x0108;
            case 4: goto L_0x0117;
            default: goto L_0x0049;
        };
    L_0x0049:
        r0 = r11;
        r1 = r12;
        r4 = r13;
        r5 = r3;
        r0.a(r1, r2, r3, r4, r5);
        r0 = new com.taobao.accs.ut.monitor.TrafficsMonitor$a;
        r1 = r12.F;
        r2 = anet.channel.GlobalAppRuntimeInfo.isAppBackground();
        r3 = r14.length;
        r4 = (long) r3;
        r3 = r15;
        r0.<init>(r1, r2, r3, r4);
        r11.a(r0);
        return;
    L_0x0062:
        r4 = "data";
        r0 = r0.getJSONObject(r4);	 Catch:{ Throwable -> 0x00c8 }
        r4 = "accsToken";
        r5 = 0;
        r4 = com.taobao.accs.utl.e.a(r0, r4, r5);	 Catch:{ Throwable -> 0x00c8 }
        r11.f = r4;	 Catch:{ Throwable -> 0x00c8 }
        r4 = "ACCS_SDK";
        r5 = r11.i;	 Catch:{ Throwable -> 0x00c8 }
        com.taobao.accs.utl.UtilityImpl.saveUtdid(r4, r5);	 Catch:{ Throwable -> 0x00c8 }
        if (r0 == 0) goto L_0x0049;
    L_0x007d:
        r4 = "packageNames";
        r4 = r0.getJSONArray(r4);	 Catch:{ Throwable -> 0x00c8 }
        if (r4 == 0) goto L_0x0049;
    L_0x0086:
        r0 = r1;
    L_0x0087:
        r5 = r4.length();	 Catch:{ Throwable -> 0x00c8 }
        if (r0 >= r5) goto L_0x0049;
    L_0x008d:
        r5 = r4.getString(r0);	 Catch:{ Throwable -> 0x00c8 }
        r6 = r11.i;	 Catch:{ Throwable -> 0x00c8 }
        r6 = com.taobao.accs.utl.UtilityImpl.packageExist(r6, r5);	 Catch:{ Throwable -> 0x00c8 }
        if (r6 == 0) goto L_0x00a7;
    L_0x0099:
        r5 = r11.l;	 Catch:{ Throwable -> 0x00c8 }
        r5 = r5.j();	 Catch:{ Throwable -> 0x00c8 }
        r6 = r12.s;	 Catch:{ Throwable -> 0x00c8 }
        r5.a(r6);	 Catch:{ Throwable -> 0x00c8 }
    L_0x00a4:
        r0 = r0 + 1;
        goto L_0x0087;
    L_0x00a7:
        r6 = r11.m;	 Catch:{ Throwable -> 0x00c8 }
        r7 = "unbind app";
        r8 = 2;
        r8 = new java.lang.Object[r8];	 Catch:{ Throwable -> 0x00c8 }
        r9 = 0;
        r10 = "pkg";
        r8[r9] = r10;	 Catch:{ Throwable -> 0x00c8 }
        r9 = 1;
        r8[r9] = r5;	 Catch:{ Throwable -> 0x00c8 }
        com.taobao.accs.utl.ALog.e(r6, r7, r8);	 Catch:{ Throwable -> 0x00c8 }
        r6 = r11.l;	 Catch:{ Throwable -> 0x00c8 }
        r7 = r11.l;	 Catch:{ Throwable -> 0x00c8 }
        r5 = com.taobao.accs.data.Message.a(r7, r5);	 Catch:{ Throwable -> 0x00c8 }
        r7 = 1;
        r6.b(r5, r7);	 Catch:{ Throwable -> 0x00c8 }
        goto L_0x00a4;
    L_0x00c8:
        r0 = move-exception;
        r4 = r11.m;
        r5 = "handleControlMessage";
        r1 = new java.lang.Object[r1];
        com.taobao.accs.utl.ALog.e(r4, r5, r0, r1);
        r1 = "accs";
        r4 = "send_fail";
        r5 = "handleControlMessage";
        r6 = "";
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = r11.b;
        r7 = r7.append(r8);
        r0 = r0.toString();
        r0 = r7.append(r0);
        r0 = r0.toString();
        com.taobao.accs.utl.b.a(r1, r4, r5, r6, r0);
        goto L_0x0049;
    L_0x00fb:
        r0 = r11.l;	 Catch:{ Throwable -> 0x00c8 }
        r0 = r0.j();	 Catch:{ Throwable -> 0x00c8 }
        r4 = r12.s;	 Catch:{ Throwable -> 0x00c8 }
        r0.b(r4);	 Catch:{ Throwable -> 0x00c8 }
        goto L_0x0049;
    L_0x0108:
        r0 = r11.l;	 Catch:{ Throwable -> 0x00c8 }
        r0 = r0.j();	 Catch:{ Throwable -> 0x00c8 }
        r4 = r12.s;	 Catch:{ Throwable -> 0x00c8 }
        r5 = r12.E;	 Catch:{ Throwable -> 0x00c8 }
        r0.a(r4, r5);	 Catch:{ Throwable -> 0x00c8 }
        goto L_0x0049;
    L_0x0117:
        r0 = r11.l;	 Catch:{ Throwable -> 0x00c8 }
        r0 = r0.j();	 Catch:{ Throwable -> 0x00c8 }
        r4 = r12.s;	 Catch:{ Throwable -> 0x00c8 }
        r0.e(r4);	 Catch:{ Throwable -> 0x00c8 }
        goto L_0x0049;
    L_0x0124:
        r0 = r12.t;	 Catch:{ Throwable -> 0x00c8 }
        r0 = r0.intValue();	 Catch:{ Throwable -> 0x00c8 }
        r4 = 3;
        if (r0 != r4) goto L_0x0049;
    L_0x012d:
        r0 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r2 != r0) goto L_0x0049;
    L_0x0131:
        r0 = r11.l;	 Catch:{ Throwable -> 0x00c8 }
        r0 = r0.j();	 Catch:{ Throwable -> 0x00c8 }
        r4 = r12.s;	 Catch:{ Throwable -> 0x00c8 }
        r0.b(r4);	 Catch:{ Throwable -> 0x00c8 }
        goto L_0x0049;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.a.a(com.taobao.accs.data.Message, byte[], byte[], java.lang.String):void");
    }

    private Map<Integer, String> a(h hVar) {
        Throwable e;
        Map<Integer, String> map = null;
        if (hVar != null) {
            try {
                int b = hVar.b();
                if (ALog.isPrintLog(Level.D)) {
                    ALog.d(this.m, "extHeaderLen:" + b, new Object[0]);
                }
                int i = 0;
                while (i < b) {
                    Map<Integer, String> hashMap;
                    int b2 = hVar.b();
                    i += 2;
                    int i2 = (64512 & b2) >> 10;
                    b2 &= Message.EXT_HEADER_VALUE_MAX_LEN;
                    String a = hVar.a(b2);
                    b2 += i;
                    if (map == null) {
                        hashMap = new HashMap();
                    } else {
                        hashMap = map;
                    }
                    try {
                        hashMap.put(Integer.valueOf(i2), a);
                        if (ALog.isPrintLog(Level.D)) {
                            ALog.d(this.m, "", "extHeaderType", Integer.valueOf(i2), "value", a);
                        }
                        map = hashMap;
                        i = b2;
                    } catch (Throwable e2) {
                        Throwable th = e2;
                        map = hashMap;
                        e = th;
                        ALog.e(this.m, "parseExtHeader", e, new Object[0]);
                        return map;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                ALog.e(this.m, "parseExtHeader", e, new Object[0]);
                return map;
            }
        }
        return map;
    }

    public void a(Message message, int i) {
        a(message, i, null, null, null);
    }

    public void a(Message message, int i, Map<Integer, String> map) {
        a(message, i, null, null, map);
    }

    public void a(Message message, int i, ReqType reqType, byte[] bArr, Map<Integer, String> map) {
        if (message.t == null || message.a() < 0 || message.a() == 2) {
            ALog.d(this.m, "onError, skip ping/ack", new Object[0]);
            return;
        }
        Map map2;
        byte[] bArr2;
        int i2;
        if (message.M != null) {
            this.a.remove(message.M);
        }
        if (this.e.checkAntiBrush(message.f, map2)) {
            i = ErrorCode.SERVIER_ANTI_BRUSH;
            bArr = null;
            map2 = null;
            reqType = null;
        }
        int a = this.d.a(map2, message.F);
        if (a != 0) {
            if (a == 2) {
                a = ErrorCode.SERVIER_HIGH_LIMIT;
            } else if (a == 3) {
                a = ErrorCode.SERVIER_HIGH_LIMIT_BRUSH;
            } else {
                a = ErrorCode.SERVIER_LOW_LIMIT;
            }
            bArr2 = null;
            map2 = null;
            reqType = null;
            i2 = a;
        } else {
            bArr2 = bArr;
            i2 = i;
        }
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(this.m, "onResult command:" + message.t + " erorcode:" + i2, new Object[0]);
        }
        if (message.t.intValue() == 102) {
            return;
        }
        if (message.t.intValue() == 105) {
            AccsAbstractDataListener listener = GlobalClientInfo.getInstance(this.i).getListener(com.taobao.accs.internal.b.ELECTION_SERVICE_ID);
            if (listener != null) {
                listener.onResponse(com.taobao.accs.internal.b.ELECTION_SERVICE_ID, message.M, i2, bArr2, null);
                return;
            } else {
                ALog.e(this.m, "onResult election listener null", new Object[0]);
                return;
            }
        }
        if (message.e) {
            ALog.e(this.m, this.b + " message is cancel! command:" + message.t, new Object[0]);
        } else if (!b(i2) || message.t.intValue() == 100 || message.P > Message.a) {
            ALog.d(this.m, "prepare send broadcast", new Object[0]);
            Intent c = c(message);
            c.putExtra(Constants.KEY_ERROR_CODE, i2);
            ReqType valueOf = ReqType.valueOf((message.k >> 13) & 3);
            if (reqType == ReqType.RES || valueOf == ReqType.REQ) {
                c.putExtra(Constants.KEY_SEND_TYPE, "res");
            }
            if (i2 == 200) {
                c.putExtra("data", bArr2);
            }
            c.putExtra(Constants.KEY_APP_KEY, this.l.b);
            c.putExtra(Constants.KEY_CONFIG_TAG, this.l.m);
            a(map2, c);
            d.a(this.i, c);
            if (!TextUtils.isEmpty(message.F)) {
                UTMini.getInstance().commitEvent(66001, "MsgToBuss0", "commandId=" + message.t, "serviceId=" + message.F + " errorCode=" + i2 + " dataId=" + message.q, Integer.valueOf(Constants.SDK_VERSION_CODE));
                com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_POINT_TO_BUSS, "1commandId=" + message.t + "serviceId=" + message.F, 0.0d);
            }
        } else {
            message.R = System.currentTimeMillis();
            message.P++;
            this.l.b(message, true);
        }
        NetPerformanceMonitor e = message.e();
        if (e != null) {
            e.onToBizDate();
            String url = message.f == null ? null : message.f.toString();
            if (i2 == 200) {
                e.setRet(true);
                if (message.P > 0) {
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_POINT_RESEND, "succ", 0.0d);
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_POINT_RESEND, "succ_" + message.P, 0.0d);
                } else {
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQUEST, url);
                }
            } else {
                if (message.P > 0) {
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_POINT_RESEND, "fail＿" + i2, 0.0d);
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_POINT_RESEND, "fail", 0.0d);
                } else if (i2 != -13) {
                    com.taobao.accs.utl.b.a("accs", BaseMonitor.ALARM_POINT_REQUEST, url, UtilityImpl.int2String(i2), this.b + message.F + message.Q);
                }
                e.setRet(false);
                e.setFailReason(i2);
            }
            AppMonitor.getInstance().commitStat(message.e());
        }
        b(message, i2);
    }

    private boolean b(int i) {
        if (i == -1 || i == -9 || i == -10 || i == -11) {
            return true;
        }
        return false;
    }

    public void a() {
        ALog.d(this.m, "onSendPing", new Object[0]);
        synchronized (a.class) {
            this.h = true;
        }
    }

    public void b() {
        ALog.d(this.m, "onRcvPing", new Object[0]);
        synchronized (a.class) {
            this.h = false;
        }
    }

    public boolean c() {
        return this.h;
    }

    public void a(Message message) {
        if (!(this.k == null || message.M == null || message.F == null || this.k.M != message.M || this.k.F != message.F)) {
            UTMini.getInstance().commitEvent(66001, "SEND_REPEAT", message.F, message.M, Long.valueOf(Thread.currentThread().getId()));
        }
        if (message.a() != -1 && message.a() != 2 && !message.c) {
            this.g.put(message.b(), message);
        }
    }

    public void a(int i) {
        this.h = false;
        String[] strArr = (String[]) this.g.keySet().toArray(new String[0]);
        if (strArr != null && strArr.length > 0) {
            ALog.d(this.m, "onNetworkFail", new Object[0]);
            for (Object remove : strArr) {
                Message message = (Message) this.g.remove(remove);
                if (message != null) {
                    a(message, i);
                }
            }
        }
    }

    public void b(Message message) {
        if (this.g.keySet() != null && this.g.keySet().size() > 0) {
            for (String str : this.g.keySet()) {
                Message message2 = (Message) this.g.get(str);
                if (!(message2 == null || message2.t == null || !message2.f().equals(message.f()))) {
                    switch (message.t.intValue()) {
                        case 1:
                        case 2:
                            if (message2.t.intValue() == 1 || message2.t.intValue() == 2) {
                                message2.e = true;
                                break;
                            }
                        case 3:
                        case 4:
                            if (message2.t.intValue() == 3 || message2.t.intValue() == 4) {
                                message2.e = true;
                                break;
                            }
                        case 5:
                        case 6:
                            if (message2.t.intValue() == 5 || message2.t.intValue() == 6) {
                                message2.e = true;
                                break;
                            }
                    }
                }
                if (message2 != null && message2.e) {
                    ALog.e(this.m, "cancelControlMessage", "command", message2.t);
                }
            }
        }
    }

    public int d() {
        return this.g.size();
    }

    public Collection<Message> e() {
        return this.g.values();
    }

    public Message a(String str) {
        return (Message) this.g.get(str);
    }

    public Message b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (Message) this.g.remove(str);
    }

    private boolean c(String str) {
        if (!TextUtils.isEmpty(str) && this.n.containsKey(str)) {
            return true;
        }
        return false;
    }

    private void d(String str) {
        if (!TextUtils.isEmpty(str) && !this.n.containsKey(str)) {
            this.n.put(str, str);
            i();
        }
    }

    private void h() {
        try {
            File file = new File(this.i.getDir("accs", 0), "message" + this.l.i());
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        this.n.put(readLine, readLine);
                    } else {
                        bufferedReader.close();
                        return;
                    }
                }
            }
            ALog.d(this.m, "message file not exist", new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void i() {
        try {
            FileWriter fileWriter = new FileWriter(new File(this.i.getDir("accs", 0), "message" + this.l.i()));
            fileWriter.write("");
            for (String str : this.n.keySet()) {
                fileWriter.append(str + "\r\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Intent c(Message message) {
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(message.s);
        intent.putExtra("command", message.t);
        intent.putExtra(Constants.KEY_SERVICE_ID, message.F);
        intent.putExtra(Constants.KEY_USER_ID, message.E);
        if (message.t != null && message.t.intValue() == 100) {
            intent.putExtra(Constants.KEY_DATA_ID, message.M);
        }
        return intent;
    }

    private void a(Map<Integer, String> map, Intent intent) {
        if (map != null && intent != null) {
            intent.putExtra(ExtraInfo.EXT_HEADER, (HashMap) map);
        }
    }

    private void a(Intent intent, String str, String str2, short s) {
        if (intent != null) {
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("source", str);
            }
            if (!TextUtils.isEmpty(str2)) {
                intent.putExtra(Constants.KEY_TARGET, str2);
            }
            intent.putExtra(Constants.KEY_FLAGS, s);
        }
    }

    public d f() {
        return this.j;
    }

    private void b(Message message, int i) {
        if (message != null) {
            String deviceId = UtilityImpl.getDeviceId(this.i);
            String str = System.currentTimeMillis() + "";
            boolean z = true;
            if (i != 200) {
                z = false;
            }
            switch (message.t.intValue()) {
                case 1:
                    com.taobao.accs.ut.statistics.a aVar = new com.taobao.accs.ut.statistics.a();
                    aVar.a = deviceId;
                    aVar.b = str;
                    aVar.c = z;
                    aVar.a(i);
                    aVar.commitUT();
                    return;
                case 3:
                    com.taobao.accs.ut.statistics.b bVar = new com.taobao.accs.ut.statistics.b();
                    bVar.a = deviceId;
                    bVar.b = str;
                    bVar.c = z;
                    bVar.e = message.E;
                    bVar.a(i);
                    bVar.commitUT();
                    return;
                default:
                    return;
            }
        }
    }

    private void a(String str, String str2) {
        e eVar = new e();
        eVar.a = UtilityImpl.getDeviceId(this.i);
        eVar.c = str;
        eVar.d = "" + System.currentTimeMillis();
        eVar.f = "";
        eVar.e = str2;
        eVar.b = "";
        eVar.commitUT();
    }

    public void g() {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(this.o);
        } catch (Throwable th) {
            ALog.e(this.m, "restoreTraffics", th, new Object[0]);
        }
    }

    public void a(com.taobao.accs.ut.monitor.TrafficsMonitor.a aVar) {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(new b(this, aVar));
        } catch (Throwable th) {
            ALog.e(this.m, "addTrafficsInfo", th, new Object[0]);
        }
    }
}
