package com.feng.car.activity;

import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityScanningLoginBinding;
import com.feng.car.event.ScanningLoginEvent;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class ScanningLoginPCActivity extends BaseActivity<ActivityScanningLoginBinding> {
    String mQRCode = "";

    public int setBaseContentView() {
        return R.layout.activity_scanning_login;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.none);
        this.mQRCode = getIntent().getStringExtra("code");
        ((ActivityScanningLoginBinding) this.mBaseBinding).noLogin.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                EventBus.getDefault().post(new ScanningLoginEvent());
                ScanningLoginPCActivity.this.finish();
            }
        });
        ((ActivityScanningLoginBinding) this.mBaseBinding).login.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ScanningLoginPCActivity.this.login();
            }
        });
    }

    private void login() {
        Map<String, Object> map = new HashMap();
        if (StringUtil.isEmpty(this.mQRCode)) {
            showSecondTypeToast((int) R.string.loginfailed_scanning_retry);
            return;
        }
        map.put("qrcode", this.mQRCode);
        FengApplication.getInstance().httpRequest("user/ywf/scan/code/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ScanningLoginPCActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ScanningLoginPCActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        EventBus.getDefault().post(new ScanningLoginEvent());
                        ScanningLoginPCActivity.this.finish();
                    } else if (code == -4) {
                        ScanningLoginPCActivity.this.showThirdTypeToast((int) R.string.user_notexist);
                    } else if (code == -99) {
                        ScanningLoginPCActivity.this.showThirdTypeToast((int) R.string.qrcode_overtime);
                    } else {
                        ScanningLoginPCActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ScanningLoginPCActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }
        });
    }
}
