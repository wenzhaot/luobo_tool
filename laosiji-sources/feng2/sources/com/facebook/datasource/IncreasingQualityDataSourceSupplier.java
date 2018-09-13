package com.facebook.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class IncreasingQualityDataSourceSupplier<T> implements Supplier<DataSource<T>> {
    private final List<Supplier<DataSource<T>>> mDataSourceSuppliers;

    @ThreadSafe
    private class IncreasingQualityDataSource extends AbstractDataSource<T> {
        @GuardedBy("IncreasingQualityDataSource.this")
        @Nullable
        private ArrayList<DataSource<T>> mDataSources;
        @GuardedBy("IncreasingQualityDataSource.this")
        private int mIndexOfDataSourceWithResult;

        private class InternalDataSubscriber implements DataSubscriber<T> {
            private int mIndex;

            public InternalDataSubscriber(int index) {
                this.mIndex = index;
            }

            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.hasResult()) {
                    IncreasingQualityDataSource.this.onDataSourceNewResult(this.mIndex, dataSource);
                } else if (dataSource.isFinished()) {
                    IncreasingQualityDataSource.this.onDataSourceFailed(this.mIndex, dataSource);
                }
            }

            public void onFailure(DataSource<T> dataSource) {
                IncreasingQualityDataSource.this.onDataSourceFailed(this.mIndex, dataSource);
            }

            public void onCancellation(DataSource<T> dataSource) {
            }

            public void onProgressUpdate(DataSource<T> dataSource) {
                if (this.mIndex == 0) {
                    IncreasingQualityDataSource.this.setProgress(dataSource.getProgress());
                }
            }
        }

        public IncreasingQualityDataSource() {
            int n = IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.size();
            this.mIndexOfDataSourceWithResult = n;
            this.mDataSources = new ArrayList(n);
            int i = 0;
            while (i < n) {
                DataSource<T> dataSource = (DataSource) ((Supplier) IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.get(i)).get();
                this.mDataSources.add(dataSource);
                dataSource.subscribe(new InternalDataSubscriber(i), CallerThreadExecutor.getInstance());
                if (!dataSource.hasResult()) {
                    i++;
                } else {
                    return;
                }
            }
        }

        @Nullable
        private synchronized DataSource<T> getDataSource(int i) {
            DataSource<T> dataSource;
            dataSource = (this.mDataSources == null || i >= this.mDataSources.size()) ? null : (DataSource) this.mDataSources.get(i);
            return dataSource;
        }

        @Nullable
        private synchronized DataSource<T> getAndClearDataSource(int i) {
            DataSource<T> dataSource = null;
            synchronized (this) {
                if (this.mDataSources != null && i < this.mDataSources.size()) {
                    dataSource = (DataSource) this.mDataSources.set(i, null);
                }
            }
            return dataSource;
        }

        @Nullable
        private synchronized DataSource<T> getDataSourceWithResult() {
            return getDataSource(this.mIndexOfDataSourceWithResult);
        }

        @Nullable
        public synchronized T getResult() {
            DataSource<T> dataSourceWithResult;
            dataSourceWithResult = getDataSourceWithResult();
            return dataSourceWithResult != null ? dataSourceWithResult.getResult() : null;
        }

        public synchronized boolean hasResult() {
            boolean z;
            DataSource<T> dataSourceWithResult = getDataSourceWithResult();
            z = dataSourceWithResult != null && dataSourceWithResult.hasResult();
            return z;
        }

        /* JADX WARNING: Missing block: B:8:0x0010, code:
            if (r0 == null) goto L_0x0028;
     */
        /* JADX WARNING: Missing block: B:9:0x0012, code:
            r1 = 0;
     */
        /* JADX WARNING: Missing block: B:11:0x0017, code:
            if (r1 >= r0.size()) goto L_0x0028;
     */
        /* JADX WARNING: Missing block: B:12:0x0019, code:
            closeSafely((com.facebook.datasource.DataSource) r0.get(r1));
            r1 = r1 + 1;
     */
        /* JADX WARNING: Missing block: B:23:?, code:
            return true;
     */
        public boolean close() {
            /*
            r3 = this;
            monitor-enter(r3);
            r2 = super.close();	 Catch:{ all -> 0x0025 }
            if (r2 != 0) goto L_0x000a;
        L_0x0007:
            r2 = 0;
            monitor-exit(r3);	 Catch:{ all -> 0x0025 }
        L_0x0009:
            return r2;
        L_0x000a:
            r0 = r3.mDataSources;	 Catch:{ all -> 0x0025 }
            r2 = 0;
            r3.mDataSources = r2;	 Catch:{ all -> 0x0025 }
            monitor-exit(r3);	 Catch:{ all -> 0x0025 }
            if (r0 == 0) goto L_0x0028;
        L_0x0012:
            r1 = 0;
        L_0x0013:
            r2 = r0.size();
            if (r1 >= r2) goto L_0x0028;
        L_0x0019:
            r2 = r0.get(r1);
            r2 = (com.facebook.datasource.DataSource) r2;
            r3.closeSafely(r2);
            r1 = r1 + 1;
            goto L_0x0013;
        L_0x0025:
            r2 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0025 }
            throw r2;
        L_0x0028:
            r2 = 1;
            goto L_0x0009;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.IncreasingQualityDataSourceSupplier.IncreasingQualityDataSource.close():boolean");
        }

        private void onDataSourceNewResult(int index, DataSource<T> dataSource) {
            maybeSetIndexOfDataSourceWithResult(index, dataSource, dataSource.isFinished());
            if (dataSource == getDataSourceWithResult()) {
                boolean z = index == 0 && dataSource.isFinished();
                setResult(null, z);
            }
        }

        private void onDataSourceFailed(int index, DataSource<T> dataSource) {
            closeSafely(tryGetAndClearDataSource(index, dataSource));
            if (index == 0) {
                setFailure(dataSource.getFailureCause());
            }
        }

        /* JADX WARNING: Missing block: B:14:0x0021, code:
            r0 = r2;
     */
        /* JADX WARNING: Missing block: B:15:0x0022, code:
            if (r0 <= r1) goto L_0x0010;
     */
        /* JADX WARNING: Missing block: B:16:0x0024, code:
            closeSafely(getAndClearDataSource(r0));
            r0 = r0 - 1;
     */
        /* JADX WARNING: Missing block: B:25:?, code:
            return;
     */
        /* JADX WARNING: Missing block: B:26:?, code:
            return;
     */
        private void maybeSetIndexOfDataSourceWithResult(int r5, com.facebook.datasource.DataSource<T> r6, boolean r7) {
            /*
            r4 = this;
            monitor-enter(r4);
            r2 = r4.mIndexOfDataSourceWithResult;	 Catch:{ all -> 0x002e }
            r1 = r4.mIndexOfDataSourceWithResult;	 Catch:{ all -> 0x002e }
            r3 = r4.getDataSource(r5);	 Catch:{ all -> 0x002e }
            if (r6 != r3) goto L_0x000f;
        L_0x000b:
            r3 = r4.mIndexOfDataSourceWithResult;	 Catch:{ all -> 0x002e }
            if (r5 != r3) goto L_0x0011;
        L_0x000f:
            monitor-exit(r4);	 Catch:{ all -> 0x002e }
        L_0x0010:
            return;
        L_0x0011:
            r3 = r4.getDataSourceWithResult();	 Catch:{ all -> 0x002e }
            if (r3 == 0) goto L_0x001d;
        L_0x0017:
            if (r7 == 0) goto L_0x0020;
        L_0x0019:
            r3 = r4.mIndexOfDataSourceWithResult;	 Catch:{ all -> 0x002e }
            if (r5 >= r3) goto L_0x0020;
        L_0x001d:
            r1 = r5;
            r4.mIndexOfDataSourceWithResult = r5;	 Catch:{ all -> 0x002e }
        L_0x0020:
            monitor-exit(r4);	 Catch:{ all -> 0x002e }
            r0 = r2;
        L_0x0022:
            if (r0 <= r1) goto L_0x0010;
        L_0x0024:
            r3 = r4.getAndClearDataSource(r0);
            r4.closeSafely(r3);
            r0 = r0 + -1;
            goto L_0x0022;
        L_0x002e:
            r3 = move-exception;
            monitor-exit(r4);	 Catch:{ all -> 0x002e }
            throw r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.IncreasingQualityDataSourceSupplier.IncreasingQualityDataSource.maybeSetIndexOfDataSourceWithResult(int, com.facebook.datasource.DataSource, boolean):void");
        }

        @Nullable
        private synchronized DataSource<T> tryGetAndClearDataSource(int i, DataSource<T> dataSource) {
            if (dataSource == getDataSourceWithResult()) {
                dataSource = null;
            } else if (dataSource == getDataSource(i)) {
                dataSource = getAndClearDataSource(i);
            }
            return dataSource;
        }

        private void closeSafely(DataSource<T> dataSource) {
            if (dataSource != null) {
                dataSource.close();
            }
        }
    }

    private IncreasingQualityDataSourceSupplier(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        Preconditions.checkArgument(!dataSourceSuppliers.isEmpty(), "List of suppliers is empty!");
        this.mDataSourceSuppliers = dataSourceSuppliers;
    }

    public static <T> IncreasingQualityDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        return new IncreasingQualityDataSourceSupplier(dataSourceSuppliers);
    }

    public DataSource<T> get() {
        return new IncreasingQualityDataSource();
    }

    public int hashCode() {
        return this.mDataSourceSuppliers.hashCode();
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof IncreasingQualityDataSourceSupplier)) {
            return false;
        }
        return Objects.equal(this.mDataSourceSuppliers, ((IncreasingQualityDataSourceSupplier) other).mDataSourceSuppliers);
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("list", this.mDataSourceSuppliers).toString();
    }
}
