package com.feng.library.emoticons.keyboard.interfaces;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.widget.EditText;
import com.feng.library.emoticons.keyboard.utils.DrawableManager;
import java.io.IOException;

public abstract class EmoticonFilter {
    public abstract void filter(EditText editText, CharSequence charSequence, int i, int i2, int i3);

    public static Drawable getDrawableFromAssets(Context context, String emoticonName) {
        try {
            return new BitmapDrawable(BitmapFactory.decodeStream(context.getAssets().open(emoticonName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable getDrawable(Context context, String emoticonName) {
        if (TextUtils.isEmpty(emoticonName)) {
            return null;
        }
        return DrawableManager.getInstance().getDrawable(emoticonName);
    }

    public static Drawable getDrawable(Context context, int emoticon) {
        if (emoticon <= 0) {
            return null;
        }
        try {
            return VERSION.SDK_INT >= 21 ? context.getResources().getDrawable(emoticon, (Theme) null) : context.getResources().getDrawable(emoticon);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
