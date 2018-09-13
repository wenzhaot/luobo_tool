package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.view.SlidingButton;

public class ActivityAboutUsBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final Button btAboutSave;
    public final Button btClearYk;
    public final EditText etAboutHttp;
    public final ImageView ivAboutFengCar;
    public final ImageView ivAboutLogo;
    public final LinearLayout linearLayout;
    public final LinearLayout llVersion;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final RelativeLayout rlVersionUpdate;
    public final SlidingButton sliderBtnAttention;
    public final SlidingButton sliderBtnTest;
    public final TextView textView2;
    public final TextView tvAboutAppName;
    public final TextView tvAboutVersion;
    public final TextView tvChannel;
    public final TextView tvTestText;
    public final TextView tvVersion;

    static {
        sViewsWithIds.put(R.id.et_about_http, 1);
        sViewsWithIds.put(R.id.tv_channel, 2);
        sViewsWithIds.put(R.id.bt_about_save, 3);
        sViewsWithIds.put(R.id.iv_about_logo, 4);
        sViewsWithIds.put(R.id.tv_about_AppName, 5);
        sViewsWithIds.put(R.id.iv_about_fengCar, 6);
        sViewsWithIds.put(R.id.slider_btn_test, 7);
        sViewsWithIds.put(R.id.tv_test_text, 8);
        sViewsWithIds.put(R.id.linearLayout, 9);
        sViewsWithIds.put(R.id.rl_version_update, 10);
        sViewsWithIds.put(R.id.textView2, 11);
        sViewsWithIds.put(R.id.tv_version, 12);
        sViewsWithIds.put(R.id.bt_clear_yk, 13);
        sViewsWithIds.put(R.id.slider_btn_attention, 14);
        sViewsWithIds.put(R.id.ll_version, 15);
        sViewsWithIds.put(R.id.tv_about_version, 16);
    }

    public ActivityAboutUsBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds);
        this.btAboutSave = (Button) bindings[3];
        this.btClearYk = (Button) bindings[13];
        this.etAboutHttp = (EditText) bindings[1];
        this.ivAboutFengCar = (ImageView) bindings[6];
        this.ivAboutLogo = (ImageView) bindings[4];
        this.linearLayout = (LinearLayout) bindings[9];
        this.llVersion = (LinearLayout) bindings[15];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlVersionUpdate = (RelativeLayout) bindings[10];
        this.sliderBtnAttention = (SlidingButton) bindings[14];
        this.sliderBtnTest = (SlidingButton) bindings[7];
        this.textView2 = (TextView) bindings[11];
        this.tvAboutAppName = (TextView) bindings[5];
        this.tvAboutVersion = (TextView) bindings[16];
        this.tvChannel = (TextView) bindings[2];
        this.tvTestText = (TextView) bindings[8];
        this.tvVersion = (TextView) bindings[12];
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

    public static ActivityAboutUsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAboutUsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityAboutUsBinding) DataBindingUtil.inflate(inflater, R.layout.activity_about_us, root, attachToRoot, bindingComponent);
    }

    public static ActivityAboutUsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAboutUsBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(R.layout.activity_about_us, null, false), bindingComponent);
    }

    public static ActivityAboutUsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAboutUsBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_about_us_0".equals(view.getTag())) {
            return new ActivityAboutUsBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
