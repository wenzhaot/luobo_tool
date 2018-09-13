package com.facebook.cache.disk;

import android.content.Context;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.NoOpCacheErrorLogger;
import com.facebook.cache.common.NoOpCacheEventListener;
import com.facebook.common.disk.DiskTrimmableRegistry;
import com.facebook.common.disk.NoOpDiskTrimmableRegistry;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.internal.Suppliers;
import com.stub.StubApp;
import java.io.File;
import javax.annotation.Nullable;

public class DiskCacheConfig {
    private final String mBaseDirectoryName;
    private final Supplier<File> mBaseDirectoryPathSupplier;
    private final CacheErrorLogger mCacheErrorLogger;
    private final CacheEventListener mCacheEventListener;
    private final Context mContext;
    private final long mDefaultSizeLimit;
    private final DiskTrimmableRegistry mDiskTrimmableRegistry;
    private final EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
    private final long mLowDiskSpaceSizeLimit;
    private final long mMinimumSizeLimit;
    private final int mVersion;

    public static class Builder {
        private String mBaseDirectoryName;
        private Supplier<File> mBaseDirectoryPathSupplier;
        private CacheErrorLogger mCacheErrorLogger;
        private CacheEventListener mCacheEventListener;
        @Nullable
        private final Context mContext;
        private DiskTrimmableRegistry mDiskTrimmableRegistry;
        private EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
        private long mMaxCacheSize;
        private long mMaxCacheSizeOnLowDiskSpace;
        private long mMaxCacheSizeOnVeryLowDiskSpace;
        private int mVersion;

        private Builder(@Nullable Context context) {
            this.mVersion = 1;
            this.mBaseDirectoryName = "image_cache";
            this.mMaxCacheSize = 41943040;
            this.mMaxCacheSizeOnLowDiskSpace = 10485760;
            this.mMaxCacheSizeOnVeryLowDiskSpace = 2097152;
            this.mEntryEvictionComparatorSupplier = new DefaultEntryEvictionComparatorSupplier();
            this.mContext = context;
        }

        public Builder setVersion(int version) {
            this.mVersion = version;
            return this;
        }

        public Builder setBaseDirectoryName(String baseDirectoryName) {
            this.mBaseDirectoryName = baseDirectoryName;
            return this;
        }

        public Builder setBaseDirectoryPath(File baseDirectoryPath) {
            this.mBaseDirectoryPathSupplier = Suppliers.of(baseDirectoryPath);
            return this;
        }

        public Builder setBaseDirectoryPathSupplier(Supplier<File> baseDirectoryPathSupplier) {
            this.mBaseDirectoryPathSupplier = baseDirectoryPathSupplier;
            return this;
        }

        public Builder setMaxCacheSize(long maxCacheSize) {
            this.mMaxCacheSize = maxCacheSize;
            return this;
        }

        public Builder setMaxCacheSizeOnLowDiskSpace(long maxCacheSizeOnLowDiskSpace) {
            this.mMaxCacheSizeOnLowDiskSpace = maxCacheSizeOnLowDiskSpace;
            return this;
        }

        public Builder setMaxCacheSizeOnVeryLowDiskSpace(long maxCacheSizeOnVeryLowDiskSpace) {
            this.mMaxCacheSizeOnVeryLowDiskSpace = maxCacheSizeOnVeryLowDiskSpace;
            return this;
        }

        public Builder setEntryEvictionComparatorSupplier(EntryEvictionComparatorSupplier supplier) {
            this.mEntryEvictionComparatorSupplier = supplier;
            return this;
        }

        public Builder setCacheErrorLogger(CacheErrorLogger cacheErrorLogger) {
            this.mCacheErrorLogger = cacheErrorLogger;
            return this;
        }

        public Builder setCacheEventListener(CacheEventListener cacheEventListener) {
            this.mCacheEventListener = cacheEventListener;
            return this;
        }

        public Builder setDiskTrimmableRegistry(DiskTrimmableRegistry diskTrimmableRegistry) {
            this.mDiskTrimmableRegistry = diskTrimmableRegistry;
            return this;
        }

        public DiskCacheConfig build() {
            boolean z = (this.mBaseDirectoryPathSupplier == null && this.mContext == null) ? false : true;
            Preconditions.checkState(z, "Either a non-null context or a base directory path or supplier must be provided.");
            if (this.mBaseDirectoryPathSupplier == null && this.mContext != null) {
                this.mBaseDirectoryPathSupplier = new Supplier<File>() {
                    public File get() {
                        return StubApp.getOrigApplicationContext(Builder.this.mContext.getApplicationContext()).getCacheDir();
                    }
                };
            }
            return new DiskCacheConfig(this);
        }
    }

    private DiskCacheConfig(Builder builder) {
        CacheErrorLogger instance;
        CacheEventListener instance2;
        DiskTrimmableRegistry instance3;
        this.mVersion = builder.mVersion;
        this.mBaseDirectoryName = (String) Preconditions.checkNotNull(builder.mBaseDirectoryName);
        this.mBaseDirectoryPathSupplier = (Supplier) Preconditions.checkNotNull(builder.mBaseDirectoryPathSupplier);
        this.mDefaultSizeLimit = builder.mMaxCacheSize;
        this.mLowDiskSpaceSizeLimit = builder.mMaxCacheSizeOnLowDiskSpace;
        this.mMinimumSizeLimit = builder.mMaxCacheSizeOnVeryLowDiskSpace;
        this.mEntryEvictionComparatorSupplier = (EntryEvictionComparatorSupplier) Preconditions.checkNotNull(builder.mEntryEvictionComparatorSupplier);
        if (builder.mCacheErrorLogger == null) {
            instance = NoOpCacheErrorLogger.getInstance();
        } else {
            instance = builder.mCacheErrorLogger;
        }
        this.mCacheErrorLogger = instance;
        if (builder.mCacheEventListener == null) {
            instance2 = NoOpCacheEventListener.getInstance();
        } else {
            instance2 = builder.mCacheEventListener;
        }
        this.mCacheEventListener = instance2;
        if (builder.mDiskTrimmableRegistry == null) {
            instance3 = NoOpDiskTrimmableRegistry.getInstance();
        } else {
            instance3 = builder.mDiskTrimmableRegistry;
        }
        this.mDiskTrimmableRegistry = instance3;
        this.mContext = builder.mContext;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public String getBaseDirectoryName() {
        return this.mBaseDirectoryName;
    }

    public Supplier<File> getBaseDirectoryPathSupplier() {
        return this.mBaseDirectoryPathSupplier;
    }

    public long getDefaultSizeLimit() {
        return this.mDefaultSizeLimit;
    }

    public long getLowDiskSpaceSizeLimit() {
        return this.mLowDiskSpaceSizeLimit;
    }

    public long getMinimumSizeLimit() {
        return this.mMinimumSizeLimit;
    }

    public EntryEvictionComparatorSupplier getEntryEvictionComparatorSupplier() {
        return this.mEntryEvictionComparatorSupplier;
    }

    public CacheErrorLogger getCacheErrorLogger() {
        return this.mCacheErrorLogger;
    }

    public CacheEventListener getCacheEventListener() {
        return this.mCacheEventListener;
    }

    public DiskTrimmableRegistry getDiskTrimmableRegistry() {
        return this.mDiskTrimmableRegistry;
    }

    public Context getContext() {
        return this.mContext;
    }

    public static Builder newBuilder(@Nullable Context context) {
        return new Builder(context);
    }
}
