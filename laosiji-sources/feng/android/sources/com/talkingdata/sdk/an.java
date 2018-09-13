package com.talkingdata.sdk;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: td */
final class an implements Creator {
    an() {
    }

    public am createFromParcel(Parcel parcel) {
        try {
            return new am(parcel);
        } catch (Throwable th) {
            return null;
        }
    }

    public am[] newArray(int i) {
        try {
            return new am[i];
        } catch (Throwable th) {
            return null;
        }
    }
}
