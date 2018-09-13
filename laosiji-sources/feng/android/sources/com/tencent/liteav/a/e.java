package com.tencent.liteav.a;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import com.tencent.liteav.l.a;
import java.util.List;

@TargetApi(17)
/* compiled from: TXCombineProcess */
public class e {
    private a a;
    private List<com.tencent.liteav.i.a.a> b;
    private int c;
    private int d;

    public e(Context context) {
        this.a = new a(context);
    }

    public void a(List<com.tencent.liteav.i.a.a> list, int i, int i2) {
        this.b = list;
        this.c = i;
        this.d = i2;
    }

    public int a(int i, int i2, com.tencent.liteav.c.e eVar, com.tencent.liteav.c.e eVar2) {
        if (this.b.size() < 2) {
            Log.w("TXCombineProcess", "join picture must has two TXAbsoluteRect!!!");
            return -1;
        }
        com.tencent.liteav.i.a.a aVar = (com.tencent.liteav.i.a.a) this.b.get(0);
        com.tencent.liteav.basic.f.a[] aVarArr = new com.tencent.liteav.basic.f.a[2];
        com.tencent.liteav.basic.f.a aVar2 = new com.tencent.liteav.basic.f.a();
        aVar2.a = i;
        aVar2.b = 0;
        aVar2.c = aVar.c;
        aVar2.d = aVar.d;
        aVar2.f = new com.tencent.liteav.basic.c.a(0, 0, aVar.c, aVar.d);
        aVar2.g = new com.tencent.liteav.basic.c.a(aVar.a, aVar.b, aVar.c, aVar.d);
        aVarArr[0] = aVar2;
        aVar = (com.tencent.liteav.i.a.a) this.b.get(1);
        aVar2 = new com.tencent.liteav.basic.f.a();
        aVar2.a = i2;
        aVar2.b = 0;
        aVar2.c = aVar.c;
        aVar2.d = aVar.d;
        aVar2.f = new com.tencent.liteav.basic.c.a(0, 0, aVar.c, aVar.d);
        aVar2.g = new com.tencent.liteav.basic.c.a(aVar.a, aVar.b, aVar.c, aVar.d);
        aVarArr[1] = aVar2;
        this.a.a(this.c, this.d);
        this.a.b(this.c, this.d);
        return this.a.a(aVarArr, 0);
    }

    public void a() {
        if (this.a != null) {
            this.a.a();
        }
    }
}
