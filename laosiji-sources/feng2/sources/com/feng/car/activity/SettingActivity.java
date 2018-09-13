package com.feng.car.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivitySettingBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.event.AudioStateEvent;
import com.feng.car.event.UserLoginEvent;
import com.feng.car.service.AudioPlayService;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.okhttp.cookie.store.PersistentCookieStore;
import com.feng.library.utils.SharedUtil;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {
    private final int PERMISSION_CLEAR_CACHE = 2;
    private final int PERMISSION_GET_CACHE = 1;
    private int PERMISSION_TYPE = 1;
    private TimerTask mTask;
    private int mTime = 4;
    private Timer mTimer = new Timer();

    static {
        StubApp.interface11(2984);
    }

    protected native void onCreate(Bundle bundle);

    protected void onResume() {
        super.onResume();
        if (SharedUtil.getInt(this, "UPDATE_CODE", 0) > FengUtil.getAPPVerionCode(this)) {
            ((ActivitySettingBinding) this.mBaseBinding).ivSettingAboutHint.setBackgroundResource(R.drawable.dot_e12c2c_radius_15px);
        } else {
            ((ActivitySettingBinding) this.mBaseBinding).ivSettingAboutHint.setBackgroundResource(R.drawable.arrow_black54);
        }
        if (FengUtil.isMiuiFloatWindowOpAllowed(this, false)) {
            ((ActivitySettingBinding) this.mBaseBinding).ivSettingPushMessage.setBackgroundResource(R.drawable.arrow_black54);
        } else {
            ((ActivitySettingBinding) this.mBaseBinding).ivSettingPushMessage.setBackgroundResource(R.drawable.dot_e12c2c_radius_15px);
        }
        int n = SharedUtil.getInt(this, "image_quality", 0);
        if (n == 2) {
            ((ActivitySettingBinding) this.mBaseBinding).tvSettingImageQuality.setText(R.string.image_quality_normal);
        } else if (n == 1) {
            ((ActivitySettingBinding) this.mBaseBinding).tvSettingImageQuality.setText(R.string.image_quality_HD);
        } else {
            ((ActivitySettingBinding) this.mBaseBinding).tvSettingImageQuality.setText(R.string.image_quality_auto);
        }
    }

    public void onSingleClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_setting_account /*2131624611*/:
                startActivity(new Intent(this, SettingAccountSecurityActivity.class));
                return;
            case R.id.rl_setting_push_message /*2131624612*/:
                startActivity(new Intent(this, PushSettingActivity.class));
                return;
            case R.id.rl_setting_private /*2131624614*/:
                intent = new Intent(this, PrivateSettingActivity.class);
                intent.putExtra("feng_type", PrivateSettingActivity.FROM_PRIVATE_SETTING);
                startActivity(intent);
                return;
            case R.id.rl_setting_image_quality /*2131624615*/:
                startActivity(new Intent(this, SettingImageQualityActivity.class));
                return;
            case R.id.rl_setting_about_us /*2131624618*/:
                startActivity(new Intent(this, AboutUsActivity.class));
                return;
            case R.id.rl_setting_clear_cache /*2131624620*/:
                this.PERMISSION_TYPE = 2;
                checkPermission();
                return;
            case R.id.rl_setting_feedback /*2131624622*/:
                startActivity(new Intent(this, FeedbackActivity.class));
                return;
            case R.id.rl_setting_user_agreement /*2131624623*/:
                intent = new Intent(this, WebActivity.class);
                intent.putExtra("title", getString(R.string.user_agreement));
                intent.putExtra("url", HttpConstant.USER_PROTOCOL_URL);
                startActivity(intent);
                return;
            case R.id.rl_setting_cooperation /*2131624624*/:
                intent = new Intent(this, WebActivity.class);
                intent.putExtra("title", getString(R.string.cooperation_info));
                intent.putExtra("url", HttpConstant.COOPERATIVE_AGREEMENT);
                startActivity(intent);
                return;
            case R.id.tv_setting_exit /*2131624625*/:
                exitDialog();
                return;
            default:
                return;
        }
    }

    protected void exitDialog() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.affirm), false));
        CommonDialog.showCommonDialog(this, "是否退出老司机?", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                SettingActivity.this.logOutRequest();
            }
        });
    }

    private void clearDialog() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("清除缓存", false));
        CommonDialog.showCommonDialog(this, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                SettingActivity.this.showProgress("", "正在清理缓存").show();
                FengUtil.clearImageCache(SettingActivity.this);
                PersistentCookieStore.getInstance(SettingActivity.this).removeAll();
                SettingActivity.this.showClearResult();
            }
        });
    }

    private void showClearResult() {
        final Dialog mShareDialog = new Dialog(this, R.style.ArticleShareDialog);
        mShareDialog.setCanceledOnTouchOutside(true);
        mShareDialog.setCancelable(false);
        Window window = mShareDialog.getWindow();
        window.setGravity(17);
        window.setContentView(View.inflate(this, R.layout.dialog_clear_cache, null));
        window.setLayout(-2, -2);
        this.mTask = new TimerTask() {
            public void run() {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    @SuppressLint({"SetTextI18n"})
                    public void run() {
                        if (SettingActivity.this.mTime < 3) {
                            SettingActivity.this.hideProgress();
                            mShareDialog.show();
                        }
                        if (SettingActivity.this.mTime <= 0) {
                            mShareDialog.dismiss();
                            SettingActivity.this.mTime = 4;
                            ((ActivitySettingBinding) SettingActivity.this.mBaseBinding).tvSettingCacheSize.setText(R.string.cache_zero);
                            SettingActivity.this.mTask.cancel();
                        }
                        SettingActivity.this.mTime = SettingActivity.this.mTime - 1;
                    }
                });
            }
        };
        this.mTimer.schedule(this.mTask, 0, 1000);
    }

    private void logOutRequest() {
        Map<String, Object> map = new HashMap();
        String device = LogGatherReadUtil.getInstance().getUniqueId(this);
        if (TextUtils.isEmpty(device)) {
            device = Build.FINGERPRINT;
        }
        map.put("visitordevice", device);
        FengApplication.getInstance().httpRequest("user/logout/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                SettingActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
                SettingActivity.this.showProgress("", "退出中...");
            }

            public void onFinish() {
                SettingActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SettingActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    if (resultJson.getInt("code") == 1) {
                        JSONObject userJsonObject = resultJson.getJSONObject("body").getJSONObject("user");
                        String strUserTokern = userJsonObject.getString("token");
                        if (TextUtils.isEmpty(strUserTokern)) {
                            SettingActivity.this.showSecondTypeToast((int) R.string.logout_fail);
                            return;
                        }
                        SettingActivity.this.showFirstTypeToast((int) R.string.logout_success);
                        FengConstant.USERTOURISTTOKEN = strUserTokern;
                        SharedUtil.putString(SettingActivity.this, "usertouristtoken", strUserTokern);
                        SharedUtil.putString(SettingActivity.this, "usertouristname", userJsonObject.getString("name"));
                        SharedUtil.putInt(SettingActivity.this, "usertouristId", userJsonObject.getInt("id"));
                        SettingActivity.this.exitSuccess();
                        return;
                    }
                    SettingActivity.this.showSecondTypeToast((int) R.string.logout_fail);
                } catch (Exception e) {
                    e.printStackTrace();
                    SettingActivity.this.showSecondTypeToast((int) R.string.logout_fail);
                    FengApplication.getInstance().upLoadTryCatchLog("user/logout/", content, e);
                }
            }
        });
    }

    private void exitSuccess() {
        EventBus.getDefault().post(new UserLoginEvent(false));
        FengApplication.getInstance().clearUserInfo();
        PersistentCookieStore.getInstance(this).removeAll();
        FengApplication.getInstance().setAlreadyFollowShow(false);
        stopService(new Intent(this, AudioPlayService.class));
        EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.FINISH_STATE, 0, 0));
        EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.NORMAL_STATE));
        finish();
    }

    public void initView() {
        initNormalTitleBar((int) R.string.setting);
        checkPermission();
        if (!FengApplication.getInstance().isLoginUser()) {
            ((ActivitySettingBinding) this.mBaseBinding).rlSettingAccount.setVisibility(8);
            ((ActivitySettingBinding) this.mBaseBinding).rlSettingPushMessage.setVisibility(8);
            ((ActivitySettingBinding) this.mBaseBinding).rlSettingPrivate.setVisibility(8);
            ((ActivitySettingBinding) this.mBaseBinding).tvSettingExit.setVisibility(8);
        }
        ((ActivitySettingBinding) this.mBaseBinding).rlSettingAccount.setOnClickListener(this);
        ((ActivitySettingBinding) this.mBaseBinding).rlSettingPushMessage.setOnClickListener(this);
        ((ActivitySettingBinding) this.mBaseBinding).rlSettingPrivate.setOnClickListener(this);
        ((ActivitySettingBinding) this.mBaseBinding).rlSettingImageQuality.setOnClickListener(this);
        ((ActivitySettingBinding) this.mBaseBinding).rlSettingAboutUs.setOnClickListener(this);
        ((ActivitySettingBinding) this.mBaseBinding).rlSettingClearCache.setOnClickListener(this);
        ((ActivitySettingBinding) this.mBaseBinding).rlSettingFeedback.setOnClickListener(this);
        ((ActivitySettingBinding) this.mBaseBinding).rlSettingUserAgreement.setOnClickListener(this);
        ((ActivitySettingBinding) this.mBaseBinding).rlSettingCooperation.setOnClickListener(this);
        ((ActivitySettingBinding) this.mBaseBinding).tvSettingExit.setOnClickListener(this);
    }

    public int setBaseContentView() {
        return R.layout.activity_setting;
    }

    private void checkPermission() {
        if (this.PERMISSION_TYPE == 1) {
            ((ActivitySettingBinding) this.mBaseBinding).tvSettingCacheSize.setText(FengUtil.getCacheSize());
        } else if (this.PERMISSION_TYPE == 2) {
            clearDialog();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50001) {
            try {
                if (permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE") && grantResults[0] == 0) {
                    if (this.PERMISSION_TYPE == 1) {
                        ((ActivitySettingBinding) this.mBaseBinding).tvSettingCacheSize.setText(FengUtil.getCacheSize());
                    } else if (this.PERMISSION_TYPE == 2) {
                        clearDialog();
                    }
                } else if (this.PERMISSION_TYPE == 2) {
                    showThirdTypeToast((int) R.string.clearcach_permission);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
