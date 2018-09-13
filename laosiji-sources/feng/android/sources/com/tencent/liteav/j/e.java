package com.tencent.liteav.j;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLES20;
import android.os.Environment;
import com.tencent.liteav.basic.c.d;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: TestUtils */
public class e {
    public static FileOutputStream a = null;
    public static FileOutputStream b = null;
    public static FileOutputStream c = null;
    private static final String d = Environment.getExternalStorageDirectory().getPath();
    private static final String e = (d + File.separator + "src.h264");
    private static final String f = (d + File.separator + "src.aac");
    private static final String g = (d + File.separator + "src.pcm");

    public static Bitmap a(int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
        Buffer order = ByteBuffer.allocate((i2 * i3) * 4).order(ByteOrder.nativeOrder());
        order.position(0);
        d dVar = new d();
        dVar.c();
        GLES20.glViewport(0, 0, i2, i3);
        dVar.b(i);
        GLES20.glReadPixels(0, 0, i2, i3, 6408, 5121, order);
        createBitmap.copyPixelsFromBuffer(order);
        dVar.e();
        return createBitmap;
    }
}
