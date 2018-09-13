package com.feng.car.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivitySplashBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.DateUtil;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private AdvertInfo mAdvertInfo;
    private boolean mHasRunHandle = false;
    private boolean mHasToHome = false;
    private boolean mIsFirst = true;
    private boolean mIsSpecialImage = false;
    private boolean mLoadImage = false;
    private String mNewCreatTime = "";
    private int mNewFlag = 0;
    private boolean mRegisterFlag = false;
    private TimerTask mTask;
    private int mTime = 0;

    static {
        StubApp.interface11(3131);
    }

    protected native void onCreate(Bundle bundle);

    public void initView() {
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        LogGatherReadUtil.getInstance().init(StubApp.getOrigApplicationContext(getApplicationContext()));
        LogGatherReadUtil.getInstance().resetLaunchidentifying();
        LogGatherReadUtil.getInstance().resetSwitchidentifying();
        this.mLogGatherInfo.setBasePage("app_welcome");
        this.mLogGatherInfo.addAppStartTime();
        this.mLogGatherInfo.addPageInTime();
        getWindow().setFlags(1024, 1024);
        if (VERSION.SDK_INT >= 19) {
            getWindow().setFlags(67108864, 67108864);
        }
        try {
            int uiFlags;
            if (VERSION.SDK_INT >= 19) {
                uiFlags = 1798 | 4096;
            } else {
                uiFlags = 1798 | 1;
            }
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((getIntent().getFlags() & 4194304) != 0) {
            finish();
            return;
        }
        closeSwip();
        MobclickAgent.openActivityDurationTrack(false);
        hideDefaultTitleBar();
        checkBasePermission();
        if (FengApplication.getInstance().isLoginUser() || !TextUtils.isEmpty(SharedUtil.getString(this, "usertouristtoken"))) {
            showIndexBackground();
        } else {
            this.mLoadImage = true;
        }
    }

    private void handleIntent() {
        this.mHasRunHandle = true;
        final Intent intent = new Intent(this, HomeActivity.class);
        Uri uridata = getIntent().getData();
        if (uridata != null) {
            List list = ActivityManager.getInstance().getActitysList();
            try {
                String url = uridata.getQueryParameter("url");
                if (!TextUtils.isEmpty(url)) {
                    if (list.size() <= 1) {
                        intent.putExtra("url", url);
                    } else if (url != null && url.length() > 0) {
                        Intent intent1 = new Intent(ActivityManager.getInstance().getActivity(list.size() - 1), TransparentActivity.class);
                        intent1.putExtra("url", url);
                        startActivity(intent1);
                        finish();
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.mTime > 0) {
            ((ActivitySplashBinding) this.mBaseBinding).rlSkip.setVisibility(0);
            this.mTask = new TimerTask() {
                public void run() {
                    SplashActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            if (SplashActivity.this.mTime > 0) {
                                SplashActivity.this.mTime = SplashActivity.this.mTime - 1;
                            } else if (!SplashActivity.this.mHasToHome) {
                                SplashActivity.this.mHasToHome = true;
                                if (SplashActivity.this.mTask != null) {
                                    SplashActivity.this.mTask.cancel();
                                }
                                if (!(SplashActivity.this.mHasToHome || SplashActivity.this.mAdvertInfo == null)) {
                                    SplashActivity.this.mAdvertInfo.adPvHandle(SplashActivity.this, false);
                                }
                                SplashActivity.this.startActivity(intent);
                                ActivityManager.getInstance().finishAllActivity();
                            }
                            ((ActivitySplashBinding) SplashActivity.this.mBaseBinding).tvSkipTime.setText((SplashActivity.this.mTime + 1) + "");
                        }
                    });
                }
            };
            new Timer().schedule(this.mTask, 0, 1000);
            FengApplication.getInstance().getSeverceState();
            MobclickAgent.onEvent(this, "poweron_pv");
            if (this.mIsSpecialImage) {
                ((ActivitySplashBinding) this.mBaseBinding).indexBg.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (!SplashActivity.this.mHasToHome && SplashActivity.this.mAdvertInfo != null) {
                            SplashActivity.this.mAdvertInfo.adPvHandle(SplashActivity.this, false);
                            SplashActivity.this.mHasToHome = true;
                            intent.putExtra("special_url", SplashActivity.this.mAdvertInfo.landingurl);
                            SplashActivity.this.mTask.cancel();
                            SplashActivity.this.startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    }
                });
            }
            ((ActivitySplashBinding) this.mBaseBinding).rlSkip.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (!SplashActivity.this.mHasToHome) {
                        SplashActivity.this.mHasToHome = true;
                        SplashActivity.this.mTask.cancel();
                        SplashActivity.this.startActivity(intent);
                        ActivityManager.getInstance().finishAllActivity();
                    }
                }
            });
            return;
        }
        ((ActivitySplashBinding) this.mBaseBinding).rlSkip.setVisibility(8);
        if (!this.mHasToHome) {
            this.mHasToHome = true;
            startActivity(intent);
            ActivityManager.getInstance().finishAllActivity();
        }
    }

    protected void onResume() {
        if (this.mIsFirst) {
            this.mIsFirst = false;
        } else {
            this.mLogGatherInfo.addPageInTime();
        }
        super.onResume();
    }

    protected void onPause() {
        this.mLogGatherInfo.addPageOutTime();
        super.onPause();
    }

    public void finish() {
        super.finish();
    }

    public int setBaseContentView() {
        return R.layout.activity_splash;
    }

    private void showIndexBackground() {
        try {
            String adJson = SharedUtil.getString(this, "ad_new_splash_json");
            if (TextUtils.isEmpty(adJson)) {
                getNextSplashData();
                return;
            }
            if (this.mAdvertInfo == null) {
                this.mAdvertInfo = new AdvertInfo();
            }
            this.mAdvertInfo.parser(new JSONObject(adJson));
            if (this.mAdvertInfo.cpd.equals("cpd_" + DateUtil.getStringByFormat(LogGatherReadUtil.getInstance().getBeijingTime(), "yyyy-MM-dd"))) {
                String picUrl = FengUtil.getUniformScaleUrl((ImageInfo) this.mAdvertInfo.tmpmap.image.get(0), 640, 1.47f);
                FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(picUrl));
                if (resource == null || resource.getFile() == null || !resource.getFile().exists()) {
                    getNextSplashData();
                    return;
                }
                this.mTime = 4;
                this.mLoadImage = true;
                ((ActivitySplashBinding) this.mBaseBinding).indexBg.setVisibility(0);
                ((ActivitySplashBinding) this.mBaseBinding).ivLogo.setVisibility(0);
                ((ActivitySplashBinding) this.mBaseBinding).rlParent.setBackgroundResource(R.color.color_ffffff);
                ((ActivitySplashBinding) this.mBaseBinding).indexBg.setImageURI(Uri.parse(picUrl));
                ((ActivitySplashBinding) this.mBaseBinding).tvAd.setVisibility(0);
                ((ActivitySplashBinding) this.mBaseBinding).tvAd.setText(this.mAdvertInfo.tmpmap.label);
                this.mIsSpecialImage = true;
                this.mAdvertInfo.adPvHandle(this, true);
                if (this.mRegisterFlag && !this.mHasRunHandle) {
                    handleIntent();
                    return;
                }
                return;
            }
            getNextSplashData();
        } catch (Exception e) {
            getNextSplashData();
        }
    }

    private void initFirstPublish() {
        try {
            ((ActivitySplashBinding) this.mBaseBinding).ivFirstPublish.setVisibility(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deviceRegister() {
        String strUserToken = SharedUtil.getString(this, "usertouristtoken");
        if (TextUtils.isEmpty(strUserToken)) {
            Map<String, Object> map = new HashMap();
            map.put("token", "");
            String device = LogGatherReadUtil.getInstance().getUniqueId(this);
            if (TextUtils.isEmpty(device)) {
                device = Build.FINGERPRINT;
            }
            map.put("visitordevice", device);
            FengApplication.getInstance().httpRequest("user/ywf/login/", map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    SplashActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            SplashActivity.this.mLoadImage = true;
                            SplashActivity.this.deviceRegister();
                        }
                    });
                }

                public void onStart() {
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    SplashActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            SplashActivity.this.mLoadImage = true;
                            SplashActivity.this.deviceRegister();
                        }
                    });
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        if (jsonResult.getInt("code") == 1) {
                            JSONObject userJsonObject = jsonResult.getJSONObject("body").getJSONObject("user");
                            String strUserTokern = userJsonObject.getString("token");
                            if (TextUtils.isEmpty(strUserTokern)) {
                                SplashActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                    public void onSingleClick(View v) {
                                        SplashActivity.this.mLoadImage = true;
                                        SplashActivity.this.deviceRegister();
                                    }
                                });
                                return;
                            }
                            SplashActivity.this.hideEmptyView();
                            FengConstant.USERTOURISTTOKEN = strUserTokern;
                            SharedUtil.putString(SplashActivity.this, "usertouristtoken", strUserTokern);
                            SharedUtil.putString(SplashActivity.this, "usertouristname", userJsonObject.getString("name"));
                            SharedUtil.putInt(SplashActivity.this, "usertouristId", userJsonObject.getInt("id"));
                            if (!userJsonObject.has("eventuser")) {
                                SharedUtil.putString(SplashActivity.this, "new_user_flag", "");
                            } else if (userJsonObject.getInt("eventuser") != 1 || TextUtils.isEmpty(userJsonObject.getString("createtime"))) {
                                SharedUtil.putString(SplashActivity.this, "new_user_flag", "");
                            } else {
                                SharedUtil.putString(SplashActivity.this, "new_user_flag", "1$" + userJsonObject.getString("createtime"));
                            }
                            SplashActivity.this.mRegisterFlag = true;
                            if (SplashActivity.this.mLoadImage && !SplashActivity.this.mHasRunHandle) {
                                SplashActivity.this.handleIntent();
                                return;
                            }
                            return;
                        }
                        SplashActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                SplashActivity.this.mLoadImage = true;
                                SplashActivity.this.deviceRegister();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog("user/ywf/login/", content, e);
                        SplashActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                SplashActivity.this.mLoadImage = true;
                                SplashActivity.this.deviceRegister();
                            }
                        });
                    }
                }
            });
            return;
        }
        FengConstant.USERTOURISTTOKEN = strUserToken;
        this.mRegisterFlag = true;
        if (this.mLoadImage && !this.mHasRunHandle) {
            handleIntent();
        }
    }

    public void permissionSuccess() {
        super.permissionSuccess();
        checkPermission();
    }

    private void checkPermission() {
        if (VERSION.SDK_INT >= 23) {
            boolean flag3;
            boolean flag2 = checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0;
            if (checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0) {
                flag3 = true;
            } else {
                flag3 = false;
            }
            List<String> strPermissions = new ArrayList();
            if (flag2) {
                strPermissions.add("android.permission.ACCESS_COARSE_LOCATION");
            }
            if (flag3) {
                strPermissions.add("android.permission.ACCESS_FINE_LOCATION");
            }
            if (strPermissions.size() > 0) {
                ActivityCompat.requestPermissions(this, (String[]) strPermissions.toArray(new String[strPermissions.size()]), 50006);
                return;
            } else {
                doRegisterOrIntent();
                return;
            }
        }
        doRegisterOrIntent();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 50006) {
                doRegisterOrIntent();
            }
        } catch (Exception e) {
            doRegisterOrIntent();
        }
    }

    private void doRegisterOrIntent() {
        if (FengApplication.getInstance().isLoginUser()) {
            this.mRegisterFlag = true;
            if (this.mLoadImage && !this.mHasRunHandle) {
                handleIntent();
                return;
            }
            return;
        }
        deviceRegister();
    }

    private void getNextSplashData() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.mLoadImage = true;
                if (SplashActivity.this.mRegisterFlag && !SplashActivity.this.mHasRunHandle) {
                    SplashActivity.this.handleIntent();
                }
            }
        }, 1000);
        final long time = System.currentTimeMillis();
        Map<String, Object> map = new HashMap();
        map.put("pageid", String.valueOf(991));
        map.put("datatype", "0");
        map.put("pagecode", "0");
        FengApplication.getInstance().httpRequest("advert/adserver/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                SplashActivity.this.mLoadImage = true;
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SharedUtil.putString(SplashActivity.this, "ad_new_splash_json", "");
                SplashActivity.this.mLoadImage = true;
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray adData = jsonResult.getJSONObject("body").getJSONArray(UriUtil.DATA_SCHEME);
                        if (adData.length() > 0) {
                            SharedUtil.putString(SplashActivity.this, "ad_new_splash_json", adData.getJSONObject(0).toString());
                            AdvertInfo advertInfo = new AdvertInfo();
                            advertInfo.parser(adData.getJSONObject(0));
                            if (advertInfo.tmpmap.image.size() > 0) {
                                SplashActivity.this.imageDownload(advertInfo, FengUtil.getUniformScaleUrl((ImageInfo) advertInfo.tmpmap.image.get(0), 640, 1.47f), time);
                                return;
                            }
                            SplashActivity.this.mLoadImage = true;
                            SharedUtil.putString(SplashActivity.this, "ad_new_splash_json", "");
                            return;
                        }
                        SplashActivity.this.mLoadImage = true;
                        SharedUtil.putString(SplashActivity.this, "ad_new_splash_json", "");
                        return;
                    }
                    SplashActivity.this.mLoadImage = true;
                    SharedUtil.putString(SplashActivity.this, "ad_new_splash_json", "");
                } catch (Exception e) {
                    e.printStackTrace();
                    SplashActivity.this.mLoadImage = true;
                    SharedUtil.putString(SplashActivity.this, "ad_new_splash_json", "");
                }
            }
        });
    }

    private void imageDownload(AdvertInfo advertInfo, String url, long time) {
        final long j = time;
        final String str = url;
        final AdvertInfo advertInfo2 = advertInfo;
        Fresco.getImagePipeline().fetchDecodedImage(ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build(), this).subscribe(new BaseBitmapDataSubscriber() {
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                if (System.currentTimeMillis() - j > 1000) {
                    SplashActivity.this.mTime = 0;
                } else {
                    try {
                        SplashActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                SplashActivity.this.mTime = 4;
                                SplashActivity.this.mLoadImage = true;
                                ((ActivitySplashBinding) SplashActivity.this.mBaseBinding).indexBg.setVisibility(0);
                                ((ActivitySplashBinding) SplashActivity.this.mBaseBinding).ivLogo.setVisibility(0);
                                ((ActivitySplashBinding) SplashActivity.this.mBaseBinding).rlParent.setBackgroundResource(R.color.color_ffffff);
                                ((ActivitySplashBinding) SplashActivity.this.mBaseBinding).indexBg.setImageURI(str);
                                ((ActivitySplashBinding) SplashActivity.this.mBaseBinding).tvAd.setVisibility(0);
                                ((ActivitySplashBinding) SplashActivity.this.mBaseBinding).tvAd.setText(advertInfo2.tmpmap.label);
                                SplashActivity.this.mIsSpecialImage = true;
                                advertInfo2.adPvHandle(SplashActivity.this, true);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        SplashActivity.this.mLoadImage = true;
                        SplashActivity.this.mTime = 0;
                        if (SplashActivity.this.mRegisterFlag && !SplashActivity.this.mHasRunHandle) {
                            SplashActivity.this.handleIntent();
                        }
                    }
                }
                if (SplashActivity.this.mRegisterFlag && !SplashActivity.this.mHasRunHandle) {
                    SplashActivity.this.handleIntent();
                }
            }

            public void onFailureImpl(DataSource dataSource) {
                SplashActivity.this.mLoadImage = true;
            }
        }, CallerThreadExecutor.getInstance());
    }
}
