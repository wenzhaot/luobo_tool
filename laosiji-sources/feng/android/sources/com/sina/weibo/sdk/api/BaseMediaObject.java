package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class BaseMediaObject implements Parcelable {
    public static final int MEDIA_TYPE_CMD = 7;
    public static final int MEDIA_TYPE_IMAGE = 2;
    public static final int MEDIA_TYPE_MUSIC = 3;
    public static final int MEDIA_TYPE_TEXT = 1;
    public static final int MEDIA_TYPE_VIDEO = 4;
    public static final int MEDIA_TYPE_VOICE = 6;
    public static final int MEDIA_TYPE_WEBPAGE = 5;
    public String actionUrl;
    public String description;
    public String identify;
    public String schema;
    public byte[] thumbData;
    public String title;

    public BaseMediaObject(Parcel parcel) {
        this.actionUrl = parcel.readString();
        this.schema = parcel.readString();
        this.identify = parcel.readString();
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.thumbData = parcel.createByteArray();
    }

    protected boolean checkArgs() {
        return this.actionUrl != null && this.actionUrl.length() <= 512 && this.identify != null && this.identify.length() <= 512 && this.thumbData != null && this.thumbData.length <= 32768 && this.title != null && this.title.length() <= 512 && this.description != null && this.description.length() <= 1024;
    }

    public int describeContents() {
        return 0;
    }

    public abstract int getObjType();

    /* JADX WARNING: Removed duplicated region for block: B:32:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0025 A:{SYNTHETIC, Splitter: B:15:0x0025} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0032 A:{SYNTHETIC, Splitter: B:22:0x0032} */
    public final void setThumbImage(android.graphics.Bitmap r4) {
        /*
        r3 = this;
        r2 = 0;
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x001e, all -> 0x002e }
        r1.<init>();	 Catch:{ Exception -> 0x001e, all -> 0x002e }
        r0 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ Exception -> 0x003d }
        r2 = 85;
        r4.compress(r0, r2, r1);	 Catch:{ Exception -> 0x003d }
        r0 = r1.toByteArray();	 Catch:{ Exception -> 0x003d }
        r3.thumbData = r0;	 Catch:{ Exception -> 0x003d }
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
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.api.BaseMediaObject.setThumbImage(android.graphics.Bitmap):void");
    }

    protected abstract BaseMediaObject toExtraMediaObject(String str);

    protected abstract String toExtraMediaString();

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.actionUrl);
        parcel.writeString(this.schema);
        parcel.writeString(this.identify);
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeByteArray(this.thumbData);
    }
}
