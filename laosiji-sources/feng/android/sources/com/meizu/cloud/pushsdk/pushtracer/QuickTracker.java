package com.meizu.cloud.pushsdk.pushtracer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.networking.http.Call;
import com.meizu.cloud.pushsdk.pushtracer.emitter.BufferOption;
import com.meizu.cloud.pushsdk.pushtracer.emitter.Emitter;
import com.meizu.cloud.pushsdk.pushtracer.emitter.Emitter.EmitterBuilder;
import com.meizu.cloud.pushsdk.pushtracer.emitter.RequestCallback;
import com.meizu.cloud.pushsdk.pushtracer.tracker.Subject;
import com.meizu.cloud.pushsdk.pushtracer.tracker.Subject.SubjectBuilder;
import com.meizu.cloud.pushsdk.pushtracer.tracker.Tracker;
import com.meizu.cloud.pushsdk.pushtracer.tracker.Tracker.TrackerBuilder;
import com.meizu.cloud.pushsdk.pushtracer.utils.LogLevel;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import com.meizu.cloud.pushsdk.pushtracer.utils.Util;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuickTracker {
    private static AtomicBoolean isRegisterNetReceiver = new AtomicBoolean(false);
    public static final String namespace = "PushAndroidTracker";
    private static BroadcastReceiver networkBroadCastReceiver;
    private static Tracker tracker;

    public static Tracker init(Context context, boolean isUpload) {
        if (tracker == null) {
            synchronized (QuickTracker.class) {
                if (tracker == null) {
                    tracker = getTrackerClassic(getEmitterClassic(context, null, null), null, context);
                }
            }
        }
        DebugLogger.i(namespace, "can upload subject " + isUpload);
        if (isUpload) {
            tracker.setSubject(getSubject(context));
        }
        return tracker;
    }

    public static Tracker getAndroidTrackerClassic(Context context, RequestCallback callback) {
        return getAndroidTrackerClassic(context, null, callback);
    }

    public static Tracker getAndroidTrackerClassic(Context context, Call call, RequestCallback callback) {
        if (tracker == null) {
            synchronized (QuickTracker.class) {
                if (tracker == null) {
                    tracker = getTrackerClassic(getEmitterClassic(context, call, callback), null, context);
                }
                if (isRegisterNetReceiver.compareAndSet(false, true)) {
                    registerNetworkReceiver(context, tracker);
                }
            }
        }
        return tracker;
    }

    private static Tracker getTrackerClassic(Emitter emitter, Subject subject, Context context) {
        return new com.meizu.cloud.pushsdk.pushtracer.tracker.classic.Tracker(new TrackerBuilder(emitter, namespace, context.getPackageCodePath(), context, com.meizu.cloud.pushsdk.pushtracer.tracker.classic.Tracker.class).level(LogLevel.VERBOSE).base64(Boolean.valueOf(false)).subject(subject).threadCount(4));
    }

    private static Emitter getEmitterClassic(Context context, Call call, RequestCallback callback) {
        return new com.meizu.cloud.pushsdk.pushtracer.emitter.classic.Emitter(new EmitterBuilder(getStaticsDomain(), context, com.meizu.cloud.pushsdk.pushtracer.emitter.classic.Emitter.class).callback(callback).call(call).tick(1).option(BufferOption.DefaultGroup).sendLimit(BufferOption.DefaultGroup.getCode()).emptyLimit(2));
    }

    private static Subject getSubject(Context context) {
        return new SubjectBuilder().context(context).build();
    }

    private static void registerNetworkReceiver(Context context, final Tracker tracker) {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        BroadcastReceiver anonymousClass1 = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (Util.isOnline(context)) {
                    Logger.e("QuickTracker", "restart track event: %s", "online true");
                    tracker.restartEventTracking();
                }
            }
        };
        networkBroadCastReceiver = anonymousClass1;
        context.registerReceiver(anonymousClass1, filter);
    }

    private static void unRegisterNetworkReceiver(Context context) {
        if (networkBroadCastReceiver != null) {
            try {
                if (isRegisterNetReceiver.compareAndSet(true, false)) {
                    context.unregisterReceiver(networkBroadCastReceiver);
                }
            } catch (Exception e) {
                DebugLogger.e(namespace, "unRegisterReceiver exception " + e);
            }
        }
    }

    public static void destoryTracker(Context context) {
        if (tracker != null) {
            synchronized (QuickTracker.class) {
                if (tracker != null) {
                    unRegisterNetworkReceiver(context);
                    tracker = null;
                }
            }
        }
    }

    private static String getStaticsDomain() {
        String staticsDomian = "push-statics.meizu.com";
        if (MzSystemUtils.isInternational() || MzSystemUtils.isIndiaLocal()) {
            return "push-statics.in.meizu.com";
        }
        DebugLogger.e("QuickTracker", "current statics domain is " + staticsDomian);
        return staticsDomian;
    }
}
