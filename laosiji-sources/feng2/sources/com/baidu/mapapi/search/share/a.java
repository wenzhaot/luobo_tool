package com.baidu.mapapi.search.share;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class a implements Creator<ShareUrlResult> {
    a() {
    }

    /* renamed from: a */
    public ShareUrlResult createFromParcel(Parcel parcel) {
        return new ShareUrlResult(parcel);
    }

    /* renamed from: a */
    public ShareUrlResult[] newArray(int i) {
        return new ShareUrlResult[i];
    }
}
