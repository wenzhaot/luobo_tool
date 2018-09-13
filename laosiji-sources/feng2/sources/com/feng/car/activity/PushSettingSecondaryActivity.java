package com.feng.car.activity;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityPushSecondaryBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.operation.OperationCallback;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.WifiUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class PushSettingSecondaryActivity extends BaseActivity<ActivityPushSecondaryBinding> {
    private int mType;

    public int setBaseContentView() {
        return R.layout.activity_push_secondary;
    }

    private void initData() {
        int nType = 0;
        switch (this.mType) {
            case 1:
                nType = FengApplication.getInstance().getUserInfo().push.commentpush;
                break;
            case 2:
                nType = FengApplication.getInstance().getUserInfo().push.atmine;
                break;
            case 3:
                nType = FengApplication.getInstance().getUserInfo().push.userdirectmessagepush;
                break;
        }
        switch (nType) {
            case 0:
                ((ActivityPushSecondaryBinding) this.mBaseBinding).rbClose.setChecked(true);
                return;
            case 1:
                ((ActivityPushSecondaryBinding) this.mBaseBinding).rbFriend.setChecked(true);
                return;
            case 2:
                ((ActivityPushSecondaryBinding) this.mBaseBinding).rbAll.setChecked(true);
                return;
            default:
                return;
        }
    }

    public void getLocalIntentData() {
        this.mType = getIntent().getIntExtra("feng_type", 1);
    }

    public void initView() {
        switch (this.mType) {
            case 1:
                initNormalTitleBar((int) R.string.comment);
                break;
            case 2:
                initNormalTitleBar((int) R.string.at_me);
                break;
            case 3:
                initNormalTitleBar((int) R.string.private_letter);
                break;
        }
        RadioGroup mGroup = (RadioGroup) findViewById(R.id.rg_setting);
        initData();
        mGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (WifiUtil.isNetworkAvailable(PushSettingSecondaryActivity.this)) {
                    switch (checkedId) {
                        case R.id.rb_all /*2131624556*/:
                            PushSettingSecondaryActivity.this.sendData(2);
                            return;
                        case R.id.rb_friend /*2131624557*/:
                            PushSettingSecondaryActivity.this.sendData(1);
                            return;
                        case R.id.rb_close /*2131624566*/:
                            PushSettingSecondaryActivity.this.sendData(0);
                            return;
                        default:
                            return;
                    }
                }
                PushSettingSecondaryActivity.this.showThirdTypeToast((int) R.string.network_ng);
            }
        });
    }

    private void sendData(final int value) {
        Map<String, Object> map = new HashMap();
        switch (this.mType) {
            case 1:
                map.put("commentpush", String.valueOf(value));
                break;
            case 2:
                map.put("atmine", String.valueOf(value));
                break;
            case 3:
                map.put("userdirectmessagepush", String.valueOf(value));
                break;
        }
        String str = JsonUtil.toJson(map);
        map.clear();
        map.put("info", str);
        new UserInfo().updateInfoOperation(map, new OperationCallback() {
            public void onNetworkError() {
                PushSettingSecondaryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                PushSettingSecondaryActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code != 1) {
                        FengApplication.getInstance().checkCode("user/update/", code);
                    } else if (PushSettingSecondaryActivity.this.mType == 2) {
                        FengApplication.getInstance().getUserInfo().push.atmine = value;
                    } else if (PushSettingSecondaryActivity.this.mType == 1) {
                        FengApplication.getInstance().getUserInfo().push.commentpush = value;
                    } else if (PushSettingSecondaryActivity.this.mType == 3) {
                        FengApplication.getInstance().getUserInfo().push.userdirectmessagepush = value;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("user/update/", content, e);
                }
            }
        });
    }

    public String getLogCurrentPage() {
        switch (this.mType) {
            case 1:
                return "app_push_comment";
            case 2:
                return "app_push_at_mine";
            case 3:
                return "app_push_private_letter";
            default:
                return "-";
        }
    }
}
