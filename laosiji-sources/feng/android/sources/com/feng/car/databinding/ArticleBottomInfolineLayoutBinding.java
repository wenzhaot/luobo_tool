package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.convenientbanner.ConvenientBanner;
import com.feng.car.view.tagview.TagCloudView;

public class ArticleBottomInfolineLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView adGoods;
    public final AutoFrescoDraweeView adNotice;
    public final ConvenientBanner banner;
    public final View bannerDivider;
    public final View bottomDivider;
    public final ImageView followImage;
    public final ImageView icon;
    public final ImageView ivArrow;
    public final RecyclerView listView;
    public final LinearLayout llCommodity;
    public final LinearLayout llNum;
    public final LinearLayout llServeList;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final View middleDivider;
    public final TextView moreText;
    public final TextView publishTime;
    public final RelativeLayout recommandLine;
    public final TextView recommandText;
    public final RelativeLayout rlAd;
    public final RelativeLayout rlGoodsInfo;
    public final RelativeLayout rlInfo;
    public final RecyclerView rvCommodity;
    public final RecyclerView rvServeList;
    public final TagCloudView tgView;
    public final TextView tvAdLabel;
    public final TextView tvGoodsName;
    public final TextView tvInShop;
    public final TextView tvOnSaleNum;
    public final TextView tvPlayNum;
    public final TextView tvPrice;
    public final TextView tvSaleNum;
    public final TextView tvStoreUserName;
    public final TextView tvTextTip;
    public final TextView tvUserInfo;
    public final TextView tvViewedGoods;
    public final TextView tvViewedNum;
    public final View vGoodsLine;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624754, 1);
        sViewsWithIds.put(2131624755, 2);
        sViewsWithIds.put(2131624756, 3);
        sViewsWithIds.put(2131624757, 4);
        sViewsWithIds.put(2131624758, 5);
        sViewsWithIds.put(2131624759, 6);
        sViewsWithIds.put(2131624760, 7);
        sViewsWithIds.put(2131624761, 8);
        sViewsWithIds.put(2131624762, 9);
        sViewsWithIds.put(2131624763, 10);
        sViewsWithIds.put(2131624498, 11);
        sViewsWithIds.put(2131624764, 12);
        sViewsWithIds.put(2131624765, 13);
        sViewsWithIds.put(2131624766, 14);
        sViewsWithIds.put(2131624767, 15);
        sViewsWithIds.put(2131624769, 16);
        sViewsWithIds.put(2131624768, 17);
        sViewsWithIds.put(2131624770, 18);
        sViewsWithIds.put(2131624771, 19);
        sViewsWithIds.put(2131624772, 20);
        sViewsWithIds.put(2131624773, 21);
        sViewsWithIds.put(2131624473, 22);
        sViewsWithIds.put(2131624774, 23);
        sViewsWithIds.put(2131624775, 24);
        sViewsWithIds.put(2131624776, 25);
        sViewsWithIds.put(2131624777, 26);
        sViewsWithIds.put(2131624778, 27);
        sViewsWithIds.put(2131624779, 28);
        sViewsWithIds.put(2131624474, 29);
        sViewsWithIds.put(2131624475, 30);
        sViewsWithIds.put(2131624780, 31);
        sViewsWithIds.put(2131624158, 32);
        sViewsWithIds.put(2131624781, 33);
        sViewsWithIds.put(2131624752, 34);
        sViewsWithIds.put(2131624782, 35);
        sViewsWithIds.put(2131624537, 36);
        sViewsWithIds.put(2131624783, 37);
    }

    public ArticleBottomInfolineLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 38, sIncludes, sViewsWithIds);
        this.adGoods = (AutoFrescoDraweeView) bindings[10];
        this.adNotice = (AutoFrescoDraweeView) bindings[29];
        this.banner = (ConvenientBanner) bindings[25];
        this.bannerDivider = (View) bindings[26];
        this.bottomDivider = (View) bindings[37];
        this.followImage = (ImageView) bindings[34];
        this.icon = (ImageView) bindings[32];
        this.ivArrow = (ImageView) bindings[11];
        this.listView = (RecyclerView) bindings[36];
        this.llCommodity = (LinearLayout) bindings[4];
        this.llNum = (LinearLayout) bindings[18];
        this.llServeList = (LinearLayout) bindings[5];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.middleDivider = (View) bindings[27];
        this.moreText = (TextView) bindings[35];
        this.publishTime = (TextView) bindings[2];
        this.recommandLine = (RelativeLayout) bindings[31];
        this.recommandText = (TextView) bindings[33];
        this.rlAd = (RelativeLayout) bindings[28];
        this.rlGoodsInfo = (RelativeLayout) bindings[9];
        this.rlInfo = (RelativeLayout) bindings[14];
        this.rvCommodity = (RecyclerView) bindings[23];
        this.rvServeList = (RecyclerView) bindings[7];
        this.tgView = (TagCloudView) bindings[1];
        this.tvAdLabel = (TextView) bindings[30];
        this.tvGoodsName = (TextView) bindings[12];
        this.tvInShop = (TextView) bindings[17];
        this.tvOnSaleNum = (TextView) bindings[19];
        this.tvPlayNum = (TextView) bindings[3];
        this.tvPrice = (TextView) bindings[8];
        this.tvSaleNum = (TextView) bindings[20];
        this.tvStoreUserName = (TextView) bindings[15];
        this.tvTextTip = (TextView) bindings[6];
        this.tvUserInfo = (TextView) bindings[16];
        this.tvViewedGoods = (TextView) bindings[13];
        this.tvViewedNum = (TextView) bindings[21];
        this.vGoodsLine = (View) bindings[24];
        this.vLine = (View) bindings[22];
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
            case 72:
                setUnwanted((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUnwanted(Integer Unwanted) {
        this.mUnwanted = Unwanted;
    }

    public Integer getUnwanted() {
        return this.mUnwanted;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        synchronized (this) {
            long dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
    }

    public static ArticleBottomInfolineLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleBottomInfolineLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ArticleBottomInfolineLayoutBinding) DataBindingUtil.inflate(inflater, 2130903155, root, attachToRoot, bindingComponent);
    }

    public static ArticleBottomInfolineLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleBottomInfolineLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903155, null, false), bindingComponent);
    }

    public static ArticleBottomInfolineLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleBottomInfolineLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/article_bottom_infoline_layout_0".equals(view.getTag())) {
            return new ArticleBottomInfolineLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
