package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.databinding.adapters.ViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.BigImageLoadProgressView;
import com.feng.car.view.largeimage.LargeImageView;
import com.feng.car.view.photoview.PhotoDraweeView;

public class ShowBigimageViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View coverView;
    public final PhotoDraweeView imageBig;
    public final AutoFrescoDraweeView imageBigGif;
    public final BigImageLoadProgressView ivBigimageProgressView;
    public final LargeImageView longImage;
    private long mDirtyFlags = -1;
    private ImageInfo mImageInfo;
    private ImageVideoInfo mInfo;
    public final RelativeLayout rlParent;
    public final TextView tvSelectIndex;

    static {
        sViewsWithIds.put(2131625489, 2);
        sViewsWithIds.put(2131625154, 3);
        sViewsWithIds.put(2131624812, 4);
        sViewsWithIds.put(2131625155, 5);
        sViewsWithIds.put(2131625148, 6);
    }

    public ShowBigimageViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.coverView = (View) bindings[6];
        this.imageBig = (PhotoDraweeView) bindings[3];
        this.imageBigGif = (AutoFrescoDraweeView) bindings[2];
        this.ivBigimageProgressView = (BigImageLoadProgressView) bindings[5];
        this.longImage = (LargeImageView) bindings[4];
        this.rlParent = (RelativeLayout) bindings[0];
        this.rlParent.setTag(null);
        this.tvSelectIndex = (TextView) bindings[1];
        this.tvSelectIndex.setTag(null);
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
            case 26:
                setImageInfo((ImageInfo) variable);
                return true;
            case 28:
                setInfo((ImageVideoInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setImageInfo(ImageInfo ImageInfo) {
        this.mImageInfo = ImageInfo;
    }

    public ImageInfo getImageInfo() {
        return this.mImageInfo;
    }

    public void setInfo(ImageVideoInfo Info) {
        updateRegistration(1, Info);
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public ImageVideoInfo getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeInfoIsSelected((ObservableBoolean) object, fieldId);
            case 1:
                return onChangeInfo((ImageVideoInfo) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeInfoIsSelected(ObservableBoolean InfoIsSelected, int fieldId) {
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

    private boolean onChangeInfo(ImageVideoInfo Info, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 2;
                }
                return true;
            case 52:
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
        boolean infoIsSelectedGet = false;
        Drawable infoIsSelectedTvSelectIndexAndroidDrawableSelectMediaSelectorTvSelectIndexAndroidDrawableSelectNotMediaSelector = null;
        ObservableBoolean infoIsSelected = null;
        ImageVideoInfo info = this.mInfo;
        String infoSelectIndexJavaLangString = null;
        int infoSelectIndex = 0;
        boolean infoSelectIndexInt0 = false;
        String infoSelectIndexInt0JavaLangStringInfoSelectIndexJavaLangString = null;
        if ((27 & dirtyFlags) != 0) {
            if ((19 & dirtyFlags) != 0) {
                if (info != null) {
                    infoIsSelected = info.isSelected;
                }
                updateRegistration(0, infoIsSelected);
                if (infoIsSelected != null) {
                    infoIsSelectedGet = infoIsSelected.get();
                }
                if ((19 & dirtyFlags) != 0) {
                    if (infoIsSelectedGet) {
                        dirtyFlags |= 64;
                    } else {
                        dirtyFlags |= 32;
                    }
                }
                if (infoIsSelectedGet) {
                    infoIsSelectedTvSelectIndexAndroidDrawableSelectMediaSelectorTvSelectIndexAndroidDrawableSelectNotMediaSelector = getDrawableFromResource(this.tvSelectIndex, 2130838450);
                } else {
                    infoIsSelectedTvSelectIndexAndroidDrawableSelectMediaSelectorTvSelectIndexAndroidDrawableSelectNotMediaSelector = getDrawableFromResource(this.tvSelectIndex, 2130838451);
                }
            }
            if ((26 & dirtyFlags) != 0) {
                if (info != null) {
                    infoSelectIndex = info.getSelectIndex();
                }
                infoSelectIndexInt0 = infoSelectIndex == 0;
                if ((26 & dirtyFlags) != 0) {
                    if (infoSelectIndexInt0) {
                        dirtyFlags |= 256;
                    } else {
                        dirtyFlags |= 128;
                    }
                }
            }
        }
        if ((128 & dirtyFlags) != 0) {
            infoSelectIndexJavaLangString = infoSelectIndex + "";
        }
        if ((26 & dirtyFlags) != 0) {
            infoSelectIndexInt0JavaLangStringInfoSelectIndexJavaLangString = infoSelectIndexInt0 ? "" : infoSelectIndexJavaLangString;
        }
        if ((19 & dirtyFlags) != 0) {
            ViewBindingAdapter.setBackground(this.tvSelectIndex, infoIsSelectedTvSelectIndexAndroidDrawableSelectMediaSelectorTvSelectIndexAndroidDrawableSelectNotMediaSelector);
        }
        if ((26 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvSelectIndex, infoSelectIndexInt0JavaLangStringInfoSelectIndexJavaLangString);
        }
    }

    public static ShowBigimageViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ShowBigimageViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ShowBigimageViewBinding) DataBindingUtil.inflate(inflater, 2130903390, root, attachToRoot, bindingComponent);
    }

    public static ShowBigimageViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ShowBigimageViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903390, null, false), bindingComponent);
    }

    public static ShowBigimageViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ShowBigimageViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/show_bigimage_view_0".equals(view.getTag())) {
            return new ShowBigimageViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
