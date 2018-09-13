package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.MsgPraiseItemBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.thread.GratuityRecordInfo;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.text.MessageFormat;
import java.util.List;

public class PraiseAdapter extends MvvmBaseAdapter<GratuityRecordInfo, MsgPraiseItemBinding> {
    private int m120 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_120PX);

    public PraiseAdapter(Context con, List<GratuityRecordInfo> list) {
        super(con, list);
    }

    public MsgPraiseItemBinding getBinding(ViewGroup parent, int viewType) {
        return MsgPraiseItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(MsgPraiseItemBinding msgPraiseItemBinding, GratuityRecordInfo gratuityRecordInfo) {
        msgPraiseItemBinding.setUserInfo(gratuityRecordInfo.user);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<MsgPraiseItemBinding> holder, int position) {
        ImageInfo imageInfo;
        super.onBaseBindViewHolder(holder, position);
        final GratuityRecordInfo gratuityRecord = (GratuityRecordInfo) this.mList.get(position);
        ((MsgPraiseItemBinding) holder.binding).tvUserPraiseTimestamp.setText((CharSequence) gratuityRecord.time.get());
        ((MsgPraiseItemBinding) holder.binding).tvThreadDetailUsername.setVisibility(0);
        if (gratuityRecord.resourcetype == 5) {
            if (TextUtils.isEmpty((CharSequence) gratuityRecord.comment.sns.user.name.get())) {
                ((MsgPraiseItemBinding) holder.binding).tvThreadDetailUsername.setVisibility(8);
            } else {
                ((MsgPraiseItemBinding) holder.binding).tvThreadDetailUsername.setText(MessageFormat.format(this.mContext.getString(R.string.at_user_name), new Object[]{gratuityRecord.comment.sns.user.name.get()}));
            }
            ((MsgPraiseItemBinding) holder.binding).tvThreadDetailContent.setText(gratuityRecord.comment.sns.getQuoteDescription());
            imageInfo = gratuityRecord.comment.sns.getQuoteImageInfo();
            ((MsgPraiseItemBinding) holder.binding).tvPraiseDetail.setText(R.string.praise_comment);
            ((MsgPraiseItemBinding) holder.binding).avCommentContent.setVisibility(0);
            ((MsgPraiseItemBinding) holder.binding).avCommentContent.setContent("@" + ((String) gratuityRecord.comment.user.name.get()) + "：" + ((String) gratuityRecord.comment.content.get()), true, gratuityRecord.comment.imagelist, gratuityRecord.comment.videolist, null);
            ((MsgPraiseItemBinding) holder.binding).llDetailContainer.setBackgroundResource(R.color.color_f7f7f7);
        } else {
            if (TextUtils.isEmpty((CharSequence) gratuityRecord.sns.user.name.get())) {
                ((MsgPraiseItemBinding) holder.binding).tvThreadDetailUsername.setVisibility(8);
            } else {
                ((MsgPraiseItemBinding) holder.binding).tvThreadDetailUsername.setText(MessageFormat.format(this.mContext.getString(R.string.at_user_name), new Object[]{gratuityRecord.sns.user.name.get()}));
            }
            ((MsgPraiseItemBinding) holder.binding).tvThreadDetailContent.setText(gratuityRecord.sns.getQuoteDescription());
            imageInfo = gratuityRecord.sns.getQuoteImageInfo();
            if (gratuityRecord.sns.snstype == 0 || gratuityRecord.sns.snstype == 1) {
                ((MsgPraiseItemBinding) holder.binding).tvPraiseDetail.setText(R.string.praise_post);
            } else if (gratuityRecord.sns.snstype == 9 || gratuityRecord.sns.snstype == 10) {
                ((MsgPraiseItemBinding) holder.binding).tvPraiseDetail.setText(R.string.praise_discuss);
            }
            ((MsgPraiseItemBinding) holder.binding).avCommentContent.setVisibility(8);
            ((MsgPraiseItemBinding) holder.binding).llDetailContainer.setBackgroundResource(R.color.color_ffffff);
        }
        ((MsgPraiseItemBinding) holder.binding).fdvThreadDetailImg.setImageURI(Uri.parse(FengUtil.getFixedSizeUrl(imageInfo, this.m120, this.m120)));
        ((MsgPraiseItemBinding) holder.binding).fdvUserPraisePortrait.setHeadUrl(FengUtil.getHeadImageUrl(gratuityRecord.user.getHeadImageInfo()));
        ((MsgPraiseItemBinding) holder.binding).llPraiseContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SnsInfo snsInfo;
                if (gratuityRecord.resourcetype == 5) {
                    snsInfo = gratuityRecord.comment.sns;
                } else {
                    snsInfo = gratuityRecord.sns;
                }
                if (snsInfo.snstype == 0 || snsInfo.snstype == 1) {
                    snsInfo.intentToArticle(PraiseAdapter.this.mContext, -1);
                } else if (snsInfo.snstype == 9) {
                    snsInfo.intentToViewPoint(PraiseAdapter.this.mContext, false);
                }
            }
        });
        ((MsgPraiseItemBinding) holder.binding).rlUserInfo.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                gratuityRecord.user.intentToPersonalHome(PraiseAdapter.this.mContext);
            }
        });
    }
}
