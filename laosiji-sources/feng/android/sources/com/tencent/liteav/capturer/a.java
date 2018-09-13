package com.tencent.liteav.capturer;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import com.feng.car.utils.FengConstant;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: TXCCameraCapturer */
public class a implements AutoFocusCallback {
    private static final String c = a.class.getSimpleName();
    private Matrix a = new Matrix();
    private int b = 0;
    private Camera d;
    private boolean e = true;
    private int f = 15;
    private int g;
    private int h = 1;
    private int i;
    private int j;
    private int k;
    private int l;
    private SurfaceTexture m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q = false;

    /* compiled from: TXCCameraCapturer */
    private class a {
        public int a = 1280;
        public int b = 720;

        a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }

    public void a(SurfaceTexture surfaceTexture) {
        this.m = surfaceTexture;
    }

    public boolean a(boolean z) {
        this.p = z;
        if (this.d == null) {
            return false;
        }
        boolean z2 = true;
        Parameters parameters = this.d.getParameters();
        List supportedFlashModes = parameters.getSupportedFlashModes();
        if (z) {
            if (supportedFlashModes == null || !supportedFlashModes.contains("torch")) {
                z2 = false;
            } else {
                TXCLog.i(c, "set FLASH_MODE_TORCH");
                parameters.setFlashMode("torch");
            }
        } else if (supportedFlashModes == null || !supportedFlashModes.contains("off")) {
            z2 = false;
        } else {
            TXCLog.i(c, "set FLASH_MODE_OFF");
            parameters.setFlashMode("off");
        }
        try {
            this.d.setParameters(parameters);
            return z2;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void a(int i) {
        this.g = i;
    }

    public void b(int i) {
        this.f = i;
    }

    public int a() {
        if (this.d == null) {
            return 0;
        }
        Parameters parameters = this.d.getParameters();
        if (parameters.getMaxZoom() <= 0 || !parameters.isZoomSupported()) {
            return 0;
        }
        return parameters.getMaxZoom();
    }

    public boolean c(int i) {
        if (this.d == null) {
            return false;
        }
        Parameters parameters = this.d.getParameters();
        if (parameters.getMaxZoom() <= 0 || !parameters.isZoomSupported()) {
            TXCLog.e(c, "camera not support zoom!");
            return false;
        } else if (i < 0 || i > parameters.getMaxZoom()) {
            TXCLog.e(c, "invalid zoom value : " + i + ", while max zoom is " + parameters.getMaxZoom());
            return false;
        } else {
            try {
                parameters.setZoom(i);
                this.d.setParameters(parameters);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public void d(int i) {
        this.h = i;
        this.k = (((this.l - 90) + (this.h * 90)) + 360) % 360;
    }

    public int b(boolean z) {
        try {
            if (this.m == null) {
                return -2;
            }
            int i;
            if (this.d != null) {
                b();
            }
            CameraInfo cameraInfo = new CameraInfo();
            int i2 = -1;
            int i3 = -1;
            for (i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.getCameraInfo(i, cameraInfo);
                TXCLog.i(c, "camera index " + i + ", facing = " + cameraInfo.facing);
                if (cameraInfo.facing == 1) {
                    i3 = i;
                }
                if (cameraInfo.facing == 0) {
                    i2 = i;
                }
            }
            TXCLog.i(c, "camera front, id = " + i3);
            TXCLog.i(c, "camera back , id = " + i2);
            if (i3 != -1 || i2 == -1) {
                i = i3;
            } else {
                i = i2;
            }
            if (i2 != -1 || i == -1) {
                i3 = i2;
            } else {
                i3 = i;
            }
            this.e = z;
            if (this.e) {
                this.d = Camera.open(i);
            } else {
                this.d = Camera.open(i3);
            }
            Parameters parameters = this.d.getParameters();
            List supportedFocusModes = parameters.getSupportedFocusModes();
            if (this.q && supportedFocusModes != null && supportedFocusModes.contains("auto")) {
                TXCLog.i(c, "support FOCUS_MODE_AUTO");
                parameters.setFocusMode("auto");
            } else if (supportedFocusModes != null) {
                if (supportedFocusModes.contains("continuous-video")) {
                    TXCLog.i(c, "support FOCUS_MODE_CONTINUOUS_VIDEO");
                    parameters.setFocusMode("continuous-video");
                }
            }
            if (VERSION.SDK_INT >= 14) {
                if (parameters.getMaxNumFocusAreas() > 0) {
                    this.n = true;
                }
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    this.o = true;
                }
            }
            String str = "";
            List supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            if (supportedPreviewSizes != null) {
                String str2 = str;
                for (int i4 = 0; i4 < supportedPreviewSizes.size(); i4++) {
                    Size size = (Size) supportedPreviewSizes.get(i4);
                    str2 = str2 + String.format("camera supported preview size %d x %d\n", new Object[]{Integer.valueOf(size.width), Integer.valueOf(size.height)});
                }
                str = str2;
            }
            a e = e(this.g);
            if (e == null) {
                this.d.release();
                this.d = null;
                TXCLog.d(c, "不支持的视频分辨率");
                return -3;
            }
            TXCLog.i(c, str + String.format("choose preview size %d x %d ", new Object[]{Integer.valueOf(e.a), Integer.valueOf(e.b)}));
            this.i = e.a;
            this.j = e.b;
            parameters.setPreviewSize(e.a, e.b);
            int[] g = g(this.f);
            if (g != null) {
                parameters.setPreviewFpsRange(g[0], g[1]);
            } else {
                parameters.setPreviewFrameRate(f(this.f));
            }
            if (this.e) {
                i2 = i;
            } else {
                i2 = i3;
            }
            this.l = h(i2);
            this.k = (((this.l - 90) + (this.h * 90)) + 360) % 360;
            this.d.setDisplayOrientation(0);
            this.d.setPreviewTexture(this.m);
            this.d.setParameters(parameters);
            this.d.startPreview();
            return 0;
        } catch (IOException e2) {
            e2.printStackTrace();
            return -1;
        } catch (Exception e3) {
            e3.printStackTrace();
            return -1;
        }
    }

    public void b() {
        if (this.d != null) {
            try {
                this.d.setPreviewCallback(null);
                this.d.stopPreview();
                this.d.release();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.d = null;
                this.m = null;
            }
        }
    }

    public int c() {
        return this.k;
    }

    public int d() {
        return this.i;
    }

    public int e() {
        return this.j;
    }

    private a e(int i) {
        List supportedPreviewSizes = this.d.getParameters().getSupportedPreviewSizes();
        List arrayList = new ArrayList();
        switch (i) {
            case 1:
            case 2:
            case 4:
                arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 360));
                arrayList.add(new a(768, 432));
                arrayList.add(new a(960, 540));
                arrayList.add(new a(800, 480));
                arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 480));
                arrayList.add(new a(960, 720));
                arrayList.add(new a(1280, 720));
                break;
            case 3:
                break;
            case 5:
                arrayList.add(new a(960, 540));
                arrayList.add(new a(960, 720));
                arrayList.add(new a(1280, 720));
                arrayList.add(new a(800, 480));
                arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 360));
                arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 480));
                break;
            case 6:
                arrayList.add(new a(1280, 720));
                arrayList.add(new a(1920, 1080));
                arrayList.add(new a(960, 540));
                arrayList.add(new a(960, 720));
                arrayList.add(new a(800, 480));
                arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 360));
                arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 480));
                break;
            case 7:
                arrayList.add(new a(1280, 720));
                arrayList.add(new a(960, 540));
                arrayList.add(new a(960, 720));
                arrayList.add(new a(800, 480));
                arrayList.add(new a(768, 432));
                arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 360));
                arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 480));
                break;
        }
        arrayList.add(new a(480, 320));
        arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 360));
        arrayList.add(new a(FengConstant.IMAGEMIDDLEWIDTH, 480));
        arrayList.add(new a(768, 432));
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= arrayList.size()) {
                return null;
            }
            a aVar = (a) arrayList.get(i3);
            int i4 = 0;
            while (true) {
                int i5 = i4;
                if (i5 < supportedPreviewSizes.size()) {
                    Size size = (Size) supportedPreviewSizes.get(i5);
                    if (size.width == aVar.a && size.height == aVar.b) {
                        TXCLog.w(c, "wanted:" + aVar.a + "*" + aVar.b);
                        return aVar;
                    }
                    i4 = i5 + 1;
                } else {
                    i2 = i3 + 1;
                }
            }
        }
    }

    public void onAutoFocus(boolean z, Camera camera) {
        if (z) {
            TXCLog.i(c, "AUTO focus success");
        } else {
            TXCLog.i(c, "AUTO focus failed");
        }
    }

    private int f(int i) {
        List supportedPreviewFrameRates = this.d.getParameters().getSupportedPreviewFrameRates();
        int i2 = 1;
        if (supportedPreviewFrameRates == null) {
            TXCLog.e(c, "getSupportedFPS error");
        } else {
            int intValue = ((Integer) supportedPreviewFrameRates.get(0)).intValue();
            while (true) {
                i2 = intValue;
                if (0 >= supportedPreviewFrameRates.size()) {
                    break;
                }
                intValue = ((Integer) supportedPreviewFrameRates.get(0)).intValue();
                if (Math.abs(intValue - i) - Math.abs(i2 - i) < 0) {
                    i2 = intValue;
                }
                intValue = 0 + 1;
            }
            TXCLog.i(c, "choose fpts=" + i2);
        }
        return i2;
    }

    private int[] g(int i) {
        int i2 = i * 1000;
        String str = "camera supported preview fps range: wantFPS = " + i2 + "\n";
        List<int[]> supportedPreviewFpsRange = this.d.getParameters().getSupportedPreviewFpsRange();
        if (supportedPreviewFpsRange == null || supportedPreviewFpsRange.size() <= 0) {
            return null;
        }
        String str2;
        int[] iArr;
        int[] iArr2 = (int[]) supportedPreviewFpsRange.get(0);
        Collections.sort(supportedPreviewFpsRange, new Comparator<int[]>() {
            /* renamed from: a */
            public int compare(int[] iArr, int[] iArr2) {
                return iArr[1] - iArr2[1];
            }
        });
        Iterator it = supportedPreviewFpsRange.iterator();
        while (true) {
            str2 = str;
            if (!it.hasNext()) {
                break;
            }
            iArr = (int[]) it.next();
            str = str2 + "camera supported preview fps range: " + iArr[0] + " - " + iArr[1] + "\n";
        }
        for (int[] iArr3 : supportedPreviewFpsRange) {
            if (iArr3[0] <= i2 && i2 <= iArr3[1]) {
                break;
            }
        }
        iArr3 = iArr2;
        TXCLog.i(c, str2 + "choose preview fps range: " + iArr3[0] + " - " + iArr3[1]);
        return iArr3;
    }

    private int h(int i) {
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        if (cameraInfo.facing == 1) {
            return (360 - cameraInfo.orientation) % 360;
        }
        return (cameraInfo.orientation + 360) % 360;
    }
}
