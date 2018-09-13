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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.entity.sendpost.SoftKeyOpen;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.largeimage.LargeImageView;
import com.feng.library.emoticons.keyboard.widget.UserDefEmoticonsEditText;

public class PostInitItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final UserDefEmoticonsEditText etDescribe;
    private InverseBindingListener etDescribeandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(PostInitItemBinding.this.etDescribe);
            PostEdit edit1 = PostInitItemBinding.this.mEdit1;
            if (edit1 != null) {
                edit1.setDescription(callbackArg_0);
            }
        }
    };
    public final ImageView ivDel;
    public final ImageView ivDragImage;
    public final AutoFrescoDraweeView ivPost;
    public final ImageView ivVideo;
    public final ImageView ivVideoPlay;
    public final LinearLayout llDescribe;
    public final LinearLayout llPicVideoParent;
    public final LinearLayout llVideoPlay;
    public final LargeImageView longImage;
    private long mDirtyFlags = -1;
    private PostEdit mEdit1;
    private SoftKeyOpen mSoftOpen;
    private final RelativeLayout mboundView0;
    public final TextView tvVideoHint;
    public final TextView tvVideoTime;

    static {
        sViewsWithIds.put(2131625393, 3);
        sViewsWithIds.put(2131625394, 4);
        sViewsWithIds.put(2131625395, 5);
        sViewsWithIds.put(2131624812, 6);
        sViewsWithIds.put(2131625397, 7);
        sViewsWithIds.put(2131625398, 8);
        sViewsWithIds.put(2131625399, 9);
        sViewsWithIds.put(2131625400, 10);
        sViewsWithIds.put(2131625401, 11);
        sViewsWithIds.put(2131625403, 12);
    }

    public PostInitItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds);
        this.etDescribe = (UserDefEmoticonsEditText) bindings[2];
        this.etDescribe.setTag(null);
        this.ivDel = (ImageView) bindings[1];
        this.ivDel.setTag(null);
        this.ivDragImage = (ImageView) bindings[12];
        this.ivPost = (AutoFrescoDraweeView) bindings[4];
        this.ivVideo = (ImageView) bindings[5];
        this.ivVideoPlay = (ImageView) bindings[8];
        this.llDescribe = (LinearLayout) bindings[11];
        this.llPicVideoParent = (LinearLayout) bindings[3];
        this.llVideoPlay = (LinearLayout) bindings[7];
        this.longImage = (LargeImageView) bindings[6];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvVideoHint = (TextView) bindings[10];
        this.tvVideoTime = (TextView) bindings[9];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16;
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
            case 21:
                setEdit1((PostEdit) variable);
                return true;
            case 63:
                setSoftOpen((SoftKeyOpen) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSoftOpen(SoftKeyOpen SoftOpen) {
        updateRegistration(0, SoftOpen);
        this.mSoftOpen = SoftOpen;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(63);
        super.requestRebind();
    }

    public SoftKeyOpen getSoftOpen() {
        return this.mSoftOpen;
    }

    public void setEdit1(PostEdit Edit1) {
        updateRegistration(1, Edit1);
        this.mEdit1 = Edit1;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(21);
        super.requestRebind();
    }

    public PostEdit getEdit1() {
        return this.mEdit1;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeSoftOpen((SoftKeyOpen) object, fieldId);
            case 1:
                return onChangeEdit1((PostEdit) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeSoftOpen(SoftKeyOpen SoftOpen, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 1;
                }
                return true;
            case 44:
                synchronized (this) {
                    this.mDirtyFlags |= 4;
                }
                return true;
            default:
                return false;
        }
    }

    private boolean onChangeEdit1(PostEdit Edit1, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 2;
                }
                return true;
            case 20:
                synchronized (this) {
                    this.mDirtyFlags |= 8;
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
        boolean softOpenOpenSoftKeyboard = false;
        SoftKeyOpen softOpen = this.mSoftOpen;
        PostEdit edit1 = this.mEdit1;
        String edit1Description = null;
        int softOpenOpenSoftKeyboardViewGONEViewVISIBLE = 0;
        if ((21 & dirtyFlags) != 0) {
            if (softOpen != null) {
                softOpenOpenSoftKeyboard = softOpen.isOpenSoftKeyboard();
            }
            if ((21 & dirtyFlags) != 0) {
                if (softOpenOpenSoftKeyboard) {
                    dirtyFlags |= 64;
                } else {
                    dirtyFlags |= 32;
                }
            }
            softOpenOpenSoftKeyboardViewGONEViewVISIBLE = softOpenOpenSoftKeyboard ? 8 : 0;
        }
        if (!((26 & dirtyFlags) == 0 || edit1 == null)) {
            edit1Description = edit1.getDescription();
        }
        if ((26 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.etDescribe, edit1Description);
        }
        if ((16 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setTextWatcher(this.etDescribe, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etDescribeandroidTextAttrChanged);
        }
        if ((21 & dirtyFlags) != 0) {
            this.ivDel.setVisibility(softOpenOpenSoftKeyboardViewGONEViewVISIBLE);
        }
    }

    public static PostInitItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PostInitItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PostInitItemBinding) DataBindingUtil.inflate(inflater, 2130903349, root, attachToRoot, bindingComponent);
    }

    public static PostInitItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PostInitItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903349, null, false), bindingComponent);
    }

    public static PostInitItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PostInitItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/post_init_item_0".equals(view.getTag())) {
            return new PostInitItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
