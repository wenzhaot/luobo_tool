package com.feng.car.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager.LayoutParams;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.SendStoreMediaLayoutBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.recyclerdrag.MyItemTouchCallback$ItemTouchAdapter;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.Collections;
import java.util.List;

public class OpenStoreAddPicAdapter extends Adapter<ViewHolder> implements MyItemTouchCallback$ItemTouchAdapter {
    private Context mContext;
    private List<PostEdit> mList;
    private MicroMediaOperationListener mOperationListener;
    private int mWidth = ((FengUtil.getScreenWidth(this.mContext) - (this.mContext.getResources().getDimensionPixelSize(R.dimen.default_54PX) * 2)) / 3);

    public interface MicroMediaOperationListener {
        void onAdd();

        void onOption(int i);
    }

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

    public OpenStoreAddPicAdapter(Context context, List<PostEdit> list) {
        this.mList = list;
        this.mContext = context;
    }

    public void setOperationListener(MicroMediaOperationListener mOperationListener) {
        this.mOperationListener = mOperationListener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(SendStoreMediaLayoutBinding.inflate(LayoutInflater.from(this.mContext), parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        PostEdit postEdit = (PostEdit) this.mList.get(position);
        SendStoreMediaLayoutBinding binding = holder.binding;
        if (postEdit.getType() == 2) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.width = Integer.parseInt(postEdit.getWidth());
            imageInfo.height = Integer.parseInt(postEdit.getHeight());
            imageInfo.hash = postEdit.getHash();
            imageInfo.url = postEdit.srcUrl;
            String imageUrl = FengUtil.getSingleNineScaleUrl(imageInfo, 200);
            binding.image.setTag(imageUrl);
            binding.image.setAutoImageURI(Uri.parse(imageUrl));
            binding.image.setVisibility(0);
            binding.image.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (OpenStoreAddPicAdapter.this.mOperationListener != null) {
                        OpenStoreAddPicAdapter.this.mOperationListener.onOption(position);
                    }
                }
            });
            binding.addImage.setVisibility(8);
        } else {
            binding.image.setVisibility(8);
            binding.addImage.setVisibility(0);
            binding.addImage.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (OpenStoreAddPicAdapter.this.mOperationListener != null) {
                        OpenStoreAddPicAdapter.this.mOperationListener.onAdd();
                    }
                }
            });
        }
        binding.getRoot().setLayoutParams(new LayoutParams(this.mWidth, (this.mWidth * 3) / 4));
    }

    public int getItemCount() {
        return this.mList.size();
    }

    public void onMove(int fromPosition, int toPosition) {
        PostEdit postEdit1 = (PostEdit) this.mList.get(toPosition);
        if (((PostEdit) this.mList.get(fromPosition)).getType() == 2 && postEdit1.getType() == 2) {
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
    }

    public void onSwiped(int position) {
    }

    public void onChange() {
    }
}
