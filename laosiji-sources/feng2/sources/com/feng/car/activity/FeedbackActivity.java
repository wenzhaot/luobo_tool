package com.feng.car.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityFeedbackBinding;
import com.feng.car.utils.FengUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.WifiUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackActivity extends BaseActivity<ActivityFeedbackBinding> {
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedBack_send /*2131624362*/:
                String strContent = ((ActivityFeedbackBinding) this.mBaseBinding).etFeedback.getText().toString().trim();
                if (strContent.equals("网络检测")) {
                    startActivity(new Intent(this, NetCheckActivity.class));
                    return;
                } else if (strContent.length() <= 0) {
                    showThirdTypeToast((int) R.string.feed_back_empty);
                    return;
                } else {
                    String strContactType = ((ActivityFeedbackBinding) this.mBaseBinding).etContactType.getText().toString().trim();
                    if (strContactType.length() <= 0) {
                        showThirdTypeToast((int) R.string.connect_empty);
                        return;
                    } else if (strContactType.length() < 5) {
                        showThirdTypeToast((int) R.string.write_right_connect);
                        return;
                    } else {
                        sendData(strContent, strContactType);
                        return;
                    }
                }
            default:
                return;
        }
    }

    private void sendData(String strContent, String strContactType) {
        Map<String, Object> map = new HashMap();
        map.put("description", strContent);
        map.put("version", FengUtil.getDeviceVersion(this));
        map.put("clientappid", String.valueOf(1));
        map.put("device", getDeviceModel());
        map.put("address", strContactType);
        FengApplication.getInstance().httpRequest("user/feed/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                FeedbackActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                FeedbackActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        FeedbackActivity.this.showFirstTypeToast((int) R.string.submit_success);
                        FeedbackActivity.this.finish();
                        return;
                    }
                    FengApplication.getInstance().checkCode("user/feed/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FeedbackActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    FengApplication.getInstance().upLoadTryCatchLog("user/feed/", content, e);
                }
            }
        });
    }

    public int setBaseContentView() {
        return R.layout.activity_feedback;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.feedback);
        ((ActivityFeedbackBinding) this.mBaseBinding).tvFeedBackSend.setOnClickListener(this);
    }

    public String getDeviceModel() {
        String model = Build.MODEL;
        String release = VERSION.RELEASE;
        String strWifi = "";
        if (WifiUtil.isMobile(this)) {
            strWifi = "3g";
        }
        if (WifiUtil.isWifiConnectivity(this)) {
            strWifi = "wifi";
        }
        return model + "[" + release + "][" + strWifi + "]";
    }
}
