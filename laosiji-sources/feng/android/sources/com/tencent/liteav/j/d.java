package com.tencent.liteav.j;

import com.tencent.liteav.i.a.i;
import java.util.List;

/* compiled from: TXSpeedUtils */
public class d {
    public static float a(int i) {
        switch (i) {
            case 0:
                return 0.25f;
            case 1:
                return 0.5f;
            case 3:
                return 2.0f;
            case 4:
                return 4.0f;
            default:
                return 1.0f;
        }
    }

    public static float a(long j, List<i> list) {
        if (list != null) {
            for (i iVar : list) {
                if (j > iVar.b && j < iVar.c) {
                    return a(iVar.a);
                }
            }
        }
        return 1.0f;
    }
}
