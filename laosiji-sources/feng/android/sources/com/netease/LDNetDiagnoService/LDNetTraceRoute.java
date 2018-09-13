package com.netease.LDNetDiagnoService;

import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LDNetTraceRoute {
    private static final String MATCH_PING_IP = "(?<=from ).*(?=: icmp_seq=1 ttl=)";
    private static final String MATCH_PING_TIME = "(?<=time=).*?ms";
    private static final String MATCH_TRACE_IP = "(?<=From )(?:[0-9]{1,3}\\.){3}[0-9]{1,3}";
    private static LDNetTraceRoute instance;
    static boolean loaded = true;
    private final String LOG_TAG = "LDNetTraceRoute";
    public boolean isCTrace = true;
    LDNetTraceRouteListener listener;

    public interface LDNetTraceRouteListener {
        void OnNetTraceFinished();

        void OnNetTraceUpdated(String str);
    }

    private class PingTask {
        private static final String MATCH_PING_HOST_IP = "(?<=\\().*?(?=\\))";
        private String host;

        public String getHost() {
            return this.host;
        }

        public PingTask(String host) {
            this.host = host;
            Matcher m = Pattern.compile(MATCH_PING_HOST_IP).matcher(host);
            if (m.find()) {
                this.host = m.group();
            }
        }
    }

    private class TraceTask {
        private int hop;
        private final String host;

        public TraceTask(String host, int hop) {
            this.host = host;
            this.hop = hop;
        }

        public String getHost() {
            return this.host;
        }

        public int getHop() {
            return this.hop;
        }

        public void setHop(int hop) {
            this.hop = hop;
        }
    }

    public native void startJNICTraceRoute(String str);

    private LDNetTraceRoute() {
    }

    public static LDNetTraceRoute getInstance() {
        if (instance == null) {
            instance = new LDNetTraceRoute();
        }
        return instance;
    }

    public void initListenter(LDNetTraceRouteListener listener) {
        this.listener = listener;
    }

    public void startTraceRoute(String host) {
        if (this.isCTrace && loaded) {
            try {
                startJNICTraceRoute(host);
                return;
            } catch (UnsatisfiedLinkError e) {
                e.printStackTrace();
                Log.i("LDNetTraceRoute", "调用java模拟traceRoute");
                execTrace(new TraceTask(host, 1));
                return;
            }
        }
        execTrace(new TraceTask(host, 1));
    }

    public void resetInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    static {
        try {
            System.loadLibrary("tracepath");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void printTraceInfo(String log) {
        this.listener.OnNetTraceUpdated(log.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0065 A:{SYNTHETIC, Splitter: B:21:0x0065} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074 A:{SYNTHETIC, Splitter: B:29:0x0074} */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0080 A:{SYNTHETIC, Splitter: B:35:0x0080} */
    private java.lang.String execPing(com.netease.LDNetDiagnoService.LDNetTraceRoute.PingTask r10) {
        /*
        r9 = this;
        r2 = 0;
        r5 = "";
        r3 = 0;
        r6 = java.lang.Runtime.getRuntime();	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7.<init>();	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r8 = "ping -c 1 ";
        r7 = r7.append(r8);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r8 = r10.getHost();	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7 = r7.append(r8);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7 = r7.toString();	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r2 = r6.exec(r7);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r4 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r6 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7 = r2.getInputStream();	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r6.<init>(r7);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r4.<init>(r6);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r1 = 0;
    L_0x0034:
        r1 = r4.readLine();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        if (r1 == 0) goto L_0x004c;
    L_0x003a:
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r6.<init>();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r6 = r6.append(r5);	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r6 = r6.append(r1);	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r5 = r6.toString();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        goto L_0x0034;
    L_0x004c:
        r4.close();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r2.waitFor();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        if (r4 == 0) goto L_0x0057;
    L_0x0054:
        r4.close();	 Catch:{ Exception -> 0x005c }
    L_0x0057:
        r2.destroy();	 Catch:{ Exception -> 0x005c }
        r3 = r4;
    L_0x005b:
        return r5;
    L_0x005c:
        r6 = move-exception;
        r3 = r4;
        goto L_0x005b;
    L_0x005f:
        r0 = move-exception;
    L_0x0060:
        r0.printStackTrace();	 Catch:{ all -> 0x007d }
        if (r3 == 0) goto L_0x0068;
    L_0x0065:
        r3.close();	 Catch:{ Exception -> 0x006c }
    L_0x0068:
        r2.destroy();	 Catch:{ Exception -> 0x006c }
        goto L_0x005b;
    L_0x006c:
        r6 = move-exception;
        goto L_0x005b;
    L_0x006e:
        r0 = move-exception;
    L_0x006f:
        r0.printStackTrace();	 Catch:{ all -> 0x007d }
        if (r3 == 0) goto L_0x0077;
    L_0x0074:
        r3.close();	 Catch:{ Exception -> 0x007b }
    L_0x0077:
        r2.destroy();	 Catch:{ Exception -> 0x007b }
        goto L_0x005b;
    L_0x007b:
        r6 = move-exception;
        goto L_0x005b;
    L_0x007d:
        r6 = move-exception;
    L_0x007e:
        if (r3 == 0) goto L_0x0083;
    L_0x0080:
        r3.close();	 Catch:{ Exception -> 0x0087 }
    L_0x0083:
        r2.destroy();	 Catch:{ Exception -> 0x0087 }
    L_0x0086:
        throw r6;
    L_0x0087:
        r7 = move-exception;
        goto L_0x0086;
    L_0x0089:
        r6 = move-exception;
        r3 = r4;
        goto L_0x007e;
    L_0x008c:
        r0 = move-exception;
        r3 = r4;
        goto L_0x006f;
    L_0x008f:
        r0 = move-exception;
        r3 = r4;
        goto L_0x0060;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.LDNetDiagnoService.LDNetTraceRoute.execPing(com.netease.LDNetDiagnoService.LDNetTraceRoute$PingTask):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x0219 A:{SYNTHETIC, Splitter: B:53:0x0219} */
    private void execTrace(com.netease.LDNetDiagnoService.LDNetTraceRoute.TraceTask r25) {
        /*
        r24 = this;
        r21 = "(?<=From )(?:[0-9]{1,3}\\.){3}[0-9]{1,3}";
        r12 = java.util.regex.Pattern.compile(r21);
        r21 = "(?<=from ).*(?=: icmp_seq=1 ttl=)";
        r10 = java.util.regex.Pattern.compile(r21);
        r21 = "(?<=time=).*?ms";
        r11 = java.util.regex.Pattern.compile(r21);
        r15 = 0;
        r16 = 0;
        r4 = 0;
        r17 = r16;
    L_0x001b:
        if (r4 != 0) goto L_0x023f;
    L_0x001d:
        r21 = r25.getHop();	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r22 = 30;
        r0 = r21;
        r1 = r22;
        if (r0 >= r1) goto L_0x023f;
    L_0x0029:
        r19 = "";
        r21 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r21.<init>();	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r22 = "ping -c 1 -t ";
        r21 = r21.append(r22);	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r22 = r25.getHop();	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r21 = r21.append(r22);	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r22 = " ";
        r21 = r21.append(r22);	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r22 = r25.getHost();	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r21 = r21.append(r22);	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r2 = r21.toString();	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r21 = java.lang.Runtime.getRuntime();	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r0 = r21;
        r15 = r0.exec(r2);	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r16 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r21 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r22 = r15.getInputStream();	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r21.<init>(r22);	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r0 = r16;
        r1 = r21;
        r0.<init>(r1);	 Catch:{ IOException -> 0x025e, InterruptedException -> 0x0256, all -> 0x0252 }
        r5 = 0;
    L_0x0070:
        r5 = r16.readLine();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        if (r5 == 0) goto L_0x008e;
    L_0x0076:
        r21 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21.<init>();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r21;
        r1 = r19;
        r21 = r0.append(r1);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r21;
        r21 = r0.append(r5);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r19 = r21.toString();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        goto L_0x0070;
    L_0x008e:
        r16.close();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r15.waitFor();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r19;
        r7 = r12.matcher(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
        r0 = r21;
        r6.<init>(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r7.find();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        if (r21 == 0) goto L_0x0177;
    L_0x00a9:
        r13 = r7.group();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r14 = new com.netease.LDNetDiagnoService.LDNetTraceRoute$PingTask;	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r24;
        r14.<init>(r13);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r24;
        r18 = r0.execPing(r14);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r18.length();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        if (r21 != 0) goto L_0x00cd;
    L_0x00c0:
        r21 = "unknown host or network error\n";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r4 = 1;
    L_0x00c9:
        r17 = r16;
        goto L_0x001b;
    L_0x00cd:
        r0 = r18;
        r9 = r11.matcher(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r9.find();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        if (r21 == 0) goto L_0x014b;
    L_0x00d9:
        r20 = r9.group();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r25.getHop();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = "\t\t";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r6.append(r13);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = "\t\t";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r20;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = "\t";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
    L_0x0106:
        r0 = r24;
        r0 = r0.listener;	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r0;
        r22 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r22.<init>();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r23 = r6.toString();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r22 = r22.append(r23);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r23 = "\n";
        r22 = r22.append(r23);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r22 = r22.toString();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21.OnNetTraceUpdated(r22);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r25.getHop();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r21 + 1;
        r0 = r25;
        r1 = r21;
        r0.setHop(r1);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        goto L_0x00c9;
    L_0x0135:
        r3 = move-exception;
    L_0x0136:
        r3.printStackTrace();	 Catch:{ all -> 0x0216 }
        if (r16 == 0) goto L_0x013e;
    L_0x013b:
        r16.close();	 Catch:{ Exception -> 0x025b }
    L_0x013e:
        r15.destroy();	 Catch:{ Exception -> 0x025b }
    L_0x0141:
        r0 = r24;
        r0 = r0.listener;
        r21 = r0;
        r21.OnNetTraceFinished();
        return;
    L_0x014b:
        r21 = r25.getHop();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = "\t\t";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r6.append(r13);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = "\t\t timeout";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        goto L_0x0106;
    L_0x0168:
        r3 = move-exception;
    L_0x0169:
        r3.printStackTrace();	 Catch:{ all -> 0x0216 }
        if (r16 == 0) goto L_0x0171;
    L_0x016e:
        r16.close();	 Catch:{ Exception -> 0x0175 }
    L_0x0171:
        r15.destroy();	 Catch:{ Exception -> 0x0175 }
        goto L_0x0141;
    L_0x0175:
        r21 = move-exception;
        goto L_0x0141;
    L_0x0177:
        r0 = r19;
        r8 = r10.matcher(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r8.find();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        if (r21 == 0) goto L_0x01e4;
    L_0x0183:
        r13 = r8.group();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r19;
        r9 = r11.matcher(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r9.find();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        if (r21 == 0) goto L_0x01e1;
    L_0x0193:
        r20 = r9.group();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r25.getHop();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = "\t\t";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r6.append(r13);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = "\t\t";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r20;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = "\t";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r24;
        r0 = r0.listener;	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r0;
        r22 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r22.<init>();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r23 = r6.toString();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r22 = r22.append(r23);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r23 = "\n";
        r22 = r22.append(r23);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r22 = r22.toString();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21.OnNetTraceUpdated(r22);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
    L_0x01e1:
        r4 = 1;
        goto L_0x00c9;
    L_0x01e4:
        r21 = r19.length();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        if (r21 != 0) goto L_0x0220;
    L_0x01ea:
        r21 = "unknown host or network error\t";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r4 = 1;
    L_0x01f3:
        r0 = r24;
        r0 = r0.listener;	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r0;
        r22 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r22.<init>();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r23 = r6.toString();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r22 = r22.append(r23);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r23 = "\n";
        r22 = r22.append(r23);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r22 = r22.toString();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21.OnNetTraceUpdated(r22);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        goto L_0x00c9;
    L_0x0216:
        r21 = move-exception;
    L_0x0217:
        if (r16 == 0) goto L_0x021c;
    L_0x0219:
        r16.close();	 Catch:{ Exception -> 0x0250 }
    L_0x021c:
        r15.destroy();	 Catch:{ Exception -> 0x0250 }
    L_0x021f:
        throw r21;
    L_0x0220:
        r21 = r25.getHop();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = "\t\t timeout \t";
        r0 = r21;
        r6.append(r0);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r25.getHop();	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        r21 = r21 + 1;
        r0 = r25;
        r1 = r21;
        r0.setHop(r1);	 Catch:{ IOException -> 0x0135, InterruptedException -> 0x0168 }
        goto L_0x01f3;
    L_0x023f:
        if (r17 == 0) goto L_0x0244;
    L_0x0241:
        r17.close();	 Catch:{ Exception -> 0x024b }
    L_0x0244:
        r15.destroy();	 Catch:{ Exception -> 0x024b }
        r16 = r17;
        goto L_0x0141;
    L_0x024b:
        r21 = move-exception;
        r16 = r17;
        goto L_0x0141;
    L_0x0250:
        r22 = move-exception;
        goto L_0x021f;
    L_0x0252:
        r21 = move-exception;
        r16 = r17;
        goto L_0x0217;
    L_0x0256:
        r3 = move-exception;
        r16 = r17;
        goto L_0x0169;
    L_0x025b:
        r21 = move-exception;
        goto L_0x0141;
    L_0x025e:
        r3 = move-exception;
        r16 = r17;
        goto L_0x0136;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.LDNetDiagnoService.LDNetTraceRoute.execTrace(com.netease.LDNetDiagnoService.LDNetTraceRoute$TraceTask):void");
    }
}
