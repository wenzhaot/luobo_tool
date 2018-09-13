package com.meizu.cloud.pushsdk.pushtracer.emitter.classic;

import com.meizu.cloud.pushsdk.networking.http.Request;
import com.meizu.cloud.pushsdk.pushtracer.dataload.DataLoad;
import com.meizu.cloud.pushsdk.pushtracer.emitter.Emitter.EmitterBuilder;
import com.meizu.cloud.pushsdk.pushtracer.emitter.ReadyRequest;
import com.meizu.cloud.pushsdk.pushtracer.emitter.RequestResult;
import com.meizu.cloud.pushsdk.pushtracer.storage.EventStore;
import com.meizu.cloud.pushsdk.pushtracer.storage.MemoryStore;
import com.meizu.cloud.pushsdk.pushtracer.storage.Store;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import com.meizu.cloud.pushsdk.pushtracer.utils.Util;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Emitter extends com.meizu.cloud.pushsdk.pushtracer.emitter.Emitter {
    private final String TAG = Emitter.class.getSimpleName();
    private int emptyCount;
    private Store eventStore = new EventStore(this.context, this.sendLimit);

    public Emitter(EmitterBuilder builder) {
        super(builder);
        if (!this.eventStore.isOpen()) {
            this.eventStore = new MemoryStore(this.sendLimit);
            Logger.e(this.TAG, "init memory store", new Object[0]);
        }
    }

    public void add(DataLoad payload) {
        this.eventStore.add(payload);
        Logger.e(this.TAG, "isRunning " + this.isRunning, new Object[0]);
        if (this.isRunning.compareAndSet(false, true)) {
            attemptEmit();
        }
    }

    public void add(DataLoad payload, boolean attemptEmit) {
        this.eventStore.add(payload);
        Logger.e(this.TAG, "isRunning " + this.isRunning + " attemptEmit " + attemptEmit, new Object[0]);
        if (!attemptEmit) {
            try {
                this.timeUnit.sleep(1);
            } catch (InterruptedException e) {
                Logger.e(this.TAG, "Emitter add thread sleep interrupted: " + e.toString(), new Object[0]);
            }
        }
        if (this.isRunning.compareAndSet(false, true)) {
            attemptEmit();
        }
    }

    public void flush() {
        Executor.execute(new Runnable() {
            public void run() {
                if (Emitter.this.isRunning.compareAndSet(false, true)) {
                    Emitter.this.attemptEmit();
                }
            }
        });
    }

    private void attemptEmit() {
        if (!Util.isOnline(this.context)) {
            Logger.e(this.TAG, "Emitter loop stopping: emitter offline.", new Object[0]);
            this.isRunning.compareAndSet(true, false);
        } else if (this.eventStore.getSize() > 0) {
            this.emptyCount = 0;
            LinkedList<RequestResult> results = performAsyncEmit(buildRequests(this.eventStore.getEmittableEvents()));
            Logger.i(this.TAG, "Processing emitter results.", new Object[0]);
            int successCount = 0;
            int failureCount = 0;
            LinkedList<Long> removableEvents = new LinkedList();
            Iterator it = results.iterator();
            while (it.hasNext()) {
                RequestResult res = (RequestResult) it.next();
                if (res.getSuccess()) {
                    Iterator it2 = res.getEventIds().iterator();
                    while (it2.hasNext()) {
                        removableEvents.add((Long) it2.next());
                    }
                    successCount += res.getEventIds().size();
                } else {
                    failureCount += res.getEventIds().size();
                    Logger.e(this.TAG, "Request sending failed but we will retry later.", new Object[0]);
                }
            }
            performAsyncEventRemoval(removableEvents);
            Logger.d(this.TAG, "Success Count: %s", Integer.valueOf(successCount));
            Logger.d(this.TAG, "Failure Count: %s", Integer.valueOf(failureCount));
            if (this.requestCallback != null) {
                if (failureCount != 0) {
                    this.requestCallback.onFailure(successCount, failureCount);
                } else {
                    this.requestCallback.onSuccess(successCount);
                }
            }
            if (failureCount <= 0 || successCount != 0) {
                attemptEmit();
                return;
            }
            if (Util.isOnline(this.context)) {
                Logger.e(this.TAG, "Ensure collector path is valid: %s", getEmitterUri());
            }
            Logger.e(this.TAG, "Emitter loop stopping: failures.", new Object[0]);
            this.isRunning.compareAndSet(true, false);
        } else if (this.emptyCount >= this.emptyLimit) {
            Logger.e(this.TAG, "Emitter loop stopping: empty limit reached.", new Object[0]);
            this.isRunning.compareAndSet(true, false);
            if (this.requestCallback != null) {
                this.requestCallback.isEmpty(true);
            }
        } else {
            this.emptyCount++;
            Logger.e(this.TAG, "Emitter database empty: " + this.emptyCount, new Object[0]);
            try {
                this.timeUnit.sleep((long) this.emitterTick);
            } catch (InterruptedException e) {
                Logger.e(this.TAG, "Emitter thread sleep interrupted: " + e.toString(), new Object[0]);
            }
            attemptEmit();
        }
    }

    private LinkedList<RequestResult> performAsyncEmit(LinkedList<ReadyRequest> requests) {
        LinkedList<RequestResult> results = new LinkedList();
        LinkedList<Future> futures = new LinkedList();
        Iterator it = requests.iterator();
        while (it.hasNext()) {
            futures.add(Executor.futureCallable(getRequestCallable(((ReadyRequest) it.next()).getRequest())));
        }
        Logger.d(this.TAG, "Request Futures: %s", Integer.valueOf(futures.size()));
        for (int i = 0; i < futures.size(); i++) {
            int code = -1;
            try {
                code = ((Integer) ((Future) futures.get(i)).get(5, TimeUnit.SECONDS)).intValue();
            } catch (InterruptedException ie) {
                Logger.e(this.TAG, "Request Future was interrupted: %s", ie.getMessage());
            } catch (ExecutionException ee) {
                Logger.e(this.TAG, "Request Future failed: %s", ee.getMessage());
            } catch (TimeoutException te) {
                Logger.e(this.TAG, "Request Future had a timeout: %s", te.getMessage());
            }
            if (((ReadyRequest) requests.get(i)).isOversize()) {
                results.add(new RequestResult(true, ((ReadyRequest) requests.get(i)).getEventIds()));
            } else {
                results.add(new RequestResult(isSuccessfulSend(code), ((ReadyRequest) requests.get(i)).getEventIds()));
            }
        }
        return results;
    }

    private LinkedList<Boolean> performAsyncEventRemoval(LinkedList<Long> eventIds) {
        LinkedList<Boolean> results = new LinkedList();
        LinkedList<Future> futures = new LinkedList();
        Iterator it = eventIds.iterator();
        while (it.hasNext()) {
            futures.add(Executor.futureCallable(getRemoveCallable((Long) it.next())));
        }
        Logger.d(this.TAG, "Removal Futures: %s", Integer.valueOf(futures.size()));
        for (int i = 0; i < futures.size(); i++) {
            boolean result = false;
            try {
                result = ((Boolean) ((Future) futures.get(i)).get(5, TimeUnit.SECONDS)).booleanValue();
            } catch (InterruptedException ie) {
                Logger.e(this.TAG, "Removal Future was interrupted: %s", ie.getMessage());
            } catch (ExecutionException ee) {
                Logger.e(this.TAG, "Removal Future failed: %s", ee.getMessage());
            } catch (TimeoutException te) {
                Logger.e(this.TAG, "Removal Future had a timeout: %s", te.getMessage());
            }
            results.add(Boolean.valueOf(result));
        }
        return results;
    }

    private Callable<Integer> getRequestCallable(final Request request) {
        return new Callable<Integer>() {
            public Integer call() throws Exception {
                return Integer.valueOf(Emitter.this.access$200(request));
            }
        };
    }

    private Callable<Boolean> getRemoveCallable(final Long eventId) {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Boolean.valueOf(Emitter.this.eventStore.removeEvent(eventId.longValue()));
            }
        };
    }

    public void shutdown() {
        Logger.d(this.TAG, "Shutting down emitter.", new Object[0]);
        this.isRunning.compareAndSet(true, false);
        Executor.shutdown();
    }

    public Store getEventStore() {
        return this.eventStore;
    }

    public boolean getEmitterStatus() {
        return this.isRunning.get();
    }
}
