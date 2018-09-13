package com.meizu.cloud.pushsdk.networking.okio;

import com.umeng.commonsdk.proguard.ar;
import com.umeng.message.proguard.l;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Buffer implements BufferedSource, BufferedSink, Cloneable {
    private static final byte[] DIGITS = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102};
    static final int REPLACEMENT_CHARACTER = 65533;
    Segment head;
    long size;

    public long size() {
        return this.size;
    }

    public Buffer buffer() {
        return this;
    }

    public OutputStream outputStream() {
        return new OutputStream() {
            public void write(int b) {
                Buffer.this.writeByte((byte) b);
            }

            public void write(byte[] data, int offset, int byteCount) {
                Buffer.this.write(data, offset, byteCount);
            }

            public void flush() {
            }

            public void close() {
            }

            public String toString() {
                return this + ".outputStream()";
            }
        };
    }

    public Buffer emitCompleteSegments() {
        return this;
    }

    public BufferedSink emit() {
        return this;
    }

    public boolean exhausted() {
        return this.size == 0;
    }

    public void require(long byteCount) throws EOFException {
        if (this.size < byteCount) {
            throw new EOFException();
        }
    }

    public boolean request(long byteCount) {
        return this.size >= byteCount;
    }

    public InputStream inputStream() {
        return new InputStream() {
            public int read() {
                if (Buffer.this.size > 0) {
                    return Buffer.this.readByte() & 255;
                }
                return -1;
            }

            public int read(byte[] sink, int offset, int byteCount) {
                return Buffer.this.read(sink, offset, byteCount);
            }

            public int available() {
                return (int) Math.min(Buffer.this.size, 2147483647L);
            }

            public void close() {
            }

            public String toString() {
                return Buffer.this + ".inputStream()";
            }
        };
    }

    public Buffer copyTo(OutputStream out) throws IOException {
        return copyTo(out, 0, this.size);
    }

    public Buffer copyTo(OutputStream out, long offset, long byteCount) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, offset, byteCount);
        if (byteCount != 0) {
            Segment s = this.head;
            while (offset >= ((long) (s.limit - s.pos))) {
                offset -= (long) (s.limit - s.pos);
                s = s.next;
            }
            while (byteCount > 0) {
                int pos = (int) (((long) s.pos) + offset);
                int toCopy = (int) Math.min((long) (s.limit - pos), byteCount);
                out.write(s.data, pos, toCopy);
                byteCount -= (long) toCopy;
                offset = 0;
                s = s.next;
            }
        }
        return this;
    }

    public Buffer copyTo(Buffer out, long offset, long byteCount) {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, offset, byteCount);
        if (byteCount != 0) {
            out.size += byteCount;
            Segment s = this.head;
            while (offset >= ((long) (s.limit - s.pos))) {
                offset -= (long) (s.limit - s.pos);
                s = s.next;
            }
            while (byteCount > 0) {
                Segment copy = new Segment(s);
                copy.pos = (int) (((long) copy.pos) + offset);
                copy.limit = Math.min(copy.pos + ((int) byteCount), copy.limit);
                if (out.head == null) {
                    copy.prev = copy;
                    copy.next = copy;
                    out.head = copy;
                } else {
                    out.head.prev.push(copy);
                }
                byteCount -= (long) (copy.limit - copy.pos);
                offset = 0;
                s = s.next;
            }
        }
        return this;
    }

    public Buffer writeTo(OutputStream out) throws IOException {
        return writeTo(out, this.size);
    }

    public Buffer writeTo(OutputStream out, long byteCount) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, 0, byteCount);
        Segment s = this.head;
        while (byteCount > 0) {
            int toCopy = (int) Math.min(byteCount, (long) (s.limit - s.pos));
            out.write(s.data, s.pos, toCopy);
            s.pos += toCopy;
            this.size -= (long) toCopy;
            byteCount -= (long) toCopy;
            if (s.pos == s.limit) {
                Segment toRecycle = s;
                s = toRecycle.pop();
                this.head = s;
                SegmentPool.recycle(toRecycle);
            }
        }
        return this;
    }

    public Buffer readFrom(InputStream in) throws IOException {
        readFrom(in, Long.MAX_VALUE, true);
        return this;
    }

    public Buffer readFrom(InputStream in, long byteCount) throws IOException {
        if (byteCount < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
        }
        readFrom(in, byteCount, false);
        return this;
    }

    private void readFrom(InputStream in, long byteCount, boolean forever) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("in == null");
        }
        while (true) {
            if (byteCount > 0 || forever) {
                Segment tail = writableSegment(1);
                int bytesRead = in.read(tail.data, tail.limit, (int) Math.min(byteCount, (long) (2048 - tail.limit)));
                if (bytesRead != -1) {
                    tail.limit += bytesRead;
                    this.size += (long) bytesRead;
                    byteCount -= (long) bytesRead;
                } else if (!forever) {
                    throw new EOFException();
                } else {
                    return;
                }
            }
            return;
        }
    }

    public long completeSegmentByteCount() {
        long result = this.size;
        if (result == 0) {
            return 0;
        }
        Segment tail = this.head.prev;
        if (tail.limit < 2048 && tail.owner) {
            result -= (long) (tail.limit - tail.pos);
        }
        return result;
    }

    public byte readByte() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        int pos2 = pos + 1;
        byte b = segment.data[pos];
        this.size--;
        if (pos2 == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos2;
        }
        return b;
    }

    public byte getByte(long pos) {
        Util.checkOffsetAndCount(this.size, pos, 1);
        Segment s = this.head;
        while (true) {
            int segmentByteCount = s.limit - s.pos;
            if (pos < ((long) segmentByteCount)) {
                return s.data[s.pos + ((int) pos)];
            }
            pos -= (long) segmentByteCount;
            s = s.next;
        }
    }

    public short readShort() {
        if (this.size < 2) {
            throw new IllegalStateException("size < 2: " + this.size);
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 2) {
            return (short) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        pos = pos2 + 1;
        int s = ((data[pos] & 255) << 8) | (data[pos2] & 255);
        this.size -= 2;
        if (pos == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos;
        }
        return (short) s;
    }

    public int readInt() {
        if (this.size < 4) {
            throw new IllegalStateException("size < 4: " + this.size);
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 4) {
            return ((((readByte() & 255) << 24) | ((readByte() & 255) << 16)) | ((readByte() & 255) << 8)) | (readByte() & 255);
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        pos = pos2 + 1;
        pos2 = pos + 1;
        pos = pos2 + 1;
        int i = ((((data[pos] & 255) << 24) | ((data[pos2] & 255) << 16)) | ((data[pos] & 255) << 8)) | (data[pos2] & 255);
        this.size -= 4;
        if (pos == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            return i;
        }
        segment.pos = pos;
        return i;
    }

    public long readLong() {
        if (this.size < 8) {
            throw new IllegalStateException("size < 8: " + this.size);
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 8) {
            return ((((long) readInt()) & 4294967295L) << 32) | (((long) readInt()) & 4294967295L);
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        pos = pos2 + 1;
        pos2 = pos + 1;
        pos = pos2 + 1;
        pos2 = pos + 1;
        pos = pos2 + 1;
        pos2 = pos + 1;
        pos = pos2 + 1;
        long v = ((((((((((long) data[pos]) & 255) << 56) | ((((long) data[pos2]) & 255) << 48)) | ((((long) data[pos]) & 255) << 40)) | ((((long) data[pos2]) & 255) << 32)) | ((((long) data[pos]) & 255) << 24)) | ((((long) data[pos2]) & 255) << 16)) | ((((long) data[pos]) & 255) << 8)) | (((long) data[pos2]) & 255);
        this.size -= 8;
        if (pos == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            return v;
        }
        segment.pos = pos;
        return v;
    }

    public short readShortLe() {
        return Util.reverseBytesShort(readShort());
    }

    public int readIntLe() {
        return Util.reverseBytesInt(readInt());
    }

    public long readLongLe() {
        return Util.reverseBytesLong(readLong());
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00e1 A:{SYNTHETIC, EDGE_INSN: B:43:0x00e1->B:37:0x00e1 ?: BREAK  } */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00d9  */
    public long readDecimalLong() {
        /*
        r24 = this;
        r0 = r24;
        r0 = r0.size;
        r20 = r0;
        r22 = 0;
        r20 = (r20 > r22 ? 1 : (r20 == r22 ? 0 : -1));
        if (r20 != 0) goto L_0x0015;
    L_0x000c:
        r20 = new java.lang.IllegalStateException;
        r21 = "size == 0";
        r20.<init>(r21);
        throw r20;
    L_0x0015:
        r18 = 0;
        r16 = 0;
        r10 = 0;
        r8 = 0;
        r14 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r12 = -7;
    L_0x0022:
        r0 = r24;
        r0 = r0.head;
        r17 = r0;
        r0 = r17;
        r6 = r0.data;
        r0 = r17;
        r11 = r0.pos;
        r0 = r17;
        r9 = r0.limit;
    L_0x0034:
        if (r11 >= r9) goto L_0x00c8;
    L_0x0036:
        r4 = r6[r11];
        r20 = 48;
        r0 = r20;
        if (r4 < r0) goto L_0x0099;
    L_0x003e:
        r20 = 57;
        r0 = r20;
        if (r4 > r0) goto L_0x0099;
    L_0x0044:
        r7 = 48 - r4;
        r20 = (r18 > r14 ? 1 : (r18 == r14 ? 0 : -1));
        if (r20 < 0) goto L_0x0055;
    L_0x004a:
        r20 = (r18 > r14 ? 1 : (r18 == r14 ? 0 : -1));
        if (r20 != 0) goto L_0x008b;
    L_0x004e:
        r0 = (long) r7;
        r20 = r0;
        r20 = (r20 > r12 ? 1 : (r20 == r12 ? 0 : -1));
        if (r20 >= 0) goto L_0x008b;
    L_0x0055:
        r20 = new com.meizu.cloud.pushsdk.networking.okio.Buffer;
        r20.<init>();
        r0 = r20;
        r1 = r18;
        r20 = r0.writeDecimalLong(r1);
        r0 = r20;
        r5 = r0.writeByte(r4);
        if (r10 != 0) goto L_0x006d;
    L_0x006a:
        r5.readByte();
    L_0x006d:
        r20 = new java.lang.NumberFormatException;
        r21 = new java.lang.StringBuilder;
        r21.<init>();
        r22 = "Number too large: ";
        r21 = r21.append(r22);
        r22 = r5.readUtf8();
        r21 = r21.append(r22);
        r21 = r21.toString();
        r20.<init>(r21);
        throw r20;
    L_0x008b:
        r20 = 10;
        r18 = r18 * r20;
        r0 = (long) r7;
        r20 = r0;
        r18 = r18 + r20;
    L_0x0094:
        r11 = r11 + 1;
        r16 = r16 + 1;
        goto L_0x0034;
    L_0x0099:
        r20 = 45;
        r0 = r20;
        if (r4 != r0) goto L_0x00a7;
    L_0x009f:
        if (r16 != 0) goto L_0x00a7;
    L_0x00a1:
        r10 = 1;
        r20 = 1;
        r12 = r12 - r20;
        goto L_0x0094;
    L_0x00a7:
        if (r16 != 0) goto L_0x00c7;
    L_0x00a9:
        r20 = new java.lang.NumberFormatException;
        r21 = new java.lang.StringBuilder;
        r21.<init>();
        r22 = "Expected leading [0-9] or '-' character but was 0x";
        r21 = r21.append(r22);
        r22 = java.lang.Integer.toHexString(r4);
        r21 = r21.append(r22);
        r21 = r21.toString();
        r20.<init>(r21);
        throw r20;
    L_0x00c7:
        r8 = 1;
    L_0x00c8:
        if (r11 != r9) goto L_0x00f7;
    L_0x00ca:
        r20 = r17.pop();
        r0 = r20;
        r1 = r24;
        r1.head = r0;
        com.meizu.cloud.pushsdk.networking.okio.SegmentPool.recycle(r17);
    L_0x00d7:
        if (r8 != 0) goto L_0x00e1;
    L_0x00d9:
        r0 = r24;
        r0 = r0.head;
        r20 = r0;
        if (r20 != 0) goto L_0x0022;
    L_0x00e1:
        r0 = r24;
        r0 = r0.size;
        r20 = r0;
        r0 = r16;
        r0 = (long) r0;
        r22 = r0;
        r20 = r20 - r22;
        r0 = r20;
        r2 = r24;
        r2.size = r0;
        if (r10 == 0) goto L_0x00fc;
    L_0x00f6:
        return r18;
    L_0x00f7:
        r0 = r17;
        r0.pos = r11;
        goto L_0x00d7;
    L_0x00fc:
        r0 = r18;
        r0 = -r0;
        r18 = r0;
        goto L_0x00f6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.networking.okio.Buffer.readDecimalLong():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b3 A:{SYNTHETIC, EDGE_INSN: B:39:0x00b3->B:35:0x00b3 ?: BREAK  } */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ad  */
    public long readHexadecimalUnsignedLong() {
        /*
        r18 = this;
        r0 = r18;
        r14 = r0.size;
        r16 = 0;
        r11 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r11 != 0) goto L_0x0013;
    L_0x000a:
        r11 = new java.lang.IllegalStateException;
        r14 = "size == 0";
        r11.<init>(r14);
        throw r11;
    L_0x0013:
        r12 = 0;
        r9 = 0;
        r6 = 0;
    L_0x0017:
        r0 = r18;
        r10 = r0.head;
        r4 = r10.data;
        r8 = r10.pos;
        r7 = r10.limit;
    L_0x0021:
        if (r8 >= r7) goto L_0x009e;
    L_0x0023:
        r2 = r4[r8];
        r11 = 48;
        if (r2 < r11) goto L_0x0063;
    L_0x0029:
        r11 = 57;
        if (r2 > r11) goto L_0x0063;
    L_0x002d:
        r5 = r2 + -48;
    L_0x002f:
        r14 = -1152921504606846976; // 0xf000000000000000 float:0.0 double:-3.105036184601418E231;
        r14 = r14 & r12;
        r16 = 0;
        r11 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r11 == 0) goto L_0x00c1;
    L_0x0038:
        r11 = new com.meizu.cloud.pushsdk.networking.okio.Buffer;
        r11.<init>();
        r11 = r11.writeHexadecimalUnsignedLong(r12);
        r3 = r11.writeByte(r2);
        r11 = new java.lang.NumberFormatException;
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r15 = "Number too large: ";
        r14 = r14.append(r15);
        r15 = r3.readUtf8();
        r14 = r14.append(r15);
        r14 = r14.toString();
        r11.<init>(r14);
        throw r11;
    L_0x0063:
        r11 = 97;
        if (r2 < r11) goto L_0x0070;
    L_0x0067:
        r11 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        if (r2 > r11) goto L_0x0070;
    L_0x006b:
        r11 = r2 + -97;
        r5 = r11 + 10;
        goto L_0x002f;
    L_0x0070:
        r11 = 65;
        if (r2 < r11) goto L_0x007d;
    L_0x0074:
        r11 = 70;
        if (r2 > r11) goto L_0x007d;
    L_0x0078:
        r11 = r2 + -65;
        r5 = r11 + 10;
        goto L_0x002f;
    L_0x007d:
        if (r9 != 0) goto L_0x009d;
    L_0x007f:
        r11 = new java.lang.NumberFormatException;
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r15 = "Expected leading [0-9a-fA-F] character but was 0x";
        r14 = r14.append(r15);
        r15 = java.lang.Integer.toHexString(r2);
        r14 = r14.append(r15);
        r14 = r14.toString();
        r11.<init>(r14);
        throw r11;
    L_0x009d:
        r6 = 1;
    L_0x009e:
        if (r8 != r7) goto L_0x00cb;
    L_0x00a0:
        r11 = r10.pop();
        r0 = r18;
        r0.head = r11;
        com.meizu.cloud.pushsdk.networking.okio.SegmentPool.recycle(r10);
    L_0x00ab:
        if (r6 != 0) goto L_0x00b3;
    L_0x00ad:
        r0 = r18;
        r11 = r0.head;
        if (r11 != 0) goto L_0x0017;
    L_0x00b3:
        r0 = r18;
        r14 = r0.size;
        r0 = (long) r9;
        r16 = r0;
        r14 = r14 - r16;
        r0 = r18;
        r0.size = r14;
        return r12;
    L_0x00c1:
        r11 = 4;
        r12 = r12 << r11;
        r14 = (long) r5;
        r12 = r12 | r14;
        r8 = r8 + 1;
        r9 = r9 + 1;
        goto L_0x0021;
    L_0x00cb:
        r10.pos = r8;
        goto L_0x00ab;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.networking.okio.Buffer.readHexadecimalUnsignedLong():long");
    }

    public ByteString readByteString() {
        return new ByteString(readByteArray());
    }

    public ByteString readByteString(long byteCount) throws EOFException {
        return new ByteString(readByteArray(byteCount));
    }

    public void readFully(Buffer sink, long byteCount) throws EOFException {
        if (this.size < byteCount) {
            sink.write(this, this.size);
            throw new EOFException();
        } else {
            sink.write(this, byteCount);
        }
    }

    public long readAll(Sink sink) throws IOException {
        long byteCount = this.size;
        if (byteCount > 0) {
            sink.write(this, byteCount);
        }
        return byteCount;
    }

    public String readUtf8() {
        try {
            return readString(this.size, Util.UTF_8);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String readUtf8(long byteCount) throws EOFException {
        return readString(byteCount, Util.UTF_8);
    }

    public String readString(Charset charset) {
        try {
            return readString(this.size, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String readString(long byteCount, Charset charset) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, byteCount);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (byteCount > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
        } else if (byteCount == 0) {
            return "";
        } else {
            Segment s = this.head;
            if (((long) s.pos) + byteCount > ((long) s.limit)) {
                return new String(readByteArray(byteCount), charset);
            }
            String result = new String(s.data, s.pos, (int) byteCount, charset);
            s.pos = (int) (((long) s.pos) + byteCount);
            this.size -= byteCount;
            if (s.pos != s.limit) {
                return result;
            }
            this.head = s.pop();
            SegmentPool.recycle(s);
            return result;
        }
    }

    public String readUtf8Line() throws EOFException {
        long newline = indexOf((byte) 10);
        if (newline == -1) {
            return this.size != 0 ? readUtf8(this.size) : null;
        } else {
            return readUtf8Line(newline);
        }
    }

    public String readUtf8LineStrict() throws EOFException {
        long newline = indexOf((byte) 10);
        if (newline != -1) {
            return readUtf8Line(newline);
        }
        Buffer data = new Buffer();
        copyTo(data, 0, Math.min(32, this.size));
        throw new EOFException("\\n not found: size=" + size() + " content=" + data.readByteString().hex() + "...");
    }

    String readUtf8Line(long newline) throws EOFException {
        String result;
        if (newline <= 0 || getByte(newline - 1) != ar.k) {
            result = readUtf8(newline);
            skip(1);
            return result;
        }
        result = readUtf8(newline - 1);
        skip(2);
        return result;
    }

    public int readUtf8CodePoint() throws EOFException {
        if (this.size == 0) {
            throw new EOFException();
        }
        int codePoint;
        int byteCount;
        int min;
        byte b0 = getByte(0);
        if ((b0 & 128) == 0) {
            codePoint = b0 & 127;
            byteCount = 1;
            min = 0;
        } else if ((b0 & 224) == 192) {
            codePoint = b0 & 31;
            byteCount = 2;
            min = 128;
        } else if ((b0 & 240) == 224) {
            codePoint = b0 & 15;
            byteCount = 3;
            min = 2048;
        } else if ((b0 & 248) == 240) {
            codePoint = b0 & 7;
            byteCount = 4;
            min = 65536;
        } else {
            skip(1);
            return REPLACEMENT_CHARACTER;
        }
        if (this.size < ((long) byteCount)) {
            throw new EOFException("size < " + byteCount + ": " + this.size + " (to read code point prefixed 0x" + Integer.toHexString(b0) + l.t);
        }
        int i = 1;
        while (i < byteCount) {
            byte b = getByte((long) i);
            if ((b & 192) == 128) {
                codePoint = (codePoint << 6) | (b & 63);
                i++;
            } else {
                skip((long) i);
                return REPLACEMENT_CHARACTER;
            }
        }
        skip((long) byteCount);
        return codePoint > 1114111 ? REPLACEMENT_CHARACTER : (codePoint < 55296 || codePoint > 57343) ? codePoint < min ? REPLACEMENT_CHARACTER : codePoint : REPLACEMENT_CHARACTER;
    }

    public byte[] readByteArray() {
        try {
            return readByteArray(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public byte[] readByteArray(long byteCount) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, byteCount);
        if (byteCount > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
        }
        byte[] result = new byte[((int) byteCount)];
        readFully(result);
        return result;
    }

    public int read(byte[] sink) {
        return read(sink, 0, sink.length);
    }

    public void readFully(byte[] sink) throws EOFException {
        int offset = 0;
        while (offset < sink.length) {
            int read = read(sink, offset, sink.length - offset);
            if (read == -1) {
                throw new EOFException();
            }
            offset += read;
        }
    }

    public int read(byte[] sink, int offset, int byteCount) {
        Util.checkOffsetAndCount((long) sink.length, (long) offset, (long) byteCount);
        Segment s = this.head;
        if (s == null) {
            return -1;
        }
        int toCopy = Math.min(byteCount, s.limit - s.pos);
        System.arraycopy(s.data, s.pos, sink, offset, toCopy);
        s.pos += toCopy;
        this.size -= (long) toCopy;
        if (s.pos != s.limit) {
            return toCopy;
        }
        this.head = s.pop();
        SegmentPool.recycle(s);
        return toCopy;
    }

    public void clear() {
        try {
            skip(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public void skip(long byteCount) throws EOFException {
        while (byteCount > 0) {
            if (this.head == null) {
                throw new EOFException();
            }
            int toSkip = (int) Math.min(byteCount, (long) (this.head.limit - this.head.pos));
            this.size -= (long) toSkip;
            byteCount -= (long) toSkip;
            Segment segment = this.head;
            segment.pos += toSkip;
            if (this.head.pos == this.head.limit) {
                Segment toRecycle = this.head;
                this.head = toRecycle.pop();
                SegmentPool.recycle(toRecycle);
            }
        }
    }

    public Buffer write(ByteString byteString) {
        if (byteString == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        byteString.write(this);
        return this;
    }

    public Buffer writeUtf8(String string) {
        return writeUtf8(string, 0, string.length());
    }

    public Buffer writeUtf8(String string, int beginIndex, int endIndex) {
        if (string == null) {
            throw new IllegalArgumentException("string == null");
        } else if (beginIndex < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + beginIndex);
        } else if (endIndex < beginIndex) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + endIndex + " < " + beginIndex);
        } else if (endIndex > string.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + endIndex + " > " + string.length());
        } else {
            int i = beginIndex;
            while (true) {
                int i2 = i;
                if (i2 >= endIndex) {
                    return this;
                }
                int c = string.charAt(i2);
                if (c < 128) {
                    Segment tail = writableSegment(1);
                    byte[] data = tail.data;
                    int segmentOffset = tail.limit - i2;
                    int runLimit = Math.min(endIndex, 2048 - segmentOffset);
                    i = i2 + 1;
                    data[segmentOffset + i2] = (byte) c;
                    i2 = i;
                    while (i2 < runLimit) {
                        c = string.charAt(i2);
                        if (c >= 128) {
                            break;
                        }
                        i = i2 + 1;
                        data[segmentOffset + i2] = (byte) c;
                        i2 = i;
                    }
                    int runSize = (i2 + segmentOffset) - tail.limit;
                    tail.limit += runSize;
                    this.size += (long) runSize;
                    i = i2;
                } else if (c < 2048) {
                    writeByte((c >> 6) | 192);
                    writeByte((c & 63) | 128);
                    i = i2 + 1;
                } else if (c < 55296 || c > 57343) {
                    writeByte((c >> 12) | 224);
                    writeByte(((c >> 6) & 63) | 128);
                    writeByte((c & 63) | 128);
                    i = i2 + 1;
                } else {
                    int low;
                    if (i2 + 1 < endIndex) {
                        low = string.charAt(i2 + 1);
                    } else {
                        low = 0;
                    }
                    if (c > 56319 || low < 56320 || low > 57343) {
                        writeByte(63);
                        i = i2 + 1;
                    } else {
                        int codePoint = 65536 + (((-55297 & c) << 10) | (-56321 & low));
                        writeByte((codePoint >> 18) | 240);
                        writeByte(((codePoint >> 12) & 63) | 128);
                        writeByte(((codePoint >> 6) & 63) | 128);
                        writeByte((codePoint & 63) | 128);
                        i = i2 + 2;
                    }
                }
            }
        }
    }

    public Buffer writeUtf8CodePoint(int codePoint) {
        if (codePoint < 128) {
            writeByte(codePoint);
        } else if (codePoint < 2048) {
            writeByte((codePoint >> 6) | 192);
            writeByte((codePoint & 63) | 128);
        } else if (codePoint < 65536) {
            if (codePoint < 55296 || codePoint > 57343) {
                writeByte((codePoint >> 12) | 224);
                writeByte(((codePoint >> 6) & 63) | 128);
                writeByte((codePoint & 63) | 128);
            } else {
                throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(codePoint));
            }
        } else if (codePoint <= 1114111) {
            writeByte((codePoint >> 18) | 240);
            writeByte(((codePoint >> 12) & 63) | 128);
            writeByte(((codePoint >> 6) & 63) | 128);
            writeByte((codePoint & 63) | 128);
        } else {
            throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(codePoint));
        }
        return this;
    }

    public Buffer writeString(String string, Charset charset) {
        return writeString(string, 0, string.length(), charset);
    }

    public Buffer writeString(String string, int beginIndex, int endIndex, Charset charset) {
        if (string == null) {
            throw new IllegalArgumentException("string == null");
        } else if (beginIndex < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + beginIndex);
        } else if (endIndex < beginIndex) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + endIndex + " < " + beginIndex);
        } else if (endIndex > string.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + endIndex + " > " + string.length());
        } else if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (charset.equals(Util.UTF_8)) {
            return writeUtf8(string);
        } else {
            byte[] data = string.substring(beginIndex, endIndex).getBytes(charset);
            return write(data, 0, data.length);
        }
    }

    public Buffer write(byte[] source) {
        if (source != null) {
            return write(source, 0, source.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    public Buffer write(byte[] source, int offset, int byteCount) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        Util.checkOffsetAndCount((long) source.length, (long) offset, (long) byteCount);
        int limit = offset + byteCount;
        while (offset < limit) {
            Segment tail = writableSegment(1);
            int toCopy = Math.min(limit - offset, 2048 - tail.limit);
            System.arraycopy(source, offset, tail.data, tail.limit, toCopy);
            offset += toCopy;
            tail.limit += toCopy;
        }
        this.size += (long) byteCount;
        return this;
    }

    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long totalBytesRead = 0;
        while (true) {
            long readCount = source.read(this, 2048);
            if (readCount == -1) {
                return totalBytesRead;
            }
            totalBytesRead += readCount;
        }
    }

    public BufferedSink write(Source source, long byteCount) throws IOException {
        while (byteCount > 0) {
            long read = source.read(this, byteCount);
            if (read == -1) {
                throw new EOFException();
            }
            byteCount -= read;
        }
        return this;
    }

    public Buffer writeByte(int b) {
        Segment tail = writableSegment(1);
        byte[] bArr = tail.data;
        int i = tail.limit;
        tail.limit = i + 1;
        bArr[i] = (byte) b;
        this.size++;
        return this;
    }

    public Buffer writeShort(int s) {
        Segment tail = writableSegment(2);
        byte[] data = tail.data;
        int i = tail.limit;
        int i2 = i + 1;
        data[i] = (byte) ((s >>> 8) & 255);
        i = i2 + 1;
        data[i2] = (byte) (s & 255);
        tail.limit = i;
        this.size += 2;
        return this;
    }

    public Buffer writeShortLe(int s) {
        return writeShort(Util.reverseBytesShort((short) s));
    }

    public Buffer writeInt(int i) {
        Segment tail = writableSegment(4);
        byte[] data = tail.data;
        int i2 = tail.limit;
        int i3 = i2 + 1;
        data[i2] = (byte) ((i >>> 24) & 255);
        i2 = i3 + 1;
        data[i3] = (byte) ((i >>> 16) & 255);
        i3 = i2 + 1;
        data[i2] = (byte) ((i >>> 8) & 255);
        i2 = i3 + 1;
        data[i3] = (byte) (i & 255);
        tail.limit = i2;
        this.size += 4;
        return this;
    }

    public Buffer writeIntLe(int i) {
        return writeInt(Util.reverseBytesInt(i));
    }

    public Buffer writeLong(long v) {
        Segment tail = writableSegment(8);
        byte[] data = tail.data;
        int i = tail.limit;
        int i2 = i + 1;
        data[i] = (byte) ((int) ((v >>> 56) & 255));
        i = i2 + 1;
        data[i2] = (byte) ((int) ((v >>> 48) & 255));
        i2 = i + 1;
        data[i] = (byte) ((int) ((v >>> 40) & 255));
        i = i2 + 1;
        data[i2] = (byte) ((int) ((v >>> 32) & 255));
        i2 = i + 1;
        data[i] = (byte) ((int) ((v >>> 24) & 255));
        i = i2 + 1;
        data[i2] = (byte) ((int) ((v >>> 16) & 255));
        i2 = i + 1;
        data[i] = (byte) ((int) ((v >>> 8) & 255));
        i = i2 + 1;
        data[i2] = (byte) ((int) (v & 255));
        tail.limit = i;
        this.size += 8;
        return this;
    }

    public Buffer writeLongLe(long v) {
        return writeLong(Util.reverseBytesLong(v));
    }

    public Buffer writeDecimalLong(long v) {
        if (v == 0) {
            return writeByte(48);
        }
        boolean negative = false;
        if (v < 0) {
            v = -v;
            if (v < 0) {
                return writeUtf8("-9223372036854775808");
            }
            negative = true;
        }
        int width = v < 100000000 ? v < 10000 ? v < 100 ? v < 10 ? 1 : 2 : v < 1000 ? 3 : 4 : v < 1000000 ? v < 100000 ? 5 : 6 : v < 10000000 ? 7 : 8 : v < 1000000000000L ? v < 10000000000L ? v < 1000000000 ? 9 : 10 : v < 100000000000L ? 11 : 12 : v < 1000000000000000L ? v < 10000000000000L ? 13 : v < 100000000000000L ? 14 : 15 : v < 100000000000000000L ? v < 10000000000000000L ? 16 : 17 : v < 1000000000000000000L ? 18 : 19;
        if (negative) {
            width++;
        }
        Segment tail = writableSegment(width);
        byte[] data = tail.data;
        int pos = tail.limit + width;
        while (v != 0) {
            pos--;
            data[pos] = DIGITS[(int) (v % 10)];
            v /= 10;
        }
        if (negative) {
            data[pos - 1] = (byte) 45;
        }
        tail.limit += width;
        this.size += (long) width;
        return this;
    }

    public Buffer writeHexadecimalUnsignedLong(long v) {
        if (v == 0) {
            return writeByte(48);
        }
        int width = (Long.numberOfTrailingZeros(Long.highestOneBit(v)) / 4) + 1;
        Segment tail = writableSegment(width);
        byte[] data = tail.data;
        int start = tail.limit;
        for (int pos = (tail.limit + width) - 1; pos >= start; pos--) {
            data[pos] = DIGITS[(int) (15 & v)];
            v >>>= 4;
        }
        tail.limit += width;
        this.size += (long) width;
        return this;
    }

    Segment writableSegment(int minimumCapacity) {
        Segment segment;
        if (minimumCapacity < 1 || minimumCapacity > 2048) {
            throw new IllegalArgumentException();
        } else if (this.head == null) {
            this.head = SegmentPool.take();
            Segment segment2 = this.head;
            Segment segment3 = this.head;
            segment = this.head;
            segment3.prev = segment;
            segment2.next = segment;
            return segment;
        } else {
            segment = this.head.prev;
            if (segment.limit + minimumCapacity > 2048 || !segment.owner) {
                return segment.push(SegmentPool.take());
            }
            return segment;
        }
    }

    public void write(Buffer source, long byteCount) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        } else if (source == this) {
            throw new IllegalArgumentException("source == this");
        } else {
            Util.checkOffsetAndCount(source.size, 0, byteCount);
            while (byteCount > 0) {
                if (byteCount < ((long) (source.head.limit - source.head.pos))) {
                    Segment tail = this.head != null ? this.head.prev : null;
                    if (tail != null && tail.owner) {
                        if ((byteCount + ((long) tail.limit)) - ((long) (tail.shared ? 0 : tail.pos)) <= 2048) {
                            source.head.writeTo(tail, (int) byteCount);
                            source.size -= byteCount;
                            this.size += byteCount;
                            return;
                        }
                    }
                    source.head = source.head.split((int) byteCount);
                }
                Segment segmentToMove = source.head;
                long movedByteCount = (long) (segmentToMove.limit - segmentToMove.pos);
                source.head = segmentToMove.pop();
                if (this.head == null) {
                    this.head = segmentToMove;
                    Segment segment = this.head;
                    Segment segment2 = this.head;
                    Segment segment3 = this.head;
                    segment2.prev = segment3;
                    segment.next = segment3;
                } else {
                    this.head.prev.push(segmentToMove).compact();
                }
                source.size -= movedByteCount;
                this.size += movedByteCount;
                byteCount -= movedByteCount;
            }
        }
    }

    public long read(Buffer sink, long byteCount) {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (byteCount < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
        } else if (this.size == 0) {
            return -1;
        } else {
            if (byteCount > this.size) {
                byteCount = this.size;
            }
            sink.write(this, byteCount);
            return byteCount;
        }
    }

    public long indexOf(byte b) {
        return indexOf(b, 0);
    }

    public long indexOf(byte b, long fromIndex) {
        if (fromIndex < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment s = this.head;
        if (s == null) {
            return -1;
        }
        long offset = 0;
        do {
            int segmentByteCount = s.limit - s.pos;
            if (fromIndex >= ((long) segmentByteCount)) {
                fromIndex -= (long) segmentByteCount;
            } else {
                byte[] data = s.data;
                int limit = s.limit;
                for (int pos = (int) (((long) s.pos) + fromIndex); pos < limit; pos++) {
                    if (data[pos] == b) {
                        return (((long) pos) + offset) - ((long) s.pos);
                    }
                }
                fromIndex = 0;
            }
            offset += (long) segmentByteCount;
            s = s.next;
        } while (s != this.head);
        return -1;
    }

    public long indexOf(ByteString bytes) throws IOException {
        return indexOf(bytes, 0);
    }

    public long indexOf(ByteString bytes, long fromIndex) throws IOException {
        if (bytes.size() == 0) {
            throw new IllegalArgumentException("bytes is empty");
        }
        while (true) {
            fromIndex = indexOf(bytes.getByte(0), fromIndex);
            if (fromIndex == -1) {
                return -1;
            }
            if (rangeEquals(fromIndex, bytes)) {
                return fromIndex;
            }
            fromIndex++;
        }
    }

    public long indexOfElement(ByteString targetBytes) {
        return indexOfElement(targetBytes, 0);
    }

    public long indexOfElement(ByteString targetBytes, long fromIndex) {
        if (fromIndex < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment s = this.head;
        if (s == null) {
            return -1;
        }
        long offset = 0;
        byte[] toFind = targetBytes.toByteArray();
        do {
            int segmentByteCount = s.limit - s.pos;
            if (fromIndex >= ((long) segmentByteCount)) {
                fromIndex -= (long) segmentByteCount;
            } else {
                byte[] data = s.data;
                long limit = (long) s.limit;
                for (long pos = ((long) s.pos) + fromIndex; pos < limit; pos++) {
                    byte b = data[(int) pos];
                    for (byte targetByte : toFind) {
                        if (b == targetByte) {
                            return (offset + pos) - ((long) s.pos);
                        }
                    }
                }
                fromIndex = 0;
            }
            offset += (long) segmentByteCount;
            s = s.next;
        } while (s != this.head);
        return -1;
    }

    boolean rangeEquals(long offset, ByteString bytes) {
        int byteCount = bytes.size();
        if (this.size - offset < ((long) byteCount)) {
            return false;
        }
        for (int i = 0; i < byteCount; i++) {
            if (getByte(((long) i) + offset) != bytes.getByte(i)) {
                return false;
            }
        }
        return true;
    }

    public void flush() {
    }

    public void close() {
    }

    public Timeout timeout() {
        return Timeout.NONE;
    }

    List<Integer> segmentSizes() {
        if (this.head == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList();
        result.add(Integer.valueOf(this.head.limit - this.head.pos));
        for (Segment s = this.head.next; s != this.head; s = s.next) {
            result.add(Integer.valueOf(s.limit - s.pos));
        }
        return result;
    }

    /* JADX WARNING: Missing block: B:23:0x006c, code:
            if (r8 != r11.limit) goto L_0x0080;
     */
    /* JADX WARNING: Missing block: B:24:0x006e, code:
            r11 = r11.next;
            r5 = r11.pos;
     */
    /* JADX WARNING: Missing block: B:26:0x0074, code:
            if (r10 != r12.limit) goto L_0x007e;
     */
    /* JADX WARNING: Missing block: B:27:0x0076, code:
            r12 = r12.next;
            r9 = r12.pos;
     */
    /* JADX WARNING: Missing block: B:28:0x007a, code:
            r6 = r6 + r2;
     */
    /* JADX WARNING: Missing block: B:30:0x007e, code:
            r9 = r10;
     */
    /* JADX WARNING: Missing block: B:31:0x0080, code:
            r5 = r8;
     */
    public boolean equals(java.lang.Object r19) {
        /*
        r18 = this;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x0008;
    L_0x0006:
        r14 = 1;
    L_0x0007:
        return r14;
    L_0x0008:
        r0 = r19;
        r14 = r0 instanceof com.meizu.cloud.pushsdk.networking.okio.Buffer;
        if (r14 != 0) goto L_0x0010;
    L_0x000e:
        r14 = 0;
        goto L_0x0007;
    L_0x0010:
        r13 = r19;
        r13 = (com.meizu.cloud.pushsdk.networking.okio.Buffer) r13;
        r0 = r18;
        r14 = r0.size;
        r0 = r13.size;
        r16 = r0;
        r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r14 == 0) goto L_0x0022;
    L_0x0020:
        r14 = 0;
        goto L_0x0007;
    L_0x0022:
        r0 = r18;
        r14 = r0.size;
        r16 = 0;
        r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r14 != 0) goto L_0x002e;
    L_0x002c:
        r14 = 1;
        goto L_0x0007;
    L_0x002e:
        r0 = r18;
        r11 = r0.head;
        r12 = r13.head;
        r5 = r11.pos;
        r9 = r12.pos;
        r6 = 0;
    L_0x003a:
        r0 = r18;
        r14 = r0.size;
        r14 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1));
        if (r14 >= 0) goto L_0x007c;
    L_0x0042:
        r14 = r11.limit;
        r14 = r14 - r5;
        r15 = r12.limit;
        r15 = r15 - r9;
        r14 = java.lang.Math.min(r14, r15);
        r2 = (long) r14;
        r4 = 0;
        r10 = r9;
        r8 = r5;
    L_0x0050:
        r14 = (long) r4;
        r14 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1));
        if (r14 >= 0) goto L_0x006a;
    L_0x0055:
        r14 = r11.data;
        r5 = r8 + 1;
        r14 = r14[r8];
        r15 = r12.data;
        r9 = r10 + 1;
        r15 = r15[r10];
        if (r14 == r15) goto L_0x0065;
    L_0x0063:
        r14 = 0;
        goto L_0x0007;
    L_0x0065:
        r4 = r4 + 1;
        r10 = r9;
        r8 = r5;
        goto L_0x0050;
    L_0x006a:
        r14 = r11.limit;
        if (r8 != r14) goto L_0x0080;
    L_0x006e:
        r11 = r11.next;
        r5 = r11.pos;
    L_0x0072:
        r14 = r12.limit;
        if (r10 != r14) goto L_0x007e;
    L_0x0076:
        r12 = r12.next;
        r9 = r12.pos;
    L_0x007a:
        r6 = r6 + r2;
        goto L_0x003a;
    L_0x007c:
        r14 = 1;
        goto L_0x0007;
    L_0x007e:
        r9 = r10;
        goto L_0x007a;
    L_0x0080:
        r5 = r8;
        goto L_0x0072;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.networking.okio.Buffer.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        Segment s = this.head;
        if (s == null) {
            return 0;
        }
        int result = 1;
        do {
            for (int pos = s.pos; pos < s.limit; pos++) {
                result = (result * 31) + s.data[pos];
            }
            s = s.next;
        } while (s != this.head);
        return result;
    }

    public String toString() {
        if (this.size == 0) {
            return "Buffer[size=0]";
        }
        if (this.size <= 16) {
            ByteString data = clone().readByteString();
            return String.format("Buffer[size=%s data=%s]", new Object[]{Long.valueOf(this.size), data.hex()});
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
            for (Segment s = this.head.next; s != this.head; s = s.next) {
                md5.update(s.data, s.pos, s.limit - s.pos);
            }
            return String.format("Buffer[size=%s md5=%s]", new Object[]{Long.valueOf(this.size), ByteString.of(md5.digest()).hex()});
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

    public Buffer clone() {
        Buffer result = new Buffer();
        if (this.size != 0) {
            result.head = new Segment(this.head);
            Segment segment = result.head;
            Segment segment2 = result.head;
            Segment segment3 = result.head;
            segment2.prev = segment3;
            segment.next = segment3;
            for (Segment s = this.head.next; s != this.head; s = s.next) {
                result.head.prev.push(new Segment(s));
            }
            result.size = this.size;
        }
        return result;
    }

    public ByteString snapshot() {
        if (this.size <= 2147483647L) {
            return snapshot((int) this.size);
        }
        throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.size);
    }

    public ByteString snapshot(int byteCount) {
        if (byteCount == 0) {
            return ByteString.EMPTY;
        }
        return new SegmentedByteString(this, byteCount);
    }
}
