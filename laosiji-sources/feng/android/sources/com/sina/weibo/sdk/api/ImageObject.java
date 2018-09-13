package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.io.File;

public class ImageObject extends BaseMediaObject {
    public static final Creator<ImageObject> CREATOR = new Creator() {
        public ImageObject createFromParcel(Parcel parcel) {
            return new ImageObject(parcel);
        }

        public ImageObject[] newArray(int i) {
            return new ImageObject[i];
        }
    };
    private static final int DATA_SIZE = 2097152;
    public byte[] imageData;
    public String imagePath;

    public ImageObject(Parcel parcel) {
        this.imageData = parcel.createByteArray();
        this.imagePath = parcel.readString();
    }

    public boolean checkArgs() {
        if (this.imageData == null && this.imagePath == null) {
            return false;
        }
        if (this.imageData != null && this.imageData.length > DATA_SIZE) {
            return false;
        }
        if (this.imagePath != null && this.imagePath.length() > 512) {
            return false;
        }
        if (this.imagePath != null) {
            File file = new File(this.imagePath);
            try {
                if (!file.exists() || file.length() == 0 || file.length() > 10485760) {
                    return false;
                }
            } catch (SecurityException e) {
                return false;
            }
        }
        return true;
    }

    public int describeContents() {
        return 0;
    }

    public int getObjType() {
        return 2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0025 A:{SYNTHETIC, Splitter: B:15:0x0025} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0032 A:{SYNTHETIC, Splitter: B:22:0x0032} */
    public final void setImageObject(android.graphics.Bitmap r4) {
        /*
        r3 = this;
        r2 = 0;
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x001e, all -> 0x002e }
        r1.<init>();	 Catch:{ Exception -> 0x001e, all -> 0x002e }
        r0 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ Exception -> 0x003d }
        r2 = 85;
        r4.compress(r0, r2, r1);	 Catch:{ Exception -> 0x003d }
        r0 = r1.toByteArray();	 Catch:{ Exception -> 0x003d }
        r3.imageData = r0;	 Catch:{ Exception -> 0x003d }
        if (r1 == 0) goto L_0x0018;
    L_0x0015:
        r1.close();	 Catch:{ IOException -> 0x0019 }
    L_0x0018:
        return;
    L_0x0019:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0018;
    L_0x001e:
        r0 = move-exception;
        r1 = r2;
    L_0x0020:
        r0.printStackTrace();	 Catch:{ all -> 0x003b }
        if (r1 == 0) goto L_0x0018;
    L_0x0025:
        r1.close();	 Catch:{ IOException -> 0x0029 }
        goto L_0x0018;
    L_0x0029:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0018;
    L_0x002e:
        r0 = move-exception;
        r1 = r2;
    L_0x0030:
        if (r1 == 0) goto L_0x0035;
    L_0x0032:
        r1.close();	 Catch:{ IOException -> 0x0036 }
    L_0x0035:
        throw r0;
    L_0x0036:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0035;
    L_0x003b:
        r0 = move-exception;
        goto L_0x0030;
    L_0x003d:
        r0 = move-exception;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.api.ImageObject.setImageObject(android.graphics.Bitmap):void");
    }

    protected BaseMediaObject toExtraMediaObject(String str) {
        return this;
    }

    protected String toExtraMediaString() {
        return "";
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.imageData);
        parcel.writeString(this.imagePath);
    }
}
