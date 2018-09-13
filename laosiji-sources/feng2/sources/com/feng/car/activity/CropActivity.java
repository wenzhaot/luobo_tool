package com.feng.car.activity;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.feng.car.R;
import com.feng.car.databinding.ActivityCropBinding;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.event.ImageOrVideoPathEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.ImageUtil;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class CropActivity extends BaseActivity<ActivityCropBinding> {
    public static final int COVER_TYPE = 1;
    public static final int DEALERS_ID_CARD_TYPE = 4;
    public static final int DEALERS_TYPE = 3;
    public static final int HEAD_TYPE = 2;
    public static final String TYPE_KEY = "TYPE_KEY";
    private Bitmap crop_bitmap = null;
    private int mType = 2;
    private Bitmap original_bitmap = null;
    private String original_path = null;

    public int setBaseContentView() {
        return R.layout.activity_crop;
    }

    public void initView() {
        closeSwip();
        initNormalTitleBar((int) R.string.crop_image);
        this.original_path = getIntent().getStringExtra("IMAGE_PATH_KEY");
        if (StringUtil.isEmpty(this.original_path)) {
            showSecondTypeToast((int) R.string.get_image_path_failed);
            return;
        }
        this.original_bitmap = FengUtil.loadBitmap(this.original_path);
        if (this.original_bitmap == null) {
            showSecondTypeToast((int) R.string.get_image_failed);
            return;
        }
        int height = this.original_bitmap.getHeight();
        int width = this.original_bitmap.getWidth();
        if (height < m_AppUI.MSG_APP_SAVESCREEN || height < width * 5) {
            ((ActivityCropBinding) this.mBaseBinding).clipImageLayout.setImageBitmap(this.original_bitmap);
            this.mType = getIntent().getIntExtra(TYPE_KEY, 2);
            ((ActivityCropBinding) this.mBaseBinding).clipImageLayout.setIsCropHead(this.mType);
            if (this.mType == 1) {
                ((ActivityCropBinding) this.mBaseBinding).getRoot().setBackgroundResource(R.drawable.bg_white);
                initTitleBarRightTextWithBg(R.string.affirm, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        CropActivity.this.cropImage();
                    }
                });
                this.mRootBinding.titleLine.tvRightText.setSelected(true);
                return;
            }
            ((ActivityCropBinding) this.mBaseBinding).getRoot().setBackgroundResource(R.color.color_000000);
            initTitleBarRightText(R.string.use, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    CropActivity.this.cropImage();
                }
            });
            return;
        }
        new Builder(this).setMessage("抱歉，不能选择高度超过4000px或者高度大于宽度5倍的图片").setPositiveButton("确认", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                CropActivity.this.finish();
            }
        }).create().show();
    }

    public void finish() {
        super.finish();
        if (this.crop_bitmap != null) {
            this.crop_bitmap.recycle();
        }
        if (this.original_bitmap != null) {
            this.original_bitmap.recycle();
        }
    }

    private void cropImage() {
        try {
            File file;
            this.crop_bitmap = ((ActivityCropBinding) this.mBaseBinding).clipImageLayout.clip();
            if (this.mType == 2) {
                file = FengUtil.saveBitmapToFile(ImageUtil.getScaleBitmap(this.crop_bitmap, 640, 640), FengConstant.IMAGE_FILE_PATH, System.currentTimeMillis() + ".jpg");
            } else if (this.mType == 1) {
                if (this.original_bitmap.getWidth() > 1600) {
                    file = FengUtil.saveBitmapToFile(ImageUtil.getScaleBitmap(this.crop_bitmap, 1600, 900), FengConstant.IMAGE_FILE_PATH, System.currentTimeMillis() + ".jpg");
                } else {
                    file = FengUtil.saveBitmapToFile(this.crop_bitmap, FengConstant.IMAGE_FILE_PATH, System.currentTimeMillis() + ".jpg");
                }
            } else if (this.original_bitmap.getWidth() > 1600) {
                file = FengUtil.saveBitmapToFile(ImageUtil.getScaleBitmap(this.crop_bitmap, 1600, 1200), FengConstant.IMAGE_FILE_PATH, System.currentTimeMillis() + ".jpg");
            } else {
                file = FengUtil.saveBitmapToFile(this.crop_bitmap, FengConstant.IMAGE_FILE_PATH, System.currentTimeMillis() + ".jpg");
            }
            List<UploadQiNiuLocalPathInfo> list;
            if (this.mType == 2) {
                Intent intent = new Intent();
                intent.putExtra("IMAGE_PATH_KEY", file.getAbsolutePath());
                setResult(-1, intent);
            } else if (this.mType == 1) {
                list = new ArrayList();
                list.add(new UploadQiNiuLocalPathInfo(10, file.getAbsolutePath()));
                EventBus.getDefault().post(new ImageOrVideoPathEvent(10, list));
                EventBus.getDefault().post(new ClosePageEvent());
            } else if (this.mType == 3) {
                list = new ArrayList();
                list.add(new UploadQiNiuLocalPathInfo(20, file.getAbsolutePath()));
                EventBus.getDefault().post(new ImageOrVideoPathEvent(20, list));
                EventBus.getDefault().post(new ClosePageEvent());
            } else if (this.mType == 4) {
                list = new ArrayList();
                list.add(new UploadQiNiuLocalPathInfo(30, file.getAbsolutePath()));
                EventBus.getDefault().post(new ImageOrVideoPathEvent(30, list));
                EventBus.getDefault().post(new ClosePageEvent());
            }
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            showSecondTypeToast((int) R.string.crop_image_failed);
        }
    }
}
