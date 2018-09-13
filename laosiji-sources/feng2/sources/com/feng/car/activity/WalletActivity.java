package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityWalletBinding;
import com.feng.car.databinding.WalletWithdrawCashDialogBinding;
import com.feng.car.databinding.WalletWithdrawVerificationDialogBinding;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class WalletActivity extends BaseActivity<ActivityWalletBinding> {
    private Double mAwardvalue;
    private Dialog mPickupDialog;
    private Dialog mVerificationDialog;
    private WalletWithdrawCashDialogBinding mWalletWithdrawCashDialog;
    private WalletWithdrawVerificationDialogBinding mWalletWithdrawVerificationDialogBinding;

    public int setBaseContentView() {
        return R.layout.activity_wallet;
    }

    public void initView() {
        hideDefaultTitleBar();
        getData();
        ((ActivityWalletBinding) this.mBaseBinding).backImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WalletActivity.this.finish();
            }
        });
        ((ActivityWalletBinding) this.mBaseBinding).detailed.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WalletActivity.this.startActivity(new Intent(WalletActivity.this, WalletDetailActivity.class));
            }
        });
        ((ActivityWalletBinding) this.mBaseBinding).disclaimer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(WalletActivity.this, WebActivity.class);
                intent.putExtra("url", HttpConstant.NEW_USER_AGREEMENT);
                WalletActivity.this.startActivity(intent);
            }
        });
        ((ActivityWalletBinding) this.mBaseBinding).withdrawCashButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WalletActivity.this.mAwardvalue.doubleValue() != 0.0d) {
                    if (TextUtils.isEmpty(FengApplication.getInstance().getUserInfo().phonenumber)) {
                        WalletActivity.this.showVerificationDialog();
                    } else {
                        WalletActivity.this.showPickupDialog();
                    }
                }
            }
        });
    }

    private void showPickupDialog() {
        if (this.mPickupDialog == null) {
            initPickupDialog();
        }
        this.mPickupDialog.show();
    }

    private void getData() {
        FengApplication.getInstance().httpRequest("hq/home/", new HashMap(), new OkHttpResponseCallback() {
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
                        WalletActivity.this.mAwardvalue = Double.valueOf(jsonResult.getJSONObject("body").getDouble("awardvalue"));
                        ((ActivityWalletBinding) WalletActivity.this.mBaseBinding).balance.setText(String.valueOf(WalletActivity.this.mAwardvalue));
                        if (WalletActivity.this.mAwardvalue.doubleValue() <= 0.0d) {
                            ((ActivityWalletBinding) WalletActivity.this.mBaseBinding).withdrawCashButton.setEnabled(false);
                            ((ActivityWalletBinding) WalletActivity.this.mBaseBinding).withdrawCashButton.getBackground().mutate().setAlpha(150);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initPickupDialog() {
        this.mWalletWithdrawCashDialog = WalletWithdrawCashDialogBinding.inflate(this.mInflater, null, true);
        this.mWalletWithdrawCashDialog.userName.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = s.toString();
                String alipay = WalletActivity.this.mWalletWithdrawCashDialog.aliAccount.getText().toString();
                if (StringUtil.isEmpty(name) || StringUtil.isEmpty(alipay) || !WalletActivity.this.isAliPay(alipay)) {
                    WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setEnabled(false);
                    WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setBackgroundResource(R.drawable.withdraw_cash_unavailable);
                    return;
                }
                WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setEnabled(true);
                WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setBackgroundResource(R.drawable.withdraw_cash_selector);
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.mWalletWithdrawCashDialog.aliAccount.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = WalletActivity.this.mWalletWithdrawCashDialog.userName.getText().toString();
                String alipay = s.toString();
                if (WalletActivity.this.isAliPay(alipay)) {
                    WalletActivity.this.mWalletWithdrawCashDialog.aliAccount.setBackgroundResource(R.drawable.withdrwals_edittext_normal);
                    WalletActivity.this.mWalletWithdrawCashDialog.tvIsAliPayTip.setVisibility(0);
                    WalletActivity.this.mWalletWithdrawCashDialog.tvNotAliPayTip.setVisibility(8);
                    if (StringUtil.isEmpty(name) || StringUtil.isEmpty(alipay)) {
                        WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setEnabled(false);
                        WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setBackgroundResource(R.drawable.withdraw_cash_unavailable);
                        return;
                    }
                    WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setEnabled(true);
                    WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setBackgroundResource(R.drawable.withdraw_cash_selector);
                    return;
                }
                WalletActivity.this.mWalletWithdrawCashDialog.tvIsAliPayTip.setVisibility(8);
                WalletActivity.this.mWalletWithdrawCashDialog.tvNotAliPayTip.setVisibility(0);
                WalletActivity.this.mWalletWithdrawCashDialog.aliAccount.setBackgroundResource(R.drawable.withdrwals_edittext_error);
                WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setEnabled(false);
                WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.setBackgroundResource(R.drawable.withdraw_cash_unavailable);
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.mWalletWithdrawCashDialog.withDrawComit.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WalletActivity.this.mWalletWithdrawCashDialog.withDrawComit.isEnabled()) {
                    WalletActivity.this.pickUpMoney();
                }
            }
        });
        this.mWalletWithdrawCashDialog.ivClose.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WalletActivity.this.mPickupDialog.dismiss();
            }
        });
        this.mPickupDialog = new Dialog(this, R.style.ArticleShareDialog);
        this.mPickupDialog.setCanceledOnTouchOutside(true);
        this.mPickupDialog.setCancelable(true);
        Window window = this.mPickupDialog.getWindow();
        window.setGravity(17);
        window.setWindowAnimations(R.style.shareAnimation);
        window.setContentView(this.mWalletWithdrawCashDialog.getRoot());
        window.setLayout(FengUtil.getScreenWidth(this) - (this.mResources.getDimensionPixelSize(R.dimen.default_20PX) * 2), (FengUtil.getScreenHeight(this) * 3) / 4);
    }

    private boolean isAliPay(String str) {
        if (StringUtil.isEmail(str).booleanValue()) {
            return true;
        }
        boolean flag = str.length() == 11;
        boolean flag1 = StringUtil.isNumber(str).booleanValue();
        if (flag && flag1) {
            return true;
        }
        return false;
    }

    private void pickUpMoney() {
        if (!TextUtils.isEmpty(this.mWalletWithdrawCashDialog.userName.getText().toString()) && !TextUtils.isEmpty(this.mWalletWithdrawCashDialog.aliAccount.getText().toString())) {
            Map<String, Object> map = new HashMap();
            map.put("realname", this.mWalletWithdrawCashDialog.userName.getText().toString());
            map.put("alipayid", this.mWalletWithdrawCashDialog.aliAccount.getText().toString());
            FengApplication.getInstance().httpRequest("hq/applytransfer/", map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    WalletActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onStart() {
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    WalletActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        int code = new JSONObject(content).getInt("code");
                        if (code == 1) {
                            WalletActivity.this.showSuccess();
                        } else if (code == -85) {
                            WalletActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        } else if (code == -86) {
                            WalletActivity.this.showThirdTypeToast((int) R.string.balance_not_enough);
                        } else {
                            WalletActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void showSuccess() {
        ((ActivityWalletBinding) this.mBaseBinding).balance.setText("0.00");
        this.mPickupDialog.dismiss();
        if (this.mWalletWithdrawCashDialog == null) {
            this.mWalletWithdrawCashDialog = WalletWithdrawCashDialogBinding.inflate(this.mInflater, null, true);
        }
        this.mWalletWithdrawCashDialog.tvText.setVisibility(8);
        this.mWalletWithdrawCashDialog.userName.setVisibility(8);
        this.mWalletWithdrawCashDialog.aliAccount.setVisibility(8);
        this.mWalletWithdrawCashDialog.tvIsAliPayTip.setVisibility(8);
        this.mWalletWithdrawCashDialog.withDrawComit.setVisibility(8);
        this.mWalletWithdrawCashDialog.tvNotAliPayTip.setVisibility(8);
        this.mWalletWithdrawCashDialog.successLine.setVisibility(0);
        this.mWalletWithdrawCashDialog.bottomButton.setVisibility(0);
        this.mPickupDialog.show();
        this.mWalletWithdrawCashDialog.bottomButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WalletActivity.this.mPickupDialog.dismiss();
            }
        });
    }

    public void finish() {
        super.finish();
        if (this.mPickupDialog != null) {
            this.mPickupDialog.dismiss();
        }
        if (this.mVerificationDialog != null) {
            this.mVerificationDialog.dismiss();
        }
    }

    private void showVerificationDialog() {
        if (this.mVerificationDialog == null) {
            initVerificationDialog();
        }
        this.mVerificationDialog.show();
    }

    private void initVerificationDialog() {
        this.mWalletWithdrawVerificationDialogBinding = WalletWithdrawVerificationDialogBinding.inflate(LayoutInflater.from(this));
        this.mWalletWithdrawVerificationDialogBinding.verificationText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WalletActivity.this.startActivity(new Intent(WalletActivity.this, SettingAccountSecurityActivity.class));
                WalletActivity.this.mVerificationDialog.dismiss();
            }
        });
        this.mVerificationDialog = new Dialog(this, R.style.ArticleShareDialog);
        this.mVerificationDialog.setCanceledOnTouchOutside(true);
        this.mVerificationDialog.setCancelable(true);
        Window window = this.mVerificationDialog.getWindow();
        window.setGravity(17);
        window.setWindowAnimations(R.style.shareAnimation);
        window.setContentView(this.mWalletWithdrawVerificationDialogBinding.getRoot());
        window.setLayout(FengUtil.getScreenWidth(this) - (this.mResources.getDimensionPixelSize(R.dimen.default_20PX) * 2), -2);
    }
}
