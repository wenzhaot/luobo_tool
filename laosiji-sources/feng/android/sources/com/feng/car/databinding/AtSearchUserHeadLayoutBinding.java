package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.entity.search.SearchKey;

public class AtSearchUserHeadLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags = -1;
    private SearchKey mKey;
    private final LinearLayout mboundView0;
    public final TextView tvAtName;

    public AtSearchUserHeadLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvAtName = (TextView) bindings[1];
        this.tvAtName.setTag(null);
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
            case 30:
                setKey((SearchKey) variable);
                return true;
            default:
                return false;
        }
    }

    public void setKey(SearchKey Key) {
        this.mKey = Key;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(30);
        super.requestRebind();
    }

    public SearchKey getKey() {
        return this.mKey;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeKeySearchKey((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeKeySearchKey(ObservableField<String> observableField, int fieldId) {
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
        String keySearchKeyGet = null;
        SearchKey key = this.mKey;
        String stringFormatTvAtNameAndroidStringAtNameKeySearchKey = null;
        ObservableField<String> keySearchKey = null;
        if ((dirtyFlags & 7) != 0) {
            if (key != null) {
                keySearchKey = key.searchKey;
            }
            updateRegistration(0, keySearchKey);
            if (keySearchKey != null) {
                keySearchKeyGet = (String) keySearchKey.get();
            }
            stringFormatTvAtNameAndroidStringAtNameKeySearchKey = String.format(this.tvAtName.getResources().getString(2131230848), new Object[]{keySearchKeyGet});
        }
        if ((dirtyFlags & 7) != 0) {
            TextViewBindingAdapter.setText(this.tvAtName, stringFormatTvAtNameAndroidStringAtNameKeySearchKey);
        }
    }

    public static AtSearchUserHeadLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static AtSearchUserHeadLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (AtSearchUserHeadLayoutBinding) DataBindingUtil.inflate(inflater, 2130903164, root, attachToRoot, bindingComponent);
    }

    public static AtSearchUserHeadLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static AtSearchUserHeadLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903164, null, false), bindingComponent);
    }

    public static AtSearchUserHeadLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static AtSearchUserHeadLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/at_search_user_head_layout_0".equals(view.getTag())) {
            return new AtSearchUserHeadLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
