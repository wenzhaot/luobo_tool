package com.feng.library.emoticons.keyboard.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.feng.library.R;
import com.feng.library.emoticons.keyboard.data.PageSetEntity;
import com.feng.library.emoticons.keyboard.utils.imageloader.ImageLoader;
import com.nineoldandroids.view.ViewHelper;
import java.io.IOException;
import java.util.ArrayList;

public class EmoticonsToolBarView extends RelativeLayout {
    protected HorizontalScrollView hsv_toolbar;
    protected LinearLayout ly_tool;
    protected int mBtnWidth;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected OnToolBarItemClickListener mItemClickListeners;
    protected ArrayList<View> mToolBtnList;

    public interface OnToolBarItemClickListener {
        void onToolBarItemClick(PageSetEntity pageSetEntity);
    }

    public EmoticonsToolBarView(Context context) {
        this(context, null);
    }

    public EmoticonsToolBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mToolBtnList = new ArrayList();
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mInflater.inflate(R.layout.emoji_view_emoticonstoolbar, this);
        this.mContext = context;
        this.mBtnWidth = (int) context.getResources().getDimension(R.dimen.bar_tool_btn_width);
        this.hsv_toolbar = (HorizontalScrollView) findViewById(R.id.hsv_toolbar);
        this.ly_tool = (LinearLayout) findViewById(R.id.ly_tool);
    }

    public void addView(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() > 3) {
            throw new IllegalArgumentException("can host only two direct child");
        }
    }

    public void addFixedToolItemView(View view, boolean isRight) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -1);
        RelativeLayout.LayoutParams hsvParams = (RelativeLayout.LayoutParams) this.hsv_toolbar.getLayoutParams();
        if (view.getId() <= 0) {
            view.setId(isRight ? R.id.id_toolbar_right : R.id.id_toolbar_left);
        }
        if (isRight) {
            params.addRule(11);
            hsvParams.addRule(0, view.getId());
        } else {
            params.addRule(9);
            hsvParams.addRule(1, view.getId());
        }
        addView(view, params);
        this.hsv_toolbar.setLayoutParams(hsvParams);
    }

    protected View getCommonItemToolBtn() {
        return this.mInflater == null ? null : this.mInflater.inflate(R.layout.emoji_item_toolbtn, null);
    }

    protected void initItemToolBtn(View toolBtnView, int rec, final PageSetEntity pageSetEntity, OnClickListener onClickListener) {
        ImageView iv_icon = (ImageView) toolBtnView.findViewById(R.id.iv_icon);
        if (rec > 0) {
            iv_icon.setImageResource(rec);
        }
        iv_icon.setLayoutParams(new LinearLayout.LayoutParams(this.mBtnWidth, -1));
        if (pageSetEntity != null) {
            iv_icon.setTag(R.id.id_tag_pageset, pageSetEntity);
            try {
                ImageLoader.getInstance(this.mContext).displayImage(pageSetEntity.getIconUri(), iv_icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (onClickListener == null) {
            onClickListener = new OnClickListener() {
                public void onClick(View view) {
                    if (EmoticonsToolBarView.this.mItemClickListeners != null && pageSetEntity != null) {
                        EmoticonsToolBarView.this.mItemClickListeners.onToolBarItemClick(pageSetEntity);
                    }
                }
            };
        }
        toolBtnView.setOnClickListener(onClickListener);
    }

    protected View getToolBgBtn(View parentView) {
        return parentView.findViewById(R.id.iv_icon);
    }

    public void addFixedToolItemView(boolean isRight, int rec, PageSetEntity pageSetEntity, OnClickListener onClickListener) {
        View toolBtnView = getCommonItemToolBtn();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -1);
        RelativeLayout.LayoutParams hsvParams = (RelativeLayout.LayoutParams) this.hsv_toolbar.getLayoutParams();
        if (toolBtnView.getId() <= 0) {
            toolBtnView.setId(isRight ? R.id.id_toolbar_right : R.id.id_toolbar_left);
        }
        if (isRight) {
            params.addRule(11);
            hsvParams.addRule(0, toolBtnView.getId());
        } else {
            params.addRule(9);
            hsvParams.addRule(1, toolBtnView.getId());
        }
        addView(toolBtnView, params);
        this.hsv_toolbar.setLayoutParams(hsvParams);
        initItemToolBtn(toolBtnView, rec, pageSetEntity, onClickListener);
    }

    public void addToolItemView(PageSetEntity pageSetEntity) {
        addToolItemView(0, pageSetEntity, null);
    }

    public void addToolItemView(int rec, OnClickListener onClickListener) {
        addToolItemView(rec, null, onClickListener);
    }

    public void addToolItemView(int rec, PageSetEntity pageSetEntity, OnClickListener onClickListener) {
        View toolBtnView = getCommonItemToolBtn();
        initItemToolBtn(toolBtnView, rec, pageSetEntity, onClickListener);
        this.ly_tool.addView(toolBtnView);
        this.mToolBtnList.add(getToolBgBtn(toolBtnView));
    }

    public void setToolBtnSelect(String uuid) {
        if (!TextUtils.isEmpty(uuid)) {
            int select = 0;
            for (int i = 0; i < this.mToolBtnList.size(); i++) {
                Object object = ((View) this.mToolBtnList.get(i)).getTag(R.id.id_tag_pageset);
                if (object != null && (object instanceof PageSetEntity) && uuid.equals(((PageSetEntity) object).getUuid())) {
                    ((View) this.mToolBtnList.get(i)).setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.toolbar_btn_select));
                    select = i;
                } else {
                    ((View) this.mToolBtnList.get(i)).setBackgroundResource(R.drawable.emoji_btn_toolbtn_bg);
                }
            }
            scrollToBtnPosition(select);
        }
    }

    protected void scrollToBtnPosition(final int position) {
        if (position < this.ly_tool.getChildCount()) {
            this.hsv_toolbar.post(new Runnable() {
                public void run() {
                    int mScrollX = EmoticonsToolBarView.this.hsv_toolbar.getScrollX();
                    int childX = (int) ViewHelper.getX(EmoticonsToolBarView.this.ly_tool.getChildAt(position));
                    if (childX < mScrollX) {
                        EmoticonsToolBarView.this.hsv_toolbar.scrollTo(childX, 0);
                        return;
                    }
                    int childRight = childX + EmoticonsToolBarView.this.ly_tool.getChildAt(position).getWidth();
                    int scrollRight = mScrollX + EmoticonsToolBarView.this.hsv_toolbar.getWidth();
                    if (childRight > scrollRight) {
                        EmoticonsToolBarView.this.hsv_toolbar.scrollTo(childRight - scrollRight, 0);
                    }
                }
            });
        }
    }

    public void setBtnWidth(int width) {
        this.mBtnWidth = width;
    }

    public void setOnToolBarItemClickListener(OnToolBarItemClickListener listener) {
        this.mItemClickListeners = listener;
    }
}
