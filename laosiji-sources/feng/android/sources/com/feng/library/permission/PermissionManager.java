package com.feng.library.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import java.util.Arrays;
import java.util.List;

@TargetApi(23)
public class PermissionManager implements OnPermissionCallback {
    private static volatile PermissionManager INSTANCE = null;
    private static final Object LOCK = new Object();
    private static final String TAG = "PermissionManager";
    private SparseArray<OnPermissionCallback> mCallbacks = new SparseArray();
    private PermissionUtil mPu = PermissionUtil.getInstance();

    public static PermissionManager getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new PermissionManager();
            }
        }
        return INSTANCE;
    }

    private PermissionManager() {
    }

    public boolean checkPermission(Activity activity, String permission) {
        return this.mPu.checkPermission(activity, permission);
    }

    public boolean checkPermission(Fragment fragment, String permission) {
        return checkPermission(fragment.getActivity(), permission);
    }

    public void requestAlertWindowPermission(Object obj, OnPermissionCallback callback) {
        registerCallback(callback, Arrays.hashCode(new String[]{"android.settings.action.MANAGE_OVERLAY_PERMISSION"}));
        this.mPu.requestAlertWindowPermission(obj);
    }

    public void requestWriteSettingPermission(Object obj, OnPermissionCallback callback) {
        registerCallback(callback, Arrays.hashCode(new String[]{"android.settings.action.MANAGE_WRITE_SETTINGS"}));
        this.mPu.requestWriteSetting(obj);
    }

    public void requestPermission(Object obj, OnPermissionCallback callback, String... permission) {
        requestPermission(obj, callback, "", registerCallback(obj, callback, permission));
    }

    private void requestPermission(Object obj, OnPermissionCallback callback, String hint, String... permission) {
        this.mPu.requestPermission(obj, 0, hint, registerCallback(obj, callback, permission));
    }

    private void registerCallback(OnPermissionCallback callback, int hashCode) {
        if (((OnPermissionCallback) this.mCallbacks.get(hashCode)) == null) {
            this.mCallbacks.append(hashCode, callback);
        }
    }

    private String[] registerCallback(Object obj, OnPermissionCallback callback, String... permission) {
        List<String> list = this.mPu.checkPermission(obj, permission);
        if (list == null || list.size() == 0) {
            return null;
        }
        String[] denyPermission = this.mPu.list2Array(list);
        int hashCode = Arrays.hashCode(denyPermission);
        if (((OnPermissionCallback) this.mCallbacks.get(hashCode)) != null) {
            return denyPermission;
        }
        this.mCallbacks.append(hashCode, callback);
        return denyPermission;
    }

    public void onSuccess(String... permissions) {
        int hashCode = Arrays.hashCode(permissions);
        OnPermissionCallback c = (OnPermissionCallback) this.mCallbacks.get(hashCode);
        if (c != null) {
            c.onSuccess(permissions);
            this.mCallbacks.remove(hashCode);
        }
    }

    public void onFail(String... permissions) {
        int hashCode = Arrays.hashCode(permissions);
        OnPermissionCallback c = (OnPermissionCallback) this.mCallbacks.get(hashCode);
        if (c != null) {
            c.onFail(permissions);
            this.mCallbacks.remove(hashCode);
        }
    }
}
