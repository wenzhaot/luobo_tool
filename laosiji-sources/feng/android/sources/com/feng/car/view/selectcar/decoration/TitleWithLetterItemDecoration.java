package com.feng.car.view.selectcar.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import com.feng.car.entity.car.CarBrandInfo;
import com.feng.car.entity.lcoation.CityInfo;
import java.util.List;

public class TitleWithLetterItemDecoration<T> extends ItemDecoration {
    private static int COLOR_TITLE_BG = Color.parseColor("#FFF5F5F5");
    private static int COLOR_TITLE_FONT = Color.parseColor("#66000000");
    private static int mTitleFontSize;
    private int m20;
    private Rect mBounds;
    private Context mContext;
    private List<Object> mDatas;
    private int mHasHeadNum;
    private LayoutInflater mInflater;
    private Paint mPaint;
    private int mTitleHeight;

    public TitleWithLetterItemDecoration(Context context, List<Object> datas) {
        this(context, 0, datas);
    }

    public TitleWithLetterItemDecoration(Context context, int headNum, List<Object> datas) {
        this.mHasHeadNum = 0;
        this.mHasHeadNum = headNum;
        this.mDatas = datas;
        this.mPaint = new Paint();
        this.mBounds = new Rect();
        this.mTitleHeight = context.getResources().getDimensionPixelOffset(2131296825);
        mTitleFontSize = (int) TypedValue.applyDimension(1, 12.0f, context.getResources().getDisplayMetrics());
        this.mPaint.setTextSize((float) mTitleFontSize);
        this.mPaint.setAntiAlias(true);
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.m20 = this.mContext.getResources().getDimensionPixelSize(2131296379);
    }

    public void onDraw(Canvas c, RecyclerView parent, State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) {
                if (position == this.mHasHeadNum) {
                    drawTitleArea(c, left, right, child, params, position - this.mHasHeadNum);
                } else if (position - this.mHasHeadNum < this.mDatas.size() && position >= this.mHasHeadNum) {
                    Object object = this.mDatas.get(position - this.mHasHeadNum);
                    if (object instanceof CarBrandInfo) {
                        CarBrandInfo carBrandInfo = (CarBrandInfo) this.mDatas.get(position - this.mHasHeadNum);
                        CarBrandInfo carBrandInfo1 = (CarBrandInfo) this.mDatas.get((position - 1) - this.mHasHeadNum);
                        if (!(carBrandInfo.abc == null || carBrandInfo.abc.equals(carBrandInfo1.abc))) {
                            drawTitleArea(c, left, right, child, params, position - this.mHasHeadNum);
                        }
                    } else if (object instanceof CityInfo) {
                        CityInfo cityInfo = (CityInfo) this.mDatas.get(position - this.mHasHeadNum);
                        CityInfo cityInfo1 = (CityInfo) this.mDatas.get((position - 1) - this.mHasHeadNum);
                        if (!(cityInfo.abc == null || cityInfo.abc.equals(cityInfo1.abc))) {
                            drawTitleArea(c, left, right, child, params, position - this.mHasHeadNum);
                        }
                    }
                }
            }
        }
    }

    private void drawTitleArea(Canvas c, int left, int right, View child, LayoutParams params, int position) {
        this.mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect((float) left, (float) ((child.getTop() - params.topMargin) - this.mTitleHeight), (float) right, (float) (child.getTop() - params.topMargin), this.mPaint);
        this.mPaint.setColor(COLOR_TITLE_FONT);
        Object object = this.mDatas.get(position);
        if (object instanceof CarBrandInfo) {
            CarBrandInfo carBrandInfo = (CarBrandInfo) this.mDatas.get(position);
            this.mPaint.getTextBounds(carBrandInfo.abc, 0, carBrandInfo.abc.length(), this.mBounds);
            c.drawText(carBrandInfo.abc, (float) (child.getPaddingLeft() + this.m20), (float) ((child.getTop() - params.topMargin) - ((this.mTitleHeight / 2) - (this.mBounds.height() / 2))), this.mPaint);
        } else if (object instanceof CityInfo) {
            CityInfo cityInfo = (CityInfo) this.mDatas.get(position);
            this.mPaint.getTextBounds(cityInfo.abc, 0, cityInfo.abc.length(), this.mBounds);
            c.drawText(cityInfo.abc, (float) (child.getPaddingLeft() + this.m20), (float) ((child.getTop() - params.topMargin) - ((this.mTitleHeight / 2) - (this.mBounds.height() / 2))), this.mPaint);
        }
    }

    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
        int pos = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition() - this.mHasHeadNum;
        if (pos >= 0) {
            Object object = this.mDatas.get(pos);
            String tag = "";
            if (object instanceof CarBrandInfo) {
                tag = ((CarBrandInfo) this.mDatas.get(pos)).abc;
            } else if (object instanceof CityInfo) {
                tag = ((CityInfo) this.mDatas.get(pos)).abc;
            }
            View child = parent.findViewHolderForLayoutPosition(this.mHasHeadNum + pos).itemView;
            boolean flag = false;
            if (pos + 1 < this.mDatas.size()) {
                Object obj = this.mDatas.get(pos + 1);
                if (obj instanceof CarBrandInfo) {
                    CarBrandInfo carBrandInfo = (CarBrandInfo) this.mDatas.get(pos + 1);
                    if (!(tag == null || tag.equals(carBrandInfo.abc))) {
                        Log.d("zxt", "onDrawOver() called with: c = [" + child.getTop());
                        if (child.getHeight() + child.getTop() < this.mTitleHeight) {
                            c.save();
                            flag = true;
                            c.translate(0.0f, (float) ((child.getHeight() + child.getTop()) - this.mTitleHeight));
                        }
                    }
                } else if (obj instanceof CityInfo) {
                    CityInfo cityInfo = (CityInfo) this.mDatas.get(pos + 1);
                    if (!(tag == null || tag.equals(cityInfo.abc))) {
                        Log.d("zxt", "onDrawOver() called with: c = [" + child.getTop());
                        if (child.getHeight() + child.getTop() < this.mTitleHeight) {
                            c.save();
                            flag = true;
                            c.translate(0.0f, (float) ((child.getHeight() + child.getTop()) - this.mTitleHeight));
                        }
                    }
                }
            }
            this.mPaint.setColor(COLOR_TITLE_BG);
            c.drawRect((float) parent.getPaddingLeft(), (float) parent.getPaddingTop(), (float) (parent.getRight() - parent.getPaddingRight()), (float) (parent.getPaddingTop() + this.mTitleHeight), this.mPaint);
            this.mPaint.setColor(COLOR_TITLE_FONT);
            this.mPaint.getTextBounds(tag, 0, tag.length(), this.mBounds);
            c.drawText(tag, (float) (child.getPaddingLeft() + this.m20), (float) ((parent.getPaddingTop() + this.mTitleHeight) - ((this.mTitleHeight / 2) - (this.mBounds.height() / 2))), this.mPaint);
            if (flag) {
                c.restore();
            }
        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position <= -1) {
            return;
        }
        if (position == this.mHasHeadNum) {
            outRect.set(0, this.mTitleHeight, 0, 0);
        } else if (position < this.mHasHeadNum) {
            outRect.set(0, 0, 0, 0);
        } else if (position - this.mHasHeadNum < this.mDatas.size()) {
            Object object = this.mDatas.get(position - this.mHasHeadNum);
            if (object instanceof CarBrandInfo) {
                CarBrandInfo carBrandInfo = (CarBrandInfo) this.mDatas.get(position - this.mHasHeadNum);
                CarBrandInfo carBrandInfo1 = (CarBrandInfo) this.mDatas.get((position - 1) - this.mHasHeadNum);
                if (carBrandInfo.abc == null || carBrandInfo.abc.equals(carBrandInfo1.abc)) {
                    outRect.set(0, 0, 0, 0);
                } else {
                    outRect.set(0, this.mTitleHeight, 0, 0);
                }
            } else if (object instanceof CityInfo) {
                CityInfo cityInfo = (CityInfo) this.mDatas.get(position - this.mHasHeadNum);
                CityInfo cityInfo1 = (CityInfo) this.mDatas.get((position - 1) - this.mHasHeadNum);
                if (cityInfo.abc == null || cityInfo.abc.equals(cityInfo1.abc)) {
                    outRect.set(0, 0, 0, 0);
                } else {
                    outRect.set(0, this.mTitleHeight, 0, 0);
                }
            }
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }
}
