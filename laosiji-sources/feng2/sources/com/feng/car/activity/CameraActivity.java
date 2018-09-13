package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CameraPickAdapter;
import com.feng.car.databinding.ActivityCameraBinding;
import com.feng.car.databinding.CameraErrorLayoutBinding;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.listener.CamerSensorListener;
import com.feng.car.listener.CamerSensorListener$SensorListener;
import com.feng.car.utils.CameraUtil;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.ScreenUtil;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.camera.CameraPreview;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CameraActivity extends BaseActivity<ActivityCameraBinding> {
    private boolean canBack = true;
    private long countUp;
    private Dialog errorDialog;
    private CamerSensorListener mCamerSensorListener;
    private Camera mCamera;
    private CameraErrorLayoutBinding mCameraErrorBinding;
    private boolean mCameraFront = false;
    private CameraPickAdapter mCameraPickAdapter;
    private int mDegree = 0;
    private String mFlashMode = "auto";
    private ScaleAnimation mFocusAnim;
    private boolean mIsRecording = false;
    private CameraPreview mPreview;
    private int mType = 1;
    private File mVideoOutputFile;
    private MediaRecorder mediaRecorder;
    private SensorManager sensorManager;

    static {
        StubApp.interface11(2233);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.activity_camera;
    }

    public void initView() {
        hideDefaultTitleBar();
        boolean flag = ScreenUtil.hasNotch();
        Log.e("===", "flag:" + flag);
        if (flag) {
            ((ActivityCameraBinding) this.mBaseBinding).topLine.setLayoutParams(new LayoutParams(-1, this.mResources.getDimensionPixelSize(R.dimen.default_134PX)));
            ((ActivityCameraBinding) this.mBaseBinding).topLine.setPadding(0, this.mResources.getDimensionPixelSize(R.dimen.default_50PX), 0, 0);
        } else {
            ((ActivityCameraBinding) this.mBaseBinding).topLine.setLayoutParams(new LayoutParams(-1, this.mResources.getDimensionPixelSize(R.dimen.default_84PX)));
        }
        closeSwip();
        initCamera();
        initChoosr();
        initButton();
    }

    private void startShutterAnim() {
        ((ActivityCameraBinding) this.mBaseBinding).shutterView.setVisibility(0);
        new Thread(new Runnable() {
            public void run() {
                try {
                    ((AudioManager) CameraActivity.this.getSystemService("audio")).playSoundEffect(4);
                    Thread.sleep(150);
                    CameraActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).shutterView.setVisibility(8);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initButton() {
        ((ActivityCameraBinding) this.mBaseBinding).topLine.setClickable(true);
        ((ActivityCameraBinding) this.mBaseBinding).bottomLine.setClickable(true);
        ((ActivityCameraBinding) this.mBaseBinding).cancel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CameraActivity.this.finish();
            }
        });
        ((ActivityCameraBinding) this.mBaseBinding).buttonFlash.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!CameraActivity.this.mIsRecording && !CameraActivity.this.mCameraFront) {
                    if (CameraActivity.this.mFlashMode.equals("auto")) {
                        CameraActivity.this.mFlashMode = "off";
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).buttonFlash.setImageResource(R.drawable.icon_flash_off);
                    } else if (CameraActivity.this.mFlashMode.equals("off")) {
                        CameraActivity.this.mFlashMode = "on";
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).buttonFlash.setImageResource(R.drawable.icon_flash_on);
                    } else if (CameraActivity.this.mFlashMode.equals("on")) {
                        CameraActivity.this.mFlashMode = "auto";
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).buttonFlash.setImageResource(R.drawable.icon_flash_auto);
                    }
                    CameraActivity.this.setFlashMode(CameraActivity.this.mFlashMode);
                }
            }
        });
        ((ActivityCameraBinding) this.mBaseBinding).switchCamera.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!CameraActivity.this.mIsRecording) {
                    if (Camera.getNumberOfCameras() > 1) {
                        CameraActivity.this.releaseCamera();
                        CameraActivity.this.chooseCamera();
                        return;
                    }
                    CameraActivity.this.showThirdTypeToast((int) R.string.sorry_only_one_camera);
                }
            }
        });
        ((ActivityCameraBinding) this.mBaseBinding).capture.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (CameraActivity.this.mType == 1) {
                    if (FengConstant.UPLOAD_IMAGE_COUNT == 30) {
                        CameraActivity.this.showThirdTypeToast("最多只能添加30张图片");
                    } else if (!CameraActivity.this.mIsRecording) {
                        CameraUtil.getInstance().initPictureParams(CameraActivity.this.mCamera);
                        CameraActivity.this.mCamera.takePicture(new ShutterCallback() {
                            public void onShutter() {
                                CameraActivity.this.startShutterAnim();
                            }
                        }, null, new PictureCallback() {
                            public void onPictureTaken(final byte[] data, Camera camera) {
                                CameraActivity.this.showProgress("", "正在处理，请稍候...");
                                CameraActivity.this.canBack = false;
                                final int d = CameraActivity.this.mDegree;
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            File outputFile = CameraUtil.getInstance().creatPicFile();
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            if (d != 0) {
                                                Matrix m = new Matrix();
                                                m.postRotate((float) d);
                                                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                                            }
                                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));
                                            bitmap.compress(CompressFormat.JPEG, 100, bos);
                                            bos.flush();
                                            bos.close();
                                            Intent intent = new Intent(CameraActivity.this, CameraPreviewActivity.class);
                                            intent.putExtra(CameraPreviewActivity.TYPE_KEY, 0);
                                            intent.putExtra(CameraPreviewActivity.PATH, outputFile.getAbsolutePath());
                                            CameraActivity.this.startActivity(intent);
                                            CameraActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    CameraActivity.this.hideProgress();
                                                    CameraActivity.this.canBack = true;
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                camera.startPreview();
                            }
                        });
                    }
                } else if (CameraActivity.this.mType != 0) {
                } else {
                    if (CameraActivity.this.mIsRecording) {
                        CameraActivity.this.showProgress("", "正在处理，请稍候...");
                        CameraActivity.this.canBack = false;
                        try {
                            CameraActivity.this.mediaRecorder.stop();
                            CameraActivity.this.stopChronometer();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).capture.setImageResource(R.drawable.icon_recorder_start);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).bottomLine.getBackground().mutate().setAlpha(178);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).gallery.setVisibility(0);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).buttonFlash.setVisibility(0);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).cancel.setVisibility(0);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).switchCamera.setVisibility(0);
                        CameraActivity.this.releaseMediaRecorder();
                        CameraActivity.this.showFirstTypeToast((int) R.string.recording_end);
                        CameraActivity.this.mIsRecording = false;
                        CameraActivity.this.canBack = true;
                        if (CameraActivity.this.countUp < 10) {
                            Toast.makeText(CameraActivity.this, "视频最小长度为10秒", 0).show();
                        } else {
                            Intent intent = new Intent(CameraActivity.this, ShortVideoSelectImageActivity.class);
                            intent.putExtra("key_video_editer_path", CameraActivity.this.mVideoOutputFile.getAbsolutePath());
                            CameraActivity.this.startActivity(intent);
                        }
                        CameraActivity.this.hideProgress();
                        return;
                    }
                    CameraActivity.this.checkPermissionRecordAudio();
                }
            }
        });
    }

    private void startRecord() {
        if (FengConstant.UPLOAD_VIDEO_COUNT == 1) {
            showThirdTypeToast((int) R.string.select_one_video_only);
        } else if (prepareMediaRecorder()) {
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        CameraActivity.this.mediaRecorder.start();
                        CameraActivity.this.startChronometer();
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).capture.setImageResource(R.drawable.icon_recorder_stop);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).topLine.getBackground().mutate().setAlpha(178);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).bottomLine.getBackground().mutate().setAlpha(1);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).gallery.setVisibility(4);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).buttonFlash.setVisibility(4);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).cancel.setVisibility(4);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).switchCamera.setVisibility(4);
                    } catch (Exception e) {
                        CameraActivity.this.showCameraErrorView();
                    }
                }
            });
            this.mIsRecording = true;
        } else {
            showSecondTypeToast((int) R.string.recorder_exctption);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50004) {
            try {
                if (permissions[0].equals("android.permission.RECORD_AUDIO") && grantResults[0] == 0) {
                    showFirstTypeToast((int) R.string.click_redbutton_start_recording);
                } else {
                    FengUtil.showSettingPermissionDialog(this, "录音", "录音");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkPermissionRecordAudio() {
        if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
            try {
                startRecord();
                return;
            } catch (Exception e) {
                return;
            }
        }
        requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 50004);
    }

    private void startChronometer() {
        final long startTime = SystemClock.elapsedRealtime();
        ((ActivityCameraBinding) this.mBaseBinding).chrono.setOnChronometerTickListener(new OnChronometerTickListener() {
            public void onChronometerTick(Chronometer arg0) {
                CameraActivity.this.countUp = (SystemClock.elapsedRealtime() - startTime) / 1000;
                if (CameraActivity.this.countUp % 2 == 0) {
                    ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).chronoRecordingImage.setVisibility(0);
                } else {
                    ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).chronoRecordingImage.setVisibility(4);
                }
                ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).chrono.setText(String.format("%02d", new Object[]{Long.valueOf(CameraActivity.this.countUp / 60)}) + ":" + String.format("%02d", new Object[]{Long.valueOf(CameraActivity.this.countUp % 60)}));
                if (CameraActivity.this.countUp == ((long) FengConstant.MAX_VIDEO_CROP)) {
                    CameraActivity.this.showProgress("", "正在处理，请稍候...");
                    CameraActivity.this.mediaRecorder.stop();
                    CameraActivity.this.stopChronometer();
                    ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).capture.setImageResource(R.drawable.icon_recorder_start);
                    CameraActivity.this.releaseMediaRecorder();
                    CameraActivity.this.mIsRecording = false;
                    Intent intent = new Intent(CameraActivity.this, ShortVideoSelectImageActivity.class);
                    intent.putExtra("key_video_editer_path", CameraActivity.this.mVideoOutputFile.getAbsolutePath());
                    CameraActivity.this.startActivity(intent);
                    CameraActivity.this.hideProgress();
                }
            }
        });
        ((ActivityCameraBinding) this.mBaseBinding).chrono.start();
    }

    private void stopChronometer() {
        ((ActivityCameraBinding) this.mBaseBinding).chrono.stop();
        ((ActivityCameraBinding) this.mBaseBinding).chrono.setText("00:00");
        ((ActivityCameraBinding) this.mBaseBinding).chronoRecordingImage.setVisibility(4);
    }

    private boolean prepareMediaRecorder() {
        this.mediaRecorder = new MediaRecorder();
        this.mCamera.unlock();
        this.mediaRecorder.setCamera(this.mCamera);
        this.mediaRecorder.setAudioSource(5);
        this.mediaRecorder.setVideoSource(1);
        this.mediaRecorder.setOrientationHint(this.mDegree);
        if (this.mCameraFront) {
            this.mediaRecorder.setProfile(CameraUtil.getInstance().getCameraQuality(findFrontFacingCamera()));
        } else {
            this.mediaRecorder.setProfile(CameraUtil.getInstance().getCameraQuality(findBackFacingCamera()));
        }
        this.mVideoOutputFile = CameraUtil.getInstance().creatVideoFile();
        this.mediaRecorder.setOutputFile(this.mVideoOutputFile.getAbsolutePath());
        this.mediaRecorder.setMaxDuration(FengConstant.MAX_VIDEO_CROP * 1000);
        this.mediaRecorder.setMaxFileSize(524288000);
        try {
            this.mediaRecorder.prepare();
            return true;
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            return false;
        } catch (IOException e2) {
            releaseMediaRecorder();
            return false;
        }
    }

    private void releaseMediaRecorder() {
        if (this.mediaRecorder != null) {
            this.mediaRecorder.reset();
            this.mediaRecorder.release();
            this.mediaRecorder = null;
            this.mCamera.lock();
        }
    }

    public void chooseCamera() {
        int cameraId;
        if (this.mCameraFront) {
            cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                this.mCamera = Camera.open(cameraId);
                this.mPreview.refreshCamera(this.mCamera);
                return;
            }
            return;
        }
        cameraId = findFrontFacingCamera();
        if (cameraId >= 0) {
            this.mCamera = Camera.open(cameraId);
            this.mPreview.refreshCamera(this.mCamera);
        }
    }

    private void initCamera() {
        this.mPreview = new CameraPreview(this, this.mCamera);
        ((ActivityCameraBinding) this.mBaseBinding).cameraPreview.addView(this.mPreview);
        this.sensorManager = (SensorManager) getSystemService("sensor");
        this.mCamerSensorListener = new CamerSensorListener(this, new CamerSensorListener$SensorListener() {
            public void onChanged(SensorEvent event) {
                float x = event.values[0];
                if (-5.0f >= x || x >= 5.0f) {
                    if (-11.0f < x && x < -7.0f) {
                        CameraActivity.this.mDegree = 180;
                    } else if (7.0f < x && x < 11.0f) {
                        CameraActivity.this.mDegree = 0;
                    }
                } else if (CameraActivity.this.mCameraFront) {
                    CameraActivity.this.mDegree = -90;
                } else {
                    CameraActivity.this.mDegree = 90;
                }
            }
        });
        ((ActivityCameraBinding) this.mBaseBinding).cameraPreview.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0 && !((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.isShown()) {
                    final int x = (int) event.getX();
                    final int y = (int) event.getY();
                    ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.setVisibility(0);
                    ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.post(new Runnable() {
                        public void run() {
                            ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.layout(x - (((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.getWidth() / 2), y - (((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.getHeight() / 2), x + (((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.getWidth() / 2), (((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.getHeight() / 2) + y);
                            if (CameraActivity.this.mFocusAnim == null) {
                                CameraActivity.this.mFocusAnim = (ScaleAnimation) AnimationUtils.loadAnimation(CameraActivity.this, R.anim.focus_anim);
                                CameraActivity.this.mFocusAnim.setAnimationListener(new AnimationListener() {
                                    public void onAnimationStart(Animation animation) {
                                    }

                                    public void onAnimationEnd(Animation animation) {
                                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.setVisibility(8);
                                    }

                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });
                            }
                            ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).focusImage.startAnimation(CameraActivity.this.mFocusAnim);
                        }
                    });
                    try {
                        CameraUtil.getInstance().focusOnTouch(CameraActivity.this.mCamera, event, CameraActivity.this.mPreview, new AutoFocusCallback() {
                            public void onAutoFocus(boolean success, Camera camera) {
                                if (success) {
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }

    protected void onPause() {
        super.onPause();
        try {
            releaseCamera();
            this.sensorManager.unregisterListener(this.mCamerSensorListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        hideCameraErrorView();
    }

    protected void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this.mCamerSensorListener, this.sensorManager.getDefaultSensor(1), 3);
        if (this.mCamera == null) {
            releaseCamera();
            boolean frontal = this.mCameraFront;
            int cameraId = findFrontFacingCamera();
            if (cameraId < 0) {
                cameraId = findBackFacingCamera();
            } else if (!frontal) {
                cameraId = findBackFacingCamera();
            }
            try {
                this.mCamera = Camera.open(cameraId);
                this.mPreview.refreshCamera(this.mCamera);
                this.mPreview.setmCameraId(cameraId);
                hideCameraErrorView();
            } catch (Exception e) {
                showCameraErrorView();
            }
        }
    }

    private void hideCameraErrorView() {
        if (this.errorDialog != null && this.errorDialog.isShowing()) {
            this.errorDialog.dismiss();
            ((ActivityCameraBinding) this.mBaseBinding).cameraErrorView.setVisibility(8);
        }
    }

    private void showCameraErrorView() {
        ((ActivityCameraBinding) this.mBaseBinding).cameraErrorView.setVisibility(0);
        if (this.mCameraErrorBinding == null) {
            this.mCameraErrorBinding = CameraErrorLayoutBinding.inflate(LayoutInflater.from(this));
            this.mCameraErrorBinding.confirm.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    CameraActivity.this.errorDialog.dismiss();
                    CameraActivity.this.finish();
                }
            });
            this.errorDialog = CommonDialog.showViewDialog(this, this.mCameraErrorBinding.getRoot(), 17, false, false, false);
        }
        this.errorDialog.show();
    }

    private int findFrontFacingCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == 1) {
                int cameraId = i;
                this.mCameraFront = true;
                return cameraId;
            }
        }
        return -1;
    }

    private int findBackFacingCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == 0) {
                int cameraId = i;
                this.mCameraFront = false;
                return cameraId;
            }
        }
        return -1;
    }

    private void releaseCamera() {
        if (this.mCamera != null) {
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    private void initChoosr() {
        Intent intent = getIntent();
        if (intent != null) {
            this.mType = intent.getIntExtra(CameraPreviewActivity.TYPE_KEY, 1);
        }
        List<String> list = new ArrayList();
        this.mCameraPickAdapter = new CameraPickAdapter(list, this);
        if (FengApplication.getInstance().isAllowSendVideo()) {
            list.add("视频");
        } else {
            this.mType = 1;
        }
        list.add("照片");
        ((ActivityCameraBinding) this.mBaseBinding).gallery.setAdapter(this.mCameraPickAdapter);
        ((ActivityCameraBinding) this.mBaseBinding).gallery.setSelection(FengApplication.getInstance().isAllowSendVideo() ? this.mType : 0);
        ((ActivityCameraBinding) this.mBaseBinding).gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (FengApplication.getInstance().isAllowSendVideo()) {
                    if (position == 0) {
                        CameraActivity.this.mType = 0;
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).topLine.getBackground().mutate().setAlpha(179);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).bottomLine.getBackground().mutate().setAlpha(179);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).capture.setImageResource(R.drawable.icon_recorder_start);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).chrono.setVisibility(0);
                    } else {
                        CameraActivity.this.mType = 1;
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).topLine.getBackground().mutate().setAlpha(255);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).bottomLine.getBackground().mutate().setAlpha(255);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).capture.setImageResource(R.drawable.icon_takepicture);
                        ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).chrono.setVisibility(4);
                    }
                    CameraActivity.this.mCameraPickAdapter.setSelectPosition(position);
                    CameraActivity.this.mCameraPickAdapter.notifyDataSetChanged();
                    return;
                }
                if (position == 0) {
                    CameraActivity.this.mType = 1;
                    ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).topLine.getBackground().mutate().setAlpha(255);
                    ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).bottomLine.getBackground().mutate().setAlpha(255);
                    ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).capture.setImageResource(R.drawable.icon_takepicture);
                    ((ActivityCameraBinding) CameraActivity.this.mBaseBinding).chrono.setVisibility(4);
                }
                CameraActivity.this.mCameraPickAdapter.setSelectPosition(position);
                CameraActivity.this.mCameraPickAdapter.notifyDataSetChanged();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setFlashMode(String mode) {
        try {
            if (getPackageManager().hasSystemFeature("android.hardware.camera.flash") && this.mCamera != null && !this.mCameraFront) {
                this.mPreview.setFlashMode(mode);
                this.mPreview.refreshCamera(this.mCamera);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showSecondTypeToast((int) R.string.set_flash_exception);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.mIsRecording) {
            this.mediaRecorder.stop();
            if (((ActivityCameraBinding) this.mBaseBinding).chrono != null && ((ActivityCameraBinding) this.mBaseBinding).chrono.isActivated()) {
                ((ActivityCameraBinding) this.mBaseBinding).chrono.stop();
            }
            releaseMediaRecorder();
            this.mIsRecording = false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClosePageEvent event) {
        finish();
    }

    public void onBackPressed() {
        if (this.canBack) {
            super.onBackPressed();
        }
    }
}
