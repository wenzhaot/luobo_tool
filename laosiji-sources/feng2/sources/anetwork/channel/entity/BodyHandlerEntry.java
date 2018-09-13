package anetwork.channel.entity;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import anet.channel.bytes.ByteArray;
import anet.channel.request.BodyEntry;
import anetwork.channel.IBodyHandler;
import anetwork.channel.aidl.ParcelableBodyHandler;
import anetwork.channel.aidl.ParcelableBodyHandler.Stub;
import anetwork.channel.aidl.adapter.ParcelableBodyHandlerWrapper;
import java.io.IOException;
import java.io.OutputStream;

/* compiled from: Taobao */
public class BodyHandlerEntry implements BodyEntry {
    public static final Creator<BodyHandlerEntry> CREATOR = new Creator<BodyHandlerEntry>() {
        public BodyHandlerEntry createFromParcel(Parcel parcel) {
            BodyHandlerEntry bodyHandlerEntry = new BodyHandlerEntry();
            bodyHandlerEntry.bodyHandler = Stub.asInterface(parcel.readStrongBinder());
            return bodyHandlerEntry;
        }

        public BodyHandlerEntry[] newArray(int i) {
            return new BodyHandlerEntry[i];
        }
    };
    ParcelableBodyHandler bodyHandler;

    public BodyHandlerEntry(IBodyHandler iBodyHandler) {
        this.bodyHandler = null;
        this.bodyHandler = new ParcelableBodyHandlerWrapper(iBodyHandler);
    }

    private BodyHandlerEntry() {
        this.bodyHandler = null;
    }

    public String getContentType() {
        return null;
    }

    public int writeTo(OutputStream outputStream) throws IOException {
        int i = 0;
        try {
            ByteArray a = a.a.a(2048);
            while (!this.bodyHandler.isCompleted()) {
                int read = this.bodyHandler.read(a.getBuffer());
                outputStream.write(a.getBuffer(), 0, read);
                i += read;
            }
            a.recycle();
            return i;
        } catch (Throwable e) {
            throw new IOException("RemoteException", e);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongInterface(this.bodyHandler);
    }
}
