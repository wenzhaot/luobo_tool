package com.feng.car.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.CommentDetailItemLayoutBinding;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.listener.CommentItemListener;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;
import java.util.UUID;

public class CommentReplyAdapter extends MvvmBaseAdapter<CommentInfo, CommentDetailItemLayoutBinding> {
    private int mCommentID;
    private SnsInfo mSnsInfo;
    private String mSoleKey = "";

    public void setSnsInfo(SnsInfo snsInfo) {
        this.mSnsInfo = snsInfo;
    }

    public CommentReplyAdapter(Context context, int commentID, List<CommentInfo> list) {
        super(context, list);
        this.mCommentID = commentID;
        this.mSoleKey = UUID.randomUUID().toString();
    }

    public String getSoleKey() {
        return this.mSoleKey;
    }

    public CommentDetailItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return CommentDetailItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<CommentDetailItemLayoutBinding> holder, int position) {
        final CommentInfo info = (CommentInfo) this.mList.get(position);
        if (info.isLocalExtend) {
            ((CommentDetailItemLayoutBinding) holder.binding).vExtend.setVisibility(0);
            return;
        }
        ((CommentDetailItemLayoutBinding) holder.binding).vExtend.setVisibility(8);
        if (this.mSnsInfo != null) {
            info.sns = this.mSnsInfo;
        }
        if (info.replyuser.id <= 0 || TextUtils.isEmpty((CharSequence) info.replyuser.name.get())) {
            ((CommentDetailItemLayoutBinding) holder.binding).content.setContent((String) info.content.get(), true, info.imagelist, info.videolist, null);
        } else {
            ((CommentDetailItemLayoutBinding) holder.binding).content.setContent("回复@" + ((String) info.replyuser.name.get()) + "：" + ((String) info.content.get()), true, info.imagelist, info.videolist, null);
        }
        ((CommentDetailItemLayoutBinding) holder.binding).topText.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ((CommentDetailItemLayoutBinding) holder.binding).topText.setEnabled(false);
                info.clickPraiseOperation(CommentReplyAdapter.this.mContext, new SuccessFailCallback() {
                    public void onSuccess() {
                        super.onSuccess();
                        ((CommentDetailItemLayoutBinding) holder.binding).topText.setEnabled(true);
                    }

                    public void onFail() {
                        super.onFail();
                        ((CommentDetailItemLayoutBinding) holder.binding).topText.setEnabled(true);
                    }
                });
            }
        });
        ((CommentDetailItemLayoutBinding) holder.binding).userImage.setHeadUrl(FengUtil.getHeadImageUrl(info.user.getHeadImageInfo()));
        ((CommentDetailItemLayoutBinding) holder.binding).userImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                info.user.intentToPersonalHome(CommentReplyAdapter.this.mContext);
            }
        });
        if (this.mCommentID == 0 || this.mCommentID != info.id) {
            ((CommentDetailItemLayoutBinding) holder.binding).parentLine.setBackgroundResource(R.drawable.ffffff_5000000_selector);
        } else {
            ((CommentDetailItemLayoutBinding) holder.binding).parentLine.setBackgroundResource(R.drawable.fff9e8_faedce_selector);
        }
        ((CommentDetailItemLayoutBinding) holder.binding).parentLine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                FengUtil.showCommentOperationDialog(CommentReplyAdapter.this.mContext, info, CommentReplyAdapter.this.mSnsInfo, true, ((CommentDetailItemLayoutBinding) holder.binding).parentLine.getHeight() + CommentReplyAdapter.this.getY(((CommentDetailItemLayoutBinding) holder.binding).parentLine), CommentReplyAdapter.this.mSoleKey, new CommentItemListener() {
                    public void onItemClick() {
                        CommentInfo commentInfo = new CommentInfo();
                        commentInfo.isLocalExtend = true;
                        CommentReplyAdapter.this.mList.add(commentInfo);
                        CommentReplyAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];
    }

    public void dataBindingTo(CommentDetailItemLayoutBinding commentDetailItemLayoutBinding, CommentInfo commentInfo) {
        commentDetailItemLayoutBinding.setCommentInfo(commentInfo);
        commentDetailItemLayoutBinding.setUserInfo(commentInfo.user);
    }
}
