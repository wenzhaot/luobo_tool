package com.umeng.social.tool;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

public class ComposeTool {
    public static int backgroundColor = -1;
    public static ComposeDirection direction = ComposeDirection.CUSTOM;
    public static int textColor = -16777216;
    public static int textsize = 18;
    public static Typeface typeface = Typeface.DEFAULT;

    public enum ComposeDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        LEFTUP,
        LEFTDOWN,
        RIGHTUP,
        RIGHTDOWN,
        CUSTOM
    }

    public static Bitmap createCompose(Bitmap src1, Bitmap src2, boolean isvertical, int space) {
        if (src1 == null) {
            return null;
        }
        if (src2 == null) {
            return null;
        }
        int w = src1.getWidth();
        int h = src1.getHeight();
        int ww = src2.getWidth();
        int wh = src2.getHeight();
        Bitmap newb = Bitmap.createBitmap(isvertical ? Math.max(w, ww) : (w + ww) + space, isvertical ? (h + wh) + space : Math.max(h, wh), Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src1, 0.0f, 0.0f, null);
        if (isvertical) {
            cv.drawBitmap(src2, 0.0f, (float) (h + space), null);
        } else {
            cv.drawBitmap(src2, (float) (w + space), 0.0f, null);
        }
        cv.save(31);
        cv.restore();
        return newb;
    }

    public static Bitmap createWaterMask(Bitmap src, Bitmap watermark, int x, int y) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        int wc = (w / 2) - (ww / 2);
        int hc = (h / 2) - (wh / 2);
        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0.0f, 0.0f, null);
        if (direction == ComposeDirection.CUSTOM) {
            cv.drawBitmap(watermark, (float) x, (float) y, null);
        } else if (direction == ComposeDirection.UP) {
            cv.drawBitmap(watermark, (float) wc, 0.0f, null);
        } else if (direction == ComposeDirection.DOWN) {
            cv.drawBitmap(watermark, (float) wc, (float) (h - wh), null);
        } else if (direction == ComposeDirection.LEFT) {
            cv.drawBitmap(watermark, 0.0f, (float) hc, null);
        } else if (direction == ComposeDirection.RIGHT) {
            cv.drawBitmap(watermark, (float) (w - ww), (float) hc, null);
        } else if (direction == ComposeDirection.LEFTUP) {
            cv.drawBitmap(watermark, 0.0f, 0.0f, null);
        } else if (direction == ComposeDirection.LEFTDOWN) {
            cv.drawBitmap(watermark, 0.0f, (float) (h - wh), null);
        } else if (direction == ComposeDirection.RIGHTUP) {
            cv.drawBitmap(watermark, (float) (w - ww), 0.0f, null);
        } else if (direction == ComposeDirection.RIGHTDOWN) {
            cv.drawBitmap(watermark, (float) (w - ww), (float) (h - wh), null);
        }
        cv.save(31);
        cv.restore();
        return newb;
    }

    public static Bitmap createTextImage(String s, Bitmap bitmap, int widthspace, int heightspace) {
        Config bitmapConfig = bitmap.getConfig();
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if (bitmapConfig == null) {
            bitmapConfig = Config.ARGB_8888;
        }
        TextPaint paint = new TextPaint(1);
        paint.setColor(textColor);
        paint.setTextSize((float) textsize);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setTypeface(typeface);
        StaticLayout layout = new StaticLayout(s, paint, w, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        paint.getTextBounds(s, 0, s.length(), new Rect());
        bitmap = bitmap.copy(bitmapConfig, true);
        Bitmap newb = Bitmap.createBitmap((widthspace * 2) + w, (layout.getHeight() + h) + (heightspace * 4), Config.ARGB_8888);
        Canvas canvas = new Canvas(newb);
        canvas.drawColor(backgroundColor);
        canvas.drawBitmap(bitmap, (float) widthspace, (float) (layout.getHeight() + (heightspace * 3)), null);
        canvas.translate((float) widthspace, (float) heightspace);
        layout.draw(canvas);
        canvas.save(31);
        canvas.restore();
        return newb;
    }
}
