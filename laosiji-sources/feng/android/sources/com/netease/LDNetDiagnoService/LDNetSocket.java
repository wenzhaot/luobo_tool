package com.netease.LDNetDiagnoService;

import android.util.Log;
import com.feng.car.utils.FengConstant;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

public class LDNetSocket {
    private static final int CONN_TIMES = 4;
    private static final String HOSTERR = "DNS解析失败,主机地址不可达";
    private static final String IOERR = "DNS解析正常,IO异常,TCP建立失败";
    private static final int PORT = 80;
    private static final String TIMEOUT = "DNS解析正常,连接超时,TCP建立失败";
    private static LDNetSocket instance = null;
    static boolean loaded = true;
    private final long[] RttTimes = new long[4];
    public InetAddress[] _remoteInet;
    public List<String> _remoteIpList;
    public boolean isCConn = true;
    private boolean[] isConnnected;
    private LDNetSocketListener listener;
    private int timeOut = FengConstant.LONG_IMAGE_HEIGHT;

    public interface LDNetSocketListener {
        void OnNetSocketFinished(String str);

        void OnNetSocketUpdated(String str);
    }

    public native void startJNITelnet(String str, String str2);

    static {
        try {
            System.loadLibrary("tracepath");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private LDNetSocket() {
    }

    public static LDNetSocket getInstance() {
        if (instance == null) {
            instance = new LDNetSocket();
        }
        return instance;
    }

    public void initListener(LDNetSocketListener listener) {
        this.listener = listener;
    }

    public boolean exec(String host) {
        if (!this.isCConn || !loaded) {
            return execUseJava(host);
        }
        try {
            startJNITelnet(host, "80");
            return true;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            Log.i("LDNetSocket", "call jni failed, call execUseJava");
            return execUseJava(host);
        }
    }

    private boolean execUseJava(String host) {
        if (this._remoteInet == null || this._remoteIpList == null) {
            this.listener.OnNetSocketFinished(HOSTERR);
        } else {
            int len = this._remoteInet.length;
            this.isConnnected = new boolean[len];
            for (int i = 0; i < len; i++) {
                if (i != 0) {
                    this.listener.OnNetSocketUpdated("\n");
                }
                this.isConnnected[i] = execIP(this._remoteInet[i], (String) this._remoteIpList.get(i));
            }
            for (boolean valueOf : this.isConnnected) {
                if (Boolean.valueOf(valueOf).booleanValue()) {
                    this.listener.OnNetSocketFinished("\n");
                    return true;
                }
            }
        }
        this.listener.OnNetSocketFinished("\n");
        return false;
    }

    private boolean execIP(InetAddress inetAddress, String ip) {
        boolean isConnected = true;
        StringBuilder log = new StringBuilder();
        if (inetAddress == null || ip == null) {
            isConnected = false;
        } else {
            InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, 80);
            int flag = 0;
            this.listener.OnNetSocketUpdated("Connect to host: " + ip + "...\n");
            int i = 0;
            while (i < 4) {
                execSocket(socketAddress, this.timeOut, i);
                if (this.RttTimes[i] == -1) {
                    this.listener.OnNetSocketUpdated((i + 1) + "'s time=TimeOut,  ");
                    this.timeOut += 4000;
                    if (i > 0 && this.RttTimes[i - 1] == -1) {
                        flag = -1;
                        break;
                    }
                } else if (this.RttTimes[i] == -2) {
                    this.listener.OnNetSocketUpdated((i + 1) + "'s time=IOException");
                    if (i > 0 && this.RttTimes[i - 1] == -2) {
                        flag = -2;
                        break;
                    }
                } else {
                    this.listener.OnNetSocketUpdated((i + 1) + "'s time=" + this.RttTimes[i] + "ms,  ");
                }
                i++;
            }
            long time = 0;
            int count = 0;
            if (flag == -1) {
                isConnected = false;
            } else if (flag == -2) {
                isConnected = false;
            } else {
                for (i = 0; i < 4; i++) {
                    if (this.RttTimes[i] > 0) {
                        time += this.RttTimes[i];
                        count++;
                    }
                }
                if (count > 0) {
                    log.append("average=" + (time / ((long) count)) + Parameters.MESSAGE_SEQ);
                }
            }
        }
        this.listener.OnNetSocketUpdated(log.toString() + "\n");
        return isConnected;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0034 A:{SYNTHETIC, Splitter: B:15:0x0034} */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0049 A:{SYNTHETIC, Splitter: B:23:0x0049} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0055 A:{SYNTHETIC, Splitter: B:29:0x0055} */
    private void execSocket(java.net.InetSocketAddress r11, int r12, int r13) {
        /*
        r10 = this;
        r1 = 0;
        r6 = 0;
        r2 = 0;
        r4 = new java.net.Socket;	 Catch:{ SocketTimeoutException -> 0x0028, IOException -> 0x003d }
        r4.<init>();	 Catch:{ SocketTimeoutException -> 0x0028, IOException -> 0x003d }
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ SocketTimeoutException -> 0x0064, IOException -> 0x0061, all -> 0x005e }
        r4.connect(r11, r12);	 Catch:{ SocketTimeoutException -> 0x0064, IOException -> 0x0061, all -> 0x005e }
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ SocketTimeoutException -> 0x0064, IOException -> 0x0061, all -> 0x005e }
        r5 = r10.RttTimes;	 Catch:{ SocketTimeoutException -> 0x0064, IOException -> 0x0061, all -> 0x005e }
        r8 = r2 - r6;
        r5[r13] = r8;	 Catch:{ SocketTimeoutException -> 0x0064, IOException -> 0x0061, all -> 0x005e }
        if (r4 == 0) goto L_0x0067;
    L_0x001d:
        r4.close();	 Catch:{ IOException -> 0x0022 }
        r1 = r4;
    L_0x0021:
        return;
    L_0x0022:
        r0 = move-exception;
        r0.printStackTrace();
        r1 = r4;
        goto L_0x0021;
    L_0x0028:
        r0 = move-exception;
    L_0x0029:
        r5 = r10.RttTimes;	 Catch:{ all -> 0x0052 }
        r8 = -1;
        r5[r13] = r8;	 Catch:{ all -> 0x0052 }
        r0.printStackTrace();	 Catch:{ all -> 0x0052 }
        if (r1 == 0) goto L_0x0021;
    L_0x0034:
        r1.close();	 Catch:{ IOException -> 0x0038 }
        goto L_0x0021;
    L_0x0038:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0021;
    L_0x003d:
        r0 = move-exception;
    L_0x003e:
        r5 = r10.RttTimes;	 Catch:{ all -> 0x0052 }
        r8 = -2;
        r5[r13] = r8;	 Catch:{ all -> 0x0052 }
        r0.printStackTrace();	 Catch:{ all -> 0x0052 }
        if (r1 == 0) goto L_0x0021;
    L_0x0049:
        r1.close();	 Catch:{ IOException -> 0x004d }
        goto L_0x0021;
    L_0x004d:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0021;
    L_0x0052:
        r5 = move-exception;
    L_0x0053:
        if (r1 == 0) goto L_0x0058;
    L_0x0055:
        r1.close();	 Catch:{ IOException -> 0x0059 }
    L_0x0058:
        throw r5;
    L_0x0059:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0058;
    L_0x005e:
        r5 = move-exception;
        r1 = r4;
        goto L_0x0053;
    L_0x0061:
        r0 = move-exception;
        r1 = r4;
        goto L_0x003e;
    L_0x0064:
        r0 = move-exception;
        r1 = r4;
        goto L_0x0029;
    L_0x0067:
        r1 = r4;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.LDNetDiagnoService.LDNetSocket.execSocket(java.net.InetSocketAddress, int, int):void");
    }

    public void resetInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public void printSocketInfo(String log) {
        this.listener.OnNetSocketUpdated(log);
    }
}
