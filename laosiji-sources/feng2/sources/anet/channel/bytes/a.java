package anet.channel.bytes;

import android.support.v4.media.session.PlaybackStateCompat;
import anet.channel.util.ALog;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;

/* compiled from: Taobao */
public class a {
    public static final int MAX_POOL_SIZE = 524288;
    public static final String TAG = "awcn.ByteArrayPool";
    private final TreeSet<ByteArray> a = new TreeSet();
    private final ByteArray b = ByteArray.create(0);
    private final Random c = new Random();
    private long d = 0;
    private long e = 0;

    /* compiled from: Taobao */
    static class a {
        public static a a = new a();

        a() {
        }
    }

    public synchronized void a(ByteArray byteArray) {
        if (byteArray != null) {
            if (byteArray.bufferLength < 524288) {
                this.d += (long) byteArray.bufferLength;
                this.a.add(byteArray);
                while (this.d > PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                    ByteArray byteArray2;
                    if (this.c.nextBoolean()) {
                        byteArray2 = (ByteArray) this.a.pollFirst();
                    } else {
                        byteArray2 = (ByteArray) this.a.pollLast();
                    }
                    this.d -= (long) byteArray2.bufferLength;
                }
                if (ALog.isPrintLog(1)) {
                    ALog.d(TAG, "ByteArray Pool refund", null, "refund", Integer.valueOf(byteArray.getBufferLength()), "total", Long.valueOf(this.d));
                }
            }
        }
    }

    public synchronized ByteArray a(int i) {
        ByteArray create;
        if (i >= 524288) {
            create = ByteArray.create(i);
        } else {
            this.b.bufferLength = i;
            create = (ByteArray) this.a.ceiling(this.b);
            if (create == null) {
                create = ByteArray.create(i);
            } else {
                Arrays.fill(create.buffer, (byte) 0);
                create.dataLength = 0;
                this.a.remove(create);
                this.d -= (long) create.bufferLength;
                this.e += (long) i;
                if (ALog.isPrintLog(1)) {
                    ALog.d(TAG, "ByteArray Pool retrieve", null, "retrieve", Integer.valueOf(i), "reused", Long.valueOf(this.e));
                }
            }
        }
        return create;
    }

    public ByteArray a(byte[] bArr, int i) {
        ByteArray a = a(i);
        System.arraycopy(bArr, 0, a.buffer, 0, i);
        a.dataLength = i;
        return a;
    }
}
