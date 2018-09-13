package com.feng.car.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.View;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.MapUtil;
import com.feng.car.view.CommonDialog;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;

public abstract class LocationBaseActivity<VB extends ViewDataBinding> extends VideoBaseActivity<VB> {
    private final int BAIDU_READ_PHONE_STATE = 100;
    public MyLocationListener listener;
    public double mCurrentLat = 0.0d;
    public double mCurrentLon = 0.0d;
    public LocationClient mLocationClient;

    private class MyLocationListener extends BDAbstractLocationListener {
        private MyLocationListener() {
        }

        /* synthetic */ MyLocationListener(LocationBaseActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                LocationBaseActivity.this.onBaiDuLocationError();
            } else if (bdLocation.getLocType() == 63) {
                LocationBaseActivity.this.onBaiDuLocationError();
            } else if (bdLocation.getLocType() == 62) {
                LocationBaseActivity.this.onBaiDuLocationError();
            } else if (bdLocation.getLocType() == 61) {
                LocationBaseActivity.this.onReceiveLocationData(bdLocation);
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                LocationBaseActivity.this.onReceiveLocationData(bdLocation);
            } else if (bdLocation.getLocType() == 66) {
                LocationBaseActivity.this.onReceiveLocationData(bdLocation);
            } else {
                LocationBaseActivity.this.onReceiveLocationData(bdLocation);
            }
        }
    }

    public void initView() {
        this.mLocationClient = MapUtil.newInstance().creatLocationClient(this);
        this.listener = new MyLocationListener(this, null);
        this.mLocationClient.registerLocationListener(this.listener);
        checkPermission(true);
    }

    protected void onResume() {
        super.onResume();
        if (this instanceof DistributorMapActivity) {
            checkPermission(false);
        }
    }

    public void checkPermission(boolean isRequest) {
        if (VERSION.SDK_INT >= 23) {
            boolean flag2;
            boolean flag1 = checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0;
            if (checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0) {
                flag2 = true;
            } else {
                flag2 = false;
            }
            List<String> strPermissions = new ArrayList();
            if (flag1) {
                strPermissions.add("android.permission.ACCESS_COARSE_LOCATION");
            }
            if (flag2) {
                strPermissions.add("android.permission.ACCESS_FINE_LOCATION");
            }
            if (strPermissions.size() > 0) {
                if (isRequest) {
                    requestPermissions((String[]) strPermissions.toArray(new String[strPermissions.size()]), 100);
                }
            } else if (!this.mLocationClient.isStarted()) {
                this.mLocationClient.start();
            }
        } else if (!this.mLocationClient.isStarted()) {
            this.mLocationClient.start();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002c  */
    public void onRequestPermissionsResult(int r5, @android.support.annotation.NonNull java.lang.String[] r6, @android.support.annotation.NonNull int[] r7) {
        /*
        r4 = this;
        r3 = 0;
        super.onRequestPermissionsResult(r5, r6, r7);
        r2 = 100;
        if (r5 != r2) goto L_0x0022;
    L_0x0008:
        r0 = 1;
        r1 = 0;
    L_0x000a:
        r2 = r7.length;
        if (r1 >= r2) goto L_0x002a;
    L_0x000d:
        r2 = r7[r1];
        if (r2 == 0) goto L_0x0027;
    L_0x0011:
        r0 = 0;
        r2 = r4 instanceof com.feng.car.activity.CityListActivity;
        if (r2 == 0) goto L_0x002a;
    L_0x0016:
        r2 = r6[r3];
        r2 = android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(r4, r2);
        if (r2 != 0) goto L_0x0023;
    L_0x001e:
        r2 = 1;
        r4.showPermissionsDialog(r2);
    L_0x0022:
        return;
    L_0x0023:
        r4.showPermissionsDialog(r3);
        goto L_0x0022;
    L_0x0027:
        r1 = r1 + 1;
        goto L_0x000a;
    L_0x002a:
        if (r0 != 0) goto L_0x0030;
    L_0x002c:
        r4.onBaiDuLocationError();
        goto L_0x0022;
    L_0x0030:
        r2 = r4.mLocationClient;
        r2.start();
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.activity.LocationBaseActivity.onRequestPermissionsResult(int, java.lang.String[], int[]):void");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50005) {
            checkPermission(false);
        }
    }

    private void showPermissionsDialog(final boolean notInquiry) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.go_open_permissions), false));
        CommonDialog.showCommonDialog(ActivityManager.getInstance().getCurrentActivity(), getString(R.string.no_open_location_permissions), "", getString(R.string.repulse_permissions), list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (notInquiry) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", LocationBaseActivity.this.getPackageName(), null));
                    LocationBaseActivity.this.startActivityForResult(intent, 50005);
                    return;
                }
                LocationBaseActivity.this.checkPermission(true);
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        }, true);
    }

    public void onReceiveLocationData(BDLocation bdLocation) {
    }

    protected void onBaiDuLocationError() {
    }

    protected void onDestroy() {
        stopBaiduLocation();
        super.onDestroy();
    }

    public void stopBaiduLocation() {
        if (this.mLocationClient != null) {
            this.mLocationClient.stop();
        }
    }
}
