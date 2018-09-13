package com.feng.library.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.feng.library.utils.AndroidVersionUtil;
import com.feng.library.utils.LogUtils;
import com.feng.library.utils.ToastUtil;
import java.util.ArrayList;
import java.util.List;

@TargetApi(23)
class PermissionUtil {
    public static volatile PermissionUtil INSTANCE = null;
    public static final Object LOCK = new Object();
    private static final String TAG = "PermissionUtil";

    public static PermissionUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new PermissionUtil();
            }
        }
        return INSTANCE;
    }

    private PermissionUtil() {
    }

    public void requestPermission(Object obj, int requestCode, String... permission) {
        if (AndroidVersionUtil.hasM()) {
            requestPermission(obj, requestCode, "", permission);
        }
    }

    public void requestPermission(Object obj, int requestCode, String hint, String... permission) {
        if (AndroidVersionUtil.hasM() && permission != null && permission.length != 0) {
            Context activity;
            Fragment fragment = null;
            if (obj instanceof Activity) {
                activity = (Activity) obj;
            } else if (obj instanceof Fragment) {
                fragment = (Fragment) obj;
                activity = fragment.getActivity();
            } else {
                LogUtils.e(TAG, "obj 只能是 Activity 或者 fragment 及其子类");
                return;
            }
            if (!TextUtils.isEmpty(hint)) {
                for (String str : permission) {
                    if (fragment != null) {
                        if (fragment.shouldShowRequestPermissionRationale(str)) {
                            ToastUtil.showToast(fragment.getContext(), hint);
                            break;
                        }
                    } else if (activity.shouldShowRequestPermissionRationale(str)) {
                        ToastUtil.showToast(activity, hint);
                        break;
                    }
                }
            }
            if (fragment != null) {
                fragment.requestPermissions(permission, requestCode);
            } else {
                activity.requestPermissions(permission, requestCode);
            }
        }
    }

    protected String[] list2Array(List<String> denyPermission) {
        String[] array = new String[denyPermission.size()];
        int count = denyPermission.size();
        for (int i = 0; i < count; i++) {
            array[i] = (String) denyPermission.get(i);
        }
        return array;
    }

    public List<String> checkPermission(Object obj, String... permission) {
        List<String> denyPermissions = null;
        if (!(!AndroidVersionUtil.hasM() || permission == null || permission.length == 0)) {
            Activity activity;
            if (obj instanceof Activity) {
                activity = (Activity) obj;
            } else if (obj instanceof Fragment) {
                activity = ((Fragment) obj).getActivity();
            } else {
                LogUtils.e(TAG, "obj 只能是 Activity 或者 fragment 及其子类");
            }
            denyPermissions = new ArrayList();
            for (String p : permission) {
                if (activity.checkSelfPermission(p) != 0) {
                    denyPermissions.add(p);
                }
            }
        }
        return denyPermissions;
    }

    public boolean checkPermission(Activity activity, String permission) {
        return AndroidVersionUtil.hasM() && activity.checkSelfPermission(permission) == 0;
    }

    public void requestAlertWindowPermission(Object obj) {
        if (AndroidVersionUtil.hasM()) {
            Activity activity;
            Fragment fragment = null;
            if (obj instanceof Activity) {
                activity = (Activity) obj;
            } else if (obj instanceof Fragment) {
                fragment = (Fragment) obj;
                activity = fragment.getActivity();
            } else {
                LogUtils.e(TAG, "obj 只能是 Activity 或者 fragment 及其衍生类");
                return;
            }
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            if (fragment != null) {
                fragment.startActivityForResult(intent, OnPermissionCallback.PERMISSION_ALERT_WINDOW);
            } else {
                activity.startActivityForResult(intent, OnPermissionCallback.PERMISSION_ALERT_WINDOW);
            }
        }
    }

    public void requestWriteSetting(Object obj) {
        if (AndroidVersionUtil.hasM()) {
            Activity activity;
            Fragment fragment = null;
            if (obj instanceof Activity) {
                activity = (Activity) obj;
            } else if (obj instanceof Fragment) {
                fragment = (Fragment) obj;
                activity = fragment.getActivity();
            } else {
                LogUtils.e(TAG, "obj 只能是 Activity 或者 fragment 及其衍生类");
                return;
            }
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            if (fragment != null) {
                fragment.startActivityForResult(intent, OnPermissionCallback.PERMISSION_WRITE_SETTING);
            } else {
                activity.startActivityForResult(intent, OnPermissionCallback.PERMISSION_WRITE_SETTING);
            }
        }
    }
}
