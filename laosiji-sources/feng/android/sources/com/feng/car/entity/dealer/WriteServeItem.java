package com.feng.car.entity.dealer;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class WriteServeItem extends BaseObservable {
    private transient OnWriteTextChange change = null;
    private String content = "";
    private String price = "";
    public int type = 0;

    public WriteServeItem(OnWriteTextChange change) {
        this.change = change;
    }

    @Bindable
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
        if (this.change != null) {
            OnWriteTextChange onWriteTextChange = this.change;
            boolean z = content.length() > 0 && this.type == 0;
            onWriteTextChange.onChange(z);
        }
        notifyPropertyChanged(17);
    }

    @Bindable
    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
        if (this.change != null) {
            OnWriteTextChange onWriteTextChange = this.change;
            boolean z = price.length() > 0 && this.type == 0;
            onWriteTextChange.onChange(z);
        }
        notifyPropertyChanged(47);
    }
}
