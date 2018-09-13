package com.meizu.cloud.pushsdk.networking.common;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.text.TextUtils;
import android.widget.ImageView.ScaleType;
import com.meizu.cloud.pushsdk.networking.core.Core;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.networking.http.Call;
import com.meizu.cloud.pushsdk.networking.http.FormBody;
import com.meizu.cloud.pushsdk.networking.http.Headers;
import com.meizu.cloud.pushsdk.networking.http.HttpUrl;
import com.meizu.cloud.pushsdk.networking.http.HttpUrl.Builder;
import com.meizu.cloud.pushsdk.networking.http.MediaType;
import com.meizu.cloud.pushsdk.networking.http.MultipartBody;
import com.meizu.cloud.pushsdk.networking.http.RequestBody;
import com.meizu.cloud.pushsdk.networking.http.Response;
import com.meizu.cloud.pushsdk.networking.interfaces.AnalyticsListener;
import com.meizu.cloud.pushsdk.networking.interfaces.BitmapRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.DownloadListener;
import com.meizu.cloud.pushsdk.networking.interfaces.DownloadProgressListener;
import com.meizu.cloud.pushsdk.networking.interfaces.JSONArrayRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.JSONObjectRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.OkHttpResponseAndBitmapRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.OkHttpResponseAndJSONArrayRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.OkHttpResponseAndJSONObjectRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.OkHttpResponseAndStringRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.OkHttpResponseListener;
import com.meizu.cloud.pushsdk.networking.interfaces.ParsedRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.StringRequestListener;
import com.meizu.cloud.pushsdk.networking.interfaces.UploadProgressListener;
import com.meizu.cloud.pushsdk.networking.internal.ANRequestQueue;
import com.meizu.cloud.pushsdk.networking.internal.SynchronousCall;
import com.meizu.cloud.pushsdk.networking.okio.Okio;
import com.meizu.cloud.pushsdk.networking.utils.Utils;
import com.meizu.cloud.pushsdk.pushtracer.constant.TrackerConstants;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

public class ANRequest<T extends ANRequest> {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse(TrackerConstants.POST_CONTENT_TYPE);
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final String TAG = ANRequest.class.getSimpleName();
    private static final Object sDecodeLock = new Object();
    private Call call;
    private MediaType customMediaType;
    private Future future;
    private boolean isCancelled;
    private boolean isDelivered;
    private AnalyticsListener mAnalyticsListener;
    private BitmapRequestListener mBitmapRequestListener;
    private HashMap<String, String> mBodyParameterMap;
    private byte[] mByte;
    private Config mDecodeConfig;
    private String mDirPath;
    private DownloadListener mDownloadListener;
    private DownloadProgressListener mDownloadProgressListener;
    private Executor mExecutor;
    private File mFile;
    private String mFileName;
    private HashMap<String, String> mHeadersMap;
    private JSONArrayRequestListener mJSONArrayRequestListener;
    private JSONObjectRequestListener mJSONObjectRequestListener;
    private JSONArray mJsonArray;
    private JSONObject mJsonObject;
    private int mMaxHeight;
    private int mMaxWidth;
    private int mMethod;
    private HashMap<String, File> mMultiPartFileMap;
    private HashMap<String, String> mMultiPartParameterMap;
    private OkHttpResponseAndBitmapRequestListener mOkHttpResponseAndBitmapRequestListener;
    private OkHttpResponseAndJSONArrayRequestListener mOkHttpResponseAndJSONArrayRequestListener;
    private OkHttpResponseAndJSONObjectRequestListener mOkHttpResponseAndJSONObjectRequestListener;
    private OkHttpResponseAndParsedRequestListener mOkHttpResponseAndParsedRequestListener;
    private OkHttpResponseAndStringRequestListener mOkHttpResponseAndStringRequestListener;
    private OkHttpResponseListener mOkHttpResponseListener;
    private ParsedRequestListener mParsedRequestListener;
    private HashMap<String, String> mPathParameterMap;
    private int mPercentageThresholdForCancelling;
    private Priority mPriority;
    private int mProgress;
    private HashMap<String, String> mQueryParameterMap;
    private int mRequestType;
    private ResponseType mResponseType;
    private ScaleType mScaleType;
    private String mStringBody;
    private StringRequestListener mStringRequestListener;
    private Object mTag;
    private Type mType;
    private UploadProgressListener mUploadProgressListener;
    private String mUrl;
    private HashMap<String, String> mUrlEncodedFormBodyParameterMap;
    private String mUserAgent;
    private int sequenceNumber;

    public static class PostRequestBuilder<T extends PostRequestBuilder> implements RequestBuilder {
        private HashMap<String, String> mBodyParameterMap = new HashMap();
        private byte[] mByte = null;
        private String mCustomContentType;
        private Executor mExecutor;
        private File mFile = null;
        private HashMap<String, String> mHeadersMap = new HashMap();
        private JSONArray mJsonArray = null;
        private JSONObject mJsonObject = null;
        private int mMethod = 1;
        private HashMap<String, String> mPathParameterMap = new HashMap();
        private Priority mPriority = Priority.MEDIUM;
        private HashMap<String, String> mQueryParameterMap = new HashMap();
        private String mStringBody = null;
        private Object mTag;
        private String mUrl;
        private HashMap<String, String> mUrlEncodedFormBodyParameterMap = new HashMap();
        private String mUserAgent;

        public PostRequestBuilder(String url) {
            this.mUrl = url;
            this.mMethod = 1;
        }

        public PostRequestBuilder(String url, int method) {
            this.mUrl = url;
            this.mMethod = method;
        }

        public T setPriority(Priority priority) {
            this.mPriority = priority;
            return this;
        }

        public T setTag(Object tag) {
            this.mTag = tag;
            return this;
        }

        public T addQueryParameter(String key, String value) {
            this.mQueryParameterMap.put(key, value);
            return this;
        }

        public T addQueryParameter(HashMap<String, String> queryParameterMap) {
            if (queryParameterMap != null) {
                for (Entry<String, String> entry : queryParameterMap.entrySet()) {
                    this.mQueryParameterMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T addPathParameter(String key, String value) {
            this.mPathParameterMap.put(key, value);
            return this;
        }

        public T addHeaders(String key, String value) {
            this.mHeadersMap.put(key, value);
            return this;
        }

        public T addHeaders(HashMap<String, String> headerMap) {
            if (headerMap != null) {
                for (Entry<String, String> entry : headerMap.entrySet()) {
                    this.mHeadersMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T doNotCacheResponse() {
            return this;
        }

        public T getResponseOnlyIfCached() {
            return this;
        }

        public T getResponseOnlyFromNetwork() {
            return this;
        }

        public T setMaxAgeCacheControl(int maxAge, TimeUnit timeUnit) {
            return this;
        }

        public T setMaxStaleCacheControl(int maxStale, TimeUnit timeUnit) {
            return this;
        }

        public T setExecutor(Executor executor) {
            this.mExecutor = executor;
            return this;
        }

        public T setUserAgent(String userAgent) {
            this.mUserAgent = userAgent;
            return this;
        }

        public T addBodyParameter(String key, String value) {
            this.mBodyParameterMap.put(key, value);
            return this;
        }

        public T addUrlEncodeFormBodyParameter(String key, String value) {
            this.mUrlEncodedFormBodyParameterMap.put(key, value);
            return this;
        }

        public T addBodyParameter(HashMap<String, String> bodyParameterMap) {
            if (bodyParameterMap != null) {
                for (Entry<String, String> entry : bodyParameterMap.entrySet()) {
                    this.mBodyParameterMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T addUrlEncodeFormBodyParameter(HashMap<String, String> bodyParameterMap) {
            if (bodyParameterMap != null) {
                for (Entry<String, String> entry : bodyParameterMap.entrySet()) {
                    this.mUrlEncodedFormBodyParameterMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T addJSONObjectBody(JSONObject jsonObject) {
            this.mJsonObject = jsonObject;
            return this;
        }

        public T addJSONArrayBody(JSONArray jsonArray) {
            this.mJsonArray = jsonArray;
            return this;
        }

        public T addStringBody(String stringBody) {
            this.mStringBody = stringBody;
            return this;
        }

        public T addFileBody(File file) {
            this.mFile = file;
            return this;
        }

        public T addByteBody(byte[] bytes) {
            this.mByte = bytes;
            return this;
        }

        public T setContentType(String contentType) {
            this.mCustomContentType = contentType;
            return this;
        }

        public ANRequest build() {
            return new ANRequest(this);
        }
    }

    public static class DeleteRequestBuilder extends PostRequestBuilder {
        public DeleteRequestBuilder(String url) {
            super(url, 3);
        }
    }

    public static class DownloadBuilder<T extends DownloadBuilder> implements RequestBuilder {
        private String mDirPath;
        private Executor mExecutor;
        private String mFileName;
        private HashMap<String, String> mHeadersMap = new HashMap();
        private HashMap<String, String> mPathParameterMap = new HashMap();
        private int mPercentageThresholdForCancelling = 0;
        private Priority mPriority = Priority.MEDIUM;
        private HashMap<String, String> mQueryParameterMap = new HashMap();
        private Object mTag;
        private String mUrl;
        private String mUserAgent;

        public DownloadBuilder(String url, String dirPath, String fileName) {
            this.mUrl = url;
            this.mDirPath = dirPath;
            this.mFileName = fileName;
        }

        public T setPriority(Priority priority) {
            this.mPriority = priority;
            return this;
        }

        public T setTag(Object tag) {
            this.mTag = tag;
            return this;
        }

        public T addHeaders(String key, String value) {
            this.mHeadersMap.put(key, value);
            return this;
        }

        public T addHeaders(HashMap<String, String> headerMap) {
            if (headerMap != null) {
                for (Entry<String, String> entry : headerMap.entrySet()) {
                    this.mHeadersMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T addQueryParameter(String key, String value) {
            this.mQueryParameterMap.put(key, value);
            return this;
        }

        public T addQueryParameter(HashMap<String, String> queryParameterMap) {
            if (queryParameterMap != null) {
                for (Entry<String, String> entry : queryParameterMap.entrySet()) {
                    this.mQueryParameterMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T addPathParameter(String key, String value) {
            this.mPathParameterMap.put(key, value);
            return this;
        }

        public T doNotCacheResponse() {
            return this;
        }

        public T getResponseOnlyIfCached() {
            return this;
        }

        public T getResponseOnlyFromNetwork() {
            return this;
        }

        public T setMaxAgeCacheControl(int maxAge, TimeUnit timeUnit) {
            return this;
        }

        public T setMaxStaleCacheControl(int maxStale, TimeUnit timeUnit) {
            return this;
        }

        public T setExecutor(Executor executor) {
            this.mExecutor = executor;
            return this;
        }

        public T setUserAgent(String userAgent) {
            this.mUserAgent = userAgent;
            return this;
        }

        public T setPercentageThresholdForCancelling(int percentageThresholdForCancelling) {
            this.mPercentageThresholdForCancelling = percentageThresholdForCancelling;
            return this;
        }

        public ANRequest build() {
            return new ANRequest(this);
        }
    }

    public static class GetRequestBuilder<T extends GetRequestBuilder> implements RequestBuilder {
        private Config mDecodeConfig;
        private Executor mExecutor;
        private HashMap<String, String> mHeadersMap = new HashMap();
        private int mMaxHeight;
        private int mMaxWidth;
        private int mMethod = 0;
        private HashMap<String, String> mPathParameterMap = new HashMap();
        private Priority mPriority = Priority.MEDIUM;
        private HashMap<String, String> mQueryParameterMap = new HashMap();
        private ScaleType mScaleType;
        private Object mTag;
        private String mUrl;
        private String mUserAgent;

        public GetRequestBuilder(String url) {
            this.mUrl = url;
            this.mMethod = 0;
        }

        public GetRequestBuilder(String url, int method) {
            this.mUrl = url;
            this.mMethod = method;
        }

        public T setPriority(Priority priority) {
            this.mPriority = priority;
            return this;
        }

        public T setTag(Object tag) {
            this.mTag = tag;
            return this;
        }

        public T addQueryParameter(String key, String value) {
            this.mQueryParameterMap.put(key, value);
            return this;
        }

        public T addQueryParameter(HashMap<String, String> queryParameterMap) {
            if (queryParameterMap != null) {
                for (Entry<String, String> entry : queryParameterMap.entrySet()) {
                    this.mQueryParameterMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T addPathParameter(String key, String value) {
            this.mPathParameterMap.put(key, value);
            return this;
        }

        public T addHeaders(String key, String value) {
            this.mHeadersMap.put(key, value);
            return this;
        }

        public T addHeaders(HashMap<String, String> headerMap) {
            if (headerMap != null) {
                for (Entry<String, String> entry : headerMap.entrySet()) {
                    this.mHeadersMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T doNotCacheResponse() {
            return this;
        }

        public T getResponseOnlyIfCached() {
            return this;
        }

        public T getResponseOnlyFromNetwork() {
            return this;
        }

        public T setMaxAgeCacheControl(int maxAge, TimeUnit timeUnit) {
            return this;
        }

        public T setMaxStaleCacheControl(int maxStale, TimeUnit timeUnit) {
            return this;
        }

        public T setExecutor(Executor executor) {
            this.mExecutor = executor;
            return this;
        }

        public T setUserAgent(String userAgent) {
            this.mUserAgent = userAgent;
            return this;
        }

        public T setBitmapConfig(Config bitmapConfig) {
            this.mDecodeConfig = bitmapConfig;
            return this;
        }

        public T setBitmapMaxHeight(int maxHeight) {
            this.mMaxHeight = maxHeight;
            return this;
        }

        public T setBitmapMaxWidth(int maxWidth) {
            this.mMaxWidth = maxWidth;
            return this;
        }

        public T setImageScaleType(ScaleType imageScaleType) {
            this.mScaleType = imageScaleType;
            return this;
        }

        public ANRequest build() {
            return new ANRequest(this);
        }
    }

    public static class HeadRequestBuilder extends GetRequestBuilder {
        public HeadRequestBuilder(String url) {
            super(url, 4);
        }
    }

    public static class MultiPartBuilder<T extends MultiPartBuilder> implements RequestBuilder {
        private String mCustomContentType;
        private Executor mExecutor;
        private HashMap<String, String> mHeadersMap = new HashMap();
        private HashMap<String, File> mMultiPartFileMap = new HashMap();
        private HashMap<String, String> mMultiPartParameterMap = new HashMap();
        private HashMap<String, String> mPathParameterMap = new HashMap();
        private int mPercentageThresholdForCancelling = 0;
        private Priority mPriority = Priority.MEDIUM;
        private HashMap<String, String> mQueryParameterMap = new HashMap();
        private Object mTag;
        private String mUrl;
        private String mUserAgent;

        public MultiPartBuilder(String url) {
            this.mUrl = url;
        }

        public T setPriority(Priority priority) {
            this.mPriority = priority;
            return this;
        }

        public T setTag(Object tag) {
            this.mTag = tag;
            return this;
        }

        public T addQueryParameter(String key, String value) {
            this.mQueryParameterMap.put(key, value);
            return this;
        }

        public T addQueryParameter(HashMap<String, String> queryParameterMap) {
            if (queryParameterMap != null) {
                for (Entry<String, String> entry : queryParameterMap.entrySet()) {
                    this.mQueryParameterMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T addPathParameter(String key, String value) {
            this.mPathParameterMap.put(key, value);
            return this;
        }

        public T addHeaders(String key, String value) {
            this.mHeadersMap.put(key, value);
            return this;
        }

        public T addHeaders(HashMap<String, String> headerMap) {
            if (headerMap != null) {
                for (Entry<String, String> entry : headerMap.entrySet()) {
                    this.mHeadersMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T doNotCacheResponse() {
            return this;
        }

        public T getResponseOnlyIfCached() {
            return this;
        }

        public T getResponseOnlyFromNetwork() {
            return this;
        }

        public T setMaxAgeCacheControl(int maxAge, TimeUnit timeUnit) {
            return this;
        }

        public T setMaxStaleCacheControl(int maxStale, TimeUnit timeUnit) {
            return this;
        }

        public T setExecutor(Executor executor) {
            this.mExecutor = executor;
            return this;
        }

        public T setUserAgent(String userAgent) {
            this.mUserAgent = userAgent;
            return this;
        }

        public T addMultipartParameter(String key, String value) {
            this.mMultiPartParameterMap.put(key, value);
            return this;
        }

        public T addMultipartParameter(HashMap<String, String> multiPartParameterMap) {
            if (multiPartParameterMap != null) {
                for (Entry<String, String> entry : multiPartParameterMap.entrySet()) {
                    this.mMultiPartParameterMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T addMultipartFile(String key, File file) {
            this.mMultiPartFileMap.put(key, file);
            return this;
        }

        public T addMultipartFile(HashMap<String, File> multiPartFileMap) {
            if (multiPartFileMap != null) {
                for (Entry<String, File> entry : multiPartFileMap.entrySet()) {
                    this.mMultiPartFileMap.put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public T setPercentageThresholdForCancelling(int percentageThresholdForCancelling) {
            this.mPercentageThresholdForCancelling = percentageThresholdForCancelling;
            return this;
        }

        public T setContentType(String contentType) {
            this.mCustomContentType = contentType;
            return this;
        }

        public ANRequest build() {
            return new ANRequest(this);
        }
    }

    public static class PatchRequestBuilder extends PostRequestBuilder {
        public PatchRequestBuilder(String url) {
            super(url, 5);
        }
    }

    public static class PutRequestBuilder extends PostRequestBuilder {
        public PutRequestBuilder(String url) {
            super(url, 2);
        }
    }

    public ANRequest(GetRequestBuilder builder) {
        this.mHeadersMap = new HashMap();
        this.mBodyParameterMap = new HashMap();
        this.mUrlEncodedFormBodyParameterMap = new HashMap();
        this.mMultiPartParameterMap = new HashMap();
        this.mQueryParameterMap = new HashMap();
        this.mPathParameterMap = new HashMap();
        this.mMultiPartFileMap = new HashMap();
        this.mJsonObject = null;
        this.mJsonArray = null;
        this.mStringBody = null;
        this.mByte = null;
        this.mFile = null;
        this.customMediaType = null;
        this.mPercentageThresholdForCancelling = 0;
        this.mExecutor = null;
        this.mUserAgent = null;
        this.mType = null;
        this.mRequestType = 0;
        this.mMethod = builder.mMethod;
        this.mPriority = builder.mPriority;
        this.mUrl = builder.mUrl;
        this.mTag = builder.mTag;
        this.mHeadersMap = builder.mHeadersMap;
        this.mDecodeConfig = builder.mDecodeConfig;
        this.mMaxHeight = builder.mMaxHeight;
        this.mMaxWidth = builder.mMaxWidth;
        this.mScaleType = builder.mScaleType;
        this.mQueryParameterMap = builder.mQueryParameterMap;
        this.mPathParameterMap = builder.mPathParameterMap;
        this.mExecutor = builder.mExecutor;
        this.mUserAgent = builder.mUserAgent;
    }

    public ANRequest(PostRequestBuilder builder) {
        this.mHeadersMap = new HashMap();
        this.mBodyParameterMap = new HashMap();
        this.mUrlEncodedFormBodyParameterMap = new HashMap();
        this.mMultiPartParameterMap = new HashMap();
        this.mQueryParameterMap = new HashMap();
        this.mPathParameterMap = new HashMap();
        this.mMultiPartFileMap = new HashMap();
        this.mJsonObject = null;
        this.mJsonArray = null;
        this.mStringBody = null;
        this.mByte = null;
        this.mFile = null;
        this.customMediaType = null;
        this.mPercentageThresholdForCancelling = 0;
        this.mExecutor = null;
        this.mUserAgent = null;
        this.mType = null;
        this.mRequestType = 0;
        this.mMethod = builder.mMethod;
        this.mPriority = builder.mPriority;
        this.mUrl = builder.mUrl;
        this.mTag = builder.mTag;
        this.mHeadersMap = builder.mHeadersMap;
        this.mBodyParameterMap = builder.mBodyParameterMap;
        this.mUrlEncodedFormBodyParameterMap = builder.mUrlEncodedFormBodyParameterMap;
        this.mQueryParameterMap = builder.mQueryParameterMap;
        this.mPathParameterMap = builder.mPathParameterMap;
        this.mJsonObject = builder.mJsonObject;
        this.mJsonArray = builder.mJsonArray;
        this.mStringBody = builder.mStringBody;
        this.mFile = builder.mFile;
        this.mByte = builder.mByte;
        this.mExecutor = builder.mExecutor;
        this.mUserAgent = builder.mUserAgent;
        if (builder.mCustomContentType != null) {
            this.customMediaType = MediaType.parse(builder.mCustomContentType);
        }
    }

    public ANRequest(DownloadBuilder builder) {
        this.mHeadersMap = new HashMap();
        this.mBodyParameterMap = new HashMap();
        this.mUrlEncodedFormBodyParameterMap = new HashMap();
        this.mMultiPartParameterMap = new HashMap();
        this.mQueryParameterMap = new HashMap();
        this.mPathParameterMap = new HashMap();
        this.mMultiPartFileMap = new HashMap();
        this.mJsonObject = null;
        this.mJsonArray = null;
        this.mStringBody = null;
        this.mByte = null;
        this.mFile = null;
        this.customMediaType = null;
        this.mPercentageThresholdForCancelling = 0;
        this.mExecutor = null;
        this.mUserAgent = null;
        this.mType = null;
        this.mRequestType = 1;
        this.mMethod = 0;
        this.mPriority = builder.mPriority;
        this.mUrl = builder.mUrl;
        this.mTag = builder.mTag;
        this.mDirPath = builder.mDirPath;
        this.mFileName = builder.mFileName;
        this.mHeadersMap = builder.mHeadersMap;
        this.mQueryParameterMap = builder.mQueryParameterMap;
        this.mPathParameterMap = builder.mPathParameterMap;
        this.mPercentageThresholdForCancelling = builder.mPercentageThresholdForCancelling;
        this.mExecutor = builder.mExecutor;
        this.mUserAgent = builder.mUserAgent;
    }

    public ANRequest(MultiPartBuilder builder) {
        this.mHeadersMap = new HashMap();
        this.mBodyParameterMap = new HashMap();
        this.mUrlEncodedFormBodyParameterMap = new HashMap();
        this.mMultiPartParameterMap = new HashMap();
        this.mQueryParameterMap = new HashMap();
        this.mPathParameterMap = new HashMap();
        this.mMultiPartFileMap = new HashMap();
        this.mJsonObject = null;
        this.mJsonArray = null;
        this.mStringBody = null;
        this.mByte = null;
        this.mFile = null;
        this.customMediaType = null;
        this.mPercentageThresholdForCancelling = 0;
        this.mExecutor = null;
        this.mUserAgent = null;
        this.mType = null;
        this.mRequestType = 2;
        this.mMethod = 1;
        this.mPriority = builder.mPriority;
        this.mUrl = builder.mUrl;
        this.mTag = builder.mTag;
        this.mHeadersMap = builder.mHeadersMap;
        this.mQueryParameterMap = builder.mQueryParameterMap;
        this.mPathParameterMap = builder.mPathParameterMap;
        this.mMultiPartParameterMap = builder.mMultiPartParameterMap;
        this.mMultiPartFileMap = builder.mMultiPartFileMap;
        this.mPercentageThresholdForCancelling = builder.mPercentageThresholdForCancelling;
        this.mExecutor = builder.mExecutor;
        this.mUserAgent = builder.mUserAgent;
        if (builder.mCustomContentType != null) {
            this.customMediaType = MediaType.parse(builder.mCustomContentType);
        }
    }

    public void getAsJSONObject(JSONObjectRequestListener requestListener) {
        this.mResponseType = ResponseType.JSON_OBJECT;
        this.mJSONObjectRequestListener = requestListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void getAsJSONArray(JSONArrayRequestListener requestListener) {
        this.mResponseType = ResponseType.JSON_ARRAY;
        this.mJSONArrayRequestListener = requestListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void getAsString(StringRequestListener requestListener) {
        this.mResponseType = ResponseType.STRING;
        this.mStringRequestListener = requestListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void getAsOkHttpResponse(OkHttpResponseListener requestListener) {
        this.mResponseType = ResponseType.OK_HTTP_RESPONSE;
        this.mOkHttpResponseListener = requestListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void getAsBitmap(BitmapRequestListener requestListener) {
        this.mResponseType = ResponseType.BITMAP;
        this.mBitmapRequestListener = requestListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void getAsOkHttpResponseAndJSONObject(OkHttpResponseAndJSONObjectRequestListener requestListener) {
        this.mResponseType = ResponseType.JSON_OBJECT;
        this.mOkHttpResponseAndJSONObjectRequestListener = requestListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void getAsOkHttpResponseAndJSONArray(OkHttpResponseAndJSONArrayRequestListener requestListener) {
        this.mResponseType = ResponseType.JSON_ARRAY;
        this.mOkHttpResponseAndJSONArrayRequestListener = requestListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void getAsOkHttpResponseAndString(OkHttpResponseAndStringRequestListener requestListener) {
        this.mResponseType = ResponseType.STRING;
        this.mOkHttpResponseAndStringRequestListener = requestListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void getAsOkHttpResponseAndBitmap(OkHttpResponseAndBitmapRequestListener requestListener) {
        this.mResponseType = ResponseType.BITMAP;
        this.mOkHttpResponseAndBitmapRequestListener = requestListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void startDownload(DownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public void prefetch() {
        this.mResponseType = ResponseType.PREFETCH;
        ANRequestQueue.getInstance().addRequest(this);
    }

    public ANResponse executeForJSONObject() {
        this.mResponseType = ResponseType.JSON_OBJECT;
        return SynchronousCall.execute(this);
    }

    public ANResponse executeForJSONArray() {
        this.mResponseType = ResponseType.JSON_ARRAY;
        return SynchronousCall.execute(this);
    }

    public ANResponse executeForString() {
        this.mResponseType = ResponseType.STRING;
        return SynchronousCall.execute(this);
    }

    public ANResponse executeForOkHttpResponse() {
        this.mResponseType = ResponseType.OK_HTTP_RESPONSE;
        return SynchronousCall.execute(this);
    }

    public ANResponse executeForBitmap() {
        this.mResponseType = ResponseType.BITMAP;
        return SynchronousCall.execute(this);
    }

    public ANResponse executeForDownload() {
        return SynchronousCall.execute(this);
    }

    public T setDownloadProgressListener(DownloadProgressListener downloadProgressListener) {
        this.mDownloadProgressListener = downloadProgressListener;
        return this;
    }

    public T setUploadProgressListener(UploadProgressListener uploadProgressListener) {
        this.mUploadProgressListener = uploadProgressListener;
        return this;
    }

    public T setAnalyticsListener(AnalyticsListener analyticsListener) {
        this.mAnalyticsListener = analyticsListener;
        return this;
    }

    public AnalyticsListener getAnalyticsListener() {
        return this.mAnalyticsListener;
    }

    public int getMethod() {
        return this.mMethod;
    }

    public Priority getPriority() {
        return this.mPriority;
    }

    public String getUrl() {
        String tempUrl = this.mUrl;
        for (Entry<String, String> entry : this.mPathParameterMap.entrySet()) {
            tempUrl = tempUrl.replace("{" + ((String) entry.getKey()) + "}", String.valueOf(entry.getValue()));
        }
        Builder urlBuilder = HttpUrl.parse(tempUrl).newBuilder();
        for (Entry<String, String> entry2 : this.mQueryParameterMap.entrySet()) {
            urlBuilder.addQueryParameter((String) entry2.getKey(), (String) entry2.getValue());
        }
        return urlBuilder.build().toString();
    }

    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }

    public void setResponseAs(ResponseType responseType) {
        this.mResponseType = responseType;
    }

    public ResponseType getResponseAs() {
        return this.mResponseType;
    }

    public Object getTag() {
        return this.mTag;
    }

    public int getRequestType() {
        return this.mRequestType;
    }

    public void setUserAgent(String userAgent) {
        this.mUserAgent = userAgent;
    }

    public String getUserAgent() {
        return this.mUserAgent;
    }

    public Type getType() {
        return this.mType;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public DownloadProgressListener getDownloadProgressListener() {
        return new DownloadProgressListener() {
            public void onProgress(long bytesDownloaded, long totalBytes) {
                if (ANRequest.this.mDownloadProgressListener != null && !ANRequest.this.isCancelled) {
                    ANRequest.this.mDownloadProgressListener.onProgress(bytesDownloaded, totalBytes);
                }
            }
        };
    }

    public void updateDownloadCompletion() {
        this.isDelivered = true;
        if (this.mDownloadListener == null) {
            ANLog.d("Prefetch done : " + toString());
            finish();
        } else if (this.isCancelled) {
            deliverError(new ANError());
            finish();
        } else if (this.mExecutor != null) {
            this.mExecutor.execute(new Runnable() {
                public void run() {
                    if (ANRequest.this.mDownloadListener != null) {
                        ANRequest.this.mDownloadListener.onDownloadComplete();
                    }
                    ANLog.d("Delivering success : " + toString());
                    ANRequest.this.finish();
                }
            });
        } else {
            Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
                public void run() {
                    if (ANRequest.this.mDownloadListener != null) {
                        ANRequest.this.mDownloadListener.onDownloadComplete();
                    }
                    ANLog.d("Delivering success : " + toString());
                    ANRequest.this.finish();
                }
            });
        }
    }

    public UploadProgressListener getUploadProgressListener() {
        return new UploadProgressListener() {
            public void onProgress(long bytesUploaded, long totalBytes) {
                ANRequest.this.mProgress = (int) ((100 * bytesUploaded) / totalBytes);
                if (ANRequest.this.mUploadProgressListener != null && !ANRequest.this.isCancelled) {
                    ANRequest.this.mUploadProgressListener.onProgress(bytesUploaded, totalBytes);
                }
            }
        };
    }

    public String getDirPath() {
        return this.mDirPath;
    }

    public String getFileName() {
        return this.mFileName;
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public void cancel(boolean forceCancel) {
        if (!forceCancel) {
            try {
                if (this.mPercentageThresholdForCancelling != 0 && this.mProgress >= this.mPercentageThresholdForCancelling) {
                    ANLog.d("not cancelling request : " + toString());
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        ANLog.d("cancelling request : " + toString());
        this.isCancelled = true;
        if (this.call != null) {
            this.call.cancel();
        }
        if (this.future != null) {
            this.future.cancel(true);
        }
        if (!this.isDelivered) {
            deliverError(new ANError());
        }
    }

    public boolean isCanceled() {
        return this.isCancelled;
    }

    public Call getCall() {
        return this.call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public Future getFuture() {
        return this.future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    public void destroy() {
        this.mJSONArrayRequestListener = null;
        this.mJSONArrayRequestListener = null;
        this.mStringRequestListener = null;
        this.mBitmapRequestListener = null;
        this.mParsedRequestListener = null;
        this.mDownloadProgressListener = null;
        this.mUploadProgressListener = null;
        this.mDownloadListener = null;
        this.mAnalyticsListener = null;
    }

    public void finish() {
        destroy();
        ANRequestQueue.getInstance().finish(this);
    }

    public ANResponse parseResponse(Response response) {
        switch (this.mResponseType) {
            case JSON_ARRAY:
                try {
                    return ANResponse.success(new JSONArray(Okio.buffer(response.body().source()).readUtf8()));
                } catch (Throwable e) {
                    return ANResponse.failed(Utils.getErrorForParse(new ANError(e)));
                }
            case JSON_OBJECT:
                try {
                    return ANResponse.success(new JSONObject(Okio.buffer(response.body().source()).readUtf8()));
                } catch (Throwable e2) {
                    return ANResponse.failed(Utils.getErrorForParse(new ANError(e2)));
                }
            case STRING:
                try {
                    return ANResponse.success(Okio.buffer(response.body().source()).readUtf8());
                } catch (Throwable e22) {
                    return ANResponse.failed(Utils.getErrorForParse(new ANError(e22)));
                }
            case BITMAP:
                ANResponse decodeBitmap;
                synchronized (sDecodeLock) {
                    try {
                        decodeBitmap = Utils.decodeBitmap(response, this.mMaxWidth, this.mMaxHeight, this.mDecodeConfig, this.mScaleType);
                    } catch (Throwable e222) {
                        return ANResponse.failed(Utils.getErrorForParse(new ANError(e222)));
                    }
                }
                return decodeBitmap;
            case PREFETCH:
                return ANResponse.success(ANConstants.PREFETCH);
            default:
                return null;
        }
    }

    public ANError parseNetworkError(ANError anError) {
        try {
            if (!(anError.getResponse() == null || anError.getResponse().body() == null || anError.getResponse().body().source() == null)) {
                anError.setErrorBody(Okio.buffer(anError.getResponse().body().source()).readUtf8());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return anError;
    }

    public synchronized void deliverError(ANError anError) {
        try {
            if (!this.isDelivered) {
                if (this.isCancelled) {
                    anError.setCancellationMessageInError();
                    anError.setErrorCode(0);
                }
                deliverErrorResponse(anError);
                ANLog.d("Delivering anError : " + toString());
            }
            this.isDelivered = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public void deliverResponse(final ANResponse response) {
        try {
            this.isDelivered = true;
            if (this.isCancelled) {
                ANError anError = new ANError();
                anError.setCancellationMessageInError();
                anError.setErrorCode(0);
                deliverErrorResponse(anError);
                finish();
                ANLog.d("Delivering cancelled : " + toString());
                return;
            }
            if (this.mExecutor != null) {
                this.mExecutor.execute(new Runnable() {
                    public void run() {
                        ANRequest.this.deliverSuccessResponse(response);
                    }
                });
            } else {
                Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
                    public void run() {
                        ANRequest.this.deliverSuccessResponse(response);
                    }
                });
            }
            ANLog.d("Delivering success : " + toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deliverSuccessResponse(ANResponse response) {
        if (this.mJSONObjectRequestListener != null) {
            this.mJSONObjectRequestListener.onResponse((JSONObject) response.getResult());
        } else if (this.mJSONArrayRequestListener != null) {
            this.mJSONArrayRequestListener.onResponse((JSONArray) response.getResult());
        } else if (this.mStringRequestListener != null) {
            this.mStringRequestListener.onResponse((String) response.getResult());
        } else if (this.mBitmapRequestListener != null) {
            this.mBitmapRequestListener.onResponse((Bitmap) response.getResult());
        } else if (this.mParsedRequestListener != null) {
            this.mParsedRequestListener.onResponse(response.getResult());
        } else if (this.mOkHttpResponseAndJSONObjectRequestListener != null) {
            this.mOkHttpResponseAndJSONObjectRequestListener.onResponse(response.getOkHttpResponse(), (JSONObject) response.getResult());
        } else if (this.mOkHttpResponseAndJSONArrayRequestListener != null) {
            this.mOkHttpResponseAndJSONArrayRequestListener.onResponse(response.getOkHttpResponse(), (JSONArray) response.getResult());
        } else if (this.mOkHttpResponseAndStringRequestListener != null) {
            this.mOkHttpResponseAndStringRequestListener.onResponse(response.getOkHttpResponse(), (String) response.getResult());
        } else if (this.mOkHttpResponseAndBitmapRequestListener != null) {
            this.mOkHttpResponseAndBitmapRequestListener.onResponse(response.getOkHttpResponse(), (Bitmap) response.getResult());
        } else if (this.mOkHttpResponseAndParsedRequestListener != null) {
            this.mOkHttpResponseAndParsedRequestListener.onResponse(response.getOkHttpResponse(), response.getResult());
        }
        finish();
    }

    private void deliverErrorResponse(ANError anError) {
        if (this.mJSONObjectRequestListener != null) {
            this.mJSONObjectRequestListener.onError(anError);
        } else if (this.mJSONArrayRequestListener != null) {
            this.mJSONArrayRequestListener.onError(anError);
        } else if (this.mStringRequestListener != null) {
            this.mStringRequestListener.onError(anError);
        } else if (this.mBitmapRequestListener != null) {
            this.mBitmapRequestListener.onError(anError);
        } else if (this.mParsedRequestListener != null) {
            this.mParsedRequestListener.onError(anError);
        } else if (this.mOkHttpResponseAndJSONObjectRequestListener != null) {
            this.mOkHttpResponseAndJSONObjectRequestListener.onError(anError);
        } else if (this.mOkHttpResponseAndJSONArrayRequestListener != null) {
            this.mOkHttpResponseAndJSONArrayRequestListener.onError(anError);
        } else if (this.mOkHttpResponseAndStringRequestListener != null) {
            this.mOkHttpResponseAndStringRequestListener.onError(anError);
        } else if (this.mOkHttpResponseAndBitmapRequestListener != null) {
            this.mOkHttpResponseAndBitmapRequestListener.onError(anError);
        } else if (this.mOkHttpResponseAndParsedRequestListener != null) {
            this.mOkHttpResponseAndParsedRequestListener.onError(anError);
        } else if (this.mDownloadListener != null) {
            this.mDownloadListener.onError(anError);
        }
    }

    public void deliverOkHttpResponse(final Response response) {
        try {
            this.isDelivered = true;
            if (this.isCancelled) {
                ANError anError = new ANError();
                anError.setCancellationMessageInError();
                anError.setErrorCode(0);
                if (this.mOkHttpResponseListener != null) {
                    this.mOkHttpResponseListener.onError(anError);
                }
                finish();
                ANLog.d("Delivering cancelled : " + toString());
                return;
            }
            if (this.mExecutor != null) {
                this.mExecutor.execute(new Runnable() {
                    public void run() {
                        if (ANRequest.this.mOkHttpResponseListener != null) {
                            ANRequest.this.mOkHttpResponseListener.onResponse(response);
                        }
                        ANRequest.this.finish();
                    }
                });
            } else {
                Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
                    public void run() {
                        if (ANRequest.this.mOkHttpResponseListener != null) {
                            ANRequest.this.mOkHttpResponseListener.onResponse(response);
                        }
                        ANRequest.this.finish();
                    }
                });
            }
            ANLog.d("Delivering success : " + toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RequestBody getRequestBody() {
        if (this.mJsonObject != null) {
            if (this.customMediaType != null) {
                return RequestBody.create(this.customMediaType, this.mJsonObject.toString());
            }
            return RequestBody.create(JSON_MEDIA_TYPE, this.mJsonObject.toString());
        } else if (this.mJsonArray != null) {
            if (this.customMediaType != null) {
                return RequestBody.create(this.customMediaType, this.mJsonArray.toString());
            }
            return RequestBody.create(JSON_MEDIA_TYPE, this.mJsonArray.toString());
        } else if (this.mStringBody != null) {
            if (this.customMediaType != null) {
                return RequestBody.create(this.customMediaType, this.mStringBody);
            }
            return RequestBody.create(MEDIA_TYPE_MARKDOWN, this.mStringBody);
        } else if (this.mFile != null) {
            if (this.customMediaType != null) {
                return RequestBody.create(this.customMediaType, this.mFile);
            }
            return RequestBody.create(MEDIA_TYPE_MARKDOWN, this.mFile);
        } else if (this.mByte == null) {
            FormBody.Builder builder = new FormBody.Builder();
            try {
                for (Entry<String, String> entry : this.mBodyParameterMap.entrySet()) {
                    if (!(TextUtils.isEmpty((CharSequence) entry.getKey()) || TextUtils.isEmpty((CharSequence) entry.getValue()))) {
                        builder.add((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                for (Entry<String, String> entry2 : this.mUrlEncodedFormBodyParameterMap.entrySet()) {
                    if (!(TextUtils.isEmpty((CharSequence) entry2.getKey()) || TextUtils.isEmpty((CharSequence) entry2.getValue()))) {
                        builder.addEncoded((String) entry2.getKey(), (String) entry2.getValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.build();
        } else if (this.customMediaType != null) {
            return RequestBody.create(this.customMediaType, this.mByte);
        } else {
            return RequestBody.create(MEDIA_TYPE_MARKDOWN, this.mByte);
        }
    }

    public RequestBody getMultiPartRequestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            for (Entry<String, String> entry : this.mMultiPartParameterMap.entrySet()) {
                String[] strArr = new String[]{"Content-Disposition", "form-data; name=\"" + ((String) entry.getKey()) + "\""};
                builder.addPart(Headers.of(strArr), RequestBody.create(null, (String) ((Entry) r7.next()).getValue()));
            }
            for (Entry<String, File> entry2 : this.mMultiPartFileMap.entrySet()) {
                if (entry2.getValue() != null) {
                    builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + ((String) entry2.getKey()) + "\"; filename=\"" + fileName + "\""), RequestBody.create(MediaType.parse(Utils.getMimeType(((File) entry2.getValue()).getName())), (File) entry2.getValue()));
                    if (this.customMediaType != null) {
                        builder.setType(this.customMediaType);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    public Headers getHeaders() {
        Headers.Builder builder = new Headers.Builder();
        try {
            for (Entry<String, String> entry : this.mHeadersMap.entrySet()) {
                builder.add((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    public String toString() {
        return "ANRequest{sequenceNumber='" + this.sequenceNumber + ", mMethod=" + this.mMethod + ", mPriority=" + this.mPriority + ", mRequestType=" + this.mRequestType + ", mUrl=" + this.mUrl + '}';
    }
}
