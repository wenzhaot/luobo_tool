package com.feng.car.view.textview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import com.tencent.stat.DeviceInfo;

public class MyURLSpan extends ClickableSpan {
    private int color;
    private OnUrlClickListener mListener;
    private final String mURL;

    public interface OnUrlClickListener {
        void onLongClick();

        void onUrlClick();
    }

    public MyURLSpan(String url) {
        this.mURL = url;
    }

    public void setOnUrlClickListener(OnUrlClickListener listener) {
        this.mListener = listener;
    }

    public MyURLSpan(Parcel src) {
        this.mURL = src.readString();
    }

    public int getSpanTypeId() {
        return 11;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mURL);
    }

    public String getURL() {
        return this.mURL;
    }

    public void onClick(View widget) {
        if (this.mListener != null) {
            this.mListener.onUrlClick();
            return;
        }
        Uri uri = Uri.parse(getURL());
        Context context = widget.getContext();
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        intent.putExtra("com.android.browser.application_id", context.getPackageName());
        context.startActivity(intent);
    }

    public void onLongClick(View widget) {
        if (this.mListener != null) {
            this.mListener.onLongClick();
            return;
        }
        Uri data = Uri.parse(getURL());
        if (data != null) {
            String d = data.toString();
            String newValue = "";
            if (d.startsWith("com.feng.car.intent")) {
                newValue = d.substring(d.lastIndexOf("/") + 1);
            } else if (d.startsWith("http")) {
                newValue = d;
            }
            if (!TextUtils.isEmpty(newValue)) {
                ((ClipboardManager) widget.getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(DeviceInfo.TAG_IMEI, newValue));
            }
        }
    }

    public void updateDrawState(TextPaint tp) {
        if (this.color == 0) {
            tp.setColor(3384567);
        } else {
            tp.setColor(this.color);
        }
    }

    public void setColor(int color) {
        this.color = color;
    }
}
