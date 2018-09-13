package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingListener;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.dealer.WriteServeItem;

public class WriteServeItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageButton delete;
    public final EditText etItem;
    private InverseBindingListener etItemandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(WriteServeItemLayoutBinding.this.etItem);
            WriteServeItem item = WriteServeItemLayoutBinding.this.mItem;
            if (item != null) {
                item.setContent(callbackArg_0);
            }
        }
    };
    public final View llAdd;
    private long mDirtyFlags = -1;
    private WriteServeItem mItem;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlPrice;
    public final EditText tvPrice;
    private InverseBindingListener tvPriceandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(WriteServeItemLayoutBinding.this.tvPrice);
            WriteServeItem item = WriteServeItemLayoutBinding.this.mItem;
            if (item != null) {
                item.setPrice(callbackArg_0);
            }
        }
    };
    public final TextView tvTextTip;
    public final TextView tvYuan;

    static {
        sViewsWithIds.put(2131624759, 3);
        sViewsWithIds.put(2131624830, 4);
        sViewsWithIds.put(2131625581, 5);
        sViewsWithIds.put(2131625582, 6);
        sViewsWithIds.put(2131625583, 7);
    }

    public WriteServeItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.delete = (ImageButton) bindings[4];
        this.etItem = (EditText) bindings[1];
        this.etItem.setTag(null);
        this.llAdd = (View) bindings[5];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlPrice = (RelativeLayout) bindings[6];
        this.tvPrice = (EditText) bindings[2];
        this.tvPrice.setTag(null);
        this.tvTextTip = (TextView) bindings[3];
        this.tvYuan = (TextView) bindings[7];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8;
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
            case 29:
                setItem((WriteServeItem) variable);
                return true;
            default:
                return false;
        }
    }

    public void setItem(WriteServeItem Item) {
        updateRegistration(0, Item);
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(29);
        super.requestRebind();
    }

    public WriteServeItem getItem() {
        return this.mItem;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeItem((WriteServeItem) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeItem(WriteServeItem Item, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 1;
                }
                return true;
            case 17:
                synchronized (this) {
                    this.mDirtyFlags |= 2;
                }
                return true;
            case 47:
                synchronized (this) {
                    this.mDirtyFlags |= 4;
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
        WriteServeItem item = this.mItem;
        String itemPrice = null;
        String itemContent = null;
        if ((15 & dirtyFlags) != 0) {
            if (!((13 & dirtyFlags) == 0 || item == null)) {
                itemPrice = item.getPrice();
            }
            if (!((11 & dirtyFlags) == 0 || item == null)) {
                itemContent = item.getContent();
            }
        }
        if ((11 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.etItem, itemContent);
        }
        if ((8 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setTextWatcher(this.etItem, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etItemandroidTextAttrChanged);
            TextViewBindingAdapter.setTextWatcher(this.tvPrice, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.tvPriceandroidTextAttrChanged);
        }
        if ((13 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvPrice, itemPrice);
        }
    }

    public static WriteServeItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static WriteServeItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (WriteServeItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903438, root, attachToRoot, bindingComponent);
    }

    public static WriteServeItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static WriteServeItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903438, null, false), bindingComponent);
    }

    public static WriteServeItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static WriteServeItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/write_serve_item_layout_0".equals(view.getTag())) {
            return new WriteServeItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
