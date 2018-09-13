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
import com.feng.car.view.ScrollerBottomView;
import com.feng.library.R;
import com.feng.library.emoticons.keyboard.XhsEmoticonsKeyBoard;

public class PostInitActivityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = new IncludedLayouts(33);
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ActivitySelectArticleCoverBinding includeCover;
    public final ImageView ivAddGoods;
    public final ImageView ivAddImg;
    public final ImageView ivAddService;
    public final ImageView ivAddTopic;
    public final ImageView ivClose;
    public final ImageView ivIcon;
    public final ImageView ivIconGoods;
    public final XhsEmoticonsKeyBoard kbDefEmoticons;
    public final LinearLayout llItem;
    public final ScrollerBottomView llItemExpend;
    private long mDirtyFlags = -1;
    private String mStr;
    public final RecyclerView recyclerView;
    public final RelativeLayout rlAddGoods;
    public final RelativeLayout rlAddService;
    public final RelativeLayout rlBottomMenu;
    public final RelativeLayout rlParent;
    public final RelativeLayout rlTitleBar;
    public final TextView tvAddGoods;
    public final TextView tvAddImg;
    public final TextView tvAddMore;
    public final TextView tvAddService;
    public final TextView tvAddTopic;
    public final TextView tvGoodsNum;
    public final TextView tvRightTitle;
    public final TextView tvServiceNum;
    public final TextView tvSort;
    public final TextView tvTakePhoto;
    public final View vBottonPlace;
    public final View vCloseMenu;
    public final View vImgLine;
    public final View vLineBottom;
    public final View vTakeLine;
    public final View vTopicLine;

    static {
        sIncludes.setIncludes(1, new String[]{"activity_select_article_cover"}, new int[]{2}, new int[]{2130903129});
        sViewsWithIds.put(2131624215, 3);
        sViewsWithIds.put(2131624993, 4);
        sViewsWithIds.put(2131625368, 5);
        sViewsWithIds.put(2131624481, 6);
        sViewsWithIds.put(2131625369, 7);
        sViewsWithIds.put(2131624249, 8);
        sViewsWithIds.put(2131625370, 9);
        sViewsWithIds.put(2131624445, 10);
        sViewsWithIds.put(2131625371, 11);
        sViewsWithIds.put(2131625372, 12);
        sViewsWithIds.put(2131625373, 13);
        sViewsWithIds.put(2131625374, 14);
        sViewsWithIds.put(2131625375, 15);
        sViewsWithIds.put(2131625376, 16);
        sViewsWithIds.put(2131625377, 17);
        sViewsWithIds.put(R.id.iv_icon, 18);
        sViewsWithIds.put(2131625378, 19);
        sViewsWithIds.put(2131625379, 20);
        sViewsWithIds.put(2131625380, 21);
        sViewsWithIds.put(2131625381, 22);
        sViewsWithIds.put(2131625382, 23);
        sViewsWithIds.put(2131625383, 24);
        sViewsWithIds.put(2131625384, 25);
        sViewsWithIds.put(2131625385, 26);
        sViewsWithIds.put(2131625386, 27);
        sViewsWithIds.put(2131625387, 28);
        sViewsWithIds.put(2131625388, 29);
        sViewsWithIds.put(2131625389, 30);
        sViewsWithIds.put(2131625390, 31);
        sViewsWithIds.put(2131625391, 32);
    }

    public PostInitActivityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds);
        this.includeCover = (ActivitySelectArticleCoverBinding) bindings[2];
        setContainedBinding(this.includeCover);
        this.ivAddGoods = (ImageView) bindings[30];
        this.ivAddImg = (ImageView) bindings[27];
        this.ivAddService = (ImageView) bindings[29];
        this.ivAddTopic = (ImageView) bindings[28];
        this.ivClose = (ImageView) bindings[4];
        this.ivIcon = (ImageView) bindings[18];
        this.ivIconGoods = (ImageView) bindings[22];
        this.kbDefEmoticons = (XhsEmoticonsKeyBoard) bindings[0];
        this.kbDefEmoticons.setTag(null);
        this.llItem = (LinearLayout) bindings[26];
        this.llItemExpend = (ScrollerBottomView) bindings[9];
        this.recyclerView = (RecyclerView) bindings[8];
        this.rlAddGoods = (RelativeLayout) bindings[21];
        this.rlAddService = (RelativeLayout) bindings[17];
        this.rlBottomMenu = (RelativeLayout) bindings[25];
        this.rlParent = (RelativeLayout) bindings[1];
        this.rlParent.setTag(null);
        this.rlTitleBar = (RelativeLayout) bindings[3];
        this.tvAddGoods = (TextView) bindings[23];
        this.tvAddImg = (TextView) bindings[11];
        this.tvAddMore = (TextView) bindings[31];
        this.tvAddService = (TextView) bindings[19];
        this.tvAddTopic = (TextView) bindings[15];
        this.tvGoodsNum = (TextView) bindings[24];
        this.tvRightTitle = (TextView) bindings[5];
        this.tvServiceNum = (TextView) bindings[20];
        this.tvSort = (TextView) bindings[6];
        this.tvTakePhoto = (TextView) bindings[13];
        this.vBottonPlace = (View) bindings[7];
        this.vCloseMenu = (View) bindings[32];
        this.vImgLine = (View) bindings[12];
        this.vLineBottom = (View) bindings[10];
        this.vTakeLine = (View) bindings[14];
        this.vTopicLine = (View) bindings[16];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
        }
        this.includeCover.invalidateAll();
        requestRebind();
    }

    /* JADX WARNING: Missing block: B:8:0x0013, code:
            if (r6.includeCover.hasPendingBindings() != false) goto L_?;
     */
    /* JADX WARNING: Missing block: B:18:?, code:
            return true;
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            return false;
     */
    public boolean hasPendingBindings() {
        /*
        r6 = this;
        r0 = 1;
        monitor-enter(r6);
        r2 = r6.mDirtyFlags;	 Catch:{ all -> 0x0017 }
        r4 = 0;
        r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r1 == 0) goto L_0x000c;
    L_0x000a:
        monitor-exit(r6);	 Catch:{ all -> 0x0017 }
    L_0x000b:
        return r0;
    L_0x000c:
        monitor-exit(r6);	 Catch:{ all -> 0x0017 }
        r1 = r6.includeCover;
        r1 = r1.hasPendingBindings();
        if (r1 != 0) goto L_0x000b;
    L_0x0015:
        r0 = 0;
        goto L_0x000b;
    L_0x0017:
        r0 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0017 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.databinding.PostInitActivityBinding.hasPendingBindings():boolean");
    }

    public boolean setVariable(int variableId, Object variable) {
        switch (variableId) {
            case 65:
                setStr((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setStr(String Str) {
        this.mStr = Str;
    }

    public String getStr() {
        return this.mStr;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeIncludeCover((ActivitySelectArticleCoverBinding) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeIncludeCover(ActivitySelectArticleCoverBinding IncludeCover, int fieldId) {
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
        synchronized (this) {
            long dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        executeBindingsOn(this.includeCover);
    }

    public static PostInitActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PostInitActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PostInitActivityBinding) DataBindingUtil.inflate(inflater, 2130903348, root, attachToRoot, bindingComponent);
    }

    public static PostInitActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PostInitActivityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903348, null, false), bindingComponent);
    }

    public static PostInitActivityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PostInitActivityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/post_init_activity_0".equals(view.getTag())) {
            return new PostInitActivityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
