package anet.channel.session;

import android.os.Build.VERSION;
import android.util.Pair;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.RequestCb;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.request.Request;
import anet.channel.request.Request.Method;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.c;
import anet.channel.util.d;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CancellationException;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;

/* compiled from: Taobao */
public class b {

    /* compiled from: Taobao */
    public static class a {
        public int a;
        public byte[] b;
        public Map<String, List<String>> c;
    }

    private b() {
    }

    public static a a(Request request, RequestCb requestCb) {
        Throwable e;
        Throwable th;
        HttpURLConnection httpURLConnection;
        a aVar = new a();
        if (request == null || request.getUrl() == null) {
            if (requestCb != null) {
                requestCb.onFinish(-102, ErrorConstant.getErrMsg(-102), new RequestStatistic(null, null));
            }
            return aVar;
        }
        request.rs.sendBeforeTime = System.currentTimeMillis() - request.rs.start;
        HttpURLConnection httpURLConnection2 = null;
        while (NetworkStatusHelper.g()) {
            try {
                if (ALog.isPrintLog(2)) {
                    ALog.i("awcn.HttpConnector", "", request.getSeq(), "request URL", request.getUrl().toString());
                    ALog.i("awcn.HttpConnector", "", request.getSeq(), "request Method", request.getMethod());
                    ALog.i("awcn.HttpConnector", "", request.getSeq(), "request headers", request.getHeaders());
                }
                httpURLConnection2 = a(request);
                if (httpURLConnection2 != null) {
                    httpURLConnection2.connect();
                    a(httpURLConnection2, request);
                    aVar.a = httpURLConnection2.getResponseCode();
                    aVar.c = anet.channel.util.a.a(httpURLConnection2.getHeaderFields());
                    ALog.i("awcn.HttpConnector", "", request.getSeq(), "response code", Integer.valueOf(aVar.a));
                    ALog.i("awcn.HttpConnector", "", request.getSeq(), "response headers", aVar.c);
                    if (anet.channel.util.a.a(request, aVar.a)) {
                        String b = anet.channel.util.a.b(aVar.c, HttpConstant.LOCATION);
                        if (b != null) {
                            c a = c.a(b);
                            if (a != null) {
                                request = request.newBuilder().setUrl(a).setRedirectTimes(request.getRedirectTimes() + 1).build();
                                if (httpURLConnection2 != null) {
                                    try {
                                        httpURLConnection2.disconnect();
                                    } catch (Throwable e2) {
                                        ALog.e("awcn.HttpConnector", "http disconnect", null, e2, new Object[0]);
                                    }
                                }
                            } else {
                                ALog.e("awcn.HttpConnector", "redirect url is invalid!", request.getSeq(), "redirect url", b);
                            }
                        }
                    }
                    if (request.getMethod() != Method.HEAD && aVar.a != HttpConstant.SC_NOT_MODIFIED && aVar.a != 204 && (aVar.a < 100 || aVar.a >= 200)) {
                        if (!(anet.channel.util.a.b(aVar.c) || requestCb == null)) {
                            requestCb.onResponseCode(aVar.a, aVar.c);
                        }
                        a(httpURLConnection2, request, aVar, requestCb);
                    } else if (requestCb != null) {
                        requestCb.onResponseCode(aVar.a, aVar.c);
                    }
                    request.rs.oneWayTime = System.currentTimeMillis() - request.rs.start;
                    request.rs.statusCode = aVar.a;
                    request.rs.ret = true;
                    if (requestCb != null) {
                        requestCb.onFinish(aVar.a, HttpConstant.SUCCESS, request.rs);
                    }
                } else {
                    a(request, aVar, requestCb, ErrorConstant.ERROR_OPEN_CONNECTION_NULL, null);
                }
                if (httpURLConnection2 != null) {
                    try {
                        httpURLConnection2.disconnect();
                    } catch (Throwable e3) {
                        ALog.e("awcn.HttpConnector", "http disconnect", null, e3, new Object[0]);
                    }
                }
            } catch (Throwable e22) {
                th = e22;
                httpURLConnection = httpURLConnection2;
                a(request, aVar, requestCb, ErrorConstant.ERROR_UNKNOWN_HOST_EXCEPTION, th);
                ALog.e("awcn.HttpConnector", "Unknown Host Exception", request.getSeq(), "host", request.getHost(), e3);
                NetworkStatusHelper.k();
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e32) {
                        ALog.e("awcn.HttpConnector", "http disconnect", null, e32, new Object[0]);
                    }
                }
            } catch (Throwable e222) {
                th = e222;
                httpURLConnection = httpURLConnection2;
                e32 = th;
                a(request, aVar, requestCb, ErrorConstant.ERROR_SOCKET_TIME_OUT, e32);
                ALog.e("awcn.HttpConnector", "HTTP Socket Timeout", request.getSeq(), e32, new Object[0]);
                NetworkStatusHelper.k();
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e322) {
                        ALog.e("awcn.HttpConnector", "http disconnect", null, e322, new Object[0]);
                    }
                }
            } catch (Throwable e2222) {
                th = e2222;
                httpURLConnection = httpURLConnection2;
                e322 = th;
                a(request, aVar, requestCb, ErrorConstant.ERROR_CONN_TIME_OUT, e322);
                ALog.e("awcn.HttpConnector", "HTTP Connect Timeout", request.getSeq(), e322, new Object[0]);
                NetworkStatusHelper.k();
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e3222) {
                        ALog.e("awcn.HttpConnector", "http disconnect", null, e3222, new Object[0]);
                    }
                }
            } catch (Throwable e22222) {
                th = e22222;
                httpURLConnection = httpURLConnection2;
                e3222 = th;
                a(request, aVar, requestCb, ErrorConstant.ERROR_CONNECT_EXCEPTION, e3222);
                ALog.e("awcn.HttpConnector", "HTTP Connect Exception", request.getSeq(), e3222, new Object[0]);
                NetworkStatusHelper.k();
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e32222) {
                        ALog.e("awcn.HttpConnector", "http disconnect", null, e32222, new Object[0]);
                    }
                }
            } catch (Throwable e222222) {
                th = e222222;
                httpURLConnection = httpURLConnection2;
                e32222 = th;
                a.a.b(request.getHost());
                a(request, aVar, requestCb, ErrorConstant.ERROR_SSL_ERROR, e32222);
                ALog.e("awcn.HttpConnector", "HTTP Connect SSLHandshakeException", request.getSeq(), "host", request.getHost(), e32222);
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e322222) {
                        ALog.e("awcn.HttpConnector", "http disconnect", null, e322222, new Object[0]);
                    }
                }
            } catch (Throwable e2222222) {
                th = e2222222;
                httpURLConnection = httpURLConnection2;
                e322222 = th;
                a.a.b(request.getHost());
                a(request, aVar, requestCb, ErrorConstant.ERROR_SSL_ERROR, e322222);
                ALog.e("awcn.HttpConnector", "connect SSLException", request.getSeq(), "host", request.getHost(), e322222);
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e3222222) {
                        ALog.e("awcn.HttpConnector", "http disconnect", null, e3222222, new Object[0]);
                    }
                }
            } catch (Throwable e22222222) {
                th = e22222222;
                httpURLConnection = httpURLConnection2;
                e3222222 = th;
                a(request, aVar, requestCb, ErrorConstant.ERROR_REQUEST_CANCEL, e3222222);
                ALog.e("awcn.HttpConnector", "HTTP Request Cancel", request.getSeq(), e3222222, new Object[0]);
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e32222222) {
                        ALog.e("awcn.HttpConnector", "http disconnect", null, e32222222, new Object[0]);
                    }
                }
            } catch (Throwable e222222222) {
                th = e222222222;
                httpURLConnection = httpURLConnection2;
                e32222222 = th;
                String message = e32222222.getMessage();
                if (message == null || !message.contains("not verified")) {
                    a(request, aVar, requestCb, -101, e32222222);
                } else {
                    a.a.b(request.getHost());
                    a(request, aVar, requestCb, ErrorConstant.ERROR_HOST_NOT_VERIFY_ERROR, e32222222);
                }
                ALog.e("awcn.HttpConnector", "HTTP Connect Exception", request.getSeq(), e32222222, new Object[0]);
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e322222222) {
                        ALog.e("awcn.HttpConnector", "http disconnect", null, e322222222, new Object[0]);
                    }
                }
            } catch (Throwable th2) {
                e322222222 = th2;
            }
            return aVar;
        }
        a(request, aVar, requestCb, ErrorConstant.ERROR_NO_NETWORK, null);
        return aVar;
        throw e322222222;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Throwable th3) {
                ALog.e("awcn.HttpConnector", "http disconnect", null, th3, new Object[0]);
            }
        }
        throw e322222222;
    }

    private static void a(Request request, a aVar, RequestCb requestCb, int i, Throwable th) {
        String errMsg = ErrorConstant.getErrMsg(i);
        ALog.e("awcn.HttpConnector", "onException", request.getSeq(), "errorCode", Integer.valueOf(i), "errMsg", errMsg, "url", request.getUrlString(), "host", request.getHost());
        if (aVar != null) {
            aVar.a = i;
        }
        request.rs.statusCode = i;
        request.rs.oneWayTime = System.currentTimeMillis() - request.rs.start;
        if (requestCb != null) {
            requestCb.onFinish(i, errMsg, request.rs);
        }
        if (i != ErrorConstant.ERROR_REQUEST_CANCEL) {
            AppMonitor.getInstance().commitStat(new ExceptionStatistic(i, errMsg, request.rs, th));
        }
    }

    private static HttpURLConnection a(Request request) throws IOException {
        Proxy proxy;
        d dVar;
        Pair j = NetworkStatusHelper.j();
        if (j != null) {
            proxy = new Proxy(Type.HTTP, new InetSocketAddress((String) j.first, ((Integer) j.second).intValue()));
        } else {
            proxy = null;
        }
        if (NetworkStatusHelper.a().isMobile()) {
            d proxySetting = GlobalAppRuntimeInfo.getProxySetting();
            if (proxySetting != null) {
                proxy = proxySetting.a;
                dVar = proxySetting;
            } else {
                dVar = proxySetting;
            }
        } else {
            dVar = null;
        }
        try {
            HttpURLConnection httpURLConnection;
            URL url = request.getUrl();
            if (proxy != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            try {
                httpURLConnection.setConnectTimeout(request.getConnectTimeout());
                httpURLConnection.setReadTimeout(request.getReadTimeout());
                httpURLConnection.setRequestMethod(request.getMethod());
                if (request.containsBody()) {
                    httpURLConnection.setDoOutput(true);
                }
                Map headers = request.getHeaders();
                for (Entry entry : headers.entrySet()) {
                    httpURLConnection.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                }
                String str = (String) headers.get(HttpConstant.HOST);
                if (str == null) {
                    str = request.getHost();
                }
                httpURLConnection.setRequestProperty(HttpConstant.HOST, str);
                if (NetworkStatusHelper.c().equals("cmwap")) {
                    httpURLConnection.setRequestProperty(HttpConstant.X_ONLINE_HOST, str);
                }
                if (!headers.containsKey(HttpConstant.ACCEPT_ENCODING)) {
                    httpURLConnection.addRequestProperty(HttpConstant.ACCEPT_ENCODING, HttpConstant.GZIP);
                }
                if (dVar != null) {
                    httpURLConnection.setRequestProperty(HttpConstant.AUTHORIZATION, dVar.a());
                }
                if (url.getProtocol().equalsIgnoreCase("https")) {
                    a(httpURLConnection, request, str);
                }
                httpURLConnection.setInstanceFollowRedirects(false);
                return httpURLConnection;
            } catch (Exception e) {
                return httpURLConnection;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    private static void a(HttpURLConnection httpURLConnection, Request request, String str) {
        if (Integer.parseInt(VERSION.SDK) < 8) {
            ALog.e("awcn.HttpConnector", "supportHttps", "[supportHttps]Froyo 以下版本不支持https", new Object[0]);
            return;
        }
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
        if (request.getSslSocketFactory() != null) {
            httpsURLConnection.setSSLSocketFactory(request.getSslSocketFactory());
        } else if (anet.channel.util.b.a() != null) {
            httpsURLConnection.setSSLSocketFactory(anet.channel.util.b.a());
        }
        if (request.getHostnameVerifier() != null) {
            httpsURLConnection.setHostnameVerifier(request.getHostnameVerifier());
        } else if (anet.channel.util.b.b() != null) {
            httpsURLConnection.setHostnameVerifier(anet.channel.util.b.b());
        } else {
            httpsURLConnection.setHostnameVerifier(new c(str));
        }
    }

    private static void a(HttpURLConnection httpURLConnection, Request request) {
        if (request.containsBody()) {
            int postBody;
            long currentTimeMillis = System.currentTimeMillis();
            OutputStream outputStream = null;
            try {
                outputStream = httpURLConnection.getOutputStream();
                postBody = request.postBody(outputStream);
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (Throwable e) {
                        ALog.e("awcn.HttpConnector", "postData", request.getSeq(), e, new Object[0]);
                    }
                }
            } catch (Throwable e2) {
                ALog.e("awcn.HttpConnector", "postData error", request.getSeq(), e2, new Object[0]);
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                        postBody = 0;
                    } catch (Throwable e22) {
                        ALog.e("awcn.HttpConnector", "postData", request.getSeq(), e22, new Object[0]);
                        postBody = 0;
                    }
                } else {
                    postBody = 0;
                }
            } catch (Throwable th) {
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (Throwable e3) {
                        ALog.e("awcn.HttpConnector", "postData", request.getSeq(), e3, new Object[0]);
                    }
                }
            }
            request.rs.sendDataSize = (long) postBody;
            request.rs.sendDataTime = System.currentTimeMillis() - currentTimeMillis;
        }
    }

    private static void a(HttpURLConnection httpURLConnection, Request request, a aVar, RequestCb requestCb) throws IOException, CancellationException {
        InputStream inputStream;
        Throwable th;
        InputStream inputStream2 = null;
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (Throwable e) {
            try {
                inputStream2 = httpURLConnection.getErrorStream();
            } catch (Throwable e2) {
                ALog.e("awcn.HttpConnector", "get error stream failed." + httpURLConnection.getURL().toString(), request.getSeq(), e2, new Object[0]);
            }
            ALog.w("awcn.HttpConnector", httpURLConnection.getURL().toString(), null, e, new Object[0]);
            inputStream = inputStream2;
        }
        if (inputStream == null) {
            a(request, aVar, requestCb, ErrorConstant.ERROR_IO_EXCEPTION, null);
            return;
        }
        OutputStream byteArrayOutputStream;
        int c = anet.channel.util.a.c(aVar.c);
        boolean b = anet.channel.util.a.b(aVar.c);
        if (b) {
            aVar.c.remove(HttpConstant.CONTENT_ENCODING);
        }
        if (requestCb == null || (b && c <= 1048576)) {
            if (c <= 0) {
                c = 1024;
            }
            byteArrayOutputStream = new ByteArrayOutputStream(c);
        } else {
            byteArrayOutputStream = null;
        }
        InputStream cVar;
        try {
            cVar = new anet.channel.strategy.utils.c(inputStream);
            if (b) {
                inputStream2 = new GZIPInputStream(cVar);
            } else {
                inputStream2 = cVar;
            }
            ByteArray byteArray = null;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (byteArray == null) {
                        byteArray = a.a.a(2048);
                    }
                    if (byteArray.readFrom(inputStream2) != -1) {
                        if (request.rs.firstDataTime == 0) {
                            request.rs.firstDataTime = System.currentTimeMillis() - request.rs.start;
                        }
                        if (byteArrayOutputStream != null) {
                            byteArray.writeTo(byteArrayOutputStream);
                        } else {
                            requestCb.onDataReceive(byteArray, false);
                            byteArray = null;
                        }
                    } else {
                        if (byteArrayOutputStream != null) {
                            byteArray.recycle();
                        } else {
                            requestCb.onDataReceive(byteArray, true);
                        }
                        request.rs.recDataTime = (System.currentTimeMillis() - request.rs.start) - request.rs.firstDataTime;
                        request.rs.recDataSize = cVar.a();
                        if (byteArrayOutputStream != null) {
                            aVar.b = byteArrayOutputStream.toByteArray();
                            if (b) {
                                List arrayList = new ArrayList();
                                arrayList.add(String.valueOf(aVar.b.length));
                                aVar.c.put(HttpConstant.CONTENT_LENGTH, arrayList);
                            }
                            if (requestCb != null) {
                                requestCb.onResponseCode(aVar.a, aVar.c);
                                requestCb.onDataReceive(ByteArray.wrap(aVar.b), true);
                            }
                        }
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                                return;
                            } catch (IOException e3) {
                                return;
                            }
                        }
                        return;
                    }
                } catch (Throwable e4) {
                    Throwable th2 = e4;
                    cVar = inputStream2;
                    th = th2;
                }
            }
            throw new CancellationException("task cancelled");
        } catch (Throwable th3) {
            th = th3;
            cVar = inputStream;
            if (cVar != null) {
                try {
                    cVar.close();
                } catch (IOException e5) {
                }
            }
            throw th;
        }
    }
}
