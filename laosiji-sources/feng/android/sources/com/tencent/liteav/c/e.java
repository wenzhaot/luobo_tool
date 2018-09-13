package com.tencent.liteav.c;

import android.annotation.TargetApi;
import android.media.MediaCodec.BufferInfo;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;

@TargetApi(16)
/* compiled from: Frame */
public class e implements Serializable {
    private int audioBitRate;
    private List bitmapList;
    private float blurLevel;
    private int bufferIndex;
    private BufferInfo bufferInfo = new BufferInfo();
    private ByteBuffer byteBuffer;
    private int channelCount;
    private int frameFormat = 4;
    private int frameRate;
    private int height;
    private float mCropOffsetRatio;
    private String mime;
    private long originSampleTime;
    private long reverseSampleTime;
    private int rotation;
    private int sampleRate;
    private long speedSampleTime;
    private boolean tailFrame;
    private int textureId;
    private int trackId;
    private int width;

    public e(ByteBuffer byteBuffer, int i, long j, int i2, int i3, int i4) {
        this.byteBuffer = byteBuffer;
        this.trackId = i4;
        this.bufferInfo.flags = i3;
        this.bufferInfo.presentationTimeUs = j;
        this.bufferIndex = i2;
        this.bufferInfo.size = i;
    }

    public e(String str, ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        this.mime = str;
        this.byteBuffer = byteBuffer;
        this.bufferInfo = new BufferInfo();
        this.bufferInfo.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
    }

    public String a() {
        return this.mime;
    }

    public void a(String str) {
        this.mime = str;
    }

    public ByteBuffer b() {
        return this.byteBuffer;
    }

    public int c() {
        return this.trackId;
    }

    public int d() {
        return this.bufferIndex;
    }

    public void a(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public void a(int i) {
        this.trackId = i;
    }

    public void b(int i) {
        this.bufferIndex = i;
    }

    public void a(long j) {
        this.bufferInfo.presentationTimeUs = j;
    }

    public long e() {
        return this.bufferInfo.presentationTimeUs;
    }

    public void c(int i) {
        this.bufferInfo.flags = i;
    }

    public void d(int i) {
        this.bufferInfo.size = i;
    }

    public int f() {
        return this.bufferInfo.flags;
    }

    public int g() {
        return this.bufferInfo.size;
    }

    public int h() {
        return this.rotation;
    }

    public void e(int i) {
        this.rotation = i;
    }

    public int i() {
        return this.frameRate;
    }

    public void f(int i) {
        this.frameRate = i;
    }

    public int j() {
        return this.sampleRate;
    }

    public void g(int i) {
        this.sampleRate = i;
    }

    public int k() {
        return this.channelCount;
    }

    public void h(int i) {
        this.channelCount = i;
    }

    public int l() {
        return this.audioBitRate;
    }

    public void i(int i) {
        this.audioBitRate = i;
    }

    public int m() {
        return this.width;
    }

    public void j(int i) {
        this.width = i;
    }

    public int n() {
        return this.height;
    }

    public void k(int i) {
        this.height = i;
    }

    public BufferInfo o() {
        return this.bufferInfo;
    }

    public String toString() {
        return "";
    }

    public boolean p() {
        return (f() & 4) != 0;
    }

    public boolean q() {
        return g() == 0 || f() == 2;
    }

    public boolean r() {
        return this.tailFrame;
    }

    public void a(boolean z) {
        this.tailFrame = z;
    }

    public void a(float f) {
        this.blurLevel = f;
    }

    public float s() {
        return this.blurLevel;
    }

    public void b(long j) {
        this.speedSampleTime = j;
    }

    public long t() {
        return this.speedSampleTime;
    }

    public long u() {
        return this.reverseSampleTime;
    }

    public void c(long j) {
        this.reverseSampleTime = j;
    }

    public long v() {
        return this.originSampleTime;
    }

    public void d(long j) {
        this.originSampleTime = j;
    }

    public List w() {
        return this.bitmapList;
    }

    public void a(List list) {
        this.bitmapList = list;
    }

    public int x() {
        return this.textureId;
    }

    public void l(int i) {
        this.textureId = i;
    }

    public int y() {
        return this.frameFormat;
    }

    public void m(int i) {
        this.frameFormat = i;
    }
}
