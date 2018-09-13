package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.tagview.TagCloudView;

public class ActivityNewSearchBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout llBubbleCityContainer;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final RelativeLayout rlRecordDel;
    public final RelativeLayout rlTab;
    public final RecyclerView rvAssociateRecord;
    public final TabLayout tbMenu;
    public final TagCloudView tgRecordView;
    public final TextView tvAgencyBubbleLocationCity;
    public final TextView tvRecordDel;
    public final View vTabLine;
    public final ViewPager vpSearch;

    static {
        sViewsWithIds.put(2131624428, 1);
        sViewsWithIds.put(2131624429, 2);
        sViewsWithIds.put(2131624430, 3);
        sViewsWithIds.put(2131624431, 4);
        sViewsWithIds.put(2131624432, 5);
        sViewsWithIds.put(2131624433, 6);
        sViewsWithIds.put(2131624434, 7);
        sViewsWithIds.put(2131624435, 8);
        sViewsWithIds.put(2131624436, 9);
        sViewsWithIds.put(2131624437, 10);
    }

    public ActivityNewSearchBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.llBubbleCityContainer = (LinearLayout) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlRecordDel = (RelativeLayout) bindings[7];
        this.rlTab = (RelativeLayout) bindings[1];
        this.rvAssociateRecord = (RecyclerView) bindings[10];
        this.tbMenu = (TabLayout) bindings[2];
        this.tgRecordView = (TagCloudView) bindings[9];
        this.tvAgencyBubbleLocationCity = (TextView) bindings[6];
        this.tvRecordDel = (TextView) bindings[8];
        this.vTabLine = (View) bindings[3];
        this.vpSearch = (ViewPager) bindings[4];
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
            case 72:
                setUnwanted((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUnwanted(Integer Unwanted) {
        this.mUnwanted = Unwanted;
    }

    public Integer getUnwanted() {
        return this.mUnwanted;
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

    public static ActivityNewSearchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityNewSearchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityNewSearchBinding) DataBindingUtil.inflate(inflater, 2130903102, root, attachToRoot, bindingComponent);
    }

    public static ActivityNewSearchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityNewSearchBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903102, null, false), bindingComponent);
    }

    public static ActivityNewSearchBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityNewSearchBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_new_search_0".equals(view.getTag())) {
            return new ActivityNewSearchBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
