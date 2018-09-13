package com.tencent.liteav.beauty.b.a;

import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.beauty.NativeLoad;

/* compiled from: TXCTILSkinFilter */
public class c extends d {
    public boolean c() {
        NativeLoad.getInstance();
        this.a = NativeLoad.nativeLoadGLProgram(6);
        if (this.a == 0 || !a()) {
            this.g = false;
        } else {
            this.g = true;
        }
        d();
        return this.g;
    }
}
