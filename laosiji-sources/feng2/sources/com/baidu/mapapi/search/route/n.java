package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class n implements Creator<SuggestAddrInfo> {
    n() {
    }

    /* renamed from: a */
    public SuggestAddrInfo createFromParcel(Parcel parcel) {
        return new SuggestAddrInfo(parcel);
    }

    /* renamed from: a */
    public SuggestAddrInfo[] newArray(int i) {
        return new SuggestAddrInfo[i];
    }
}
