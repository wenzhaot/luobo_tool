package com.feng.car.activity;

import android.os.Bundle;
import android.view.View;
import com.feng.car.R;
import com.feng.car.databinding.ActivityCameraPreviewBinding;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.event.ImageOrVideoPathEvent;
import com.feng.car.utils.FengConstant;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class CameraPreviewActivity extends BaseActivity<ActivityCameraPreviewBinding> {
    public static final String PATH = "path";
    public static final String POST_LOCAL_VIDEO = "post_local_video";
    public static final String TYPE_KEY = "type_key";
    public static final int TYPE_PIC = 0;
    public static final int TYPE_VIDEO = 1;
    private boolean mIsPostLocal = false;
    private String mPath;
    private int mType = 0;

    static {
        StubApp.interface11(2235);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.activity_camera_preview;
    }

    public void initView() {
        ((ActivityCameraPreviewBinding) this.mBaseBinding).cancel.setOnClickListener(this);
        ((ActivityCameraPreviewBinding) this.mBaseBinding).confirm.setOnClickListener(this);
    }

    public void onSingleClick(View v) {
        switch (v.getId()) {
            case 2131624007:
                ((ActivityCameraPreviewBinding) this.mBaseBinding).videoView.start();
                ((ActivityCameraPreviewBinding) this.mBaseBinding).start.setVisibility(8);
                return;
            case R.id.cancel /*2131624291*/:
                finish();
                return;
            case R.id.confirm /*2131624294*/:
                if (!this.mIsPostLocal) {
                    List<UploadQiNiuLocalPathInfo> list = new ArrayList();
                    if (this.mType == 0) {
                        FengConstant.UPLOAD_IMAGE_COUNT++;
                        list.add(new UploadQiNiuLocalPathInfo(2, this.mPath));
                        EventBus.getDefault().post(new ImageOrVideoPathEvent(2, list));
                    } else if (this.mType == 1) {
                        FengConstant.UPLOAD_VIDEO_COUNT++;
                        list.add(new UploadQiNiuLocalPathInfo(3, this.mPath));
                        EventBus.getDefault().post(new ImageOrVideoPathEvent(3, list));
                    }
                    EventBus.getDefault().post(new ClosePageEvent(1));
                    finish();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
