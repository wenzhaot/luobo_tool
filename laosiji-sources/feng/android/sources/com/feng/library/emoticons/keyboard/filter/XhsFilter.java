package com.feng.library.emoticons.keyboard.filter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.TextUtils;
import android.widget.EditText;
import com.feng.library.emoticons.keyboard.EmojiDisplayListener;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonFilter;
import com.feng.library.emoticons.keyboard.utils.EmoticonsKeyboardUtils;
import com.feng.library.emoticons.keyboard.widget.EditTextEmoticonSpan;
import com.feng.library.emoticons.keyboard.widget.EmoticonSpan;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XhsFilter extends EmoticonFilter {
    public static final int WRAP_DRAWABLE = -1;
    public static final Pattern XHS_RANGE = Pattern.compile("\\[5X:[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]");
    public static final Pattern XHS_RANGE_TEXT = Pattern.compile("\\[\\$([^x00-xff]+|[A-Za-z0-9]+)\\$\\]");
    private int emoticonSize = -1;

    public static Matcher getMatcher(CharSequence matchStr) {
        return XHS_RANGE.matcher(matchStr);
    }

    public static Matcher getMatcherText(CharSequence matchStr) {
        return XHS_RANGE_TEXT.matcher(matchStr);
    }

    public void filter(EditText editText, CharSequence text, int start, int lengthBefore, int lengthAfter) {
        String icon;
        this.emoticonSize = this.emoticonSize == -1 ? EmoticonsKeyboardUtils.getFontHeight(editText) : this.emoticonSize;
        clearEditTextSpan(editText.getText(), start, text.toString().length());
        Matcher m = getMatcher(text.toString().substring(start, text.toString().length()));
        if (m != null) {
            while (m.find()) {
                icon = (String) EmoticonsRule.sXhsEmoticonHashMap.get(m.group());
                if (!TextUtils.isEmpty(icon)) {
                    emoticonDisplay_EditText(editText.getContext(), editText.getText(), icon, this.emoticonSize, m.start() + start, m.end() + start);
                }
            }
        }
        m = getMatcherText(text.toString().substring(start, text.toString().length()));
        if (m != null) {
            while (m.find()) {
                String key_code = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(m.group());
                if (!TextUtils.isEmpty(key_code)) {
                    icon = (String) EmoticonsRule.sXhsEmoticonHashMap.get(key_code);
                    if (!TextUtils.isEmpty(icon)) {
                        emoticonDisplay_EditText(editText.getContext(), editText.getText(), icon, this.emoticonSize, m.start() + start, m.end() + start);
                    }
                }
            }
        }
    }

    public static Spannable spannableFilter(Context context, Spannable spannable, CharSequence text, int fontSize, EmojiDisplayListener emojiDisplayListener) {
        String icon;
        Matcher m = getMatcher(text);
        if (m != null) {
            while (m.find()) {
                icon = (String) EmoticonsRule.sXhsEmoticonHashMap.get(m.group());
                if (emojiDisplayListener != null) {
                    emojiDisplayListener.onEmojiDisplay(context, spannable, icon, fontSize, m.start(), m.end());
                } else if (!TextUtils.isEmpty(icon)) {
                    emoticonDisplay(context, spannable, icon, fontSize, m.start(), m.end());
                }
            }
        }
        m = getMatcherText(text);
        if (m != null) {
            while (m.find()) {
                String key_code = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(m.group());
                if (!TextUtils.isEmpty(key_code)) {
                    icon = (String) EmoticonsRule.sXhsEmoticonHashMap.get(key_code);
                    if (emojiDisplayListener != null) {
                        emojiDisplayListener.onEmojiDisplay(context, spannable, icon, fontSize, m.start(), m.end());
                    } else if (!TextUtils.isEmpty(icon)) {
                        emoticonDisplay(context, spannable, icon, fontSize, m.start(), m.end());
                    }
                }
            }
        }
        return spannable;
    }

    private void clearEditTextSpan(Spannable spannable, int start, int end) {
        if (start != end) {
            EditTextEmoticonSpan[] oldSpans = (EditTextEmoticonSpan[]) spannable.getSpans(start, end, EditTextEmoticonSpan.class);
            for (Object removeSpan : oldSpans) {
                spannable.removeSpan(removeSpan);
            }
        }
    }

    public static void emoticonDisplay(Context context, Spannable spannable, String emoticonName, int fontSize, int start, int end) {
        Drawable drawable = EmoticonFilter.getDrawable(context, emoticonName);
        if (drawable != null) {
            int itemWidth;
            if (fontSize == -1) {
                int itemHeight = drawable.getIntrinsicHeight();
                itemWidth = drawable.getIntrinsicWidth();
            } else {
                fontSize -= 8;
                itemWidth = (int) ((((float) (drawable.getIntrinsicWidth() * fontSize)) * 1.0f) / ((float) drawable.getIntrinsicHeight()));
            }
            drawable.setBounds(0, 0, itemWidth, fontSize);
            spannable.setSpan(new EmoticonSpan(drawable), start, end, 17);
        }
    }

    public static void emoticonDisplay_EditText(Context context, Spannable spannable, String emoticonName, int fontSize, int start, int end) {
        Drawable drawable = EmoticonFilter.getDrawable(context, emoticonName);
        if (drawable != null) {
            int itemWidth;
            if (fontSize == -1) {
                int itemHeight = drawable.getIntrinsicHeight();
                itemWidth = drawable.getIntrinsicWidth();
            } else {
                fontSize -= 8;
                itemWidth = (int) ((((float) (drawable.getIntrinsicWidth() * fontSize)) * 1.0f) / ((float) drawable.getIntrinsicHeight()));
            }
            drawable.setBounds(0, 0, itemWidth, fontSize);
            spannable.setSpan(new EditTextEmoticonSpan(drawable), start, end, 17);
        }
    }
}
