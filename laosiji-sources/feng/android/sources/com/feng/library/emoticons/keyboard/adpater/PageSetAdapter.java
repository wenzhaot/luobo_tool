package com.feng.library.emoticons.keyboard.adpater;

import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.feng.library.emoticons.keyboard.data.PageEntity;
import com.feng.library.emoticons.keyboard.data.PageSetEntity;
import com.feng.library.emoticons.keyboard.data.PageSetEntity.Builder;
import java.util.ArrayList;
import java.util.Iterator;

public class PageSetAdapter extends PagerAdapter {
    private final ArrayList<PageSetEntity> mPageSetEntityList = new ArrayList();

    public ArrayList<PageSetEntity> getPageSetEntityList() {
        return this.mPageSetEntityList;
    }

    public int getPageSetStartPosition(PageSetEntity pageSetEntity) {
        if (pageSetEntity == null || TextUtils.isEmpty(pageSetEntity.getUuid())) {
            return 0;
        }
        int startPosition = 0;
        int i = 0;
        while (i < this.mPageSetEntityList.size()) {
            if (i == this.mPageSetEntityList.size() - 1 && !pageSetEntity.getUuid().equals(((PageSetEntity) this.mPageSetEntityList.get(i)).getUuid())) {
                return 0;
            }
            if (pageSetEntity.getUuid().equals(((PageSetEntity) this.mPageSetEntityList.get(i)).getUuid())) {
                return startPosition;
            }
            startPosition += ((PageSetEntity) this.mPageSetEntityList.get(i)).getPageCount();
            i++;
        }
        return startPosition;
    }

    public void add(View view) {
        add(this.mPageSetEntityList.size(), view);
    }

    public void add(int index, View view) {
        this.mPageSetEntityList.add(index, new Builder().addPageEntity(new PageEntity(view)).setShowIndicator(false).build());
    }

    public void add(PageSetEntity pageSetEntity) {
        add(this.mPageSetEntityList.size(), pageSetEntity);
    }

    public void add(int index, PageSetEntity pageSetEntity) {
        if (pageSetEntity != null) {
            this.mPageSetEntityList.add(index, pageSetEntity);
        }
    }

    public PageSetEntity get(int position) {
        return (PageSetEntity) this.mPageSetEntityList.get(position);
    }

    public void remove(int position) {
        this.mPageSetEntityList.remove(position);
        notifyData();
    }

    public void notifyData() {
    }

    public PageEntity getPageEntity(int position) {
        Iterator it = this.mPageSetEntityList.iterator();
        while (it.hasNext()) {
            PageSetEntity pageSetEntity = (PageSetEntity) it.next();
            if (pageSetEntity.getPageCount() > position) {
                return (PageEntity) pageSetEntity.getPageEntityList().get(position);
            }
            position -= pageSetEntity.getPageCount();
        }
        return null;
    }

    public int getCount() {
        int count = 0;
        Iterator it = this.mPageSetEntityList.iterator();
        while (it.hasNext()) {
            count += ((PageSetEntity) it.next()).getPageCount();
        }
        return count;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = getPageEntity(position).instantiateItem(container, position, null);
        if (view == null) {
            return null;
        }
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
