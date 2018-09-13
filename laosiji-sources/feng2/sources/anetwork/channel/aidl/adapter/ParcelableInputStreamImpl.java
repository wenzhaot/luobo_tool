package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anet.channel.bytes.ByteArray;
import anet.channel.util.ALog;
import anetwork.channel.aidl.ParcelableInputStream.Stub;
import anetwork.channel.entity.RequestConfig;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: Taobao */
public class ParcelableInputStreamImpl extends Stub {
    private static final ByteArray EOS = ByteArray.create(0);
    private static final String TAG = "anet.ParcelableInputStreamImpl";
    private int blockIndex;
    private int blockOffset;
    private LinkedList<ByteArray> byteList = new LinkedList();
    private int contentLength;
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    final ReentrantLock lock = new ReentrantLock();
    final Condition newDataArrive = this.lock.newCondition();
    private int receivedLength = 0;
    private int rto = 10000;
    private String seqNo = "";
    private String url = "";

    public void write(ByteArray byteArray) {
        if (!this.isClosed.get()) {
            this.lock.lock();
            try {
                this.byteList.add(byteArray);
                this.receivedLength += byteArray.getDataLength();
                this.newDataArrive.signal();
            } finally {
                this.lock.unlock();
            }
        }
    }

    public void writeEnd() {
        write(EOS);
        if (ALog.isPrintLog(1)) {
            ALog.d(TAG, "set EOS flag to stream", this.seqNo, new Object[0]);
        }
        if (this.contentLength != 0 && this.contentLength != this.receivedLength) {
            ALog.e(TAG, "data length no match!", this.seqNo, "ContentLength", Integer.valueOf(this.contentLength), "Received", Integer.valueOf(this.receivedLength), "url", this.url);
        }
    }

    private void recycleCurrentItem() {
        this.lock.lock();
        try {
            ((ByteArray) this.byteList.set(this.blockIndex, EOS)).recycle();
        } finally {
            this.lock.unlock();
        }
    }

    public int available() throws RemoteException {
        int i = 0;
        if (this.isClosed.get()) {
            throw new RuntimeException("Stream is closed");
        }
        this.lock.lock();
        try {
            if (this.blockIndex == this.byteList.size()) {
                return 0;
            }
            ListIterator listIterator = this.byteList.listIterator(this.blockIndex);
            while (true) {
                int i2 = i;
                if (listIterator.hasNext()) {
                    i = ((ByteArray) listIterator.next()).getDataLength() + i2;
                } else {
                    i = i2 - this.blockOffset;
                    this.lock.unlock();
                    return i;
                }
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void close() throws RemoteException {
        if (this.isClosed.compareAndSet(false, true)) {
            this.lock.lock();
            try {
                Iterator it = this.byteList.iterator();
                while (it.hasNext()) {
                    ByteArray byteArray = (ByteArray) it.next();
                    if (byteArray != EOS) {
                        byteArray.recycle();
                    }
                }
                this.byteList.clear();
                this.byteList = null;
                this.blockIndex = -1;
                this.blockOffset = -1;
                this.contentLength = 0;
            } finally {
                this.lock.unlock();
            }
        }
    }

    public int readByte() throws RemoteException {
        if (this.isClosed.get()) {
            throw new RuntimeException("Stream is closed");
        }
        int i;
        this.lock.lock();
        while (true) {
            try {
                if (this.blockIndex != this.byteList.size() || this.newDataArrive.await((long) this.rto, TimeUnit.MILLISECONDS)) {
                    ByteArray byteArray = (ByteArray) this.byteList.get(this.blockIndex);
                    if (byteArray == EOS) {
                        i = -1;
                        break;
                    } else if (this.blockOffset < byteArray.getDataLength()) {
                        i = byteArray.getBuffer()[this.blockOffset];
                        this.blockOffset++;
                        break;
                    } else {
                        recycleCurrentItem();
                        this.blockIndex++;
                        this.blockOffset = 0;
                    }
                } else {
                    close();
                    throw new RuntimeException("await timeout.");
                }
            } catch (InterruptedException e) {
                close();
                throw new RuntimeException("await interrupt");
            } catch (Throwable th) {
                this.lock.unlock();
            }
        }
        this.lock.unlock();
        return i;
    }

    public int readBytes(byte[] bArr, int i, int i2) throws RemoteException {
        if (this.isClosed.get()) {
            throw new RuntimeException("Stream is closed");
        } else if (bArr == null) {
            throw new NullPointerException();
        } else if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            int i3;
            int i4 = i + i2;
            this.lock.lock();
            int i5 = i;
            while (i5 < i4) {
                try {
                    if (this.blockIndex != this.byteList.size() || this.newDataArrive.await((long) this.rto, TimeUnit.MILLISECONDS)) {
                        ByteArray byteArray = (ByteArray) this.byteList.get(this.blockIndex);
                        if (byteArray == EOS) {
                            break;
                        }
                        int dataLength = byteArray.getDataLength() - this.blockOffset;
                        int i6 = i4 - i5;
                        if (dataLength < i6) {
                            System.arraycopy(byteArray.getBuffer(), this.blockOffset, bArr, i5, dataLength);
                            i3 = i5 + dataLength;
                            recycleCurrentItem();
                            this.blockIndex++;
                            this.blockOffset = 0;
                        } else {
                            System.arraycopy(byteArray.getBuffer(), this.blockOffset, bArr, i5, i6);
                            this.blockOffset += i6;
                            i3 = i5 + i6;
                        }
                        i5 = i3;
                    } else {
                        close();
                        throw new RuntimeException("await timeout.");
                    }
                } catch (InterruptedException e) {
                    close();
                    throw new RuntimeException("await interrupt");
                } catch (Throwable th) {
                    this.lock.unlock();
                }
            }
            this.lock.unlock();
            i3 = i5 - i;
            if (i3 > 0) {
                return i3;
            }
            return -1;
        }
    }

    public int read(byte[] bArr) throws RemoteException {
        return readBytes(bArr, 0, bArr.length);
    }

    public long skip(int i) throws RemoteException {
        int i2;
        int i3 = 0;
        this.lock.lock();
        while (i3 < i) {
            try {
                if (this.blockIndex == this.byteList.size()) {
                    i2 = i3;
                    break;
                }
                ByteArray byteArray = (ByteArray) this.byteList.get(this.blockIndex);
                if (byteArray == EOS) {
                    i2 = i3;
                    break;
                }
                i2 = byteArray.getDataLength();
                if (i2 - this.blockOffset < i - i3) {
                    i2 = (i2 - this.blockOffset) + i3;
                    recycleCurrentItem();
                    this.blockIndex++;
                    this.blockOffset = 0;
                    break;
                }
                this.blockOffset += i - i;
                i3 = i;
            } catch (Throwable th) {
                this.lock.unlock();
            }
        }
        i2 = i3;
        this.lock.unlock();
        return (long) i2;
    }

    public int length() throws RemoteException {
        return this.contentLength;
    }

    public void init(RequestConfig requestConfig, int i) {
        this.contentLength = i;
        this.seqNo = requestConfig.getSeqNo();
        this.url = requestConfig.getUrlString();
        this.rto = requestConfig.getReadTimeout();
    }
}
