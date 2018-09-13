package com.feng.car.listener;

import android.graphics.Bitmap;

public interface OnDownloadImageListener {
    void onDownloadFailed();

    void onDownloadSuccess(Bitmap bitmap);
}
