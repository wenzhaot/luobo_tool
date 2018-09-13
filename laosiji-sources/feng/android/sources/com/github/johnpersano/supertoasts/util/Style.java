package com.github.johnpersano.supertoasts.util;

import android.os.Build.VERSION;
import com.github.johnpersano.supertoasts.R;
import com.github.johnpersano.supertoasts.SuperToast.Animations;

public class Style {
    public static final int BLACK = 0;
    public static final int BLUE = 1;
    public static final int GRAY = 2;
    public static final int GREEN = 3;
    public static final int ORANGE = 4;
    public static final int PURPLE = 5;
    public static final int RED = 6;
    public static final int WHITE = 7;
    public Animations animations = Animations.FADE;
    public int background = getBackground(2);
    public int buttonTextColor = -3355444;
    public int dividerColor = -1;
    public int textColor = -1;
    public int typefaceStyle = 0;

    public static Style getStyle(int styleType) {
        Style style = new Style();
        switch (styleType) {
            case 0:
                style.textColor = -1;
                style.background = getBackground(0);
                style.dividerColor = -1;
                break;
            case 1:
                style.textColor = -1;
                style.background = getBackground(1);
                style.dividerColor = -1;
                break;
            case 2:
                style.textColor = -1;
                style.background = getBackground(2);
                style.dividerColor = -1;
                style.buttonTextColor = -7829368;
                break;
            case 3:
                style.textColor = -1;
                style.background = getBackground(3);
                style.dividerColor = -1;
                break;
            case 4:
                style.textColor = -1;
                style.background = getBackground(4);
                style.dividerColor = -1;
                break;
            case 5:
                style.textColor = -1;
                style.background = getBackground(5);
                style.dividerColor = -1;
                break;
            case 6:
                style.textColor = -1;
                style.background = getBackground(6);
                style.dividerColor = -1;
                break;
            case 7:
                style.textColor = -12303292;
                style.background = getBackground(7);
                style.dividerColor = -12303292;
                style.buttonTextColor = -7829368;
                break;
            default:
                style.textColor = -1;
                style.background = getBackground(2);
                style.dividerColor = -1;
                break;
        }
        return style;
    }

    public static Style getStyle(int styleType, Animations animations) {
        Style style = new Style();
        style.animations = animations;
        switch (styleType) {
            case 0:
                style.textColor = -1;
                style.background = getBackground(0);
                style.dividerColor = -1;
                break;
            case 1:
                style.textColor = -1;
                style.background = getBackground(1);
                style.dividerColor = -1;
                break;
            case 2:
                style.textColor = -1;
                style.background = getBackground(2);
                style.dividerColor = -1;
                style.buttonTextColor = -7829368;
                break;
            case 3:
                style.textColor = -1;
                style.background = getBackground(3);
                style.dividerColor = -1;
                break;
            case 4:
                style.textColor = -1;
                style.background = getBackground(4);
                style.dividerColor = -1;
                break;
            case 5:
                style.textColor = -1;
                style.background = getBackground(5);
                style.dividerColor = -1;
                break;
            case 6:
                style.textColor = -1;
                style.background = getBackground(6);
                style.dividerColor = -1;
                break;
            case 7:
                style.textColor = -12303292;
                style.background = getBackground(7);
                style.dividerColor = -12303292;
                style.buttonTextColor = -7829368;
                break;
            default:
                style.textColor = -1;
                style.background = getBackground(2);
                style.dividerColor = -1;
                break;
        }
        return style;
    }

    public static int getBackground(int style) {
        if (VERSION.SDK_INT >= 19) {
            switch (style) {
                case 0:
                    return R.drawable.background_kitkat_black;
                case 1:
                    return R.drawable.background_kitkat_blue;
                case 2:
                    return R.drawable.background_kitkat_gray;
                case 3:
                    return R.drawable.background_kitkat_green;
                case 4:
                    return R.drawable.background_kitkat_orange;
                case 5:
                    return R.drawable.background_kitkat_purple;
                case 6:
                    return R.drawable.background_kitkat_red;
                case 7:
                    return R.drawable.background_kitkat_white;
                default:
                    return R.drawable.background_kitkat_gray;
            }
        }
        switch (style) {
            case 0:
                return R.drawable.background_standard_black;
            case 1:
                return R.drawable.background_standard_blue;
            case 2:
                return R.drawable.background_standard_gray;
            case 3:
                return R.drawable.background_standard_green;
            case 4:
                return R.drawable.background_standard_orange;
            case 5:
                return R.drawable.background_standard_purple;
            case 6:
                return R.drawable.background_standard_red;
            case 7:
                return R.drawable.background_standard_white;
            default:
                return R.drawable.background_standard_gray;
        }
    }
}
