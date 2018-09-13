package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingListener;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged;
import android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged;
import android.databinding.adapters.TextViewBindingAdapter.OnTextChanged;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.search.SearchKey;
import com.feng.car.view.ClearEditText;
import com.feng.car.view.ComparisonView;

public class NewTitleBarLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View divier;
    public final ClearEditText etSearchKey;
    private InverseBindingListener etSearchKeyandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(NewTitleBarLayoutBinding.this.etSearchKey);
            SearchKey key = NewTitleBarLayoutBinding.this.mKey;
            if (key != null) {
                boolean keySearchKeyJavaLangObjectNull;
                ObservableField<String> keySearchKey = key.searchKey;
                if (keySearchKey != null) {
                    keySearchKeyJavaLangObjectNull = true;
                } else {
                    keySearchKeyJavaLangObjectNull = false;
                }
                if (keySearchKeyJavaLangObjectNull) {
                    keySearchKey.set(callbackArg_0);
                }
            }
        }
    };
    public final ImageView ivMessage;
    public final ImageView ivRightImage;
    public final ImageView ivRightImageTwo;
    public final ImageView ivTitleLeft;
    public final ImageView ivTitleLeftClose;
    private long mDirtyFlags = -1;
    private SearchKey mKey;
    private final RelativeLayout mboundView0;
    public final ComparisonView rightCvPk;
    public final RelativeLayout rlSearchBar;
    public final TextView title;
    public final RelativeLayout titlebarMiddleParent;
    public final RelativeLayout titlebarRightParent;
    public final TextView tvCancel;
    public final TextView tvMessageCommentNum;
    public final TextView tvRightText;
    public final TextView tvRightTextTwo;
    public final TextView tvSearch;
    public final TextView tvTitleLeft;
    public final ImageView uploadPriceButton;

    static {
        sViewsWithIds.put(2131624295, 2);
        sViewsWithIds.put(2131625315, 3);
        sViewsWithIds.put(2131625316, 4);
        sViewsWithIds.put(2131625317, 5);
        sViewsWithIds.put(2131624015, 6);
        sViewsWithIds.put(2131624232, 7);
        sViewsWithIds.put(2131624233, 8);
        sViewsWithIds.put(2131625318, 9);
        sViewsWithIds.put(2131625319, 10);
        sViewsWithIds.put(2131625320, 11);
        sViewsWithIds.put(2131625321, 12);
        sViewsWithIds.put(2131625322, 13);
        sViewsWithIds.put(2131624297, 14);
        sViewsWithIds.put(2131625323, 15);
        sViewsWithIds.put(2131624545, 16);
        sViewsWithIds.put(2131624575, 17);
        sViewsWithIds.put(2131624576, 18);
        sViewsWithIds.put(2131624751, 19);
    }

    public NewTitleBarLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds);
        this.divier = (View) bindings[19];
        this.etSearchKey = (ClearEditText) bindings[1];
        this.etSearchKey.setTag(null);
        this.ivMessage = (ImageView) bindings[7];
        this.ivRightImage = (ImageView) bindings[10];
        this.ivRightImageTwo = (ImageView) bindings[11];
        this.ivTitleLeft = (ImageView) bindings[2];
        this.ivTitleLeftClose = (ImageView) bindings[3];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rightCvPk = (ComparisonView) bindings[14];
        this.rlSearchBar = (RelativeLayout) bindings[17];
        this.title = (TextView) bindings[6];
        this.titlebarMiddleParent = (RelativeLayout) bindings[5];
        this.titlebarRightParent = (RelativeLayout) bindings[9];
        this.tvCancel = (TextView) bindings[18];
        this.tvMessageCommentNum = (TextView) bindings[8];
        this.tvRightText = (TextView) bindings[12];
        this.tvRightTextTwo = (TextView) bindings[13];
        this.tvSearch = (TextView) bindings[16];
        this.tvTitleLeft = (TextView) bindings[4];
        this.uploadPriceButton = (ImageView) bindings[15];
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
        ObservableField<String> keySearchKey = null;
        if ((dirtyFlags & 7) != 0) {
            if (key != null) {
                keySearchKey = key.searchKey;
            }
            updateRegistration(0, keySearchKey);
            if (keySearchKey != null) {
                keySearchKeyGet = (String) keySearchKey.get();
            }
        }
        if ((dirtyFlags & 7) != 0) {
            TextViewBindingAdapter.setText(this.etSearchKey, keySearchKeyGet);
        }
        if ((4 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setTextWatcher(this.etSearchKey, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etSearchKeyandroidTextAttrChanged);
        }
    }

    public static NewTitleBarLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static NewTitleBarLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (NewTitleBarLayoutBinding) DataBindingUtil.inflate(inflater, 2130903319, root, attachToRoot, bindingComponent);
    }

    public static NewTitleBarLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static NewTitleBarLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903319, null, false), bindingComponent);
    }

    public static NewTitleBarLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static NewTitleBarLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/new_title_bar_layout_0".equals(view.getTag())) {
            return new NewTitleBarLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
