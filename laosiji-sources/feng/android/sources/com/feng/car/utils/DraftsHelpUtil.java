package com.feng.car.utils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.feng.car.FengApplication;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.db.SparkDB;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.drafts.DraftsModel;
import com.feng.car.view.CommonDialog;
import java.util.ArrayList;
import java.util.List;

public class DraftsHelpUtil {
    private Context mContext;
    private String mDraftResourceID;
    private DraftsModel mDraftsModel;
    private int mFromType;
    private LayoutInflater mInflater;
    private boolean mIsHasLocalDraftsModel = false;
    private String mOldJson = "";
    private SparkDB mSparkDB;
    private int mType;
    private int mUserID;

    public DraftsHelpUtil(Context context, int type, int fromType, String draftResourceID) {
        this.mContext = context;
        this.mType = type;
        this.mFromType = fromType;
        this.mDraftResourceID = draftResourceID;
        init();
    }

    private void init() {
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mSparkDB = FengApplication.getInstance().getSparkDB();
        this.mUserID = FengApplication.getInstance().getUserInfo().id;
        this.mDraftsModel = this.mSparkDB.getDraftsById(this.mDraftResourceID, this.mUserID, this.mType);
        if (this.mDraftsModel == null) {
            this.mIsHasLocalDraftsModel = false;
        } else {
            this.mIsHasLocalDraftsModel = true;
        }
    }

    public boolean isHasLocalDraft() {
        return this.mIsHasLocalDraftsModel;
    }

    public DraftsModel getDraftsModel() {
        return this.mDraftsModel;
    }

    public void setOldJson(String oldJson) {
        this.mOldJson = oldJson;
    }

    public String getOldJson() {
        return this.mOldJson;
    }

    public void showSeverLocalDialog(final OnUserSelecsstListener listener) {
        View view = this.mInflater.inflate(2130903229, null);
        RadioGroup rgSelUse = (RadioGroup) view.findViewById(2131625008);
        final Dialog dialog = new Builder(this.mContext, 3).setView(view).setCancelable(false).create();
        rgSelUse.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case 2131625009:
                        dialog.dismiss();
                        listener.onSelected(1);
                        return;
                    case 2131625010:
                        dialog.dismiss();
                        listener.onSelected(2);
                        return;
                    default:
                        return;
                }
            }
        });
        dialog.show();
    }

    public void showSaveFailureDialog(final int type, final OnUserSelecsstListener listener) {
        String strHint = "";
        String strPostButton = "";
        String strNegativeButton = "";
        switch (type) {
            case 1:
                strHint = "帖子发布失败，再试一次?";
                strPostButton = "重新发布";
                strNegativeButton = "保存本地";
                break;
            case 2:
                strHint = "保存草稿失败，再试一次?";
                strPostButton = "重新保存";
                strNegativeButton = "保存本地";
                break;
            case 3:
                strHint = "草稿同步失败，再试一次";
                strPostButton = "重试";
                strNegativeButton = "退出";
                break;
        }
        new Builder(this.mContext, 3).setMessage(strHint).setPositiveButton(strPostButton, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.onSelected(3);
            }
        }).setNegativeButton(strNegativeButton, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (type == 3) {
                    listener.onSelected(5);
                } else {
                    listener.onSelected(4);
                }
            }
        }).create().show();
    }

    public void saveDraftsHint(Context context, OnDialogItemClickListener l) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("保存草稿", false, 1));
        list.add(new DialogItemEntity("不保存", true, 2));
        CommonDialog.showCommonDialog(context, "", list, l);
    }

    public void deleteLocalDrafts() {
        this.mSparkDB.delDrafts(this.mDraftResourceID, this.mUserID, this.mType);
    }
}
