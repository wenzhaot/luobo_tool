package com.feng.car.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivitySettingAccountPhoneBinding;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.operation.ThirdLoginOperation;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SettingAccountPhoneActivity extends BaseActivity<ActivitySettingAccountPhoneBinding> {
    public static final int PHONE_BIND = 0;
    public static final int PHONE_CHAGE_FIRST = 1;
    public static final int PHONE_CHAGE_SECOND = 2;
    private final int CHANGE_TIME = 2;
    private final int RESET_TIME = 1;
    private final int START_TIME = 0;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SettingAccountPhoneActivity.this.mTime = 60;
                    SettingAccountPhoneActivity.this.startTime();
                    sendEmptyMessage(2);
                    return;
                case 1:
                    SettingAccountPhoneActivity.this.mTime = 60;
                    SettingAccountPhoneActivity.this.resetVerifyText(SettingAccountPhoneActivity.this.getString(R.string.send_code));
                    SettingAccountPhoneActivity.this.mHandler.removeCallbacksAndMessages(null);
                    return;
                case 2:
                    if (SettingAccountPhoneActivity.this.mTime <= 0) {
                        SettingAccountPhoneActivity.this.resetVerifyText(SettingAccountPhoneActivity.this.getString(R.string.again_send));
                        return;
                    }
                    ((ActivitySettingAccountPhoneBinding) SettingAccountPhoneActivity.this.mBaseBinding).tvPhoneSendCode.setText(SettingAccountPhoneActivity.this.getString(R.string.resendLeftTime, new Object[]{Integer.valueOf(SettingAccountPhoneActivity.this.mTime)}));
                    SettingAccountPhoneActivity.this.mTime = SettingAccountPhoneActivity.this.mTime - 1;
                    sendEmptyMessageDelayed(2, 1000);
                    return;
                default:
                    return;
            }
        }
    };
    private ThirdLoginOperation mOperation;
    private int mTime = 60;
    private int mType;

    private void startTime() {
        ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneSendCode.setEnabled(false);
        ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneSendCode.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneSendCode.setPadding(this.mResources.getDimensionPixelSize(R.dimen.default_10PX), this.mResources.getDimensionPixelSize(R.dimen.default_5PX), this.mResources.getDimensionPixelOffset(R.dimen.default_10PX), this.mResources.getDimensionPixelSize(R.dimen.default_5PX));
        if (this.mType == 1) {
            ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneReceiveCodeTip.setText(R.string.already_send_code);
        }
    }

    private void resetVerifyText(String strText) {
        ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneSendCode.setEnabled(true);
        ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneSendCode.setText(strText);
        ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneSendCode.setTextColor(ContextCompat.getColor(this, R.color.color_ffb80a));
        ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneSendCode.setPadding(this.mResources.getDimensionPixelSize(R.dimen.default_10PX), this.mResources.getDimensionPixelSize(R.dimen.default_5PX), this.mResources.getDimensionPixelOffset(R.dimen.default_10PX), this.mResources.getDimensionPixelSize(R.dimen.default_5PX));
        if (this.mType == 1) {
            ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneReceiveCodeTip.setText(R.string.receive_code_via_this_number);
        }
    }

    private void sendCode() {
        int i;
        String phoneNum = ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).etPhoneNumber.getText().toString().trim();
        if (this.mType == 1) {
            phoneNum = FengApplication.getInstance().getUserInfo().phonenumber;
        }
        ThirdLoginOperation thirdLoginOperation = this.mOperation;
        if (this.mType == 1) {
            i = 7;
        } else {
            i = 6;
        }
        thirdLoginOperation.sendCodeOperation(this, phoneNum, i, "", new SuccessFailCallback() {
            public void onStart() {
                SettingAccountPhoneActivity.this.mHandler.sendEmptyMessage(0);
            }

            public void onFail() {
                SettingAccountPhoneActivity.this.mHandler.sendEmptyMessage(1);
            }
        });
    }

    public int setBaseContentView() {
        return R.layout.activity_setting_account_phone;
    }

    public void getLocalIntentData() {
        this.mType = getIntent().getIntExtra("feng_type", 0);
    }

    public void initView() {
        this.mOperation = new ThirdLoginOperation();
        if (this.mType == 0) {
            initNormalTitleBar((int) R.string.phone_bind);
        } else {
            initNormalTitleBar((int) R.string.phone_change);
        }
        if (this.mType == 1) {
            ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).etPhoneNumber.setVisibility(8);
            ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneReceiveCodeTip.setVisibility(0);
            ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneNumber.setVisibility(0);
            String str = FengApplication.getInstance().getUserInfo().phonenumber;
            ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneNumber.setText(str.replace(str.substring(3, 7), "****"));
            ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvComplete.setText(R.string.next);
        }
        ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvPhoneSendCode.setOnClickListener(this);
        ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).tvComplete.setOnClickListener(this);
    }

    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.tv_phone_sendCode /*2131624629*/:
                sendCode();
                return;
            case R.id.tv_complete /*2131624630*/:
                String phoneNum = ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).etPhoneNumber.getText().toString().trim();
                String code = ((ActivitySettingAccountPhoneBinding) this.mBaseBinding).etPhoneCode.getText().toString().trim();
                if (this.mType == 0) {
                    this.mOperation.updatePhoneOperation(this, phoneNum, "", code, new SuccessFailCallback() {
                        public void onSuccess() {
                            SettingAccountPhoneActivity.this.finish();
                        }
                    });
                    return;
                } else if (this.mType == 2) {
                    this.mOperation.updatePhoneOperation(this, phoneNum, FengApplication.getInstance().getUserInfo().phonenumber, code, new SuccessFailCallback() {
                        public void onSuccess() {
                            EventBus.getDefault().post(new ClosePageEvent());
                        }
                    });
                    return;
                } else if (this.mType == 1) {
                    this.mOperation.checkOldPhoneOperation(this, FengApplication.getInstance().getUserInfo().phonenumber, code, new SuccessFailCallback() {
                        public void onSuccess() {
                            Intent intent = new Intent(SettingAccountPhoneActivity.this, SettingAccountPhoneActivity.class);
                            intent.putExtra("feng_type", 2);
                            SettingAccountPhoneActivity.this.startActivity(intent);
                        }
                    });
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public String getLogCurrentPage() {
        if (this.mType == 0) {
            return "app_account_binding_mobile";
        }
        return "app_account_change_mobile";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClosePageEvent event) {
        finish();
    }
}
