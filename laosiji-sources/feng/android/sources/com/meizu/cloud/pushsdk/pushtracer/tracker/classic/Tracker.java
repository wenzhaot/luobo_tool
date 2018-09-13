package com.meizu.cloud.pushsdk.pushtracer.tracker.classic;

import com.meizu.cloud.pushsdk.pushtracer.emitter.classic.Executor;
import com.meizu.cloud.pushsdk.pushtracer.event.PushEvent;
import com.meizu.cloud.pushsdk.pushtracer.tracker.Session;
import com.meizu.cloud.pushsdk.pushtracer.tracker.Tracker.TrackerBuilder;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Tracker extends com.meizu.cloud.pushsdk.pushtracer.tracker.Tracker {
    private static final String TAG = Tracker.class.getSimpleName();
    private static ScheduledExecutorService sessionExecutor;

    public Tracker(TrackerBuilder builder) {
        super(builder);
        Executor.setThreadCount(this.threadCount);
        resumeSessionChecking();
    }

    public void resumeSessionChecking() {
        if (sessionExecutor == null && this.sessionContext) {
            Logger.d(TAG, "Session checking has been resumed.", new Object[0]);
            final Session session = this.trackerSession;
            sessionExecutor = Executors.newSingleThreadScheduledExecutor();
            sessionExecutor.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    session.checkAndUpdateSession();
                }
            }, this.sessionCheckInterval, this.sessionCheckInterval, this.timeUnit);
        }
    }

    public void pauseSessionChecking() {
        if (sessionExecutor != null) {
            Logger.d(TAG, "Session checking has been paused.", new Object[0]);
            sessionExecutor.shutdown();
            sessionExecutor = null;
        }
    }

    public void track(final PushEvent event) {
        Executor.execute(new Runnable() {
            public void run() {
                super.access$001(event);
            }
        });
    }

    public void track(final PushEvent event, final boolean attemptEmit) {
        Executor.execute(new Runnable() {
            public void run() {
                super.access$101(event, attemptEmit);
            }
        });
    }
}
