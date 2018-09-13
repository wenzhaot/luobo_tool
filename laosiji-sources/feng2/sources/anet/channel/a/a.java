package anet.channel.a;

import com.baidu.mapapi.UIMsg.d_ResultType;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: Taobao */
class a implements Comparable<a>, Runnable, Future {
    Runnable a = null;
    int b = 0;
    long c = System.currentTimeMillis();
    volatile boolean d = false;
    volatile Future<?> e = null;

    public a(Runnable runnable, int i) {
        this.a = runnable;
        if (i < 0) {
            i = 0;
        }
        this.b = i;
        this.c = System.currentTimeMillis();
    }

    /* renamed from: a */
    public int compareTo(a aVar) {
        if (this.b != aVar.b) {
            return this.b - aVar.b;
        }
        return (int) (aVar.c - this.c);
    }

    public void run() {
        try {
            if (!this.d) {
                if (this.b <= 6) {
                    this.e = d.a().submit(this.a);
                } else {
                    this.e = d.b().submit(this.a);
                }
            }
        } catch (RejectedExecutionException e) {
            this.b++;
            c.a(this, (long) ((this.b + 1) * d_ResultType.SHORT_URL), TimeUnit.MILLISECONDS);
        }
    }

    public boolean cancel(boolean z) {
        this.d = true;
        if (this.e != null) {
            return this.e.cancel(z);
        }
        return true;
    }

    public boolean isCancelled() {
        return this.d;
    }

    public boolean isDone() {
        return false;
    }

    public Object get() throws InterruptedException, ExecutionException {
        throw new RuntimeException("NOT SUPPORT!");
    }

    public Object get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new RuntimeException("NOT SUPPORT!");
    }
}
