package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.user.UserInfo;

public class NewSearchAllHeaderBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout llTopic;
    private CarSeriesInfo mCarSeriesInfo;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final LinearLayout mboundView0;
    public final RecyclerView reTopic;
    public final RelativeLayout rlHotProgram;
    public final RelativeLayout rlRelatedUser;
    public final RecyclerView rvProgram;
    public final RecyclerView rvUserPhoto;
    public final TextView tvCorrelation;
    public final TextView tvFindMoreUser;
    public final TextView tvProgram;
    public final TextView tvSeeAll;
    public final View vLineUser;

    static {
        sViewsWithIds.put(2131625304, 1);
        sViewsWithIds.put(2131625305, 2);
        sViewsWithIds.put(2131625306, 3);
        sViewsWithIds.put(2131625307, 4);
        sViewsWithIds.put(2131625308, 5);
        sViewsWithIds.put(2131625309, 6);
        sViewsWithIds.put(2131625310, 7);
        sViewsWithIds.put(2131625311, 8);
        sViewsWithIds.put(2131625312, 9);
        sViewsWithIds.put(2131625313, 10);
        sViewsWithIds.put(2131625314, 11);
    }

    public NewSearchAllHeaderBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.llTopic = (LinearLayout) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.reTopic = (RecyclerView) bindings[2];
        this.rlHotProgram = (RelativeLayout) bindings[9];
        this.rlRelatedUser = (RelativeLayout) bindings[4];
        this.rvProgram = (RecyclerView) bindings[11];
        this.rvUserPhoto = (RecyclerView) bindings[7];
        this.tvCorrelation = (TextView) bindings[5];
        this.tvFindMoreUser = (TextView) bindings[6];
        this.tvProgram = (TextView) bindings[10];
        this.tvSeeAll = (TextView) bindings[3];
        this.vLineUser = (View) bindings[8];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
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
            case 7:
                setCarSeriesInfo((CarSeriesInfo) variable);
                return true;
            case 75:
                setUserInfo((UserInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setCarSeriesInfo(CarSeriesInfo CarSeriesInfo) {
        this.mCarSeriesInfo = CarSeriesInfo;
    }

    public CarSeriesInfo getCarSeriesInfo() {
        return this.mCarSeriesInfo;
    }

    public void setUserInfo(UserInfo UserInfo) {
        this.mUserInfo = UserInfo;
    }

    public UserInfo getUserInfo() {
        return this.mUserInfo;
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

    public static NewSearchAllHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static NewSearchAllHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (NewSearchAllHeaderBinding) DataBindingUtil.inflate(inflater, 2130903318, root, attachToRoot, bindingComponent);
    }

    public static NewSearchAllHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static NewSearchAllHeaderBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903318, null, false), bindingComponent);
    }

    public static NewSearchAllHeaderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static NewSearchAllHeaderBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/new_search_all_header_0".equals(view.getTag())) {
            return new NewSearchAllHeaderBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
