package com.feng.library.emoticons.keyboard.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.RelativeLayout;
import com.feng.library.R;

public class EmoticonPageView extends RelativeLayout {
    private GridView mGvEmotion;

    public GridView getEmoticonsGridView() {
        return this.mGvEmotion;
    }

    public EmoticonPageView(Context context) {
        this(context, null);
    }

    public EmoticonPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mGvEmotion = (GridView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.emoji_item_emoticonpage, this).findViewById(R.id.gv_emotion);
        if (VERSION.SDK_INT > 11) {
            this.mGvEmotion.setMotionEventSplittingEnabled(false);
        }
        this.mGvEmotion.setStretchMode(2);
        this.mGvEmotion.setCacheColorHint(0);
        this.mGvEmotion.setSelector(new ColorDrawable(0));
        this.mGvEmotion.setVerticalScrollBarEnabled(false);
    }

    public void setNumColumns(int row) {
        this.mGvEmotion.setNumColumns(row);
    }
}
