package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class k implements Creator<TrainInfo> {
    k() {
    }

    /* renamed from: a */
    public TrainInfo createFromParcel(Parcel parcel) {
        return new TrainInfo(parcel);
    }

    /* renamed from: a */
    public TrainInfo[] newArray(int i) {
        return new TrainInfo[i];
    }
}
