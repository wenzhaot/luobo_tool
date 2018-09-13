package com.feng.car.activity;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.feng.car.R;
import com.feng.car.databinding.ActivityPushSecondaryBinding;
import com.feng.library.utils.SharedUtil;

public class SettingImageQualityActivity extends BaseActivity<ActivityPushSecondaryBinding> {
    public int setBaseContentView() {
        return R.layout.activity_push_secondary;
    }

    public void initView() {
        initNormalTitleBar(getString(R.string.image_quality_setting));
        ((ActivityPushSecondaryBinding) this.mBaseBinding).divider.setVisibility(8);
        ((ActivityPushSecondaryBinding) this.mBaseBinding).tvImageQualityAutoTip.setVisibility(0);
        ((ActivityPushSecondaryBinding) this.mBaseBinding).rbAll.setText(R.string.image_quality_auto);
        ((ActivityPushSecondaryBinding) this.mBaseBinding).rbFriend.setText(R.string.image_quality_HD);
        ((ActivityPushSecondaryBinding) this.mBaseBinding).rbClose.setText(R.string.image_quality_normal);
        initData();
        ((ActivityPushSecondaryBinding) this.mBaseBinding).rgSetting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_all /*2131624556*/:
                        SettingImageQualityActivity.this.saveImageQuality(0);
                        return;
                    case R.id.rb_friend /*2131624557*/:
                        SettingImageQualityActivity.this.saveImageQuality(1);
                        return;
                    case R.id.rb_close /*2131624566*/:
                        SettingImageQualityActivity.this.saveImageQuality(2);
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void initData() {
        switch (SharedUtil.getInt(this, "image_quality", 0)) {
            case 0:
                ((ActivityPushSecondaryBinding) this.mBaseBinding).rbAll.setChecked(true);
                return;
            case 1:
                ((ActivityPushSecondaryBinding) this.mBaseBinding).rbFriend.setChecked(true);
                return;
            case 2:
                ((ActivityPushSecondaryBinding) this.mBaseBinding).rbClose.setChecked(true);
                return;
            default:
                return;
        }
    }

    private void saveImageQuality(int imageQualityAuto) {
        SharedUtil.putInt(this, "image_quality", imageQualityAuto);
    }
}
