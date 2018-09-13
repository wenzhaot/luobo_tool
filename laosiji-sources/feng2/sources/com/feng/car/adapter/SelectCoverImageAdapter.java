package com.feng.car.adapter;

import android.content.Context;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.SelectCoverItemBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.utils.FengUtil;
import com.feng.car.video.player.JCUtils;
import java.util.List;

public class SelectCoverImageAdapter extends MvvmBaseAdapter<PostEdit, SelectCoverItemBinding> {
    private ObservableInt mSelectPosition = new ObservableInt(-1);
    private int padding = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_8PX);
    private int width = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_135PX);

    public void setSelectPosition(int p) {
        this.mSelectPosition.set(p);
    }

    public SelectCoverImageAdapter(Context context, List<PostEdit> list) {
        super(context, list);
    }

    public SelectCoverItemBinding getBinding(ViewGroup parent, int viewType) {
        return SelectCoverItemBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(SelectCoverItemBinding selectCoverItemBinding, PostEdit postEdit) {
        selectCoverItemBinding.setSelpos(this.mSelectPosition);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<SelectCoverItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        PostEdit postEdit = (PostEdit) this.mList.get(position);
        ((SelectCoverItemBinding) holder.binding).setIndext(Integer.valueOf(position));
        if (position == 0) {
            ((SelectCoverItemBinding) holder.binding).headerView.setVisibility(0);
            ((SelectCoverItemBinding) holder.binding).footerView.setVisibility(8);
        } else if (position == this.mList.size() - 1) {
            ((SelectCoverItemBinding) holder.binding).headerView.setVisibility(8);
            ((SelectCoverItemBinding) holder.binding).footerView.setVisibility(0);
        } else {
            ((SelectCoverItemBinding) holder.binding).headerView.setVisibility(8);
            ((SelectCoverItemBinding) holder.binding).footerView.setVisibility(8);
        }
        ((SelectCoverItemBinding) holder.binding).imageLine.setPadding(this.padding / 2, this.padding / 2, this.padding / 2, this.padding / 2);
        ImageInfo imageInfo;
        if (isVideo(postEdit)) {
            if (TextUtils.isEmpty(postEdit.videoCoverImage.srcUrl)) {
                ((SelectCoverItemBinding) holder.binding).image.setAutoImageURI(Uri.parse("res://com.feng.car/2130838457"));
            } else {
                imageInfo = new ImageInfo();
                imageInfo.width = Integer.parseInt(postEdit.videoCoverImage.getWidth());
                imageInfo.height = Integer.parseInt(postEdit.videoCoverImage.getHeight());
                imageInfo.url = postEdit.videoCoverImage.srcUrl;
                ((SelectCoverItemBinding) holder.binding).image.setDraweeImage(FengUtil.getUniformScaleUrl(imageInfo, 640), this.width, this.width);
            }
            ((SelectCoverItemBinding) holder.binding).timeText.setVisibility(0);
            ((SelectCoverItemBinding) holder.binding).timeText.setText(JCUtils.stringForTime(postEdit.getTime()));
        } else {
            imageInfo = new ImageInfo();
            imageInfo.width = Integer.parseInt(postEdit.getWidth());
            imageInfo.height = Integer.parseInt(postEdit.getHeight());
            imageInfo.url = postEdit.srcUrl;
            ((SelectCoverItemBinding) holder.binding).image.setDraweeImage(FengUtil.getUniformScaleUrl(imageInfo, 640), this.width, this.width);
            ((SelectCoverItemBinding) holder.binding).timeText.setVisibility(8);
        }
        if (position == this.mList.size() - 1) {
            ((SelectCoverItemBinding) holder.binding).parentLine.setPadding(0, 0, 0, 0);
        } else {
            ((SelectCoverItemBinding) holder.binding).parentLine.setPadding(0, 0, this.padding / 2, 0);
        }
    }

    private boolean isVideo(PostEdit postEdit) {
        return postEdit.getType() == 3;
    }
}
