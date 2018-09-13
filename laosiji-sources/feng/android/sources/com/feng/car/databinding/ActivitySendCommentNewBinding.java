package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.library.emoticons.keyboard.XhsEmoticonsKeyBoard;
import com.feng.library.emoticons.keyboard.widget.UserDefEmoticonsEditText;

public class ActivitySendCommentNewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View closeKeyBoard;
    public final UserDefEmoticonsEditText etDefEmoticonsText;
    public final ImageButton ibDel;
    public final ImageView ivFullScreen;
    public final AutoFrescoDraweeView ivImage;
    public final XhsEmoticonsKeyBoard kbDefEmoticons;
    private long mDirtyFlags = -1;
    private int mUnWanted;
    public final RelativeLayout rlContentTextImage;
    public final RelativeLayout rlLayoutComment;
    public final TextView tvSend;

    static {
        sViewsWithIds.put(2131624604, 1);
        sViewsWithIds.put(2131624605, 2);
        sViewsWithIds.put(2131624607, 3);
        sViewsWithIds.put(2131624438, 4);
        sViewsWithIds.put(2131624608, 5);
        sViewsWithIds.put(2131624609, 6);
        sViewsWithIds.put(2131624610, 7);
        sViewsWithIds.put(2131624606, 8);
    }

    public ActivitySendCommentNewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.closeKeyBoard = (View) bindings[6];
        this.etDefEmoticonsText = (UserDefEmoticonsEditText) bindings[3];
        this.ibDel = (ImageButton) bindings[5];
        this.ivFullScreen = (ImageView) bindings[7];
        this.ivImage = (AutoFrescoDraweeView) bindings[4];
        this.kbDefEmoticons = (XhsEmoticonsKeyBoard) bindings[0];
        this.kbDefEmoticons.setTag(null);
        this.rlContentTextImage = (RelativeLayout) bindings[2];
        this.rlLayoutComment = (RelativeLayout) bindings[1];
        this.tvSend = (TextView) bindings[8];
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
            case 70:
                setUnWanted(((Integer) variable).intValue());
                return true;
            default:
                return false;
        }
    }

    public void setUnWanted(int UnWanted) {
        this.mUnWanted = UnWanted;
    }

    public int getUnWanted() {
        return this.mUnWanted;
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

    public static ActivitySendCommentNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySendCommentNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySendCommentNewBinding) DataBindingUtil.inflate(inflater, 2130903132, root, attachToRoot, bindingComponent);
    }

    public static ActivitySendCommentNewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySendCommentNewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903132, null, false), bindingComponent);
    }

    public static ActivitySendCommentNewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySendCommentNewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_send_comment_new_0".equals(view.getTag())) {
            return new ActivitySendCommentNewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
