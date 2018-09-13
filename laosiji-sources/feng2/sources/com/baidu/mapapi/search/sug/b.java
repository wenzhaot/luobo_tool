package com.baidu.mapapi.search.sug;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;

final class b implements Creator<SuggestionInfo> {
    b() {
    }

    /* renamed from: a */
    public SuggestionInfo createFromParcel(Parcel parcel) {
        return new SuggestionInfo(parcel);
    }

    /* renamed from: a */
    public SuggestionInfo[] newArray(int i) {
        return new SuggestionInfo[i];
    }
}
