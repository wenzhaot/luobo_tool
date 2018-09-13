package com.netease.LDNetDiagnoService;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.netease.LDNetDiagnoService.LDNetPing.LDNetPingListener;
import com.netease.LDNetDiagnoService.LDNetSocket.LDNetSocketListener;
import com.netease.LDNetDiagnoService.LDNetTraceRoute.LDNetTraceRouteListener;
import com.netease.LDNetDiagnoUtils.LDNetUtil;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LDNetDiagnoService extends LDNetAsyncTaskEx<String, String, String> implements LDNetPingListener, LDNetTraceRouteListener, LDNetSocketListener {
    private static final int CORE_POOL_SIZE = 1;
    private static final int KEEP_ALIVE = 10;
    private static final int MAXIMUM_POOL_SIZE = 1;
    private static ThreadPoolExecutor sExecutor = null;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "Trace #" + this.mCount.getAndIncrement());
            t.setPriority(1);
            return t;
        }
    };
    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue(2);
    private String _ISOCountryCode;
    private String _MobileCountryCode;
    private String _MobileNetCode;
    private String _UID;
    private String _appCode;
    private String _appName;
    private String _appVersion;
    private String _carrierName;
    private Context _context;
    private String _deviceID;
    private String _dns1;
    private String _dns2;
    private String _dormain;
    private String _gateWay;
    private boolean _isDomainParseOk;
    private boolean _isNetConnected;
    private boolean _isRunning;
    private boolean _isSocketConnected;
    private boolean _isUseJNICConn = false;
    private boolean _isUseJNICTrace = true;
    private String _localIp;
    private final StringBuilder _logInfo = new StringBuilder(AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
    private LDNetDiagnoListener _netDiagnolistener;
    private LDNetPing _netPinger;
    private LDNetSocket _netSocker;
    private String _netType;
    private InetAddress[] _remoteInet;
    private List<String> _remoteIpList;
    private TelephonyManager _telManager = null;
    private LDNetTraceRoute _traceRouter;

    public LDNetDiagnoService(Context context, String theAppCode, String theAppName, String theAppVersion, String theUID, String theDeviceID, String theDormain, String theCarrierName, String theISOCountryCode, String theMobileCountryCode, String theMobileNetCode, LDNetDiagnoListener theListener) {
        this._context = context;
        this._appCode = theAppCode;
        this._appName = theAppName;
        this._appVersion = theAppVersion;
        this._UID = theUID;
        this._deviceID = theDeviceID;
        this._dormain = theDormain;
        this._carrierName = theCarrierName;
        this._ISOCountryCode = theISOCountryCode;
        this._MobileCountryCode = theMobileCountryCode;
        this._MobileNetCode = theMobileNetCode;
        this._netDiagnolistener = theListener;
        this._isRunning = false;
        this._remoteIpList = new ArrayList();
        this._telManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        sExecutor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);
    }

    protected String doInBackground(String... params) {
        if (isCancelled()) {
            return null;
        }
        return startNetDiagnosis();
    }

    protected void onPostExecute(String result) {
        if (!isCancelled()) {
            super.onPostExecute(result);
            recordStepInfo("\n网络诊断结束\n");
            stopNetDialogsis();
            if (this._netDiagnolistener != null) {
                this._netDiagnolistener.OnNetDiagnoFinished(this._logInfo.toString());
            }
        }
    }

    protected void onProgressUpdate(String... values) {
        if (!isCancelled()) {
            super.onProgressUpdate(values);
            if (this._netDiagnolistener != null) {
                this._netDiagnolistener.OnNetDiagnoUpdated(values[0]);
            }
        }
    }

    protected void onCancelled() {
        stopNetDialogsis();
    }

    public String startNetDiagnosis() {
        if (TextUtils.isEmpty(this._dormain)) {
            return "";
        }
        this._isRunning = true;
        this._logInfo.setLength(0);
        recordStepInfo("开始诊断...");
        recordCurrentAppVersion();
        recordLocalNetEnvironmentInfo();
        if (this._isNetConnected) {
            recordStepInfo("\n开始TCP连接测试...");
            this._netSocker = LDNetSocket.getInstance();
            this._netSocker._remoteInet = this._remoteInet;
            this._netSocker._remoteIpList = this._remoteIpList;
            this._netSocker.initListener(this);
            this._netSocker.isCConn = this._isUseJNICConn;
            this._isSocketConnected = this._netSocker.exec(this._dormain);
            recordStepInfo("\n开始ping...");
            if (this._isNetConnected) {
                this._netPinger = new LDNetPing(this, 4);
                recordStepInfo("ping..." + this._dormain);
                this._netPinger.exec(this._dormain, false);
                recordStepInfo("ping本机IP..." + this._localIp);
                this._netPinger.exec(this._localIp, false);
                if (LDNetUtil.NETWORKTYPE_WIFI.equals(this._netType)) {
                    recordStepInfo("ping本地网关..." + this._gateWay);
                    this._netPinger.exec(this._gateWay, false);
                }
                recordStepInfo("ping本地DNS1..." + this._dns1);
                this._netPinger.exec(this._dns1, false);
                recordStepInfo("ping本地DNS2..." + this._dns2);
                this._netPinger.exec(this._dns2, false);
            }
            if (this._netPinger == null) {
                this._netPinger = new LDNetPing(this, 4);
            }
            recordStepInfo("\n开始traceroute...");
            this._traceRouter = LDNetTraceRoute.getInstance();
            this._traceRouter.initListenter(this);
            this._traceRouter.isCTrace = this._isUseJNICTrace;
            this._traceRouter.startTraceRoute(this._dormain);
            return this._logInfo.toString();
        }
        recordStepInfo("\n\n当前主机未联网,请检查网络！");
        return this._logInfo.toString();
    }

    public void stopNetDialogsis() {
        if (this._isRunning) {
            if (this._netSocker != null) {
                this._netSocker.resetInstance();
                this._netSocker = null;
            }
            if (this._netPinger != null) {
                this._netPinger = null;
            }
            if (this._traceRouter != null) {
                this._traceRouter.resetInstance();
                this._traceRouter = null;
            }
            cancel(true);
            if (!(sExecutor == null || sExecutor.isShutdown())) {
                sExecutor.shutdown();
                sExecutor = null;
            }
            this._isRunning = false;
        }
    }

    public void setIfUseJNICConn(boolean use) {
        this._isUseJNICConn = use;
    }

    public void setIfUseJNICTrace(boolean use) {
        this._isUseJNICTrace = use;
    }

    public void printLogInfo() {
        System.out.print(this._logInfo);
    }

    private void recordStepInfo(String stepInfo) {
        this._logInfo.append(stepInfo + "\n");
        publishProgress(stepInfo + "\n");
    }

    public void OnNetTraceFinished() {
    }

    public void OnNetTraceUpdated(String log) {
        if (log != null) {
            if (this._traceRouter == null || !this._traceRouter.isCTrace) {
                recordStepInfo(log);
                return;
            }
            if (log.contains(Parameters.MESSAGE_SEQ) || log.contains("***")) {
                log = log + "\n";
            }
            this._logInfo.append(log);
            publishProgress(log);
        }
    }

    public void OnNetSocketFinished(String log) {
        this._logInfo.append(log);
        publishProgress(log);
    }

    public void OnNetSocketUpdated(String log) {
        this._logInfo.append(log);
        publishProgress(log);
    }

    private void recordCurrentAppVersion() {
        recordStepInfo("应用code:\t" + this._appCode);
        recordStepInfo("应用名称:\t" + this._appName);
        recordStepInfo("应用版本:\t" + this._appVersion);
        recordStepInfo("用户id:\t" + this._UID);
        recordStepInfo("机器类型:\t" + Build.MANUFACTURER + ":" + Build.BRAND + ":" + Build.MODEL);
        recordStepInfo("系统版本:\t" + VERSION.RELEASE);
        if (this._telManager != null && TextUtils.isEmpty(this._deviceID)) {
            this._deviceID = this._telManager.getDeviceId();
        }
        recordStepInfo("机器ID:\t" + this._deviceID);
        if (TextUtils.isEmpty(this._carrierName)) {
            this._carrierName = LDNetUtil.getMobileOperator(this._context);
        }
        recordStepInfo("运营商:\t" + this._carrierName);
        if (this._telManager != null && TextUtils.isEmpty(this._ISOCountryCode)) {
            this._ISOCountryCode = this._telManager.getNetworkCountryIso();
        }
        recordStepInfo("ISOCountryCode:\t" + this._ISOCountryCode);
        if (this._telManager != null && TextUtils.isEmpty(this._MobileCountryCode)) {
            String tmp = this._telManager.getNetworkOperator();
            this._MobileCountryCode = tmp.substring(0, 3);
            if (tmp.length() >= 5) {
                this._MobileNetCode = tmp.substring(3, 5);
            }
        }
        recordStepInfo("MobileCountryCode:\t" + this._MobileCountryCode);
        recordStepInfo("MobileNetworkCode:\t" + this._MobileNetCode);
    }

    private void recordLocalNetEnvironmentInfo() {
        recordStepInfo("\n诊断域名 " + this._dormain + "...");
        if (LDNetUtil.isNetworkConnected(this._context).booleanValue()) {
            this._isNetConnected = true;
            recordStepInfo("当前是否联网:\t已联网");
        } else {
            this._isNetConnected = false;
            recordStepInfo("当前是否联网:\t未联网");
        }
        this._netType = LDNetUtil.getNetWorkType(this._context);
        recordStepInfo("当前联网类型:\t" + this._netType);
        if (this._isNetConnected) {
            if (LDNetUtil.NETWORKTYPE_WIFI.equals(this._netType)) {
                this._localIp = LDNetUtil.getLocalIpByWifi(this._context);
                this._gateWay = LDNetUtil.pingGateWayInWifi(this._context);
            } else {
                this._localIp = LDNetUtil.getLocalIpBy3G();
            }
            recordStepInfo("本地IP:\t" + this._localIp);
        } else {
            recordStepInfo("本地IP:\t127.0.0.1");
        }
        if (this._gateWay != null) {
            recordStepInfo("本地网关:\t" + this._gateWay);
        }
        if (this._isNetConnected) {
            this._dns1 = LDNetUtil.getLocalDns("dns1");
            this._dns2 = LDNetUtil.getLocalDns("dns2");
            recordStepInfo("本地DNS:\t" + this._dns1 + MiPushClient.ACCEPT_TIME_SEPARATOR + this._dns2);
        } else {
            recordStepInfo("本地DNS:\t0.0.0.0,0.0.0.0");
        }
        if (this._isNetConnected) {
            recordStepInfo("远端域名:\t" + this._dormain);
            this._isDomainParseOk = parseDomain(this._dormain);
        }
    }

    private boolean parseDomain(String _dormain) {
        String timeShow;
        String ipString = "";
        Map<String, Object> map = LDNetUtil.getDomainIp(_dormain);
        String useTime = (String) map.get("useTime");
        this._remoteInet = (InetAddress[]) map.get("remoteInet");
        if (Integer.parseInt(useTime) > 5000) {
            timeShow = " (" + (Integer.parseInt(useTime) / 1000) + "s)";
        } else {
            timeShow = " (" + useTime + "ms)";
        }
        int len;
        int i;
        if (this._remoteInet != null) {
            len = this._remoteInet.length;
            for (i = 0; i < len; i++) {
                this._remoteIpList.add(this._remoteInet[i].getHostAddress());
                ipString = ipString + this._remoteInet[i].getHostAddress() + MiPushClient.ACCEPT_TIME_SEPARATOR;
            }
            recordStepInfo("DNS解析结果:\t" + ipString.substring(0, ipString.length() - 1) + timeShow);
            return true;
        } else if (Integer.parseInt(useTime) > 10000) {
            map = LDNetUtil.getDomainIp(_dormain);
            useTime = (String) map.get("useTime");
            this._remoteInet = (InetAddress[]) map.get("remoteInet");
            if (Integer.parseInt(useTime) > 5000) {
                timeShow = " (" + (Integer.parseInt(useTime) / 1000) + "s)";
            } else {
                timeShow = " (" + useTime + "ms)";
            }
            if (this._remoteInet != null) {
                len = this._remoteInet.length;
                for (i = 0; i < len; i++) {
                    this._remoteIpList.add(this._remoteInet[i].getHostAddress());
                    ipString = ipString + this._remoteInet[i].getHostAddress() + MiPushClient.ACCEPT_TIME_SEPARATOR;
                }
                recordStepInfo("DNS解析结果:\t" + ipString.substring(0, ipString.length() - 1) + timeShow);
                return true;
            }
            recordStepInfo("DNS解析结果:\t解析失败" + timeShow);
            return false;
        } else {
            recordStepInfo("DNS解析结果:\t解析失败" + timeShow);
            return false;
        }
    }

    /* JADX WARNING: Missing block: B:26:?, code:
            return null;
     */
    private java.lang.String requestOperatorInfo() {
        /*
        r9 = this;
        r4 = 0;
        r7 = "";
        r2 = 0;
        r1 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        r1.<init>(r7);	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        r8 = r1.openConnection();	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        r0 = r8;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        r2 = r0;
        r8 = "GET";
        r2.setRequestMethod(r8);	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        r8 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r2.setConnectTimeout(r8);	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        r2.connect();	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        r6 = r2.getResponseCode();	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        r8 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r6 != r8) goto L_0x0035;
    L_0x0028:
        r8 = r2.getInputStream();	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        r4 = com.netease.LDNetDiagnoUtils.LDNetUtil.getStringFromStream(r8);	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
        if (r2 == 0) goto L_0x0035;
    L_0x0032:
        r2.disconnect();	 Catch:{ MalformedURLException -> 0x003c, IOException -> 0x0047 }
    L_0x0035:
        if (r2 == 0) goto L_0x003a;
    L_0x0037:
        r2.disconnect();
    L_0x003a:
        r5 = r4;
    L_0x003b:
        return r5;
    L_0x003c:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ all -> 0x0051 }
        if (r2 == 0) goto L_0x0045;
    L_0x0042:
        r2.disconnect();
    L_0x0045:
        r5 = r4;
        goto L_0x003b;
    L_0x0047:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ all -> 0x0051 }
        if (r2 == 0) goto L_0x0045;
    L_0x004d:
        r2.disconnect();
        goto L_0x0045;
    L_0x0051:
        r8 = move-exception;
        if (r2 == 0) goto L_0x0057;
    L_0x0054:
        r2.disconnect();
    L_0x0057:
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.LDNetDiagnoService.LDNetDiagnoService.requestOperatorInfo():java.lang.String");
    }

    public void OnNetPingFinished(String log) {
        recordStepInfo(log);
    }

    protected ThreadPoolExecutor getThreadPoolExecutor() {
        return sExecutor;
    }
}
