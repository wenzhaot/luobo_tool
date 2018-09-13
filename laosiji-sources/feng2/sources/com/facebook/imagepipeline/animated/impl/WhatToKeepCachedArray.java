package com.facebook.imagepipeline.animated.impl;

import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
class WhatToKeepCachedArray {
    private final boolean[] mData;

    WhatToKeepCachedArray(int size) {
        this.mData = new boolean[size];
    }

    boolean get(int index) {
        return this.mData[index];
    }

    void setAll(boolean value) {
        for (int i = 0; i < this.mData.length; i++) {
            this.mData[i] = value;
        }
    }

    void removeOutsideRange(int start, int end) {
        for (int i = 0; i < this.mData.length; i++) {
            if (AnimatedDrawableUtil.isOutsideRange(start, end, i)) {
                this.mData[i] = false;
            }
        }
    }

    void set(int index, boolean value) {
        this.mData[index] = value;
    }
}
