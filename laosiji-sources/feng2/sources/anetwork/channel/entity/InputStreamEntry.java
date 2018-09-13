package anetwork.channel.entity;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import anet.channel.bytes.ByteArray;
import anet.channel.request.BodyEntry;
import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.aidl.ParcelableInputStream.Stub;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: Taobao */
public class InputStreamEntry implements BodyEntry {
    public static final Creator<InputStreamEntry> CREATOR = new Creator<InputStreamEntry>() {
        public InputStreamEntry createFromParcel(Parcel parcel) {
            InputStreamEntry inputStreamEntry = new InputStreamEntry();
            inputStreamEntry.inputStream = Stub.asInterface(parcel.readStrongBinder());
            return inputStreamEntry;
        }

        public InputStreamEntry[] newArray(int i) {
            return new InputStreamEntry[i];
        }
    };
    private ParcelableInputStream inputStream;

    /* compiled from: Taobao */
    private static class ParcelableInputStreamWrapper extends Stub {
        private InputStream inputStream;

        public ParcelableInputStreamWrapper(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public int available() throws RemoteException {
            try {
                return this.inputStream.available();
            } catch (IOException e) {
                throw new RemoteException("IO Exception");
            }
        }

        public void close() throws RemoteException {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                throw new RemoteException("IO Exception");
            }
        }

        public int readByte() throws RemoteException {
            try {
                return this.inputStream.read();
            } catch (IOException e) {
                throw new RemoteException("IO Exception");
            }
        }

        public int readBytes(byte[] bArr, int i, int i2) throws RemoteException {
            try {
                return this.inputStream.read(bArr, i, i2);
            } catch (IOException e) {
                throw new RemoteException("IO Exception");
            }
        }

        public int read(byte[] bArr) throws RemoteException {
            try {
                return this.inputStream.read(bArr);
            } catch (IOException e) {
                throw new RemoteException("IO Exception");
            }
        }

        public long skip(int i) throws RemoteException {
            try {
                return this.inputStream.skip((long) i);
            } catch (IOException e) {
                throw new RemoteException("IO Exception");
            }
        }

        public int length() throws RemoteException {
            return 0;
        }
    }

    public InputStreamEntry(InputStream inputStream) {
        this.inputStream = null;
        this.inputStream = new ParcelableInputStreamWrapper(inputStream);
    }

    private InputStreamEntry() {
        this.inputStream = null;
    }

    public String getContentType() {
        return null;
    }

    public int writeTo(OutputStream outputStream) throws IOException {
        int i = 0;
        try {
            ByteArray a = a.a.a(2048);
            while (true) {
                int read = this.inputStream.read(a.getBuffer());
                if (read != -1) {
                    a.writeTo(outputStream);
                    i += read;
                } else {
                    a.recycle();
                    return i;
                }
            }
        } catch (Throwable e) {
            throw new IOException("RemoteException", e);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongInterface(this.inputStream);
    }
}
