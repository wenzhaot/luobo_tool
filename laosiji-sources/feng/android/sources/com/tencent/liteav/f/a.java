package com.tencent.liteav.f;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import java.nio.ByteBuffer;

/* compiled from: Editer */
public class a {

    /* compiled from: Editer */
    public interface g {
        void e();
    }

    /* compiled from: Editer */
    public interface b {
        void a(com.tencent.liteav.c.e eVar);

        void b(com.tencent.liteav.c.e eVar);
    }

    /* compiled from: Editer */
    public interface i {
        void a(com.tencent.liteav.c.e eVar);
    }

    /* compiled from: Editer */
    public interface a {
        void a(MediaFormat mediaFormat);

        void a(String str);

        void a(String str, ByteBuffer byteBuffer, BufferInfo bufferInfo);
    }

    /* compiled from: Editer */
    public interface c {
        void a(float f);

        void a(int i, String str);
    }

    /* compiled from: Editer */
    public interface d {
        void c(com.tencent.liteav.c.e eVar);
    }

    /* compiled from: Editer */
    public interface e {
        void onJoinDecodeCompletion();
    }

    /* compiled from: Editer */
    public interface f {
        void onPreviewCompletion();
    }

    /* compiled from: Editer */
    public interface h {
        void a();
    }
}
