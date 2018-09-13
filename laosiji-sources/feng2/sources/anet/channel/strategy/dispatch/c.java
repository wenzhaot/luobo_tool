package anet.channel.strategy.dispatch;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.flow.b;
import anet.channel.statist.AmdcStatistic;
import anet.channel.statist.StatObject;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.utils.d;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.HostnameVerifier;

/* compiled from: Taobao */
class c {
    static AtomicInteger a = new AtomicInteger(0);
    static HostnameVerifier b = new d();
    static Random c = new Random();

    c() {
    }

    static List<IConnStrategy> a(String str) {
        List<IConnStrategy> list = Collections.EMPTY_LIST;
        if (NetworkStatusHelper.h()) {
            return list;
        }
        List<IConnStrategy> connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(DispatchConstants.getAmdcServerDomain());
        ListIterator listIterator = connStrategyListByHost.listIterator();
        while (listIterator.hasNext()) {
            if (!((IConnStrategy) listIterator.next()).getProtocol().protocol.equalsIgnoreCase(str)) {
                listIterator.remove();
            }
        }
        return connStrategyListByHost;
    }

    public static void a(Map map) {
        if (map == null) {
            ALog.e("awcn.DispatchCore", "amdc request's parameter invalid!", null, new Object[0]);
            return;
        }
        String schemeByHost = StrategyCenter.getInstance().getSchemeByHost(DispatchConstants.getAmdcServerDomain(), "http");
        List a = a(schemeByHost);
        int i = 0;
        while (i < 3) {
            IConnStrategy iConnStrategy;
            String a2;
            Map hashMap = new HashMap(map);
            if (i != 2) {
                if (a.isEmpty()) {
                    iConnStrategy = null;
                } else {
                    iConnStrategy = (IConnStrategy) a.remove(0);
                }
                if (iConnStrategy != null) {
                    a2 = a(schemeByHost, iConnStrategy.getIp(), iConnStrategy.getPort(), hashMap, i);
                } else {
                    a2 = a(schemeByHost, null, 0, hashMap, i);
                }
            } else {
                String[] amdcServerFixIp = DispatchConstants.getAmdcServerFixIp();
                if (amdcServerFixIp == null || amdcServerFixIp.length <= 0) {
                    a2 = a(schemeByHost, null, 0, hashMap, i);
                    iConnStrategy = null;
                } else {
                    a2 = a(schemeByHost, amdcServerFixIp[c.nextInt(amdcServerFixIp.length)], 0, hashMap, i);
                    iConnStrategy = null;
                }
            }
            int a3 = a(a2, hashMap, i);
            if (iConnStrategy != null) {
                boolean z;
                ConnEvent connEvent = new ConnEvent();
                if (a3 == 0) {
                    z = true;
                } else {
                    z = false;
                }
                connEvent.isSuccess = z;
                StrategyCenter.getInstance().notifyConnEvent(DispatchConstants.getAmdcServerDomain(), iConnStrategy, connEvent);
            }
            if (a3 != 0 && a3 != 2) {
                i++;
            } else {
                return;
            }
        }
    }

    private static String a(String str, String str2, int i, Map<String, String> map, int i2) {
        StringBuilder stringBuilder = new StringBuilder(64);
        if (i2 == 2 && "https".equalsIgnoreCase(str) && c.nextBoolean()) {
            str = "http";
        }
        stringBuilder.append(str);
        stringBuilder.append(HttpConstant.SCHEME_SPLIT);
        if (str2 != null) {
            int i3 = i == 0 ? "https".equalsIgnoreCase(str) ? 443 : 80 : i;
            stringBuilder.append(str2).append(":").append(i3);
        } else {
            stringBuilder.append(DispatchConstants.getAmdcServerDomain());
        }
        stringBuilder.append(DispatchConstants.serverPath);
        Map treeMap = new TreeMap();
        treeMap.put(DispatchConstants.APPKEY, map.remove(DispatchConstants.APPKEY));
        treeMap.put(DispatchConstants.VERSION, map.remove(DispatchConstants.VERSION));
        treeMap.put(DispatchConstants.DEVICEID, map.remove(DispatchConstants.DEVICEID));
        treeMap.put(DispatchConstants.PLATFORM, map.remove(DispatchConstants.PLATFORM));
        stringBuilder.append('?');
        stringBuilder.append(d.b(treeMap, "utf-8"));
        return stringBuilder.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:125:0x0338 A:{SYNTHETIC, Splitter: B:125:0x0338} */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0303 A:{Catch:{ all -> 0x034b }} */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x031f A:{SYNTHETIC, Splitter: B:118:0x031f} */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0334 A:{Splitter: B:1:0x0040, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0303 A:{Catch:{ all -> 0x034b }} */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x031f A:{SYNTHETIC, Splitter: B:118:0x031f} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:114:0x0303, code:
            r5 = r2.toString();
     */
    /* JADX WARNING: Missing block: B:119:?, code:
            r3.disconnect();
     */
    /* JADX WARNING: Missing block: B:120:0x0324, code:
            r3 = move-exception;
     */
    /* JADX WARNING: Missing block: B:121:0x0325, code:
            anet.channel.util.ALog.e("awcn.DispatchCore", "http disconnect failed", null, r3, new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:122:0x0334, code:
            r2 = th;
     */
    /* JADX WARNING: Missing block: B:123:0x0335, code:
            r3 = null;
     */
    /* JADX WARNING: Missing block: B:131:0x034d, code:
            r2 = th;
     */
    /* JADX WARNING: Missing block: B:132:0x034e, code:
            r3 = null;
            r4 = r5;
     */
    public static int a(java.lang.String r17, java.util.Map r18, int r19) {
        /*
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "AMDC";
        r2 = r2.append(r3);
        r3 = a;
        r3 = r3.incrementAndGet();
        r3 = java.lang.String.valueOf(r3);
        r2 = r2.append(r3);
        r6 = r2.toString();
        r2 = "awcn.DispatchCore";
        r3 = "send amdc request";
        r4 = 2;
        r4 = new java.lang.Object[r4];
        r5 = 0;
        r7 = "url";
        r4[r5] = r7;
        r5 = 1;
        r4[r5] = r17;
        anet.channel.util.ALog.i(r2, r3, r6, r4);
        r2 = "Env";
        r0 = r18;
        r2 = r0.remove(r2);
        r2 = (anet.channel.entity.ENV) r2;
        r3 = 0;
        r4 = 0;
        r5 = new java.net.URL;	 Catch:{ Throwable -> 0x02f3, all -> 0x0334 }
        r0 = r17;
        r5.<init>(r0);	 Catch:{ Throwable -> 0x02f3, all -> 0x0334 }
        r3 = r5.openConnection();	 Catch:{ Throwable -> 0x034d, all -> 0x0334 }
        r3 = (java.net.HttpURLConnection) r3;	 Catch:{ Throwable -> 0x034d, all -> 0x0334 }
        r4 = 20000; // 0x4e20 float:2.8026E-41 double:9.8813E-320;
        r3.setConnectTimeout(r4);	 Catch:{ Throwable -> 0x0351 }
        r4 = 20000; // 0x4e20 float:2.8026E-41 double:9.8813E-320;
        r3.setReadTimeout(r4);	 Catch:{ Throwable -> 0x0351 }
        r4 = "POST";
        r3.setRequestMethod(r4);	 Catch:{ Throwable -> 0x0351 }
        r4 = 1;
        r3.setDoOutput(r4);	 Catch:{ Throwable -> 0x0351 }
        r4 = 1;
        r3.setDoInput(r4);	 Catch:{ Throwable -> 0x0351 }
        r4 = "Connection";
        r7 = "close";
        r3.addRequestProperty(r4, r7);	 Catch:{ Throwable -> 0x0351 }
        r4 = "Accept-Encoding";
        r7 = "gzip";
        r3.addRequestProperty(r4, r7);	 Catch:{ Throwable -> 0x0351 }
        r4 = 0;
        r3.setInstanceFollowRedirects(r4);	 Catch:{ Throwable -> 0x0351 }
        r4 = r5.getProtocol();	 Catch:{ Throwable -> 0x0351 }
        r7 = "https";
        r4 = r4.equals(r7);	 Catch:{ Throwable -> 0x0351 }
        if (r4 == 0) goto L_0x0091;
    L_0x0088:
        r0 = r3;
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ Throwable -> 0x0351 }
        r4 = r0;
        r7 = b;	 Catch:{ Throwable -> 0x0351 }
        r4.setHostnameVerifier(r7);	 Catch:{ Throwable -> 0x0351 }
    L_0x0091:
        r4 = r3.getOutputStream();	 Catch:{ Throwable -> 0x0351 }
        r7 = "utf-8";
        r0 = r18;
        r7 = anet.channel.strategy.utils.d.b(r0, r7);	 Catch:{ Throwable -> 0x0351 }
        r7 = r7.getBytes();	 Catch:{ Throwable -> 0x0351 }
        r4.write(r7);	 Catch:{ Throwable -> 0x0351 }
        r4 = r3.getResponseCode();	 Catch:{ Throwable -> 0x0351 }
        r8 = r3.getHeaderFields();	 Catch:{ Throwable -> 0x0351 }
        r9 = 1;
        r9 = anet.channel.util.ALog.isPrintLog(r9);	 Catch:{ Throwable -> 0x0351 }
        if (r9 == 0) goto L_0x00da;
    L_0x00b4:
        r9 = "awcn.DispatchCore";
        r10 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0351 }
        r10.<init>();	 Catch:{ Throwable -> 0x0351 }
        r11 = "amdc response. code: ";
        r10 = r10.append(r11);	 Catch:{ Throwable -> 0x0351 }
        r10 = r10.append(r4);	 Catch:{ Throwable -> 0x0351 }
        r10 = r10.toString();	 Catch:{ Throwable -> 0x0351 }
        r11 = 2;
        r11 = new java.lang.Object[r11];	 Catch:{ Throwable -> 0x0351 }
        r12 = 0;
        r13 = "\nheaders";
        r11[r12] = r13;	 Catch:{ Throwable -> 0x0351 }
        r12 = 1;
        r11[r12] = r8;	 Catch:{ Throwable -> 0x0351 }
        anet.channel.util.ALog.d(r9, r10, r6, r11);	 Catch:{ Throwable -> 0x0351 }
    L_0x00da:
        r9 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r4 == r9) goto L_0x010a;
    L_0x00de:
        r2 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
        if (r4 == r2) goto L_0x00e6;
    L_0x00e2:
        r2 = 307; // 0x133 float:4.3E-43 double:1.517E-321;
        if (r4 != r2) goto L_0x00f9;
    L_0x00e6:
        r2 = 2;
    L_0x00e7:
        r4 = java.lang.String.valueOf(r4);	 Catch:{ Throwable -> 0x0351 }
        r7 = "response code not 200";
        r0 = r19;
        a(r4, r7, r5, r0, r2);	 Catch:{ Throwable -> 0x0351 }
        if (r3 == 0) goto L_0x00f8;
    L_0x00f5:
        r3.disconnect();	 Catch:{ Exception -> 0x00fb }
    L_0x00f8:
        return r2;
    L_0x00f9:
        r2 = 1;
        goto L_0x00e7;
    L_0x00fb:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x00f8;
    L_0x010a:
        r4 = "x-am-code";
        r9 = anet.channel.strategy.utils.d.a(r8, r4);	 Catch:{ Throwable -> 0x0351 }
        r4 = "1000";
        r4 = r4.equals(r9);	 Catch:{ Throwable -> 0x0351 }
        if (r4 != 0) goto L_0x015d;
    L_0x011a:
        r2 = "1007";
        r2 = r2.equals(r9);	 Catch:{ Throwable -> 0x0351 }
        if (r2 != 0) goto L_0x012c;
    L_0x0123:
        r2 = "1008";
        r2 = r2.equals(r9);	 Catch:{ Throwable -> 0x0351 }
        if (r2 == 0) goto L_0x015b;
    L_0x012c:
        r2 = 2;
    L_0x012d:
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0351 }
        r4.<init>();	 Catch:{ Throwable -> 0x0351 }
        r7 = "return code: ";
        r4 = r4.append(r7);	 Catch:{ Throwable -> 0x0351 }
        r4 = r4.append(r9);	 Catch:{ Throwable -> 0x0351 }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x0351 }
        r0 = r19;
        a(r9, r4, r5, r0, r2);	 Catch:{ Throwable -> 0x0351 }
        if (r3 == 0) goto L_0x00f8;
    L_0x0148:
        r3.disconnect();	 Catch:{ Exception -> 0x014c }
        goto L_0x00f8;
    L_0x014c:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x00f8;
    L_0x015b:
        r2 = 1;
        goto L_0x012d;
    L_0x015d:
        r4 = "x-am-sign";
        r4 = anet.channel.strategy.utils.d.a(r8, r4);	 Catch:{ Throwable -> 0x0351 }
        r10 = r4.trim();	 Catch:{ Throwable -> 0x0351 }
        r4 = android.text.TextUtils.isEmpty(r10);	 Catch:{ Throwable -> 0x0351 }
        if (r4 == 0) goto L_0x0192;
    L_0x016e:
        r2 = "-1001";
        r4 = "response sign is empty";
        r7 = 1;
        r0 = r19;
        a(r2, r4, r5, r0, r7);	 Catch:{ Throwable -> 0x0351 }
        r2 = 1;
        if (r3 == 0) goto L_0x00f8;
    L_0x017d:
        r3.disconnect();	 Catch:{ Exception -> 0x0182 }
        goto L_0x00f8;
    L_0x0182:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x00f8;
    L_0x0192:
        r4 = new anet.channel.strategy.utils.c;	 Catch:{ Throwable -> 0x0351 }
        r11 = r3.getInputStream();	 Catch:{ Throwable -> 0x0351 }
        r4.<init>(r11);	 Catch:{ Throwable -> 0x0351 }
        r11 = "gzip";
        r12 = "Content-Encoding";
        r8 = anet.channel.strategy.utils.d.a(r8, r12);	 Catch:{ Throwable -> 0x0351 }
        r8 = r11.equalsIgnoreCase(r8);	 Catch:{ Throwable -> 0x0351 }
        r8 = a(r4, r8);	 Catch:{ Throwable -> 0x0351 }
        r11 = 1;
        r11 = anet.channel.util.ALog.isPrintLog(r11);	 Catch:{ Throwable -> 0x0351 }
        if (r11 == 0) goto L_0x01c9;
    L_0x01b4:
        r11 = "awcn.DispatchCore";
        r12 = "amdc response body";
        r13 = 2;
        r13 = new java.lang.Object[r13];	 Catch:{ Throwable -> 0x0351 }
        r14 = 0;
        r15 = "\nbody";
        r13[r14] = r15;	 Catch:{ Throwable -> 0x0351 }
        r14 = 1;
        r13[r14] = r8;	 Catch:{ Throwable -> 0x0351 }
        anet.channel.util.ALog.d(r11, r12, r6, r13);	 Catch:{ Throwable -> 0x0351 }
    L_0x01c9:
        r7 = r7.length;	 Catch:{ Throwable -> 0x0351 }
        r12 = (long) r7;	 Catch:{ Throwable -> 0x0351 }
        r14 = r4.a();	 Catch:{ Throwable -> 0x0351 }
        r0 = r17;
        a(r0, r12, r14);	 Catch:{ Throwable -> 0x0351 }
        r4 = android.text.TextUtils.isEmpty(r8);	 Catch:{ Throwable -> 0x0351 }
        if (r4 == 0) goto L_0x01fe;
    L_0x01da:
        r2 = "-1002";
        r4 = "read answer error";
        r7 = 1;
        r0 = r19;
        a(r2, r4, r5, r0, r7);	 Catch:{ Throwable -> 0x0351 }
        r2 = 1;
        if (r3 == 0) goto L_0x00f8;
    L_0x01e9:
        r3.disconnect();	 Catch:{ Exception -> 0x01ee }
        goto L_0x00f8;
    L_0x01ee:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x00f8;
    L_0x01fe:
        r4 = 0;
        r7 = anet.channel.strategy.dispatch.a.b();	 Catch:{ Throwable -> 0x0351 }
        if (r7 == 0) goto L_0x0209;
    L_0x0205:
        r4 = r7.sign(r8);	 Catch:{ Throwable -> 0x0351 }
    L_0x0209:
        r7 = r4.equalsIgnoreCase(r10);	 Catch:{ Throwable -> 0x0351 }
        if (r7 != 0) goto L_0x0251;
    L_0x020f:
        r2 = "awcn.DispatchCore";
        r7 = "check ret sign failed";
        r8 = 4;
        r8 = new java.lang.Object[r8];	 Catch:{ Throwable -> 0x0351 }
        r9 = 0;
        r11 = "retSign";
        r8[r9] = r11;	 Catch:{ Throwable -> 0x0351 }
        r9 = 1;
        r8[r9] = r10;	 Catch:{ Throwable -> 0x0351 }
        r9 = 2;
        r10 = "checkSign";
        r8[r9] = r10;	 Catch:{ Throwable -> 0x0351 }
        r9 = 3;
        r8[r9] = r4;	 Catch:{ Throwable -> 0x0351 }
        anet.channel.util.ALog.e(r2, r7, r6, r8);	 Catch:{ Throwable -> 0x0351 }
        r2 = "-1003";
        r4 = "check sign failed";
        r7 = 1;
        r0 = r19;
        a(r2, r4, r5, r0, r7);	 Catch:{ Throwable -> 0x0351 }
        r2 = 1;
        if (r3 == 0) goto L_0x00f8;
    L_0x023c:
        r3.disconnect();	 Catch:{ Exception -> 0x0241 }
        goto L_0x00f8;
    L_0x0241:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x00f8;
    L_0x0251:
        r4 = new org.json.JSONTokener;	 Catch:{ JSONException -> 0x02b4 }
        r4.<init>(r8);	 Catch:{ JSONException -> 0x02b4 }
        r4 = r4.nextValue();	 Catch:{ JSONException -> 0x02b4 }
        r4 = (org.json.JSONObject) r4;	 Catch:{ JSONException -> 0x02b4 }
        r7 = anet.channel.GlobalAppRuntimeInfo.getEnv();	 Catch:{ JSONException -> 0x02b4 }
        if (r7 == r2) goto L_0x0286;
    L_0x0262:
        r2 = "awcn.DispatchCore";
        r4 = "env change, do not notify result";
        r7 = 0;
        r7 = new java.lang.Object[r7];	 Catch:{ JSONException -> 0x02b4 }
        anet.channel.util.ALog.w(r2, r4, r6, r7);	 Catch:{ JSONException -> 0x02b4 }
        r2 = 0;
        if (r3 == 0) goto L_0x00f8;
    L_0x0271:
        r3.disconnect();	 Catch:{ Exception -> 0x0276 }
        goto L_0x00f8;
    L_0x0276:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x00f8;
    L_0x0286:
        r2 = anet.channel.strategy.dispatch.HttpDispatcher.getInstance();	 Catch:{ JSONException -> 0x02b4 }
        r7 = new anet.channel.strategy.dispatch.DispatchEvent;	 Catch:{ JSONException -> 0x02b4 }
        r8 = 1;
        r7.<init>(r8, r4);	 Catch:{ JSONException -> 0x02b4 }
        r2.fireEvent(r7);	 Catch:{ JSONException -> 0x02b4 }
        r2 = "request success";
        r4 = 0;
        r0 = r19;
        a(r9, r2, r5, r0, r4);	 Catch:{ Throwable -> 0x0351 }
        r2 = 0;
        if (r3 == 0) goto L_0x00f8;
    L_0x029f:
        r3.disconnect();	 Catch:{ Exception -> 0x02a4 }
        goto L_0x00f8;
    L_0x02a4:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x00f8;
    L_0x02b4:
        r2 = move-exception;
        r2 = anet.channel.strategy.dispatch.HttpDispatcher.getInstance();	 Catch:{ Throwable -> 0x0351 }
        r4 = new anet.channel.strategy.dispatch.DispatchEvent;	 Catch:{ Throwable -> 0x0351 }
        r7 = 0;
        r8 = 0;
        r4.<init>(r7, r8);	 Catch:{ Throwable -> 0x0351 }
        r2.fireEvent(r4);	 Catch:{ Throwable -> 0x0351 }
        r2 = "awcn.DispatchCore";
        r4 = "resolve amdc anser failed";
        r7 = 0;
        r7 = new java.lang.Object[r7];	 Catch:{ Throwable -> 0x0351 }
        anet.channel.util.ALog.e(r2, r4, r6, r7);	 Catch:{ Throwable -> 0x0351 }
        r2 = "-1004";
        r4 = "resolve answer failed";
        r7 = 1;
        r0 = r19;
        a(r2, r4, r5, r0, r7);	 Catch:{ Throwable -> 0x0351 }
        r2 = 1;
        if (r3 == 0) goto L_0x00f8;
    L_0x02de:
        r3.disconnect();	 Catch:{ Exception -> 0x02e3 }
        goto L_0x00f8;
    L_0x02e3:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x00f8;
    L_0x02f3:
        r2 = move-exception;
        r16 = r4;
        r4 = r3;
        r3 = r16;
    L_0x02f9:
        r5 = r2.getMessage();	 Catch:{ all -> 0x034b }
        r7 = android.text.TextUtils.isEmpty(r5);	 Catch:{ all -> 0x034b }
        if (r7 == 0) goto L_0x0307;
    L_0x0303:
        r5 = r2.toString();	 Catch:{ all -> 0x034b }
    L_0x0307:
        r7 = "-1000";
        r8 = 1;
        r0 = r19;
        a(r7, r5, r4, r0, r8);	 Catch:{ all -> 0x034b }
        r4 = "awcn.DispatchCore";
        r5 = "amdc request fail";
        r7 = 0;
        r7 = new java.lang.Object[r7];	 Catch:{ all -> 0x034b }
        anet.channel.util.ALog.e(r4, r5, r6, r2, r7);	 Catch:{ all -> 0x034b }
        r2 = 1;
        if (r3 == 0) goto L_0x00f8;
    L_0x031f:
        r3.disconnect();	 Catch:{ Exception -> 0x0324 }
        goto L_0x00f8;
    L_0x0324:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x00f8;
    L_0x0334:
        r2 = move-exception;
        r3 = r4;
    L_0x0336:
        if (r3 == 0) goto L_0x033b;
    L_0x0338:
        r3.disconnect();	 Catch:{ Exception -> 0x033c }
    L_0x033b:
        throw r2;
    L_0x033c:
        r3 = move-exception;
        r4 = "awcn.DispatchCore";
        r5 = "http disconnect failed";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];
        anet.channel.util.ALog.e(r4, r5, r6, r3, r7);
        goto L_0x033b;
    L_0x034b:
        r2 = move-exception;
        goto L_0x0336;
    L_0x034d:
        r2 = move-exception;
        r3 = r4;
        r4 = r5;
        goto L_0x02f9;
    L_0x0351:
        r2 = move-exception;
        r4 = r5;
        goto L_0x02f9;
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.dispatch.c.a(java.lang.String, java.util.Map, int):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003a A:{SYNTHETIC, Splitter: B:17:0x003a} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0054 A:{SYNTHETIC, Splitter: B:27:0x0054} */
    static java.lang.String a(java.io.InputStream r7, boolean r8) {
        /*
        r0 = 0;
        r1 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2 = new java.io.BufferedInputStream;
        r2.<init>(r7);
        r4 = new java.io.ByteArrayOutputStream;
        r4.<init>(r1);
        if (r8 == 0) goto L_0x0064;
    L_0x000f:
        r3 = new java.util.zip.GZIPInputStream;	 Catch:{ IOException -> 0x002a }
        r3.<init>(r2);	 Catch:{ IOException -> 0x002a }
    L_0x0014:
        r2 = new android.util.Base64InputStream;	 Catch:{ IOException -> 0x0061, all -> 0x005e }
        r1 = 0;
        r2.<init>(r3, r1);	 Catch:{ IOException -> 0x0061, all -> 0x005e }
        r1 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r1 = new byte[r1];	 Catch:{ IOException -> 0x002a }
    L_0x001e:
        r3 = r2.read(r1);	 Catch:{ IOException -> 0x002a }
        r5 = -1;
        if (r3 == r5) goto L_0x003e;
    L_0x0025:
        r5 = 0;
        r4.write(r1, r5, r3);	 Catch:{ IOException -> 0x002a }
        goto L_0x001e;
    L_0x002a:
        r1 = move-exception;
    L_0x002b:
        r3 = "awcn.DispatchCore";
        r4 = "";
        r5 = 0;
        r6 = 0;
        r6 = new java.lang.Object[r6];	 Catch:{ all -> 0x0051 }
        anet.channel.util.ALog.e(r3, r4, r5, r1, r6);	 Catch:{ all -> 0x0051 }
        if (r2 == 0) goto L_0x003d;
    L_0x003a:
        r2.close();	 Catch:{ IOException -> 0x005a }
    L_0x003d:
        return r0;
    L_0x003e:
        r1 = new java.lang.String;	 Catch:{ IOException -> 0x002a }
        r3 = r4.toByteArray();	 Catch:{ IOException -> 0x002a }
        r4 = "utf-8";
        r1.<init>(r3, r4);	 Catch:{ IOException -> 0x002a }
        if (r2 == 0) goto L_0x004f;
    L_0x004c:
        r2.close();	 Catch:{ IOException -> 0x0058 }
    L_0x004f:
        r0 = r1;
        goto L_0x003d;
    L_0x0051:
        r0 = move-exception;
    L_0x0052:
        if (r2 == 0) goto L_0x0057;
    L_0x0054:
        r2.close();	 Catch:{ IOException -> 0x005c }
    L_0x0057:
        throw r0;
    L_0x0058:
        r0 = move-exception;
        goto L_0x004f;
    L_0x005a:
        r1 = move-exception;
        goto L_0x003d;
    L_0x005c:
        r1 = move-exception;
        goto L_0x0057;
    L_0x005e:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0052;
    L_0x0061:
        r1 = move-exception;
        r2 = r3;
        goto L_0x002b;
    L_0x0064:
        r3 = r2;
        goto L_0x0014;
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.dispatch.c.a(java.io.InputStream, boolean):java.lang.String");
    }

    static void a(String str, String str2, URL url, int i, int i2) {
        if ((i2 != 1 || i == 2) && GlobalAppRuntimeInfo.isTargetProcess()) {
            try {
                StatObject amdcStatistic = new AmdcStatistic();
                amdcStatistic.errorCode = str;
                amdcStatistic.errorMsg = str2;
                if (url != null) {
                    amdcStatistic.host = url.getHost();
                    amdcStatistic.url = url.toString();
                }
                amdcStatistic.retryTimes = i;
                AppMonitor.getInstance().commitStat(amdcStatistic);
            } catch (Exception e) {
            }
        }
    }

    static void a(String str, long j, long j2) {
        try {
            b bVar = new b();
            bVar.a = "amdc";
            bVar.b = "http";
            bVar.c = str;
            bVar.d = j;
            bVar.e = j2;
            anet.channel.flow.c.a().commitFlow(bVar);
        } catch (Throwable e) {
            ALog.e("awcn.DispatchCore", "commit flow info failed!", null, e, new Object[0]);
        }
    }
}
