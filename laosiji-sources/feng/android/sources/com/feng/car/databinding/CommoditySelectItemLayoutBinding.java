package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.DynamicUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.ViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class CommoditySelectItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView ivImage;
    private long mDirtyFlags = -1;
    private CommodityInfo mInfo;
    private Integer mSaletype;
    private final RelativeLayout mboundView0;
    private final RelativeLayout mboundView1;
    private final ImageView mboundView4;
    public final TextView tvBrowseNum;
    public final TextView tvCommodityName;
    public final TextView tvCover;
    public final TextView tvCurrentPrice;
    public final TextView tvGuidePrice;

    static {
        sViewsWithIds.put(2131624438, 5);
        sViewsWithIds.put(2131624954, 6);
        sViewsWithIds.put(2131624950, 7);
        sViewsWithIds.put(2131624953, 8);
    }

    public CommoditySelectItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.ivImage = (AutoFrescoDraweeView) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (RelativeLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView4 = (ImageView) bindings[4];
        this.mboundView4.setTag(null);
        this.tvBrowseNum = (TextView) bindings[8];
        this.tvCommodityName = (TextView) bindings[7];
        this.tvCover = (TextView) bindings[6];
        this.tvCurrentPrice = (TextView) bindings[2];
        this.tvCurrentPrice.setTag(null);
        this.tvGuidePrice = (TextView) bindings[3];
        this.tvGuidePrice.setTag(null);
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
            case 28:
                setInfo((CommodityInfo) variable);
                return true;
            case 50:
                setSaletype((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSaletype(Integer Saletype) {
        this.mSaletype = Saletype;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(50);
        super.requestRebind();
    }

    public Integer getSaletype() {
        return this.mSaletype;
    }

    public void setInfo(CommodityInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CommodityInfo getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeInfoLocalSelect((ObservableBoolean) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeInfoLocalSelect(ObservableBoolean InfoLocalSelect, int fieldId) {
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
        ObservableBoolean infoLocalSelect = null;
        boolean infoLocalSelectGet = false;
        int saletypeInt1ViewVISIBLEViewGONE = 0;
        int infoLocalSelectViewVISIBLEViewGONE = 0;
        Integer saletype = this.mSaletype;
        Drawable infoLocalSelectMboundView0AndroidDrawableBgBorderFfffffFb6c068pxMboundView0AndroidDrawableBgTransparent = null;
        Drawable infoLocalSelectMboundView1AndroidDrawableBgTransparentMboundView1AndroidDrawableBgRoundFfffffBottom4dp = null;
        CommodityInfo info = this.mInfo;
        if ((10 & dirtyFlags) != 0) {
            boolean saletypeInt1 = DynamicUtil.safeUnbox(saletype) == 1;
            if ((10 & dirtyFlags) != 0) {
                if (saletypeInt1) {
                    dirtyFlags |= 32;
                } else {
                    dirtyFlags |= 16;
                }
            }
            saletypeInt1ViewVISIBLEViewGONE = saletypeInt1 ? 0 : 8;
        }
        if ((13 & dirtyFlags) != 0) {
            if (info != null) {
                infoLocalSelect = info.local_select;
            }
            updateRegistration(0, infoLocalSelect);
            if (infoLocalSelect != null) {
                infoLocalSelectGet = infoLocalSelect.get();
            }
            if ((13 & dirtyFlags) != 0) {
                if (infoLocalSelectGet) {
                    dirtyFlags = ((dirtyFlags | 128) | 512) | 2048;
                } else {
                    dirtyFlags = ((dirtyFlags | 64) | 256) | 1024;
                }
            }
            infoLocalSelectViewVISIBLEViewGONE = infoLocalSelectGet ? 0 : 8;
            infoLocalSelectMboundView0AndroidDrawableBgBorderFfffffFb6c068pxMboundView0AndroidDrawableBgTransparent = infoLocalSelectGet ? getDrawableFromResource(this.mboundView0, 2130837675) : getDrawableFromResource(this.mboundView0, 2130837736);
            infoLocalSelectMboundView1AndroidDrawableBgTransparentMboundView1AndroidDrawableBgRoundFfffffBottom4dp = infoLocalSelectGet ? getDrawableFromResource(this.mboundView1, 2130837736) : getDrawableFromResource(this.mboundView1, 2130837729);
        }
        if ((13 & dirtyFlags) != 0) {
            ViewBindingAdapter.setBackground(this.mboundView0, infoLocalSelectMboundView0AndroidDrawableBgBorderFfffffFb6c068pxMboundView0AndroidDrawableBgTransparent);
            ViewBindingAdapter.setBackground(this.mboundView1, infoLocalSelectMboundView1AndroidDrawableBgTransparentMboundView1AndroidDrawableBgRoundFfffffBottom4dp);
            this.mboundView4.setVisibility(infoLocalSelectViewVISIBLEViewGONE);
        }
        if ((10 & dirtyFlags) != 0) {
            this.tvCurrentPrice.setVisibility(saletypeInt1ViewVISIBLEViewGONE);
            this.tvGuidePrice.setVisibility(saletypeInt1ViewVISIBLEViewGONE);
        }
    }

    public static CommoditySelectItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CommoditySelectItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CommoditySelectItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903200, root, attachToRoot, bindingComponent);
    }

    public static CommoditySelectItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CommoditySelectItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903200, null, false), bindingComponent);
    }

    public static CommoditySelectItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CommoditySelectItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/commodity_select_item_layout_0".equals(view.getTag())) {
            return new CommoditySelectItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
