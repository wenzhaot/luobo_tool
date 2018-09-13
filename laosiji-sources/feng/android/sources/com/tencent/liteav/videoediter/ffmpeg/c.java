package com.tencent.liteav.videoediter.ffmpeg;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.view.Surface;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.f.b;
import com.tencent.liteav.videoediter.ffmpeg.jni.FFDecodedFrame;
import com.tencent.liteav.videoediter.ffmpeg.jni.TXFFAudioDecoderJNI;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(16)
/* compiled from: TXSWAudioDecoder */
public class c implements b {
    public static String[] a = new String[]{"audio/mp4a-latm", "audio/mpeg"};
    private ByteBuffer b;
    private int c;
    private int d;
    private int e;
    private TXFFAudioDecoderJNI f;
    private List<e> g;
    private AtomicBoolean h;
    private FFDecodedFrame i;

    public static boolean a(String str) {
        for (String equals : a) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private int b(java.lang.String r5) {
        /*
        r4 = this;
        r1 = 1;
        r0 = 0;
        r2 = -1;
        r3 = r5.hashCode();
        switch(r3) {
            case -53558318: goto L_0x0010;
            case 1504831518: goto L_0x001b;
            default: goto L_0x000a;
        };
    L_0x000a:
        r3 = r2;
    L_0x000b:
        switch(r3) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0026;
            default: goto L_0x000e;
        };
    L_0x000e:
        r0 = r2;
    L_0x000f:
        return r0;
    L_0x0010:
        r3 = "audio/mp4a-latm";
        r3 = r5.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x0019:
        r3 = r0;
        goto L_0x000b;
    L_0x001b:
        r3 = "audio/mpeg";
        r3 = r5.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x0024:
        r3 = r1;
        goto L_0x000b;
    L_0x0026:
        r0 = r1;
        goto L_0x000f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videoediter.ffmpeg.c.b(java.lang.String):int");
    }

    public c() {
        this.h = new AtomicBoolean(false);
        this.g = new LinkedList();
        this.g = Collections.synchronizedList(this.g);
    }

    public void a(MediaFormat mediaFormat) {
        int i = 0;
        b();
        this.c = mediaFormat.getInteger("channel-count");
        this.d = mediaFormat.getInteger("sample-rate");
        if (mediaFormat.containsKey("max-input-size")) {
            this.e = mediaFormat.getInteger("max-input-size");
        }
        ByteBuffer byteBuffer = mediaFormat.getByteBuffer("csd-0");
        if (byteBuffer != null) {
            byteBuffer.position(0);
        }
        String string = mediaFormat.getString(IMediaFormat.KEY_MIME);
        this.f = new TXFFAudioDecoderJNI();
        TXFFAudioDecoderJNI tXFFAudioDecoderJNI = this.f;
        int b = b(string);
        if (byteBuffer != null) {
            i = byteBuffer.capacity();
        }
        tXFFAudioDecoderJNI.configureInput(b, byteBuffer, i, this.d, this.c);
        b = (this.c * 1024) * 2;
        this.b = ByteBuffer.allocateDirect(b > this.e ? b : this.e);
        TXCLog.i("TXSWAudioDecoder", "createDecoderByFormat: type = " + string + ", mediaFormat = " + mediaFormat.toString() + ", calculateBufferSize = " + b + ", mMaxInputSize = " + this.e);
    }

    public void a(MediaFormat mediaFormat, Surface surface) {
    }

    public void a() {
        if (this.h.get()) {
            TXCLog.w("TXSWAudioDecoder", "start error: decoder have been started!");
            return;
        }
        this.g.clear();
        this.h.set(true);
    }

    public void b() {
        if (this.h.get()) {
            this.g.clear();
            if (this.f != null) {
                this.f.release();
                this.f = null;
            }
            this.h.set(false);
            return;
        }
        TXCLog.w("TXSWAudioDecoder", "stop error: decoder isn't starting yet!!");
    }

    public e c() {
        if (this.h.get()) {
            this.b.position(0);
            e eVar = new e();
            eVar.a(this.b);
            eVar.h(this.c);
            eVar.g(this.d);
            eVar.d(this.b.capacity());
            return eVar;
        }
        TXCLog.w("TXSWAudioDecoder", "find frame error: decoder isn't starting yet!!");
        return null;
    }

    public void a(e eVar) {
        if (this.h.get()) {
            if (eVar.f() == 1) {
                byte[] a = a(eVar.b(), eVar.g());
                if (a == null) {
                    this.i = null;
                    return;
                }
                this.i = this.f.decode(a, eVar.e(), eVar.f());
            } else if (eVar.f() == 4) {
                this.i = new FFDecodedFrame();
                this.i.data = new byte[0];
                this.i.flags = 4;
                this.i.pts = eVar.e();
            }
            eVar.a(null);
            eVar.d(0);
            this.b.position(0);
            return;
        }
        TXCLog.e("TXSWAudioDecoder", "decode error: decoder isn't starting yet!!");
    }

    private byte[] a(ByteBuffer byteBuffer, int i) {
        byte[] bArr = new byte[i];
        try {
            byteBuffer.get(bArr);
            return bArr;
        } catch (BufferUnderflowException e) {
            e.printStackTrace();
            return null;
        }
    }

    public e d() {
        if (!this.h.get()) {
            TXCLog.e("TXSWAudioDecoder", "decode error: decoder isn't starting yet!!");
            return null;
        } else if (this.i == null || this.i.data == null) {
            return null;
        } else {
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.i.data.length);
            allocateDirect.put(this.i.data);
            allocateDirect.position(0);
            e eVar = new e();
            eVar.a(allocateDirect);
            eVar.d(this.i.data.length);
            eVar.a(this.i.pts);
            eVar.c(this.i.flags);
            eVar.h(this.c);
            eVar.g(this.i.sampleRate);
            this.i = null;
            return eVar;
        }
    }
}
