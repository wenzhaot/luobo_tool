package com.feng.library.emoticons.keyboard.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

public class PageSetEntity<T extends PageEntity> implements Serializable {
    protected final String mIconUri;
    protected final boolean mIsShowIndicator;
    protected final int mPageCount;
    protected final LinkedList<T> mPageEntityList;
    protected final String mSetName;
    protected final String uuid = UUID.randomUUID().toString();

    public static class Builder<T extends PageEntity> {
        protected String iconUri;
        protected boolean isShowIndicator = true;
        protected int pageCount;
        protected LinkedList<T> pageEntityList = new LinkedList();
        protected String setName;

        public Builder setPageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder setShowIndicator(boolean showIndicator) {
            this.isShowIndicator = showIndicator;
            return this;
        }

        public Builder setPageEntityList(LinkedList<T> pageEntityList) {
            this.pageEntityList = pageEntityList;
            return this;
        }

        public Builder addPageEntity(T pageEntityt) {
            this.pageEntityList.add(pageEntityt);
            return this;
        }

        public Builder setIconUri(String iconUri) {
            this.iconUri = iconUri;
            return this;
        }

        public Builder setIconUri(int iconUri) {
            this.iconUri = "" + iconUri;
            return this;
        }

        public Builder setSetName(String setName) {
            this.setName = setName;
            return this;
        }

        public PageSetEntity<T> build() {
            return new PageSetEntity(this);
        }
    }

    public PageSetEntity(Builder builder) {
        this.mPageCount = builder.pageCount;
        this.mIsShowIndicator = builder.isShowIndicator;
        this.mPageEntityList = builder.pageEntityList;
        this.mIconUri = builder.iconUri;
        this.mSetName = builder.setName;
    }

    public String getIconUri() {
        return this.mIconUri;
    }

    public int getPageCount() {
        return this.mPageEntityList == null ? 0 : this.mPageEntityList.size();
    }

    public LinkedList<T> getPageEntityList() {
        return this.mPageEntityList;
    }

    public String getUuid() {
        return this.uuid;
    }

    public boolean isShowIndicator() {
        return this.mIsShowIndicator;
    }
}
