package com.meizu.cloud.pushsdk.pushtracer.tracker;

import android.content.Context;
import com.meizu.cloud.pushsdk.pushtracer.constant.TrackerConstants;
import com.meizu.cloud.pushsdk.pushtracer.dataload.SelfDescribingJson;
import com.meizu.cloud.pushsdk.pushtracer.dataload.TrackerDataload;
import com.meizu.cloud.pushsdk.pushtracer.emitter.Emitter;
import com.meizu.cloud.pushsdk.pushtracer.event.PushEvent;
import com.meizu.cloud.pushsdk.pushtracer.utils.LogLevel;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Tracker {
    private static final String TAG = Tracker.class.getSimpleName();
    protected String appId;
    protected boolean base64Encoded;
    protected AtomicBoolean dataCollection = new AtomicBoolean(true);
    protected Emitter emitter;
    protected LogLevel level;
    protected String namespace;
    protected long sessionCheckInterval;
    protected boolean sessionContext;
    protected Subject subject;
    protected int threadCount;
    protected TimeUnit timeUnit;
    protected Session trackerSession;
    protected final String trackerVersion = "3.5.0-SNAPSHOT";

    public static class TrackerBuilder {
        protected static Class<? extends Tracker> defaultTrackerClass;
        protected final String appId;
        protected long backgroundTimeout;
        protected boolean base64Encoded;
        protected final Context context;
        protected final Emitter emitter;
        protected long foregroundTimeout;
        protected LogLevel logLevel;
        protected final String namespace;
        protected long sessionCheckInterval;
        protected boolean sessionContext;
        protected Subject subject;
        protected int threadCount;
        protected TimeUnit timeUnit;
        private Class<? extends Tracker> trackerClass;

        public TrackerBuilder(Emitter emitter, String namespace, String appId, Context context) {
            this(emitter, namespace, appId, context, defaultTrackerClass);
        }

        public TrackerBuilder(Emitter emitter, String namespace, String appId, Context context, Class<? extends Tracker> trackerClass) {
            this.subject = null;
            this.base64Encoded = false;
            this.logLevel = LogLevel.OFF;
            this.sessionContext = false;
            this.foregroundTimeout = 600;
            this.backgroundTimeout = 300;
            this.sessionCheckInterval = 15;
            this.threadCount = 10;
            this.timeUnit = TimeUnit.SECONDS;
            this.emitter = emitter;
            this.namespace = namespace;
            this.appId = appId;
            this.context = context;
            this.trackerClass = trackerClass;
        }

        public TrackerBuilder subject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public TrackerBuilder base64(Boolean base64) {
            this.base64Encoded = base64.booleanValue();
            return this;
        }

        public TrackerBuilder level(LogLevel log) {
            this.logLevel = log;
            return this;
        }

        public TrackerBuilder sessionContext(boolean sessionContext) {
            this.sessionContext = sessionContext;
            return this;
        }

        public TrackerBuilder foregroundTimeout(long timeout) {
            this.foregroundTimeout = timeout;
            return this;
        }

        public TrackerBuilder backgroundTimeout(long timeout) {
            this.backgroundTimeout = timeout;
            return this;
        }

        public TrackerBuilder sessionCheckInterval(long sessionCheckInterval) {
            this.sessionCheckInterval = sessionCheckInterval;
            return this;
        }

        public TrackerBuilder threadCount(int threadCount) {
            this.threadCount = threadCount;
            return this;
        }

        public TrackerBuilder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }
    }

    public abstract void pauseSessionChecking();

    public abstract void resumeSessionChecking();

    public Tracker(TrackerBuilder builder) {
        int i = 2;
        this.emitter = builder.emitter;
        this.appId = builder.appId;
        this.base64Encoded = builder.base64Encoded;
        this.namespace = builder.namespace;
        this.subject = builder.subject;
        this.level = builder.logLevel;
        this.sessionContext = builder.sessionContext;
        this.sessionCheckInterval = builder.sessionCheckInterval;
        if (builder.threadCount >= 2) {
            i = builder.threadCount;
        }
        this.threadCount = i;
        this.timeUnit = builder.timeUnit;
        if (this.sessionContext) {
            this.trackerSession = new Session(builder.foregroundTimeout, builder.backgroundTimeout, builder.timeUnit, builder.context);
        }
        Logger.updateLogLevel(builder.logLevel);
        Logger.i(TAG, "Tracker created successfully.", new Object[0]);
    }

    private void addEventPayload(TrackerDataload payload, List<SelfDescribingJson> context, boolean attemptEmit) {
        if (this.subject != null) {
            payload.addMap(new HashMap(this.subject.getSubject()));
            payload.add("et", getFinalContext(context).getMap());
        }
        Logger.i(TAG, "Adding new payload to event storage: %s", payload);
        this.emitter.add(payload, attemptEmit);
    }

    private SelfDescribingJson getFinalContext(List<SelfDescribingJson> context) {
        if (this.sessionContext) {
            context.add(this.trackerSession.getSessionContext());
        }
        if (this.subject != null) {
            if (!this.subject.getSubjectLocation().isEmpty()) {
                context.add(new SelfDescribingJson(TrackerConstants.GEOLOCATION_SCHEMA, this.subject.getSubjectLocation()));
            }
            if (!this.subject.getSubjectMobile().isEmpty()) {
                context.add(new SelfDescribingJson(TrackerConstants.MOBILE_SCHEMA, this.subject.getSubjectMobile()));
            }
        }
        Object contextMaps = new LinkedList();
        for (SelfDescribingJson selfDescribingJson : context) {
            contextMaps.add(selfDescribingJson.getMap());
        }
        return new SelfDescribingJson(TrackerConstants.SCHEMA_CONTEXTS, contextMaps);
    }

    public void track(PushEvent event, boolean attemptEmit) {
        if (this.dataCollection.get()) {
            addEventPayload(event.getDataLoad(), event.getSelfDescribingJson(), attemptEmit);
        }
    }

    public void track(PushEvent event) {
        track(event, true);
    }

    public void pauseEventTracking() {
        if (this.dataCollection.compareAndSet(true, false)) {
            pauseSessionChecking();
            getEmitter().shutdown();
        }
    }

    public void resumeEventTracking() {
        if (this.dataCollection.compareAndSet(false, true)) {
            resumeSessionChecking();
            getEmitter().flush();
        }
    }

    public void restartEventTracking() {
        if (this.dataCollection.get()) {
            getEmitter().flush();
        }
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setEmitter(Emitter emitter) {
        getEmitter().shutdown();
        this.emitter = emitter;
    }

    public String getTrackerVersion() {
        getClass();
        return "3.5.0-SNAPSHOT";
    }

    public Subject getSubject() {
        return this.subject;
    }

    public Emitter getEmitter() {
        return this.emitter;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getAppId() {
        return this.appId;
    }

    public boolean getBase64Encoded() {
        return this.base64Encoded;
    }

    public LogLevel getLogLevel() {
        return this.level;
    }

    public Session getSession() {
        return this.trackerSession;
    }

    public boolean getDataCollection() {
        return this.dataCollection.get();
    }

    public int getThreadCount() {
        return this.threadCount;
    }
}
