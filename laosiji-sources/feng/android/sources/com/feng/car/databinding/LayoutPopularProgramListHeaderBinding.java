package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.ViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class LayoutPopularProgramListHeaderBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView headerBg;
    public final ImageView ivShowFollow;
    public final LinearLayout llHostParent;
    public final LinearLayout llPopProgramListHeaderContainer;
    public final RecyclerView lrvShowsHosts;
    private long mDirtyFlags = -1;
    private HotShowInfo mHotShowInfo;
    public final RelativeLayout rlSortSettingContainer;
    public final TextView tvSortType;

    static {
        sViewsWithIds.put(2131624514, 2);
        sViewsWithIds.put(2131625228, 3);
        sViewsWithIds.put(2131624936, 4);
        sViewsWithIds.put(2131625230, 5);
        sViewsWithIds.put(2131625231, 6);
    }

    public LayoutPopularProgramListHeaderBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.headerBg = (AutoFrescoDraweeView) bindings[2];
        this.ivShowFollow = (ImageView) bindings[1];
        this.ivShowFollow.setTag(null);
        this.llHostParent = (LinearLayout) bindings[5];
        this.llPopProgramListHeaderContainer = (LinearLayout) bindings[0];
        this.llPopProgramListHeaderContainer.setTag(null);
        this.lrvShowsHosts = (RecyclerView) bindings[6];
        this.rlSortSettingContainer = (RelativeLayout) bindings[3];
        this.tvSortType = (TextView) bindings[4];
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
            case 24:
                setHotShowInfo((HotShowInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setHotShowInfo(HotShowInfo HotShowInfo) {
        this.mHotShowInfo = HotShowInfo;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(24);
        super.requestRebind();
    }

    public HotShowInfo getHotShowInfo() {
        return this.mHotShowInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeHotShowInfoIsfollow((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeHotShowInfoIsfollow(ObservableInt HotShowInfoIsfollow, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 1;
                }
                return true;
            default:
                return false;
        }
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        Drawable hotShowInfoIsfollowInt1IvShowFollowAndroidDrawableFollowCancelSelectorIvShowFollowAndroidDrawableFollowSelector = null;
        ObservableInt hotShowInfoIsfollow = null;
        HotShowInfo hotShowInfo = this.mHotShowInfo;
        int hotShowInfoIsfollowGet = 0;
        if ((7 & dirtyFlags) != 0) {
            if (hotShowInfo != null) {
                hotShowInfoIsfollow = hotShowInfo.isfollow;
            }
            updateRegistration(0, hotShowInfoIsfollow);
            if (hotShowInfoIsfollow != null) {
                hotShowInfoIsfollowGet = hotShowInfoIsfollow.get();
            }
            boolean hotShowInfoIsfollowInt1 = hotShowInfoIsfollowGet == 1;
            if ((7 & dirtyFlags) != 0) {
                if (hotShowInfoIsfollowInt1) {
                    dirtyFlags |= 16;
                } else {
                    dirtyFlags |= 8;
                }
            }
            hotShowInfoIsfollowInt1IvShowFollowAndroidDrawableFollowCancelSelectorIvShowFollowAndroidDrawableFollowSelector = hotShowInfoIsfollowInt1 ? getDrawableFromResource(this.ivShowFollow, 2130837872) : getDrawableFromResource(this.ivShowFollow, 2130837877);
        }
        if ((7 & dirtyFlags) != 0) {
            ViewBindingAdapter.setBackground(this.ivShowFollow, hotShowInfoIsfollowInt1IvShowFollowAndroidDrawableFollowCancelSelectorIvShowFollowAndroidDrawableFollowSelector);
        }
    }

    public static LayoutPopularProgramListHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutPopularProgramListHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (LayoutPopularProgramListHeaderBinding) DataBindingUtil.inflate(inflater, 2130903291, root, attachToRoot, bindingComponent);
    }

    public static LayoutPopularProgramListHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutPopularProgramListHeaderBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903291, null, false), bindingComponent);
    }

    public static LayoutPopularProgramListHeaderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutPopularProgramListHeaderBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/layout_popular_program_list_header_0".equals(view.getTag())) {
            return new LayoutPopularProgramListHeaderBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
