package com.feng.car.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityPushBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.operation.OperationCallback;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.WifiUtil;
import com.stub.StubApp;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class PushSettingActivity extends BaseActivity<ActivityPushBinding> {
    private UserInfo mUserInfo;

    static {
        StubApp.interface11(2779);
    }

    protected native void onCreate(Bundle bundle);

    public void onSingleClick(View v) {
        Intent intent = new Intent(this, PushSettingSecondaryActivity.class);
        switch (v.getId()) {
            case R.id.rl_push_comment /*2131624559*/:
                intent.putExtra("feng_type", 1);
                break;
            case R.id.rl_push_at_me /*2131624560*/:
                intent.putExtra("feng_type", 2);
                break;
            case R.id.rl_push_private_letter /*2131624561*/:
                intent.putExtra("feng_type", 3);
                break;
            case R.id.rl_open_netmonitor /*2131624562*/:
                SharedUtil.putBoolean(this, "click_floating_window", true);
                startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + getPackageName())));
                return;
        }
        startActivity(intent);
    }

    public void initView() {
        initNormalTitleBar((int) R.string.push_message);
        ((ActivityPushBinding) this.mBaseBinding).sliderBtnAttention.setImageResource(R.drawable.btn_bottom, R.drawable.btn_frame, R.drawable.btn_mask, R.drawable.btn_unpressed, R.drawable.btn_pressed);
        this.mUserInfo = FengApplication.getInstance().getUserInfo();
        ((ActivityPushBinding) this.mBaseBinding).setLoginUserInfo(this.mUserInfo);
        ((ActivityPushBinding) this.mBaseBinding).sliderBtnAttention.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (!WifiUtil.isNetworkAvailable(PushSettingActivity.this)) {
                    PushSettingActivity.this.showThirdTypeToast((int) R.string.network_ng);
                } else if (arg1) {
                    PushSettingActivity.this.sendData("followpush", 1);
                } else {
                    PushSettingActivity.this.sendData("followpush", 0);
                }
            }
        });
        ((ActivityPushBinding) this.mBaseBinding).rlPushComment.setOnClickListener(this);
        ((ActivityPushBinding) this.mBaseBinding).rlPushAtMe.setOnClickListener(this);
        ((ActivityPushBinding) this.mBaseBinding).rlPushPrivateLetter.setOnClickListener(this);
        ((ActivityPushBinding) this.mBaseBinding).rlOpenNetmonitor.setOnClickListener(this);
    }

    private void sendData(final String key, final int value) {
        Map<String, Object> map = new HashMap();
        map.put(key, String.valueOf(value));
        String str = JsonUtil.toJson(map);
        map.clear();
        map.put("info", str);
        this.mUserInfo.updateInfoOperation(map, new OperationCallback() {
            public void onNetworkError() {
                PushSettingActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                PushSettingActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code != 1) {
                        FengApplication.getInstance().checkCode("user/update/", code);
                    } else if (key.equals("followpush")) {
                        FengApplication.getInstance().getUserInfo().push.followpush = value;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("user/update/", content, e);
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if (FengUtil.isMiuiFloatWindowOpAllowed(this, false)) {
            ((ActivityPushBinding) this.mBaseBinding).ivOpenFloat.setImageResource(R.drawable.arrow_black54);
        } else {
            ((ActivityPushBinding) this.mBaseBinding).ivOpenFloat.setImageResource(R.drawable.dot_e12c2c_radius_15px);
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_push;
    }
}
