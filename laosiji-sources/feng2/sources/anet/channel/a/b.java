package anet.channel.a;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: Taobao */
class b extends ThreadPoolExecutor {

    /* compiled from: Taobao */
    class a<V> extends FutureTask<V> implements Comparable<a<V>> {
        private Object b;

        public a(Callable<V> callable) {
            super(callable);
            this.b = callable;
        }

        public a(Runnable runnable, V v) {
            super(runnable, v);
            this.b = runnable;
        }

        /* renamed from: a */
        public int compareTo(a<V> aVar) {
            if (this == aVar) {
                return 0;
            }
            if (aVar == null) {
                return -1;
            }
            if (this.b == null || aVar.b == null || !this.b.getClass().equals(aVar.b.getClass()) || !(this.b instanceof Comparable)) {
                return 0;
            }
            return ((Comparable) this.b).compareTo(aVar.b);
        }
    }

    public b(int i, ThreadFactory threadFactory) {
        super(i, i, 60, TimeUnit.SECONDS, new PriorityBlockingQueue(), threadFactory);
    }

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new a(runnable, t);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new a(callable);
    }
}
