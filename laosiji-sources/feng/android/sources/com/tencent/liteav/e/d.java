package com.tencent.liteav.e;

import android.graphics.SurfaceTexture;
import android.opengl.GLUtils;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* compiled from: EglCore */
public class d {
    private int a = 12440;
    private int b = 4;
    private EGL10 c;
    private EGLDisplay d;
    private EGLConfig e;
    private EGLSurface f;
    private EGLContext g;

    public void a(SurfaceTexture surfaceTexture) {
        this.c = (EGL10) EGLContext.getEGL();
        this.d = this.c.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.c.eglInitialize(this.d, new int[2]);
        this.e = c();
        this.f = this.c.eglCreateWindowSurface(this.d, this.e, surfaceTexture, null);
        this.g = a(this.c, this.d, this.e, EGL10.EGL_NO_CONTEXT);
        if (this.f == null || this.f == EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("GL error:" + GLUtils.getEGLErrorString(this.c.eglGetError()));
        } else if (!this.c.eglMakeCurrent(this.d, this.f, this.f, this.g)) {
            throw new RuntimeException("GL Make current Error" + GLUtils.getEGLErrorString(this.c.eglGetError()));
        }
    }

    public void a() {
        if (this.c != null) {
            this.c.eglMakeCurrent(this.d, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.c.eglDestroyContext(this.d, this.g);
            this.c.eglDestroySurface(this.d, this.f);
            this.c.eglTerminate(this.d);
            this.c = null;
        }
    }

    private EGLContext a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, EGLContext eGLContext) {
        return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, new int[]{this.a, 2, 12344});
    }

    private EGLConfig c() {
        int[] iArr = new int[1];
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (!this.c.eglChooseConfig(this.d, d(), eGLConfigArr, 1, iArr)) {
            throw new IllegalArgumentException("Failed to choose config:" + GLUtils.getEGLErrorString(this.c.eglGetError()));
        } else if (iArr[0] > 0) {
            return eGLConfigArr[0];
        } else {
            return null;
        }
    }

    private int[] d() {
        return new int[]{12352, this.b, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344};
    }

    public void b() {
        if (this.c != null && this.d != null && this.f != null) {
            this.c.eglSwapBuffers(this.d, this.f);
        }
    }
}
