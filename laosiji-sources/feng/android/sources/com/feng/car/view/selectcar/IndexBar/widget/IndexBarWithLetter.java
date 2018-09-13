package com.feng.car.view.selectcar.IndexBar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.activity.CityListActivity;
import com.feng.car.entity.car.CarBrandInfo;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.lcoation.CityInfo;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.UmengConstans;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IndexBarWithLetter<T> extends View {
    public static String[] INDEX_STRING = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private boolean isNeedRealIndex;
    private int mGapHeight;
    private int mHeadNum;
    private int mHeight;
    private List<String> mIndexDatas;
    private boolean mIsCity;
    private LinearLayoutManager mLayoutManager;
    private onIndexPressedListener mOnIndexPressedListener;
    private Paint mPaint;
    private int mPressedBackground;
    private TextView mPressedShowTextView;
    private List<T> mSourceDatas;
    private int mWidth;

    public interface onIndexPressedListener {
        void onIndexPressed(int i, String str);

        void onMotionEventEnd();
    }

    public IndexBarWithLetter(Context context) {
        this(context, null);
    }

    public IndexBarWithLetter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBarWithLetter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mHeadNum = 0;
        this.mIsCity = false;
        if (context instanceof CityListActivity) {
            this.mIsCity = true;
        }
        init(context, attrs, defStyleAttr);
    }

    private void init(final Context context, AttributeSet attrs, int defStyleAttr) {
        int textSize = (int) TypedValue.applyDimension(2, 16.0f, getResources().getDisplayMetrics());
        this.mPressedBackground = -16777216;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndexBarWithLetter, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case 0:
                    textSize = typedArray.getDimensionPixelSize(attr, textSize);
                    break;
                case 1:
                    this.mPressedBackground = typedArray.getColor(attr, this.mPressedBackground);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();
        if (!this.isNeedRealIndex) {
            this.mIndexDatas = Arrays.asList(INDEX_STRING);
        }
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize((float) textSize);
        this.mPaint.setColor(-16777216);
        setmOnIndexPressedListener(new onIndexPressedListener() {
            public void onIndexPressed(int index, String text) {
                if (IndexBarWithLetter.this.mPressedShowTextView != null) {
                    IndexBarWithLetter.this.mPressedShowTextView.setVisibility(0);
                    IndexBarWithLetter.this.mPressedShowTextView.setText(text);
                }
                if (IndexBarWithLetter.this.mLayoutManager != null) {
                    int position = IndexBarWithLetter.this.getPosByTag(text);
                    if (position != -1) {
                        IndexBarWithLetter.this.mLayoutManager.scrollToPositionWithOffset(IndexBarWithLetter.this.mHeadNum + position, 0);
                    }
                }
                if (IndexBarWithLetter.this.mIsCity) {
                    MobclickAgent.onEvent(context, UmengConstans.DEALER_LOCATION_CHOOSE);
                }
            }

            public void onMotionEventEnd() {
                if (IndexBarWithLetter.this.mPressedShowTextView != null) {
                    IndexBarWithLetter.this.mPressedShowTextView.setVisibility(8);
                }
            }
        });
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        Rect indexBounds = new Rect();
        for (int i = 0; i < this.mIndexDatas.size(); i++) {
            String index = (String) this.mIndexDatas.get(i);
            this.mPaint.getTextBounds(index, 0, index.length(), indexBounds);
            measureWidth = Math.max(indexBounds.width(), measureWidth);
            measureHeight = Math.max(indexBounds.width(), measureHeight);
        }
        measureHeight *= this.mIndexDatas.size();
        switch (wMode) {
            case Integer.MIN_VALUE:
                measureWidth = Math.min(measureWidth, wSize);
                break;
            case FengConstant.MAXVIDEOSIZE /*1073741824*/:
                measureWidth = wSize;
                break;
        }
        switch (hMode) {
            case Integer.MIN_VALUE:
                measureHeight = Math.min(measureHeight, hSize);
                break;
            case FengConstant.MAXVIDEOSIZE /*1073741824*/:
                measureHeight = hSize;
                break;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    protected void onDraw(Canvas canvas) {
        int t = getPaddingTop();
        Rect indexBounds = new Rect();
        for (int i = 0; i < this.mIndexDatas.size(); i++) {
            String index = (String) this.mIndexDatas.get(i);
            this.mPaint.getTextBounds(index, 0, index.length(), indexBounds);
            FontMetrics fontMetrics = this.mPaint.getFontMetrics();
            canvas.drawText(index, (float) ((this.mWidth / 2) - (indexBounds.width() / 2)), (float) (((this.mGapHeight * i) + t) + ((int) (((((float) this.mGapHeight) - fontMetrics.bottom) - fontMetrics.top) / 2.0f))), this.mPaint);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                setBackgroundColor(this.mPressedBackground);
                break;
            case 2:
                break;
            default:
                setBackgroundResource(17170445);
                if (this.mOnIndexPressedListener != null) {
                    this.mOnIndexPressedListener.onMotionEventEnd();
                    break;
                }
                break;
        }
        int pressI = (int) ((event.getY() - ((float) getPaddingTop())) / ((float) this.mGapHeight));
        if (pressI < 0) {
            pressI = 0;
        } else if (pressI >= this.mIndexDatas.size()) {
            pressI = this.mIndexDatas.size() - 1;
        }
        if (this.mOnIndexPressedListener != null) {
            this.mOnIndexPressedListener.onIndexPressed(pressI, (String) this.mIndexDatas.get(pressI));
        }
        return true;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        this.mGapHeight = ((this.mHeight - getPaddingTop()) - getPaddingBottom()) / this.mIndexDatas.size();
    }

    public onIndexPressedListener getmOnIndexPressedListener() {
        return this.mOnIndexPressedListener;
    }

    public void setmOnIndexPressedListener(onIndexPressedListener mOnIndexPressedListener) {
        this.mOnIndexPressedListener = mOnIndexPressedListener;
    }

    public IndexBarWithLetter setmPressedShowTextView(TextView mPressedShowTextView) {
        this.mPressedShowTextView = mPressedShowTextView;
        return this;
    }

    public IndexBarWithLetter setmLayoutManager(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        return this;
    }

    public IndexBarWithLetter setHeadNum(int headNum) {
        this.mHeadNum = headNum;
        return this;
    }

    public IndexBarWithLetter setNeedRealIndex(boolean needRealIndex) {
        this.isNeedRealIndex = needRealIndex;
        if (this.isNeedRealIndex && this.mIndexDatas != null) {
            this.mIndexDatas = new ArrayList();
        }
        return this;
    }

    public IndexBarWithLetter setmSourceDatas(List<T> mSourceDatas) {
        this.mSourceDatas = mSourceDatas;
        initSourceDatas();
        return this;
    }

    private void initSourceDatas() {
        int size = this.mSourceDatas.size();
        for (int i = 0; i < size; i++) {
            if (this.isNeedRealIndex) {
                CarBrandInfo object = this.mSourceDatas.get(i);
                if (object instanceof CarModelInfo) {
                    CarBrandInfo carModelInfo = object;
                    if (!this.mIndexDatas.contains(carModelInfo.abc)) {
                        this.mIndexDatas.add(carModelInfo.abc);
                    }
                } else if (object instanceof CityInfo) {
                    CityInfo cityInfo = (CityInfo) object;
                    if (!this.mIndexDatas.contains(cityInfo.abc)) {
                        this.mIndexDatas.add(cityInfo.abc);
                    }
                }
            }
        }
        sortData();
    }

    private void sortData() {
        Collections.sort(this.mIndexDatas, new Comparator<String>() {
            public int compare(String lhs, String rhs) {
                if (lhs.equals("#")) {
                    return 1;
                }
                if (rhs.equals("#")) {
                    return -1;
                }
                return lhs.compareTo(rhs);
            }
        });
        Collections.sort(this.mSourceDatas, new Comparator<Object>() {
            public int compare(Object lhs, Object rhs) {
                if (lhs instanceof CarBrandInfo) {
                    CarBrandInfo carlhs = (CarBrandInfo) lhs;
                    CarBrandInfo carrhs = (CarBrandInfo) rhs;
                    if (carlhs.abc.equals("#")) {
                        return 1;
                    }
                    if (carrhs.abc.equals("#")) {
                        return -1;
                    }
                    return carlhs.abc.compareTo(carrhs.abc);
                } else if (!(lhs instanceof CityInfo)) {
                    return -1;
                } else {
                    CityInfo citylhs = (CityInfo) lhs;
                    CityInfo cityrhs = (CityInfo) rhs;
                    if (citylhs.abc.equals("#")) {
                        return 1;
                    }
                    if (cityrhs.abc.equals("#")) {
                        return -1;
                    }
                    return citylhs.abc.compareTo(cityrhs.abc);
                }
            }
        });
    }

    private int getPosByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        for (int i = 0; i < this.mSourceDatas.size(); i++) {
            CarBrandInfo objcet = this.mSourceDatas.get(i);
            if (objcet instanceof CarBrandInfo) {
                if (tag.equals(objcet.abc)) {
                    return i;
                }
            } else if ((objcet instanceof CityInfo) && tag.equals(((CityInfo) objcet).abc)) {
                return i;
            }
        }
        return -1;
    }
}
