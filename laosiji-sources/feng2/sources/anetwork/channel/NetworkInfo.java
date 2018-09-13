package anetwork.channel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.util.Log;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.entity.ConnType;
import anet.channel.request.ByteArrayEntry;
import anet.channel.request.Request;
import anet.channel.request.Request.Builder;
import anet.channel.session.a;
import anet.channel.session.b;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.HttpConstant;
import anetwork.channel.entity.RequestImpl;
import anetwork.channel.http.HttpNetwork;
import anetwork.channel.util.RequestConstant;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.facebook.common.util.UriUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.android.spdy.RequestPriority;
import org.android.spdy.SessionCb;
import org.android.spdy.SessionInfo;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyRequest;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

/* compiled from: Taobao */
public class NetworkInfo {
    private static final String DESC_SEPARATOR = "==============================\n";
    private static final int NET_CONNECTED = 2;
    private static final int NET_UNAUTHORIZED = 1;
    private static final int NET_UNCONNECTED = 0;
    public static final String RESULT_BACKGROUND = "BACKGROUND ACTIVITY";
    public static final String RESULT_UNAUTHORIZED = "NETWORK_UNAUTHROIZED";
    public static final String RESULT_UNCONNECTED = "NETWORK_UNCONNECTED";
    private static final String TAG = NetworkInfo.class.getSimpleName();
    private static final int THREAD_NUMS = 4;
    private static final String URL_204 = "http://client.aliyun.com/";
    private static final String URL_ASERVER_CENTER = "http://acs.m.taobao.com/gw/mtop.wdetail.getitemdetail/";
    private static final String URL_ASERVER_UNIT = "http://unitacs.m.taobao.com/gw/mtop.wdetail.getitemdetail/";
    private static final String URL_ASERVER_UNSZ = "http://unszacs.m.taobao.com/gw/mtop.wdetail.getitemdetail/";
    private static final String URL_ASSET_CDN = "http://g.alicdn.com/tbc/??search-suggest/1.4.6/mods/storage-min.js";
    private static final String URL_BAIDU = "http://www.baidu.com";
    private static final String URL_DETECT = "http://140.205.130.1/api/cdnDetect?method=createDetect";
    private static final String URL_GW_CDN = "http://gw.alicdn.com/bao/uploaded/i2/12071029418847231/T13I2HFk8aXXXXXXXX_!!0-item_pic.jpg_170x170.jpg";
    private static final String URL_H5 = "http://h5.m.taobao.com/app/category/www/man/index.html";
    private static final String URL_HWS = "http://hws.m.taobao.com/cache/desc/5.0?id=42860783596&type=1&f=TB1FFmAJFXXXXbvXFXX8qtpFXlX";
    private static final String URL_MTOP_WJAS = "http://api.m.taobao.com/gw/mtop.common.getTimeStamp/*";
    private static final String URL_NETWORK_HEALTH = "http://api.m.taobao.com/status.taobao";
    private static final String URL_TAOBAO = "http://m.taobao.com";
    private static final String URL_TENCENT = "http://www.tencent.com";
    private static NetworkInfo mInstance = new NetworkInfo();
    private Context context;
    private NetworkInfoListener listener;
    private ExecutorService mService = new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(60));
    private ConcurrentHashMap<Integer, String> resultMap;

    /* compiled from: Taobao */
    public interface NetworkInfoListener {
        void onFinished(String str);
    }

    /* compiled from: Taobao */
    private class NetworkTask implements Callable<String> {
        public static final int TOTAL_COUNT = 21;
        public static final int TYPE_APN = 2;
        public static final int TYPE_CHECK_ASERVER_CENTER = 18;
        public static final int TYPE_CHECK_ASERVER_UNIT = 19;
        public static final int TYPE_CHECK_ASERVER_UNSZ = 20;
        public static final int TYPE_CHECK_ASSET_CDN = 15;
        public static final int TYPE_CHECK_H5 = 17;
        public static final int TYPE_CHECK_HWS = 16;
        public static final int TYPE_CHECK_IMG_CDN = 14;
        public static final int TYPE_CHECK_WJAS = 13;
        public static final int TYPE_HTTP_ASSET_CDN = 8;
        public static final int TYPE_HTTP_BAIDU = 11;
        public static final int TYPE_HTTP_GW_CDN = 7;
        public static final int TYPE_HTTP_MTOP = 6;
        public static final int TYPE_HTTP_POST = 12;
        public static final int TYPE_HTTP_TAOBAO = 9;
        public static final int TYPE_HTTP_TENCENT = 10;
        public static final int TYPE_IP_LDNS = 1;
        public static final int TYPE_NETWORK_TYPE = 0;
        public static final int TYPE_PROXY_BYPASS = 5;
        public static final int TYPE_PROXY_HOST = 3;
        public static final int TYPE_PROXY_PORT = 4;
        private Context context;
        private CountDownLatch latch;
        private ConcurrentHashMap<Integer, String> map;
        private int type;

        public NetworkTask(Context context, int i, ConcurrentHashMap<Integer, String> concurrentHashMap, CountDownLatch countDownLatch) {
            this.context = context;
            this.type = i;
            this.map = concurrentHashMap;
            this.latch = countDownLatch;
        }

        public String call() throws Exception {
            return execute();
        }

        private String execute() {
            String str = null;
            NetworkInfo access$900 = NetworkInfo.getInstance();
            switch (this.type) {
                case 0:
                    str = access$900.getConnectionType(this.context);
                    break;
                case 1:
                    str = access$900.getIpAndLdns(this.context);
                    break;
                case 2:
                    str = access$900.getCurrentApn(this.context);
                    break;
                case 3:
                    str = NetworkInfo.this.getStringOrEmpty(System.getProperty("http.proxyHost"));
                    break;
                case 4:
                    str = NetworkInfo.this.getStringOrEmpty(System.getProperty("http.proxyPort"));
                    break;
                case 5:
                    str = NetworkInfo.this.getStringOrEmpty(System.getProperty("http.nonProxyHosts"));
                    break;
                case 6:
                    str = NetworkInfo.this.isUrlReachableByHttpNetwork(this.context, NetworkInfo.URL_MTOP_WJAS) + "";
                    break;
                case 7:
                    str = NetworkInfo.this.isUrlReachableByHttpNetwork(this.context, NetworkInfo.URL_GW_CDN) + "";
                    break;
                case 8:
                    str = NetworkInfo.this.isUrlReachableByHttpNetwork(this.context, NetworkInfo.URL_ASSET_CDN) + "";
                    break;
                case 9:
                    str = NetworkInfo.this.isUrlReachableByHttpNetwork(this.context, NetworkInfo.URL_TAOBAO) + "";
                    break;
                case 10:
                    str = NetworkInfo.this.isUrlReachableByHttpNetwork(this.context, NetworkInfo.URL_TENCENT) + "";
                    break;
                case 11:
                    str = NetworkInfo.this.isUrlReachableByHttpNetwork(this.context, NetworkInfo.URL_BAIDU) + "";
                    break;
                case 12:
                    str = access$900.isConnectedViaPost(this.context) + "";
                    break;
                case 13:
                    str = access$900.checkAMDCPolices(this.context, NetworkInfo.URL_MTOP_WJAS);
                    break;
                case 14:
                    str = access$900.checkAMDCPolices(this.context, NetworkInfo.URL_GW_CDN);
                    break;
                case 15:
                    str = access$900.checkAMDCPolices(this.context, NetworkInfo.URL_ASSET_CDN);
                    break;
                case 16:
                    str = access$900.checkAMDCPolices(this.context, NetworkInfo.URL_HWS);
                    break;
                case 17:
                    str = access$900.checkAMDCPolices(this.context, NetworkInfo.URL_H5);
                    break;
                case 18:
                    str = access$900.checkAMDCPolices(this.context, NetworkInfo.URL_ASERVER_CENTER);
                    break;
                case 19:
                    str = access$900.checkAMDCPolices(this.context, NetworkInfo.URL_ASERVER_UNIT);
                    break;
                case 20:
                    str = access$900.checkAMDCPolices(this.context, NetworkInfo.URL_ASERVER_UNSZ);
                    break;
            }
            this.map.put(Integer.valueOf(this.type), str);
            this.latch.countDown();
            return str;
        }
    }

    /* compiled from: Taobao */
    private class SessionCallback implements SessionCb {
        private String key;
        private HashMap<String, String[]> map;

        /* synthetic */ SessionCallback(NetworkInfo networkInfo, HashMap hashMap, String str, AnonymousClass1 anonymousClass1) {
            this(hashMap, str);
        }

        private SessionCallback(HashMap<String, String[]> hashMap, String str) {
            this.key = str;
            this.map = hashMap;
        }

        public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
            ((String[]) this.map.get(this.key))[0] = RequestConstant.TURE;
            ((String[]) this.map.get(this.key))[1] = "0";
        }

        public void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj) {
        }

        public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
        }

        public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        }

        public void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
            ((String[]) this.map.get(this.key))[1] = i + "";
        }

        public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
            Log.i(NetworkInfo.TAG, "spdy session close: " + spdySession.getDomain());
        }

        public void bioPingRecvCallback(SpdySession spdySession, int i) {
        }

        public byte[] getSSLMeta(SpdySession spdySession) {
            return new byte[0];
        }

        public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
            return 0;
        }
    }

    /* compiled from: Taobao */
    private class SpdyRequestCallback extends a {
        private String key;
        private CountDownLatch latch;
        private HashMap<String, String[]> map;

        public SpdyRequestCallback(HashMap<String, String[]> hashMap, String str, CountDownLatch countDownLatch) {
            this.key = str;
            this.latch = countDownLatch;
            this.map = hashMap;
        }

        public void spdyStreamCloseCallback(SpdySession spdySession, long j, int i, Object obj, SuperviseData superviseData) {
            this.latch.countDown();
        }

        public void spdyOnStreamResponse(SpdySession spdySession, long j, Map<String, List<String>> map, Object obj) {
            ((String[]) this.map.get(this.key))[2] = RequestConstant.TURE;
            ((String[]) this.map.get(this.key))[3] = anet.channel.util.a.b(map, HttpConstant.STATUS) + "";
            spdySession.closeSession();
        }
    }

    /* compiled from: Taobao */
    static class WaitThread extends Thread {
        private CountDownLatch latch;
        private NetworkInfoListener listener;

        public WaitThread(NetworkInfoListener networkInfoListener, CountDownLatch countDownLatch) {
            this.listener = networkInfoListener;
            this.latch = countDownLatch;
        }

        public void run() {
            try {
                this.listener.onFinished(NetworkInfo.getInstance().waitResult(this.latch));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private NetworkInfo() {
    }

    public static void getNetworkInfo(Context context, NetworkInfoListener networkInfoListener) {
        try {
            NetworkInfo instance = getInstance();
            instance.context = context;
            instance.listener = networkInfoListener;
            new Thread() {
                public void run() {
                    int access$100 = NetworkInfo.this.checkNetworkState(NetworkInfo.this.context);
                    if ((!GlobalAppRuntimeInfo.isAppBackground() ? 1 : null) == null) {
                        NetworkInfo.onFinished(NetworkInfo.this.listener, NetworkInfo.RESULT_BACKGROUND);
                    } else if (access$100 == 2) {
                        NetworkInfo.this.resultMap = new ConcurrentHashMap();
                        CountDownLatch countDownLatch = new CountDownLatch(21);
                        NetworkInfo.this.submitAllTasks(countDownLatch);
                        new WaitThread(NetworkInfo.this.listener, countDownLatch).start();
                    } else if (access$100 == 1) {
                        NetworkInfo.onFinished(NetworkInfo.this.listener, NetworkInfo.RESULT_UNAUTHORIZED);
                    } else if (access$100 == 0) {
                        NetworkInfo.onFinished(NetworkInfo.this.listener, NetworkInfo.RESULT_UNCONNECTED);
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void onFinished(final NetworkInfoListener networkInfoListener, final String str) {
        new Thread() {
            public void run() {
                networkInfoListener.onFinished(str);
            }
        }.start();
    }

    public static String getNetworkInfo(Context context) {
        try {
            NetworkInfo instance = getInstance();
            instance.context = context;
            int checkNetworkState = instance.checkNetworkState(context);
            if ((!GlobalAppRuntimeInfo.isAppBackground() ? 1 : null) == null) {
                return RESULT_BACKGROUND;
            }
            if (checkNetworkState == 2) {
                instance.resultMap = new ConcurrentHashMap();
                CountDownLatch countDownLatch = new CountDownLatch(21);
                instance.submitAllTasks(countDownLatch);
                return instance.waitResult(countDownLatch);
            } else if (checkNetworkState == 1) {
                return RESULT_UNAUTHORIZED;
            } else {
                if (checkNetworkState == 0) {
                    return RESULT_UNCONNECTED;
                }
                return "Exception";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitAllTasks(CountDownLatch countDownLatch) {
        for (int i = 0; i < 21; i++) {
            this.mService.submit(new NetworkTask(this.context, i, this.resultMap, countDownLatch));
        }
    }

    private String waitResult(CountDownLatch countDownLatch) {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= 21) {
                return stringBuilder.toString();
            }
            stringBuilder.append(getDescByIndex(i2)).append(" ").append((String) this.resultMap.get(Integer.valueOf(i2))).append("\n");
            i = i2 + 1;
        }
    }

    private String getDescByIndex(int i) {
        switch (i) {
            case 0:
                return "Network Type:";
            case 1:
                return "Mobile IP:";
            case 2:
                return "APN:";
            case 3:
                return "Proxy Host:";
            case 4:
                return "Proxy Port:";
            case 5:
                return "Proxy Bypass:";
            case 6:
                return "==============================\nHttpNetwork:\napi.m.taobao.com:";
            case 7:
                return "gw.alicdn.com:";
            case 8:
                return "g.alicdn.com:";
            case 9:
                return "www.taobao.com:";
            case 10:
                return "www.tencent.com:";
            case 11:
                return "www.baidu.com:";
            case 12:
                return "POST:";
            case 13:
                return "==============================\nAMDC:\nhttp://api.m.taobao.com/gw/mtop.common.getTimeStamp/*\n";
            case 14:
                return "http://gw.alicdn.com/bao/uploaded/i2/12071029418847231/T13I2HFk8aXXXXXXXX_!!0-item_pic.jpg_170x170.jpg\n";
            case 15:
                return "http://g.alicdn.com/tbc/??search-suggest/1.4.6/mods/storage-min.js\n";
            case 16:
                return "http://hws.m.taobao.com/cache/desc/5.0?id=42860783596&type=1&f=TB1FFmAJFXXXXbvXFXX8qtpFXlX\n";
            case 17:
                return "http://h5.m.taobao.com/app/category/www/man/index.html\n";
            case 18:
                return "http://acs.m.taobao.com/gw/mtop.wdetail.getitemdetail/\n";
            case 19:
                return "http://unitacs.m.taobao.com/gw/mtop.wdetail.getitemdetail/\n";
            case 20:
                return "http://unszacs.m.taobao.com/gw/mtop.wdetail.getitemdetail/\n";
            default:
                return "";
        }
    }

    private static NetworkInfo getInstance() {
        return mInstance;
    }

    private int isNetworkConnected(Context context) {
        for (android.net.NetworkInfo state : ((ConnectivityManager) context.getSystemService("connectivity")).getAllNetworkInfo()) {
            if (state.getState() == State.CONNECTED) {
                try {
                    HttpURLConnection uRLConnection = getURLConnection(URL_204);
                    if (!("".equals(readContent(uRLConnection)) && uRLConnection.getResponseCode() == 204)) {
                        return 1;
                    }
                } catch (Exception e) {
                }
                return 2;
            }
        }
        return 0;
    }

    private String getConnectionType(Context context) {
        if (isNetworkConnected(context) == 2) {
            for (android.net.NetworkInfo networkInfo : ((ConnectivityManager) context.getSystemService("connectivity")).getAllNetworkInfo()) {
                if (networkInfo.getState() == State.CONNECTED) {
                    return networkInfo.getTypeName();
                }
            }
        }
        return "";
    }

    private String getCurrentApn(Context context) {
        android.net.NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0);
        return networkInfo.getExtraInfo() == null ? "Null" : networkInfo.getExtraInfo();
    }

    private String getIpAndLdns(Context context) {
        String str = "";
        try {
            JSONObject jSONObject = new JSONObject(readContent(getURLConnection(new JSONObject(readContent(getURLConnection(URL_DETECT))).optString(UriUtil.LOCAL_CONTENT_SCHEME, "")))).getJSONObject(UriUtil.LOCAL_CONTENT_SCHEME);
            StringBuilder stringBuilder = new StringBuilder(32);
            stringBuilder.append(jSONObject.optString("localIp")).append(10).append("Local DNS: ").append(jSONObject.optString("ldns"));
            return stringBuilder.toString();
        } catch (Exception e) {
            return str;
        }
    }

    private String getStringOrEmpty(String str) {
        return str == null ? "" : str;
    }

    private String readContent(HttpURLConnection httpURLConnection) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Request.DEFAULT_CHARSET));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return stringBuilder.toString();
                }
                stringBuilder.append(readLine).append("\n");
            }
        } catch (Exception e) {
            return "";
        }
    }

    private HttpURLConnection getURLConnection(String str) {
        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.connect();
            } catch (MalformedURLException e) {
                Log.e(TAG, "URL Error");
                return httpURLConnection;
            } catch (SocketTimeoutException e2) {
                Log.e(TAG, "Socket Time Out Exception");
                return httpURLConnection;
            } catch (ConnectTimeoutException e3) {
                Log.e(TAG, "Connect Time Out Exception");
                return httpURLConnection;
            } catch (IOException e4) {
                Log.e(TAG, "Open Connection Exception");
                return httpURLConnection;
            }
        } catch (MalformedURLException e5) {
            httpURLConnection = null;
            Log.e(TAG, "URL Error");
            return httpURLConnection;
        } catch (SocketTimeoutException e6) {
            httpURLConnection = null;
            Log.e(TAG, "Socket Time Out Exception");
            return httpURLConnection;
        } catch (ConnectTimeoutException e7) {
            httpURLConnection = null;
            Log.e(TAG, "Connect Time Out Exception");
            return httpURLConnection;
        } catch (IOException e8) {
            httpURLConnection = null;
            Log.e(TAG, "Open Connection Exception");
            return httpURLConnection;
        }
        return httpURLConnection;
    }

    private int isUrlReachableByHttpNetwork(Context context, String str) {
        if (isNetworkConnected(context) != 2) {
            return -1;
        }
        Request requestImpl = new RequestImpl(str);
        requestImpl.setRetryTime(1);
        requestImpl.setConnectTimeout(10000);
        requestImpl.setReadTimeout(10000);
        return new HttpNetwork(context).syncSend(requestImpl, null).getStatusCode();
    }

    private int checkNetworkState(final Context context) {
        try {
            return ((Integer) this.mService.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    return Integer.valueOf(NetworkInfo.this.isNetworkConnected(context));
                }
            }).get()).intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        } catch (ExecutionException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public String checkAMDCPolices(Context context, String str) {
        try {
            URL url = new URL(str);
            String host = url.getHost();
            List<IConnStrategy> connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(host);
            if (connStrategyListByHost == null || connStrategyListByHost.isEmpty()) {
                return "";
            }
            Collections.shuffle(connStrategyListByHost);
            while (connStrategyListByHost.size() > 4) {
                connStrategyListByHost.remove(0);
            }
            CountDownLatch countDownLatch = new CountDownLatch(connStrategyListByHost.size());
            HashMap hashMap = new HashMap();
            for (IConnStrategy iConnStrategy : connStrategyListByHost) {
                String replace;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Strategy: ").append(iConnStrategy.getIp()).append(":").append(iConnStrategy.getPort()).append(" - ").append(iConnStrategy.getProtocol());
                String stringBuilder2 = stringBuilder.toString();
                hashMap.put(stringBuilder2, new String[]{RequestConstant.FALSE, "null", RequestConstant.FALSE, "null"});
                ConnType valueOf = ConnType.valueOf(iConnStrategy.getProtocol());
                Builder builder = new Builder();
                if (valueOf.isSSL()) {
                    replace = str.replace("http:", "https:");
                } else {
                    replace = str;
                }
                Request build = builder.setUrl(replace).build();
                build.setDnsOptimize(iConnStrategy.getIp(), iConnStrategy.getPort());
                if (valueOf.equals(ConnType.HTTP) || valueOf.equals(ConnType.HTTPS)) {
                    try {
                        int i = b.a(build, null).a;
                        if (i > 0) {
                            ((String[]) hashMap.get(stringBuilder2))[0] = RequestConstant.TURE;
                            ((String[]) hashMap.get(stringBuilder2))[2] = RequestConstant.TURE;
                        } else {
                            ((String[]) hashMap.get(stringBuilder2))[0] = RequestConstant.FALSE;
                            ((String[]) hashMap.get(stringBuilder2))[2] = RequestConstant.FALSE;
                        }
                        ((String[]) hashMap.get(stringBuilder2))[1] = i + "";
                        ((String[]) hashMap.get(stringBuilder2))[3] = i + "";
                    } finally {
                        countDownLatch.countDown();
                    }
                } else {
                    SpdyAgent instance = SpdyAgent.getInstance(context, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
                    SessionInfo sessionInfo = new SessionInfo(iConnStrategy.getIp(), iConnStrategy.getPort(), host, "", 0, String.format("%s_%d", new Object[]{"", Long.valueOf(System.currentTimeMillis())}), new SessionCallback(this, hashMap, stringBuilder2, null), valueOf.getTnetConType());
                    sessionInfo.setConnectionTimeoutMs(10000);
                    sessionInfo.setPubKeySeqNum(valueOf.getTnetPublicKey(false));
                    SpdySession createSession = instance.createSession(sessionInfo);
                    SpdyRequest spdyRequest = new SpdyRequest(build.getUrl(), "GET", RequestPriority.DEFAULT_PRIORITY, 60000, 40000);
                    spdyRequest.addHeader(":host", url.getHost());
                    createSession.submitRequest(spdyRequest, null, createSession, new SpdyRequestCallback(hashMap, stringBuilder2, countDownLatch));
                }
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Set<Entry> entrySet = hashMap.entrySet();
            StringBuilder stringBuilder3 = new StringBuilder();
            for (Entry entry : entrySet) {
                stringBuilder3.append((String) entry.getKey()).append("\n").append("\tConnection:").append(((String[]) entry.getValue())[0]).append(", code:").append(((String[]) entry.getValue())[1]).append(", Request:").append(((String[]) entry.getValue())[2]).append(", code: ").append(((String[]) entry.getValue())[3]).append("\n");
            }
            return stringBuilder3.toString();
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private int isConnectedViaPost(Context context) {
        Request requestImpl = new RequestImpl(URL_NETWORK_HEALTH);
        requestImpl.setMethod("POST");
        requestImpl.setRetryTime(1);
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < m_AppUI.MSG_APP_DATA_OK) {
            stringBuilder.append(UriUtil.DATA_SCHEME);
        }
        requestImpl.setBodyEntry(new ByteArrayEntry(stringBuilder.toString().getBytes()));
        return new HttpNetwork(context).syncSend(requestImpl, null).getStatusCode();
    }
}
