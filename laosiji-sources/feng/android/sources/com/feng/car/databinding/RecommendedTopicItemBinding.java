package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class RecommendedTopicItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View divider;
    public final View divider1;
    public final AutoFrescoDraweeView ivImage;
    private long mDirtyFlags = -1;
    private CircleInfo mInfo;
    private final RelativeLayout mboundView0;
    private final TextView mboundView1;
    public final TextView tvFansNumContentNum;

    static {
        sViewsWithIds.put(2131625452, 2);
        sViewsWithIds.put(2131624438, 3);
        sViewsWithIds.put(2131625453, 4);
        sViewsWithIds.put(2131624240, 5);
    }

    public RecommendedTopicItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.divider = (View) bindings[5];
        this.divider1 = (View) bindings[2];
        this.ivImage = (AutoFrescoDraweeView) bindings[3];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.tvFansNumContentNum = (TextView) bindings[4];
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
            case 28:
                setInfo((CircleInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CircleInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CircleInfo getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeInfoIslocalselect((ObservableBoolean) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeInfoIslocalselect(ObservableBoolean InfoIslocalselect, int fieldId) {
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
        String infoName = null;
        boolean infoIslocalselectGet = false;
        int infoIslocalselectMboundView1AndroidColorColorFb6c06MboundView1AndroidColorColor87000000 = 0;
        CircleInfo info = this.mInfo;
        ObservableBoolean infoIslocalselect = null;
        if ((7 & dirtyFlags) != 0) {
            if (!((6 & dirtyFlags) == 0 || info == null)) {
                infoName = info.name;
            }
            if (info != null) {
                infoIslocalselect = info.islocalselect;
            }
            updateRegistration(0, infoIslocalselect);
            if (infoIslocalselect != null) {
                infoIslocalselectGet = infoIslocalselect.get();
            }
            if ((7 & dirtyFlags) != 0) {
                if (infoIslocalselectGet) {
                    dirtyFlags |= 16;
                } else {
                    dirtyFlags |= 8;
                }
            }
            infoIslocalselectMboundView1AndroidColorColorFb6c06MboundView1AndroidColorColor87000000 = infoIslocalselectGet ? getColorFromResource(this.mboundView1, 2131558537) : getColorFromResource(this.mboundView1, 2131558478);
        }
        if ((6 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView1, infoName);
        }
        if ((7 & dirtyFlags) != 0) {
            this.mboundView1.setTextColor(infoIslocalselectMboundView1AndroidColorColorFb6c06MboundView1AndroidColorColor87000000);
        }
    }

    public static RecommendedTopicItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static RecommendedTopicItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (RecommendedTopicItemBinding) DataBindingUtil.inflate(inflater, 2130903368, root, attachToRoot, bindingComponent);
    }

    public static RecommendedTopicItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static RecommendedTopicItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903368, null, false), bindingComponent);
    }

    public static RecommendedTopicItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static RecommendedTopicItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/recommended_topic_item_0".equals(view.getTag())) {
            return new RecommendedTopicItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
