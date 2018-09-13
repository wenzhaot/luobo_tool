package com.feng.library.permission;

public interface OnPermissionCallback {
    public static final int PERMISSION_ALERT_WINDOW = 2769;
    public static final int PERMISSION_WRITE_SETTING = 2770;

    void onFail(String... strArr);

    void onSuccess(String... strArr);
}
