package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class m implements Creator<TransitResultNode> {
    m() {
    }

    /* renamed from: a */
    public TransitResultNode createFromParcel(Parcel parcel) {
        return new TransitResultNode(parcel);
    }

    /* renamed from: a */
    public TransitResultNode[] newArray(int i) {
        return new TransitResultNode[i];
    }
}
