package com.feng.car;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Base64;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.lbsapi.auth.LBSAuthManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.BaseSearchResult;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.feng.car.activity.ArticleDetailActivity;
import com.feng.car.activity.AtMeActivity;
import com.feng.car.activity.CommentActivity;
import com.feng.car.activity.FansActivity;
import com.feng.car.activity.HomeActivity;
import com.feng.car.activity.LoginActivity;
import com.feng.car.activity.NewSubjectActivity;
import com.feng.car.activity.PopularProgramListActivity;
import com.feng.car.activity.PrivateChatActivity;
import com.feng.car.activity.SystemNoticeActivity;
import com.feng.car.activity.TransparentActivity;
import com.feng.car.activity.ViewPointActivity;
import com.feng.car.activity.WebActivity;
import com.feng.car.db.SparkDB;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.AudioStateEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.AudioRecordInfoManager;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.library.emoticons.keyboard.utils.DrawableManager;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.okhttp.callback.StringCallback;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.feng.library.utils.DateUtil;
import com.feng.library.utils.Md5Utils;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.feng.library.utils.WifiUtil;
import com.google.gson.reflect.TypeToken;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.stub.StubApp;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.ugc.TXUGCBase;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.Request;
import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.json.JSONObject;

public class FengApplication extends Application {
    private static FengApplication mApplication;
    private List<AdvertInfo> mAdList;
    private String mApiDomainUrl = "";
    private AudioStateEvent mAudioState = new AudioStateEvent();
    private int mConnectState = 0;
    private Map<Integer, Boolean> mFollowState = new HashMap();
    private LinkedList<String> mHdImageUrl;
    private boolean mIsBackGround = false;
    private boolean mIsFollowShow = false;
    private boolean mIsOpenTest = false;
    private boolean mIsWifi = false;
    private Pattern mPattern5xThread = Pattern.compile("(http|https)://www.xxxxxbbs.com/thread/(\\d+)|(http|https)://m.xxxxxbbs.com/th/info\\?id=(\\d+)");
    private Pattern mPatternCircle = Pattern.compile(formatPatternUrl(HttpConstant.FENGHTML5M) + "community/(\\d+)/(\\d+)");
    private Pattern mPatternHotPro = Pattern.compile(formatPatternUrl(HttpConstant.FENGHTML5M) + "hotshow/(\\d+)|" + formatPatternUrl(HttpConstant.FENGHTML_PC) + "new_web/html/hotshow_details.html\\?id=(\\d+)");
    private Pattern mPatternPDiscuss = Pattern.compile(formatPatternUrl(HttpConstant.FENGHTML5M) + "autodiscuss/p/(\\d+)");
    private Pattern mPatternSeries = Pattern.compile(formatPatternUrl(HttpConstant.FENGHTML5M) + "car/seriesinfo/(\\d+)");
    private Pattern mPatternThread = Pattern.compile(formatPatternUrl(HttpConstant.FENGHTML5M) + "thread/(\\d+)|" + formatPatternUrl(HttpConstant.FENGHTML_PC) + "thread/(\\d+)");
    private Pattern mPatternUDiscuss = Pattern.compile(formatPatternUrl(HttpConstant.FENGHTML5M) + "autodiscuss/u/(\\d+)");
    private PushAgent mPushAgent;
    private String mRedAlertUrl = "";
    private Map<String, Object> mSeverceStateMap = new HashMap();
    private SparkDB mSparkDB;
    private int mStatusBarHeight = 0;
    private UserInfo mUserInfo;
    private int videoBdLoginFlag = 0;

    public static synchronized FengApplication getInstance() {
        FengApplication fengApplication;
        synchronized (FengApplication.class) {
            fengApplication = mApplication;
        }
        return fengApplication;
    }

    public boolean isBackGround() {
        return this.mIsBackGround;
    }

    public boolean getIsOpenTest() {
        return this.mIsOpenTest;
    }

    public void setIsOpenTest(boolean openTest) {
        this.mIsOpenTest = openTest;
    }

    public void setApiDomainUrl(String apiDomainUrl) {
        this.mApiDomainUrl = apiDomainUrl;
    }

    public String getRedAlertUrl() {
        return this.mRedAlertUrl;
    }

    public void setRedAlertUrl(String redAlertUrl) {
        this.mRedAlertUrl = redAlertUrl;
    }

    public boolean isAllowSendVideo() {
        if (!isLoginUser() || SharedUtil.getInt(this, "login_canpublishvideo", 1) > 0) {
            return true;
        }
        return false;
    }

    public int getVideoBdLoginFlag() {
        return this.videoBdLoginFlag;
    }

    public void setVideoBdLoginFlag(int videoBdLoginFlag) {
        this.videoBdLoginFlag = videoBdLoginFlag;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        this.mIsOpenTest = SharedUtil.getBoolean(this, "setting_test_open", false);
        String strChannel = "";
        try {
            strChannel = getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getString("UMENG_CHANNEL").trim();
            if (TextUtils.isEmpty(strChannel)) {
                strChannel = "LSJOfficial";
            }
        } catch (Exception e) {
            e.printStackTrace();
            strChannel = "LSJOfficial";
        }
        UMConfigure.init(this, "58624494310c931c3f001e4b", strChannel, 1, "c2298ef77c5443d2b82e3f46f73c2d98");
        TalkingDataAppCpa.init(this, "95F92940C3EC45B3820F20D1E83E0CB4", strChannel);
        initUMengPush();
        this.mAdList = new ArrayList();
        mApplication = this;
        initApiUrl();
        ActivityManager.init(this);
        AudioRecordInfoManager.getInstance().clear();
        this.mSparkDB = new SparkDB(this);
        new Thread(new Runnable() {
            public void run() {
                FengApplication.this.init();
            }
        }).start();
        SDKInitializer.initialize(StubApp.getOrigApplicationContext(getApplicationContext()));
        initTecentBugly();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            public void onActivityStarted(Activity activity) {
            }

            public void onActivityResumed(Activity activity) {
                if (FengApplication.this.mIsBackGround) {
                    FengApplication.this.mIsBackGround = false;
                    LogGatherReadUtil.getInstance().resetSwitchidentifying();
                }
            }

            public void onActivityPaused(Activity activity) {
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            public void onActivityDestroyed(Activity activity) {
            }
        });
        TXUGCBase.getInstance().setLicence(this, "http://license.vod2.myqcloud.com/license/v1/b2b3910949a7b10f29f1d672a7be4704/TXUgcSDK.licence", "b77321d85942fefeac5a40aba60bb6df");
        TXLiveBase.setConsoleEnabled(true);
        TXLiveBase.setLogLevel(1);
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == 20) {
            this.mIsBackGround = true;
        }
    }

    private void initTecentBugly() {
        String packageName = getPackageName();
        String processName = getProcessName(Process.myPid());
        UserStrategy strategy = new UserStrategy(this);
        boolean z = processName == null || processName.equals(packageName);
        strategy.setUploadProcess(z);
        CrashReport.initCrashReport(this, "e141b02af6", true, strategy);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0046 A:{SYNTHETIC, Splitter: B:17:0x0046} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0053 A:{SYNTHETIC, Splitter: B:24:0x0053} */
    private static java.lang.String getProcessName(int r8) {
        /*
        r2 = 0;
        r3 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x0040 }
        r5 = new java.io.FileReader;	 Catch:{ Throwable -> 0x0040 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0040 }
        r6.<init>();	 Catch:{ Throwable -> 0x0040 }
        r7 = "/proc/";
        r6 = r6.append(r7);	 Catch:{ Throwable -> 0x0040 }
        r6 = r6.append(r8);	 Catch:{ Throwable -> 0x0040 }
        r7 = "/cmdline";
        r6 = r6.append(r7);	 Catch:{ Throwable -> 0x0040 }
        r6 = r6.toString();	 Catch:{ Throwable -> 0x0040 }
        r5.<init>(r6);	 Catch:{ Throwable -> 0x0040 }
        r3.<init>(r5);	 Catch:{ Throwable -> 0x0040 }
        r1 = r3.readLine();	 Catch:{ Throwable -> 0x005f, all -> 0x005c }
        r5 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Throwable -> 0x005f, all -> 0x005c }
        if (r5 != 0) goto L_0x0034;
    L_0x0030:
        r1 = r1.trim();	 Catch:{ Throwable -> 0x005f, all -> 0x005c }
    L_0x0034:
        if (r3 == 0) goto L_0x0039;
    L_0x0036:
        r3.close();	 Catch:{ IOException -> 0x003b }
    L_0x0039:
        r2 = r3;
    L_0x003a:
        return r1;
    L_0x003b:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0039;
    L_0x0040:
        r4 = move-exception;
    L_0x0041:
        r4.printStackTrace();	 Catch:{ all -> 0x0050 }
        if (r2 == 0) goto L_0x0049;
    L_0x0046:
        r2.close();	 Catch:{ IOException -> 0x004b }
    L_0x0049:
        r1 = 0;
        goto L_0x003a;
    L_0x004b:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0049;
    L_0x0050:
        r5 = move-exception;
    L_0x0051:
        if (r2 == 0) goto L_0x0056;
    L_0x0053:
        r2.close();	 Catch:{ IOException -> 0x0057 }
    L_0x0056:
        throw r5;
    L_0x0057:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0056;
    L_0x005c:
        r5 = move-exception;
        r2 = r3;
        goto L_0x0051;
    L_0x005f:
        r4 = move-exception;
        r2 = r3;
        goto L_0x0041;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.FengApplication.getProcessName(int):java.lang.String");
    }

    private void init() {
        ZXingLibrary.initDisplayOpinion(this);
        Fresco.initialize(this, ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).setResizeAndRotateEnabledForNetwork(true).build());
        DrawableManager.getInstance().initAllEmoticonsDrawable(this);
        initFlymePush();
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        if (getPackageName().equals("com.feng.live")) {
            PlatformConfig.setQQZone("1106433954", "ywOLmmGEr5tBgqWx");
            PlatformConfig.setSinaWeibo("3956359386", "1586b7eb4da515ac6c8e8b384ddaa218", "http://sns.whalecloud.com/sina2/callback");
            HttpConstant.BASE_WX_APP_ID = "wxfe644f5c9917518d";
            PlatformConfig.setWeixin(HttpConstant.BASE_WX_APP_ID, "4f719af2920ad6093855d81ef59d3e13");
            return;
        }
        PlatformConfig.setQQZone("1105834165", "F9hWnYuxLLuuTZRa");
        PlatformConfig.setSinaWeibo("2635887947", "8e4172609d8226c5605088154fa46ee3", "http://sns.whalecloud.com/sina2/callback");
        HttpConstant.BASE_WX_APP_ID = "wx9fe65e722ec05c3a";
        PlatformConfig.setWeixin(HttpConstant.BASE_WX_APP_ID, "ece5beb5d8082fadd2fdfae0713b8fec");
    }

    private void initFlymePush() {
        if (MzSystemUtils.isBrandMeizu()) {
            PushManager.register(this, "111657", "c44d12a38c4747ea914b848b854bf88d");
        }
    }

    private void initApiUrl() {
        String apiUrl = SharedUtil.getString(this, "api_key").trim();
        if (TextUtils.isEmpty(apiUrl)) {
            HttpConstant.YWFHTTPURL = "http://api.laosiji.com/";
            HttpConstant.FENGHTML5M = "http://m.laosiji.com/";
            HttpConstant.BASE_LOG_URL = "http://logstat.laosiji.com/index.html?action=";
            HttpConstant.BASE_FENG_AD = HttpConstant.BASE_FENG_AD_ON_LINE;
            HttpConstant.BASE_WX_APP_ID = HttpConstant.BASE_WX_APP_ID_ONLINE;
            HttpConstant.BASE_WX_MIN_ORIGINAL_ID = HttpConstant.WX_MIN_ORIGINAL_ID_ONLINE;
        } else if (apiUrl.equals("258369")) {
            HttpConstant.YWFHTTPURL = HttpConstant.YWFHTTPURL_EXPLOIT_TEST;
            HttpConstant.FENGCAR = HttpConstant.FENGCAR_TEST;
            HttpConstant.BASE_LOG_URL = "http://172.18.30.195/index.html?action=";
            HttpConstant.BASE_FENG_AD = HttpConstant.BASE_FENG_AD_TEST;
            HttpConstant.BASE_WX_APP_ID = HttpConstant.BASE_WX_APP_ID_TEST;
            HttpConstant.BASE_WX_MIN_ORIGINAL_ID = HttpConstant.WX_MIN_ORIGINAL_ID_TEST;
        } else if (apiUrl.equals("13579")) {
            HttpConstant.YWFHTTPURL = HttpConstant.YWFHTTPURL_TEST;
            HttpConstant.FENGCAR = HttpConstant.FENGCAR_TEST;
            HttpConstant.BASE_LOG_URL = "http://172.18.30.195/index.html?action=";
            HttpConstant.BASE_FENG_AD = HttpConstant.BASE_FENG_AD_TEST;
            HttpConstant.BASE_WX_APP_ID = HttpConstant.BASE_WX_APP_ID_TEST;
            HttpConstant.BASE_WX_MIN_ORIGINAL_ID = HttpConstant.WX_MIN_ORIGINAL_ID_TEST;
        } else {
            HttpConstant.YWFHTTPURL = apiUrl;
            if (apiUrl.equals(HttpConstant.YWFHTTPURL_TEST) || apiUrl.equals(HttpConstant.YWFHTTPURL_EXPLOIT_TEST)) {
                HttpConstant.FENGCAR = HttpConstant.FENGCAR_TEST;
            } else {
                HttpConstant.FENGHTML5M = "http://m.laosiji.com/";
            }
            HttpConstant.BASE_LOG_URL = "http://logstat.laosiji.com/index.html?action=";
            HttpConstant.BASE_FENG_AD = HttpConstant.BASE_FENG_AD_ON_LINE;
            HttpConstant.BASE_WX_APP_ID = HttpConstant.BASE_WX_APP_ID_ONLINE;
            HttpConstant.BASE_WX_MIN_ORIGINAL_ID = HttpConstant.WX_MIN_ORIGINAL_ID_ONLINE;
        }
        HttpConstant.initConfig();
    }

    public void setApiUrl(String str) {
        if (TextUtils.isEmpty(str.trim())) {
            if (TextUtils.isEmpty(this.mApiDomainUrl)) {
                HttpConstant.YWFHTTPURL = "http://api.laosiji.com/";
            } else {
                HttpConstant.YWFHTTPURL = this.mApiDomainUrl;
            }
            HttpConstant.FENGHTML5M = "http://m.laosiji.com/";
            HttpConstant.BASE_LOG_URL = "http://logstat.laosiji.com/index.html?action=";
            HttpConstant.BASE_FENG_AD = HttpConstant.BASE_FENG_AD_ON_LINE;
            HttpConstant.BASE_WX_APP_ID = HttpConstant.BASE_WX_APP_ID_ONLINE;
            HttpConstant.BASE_WX_MIN_ORIGINAL_ID = HttpConstant.WX_MIN_ORIGINAL_ID_ONLINE;
        } else if (str.equals("258369")) {
            HttpConstant.YWFHTTPURL = HttpConstant.YWFHTTPURL_EXPLOIT_TEST;
            HttpConstant.FENGCAR = HttpConstant.FENGCAR_TEST;
            HttpConstant.BASE_LOG_URL = "http://172.18.30.195/index.html?action=";
            HttpConstant.BASE_FENG_AD = HttpConstant.BASE_FENG_AD_TEST;
            HttpConstant.BASE_WX_APP_ID = HttpConstant.BASE_WX_APP_ID_TEST;
            HttpConstant.BASE_WX_MIN_ORIGINAL_ID = HttpConstant.WX_MIN_ORIGINAL_ID_TEST;
        } else if (str.equals("13579")) {
            HttpConstant.YWFHTTPURL = HttpConstant.YWFHTTPURL_TEST;
            HttpConstant.FENGCAR = HttpConstant.FENGCAR_TEST;
            HttpConstant.BASE_LOG_URL = "http://172.18.30.195/index.html?action=";
            HttpConstant.BASE_FENG_AD = HttpConstant.BASE_FENG_AD_TEST;
            HttpConstant.BASE_WX_APP_ID = HttpConstant.BASE_WX_APP_ID_TEST;
            HttpConstant.BASE_WX_MIN_ORIGINAL_ID = HttpConstant.WX_MIN_ORIGINAL_ID_TEST;
        } else {
            if (str.lastIndexOf("/") != str.length() - 1) {
                str = str + "/";
            }
            HttpConstant.YWFHTTPURL = str;
            if (str.equals(HttpConstant.YWFHTTPURL_TEST) || str.equals(HttpConstant.YWFHTTPURL_EXPLOIT_TEST)) {
                HttpConstant.FENGCAR = HttpConstant.FENGCAR_TEST;
            } else {
                HttpConstant.FENGHTML5M = "http://m.laosiji.com/";
            }
            HttpConstant.BASE_LOG_URL = "http://logstat.laosiji.com/index.html?action=";
            HttpConstant.BASE_FENG_AD = HttpConstant.BASE_FENG_AD_ON_LINE;
            HttpConstant.BASE_WX_APP_ID = HttpConstant.BASE_WX_APP_ID_ONLINE;
            HttpConstant.BASE_WX_MIN_ORIGINAL_ID = HttpConstant.WX_MIN_ORIGINAL_ID_ONLINE;
        }
        HttpConstant.initConfig();
        SharedUtil.putString(this, "api_key", str);
    }

    private void initUMengPush() {
        this.mPushAgent = PushAgent.getInstance(this);
        this.mPushAgent.setDisplayNotificationNumber(10);
        this.mPushAgent.setNotificationPlaySound(1);
        this.mPushAgent.setMessageHandler(new UmengMessageHandler() {
            public Notification getNotification(Context context, UMessage msg) {
                return super.getNotification(context, msg);
            }
        });
        this.mPushAgent.setNotificationClickHandler(new UmengNotificationClickHandler() {
            public void launchApp(Context context, UMessage uMessage) {
                if (uMessage.extra == null || !uMessage.extra.containsKey("url")) {
                    FengApplication.this.handlePushUrl("", context);
                    return;
                }
                FengApplication.this.handlePushUrl((String) uMessage.extra.get("url"), context);
            }

            public void openUrl(Context context, UMessage uMessage) {
                FengApplication.this.handlePushUrl(uMessage.url.trim(), context);
            }
        });
        this.mPushAgent.setNotificaitonOnForeground(true);
        this.mPushAgent.register(new IUmengRegisterCallback() {
            public void onSuccess(String deviceToken) {
            }

            public void onFailure(String s, String s1) {
            }
        });
        HuaWeiRegister.register(this);
        MiPushRegistar.register(this, "2882303761517556908", "5741755689908");
    }

    private void handlePushUrl(String url, Context context) {
        Intent intent = new Intent(context, TransparentActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("umeng_push", 1);
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    public synchronized List<AdvertInfo> getAdList() {
        return this.mAdList;
    }

    public synchronized void setAdList(List<AdvertInfo> list) {
        this.mAdList.clear();
        if (list != null && list.size() > 0) {
            this.mAdList.addAll(list);
        }
    }

    public synchronized SparkDB getSparkDB() {
        if (this.mSparkDB == null) {
            this.mSparkDB = new SparkDB(this);
        }
        return this.mSparkDB;
    }

    public synchronized AudioStateEvent getAudioState() {
        return this.mAudioState;
    }

    public synchronized boolean getSeverceState() {
        boolean z = true;
        synchronized (this) {
            if (!this.mSeverceStateMap.containsKey("ServiceFix")) {
                checkServerState();
            } else if (!this.mSeverceStateMap.get("ServiceFix").toString().equals("0")) {
                z = false;
            }
        }
        return z;
    }

    public synchronized String getTestSwitch() {
        String obj;
        if (this.mSeverceStateMap.containsKey("EnterpriseUrl")) {
            obj = this.mSeverceStateMap.get("EnterpriseUrl").toString();
        } else {
            obj = "";
        }
        return obj;
    }

    public synchronized boolean getAudioShareState() {
        boolean z = true;
        synchronized (this) {
            if (!this.mSeverceStateMap.containsKey("AudioShareHide")) {
                checkServerState();
            } else if (!this.mSeverceStateMap.get("AudioShareHide").toString().equals("0")) {
                z = false;
            }
        }
        return z;
    }

    public synchronized Map<Integer, Boolean> getFollowState() {
        return this.mFollowState;
    }

    public synchronized void addFollowState(int uId, boolean isFollow) {
        if (!this.mFollowState.containsKey(Integer.valueOf(uId))) {
            this.mFollowState.put(Integer.valueOf(uId), Boolean.valueOf(isFollow));
        } else if (((Boolean) this.mFollowState.get(Integer.valueOf(uId))).booleanValue() != isFollow) {
            this.mFollowState.remove(Integer.valueOf(uId));
        }
    }

    public synchronized String getNewUserActivity() {
        String str;
        if (this.mSeverceStateMap.containsKey("wxAction")) {
            str = this.mSeverceStateMap.get("wxAction").toString().trim();
            if (TextUtils.isEmpty(str)) {
                str = "0";
            }
        } else {
            checkServerState();
            str = "";
        }
        return str;
    }

    public synchronized String getSearchKeyAd() {
        String str;
        if (this.mSeverceStateMap.containsKey("searchKeyAd")) {
            str = this.mSeverceStateMap.get("searchKeyAd").toString().trim();
            if (TextUtils.isEmpty(str)) {
                str = "0";
            }
        } else {
            checkServerState();
            str = "";
        }
        return str;
    }

    public synchronized boolean getWeiXinSkipPhoneBind() {
        boolean z = false;
        synchronized (this) {
            if (this.mSeverceStateMap.containsKey("SkipPhoneBind")) {
                String str = this.mSeverceStateMap.get("SkipPhoneBind").toString().trim();
                if (!TextUtils.isEmpty(str)) {
                    String[] strSplit = str.split(",");
                    if (strSplit.length == 3 && strSplit[0].equals("1")) {
                        z = true;
                    }
                }
            } else {
                checkServerState();
            }
        }
        return z;
    }

    public synchronized boolean getWeiBoSkipPhoneBind() {
        boolean z = false;
        synchronized (this) {
            if (this.mSeverceStateMap.containsKey("SkipPhoneBind")) {
                String str = this.mSeverceStateMap.get("SkipPhoneBind").toString().trim();
                if (!TextUtils.isEmpty(str)) {
                    String[] strSplit = str.split(",");
                    if (strSplit.length == 3 && strSplit[1].equals("1")) {
                        z = true;
                    }
                }
            } else {
                checkServerState();
            }
        }
        return z;
    }

    public synchronized boolean getQQSkipPhoneBind() {
        boolean z = false;
        synchronized (this) {
            if (this.mSeverceStateMap.containsKey("SkipPhoneBind")) {
                String str = this.mSeverceStateMap.get("SkipPhoneBind").toString().trim();
                if (!TextUtils.isEmpty(str)) {
                    String[] strSplit = str.split(",");
                    if (strSplit.length == 3 && strSplit[2].equals("1")) {
                        z = true;
                    }
                }
            } else {
                checkServerState();
            }
        }
        return z;
    }

    public synchronized UserInfo getUserInfo() {
        if (this.mUserInfo == null) {
            this.mUserInfo = FengUtil.getUserInfo(this);
        }
        return this.mUserInfo;
    }

    public synchronized void clearUserInfo() {
        this.mUserInfo = null;
        clearUserInfo(this);
    }

    private void clearUserInfo(Context context) {
        SharedUtil.putInt(context, "ID_KEY", 0);
        SharedUtil.putString(context, "TOKEN_KEY", "");
        SharedUtil.putString(context, "MOBLIE_KEY", "");
        SharedUtil.putString(context, "USERNAME_KEY", "");
        SharedUtil.putInt(context, "SEX_KEY", 1);
        SharedUtil.putString(context, "IMAGE_KEY_URL", "");
        SharedUtil.putInt(context, "IMAGE_KEY_WIDTH", 0);
        SharedUtil.putInt(context, "IMAGE_KEY_HEIGHT", 0);
        SharedUtil.putInt(context, "IMAGE_KEY_MIMETYPE", 1);
        SharedUtil.putInt(context, "follow_key_count", 0);
        SharedUtil.putInt(context, "is_complete_info", -1);
    }

    public void saveUserInfo() {
        FengUtil.saveLoginUserInfo(this, this.mUserInfo);
    }

    public boolean isLoginUser() {
        return getUserInfo() != null;
    }

    public boolean isAllowFollowShow() {
        if (this.mIsFollowShow || !isLoginUser() || getUserInfo().followcount.get() > 0) {
            return false;
        }
        return true;
    }

    public void setAlreadyFollowShow(boolean isFollowShow) {
        this.mIsFollowShow = isFollowShow;
    }

    public boolean isNeedLoginInterface(String strInterface) {
        if (!StringUtil.isEmpty(strInterface)) {
            boolean z = true;
            switch (strInterface.hashCode()) {
                case -2084650699:
                    if (strInterface.equals("sns/iseditorordel/")) {
                        z = true;
                        break;
                    }
                    break;
                case -1874501745:
                    if (strInterface.equals("thread/delete/comment/")) {
                        z = false;
                        break;
                    }
                    break;
                case -725706228:
                    if (strInterface.equals("car/inputautoremind/")) {
                        z = true;
                        break;
                    }
                    break;
                case 71781865:
                    if (strInterface.equals("user/blackuser/")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                case true:
                case true:
                case true:
                    return true;
            }
        }
        return false;
    }

    public void cancelRequest(String strInterface) {
        Dispatcher dispatcher = OkHttpUtils.getInstance().getOkHttpClient().dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (strInterface.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call2 : dispatcher.runningCalls()) {
            if (strInterface.equals(call2.request().tag())) {
                call2.cancel();
            }
        }
    }

    public void httpRequest(String strInterface, Map<String, Object> map, OkHttpResponseCallback okHttpResponseCallback) {
        String expendedJson;
        if (!isLoginUser() && isNeedLoginInterface(strInterface)) {
            if (map.containsKey("local_audio_flag")) {
                map.remove("local_audio_flag");
            } else if (ActivityManager.getInstance().getCurrentActivity() != null) {
                ActivityManager.getInstance().getCurrentActivity().startActivity(new Intent(ActivityManager.getInstance().getCurrentActivity(), LoginActivity.class));
                return;
            }
        }
        final long stateTime = System.currentTimeMillis();
        if (strInterface.equals("advert/adserver/")) {
            if (map.containsKey("pageid")) {
                expendedJson = JsonUtil.toJson(map);
            } else {
                expendedJson = "";
            }
            map.remove("pageid");
            map.remove("datatype");
            map.remove("pagecode");
        } else if (isWriteQequest(strInterface)) {
            expendedJson = "";
        } else {
            expendedJson = JsonUtil.toJson(map);
        }
        String strJson = JsonUtil.parameterToJson(map, strInterface, this.mPushAgent != null ? this.mPushAgent.getRegistrationId() : "");
        String strUrl = HttpConstant.YWFHTTPURL + strInterface + 4 + "/" + getVersion(strInterface) + "/";
        StringBuffer buffer2 = new StringBuffer();
        buffer2.append(strUrl).append("?apptoken=").append("457AFE91B86EF9E35B19EFC204279D89").append("&parameter=").append(strJson);
        if (okHttpResponseCallback == null) {
            LogGatherReadUtil.getInstance().addNetElapsedTime(String.valueOf(System.currentTimeMillis() - stateTime), strUrl, "-10000", "请求是okHttpResponseCallback为null", expendedJson);
            upLoadLog(true, strInterface + "   请求是okHttpResponseCallback为null");
        } else if (WifiUtil.isNetworkAvailable(StubApp.getOrigApplicationContext(getApplicationContext()))) {
            okHttpResponseCallback.onStart();
            try {
                final OkHttpResponseCallback okHttpResponseCallback2 = okHttpResponseCallback;
                final String str = strUrl;
                final String str2 = expendedJson;
                OkHttpUtils.post().tag(strInterface).url(LogGatherReadUtil.getInstance().apiRequest(strUrl, expendedJson)).addHeader("User-Agent", OkHttpUtils.userAgent).addParams("parameter", strJson).addParams("chk", Md5Utils.md5(buffer2.toString().trim())).build().execute(StubApp.getOrigApplicationContext(getApplicationContext()), new StringCallback() {
                    public void onAfter() {
                        super.onAfter();
                        okHttpResponseCallback2.onFinish();
                    }

                    public void onError(Call call, Exception e) {
                        e.printStackTrace();
                        FengApplication.this.upLoadLog(true, e.getMessage());
                        okHttpResponseCallback2.onFailure(-1, "", null);
                        LogGatherReadUtil.getInstance().addNetElapsedTime(String.valueOf(System.currentTimeMillis() - stateTime), str, "-10000", e.getMessage(), str2);
                    }

                    public void onResponse(String response) {
                        try {
                            String strResult = FengUtil.checkResponse(response);
                            if (strResult != null) {
                                okHttpResponseCallback2.onSuccess(200, strResult);
                                LogGatherReadUtil.getInstance().addNetElapsedTime((System.currentTimeMillis() - stateTime) + "", str, new JSONObject(strResult).getInt("code") + "", "", str2);
                                return;
                            }
                            okHttpResponseCallback2.onFailure(-1, "", null);
                            ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast((int) R.string.net_abnormal);
                            FengApplication.this.upLoadLog(true, str + "   解析异常 = " + strResult);
                            LogGatherReadUtil.getInstance().addNetElapsedTime(String.valueOf(System.currentTimeMillis() - stateTime), str, "-10000", "解析异常=" + strResult, str2);
                        } catch (Exception e) {
                            okHttpResponseCallback2.onFailure(-1, "", null);
                            FengApplication.getInstance().upLoadLog(true, str + "   解析异常 = " + e.getMessage());
                            LogGatherReadUtil.getInstance().addNetElapsedTime(String.valueOf(System.currentTimeMillis() - stateTime), str, "-10000", "解析异常=" + e.getMessage(), str2);
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                okHttpResponseCallback.onFailure(-1, "", null);
                if (e.getMessage().indexOf("unexpected url:") <= 0) {
                    SharedUtil.putString(this, "api_key", "");
                }
                getInstance().upLoadLog(true, strUrl + "   解析异常 = " + e.getMessage());
                LogGatherReadUtil.getInstance().addNetElapsedTime(String.valueOf(System.currentTimeMillis() - stateTime), strUrl, "-10000", "解析异常=" + e.getMessage(), expendedJson);
            }
        } else {
            okHttpResponseCallback.onNetworkError();
        }
    }

    public void httpGetUpdateVersion(String url, final OkHttpResponseCallback okHttpResponseCallback) {
        okHttpResponseCallback.onStart();
        OkHttpUtils.get().url(url).addHeader("User-Agent", OkHttpUtils.userAgent).build().execute(StubApp.getOrigApplicationContext(getApplicationContext()), new StringCallback() {
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            public void onAfter() {
                super.onAfter();
                okHttpResponseCallback.onFinish();
            }

            public void onError(Call call, Exception e) {
                okHttpResponseCallback.onFailure(-1, "", e);
            }

            public void onResponse(String response) {
                try {
                    okHttpResponseCallback.onSuccess(200, response);
                } catch (Exception e) {
                    okHttpResponseCallback.onFailure(-1, "", e);
                }
            }
        });
    }

    public void checkServerState(final SuccessFailCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put("clientid", String.valueOf(4));
        map.put("version", "1.2");
        getInstance().httpRequest("home/ywf/getfox/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (callback != null) {
                    callback.onFail();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (callback != null) {
                    callback.onFail();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    if (resultJson.getInt("code") == 1) {
                        List<Map<String, Object>> list = JsonUtil.getListForJson(new String(Base64.decode(resultJson.getJSONObject("body").getString("fox"), 0)));
                        FengApplication.this.mSeverceStateMap.clear();
                        for (Map<String, Object> map2 : list) {
                            FengApplication.this.mSeverceStateMap.putAll(map2);
                        }
                        if (callback != null) {
                            callback.onSuccess();
                        }
                    } else if (callback != null) {
                        callback.onFail();
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFail();
                    }
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("sns/state/", content, e);
                }
            }
        });
    }

    private void checkServerState() {
        checkServerState(null);
    }

    public void upLoadTryCatchLog(String strInterface, String strJson, Exception e) {
        if (e != null) {
            upLoadLog(true, strInterface + "   解析异常 = " + e.getMessage());
        } else {
            upLoadLog(true, strInterface + "   解析异常 = \r\n详细数据" + strJson);
        }
    }

    public void upLoadLog(boolean isInclude, String msg) {
        String str = "";
        if (isInclude) {
            if (getUserInfo() != null) {
                str = "\r\n\r\n\r\nuserID:" + getUserInfo().id + "\r\nuserName:" + ((String) getUserInfo().name.get()) + "\r\nversion:" + FengUtil.getDeviceVersion(this) + "\r\ndevice:" + getDeviceModel();
            } else {
                str = "\r\n\r\n\r\nuserID:" + SharedUtil.getInt(this, "usertouristId", 0) + "\r\nuserName:\r\nversion:" + FengUtil.getDeviceVersion(this) + "\r\ndevice:" + getDeviceModel();
            }
        }
        if (msg != null && msg.length() > 0) {
            str = str + "\r\n" + DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss") + "---" + msg;
        }
        Map<String, String> params = new HashMap();
        if (getUserInfo() != null) {
            params.put("id", "lsj_android_" + getUserInfo().id);
        } else {
            int usertouristId = SharedUtil.getInt(this, "usertouristId", 0);
            params.put("id", usertouristId == 0 ? "lsj_android_88888888" : "lsj_android_" + usertouristId);
        }
        params.put("log", str);
        httpPostLog(params, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
            }
        });
    }

    public void httpPostLog(Map<String, String> map, final OkHttpResponseCallback okHttpResponseCallback) {
        okHttpResponseCallback.onStart();
        OkHttpUtils.post().url("http://php.laosiji.com/PublicRoadTest/log-receiver.php").addHeader("User-Agent", OkHttpUtils.userAgent).params(map).build().execute(StubApp.getOrigApplicationContext(getApplicationContext()), new StringCallback() {
            public void onAfter() {
                super.onAfter();
                okHttpResponseCallback.onFinish();
            }

            public void onError(Call call, Exception e) {
                okHttpResponseCallback.onFailure(-1, "", e);
            }

            public void onResponse(String response) {
                try {
                    okHttpResponseCallback.onSuccess(200, response);
                } catch (Exception e) {
                    okHttpResponseCallback.onFailure(-1, "", e);
                }
            }
        });
    }

    private boolean isWriteQequest(String url) {
        boolean z = true;
        switch (url.hashCode()) {
            case -2099397964:
                if (url.equals("thread/applyforrevision/")) {
                    z = true;
                    break;
                }
                break;
            case 548158221:
                if (url.equals("user/feed/")) {
                    z = false;
                    break;
                }
                break;
            case 1280606904:
                if (url.equals("sns/adddraft/")) {
                    z = true;
                    break;
                }
                break;
            case 1886131347:
                if (url.equals("thread/add/")) {
                    z = true;
                    break;
                }
                break;
            case 2114456997:
                if (url.equals("sns/addvv/")) {
                    z = true;
                    break;
                }
                break;
            case 2146335527:
                if (url.equals("shop/add/")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
            case true:
            case true:
            case true:
            case true:
                return true;
            default:
                return false;
        }
    }

    private int getVersion(String url) {
        int i = -1;
        switch (url.hashCode()) {
            case -2121847363:
                if (url.equals("search/ywf/suggestion/")) {
                    i = 12;
                    break;
                }
                break;
            case -2119914114:
                if (url.equals("user/ywf/listbyfollow/")) {
                    i = 19;
                    break;
                }
                break;
            case -2051715836:
                if (url.equals("search/ywf/content/")) {
                    i = 23;
                    break;
                }
                break;
            case -1850258700:
                if (url.equals("hq/show/quit/")) {
                    i = 10;
                    break;
                }
                break;
            case -1670624024:
                if (url.equals("recommend/listsnsinfobyid/")) {
                    i = 24;
                    break;
                }
                break;
            case -1635268344:
                if (url.equals("thread/threadinfofrom/")) {
                    i = 13;
                    break;
                }
                break;
            case -1481482308:
                if (url.equals("hq/applytransfer/")) {
                    i = 6;
                    break;
                }
                break;
            case -958716769:
                if (url.equals("community/getinfobyid/")) {
                    i = 0;
                    break;
                }
                break;
            case -800076687:
                if (url.equals("carsearch/getspec/")) {
                    i = 1;
                    break;
                }
                break;
            case -739630499:
                if (url.equals("hq/show/gameover/")) {
                    i = 8;
                    break;
                }
                break;
            case -652150162:
                if (url.equals("car/getallspec/")) {
                    i = 2;
                    break;
                }
                break;
            case -604590946:
                if (url.equals("hq/winnerrank/")) {
                    i = 4;
                    break;
                }
                break;
            case -551009905:
                if (url.equals("hq/show/qc/sig/")) {
                    i = 3;
                    break;
                }
                break;
            case -511410702:
                if (url.equals("user/ywf/myhistory/")) {
                    i = 20;
                    break;
                }
                break;
            case -482619734:
                if (url.equals("hq/home/")) {
                    i = 26;
                    break;
                }
                break;
            case -302437069:
                if (url.equals("home/ywf/tablist/")) {
                    i = 17;
                    break;
                }
                break;
            case -30595527:
                if (url.equals("thread/listbyfavorite/")) {
                    i = 16;
                    break;
                }
                break;
            case -6303081:
                if (url.equals("hq/resurrectcard/consume/")) {
                    i = 7;
                    break;
                }
                break;
            case 3867878:
                if (url.equals("snshotshow/getlistbyid/")) {
                    i = 21;
                    break;
                }
                break;
            case 85129320:
                if (url.equals("snshotshow/mylist/")) {
                    i = 11;
                    break;
                }
                break;
            case 98985396:
                if (url.equals("community/getsnslistbysort/")) {
                    i = 31;
                    break;
                }
                break;
            case 218365583:
                if (url.equals("sns/snsall/")) {
                    i = 15;
                    break;
                }
                break;
            case 421301433:
                if (url.equals("search/ywf/indexapi/")) {
                    i = 30;
                    break;
                }
                break;
            case 470831250:
                if (url.equals("recommend/getnew/")) {
                    i = 32;
                    break;
                }
                break;
            case 470867179:
                if (url.equals("recommend/getold/")) {
                    i = 25;
                    break;
                }
                break;
            case 795977349:
                if (url.equals("hq/resurrectcard/active/")) {
                    i = 5;
                    break;
                }
                break;
            case 1280606904:
                if (url.equals("sns/adddraft/")) {
                    i = 29;
                    break;
                }
                break;
            case 1461369322:
                if (url.equals("sns/info/")) {
                    i = 14;
                    break;
                }
                break;
            case 1507220743:
                if (url.equals("car/carsinfo/")) {
                    i = 27;
                    break;
                }
                break;
            case 1556750601:
                if (url.equals("community/getsnslist/")) {
                    i = 18;
                    break;
                }
                break;
            case 1786243403:
                if (url.equals("hq/show/submit/")) {
                    i = 9;
                    break;
                }
                break;
            case 1830957424:
                if (url.equals("sns/threadlist/")) {
                    i = 22;
                    break;
                }
                break;
            case 1886131347:
                if (url.equals("thread/add/")) {
                    i = 28;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                return 4;
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                return 5;
            case 32:
                return 8;
            default:
                return 3;
        }
    }

    public void checkCode(String strInterface, int code) {
        if (code != 1) {
            String strHiht = "";
            switch (code) {
                case -73:
                case -28:
                    ActivityManager.getInstance().getCurrentActivity().showFirstTypeToast("申请已提交");
                    return;
                case -62:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("该第三方账号已被其他人使用了");
                    return;
                case -61:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("该手机号已被其他老司机使用了");
                    return;
                case -59:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("绑定失败");
                    return;
                case -55:
                    ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast("该用户已在您的黑名单中，无法关注");
                    return;
                case -47:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("昵称为敏感词 请修改");
                    return;
                case -37:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("对方把您拉入黑名单中，操作被拒绝");
                    return;
                case -36:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("新旧手机号一样");
                    return;
                case -23:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("邮箱已存在");
                    return;
                case -22:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("邮箱不存在");
                    return;
                case -21:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("昵称已存在");
                    return;
                case -20:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("网络异常，操作失败");
                    return;
                case -19:
                case -18:
                case -16:
                case -15:
                case -14:
                case -1:
                case 0:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("网络异常，操作失败");
                    upLoadLog(true, strInterface + "-----错误码 = " + code);
                    return;
                case -17:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("帖子标题不能为空");
                    return;
                case -13:
                case -12:
                case LBSAuthManager.CODE_NETWORK_FAILED /*-11*/:
                    ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast("您的帖子被管理员推荐");
                    return;
                case LBSAuthManager.CODE_NETWORK_INVALID /*-10*/:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("已赞过！");
                    return;
                case -9:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("您已经顶过该评论了");
                    return;
                case -8:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("旧密码和新密码一样");
                    return;
                case -7:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("您输入旧密码错误");
                    return;
                case -6:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("该手机号码尚未注册");
                    return;
                case -5:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("您好，您的账号处于禁言状态，无法进行此项操作。");
                    return;
                case BaseSearchResult.STATUS_CODE_PERMISSION_UNFINISHED /*-4*/:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("您输入的用户名密码不正确");
                    return;
                case -3:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("该手机号已被其他老司机使用了");
                    return;
                case -2:
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("验证码错误！");
                    return;
                default:
                    upLoadLog(true, strInterface + "-----错误码 = " + code);
                    ActivityManager.getInstance().getCurrentActivity().showSecondTypeToast("网络异常，操作失败");
                    return;
            }
        }
    }

    private String getDeviceModel() {
        String model = Build.MODEL;
        String release = VERSION.RELEASE;
        String strWifi = "";
        if (WifiUtil.isMobile(this)) {
            strWifi = "4g";
        }
        if (WifiUtil.isWifiConnectivity(this)) {
            strWifi = "wifi";
        }
        return model + "[" + release + "][" + strWifi + "]";
    }

    public void setApkId(String id) {
        SharedUtil.putString(this, "apk_id", id);
    }

    public String getApkId() {
        String str = SharedUtil.getString(this, "apk_id");
        return (str == null || str.length() <= 0) ? "0" : str;
    }

    public synchronized boolean containsHd(String strUrl) {
        if (this.mHdImageUrl == null) {
            String str = SharedUtil.getString(this, "hd_big_image_url");
            this.mHdImageUrl = new LinkedList();
            if (str != null && str.length() > 0) {
                this.mHdImageUrl.addAll((LinkedList) JsonUtil.fromJson(str, new TypeToken<LinkedList<String>>() {
                }));
            }
        }
        return this.mHdImageUrl.contains(strUrl);
    }

    public synchronized void addHdImage(String strUrl) {
        if (!containsHd(strUrl)) {
            if (this.mHdImageUrl.size() >= 100) {
                this.mHdImageUrl.removeLast();
            }
            this.mHdImageUrl.addFirst(strUrl);
            SharedUtil.putString(this, "hd_big_image_url", JsonUtil.toJson(this.mHdImageUrl));
        }
    }

    private String formatPatternUrl(String url) {
        return url.replace("https:", "(http|https):");
    }

    public void handlerUrlSkip(Context context, String strUrl, String strTitle, boolean isAd, AdvertInfo advertInfo) {
        if (!TextUtils.isEmpty(strUrl)) {
            if (!handlerWeb302Skip(context, strUrl)) {
                if (isAd && advertInfo != null) {
                    strUrl = LogGatherReadUtil.getInstance().getAdFormatUrl(advertInfo.adid, advertInfo.seat, TextUtils.isEmpty(advertInfo.backjson) ? JsonUtil.toJson(advertInfo) : advertInfo.backjson);
                }
                Intent webIntent = new Intent(context, WebActivity.class);
                webIntent.putExtra("url", strUrl.trim());
                webIntent.putExtra("title", strTitle);
                context.startActivity(webIntent);
            } else if (isAd && advertInfo != null) {
                LogGatherReadUtil.getInstance().addAdClickLog(advertInfo.adid, advertInfo.seat, TextUtils.isEmpty(advertInfo.backjson) ? JsonUtil.toJson(advertInfo) : advertInfo.backjson);
            }
        }
    }

    public boolean handlerWeb302Skip(Context context, String url) {
        Matcher matcher = this.mPatternThread.matcher(url);
        String str;
        int resourceId;
        Intent intent;
        if (matcher.find()) {
            str = matcher.group(2);
            if (TextUtils.isEmpty(str)) {
                str = matcher.group(4);
            }
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            resourceId = Integer.parseInt(str);
            intent = new Intent(context, ArticleDetailActivity.class);
            intent.putExtra("resourceid", resourceId);
            intent.putExtra("resourcetype", 0);
            context.startActivity(intent);
            return true;
        }
        matcher = this.mPattern5xThread.matcher(url);
        if (matcher.find()) {
            str = matcher.group(2);
            if (TextUtils.isEmpty(str)) {
                str = matcher.group(4);
            }
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            resourceId = Integer.parseInt(str);
            intent = new Intent(context, ArticleDetailActivity.class);
            intent.putExtra("resourceid", resourceId);
            intent.putExtra("resourcetype", 0);
            context.startActivity(intent);
            return true;
        }
        matcher = this.mPatternPDiscuss.matcher(url);
        int id;
        if (matcher.find()) {
            str = matcher.group(2);
            if (TextUtils.isEmpty(str)) {
                str = matcher.group(4);
            }
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            id = Integer.parseInt(str);
            intent = new Intent(context, ViewPointActivity.class);
            intent.putExtra("resourceid", id);
            intent.putExtra("resourcetype", 9);
            context.startActivity(intent);
            return true;
        }
        matcher = this.mPatternUDiscuss.matcher(url);
        if (matcher.find()) {
            str = matcher.group(2);
            if (TextUtils.isEmpty(str)) {
                str = matcher.group(4);
            }
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            id = Integer.parseInt(str);
            intent = new Intent(context, ViewPointActivity.class);
            intent.putExtra("resourceid", id);
            intent.putExtra("resourcetype", 10);
            context.startActivity(intent);
            return true;
        }
        matcher = this.mPatternHotPro.matcher(url);
        if (matcher.find()) {
            str = matcher.group(2);
            if (TextUtils.isEmpty(str)) {
                str = matcher.group(4);
            }
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            id = Integer.parseInt(str);
            intent = new Intent(context, PopularProgramListActivity.class);
            intent.putExtra("hotshowid", id);
            intent.putExtra("hot_show_name", "");
            context.startActivity(intent);
            return true;
        }
        matcher = this.mPatternCircle.matcher(url);
        if (!matcher.find()) {
            matcher = this.mPatternSeries.matcher(url);
            if (!matcher.find()) {
                return false;
            }
            str = matcher.group(2);
            if (TextUtils.isEmpty(str)) {
                str = matcher.group(4);
            }
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            id = Integer.parseInt(str);
            intent = new Intent(context, NewSubjectActivity.class);
            intent.putExtra("carsid", id);
            context.startActivity(intent);
            return true;
        } else if (matcher.groupCount() < 3) {
            return false;
        } else {
            str = matcher.group(3);
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            id = Integer.parseInt(str);
            intent = new Intent(context, NewSubjectActivity.class);
            intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, id);
            context.startActivity(intent);
            return true;
        }
    }

    public boolean isAppInstalled(String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        }
        return true;
    }

    public int getConnectState() {
        return this.mConnectState;
    }

    public void setConnectState(int connectState) {
        this.mConnectState = connectState;
    }

    public boolean isWifiConnect() {
        return this.mIsWifi;
    }

    public void setIsWifiConnect(boolean isWifiConnect) {
        this.mIsWifi = isWifiConnect;
    }

    public int getStatusBarHeight() {
        if (this.mStatusBarHeight != 0) {
            return this.mStatusBarHeight;
        }
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", DispatchConstants.ANDROID);
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        if (result == 0) {
            return getResources().getDimensionPixelSize(R.dimen.default_48PX);
        }
        return result;
    }

    public void handlerUmengPush(String url, Context context) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            Intent intent;
            try {
                int id;
                String name;
                int resourceId;
                int hotId;
                if (url.indexOf("http://m.laosiji.com/center/fins/") >= 0 || url.indexOf("https://m.laosiji.com/center/fins/") >= 0) {
                    if (getInstance().isLoginUser()) {
                        intent = new Intent(context, FansActivity.class);
                        intent.putExtra("userid", getInstance().getUserInfo().id);
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                        return;
                    }
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else if (url.indexOf("http://m.laosiji.com/center/message/") >= 0) {
                    if (getInstance().isLoginUser()) {
                        id = getUrlLastInt("http://m.laosiji.com/center/message/", url);
                        if (id <= 0) {
                            id = Integer.parseInt(uri.getLastPathSegment());
                        }
                        name = uri.getQueryParameter("name");
                        intent = new Intent(context, PrivateChatActivity.class);
                        intent.putExtra("userid", id);
                        intent.putExtra("name", name);
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                        return;
                    }
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else if (url.indexOf("https://m.laosiji.com/center/message/") >= 0) {
                    if (getInstance().isLoginUser()) {
                        id = getUrlLastInt("https://m.laosiji.com/center/message/", url);
                        if (id <= 0) {
                            id = Integer.parseInt(uri.getLastPathSegment());
                        }
                        name = uri.getQueryParameter("name");
                        intent = new Intent(context, PrivateChatActivity.class);
                        intent.putExtra("userid", id);
                        intent.putExtra("name", name);
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                        return;
                    }
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else if (url.indexOf("http://m.laosiji.com/center/commend/") >= 0 || url.indexOf("https://m.laosiji.com/center/commend/") >= 0) {
                    if (getInstance().isLoginUser()) {
                        intent = new Intent(context, CommentActivity.class);
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                        return;
                    }
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else if (url.indexOf("http://m.laosiji.com/center/at/") >= 0 || url.indexOf("https://m.laosiji.com/center/at/") >= 0) {
                    if (getInstance().isLoginUser()) {
                        intent = new Intent(context, AtMeActivity.class);
                        intent.putExtra("feng_type", 0);
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                        return;
                    }
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else if (url.indexOf("http://m.laosiji.com/center/atcomment/") >= 0 || url.indexOf("https://m.laosiji.com/center/atcomment/") >= 0) {
                    if (getInstance().isLoginUser()) {
                        intent = new Intent(context, AtMeActivity.class);
                        intent.putExtra("feng_type", 1);
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                        return;
                    }
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else if (url.indexOf("http://m.laosiji.com/center/system/") >= 0 || url.indexOf("https://m.laosiji.com/center/system/") >= 0) {
                    if (getInstance().isLoginUser()) {
                        intent = new Intent(context, SystemNoticeActivity.class);
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                        return;
                    }
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else if (url.indexOf("http://m.laosiji.com/thread/") >= 0) {
                    resourceId = getUrlLastInt("http://m.laosiji.com/thread/", url);
                    if (resourceId <= 0) {
                        resourceId = Integer.parseInt(uri.getLastPathSegment());
                    }
                    intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra("resourceid", resourceId);
                    intent.putExtra("resourcetype", 0);
                    intent.addFlags(268435456);
                    context.startActivity(intent);
                } else if (url.indexOf("https://m.laosiji.com/thread/") >= 0) {
                    resourceId = getUrlLastInt("https://m.laosiji.com/thread/", url);
                    if (resourceId <= 0) {
                        resourceId = Integer.parseInt(uri.getLastPathSegment());
                    }
                    intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra("resourceid", resourceId);
                    intent.putExtra("resourcetype", 0);
                    intent.addFlags(268435456);
                    context.startActivity(intent);
                } else if (url.indexOf("http://m.laosiji.com/autodiscuss/p/") >= 0) {
                    resourceId = getUrlLastInt("http://m.laosiji.com/autodiscuss/p/", url);
                    if (resourceId <= 0) {
                        resourceId = Integer.parseInt(uri.getLastPathSegment());
                    }
                    intent = new Intent(context, ViewPointActivity.class);
                    intent.putExtra("resourceid", resourceId);
                    intent.putExtra("resourcetype", 9);
                    intent.addFlags(268435456);
                    context.startActivity(intent);
                } else if (url.indexOf("https://m.laosiji.com/autodiscuss/p/") >= 0) {
                    resourceId = getUrlLastInt("https://m.laosiji.com/autodiscuss/p/", url);
                    if (resourceId <= 0) {
                        resourceId = Integer.parseInt(uri.getLastPathSegment());
                    }
                    intent = new Intent(context, ViewPointActivity.class);
                    intent.putExtra("resourceid", resourceId);
                    intent.putExtra("resourcetype", 9);
                    intent.addFlags(268435456);
                    context.startActivity(intent);
                } else if (url.indexOf("http://m.laosiji.com/autodiscuss/u/") >= 0) {
                    resourceId = getUrlLastInt("http://m.laosiji.com/autodiscuss/u/", url);
                    if (resourceId <= 0) {
                        resourceId = Integer.parseInt(uri.getLastPathSegment());
                    }
                    intent = new Intent(context, ViewPointActivity.class);
                    intent.putExtra("resourceid", resourceId);
                    intent.putExtra("resourcetype", 10);
                    intent.addFlags(268435456);
                    context.startActivity(intent);
                } else if (url.indexOf("https://m.laosiji.com/autodiscuss/u/") >= 0) {
                    resourceId = getUrlLastInt("https://m.laosiji.com/autodiscuss/u/", url);
                    if (resourceId <= 0) {
                        resourceId = Integer.parseInt(uri.getLastPathSegment());
                    }
                    intent = new Intent(context, ViewPointActivity.class);
                    intent.putExtra("resourceid", resourceId);
                    intent.putExtra("resourcetype", 10);
                    intent.addFlags(268435456);
                    context.startActivity(intent);
                } else if (url.indexOf("http://m.laosiji.com/hotshow/") >= 0) {
                    hotId = getUrlLastInt("http://m.laosiji.com/hotshow/", url);
                    if (hotId <= 0) {
                        hotId = Integer.parseInt(uri.getLastPathSegment());
                    }
                    intent = new Intent(context, PopularProgramListActivity.class);
                    intent.putExtra("hotshowid", hotId);
                    context.startActivity(intent);
                } else if (url.indexOf("https://m.laosiji.com/hotshow/") >= 0) {
                    hotId = getUrlLastInt("https://m.laosiji.com/hotshow/", url);
                    if (hotId <= 0) {
                        hotId = Integer.parseInt(uri.getLastPathSegment());
                    }
                    intent = new Intent(context, PopularProgramListActivity.class);
                    intent.putExtra("hotshowid", hotId);
                    context.startActivity(intent);
                } else if (url.indexOf("http://m.laosiji.com/community/") >= 0 || url.indexOf("https://m.laosiji.com/community/") >= 0) {
                    handlerWeb302Skip(context, url);
                } else {
                    intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", url);
                    intent.addFlags(268435456);
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
                intent = new Intent(context, HomeActivity.class);
                intent.addFlags(268435456);
                context.startActivity(intent);
            }
        }
    }

    private int getUrlLastInt(String strRegex, String url) {
        Matcher matcher = Pattern.compile(strRegex + "(\\d+)").matcher(url);
        if (!matcher.find()) {
            return 0;
        }
        String str = matcher.group(1);
        if (TextUtils.isEmpty(str)) {
            str = matcher.group(2);
        }
        return Integer.parseInt(str);
    }
}
