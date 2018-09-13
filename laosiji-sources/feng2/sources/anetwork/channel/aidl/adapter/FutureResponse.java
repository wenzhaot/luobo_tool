package anetwork.channel.aidl.adapter;

import anet.channel.util.ALog;
import anetwork.channel.Response;
import anetwork.channel.aidl.ParcelableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: Taobao */
public class FutureResponse implements Future<Response> {
    private static final String TAG = "anet.FutureResponse";
    private static final int TIMEOUT = 20000;
    private ParcelableFuture delegate;

    public FutureResponse(ParcelableFuture parcelableFuture) {
        this.delegate = parcelableFuture;
    }

    public boolean cancel(boolean z) {
        boolean z2 = false;
        if (this.delegate == null) {
            return z2;
        }
        try {
            return this.delegate.cancel(z);
        } catch (Throwable e) {
            ALog.w(TAG, "[cancel]", null, e, new Object[z2]);
            return z2;
        }
    }

    public boolean isCancelled() {
        boolean z = false;
        try {
            return this.delegate.isCancelled();
        } catch (Throwable e) {
            ALog.w(TAG, "[isCancelled]", null, e, new Object[z]);
            return z;
        }
    }

    public boolean isDone() {
        try {
            return this.delegate.isDone();
        } catch (Throwable e) {
            ALog.w(TAG, "[isDone]", null, e, new Object[0]);
            return true;
        }
    }

    public Response get() throws InterruptedException, ExecutionException {
        Response response = null;
        if (this.delegate == null) {
            return response;
        }
        try {
            return this.delegate.get(20000);
        } catch (Throwable e) {
            ALog.w(TAG, "[get]", response, e, new Object[0]);
            return response;
        }
    }

    public Response get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        Response response = null;
        if (this.delegate == null) {
            return response;
        }
        try {
            return this.delegate.get(j);
        } catch (Throwable e) {
            ALog.w(TAG, "[get(long timeout, TimeUnit unit)]", response, e, new Object[0]);
            return response;
        }
    }

    public void setFuture(ParcelableFuture parcelableFuture) {
        this.delegate = parcelableFuture;
    }
}
