package com.feng.car.utils;

import java.util.HashMap;

public interface UploadSingleImage$OnUploadResult {
    void onFailure();

    void onResult(HashMap<String, String> hashMap);

    void onStart();
}
