package com.github.johnpersano.supertoasts;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.johnpersano.supertoasts.util.Style;
import com.stub.StubApp;

public class SuperToast {
    private static final String ERROR_CONTEXTNULL = " - You cannot use a null context.";
    private static final String ERROR_DURATIONTOOLONG = " - You should NEVER specify a duration greater than four and a half seconds for a SuperToast.";
    private static final String TAG = "SuperToast";
    private Animations mAnimations = Animations.FADE;
    private int mBackground;
    private Context mContext;
    private int mDuration = 2000;
    private int mGravity = 81;
    private TextView mMessageTextView;
    private OnDismissListener mOnDismissListener;
    private LinearLayout mRootLayout;
    private View mToastView;
    private int mTypefaceStyle;
    private WindowManager mWindowManager;
    private LayoutParams mWindowManagerParams;
    private int mXOffset = 0;
    private int mYOffset = 0;

    public enum Animations {
        FADE,
        FLYIN,
        SCALE,
        POPUP
    }

    public static class Background {
        public static final int BLACK = Style.getBackground(0);
        public static final int BLUE = Style.getBackground(1);
        public static final int GRAY = Style.getBackground(2);
        public static final int GREEN = Style.getBackground(3);
        public static final int ORANGE = Style.getBackground(4);
        public static final int PURPLE = Style.getBackground(5);
        public static final int RED = Style.getBackground(6);
        public static final int WHITE = Style.getBackground(7);
    }

    public static class Duration {
        public static final int EXTRA_LONG = 4500;
        public static final int LONG = 3500;
        public static final int MEDIUM = 2750;
        public static final int SHORT = 2000;
        public static final int VERY_SHORT = 1500;
    }

    public static class Icon {

        public static class Dark {
            public static final int EDIT = R.drawable.icon_dark_edit;
            public static final int EXIT = R.drawable.icon_dark_exit;
            public static final int INFO = R.drawable.icon_dark_info;
            public static final int REDO = R.drawable.icon_dark_redo;
            public static final int REFRESH = R.drawable.icon_dark_refresh;
            public static final int SAVE = R.drawable.icon_dark_save;
            public static final int SHARE = R.drawable.icon_dark_share;
            public static final int UNDO = R.drawable.icon_dark_undo;
        }

        public static class Light {
            public static final int EDIT = R.drawable.icon_light_edit;
            public static final int EXIT = R.drawable.icon_light_exit;
            public static final int INFO = R.drawable.icon_light_info;
            public static final int REDO = R.drawable.icon_light_redo;
            public static final int REFRESH = R.drawable.icon_light_refresh;
            public static final int SAVE = R.drawable.icon_light_save;
            public static final int SHARE = R.drawable.icon_light_share;
            public static final int UNDO = R.drawable.icon_light_undo;
        }
    }

    public enum IconPosition {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    public interface OnClickListener {
        void onClick(View view, Parcelable parcelable);
    }

    public interface OnDismissListener {
        void onDismiss(View view);
    }

    public static class TextSize {
        public static final int EXTRA_SMALL = 12;
        public static final int LARGE = 18;
        public static final int MEDIUM = 16;
        public static final int SMALL = 14;
    }

    public enum Type {
        STANDARD,
        PROGRESS,
        PROGRESS_HORIZONTAL,
        BUTTON
    }

    public SuperToast(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("SuperToast - You cannot use a null context.");
        }
        this.mContext = context;
        this.mYOffset = context.getResources().getDimensionPixelSize(R.dimen.toast_hover);
        this.mToastView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.supertoast, null);
        this.mWindowManager = (WindowManager) StubApp.getOrigApplicationContext(this.mToastView.getContext().getApplicationContext()).getSystemService("window");
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
    }

    public SuperToast(Context context, Style style) {
        if (context == null) {
            throw new IllegalArgumentException("SuperToast - You cannot use a null context.");
        }
        this.mContext = context;
        this.mYOffset = context.getResources().getDimensionPixelSize(R.dimen.toast_hover);
        this.mToastView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.supertoast, null);
        this.mWindowManager = (WindowManager) StubApp.getOrigApplicationContext(this.mToastView.getContext().getApplicationContext()).getSystemService("window");
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
        setStyle(style);
    }

    public void show() {
        this.mWindowManagerParams = new LayoutParams();
        this.mWindowManagerParams.height = -2;
        this.mWindowManagerParams.width = -2;
        this.mWindowManagerParams.flags = 152;
        this.mWindowManagerParams.format = -3;
        this.mWindowManagerParams.windowAnimations = getAnimation();
        this.mWindowManagerParams.type = 2005;
        this.mWindowManagerParams.gravity = this.mGravity;
        this.mWindowManagerParams.x = this.mXOffset;
        this.mWindowManagerParams.y = this.mYOffset;
        ManagerSuperToast.getInstance().add(this);
    }

    public void setText(CharSequence text) {
        this.mMessageTextView.setText(text);
    }

    public CharSequence getText() {
        return this.mMessageTextView.getText();
    }

    public void setTypefaceStyle(int typeface) {
        this.mTypefaceStyle = typeface;
        this.mMessageTextView.setTypeface(this.mMessageTextView.getTypeface(), typeface);
    }

    public int getTypefaceStyle() {
        return this.mTypefaceStyle;
    }

    public void setTextColor(int textColor) {
        this.mMessageTextView.setTextColor(textColor);
    }

    public int getTextColor() {
        return this.mMessageTextView.getCurrentTextColor();
    }

    public void setTextSize(int textSize) {
        this.mMessageTextView.setTextSize((float) textSize);
    }

    public float getTextSize() {
        return this.mMessageTextView.getTextSize();
    }

    public void setDuration(int duration) {
        if (duration > Duration.EXTRA_LONG) {
            Log.e(TAG, "SuperToast - You should NEVER specify a duration greater than four and a half seconds for a SuperToast.");
            this.mDuration = Duration.EXTRA_LONG;
            return;
        }
        this.mDuration = duration;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setIcon(int iconResource, IconPosition iconPosition) {
        if (iconPosition == IconPosition.BOTTOM) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, this.mContext.getResources().getDrawable(iconResource));
        } else if (iconPosition == IconPosition.LEFT) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(iconResource), null, null, null);
        } else if (iconPosition == IconPosition.RIGHT) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, this.mContext.getResources().getDrawable(iconResource), null);
        } else if (iconPosition == IconPosition.TOP) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(null, this.mContext.getResources().getDrawable(iconResource), null, null);
        }
    }

    public void setBackground(int background) {
        this.mBackground = background;
        this.mRootLayout.setBackgroundResource(background);
    }

    public int getBackground() {
        return this.mBackground;
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        this.mGravity = gravity;
        this.mXOffset = xOffset;
        this.mYOffset = yOffset;
    }

    public void setAnimations(Animations animations) {
        this.mAnimations = animations;
    }

    public Animations getAnimations() {
        return this.mAnimations;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    public void dismiss() {
        ManagerSuperToast.getInstance().removeSuperToast(this);
    }

    public TextView getTextView() {
        return this.mMessageTextView;
    }

    public View getView() {
        return this.mToastView;
    }

    public boolean isShowing() {
        return this.mToastView != null && this.mToastView.isShown();
    }

    public WindowManager getWindowManager() {
        return this.mWindowManager;
    }

    public LayoutParams getWindowManagerParams() {
        return this.mWindowManagerParams;
    }

    private int getAnimation() {
        if (this.mAnimations == Animations.FLYIN) {
            return 16973827;
        }
        if (this.mAnimations == Animations.SCALE) {
            return 16973826;
        }
        if (this.mAnimations == Animations.POPUP) {
            return 16973910;
        }
        return 16973828;
    }

    private void setStyle(Style style) {
        setAnimations(style.animations);
        setTypefaceStyle(style.typefaceStyle);
        setTextColor(style.textColor);
        setBackground(style.background);
    }

    public static SuperToast create(Context context, CharSequence textCharSequence, int durationInteger) {
        SuperToast superToast = new SuperToast(context);
        superToast.setText(textCharSequence);
        superToast.setDuration(durationInteger);
        return superToast;
    }

    public static SuperToast create(Context context, CharSequence textCharSequence, int durationInteger, Animations animations) {
        SuperToast superToast = new SuperToast(context);
        superToast.setText(textCharSequence);
        superToast.setDuration(durationInteger);
        superToast.setAnimations(animations);
        return superToast;
    }

    public static SuperToast create(Context context, CharSequence textCharSequence, int durationInteger, Style style) {
        SuperToast superToast = new SuperToast(context);
        superToast.setText(textCharSequence);
        superToast.setDuration(durationInteger);
        superToast.setStyle(style);
        return superToast;
    }

    public static void cancelAllSuperToasts() {
        ManagerSuperToast.getInstance().cancelAllSuperToasts();
    }
}
