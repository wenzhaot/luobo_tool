package com.tencent.liteav.a;

import android.content.Context;
import android.opengl.EGLContext;
import android.support.annotation.RequiresApi;
import com.tencent.liteav.a.a.b;
import com.tencent.liteav.a.a.c;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.i.a.d;
import com.tencent.liteav.i.c.a;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiresApi(api = 16)
/* compiled from: TXCombineVideo */
public class f {
    private Context a;
    private a b;
    private d c = new d(this.a);
    private c d = new c(this.a);
    private c e;
    private b f;

    static {
        com.tencent.liteav.basic.util.a.d();
    }

    public f(Context context) {
        this.a = context;
        c();
    }

    private void c() {
        this.e = new c() {
            public void a(EGLContext eGLContext) {
                f.this.c.a(f.this.d.b());
                f.this.c.b(f.this.d.a());
                f.this.c.a(eGLContext);
            }

            public void a(int i, int i2, int i3, e eVar) {
                f.this.c.a(i, i2, i3, eVar);
            }

            public void a(e eVar) {
                f.this.c.a(eVar);
            }

            public void b(e eVar) {
                f.this.c.b(eVar);
            }

            public void c(e eVar) {
                f.this.c.c(eVar);
            }
        };
        this.f = new b() {
            public void a(float f) {
                if (f.this.b != null) {
                    f.this.b.a(f);
                }
            }

            public void a(int i, String str) {
                TXCLog.e("TXCombineVideo", "===onEncodedComplete===");
                if (f.this.b != null) {
                    d dVar = new d();
                    dVar.a = i;
                    dVar.b = str;
                    if (i == 0) {
                        f.this.b.a(1.0f);
                    }
                    f.this.b.a(dVar);
                }
            }
        };
        this.d.a(this.e);
        this.c.a(this.f);
    }

    public void a(a aVar) {
        this.b = aVar;
    }

    public void a(List<String> list) {
        this.d.a((List) list);
    }

    public void a(long j) {
        TXCLog.e("TXCombineVideo", "duration:" + j);
        this.c.a(j);
    }

    public void a(int i) {
    }

    public void a(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.c.a(str);
    }

    public void a() {
        this.d.c();
    }

    public void b() {
        this.d.d();
        this.c.a();
    }

    public void a(List<com.tencent.liteav.i.a.a> list, int i, int i2) {
        int i3 = ((i + 15) / 16) * 16;
        int i4 = ((i2 + 15) / 16) * 16;
        this.c.a(i3, i4);
        this.d.a((List) list, i3, i4);
    }
}
