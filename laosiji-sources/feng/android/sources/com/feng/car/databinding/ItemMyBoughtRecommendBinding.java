package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.car.OldDriverVoiceInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.UserRelationView;
import com.feng.car.view.VoiceBoxView;

public class ItemMyBoughtRecommendBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private OldDriverVoiceInfo mMOldDriverVoiceInfo;
    private final LinearLayout mboundView0;
    public final TextView tvCarModelName;
    public final TextView tvCarPrice;
    public final TextView tvFollow;
    public final TextView tvUserName;
    public final TextView tvUserSignature;
    public final UserRelationView urvBought;
    public final AutoFrescoDraweeView userImage;
    public final VoiceBoxView vbvBought;

    static {
        sViewsWithIds.put(2131625029, 5);
        sViewsWithIds.put(2131624802, 6);
        sViewsWithIds.put(2131625152, 7);
        sViewsWithIds.put(2131625153, 8);
    }

    public ItemMyBoughtRecommendBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 3);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCarModelName = (TextView) bindings[1];
        this.tvCarModelName.setTag(null);
        this.tvCarPrice = (TextView) bindings[5];
        this.tvFollow = (TextView) bindings[3];
        this.tvFollow.setTag(null);
        this.tvUserName = (TextView) bindings[2];
        this.tvUserName.setTag(null);
        this.tvUserSignature = (TextView) bindings[4];
        this.tvUserSignature.setTag(null);
        this.urvBought = (UserRelationView) bindings[7];
        this.userImage = (AutoFrescoDraweeView) bindings[6];
        this.vbvBought = (VoiceBoxView) bindings[8];
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
            case 35:
                setMOldDriverVoiceInfo((OldDriverVoiceInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setMOldDriverVoiceInfo(OldDriverVoiceInfo MOldDriverVoiceInfo) {
        this.mMOldDriverVoiceInfo = MOldDriverVoiceInfo;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(35);
        super.requestRebind();
    }

    public OldDriverVoiceInfo getMOldDriverVoiceInfo() {
        return this.mMOldDriverVoiceInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeMOldDriverVoiceInfoAuthoruserName((ObservableField) object, fieldId);
            case 1:
                return onChangeMOldDriverVoiceInfoCarsName((ObservableField) object, fieldId);
            case 2:
                return onChangeMOldDriverVoiceInfoAuthoruserIsfollow((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeMOldDriverVoiceInfoAuthoruserName(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeMOldDriverVoiceInfoCarsName(ObservableField<String> observableField, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 2;
                }
                return true;
            default:
                return false;
        }
    }

    private boolean onChangeMOldDriverVoiceInfoAuthoruserIsfollow(ObservableInt MOldDriverVoiceInfoAuthoruserIsfollow, int fieldId) {
        switch (fieldId) {
            case 0:
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
        ObservableField<String> mOldDriverVoiceInfoAuthoruserName = null;
        String mOldDriverVoiceInfoCarsNameGet = null;
        int mOldDriverVoiceInfoAuthoruserIsfollowGet = 0;
        int mOldDriverVoiceInfoAuthoruserIsfollowInt0ViewVISIBLEViewGONE = 0;
        Drawable mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        CarSeriesInfo mOldDriverVoiceInfoCars = null;
        ObservableField<String> mOldDriverVoiceInfoCarsName = null;
        UserInfo mOldDriverVoiceInfoAuthoruser = null;
        ObservableInt mOldDriverVoiceInfoAuthoruserIsfollow = null;
        CarModelInfo mOldDriverVoiceInfoCarx = null;
        String mOldDriverVoiceInfoCarxName = null;
        String stringFormatTvCarModelNameAndroidStringRecommendCarNameMOldDriverVoiceInfoCarsNameMOldDriverVoiceInfoCarxName = null;
        OldDriverVoiceInfo mOldDriverVoiceInfo = this.mMOldDriverVoiceInfo;
        String mOldDriverVoiceInfoAuthoruserNameGet = null;
        boolean mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticated = false;
        String mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticatedInfo = null;
        if ((31 & dirtyFlags) != 0) {
            if ((26 & dirtyFlags) != 0) {
                if (mOldDriverVoiceInfo != null) {
                    mOldDriverVoiceInfoCars = mOldDriverVoiceInfo.cars;
                    mOldDriverVoiceInfoCarx = mOldDriverVoiceInfo.carx;
                }
                if (mOldDriverVoiceInfoCars != null) {
                    mOldDriverVoiceInfoCarsName = mOldDriverVoiceInfoCars.name;
                }
                updateRegistration(1, mOldDriverVoiceInfoCarsName);
                if (mOldDriverVoiceInfoCarx != null) {
                    mOldDriverVoiceInfoCarxName = mOldDriverVoiceInfoCarx.name;
                }
                if (mOldDriverVoiceInfoCarsName != null) {
                    mOldDriverVoiceInfoCarsNameGet = (String) mOldDriverVoiceInfoCarsName.get();
                }
                stringFormatTvCarModelNameAndroidStringRecommendCarNameMOldDriverVoiceInfoCarsNameMOldDriverVoiceInfoCarxName = String.format(this.tvCarModelName.getResources().getString(2131231451), new Object[]{mOldDriverVoiceInfoCarsNameGet, mOldDriverVoiceInfoCarxName});
            }
            if ((29 & dirtyFlags) != 0) {
                if (mOldDriverVoiceInfo != null) {
                    mOldDriverVoiceInfoAuthoruser = mOldDriverVoiceInfo.authoruser;
                }
                if ((25 & dirtyFlags) != 0) {
                    if (mOldDriverVoiceInfoAuthoruser != null) {
                        mOldDriverVoiceInfoAuthoruserName = mOldDriverVoiceInfoAuthoruser.name;
                    }
                    updateRegistration(0, mOldDriverVoiceInfoAuthoruserName);
                    if (mOldDriverVoiceInfoAuthoruserName != null) {
                        mOldDriverVoiceInfoAuthoruserNameGet = (String) mOldDriverVoiceInfoAuthoruserName.get();
                    }
                }
                if ((28 & dirtyFlags) != 0) {
                    if (mOldDriverVoiceInfoAuthoruser != null) {
                        mOldDriverVoiceInfoAuthoruserIsfollow = mOldDriverVoiceInfoAuthoruser.isfollow;
                    }
                    updateRegistration(2, mOldDriverVoiceInfoAuthoruserIsfollow);
                    if (mOldDriverVoiceInfoAuthoruserIsfollow != null) {
                        mOldDriverVoiceInfoAuthoruserIsfollowGet = mOldDriverVoiceInfoAuthoruserIsfollow.get();
                    }
                    boolean mOldDriverVoiceInfoAuthoruserIsfollowInt0 = mOldDriverVoiceInfoAuthoruserIsfollowGet != 0;
                    if ((28 & dirtyFlags) != 0) {
                        if (mOldDriverVoiceInfoAuthoruserIsfollowInt0) {
                            dirtyFlags |= 64;
                        } else {
                            dirtyFlags |= 32;
                        }
                    }
                    mOldDriverVoiceInfoAuthoruserIsfollowInt0ViewVISIBLEViewGONE = mOldDriverVoiceInfoAuthoruserIsfollowInt0 ? 0 : 8;
                }
                if ((24 & dirtyFlags) != 0) {
                    if (mOldDriverVoiceInfoAuthoruser != null) {
                        mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticated = mOldDriverVoiceInfoAuthoruser.getIsFirstAuthenticated();
                        mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticatedInfo = mOldDriverVoiceInfoAuthoruser.getIsFirstAuthenticatedInfo();
                    }
                    if ((24 & dirtyFlags) != 0) {
                        if (mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticated) {
                            dirtyFlags |= 256;
                        } else {
                            dirtyFlags |= 128;
                        }
                    }
                    mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticated ? getDrawableFromResource(this.tvUserName, 2130837629) : null;
                }
            }
        }
        if ((26 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvCarModelName, stringFormatTvCarModelNameAndroidStringRecommendCarNameMOldDriverVoiceInfoCarsNameMOldDriverVoiceInfoCarxName);
        }
        if ((28 & dirtyFlags) != 0) {
            this.tvFollow.setVisibility(mOldDriverVoiceInfoAuthoruserIsfollowInt0ViewVISIBLEViewGONE);
        }
        if ((24 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.tvUserName, mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvUserName, mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setText(this.tvUserSignature, mOldDriverVoiceInfoAuthoruserGetIsFirstAuthenticatedInfo);
        }
        if ((25 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvUserName, mOldDriverVoiceInfoAuthoruserNameGet);
        }
    }

    public static ItemMyBoughtRecommendBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemMyBoughtRecommendBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemMyBoughtRecommendBinding) DataBindingUtil.inflate(inflater, 2130903277, root, attachToRoot, bindingComponent);
    }

    public static ItemMyBoughtRecommendBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemMyBoughtRecommendBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903277, null, false), bindingComponent);
    }

    public static ItemMyBoughtRecommendBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemMyBoughtRecommendBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_my_bought_recommend_0".equals(view.getTag())) {
            return new ItemMyBoughtRecommendBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
