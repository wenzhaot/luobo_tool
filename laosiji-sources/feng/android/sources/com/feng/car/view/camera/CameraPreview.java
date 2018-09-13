package com.feng.car.view.camera;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.feng.car.utils.FengUtil;
import com.tencent.rtmp.TXLiveConstants;
import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements Callback {
    private String flashMode = "off";
    private Camera mCamera;
    private int mCameraId;
    private Context mContext;
    private int mDegree = 0;
    private SurfaceHolder mHolder;

    public void setmCameraId(int mCameraId) {
        this.mCameraId = mCameraId;
    }

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.mCamera = camera;
        this.mContext = context;
        this.mHolder = getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setType(3);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (this.mCamera != null) {
                this.mCamera.setPreviewDisplay(holder);
                optimizeCameraDimens(this.mCamera);
                this.mCamera.startPreview();
            }
        } catch (IOException e) {
        }
    }

    public int getDegree() {
        return this.mDegree;
    }

    public void refreshCamera(Camera camera) {
        if (this.mHolder.getSurface() != null) {
            try {
                this.mCamera.stopPreview();
            } catch (Exception e) {
            }
            if (camera != null) {
                roateCamera(camera);
            }
            setCamera(camera);
            try {
                this.mCamera.setPreviewDisplay(this.mHolder);
                optimizeCameraDimens(this.mCamera);
                this.mCamera.startPreview();
                camera.cancelAutoFocus();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void roateCamera(Camera camera) {
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(this.mCameraId, info);
        int degrees = 0;
        switch (((Activity) this.mContext).getWindowManager().getDefaultDisplay().getRotation()) {
            case 0:
                degrees = 0;
                break;
            case 1:
                degrees = 90;
                break;
            case 2:
                degrees = TXLiveConstants.RENDER_ROTATION_180;
                break;
            case 3:
                degrees = 270;
                break;
        }
        if (info.facing == 1) {
            this.mDegree = (info.orientation + degrees) % 360;
            this.mDegree = (360 - this.mDegree) % 360;
        } else {
            this.mDegree = ((info.orientation - degrees) + 360) % 360;
        }
        camera.setDisplayOrientation(this.mDegree);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        refreshCamera(this.mCamera);
    }

    public void setCamera(Camera camera) {
        this.mCamera = camera;
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void optimizeCameraDimens(Camera mCamera) {
        int width = FengUtil.getScreenWidth(this.mContext);
        int height = FengUtil.getScreenHeight(this.mContext);
        List<Size> mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        if (mSupportedPreviewSizes != null) {
            float ratio;
            Size mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
            if (mPreviewSize.height >= mPreviewSize.width) {
                ratio = ((float) mPreviewSize.height) / ((float) mPreviewSize.width);
            } else {
                ratio = ((float) mPreviewSize.width) / ((float) mPreviewSize.height);
            }
            setMeasuredDimension(width, (int) (((float) width) * ratio));
            Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            parameters.setFlashMode(this.flashMode);
            mCamera.setParameters(parameters);
        }
    }

    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        double targetRatio = ((double) h) / ((double) w);
        if (w > h) {
            targetRatio = ((double) w) / ((double) h);
        }
        if (sizes == null) {
            return null;
        }
        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = h;
        for (Size size : sizes) {
            double ratio = ((double) size.width) / ((double) size.height);
            if (size.height >= size.width) {
                ratio = (double) (((float) size.height) / ((float) size.width));
            }
            if (Math.abs(ratio - targetRatio) <= 0.1d && ((double) Math.abs(size.height - targetHeight)) < minDiff) {
                optimalSize = size;
                minDiff = (double) Math.abs(size.height - targetHeight);
            }
        }
        if (optimalSize != null) {
            return optimalSize;
        }
        minDiff = Double.MAX_VALUE;
        for (Size size2 : sizes) {
            if (((double) Math.abs(size2.height - targetHeight)) < minDiff) {
                optimalSize = size2;
                minDiff = (double) Math.abs(size2.height - targetHeight);
            }
        }
        return optimalSize;
    }

    public void setFlashMode(String mode) {
        this.flashMode = mode;
    }
}
