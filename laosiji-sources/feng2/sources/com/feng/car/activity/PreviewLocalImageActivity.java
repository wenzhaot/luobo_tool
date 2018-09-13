package com.feng.car.activity;

import android.content.Intent;
import android.net.Uri;
import com.feng.car.R;
import com.feng.car.databinding.ActivityPreviewLocalimageBinding;
import com.feng.library.utils.StringUtil;

public class PreviewLocalImageActivity extends BaseActivity<ActivityPreviewLocalimageBinding> {
    public int setBaseContentView() {
        return R.layout.activity_preview_localimage;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.preview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (StringUtil.isEmpty(url)) {
            showSecondTypeToast((int) R.string.get_image_path_failed);
            return;
        }
        ((ActivityPreviewLocalimageBinding) this.mBaseBinding).image.setImageURI(Uri.parse("file://" + url));
        ((ActivityPreviewLocalimageBinding) this.mBaseBinding).image.update(intent.getIntExtra("width", 0), intent.getIntExtra("height", 0));
    }
}
