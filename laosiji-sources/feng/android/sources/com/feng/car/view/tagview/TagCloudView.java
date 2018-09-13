package com.feng.car.view.tagview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.search.SearchItem;
import com.feng.car.utils.FengConstant;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class TagCloudView extends ViewGroup {
    private static final boolean DEFAULT_CAN_TAG_CLICK = true;
    private static final String DEFAULT_END_TEXT_STRING = " … ";
    private static final int DEFAULT_RIGHT_IMAGE = 2130838045;
    private static final boolean DEFAULT_SHOW_END_TEXT = true;
    private static final boolean DEFAULT_SHOW_RIGHT_IMAGE = true;
    private static final boolean DEFAULT_SINGLE_LINE = false;
    private static final int DEFAULT_TAG_RESID = 2130903281;
    private static final int DEFAULT_TEXT_BACKGROUND = 2130837840;
    private static final int DEFAULT_TEXT_BORDER_HORIZONTAL = 8;
    private static final int DEFAULT_TEXT_BORDER_VERTICAL = 5;
    private static final int DEFAULT_TEXT_COLOR = -1;
    private static final int DEFAULT_TEXT_SIZE = 14;
    private static final int DEFAULT_VIEW_BORDER = 6;
    private static final String TAG = TagCloudView.class.getSimpleName();
    private static final int TYPE_TEXT_NORMAL = 1;
    private TextView endText;
    private int endTextHeight;
    private String endTextString;
    private int endTextWidth;
    private int imageHeight;
    private ImageView imageView;
    private int imageWidth;
    private int mBackground;
    private boolean mCanTagClick;
    private int mCurrentLine;
    private LayoutInflater mInflater;
    private OnShowMoreListener mMoreListener;
    private int mRightImageResId;
    private boolean mShowEndText;
    private int mShowMaxLines;
    private boolean mShowMore;
    private boolean mShowRightImage;
    private boolean mSingleLine;
    private int mTagBorderHor;
    private int mTagBorderVer;
    private int mTagColor;
    private int mTagResId;
    private float mTagSize;
    private int mViewBorder;
    private OnTagClickListener onTagClickListener;
    private int sizeHeight;
    private int sizeWidth;
    private List<CircleInfo> tags;

    public interface OnShowMoreListener {
        void onShowMore(boolean z);
    }

    public TagCloudView(Context context) {
        this(context, null);
    }

    public TagCloudView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagCloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.imageView = null;
        this.endTextWidth = 0;
        this.endTextHeight = 0;
        this.endText = null;
        this.mShowMaxLines = 0;
        this.mCurrentLine = 1;
        this.mShowMore = false;
        this.mInflater = LayoutInflater.from(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TagCloudView, defStyleAttr, defStyleAttr);
        this.mTagSize = (float) a.getInteger(3, 14);
        this.mShowMaxLines = a.getInteger(14, 0);
        this.mTagColor = a.getColor(1, -1);
        this.mBackground = a.getResourceId(0, DEFAULT_TEXT_BACKGROUND);
        this.mViewBorder = a.getDimensionPixelSize(2, 6);
        this.mTagBorderHor = a.getDimensionPixelSize(6, 8);
        this.mTagBorderVer = a.getDimensionPixelSize(5, 5);
        this.mCanTagClick = a.getBoolean(12, true);
        this.mRightImageResId = a.getResourceId(11, DEFAULT_RIGHT_IMAGE);
        this.mSingleLine = a.getBoolean(7, false);
        this.mShowRightImage = a.getBoolean(9, true);
        this.mShowEndText = a.getBoolean(8, true);
        this.endTextString = a.getString(10);
        this.mTagResId = a.getResourceId(13, DEFAULT_TAG_RESID);
        a.recycle();
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return (!this.mCanTagClick && this.mSingleLine) || super.onInterceptTouchEvent(ev);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @SuppressLint({"DrawAllocation"})
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        this.sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int totalHeight = this.mTagBorderVer;
        if (this.mSingleLine) {
            totalHeight = getSingleTotalHeight(0, totalHeight);
        } else {
            totalHeight = getMultiTotalHeight(0, totalHeight);
        }
        if (this.mShowMaxLines != 0) {
            setMeasuredDimension(this.sizeWidth, totalHeight);
            return;
        }
        int i = this.sizeWidth;
        if (heightMode == FengConstant.MAXVIDEOSIZE) {
            totalHeight = this.sizeHeight;
        }
        setMeasuredDimension(i, totalHeight);
    }

    private void initSingleLineView(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mSingleLine) {
            if (this.mShowRightImage) {
                this.imageView = new ImageView(getContext());
                this.imageView.setImageResource(this.mRightImageResId);
                this.imageView.setLayoutParams(new LayoutParams(-2, -2));
                this.imageView.setScaleType(ScaleType.CENTER_INSIDE);
                measureChild(this.imageView, widthMeasureSpec, heightMeasureSpec);
                this.imageWidth = this.imageView.getMeasuredWidth();
                this.imageHeight = this.imageView.getMeasuredHeight();
                addView(this.imageView);
            }
            if (this.mShowEndText) {
                this.endText = (TextView) this.mInflater.inflate(this.mTagResId, null);
                if (this.mTagResId == DEFAULT_TAG_RESID) {
                    this.endText.setTextColor(this.mTagColor);
                }
                this.endText.setLayoutParams(new LayoutParams(-2, -2));
                TextView textView = this.endText;
                CharSequence charSequence = (this.endTextString == null || this.endTextString.equals("")) ? DEFAULT_END_TEXT_STRING : this.endTextString;
                textView.setText(charSequence);
                measureChild(this.endText, widthMeasureSpec, heightMeasureSpec);
                this.endTextHeight = this.endText.getMeasuredHeight();
                this.endTextWidth = this.endText.getMeasuredWidth();
                addView(this.endText);
                this.endText.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View view) {
                        if (TagCloudView.this.onTagClickListener != null) {
                            TagCloudView.this.onTagClickListener.onTagClick(TagCloudView.this.endText, null, -1);
                        }
                    }
                });
            }
        }
    }

    private int getSingleTotalHeight(int totalWidth, int totalHeight) {
        totalWidth += this.mViewBorder;
        if (getTextTotalWidth() < this.sizeWidth - this.imageWidth) {
            this.endText = null;
            this.endTextWidth = 0;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (i == 0) {
                totalWidth += childWidth;
                totalHeight = childHeight + this.mViewBorder;
            } else {
                totalWidth += this.mTagBorderHor + childWidth;
            }
            if (child.getTag() != null && Integer.parseInt(child.getTag().toString()) == 1) {
                if (((((this.mTagBorderHor + totalWidth) + this.mViewBorder) + this.mViewBorder) + this.endTextWidth) + this.imageWidth >= this.sizeWidth) {
                    totalWidth -= this.mViewBorder + childWidth;
                    break;
                }
                child.layout((totalWidth - childWidth) + this.mTagBorderVer, totalHeight - childHeight, this.mTagBorderVer + totalWidth, totalHeight);
            }
        }
        if (this.endText != null) {
            this.endText.layout((this.mViewBorder + totalWidth) + this.mTagBorderVer, totalHeight - this.endTextHeight, ((this.mViewBorder + totalWidth) + this.mTagBorderVer) + this.endTextWidth, totalHeight);
        }
        totalHeight += this.mViewBorder;
        if (this.imageView != null) {
            this.imageView.layout((this.sizeWidth - this.imageWidth) - this.mViewBorder, (totalHeight - this.imageHeight) / 2, this.sizeWidth - this.mViewBorder, ((totalHeight - this.imageHeight) / 2) + this.imageHeight);
        }
        return totalHeight;
    }

    private int getMultiTotalHeight(int totalWidth, int totalHeight) {
        if (this.mShowMaxLines > 0) {
            this.mShowMore = false;
            this.mCurrentLine = 1;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            totalWidth += this.mViewBorder + childWidth;
            if (i == 0) {
                totalHeight = childHeight + this.mViewBorder;
            }
            if ((this.mTagBorderHor + totalWidth) + this.mViewBorder > this.sizeWidth) {
                totalWidth = this.mViewBorder;
                if (this.mShowMaxLines != 0) {
                    if (this.mCurrentLine >= this.mShowMaxLines) {
                        this.mShowMore = true;
                        break;
                    }
                    this.mCurrentLine++;
                }
                totalHeight += this.mTagBorderVer + childHeight;
                child.layout(this.mTagBorderHor + totalWidth, totalHeight - childHeight, (totalWidth + childWidth) + this.mTagBorderHor, totalHeight);
                totalWidth += childWidth;
            } else {
                child.layout((totalWidth - childWidth) + this.mTagBorderHor, totalHeight - childHeight, this.mTagBorderHor + totalWidth, totalHeight);
            }
        }
        if (this.mMoreListener != null) {
            this.mMoreListener.onShowMore(this.mShowMore);
        }
        return this.mViewBorder + totalHeight;
    }

    private int getTextTotalWidth() {
        if (getChildCount() == 0) {
            return 0;
        }
        int totalChildWidth = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getTag() != null && ((Integer) child.getTag()).intValue() == 1) {
                totalChildWidth += child.getMeasuredWidth() + this.mViewBorder;
            }
        }
        return (this.mTagBorderHor * 2) + totalChildWidth;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    public void setTags(List<CircleInfo> tagList) {
        this.mCurrentLine = 1;
        this.mShowMore = false;
        this.tags = tagList;
        removeAllViews();
        if (this.tags != null && this.tags.size() > 0) {
            for (int i = 0; i < this.tags.size(); i++) {
                final TextView tagView = (TextView) this.mInflater.inflate(this.mTagResId, null);
                if (this.mTagResId == DEFAULT_TAG_RESID) {
                    tagView.setTextColor(this.mTagColor);
                }
                tagView.setLayoutParams(new LayoutParams(-2, -2));
                tagView.setText(((CircleInfo) this.tags.get(i)).name);
                tagView.setTag(Integer.valueOf(1));
                final int post = i;
                tagView.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (TagCloudView.this.onTagClickListener != null) {
                            TagCloudView.this.onTagClickListener.onTagClick(tagView, (CircleInfo) TagCloudView.this.tags.get(post), post);
                        }
                    }
                });
                addView(tagView);
            }
        }
        postInvalidate();
    }

    public void setRecodeTags(List<SearchItem> tagList) {
        this.mCurrentLine = 1;
        this.mShowMore = false;
        removeAllViews();
        if (tagList != null && tagList.size() > 0) {
            for (int i = 0; i < tagList.size(); i++) {
                final TextView tagView = (TextView) this.mInflater.inflate(this.mTagResId, null);
                if (this.mTagResId == DEFAULT_TAG_RESID) {
                    tagView.setTextColor(this.mTagColor);
                }
                tagView.setLayoutParams(new LayoutParams(-2, -2));
                tagView.setText(((SearchItem) tagList.get(i)).content);
                tagView.setTag(Integer.valueOf(1));
                final int post = i;
                tagView.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (TagCloudView.this.onTagClickListener != null) {
                            TagCloudView.this.onTagClickListener.onTagClick(tagView, null, post);
                        }
                    }
                });
                addView(tagView);
            }
        }
        postInvalidate();
    }

    public void singleLine(boolean mSingleLine) {
        this.mSingleLine = mSingleLine;
        if (this.tags != null) {
            setTags(this.tags);
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public void setShowMoreListener(OnShowMoreListener onShowMoreListener) {
        this.mMoreListener = onShowMoreListener;
    }
}
