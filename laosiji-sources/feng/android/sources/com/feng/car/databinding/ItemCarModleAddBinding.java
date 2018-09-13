package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.DynamicUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarModelInfo;
import com.tencent.ijk.media.player.IjkMediaMeta;

public class ItemCarModleAddBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivCarState;
    private String mCarseriesname;
    private long mDirtyFlags = -1;
    private CarModelInfo mInfo;
    private Integer mPosition;
    private final LinearLayout mboundView0;
    private final View mboundView1;
    public final RelativeLayout rlContent;
    public final TextView tvCarEngine;
    public final TextView tvComparison;
    public final TextView tvContent;
    public final TextView tvPrice;
    public final TextView tvYear;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624858, 7);
        sViewsWithIds.put(2131624861, 8);
        sViewsWithIds.put(2131625127, 9);
    }

    public ItemCarModleAddBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.ivCarState = (ImageView) bindings[8];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (View) bindings[1];
        this.mboundView1.setTag(null);
        this.rlContent = (RelativeLayout) bindings[7];
        this.tvCarEngine = (TextView) bindings[3];
        this.tvCarEngine.setTag(null);
        this.tvComparison = (TextView) bindings[9];
        this.tvContent = (TextView) bindings[4];
        this.tvContent.setTag(null);
        this.tvPrice = (TextView) bindings[5];
        this.tvPrice.setTag(null);
        this.tvYear = (TextView) bindings[2];
        this.tvYear.setTag(null);
        this.vLine = (View) bindings[6];
        this.vLine.setTag(null);
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
            case 10:
                setCarseriesname((String) variable);
                return true;
            case 28:
                setInfo((CarModelInfo) variable);
                return true;
            case 46:
                setPosition((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CarModelInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CarModelInfo getInfo() {
        return this.mInfo;
    }

    public void setPosition(Integer Position) {
        this.mPosition = Position;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(46);
        super.requestRebind();
    }

    public Integer getPosition() {
        return this.mPosition;
    }

    public void setCarseriesname(String Carseriesname) {
        this.mCarseriesname = Carseriesname;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    public String getCarseriesname() {
        return this.mCarseriesname;
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
        boolean positionInt0 = false;
        String infoName = null;
        int infoPoslastflag = 0;
        int infoPosfirstflagInt1ViewVISIBLEViewGONE = 0;
        String infoGetGuidePrice = null;
        String infoGetCarPriceTextInfoGetGuidePrice = null;
        boolean infoPosfirstflagInt1 = false;
        String infoGetYear = null;
        int infoPoslastflagInt0ViewVISIBLEViewGONE = 0;
        String infoEngine = null;
        String infoGetCarPriceText = null;
        int infoPosfirstflagInt1InfoLocalshowyearBooleanFalsePositionInt0BooleanFalseViewVISIBLEViewGONE = 0;
        boolean infoLocalshowyear = false;
        int infoPosfirstflagInt1InfoLocalshowyearBooleanFalseViewVISIBLEViewGONE = 0;
        String carseriesnameJavaLangStringInfoName = null;
        CarModelInfo info = this.mInfo;
        Integer position = this.mPosition;
        int infoPosfirstflag = 0;
        String carseriesname = this.mCarseriesname;
        boolean infoPosfirstflagInt1InfoLocalshowyearBooleanFalse = false;
        if ((15 & dirtyFlags) != 0) {
            if (!((13 & dirtyFlags) == 0 || info == null)) {
                infoName = info.name;
            }
            if ((9 & dirtyFlags) != 0) {
                if (info != null) {
                    infoPoslastflag = info.poslastflag;
                    infoGetGuidePrice = info.getGuidePrice();
                    infoGetYear = info.getYear();
                    infoEngine = info.engine;
                    infoGetCarPriceText = info.getCarPriceText();
                }
                boolean infoPoslastflagInt0 = infoPoslastflag == 0;
                infoGetCarPriceTextInfoGetGuidePrice = infoGetCarPriceText + infoGetGuidePrice;
                if ((9 & dirtyFlags) != 0) {
                    if (infoPoslastflagInt0) {
                        dirtyFlags |= 512;
                    } else {
                        dirtyFlags |= 256;
                    }
                }
                infoPoslastflagInt0ViewVISIBLEViewGONE = infoPoslastflagInt0 ? 0 : 8;
            }
            if ((11 & dirtyFlags) != 0) {
                if (info != null) {
                    infoPosfirstflag = info.posfirstflag;
                }
                infoPosfirstflagInt1 = infoPosfirstflag == 1;
                if ((9 & dirtyFlags) != 0) {
                    if (infoPosfirstflagInt1) {
                        dirtyFlags |= 32;
                    } else {
                        dirtyFlags |= 16;
                    }
                }
                if ((11 & dirtyFlags) != 0) {
                    if (infoPosfirstflagInt1) {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_LEFT;
                    } else {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_FRONT_RIGHT;
                    }
                }
                if ((9 & dirtyFlags) != 0) {
                    infoPosfirstflagInt1ViewVISIBLEViewGONE = infoPosfirstflagInt1 ? 0 : 8;
                }
            }
        }
        if ((13 & dirtyFlags) != 0) {
            carseriesnameJavaLangStringInfoName = (carseriesname + " ") + infoName;
        }
        if (!((IjkMediaMeta.AV_CH_TOP_BACK_LEFT & dirtyFlags) == 0 || info == null)) {
            infoLocalshowyear = info.localshowyear;
        }
        if ((11 & dirtyFlags) != 0) {
            infoPosfirstflagInt1InfoLocalshowyearBooleanFalse = infoPosfirstflagInt1 ? infoLocalshowyear : false;
            if ((11 & dirtyFlags) != 0) {
                if (infoPosfirstflagInt1InfoLocalshowyearBooleanFalse) {
                    dirtyFlags |= 128;
                } else {
                    dirtyFlags |= 64;
                }
            }
            if ((9 & dirtyFlags) != 0) {
                if (infoPosfirstflagInt1InfoLocalshowyearBooleanFalse) {
                    dirtyFlags |= IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                } else {
                    dirtyFlags |= 4096;
                }
            }
            if ((9 & dirtyFlags) != 0) {
                infoPosfirstflagInt1InfoLocalshowyearBooleanFalseViewVISIBLEViewGONE = infoPosfirstflagInt1InfoLocalshowyearBooleanFalse ? 0 : 8;
            }
        }
        if ((128 & dirtyFlags) != 0) {
            positionInt0 = DynamicUtil.safeUnbox(position) > 0;
        }
        if ((11 & dirtyFlags) != 0) {
            boolean infoPosfirstflagInt1InfoLocalshowyearBooleanFalsePositionInt0BooleanFalse = infoPosfirstflagInt1InfoLocalshowyearBooleanFalse ? positionInt0 : false;
            if ((11 & dirtyFlags) != 0) {
                if (infoPosfirstflagInt1InfoLocalshowyearBooleanFalsePositionInt0BooleanFalse) {
                    dirtyFlags |= 2048;
                } else {
                    dirtyFlags |= 1024;
                }
            }
            infoPosfirstflagInt1InfoLocalshowyearBooleanFalsePositionInt0BooleanFalseViewVISIBLEViewGONE = infoPosfirstflagInt1InfoLocalshowyearBooleanFalsePositionInt0BooleanFalse ? 0 : 8;
        }
        if ((11 & dirtyFlags) != 0) {
            this.mboundView1.setVisibility(infoPosfirstflagInt1InfoLocalshowyearBooleanFalsePositionInt0BooleanFalseViewVISIBLEViewGONE);
        }
        if ((9 & dirtyFlags) != 0) {
            this.tvCarEngine.setVisibility(infoPosfirstflagInt1ViewVISIBLEViewGONE);
            TextViewBindingAdapter.setText(this.tvCarEngine, infoEngine);
            TextViewBindingAdapter.setText(this.tvPrice, infoGetCarPriceTextInfoGetGuidePrice);
            this.tvYear.setVisibility(infoPosfirstflagInt1InfoLocalshowyearBooleanFalseViewVISIBLEViewGONE);
            TextViewBindingAdapter.setText(this.tvYear, infoGetYear);
            this.vLine.setVisibility(infoPoslastflagInt0ViewVISIBLEViewGONE);
        }
        if ((13 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvContent, carseriesnameJavaLangStringInfoName);
        }
    }

    public static ItemCarModleAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarModleAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemCarModleAddBinding) DataBindingUtil.inflate(inflater, 2130903266, root, attachToRoot, bindingComponent);
    }

    public static ItemCarModleAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarModleAddBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903266, null, false), bindingComponent);
    }

    public static ItemCarModleAddBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCarModleAddBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_car_modle_add_0".equals(view.getTag())) {
            return new ItemCarModleAddBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
