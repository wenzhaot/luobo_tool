package com.facebook.imagepipeline.core;

import com.facebook.common.webp.WebpSupportStatus;

public class ImagePipelineExperiments {
    private boolean mDecodeFileDescriptorEnabled;
    private final int mForceSmallCacheThresholdBytes;
    private final int mThrottlingMaxSimultaneousRequests;
    private final boolean mWebpSupportEnabled;

    public static class Builder {
        private static final int DEFAULT_MAX_SIMULTANEOUS_FILE_FETCH_AND_RESIZE = 5;
        private final com.facebook.imagepipeline.core.ImagePipelineConfig.Builder mConfigBuilder;
        private boolean mDecodeFileDescriptorEnabled = false;
        private int mForceSmallCacheThresholdBytes = 0;
        private int mThrottlingMaxSimultaneousRequests = 5;
        private boolean mWebpSupportEnabled = false;

        public Builder(com.facebook.imagepipeline.core.ImagePipelineConfig.Builder configBuilder) {
            this.mConfigBuilder = configBuilder;
        }

        public com.facebook.imagepipeline.core.ImagePipelineConfig.Builder setDecodeFileDescriptorEnabled(boolean decodeFileDescriptorEnabled) {
            this.mDecodeFileDescriptorEnabled = decodeFileDescriptorEnabled;
            return this.mConfigBuilder;
        }

        public com.facebook.imagepipeline.core.ImagePipelineConfig.Builder setForceSmallCacheThresholdBytes(int forceSmallCacheThresholdBytes) {
            this.mForceSmallCacheThresholdBytes = forceSmallCacheThresholdBytes;
            return this.mConfigBuilder;
        }

        public com.facebook.imagepipeline.core.ImagePipelineConfig.Builder setWebpSupportEnabled(boolean webpSupportEnabled) {
            this.mWebpSupportEnabled = webpSupportEnabled;
            return this.mConfigBuilder;
        }

        public com.facebook.imagepipeline.core.ImagePipelineConfig.Builder setThrottlingMaxSimultaneousRequests(int throttlingMaxSimultaneousRequests) {
            this.mThrottlingMaxSimultaneousRequests = throttlingMaxSimultaneousRequests;
            return this.mConfigBuilder;
        }

        public ImagePipelineExperiments build() {
            return new ImagePipelineExperiments(this, this.mConfigBuilder);
        }
    }

    private ImagePipelineExperiments(Builder builder, com.facebook.imagepipeline.core.ImagePipelineConfig.Builder configBuilder) {
        boolean z;
        boolean z2 = true;
        this.mForceSmallCacheThresholdBytes = builder.mForceSmallCacheThresholdBytes;
        if (builder.mWebpSupportEnabled && WebpSupportStatus.sWebpLibraryPresent) {
            z = true;
        } else {
            z = false;
        }
        this.mWebpSupportEnabled = z;
        if (!(configBuilder.isDownsampleEnabled() && builder.mDecodeFileDescriptorEnabled)) {
            z2 = false;
        }
        this.mDecodeFileDescriptorEnabled = z2;
        this.mThrottlingMaxSimultaneousRequests = builder.mThrottlingMaxSimultaneousRequests;
    }

    public boolean isDecodeFileDescriptorEnabled() {
        return this.mDecodeFileDescriptorEnabled;
    }

    public int getForceSmallCacheThresholdBytes() {
        return this.mForceSmallCacheThresholdBytes;
    }

    public boolean isWebpSupportEnabled() {
        return this.mWebpSupportEnabled;
    }

    public int getThrottlingMaxSimultaneousRequests() {
        return this.mThrottlingMaxSimultaneousRequests;
    }

    public static Builder newBuilder(com.facebook.imagepipeline.core.ImagePipelineConfig.Builder configBuilder) {
        return new Builder(configBuilder);
    }
}
