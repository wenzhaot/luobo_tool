package com.netease.LDNetDiagnoService;

import android.util.Log;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.netease.LDNetDiagnoUtils.LDPingParse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LDNetPing {
    private static final String MATCH_PING_IP = "(?<=from ).*(?=: icmp_seq=1 ttl=)";
    private final int _sendCount;
    LDNetPingListener listener;

    public interface LDNetPingListener {
        void OnNetPingFinished(String str);
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

    public LDNetPing(LDNetPingListener listener, int theSendCount) {
        this.listener = listener;
        this._sendCount = theSendCount;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0077 A:{SYNTHETIC, Splitter: B:24:0x0077} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0086 A:{SYNTHETIC, Splitter: B:32:0x0086} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0092 A:{SYNTHETIC, Splitter: B:38:0x0092} */
    private java.lang.String execPing(com.netease.LDNetDiagnoService.LDNetPing.PingTask r11, boolean r12) {
        /*
        r10 = this;
        r0 = "ping -c ";
        if (r12 == 0) goto L_0x0008;
    L_0x0005:
        r0 = "ping -s 8185 -c  ";
    L_0x0008:
        r3 = 0;
        r6 = "";
        r4 = 0;
        r7 = java.lang.Runtime.getRuntime();	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r8 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r8.<init>();	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r8 = r8.append(r0);	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r9 = r10._sendCount;	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r8 = r8.append(r9);	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r9 = " ";
        r8 = r8.append(r9);	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r9 = r11.getHost();	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r8 = r8.append(r9);	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r8 = r8.toString();	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r3 = r7.exec(r8);	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r5 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r7 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r8 = r3.getInputStream();	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r7.<init>(r8);	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r5.<init>(r7);	 Catch:{ IOException -> 0x0071, InterruptedException -> 0x0080 }
        r2 = 0;
    L_0x0046:
        r2 = r5.readLine();	 Catch:{ IOException -> 0x00a1, InterruptedException -> 0x009e, all -> 0x009b }
        if (r2 == 0) goto L_0x005e;
    L_0x004c:
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00a1, InterruptedException -> 0x009e, all -> 0x009b }
        r7.<init>();	 Catch:{ IOException -> 0x00a1, InterruptedException -> 0x009e, all -> 0x009b }
        r7 = r7.append(r6);	 Catch:{ IOException -> 0x00a1, InterruptedException -> 0x009e, all -> 0x009b }
        r7 = r7.append(r2);	 Catch:{ IOException -> 0x00a1, InterruptedException -> 0x009e, all -> 0x009b }
        r6 = r7.toString();	 Catch:{ IOException -> 0x00a1, InterruptedException -> 0x009e, all -> 0x009b }
        goto L_0x0046;
    L_0x005e:
        r5.close();	 Catch:{ IOException -> 0x00a1, InterruptedException -> 0x009e, all -> 0x009b }
        r3.waitFor();	 Catch:{ IOException -> 0x00a1, InterruptedException -> 0x009e, all -> 0x009b }
        if (r5 == 0) goto L_0x0069;
    L_0x0066:
        r5.close();	 Catch:{ Exception -> 0x006e }
    L_0x0069:
        r3.destroy();	 Catch:{ Exception -> 0x006e }
        r4 = r5;
    L_0x006d:
        return r6;
    L_0x006e:
        r7 = move-exception;
        r4 = r5;
        goto L_0x006d;
    L_0x0071:
        r1 = move-exception;
    L_0x0072:
        r1.printStackTrace();	 Catch:{ all -> 0x008f }
        if (r4 == 0) goto L_0x007a;
    L_0x0077:
        r4.close();	 Catch:{ Exception -> 0x007e }
    L_0x007a:
        r3.destroy();	 Catch:{ Exception -> 0x007e }
        goto L_0x006d;
    L_0x007e:
        r7 = move-exception;
        goto L_0x006d;
    L_0x0080:
        r1 = move-exception;
    L_0x0081:
        r1.printStackTrace();	 Catch:{ all -> 0x008f }
        if (r4 == 0) goto L_0x0089;
    L_0x0086:
        r4.close();	 Catch:{ Exception -> 0x008d }
    L_0x0089:
        r3.destroy();	 Catch:{ Exception -> 0x008d }
        goto L_0x006d;
    L_0x008d:
        r7 = move-exception;
        goto L_0x006d;
    L_0x008f:
        r7 = move-exception;
    L_0x0090:
        if (r4 == 0) goto L_0x0095;
    L_0x0092:
        r4.close();	 Catch:{ Exception -> 0x0099 }
    L_0x0095:
        r3.destroy();	 Catch:{ Exception -> 0x0099 }
    L_0x0098:
        throw r7;
    L_0x0099:
        r8 = move-exception;
        goto L_0x0098;
    L_0x009b:
        r7 = move-exception;
        r4 = r5;
        goto L_0x0090;
    L_0x009e:
        r1 = move-exception;
        r4 = r5;
        goto L_0x0081;
    L_0x00a1:
        r1 = move-exception;
        r4 = r5;
        goto L_0x0072;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.LDNetDiagnoService.LDNetPing.execPing(com.netease.LDNetDiagnoService.LDNetPing$PingTask, boolean):java.lang.String");
    }

    public void exec(String host, boolean isNeedL) {
        PingTask pingTask = new PingTask(host);
        StringBuilder log = new StringBuilder(AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
        String status = execPing(pingTask, isNeedL);
        if (Pattern.compile(MATCH_PING_IP).matcher(status).find()) {
            Log.i("LDNetPing", "status" + status);
            log.append("\t" + status);
        } else if (status.length() == 0) {
            log.append("unknown host or network error");
        } else {
            log.append("timeout");
        }
        this.listener.OnNetPingFinished(LDPingParse.getFormattingStr(host, log.toString()) + "\n");
    }
}
