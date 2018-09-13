package com.feng.car.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.R;
import com.feng.car.databinding.ActivityUploadIdcardBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.dealer.ShopRegisterInfo;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.event.IDCardUploadFinishEvent;
import com.feng.car.event.ImageOrVideoPathEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.UploadSingleImage;
import com.feng.car.utils.UploadSingleImage$OnUploadResult;
import com.feng.library.utils.SharedUtil;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UpLoadIDCardActivity extends BaseActivity<ActivityUploadIdcardBinding> {
    private Map<Integer, PostEdit> mImageMap = new HashMap();
    private int mIndex;
    private ShopRegisterInfo mShopRegisterInfo;
    private UploadSingleImage mUploadImage;

    public int setBaseContentView() {
        return R.layout.activity_upload_idcard;
    }

    public void initView() {
        initNormalTitleBar("上传身份证照片");
        this.mUploadImage = new UploadSingleImage(this, new UploadSingleImage$OnUploadResult() {
            public void onStart() {
            }

            public void onResult(HashMap<String, String> imageMap) {
                UpLoadIDCardActivity.this.initImage(imageMap);
            }

            public void onFailure() {
            }
        });
        String json = SharedUtil.getString(this, "register_store_json");
        if (!TextUtils.isEmpty(json)) {
            this.mShopRegisterInfo = (ShopRegisterInfo) JsonUtil.fromJson(json, ShopRegisterInfo.class);
            restoreImage();
        }
        ((ActivityUploadIdcardBinding) this.mBaseBinding).image1.setOnClickListener(this);
        ((ActivityUploadIdcardBinding) this.mBaseBinding).image2.setOnClickListener(this);
        ((ActivityUploadIdcardBinding) this.mBaseBinding).image3.setOnClickListener(this);
        ((ActivityUploadIdcardBinding) this.mBaseBinding).btFinish.setOnClickListener(this);
        ((ActivityUploadIdcardBinding) this.mBaseBinding).btFinish.setEnabled(this.mImageMap.size() > 2);
    }

    private void restoreImage() {
        if (this.mShopRegisterInfo != null) {
            if (!(this.mShopRegisterInfo.idcardimagejson1 == null || TextUtils.isEmpty(this.mShopRegisterInfo.idcardimagejson1.srcUrl))) {
                ((ActivityUploadIdcardBinding) this.mBaseBinding).image1.setAutoImageURI(Uri.parse(getImageUrl(this.mShopRegisterInfo.idcardimagejson1)));
                this.mImageMap.put(Integer.valueOf(1), this.mShopRegisterInfo.idcardimagejson1);
            }
            if (!(this.mShopRegisterInfo.idcardimagejson2 == null || TextUtils.isEmpty(this.mShopRegisterInfo.idcardimagejson2.srcUrl))) {
                ((ActivityUploadIdcardBinding) this.mBaseBinding).image2.setAutoImageURI(Uri.parse(getImageUrl(this.mShopRegisterInfo.idcardimagejson2)));
                this.mImageMap.put(Integer.valueOf(2), this.mShopRegisterInfo.idcardimagejson2);
            }
            if (this.mShopRegisterInfo.idcardimagejson3 != null && !TextUtils.isEmpty(this.mShopRegisterInfo.idcardimagejson3.srcUrl)) {
                ((ActivityUploadIdcardBinding) this.mBaseBinding).image3.setAutoImageURI(Uri.parse(getImageUrl(this.mShopRegisterInfo.idcardimagejson3)));
                this.mImageMap.put(Integer.valueOf(3), this.mShopRegisterInfo.idcardimagejson3);
            }
        }
    }

    private void initImage(HashMap<String, String> map) {
        PostEdit edit = new PostEdit(2, "");
        edit.setHeight((String) map.get("height"));
        edit.setWidth((String) map.get("width"));
        edit.srcUrl = HttpConstant.QINIUIMAGEBASEPATH + ((String) map.get("hash"));
        edit.setMime((String) map.get("mime"));
        edit.setHash((String) map.get("hash"));
        edit.setUrl((String) map.get("url"));
        edit.setSize((String) map.get("size"));
        loadImage(getImageUrl(edit));
        this.mImageMap.put(Integer.valueOf(this.mIndex), edit);
        ((ActivityUploadIdcardBinding) this.mBaseBinding).btFinish.setEnabled(this.mImageMap.size() > 2);
    }

    private String getImageUrl(PostEdit edit) {
        try {
            ImageInfo info = new ImageInfo();
            info.url = edit.srcUrl;
            info.height = Integer.parseInt(edit.getHeight());
            info.width = Integer.parseInt(edit.getWidth());
            info.setMimetype(edit.getMime());
            return FengUtil.getUniformScaleUrl(info, 400);
        } catch (Exception e) {
            return edit.srcUrl;
        }
    }

    public void onSingleClick(View v) {
        super.onSingleClick(v);
        switch (v.getId()) {
            case R.id.bt_finish /*2131624692*/:
                finishUpLoadIDCard();
                return;
            case R.id.image1 /*2131624693*/:
                this.mIndex = 1;
                jumpToSelectPhoto();
                return;
            case R.id.image2 /*2131624694*/:
                this.mIndex = 2;
                jumpToSelectPhoto();
                return;
            case R.id.image3 /*2131624695*/:
                this.mIndex = 3;
                jumpToSelectPhoto();
                return;
            default:
                return;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveImageOrVideoPath(ImageOrVideoPathEvent pathEvent) {
        if (pathEvent.type == 30 && pathEvent.list != null) {
            this.mUploadImage.startUpload(((UploadQiNiuLocalPathInfo) pathEvent.list.get(0)).path);
        }
    }

    private void loadImage(String path) {
        if (this.mIndex == 1) {
            ((ActivityUploadIdcardBinding) this.mBaseBinding).image1.setAutoImageURI(Uri.parse(path));
        } else if (this.mIndex == 2) {
            ((ActivityUploadIdcardBinding) this.mBaseBinding).image2.setAutoImageURI(Uri.parse(path));
        } else if (this.mIndex == 3) {
            ((ActivityUploadIdcardBinding) this.mBaseBinding).image3.setAutoImageURI(Uri.parse(path));
        }
    }

    private void jumpToSelectPhoto() {
        Intent intent = new Intent(this, SelectPhotoActivity.class);
        intent.putExtra("feng_type", 4);
        startActivity(intent);
    }

    private void finishUpLoadIDCard() {
        EventBus.getDefault().post(new IDCardUploadFinishEvent(this.mImageMap));
        finish();
    }
}
