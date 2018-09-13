package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.ImageInfo;
import com.feng.car.utils.FengConstant;
import com.feng.car.view.FixedViewPager;
import com.feng.library.utils.StringUtil;

public class ActivityShowBigimageBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView describeText;
    public final RelativeLayout groupLine;
    public final ImageView ivBigimageArrow;
    public final ImageView ivBigimageArrowControllBar;
    public final ImageView ivBigimageBottomShadow;
    public final ImageView ivBigimageHd;
    public final ImageView ivBigimageSave;
    public final LinearLayout llBigimageDescribeContainer;
    public final RelativeLayout llParent;
    private long mDirtyFlags = -1;
    private ImageInfo mImageInfo;
    public final RelativeLayout rlBigimageHd;
    public final RelativeLayout rlBigimageSave;
    public final RelativeLayout rlComment;
    public final RelativeLayout rlDescribeCommentContainer;
    public final TextView tvBigimagePageText;
    public final FixedViewPager viewpager;

    static {
        sViewsWithIds.put(2131624650, 3);
        sViewsWithIds.put(2131624651, 4);
        sViewsWithIds.put(2131624652, 5);
        sViewsWithIds.put(2131624653, 6);
        sViewsWithIds.put(2131624654, 7);
        sViewsWithIds.put(2131624655, 8);
        sViewsWithIds.put(2131624656, 9);
        sViewsWithIds.put(2131624245, 10);
        sViewsWithIds.put(2131624658, 11);
        sViewsWithIds.put(2131624661, 12);
        sViewsWithIds.put(2131624660, 13);
        sViewsWithIds.put(2131624662, 14);
    }

    public ActivityShowBigimageBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds);
        this.describeText = (TextView) bindings[1];
        this.describeText.setTag(null);
        this.groupLine = (RelativeLayout) bindings[4];
        this.ivBigimageArrow = (ImageView) bindings[9];
        this.ivBigimageArrowControllBar = (ImageView) bindings[6];
        this.ivBigimageBottomShadow = (ImageView) bindings[5];
        this.ivBigimageHd = (ImageView) bindings[12];
        this.ivBigimageSave = (ImageView) bindings[14];
        this.llBigimageDescribeContainer = (LinearLayout) bindings[8];
        this.llParent = (RelativeLayout) bindings[0];
        this.llParent.setTag(null);
        this.rlBigimageHd = (RelativeLayout) bindings[2];
        this.rlBigimageHd.setTag(null);
        this.rlBigimageSave = (RelativeLayout) bindings[13];
        this.rlComment = (RelativeLayout) bindings[10];
        this.rlDescribeCommentContainer = (RelativeLayout) bindings[7];
        this.tvBigimagePageText = (TextView) bindings[11];
        this.viewpager = (FixedViewPager) bindings[3];
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
            case 26:
                setImageInfo((ImageInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setImageInfo(ImageInfo ImageInfo) {
        this.mImageInfo = ImageInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(26);
        super.requestRebind();
    }

    public ImageInfo getImageInfo() {
        return this.mImageInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        String imageInfoDescription = null;
        int imageInfoMimetypeInt3ImageInfoWidthFengConstantIMAGEMIDDLEWIDTHBooleanFalseViewVISIBLEViewGONE = 0;
        int imageInfoWidth = 0;
        ImageInfo imageInfo = this.mImageInfo;
        int imageInfoMimetype = 0;
        boolean imageInfoMimetypeInt3 = false;
        int stringUtilIsEmptyImageInfoDescriptionViewGONEViewVISIBLE = 0;
        boolean imageInfoWidthFengConstantIMAGEMIDDLEWIDTH = false;
        if ((3 & dirtyFlags) != 0) {
            if (imageInfo != null) {
                imageInfoDescription = imageInfo.description;
                imageInfoMimetype = imageInfo.mimetype;
            }
            boolean stringUtilIsEmptyImageInfoDescription = StringUtil.isEmpty(imageInfoDescription);
            imageInfoMimetypeInt3 = imageInfoMimetype != 3;
            if ((3 & dirtyFlags) != 0) {
                if (stringUtilIsEmptyImageInfoDescription) {
                    dirtyFlags |= 128;
                } else {
                    dirtyFlags |= 64;
                }
            }
            if ((3 & dirtyFlags) != 0) {
                if (imageInfoMimetypeInt3) {
                    dirtyFlags |= 32;
                } else {
                    dirtyFlags |= 16;
                }
            }
            stringUtilIsEmptyImageInfoDescriptionViewGONEViewVISIBLE = stringUtilIsEmptyImageInfoDescription ? 8 : 0;
        }
        if ((32 & dirtyFlags) != 0) {
            if (imageInfo != null) {
                imageInfoWidth = imageInfo.width;
            }
            imageInfoWidthFengConstantIMAGEMIDDLEWIDTH = imageInfoWidth >= FengConstant.IMAGEMIDDLEWIDTH;
        }
        if ((3 & dirtyFlags) != 0) {
            boolean imageInfoMimetypeInt3ImageInfoWidthFengConstantIMAGEMIDDLEWIDTHBooleanFalse = imageInfoMimetypeInt3 ? imageInfoWidthFengConstantIMAGEMIDDLEWIDTH : false;
            if ((3 & dirtyFlags) != 0) {
                if (imageInfoMimetypeInt3ImageInfoWidthFengConstantIMAGEMIDDLEWIDTHBooleanFalse) {
                    dirtyFlags |= 8;
                } else {
                    dirtyFlags |= 4;
                }
            }
            imageInfoMimetypeInt3ImageInfoWidthFengConstantIMAGEMIDDLEWIDTHBooleanFalseViewVISIBLEViewGONE = imageInfoMimetypeInt3ImageInfoWidthFengConstantIMAGEMIDDLEWIDTHBooleanFalse ? 0 : 8;
        }
        if ((3 & dirtyFlags) != 0) {
            this.describeText.setVisibility(stringUtilIsEmptyImageInfoDescriptionViewGONEViewVISIBLE);
            this.rlBigimageHd.setVisibility(imageInfoMimetypeInt3ImageInfoWidthFengConstantIMAGEMIDDLEWIDTHBooleanFalseViewVISIBLEViewGONE);
        }
    }

    public static ActivityShowBigimageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShowBigimageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityShowBigimageBinding) DataBindingUtil.inflate(inflater, 2130903139, root, attachToRoot, bindingComponent);
    }

    public static ActivityShowBigimageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShowBigimageBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903139, null, false), bindingComponent);
    }

    public static ActivityShowBigimageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShowBigimageBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_show_bigimage_0".equals(view.getTag())) {
            return new ActivityShowBigimageBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
