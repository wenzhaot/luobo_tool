package com.github.johnpersano.supertoasts;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
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
import com.github.johnpersano.supertoasts.util.SwipeDismissListener;
import com.github.johnpersano.supertoasts.util.SwipeDismissListener.OnDismissCallback;
import com.github.johnpersano.supertoasts.util.Wrappers;
import java.util.LinkedList;

public class SuperCardToast {
    private static final String BUNDLE_TAG = "0x532e432e542e";
    private static final String ERROR_ACTIVITYNULL = " - You cannot pass a null Activity as a parameter.";
    private static final String ERROR_CONTAINERNULL = " - You must have a LinearLayout with the id of card_container in your layout!";
    private static final String ERROR_NOTBUTTONTYPE = " is only compatible with BUTTON type SuperCardToasts.";
    private static final String ERROR_NOTPROGRESSHORIZONTALTYPE = " is only compatible with PROGRESS_HORIZONTAL type SuperCardToasts.";
    private static final String ERROR_VIEWCONTAINERNULL = " - Either the View or Container was null when trying to dismiss.";
    private static final String MANAGER_TAG = "SuperCardToast Manager";
    private static final String TAG = "SuperCardToast";
    private static final String WARNING_PREHONEYCOMB = "Swipe to dismiss was enabled but the SDK version is pre-Honeycomb";
    private boolean isProgressIndeterminate;
    private Activity mActivity;
    private Animations mAnimations = Animations.FADE;
    private int mBackground = R.drawable.background_standard_gray;
    private Button mButton;
    private int mButtonIcon = Dark.UNDO;
    private OnClickListener mButtonListener = new OnClickListener() {
        public void onClick(View view) {
            if (SuperCardToast.this.mOnClickWrapper != null) {
                SuperCardToast.this.mOnClickWrapper.onClick(view, SuperCardToast.this.mToken);
            }
            SuperCardToast.this.dismiss();
            SuperCardToast.this.mButton.setClickable(false);
        }
    };
    private int mButtonTypefaceStyle = 1;
    private int mDividerColor = -12303292;
    private View mDividerView;
    private int mDuration = 2000;
    private Handler mHandler;
    private final Runnable mHideImmediateRunnable = new Runnable() {
        public void run() {
            SuperCardToast.this.dismissImmediately();
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        public void run() {
            SuperCardToast.this.dismiss();
        }
    };
    private final Runnable mHideWithAnimationRunnable = new Runnable() {
        public void run() {
            SuperCardToast.this.dismissWithLayoutAnimation();
        }
    };
    private int mIcon;
    private IconPosition mIconPosition;
    private final Runnable mInvalidateRunnable = new Runnable() {
        public void run() {
            if (SuperCardToast.this.mViewGroup != null) {
                SuperCardToast.this.mViewGroup.postInvalidate();
            }
        }
    };
    private boolean mIsIndeterminate;
    private boolean mIsSwipeDismissible;
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
                SuperCardToast.this.dismiss();
            }
            this.timesTouched++;
            return false;
        }
    };
    private Type mType = Type.STANDARD;
    private int mTypeface = 0;
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
        int mButtonDivider;
        int mButtonIcon;
        String mButtonText;
        int mButtonTextColor;
        float mButtonTextSize;
        int mButtonTypefaceStyle;
        String mClickListenerTag;
        String mDismissListenerTag;
        int mDuration;
        int mIcon;
        IconPosition mIconPosition;
        boolean mIsIndeterminate;
        boolean mIsSwipeDismissible;
        boolean mIsTouchDismissible;
        String mText;
        int mTextColor;
        float mTextSize;
        Parcelable mToken;
        Type mType;
        int mTypefaceStyle;

        public ReferenceHolder(SuperCardToast superCardToast) {
            this.mType = superCardToast.getType();
            if (this.mType == Type.BUTTON) {
                this.mButtonText = superCardToast.getButtonText().toString();
                this.mButtonTextSize = superCardToast.getButtonTextSize();
                this.mButtonTextColor = superCardToast.getButtonTextColor();
                this.mButtonIcon = superCardToast.getButtonIcon();
                this.mButtonDivider = superCardToast.getDividerColor();
                this.mClickListenerTag = superCardToast.getOnClickWrapperTag();
                this.mButtonTypefaceStyle = superCardToast.getButtonTypefaceStyle();
                this.mToken = superCardToast.getToken();
            }
            if (!(superCardToast.getIconResource() == 0 || superCardToast.getIconPosition() == null)) {
                this.mIcon = superCardToast.getIconResource();
                this.mIconPosition = superCardToast.getIconPosition();
            }
            this.mDismissListenerTag = superCardToast.getDismissListenerTag();
            this.mAnimations = superCardToast.getAnimations();
            this.mText = superCardToast.getText().toString();
            this.mTypefaceStyle = superCardToast.getTypefaceStyle();
            this.mDuration = superCardToast.getDuration();
            this.mTextColor = superCardToast.getTextColor();
            this.mTextSize = superCardToast.getTextSize();
            this.mIsIndeterminate = superCardToast.isIndeterminate();
            this.mBackground = superCardToast.getBackgroundResource();
            this.mIsTouchDismissible = superCardToast.isTouchDismissible();
            this.mIsSwipeDismissible = superCardToast.isSwipeDismissible();
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
                this.mButtonDivider = parcel.readInt();
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
            if (parcel.readByte() != (byte) 0) {
                z = true;
            } else {
                z = false;
            }
            this.mIsTouchDismissible = z;
            if (parcel.readByte() == (byte) 0) {
                z2 = false;
            }
            this.mIsSwipeDismissible = z2;
        }

        public void writeToParcel(Parcel parcel, int i) {
            int i2;
            int i3 = 1;
            parcel.writeInt(this.mType.ordinal());
            if (this.mType == Type.BUTTON) {
                parcel.writeString(this.mButtonText);
                parcel.writeFloat(this.mButtonTextSize);
                parcel.writeInt(this.mButtonTextColor);
                parcel.writeInt(this.mButtonIcon);
                parcel.writeInt(this.mButtonDivider);
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
            if (this.mIsTouchDismissible) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            parcel.writeByte((byte) i2);
            if (!this.mIsSwipeDismissible) {
                i3 = 0;
            }
            parcel.writeByte((byte) i3);
        }

        public int describeContents() {
            return 0;
        }
    }

    public SuperCardToast(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("SuperCardToast - You cannot pass a null Activity as a parameter.");
        }
        this.mActivity = activity;
        this.mType = Type.STANDARD;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.mViewGroup = (LinearLayout) activity.findViewById(R.id.card_container);
        if (this.mViewGroup == null) {
            throw new IllegalArgumentException("SuperCardToast - You must have a LinearLayout with the id of card_container in your layout!");
        }
        this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast, this.mViewGroup, false);
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
    }

    public SuperCardToast(Activity activity, Style style) {
        if (activity == null) {
            throw new IllegalArgumentException("SuperCardToast - You cannot pass a null Activity as a parameter.");
        }
        this.mActivity = activity;
        this.mType = Type.STANDARD;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.mViewGroup = (LinearLayout) activity.findViewById(R.id.card_container);
        if (this.mViewGroup == null) {
            throw new IllegalArgumentException("SuperCardToast - You must have a LinearLayout with the id of card_container in your layout!");
        }
        this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast, this.mViewGroup, false);
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
        setStyle(style);
    }

    public SuperCardToast(Activity activity, Type type) {
        if (activity == null) {
            throw new IllegalArgumentException("SuperCardToast - You cannot pass a null Activity as a parameter.");
        }
        this.mActivity = activity;
        this.mType = type;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.mViewGroup = (LinearLayout) activity.findViewById(R.id.card_container);
        if (this.mViewGroup == null) {
            throw new IllegalArgumentException("SuperCardToast - You must have a LinearLayout with the id of card_container in your layout!");
        }
        if (type == Type.BUTTON) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast_button, this.mViewGroup, false);
            this.mButton = (Button) this.mToastView.findViewById(R.id.button);
            this.mDividerView = this.mToastView.findViewById(R.id.divider);
            this.mButton.setOnClickListener(this.mButtonListener);
        } else if (type == Type.PROGRESS) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast_progresscircle, this.mViewGroup, false);
            this.mProgressBar = (ProgressBar) this.mToastView.findViewById(R.id.progress_bar);
        } else if (type == Type.PROGRESS_HORIZONTAL) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast_progresshorizontal, this.mViewGroup, false);
            this.mProgressBar = (ProgressBar) this.mToastView.findViewById(R.id.progress_bar);
        } else {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast, this.mViewGroup, false);
        }
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
    }

    public SuperCardToast(Activity activity, Type type, Style style) {
        if (activity == null) {
            throw new IllegalArgumentException("SuperCardToast - You cannot pass a null Activity as a parameter.");
        }
        this.mActivity = activity;
        this.mType = type;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.mViewGroup = (LinearLayout) activity.findViewById(R.id.card_container);
        if (this.mViewGroup == null) {
            throw new IllegalArgumentException("SuperCardToast - You must have a LinearLayout with the id of card_container in your layout!");
        }
        if (type == Type.BUTTON) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast_button, this.mViewGroup, false);
            this.mButton = (Button) this.mToastView.findViewById(R.id.button);
            this.mDividerView = this.mToastView.findViewById(R.id.divider);
            this.mButton.setOnClickListener(this.mButtonListener);
        } else if (type == Type.PROGRESS) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast_progresscircle, this.mViewGroup, false);
            this.mProgressBar = (ProgressBar) this.mToastView.findViewById(R.id.progress_bar);
        } else if (type == Type.PROGRESS_HORIZONTAL) {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast_progresshorizontal, this.mViewGroup, false);
            this.mProgressBar = (ProgressBar) this.mToastView.findViewById(R.id.progress_bar);
        } else {
            this.mToastView = this.mLayoutInflater.inflate(R.layout.supercardtoast, this.mViewGroup, false);
        }
        this.mMessageTextView = (TextView) this.mToastView.findViewById(R.id.message_textview);
        this.mRootLayout = (LinearLayout) this.mToastView.findViewById(R.id.root_layout);
        setStyle(style);
    }

    public void show() {
        ManagerSuperCardToast.getInstance().add(this);
        if (!this.mIsIndeterminate) {
            this.mHandler = new Handler();
            this.mHandler.postDelayed(this.mHideRunnable, (long) this.mDuration);
        }
        this.mViewGroup.addView(this.mToastView);
        if (!this.showImmediate) {
            Animation animation = getShowAnimation();
            animation.setAnimationListener(new AnimationListener() {
                public void onAnimationEnd(Animation arg0) {
                    new Handler().post(SuperCardToast.this.mInvalidateRunnable);
                }

                public void onAnimationRepeat(Animation arg0) {
                }

                public void onAnimationStart(Animation arg0) {
                }
            });
            this.mToastView.startAnimation(animation);
        }
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
        this.mTypeface = typeface;
        this.mMessageTextView.setTypeface(this.mMessageTextView.getTypeface(), typeface);
    }

    public int getTypefaceStyle() {
        return this.mTypeface;
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

    public void setIcon(int icon, IconPosition iconPosition) {
        this.mIcon = icon;
        this.mIconPosition = iconPosition;
        if (iconPosition == IconPosition.BOTTOM) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, this.mActivity.getResources().getDrawable(icon));
        } else if (iconPosition == IconPosition.LEFT) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(this.mActivity.getResources().getDrawable(icon), null, null, null);
        } else if (iconPosition == IconPosition.RIGHT) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, this.mActivity.getResources().getDrawable(icon), null);
        } else if (iconPosition == IconPosition.TOP) {
            this.mMessageTextView.setCompoundDrawablesWithIntrinsicBounds(null, this.mActivity.getResources().getDrawable(icon), null, null);
        }
    }

    public IconPosition getIconPosition() {
        return this.mIconPosition;
    }

    public int getIconResource() {
        return this.mIcon;
    }

    public void setBackground(int background) {
        this.mBackground = checkForKitKatBackgrounds(background);
        this.mRootLayout.setBackgroundResource(this.mBackground);
    }

    private int checkForKitKatBackgrounds(int background) {
        if (background == R.drawable.background_kitkat_black) {
            return R.drawable.background_standard_black;
        }
        if (background == R.drawable.background_kitkat_blue) {
            return R.drawable.background_standard_blue;
        }
        if (background == R.drawable.background_kitkat_gray) {
            return R.drawable.background_standard_gray;
        }
        if (background == R.drawable.background_kitkat_green) {
            return R.drawable.background_standard_green;
        }
        if (background == R.drawable.background_kitkat_orange) {
            return R.drawable.background_standard_orange;
        }
        if (background == R.drawable.background_kitkat_purple) {
            return R.drawable.background_standard_purple;
        }
        if (background == R.drawable.background_kitkat_red) {
            return R.drawable.background_standard_red;
        }
        if (background == R.drawable.background_kitkat_white) {
            return R.drawable.background_standard_white;
        }
        return background;
    }

    public int getBackgroundResource() {
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

    public void setSwipeToDismiss(boolean swipeDismiss) {
        this.mIsSwipeDismissible = swipeDismiss;
        if (!swipeDismiss) {
            this.mToastView.setOnTouchListener(null);
        } else if (VERSION.SDK_INT > 12) {
            this.mToastView.setOnTouchListener(new SwipeDismissListener(this.mToastView, new OnDismissCallback() {
                public void onDismiss(View view) {
                    SuperCardToast.this.dismissImmediately();
                }
            }));
        } else {
            Log.w(TAG, WARNING_PREHONEYCOMB);
        }
    }

    public boolean isSwipeDismissible() {
        return this.mIsSwipeDismissible;
    }

    public void setOnDismissWrapper(OnDismissWrapper onDismissWrapper) {
        this.mOnDismissWrapper = onDismissWrapper;
        this.mOnDismissWrapperTag = onDismissWrapper.getTag();
    }

    protected OnDismissWrapper getOnDismissWrapper() {
        return this.mOnDismissWrapper;
    }

    private String getDismissListenerTag() {
        return this.mOnDismissWrapperTag;
    }

    public void dismiss() {
        ManagerSuperCardToast.getInstance().remove(this);
        dismissWithAnimation();
    }

    public void dismissImmediately() {
        ManagerSuperCardToast.getInstance().remove(this);
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mHideRunnable);
            this.mHandler.removeCallbacks(this.mHideWithAnimationRunnable);
            this.mHandler = null;
        }
        if (this.mToastView == null || this.mViewGroup == null) {
            Log.e(TAG, ERROR_VIEWCONTAINERNULL);
            return;
        }
        this.mViewGroup.removeView(this.mToastView);
        if (this.mOnDismissWrapper != null) {
            this.mOnDismissWrapper.onDismiss(getView());
        }
        this.mToastView = null;
    }

    @SuppressLint({"NewApi"})
    private void dismissWithLayoutAnimation() {
        if (this.mToastView != null) {
            this.mToastView.setVisibility(4);
            final LayoutParams layoutParams = this.mToastView.getLayoutParams();
            int originalHeight = this.mToastView.getHeight();
            ValueAnimator animator = ValueAnimator.ofInt(new int[]{originalHeight, 1}).setDuration((long) this.mActivity.getResources().getInteger(17694720));
            animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    new Handler().post(SuperCardToast.this.mHideImmediateRunnable);
                }
            });
            animator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (SuperCardToast.this.mToastView != null) {
                        try {
                            layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                            SuperCardToast.this.mToastView.setLayoutParams(layoutParams);
                        } catch (NullPointerException e) {
                        }
                    }
                }
            });
            animator.start();
            return;
        }
        dismissImmediately();
    }

    @SuppressLint({"NewApi"})
    private void dismissWithAnimation() {
        Animation animation = getDismissAnimation();
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (VERSION.SDK_INT >= 11) {
                    new Handler().post(SuperCardToast.this.mHideWithAnimationRunnable);
                } else {
                    new Handler().post(SuperCardToast.this.mHideImmediateRunnable);
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        if (this.mToastView != null) {
            this.mToastView.startAnimation(animation);
        }
    }

    public void setOnClickWrapper(OnClickWrapper onClickWrapper) {
        if (this.mType != Type.BUTTON) {
            Log.w(TAG, "setOnClickListenerWrapper() is only compatible with BUTTON type SuperCardToasts.");
        }
        this.mOnClickWrapper = onClickWrapper;
        this.mOnClickWrapperTag = onClickWrapper.getTag();
    }

    public void setOnClickWrapper(OnClickWrapper onClickWrapper, Parcelable token) {
        if (this.mType != Type.BUTTON) {
            Log.e(TAG, "setOnClickListenerWrapper() is only compatible with BUTTON type SuperCardToasts.");
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
            Log.w(TAG, "setButtonIcon() is only compatible with BUTTON type SuperCardToasts.");
        }
        this.mButtonIcon = buttonIcon;
        if (this.mButton != null) {
            this.mButton.setCompoundDrawablesWithIntrinsicBounds(this.mActivity.getResources().getDrawable(buttonIcon), null, null, null);
        }
    }

    public void setButtonIcon(int buttonIcon, CharSequence buttonText) {
        if (this.mType != Type.BUTTON) {
            Log.w(TAG, "setButtonIcon() is only compatible with BUTTON type SuperCardToasts.");
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
            Log.w(TAG, "setDivider() is only compatible with BUTTON type SuperCardToasts.");
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
            Log.w(TAG, "setButtonText() is only compatible with BUTTON type SuperCardToasts.");
        }
        if (this.mButton != null) {
            this.mButton.setText(buttonText);
        }
    }

    public CharSequence getButtonText() {
        if (this.mButton != null) {
            return this.mButton.getText();
        }
        Log.e(TAG, "getButtonText() is only compatible with BUTTON type SuperCardToasts.");
        return "";
    }

    public void setButtonTypefaceStyle(int typefaceStyle) {
        if (this.mType != Type.BUTTON) {
            Log.w(TAG, "setButtonTypefaceStyle() is only compatible with BUTTON type SuperCardToasts.");
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
            Log.w(TAG, "setButtonTextColor() is only compatible with BUTTON type SuperCardToasts.");
        }
        if (this.mButton != null) {
            this.mButton.setTextColor(buttonTextColor);
        }
    }

    public int getButtonTextColor() {
        if (this.mButton != null) {
            return this.mButton.getCurrentTextColor();
        }
        Log.e(TAG, "getButtonTextColor() is only compatible with BUTTON type SuperCardToasts.");
        return 0;
    }

    public void setButtonTextSize(int buttonTextSize) {
        if (this.mType != Type.BUTTON) {
            Log.w(TAG, "setButtonTextSize() is only compatible with BUTTON type SuperCardToasts.");
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
        Log.e(TAG, "getButtonTextSize() is only compatible with BUTTON type SuperCardToasts.");
        return 0.0f;
    }

    public void setProgress(int progress) {
        if (this.mType != Type.PROGRESS_HORIZONTAL) {
            Log.w(TAG, "setProgress() is only compatible with PROGRESS_HORIZONTAL type SuperCardToasts.");
        }
        if (this.mProgressBar != null) {
            this.mProgressBar.setProgress(progress);
        }
    }

    public int getProgress() {
        if (this.mProgressBar != null) {
            return this.mProgressBar.getProgress();
        }
        Log.e(TAG, "ProgressBar is only compatible with PROGRESS_HORIZONTAL type SuperCardToasts.");
        return 0;
    }

    public void setMaxProgress(int maxProgress) {
        if (this.mType != Type.PROGRESS_HORIZONTAL) {
            Log.e(TAG, "setMaxProgress() is only compatible with PROGRESS_HORIZONTAL type SuperCardToasts.");
        }
        if (this.mProgressBar != null) {
            this.mProgressBar.setMax(maxProgress);
        }
    }

    public int getMaxProgress() {
        if (this.mProgressBar != null) {
            return this.mProgressBar.getMax();
        }
        Log.e(TAG, "getMaxProgress() is only compatible with PROGRESS_HORIZONTAL type SuperCardToasts.");
        return this.mProgressBar.getMax();
    }

    public void setProgressIndeterminate(boolean isIndeterminate) {
        if (this.mType != Type.PROGRESS_HORIZONTAL) {
            Log.e(TAG, "setProgressIndeterminate() is only compatible with PROGRESS_HORIZONTAL type SuperCardToasts.");
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

    private Animation getShowAnimation() {
        AlphaAnimation alphaAnimation;
        AnimationSet animationSet;
        if (getAnimations() == Animations.FLYIN) {
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.75f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else if (getAnimations() == Animations.SCALE) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, 1, 0.5f, 1, 0.5f);
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else if (getAnimations() == Animations.POPUP) {
            TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.1f, 1, 0.0f);
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation2);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else {
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(500);
            animation.setInterpolator(new DecelerateInterpolator());
            return animation;
        }
    }

    private Animation getDismissAnimation() {
        AlphaAnimation alphaAnimation;
        AnimationSet animationSet;
        if (getAnimations() == Animations.FLYIN) {
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.75f, 1, 0.0f, 1, 0.0f);
            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new AccelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else if (getAnimations() == Animations.SCALE) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, 1, 0.5f, 1, 0.5f);
            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else if (getAnimations() == Animations.POPUP) {
            TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 0.1f);
            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation2);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else {
            Animation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation2.setDuration(500);
            alphaAnimation2.setInterpolator(new AccelerateInterpolator());
            return alphaAnimation2;
        }
    }

    public static SuperCardToast create(Activity activity, CharSequence textCharSequence, int durationInteger) {
        SuperCardToast superCardToast = new SuperCardToast(activity);
        superCardToast.setText(textCharSequence);
        superCardToast.setDuration(durationInteger);
        return superCardToast;
    }

    public static SuperCardToast create(Activity activity, CharSequence textCharSequence, int durationInteger, Animations animations) {
        SuperCardToast superCardToast = new SuperCardToast(activity);
        superCardToast.setText(textCharSequence);
        superCardToast.setDuration(durationInteger);
        superCardToast.setAnimations(animations);
        return superCardToast;
    }

    public static SuperCardToast create(Activity activity, CharSequence textCharSequence, int durationInteger, Style style) {
        SuperCardToast superCardToast = new SuperCardToast(activity);
        superCardToast.setText(textCharSequence);
        superCardToast.setDuration(durationInteger);
        superCardToast.setStyle(style);
        return superCardToast;
    }

    public static void cancelAllSuperCardToasts() {
        ManagerSuperCardToast.getInstance().cancelAllSuperActivityToasts();
    }

    public static void onSaveState(Bundle bundle) {
        ReferenceHolder[] list = new ReferenceHolder[ManagerSuperCardToast.getInstance().getList().size()];
        LinkedList<SuperCardToast> lister = ManagerSuperCardToast.getInstance().getList();
        for (int i = 0; i < list.length; i++) {
            list[i] = new ReferenceHolder((SuperCardToast) lister.get(i));
        }
        bundle.putParcelableArray(BUNDLE_TAG, list);
        cancelAllSuperCardToasts();
    }

    public static void onRestoreState(Bundle bundle, Activity activity) {
        if (bundle != null) {
            Parcelable[] savedArray = bundle.getParcelableArray(BUNDLE_TAG);
            int i = 0;
            if (savedArray != null) {
                for (Parcelable parcelable : savedArray) {
                    i++;
                    SuperCardToast superCardToast = new SuperCardToast(activity, (ReferenceHolder) parcelable, null, i);
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
                    SuperCardToast superCardToast = new SuperCardToast(activity, (ReferenceHolder) parcelable, wrappers, i);
                }
            }
        }
    }

    private SuperCardToast(Activity activity, ReferenceHolder referenceHolder, Wrappers wrappers, int position) {
        SuperCardToast superCardToast;
        if (referenceHolder.mType == Type.BUTTON) {
            superCardToast = new SuperCardToast(activity, Type.BUTTON);
            superCardToast.setButtonText(referenceHolder.mButtonText);
            superCardToast.setButtonTextSizeFloat(referenceHolder.mButtonTextSize);
            superCardToast.setButtonTextColor(referenceHolder.mButtonTextColor);
            superCardToast.setButtonIcon(referenceHolder.mButtonIcon);
            superCardToast.setDividerColor(referenceHolder.mButtonDivider);
            superCardToast.setButtonTypefaceStyle(referenceHolder.mButtonTypefaceStyle);
            if (wrappers != null) {
                for (OnClickWrapper onClickWrapper : wrappers.getOnClickWrappers()) {
                    if (onClickWrapper.getTag().equalsIgnoreCase(referenceHolder.mClickListenerTag)) {
                        superCardToast.setOnClickWrapper(onClickWrapper, referenceHolder.mToken);
                    }
                }
            }
        } else if (referenceHolder.mType != Type.PROGRESS && referenceHolder.mType != Type.PROGRESS_HORIZONTAL) {
            superCardToast = new SuperCardToast(activity);
        } else {
            return;
        }
        if (wrappers != null) {
            for (OnDismissWrapper onDismissListenerWrapper : wrappers.getOnDismissWrappers()) {
                if (onDismissListenerWrapper.getTag().equalsIgnoreCase(referenceHolder.mDismissListenerTag)) {
                    superCardToast.setOnDismissWrapper(onDismissListenerWrapper);
                }
            }
        }
        superCardToast.setAnimations(referenceHolder.mAnimations);
        superCardToast.setText(referenceHolder.mText);
        superCardToast.setTypefaceStyle(referenceHolder.mTypefaceStyle);
        superCardToast.setDuration(referenceHolder.mDuration);
        superCardToast.setTextColor(referenceHolder.mTextColor);
        superCardToast.setTextSizeFloat(referenceHolder.mTextSize);
        superCardToast.setIndeterminate(referenceHolder.mIsIndeterminate);
        superCardToast.setIcon(referenceHolder.mIcon, referenceHolder.mIconPosition);
        superCardToast.setBackground(referenceHolder.mBackground);
        if (referenceHolder.mIsTouchDismissible) {
            superCardToast.setTouchToDismiss(true);
        } else if (referenceHolder.mIsSwipeDismissible) {
            superCardToast.setSwipeToDismiss(true);
        }
        superCardToast.setShowImmediate(true);
        superCardToast.show();
    }
}
