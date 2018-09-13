package com.feng.car.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.support.v4.util.ArrayMap;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.baidu.location.BDLocation;
import com.feng.car.FengApplication;
import com.feng.library.okhttp.callback.StringCallback;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.feng.library.utils.Md5Utils;
import com.feng.library.utils.SharedUtil;
import com.netease.LDNetDiagnoUtils.LDNetUtil;
import com.stub.StubApp;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.ContextUtil;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import okhttp3.Call;

public class LogGatherReadUtil {
    private static LogGatherReadUtil mInstance;
    private String mAndroidID = "";
    private String mAppVersion = "2.9";
    private String mChannel = "";
    private int mChargeStatus = 0;
    private String mCity = "";
    private String mCountry = "";
    private String mCurrentPage = "";
    private String mDeviceID = "";
    private String mDistrict = "";
    private String mElectricity = "";
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LogGatherReadUtil.this.initChangData(FengApplication.getInstance());
                    return;
                default:
                    return;
            }
        }
    };
    private String mImei = "";
    private String mImpressIp = "192.168.1.2";
    private String mImsi = "";
    private String mLan = "";
    private int mLandscape = 0;
    private String mLanguage = "";
    private String mLatitude = "";
    private String mLaunchidentifying = "";
    private String mLocal = "";
    private String mLogVersion = "2.2.3";
    private String mMacAddress = "";
    private String mMobile = "";
    private int mOperatorID = 1;
    private ArrayMap<String, String> mPageMap = null;
    private String mPageidentifying = "";
    private String mProvince = "";
    private String mReferer = "";
    private int mScreenHight = 1920;
    private int mScreenWidth = 1080;
    private String mSwitchidentifying = "";
    private String mWifi = "";
    private String mWifiAddress = "";
    private String mXposed = "";
    private String mlongitude = "";

    public String getReferer() {
        return this.mReferer;
    }

    public void resetLaunchidentifying() {
        this.mLaunchidentifying = UUID.randomUUID().toString();
    }

    public void resetSwitchidentifying() {
        this.mSwitchidentifying = UUID.randomUUID().toString();
    }

    public void SetPageidentifying(String pageidentifying) {
        this.mPageidentifying = pageidentifying;
    }

    public ArrayMap<String, String> getPageMap() {
        initMapData();
        return this.mPageMap;
    }

    public String getCurrentPage() {
        return this.mCurrentPage;
    }

    public void setReferer(String strReferer) {
        if (!TextUtils.isEmpty(strReferer)) {
            this.mReferer = strReferer;
        }
    }

    public void setCurrentPage(String strPage) {
        if (!TextUtils.isEmpty(strPage)) {
            this.mCurrentPage = strPage;
        }
    }

    public void setScreenOrientation(Context context) {
        if (context.getResources().getConfiguration().orientation == 2) {
            this.mLandscape = 1;
        } else if (context.getResources().getConfiguration().orientation == 1) {
            this.mLandscape = 0;
        }
    }

    public static LogGatherReadUtil getInstance() {
        if (mInstance == null) {
            mInstance = new LogGatherReadUtil();
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mAppVersion = FengUtil.getVersion(context);
        this.mScreenWidth = FengUtil.getScreenWidth(context);
        this.mScreenHight = FengUtil.getScreenHeight(context);
        this.mDeviceID = getUniqueId(context);
        initChannel(context);
        initChangData(context);
        initMapData();
        checkXposed();
    }

    private void initChannel(Context context) {
        try {
            this.mChannel = context.getPackageManager().getApplicationInfo(ContextUtil.getPackageName(), 128).metaData.getString("UMENG_CHANNEL").trim();
            if (TextUtils.isEmpty(this.mChannel)) {
                this.mChannel = "";
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            this.mChannel = "";
        }
    }

    private void initChangData(Context context) {
        initCurrentLanguage(context);
        initCurrentTimeZone();
        initNetInfo(context);
        initPhoneInfo(context);
        initPowerInfo(context);
        initWifiInfo(context);
        initAndroidID(context);
        this.mHandler.sendEmptyMessageDelayed(0, 120000);
    }

    private void checkXposed() {
        try {
            for (PackageInfo info : FengApplication.getInstance().getPackageManager().getInstalledPackages(0)) {
                if ("de.robv.android.xposed.installer".equals(info.applicationInfo.packageName)) {
                    this.mXposed = "xposed";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLocationClick(int nSnsID, int nResourceID, int nResourceType, String expendedJson) {
        httpGetLog(assembleUrl(nSnsID + "", nResourceID + "", nResourceType + "", "", "", HttpConstant.BASE_LOG_URL + "locationclick&", "", "", "", "", "", "", "", "", expendedJson, "", "", ""));
    }

    public void addAdPvLog(int nAdId, int nSeat, String expendedJson, boolean isStart) {
        httpGetLog(assembleUrl("", "", "", nAdId + "", nSeat + "", HttpConstant.BASE_FENG_AD + "pvLog?action=&", isStart ? getBeijingTime() + "" : "", isStart ? "" : getBeijingTime() + "", "", "", "", "", "", "", expendedJson, "", "", ""));
    }

    public void addAdClickLog(int nAdId, int nSeat, String expendedJson) {
        httpGetLog(assembleUrl("", "", "", nAdId + "", nSeat + "", HttpConstant.BASE_FENG_AD + "ckLog?action=&", "", "", "", "", "", "", "", "", expendedJson, "", "", ""));
    }

    public String getAdFormatUrl(int nAdId, int nSeat, String expendedJson) {
        return assembleUrl("", "", "", nAdId + "", nSeat + "", HttpConstant.BASE_FENG_AD + "ckLog?action=&", "", "", "", "", "", "", "", "", expendedJson, "", "", "");
    }

    public void addNetElapsedTime(String time, String interfaceurl, String resultcode, String errormsg, String expendedJson) {
        httpGetLog(assembleUrl("", "", "", "", "", HttpConstant.BASE_LOG_URL + "netelapsedtime&", "", "", "", "", time, interfaceurl, resultcode, errormsg, expendedJson, "", "", ""));
    }

    public void addPercentageLog(int nSnsID, int nResourceID, int nResourceType, int type, String percent, String expendedJson) {
        httpGetLog(assembleUrl(nSnsID + "", nResourceID + "", nResourceType + "", "", "", HttpConstant.BASE_LOG_URL + (type == 0 ? "articlepercentage&" : "videopercentage&"), "", "", type == 0 ? percent : "", type == 1 ? percent : "", "", "", "", "", expendedJson, "", "", ""));
    }

    public String apiRequest(String url, String expendedJson) {
        return assembleUrl("", "", "", "", "", url + "?", "", "", "", "", "", "", "", "", expendedJson, "", "", "");
    }

    public void httpGetLog(String formatUrl) {
        OkHttpUtils.get().url(formatUrl).build().execute(StubApp.getOrigApplicationContext(FengApplication.getInstance().getApplicationContext()), new StringCallback() {
            public void onAfter() {
                super.onAfter();
            }

            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            public void onResponse(String response) {
            }
        });
    }

    public String assembleUrl(String nSnsID, String nResourceID, String nResourceType, String adid, String seat, String strUrl, String startime, String endtime, String readPercent, String videoPercent, String elapsedTime, String interfaceurl, String resultcode, String errormsg, String expandedJson, String page, String referer, String pageidentifying) {
        try {
            if (TextUtils.isEmpty(this.mDeviceID)) {
                this.mDeviceID = getUniqueId(FengApplication.getInstance());
            }
            if (TextUtils.isEmpty(this.mChannel)) {
                initChannel(FengApplication.getInstance());
            }
            if (TextUtils.isEmpty(page)) {
                page = this.mCurrentPage;
            }
            if (TextUtils.isEmpty(referer)) {
                referer = this.mReferer;
            }
            if (TextUtils.isEmpty(pageidentifying)) {
                pageidentifying = this.mPageidentifying;
            }
            StringBuffer buffer = new StringBuffer("production=laosiji&production_version=android");
            buffer.append(encode(this.mAppVersion)).append("&systemtag=4&mac_address=").append(this.mMacAddress).append("&appid=android&osversion=").append(encode(VERSION.RELEASE)).append("&chargestatus=").append(this.mChargeStatus).append("&latitude=").append(this.mLatitude).append("&longitude=").append(this.mlongitude).append("&country=").append(this.mCountry).append("&province=").append(this.mProvince).append("&city=").append(this.mCity).append("&district=").append(this.mDistrict).append("&electricity=").append(this.mElectricity).append("&imsi=").append(this.mImsi).append("&impresstime=").append(getBeijingTime()).append("&ip=").append(this.mImpressIp).append("&language=").append(this.mLanguage).append("&platform=2&version=").append(this.mLogVersion).append("&screen_width=").append(this.mScreenWidth).append("&screen_hight=").append(this.mScreenHight).append("&brand=").append(encode(Build.BRAND)).append("&devicename=").append(encode(Build.MODEL)).append("&deviceid=").append(this.mDeviceID).append("&imei=").append(this.mImei).append("&android=").append(this.mAndroidID).append("&local=").append(this.mLocal).append("&lan=").append(this.mLan).append("&wifi=").append(this.mWifi).append("&wifi_address=").append(this.mWifiAddress).append("&pvuid=").append("&uid=").append(getUserTokenId()).append("&mobile=").append(this.mMobile).append("&operator=").append(this.mOperatorID).append("&landscape=").append(this.mLandscape).append("&channel=").append(this.mChannel).append("&winlog=").append(encode(this.mXposed)).append("&referer=").append(encode(referer)).append("&page=").append(encode(page)).append("&snsid=").append(nSnsID).append("&resourceid=").append(nResourceID).append("&resourcetype=").append(nResourceType).append("&adid=").append(adid).append("&seat=").append(seat).append("&startime=").append(startime).append("&endtime=").append(endtime).append("&read_percent=").append(encode(readPercent)).append("&video_percent=").append(encode(videoPercent)).append("&elapsed_time=").append(elapsedTime).append("&interfaceurl=").append(encode(interfaceurl)).append("&resultcode=").append(resultcode).append("&errormsg=").append(encode(errormsg)).append("&launchidentifying=").append(this.mLaunchidentifying).append("&switchidentifying=").append(this.mSwitchidentifying).append("&pageidentifying=").append(pageidentifying).append("&baseexpanded=").append(encode(expandedJson));
            return strUrl + "md5=" + Md5Utils.md5(buffer.toString()) + "&" + buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return strUrl;
        }
    }

    public long getBeijingTime() {
        return System.currentTimeMillis() + ((long) TimeZone.getTimeZone("GMT+8").getRawOffset());
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private String getUserToken() {
        if (FengApplication.getInstance().isLoginUser()) {
            return FengApplication.getInstance().getUserInfo().token;
        }
        String strUserToken = SharedUtil.getString(FengApplication.getInstance(), "usertouristtoken");
        if (TextUtils.isEmpty(strUserToken)) {
            return "";
        }
        return strUserToken;
    }

    private int getUserTokenId() {
        if (FengApplication.getInstance().isLoginUser()) {
            return FengApplication.getInstance().getUserInfo().id;
        }
        return SharedUtil.getInt(FengApplication.getInstance(), "usertouristId", 0);
    }

    public String getUniqueId(Context context) {
        String id = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID) + Build.SERIAL;
        try {
            return encode(toMD5(id));
        } catch (Exception e) {
            e.printStackTrace();
            return encode(id);
        }
    }

    private String toMD5(String text) throws NoSuchAlgorithmException {
        byte[] digest = MessageDigest.getInstance("MD5").digest(text.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public void setLocationInfo(BDLocation locationInfo) {
        if (locationInfo != null) {
            try {
                this.mLatitude = encode(locationInfo.getLatitude() + "");
                this.mlongitude = encode(locationInfo.getLongitude() + "");
                this.mCountry = encode(locationInfo.getCountry());
                this.mProvince = encode(locationInfo.getProvince());
                this.mCity = encode(locationInfo.getCity());
                this.mDistrict = encode(locationInfo.getDistrict());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initNetInfo(Context context) {
        try {
            NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (info == null) {
                this.mLan = "";
            } else if (info.getType() == 1) {
                this.mLan = encode(UtilityImpl.NET_TYPE_WIFI);
            } else if (info.getType() == 0) {
                int subType = info.getSubtype();
                if (subType == 4 || subType == 1 || subType == 2) {
                    this.mLan = encode(UtilityImpl.NET_TYPE_2G);
                } else if (subType == 3 || subType == 8 || subType == 6 || subType == 5 || subType == 12) {
                    this.mLan = encode(UtilityImpl.NET_TYPE_3G);
                } else {
                    this.mLan = encode(UtilityImpl.NET_TYPE_4G);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAndroidID(Context context) {
        try {
            this.mAndroidID = encode(System.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initCurrentLanguage(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            this.mLanguage = encode(language + "_" + locale.getCountry());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initCurrentTimeZone() {
        try {
            this.mLocal = encode(TimeZone.getDefault().getDisplayName(false, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initWifiInfo(Context context) {
        try {
            if (FengUtil.isWifiConnectivity(context)) {
                WifiInfo info = ((WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI)).getConnectionInfo();
                this.mMacAddress = encode(info.getMacAddress());
                this.mWifi = encode(info.getSSID());
                this.mWifiAddress = encode(LDNetUtil.pingGateWayInWifi(context));
                this.mImpressIp = encode(LDNetUtil.getLocalIpByWifi(context));
                return;
            }
            this.mImpressIp = encode(LDNetUtil.getLocalIpBy3G());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPhoneInfo(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            if (TextUtils.isEmpty(this.mImsi)) {
                this.mImsi = "";
            }
            if (TextUtils.isEmpty(this.mImei)) {
                this.mImei = "";
            }
            String operatorString = tm.getSimOperator();
            if (operatorString == null) {
                this.mOperatorID = 4;
            }
            if (operatorString.equals("46000") || operatorString.equals("46002")) {
                this.mOperatorID = 1;
            } else if (operatorString.equals("46001")) {
                this.mOperatorID = 2;
            } else if (operatorString.equals("46003")) {
                this.mOperatorID = 3;
            } else {
                this.mOperatorID = 4;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPowerInfo(Context context) {
        try {
            Intent batteryStatus = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int status = batteryStatus.getIntExtra("status", -1);
            int i = (status == 2 || status == 5) ? 1 : 0;
            this.mChargeStatus = i;
            this.mElectricity = encode(((((float) batteryStatus.getIntExtra("level", -1)) / ((float) batteryStatus.getIntExtra("scale", -1))) * 100.0f) + "%");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMapData() {
        if (this.mPageMap == null) {
            this.mPageMap = new ArrayMap();
            this.mPageMap.put("activity.SearchCarResultActivity", LogConstans.APP_SEARCH_CAR_RESULT);
            this.mPageMap.put("activity.PriceConditionActivity", LogConstans.APP_SEARCH_CAR_PRICE);
            this.mPageMap.put("activity.LevelConditionActivity", LogConstans.APP_SEARCH_CAR_LEVEL);
            this.mPageMap.put("activity.MoreConditionActivity", LogConstans.APP_SEARCH_CAR_MORE);
            this.mPageMap.put("activity.BrandConditionActivity", LogConstans.APP_SEARCH_CAR_BRAND);
            this.mPageMap.put("activity.AllCarActivity", LogConstans.APP_SEARCH_CAR_MODEL_LIST);
            this.mPageMap.put("activity.StopSellingCarActivity", LogConstans.APP_SEARCH_CAR_STOP_UNSOLD_LIST);
            this.mPageMap.put("activity.VehicleActivity", LogConstans.APP_CAR);
            this.mPageMap.put("activity.MediaMeasurementDetailActivity", LogConstans.APP_CAR_SERIES_EVALUATING_GUIDE);
            this.mPageMap.put("activity.VehicleClassDetailActivity", LogConstans.APP_CAR_SERIES_DEALER);
            this.mPageMap.put("activity.SingleConfigureActivity", LogConstans.APP_CAR_SINGLE_CONFIGURATION);
            this.mPageMap.put("activity.DistributorMapActivity", LogConstans.APP_CAR_DEALER_MAP);
            this.mPageMap.put("activity.CityListActivity", LogConstans.APP_CAR_DEALER_CITY);
            this.mPageMap.put("activity.CarModleComparisonActivity", LogConstans.APP_CAR_PK);
            this.mPageMap.put("activity.SelectCarByBrandActivity", LogConstans.APP_CAR_PK_SEL_BRAND);
            this.mPageMap.put("activity.AllCarSeriesActivity", LogConstans.APP_CAR_PK_SEL_CARSERIES);
            this.mPageMap.put("activity.AddCarModelActivity", LogConstans.APP_CAR_PK_SEL_CARMODEL);
            this.mPageMap.put("activity.CommentActivity", LogConstans.APP_MSG_COMMENT);
            this.mPageMap.put("activity.AtMeActivity", LogConstans.APP_MSG_AT_MINE);
            this.mPageMap.put("activity.PraiseActivity", LogConstans.APP_MSG_PRAISE);
            this.mPageMap.put("activity.PrivateLetterListActivity", LogConstans.APP_MSG_PRIVATE_LETTER_LIST);
            this.mPageMap.put("activity.PrivateChatActivity", LogConstans.APP_PRIVATE_LETTER_PARTICULARS);
            this.mPageMap.put("activity.SystemNoticeActivity", LogConstans.APP_MSG_SYSTEM_MESSAGES);
            this.mPageMap.put("activity.FansFollowActivity", LogConstans.APP_FOLLOWS_LIST);
            this.mPageMap.put("activity.FindFollowActivity", LogConstans.APP_FOLLOWS_FIND_LIST);
            this.mPageMap.put("activity.FansActivity", LogConstans.APP_FANS_LIST);
            this.mPageMap.put("activity.EditUserInfoActivity", LogConstans.APP_USER_EDIT_INFORMATION);
            this.mPageMap.put("activity.CropActivity", LogConstans.APP_PICTURE_CROP);
            this.mPageMap.put("activity.CollectionActivity", LogConstans.APP_MINE_COLLECT);
            this.mPageMap.put("activity.DraftActivity", LogConstans.APP_MINE_DRAFT);
            this.mPageMap.put("activity.HistoryActivity", LogConstans.APP_MINE_SEE_HISTORY);
            this.mPageMap.put("activity.BlackListActivity", LogConstans.APP_MINE_BLACKLIST);
            this.mPageMap.put("activity.SnsAllActivity", LogConstans.APP_MINE_NEW_ARTICLE_LIST);
            this.mPageMap.put("activity.SettingActivity", LogConstans.APP_MINE_SETTING);
            this.mPageMap.put("activity.SettingAccountSecurityActivity", LogConstans.APP_SET_ACCOUNT_SECURITY);
            this.mPageMap.put("activity.PushSettingActivity", LogConstans.APP_SET_PUSH);
            this.mPageMap.put("activity.PrivateSettingActivity", LogConstans.APP_SET_PRIVACY);
            this.mPageMap.put("activity.SettingImageQualityActivity", LogConstans.APP_SET_PICTURE);
            this.mPageMap.put("activity.SettingVideoAutoPlayActivity", LogConstans.APP_SET_AUTO_VIDEO);
            this.mPageMap.put("activity.AboutUsActivity", LogConstans.APP_SET_ABOUT_US);
            this.mPageMap.put("activity.FeedbackActivity", LogConstans.APP_SET_FEEDBACK);
            this.mPageMap.put("activity.NetCheckActivity", LogConstans.APP_NETWORK_CHECK);
            this.mPageMap.put("activity.SearchCityActivity", LogConstans.APP_CAR_DEALER_SEARCH_CITY);
            this.mPageMap.put("activity.CircleSearchActivity", LogConstans.APP_SEARCH_CIRCLE);
            this.mPageMap.put("activity.ArticleDetailActivity", LogConstans.APP_THREAD);
            this.mPageMap.put("activity.VideoFinalPageActivity", LogConstans.APP_VIDEO_PLAY_FINAL);
            this.mPageMap.put("activity.PopularProgramListActivity", LogConstans.APP_PROGRAM);
            this.mPageMap.put("activity.AtUserActivity", LogConstans.APP_AT_USER_LIST);
            this.mPageMap.put("activity.LoginBy5X", LogConstans.APP_LOGIN_5X);
            this.mPageMap.put("activity.GuideActivity", LogConstans.APP_GUIDE);
            this.mPageMap.put("activity.AudioPlayDetailActivity", LogConstans.APP_AUDIO_PLAY);
            this.mPageMap.put("activity.CommentReplyListActivity", LogConstans.APP_COMMENT_REPLY_LIST);
            this.mPageMap.put("activity.WatchVideoActivity", LogConstans.APP_VIDEO_PLAY_LOCAL);
            this.mPageMap.put("activity.PostInitActivity", LogConstans.APP_SEND_THREAD);
            this.mPageMap.put("activity.SendCommentActivity", LogConstans.APP_SEND_COMMENT);
            this.mPageMap.put("activity.SendPrivateLetterActivity", LogConstans.APP_SEND_PRIVATE_LETTER);
            this.mPageMap.put("activity.SelectPhotoActivity", LogConstans.APP_SEND_SEL_PICTURE);
            this.mPageMap.put("activity.SelectVideoActivity", LogConstans.APP_SEND_SEL_VIDEO);
            this.mPageMap.put("activity.SelectImageVideoActivity", LogConstans.APP_SEND_SEL_PIC_VIDEO);
            this.mPageMap.put("activity.AddSubjectActivity", LogConstans.APP_SEND_SEL_CIRCLE);
            this.mPageMap.put("activity.CameraActivity", LogConstans.APP_SEND_TAKE_RECORD);
            this.mPageMap.put("activity.CameraPreviewActivity", LogConstans.APP_SEND_PICTURE_PRE);
            this.mPageMap.put("activity.AllProgramActivity", LogConstans.APP_HOME_PROGRAM);
            this.mPageMap.put("activity.CircleFindActivity", LogConstans.APP_MINE_ALL_CIRCLE);
            this.mPageMap.put("activity.ListenedHistoryActivity", LogConstans.APP_MINE_DIRVER_RECOMMEND);
            this.mPageMap.put("activity.QRCodeActivity", LogConstans.APP_MINE_QR_CODE);
            this.mPageMap.put("activity.ScanningLoginPCActivity", LogConstans.APP_MINE_QR_CODE_LOGIN);
            this.mPageMap.put("activity.OldDriverChooseCarActivity", LogConstans.APP_SEL_CAR_HELP);
            this.mPageMap.put("activity.MessageActivity", LogConstans.APP_MESSAGE);
            this.mPageMap.put("activity.PersonalSearchActivity", LogConstans.APP_PROFILE_SEARCH);
            this.mPageMap.put("activity.ShowNativeImageActivity", LogConstans.APP_SHOW_BIG_PICTURE_NATIVE);
            this.mPageMap.put("activity.ShowCarImageNewActivity", LogConstans.APP_SHOW_BIG_PICTURE_CAR);
            this.mPageMap.put("activity.ShowBigImageActivity", LogConstans.APP_SHOW_BIG_PICTURE);
            this.mPageMap.put("activity.InvoiceUploadActivity", LogConstans.APP_OWNER_VOUCHER);
            this.mPageMap.put("activity.WriteBuyCarInfoActivity", LogConstans.APP_OWNER_CAR_INFO);
            this.mPageMap.put("activity.PricesSeriesChartActivity", LogConstans.APP_CAR_ONWER_PRICE_RANK_SERIES);
            this.mPageMap.put("activity.PricesModelChartActivity", LogConstans.APP_CAR_ONWER_PRICE_RANK_MODEL);
            this.mPageMap.put("activity.SearchConditionActivity", LogConstans.APP_SEARCH_CAR_CONDITION);
            this.mPageMap.put("activity.VideoCacheActivity", LogConstans.APP_MINE_VIDEO_CACHE);
            this.mPageMap.put("activity.WriteGoodsServeActivity", LogConstans.APP_DEALER_GOODS_SERVES);
            this.mPageMap.put("activity.RegisterStoreNoWechatActivity", LogConstans.APP_DEALER_REGISTER_WEIXIN);
            this.mPageMap.put("activity.RegisterStoreActivity", LogConstans.APP_DEALER_OPEN_SHOP);
            this.mPageMap.put("activity.UpLoadIDCardActivity", LogConstans.APP_DEALER_UPLOAD_IDENTITY_CARD);
            this.mPageMap.put("activity.OldDriverChooseCarNextActivity", LogConstans.APP_CAR_STRATEGY);
            this.mPageMap.put("activity.CarConfigureCompareActivity", LogConstans.APP_CAR_CONFIGURATION);
            this.mPageMap.put("activity.EditUserInfoNextActivity", LogConstans.APP_USER_EDIT_NICKNAME);
            this.mPageMap.put("activity.LoginActivity", LogConstans.APP_LOGIN);
            this.mPageMap.put("activity.PushSettingSecondaryActivity", LogConstans.APP_PUSH_COMMENT);
            this.mPageMap.put("activity.SettingAccountPhoneActivity", LogConstans.APP_ACCOUNT_BINDING_MOBILE);
            this.mPageMap.put("activity.ViewPointActivity", LogConstans.APP_OLDDIRVERVIEW);
            this.mPageMap.put("activity.WebActivity", LogConstans.APP_WEB);
            this.mPageMap.put("activity.WalletActivity", LogConstans.APP_WALLET);
            this.mPageMap.put("activity.WalletDetailActivity", LogConstans.APP_WALLET_DETAIL);
            this.mPageMap.put("activity.ShortVideoCropActivity", LogConstans.APP_SEND_THREAD_VIDEO_CUT);
            this.mPageMap.put("activity.ShortVideoSelectImageActivity", LogConstans.APP_SEND_THREAD_VIDEO_COVER);
            this.mPageMap.put("activity.RearrangeActivity", LogConstans.APP_SEND_THREAD_SORT);
        }
    }
}
