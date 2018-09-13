package com.feng.library.utils;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ExToast {
    public static final int LENGTH_ALWAYS = 0;
    public static final int LENGTH_LONG = 3500;
    public static final int LENGTH_SHORT = 2000;
    private int animations = -1;
    private Handler handler = new Handler();
    private Method hide;
    private Runnable hideRunnable = new Runnable() {
        public void run() {
            ExToast.this.hide();
        }
    };
    private boolean isShow = false;
    private Context mContext;
    private int mDuration = 2000;
    private Object mTN;
    private Method show;
    private Toast toast;

    public ExToast(Context context) {
        this.mContext = context;
        if (this.toast == null) {
            this.toast = new Toast(this.mContext);
        }
    }

    public void show() {
        if (!this.isShow) {
            initTN();
            try {
                this.show.invoke(this.mTN, new Object[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.isShow = true;
            if (this.mDuration > 0) {
                this.handler.postDelayed(this.hideRunnable, (long) this.mDuration);
            }
        }
    }

    public void hide() {
        if (this.isShow) {
            try {
                this.hide.invoke(this.mTN, new Object[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.isShow = false;
        }
    }

    public void setView(View view) {
        this.toast.setView(view);
    }

    public View getView() {
        return this.toast.getView();
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setMargin(float horizontalMargin, float verticalMargin) {
        this.toast.setMargin(horizontalMargin, verticalMargin);
    }

    public float getHorizontalMargin() {
        return this.toast.getHorizontalMargin();
    }

    public float getVerticalMargin() {
        return this.toast.getVerticalMargin();
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        this.toast.setGravity(gravity, xOffset, yOffset);
    }

    public int getGravity() {
        return this.toast.getGravity();
    }

    public int getXOffset() {
        return this.toast.getXOffset();
    }

    public int getYOffset() {
        return this.toast.getYOffset();
    }

    public static ExToast makeText(Context context, CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, text, 0);
        ExToast exToast = new ExToast(context);
        exToast.toast = toast;
        exToast.mDuration = duration;
        return exToast;
    }

    public static ExToast makeText(Context context, int resId, int duration) throws NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }

    public void setText(int resId) {
        setText(this.mContext.getText(resId));
    }

    public void setText(CharSequence s) {
        this.toast.setText(s);
    }

    public int getAnimations() {
        return this.animations;
    }

    public void setAnimations(int animations) {
        this.animations = animations;
    }

    private void initTN() {
        try {
            Field tnField = this.toast.getClass().getDeclaredField("mTN");
            tnField.setAccessible(true);
            this.mTN = tnField.get(this.toast);
            this.show = this.mTN.getClass().getMethod("show", new Class[0]);
            this.hide = this.mTN.getClass().getMethod("hide", new Class[0]);
            if (this.animations != -1) {
                Field tnParamsField = this.mTN.getClass().getDeclaredField("mParams");
                tnParamsField.setAccessible(true);
                ((LayoutParams) tnParamsField.get(this.mTN)).windowAnimations = this.animations;
            }
            Field tnNextViewField = this.mTN.getClass().getDeclaredField("mNextView");
            tnNextViewField.setAccessible(true);
            tnNextViewField.set(this.mTN, this.toast.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
