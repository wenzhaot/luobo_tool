package org.android.spdy;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SpdySession {
    private static volatile int count = 0;
    private SpdyAgent agent;
    private String authority;
    private AtomicBoolean closed = new AtomicBoolean();
    private String domain;
    private Handler handler;
    Intenalcb intenalcb;
    private Object lock = new Object();
    private int mode;
    private int pubkey_seqnum = 0;
    volatile int refcount = 1;
    SessionCb sessionCallBack = null;
    private boolean sessionClearedFromSessionMgr = false;
    private volatile long sessionNativePtr;
    private NetSparseArray<SpdyStreamContext> spdyStream = null;
    private int streamcount = 1;
    private HandlerThread thread;
    private Object userData = null;

    private native int sendCustomControlFrameN(long j, int i, int i2, int i3, int i4, byte[] bArr);

    private native int sendHeadersN(long j, int i, String[] strArr, boolean z);

    private native int setOptionN(long j, int i, int i2);

    private native int streamCloseN(long j, int i, int i2);

    private native int streamSendDataN(long j, int i, byte[] bArr, int i2, int i3, boolean z);

    private native int submitBioPingN(long j);

    private native int submitPingN(long j);

    private native int submitRequestN(long j, String str, byte b, String[] strArr, byte[] bArr, boolean z, int i, int i2);

    SpdySession(long j, SpdyAgent spdyAgent, String str, String str2, SessionCb sessionCb, int i, int i2, Object obj) {
        this.sessionNativePtr = j;
        this.agent = spdyAgent;
        this.authority = str;
        this.intenalcb = new SpdySessionCallBack();
        this.domain = str2;
        this.spdyStream = new NetSparseArray(5);
        this.sessionCallBack = sessionCb;
        this.pubkey_seqnum = i2;
        this.mode = i;
        this.userData = obj;
        this.closed.set(false);
    }

    public int getRefCount() {
        return this.refcount;
    }

    void increRefCount() {
        this.refcount++;
    }

    private String getAuthority() {
        return this.authority;
    }

    public String getDomain() {
        return this.domain;
    }

    public Object getUserData() {
        return this.userData;
    }

    int putSpdyStreamCtx(SpdyStreamContext spdyStreamContext) {
        int i;
        synchronized (this.lock) {
            i = this.streamcount;
            this.streamcount = i + 1;
            this.spdyStream.put(i, spdyStreamContext);
        }
        return i;
    }

    SpdyStreamContext getSpdyStream(int i) {
        SpdyStreamContext spdyStreamContext = null;
        if (i > 0) {
            synchronized (this.lock) {
                spdyStreamContext = (SpdyStreamContext) this.spdyStream.get(i);
            }
        }
        return spdyStreamContext;
    }

    void removeSpdyStream(int i) {
        if (i > 0) {
            synchronized (this.lock) {
                this.spdyStream.remove(i);
            }
        }
    }

    public SpdyStreamContext[] getAllStreamCb() {
        SpdyStreamContext[] spdyStreamContextArr = null;
        synchronized (this.lock) {
            int size = this.spdyStream.size();
            if (size > 0) {
                spdyStreamContextArr = new SpdyStreamContext[size];
                this.spdyStream.toArray(spdyStreamContextArr);
            }
        }
        return spdyStreamContextArr;
    }

    public void clearAllStreamCb() {
        spduLog.Logd("tnet-jni", "[SpdySession.clearAllStreamCb] - ");
        synchronized (this.lock) {
            this.spdyStream.clear();
        }
    }

    SpdyAgent getSpdyAgent() {
        return this.agent;
    }

    Handler getMsgHandler() {
        return this.handler;
    }

    long getSessionNativePtr() {
        return this.sessionNativePtr;
    }

    void setSessionNativePtr(long j) {
        this.sessionNativePtr = j;
    }

    public int setOption(int i, int i2) throws SpdyErrorException {
        sessionIsOpen();
        int optionN = setOptionN(this.sessionNativePtr, i, i2);
        if (optionN == 0) {
            return optionN;
        }
        throw new SpdyErrorException("setOption error: " + optionN, optionN);
    }

    public int submitPing() throws SpdyErrorException {
        sessionIsOpen();
        int submitPingN = submitPingN(this.sessionNativePtr);
        if (submitPingN == 0) {
            return submitPingN;
        }
        throw new SpdyErrorException("submitPing error: " + submitPingN, submitPingN);
    }

    @Deprecated
    public int submitBioPing() throws SpdyErrorException {
        sessionIsOpen();
        int submitBioPingN = submitBioPingN(this.sessionNativePtr);
        if (submitBioPingN == 0) {
            return submitBioPingN;
        }
        throw new SpdyErrorException("submitBioPing error: " + submitBioPingN, submitBioPingN);
    }

    public int streamReset(long j, int i) throws SpdyErrorException {
        sessionIsOpen();
        spduLog.Logd("tnet-jni", "[SpdySession.streamReset] - ");
        int streamCloseN = streamCloseN(this.sessionNativePtr, (int) j, i);
        if (streamCloseN == 0) {
            return streamCloseN;
        }
        throw new SpdyErrorException("streamReset error: " + streamCloseN, streamCloseN);
    }

    public int sendCustomControlFrame(int i, int i2, int i3, int i4, byte[] bArr) throws SpdyErrorException {
        byte[] bArr2;
        sessionIsOpen();
        if (bArr == null || bArr.length > 0) {
            bArr2 = bArr;
        } else {
            bArr2 = null;
        }
        spduLog.Logi("tnet-jni", "[sendCustomControlFrame] - type: " + i2);
        int sendCustomControlFrameN = sendCustomControlFrameN(this.sessionNativePtr, i, i2, i3, i4, bArr2);
        if (sendCustomControlFrameN == 0) {
            return sendCustomControlFrameN;
        }
        throw new SpdyErrorException("sendCustomControlFrame error: " + sendCustomControlFrameN, sendCustomControlFrameN);
    }

    public int submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Spdycb spdycb) throws SpdyErrorException {
        if (spdyRequest == null || obj == null || spdyRequest.getAuthority() == null) {
            throw new SpdyErrorException("submitRequest error: -1102", -1102);
        }
        sessionIsOpen();
        byte[] dataproviderToByteArray = SpdyAgent.dataproviderToByteArray(spdyRequest, spdyDataProvider);
        if (dataproviderToByteArray != null && dataproviderToByteArray.length <= 0) {
            dataproviderToByteArray = null;
        }
        boolean z = true;
        if (spdyDataProvider != null) {
            z = spdyDataProvider.finished;
        }
        SpdyStreamContext spdyStreamContext = new SpdyStreamContext(obj, spdycb);
        int putSpdyStreamCtx = putSpdyStreamCtx(spdyStreamContext);
        String[] mapToByteArray = SpdyAgent.mapToByteArray(spdyRequest.getHeaders());
        spduLog.Logi("tnet-jni", "index=" + putSpdyStreamCtx + "  " + "starttime=" + System.currentTimeMillis());
        int submitRequestN = submitRequestN(this.sessionNativePtr, spdyRequest.getUrlPath(), (byte) spdyRequest.getPriority(), mapToByteArray, dataproviderToByteArray, z, putSpdyStreamCtx, spdyRequest.getRequestTimeoutMs());
        spduLog.Logi("tnet-jni", "index=" + putSpdyStreamCtx + "  " + " calltime=" + System.currentTimeMillis());
        if (submitRequestN < 0) {
            removeSpdyStream(putSpdyStreamCtx);
            throw new SpdyErrorException("submitRequest error: " + submitRequestN, submitRequestN);
        }
        spdyStreamContext.streamId = submitRequestN;
        return submitRequestN;
    }

    void sessionIsOpen() {
        if (this.closed.get()) {
            throw new SpdyErrorException("session is already closed: -1104", -1104);
        }
    }

    public int cleanUp() {
        spduLog.Logd("tnet-jni", "[SpdySession.cleanUp] - ");
        if (this.closed.getAndSet(true)) {
            return 0;
        }
        this.agent.removeSession(this);
        return closeprivate();
    }

    int closeInternal() {
        if (this.closed.getAndSet(true)) {
            return 0;
        }
        return closeprivate();
    }

    public int closeSession() {
        int closeSession;
        spduLog.Logd("tnet-jni", "[SpdySession.closeSession] - ");
        synchronized (this.lock) {
            if (!this.sessionClearedFromSessionMgr) {
                spduLog.Logd("tnet-jni", "[SpdySession.closeSession] - " + this.authority);
                this.agent.clearSpdySession(this.authority, this.domain, this.mode);
                this.sessionClearedFromSessionMgr = true;
                try {
                    closeSession = this.agent.closeSession(this.sessionNativePtr);
                } catch (UnsatisfiedLinkError e) {
                    e.printStackTrace();
                }
            }
            closeSession = 0;
        }
        return closeSession;
    }

    private int closeprivate() {
        synchronized (this.lock) {
            if (!this.sessionClearedFromSessionMgr) {
                this.agent.clearSpdySession(this.authority, this.domain, this.mode);
                this.sessionClearedFromSessionMgr = true;
            }
        }
        this.sessionNativePtr = 0;
        synchronized (this.lock) {
            SpdyStreamContext[] allStreamCb = getAllStreamCb();
            if (allStreamCb != null) {
                for (SpdyStreamContext spdyStreamContext : allStreamCb) {
                    spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyStreamCloseCallback] unfinished stm=" + spdyStreamContext.streamId);
                    spdyStreamContext.callBack.spdyStreamCloseCallback(this, (long) spdyStreamContext.streamId, -2001, spdyStreamContext.streamContext, null);
                }
            }
            this.spdyStream.clear();
        }
        return 0;
    }
}
