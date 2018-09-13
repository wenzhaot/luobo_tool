package com.feng.car.listener;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import com.feng.car.activity.AtUserActivity;

public class EditInputFilter implements InputFilter {
    private Context mContext;
    public boolean mInputFilterAt = false;

    public EditInputFilter(Context context) {
        this.mContext = context;
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source.toString().equalsIgnoreCase("@") || source.toString().equalsIgnoreCase("＠")) {
            this.mInputFilterAt = true;
            this.mContext.startActivity(new Intent(this.mContext, AtUserActivity.class));
        }
        return source;
    }
}
