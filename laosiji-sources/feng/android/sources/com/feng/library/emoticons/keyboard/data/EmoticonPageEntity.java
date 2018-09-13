package com.feng.library.emoticons.keyboard.data;

import android.view.View;
import android.view.ViewGroup;
import com.feng.library.emoticons.keyboard.widget.EmoticonPageView;
import java.util.List;

public class EmoticonPageEntity<T> extends PageEntity<EmoticonPageEntity> {
    private DelBtnStatus mDelBtnStatus;
    private List<T> mEmoticonList;
    private int mLine;
    private int mRow;

    public enum DelBtnStatus {
        GONE,
        FOLLOW,
        LAST;

        public boolean isShow() {
            return !GONE.toString().equals(toString());
        }
    }

    public List<T> getEmoticonList() {
        return this.mEmoticonList;
    }

    public void setEmoticonList(List<T> emoticonList) {
        this.mEmoticonList = emoticonList;
    }

    public int getLine() {
        return this.mLine;
    }

    public void setLine(int line) {
        this.mLine = line;
    }

    public int getRow() {
        return this.mRow;
    }

    public void setRow(int row) {
        this.mRow = row;
    }

    public DelBtnStatus getDelBtnStatus() {
        return this.mDelBtnStatus;
    }

    public void setDelBtnStatus(DelBtnStatus delBtnStatus) {
        this.mDelBtnStatus = delBtnStatus;
    }

    public View instantiateItem(ViewGroup container, int position, EmoticonPageEntity pageEntity) {
        if (this.mPageViewInstantiateListener != null) {
            return this.mPageViewInstantiateListener.instantiateItem(container, position, this);
        }
        if (getRootView() == null) {
            EmoticonPageView pageView = new EmoticonPageView(container.getContext());
            pageView.setNumColumns(this.mRow);
            setRootView(pageView);
        }
        return getRootView();
    }
}
