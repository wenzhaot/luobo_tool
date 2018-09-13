package com.feng.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityEditUserNextBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.HomeRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.operation.OperationCallback;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class EditUserInfoNextActivity extends BaseActivity<ActivityEditUserNextBinding> {
    private String mEditType;
    private String mLastText;
    Pattern mNickNamePattern = Pattern.compile("^[\\w\\u4e00-\\u9fa5\\-]+$");
    private UserInfo mUserInfo;

    static {
        StubApp.interface11(2396);
    }

    protected native void onCreate(Bundle bundle);

    private void setListener() {
        ((ActivityEditUserNextBinding) this.mBaseBinding).saveEditButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                String str = ((ActivityEditUserNextBinding) EditUserInfoNextActivity.this.mBaseBinding).etUserInfo.getText().toString().trim();
                if (EditUserInfoNextActivity.this.mEditType.equals(EditUserInfoActivity.EDIT_NICK)) {
                    Matcher matcher = EditUserInfoNextActivity.this.mNickNamePattern.matcher(str);
                    if (str.length() > 0 && !matcher.find()) {
                        EditUserInfoNextActivity.this.showThirdTypeToast((int) R.string.nick_name_hint);
                        return;
                    } else if (StringUtil.strLength(str) < 4 || StringUtil.strLength(str) > 16) {
                        EditUserInfoNextActivity.this.showThirdTypeToast((int) R.string.nickname_long_tips);
                        return;
                    }
                } else if (EditUserInfoNextActivity.this.mEditType.equals(EditUserInfoActivity.EDIT_SIGNATURE) && StringUtil.strLength(str) > 60) {
                    EditUserInfoNextActivity.this.showThirdTypeToast((int) R.string.sign_long_tips);
                    return;
                }
                if (EditUserInfoNextActivity.this.mLastText.equals(str)) {
                    EditUserInfoNextActivity.this.finish();
                } else {
                    EditUserInfoNextActivity.this.saveData(str);
                }
            }
        });
    }

    private void saveData(final String str) {
        Map<String, Object> map = new HashMap();
        if (this.mEditType.equals(EditUserInfoActivity.EDIT_NICK)) {
            map.put("name", str);
        } else if (this.mEditType.equals(EditUserInfoActivity.EDIT_SIGNATURE)) {
            map.put("signature", str);
        }
        String str1 = JsonUtil.toJson(map);
        map.clear();
        map.put("info", str1);
        this.mUserInfo.updateInfoOperation(map, new OperationCallback() {
            public void onNetworkError() {
                EditUserInfoNextActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                EditUserInfoNextActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        UserInfo userInfo = new UserInfo();
                        userInfo.id = FengApplication.getInstance().getUserInfo().id;
                        if (EditUserInfoNextActivity.this.mEditType.equals(EditUserInfoActivity.EDIT_NICK)) {
                            FengApplication.getInstance().getUserInfo().name.set(str);
                            userInfo.name.set(str);
                        } else if (EditUserInfoNextActivity.this.mEditType.equals(EditUserInfoActivity.EDIT_SIGNATURE)) {
                            if (str.equals("")) {
                                FengApplication.getInstance().getUserInfo().signature.set("这家伙很懒，什么都没留下...");
                                userInfo.signature.set("这家伙很懒，什么都没留下...");
                            } else {
                                FengApplication.getInstance().getUserInfo().signature.set(str);
                                userInfo.signature.set(str);
                            }
                        }
                        FengApplication.getInstance().saveUserInfo();
                        EventBus.getDefault().post(new HomeRefreshEvent(4));
                        Intent intent = new Intent();
                        intent.putExtra(EditUserInfoActivity.EDIT_RESULT, str);
                        EditUserInfoNextActivity.this.setResult(-1, intent);
                        UserInfoRefreshEvent event = new UserInfoRefreshEvent(userInfo);
                        event.type = 1;
                        EventBus.getDefault().post(event);
                        EditUserInfoNextActivity.this.showFirstTypeToast((int) R.string.save_success);
                        EditUserInfoNextActivity.this.finish();
                        return;
                    }
                    FengApplication.getInstance().checkCode("user/update/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("user/update/", content, e);
                }
            }
        });
    }

    public int setBaseContentView() {
        return R.layout.activity_edit_user_next;
    }

    public void getLocalIntentData() {
        this.mEditType = getIntent().getStringExtra(EditUserInfoActivity.EDIT_FROM);
    }

    public void initView() {
        if (this.mEditType != null) {
            this.mUserInfo = new UserInfo();
            if (this.mEditType.equals(EditUserInfoActivity.EDIT_NICK)) {
                initNormalTitleBar((int) R.string.edit_nick);
                ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setHint("2-8个字，支持中英文 数字 _和-");
                ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setSingleLine(true);
                ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setText((CharSequence) FengApplication.getInstance().getUserInfo().name.get());
                if (StringUtil.isEmpty((String) FengApplication.getInstance().getUserInfo().name.get())) {
                    ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setText((CharSequence) FengApplication.getInstance().getUserInfo().name.get());
                }
            } else if (this.mEditType.equals(EditUserInfoActivity.EDIT_SIGNATURE)) {
                initNormalTitleBar((int) R.string.edit_signature);
                LayoutParams p = (LayoutParams) ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.getLayoutParams();
                p.height = FengUtil.getScreenHeight(this) / 5;
                ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setGravity(8388659);
                ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setLayoutParams(p);
                ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setHint("请输入签名，不多于30字");
                if (((String) FengApplication.getInstance().getUserInfo().signature.get()).equals(getString(R.string.no_signature_tips))) {
                    ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setText("");
                } else {
                    ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setText((CharSequence) FengApplication.getInstance().getUserInfo().signature.get());
                }
            }
            ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.setSelection(((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.getText().toString().length());
            this.mLastText = ((ActivityEditUserNextBinding) this.mBaseBinding).etUserInfo.getText().toString().trim();
        }
    }

    public String getLogCurrentPage() {
        if (TextUtils.isEmpty(this.mEditType)) {
            return "-";
        }
        if (this.mEditType.equals(EditUserInfoActivity.EDIT_NICK)) {
            return "app_user_edit_nickname";
        }
        if (this.mEditType.equals(EditUserInfoActivity.EDIT_SIGNATURE)) {
            return "app_user_edit_signature";
        }
        return "-";
    }
}
