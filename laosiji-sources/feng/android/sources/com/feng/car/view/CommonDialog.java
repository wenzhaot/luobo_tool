package com.feng.car.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.activity.VideoBaseActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.utils.FengUtil;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class CommonDialog {
    public static void showCommonDialog(final Context context, String dialogTitle, String dialogSecondTitle, String dialogBottomText, List<DialogItemEntity> list, OnDialogItemClickListener dialogItemClickListener, OnSingleClickListener onBottomSingleClickListener, boolean isCancelShow) {
        View commonDialogLayout = LayoutInflater.from(context).inflate(2130903202, null, false);
        final Dialog mCommonDialog = new Dialog(context, 2131361986);
        mCommonDialog.setCanceledOnTouchOutside(true);
        mCommonDialog.setCancelable(true);
        Window window = mCommonDialog.getWindow();
        window.setBackgroundDrawableResource(2131558540);
        window.setGravity(80);
        window.setWindowAnimations(2131362223);
        window.setContentView(commonDialogLayout);
        window.setLayout(-1, -2);
        TextView tvDialogTitle = (TextView) commonDialogLayout.findViewById(2131624958);
        TextView tvDialogSecondTitle = (TextView) commonDialogLayout.findViewById(2131624959);
        View v_line = commonDialogLayout.findViewById(2131624473);
        LinearLayout llDialogCancelLine = (LinearLayout) commonDialogLayout.findViewById(2131624962);
        View viewDialogCancelTopDivider = commonDialogLayout.findViewById(2131624961);
        if (!isCancelShow) {
            llDialogCancelLine.setVisibility(8);
            viewDialogCancelTopDivider.setVisibility(8);
        }
        if (StringUtil.isEmpty(dialogTitle)) {
            tvDialogTitle.setVisibility(8);
            v_line.setVisibility(8);
        } else {
            tvDialogTitle.setText(dialogTitle);
            tvDialogTitle.setVisibility(0);
            v_line.setVisibility(0);
            if (TextUtils.isEmpty(dialogSecondTitle)) {
                tvDialogSecondTitle.setVisibility(8);
            } else {
                tvDialogSecondTitle.setVisibility(0);
                tvDialogSecondTitle.setText(dialogSecondTitle);
                tvDialogTitle.setTextColor(ContextCompat.getColor(context, 2131558478));
            }
        }
        if (!TextUtils.isEmpty(dialogBottomText)) {
            ((TextView) commonDialogLayout.findViewById(2131624576)).setText(dialogBottomText);
        }
        final OnSingleClickListener onSingleClickListener = onBottomSingleClickListener;
        llDialogCancelLine.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (onSingleClickListener != null) {
                    onSingleClickListener.onClick(v);
                }
                if (mCommonDialog.isShowing()) {
                    mCommonDialog.dismiss();
                }
            }
        });
        final OnDialogItemClickListener onDialogItemClickListener = dialogItemClickListener;
        CommonBottomDialogAdapter mCommonBottomDialogAdapter = new CommonBottomDialogAdapter(context, list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (onDialogItemClickListener != null) {
                    onDialogItemClickListener.onItemClick(dialogItemEntity, position);
                }
                if (mCommonDialog.isShowing()) {
                    mCommonDialog.dismiss();
                }
            }
        });
        mCommonDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                if (context instanceof VideoBaseActivity) {
                    ((VideoBaseActivity) context).videoPlaying();
                }
            }
        });
        RecyclerView rcCommonDialogList = (RecyclerView) commonDialogLayout.findViewById(2131624960);
        rcCommonDialogList.setAdapter(mCommonBottomDialogAdapter);
        rcCommonDialogList.setLayoutManager(new LinearLayoutManager(context));
        mCommonDialog.show();
        if (context instanceof VideoBaseActivity) {
            ((VideoBaseActivity) context).videoPause();
        }
    }

    public static void showCommonDialog(Context context, String dialogTitle, String dialogSecondTitle, List<DialogItemEntity> list, OnDialogItemClickListener dialogItemClickListener, boolean isCancelShow) {
        showCommonDialog(context, dialogTitle, dialogSecondTitle, "", list, dialogItemClickListener, null, isCancelShow);
    }

    public static void showCommonDialog(Context context, String dialogTitle, List<DialogItemEntity> list, OnDialogItemClickListener dialogItemClickListener) {
        showCommonDialog(context, dialogTitle, "", list, dialogItemClickListener, true);
    }

    public static void showCommonDialog(Context context, String dialogTitle, List<DialogItemEntity> list, OnDialogItemClickListener dialogItemClickListener, OnSingleClickListener cancelListener) {
        showCommonDialog(context, dialogTitle, "", "", list, dialogItemClickListener, cancelListener, true);
    }

    public static void showCommonDialog(Context context, String dialogTitle, String dialogSecondTitle, List<DialogItemEntity> list, OnDialogItemClickListener dialogItemClickListener) {
        showCommonDialog(context, dialogTitle, dialogSecondTitle, list, dialogItemClickListener, true);
    }

    public static Dialog showViewDialog(Context context, View view) {
        Dialog mCommonDialog = new Dialog(context, 2131361986);
        mCommonDialog.setCanceledOnTouchOutside(true);
        mCommonDialog.setCancelable(true);
        Window window = mCommonDialog.getWindow();
        window.setBackgroundDrawableResource(2131558540);
        window.setGravity(80);
        window.setWindowAnimations(2131362223);
        window.setContentView(view);
        window.setLayout(FengUtil.getScreenWidth(context), -2);
        return mCommonDialog;
    }

    public static Dialog showViewDialog(Context context, View view, int gravity, boolean canCancel, boolean needAnim) {
        Dialog mCommonDialog = new Dialog(context, 2131361986);
        mCommonDialog.setCanceledOnTouchOutside(canCancel);
        mCommonDialog.setCancelable(true);
        Window window = mCommonDialog.getWindow();
        window.setGravity(gravity);
        if (needAnim) {
            window.setWindowAnimations(2131362223);
        }
        window.setContentView(view);
        window.setLayout(-1, -2);
        return mCommonDialog;
    }

    public static Dialog showViewDialog(Context context, View view, int gravity, boolean canCancel, boolean needAnim, boolean cancelable) {
        Dialog mCommonDialog = new Dialog(context, 2131361986);
        mCommonDialog.setCanceledOnTouchOutside(canCancel);
        mCommonDialog.setCancelable(cancelable);
        Window window = mCommonDialog.getWindow();
        window.setGravity(gravity);
        if (needAnim) {
            window.setWindowAnimations(2131362223);
        }
        window.setContentView(view);
        window.setLayout(-1, -2);
        return mCommonDialog;
    }
}
