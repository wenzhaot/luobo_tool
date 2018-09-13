package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.library.emoticons.keyboard.XhsEmoticonsKeyBoard;
import com.feng.library.emoticons.keyboard.widget.UserDefEmoticonsEditText;

public class CommentDialogLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final UserDefEmoticonsEditText atContent;
    public final CheckBox cbMeanwhile;
    public final ScrollView closeKeyBoard;
    public final ImageButton ibDel;
    public final ImageView ivAddAt;
    public final ImageView ivAddEmoji;
    public final ImageView ivAddImage;
    public final ImageView ivFullScreen;
    public final AutoFrescoDraweeView ivImage;
    public final XhsEmoticonsKeyBoard kbDefEmoticons;
    public final LinearLayout llContent;
    private long mDirtyFlags = -1;
    public final RelativeLayout rlContentTextImage;
    public final RelativeLayout rlDialogLayoutComment;
    public final TextView tvCount;
    public final TextView tvSend;
    public final TextView tvTextCount;

    static {
        sViewsWithIds.put(2131624410, 1);
        sViewsWithIds.put(2131624609, 2);
        sViewsWithIds.put(2131624941, 3);
        sViewsWithIds.put(2131624605, 4);
        sViewsWithIds.put(2131624942, 5);
        sViewsWithIds.put(2131624438, 6);
        sViewsWithIds.put(2131624608, 7);
        sViewsWithIds.put(2131624610, 8);
        sViewsWithIds.put(2131624606, 9);
        sViewsWithIds.put(2131624943, 10);
        sViewsWithIds.put(2131624944, 11);
        sViewsWithIds.put(2131624945, 12);
        sViewsWithIds.put(2131624946, 13);
        sViewsWithIds.put(2131624947, 14);
        sViewsWithIds.put(2131624948, 15);
    }

    public CommentDialogLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds);
        this.atContent = (UserDefEmoticonsEditText) bindings[5];
        this.cbMeanwhile = (CheckBox) bindings[10];
        this.closeKeyBoard = (ScrollView) bindings[2];
        this.ibDel = (ImageButton) bindings[7];
        this.ivAddAt = (ImageView) bindings[12];
        this.ivAddEmoji = (ImageView) bindings[13];
        this.ivAddImage = (ImageView) bindings[11];
        this.ivFullScreen = (ImageView) bindings[8];
        this.ivImage = (AutoFrescoDraweeView) bindings[6];
        this.kbDefEmoticons = (XhsEmoticonsKeyBoard) bindings[0];
        this.kbDefEmoticons.setTag(null);
        this.llContent = (LinearLayout) bindings[1];
        this.rlContentTextImage = (RelativeLayout) bindings[4];
        this.rlDialogLayoutComment = (RelativeLayout) bindings[3];
        this.tvCount = (TextView) bindings[15];
        this.tvSend = (TextView) bindings[9];
        this.tvTextCount = (TextView) bindings[14];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1;
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
        return false;
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

    public static CommentDialogLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CommentDialogLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CommentDialogLayoutBinding) DataBindingUtil.inflate(inflater, 2130903198, root, attachToRoot, bindingComponent);
    }

    public static CommentDialogLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CommentDialogLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903198, null, false), bindingComponent);
    }

    public static CommentDialogLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CommentDialogLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/comment_dialog_layout_0".equals(view.getTag())) {
            return new CommentDialogLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
