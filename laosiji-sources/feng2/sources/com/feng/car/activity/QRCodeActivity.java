package com.feng.car.activity;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityQrcodBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.event.ScanningLoginEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.view.CommonDialog;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CaptureFragment.CameraInitCallBack;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class QRCodeActivity extends BaseActivity<ActivityQrcodBinding> {
    AnalyzeCallback analyzeCallback = new AnalyzeCallback() {
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            if (StringUtil.isEmpty(result)) {
                QRCodeActivity.this.showThirdTypeToast("扫描失败");
                QRCodeActivity.this.restartPreview();
            } else if (result.contains("QRCODE_KEY_") && result.indexOf("QRCODE_KEY_") == 0) {
                Intent intent = new Intent(QRCodeActivity.this, ScanningLoginPCActivity.class);
                intent.putExtra("code", result);
                QRCodeActivity.this.startActivity(intent);
                new Message().what = R.id.restart_preview;
                QRCodeActivity.this.restartPreview();
            } else {
                new Builder(QRCodeActivity.this).setTitle("扫描失败").setMessage("当前仅支持老司机PC登录二维码").setPositiveButton(R.string.confirm, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        QRCodeActivity.this.restartPreview();
                    }
                }).create().show();
            }
        }

        public void onAnalyzeFailed() {
            QRCodeActivity.this.showThirdTypeToast("扫描失败");
            QRCodeActivity.this.restartPreview();
        }
    };
    private CaptureFragment captureFragment;
    private final String mVerification = "QRCODE_KEY_";

    public int setBaseContentView() {
        return R.layout.activity_qrcod;
    }

    public void initView() {
        int uiFlags;
        if (VERSION.SDK_INT >= 19) {
            uiFlags = 1798 | 4096;
        } else {
            uiFlags = 1798 | 1;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        initNormalTitleBar((int) R.string.scanning);
        this.mRootBinding.titleLine.getRoot().setBackgroundResource(R.color.color_000000);
        this.mRootBinding.titleLine.title.setTextColor(this.mResources.getColor(R.color.color_ffffff));
        this.mRootBinding.titleLine.ivTitleLeft.setImageResource(R.drawable.icon_back_wh);
        this.mRootBinding.titleLine.divier.setVisibility(8);
        checkPermission();
    }

    public void checkPermission() {
        if (VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.CAMERA") != 0) {
                requestPermissions(new String[]{"android.permission.CAMERA"}, 50003);
                return;
            }
            permissionSuccess();
        } else if (cameraIsCanUse()) {
            permissionSuccess();
        } else {
            showSecondTypeToast((int) R.string.no_low_open_camear_permissions);
            finish();
        }
    }

    public boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            mCamera.setParameters(mCamera.getParameters());
        } catch (Exception e) {
            isCanUse = false;
        }
        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return isCanUse;
    }

    private void init() {
        ((ActivityQrcodBinding) this.mBaseBinding).parent.setBackgroundResource(R.color.transparent);
        this.captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(this.captureFragment, R.layout.qrcode_scanning_layout);
        this.captureFragment.setAnalyzeCallback(this.analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, this.captureFragment).commitAllowingStateLoss();
        this.captureFragment.setCameraInitCallBack(new CameraInitCallBack() {
            public void callBack(Exception e) {
                if (e != null) {
                    QRCodeActivity.this.showSecondTypeToast((int) R.string.no_low_open_camear_permissions);
                    QRCodeActivity.this.finish();
                }
            }
        });
    }

    private void restartPreview() {
        Message message = new Message();
        message.what = R.id.restart_preview;
        this.captureFragment.handler.handleMessage(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ScanningLoginEvent event) {
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50003) {
            checkPermission();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50003) {
            try {
                if (permissions[0].equals("android.permission.CAMERA") && grantResults[0] == 0) {
                    permissionSuccess();
                } else if (grantResults[0] == 0) {
                    permissionSuccess();
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    showPermissionsDialog(false);
                } else {
                    showPermissionsDialog(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                permissionSuccess();
            }
        }
    }

    private void showPermissionsDialog(final boolean notInquiry) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.go_open_permissions), false));
        CommonDialog.showCommonDialog(ActivityManager.getInstance().getCurrentActivity(), getString(R.string.no_open_camear_permissions), "", getString(R.string.repulse_permissions), list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (notInquiry) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", QRCodeActivity.this.getPackageName(), null));
                    QRCodeActivity.this.startActivityForResult(intent, 50003);
                    return;
                }
                QRCodeActivity.this.checkPermission();
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                QRCodeActivity.this.finish();
            }
        }, true);
    }

    public void permissionSuccess() {
        init();
    }

    public void finish() {
        super.finish();
        getWindow().getDecorView().setSystemUiVisibility(0);
    }
}
