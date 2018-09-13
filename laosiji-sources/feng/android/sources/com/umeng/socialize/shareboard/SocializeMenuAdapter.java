package com.umeng.socialize.shareboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.umeng.socialize.shareboard.widgets.SocializePagerAdapter;
import java.util.ArrayList;
import java.util.List;

class SocializeMenuAdapter extends SocializePagerAdapter {
    private Context mContext;
    private ShareBoardMenuHelper mMenuHelper;
    private List<SnsPlatform[][]> mPageData;

    public SocializeMenuAdapter(Context context, ShareBoardConfig shareBoardConfig) {
        this(context, shareBoardConfig, null);
    }

    public SocializeMenuAdapter(Context context, ShareBoardConfig shareBoardConfig, List<SnsPlatform> list) {
        this.mPageData = new ArrayList();
        this.mContext = context;
        this.mMenuHelper = new ShareBoardMenuHelper(shareBoardConfig);
        setData(list);
    }

    public void setData(List<SnsPlatform> list) {
        this.mPageData.clear();
        if (list != null) {
            this.mPageData.addAll(this.mMenuHelper.formatPageData(list));
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mPageData == null ? 0 : this.mPageData.size();
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View createPageLayout = this.mMenuHelper.createPageLayout(this.mContext, (SnsPlatform[][]) this.mPageData.get(i));
        viewGroup.addView(createPageLayout);
        return createPageLayout;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}
