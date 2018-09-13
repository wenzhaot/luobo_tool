package com.feng.library.emoticons.keyboard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.feng.library.emoticons.keyboard.SimpleCommonUtils;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonFilter;
import java.util.ArrayList;
import java.util.List;

public class UserDefEmoticonsEditText extends EditText {
    private List<EmoticonFilter> mFilterList;

    public UserDefEmoticonsEditText(Context context) {
        super(context);
        SimpleCommonUtils.initEmoticonsEditText(this);
    }

    public UserDefEmoticonsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        SimpleCommonUtils.initEmoticonsEditText(this);
    }

    public UserDefEmoticonsEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SimpleCommonUtils.initEmoticonsEditText(this);
    }

    protected final void onTextChanged(CharSequence arg0, int start, int lengthBefore, int after) {
        super.onTextChanged(arg0, start, lengthBefore, after);
        if (this.mFilterList != null) {
            for (EmoticonFilter emoticonFilter : this.mFilterList) {
                emoticonFilter.filter(this, arg0, start, lengthBefore, after);
            }
        }
    }

    public void addEmoticonFilter(EmoticonFilter emoticonFilter) {
        if (this.mFilterList == null) {
            this.mFilterList = new ArrayList();
        }
        this.mFilterList.add(emoticonFilter);
    }

    public boolean filterIsNull() {
        if (this.mFilterList == null) {
            return true;
        }
        return false;
    }
}
