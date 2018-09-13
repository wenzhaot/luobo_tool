package com.meizu.cloud.pushsdk.networking.internal;

import com.meizu.cloud.pushsdk.networking.common.ANLog;
import com.meizu.cloud.pushsdk.networking.common.ANRequest;
import com.meizu.cloud.pushsdk.networking.common.Priority;
import com.meizu.cloud.pushsdk.networking.core.Core;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ANRequestQueue {
    private static final String TAG = ANRequestQueue.class.getSimpleName();
    private static ANRequestQueue sInstance = null;
    private final Set<ANRequest> mCurrentRequests = new HashSet();
    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    public interface RequestFilter {
        boolean apply(ANRequest aNRequest);
    }

    public static void initialize() {
        getInstance();
    }

    public static ANRequestQueue getInstance() {
        if (sInstance == null) {
            synchronized (ANRequestQueue.class) {
                if (sInstance == null) {
                    sInstance = new ANRequestQueue();
                }
            }
        }
        return sInstance;
    }

    private void cancel(RequestFilter filter, boolean forceCancel) {
        synchronized (this.mCurrentRequests) {
            try {
                Iterator<ANRequest> iterator = this.mCurrentRequests.iterator();
                while (iterator.hasNext()) {
                    ANRequest request = (ANRequest) iterator.next();
                    if (filter.apply(request)) {
                        request.cancel(forceCancel);
                        if (request.isCanceled()) {
                            request.destroy();
                            iterator.remove();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelAll(boolean forceCancel) {
        synchronized (this.mCurrentRequests) {
            try {
                Iterator<ANRequest> iterator = this.mCurrentRequests.iterator();
                while (iterator.hasNext()) {
                    ANRequest request = (ANRequest) iterator.next();
                    request.cancel(forceCancel);
                    if (request.isCanceled()) {
                        request.destroy();
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelRequestWithGivenTag(final Object tag, boolean forceCancel) {
        if (tag != null) {
            try {
                cancel(new RequestFilter() {
                    public boolean apply(ANRequest request) {
                        if ((request.getTag() instanceof String) && (tag instanceof String)) {
                            return ((String) request.getTag()).equals(tag);
                        }
                        return request.getTag().equals(tag);
                    }
                }, forceCancel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getSequenceNumber() {
        return this.mSequenceGenerator.incrementAndGet();
    }

    public ANRequest addRequest(ANRequest request) {
        synchronized (this.mCurrentRequests) {
            try {
                this.mCurrentRequests.add(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            request.setSequenceNumber(getSequenceNumber());
            if (request.getPriority() == Priority.IMMEDIATE) {
                request.setFuture(Core.getInstance().getExecutorSupplier().forImmediateNetworkTasks().submit(new InternalRunnable(request)));
            } else {
                request.setFuture(Core.getInstance().getExecutorSupplier().forNetworkTasks().submit(new InternalRunnable(request)));
            }
            ANLog.d("addRequest: after addition - mCurrentRequests size: " + this.mCurrentRequests.size());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return request;
    }

    public void finish(ANRequest request) {
        synchronized (this.mCurrentRequests) {
            try {
                this.mCurrentRequests.remove(request);
                ANLog.d("finish: after removal - mCurrentRequests size: " + this.mCurrentRequests.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
