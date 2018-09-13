package com.github.johnpersano.supertoasts;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.johnpersano.supertoasts.SuperToast.Animations;
import com.github.johnpersano.supertoasts.SuperToast.Icon.Dark;
import com.github.johnpersano.supertoasts.SuperToast.IconPosition;
import com.github.johnpersano.supertoasts.SuperToast.Type;
import com.github.johnpersano.supertoasts.util.OnClickWrapper;
import com.github.johnpersano.supertoasts.util.OnDismissWrapper;
import com.github.johnpersano.supertoasts.util.Style;
import com.github.johnpersano.supertoasts.util.Wrappers;
import java.util.LinkedList;

public class SuperActivityToast {
    private static final String BUNDLE_TAG = "0x532e412e542e";
    private static final String ERROR_ACTIVITYNULL = " - You cannot pass a null Activity as a parameter.";
    private static final String ERROR_NOTBUTTONTYPE = " - is only compatible with BUTTON type SuperActivityToasts.";
    private static final String ERROR_NOTEITHERPROGRESSTYPE = " - is only compatible with PROGRESS_HORIZONTAL or PROGRESS type SuperActivityToasts.";
    private static final String ERROR_NOTPROGRESSHORIZONTALTYPE = " - is only compatible with PROGRESS_HORIZONTAL type SuperActivityToasts.";
    private static final String MANAGER_TAG = "SuperActivityToast Manager";
    private static final String TAG = "SuperActivityToast";
    private boolean isProgressIndeterminate;
    private Activity mActivity;
    private Animations mAnimations = Animations.FADE;
    private int mBackground = Style.getBackground(2);
    private Button mButton;
    private int mButtonIcon = Dark.UNDO;
    private OnClickListener mButtonListener = new OnClickListener() {
        public void onClick(View view) {
            if (SuperActivityToast.this.mOnClickWrapper != null) {
                SuperActivityToast.this.mOnClickWrapper.onClick(view, SuperActivityToast.this.mToken);
            }
            SuperActivityToast.this.dismiss();
            SuperActivityToast.this.mButton.setClickable(false);
        }
    };
    private int mButtonTypefaceStyle = 1;
    private int mDividerColor = -3355444;
    private View mDividerView;
    private int mDuration = 2000;
    private int mIcon;
    private IconPosition mIconPosition;
    private boolean mIsIndeterminate;
    private boolean mIsTouchDismissible;
    private LayoutInflater mLayoutInflater;
    private TextView mMessageTextView;
    private OnClickWrapper mOnClickWrapper;
    private String mOnClickWrapperTag;
    private OnDismissWrapper mOnDismissWrapper;
    private String mOnDismissWrapperTag;
    private ProgressBar mProgressBar;
    private LinearLayout mRootLayout;
    private View mToastView;
    private Parcelable mToken;
    private OnTouchListener mTouchDismissListener = new OnTouchListener() {
        int timesTouched;

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (this.timesTouched == 0 && motionEvent.getAction() == 0) {
                SuperActivityToast.this.dismiss();
            }
            this.timesTouched++;
            return false;
        }
    };
    private Type mType = Type.STANDARD;
    private int mTypefaceStyle = 0;
    private ViewGroup mViewGroup;
    private boolean showImmediate;

    private static class ReferenceHolder implements Parcelable {
        public final Creator CREATOR = new Creator() {
            public ReferenceHolder createFromParcel(Parcel parcel) {
                return new ReferenceHolder(parcel);
            }

            public ReferenceHolder[] newArray(int size) {
                return new ReferenceHolder[size];
            }
        };
        Animations mAnimations;
        int mBackground;
        int mButtonIcon;
        String mButtonText;
        int mButtonTextColor;
        float mButtonTextSize;
        int mButtonTypefaceStyle;
        String mClickListenerTag;
        String mDismissListenerTag;
        int mDivider;
        int mDuration;
        int mIcon;
        IconPosition mIconPosition;
        boolean mIsIndeterminate;
        boolean mIsTouchDismissible;
        String mText;
        int mTextColor;
        float mTextSize;
        Parcelable mToken;
        Type mType;
        int mTypefaceStyle;

        public ReferenceHolder(SuperActivityToast superActivityToast) {
            this.mType = superActivityToast.getType();
            if (this.mType == Type.BUTTON) {
                this.mButtonText = superActivityToast.getButtonText().toString();
                this.mButtonTextSize = superActivityToast.getButtonTextSize();
                this.mButtonTextColor = superActivityToast.getButtonTextColor();
                this.mButtonIcon = superActivityToast.getButtonIcon();
                this.mDivider = superActivityToast.getDividerColor();
                this.mClickListenerTag = superActivityToast.getOnClickWrapperTag();
                this.mButtonTypefaceStyle = superActivityToast.getButtonTypefaceStyle();
                this.mToken = superActivityToast.getToken();
            }
            if (!(superActivityToast.getIconResource() == 0 || superActivityToast.getIconPosition() == null)) {
                this.mIcon = superActivityToast.getIconResource();
                this.mIconPosition = superActivityToast.getIconPosition();
            }
            this.mDismissListenerTag = superActivityToast.getOnDismissWrapperTag();
            this.mAnimations = superActivityToast.getAnimations();
            this.mText = superActivityToast.getText().toString();
            this.mTypefaceStyle = superActivityToast.getTypefaceStyle();
            this.mDuration = superActivityToast.getDuration();
            this.mTextColor = superActivityToast.getTextColor();
            this.mTextSize = superActivityToast.getTextSize();
            this.mIsIndeterminate = superActivityToast.isIndeterminate();
            this.mBackground = superActivityToast.getBackground();
            this.mIsTouchDismissible = superActivityToast.isTouchDismissible();
        }

        public ReferenceHolder(Parcel parcel) {
            boolean hasIcon;
            boolean z;
            boolean z2 = true;
            this.mType = Type.values()[parcel.readInt()];
            if (this.mType == Type.BUTTON) {
                this.mButtonText = parcel.readString();
                this.mButtonTextSize = parcel.readFloat();
                this.mButtonTextColor = parcel.readInt();
                this.mButtonIcon = parcel.readInt();
                this.mDivider = parcel.readInt();
                this.mButtonTypefaceStyle = parcel.readInt();
                this.mClickListenerTag = parcel.readString();
                this.mToken = parcel.readParcelable(getClass().getClassLoader());
            }
            if (parcel.readByte() != (byte) 0) {
                hasIcon = true;
            } else {
                hasIcon = false;
            }
            if (hasIcon) {
                this.mIcon = parcel.readInt();
                this.mIconPosition = IconPosition.values()[parcel.readInt()];
            }
            this.mDismissListenerTag = parcel.readString();
            this.mAnimations = Animations.values()[parcel.readInt()];
            this.mText = parcel.readString();
            this.mTypefaceStyle = parcel.readInt();
            this.mDuration = parcel.readInt();
            this.mTextColor = parcel.readInt();
            this.mTextSize = parcel.readFloat();
            if (parcel.readByte() != (byte) 0) {
                z = true;
            } else {
                z = false;
            }
            this.mIsIndeterminate = z;
            this.mBackground = parcel.readInt();
            if (parcel.readByte() == (byte) 0) {
                z2 = false;
            }
            this.mIsTouchDismissible = z2;
        }

        public void writeToParcel(Parcel parcel, int i) {
            int i2 = 1;
            parcel.writeInt(this.mType.ordinal());
            if (this.mType == Type.BUTTON) {
                parcel.writeString(this.mButtonText);
                parcel.writeFloat(this.mButtonTextSize);
                parcel.writeInt(this.mButtonTextColor);
                parcel.writeInt(this.mButtonIcon);
                parcel.writeInt(this.mDivider);
                parcel.writeInt(this.mButtonTypefaceStyle);
                parcel.writeString(this.mClickListenerTag);
                parcel.writeParcelable(this.mToken, 0);
            }
            if (this.mIcon == 0 || this.mIconPosition == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(this.mIcon);
                parcel.writeInt(this.mIconPosition.ordinal());
            }
            parcel.writeString(this.mDismissListenerTag);
            parcel.writeInt(this.mAnimations.ordinal());
            parcel.writeString(this.mText);
            parcel.writeInt(this.mTypefaceStyle);
            parcel.writeInt(this.mDuration);
            parcel.writeInt(this.mTextColor);
            parcel.writeFloat(this.mTextSize);
            parcel.writeByte((byte) (this.mIsIndeterminate ? 1 : 0));
            parcel.writeInt(this.mBackground);
            if (!this.mIsTouchDismissible) {
                i2 = 0;
            }
            parcel.writeByte((byte) i2);
        }

        public int describeContents() {
            return 0;
        }
    }

    public SuperActivityToast(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("SuperActivityToast - You cannot pass a null Activity as a parameter.");
        }
        this.mActivity = activity;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.mViewGroup = (ViewGroup) activity.findViewById(16908290);
        this.mToastView = this.mLayoutInflater.inflate(R.layout.supertoast, this.mViewGroup, false);
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
    }

    public SuperActivityToast(Activity activity, Style style) {
        if (activity == null) {
            throw new IllegalArgumentException("SuperActivityToast - You cannot pass a null Activity as a parameter.");
        }
        this.mActivity = activity;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.mViewGroup = (ViewGroup) activity.findViewById(16908290);
        this.mToastView = this.mLayoutInflater.inflate(R.layout.supertoast, this.mViewGroup, false);
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
        setStyle(style);
    }

    public SuperActivityToast(Activity activity, Type type) {
        if (activity == null) {
            throw new IllegalArgumentException("SuperActivityToast - You cannot pass a null Activity as a parameter.");
        }
        this.mActivity = activity;
        this.mType = type;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.mViewGroup = (ViewGroup) activity.findViewById(16908290);
        if (type == Type.STANDARD) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supertoast, this.mViewGroup, false);
        } else if (type == Type.BUTTON) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.superactivitytoast_button, this.mViewGroup, false);
            this.mButton = (Button) this.mToastView.findViewById(R.id.button);
            this.mDividerView = this.mToastView.findViewById(R.id.divider);
            this.mButton.setOnClickListener(this.mButtonListener);
        } else if (type == Type.PROGRESS) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.superactivitytoast_progresscircle, this.mViewGroup, false);
            this.mProgressBar = (ProgressBar) this.mToastView.findViewById(R.id.progress_bar);
        } else if (type == Type.PROGRESS_HORIZONTAL) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.superactivitytoast_progresshorizontal, this.mViewGroup, false);
            this.mProgressBar = (ProgressBar) this.mToastView.findViewById(R.id.progress_bar);
        }
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
    }

    public SuperActivityToast(Activity activity, Type type, Style style) {
        if (activity == null) {
            throw new IllegalArgumentException("SuperActivityToast - You cannot pass a null Activity as a parameter.");
        }
        this.mActivity = activity;
        this.mType = type;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.mViewGroup = (ViewGroup) activity.findViewById(16908290);
        if (type == Type.STANDARD) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supertoast, this.mViewGroup, false);
        } else if (type == Type.BUTTON) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.superactivitytoast_button, this.mViewGroup, false);
            this.mButton = (Button) this.mToastView.findViewById(R.id.button);
            this.mDividerView = this.mToastView.findViewById(R.id.divider);
            this.mButton.setOnClickListener(this.mButtonListener);
        } else if (type == Type.PROGRESS) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.superactivitytoast_progresscircle, this.mViewGroup, false);
            this.mProgressBar = (ProgressBar) this.mToastView.findViewById(R.id.progress_bar);
        } else if (type == Type.PROGRESS_HORIZONTAL) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.superactivitytoast_progresshorizontal, this.mViewGroup, false);
            this.mProgressBar = (ProgressBar) this.mToastView.findViewById(R.id.progress_bar);
        }
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
        setStyle(style);
    }

    public void show() {
        ManagerSuperActivityToast.getInstance().add(this);
    }

    public Type getType() {
        return this.mType;
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

    private void setTextSizeFloat(float textSize) {
        this.mMessageTextView.setTextSize(0, textSize);
    }

    public float getTextSize() {
        return this.mMessageTextView.getTextSize();
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setIndeterminate(boolean isIndeterminate) {
        this.mIsIndeterminate = isIndeterminate;
    }

    public boolean isIndeterminate() {
        return this.mIsIndeterminate;
    }

    public void setIcon(int iconResource, IconPosition iconPosition) {
        this.mIcon = iconResource;
        this.mIconPosition = iconPosition;
        if (iconPosition == IconPosition.BOTTOM) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, this.mActivity.getResources().getDrawable(iconResource));
        } else if (iconPosition == IconPosition.LEFT) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(this.mActivity.getResources().getDrawable(iconResource), null, null, null);
        } else if (iconPosition == IconPosition.RIGHT) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, this.mActivity.getResources().getDrawable(iconResource), null);
        } else if (iconPosition == IconPosition.TOP) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(null, this.mActivity.getResources().getDrawable(iconResource), null, null);
        }
    }

    public IconPosition getIconPosition() {
        return this.mIconPosition;
    }

    public int getIconResource() {
        return this.mIcon;
    }

    public void setBackground(int background) {
        this.mBackground = background;
        this.mRootLayout.setBackgroundResource(background);
    }

    public int getBackground() {
        return this.mBackground;
    }

    public void setAnimations(Animations animations) {
        this.mAnimations = animations;
    }

    public Animations getAnimations() {
        return this.mAnimations;
    }

    public void setShowImmediate(boolean showImmediate) {
        this.showImmediate = showImmediate;
    }

    public boolean getShowImmediate() {
        return this.showImmediate;
    }

    public void setTouchToDismiss(boolean touchDismiss) {
        this.mIsTouchDismissible = touchDismiss;
        if (touchDismiss) {
            this.mToastView.setOnTouchListener(this.mTouchDismissListener);
        } else {
            this.mToastView.setOnTouchListener(null);
        }
    }

    public boolean isTouchDismissible() {
        return this.mIsTouchDismissible;
    }

    public void setOnDismissWrapper(OnDismissWrapper onDismissWrapper) {
        this.mOnDismissWrapper = onDismissWrapper;
        this.mOnDismissWrapperTag = onDismissWrapper.getTag();
    }

    protected OnDismissWrapper getOnDismissWrapper() {
        return this.mOnDismissWrapper;
    }

    private String getOnDismissWrapperTag() {
        return this.mOnDismissWrapperTag;
    }

    public void dismiss() {
        ManagerSuperActivityToast.getInstance().removeSuperToast(this);
    }

    public void setOnClickWrapper(OnClickWrapper onClickWrapper) {
        if (this.mType != Type.BUTTON) {
            Log.e(TAG, "setOnClickListenerWrapper() - is only compatible with BUTTON type SuperActivityToasts.");
        }
        this.mOnClickWrapper = onClickWrapper;
        this.mOnClickWrapperTag = onClickWrapper.getTag();
    }

    public void setOnClickWrapper(OnClickWrapper onClickWrapper, Parcelable token) {
        if (this.mType != Type.BUTTON) {
            Log.e(TAG, "setOnClickListenerWrapper() - is only compatible with BUTTON type SuperActivityToasts.");
        }
        onClickWrapper.setToken(token);
        this.mToken = token;
        this.mOnClickWrapper = onClickWrapper;
        this.mOnClickWrapperTag = onClickWrapper.getTag();
    }

    private Parcelable getToken() {
        return this.mToken;
    }

    private String getOnClickWrapperTag() {
        return this.mOnClickWrapperTag;
    }

    public void setButtonIcon(int buttonIcon) {
        if (this.mType != Type.BUTTON) {
            Log.e(TAG, "setButtonIcon() - is only compatible with BUTTON type SuperActivityToasts.");
        }
        this.mButtonIcon = buttonIcon;
        if (this.mButton != null) {
            this.mButton.setCompoundDrawablesWithIntrinsicBounds(this.mActivity.getResources().getDrawable(buttonIcon), null, null, null);
        }
    }

    public void setButtonIcon(int buttonIcon, CharSequence buttonText) {
        if (this.mType != Type.BUTTON) {
            Log.w(TAG, "setButtonIcon() - is only compatible with BUTTON type SuperActivityToasts.");
        }
        this.mButtonIcon = buttonIcon;
        if (this.mButton != null) {
            this.mButton.setCompoundDrawablesWithIntrinsicBounds(this.mActivity.getResources().getDrawable(buttonIcon), null, null, null);
            this.mButton.setText(buttonText);
        }
    }

    public int getButtonIcon() {
        return this.mButtonIcon;
    }

    public void setDividerColor(int dividerColor) {
        if (this.mType != Type.BUTTON) {
            Log.e(TAG, "setDivider() - is only compatible with BUTTON type SuperActivityToasts.");
        }
        this.mDividerColor = dividerColor;
        if (this.mDividerView != null) {
            this.mDividerView.setBackgroundColor(dividerColor);
        }
    }

    public int getDividerColor() {
        return this.mDividerColor;
    }

    public void setButtonText(CharSequence buttonText) {
        if (this.mType != Type.BUTTON) {
            Log.e(TAG, "setButtonText() - is only compatible with BUTTON type SuperActivityToasts.");
        }
        if (this.mButton != null) {
            this.mButton.setText(buttonText);
        }
    }

    public CharSequence getButtonText() {
        if (this.mButton != null) {
            return this.mButton.getText();
        }
        Log.e(TAG, "getButtonText() - is only compatible with BUTTON type SuperActivityToasts.");
        return "";
    }

    public void setButtonTypefaceStyle(int typefaceStyle) {
        if (this.mType != Type.BUTTON) {
            Log.e(TAG, "setButtonTypefaceStyle() - is only compatible with BUTTON type SuperActivityToasts.");
        }
        if (this.mButton != null) {
            this.mButtonTypefaceStyle = typefaceStyle;
            this.mButton.setTypeface(this.mButton.getTypeface(), typefaceStyle);
        }
    }

    public int getButtonTypefaceStyle() {
        return this.mButtonTypefaceStyle;
    }

    public void setButtonTextColor(int buttonTextColor) {
        if (this.mType != Type.BUTTON) {
            Log.e(TAG, "setButtonTextColor() - is only compatible with BUTTON type SuperActivityToasts.");
        }
        if (this.mButton != null) {
            this.mButton.setTextColor(buttonTextColor);
        }
    }

    public int getButtonTextColor() {
        if (this.mButton != null) {
            return this.mButton.getCurrentTextColor();
        }
        Log.e(TAG, "getButtonTextColor() - is only compatible with BUTTON type SuperActivityToasts.");
        return 0;
    }

    public void setButtonTextSize(int buttonTextSize) {
        if (this.mType != Type.BUTTON) {
            Log.e(TAG, "setButtonTextSize() - is only compatible with BUTTON type SuperActivityToasts.");
        }
        if (this.mButton != null) {
            this.mButton.setTextSize((float) buttonTextSize);
        }
    }

    private void setButtonTextSizeFloat(float buttonTextSize) {
        this.mButton.setTextSize(0, buttonTextSize);
    }

    public float getButtonTextSize() {
        if (this.mButton != null) {
            return this.mButton.getTextSize();
        }
        Log.e(TAG, "getButtonTextSize() - is only compatible with BUTTON type SuperActivityToasts.");
        return 0.0f;
    }

    public void setProgress(int progress) {
        if (this.mType != Type.PROGRESS_HORIZONTAL) {
            Log.e(TAG, "setProgress() - is only compatible with PROGRESS_HORIZONTAL type SuperActivityToasts.");
        }
        if (this.mProgressBar != null) {
            this.mProgressBar.setProgress(progress);
        }
    }

    public int getProgress() {
        if (this.mProgressBar != null) {
            return this.mProgressBar.getProgress();
        }
        Log.e(TAG, "getProgress() - is only compatible with PROGRESS_HORIZONTAL type SuperActivityToasts.");
        return 0;
    }

    public void setMaxProgress(int maxProgress) {
        if (this.mType != Type.PROGRESS_HORIZONTAL) {
            Log.e(TAG, "setMaxProgress() - is only compatible with PROGRESS_HORIZONTAL type SuperActivityToasts.");
        }
        if (this.mProgressBar != null) {
            this.mProgressBar.setMax(maxProgress);
        }
    }

    public int getMaxProgress() {
        if (this.mProgressBar != null) {
            return this.mProgressBar.getMax();
        }
        Log.e(TAG, "getMaxProgress() - is only compatible with PROGRESS_HORIZONTAL type SuperActivityToasts.");
        return 0;
    }

    public void setProgressIndeterminate(boolean isIndeterminate) {
        if (!(this.mType == Type.PROGRESS_HORIZONTAL || this.mType == Type.PROGRESS)) {
            Log.e(TAG, "setProgressIndeterminate() - is only compatible with PROGRESS_HORIZONTAL or PROGRESS type SuperActivityToasts.");
        }
        this.isProgressIndeterminate = isIndeterminate;
        if (this.mProgressBar != null) {
            this.mProgressBar.setIndeterminate(isIndeterminate);
        }
    }

    public boolean getProgressIndeterminate() {
        return this.isProgressIndeterminate;
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

    public Activity getActivity() {
        return this.mActivity;
    }

    public ViewGroup getViewGroup() {
        return this.mViewGroup;
    }

    private LinearLayout getRootLayout() {
        return this.mRootLayout;
    }

    private void setStyle(Style style) {
        setAnimations(style.animations);
        setTypefaceStyle(style.typefaceStyle);
        setTextColor(style.textColor);
        setBackground(style.background);
        if (this.mType == Type.BUTTON) {
            setDividerColor(style.dividerColor);
            setButtonTextColor(style.buttonTextColor);
        }
    }

    public static SuperActivityToast create(Activity activity, CharSequence textCharSequence, int durationInteger) {
        SuperActivityToast superActivityToast = new SuperActivityToast(activity);
        superActivityToast.setText(textCharSequence);
        superActivityToast.setDuration(durationInteger);
        return superActivityToast;
    }

    public static SuperActivityToast create(Activity activity, CharSequence textCharSequence, int durationInteger, Animations animations) {
        SuperActivityToast superActivityToast = new SuperActivityToast(activity);
        superActivityToast.setText(textCharSequence);
        superActivityToast.setDuration(durationInteger);
        superActivityToast.setAnimations(animations);
        return superActivityToast;
    }

    public static SuperActivityToast create(Activity activity, CharSequence textCharSequence, int durationInteger, Style style) {
        SuperActivityToast superActivityToast = new SuperActivityToast(activity);
        superActivityToast.setText(textCharSequence);
        superActivityToast.setDuration(durationInteger);
        superActivityToast.setStyle(style);
        return superActivityToast;
    }

    public static void cancelAllSuperActivityToasts() {
        ManagerSuperActivityToast.getInstance().cancelAllSuperActivityToasts();
    }

    public static void clearSuperActivityToastsForActivity(Activity activity) {
        ManagerSuperActivityToast.getInstance().cancelAllSuperActivityToastsForActivity(activity);
    }

    public static void onSaveState(Bundle bundle) {
        ReferenceHolder[] list = new ReferenceHolder[ManagerSuperActivityToast.getInstance().getList().size()];
        LinkedList<SuperActivityToast> lister = ManagerSuperActivityToast.getInstance().getList();
        for (int i = 0; i < list.length; i++) {
            list[i] = new ReferenceHolder((SuperActivityToast) lister.get(i));
        }
        bundle.putParcelableArray(BUNDLE_TAG, list);
        cancelAllSuperActivityToasts();
    }

    public static void onRestoreState(Bundle bundle, Activity activity) {
        if (bundle != null) {
            Parcelable[] savedArray = bundle.getParcelableArray(BUNDLE_TAG);
            int i = 0;
            if (savedArray != null) {
                for (Parcelable parcelable : savedArray) {
                    i++;
                    SuperActivityToast superActivityToast = new SuperActivityToast(activity, (ReferenceHolder) parcelable, null, i);
                }
            }
        }
    }

    public static void onRestoreState(Bundle bundle, Activity activity, Wrappers wrappers) {
        if (bundle != null) {
            Parcelable[] savedArray = bundle.getParcelableArray(BUNDLE_TAG);
            int i = 0;
            if (savedArray != null) {
                for (Parcelable parcelable : savedArray) {
                    i++;
                    SuperActivityToast superActivityToast = new SuperActivityToast(activity, (ReferenceHolder) parcelable, wrappers, i);
                }
            }
        }
    }

    private SuperActivityToast(Activity activity, ReferenceHolder referenceHolder, Wrappers wrappers, int position) {
        SuperActivityToast superActivityToast;
        if (referenceHolder.mType == Type.BUTTON) {
            superActivityToast = new SuperActivityToast(activity, Type.BUTTON);
            superActivityToast.setButtonText(referenceHolder.mButtonText);
            superActivityToast.setButtonTextSizeFloat(referenceHolder.mButtonTextSize);
            superActivityToast.setButtonTextColor(referenceHolder.mButtonTextColor);
            superActivityToast.setButtonIcon(referenceHolder.mButtonIcon);
            superActivityToast.setDividerColor(referenceHolder.mDivider);
            superActivityToast.setButtonTypefaceStyle(referenceHolder.mButtonTypefaceStyle);
            if ((activity.getResources().getConfiguration().screenLayout & 15) >= 3) {
                LayoutParams layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = (int) activity.getResources().getDimension(R.dimen.buttontoast_hover);
                layoutParams.rightMargin = (int) activity.getResources().getDimension(R.dimen.buttontoast_x_padding);
                layoutParams.leftMargin = (int) activity.getResources().getDimension(R.dimen.buttontoast_x_padding);
                superActivityToast.getRootLayout().setLayoutParams(layoutParams);
            }
            if (wrappers != null) {
                for (OnClickWrapper onClickWrapper : wrappers.getOnClickWrappers()) {
                    if (onClickWrapper.getTag().equalsIgnoreCase(referenceHolder.mClickListenerTag)) {
                        superActivityToast.setOnClickWrapper(onClickWrapper, referenceHolder.mToken);
                    }
                }
            }
        } else if (referenceHolder.mType != Type.PROGRESS && referenceHolder.mType != Type.PROGRESS_HORIZONTAL) {
            superActivityToast = new SuperActivityToast(activity);
        } else {
            return;
        }
        if (wrappers != null) {
            for (OnDismissWrapper onDismissWrapper : wrappers.getOnDismissWrappers()) {
                if (onDismissWrapper.getTag().equalsIgnoreCase(referenceHolder.mDismissListenerTag)) {
                    superActivityToast.setOnDismissWrapper(onDismissWrapper);
                }
            }
        }
        superActivityToast.setAnimations(referenceHolder.mAnimations);
        superActivityToast.setText(referenceHolder.mText);
        superActivityToast.setTypefaceStyle(referenceHolder.mTypefaceStyle);
        superActivityToast.setDuration(referenceHolder.mDuration);
        superActivityToast.setTextColor(referenceHolder.mTextColor);
        superActivityToast.setTextSizeFloat(referenceHolder.mTextSize);
        superActivityToast.setIndeterminate(referenceHolder.mIsIndeterminate);
        superActivityToast.setIcon(referenceHolder.mIcon, referenceHolder.mIconPosition);
        superActivityToast.setBackground(referenceHolder.mBackground);
        superActivityToast.setTouchToDismiss(referenceHolder.mIsTouchDismissible);
        if (position == 1) {
            superActivityToast.setShowImmediate(true);
        }
        superActivityToast.show();
    }
}
