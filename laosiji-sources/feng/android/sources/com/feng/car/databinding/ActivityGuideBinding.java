package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;

public class ActivityGuideBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdGuideHead;
    public final ImageView btGuideFinish;
    public final EditText etGuideName;
    public final View line;
    private long mDirtyFlags = -1;
    private int mUseless;
    private final LinearLayout mboundView0;
    public final RadioButton rbGuideFemale;
    public final RadioButton rbGuideMale;
    public final RadioGroup rgGuideGender;
    public final TextView tvFemaleXing;
    public final TextView tvName;
    public final TextView tvXing;

    static {
        sViewsWithIds.put(2131624367, 1);
        sViewsWithIds.put(2131624369, 2);
        sViewsWithIds.put(2131624368, 3);
        sViewsWithIds.put(2131624370, 4);
        sViewsWithIds.put(2131624371, 5);
        sViewsWithIds.put(2131624372, 6);
        sViewsWithIds.put(2131624373, 7);
        sViewsWithIds.put(2131624374, 8);
        sViewsWithIds.put(2131624375, 9);
        sViewsWithIds.put(2131624376, 10);
    }

    public ActivityGuideBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.afdGuideHead = (AutoFrescoDraweeView) bindings[10];
        this.btGuideFinish = (ImageView) bindings[9];
        this.etGuideName = (EditText) bindings[3];
        this.line = (View) bindings[8];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rbGuideFemale = (RadioButton) bindings[7];
        this.rbGuideMale = (RadioButton) bindings[6];
        this.rgGuideGender = (RadioGroup) bindings[5];
        this.tvFemaleXing = (TextView) bindings[4];
        this.tvName = (TextView) bindings[2];
        this.tvXing = (TextView) bindings[1];
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
            case 74:
                setUseless(((Integer) variable).intValue());
                return true;
            default:
                return false;
        }
    }

    public void setUseless(int Useless) {
        this.mUseless = Useless;
    }

    public int getUseless() {
        return this.mUseless;
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

    public static ActivityGuideBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityGuideBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityGuideBinding) DataBindingUtil.inflate(inflater, 2130903095, root, attachToRoot, bindingComponent);
    }

    public static ActivityGuideBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityGuideBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903095, null, false), bindingComponent);
    }

    public static ActivityGuideBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityGuideBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_guide_0".equals(view.getTag())) {
            return new ActivityGuideBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
