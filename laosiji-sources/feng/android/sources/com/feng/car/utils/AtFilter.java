package com.feng.car.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.widget.EditText;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonFilter;
import com.feng.library.emoticons.keyboard.utils.DrawableManager;
import com.feng.library.emoticons.keyboard.utils.EmoticonsKeyboardUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtFilter extends EmoticonFilter {
    private int emoticonSize = -1;
    private boolean mIsAllowAddTopic = true;
    private Pattern mPatternUrl = Pattern.compile("http://[a-zA-Z0-9+&@#/%?=~_\\-|!:,\\.;]*[a-zA-Z0-9+&@#/%=~_|]");
    private Pattern mPatternUser = Pattern.compile("@[-_a-zA-Z0-9\\u4E00-\\u9FA5]+");
    private int mTextSize = 0;

    public AtFilter(boolean isAddTopic) {
        this.mIsAllowAddTopic = isAddTopic;
    }

    public void filter(EditText editText, CharSequence text, int start, int lengthBefore, int lengthAfter) {
        this.emoticonSize = this.emoticonSize == -1 ? EmoticonsKeyboardUtils.getFontHeight(editText) : this.emoticonSize;
        this.mTextSize = ((int) TypedValue.applyDimension(1, 14.0f, editText.getResources().getDisplayMetrics())) + 2;
        Matcher m = this.mPatternUser.matcher(text);
        clearEditTextColorSpan(editText.getText(), 0, text.length());
        if (m != null) {
            while (m.find()) {
                editText.getText().setSpan(new ForegroundColorSpan(ContextCompat.getColor(editText.getContext(), 2131558463)), m.start(), m.end(), 33);
            }
        }
        Matcher matcherImage = HttpConstant.PATTERN_IMAGE.matcher(text.toString().substring(start, text.toString().length()));
        if (matcherImage != null) {
            while (matcherImage.find()) {
                setAtImageSpan(editText.getContext(), editText.getText(), "查看图片", this.emoticonSize, start + matcherImage.start(), start + matcherImage.end(), false, true);
            }
        }
        Matcher matcherVideo = HttpConstant.PATTERN_VIDEO.matcher(text.toString().substring(start, text.toString().length()));
        if (matcherVideo != null) {
            while (matcherVideo.find()) {
                setAtImageSpan(editText.getContext(), editText.getText(), "查看视频", this.emoticonSize, start + matcherVideo.start(), start + matcherVideo.end(), false, false);
            }
        }
    }

    private void setAtImageSpan(Context context, Spannable spannable, String nameStr, int fontSize, int start, int end, boolean isAt, boolean isImage) {
        if (nameStr != null && nameStr != null && nameStr.trim().length() > 0) {
            final Drawable drawable = DrawableManager.getInstance().getDrawable(nameStr);
            if (drawable == null) {
                final Bitmap bmp = isAt ? getNameBitmap(context, nameStr, fontSize) : getSeeOriginalBitmap(context, fontSize, isImage);
                final Context context2 = context;
                final String str = nameStr;
                spannable.setSpan(new DynamicDrawableSpan(0) {
                    public Drawable getDrawable() {
                        BitmapDrawable drawable = new BitmapDrawable(context2.getResources(), bmp);
                        drawable.setBounds(0, -10, bmp.getWidth(), bmp.getHeight() - 12);
                        DrawableManager.getInstance().putDrawable(str, drawable);
                        return drawable;
                    }
                }, start, end, 33);
                return;
            }
            spannable.setSpan(new DynamicDrawableSpan(0) {
                public Drawable getDrawable() {
                    return drawable;
                }
            }, start, end, 33);
        }
    }

    private Bitmap getNameBitmap(Context context, String name, int fontSize) {
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(2131558463));
        paint.setAntiAlias(true);
        paint.setTextSize((float) this.mTextSize);
        Rect rect = new Rect();
        paint.getTextBounds(name, 0, name.length(), rect);
        Bitmap bmp = Bitmap.createBitmap((int) paint.measureText(name), fontSize, Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(context.getResources().getColor(2131558558));
        canvas.drawText(name, (float) rect.left, (float) this.mTextSize, paint);
        return bmp;
    }

    private Bitmap getSeeOriginalBitmap(Context context, int fontSize, boolean isImage) {
        this.mTextSize = (int) TypedValue.applyDimension(1, 14.0f, context.getResources().getDisplayMetrics());
        String name = isImage ? "查看图片" : "查看视频";
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(2131558463));
        paint.setAntiAlias(true);
        paint.setTextSize((float) this.mTextSize);
        Rect rect = new Rect();
        paint.getTextBounds(name, 0, name.length(), rect);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), isImage ? 2130838090 : 2130838263);
        Bitmap bmp = Bitmap.createBitmap((bitmap.getWidth() + ((int) paint.measureText(name))) + 10, fontSize, Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(ContextCompat.getColor(context, 2131558558));
        canvas.drawBitmap(bitmap, (float) rect.left, (float) ((fontSize - bitmap.getHeight()) / 2), paint);
        canvas.drawText(name, (float) ((rect.left + bitmap.getWidth()) + 10), (float) this.mTextSize, paint);
        return bmp;
    }

    private void clearEditTextColorSpan(Spannable spannable, int start, int end) {
        if (start != end) {
            ForegroundColorSpan[] oldSpans = (ForegroundColorSpan[]) spannable.getSpans(start, end, ForegroundColorSpan.class);
            for (Object removeSpan : oldSpans) {
                spannable.removeSpan(removeSpan);
            }
        }
    }
}
