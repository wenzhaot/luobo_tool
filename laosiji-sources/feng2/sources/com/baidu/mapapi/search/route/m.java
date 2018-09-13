package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class m implements Creator<PlanNode> {
    m() {
    }

    /* renamed from: a */
    public PlanNode createFromParcel(Parcel parcel) {
        return new PlanNode(parcel);
    }

    /* renamed from: a */
    public PlanNode[] newArray(int i) {
        return new PlanNode[i];
    }
}
