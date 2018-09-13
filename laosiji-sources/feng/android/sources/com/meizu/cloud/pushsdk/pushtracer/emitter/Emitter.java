package com.meizu.cloud.pushsdk.pushtracer.emitter;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.feng.car.utils.FengConstant;
import com.meizu.cloud.pushsdk.networking.http.Call;
import com.meizu.cloud.pushsdk.networking.http.HttpURLConnectionCall;
import com.meizu.cloud.pushsdk.networking.http.MediaType;
import com.meizu.cloud.pushsdk.networking.http.Request;
import com.meizu.cloud.pushsdk.networking.http.RequestBody;
import com.meizu.cloud.pushsdk.networking.http.Response;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.meizu.cloud.pushsdk.pushtracer.constant.TrackerConstants;
import com.meizu.cloud.pushsdk.pushtracer.dataload.DataLoad;
import com.meizu.cloud.pushsdk.pushtracer.dataload.SelfDescribingJson;
import com.meizu.cloud.pushsdk.pushtracer.storage.Store;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import com.meizu.cloud.pushsdk.pushtracer.utils.Util;
import com.umeng.commonsdk.proguard.g;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public abstract class Emitter {
    protected final MediaType JSON = MediaType.parse(TrackerConstants.POST_CONTENT_TYPE);
    protected int POST_STM_BYTES = 22;
    protected int POST_WRAPPER_BYTES = 88;
    private final String TAG = Emitter.class.getSimpleName();
    protected BufferOption bufferOption;
    protected long byteLimitGet;
    protected long byteLimitPost;
    protected Call call;
    protected Context context;
    protected int emitterTick;
    protected int emptyLimit;
    protected HostnameVerifier hostnameVerifier;
    protected HttpMethod httpMethod;
    protected AtomicBoolean isRunning = new AtomicBoolean(false);
    protected RequestCallback requestCallback;
    protected RequestSecurity requestSecurity;
    protected int sendLimit;
    protected SSLSocketFactory sslSocketFactory;
    protected TimeUnit timeUnit;
    protected String uri;
    protected Builder uriBuilder;

    public static class EmitterBuilder {
        protected static Class<? extends Emitter> defaultEmitterClass;
        protected BufferOption bufferOption;
        protected long byteLimitGet;
        protected long byteLimitPost;
        protected Call call;
        protected final Context context;
        private Class<? extends Emitter> emitterClass;
        protected int emitterTick;
        protected int emptyLimit;
        protected HostnameVerifier hostnameVerifier;
        protected HttpMethod httpMethod;
        protected RequestCallback requestCallback;
        protected RequestSecurity requestSecurity;
        protected int sendLimit;
        protected SSLSocketFactory sslSocketFactory;
        protected TimeUnit timeUnit;
        protected final String uri;

        public EmitterBuilder(String uri, Context context) {
            this(uri, context, defaultEmitterClass);
        }

        public EmitterBuilder(String uri, Context context, Class<? extends Emitter> emitterClass) {
            this.requestCallback = null;
            this.httpMethod = HttpMethod.POST;
            this.bufferOption = BufferOption.Single;
            this.requestSecurity = RequestSecurity.HTTPS;
            this.emitterTick = 5;
            this.sendLimit = FengConstant.IMAGE_SMALL_WIDTH;
            this.emptyLimit = 5;
            this.byteLimitGet = 40000;
            this.byteLimitPost = 40000;
            this.timeUnit = TimeUnit.SECONDS;
            this.call = new HttpURLConnectionCall();
            this.uri = uri;
            this.context = context;
            this.emitterClass = emitterClass;
        }

        public EmitterBuilder method(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public EmitterBuilder option(BufferOption option) {
            this.bufferOption = option;
            return this;
        }

        public EmitterBuilder security(RequestSecurity requestSecurity) {
            this.requestSecurity = requestSecurity;
            return this;
        }

        public EmitterBuilder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public EmitterBuilder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public EmitterBuilder callback(RequestCallback requestCallback) {
            this.requestCallback = requestCallback;
            return this;
        }

        public EmitterBuilder tick(int emitterTick) {
            this.emitterTick = emitterTick;
            return this;
        }

        public EmitterBuilder sendLimit(int sendLimit) {
            this.sendLimit = sendLimit;
            return this;
        }

        public EmitterBuilder emptyLimit(int emptyLimit) {
            this.emptyLimit = emptyLimit;
            return this;
        }

        public EmitterBuilder byteLimitGet(long byteLimitGet) {
            this.byteLimitGet = byteLimitGet;
            return this;
        }

        public EmitterBuilder byteLimitPost(long byteLimitPost) {
            this.byteLimitPost = byteLimitPost;
            return this;
        }

        public EmitterBuilder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public EmitterBuilder call(Call call) {
            if (call != null) {
                this.call = call;
                Logger.i(EmitterBuilder.class.getSimpleName(), "set new call " + call, new Object[0]);
            }
            return this;
        }

        public EmitterBuilder call(Class<Call> clazz) {
            try {
                this.call = (Call) clazz.newInstance();
                Logger.i(EmitterBuilder.class.getSimpleName(), "set new call " + this.call, new Object[0]);
            } catch (Exception e) {
                Logger.i(EmitterBuilder.class.getSimpleName(), "error new call " + e, new Object[0]);
            }
            return this;
        }
    }

    public abstract void add(DataLoad dataLoad);

    public abstract void add(DataLoad dataLoad, boolean z);

    public abstract void flush();

    public abstract boolean getEmitterStatus();

    public abstract Store getEventStore();

    public abstract void shutdown();

    public Emitter(EmitterBuilder builder) {
        this.httpMethod = builder.httpMethod;
        this.requestCallback = builder.requestCallback;
        this.context = builder.context;
        this.bufferOption = builder.bufferOption;
        this.requestSecurity = builder.requestSecurity;
        this.sslSocketFactory = builder.sslSocketFactory;
        this.hostnameVerifier = builder.hostnameVerifier;
        this.emitterTick = builder.emitterTick;
        this.emptyLimit = builder.emptyLimit;
        this.sendLimit = builder.sendLimit;
        this.byteLimitGet = builder.byteLimitGet;
        this.byteLimitPost = builder.byteLimitPost;
        this.uri = builder.uri;
        this.timeUnit = builder.timeUnit;
        this.call = builder.call;
        buildEmitterUri();
        Logger.i(this.TAG, "Emitter created successfully!", new Object[0]);
    }

    private void buildEmitterUri() {
        Logger.e(this.TAG, "security " + this.requestSecurity, new Object[0]);
        if (this.requestSecurity == RequestSecurity.HTTP) {
            this.uriBuilder = Uri.parse("http://" + this.uri).buildUpon();
        } else {
            this.uriBuilder = Uri.parse("https://" + this.uri).buildUpon();
        }
        if (this.httpMethod == HttpMethod.GET) {
            this.uriBuilder.appendPath(g.aq);
        } else {
            this.uriBuilder.appendEncodedPath("push_data_report/mobile");
        }
    }

    private void buildHttpsSecurity() {
        if (this.requestSecurity == RequestSecurity.HTTPS) {
            if (this.sslSocketFactory == null) {
                Logger.e(this.TAG, "Https Ensure you have set SSLSocketFactory", new Object[0]);
            }
            if (this.hostnameVerifier == null) {
                Logger.e(this.TAG, "Https Ensure you have set HostnameVerifier", new Object[0]);
            }
        }
    }

    protected LinkedList<RequestResult> performSyncEmit(LinkedList<ReadyRequest> requests) {
        LinkedList<RequestResult> results = new LinkedList();
        Iterator it = requests.iterator();
        while (it.hasNext()) {
            ReadyRequest request = (ReadyRequest) it.next();
            int code = requestSender(request.getRequest());
            if (request.isOversize()) {
                results.add(new RequestResult(true, request.getEventIds()));
            } else {
                results.add(new RequestResult(isSuccessfulSend(code), request.getEventIds()));
            }
        }
        return results;
    }

    protected int requestSender(Request request) {
        int code;
        Response response = null;
        try {
            Logger.d(this.TAG, "Sending request: %s", request);
            response = this.call.execute(request);
            code = response.code();
        } catch (IOException e) {
            Logger.e(this.TAG, "Request sending failed: %s", e.toString());
            code = -1;
        } finally {
            close(response);
        }
        return code;
    }

    protected void close(Response response) {
        if (response != null) {
            try {
                if (response.body() != null) {
                    response.body().close();
                }
            } catch (Exception e) {
                Logger.d(this.TAG, "Unable to close source data", new Object[0]);
            }
        }
    }

    protected LinkedList<ReadyRequest> buildRequests(EmittableEvents events) {
        int dataLoadCount = events.getEvents().size();
        LinkedList<Long> eventIds = events.getEventIds();
        LinkedList<ReadyRequest> requests = new LinkedList();
        int i;
        LinkedList<Long> reqEventId;
        DataLoad dataLoad;
        if (this.httpMethod == HttpMethod.GET) {
            for (i = 0; i < dataLoadCount; i++) {
                reqEventId = new LinkedList();
                reqEventId.add(eventIds.get(i));
                dataLoad = (DataLoad) events.getEvents().get(i);
                requests.add(new ReadyRequest(dataLoad.getByteSize() + ((long) this.POST_STM_BYTES) > this.byteLimitGet, requestBuilderGet(dataLoad), reqEventId));
            }
        } else {
            i = 0;
            while (i < dataLoadCount) {
                LinkedList<Long> reqEventIds = new LinkedList();
                ArrayList<DataLoad> postDataLoadMaps = new ArrayList();
                long totalByteSize = 0;
                int j = i;
                while (j < this.bufferOption.getCode() + i && j < dataLoadCount) {
                    dataLoad = (DataLoad) events.getEvents().get(j);
                    long payloadByteSize = dataLoad.getByteSize() + ((long) this.POST_STM_BYTES);
                    if (((long) this.POST_WRAPPER_BYTES) + payloadByteSize > this.byteLimitPost) {
                        ArrayList<DataLoad> singlePayloadMap = new ArrayList();
                        reqEventId = new LinkedList();
                        singlePayloadMap.add(dataLoad);
                        reqEventId.add(eventIds.get(j));
                        requests.add(new ReadyRequest(true, requestBuilderPost(singlePayloadMap), reqEventId));
                    } else if (((totalByteSize + payloadByteSize) + ((long) this.POST_WRAPPER_BYTES)) + ((long) (postDataLoadMaps.size() - 1)) > this.byteLimitPost) {
                        requests.add(new ReadyRequest(false, requestBuilderPost(postDataLoadMaps), reqEventIds));
                        postDataLoadMaps = new ArrayList();
                        reqEventIds = new LinkedList();
                        postDataLoadMaps.add(dataLoad);
                        reqEventIds.add(eventIds.get(j));
                        totalByteSize = payloadByteSize;
                    } else {
                        totalByteSize += payloadByteSize;
                        postDataLoadMaps.add(dataLoad);
                        reqEventIds.add(eventIds.get(j));
                    }
                    j++;
                }
                if (!postDataLoadMaps.isEmpty()) {
                    requests.add(new ReadyRequest(false, requestBuilderPost(postDataLoadMaps), reqEventIds));
                }
                i += this.bufferOption.getCode();
            }
        }
        return requests;
    }

    private Request requestBuilderGet(DataLoad dataLoad) {
        addStmToEvent(dataLoad, "");
        this.uriBuilder.clearQuery();
        HashMap hashMap = (HashMap) dataLoad.getMap();
        for (String key : hashMap.keySet()) {
            this.uriBuilder.appendQueryParameter(key, (String) hashMap.get(key));
        }
        return new Request.Builder().url(this.uriBuilder.build().toString()).get().build();
    }

    private Request requestBuilderPost(ArrayList<DataLoad> dataLoads) {
        Object finalPayloads = new ArrayList();
        StringBuffer dataloadbuffer = new StringBuffer();
        Iterator it = dataLoads.iterator();
        while (it.hasNext()) {
            DataLoad payload = (DataLoad) it.next();
            dataloadbuffer.append(payload.toString());
            finalPayloads.add(payload.getMap());
        }
        SelfDescribingJson postPayload = new SelfDescribingJson("push_group_data", finalPayloads);
        Logger.d(this.TAG, "final SelfDescribingJson " + postPayload, new Object[0]);
        String reqUrl = this.uriBuilder.build().toString();
        return new Request.Builder().url(reqUrl).post(RequestBody.create(this.JSON, postPayload.toString())).build();
    }

    private void addStmToEvent(DataLoad dataLoad, String timestamp) {
        String str = Parameters.SENT_TIMESTAMP;
        if (timestamp.equals("")) {
            timestamp = Util.getTimestamp();
        }
        dataLoad.add(str, timestamp);
    }

    protected boolean isSuccessfulSend(int code) {
        return code >= 200 && code < 300;
    }

    public void setBufferOption(BufferOption option) {
        if (!this.isRunning.get()) {
            this.bufferOption = option;
        }
    }

    public void setHttpMethod(HttpMethod method) {
        if (!this.isRunning.get()) {
            this.httpMethod = method;
            buildEmitterUri();
        }
    }

    public void setRequestSecurity(RequestSecurity security) {
        if (!this.isRunning.get()) {
            this.requestSecurity = security;
            buildEmitterUri();
        }
    }

    public void setEmitterUri(String uri) {
        if (!this.isRunning.get()) {
            this.uri = uri;
            buildEmitterUri();
        }
    }

    public String getEmitterUri() {
        return this.uriBuilder.clearQuery().build().toString();
    }

    public RequestCallback getRequestCallback() {
        return this.requestCallback;
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public BufferOption getBufferOption() {
        return this.bufferOption;
    }

    public RequestSecurity getRequestSecurity() {
        return this.requestSecurity;
    }

    public int getEmitterTick() {
        return this.emitterTick;
    }

    public int getEmptyLimit() {
        return this.emptyLimit;
    }

    public int getSendLimit() {
        return this.sendLimit;
    }

    public long getByteLimitGet() {
        return this.byteLimitGet;
    }

    public long getByteLimitPost() {
        return this.byteLimitPost;
    }
}
