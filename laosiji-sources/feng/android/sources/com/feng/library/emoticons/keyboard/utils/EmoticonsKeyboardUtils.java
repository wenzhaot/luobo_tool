package com.feng.library.emoticons.keyboard.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class EmoticonsKeyboardUtils {
    private static final int DEF_KEYBOARD_HEAGH_WITH_DP = 300;
    private static final String EXTRA_DEF_KEYBOARDHEIGHT = "DEF_KEYBOARDHEIGHT";
    private static int sDefKeyboardHeight = -1;

    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static int getDisplayWidthPixels(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getFontHeight(TextView textView) {
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil((double) (fm.bottom - fm.top));
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(16908290)).getChildAt(0);
    }

    public static int getDefKeyboardHeight(Context context) {
        if (sDefKeyboardHeight < 0) {
            sDefKeyboardHeight = dip2px(context, 300.0f);
        }
        int height = PreferenceManager.getDefaultSharedPreferences(context).getInt(EXTRA_DEF_KEYBOARDHEIGHT, 0);
        if (height <= 0 || sDefKeyboardHeight == height) {
            height = sDefKeyboardHeight;
        }
        sDefKeyboardHeight = height;
        return height;
    }

    public static void setDefKeyboardHeight(Context context, int height) {
        if (sDefKeyboardHeight != height) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(EXTRA_DEF_KEYBOARDHEIGHT, height).commit();
            sDefKeyboardHeight = height;
        }
    }

    public static boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags & 1024) != 0;
    }

    public static void openSoftKeyboard(EditText et) {
        if (et != null) {
            et.setFocusable(true);
            et.setFocusableInTouchMode(true);
            et.requestFocus();
            ((InputMethodManager) et.getContext().getSystemService("input_method")).showSoftInput(et, 0);
        }
    }

    public static void openSoftKeyboard(Context context, EditText et) {
        if (et != null) {
            et.setFocusable(true);
            et.setFocusableInTouchMode(true);
            et.requestFocus();
            ((InputMethodManager) et.getContext().getSystemService("input_method")).showSoftInput(et, 0);
            return;
        }
        ((InputMethodManager) context.getSystemService("input_method")).toggleSoftInput(0, 2);
    }

    public static void closeSoftKeyboard(Context context) {
        if (context != null && (context instanceof Activity) && ((Activity) context).getCurrentFocus() != null) {
            try {
                View view = ((Activity) context).getCurrentFocus();
                ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeSoftKeyboard(View view) {
        if (view != null && view.getWindowToken() != null) {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
