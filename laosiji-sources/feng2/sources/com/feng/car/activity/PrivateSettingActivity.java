package com.feng.car.activity;

import android.support.annotation.IdRes;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityPrivateSettingBinding;
import com.feng.car.utils.JsonUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.WifiUtil;
import com.stub.StubApp;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class PrivateSettingActivity extends BaseActivity<ActivityPrivateSettingBinding> {
    public static int FROM_PRIVATE_LETTER_LIST = 0;
    public static int FROM_PRIVATE_SETTING = 1;

    static {
        StubApp.interface11(2776);
    }

    public int setBaseContentView() {
        return R.layout.activity_private_setting;
    }

    public void initView() {
        if (getIntent().getIntExtra("feng_type", FROM_PRIVATE_LETTER_LIST) == FROM_PRIVATE_SETTING) {
            initNormalTitleBar((int) R.string.private_setting);
        } else {
            initNormalTitleBar((int) R.string.private_letter_setting);
        }
        if (FengApplication.getInstance().getUserInfo().push.messageswitch == 1) {
            ((ActivityPrivateSettingBinding) this.mBaseBinding).rbFriend.setChecked(true);
        } else {
            ((ActivityPrivateSettingBinding) this.mBaseBinding).rbAll.setChecked(true);
        }
        ((ActivityPrivateSettingBinding) this.mBaseBinding).rgPrivateSetting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (WifiUtil.isNetworkAvailable(PrivateSettingActivity.this)) {
                    switch (checkedId) {
                        case R.id.rb_all /*2131624556*/:
                            PrivateSettingActivity.this.sendData(2);
                            return;
                        case R.id.rb_friend /*2131624557*/:
                            PrivateSettingActivity.this.sendData(1);
                            return;
                        default:
                            return;
                    }
                }
                PrivateSettingActivity.this.showThirdTypeToast((int) R.string.network_ng);
            }
        });
    }

    private void sendData(final int value) {
        Map<String, Object> map = new HashMap();
        map.put("messageswitch", String.valueOf(value));
        String str = JsonUtil.toJson(map);
        map.clear();
        map.put("info", str);
        FengApplication.getInstance().httpRequest("user/update/", map, new OkHttpResponseCallback() {
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
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        FengApplication.getInstance().getUserInfo().push.messageswitch = value;
                        return;
                    }
                    FengApplication.getInstance().checkCode("user/update/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
