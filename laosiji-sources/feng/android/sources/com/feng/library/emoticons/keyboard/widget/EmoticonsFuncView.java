package com.feng.library.emoticons.keyboard.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import com.feng.library.emoticons.keyboard.adpater.PageSetAdapter;
import com.feng.library.emoticons.keyboard.data.PageSetEntity;
import java.util.Iterator;

public class EmoticonsFuncView extends ViewPager {
    protected int mCurrentPagePosition;
    private OnEmoticonsPageViewListener mOnEmoticonsPageViewListener;
    protected PageSetAdapter mPageSetAdapter;

    public interface OnEmoticonsPageViewListener {
        void emoticonSetChanged(PageSetEntity pageSetEntity);

        void playBy(int i, int i2, PageSetEntity pageSetEntity);

        void playTo(int i, PageSetEntity pageSetEntity);
    }

    public EmoticonsFuncView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(PageSetAdapter adapter) {
        super.setAdapter(adapter);
        this.mPageSetAdapter = adapter;
        setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                EmoticonsFuncView.this.checkPageChange(position);
                EmoticonsFuncView.this.mCurrentPagePosition = position;
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        if (this.mOnEmoticonsPageViewListener != null && !this.mPageSetAdapter.getPageSetEntityList().isEmpty()) {
            PageSetEntity pageSetEntity = (PageSetEntity) this.mPageSetAdapter.getPageSetEntityList().get(0);
            this.mOnEmoticonsPageViewListener.playTo(0, pageSetEntity);
            this.mOnEmoticonsPageViewListener.emoticonSetChanged(pageSetEntity);
        }
    }

    public void setCurrentPageSet(PageSetEntity pageSetEntity) {
        if (this.mPageSetAdapter != null && this.mPageSetAdapter.getCount() > 0) {
            setCurrentItem(this.mPageSetAdapter.getPageSetStartPosition(pageSetEntity));
        }
    }

    public void checkPageChange(int position) {
        if (this.mPageSetAdapter != null) {
            int end = 0;
            Iterator it = this.mPageSetAdapter.getPageSetEntityList().iterator();
            while (it.hasNext()) {
                PageSetEntity pageSetEntity = (PageSetEntity) it.next();
                int size = pageSetEntity.getPageCount();
                if (end + size > position) {
                    boolean isEmoticonSetChanged = true;
                    if (this.mCurrentPagePosition - end >= size) {
                        if (this.mOnEmoticonsPageViewListener != null) {
                            this.mOnEmoticonsPageViewListener.playTo(position - end, pageSetEntity);
                        }
                    } else if (this.mCurrentPagePosition - end >= 0) {
                        if (this.mOnEmoticonsPageViewListener != null) {
                            this.mOnEmoticonsPageViewListener.playBy(this.mCurrentPagePosition - end, position - end, pageSetEntity);
                        }
                        isEmoticonSetChanged = false;
                    } else if (this.mOnEmoticonsPageViewListener != null) {
                        this.mOnEmoticonsPageViewListener.playTo(0, pageSetEntity);
                    }
                    if (isEmoticonSetChanged && this.mOnEmoticonsPageViewListener != null) {
                        this.mOnEmoticonsPageViewListener.emoticonSetChanged(pageSetEntity);
                        return;
                    }
                    return;
                }
                end += size;
            }
        }
    }

    public void setOnIndicatorListener(OnEmoticonsPageViewListener listener) {
        this.mOnEmoticonsPageViewListener = listener;
    }
}
