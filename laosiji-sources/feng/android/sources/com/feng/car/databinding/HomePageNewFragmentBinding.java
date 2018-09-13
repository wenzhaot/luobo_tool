package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.view.recyclerview.EmptyView;

public class HomePageNewFragmentBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final EmptyView emptyView;
    public final ImageView ivCircleSortVerticalShadow;
    public final ImageView ivMessage;
    private long mDirtyFlags = -1;
    private CircleInfo mInfo;
    private final RelativeLayout mboundView0;
    public final RelativeLayout rlTab;
    public final RelativeLayout rlTitleBar;
    public final TabLayout tabLayout;
    public final TextView tvMessageCommentNum;
    public final TextView tvSearch;
    public final TextView tvUpdataApp;
    public final View vTabLine;
    public final ViewPager viewPage;

    static {
        sViewsWithIds.put(2131624215, 1);
        sViewsWithIds.put(2131624545, 2);
        sViewsWithIds.put(2131624232, 3);
        sViewsWithIds.put(2131624233, 4);
        sViewsWithIds.put(2131625102, 5);
        sViewsWithIds.put(2131624428, 6);
        sViewsWithIds.put(2131624549, 7);
        sViewsWithIds.put(2131625103, 8);
        sViewsWithIds.put(2131624430, 9);
        sViewsWithIds.put(2131624550, 10);
        sViewsWithIds.put(2131625104, 11);
    }

    public HomePageNewFragmentBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.emptyView = (EmptyView) bindings[11];
        this.ivCircleSortVerticalShadow = (ImageView) bindings[8];
        this.ivMessage = (ImageView) bindings[3];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlTab = (RelativeLayout) bindings[6];
        this.rlTitleBar = (RelativeLayout) bindings[1];
        this.tabLayout = (TabLayout) bindings[7];
        this.tvMessageCommentNum = (TextView) bindings[4];
        this.tvSearch = (TextView) bindings[2];
        this.tvUpdataApp = (TextView) bindings[5];
        this.vTabLine = (View) bindings[9];
        this.viewPage = (ViewPager) bindings[10];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2;
        }
        requestRebind();
    }

    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return false;
        }
    }

    public boolean setVariable(int variableId, Object variable) {
        switch (variableId) {
            case 28:
                setInfo((CircleInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CircleInfo Info) {
        this.mInfo = Info;
    }

    public CircleInfo getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        synchronized (this) {
            long dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
    }

    public static HomePageNewFragmentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static HomePageNewFragmentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (HomePageNewFragmentBinding) DataBindingUtil.inflate(inflater, 2130903258, root, attachToRoot, bindingComponent);
    }

    public static HomePageNewFragmentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static HomePageNewFragmentBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903258, null, false), bindingComponent);
    }

    public static HomePageNewFragmentBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HomePageNewFragmentBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/home_page_new_fragment_0".equals(view.getTag())) {
            return new HomePageNewFragmentBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
