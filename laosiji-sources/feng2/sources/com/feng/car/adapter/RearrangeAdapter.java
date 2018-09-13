package com.feng.car.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.databinding.ItemRearrangeImageBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.recyclerdrag.MyItemTouchCallback$ItemTouchAdapter;
import com.feng.car.utils.FengUtil;
import com.feng.car.video.player.JCUtils;
import java.util.Collections;
import java.util.List;

public class RearrangeAdapter extends Adapter<ViewHolder> implements MyItemTouchCallback$ItemTouchAdapter {
    private Context mContext;
    private List<PostEdit> mList;
    private int mViewLengthOfSide = ((this.mContext.getResources().getDisplayMetrics().widthPixels - this.mContext.getResources().getDimensionPixelSize(R.dimen.default_60PX)) / 4);

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ViewDataBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RearrangeAdapter(Context context, List<PostEdit> list) {
        this.mContext = context;
        this.mList = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRearrangeImageBinding.inflate(LayoutInflater.from(this.mContext), parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        PostEdit postEdit = (PostEdit) this.mList.get(position);
        ItemRearrangeImageBinding binding = holder.binding;
        binding.setInfo(postEdit);
        LayoutParams params = (LayoutParams) binding.simpledraweeview.getLayoutParams();
        params.height = this.mViewLengthOfSide;
        binding.simpledraweeview.setLayoutParams(params);
        ImageInfo imageInfo;
        if (postEdit.getType() == 2) {
            imageInfo = new ImageInfo();
            imageInfo.width = Integer.parseInt(postEdit.getWidth());
            imageInfo.height = Integer.parseInt(postEdit.getHeight());
            imageInfo.hash = postEdit.getHash();
            imageInfo.url = postEdit.srcUrl;
            binding.simpledraweeview.setAutoImageURI(Uri.parse(FengUtil.getSingleNineScaleUrl(imageInfo, 200)));
            binding.tvTime.setVisibility(8);
        } else if (postEdit.getType() == 3) {
            if (TextUtils.isEmpty(postEdit.videoCoverImage.srcUrl)) {
                binding.simpledraweeview.setAutoImageURI(Uri.parse("res://com.feng.car/2130838457"));
            } else {
                imageInfo = new ImageInfo();
                imageInfo.width = Integer.parseInt(postEdit.videoCoverImage.getWidth());
                imageInfo.height = Integer.parseInt(postEdit.videoCoverImage.getHeight());
                imageInfo.hash = postEdit.videoCoverImage.getHash();
                imageInfo.url = postEdit.videoCoverImage.srcUrl;
                binding.simpledraweeview.setAutoImageURI(Uri.parse(FengUtil.getSingleNineScaleUrl(imageInfo, 200)));
            }
            binding.tvTime.setVisibility(0);
            binding.tvTime.setText(JCUtils.stringForTime(postEdit.getTime()));
        }
    }

    public int getItemCount() {
        return this.mList.size();
    }

    public void onMove(int fromPosition, int toPosition) {
        int i;
        if (fromPosition < toPosition) {
            for (i = fromPosition; i < toPosition; i++) {
                Collections.swap(this.mList, i, i + 1);
            }
        } else {
            for (i = fromPosition; i > toPosition; i--) {
                Collections.swap(this.mList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void onSwiped(int position) {
    }

    public void onChange() {
    }
}
