package com.feng.library.okhttp.utils;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import com.feng.car.utils.FengConstant;
import com.feng.library.okhttp.builder.GetBuilder;
import com.feng.library.okhttp.builder.HeadBuilder;
import com.feng.library.okhttp.builder.OtherRequestBuilder;
import com.feng.library.okhttp.builder.PostFileBuilder;
import com.feng.library.okhttp.builder.PostFormBuilder;
import com.feng.library.okhttp.builder.PostStringBuilder;
import com.feng.library.okhttp.callback.Callback;
import com.feng.library.okhttp.cookie.CookieJarImpl;
import com.feng.library.okhttp.cookie.store.CookieStore;
import com.feng.library.okhttp.cookie.store.HasCookieStore;
import com.feng.library.okhttp.cookie.store.PersistentCookieStore;
import com.feng.library.okhttp.https.HttpsUtils;
import com.feng.library.okhttp.log.LoggerInterceptor;
import com.feng.library.okhttp.request.RequestCall;
import com.umeng.message.proguard.l;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Response;

public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 60000;
    public static final String USER_AGENT = "User-Agent";
    private static Context mContext;
    private static OkHttpUtils mInstance;
    public static String userAgent = ("Dalvik/1.6.0 (Linux; U; Android " + VERSION.RELEASE + "; " + Build.MODEL + " Build/" + Build.MANUFACTURER + l.t);
    private Handler mDelivery;
    private OkHttpClient mOkHttpClient;

    public static class METHOD {
        public static final String DELETE = "DELETE";
        public static final String HEAD = "HEAD";
        public static final String PATCH = "PATCH";
        public static final String PUT = "PUT";
    }

    public OkHttpUtils(Context context, OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            Builder okHttpClientBuilder = new Builder();
            okHttpClientBuilder.cookieJar(new CookieJarImpl(PersistentCookieStore.getInstance(context)));
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            this.mOkHttpClient = okHttpClientBuilder.build();
        } else {
            this.mOkHttpClient = okHttpClient;
        }
        this.mOkHttpClient.dispatcher().setMaxRequests(100);
        this.mOkHttpClient.dispatcher().setMaxRequestsPerHost(8);
        init();
    }

    private void init() {
        this.mDelivery = new Handler(Looper.getMainLooper());
    }

    public OkHttpUtils debug(String tag) {
        this.mOkHttpClient = getOkHttpClient().newBuilder().addInterceptor(new LoggerInterceptor(tag, false)).build();
        return this;
    }

    public OkHttpUtils debug(String tag, boolean showResponse) {
        this.mOkHttpClient = getOkHttpClient().newBuilder().addInterceptor(new LoggerInterceptor(tag, showResponse)).build();
        return this;
    }

    public static OkHttpUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mContext = context;
                    mInstance = new OkHttpUtils(context, null);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(mContext, null);
                }
            }
        }
        return mInstance;
    }

    public Handler getDelivery() {
        return this.mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        return this.mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder("PUT");
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder("DELETE");
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(RequestCall requestCall, Callback callback) {
        if (callback == null) {
            callback = Callback.CALLBACK_DEFAULT;
        }
        final Callback finalCallback = callback;
        requestCall.getCall().enqueue(new okhttp3.Callback() {
            public void onFailure(Call call, IOException e) {
                OkHttpUtils.this.sendFailResultCallback(call, e, finalCallback);
            }

            public void onResponse(Call call, Response response) {
                if (response.code() < FengConstant.SINGLE_IMAGE_MAX_WIDTH || response.code() > 599) {
                    try {
                        OkHttpUtils.this.sendSuccessResultCallback(finalCallback.parseNetworkResponse(response), finalCallback);
                        return;
                    } catch (Exception e) {
                        OkHttpUtils.this.sendFailResultCallback(call, e, finalCallback);
                        return;
                    }
                }
                try {
                    OkHttpUtils.this.sendFailResultCallback(call, new RuntimeException(response.body().string()), finalCallback);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public CookieStore getCookieStore() {
        CookieJar cookieJar = this.mOkHttpClient.cookieJar();
        if (cookieJar == null) {
            Exceptions.illegalArgument("you should invoked okHttpClientBuilder.cookieJar() to set a cookieJar.", new Object[0]);
        }
        if (cookieJar instanceof HasCookieStore) {
            return ((HasCookieStore) cookieJar).getCookieStore();
        }
        return null;
    }

    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback) {
        if (callback != null) {
            this.mDelivery.post(new Runnable() {
                public void run() {
                    callback.onError(call, e);
                    callback.onAfter();
                }
            });
        }
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback) {
        if (callback != null) {
            this.mDelivery.post(new Runnable() {
                public void run() {
                    callback.onResponse(object);
                    callback.onAfter();
                }
            });
        }
    }

    public void cancelTag(Object tag) {
        for (Call call : this.mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call2 : this.mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call2.request().tag())) {
                call2.cancel();
            }
        }
    }

    public void setCertificates(InputStream... certificates) {
        this.mOkHttpClient = getOkHttpClient().newBuilder().sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null)).build();
    }

    public void setCertificates(InputStream[] certificates, InputStream bksFile, String password) {
        this.mOkHttpClient = getOkHttpClient().newBuilder().sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, bksFile, password)).build();
    }

    public void setHostNameVerifier(HostnameVerifier hostNameVerifier) {
        this.mOkHttpClient = getOkHttpClient().newBuilder().hostnameVerifier(hostNameVerifier).build();
    }

    public void setConnectTimeout(int timeout, TimeUnit units) {
        this.mOkHttpClient = getOkHttpClient().newBuilder().connectTimeout((long) timeout, units).build();
    }

    public void setReadTimeout(int timeout, TimeUnit units) {
        this.mOkHttpClient = getOkHttpClient().newBuilder().readTimeout((long) timeout, units).build();
    }

    public void setWriteTimeout(int timeout, TimeUnit units) {
        this.mOkHttpClient = getOkHttpClient().newBuilder().writeTimeout((long) timeout, units).build();
    }
}
