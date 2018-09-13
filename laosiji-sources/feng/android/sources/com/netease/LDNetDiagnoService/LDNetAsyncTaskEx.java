package com.netease.LDNetDiagnoService;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class LDNetAsyncTaskEx<Params, Progress, Result> {
    private static final int MESSAGE_POST_CANCEL = 3;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    private static final LDNetInternalHandler sHandler = new LDNetInternalHandler();
    private final FutureTask<Result> mFuture = new FutureTask<Result>(this.mWorker) {
        protected void done() {
            Result result = null;
            try {
                result = get();
            } catch (InterruptedException e) {
                Log.w(getClass().getSimpleName(), e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
            } catch (CancellationException e3) {
                LDNetAsyncTaskEx.sHandler.obtainMessage(3, new LDNetAsyncTaskResult(LDNetAsyncTaskEx.this, (Object[]) null)).sendToTarget();
                return;
            } catch (Throwable t) {
                RuntimeException runtimeException = new RuntimeException("An error occured while executing doInBackground()", t);
            }
            LDNetAsyncTaskEx.sHandler.obtainMessage(1, new LDNetAsyncTaskResult(LDNetAsyncTaskEx.this, result)).sendToTarget();
        }
    };
    private volatile Status mStatus = Status.PENDING;
    private final LDNetWorkerRunnable<Params, Result> mWorker = new LDNetWorkerRunnable<Params, Result>() {
        public Result call() throws Exception {
            return LDNetAsyncTaskEx.this.doInBackground(this.mParams);
        }
    };

    private static abstract class LDNetWorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;

        private LDNetWorkerRunnable() {
        }

        /* synthetic */ LDNetWorkerRunnable(AnonymousClass1 x0) {
            this();
        }
    }

    private static class LDNetAsyncTaskResult<Data> {
        final Data[] mData;
        final LDNetAsyncTaskEx mTask;

        LDNetAsyncTaskResult(LDNetAsyncTaskEx task, Data... data) {
            this.mTask = task;
            this.mData = data;
        }
    }

    private static class LDNetInternalHandler extends Handler {
        private LDNetInternalHandler() {
        }

        /* synthetic */ LDNetInternalHandler(AnonymousClass1 x0) {
            this();
        }

        public void handleMessage(Message msg) {
            LDNetAsyncTaskResult result = msg.obj;
            switch (msg.what) {
                case 1:
                    result.mTask.finish(result.mData[0]);
                    return;
                case 2:
                    result.mTask.onProgressUpdate(result.mData);
                    return;
                case 3:
                    result.mTask.onCancelled();
                    return;
                default:
                    return;
            }
        }
    }

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    protected abstract Result doInBackground(Params... paramsArr);

    protected abstract ThreadPoolExecutor getThreadPoolExecutor();

    public final Status getStatus() {
        return this.mStatus;
    }

    protected void onPreExecute() {
    }

    protected void onPostExecute(Result result) {
    }

    protected void onProgressUpdate(Progress... progressArr) {
    }

    protected void onCancelled() {
    }

    public final boolean isCancelled() {
        return this.mFuture.isCancelled();
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        return this.mFuture.cancel(mayInterruptIfRunning);
    }

    public final LDNetAsyncTaskEx<Params, Progress, Result> execute(Params... params) {
        if (this.mStatus != Status.PENDING) {
            switch (this.mStatus) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.mStatus = Status.RUNNING;
        onPreExecute();
        this.mWorker.mParams = params;
        ThreadPoolExecutor sExecutor = getThreadPoolExecutor();
        if (sExecutor == null) {
            return null;
        }
        sExecutor.execute(this.mFuture);
        return this;
    }

    protected final void publishProgress(Progress... values) {
        sHandler.obtainMessage(2, new LDNetAsyncTaskResult(this, values)).sendToTarget();
    }

    protected void finish(Result result) {
        if (isCancelled()) {
            result = null;
        }
        onPostExecute(result);
        this.mStatus = Status.FINISHED;
    }
}
