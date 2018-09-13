package com.feng.car.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout.LayoutParams;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus.Builder;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityDistributorMapBinding;
import com.feng.car.databinding.ItemVehicleAgencyBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.car.DealerInfo;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.emoticons.keyboard.utils.EmoticonsKeyboardUtils;
import com.feng.library.emoticons.keyboard.widget.EmoticonSpan;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistributorMapActivity extends LocationBaseActivity<ActivityDistributorMapBinding> {
    private final String MY_LOCATION_TAG = "my_location_tag";
    private Animation bottomInAnim;
    private Animation bottomOutAnim;
    private boolean isFirstLoc = true;
    private BaiduMap mBaiduMap;
    private List<DealerInfo> mCarShopList = new ArrayList();
    private boolean mClickLocation = false;
    private Marker mCurrentMarker;
    private String mCurrentPhoneNumber;
    private DealerInfo mCuttentDealerInfo;
    private BitmapDescriptor mDistributorBigBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_distributor_big);
    private ItemVehicleAgencyBinding mDistributorBinding;
    private BitmapDescriptor mDistributorSmallBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_distributor_smal);
    private Drawable mLocationDrawable;
    private BitmapDescriptor mMyLocationBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location);
    private LatLng mSelectLatLng = null;
    private DealerInfo mSeletDealerInfo;

    private void setDealerInfoAddress(DealerInfo info) {
        int itemWidth;
        if (this.mLocationDrawable == null) {
            this.mLocationDrawable = getResources().getDrawable(R.drawable.icon_vehicle_shop_addr_tips);
        }
        int fontSize = EmoticonsKeyboardUtils.getFontHeight(this.mDistributorBinding.tvVehicleShopAddress);
        if (fontSize == -1) {
            itemWidth = this.mLocationDrawable.getIntrinsicWidth();
        } else {
            fontSize -= 8;
            itemWidth = (int) ((((float) (this.mLocationDrawable.getIntrinsicWidth() * fontSize)) * 1.0f) / ((float) this.mLocationDrawable.getIntrinsicHeight()));
        }
        this.mLocationDrawable.setBounds(0, 0, itemWidth, fontSize);
        EmoticonSpan imageSpan = new EmoticonSpan(this.mLocationDrawable);
        Spannable spannable = new SpannableStringBuilder("@ " + info.dealeraddress);
        spannable.setSpan(imageSpan, 0, 1, 17);
        this.mDistributorBinding.tvVehicleShopAddress.setText(spannable);
    }

    private void showDistributorView(final DealerInfo info) {
        if (this.bottomInAnim == null) {
            this.bottomInAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_in_anim);
        }
        if (!this.mDistributorBinding.getRoot().isShown()) {
            this.mDistributorBinding.getRoot().setVisibility(0);
            this.mDistributorBinding.getRoot().startAnimation(this.bottomInAnim);
        }
        this.mDistributorBinding.setDealerInfo(info);
        this.mDistributorBinding.tvVehicleShopDistance.setText(MapUtil.newInstance().getFormateDistance(MapUtil.newInstance().getDistanceBetween2Points(new LatLng(info.baidulat, info.baidulon), new LatLng(this.mCurrentLat, this.mCurrentLon))));
        setDealerInfoAddress(info);
        this.mDistributorBinding.llShopItemCallContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (StringUtil.isEmpty(info.dealermobile)) {
                    DistributorMapActivity.this.showSecondTypeToast((int) R.string.not_find_distributor_phone_number);
                } else {
                    DistributorMapActivity.this.mCuttentDealerInfo = info;
                    DistributorMapActivity.this.checkPhonePermission(info.dealermobile);
                }
                Map<String, String> map = new HashMap();
                map.put("jxsid", info.id + "");
                map.put("iscall", "0");
                DistributorMapActivity.this.mLogGatherInfo.addLogBtnEvent("app_btn_jxs_mobile_click", map);
            }
        });
        this.mDistributorBinding.llShopItemNavContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (DistributorMapActivity.this.mCurrentLon == 0.0d || DistributorMapActivity.this.mCurrentLat == 0.0d) {
                    DistributorMapActivity.this.showSecondTypeToast((int) R.string.location_failed_retry);
                } else {
                    DistributorMapActivity.this.showNavigationDialog(info);
                }
                MobclickAgent.onEvent(DistributorMapActivity.this, "dealer_map_nav");
            }
        });
    }

    private void hideDistributorView() {
        if (this.bottomOutAnim == null) {
            this.bottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_out_anim);
            this.bottomOutAnim.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    DistributorMapActivity.this.mDistributorBinding.getRoot().setVisibility(8);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        this.mDistributorBinding.getRoot().startAnimation(this.bottomOutAnim);
    }

    public int setBaseContentView() {
        return R.layout.activity_distributor_map;
    }

    public void initView() {
        super.initView();
        hideDefaultTitleBar();
        closeSwip();
        initDistributorView();
        ((ActivityDistributorMapBinding) this.mBaseBinding).close.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                DistributorMapActivity.this.finish();
            }
        });
        ((ActivityDistributorMapBinding) this.mBaseBinding).location.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                DistributorMapActivity.this.mClickLocation = true;
                DistributorMapActivity.this.checkPermission(true);
                DistributorMapActivity.this.isFirstLoc = true;
                MobclickAgent.onEvent(DistributorMapActivity.this, "dealer_map_fix");
            }
        });
        initMap();
    }

    public void finish() {
        super.finish();
        if (this.mDistributorBinding.getRoot().isShown()) {
            hideDistributorView();
        }
    }

    private void initDistributorView() {
        this.mDistributorBinding = ItemVehicleAgencyBinding.inflate(LayoutInflater.from(this));
        this.mDistributorBinding.dividerLine.setVisibility(8);
        this.mDistributorBinding.getRoot().setVisibility(8);
        ((ActivityDistributorMapBinding) this.mBaseBinding).parentLine.addView(this.mDistributorBinding.getRoot());
        LayoutParams p = new LayoutParams(-1, -2);
        p.addRule(12);
        this.mDistributorBinding.getRoot().setLayoutParams(p);
    }

    private void showNavigationDialog(DealerInfo info) {
        MapUtil.newInstance().showNavigationDialog(this, info, new LatLng(this.mCurrentLat, this.mCurrentLon), this.mAddress);
    }

    private void initMap() {
        this.mBaiduMap = ((ActivityDistributorMapBinding) this.mBaseBinding).mapView.getMap();
        this.mBaiduMap.setMapType(1);
        this.mBaiduMap.getUiSettings().setCompassEnabled(false);
        initPoint();
        this.mBaiduMap.setMyLocationEnabled(true);
        this.mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                if (!(marker == null || marker.getTitle() == null || marker.getTitle().equals("my_location_tag"))) {
                    DistributorMapActivity.this.mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new Builder().target(marker.getPosition()).build()));
                    if (DistributorMapActivity.this.mCurrentMarker != null) {
                        DistributorMapActivity.this.mCurrentMarker.setIcon(DistributorMapActivity.this.mDistributorSmallBitmap);
                    }
                    marker.setIcon(DistributorMapActivity.this.mDistributorBigBitmap);
                    marker.setToTop();
                    DistributorMapActivity.this.mCurrentMarker = marker;
                    try {
                        DistributorMapActivity.this.showDistributorView((DealerInfo) DistributorMapActivity.this.mCarShopList.get(Integer.valueOf(marker.getTitle()).intValue()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MobclickAgent.onEvent(DistributorMapActivity.this, "dealer_map_site");
                }
                return false;
            }
        });
    }

    public void onReceiveLocationData(BDLocation bdLocation) {
        super.onReceiveLocationData(bdLocation);
        this.mCurrentLat = bdLocation.getLatitude();
        this.mCurrentLon = bdLocation.getLongitude();
        this.mAddress = bdLocation.getAddrStr();
        if (this.isFirstLoc) {
            this.isFirstLoc = false;
            LatLng ll = new LatLng(this.mCurrentLat, this.mCurrentLon);
            this.mBaiduMap.addOverlay(new MarkerOptions().position(ll).title("my_location_tag").icon(this.mMyLocationBitmap));
            if (!this.mClickLocation) {
                showDistributorInOneScreen();
            }
            if (this.mCurrentLat != 0.0d && this.mCurrentLon != 0.0d && this.mClickLocation) {
                this.mClickLocation = false;
                Builder builder = new Builder();
                builder.target(ll);
                this.mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    protected void onBaiDuLocationError() {
        super.onBaiDuLocationError();
        stopBaiduLocation();
        showSecondTypeToast((int) R.string.baidu_location_error);
    }

    private void initPoint() {
        String json = getIntent().getStringExtra("DATA_JSON");
        if (!StringUtil.isEmpty(json)) {
            int infoId = getIntent().getIntExtra("id", -1);
            this.mCarShopList = (ArrayList) JsonUtil.fromJson(json, new TypeToken<ArrayList<DealerInfo>>() {
            });
            if (this.mCarShopList != null && this.mCarShopList.size() > 0) {
                for (int i = 0; i < this.mCarShopList.size(); i++) {
                    DealerInfo info = (DealerInfo) this.mCarShopList.get(i);
                    if (!(info.baidulat == 0.0d || info.baidulon == 0.0d)) {
                        if (info.id == infoId) {
                            this.mSelectLatLng = new LatLng(info.baidulat, info.baidulon);
                            this.mSeletDealerInfo = info;
                            this.mCurrentMarker = (Marker) this.mBaiduMap.addOverlay(new MarkerOptions().position(new LatLng(info.baidulat, info.baidulon)).title(String.valueOf(i)).icon(this.mDistributorBigBitmap));
                        } else {
                            this.mBaiduMap.addOverlay(new MarkerOptions().position(new LatLng(info.baidulat, info.baidulon)).title(String.valueOf(i)).icon(this.mDistributorSmallBitmap));
                        }
                    }
                }
                showDistributorInOneScreen();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50005) {
            checkPhonePermission(this.mCurrentPhoneNumber);
        }
    }

    private void checkPhonePermission(String phoneNumber) {
        this.mCurrentPhoneNumber = phoneNumber;
        if (VERSION.SDK_INT < 23) {
            showPhoneCallDialog();
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 50005);
        } else {
            showPhoneCallDialog();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50005 && permissions != null && permissions.length != 0) {
            if (permissions[0].equals("android.permission.CALL_PHONE") && grantResults[0] == 0) {
                if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
                    showSecondTypeToast((int) R.string.authorization_failed_cannot_make_phonecall);
                } else {
                    showPhoneCallDialog();
                }
            } else if (grantResults[0] == 0) {
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    showPermissionsDialog(false);
                } else {
                    showPermissionsDialog(true);
                }
            }
        }
    }

    private void showPermissionsDialog(final boolean notInquiry) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.go_open_permissions), false));
        CommonDialog.showCommonDialog(ActivityManager.getInstance().getCurrentActivity(), getString(R.string.no_open_call_phone_permissions), "", getString(R.string.repulse_permissions), list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (notInquiry) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", DistributorMapActivity.this.getPackageName(), null));
                    DistributorMapActivity.this.startActivityForResult(intent, 50005);
                    return;
                }
                DistributorMapActivity.this.checkPhonePermission(DistributorMapActivity.this.mCurrentPhoneNumber);
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        }, true);
    }

    protected void onResume() {
        ((ActivityDistributorMapBinding) this.mBaseBinding).mapView.onResume();
        super.onResume();
    }

    protected void onPause() {
        ((ActivityDistributorMapBinding) this.mBaseBinding).mapView.onPause();
        super.onPause();
    }

    protected void onDestroy() {
        this.mBaiduMap.setMyLocationEnabled(false);
        ((ActivityDistributorMapBinding) this.mBaseBinding).mapView.onDestroy();
        super.onDestroy();
    }

    private void showDistributorInOneScreen() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (this.mCarShopList != null && this.mCarShopList.size() > 0) {
            for (DealerInfo info : this.mCarShopList) {
                if (!(info.baidulon == 0.0d || info.baidulat == 0.0d)) {
                    builder.include(new LatLng(info.baidulat, info.baidulon));
                }
            }
        }
        if (!(this.mCurrentLat == 0.0d || this.mCurrentLon == 0.0d)) {
            builder.include(new LatLng(this.mCurrentLat, this.mCurrentLon));
        }
        this.mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
        if (this.mSeletDealerInfo != null) {
            this.mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new Builder().target(this.mSelectLatLng).build()));
            showDistributorView(this.mSeletDealerInfo);
            if (this.mCurrentMarker != null) {
                this.mCurrentMarker.setToTop();
            }
        }
    }

    private void locationChina() {
        this.mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new Builder().target(new LatLng(30.663791d, 104.07281d)).zoom(4.505f).build()));
    }

    private void showPhoneCallDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        builder.setMessage(this.mCurrentPhoneNumber);
        builder.setPositiveButton(getString(R.string.cancel), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.shop_item_call_tips), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DistributorMapActivity.this.callDealerShop();
                dialog.dismiss();
                Map<String, String> map = new HashMap();
                if (DistributorMapActivity.this.mCuttentDealerInfo != null) {
                    map.put("jxsid", DistributorMapActivity.this.mCuttentDealerInfo.id + "");
                }
                map.put("iscall", "1");
                DistributorMapActivity.this.mLogGatherInfo.addLogBtnEvent("app_btn_jxs_mobile_click", map);
            }
        });
        builder.create().show();
    }

    private void callDealerShop() {
        Intent intentTo = new Intent("android.intent.action.CALL", Uri.parse("tel:" + this.mCurrentPhoneNumber));
        if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            showSecondTypeToast((int) R.string.authorization_failed_cannot_make_phonecall);
        } else {
            startActivity(intentTo);
        }
    }

    public void onBackPressed() {
        if (this.mDistributorBinding.getRoot().isShown()) {
            hideDistributorView();
        } else {
            super.onBackPressed();
        }
    }
}
