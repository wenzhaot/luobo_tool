package com.baidu.platform.comapi.map;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MapRenderer implements Renderer {
    private static final String d = MapRenderer.class.getSimpleName();
    public int a;
    public int b;
    public int c;
    private long e;
    private a f;
    private final j g;

    public interface a {
        void f();
    }

    public MapRenderer(j jVar, a aVar) {
        this.f = aVar;
        this.g = jVar;
    }

    private void a(GL10 gl10) {
        GLES20.glClear(16640);
        GLES20.glClearColor(0.85f, 0.8f, 0.8f, 0.0f);
    }

    private boolean a() {
        return this.e != 0;
    }

    public static native void nativeInit(long j);

    public static native int nativeRender(long j);

    public static native void nativeResize(long j, int i, int i2);

    public void a(long j) {
        this.e = j;
    }

    public void onDrawFrame(GL10 gl10) {
        if (a()) {
            if (this.c <= 1) {
                nativeResize(this.e, this.a, this.b);
                this.c++;
            }
            this.f.f();
            int nativeRender = nativeRender(this.e);
            for (l a : this.g.a().f) {
                a.a(gl10, this.g.a().I());
            }
            j jVar = this.g;
            if (nativeRender == 1) {
                jVar.requestRender();
                return;
            } else if (this.g.a().b()) {
                if (jVar.getRenderMode() != 1) {
                    jVar.setRenderMode(1);
                    return;
                }
                return;
            } else if (jVar.getRenderMode() != 0) {
                jVar.setRenderMode(0);
                return;
            } else {
                return;
            }
        }
        a(gl10);
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        if (this.e != 0) {
            nativeResize(this.e, i, i2);
        }
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        nativeInit(this.e);
        if (a()) {
            this.f.f();
        }
    }
}
