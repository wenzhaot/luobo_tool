package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.ComparisonView;
import com.feng.car.view.behavior.DisInterceptNestedScrollView;

public class ActivityNewSubjectBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView adNotice;
    public final AppBarLayout appBar;
    public final ImageView backImage;
    public final ImageButton ibConfig;
    public final ImageButton ibFollow;
    public final ImageButton ibPublish;
    public final ImageView ivEmpty;
    public final AutoFrescoDraweeView ivImage;
    public final AutoFrescoDraweeView ivImagePlace;
    public final ImageView ivSubjectDealer;
    public final ImageView ivSubjectGoods;
    public final ImageView ivSubjectModel;
    public final ImageView ivSubjectPic;
    public final ImageView ivSubjectPrice;
    public final ImageView ivSubjectTips;
    public final LinearLayout line1;
    public final LinearLayout line2;
    public final LinearLayout llCommand;
    public final LinearLayout llModel;
    public final LinearLayout llParent1;
    public final LinearLayout llPictures;
    public final LinearLayout llPrice;
    public final LinearLayout llRelativeGoods;
    public final LinearLayout llTips;
    private long mDirtyFlags = -1;
    private CarSeriesInfo mInfo;
    public final DisInterceptNestedScrollView middleLayout;
    public final ImageView optionImage;
    public final CoordinatorLayout parent;
    public final ProgressBar progressBar;
    public final ComparisonView rightCvPk;
    public final RelativeLayout rlTab;
    public final RelativeLayout rlTitleBar;
    public final TabLayout tabSubject;
    public final TextView tvAdLabel;
    public final TextView tvContentNum;
    public final TextView tvFollowerNum;
    public final TextView tvPicNum;
    public final TextView tvSort;
    public final TextView tvSubjectDealer;
    public final TextView tvSubjectDealerNum;
    public final TextView tvSubjectGoods;
    public final TextView tvSubjectGoodsNum;
    public final TextView tvSubjectModelNum;
    public final TextView tvSubjectName;
    public final TextView tvSubjectPic;
    public final TextView tvSubjectPicNum;
    public final TextView tvSubjectPreferential;
    public final TextView tvSubjectPriceNum;
    public final TextView tvSubjectPriceRange;
    public final TextView tvSubjectTips;
    public final TextView tvSubjectTipsNum;
    public final TextView tvTitleSubjectName;
    public final View vAdLine;
    public final View vLine;
    public final View vLineBottom;
    public final View vTabLine;
    public final ViewPager vpSubject;

    static {
        sViewsWithIds.put(2131624309, 10);
        sViewsWithIds.put(2131624438, 11);
        sViewsWithIds.put(2131624439, 12);
        sViewsWithIds.put(2131624440, 13);
        sViewsWithIds.put(2131624441, 14);
        sViewsWithIds.put(2131624442, 15);
        sViewsWithIds.put(2131624444, 16);
        sViewsWithIds.put(2131624446, 17);
        sViewsWithIds.put(2131624447, 18);
        sViewsWithIds.put(2131624445, 19);
        sViewsWithIds.put(2131623983, 20);
        sViewsWithIds.put(2131624448, 21);
        sViewsWithIds.put(2131624449, 22);
        sViewsWithIds.put(2131624452, 23);
        sViewsWithIds.put(2131624453, 24);
        sViewsWithIds.put(2131624456, 25);
        sViewsWithIds.put(2131624457, 26);
        sViewsWithIds.put(2131624458, 27);
        sViewsWithIds.put(2131624460, 28);
        sViewsWithIds.put(2131624461, 29);
        sViewsWithIds.put(2131624462, 30);
        sViewsWithIds.put(2131624463, 31);
        sViewsWithIds.put(2131624465, 32);
        sViewsWithIds.put(2131624466, 33);
        sViewsWithIds.put(2131624467, 34);
        sViewsWithIds.put(2131624469, 35);
        sViewsWithIds.put(2131624470, 36);
        sViewsWithIds.put(2131624471, 37);
        sViewsWithIds.put(2131624473, 38);
        sViewsWithIds.put(2131624474, 39);
        sViewsWithIds.put(2131624475, 40);
        sViewsWithIds.put(2131624476, 41);
        sViewsWithIds.put(2131624215, 42);
        sViewsWithIds.put(2131624418, 43);
        sViewsWithIds.put(2131624477, 44);
        sViewsWithIds.put(2131624297, 45);
        sViewsWithIds.put(2131624478, 46);
        sViewsWithIds.put(2131624421, 47);
        sViewsWithIds.put(2131624479, 48);
        sViewsWithIds.put(2131624428, 49);
        sViewsWithIds.put(2131624480, 50);
        sViewsWithIds.put(2131624481, 51);
        sViewsWithIds.put(2131624430, 52);
        sViewsWithIds.put(2131624482, 53);
        sViewsWithIds.put(2131624483, 54);
        sViewsWithIds.put(2131624484, 55);
    }

    public ActivityNewSubjectBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 56, sIncludes, sViewsWithIds);
        this.adNotice = (AutoFrescoDraweeView) bindings[39];
        this.appBar = (AppBarLayout) bindings[10];
        this.backImage = (ImageView) bindings[43];
        this.ibConfig = (ImageButton) bindings[18];
        this.ibFollow = (ImageButton) bindings[17];
        this.ibPublish = (ImageButton) bindings[55];
        this.ivEmpty = (ImageView) bindings[54];
        this.ivImage = (AutoFrescoDraweeView) bindings[11];
        this.ivImagePlace = (AutoFrescoDraweeView) bindings[13];
        this.ivSubjectDealer = (ImageView) bindings[36];
        this.ivSubjectGoods = (ImageView) bindings[26];
        this.ivSubjectModel = (ImageView) bindings[22];
        this.ivSubjectPic = (ImageView) bindings[30];
        this.ivSubjectPrice = (ImageView) bindings[24];
        this.ivSubjectTips = (ImageView) bindings[33];
        this.line1 = (LinearLayout) bindings[20];
        this.line2 = (LinearLayout) bindings[28];
        this.llCommand = (LinearLayout) bindings[35];
        this.llModel = (LinearLayout) bindings[21];
        this.llParent1 = (LinearLayout) bindings[48];
        this.llPictures = (LinearLayout) bindings[29];
        this.llPrice = (LinearLayout) bindings[23];
        this.llRelativeGoods = (LinearLayout) bindings[25];
        this.llTips = (LinearLayout) bindings[32];
        this.middleLayout = (DisInterceptNestedScrollView) bindings[12];
        this.optionImage = (ImageView) bindings[46];
        this.parent = (CoordinatorLayout) bindings[0];
        this.parent.setTag(null);
        this.progressBar = (ProgressBar) bindings[47];
        this.rightCvPk = (ComparisonView) bindings[45];
        this.rlTab = (RelativeLayout) bindings[49];
        this.rlTitleBar = (RelativeLayout) bindings[42];
        this.tabSubject = (TabLayout) bindings[50];
        this.tvAdLabel = (TextView) bindings[40];
        this.tvContentNum = (TextView) bindings[16];
        this.tvFollowerNum = (TextView) bindings[15];
        this.tvPicNum = (TextView) bindings[1];
        this.tvPicNum.setTag(null);
        this.tvSort = (TextView) bindings[51];
        this.tvSubjectDealer = (TextView) bindings[37];
        this.tvSubjectDealerNum = (TextView) bindings[9];
        this.tvSubjectDealerNum.setTag(null);
        this.tvSubjectGoods = (TextView) bindings[27];
        this.tvSubjectGoodsNum = (TextView) bindings[6];
        this.tvSubjectGoodsNum.setTag(null);
        this.tvSubjectModelNum = (TextView) bindings[2];
        this.tvSubjectModelNum.setTag(null);
        this.tvSubjectName = (TextView) bindings[14];
        this.tvSubjectPic = (TextView) bindings[31];
        this.tvSubjectPicNum = (TextView) bindings[7];
        this.tvSubjectPicNum.setTag(null);
        this.tvSubjectPreferential = (TextView) bindings[5];
        this.tvSubjectPreferential.setTag(null);
        this.tvSubjectPriceNum = (TextView) bindings[4];
        this.tvSubjectPriceNum.setTag(null);
        this.tvSubjectPriceRange = (TextView) bindings[3];
        this.tvSubjectPriceRange.setTag(null);
        this.tvSubjectTips = (TextView) bindings[34];
        this.tvSubjectTipsNum = (TextView) bindings[8];
        this.tvSubjectTipsNum.setTag(null);
        this.tvTitleSubjectName = (TextView) bindings[44];
        this.vAdLine = (View) bindings[41];
        this.vLine = (View) bindings[38];
        this.vLineBottom = (View) bindings[19];
        this.vTabLine = (View) bindings[52];
        this.vpSubject = (ViewPager) bindings[53];
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
            case 28:
                setInfo((CarSeriesInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CarSeriesInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CarSeriesInfo getInfo() {
        return this.mInfo;
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
        String infoGetImageCountSubject = null;
        String infoGetAutoDiscussCountSubject = null;
        String infoGetPriceCountSubject = null;
        String infoGetPreferentialNew = null;
        String infoGetCarPrice = null;
        String infoGetGoodsCountSubject = null;
        String infoGetSpecCountSubject = null;
        CarSeriesInfo info = this.mInfo;
        String infoGetDealerCountSubject = null;
        if (!((3 & dirtyFlags) == 0 || info == null)) {
            infoGetImageCountSubject = info.getImageCountSubject();
            infoGetAutoDiscussCountSubject = info.getAutoDiscussCountSubject();
            infoGetPriceCountSubject = info.getPriceCountSubject();
            infoGetPreferentialNew = info.getPreferentialNew();
            infoGetCarPrice = info.getCarPrice();
            infoGetGoodsCountSubject = info.getGoodsCountSubject();
            infoGetSpecCountSubject = info.getSpecCountSubject();
            infoGetDealerCountSubject = info.getDealerCountSubject();
        }
        if ((3 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvPicNum, infoGetImageCountSubject);
            TextViewBindingAdapter.setText(this.tvSubjectDealerNum, infoGetDealerCountSubject);
            TextViewBindingAdapter.setText(this.tvSubjectGoodsNum, infoGetGoodsCountSubject);
            TextViewBindingAdapter.setText(this.tvSubjectModelNum, infoGetSpecCountSubject);
            TextViewBindingAdapter.setText(this.tvSubjectPicNum, infoGetImageCountSubject);
            TextViewBindingAdapter.setText(this.tvSubjectPreferential, infoGetPreferentialNew);
            TextViewBindingAdapter.setText(this.tvSubjectPriceNum, infoGetPriceCountSubject);
            TextViewBindingAdapter.setText(this.tvSubjectPriceRange, infoGetCarPrice);
            TextViewBindingAdapter.setText(this.tvSubjectTipsNum, infoGetAutoDiscussCountSubject);
        }
    }

    public static ActivityNewSubjectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityNewSubjectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityNewSubjectBinding) DataBindingUtil.inflate(inflater, 2130903103, root, attachToRoot, bindingComponent);
    }

    public static ActivityNewSubjectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityNewSubjectBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903103, null, false), bindingComponent);
    }

    public static ActivityNewSubjectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityNewSubjectBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_new_subject_0".equals(view.getTag())) {
            return new ActivityNewSubjectBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
