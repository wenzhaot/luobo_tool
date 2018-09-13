package com.feng.car.entity.sendpost;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class SoftKeyOpen extends BaseObservable {
    private Boolean openSoftKeyboard = Boolean.valueOf(false);

    @Bindable
    public boolean isOpenSoftKeyboard() {
        return this.openSoftKeyboard.booleanValue();
    }

    public void setOpenSoftKeyboard(boolean openSoftKeyboard) {
        this.openSoftKeyboard = Boolean.valueOf(openSoftKeyboard);
        notifyPropertyChanged(44);
    }
}
