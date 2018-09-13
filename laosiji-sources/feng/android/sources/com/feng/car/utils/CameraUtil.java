package com.feng.car.utils;

import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.view.MotionEvent;
import android.view.SurfaceView;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.taobao.accs.flowcontrol.FlowControl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CameraUtil {
    private static final int FOCUS_AREA_SIZE = 500;
    public static final int MAX_DURATION_RECORD = 15000;
    public static final long MAX_FILE_SIZE_RECORD = 524288000;
    public static final int PICTURE_TYPE = 1;
    public static final String TYPE_KEY = "type_key";
    public static final int VIDEO_TYPE = 0;
    private static CameraUtil instance;
    private int mPreviewHeight = 0;
    private int mPreviewWidth = 0;

    public static CameraUtil getInstance() {
        if (instance == null) {
            instance = new CameraUtil();
        }
        return instance;
    }

    public CamcorderProfile getCameraQuality(int idCamera) {
        if (CamcorderProfile.hasProfile(idCamera, 5)) {
            return CamcorderProfile.get(5);
        }
        if (CamcorderProfile.hasProfile(idCamera, 6)) {
            return CamcorderProfile.get(6);
        }
        return CamcorderProfile.get(4);
    }

    public File creatPicFile() {
        File file = new File(FengConstant.IMAGE_FILE_PATH_TAKE_PHOTO);
        if (!file.exists()) {
            file.mkdirs();
        }
        File outputFile = new File(file.getAbsolutePath() + String.valueOf(new Date().getTime()) + ".jpg");
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputFile;
    }

    public File creatVideoFile() {
        File file = new File(FengConstant.IMAGE_FILE_PATH_TAKE_PHOTO);
        if (!file.exists()) {
            file.mkdirs();
        }
        File outputFile = new File(file.getAbsolutePath() + "/preview_" + String.valueOf(new Date().getTime()) + ".mp4");
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputFile;
    }

    public int getPreviewWidth() {
        return this.mPreviewWidth;
    }

    public int getPreviewHeight() {
        return this.mPreviewHeight;
    }

    public void initPictureParams(Camera camera) {
        Parameters params = camera.getParameters();
        List<Size> sizeList = params.getSupportedPictureSizes();
        if (sizeList.size() > 1) {
            for (Size cur : sizeList) {
                if (cur.width >= this.mPreviewWidth && cur.height >= this.mPreviewHeight) {
                    this.mPreviewWidth = cur.width;
                    this.mPreviewHeight = cur.height;
                    if (this.mPreviewHeight > 1280) {
                        break;
                    }
                }
            }
        }
        params.setPictureFormat(AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
        params.set("jpeg-quality", 100);
        params.setPictureSize(this.mPreviewWidth, this.mPreviewHeight);
        camera.setParameters(params);
    }

    public void focusOnTouch(Camera camera, MotionEvent event, SurfaceView preview, AutoFocusCallback autoFocusTakePictureCallback) {
        if (camera != null) {
            Parameters parameters = camera.getParameters();
            if (parameters.getMaxNumMeteringAreas() > 0) {
                Rect rect = calculateFocusArea(event.getX(), event.getY(), preview);
                parameters.setFocusMode("auto");
                List<Area> meteringAreas = new ArrayList();
                meteringAreas.add(new Area(rect, 800));
                parameters.setFocusAreas(meteringAreas);
                camera.setParameters(parameters);
                camera.autoFocus(autoFocusTakePictureCallback);
                return;
            }
            camera.autoFocus(autoFocusTakePictureCallback);
        }
    }

    private Rect calculateFocusArea(float x, float y, SurfaceView preview) {
        int left = clamp(Float.valueOf(((x / ((float) preview.getWidth())) * 2000.0f) - 1000.0f).intValue(), FOCUS_AREA_SIZE);
        int top = clamp(Float.valueOf(((y / ((float) preview.getHeight())) * 2000.0f) - 1000.0f).intValue(), FOCUS_AREA_SIZE);
        return new Rect(left, top, left + FOCUS_AREA_SIZE, top + FOCUS_AREA_SIZE);
    }

    private int clamp(int touchCoordinateInCameraReper, int focusAreaSize) {
        if (Math.abs(touchCoordinateInCameraReper) + (focusAreaSize / 2) <= 1000) {
            return touchCoordinateInCameraReper - (focusAreaSize / 2);
        }
        if (touchCoordinateInCameraReper > 0) {
            return 1000 - (focusAreaSize / 2);
        }
        return (focusAreaSize / 2) + FlowControl.DELAY_MAX_BRUSH;
    }
}
