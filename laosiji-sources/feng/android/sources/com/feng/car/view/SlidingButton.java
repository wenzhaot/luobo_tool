package com.feng.car.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SlidingButton extends CheckBox {
    private Bitmap btnBottom;
    private Bitmap btnFrame;
    private Bitmap btnMask;
    private Context context;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    SlidingButton.this.mRealPos = SlidingButton.this.getRealPos(((Float) msg.obj).floatValue());
                    SlidingButton.this.mLastBtnPos = ((Float) msg.obj).floatValue();
                    SlidingButton.this.invalidate();
                    return;
                default:
                    return;
            }
        }
    };
    private boolean isChecked;
    private int mAlpha;
    private float mAnimatedVelocity;
    private boolean mAnimating;
    private float mAnimationPosition;
    private Bitmap mBtnNormal;
    private float mBtnOffPos;
    private float mBtnOnPos;
    private float mBtnPos;
    private Bitmap mBtnPressed;
    private float mBtnWidth;
    private Bitmap mCurBtnPic;
    private float mExtendOffsetY;
    private float mFirstDownX;
    private float mLastBtnPos;
    private float mMaskHeight;
    private float mMaskWidth;
    private boolean mMoveEvent;
    private Paint mPaint;
    private float mRealPos;
    private RectF mSaveLayerRectF;
    private PorterDuffXfermode mXfermode;
    private OnCheckedChangeListener onCheckedChangeListener;

    public SlidingButton(Context context) {
        super(context);
        init(context);
    }

    public SlidingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.mAlpha = 255;
        this.isChecked = false;
        this.mPaint = new Paint();
        this.mPaint.setColor(-1);
        this.mAnimatedVelocity = (float) ((int) (0.5f + (350.0f * getResources().getDisplayMetrics().density)));
    }

    protected void onDraw(Canvas canvas) {
        canvas.saveLayerAlpha(this.mSaveLayerRectF, this.mAlpha, 31);
        canvas.drawBitmap(this.btnMask, 0.0f, this.mExtendOffsetY, this.mPaint);
        this.mPaint.setXfermode(this.mXfermode);
        canvas.drawBitmap(this.btnBottom, this.mRealPos, this.mExtendOffsetY, this.mPaint);
        this.mPaint.setXfermode(null);
        canvas.drawBitmap(this.btnFrame, 0.0f, this.mExtendOffsetY, this.mPaint);
        canvas.drawBitmap(this.mCurBtnPic, this.mRealPos, 0.4f + this.mExtendOffsetY, this.mPaint);
        canvas.restore();
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        setMeasuredDimension((int) this.mMaskWidth, (int) (this.mMaskHeight + (2.0f * this.mExtendOffsetY)));
    }

    public void setImageResource(int btnBottomResource, int btnFrameResource, int btnMaskResource, int btnNormalResource, int btnPressedResource) {
        this.btnBottom = BitmapFactory.decodeResource(this.context.getResources(), btnBottomResource);
        this.btnFrame = BitmapFactory.decodeResource(this.context.getResources(), btnFrameResource);
        this.btnMask = BitmapFactory.decodeResource(this.context.getResources(), btnMaskResource);
        this.mBtnNormal = BitmapFactory.decodeResource(this.context.getResources(), btnNormalResource);
        this.mBtnPressed = BitmapFactory.decodeResource(this.context.getResources(), btnPressedResource);
        this.mMaskWidth = (float) this.btnMask.getWidth();
        this.mMaskHeight = (float) this.btnMask.getHeight();
        this.mExtendOffsetY = (float) ((int) (0.5f + (0.0f * getResources().getDisplayMetrics().density)));
        this.mSaveLayerRectF = new RectF(-20.0f, this.mExtendOffsetY, 20.0f + this.mMaskWidth, this.mMaskHeight + this.mExtendOffsetY);
        this.mXfermode = new PorterDuffXfermode(Mode.SRC_IN);
        this.mCurBtnPic = this.mBtnNormal;
        this.mBtnWidth = (float) this.mBtnPressed.getWidth();
        this.mBtnOnPos = this.mBtnWidth / 2.0f;
        this.mBtnOffPos = this.mMaskWidth - (this.mBtnWidth / 2.0f);
        if (this.isChecked) {
            this.mBtnPos = this.mBtnOnPos;
        } else {
            this.mBtnPos = this.mBtnOffPos;
        }
        this.mRealPos = getRealPos(this.mBtnPos);
    }

    private float getRealPos(float paramFloat) {
        return paramFloat - (this.mBtnWidth / 2.0f);
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        setChecked(checked, false);
    }

    public void setChecked(boolean checked, boolean anim) {
        if (checked) {
            this.mBtnPos = this.mBtnOnPos;
        } else {
            this.mBtnPos = this.mBtnOffPos;
        }
        if (anim) {
            startAnimation();
        } else {
            moveViewToTarget();
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean z = false;
        if (!this.mAnimating) {
            switch (event.getAction()) {
                case 0:
                    this.mMoveEvent = false;
                    this.mFirstDownX = event.getX();
                    this.mCurBtnPic = this.mBtnPressed;
                    if (this.isChecked) {
                        this.mBtnPos = this.mBtnOnPos;
                    } else {
                        this.mBtnPos = this.mBtnOffPos;
                    }
                    this.mLastBtnPos = this.mBtnPos;
                    break;
                case 2:
                    float offsetX = event.getX() - this.mFirstDownX;
                    if (Math.abs(offsetX) >= 5.0f) {
                        this.mMoveEvent = true;
                        this.mFirstDownX = event.getX();
                        this.mBtnPos += offsetX;
                        if (this.mBtnPos < this.mBtnOffPos) {
                            this.mBtnPos = this.mBtnOffPos;
                        }
                        if (this.mBtnPos > this.mBtnOnPos) {
                            this.mBtnPos = this.mBtnOnPos;
                        }
                        moveViewToTarget();
                        break;
                    }
                    break;
                default:
                    if (!this.mMoveEvent) {
                        if (!this.isChecked) {
                            z = true;
                        }
                        setChecked(z, true);
                        break;
                    }
                    this.mCurBtnPic = this.mBtnNormal;
                    if (this.mBtnPos < ((this.mBtnOnPos - this.mBtnOffPos) / 2.0f) + this.mBtnOffPos) {
                        this.mBtnPos = this.mBtnOffPos;
                    } else {
                        this.mBtnPos = this.mBtnOnPos;
                    }
                    startAnimation();
                    break;
            }
        }
        return true;
    }

    private void moveView(float pos) {
        moveView(pos, false);
    }

    private void moveView(float pos, boolean delay) {
        if (this.handler != null) {
            this.handler.obtainMessage(0, Float.valueOf(pos)).sendToTarget();
        }
    }

    private void startAnimation() {
        if (this.mLastBtnPos != this.mBtnPos) {
            this.mAnimating = true;
            float mVelocity = this.mAnimatedVelocity;
            if (this.mLastBtnPos > this.mBtnPos) {
                mVelocity = -this.mAnimatedVelocity;
            }
            this.mAnimationPosition = this.mLastBtnPos;
            int i = 0;
            while (true) {
                this.mAnimationPosition += (16.0f * mVelocity) / 1000.0f;
                if (this.mAnimationPosition >= this.mBtnOnPos) {
                    this.mAnimationPosition = this.mBtnOnPos;
                    moveView(this.mAnimationPosition, true);
                    if (!this.isChecked) {
                        this.isChecked = true;
                        if (this.onCheckedChangeListener != null) {
                            this.onCheckedChangeListener.onCheckedChanged(this, this.isChecked);
                        }
                    }
                } else if (this.mAnimationPosition <= this.mBtnOffPos) {
                    this.mAnimationPosition = this.mBtnOffPos;
                    moveView(this.mAnimationPosition, true);
                    if (this.isChecked) {
                        this.isChecked = false;
                        if (this.onCheckedChangeListener != null) {
                            this.onCheckedChangeListener.onCheckedChanged(this, this.isChecked);
                        }
                    }
                } else {
                    moveView(this.mAnimationPosition, true);
                    i++;
                }
            }
            this.mAnimating = false;
        }
    }

    private void moveViewToTarget() {
        moveView(this.mBtnPos);
        if (this.mBtnPos == this.mBtnOnPos) {
            if (!this.isChecked) {
                this.isChecked = true;
                if (this.onCheckedChangeListener != null) {
                    this.onCheckedChangeListener.onCheckedChanged(this, this.isChecked);
                }
            }
        } else if (this.mBtnPos == this.mBtnOffPos && this.isChecked) {
            this.isChecked = false;
            if (this.onCheckedChangeListener != null) {
                this.onCheckedChangeListener.onCheckedChanged(this, this.isChecked);
            }
        }
    }

    public boolean performClick() {
        setChecked(!this.isChecked);
        return true;
    }

    public void toggle() {
        setChecked(!this.isChecked);
    }
}
