package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;

final class s implements Creator<WalkingStep> {
    s() {
    }

    /* renamed from: a */
    public WalkingStep createFromParcel(Parcel parcel) {
        return new WalkingStep(parcel);
    }

    /* renamed from: a */
    public WalkingStep[] newArray(int i) {
        return new WalkingStep[i];
    }
}
