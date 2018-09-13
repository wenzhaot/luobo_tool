package com.feng.car.activity;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityAboutUsBinding;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.ToastUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import org.json.JSONObject;

public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding> {
    private int m10;
    private int mLogoTime = 0;
    private int mVersionTime = 0;

    public int setBaseContentView() {
        return R.layout.activity_about_us;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.about_us);
        this.m10 = getResources().getDimensionPixelSize(R.dimen.default_10PX);
        if (FengUtil.getVersion(this) != null) {
            ((ActivityAboutUsBinding) this.mBaseBinding).tvAboutVersion.setText(getString(R.string.versionText, new Object[]{FengUtil.getVersion(this)}));
        }
        if (SharedUtil.getInt(this, "UPDATE_CODE", 0) > FengUtil.getAPPVerionCode(this)) {
            try {
                final JSONObject json = new JSONObject(SharedUtil.getString(this, "update_version_json"));
                ((ActivityAboutUsBinding) this.mBaseBinding).tvVersion.setBackgroundResource(R.drawable.bg_round_e12c2c_10dp);
                ((ActivityAboutUsBinding) this.mBaseBinding).tvVersion.setPadding(this.m10, 0, this.m10, 0);
                ((ActivityAboutUsBinding) this.mBaseBinding).tvVersion.setText(json.getString("versionname"));
                ((ActivityAboutUsBinding) this.mBaseBinding).tvVersion.setTextColor(ContextCompat.getColor(this, R.color.color_ffffffff));
                ((ActivityAboutUsBinding) this.mBaseBinding).tvVersion.setTextAppearance(this, R.style.textsize_14);
                ((ActivityAboutUsBinding) this.mBaseBinding).rlVersionUpdate.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        FengUtil.checkUpdate(AboutUsActivity.this, json, true, false);
                        FengApplication.getInstance().upLoadLog(true, json.toString());
                    }
                });
                ((ActivityAboutUsBinding) this.mBaseBinding).tvVersion.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        FengUtil.checkUpdate(AboutUsActivity.this, json, true, false);
                        FengApplication.getInstance().upLoadLog(true, json.toString());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ((ActivityAboutUsBinding) this.mBaseBinding).ivAboutLogo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutUsActivity.this.mLogoTime = AboutUsActivity.this.mLogoTime + 1;
                try {
                    if (AboutUsActivity.this.mLogoTime == 5) {
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).etAboutHttp.setVisibility(0);
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).btAboutSave.setVisibility(0);
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).ivAboutLogo.setVisibility(4);
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).tvChannel.setVisibility(0);
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).sliderBtnTest.setVisibility(0);
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).tvTestText.setVisibility(0);
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).btClearYk.setVisibility(0);
                        String msg = AboutUsActivity.this.getPackageManager().getApplicationInfo(AboutUsActivity.this.getPackageName(), 128).metaData.getString("UMENG_CHANNEL").trim();
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).tvChannel.setText(AboutUsActivity.this.getString(R.string.channel, new Object[]{msg}));
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).tvAboutAppName.setText(AboutUsActivity.this.getString(R.string.currentAPI, new Object[]{HttpConstant.YWFHTTPURL}));
                        AboutUsActivity.this.mLogoTime = 0;
                        boolean isDebugOpen = SharedUtil.getBoolean(AboutUsActivity.this, "setting_test_open", false);
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).sliderBtnTest.setImageResource(R.drawable.btn_bottom, R.drawable.btn_frame, R.drawable.btn_mask, R.drawable.btn_unpressed, R.drawable.btn_pressed);
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).sliderBtnTest.setChecked(isDebugOpen);
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).sliderBtnTest.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                                if (arg1) {
                                    SharedUtil.putBoolean(AboutUsActivity.this, "setting_test_open", true);
                                    FengApplication.getInstance().setIsOpenTest(true);
                                    return;
                                }
                                SharedUtil.putBoolean(AboutUsActivity.this, "setting_test_open", false);
                                FengApplication.getInstance().setIsOpenTest(false);
                            }
                        });
                        ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).btClearYk.setOnClickListener(new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                SharedUtil.putString(AboutUsActivity.this, "usertouristtoken", "");
                                SharedUtil.putString(AboutUsActivity.this, "new_user_flag", "");
                                ToastUtil.showToast(AboutUsActivity.this, "清除成功");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (FengApplication.getInstance().isLoginUser()) {
            ((ActivityAboutUsBinding) this.mBaseBinding).llVersion.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    AboutUsActivity.this.mVersionTime = AboutUsActivity.this.mVersionTime + 1;
                    try {
                        if (AboutUsActivity.this.mVersionTime == 5) {
                            ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).llVersion.setVisibility(8);
                            ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).sliderBtnAttention.setVisibility(0);
                            ((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).sliderBtnAttention.setChecked(SharedUtil.getBoolean(AboutUsActivity.this, "sns_all_list", false));
                            AboutUsActivity.this.mVersionTime = 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ((ActivityAboutUsBinding) this.mBaseBinding).sliderBtnAttention.setImageResource(R.drawable.btn_bottom, R.drawable.btn_frame, R.drawable.btn_mask, R.drawable.btn_unpressed, R.drawable.btn_pressed);
            ((ActivityAboutUsBinding) this.mBaseBinding).sliderBtnAttention.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    if (arg1) {
                        SharedUtil.putBoolean(AboutUsActivity.this, "sns_all_list", true);
                    } else {
                        SharedUtil.putBoolean(AboutUsActivity.this, "sns_all_list", false);
                    }
                }
            });
        }
        ((ActivityAboutUsBinding) this.mBaseBinding).btAboutSave.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FengApplication.getInstance().setApiUrl(((ActivityAboutUsBinding) AboutUsActivity.this.mBaseBinding).etAboutHttp.getText().toString().trim());
                AboutUsActivity.this.finish();
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 60000) {
            try {
                if (grantResults.length != 1 || permissions.length != 1) {
                    return;
                }
                if ((!permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE") || grantResults[0] != 0) && grantResults[0] != 0) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                        showThirdTypeToast("请打开存储权限！");
                    } else {
                        showThirdTypeToast("请打开存储权限！");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                permissionSuccess();
            }
        }
    }
}
