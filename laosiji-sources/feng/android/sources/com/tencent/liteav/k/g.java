package com.tencent.liteav.k;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.b.ai;
import com.tencent.liteav.beauty.b.s;
import com.tencent.liteav.k.n.h;
import java.io.IOException;

/* compiled from: TXCGPULightingFilter */
public class g {
    private ai a = null;
    private s b = null;
    private Context c = null;
    private String d = "Lighting";
    private h e = null;

    public g(Context context) {
        this.c = context;
    }

    public boolean a(int i, int i2) {
        return c(i, i2);
    }

    private boolean c(int i, int i2) {
        if (this.c == null) {
            TXCLog.e(this.d, "mContext is null!");
            return false;
        }
        AssetManager assets = this.c.getResources().getAssets();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(assets.open("fennen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.a == null) {
            this.a = new ai(bitmap);
            this.a.a(true);
            if (!this.a.c()) {
                TXCLog.e(this.d, "mLoopupInvertFilter init error!");
                return false;
            }
        }
        this.a.a(i, i2);
        try {
            bitmap = BitmapFactory.decodeStream(assets.open("qingliang.png"));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (this.b == null) {
            this.b = new s(bitmap);
            this.b.a(true);
            if (!this.b.c()) {
                TXCLog.e(this.d, "mLoopupFilter init error!");
                return false;
            }
        }
        this.b.a(i, i2);
        return true;
    }

    public void a() {
        if (this.a != null) {
            this.a.e();
            this.a = null;
        }
        if (this.b != null) {
            this.b.e();
            this.b = null;
        }
    }

    public void b(int i, int i2) {
        c(i, i2);
    }

    public void a(h hVar) {
        this.e = hVar;
        if (this.e != null) {
            if (this.a != null) {
                this.a.a(this.e.a / 5.0f);
                this.a.b(this.e.a * 1.5f);
            }
            if (this.b != null) {
                this.b.a(this.e.a / 5.0f);
            }
        }
    }

    public int a(int i) {
        if (this.e == null || this.e.a <= 0.0f) {
            return i;
        }
        if (this.a != null) {
            i = this.a.a(i);
        }
        if (this.b != null) {
            return this.b.a(i);
        }
        return i;
    }

    public void b() {
        a();
    }
}
