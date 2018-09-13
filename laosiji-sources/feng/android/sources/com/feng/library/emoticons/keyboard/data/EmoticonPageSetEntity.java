package com.feng.library.emoticons.keyboard.data;

import com.feng.library.emoticons.keyboard.data.EmoticonPageEntity.DelBtnStatus;
import com.feng.library.emoticons.keyboard.interfaces.PageViewInstantiateListener;
import java.util.ArrayList;

public class EmoticonPageSetEntity<T> extends PageSetEntity<EmoticonPageEntity> {
    final DelBtnStatus mDelBtnStatus;
    final ArrayList<T> mEmoticonList;
    final int mLine;
    final int mRow;

    public static class Builder<T> extends com.feng.library.emoticons.keyboard.data.PageSetEntity.Builder {
        protected DelBtnStatus delBtnStatus = DelBtnStatus.GONE;
        protected ArrayList<T> emoticonList;
        protected int line;
        protected PageViewInstantiateListener pageViewInstantiateListener;
        protected int row;

        public Builder setLine(int line) {
            this.line = line;
            return this;
        }

        public Builder setRow(int row) {
            this.row = row;
            return this;
        }

        public Builder setShowDelBtn(DelBtnStatus showDelBtn) {
            this.delBtnStatus = showDelBtn;
            return this;
        }

        public Builder setEmoticonList(ArrayList<T> emoticonList) {
            this.emoticonList = emoticonList;
            return this;
        }

        public Builder setIPageViewInstantiateItem(PageViewInstantiateListener pageViewInstantiateListener) {
            this.pageViewInstantiateListener = pageViewInstantiateListener;
            return this;
        }

        public Builder setShowIndicator(boolean showIndicator) {
            this.isShowIndicator = showIndicator;
            return this;
        }

        public Builder setIconUri(String iconUri) {
            this.iconUri = iconUri;
            return this;
        }

        public Builder setSetName(String setName) {
            this.setName = setName;
            return this;
        }

        public EmoticonPageSetEntity<T> build() {
            int end;
            int emoticonSetSum = this.emoticonList.size();
            int everyPageMaxSum = (this.row * this.line) - (this.delBtnStatus.isShow() ? 1 : 0);
            this.pageCount = (int) Math.ceil(((double) this.emoticonList.size()) / ((double) everyPageMaxSum));
            int start = 0;
            if (everyPageMaxSum > emoticonSetSum) {
                end = emoticonSetSum;
            } else {
                end = everyPageMaxSum;
            }
            if (!this.pageEntityList.isEmpty()) {
                this.pageEntityList.clear();
            }
            for (int i = 0; i < this.pageCount; i++) {
                EmoticonPageEntity emoticonPageEntity = new EmoticonPageEntity();
                emoticonPageEntity.setLine(this.line);
                emoticonPageEntity.setRow(this.row);
                emoticonPageEntity.setDelBtnStatus(this.delBtnStatus);
                emoticonPageEntity.setEmoticonList(this.emoticonList.subList(start, end));
                emoticonPageEntity.setIPageViewInstantiateItem(this.pageViewInstantiateListener);
                this.pageEntityList.add(emoticonPageEntity);
                start = everyPageMaxSum + (i * everyPageMaxSum);
                end = everyPageMaxSum + ((i + 1) * everyPageMaxSum);
                if (end >= emoticonSetSum) {
                    end = emoticonSetSum;
                }
            }
            return new EmoticonPageSetEntity(this);
        }
    }

    public EmoticonPageSetEntity(Builder builder) {
        super(builder);
        this.mLine = builder.line;
        this.mRow = builder.row;
        this.mDelBtnStatus = builder.delBtnStatus;
        this.mEmoticonList = builder.emoticonList;
    }

    public int getLine() {
        return this.mLine;
    }

    public int getRow() {
        return this.mRow;
    }

    public DelBtnStatus getDelBtnStatus() {
        return this.mDelBtnStatus;
    }

    public ArrayList<T> getEmoticonList() {
        return this.mEmoticonList;
    }
}
