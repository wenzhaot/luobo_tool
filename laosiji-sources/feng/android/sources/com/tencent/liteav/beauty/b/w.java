package com.tencent.liteav.beauty.b;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.util.TypedValue;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.g;
import com.tencent.liteav.basic.c.h;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.b.q.a;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: TXCGPURenderer */
public class w implements PreviewCallback, Renderer {
    static final float[] a = new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    public final Object b;
    private d c;
    private SurfaceTexture d;
    private final FloatBuffer e;
    private final FloatBuffer f;
    private IntBuffer g;
    private int h;
    private int i;
    private int j;
    private int k;
    private final Queue<Runnable> l;
    private final Queue<Runnable> m;
    private g n;
    private boolean o;
    private boolean p;
    private a q;
    private float r;
    private float s;
    private float t;
    private Context u;
    private int v;
    private String w;
    private FileOutputStream x;
    private int y;
    private int z;

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        GLES20.glClearColor(this.r, this.s, this.t, 1.0f);
        GLES20.glDisable(2929);
        this.c.c();
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.h = i;
        this.i = i2;
        GLES20.glViewport(0, 0, i, i2);
        GLES20.glUseProgram(this.c.q());
        this.c.a(i, i2);
        a();
        synchronized (this.b) {
            this.b.notifyAll();
        }
    }

    public void onDrawFrame(GL10 gl10) {
        int i = 0;
        GLES20.glClear(16640);
        a(this.l);
        if (this.z == -1) {
            int[] iArr = new int[1];
            GLES20.glGenTextures(1, iArr, 0);
            this.z = iArr[0];
            GLES20.glBindTexture(3553, this.z);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            TypedValue typedValue = new TypedValue();
            Options options = new Options();
            options.inTargetDensity = typedValue.density;
            Bitmap decodeResource = BitmapFactory.decodeResource(this.u.getResources(), this.v, options);
            GLES20.glBindTexture(3553, this.z);
            GLUtils.texImage2D(3553, 0, decodeResource, 0);
        }
        this.c.a(this.z, this.e, this.f);
        Buffer allocate = IntBuffer.allocate(250000);
        GLES20.glReadPixels(0, 0, 500, 500, 6408, 5121, allocate);
        int i2 = this.y;
        this.y = i2 + 1;
        if (i2 == 50) {
            try {
                if (this.x == null) {
                    this.x = new FileOutputStream(new File("/mnt/sdcard/", "rgbBuffer"));
                }
                String byteOrder = allocate.order().toString();
                int[] array = allocate.array();
                byte[] bArr = new byte[1000000];
                if (byteOrder.compareTo("LITTLE_ENDIAN") != 0) {
                    while (i < 250000) {
                        bArr[(i * 4) + 3] = (byte) ((array[i] >> 24) & 255);
                        bArr[(i * 4) + 2] = (byte) ((array[i] >> 16) & 255);
                        bArr[(i * 4) + 1] = (byte) ((array[i] >> 8) & 255);
                        bArr[i * 4] = (byte) (array[i] & 255);
                        i++;
                    }
                } else {
                    while (i < 250000) {
                        bArr[i * 4] = (byte) ((array[i] >> 24) & 255);
                        bArr[(i * 4) + 1] = (byte) ((array[i] >> 16) & 255);
                        bArr[(i * 4) + 2] = (byte) ((array[i] >> 8) & 255);
                        bArr[(i * 4) + 3] = (byte) (array[i] & 255);
                        i++;
                    }
                }
                this.x.write(bArr, 0, bArr.length);
                this.x.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            TXCLog.e("check1", "" + this.y);
        }
        a(this.m);
        if (this.d != null) {
            this.d.updateTexImage();
        }
    }

    private void a(Queue<Runnable> queue) {
        if (queue == null) {
            TXCLog.e(this.w, "runAll queue is null!");
            return;
        }
        synchronized (queue) {
            while (!queue.isEmpty()) {
                ((Runnable) queue.poll()).run();
            }
        }
    }

    public void onPreviewFrame(byte[] bArr, Camera camera) {
        Size previewSize = camera.getParameters().getPreviewSize();
        if (this.g == null && previewSize != null) {
            this.g = IntBuffer.allocate(previewSize.height * previewSize.width);
        }
        if (this.l.isEmpty()) {
            a(new Runnable() {
                public void run() {
                }
            });
        }
    }

    private void a() {
        float[] fArr;
        float f = (float) this.h;
        float f2 = (float) this.i;
        if (this.n == g.ROTATION_270 || this.n == g.ROTATION_90) {
            f = (float) this.i;
            f2 = (float) this.h;
        }
        float max = Math.max(f / ((float) this.j), f2 / ((float) this.k));
        float round = ((float) Math.round(((float) this.j) * max)) / f;
        float round2 = ((float) Math.round(max * ((float) this.k))) / f2;
        float[] fArr2 = a;
        float[] a = h.a(this.n, this.o, this.p);
        if (this.q == a.CENTER_CROP) {
            round = (1.0f - (1.0f / round)) / 2.0f;
            round2 = (1.0f - (1.0f / round2)) / 2.0f;
            fArr = new float[]{a(a[0], round), a(a[1], round2), a(a[2], round), a(a[3], round2), a(a[4], round), a(a[5], round2), a(a[6], round), a(a[7], round2)};
            a = fArr2;
        } else {
            float[] fArr3 = a;
            a = new float[]{a[0] / round2, a[1] / round, a[2] / round2, a[3] / round, a[4] / round2, a[5] / round, a[6] / round2, a[7] / round};
            fArr = fArr3;
        }
        fArr[0] = 0.0f;
        fArr[1] = 1.0f;
        fArr[2] = 1.0f;
        fArr[3] = 1.0f;
        fArr[4] = 0.0f;
        fArr[5] = 0.0f;
        fArr[6] = 1.0f;
        fArr[7] = 0.0f;
        this.e.clear();
        this.e.put(a).position(0);
        this.f.clear();
        this.f.put(fArr).position(0);
    }

    private float a(float f, float f2) {
        return f == 0.0f ? f2 : 1.0f - f2;
    }

    protected void a(Runnable runnable) {
        synchronized (this.l) {
            this.l.add(runnable);
        }
    }
}
