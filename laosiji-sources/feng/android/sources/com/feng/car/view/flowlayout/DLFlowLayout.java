package com.feng.car.view.flowlayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.entity.searchcar.SearchCarBean;
import com.feng.car.utils.FengUtil;
import com.feng.library.emoticons.keyboard.widget.EmoticonSpan;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DLFlowLayout extends ViewGroup {
    private static final int STYPE_SELECT = 0;
    private static final int STYPE_TAG = 1;
    private boolean canCancelAll;
    private int hasSelectCount;
    private int mBackground;
    private Drawable mBlackTr;
    private List<View> mChildList;
    private Context mContext;
    private int mCountPerLine;
    private List<SearchCarBean> mData;
    private FlowDataSetListener mDataSetListener;
    private boolean mEqually;
    private FlowClickListener mFlowClickListener;
    private Drawable mGrayTr;
    private int mGuidePriceBackGround;
    private int mHorizontalSpacing;
    private boolean mIsSingle;
    private List<Integer> mLineHeights;
    public OnSelectListener mOnSelectListener;
    private int mRealPriceBackGround;
    private int mSelectPosition;
    private int mSelectTextColor;
    private int mStype;
    private ColorStateList mTextColor;
    private float mTextSize;
    private int mType;
    private int mUnSelectBackGround;
    private int mUnSelectTextColor;
    private int mVerticalSpacing;
    private List<List<View>> mViewLinesList;
    private Drawable mWhiteTr;
    private int mWidth;
    private int mar;
    private int maxItemW;
    private int maxSelectNum;
    private int usefulWidth;

    public void setType(int type) {
        this.mType = type;
    }

    public void setIsSingle(boolean s) {
        this.mIsSingle = s;
    }

    public DLFlowLayout(Context context) {
        this(context, null);
    }

    public DLFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DLFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mViewLinesList = new ArrayList();
        this.mChildList = new ArrayList();
        this.mLineHeights = new ArrayList();
        this.mIsSingle = true;
        this.mEqually = true;
        this.mTextColor = getResources().getColorStateList(2131558693);
        this.mStype = 0;
        this.mSelectPosition = -1;
        this.maxSelectNum = -1;
        this.mData = new ArrayList();
        this.mType = 0;
        this.mGuidePriceBackGround = 2130837684;
        this.mRealPriceBackGround = 2130837679;
        this.mUnSelectBackGround = 2130837685;
        this.mSelectTextColor = 2131558510;
        this.mUnSelectTextColor = 2131558482;
        this.hasSelectCount = 0;
        this.canCancelAll = false;
        this.mCountPerLine = 4;
        this.mContext = context;
        this.mar = this.mContext.getResources().getDimensionPixelSize(2131296512);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DLFlowLayout);
        this.mStype = typedArray.getInt(8, 0);
        this.mTextColor = typedArray.getColorStateList(4);
        this.mBackground = typedArray.getResourceId(2, 2130837866);
        this.mTextSize = typedArray.getFloat(3, 14.0f);
        this.mEqually = typedArray.getBoolean(6, false);
        this.mIsSingle = typedArray.getBoolean(5, true);
        this.maxSelectNum = typedArray.getInt(7, -1);
        this.mHorizontalSpacing = typedArray.getDimensionPixelSize(0, 6);
        this.mVerticalSpacing = typedArray.getDimensionPixelSize(1, this.mContext.getResources().getDimensionPixelSize(2131296268));
        this.mWhiteTr = context.getResources().getDrawable(2130838022);
        this.mGrayTr = context.getResources().getDrawable(2130838021);
        this.mBlackTr = context.getResources().getDrawable(2130838020);
        this.mWhiteTr.setBounds(0, 0, this.mWhiteTr.getMinimumWidth(), this.mWhiteTr.getMinimumHeight());
        this.mGrayTr.setBounds(0, 0, this.mGrayTr.getMinimumWidth(), this.mGrayTr.getMinimumHeight());
        this.mBlackTr.setBounds(0, 0, this.mBlackTr.getMinimumWidth(), this.mBlackTr.getMinimumHeight());
        typedArray.recycle();
    }

    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(super.generateDefaultLayoutParams());
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int iHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int iWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int iHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int mPaddingLeft = getPaddingLeft();
        int mPaddingRight = getPaddingRight();
        int mPaddingTop = getPaddingTop();
        int mPaddingBottom = getPaddingBottom();
        int iLineLeftRrightPadding = mPaddingLeft + mPaddingRight;
        int measureWidth = iWidthSpecSize;
        int measureHeight = 0;
        int iCurLineW = 0;
        int iCurLineH = 0;
        if (iWidthMode == 1073741824 && iHeightMode == 1073741824) {
            measureWidth = iWidthSpecSize;
            measureHeight = iHeightSpecSize;
        } else {
            int childCount = getChildCount();
            List<View> viewList = new ArrayList();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                resetItemWidth(childView, i);
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                int iChildWidth = (childView.getMeasuredWidth() + layoutParams.leftMargin) + layoutParams.rightMargin;
                int iChildHeight = (childView.getMeasuredHeight() + layoutParams.topMargin) + layoutParams.bottomMargin;
                if ((iCurLineW + iChildWidth) + iLineLeftRrightPadding > iWidthSpecSize) {
                    measureWidth = Math.max(measureWidth, iCurLineW);
                    measureHeight += iCurLineH;
                    this.mViewLinesList.add(viewList);
                    this.mLineHeights.add(Integer.valueOf(iCurLineH));
                    iCurLineW = iChildWidth;
                    iCurLineH = iChildHeight;
                    viewList = new ArrayList();
                    viewList.add(childView);
                } else {
                    iCurLineW += iChildWidth;
                    iCurLineH = Math.max(iCurLineH, iChildHeight);
                    viewList.add(childView);
                }
                if (i == childCount - 1) {
                    measureWidth = Math.max(measureWidth, iCurLineW);
                    measureHeight += iCurLineH;
                    this.mViewLinesList.add(viewList);
                    this.mLineHeights.add(Integer.valueOf(iCurLineH));
                }
            }
            measureHeight = (measureHeight + mPaddingTop) + mPaddingBottom;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int curLeft;
        List<View> viewList;
        int lineViewSize;
        this.usefulWidth = ((r - l) - getPaddingLeft()) - getPaddingRight();
        int mPaddingLeft = getPaddingLeft();
        int mPaddingRight = getPaddingRight();
        int curTop = getPaddingTop();
        if (this.mEqually) {
            curLeft = 0;
        } else {
            curLeft = mPaddingLeft;
        }
        int lineCount = this.mViewLinesList.size();
        int space = 0;
        if (this.mEqually && this.mViewLinesList.size() > 0) {
            viewList = (List) this.mViewLinesList.get(0);
            lineViewSize = viewList.size();
            space = (getWidth() - (((View) viewList.get(0)).getMeasuredWidth() * lineViewSize)) / (lineViewSize + 1);
        }
        for (int i = 0; i < lineCount; i++) {
            int intValue;
            int j;
            View childView;
            int left;
            int top;
            if (this.mEqually) {
                viewList = (List) this.mViewLinesList.get(i);
                lineViewSize = viewList.size();
                for (j = 0; j < lineViewSize; j++) {
                    childView = (View) viewList.get(j);
                    left = curLeft + space;
                    top = curTop + ((MarginLayoutParams) childView.getLayoutParams()).topMargin;
                    childView.layout(left, top, left + childView.getMeasuredWidth(), top + childView.getMeasuredHeight());
                    curLeft += childView.getMeasuredWidth() + space;
                }
                curLeft = this.mEqually ? 0 : mPaddingLeft;
                intValue = ((Integer) this.mLineHeights.get(i)).intValue();
            } else {
                viewList = (List) this.mViewLinesList.get(i);
                lineViewSize = viewList.size();
                for (j = 0; j < lineViewSize; j++) {
                    childView = (View) viewList.get(j);
                    MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                    left = curLeft + layoutParams.leftMargin;
                    top = curTop + layoutParams.topMargin;
                    childView.layout(left, top, left + childView.getMeasuredWidth(), top + childView.getMeasuredHeight());
                    curLeft += (childView.getMeasuredWidth() + layoutParams.leftMargin) + layoutParams.rightMargin;
                }
                curLeft = mPaddingLeft;
                intValue = ((Integer) this.mLineHeights.get(i)).intValue();
            }
            curTop += intValue;
        }
        this.mViewLinesList.clear();
        this.mLineHeights.clear();
    }

    private void compress() {
        int childCount = getChildCount();
        if (childCount != 0) {
            int i;
            View v;
            int count = childCount;
            View[] childs = new View[count];
            int[] spaces = new int[count];
            int n = 0;
            for (i = 0; i < childCount; i++) {
                v = getChildAt(i);
                childs[n] = v;
                LayoutParams childLp = v.getLayoutParams();
                int childWidth = v.getMeasuredWidth();
                if (childLp instanceof MarginLayoutParams) {
                    MarginLayoutParams mlp = (MarginLayoutParams) childLp;
                    spaces[n] = (mlp.leftMargin + childWidth) + mlp.rightMargin;
                } else {
                    spaces[n] = childWidth;
                }
                n++;
            }
            int[] compressSpaces = new int[count];
            i = 0;
            while (i < count) {
                compressSpaces[i] = spaces[i] > this.usefulWidth ? this.usefulWidth : spaces[i];
                i++;
            }
            sortToCompress(childs, compressSpaces);
            removeAllViews();
            for (View v2 : this.mChildList) {
                ViewGroup parent = (ViewGroup) v2.getParent();
                if (parent != null) {
                    parent.removeAllViewsInLayout();
                }
                addView(v2);
            }
            this.mChildList.clear();
        }
    }

    private void sortToCompress(View[] childs, int[] spaces) {
        int i;
        int j;
        int childCount = childs.length;
        int[][] table = (int[][]) Array.newInstance(Integer.TYPE, new int[]{childCount + 1, this.usefulWidth + 1});
        for (i = 0; i < childCount + 1; i++) {
            for (j = 0; j < this.usefulWidth; j++) {
                table[i][j] = 0;
            }
        }
        boolean[] flag = new boolean[childCount];
        for (i = 0; i < childCount; i++) {
            flag[i] = false;
        }
        for (i = 1; i <= childCount; i++) {
            j = spaces[i - 1];
            while (j <= this.usefulWidth) {
                table[i][j] = table[i + -1][j] > table[i + -1][j - spaces[i + -1]] + spaces[i + -1] ? table[i - 1][j] : table[i - 1][j - spaces[i - 1]] + spaces[i - 1];
                j++;
            }
        }
        int v = this.usefulWidth;
        i = childCount;
        while (i > 0 && v >= spaces[i - 1]) {
            if (table[i][v] == table[i - 1][v - spaces[i - 1]] + spaces[i - 1]) {
                flag[i - 1] = true;
                v -= spaces[i - 1];
            }
            i--;
        }
        int rest = childCount;
        for (i = 0; i < flag.length; i++) {
            if (flag[i]) {
                this.mChildList.add(childs[i]);
                rest--;
            }
        }
        if (rest != 0) {
            View[] restArray = new View[rest];
            int[] restSpaces = new int[rest];
            int index = 0;
            for (i = 0; i < flag.length; i++) {
                if (!flag[i]) {
                    restArray[index] = childs[i];
                    restSpaces[index] = spaces[i];
                    index++;
                }
            }
            table = null;
            sortToCompress(restArray, restSpaces);
        }
    }

    public void setCanCancelAll(boolean canCancelAll) {
        this.canCancelAll = canCancelAll;
    }

    public void setFlowData(List<SearchCarBean> data) {
        for (int i = 0; i < data.size(); i++) {
            ((SearchCarBean) data.get(i)).position = i;
        }
        this.mData = data;
        removeAllViews();
        addViews(this.mData);
        postInvalidate();
        if (this.mDataSetListener != null) {
            this.mDataSetListener.onDataSetFinish();
        }
    }

    public void setDataSetListener(FlowDataSetListener l) {
        this.mDataSetListener = l;
    }

    private void addViews(List<SearchCarBean> mData) {
        for (int i = 0; i < mData.size(); i++) {
            addChildView((SearchCarBean) mData.get(i));
        }
        if (this.mStype != 0) {
            setAllUnEnable();
        }
    }

    private void setUnSelectColor(TextView chk, SearchCarBean bean) {
        chk.setSelected(false);
        chk.setBackgroundResource(this.mUnSelectBackGround);
        chk.setPadding(this.mContext.getResources().getDimensionPixelSize(2131296368), this.mContext.getResources().getDimensionPixelSize(2131296290), this.mContext.getResources().getDimensionPixelSize(2131296368), this.mContext.getResources().getDimensionPixelSize(2131296290));
        chk.setTextColor(this.mContext.getResources().getColor(this.mUnSelectTextColor));
        if (bean.canClick) {
            EmoticonSpan imageSpan = new EmoticonSpan(this.mGrayTr);
            SpannableStringBuilder spannable = new SpannableStringBuilder(bean.name + " @");
            spannable.setSpan(imageSpan, bean.name.length() + 1, bean.name.length() + 2, 17);
            chk.setText(spannable);
        }
    }

    private void setSelectColor(TextView chk, SearchCarBean bean) {
        chk.setSelected(true);
        if (this.mType == 0) {
            chk.setBackgroundResource(this.mGuidePriceBackGround);
        } else {
            chk.setBackgroundResource(this.mRealPriceBackGround);
        }
        chk.setPadding(this.mContext.getResources().getDimensionPixelSize(2131296368), this.mContext.getResources().getDimensionPixelSize(2131296290), this.mContext.getResources().getDimensionPixelSize(2131296368), this.mContext.getResources().getDimensionPixelSize(2131296290));
        chk.setTextColor(this.mContext.getResources().getColor(this.mSelectTextColor));
        if (bean.canClick) {
            EmoticonSpan imageSpan = new EmoticonSpan(this.mBlackTr);
            SpannableStringBuilder spannable = new SpannableStringBuilder(bean.name + " @");
            spannable.setSpan(imageSpan, bean.name.length() + 1, bean.name.length() + 2, 17);
            chk.setText(spannable);
        }
    }

    public void addChildView(SearchCarBean bean) {
        View itemView = LayoutInflater.from(this.mContext).inflate(2130903427, null);
        TextView chk = (TextView) itemView.findViewById(2131625551);
        chk.setTag(Integer.valueOf(bean.position));
        chk.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                TextView chk = (TextView) v;
                SearchCarBean carBean = (SearchCarBean) DLFlowLayout.this.mData.get(((Integer) chk.getTag()).intValue());
                if (carBean.canClick && DLFlowLayout.this.mFlowClickListener != null) {
                    DLFlowLayout.this.mFlowClickListener.onFLowClick(carBean, chk);
                } else if (DLFlowLayout.this.mIsSingle) {
                    if (!carBean.isChecked) {
                        carBean.isChecked = true;
                        DLFlowLayout.this.setSelectColor(chk, carBean);
                        DLFlowLayout.this.mSelectPosition = ((Integer) chk.getTag()).intValue();
                        DLFlowLayout.this.notifyAllItemView(DLFlowLayout.this.mSelectPosition);
                        if (DLFlowLayout.this.mOnSelectListener != null) {
                            DLFlowLayout.this.mOnSelectListener.onSelect(carBean.position);
                        }
                    }
                } else if (!carBean.isChecked) {
                    if (carBean.name.equals("不限")) {
                        carBean.isChecked = true;
                        DLFlowLayout.this.setSelectColor(chk, carBean);
                        for (int i = 0; i < DLFlowLayout.this.mData.size(); i++) {
                            SearchCarBean b = (SearchCarBean) DLFlowLayout.this.mData.get(i);
                            TextView textView = (TextView) DLFlowLayout.this.getChildAt(i).findViewById(2131625551);
                            if (i != 0) {
                                if (b.canClick) {
                                    b.resetSublist();
                                } else {
                                    b.isChecked = false;
                                }
                                DLFlowLayout.this.setUnSelectColor(textView, b);
                            }
                        }
                        DLFlowLayout.this.hasSelectCount = 1;
                    } else {
                        SearchCarBean firstBean = (SearchCarBean) DLFlowLayout.this.mData.get(0);
                        SearchCarBean lastBean = (SearchCarBean) DLFlowLayout.this.mData.get(DLFlowLayout.this.mData.size() - 1);
                        if (firstBean.isChecked && firstBean.name.equals("不限")) {
                            firstBean.isChecked = false;
                            DLFlowLayout.this.setUnSelectColor((TextView) DLFlowLayout.this.getChildAt(0).findViewById(2131625551), firstBean);
                            DLFlowLayout.this.hasSelectCount = 1;
                        } else if (lastBean.isChecked && lastBean.name.equals("自定义")) {
                            lastBean.isChecked = false;
                            DLFlowLayout.this.setUnSelectColor((TextView) DLFlowLayout.this.getChildAt(DLFlowLayout.this.mData.size() - 1).findViewById(2131625551), lastBean);
                            DLFlowLayout.this.hasSelectCount = 1;
                        } else {
                            DLFlowLayout.this.hasSelectCount = DLFlowLayout.this.hasSelectCount + 1;
                        }
                        carBean.isChecked = true;
                        DLFlowLayout.this.setSelectColor(chk, carBean);
                    }
                    if (DLFlowLayout.this.mOnSelectListener != null) {
                        DLFlowLayout.this.mOnSelectListener.onSelect(carBean.position);
                    }
                } else if (DLFlowLayout.this.canCancelAll) {
                    DLFlowLayout.this.setUnSelectColor(chk, carBean);
                    carBean.isChecked = false;
                    if (DLFlowLayout.this.mOnSelectListener != null) {
                        DLFlowLayout.this.mOnSelectListener.onUnSelect(carBean.position);
                    }
                } else if (DLFlowLayout.this.hasSelectCount >= 2) {
                    DLFlowLayout.this.hasSelectCount = DLFlowLayout.this.hasSelectCount - 1;
                    carBean.isChecked = false;
                    DLFlowLayout.this.setUnSelectColor(chk, carBean);
                    if (DLFlowLayout.this.mOnSelectListener != null) {
                        DLFlowLayout.this.mOnSelectListener.onUnSelect(carBean.position);
                    }
                } else if (!carBean.name.equals("不限")) {
                    DLFlowLayout.this.setUnSelectColor(chk, carBean);
                    carBean.isChecked = false;
                    SearchCarBean firstB = (SearchCarBean) DLFlowLayout.this.mData.get(0);
                    firstB.isChecked = true;
                    DLFlowLayout.this.setSelectColor((TextView) DLFlowLayout.this.getChildAt(0).findViewById(2131625551), firstB);
                    if (DLFlowLayout.this.mOnSelectListener != null) {
                        DLFlowLayout.this.mOnSelectListener.onSelect(firstB.position);
                    }
                    DLFlowLayout.this.hasSelectCount = 1;
                }
            }
        });
        chk.setTextSize(2, this.mTextSize);
        if (this.mTextColor != null) {
            chk.setTextColor(this.mTextColor);
        }
        chk.setText(bean.name);
        chk.setBackgroundResource(this.mBackground);
        if (bean.hasChecked()) {
            setChecked(chk);
            setSelectColor(chk, bean);
            this.hasSelectCount++;
        } else {
            setUnSelectColor(chk, bean);
            setUnChecked(chk);
        }
        measureView(itemView);
        int width = itemView.getMeasuredWidth();
        MarginLayoutParams lp = new MarginLayoutParams(width, -1);
        lp.setMargins(dip2px(getContext(), (float) (this.mHorizontalSpacing / 2)), dip2px(getContext(), (float) (this.mVerticalSpacing / 2)), dip2px(getContext(), (float) (this.mHorizontalSpacing / 2)), dip2px(getContext(), (float) (this.mVerticalSpacing / 2)));
        chk.setLayoutParams(lp);
        if (width > this.maxItemW) {
            this.maxItemW = width;
        }
        this.mChildList.add(itemView);
        addView(itemView);
        if (this.mStype != 0) {
            setUnRnable(itemView);
        }
    }

    public void setFlowClickListener(FlowClickListener l) {
        this.mFlowClickListener = l;
    }

    public void setCountPerLine(int c) {
        this.mCountPerLine = c;
    }

    public void setMar(int mar) {
        this.mar = mar;
    }

    public void setWidth(int w) {
        this.mWidth = w;
    }

    private void resetItemWidth(View itemView, int position) {
        TextView itemViewRoot = (TextView) itemView.findViewById(2131625551);
        if (this.mWidth == 0) {
            this.mWidth = FengUtil.getScreenWidth(this.mContext);
        }
        MarginLayoutParams lp = new MarginLayoutParams((this.mWidth - (this.mar * (this.mCountPerLine + 1))) / this.mCountPerLine, -1);
        if (position % this.mCountPerLine == this.mCountPerLine - 1) {
            lp.setMargins(this.mar, dip2px(getContext(), (float) (this.mVerticalSpacing / 2)), this.mar, dip2px(getContext(), (float) (this.mVerticalSpacing / 2)));
        } else {
            lp.setMargins(this.mar, dip2px(getContext(), (float) (this.mVerticalSpacing / 2)), 0, dip2px(getContext(), (float) (this.mVerticalSpacing / 2)));
        }
        itemViewRoot.setLayoutParams(lp);
    }

    public void measureView(View v) {
        if (v != null) {
            v.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        }
    }

    private void notifyAllItemView(int p) {
        for (int i = 0; i < getChildCount(); i++) {
            TextView chk = (TextView) getChildAt(i).findViewById(2131625551);
            SearchCarBean bean = (SearchCarBean) this.mData.get(i);
            int tag = ((Integer) chk.getTag()).intValue();
            if (p >= 0 && chk.isEnabled()) {
                if (tag == p) {
                    this.mSelectPosition = p;
                    setChecked(chk);
                    setSelectColor(chk, bean);
                } else if (this.mIsSingle) {
                    if (!bean.canClick) {
                        bean.isChecked = false;
                    } else if (bean.name.equals("自定义")) {
                        bean.isChecked = false;
                    } else {
                        bean.resetSublist();
                    }
                    setUnChecked(chk);
                    setUnSelectColor(chk, bean);
                }
            }
        }
    }

    public void setAllUnEnable() {
        for (View itemView : this.mChildList) {
            setUnRnable(itemView);
        }
    }

    public void setUnRnable(View view) {
        setUnEnabled((TextView) view.findViewById(2131625551));
    }

    private void setUnChecked(TextView view) {
        view.setSelected(false);
    }

    private void setChecked(TextView view) {
        view.setSelected(true);
    }

    private void setEnabled(TextView view) {
        view.setEnabled(true);
    }

    private void setUnEnabled(TextView view) {
        view.setEnabled(false);
    }

    public int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void setOnSelectListener(OnSelectListener l) {
        this.mOnSelectListener = l;
    }
}
