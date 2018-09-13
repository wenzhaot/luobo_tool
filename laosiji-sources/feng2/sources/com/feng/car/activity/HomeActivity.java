package com.feng.car.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.request.ImageRequest;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.HomeMainBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.home.NewActivityInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.HomeRefreshEvent;
import com.feng.car.event.HomeSwitchEvent;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.fragment.BaseFragment;
import com.feng.car.fragment.FindPageFragment;
import com.feng.car.fragment.FollowFragment;
import com.feng.car.fragment.HomePageNewFragment;
import com.feng.car.fragment.MineFragment;
import com.feng.car.observer.ScreenshotObserver;
import com.feng.car.receiver.ConnectionChangeReceiver;
import com.feng.car.service.AudioPlayService;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.BlackUtil;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.utils.MessageCountManager;
import com.feng.car.utils.RecommendRecordUtil;
import com.feng.car.video.download.VideoDownloadManager;
import com.feng.car.video.player.JCVideoPlayer;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.okhttp.callback.StringCallback;
import com.feng.library.okhttp.cookie.store.PersistentCookieStore;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.WifiUtil;
import com.stub.StubApp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import okhttp3.Call;
import okhttp3.Request;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

public class HomeActivity extends VideoBaseActivity<HomeMainBinding> {
    private ConnectionChangeReceiver mConnectionChangeReceiver;
    private int mCurrentTabIndex = 0;
    private Fragment[] mFragments;
    private boolean mIsFirst = true;
    private NewActivityInfo mNewActivityInfo;
    private ScreenBroadcastReceiver mScreenBroadcastReceiver;
    private ScreenshotObserver mScreenshotObserver;
    private int mSelectIndex;

    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action;

        private ScreenBroadcastReceiver() {
            this.action = null;
        }

        /* synthetic */ ScreenBroadcastReceiver(HomeActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void onReceive(Context context, Intent intent) {
            this.action = intent.getAction();
            if ("android.intent.action.SCREEN_OFF".equals(this.action)) {
                BaseActivity activity = ActivityManager.getInstance().getCurrentActivity();
                if (activity != null) {
                    activity.scrollWhenScreenOff();
                }
            }
        }
    }

    static {
        StubApp.interface11(2462);
    }

    protected native void onCreate(Bundle bundle);

    private void checkXposed() {
        try {
            for (PackageInfo info : getPackageManager().getInstalledPackages(0)) {
                if ("de.robv.android.xposed.installer".equals(info.applicationInfo.packageName)) {
                    FengApplication.getInstance().upLoadLog(true, "用户手机安装xposed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int setBaseContentView() {
        return R.layout.home_main;
    }

    public void initView() {
        SharedUtil.putString(this, "login_third_info", "");
        String strUserToken = SharedUtil.getString(this, "usertouristtoken");
        if (!TextUtils.isEmpty(strUserToken)) {
            FengConstant.USERTOURISTTOKEN = strUserToken;
        }
        VideoDownloadManager.newInstance().initFileDownloader();
        initFragments();
        checkUser();
        MessageCountManager.getInstance().startRequestMessageCount();
        getNextSplashData();
        httpGetDoMainRed();
        String strFlag = SharedUtil.getString(this, "new_user_flag");
        if (!TextUtils.isEmpty(strFlag)) {
            try {
                if (strFlag.indexOf("1$" + new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance(TimeZone.getTimeZone("GMT+8")).getTime())) >= 0) {
                    getActivityInfo();
                } else {
                    SharedUtil.putString(this, "new_user_flag", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkUser() {
        if (WifiUtil.isNetworkAvailable(StubApp.getOrigApplicationContext(getApplicationContext()))) {
            autoLogin();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
    }

    private void initFragments() {
        Fragment firstFragment = new HomePageNewFragment();
        FindPageFragment secondFragment = new FindPageFragment();
        FollowFragment thirdFragment = new FollowFragment();
        MineFragment fourFragment = new MineFragment();
        this.mFragments = new Fragment[]{firstFragment, secondFragment, new Fragment(), thirdFragment, fourFragment};
        getSupportFragmentManager().beginTransaction().add((int) R.id.fragment_container, firstFragment).show(firstFragment).commit();
        changeTab();
        ((HomeMainBinding) this.mBaseBinding).rlFirstTab.setOnClickListener(this);
        ((HomeMainBinding) this.mBaseBinding).tvSecondTab.setOnClickListener(this);
        ((HomeMainBinding) this.mBaseBinding).ivThirdTab.setOnClickListener(this);
        ((HomeMainBinding) this.mBaseBinding).rlFourTab.setOnClickListener(this);
        ((HomeMainBinding) this.mBaseBinding).rlFiveTab.setOnClickListener(this);
    }

    public void onSingleClick(View view) {
        switch (view.getId()) {
            case R.id.tv_second_tab /*2131624670*/:
                this.mSelectIndex = 1;
                break;
            case R.id.rl_first_tab /*2131625090*/:
                this.mSelectIndex = 0;
                break;
            case R.id.rl_four_tab /*2131625093*/:
                this.mSelectIndex = 3;
                break;
            case R.id.rl_five_tab /*2131625095*/:
                this.mSelectIndex = 4;
                break;
            case R.id.iv_third_tab /*2131625098*/:
                this.mSelectIndex = 2;
                break;
        }
        if (!(this.mSelectIndex == 0 && this.mCurrentTabIndex == 0)) {
            JCVideoPlayer.releaseAllVideos();
        }
        if (this.mSelectIndex == 2) {
            if (!FengApplication.getInstance().isLoginUser()) {
                startActivity(new Intent(this, LoginActivity.class));
            } else if (((HomeMainBinding) this.mBaseBinding).activityBtn.isShown()) {
                startToWebRule();
            } else {
                FengApplication.getInstance().getUserInfo().intentToSendPost(this);
            }
            this.mSelectIndex = this.mCurrentTabIndex;
        } else if (this.mCurrentTabIndex != this.mSelectIndex) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(this.mFragments[this.mCurrentTabIndex]);
            if (!this.mFragments[this.mSelectIndex].isAdded()) {
                trx.add((int) R.id.fragment_container, this.mFragments[this.mSelectIndex]);
            } else if (this.mSelectIndex == 4) {
                EventBus.getDefault().post(new HomeRefreshEvent(this.mSelectIndex));
            }
            trx.show(this.mFragments[this.mSelectIndex]).commitAllowingStateLoss();
            changeTab();
            logPageOut();
            this.mCurrentTabIndex = this.mSelectIndex;
            umengLoadPv();
        } else if (FengApplication.getInstance().isLoginUser()) {
            EventBus.getDefault().post(new HomeRefreshEvent(this.mSelectIndex));
        } else if (this.mSelectIndex != 2) {
            EventBus.getDefault().post(new HomeRefreshEvent(this.mSelectIndex));
        }
    }

    private void changeTab() {
        boolean z;
        boolean z2 = true;
        this.mRootBinding.audioSuspensionView.onResume();
        ((HomeMainBinding) this.mBaseBinding).tvFirstTab.setSelected(this.mSelectIndex == 0);
        TextView textView = ((HomeMainBinding) this.mBaseBinding).tvSecondTab;
        if (this.mSelectIndex == 1) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        textView = ((HomeMainBinding) this.mBaseBinding).tvFourTab;
        if (this.mSelectIndex == 3) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        TextView textView2 = ((HomeMainBinding) this.mBaseBinding).tvFiveTab;
        if (this.mSelectIndex != 4) {
            z2 = false;
        }
        textView2.setSelected(z2);
        if (this.mSelectIndex == 4) {
            ((HomeMainBinding) this.mBaseBinding).ivFiveDot.setVisibility(8);
            VideoDownloadManager.newInstance().hideMineFragmentRedPoint();
        }
    }

    private void umengLoadPv() {
        switch (this.mSelectIndex) {
            case 0:
                if (this.mFragments != null && this.mFragments.length > 0) {
                    ((HomePageNewFragment) this.mFragments[0]).handlerResume();
                }
                MessageCountManager.getInstance().requestMessageCount();
                return;
            case 1:
                if (this.mFragments != null && this.mFragments.length > 0) {
                    ((BaseFragment) this.mFragments[this.mSelectIndex]).getLogGatherInfo().setBasePage("app_search_car");
                    ((BaseFragment) this.mFragments[this.mSelectIndex]).getLogGatherInfo().addPageInTime();
                }
                MessageCountManager.getInstance().requestMessageCount();
                return;
            case 3:
                MessageCountManager.getInstance().clearFollowPageMessageCount();
                ((HomeMainBinding) this.mBaseBinding).ivFourDot.setVisibility(8);
                if (this.mFragments != null && this.mFragments.length > 0 && this.mFragments[3].isAdded()) {
                    ((FollowFragment) this.mFragments[3]).uploadSlidePv();
                    return;
                }
                return;
            case 4:
                if (this.mFragments != null && this.mFragments.length > 0) {
                    ((BaseFragment) this.mFragments[this.mSelectIndex]).getLogGatherInfo().setBasePage("app_mine");
                    ((BaseFragment) this.mFragments[this.mSelectIndex]).getLogGatherInfo().addPageInTime();
                }
                MessageCountManager.getInstance().requestMessageCount();
                return;
            default:
                return;
        }
    }

    private void logPageOut() {
        switch (this.mCurrentTabIndex) {
            case 0:
                if (this.mFragments != null && this.mFragments.length > 0 && this.mFragments[0].isAdded()) {
                    ((HomePageNewFragment) this.mFragments[0]).pageOut();
                    return;
                }
                return;
            case 1:
            case 4:
                if (this.mFragments != null && this.mFragments.length > 0 && this.mFragments[this.mCurrentTabIndex].isAdded()) {
                    ((BaseFragment) this.mFragments[this.mCurrentTabIndex]).getLogGatherInfo().addPageOutTime();
                    return;
                }
                return;
            case 3:
                if (this.mFragments != null && this.mFragments.length > this.mCurrentTabIndex && this.mFragments[this.mCurrentTabIndex].isAdded()) {
                    ((FollowFragment) this.mFragments[this.mCurrentTabIndex]).pageOut();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        try {
            moveTaskToBack(true);
            PersistentCookieStore.getInstance(this).removeAll();
            SharedUtil.putString(this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY, "");
            FengApplication.getInstance().setAlreadyFollowShow(false);
            if (!Build.MANUFACTURER.equals("HUAWEI")) {
                this.mScreenshotObserver.stopObserve();
            }
            RecommendRecordUtil.getInstance().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void autoLogin() {
        FengApplication.getInstance().httpRequest("user/ywf/login/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code != 1) {
                        FengApplication.getInstance().upLoadLog(true, "user/ywf/login/    " + code);
                    } else if (FengApplication.getInstance().isLoginUser()) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        JSONObject jsonUser = jsonBody.getJSONObject("user");
                        UserInfo userInfo = new UserInfo();
                        userInfo.parser(jsonUser);
                        FengApplication.getInstance().getUserInfo().token = userInfo.token;
                        FengApplication.getInstance().getUserInfo().id = userInfo.id;
                        FengApplication.getInstance().getUserInfo().name.set(userInfo.name.get());
                        FengApplication.getInstance().getUserInfo().sex.set(userInfo.sex.get());
                        FengApplication.getInstance().getUserInfo().followcount.set(userInfo.followcount.get());
                        FengApplication.getInstance().getUserInfo().setHeadImageInfo(userInfo.getHeadImageInfo());
                        FengApplication.getInstance().getUserInfo().phonenumber = userInfo.phonenumber;
                        FengApplication.getInstance().getUserInfo().isadministrator = userInfo.isadministrator;
                        if (FengApplication.getInstance().getUserInfo().connect != null) {
                            FengApplication.getInstance().getUserInfo().connect.qq = userInfo.connect.qq;
                            FengApplication.getInstance().getUserInfo().connect.weixin = userInfo.connect.weixin;
                            FengApplication.getInstance().getUserInfo().connect.weibo = userInfo.connect.weibo;
                        }
                        if (jsonBody.has("shop")) {
                            JSONObject jsonShop = jsonBody.getJSONObject("shop");
                            if (jsonShop.has("state")) {
                                FengApplication.getInstance().getUserInfo().setLocalOpenShopState(HomeActivity.this, jsonShop.getInt("state"));
                            }
                            if (jsonShop.has("saletype")) {
                                FengApplication.getInstance().getUserInfo().setLocalSaleType(HomeActivity.this, jsonShop.getInt("saletype"));
                            }
                        }
                        FengApplication.getInstance().getUserInfo().createtime = userInfo.createtime;
                        SharedUtil.putInt(HomeActivity.this, "login_canpublishvideo", userInfo.canpublishvideo);
                        FengApplication.getInstance().saveUserInfo();
                        if (userInfo.iscomplete == 0) {
                            HomeActivity.this.startActivity(new Intent(HomeActivity.this, GuideActivity.class));
                        }
                    } else {
                        String strUserTokern = SharedUtil.getString(HomeActivity.this, "usertouristtoken");
                        JSONObject userJsonObject = jsonResult.getJSONObject("body").getJSONObject("user");
                        if (TextUtils.isEmpty(strUserTokern)) {
                            strUserTokern = userJsonObject.getString("token");
                            SharedUtil.putString(HomeActivity.this, "usertouristtoken", strUserTokern);
                            FengConstant.USERTOURISTTOKEN = strUserTokern;
                        }
                        SharedUtil.putString(HomeActivity.this, "usertouristname", userJsonObject.getString("name"));
                        SharedUtil.putInt(HomeActivity.this, "usertouristId", userJsonObject.getInt("id"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("user/ywf/login/", content, e);
                }
            }
        });
    }

    private void getUpdateJsonUrl() {
        FengApplication.getInstance().httpRequest("home/state/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        String url = jsonResult.getJSONObject("body").getJSONObject("default").getString("updateurl");
                        if (!TextUtils.isEmpty(url)) {
                            HomeActivity.this.getUpdateJson(url);
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().upLoadLog(true, "user/ywf/login/    " + code);
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("user/ywf/login/", content, e);
                }
            }
        });
    }

    private void getUpdateJson(String url) {
        FengApplication.getInstance().httpGetUpdateVersion(url, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                if (statusCode == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        SharedUtil.putString(HomeActivity.this, "update_version_json", content);
                        SharedUtil.putInt(HomeActivity.this, "UPDATE_CODE", jsonObject.getInt("code"));
                        FengUtil.checkUpdate(HomeActivity.this, jsonObject, false, false);
                        ((HomePageNewFragment) HomeActivity.this.mFragments[0]).showUpdataHint();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void changeRedDotDisplayStatus() {
        if (MessageCountManager.getInstance().getFollowPageMessageCount() <= 0 || this.mCurrentTabIndex == 3) {
            ((HomeMainBinding) this.mBaseBinding).ivFourDot.setVisibility(8);
        } else {
            ((HomeMainBinding) this.mBaseBinding).ivFourDot.setVisibility(0);
        }
        if (SharedUtil.getInt(this, "UPDATE_CODE", 0) > FengUtil.getAPPVerionCode(this) || (VideoDownloadManager.newInstance().isMineFragmentHasRedPoint() && this.mCurrentTabIndex != 4)) {
            ((HomeMainBinding) this.mBaseBinding).ivFiveDot.setVisibility(0);
            return;
        }
        ((HomeMainBinding) this.mBaseBinding).ivFiveDot.setVisibility(8);
        VideoDownloadManager.newInstance().hideMineFragmentRedPoint();
    }

    protected void onResume() {
        super.onResume();
        if (this.mIsFirst) {
            this.mIsFirst = false;
            return;
        }
        umengLoadPv();
        showActivityBtn();
    }

    protected void onPause() {
        super.onPause();
        logPageOut();
    }

    protected void onDestroy() {
        super.onDestroy();
        MessageCountManager.getInstance().stopRequestMessageCount();
        stopService(new Intent(this, AudioPlayService.class));
        unregisterScreenReceiver();
        unregisterConnectionChangeReceiver();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        changeRedDotDisplayStatus();
    }

    public void loginSuccess() {
        super.loginSuccess();
        BlackUtil.getInstance().getBlackListData(this, null);
    }

    public void loginOut() {
        super.loginOut();
        ((HomeMainBinding) this.mBaseBinding).ivFourDot.setVisibility(8);
        ((HomeMainBinding) this.mBaseBinding).ivFiveDot.setVisibility(8);
        BlackUtil.getInstance().clearBlackList(this);
    }

    private void httpGetDoMainRed() {
        final long startTime = System.currentTimeMillis();
        OkHttpUtils.get().url("http://red.laosiji.com/p.php").addHeader("User-Agent", OkHttpUtils.userAgent).build().execute(StubApp.getOrigApplicationContext(getApplicationContext()), new StringCallback() {
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            public void onError(Call call, Exception e) {
            }

            public void onResponse(String response) {
                try {
                    JSONObject jsonResult = new JSONObject(response);
                    LogGatherReadUtil.getInstance().addNetElapsedTime((System.currentTimeMillis() - startTime) + "", "http://red.laosiji.com/p.php", "1", "", "");
                    if (jsonResult.has("ApiDomain")) {
                        String apiDoMain = jsonResult.getString("ApiDomain");
                        if (TextUtils.isEmpty(SharedUtil.getString(StubApp.getOrigApplicationContext(HomeActivity.this.getApplicationContext()), "api_key")) && !TextUtils.isEmpty(apiDoMain)) {
                            FengApplication.getInstance().setApiDomainUrl(apiDoMain + "/");
                            HttpConstant.YWFHTTPURL = apiDoMain + "/";
                            HttpConstant.FENGHTML5M = "http://m.laosiji.com/";
                        }
                    }
                    if (jsonResult.has("redalert")) {
                        FengApplication.getInstance().setRedAlertUrl(jsonResult.getString("redalert").trim());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void permissionSuccess() {
        super.permissionSuccess();
        if (!Build.MANUFACTURER.equals("HUAWEI")) {
            this.mScreenshotObserver = new ScreenshotObserver(this);
            this.mScreenshotObserver.startObserve(this);
        }
        getUpdateJsonUrl();
    }

    private void getActivityInfo() {
        FengApplication.getInstance().httpRequest("theme/event/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        HomeActivity.this.mNewActivityInfo = new NewActivityInfo();
                        HomeActivity.this.mNewActivityInfo.parser(jsonBody);
                        ((HomeMainBinding) HomeActivity.this.mBaseBinding).activityBtn.setText(HomeActivity.this.mNewActivityInfo.getAward());
                        HomeActivity.this.showActivityBtn();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getNextSplashData() {
        Map<String, Object> map = new HashMap();
        map.put("pageid", String.valueOf(991));
        map.put("datatype", "0");
        map.put("pagecode", "0");
        FengApplication.getInstance().httpRequest("advert/adserver/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SharedUtil.putString(HomeActivity.this, "ad_new_splash_json", "");
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray adData = jsonResult.getJSONObject("body").getJSONArray(UriUtil.DATA_SCHEME);
                        if (adData.length() > 0) {
                            SharedUtil.putString(HomeActivity.this, "ad_new_splash_json", adData.getJSONObject(0).toString());
                            AdvertInfo advertInfo = new AdvertInfo();
                            advertInfo.parser(adData.getJSONObject(0));
                            if (advertInfo.tmpmap.image.size() > 0) {
                                Fresco.getImagePipeline().prefetchToDiskCache(ImageRequest.fromUri(Uri.parse(FengUtil.getUniformScaleUrl((ImageInfo) advertInfo.tmpmap.image.get(0), 640, 1.47f))), HomeActivity.this);
                                return;
                            }
                            SharedUtil.putString(HomeActivity.this, "ad_new_splash_json", "");
                            return;
                        }
                        SharedUtil.putString(HomeActivity.this, "ad_new_splash_json", "");
                        return;
                    }
                    SharedUtil.putString(HomeActivity.this, "ad_new_splash_json", "");
                } catch (Exception e) {
                    e.printStackTrace();
                    SharedUtil.putString(HomeActivity.this, "ad_new_splash_json", "");
                }
            }
        });
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomeSwitchEvent event) {
        ((HomeMainBinding) this.mBaseBinding).tvSecondTab.performClick();
    }

    private void initLocation() {
        if (VERSION.SDK_INT >= 23) {
            boolean flag1 = checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0;
            boolean flag2;
            if (checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0) {
                flag2 = true;
            } else {
                flag2 = false;
            }
            if (!flag1 && !flag2) {
                MapUtil.newInstance().startDefaultLocation(this);
                return;
            }
            return;
        }
        MapUtil.newInstance().startDefaultLocation(this);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50007 && VERSION.SDK_INT >= 23) {
            boolean flag1 = checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0;
            boolean flag2;
            if (checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0) {
                flag2 = true;
            } else {
                flag2 = false;
            }
            boolean flag3;
            if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0) {
                flag3 = true;
            } else {
                flag3 = false;
            }
            boolean flag4;
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                flag4 = true;
            } else {
                flag4 = false;
            }
            if (!(flag1 || flag2 || flag3 || flag4)) {
                MapUtil.newInstance().startDefaultLocation(this);
            }
        }
        if (requestCode == 60000) {
            try {
                if (grantResults.length != 1 || permissions.length != 1) {
                    return;
                }
                if ((!permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE") || grantResults[0] != 0) && grantResults[0] != 0) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                        showThirdTypeToast("请打开存储权限！");
                    } else {
                        showThirdTypeToast("请打开存储权限！");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                permissionSuccess();
            }
        }
    }

    public void onWifiConnect() {
        super.onWifiConnect();
        BaseActivity activity = ActivityManager.getInstance().getCurrentActivity();
        if (activity instanceof VideoCacheActivity) {
            VideoDownloadManager.newInstance().startAllTask(true);
            ((VideoCacheActivity) activity).refreshData();
        } else {
            VideoDownloadManager.newInstance().startAllTask(false);
        }
        SharedUtil.putBoolean(this, "no_wifi_download", false);
    }

    public void on4GConnect() {
        super.on4GConnect();
        VideoDownloadManager.newInstance().pauseAllTask(true);
        BaseActivity activity = ActivityManager.getInstance().getCurrentActivity();
        if (activity != null && (activity instanceof VideoCacheActivity)) {
            ((VideoCacheActivity) activity).refreshData();
        }
    }

    private void registerScreenReceiver() {
        this.mScreenBroadcastReceiver = new ScreenBroadcastReceiver(this, null);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(this.mScreenBroadcastReceiver, filter);
    }

    private void unregisterScreenReceiver() {
        if (this.mScreenBroadcastReceiver != null) {
            unregisterReceiver(this.mScreenBroadcastReceiver);
        }
    }

    private void registerConnectionChangeReceiver() {
        this.mConnectionChangeReceiver = new ConnectionChangeReceiver();
        registerReceiver(this.mConnectionChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void unregisterConnectionChangeReceiver() {
        unregisterReceiver(this.mConnectionChangeReceiver);
    }

    private void showActivityBtn() {
        if (this.mBaseBinding == null) {
            return;
        }
        if (isShowActivityBtn()) {
            ((HomeMainBinding) this.mBaseBinding).activityBtn.setVisibility(0);
        } else {
            ((HomeMainBinding) this.mBaseBinding).activityBtn.setVisibility(8);
        }
    }

    public boolean isShowActivityBtn() {
        String strFlag = SharedUtil.getString(this, "new_user_flag");
        if (TextUtils.isEmpty(strFlag) || strFlag.indexOf("$") <= 0 || this.mNewActivityInfo == null || TextUtils.isEmpty(this.mNewActivityInfo.url)) {
            return false;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            if (strFlag.indexOf("1$" + simpleDateFormat.format(c.getTime())) < 0) {
                SharedUtil.putString(this, "new_user_flag", "");
                return false;
            } else if (c.get(11) >= this.mNewActivityInfo.starttime && c.get(11) < this.mNewActivityInfo.endtime) {
                return true;
            } else {
                if (c.get(11) < this.mNewActivityInfo.endtime) {
                    return false;
                }
                SharedUtil.putString(this, "new_user_flag", "");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void startToWebRule() {
        Intent webIntent = new Intent(this, WebActivity.class);
        webIntent.putExtra("url", this.mNewActivityInfo.url);
        webIntent.putExtra("DATA_JSON", this.mNewActivityInfo.community.id > 0 ? JsonUtil.toJson(this.mNewActivityInfo.community) : "");
        webIntent.putExtra("type", 1002);
        startActivity(webIntent);
    }
}
