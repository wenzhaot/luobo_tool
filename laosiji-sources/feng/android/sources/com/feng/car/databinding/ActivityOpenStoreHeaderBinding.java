package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingListener;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged;
import android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged;
import android.databinding.adapters.TextViewBindingAdapter.OnTextChanged;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.dealer.ShopRegisterInfo;

public class ActivityOpenStoreHeaderBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final Button btDone;
    public final EditText etCompany;
    public final EditText etCompanyAddress;
    private InverseBindingListener etCompanyAddressandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(ActivityOpenStoreHeaderBinding.this.etCompanyAddress);
            ShopRegisterInfo shopRegisterInfo = ActivityOpenStoreHeaderBinding.this.mShopRegisterInfo;
            if (shopRegisterInfo != null) {
                shopRegisterInfo.setShopaddress(callbackArg_0);
            }
        }
    };
    private InverseBindingListener etCompanyandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(ActivityOpenStoreHeaderBinding.this.etCompany);
            ShopRegisterInfo shopRegisterInfo = ActivityOpenStoreHeaderBinding.this.mShopRegisterInfo;
            if (shopRegisterInfo != null) {
                shopRegisterInfo.setShopname(callbackArg_0);
            }
        }
    };
    public final EditText etPhoneNumber;
    private InverseBindingListener etPhoneNumberandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(ActivityOpenStoreHeaderBinding.this.etPhoneNumber);
            ShopRegisterInfo shopRegisterInfo = ActivityOpenStoreHeaderBinding.this.mShopRegisterInfo;
            if (shopRegisterInfo != null) {
                shopRegisterInfo.setMobile(callbackArg_0);
            }
        }
    };
    public final EditText etSummary;
    private InverseBindingListener etSummaryandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(ActivityOpenStoreHeaderBinding.this.etSummary);
            ShopRegisterInfo shopRegisterInfo = ActivityOpenStoreHeaderBinding.this.mShopRegisterInfo;
            if (shopRegisterInfo != null) {
                shopRegisterInfo.setDescription(callbackArg_0);
            }
        }
    };
    public final EditText etTitle;
    private InverseBindingListener etTitleandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(ActivityOpenStoreHeaderBinding.this.etTitle);
            ShopRegisterInfo shopRegisterInfo = ActivityOpenStoreHeaderBinding.this.mShopRegisterInfo;
            if (shopRegisterInfo != null) {
                shopRegisterInfo.setPosition(callbackArg_0);
            }
        }
    };
    public final EditText etUsername;
    private InverseBindingListener etUsernameandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(ActivityOpenStoreHeaderBinding.this.etUsername);
            ShopRegisterInfo shopRegisterInfo = ActivityOpenStoreHeaderBinding.this.mShopRegisterInfo;
            if (shopRegisterInfo != null) {
                shopRegisterInfo.setRealname(callbackArg_0);
            }
        }
    };
    public final EditText etVerifyCode;
    private InverseBindingListener etVerifyCodeandroidTextAttrChanged = new InverseBindingListener() {
        public void onChange() {
            String callbackArg_0 = TextViewBindingAdapter.getTextString(ActivityOpenStoreHeaderBinding.this.etVerifyCode);
            ShopRegisterInfo shopRegisterInfo = ActivityOpenStoreHeaderBinding.this.mShopRegisterInfo;
            if (shopRegisterInfo != null) {
                shopRegisterInfo.setLocalVerify(callbackArg_0);
            }
        }
    };
    public final ImageView ivArrow;
    public final ImageView ivArrowQr;
    public final ImageView ivIdCardDoneIcon;
    public final ImageView ivQrDoneIcon;
    public final LinearLayout llParent;
    private long mDirtyFlags = -1;
    private ShopRegisterInfo mShopRegisterInfo;
    public final RadioButton rbStoreFemale;
    public final RadioButton rbStoreMale;
    public final RecyclerView recyclerView;
    public final RadioGroup rgStoreGender;
    public final RelativeLayout rlSaleType;
    public final RelativeLayout rlUploadIdCard;
    public final RelativeLayout rlUploadQr;
    public final TextView tvCompanyAddressTip;
    public final TextView tvCompanyTip;
    public final TextView tvGenderTip;
    public final TextView tvLoginSendVerify;
    public final TextView tvPhoneTip;
    public final TextView tvSaleType;
    public final TextView tvSaleTypeTip;
    public final TextView tvSummaryTip;
    public final TextView tvTitleTip;
    public final TextView tvUsernameTip;
    public final TextView tvVerifyTip;

    static {
        sViewsWithIds.put(2131624249, 9);
        sViewsWithIds.put(2131624485, 10);
        sViewsWithIds.put(2131624487, 11);
        sViewsWithIds.put(2131624488, 12);
        sViewsWithIds.put(2131624489, 13);
        sViewsWithIds.put(2131624490, 14);
        sViewsWithIds.put(2131624491, 15);
        sViewsWithIds.put(2131624493, 16);
        sViewsWithIds.put(2131624495, 17);
        sViewsWithIds.put(2131624387, 18);
        sViewsWithIds.put(2131624497, 19);
        sViewsWithIds.put(2131624498, 20);
        sViewsWithIds.put(2131624499, 21);
        sViewsWithIds.put(2131624500, 22);
        sViewsWithIds.put(2131624501, 23);
        sViewsWithIds.put(2131624502, 24);
        sViewsWithIds.put(2131624503, 25);
        sViewsWithIds.put(2131624505, 26);
        sViewsWithIds.put(2131624507, 27);
        sViewsWithIds.put(2131624509, 28);
        sViewsWithIds.put(2131624510, 29);
        sViewsWithIds.put(2131624511, 30);
    }

    public ActivityOpenStoreHeaderBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 31, sIncludes, sViewsWithIds);
        this.btDone = (Button) bindings[8];
        this.btDone.setTag(null);
        this.etCompany = (EditText) bindings[5];
        this.etCompany.setTag(null);
        this.etCompanyAddress = (EditText) bindings[6];
        this.etCompanyAddress.setTag(null);
        this.etPhoneNumber = (EditText) bindings[3];
        this.etPhoneNumber.setTag(null);
        this.etSummary = (EditText) bindings[2];
        this.etSummary.setTag(null);
        this.etTitle = (EditText) bindings[7];
        this.etTitle.setTag(null);
        this.etUsername = (EditText) bindings[1];
        this.etUsername.setTag(null);
        this.etVerifyCode = (EditText) bindings[4];
        this.etVerifyCode.setTag(null);
        this.ivArrow = (ImageView) bindings[20];
        this.ivArrowQr = (ImageView) bindings[29];
        this.ivIdCardDoneIcon = (ImageView) bindings[21];
        this.ivQrDoneIcon = (ImageView) bindings[30];
        this.llParent = (LinearLayout) bindings[0];
        this.llParent.setTag(null);
        this.rbStoreFemale = (RadioButton) bindings[14];
        this.rbStoreMale = (RadioButton) bindings[13];
        this.recyclerView = (RecyclerView) bindings[9];
        this.rgStoreGender = (RadioGroup) bindings[12];
        this.rlSaleType = (RelativeLayout) bindings[22];
        this.rlUploadIdCard = (RelativeLayout) bindings[19];
        this.rlUploadQr = (RelativeLayout) bindings[28];
        this.tvCompanyAddressTip = (TextView) bindings[26];
        this.tvCompanyTip = (TextView) bindings[25];
        this.tvGenderTip = (TextView) bindings[11];
        this.tvLoginSendVerify = (TextView) bindings[18];
        this.tvPhoneTip = (TextView) bindings[16];
        this.tvSaleType = (TextView) bindings[24];
        this.tvSaleTypeTip = (TextView) bindings[23];
        this.tvSummaryTip = (TextView) bindings[15];
        this.tvTitleTip = (TextView) bindings[27];
        this.tvUsernameTip = (TextView) bindings[10];
        this.tvVerifyTip = (TextView) bindings[17];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 512;
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
            case 56:
                setShopRegisterInfo((ShopRegisterInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setShopRegisterInfo(ShopRegisterInfo ShopRegisterInfo) {
        updateRegistration(1, ShopRegisterInfo);
        this.mShopRegisterInfo = ShopRegisterInfo;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(56);
        super.requestRebind();
    }

    public ShopRegisterInfo getShopRegisterInfo() {
        return this.mShopRegisterInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeShopRegisterInfoLocalFlag((ObservableBoolean) object, fieldId);
            case 1:
                return onChangeShopRegisterInfo((ShopRegisterInfo) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeShopRegisterInfoLocalFlag(ObservableBoolean ShopRegisterInfoLocalFlag, int fieldId) {
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

    private boolean onChangeShopRegisterInfo(ShopRegisterInfo ShopRegisterInfo, int fieldId) {
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
            case 31:
                synchronized (this) {
                    this.mDirtyFlags |= 32;
                }
                return true;
            case 41:
                synchronized (this) {
                    this.mDirtyFlags |= 16;
                }
                return true;
            case 46:
                synchronized (this) {
                    this.mDirtyFlags |= 256;
                }
                return true;
            case 48:
                synchronized (this) {
                    this.mDirtyFlags |= 4;
                }
                return true;
            case 57:
                synchronized (this) {
                    this.mDirtyFlags |= 128;
                }
                return true;
            case 58:
                synchronized (this) {
                    this.mDirtyFlags |= 64;
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
        ObservableBoolean shopRegisterInfoLocalFlag = null;
        String shopRegisterInfoShopaddress = null;
        String shopRegisterInfoLocalVerify = null;
        ShopRegisterInfo shopRegisterInfo = this.mShopRegisterInfo;
        String shopRegisterInfoRealname = null;
        String shopRegisterInfoPosition = null;
        String shopRegisterInfoShopname = null;
        String shopRegisterInfoDescription = null;
        String shopRegisterInfoMobile = null;
        boolean shopRegisterInfoLocalFlagGet = false;
        if ((1023 & dirtyFlags) != 0) {
            if ((515 & dirtyFlags) != 0) {
                if (shopRegisterInfo != null) {
                    shopRegisterInfoLocalFlag = shopRegisterInfo.localFlag;
                }
                updateRegistration(0, shopRegisterInfoLocalFlag);
                if (shopRegisterInfoLocalFlag != null) {
                    shopRegisterInfoLocalFlagGet = shopRegisterInfoLocalFlag.get();
                }
            }
            if (!((642 & dirtyFlags) == 0 || shopRegisterInfo == null)) {
                shopRegisterInfoShopaddress = shopRegisterInfo.getShopaddress();
            }
            if (!((546 & dirtyFlags) == 0 || shopRegisterInfo == null)) {
                shopRegisterInfoLocalVerify = shopRegisterInfo.getLocalVerify();
            }
            if (!((518 & dirtyFlags) == 0 || shopRegisterInfo == null)) {
                shopRegisterInfoRealname = shopRegisterInfo.getRealname();
            }
            if (!((770 & dirtyFlags) == 0 || shopRegisterInfo == null)) {
                shopRegisterInfoPosition = shopRegisterInfo.getPosition();
            }
            if (!((578 & dirtyFlags) == 0 || shopRegisterInfo == null)) {
                shopRegisterInfoShopname = shopRegisterInfo.getShopname();
            }
            if (!((522 & dirtyFlags) == 0 || shopRegisterInfo == null)) {
                shopRegisterInfoDescription = shopRegisterInfo.getDescription();
            }
            if (!((530 & dirtyFlags) == 0 || shopRegisterInfo == null)) {
                shopRegisterInfoMobile = shopRegisterInfo.getMobile();
            }
        }
        if ((515 & dirtyFlags) != 0) {
            this.btDone.setEnabled(shopRegisterInfoLocalFlagGet);
        }
        if ((578 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.etCompany, shopRegisterInfoShopname);
        }
        if ((512 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setTextWatcher(this.etCompany, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etCompanyandroidTextAttrChanged);
            TextViewBindingAdapter.setTextWatcher(this.etCompanyAddress, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etCompanyAddressandroidTextAttrChanged);
            TextViewBindingAdapter.setTextWatcher(this.etPhoneNumber, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etPhoneNumberandroidTextAttrChanged);
            TextViewBindingAdapter.setTextWatcher(this.etSummary, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etSummaryandroidTextAttrChanged);
            TextViewBindingAdapter.setTextWatcher(this.etTitle, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etTitleandroidTextAttrChanged);
            TextViewBindingAdapter.setTextWatcher(this.etUsername, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etUsernameandroidTextAttrChanged);
            TextViewBindingAdapter.setTextWatcher(this.etVerifyCode, (BeforeTextChanged) null, (OnTextChanged) null, (AfterTextChanged) null, this.etVerifyCodeandroidTextAttrChanged);
        }
        if ((642 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.etCompanyAddress, shopRegisterInfoShopaddress);
        }
        if ((530 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.etPhoneNumber, shopRegisterInfoMobile);
        }
        if ((522 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.etSummary, shopRegisterInfoDescription);
        }
        if ((770 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.etTitle, shopRegisterInfoPosition);
        }
        if ((518 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.etUsername, shopRegisterInfoRealname);
        }
        if ((546 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.etVerifyCode, shopRegisterInfoLocalVerify);
        }
    }

    public static ActivityOpenStoreHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityOpenStoreHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityOpenStoreHeaderBinding) DataBindingUtil.inflate(inflater, 2130903105, root, attachToRoot, bindingComponent);
    }

    public static ActivityOpenStoreHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityOpenStoreHeaderBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903105, null, false), bindingComponent);
    }

    public static ActivityOpenStoreHeaderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityOpenStoreHeaderBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_open_store_header_0".equals(view.getTag())) {
            return new ActivityOpenStoreHeaderBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
