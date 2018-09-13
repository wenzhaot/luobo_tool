package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;

public class ArticleRecommendItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View divider;
    public final AutoFrescoDraweeView image;
    public final TextView infoText;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final LinearLayout parent;
    public final TextView title;
    public final TextView userText;

    static {
        sViewsWithIds.put(2131624155, 1);
        sViewsWithIds.put(2131624015, 2);
        sViewsWithIds.put(2131624819, 3);
        sViewsWithIds.put(2131624820, 4);
        sViewsWithIds.put(2131624240, 5);
    }

    public ArticleRecommendItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.divider = (View) bindings[5];
        this.image = (AutoFrescoDraweeView) bindings[1];
        this.infoText = (TextView) bindings[4];
        this.parent = (LinearLayout) bindings[0];
        this.parent.setTag(null);
        this.title = (TextView) bindings[2];
        this.userText = (TextView) bindings[3];
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

    public static ArticleRecommendItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleRecommendItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ArticleRecommendItemBinding) DataBindingUtil.inflate(inflater, 2130903161, root, attachToRoot, bindingComponent);
    }

    public static ArticleRecommendItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleRecommendItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903161, null, false), bindingComponent);
    }

    public static ArticleRecommendItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleRecommendItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/article_recommend_item_0".equals(view.getTag())) {
            return new ArticleRecommendItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
