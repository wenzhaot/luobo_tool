package com.feng.car.utils;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.text.TextUtils;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.car.DealerInfo;
import com.feng.car.entity.lcoation.CityInfo;
import com.feng.car.entity.lcoation.ProvinceInfo;
import com.feng.car.view.CommonDialog;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapUtil {
    private static MapUtil instance;
    private String BAIDU_MAP = "com.baidu.BaiduMap";
    private final int BAIDU_TAG = 1;
    private String GAODE_MAP = "com.autonavi.minimap";
    private final int GAODE_TAG = 2;
    private CityInfo mCityInfo = new CityInfo();
    private List<CityInfo> mCityList = new ArrayList();
    private int mDefaultCityId = 131;
    private String mDefaultCityName = "北京";
    private CityInfo mLocationInfo = new CityInfo();
    private List<ProvinceInfo> mProvinceList = new ArrayList();

    public int getLoactionCityId() {
        return this.mLocationInfo.id;
    }

    public String getCurrentCityName() {
        if (this.mCityInfo == null || StringUtil.isEmpty(this.mCityInfo.name)) {
            return this.mDefaultCityName;
        }
        return cityNameFormat(this.mCityInfo.name);
    }

    public String cityNameFormat(String cityName) {
        if (!TextUtils.isEmpty(cityName) && cityName.lastIndexOf("市") == cityName.length() - 1) {
            return cityName.substring(0, cityName.length() - 1);
        }
        return cityName;
    }

    public int getCurrentCityId() {
        if (this.mCityInfo == null || this.mCityInfo.id == 0) {
            return this.mDefaultCityId;
        }
        return this.mCityInfo.id;
    }

    public void updateCurrentCityId(Context context, int id) {
        this.mCityInfo.id = id;
        this.mCityInfo.name = getCityNameById(context, id);
        SharedUtil.putString(context, FengConstant.LOCAL_SELECT_CITY, this.mCityInfo.name);
    }

    public void updateCurrentCityName(Context context, String name) {
        this.mCityInfo.name = name;
        this.mCityInfo.id = getCityIdByName(context, name);
        SharedUtil.putString(context, FengConstant.LOCAL_SELECT_CITY, name);
    }

    public void startDefaultLocation(final Context context) {
        final LocationClient locationClient = creatLocationClient(context);
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            public void onReceiveLocation(BDLocation location) {
                LogGatherReadUtil.getInstance().setLocationInfo(location);
                MapUtil.this.mCityInfo.id = MapUtil.this.mDefaultCityId;
                MapUtil.this.mCityInfo.name = MapUtil.this.mDefaultCityName;
                if (location.getLocType() == 61 || location.getLocType() == 161 || location.getLocType() == 66) {
                    String cityName = MapUtil.this.cityNameFormat(location.getCity());
                    MapUtil.this.mCityInfo.name = cityName;
                    MapUtil.this.mCityInfo.id = MapUtil.this.getCityIdByName(context, cityName);
                    MapUtil.this.mLocationInfo.id = MapUtil.this.mCityInfo.id;
                    final String city = MapUtil.this.cityNameFormat(SharedUtil.getString(context, FengConstant.LOCAL_SELECT_CITY));
                    if (TextUtils.isEmpty(city) || TextUtils.isEmpty(MapUtil.this.mCityInfo.name) || city.equals(MapUtil.this.mCityInfo.name)) {
                        if (TextUtils.isEmpty(MapUtil.this.mCityInfo.name)) {
                            MapUtil.this.mCityInfo.name = MapUtil.this.mDefaultCityName;
                        }
                        SharedUtil.putString(context, FengConstant.LOCAL_SELECT_CITY, MapUtil.this.mCityInfo.name);
                    } else {
                        StringBuffer sb = new StringBuffer();
                        sb.append("您现在身处");
                        sb.append(MapUtil.this.mCityInfo.name);
                        sb.append("，是否切换到当前城市？");
                        new Builder(context, 3).setMessage(sb.toString()).setPositiveButton("切换城市", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedUtil.putString(context, FengConstant.LOCAL_SELECT_CITY, MapUtil.this.mCityInfo.name);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("暂不切换", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MapUtil.this.mCityInfo.name = city;
                                MapUtil.this.mCityInfo.id = MapUtil.this.getCityIdByName(context, city);
                                dialog.dismiss();
                            }
                        }).create().show();
                    }
                }
                locationClient.stop();
            }
        });
        locationClient.start();
    }

    public static MapUtil newInstance() {
        if (instance == null) {
            instance = new MapUtil();
        }
        return instance;
    }

    private boolean hasInstallBaiduMap() {
        return FengApplication.getInstance().isAppInstalled(this.BAIDU_MAP);
    }

    private boolean hasInstallGaodeMap() {
        return FengApplication.getInstance().isAppInstalled(this.GAODE_MAP);
    }

    public double getDistanceBetween2Points(LatLng point1, LatLng point2) {
        return new BigDecimal(DistanceUtil.getDistance(point1, point2)).setScale(1, 4).doubleValue();
    }

    public String getFormateDistance(double distance) {
        if (distance < 1000.0d) {
            return "距您<1km";
        }
        StringBuffer sb = new StringBuffer("距您");
        sb.append(new BigDecimal(distance / 1000.0d).setScale(1, 5).doubleValue());
        sb.append("km");
        return sb.toString();
    }

    private LatLng bd09_To_Gcj02(double bd_lon, double bd_lat) {
        double x = bd_lon - 0.0065d;
        double y = bd_lat - 0.006d;
        double z = Math.sqrt((x * x) + (y * y)) - (2.0E-5d * Math.sin(y * 52.35987755982988d));
        double theta = Math.atan2(y, x) - (3.0E-6d * Math.cos(x * 52.35987755982988d));
        return new LatLng(z * Math.cos(theta), z * Math.sin(theta));
    }

    private void startBaiduNavigation(Context context, LatLng startPoint, LatLng endPoint, String startAddress, String endAddress) {
        Intent intent = new Intent();
        StringBuffer sb = new StringBuffer("baidumap://map/direction?region=beijing&");
        if (!(startPoint.latitude == 0.0d || startPoint.longitude == 0.0d)) {
            sb.append("&origin=name:");
            sb.append(startAddress + "|latlng:");
            sb.append(startPoint.latitude);
            sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR);
            sb.append(startPoint.longitude);
        }
        sb.append("&destination=name:");
        sb.append(endAddress);
        sb.append("|latlng:");
        sb.append(endPoint.latitude);
        sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR);
        sb.append(endPoint.longitude);
        sb.append("&mode=driving");
        intent.setData(Uri.parse(sb.toString()));
        context.startActivity(intent);
    }

    private void startGaodeNavigation(Context context, LatLng startPoint, LatLng endPoint, String startAddress, String endAddress) {
        LatLng startlatLng = bd09_To_Gcj02(startPoint.latitude, startPoint.longitude);
        LatLng endlatLng = bd09_To_Gcj02(endPoint.latitude, endPoint.longitude);
        StringBuffer sb = new StringBuffer("amapuri://route/plan/?");
        if (!(startPoint.longitude == 0.0d || startPoint.latitude == 0.0d)) {
            sb.append("slat=");
            sb.append(startlatLng.latitude);
            sb.append("&slon&");
            sb.append(startlatLng.longitude);
            sb.append("&");
        }
        sb.append("dlat=");
        sb.append(endlatLng.latitude);
        sb.append("&dlon=");
        sb.append(endlatLng.longitude);
        sb.append("&dname=");
        sb.append(endAddress);
        sb.append("&dev=0");
        sb.append("&t=0");
        Intent intent = new Intent();
        intent.setData(Uri.parse(sb.toString()));
        context.startActivity(intent);
    }

    public boolean hasOpenGps(Context context) {
        return ((LocationManager) context.getSystemService("location")).isProviderEnabled("gps");
    }

    public List<ProvinceInfo> getAllProvinceList(Context context) {
        try {
            if (this.mProvinceList.size() == 0) {
                InputStream is = context.getAssets().open("map_province.json");
                String str = readTextFromSDcard(is);
                is.close();
                JSONArray array = new JSONObject(str).getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    ProvinceInfo info = new ProvinceInfo();
                    info.parser(jsonObj);
                    this.mProvinceList.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mProvinceList;
    }

    public String getProvinceNameById(Context context, int id) {
        if (this.mProvinceList.size() == 0) {
            getAllProvinceList(context);
        }
        for (ProvinceInfo info : this.mProvinceList) {
            if (info.id == id) {
                return info.name;
            }
        }
        return this.mDefaultCityName;
    }

    public List<CityInfo> getAllCityList(Context context) {
        try {
            if (this.mCityList.size() == 0) {
                InputStream is = context.getAssets().open("map_city.json");
                String str = readTextFromSDcard(is);
                is.close();
                JSONArray array = new JSONObject(str).getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    CityInfo info = new CityInfo();
                    info.parser(jsonObj);
                    this.mCityList.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mCityList;
    }

    public int getCityIdByName(Context context, String name) {
        name = cityNameFormat(name);
        if (this.mCityList.size() == 0) {
            getAllCityList(context);
        }
        for (CityInfo info : this.mCityList) {
            if (info.name.equals(name)) {
                return info.id;
            }
        }
        return this.mDefaultCityId;
    }

    public List<CityInfo> getCityListByProvinceId(Context context, int provinceid) {
        List<CityInfo> list = new ArrayList();
        if (this.mCityList.size() == 0) {
            getAllCityList(context);
        }
        for (CityInfo info : this.mCityList) {
            if (info.provinceid == provinceid) {
                list.add(info);
            }
        }
        return list;
    }

    public String getCityNameById(Context context, int cityid) {
        if (this.mCityList.size() == 0) {
            getAllCityList(context);
        }
        for (CityInfo info : this.mCityList) {
            if (info.id == cityid) {
                return info.name;
            }
        }
        return this.mDefaultCityName;
    }

    public CityInfo getCityInfoByName(Context context, String name) {
        name = cityNameFormat(name);
        if (this.mCityList.size() == 0) {
            getAllCityList(context);
        }
        for (CityInfo info : this.mCityList) {
            if (info.name.equals(name)) {
                return info;
            }
        }
        return this.mCityInfo;
    }

    public List<CityInfo> getCityListByName(Context context, String name) {
        name = cityNameFormat(name);
        List<CityInfo> list = new ArrayList();
        if (this.mCityList.size() == 0) {
            getAllCityList(context);
        }
        for (CityInfo info : this.mCityList) {
            if (info.name.contains(name)) {
                list.add(info);
            }
        }
        return list;
    }

    public int getProvinceIdByCityId(Context context, int cityid) {
        if (this.mCityList.size() == 0) {
            getAllCityList(context);
        }
        for (CityInfo info : this.mCityList) {
            if (info.id == cityid) {
                return info.provinceid;
            }
        }
        return this.mDefaultCityId;
    }

    private String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        while (true) {
            String str = bufferedReader.readLine();
            if (str != null) {
                buffer.append(str);
            } else {
                bufferedReader.close();
                reader.close();
                return buffer.toString().replace(" ", "").trim();
            }
        }
    }

    public LocationClient creatLocationClient(Context context) {
        LocationClient locationClient = new LocationClient(context);
        locationClient.setLocOption(getDefaultLocationClientOption(context));
        return locationClient;
    }

    private LocationClientOption getDefaultLocationClientOption(Context context) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setNeedDeviceDirect(false);
        option.setLocationNotify(false);
        option.setIgnoreKillProcess(false);
        option.setIsNeedLocationPoiList(false);
        return option;
    }

    public void openGpsSetting(Context context) {
        context.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    public void showNavigationDialog(Context context, DealerInfo info, LatLng startPoint, String mAddress) {
        List<DialogItemEntity> list = new ArrayList();
        if (newInstance().hasInstallBaiduMap()) {
            list.add(new DialogItemEntity("百度地图", false, 1));
        }
        if (newInstance().hasInstallGaodeMap()) {
            list.add(new DialogItemEntity("高德地图", false, 2));
        }
        if (list.size() > 0) {
            final Context context2 = context;
            final LatLng latLng = startPoint;
            final DealerInfo dealerInfo = info;
            final String str = mAddress;
            CommonDialog.showCommonDialog(context, null, null, list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    Map<String, String> map;
                    if (dialogItemEntity.itemTag == 1) {
                        MapUtil.newInstance().startBaiduNavigation(context2, latLng, new LatLng(dealerInfo.baidulat, dealerInfo.baidulon), str, dealerInfo.getShortname());
                        map = new HashMap();
                        map.put("map", PushConstants.PUSH_TYPE_THROUGH_MESSAGE);
                        map.put("jxsid", dealerInfo.id + "");
                        ((BaseActivity) context2).mLogGatherInfo.addLogBtnEvent(LogBtnConstans.APP_BTN_JXS_MAP_CLICK, map);
                    } else if (dialogItemEntity.itemTag == 2) {
                        MapUtil.newInstance().startGaodeNavigation(context2, latLng, new LatLng(dealerInfo.baidulat, dealerInfo.baidulon), str, dealerInfo.getShortname());
                        map = new HashMap();
                        map.put("map", PushConstants.PUSH_TYPE_UPLOAD_LOG);
                        map.put("jxsid", dealerInfo.id + "");
                        ((BaseActivity) context2).mLogGatherInfo.addLogBtnEvent(LogBtnConstans.APP_BTN_JXS_MAP_CLICK, map);
                    }
                }
            }, true);
            return;
        }
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("geo:" + info.baidulat + MiPushClient.ACCEPT_TIME_SEPARATOR + info.baidulon)));
        } catch (Exception e) {
            ((BaseActivity) context).showThirdTypeToast(2131231308);
        }
    }
}
