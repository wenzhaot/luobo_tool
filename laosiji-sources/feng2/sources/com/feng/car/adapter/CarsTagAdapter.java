package com.feng.car.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.activity.HomeActivity;
import com.feng.car.databinding.PriceRankingTagItemBinding;
import com.feng.car.entity.search.SearchItem;
import java.util.List;

public class CarsTagAdapter extends MvvmBaseAdapter<SearchItem, PriceRankingTagItemBinding> {
    private boolean mIsHome = false;

    public CarsTagAdapter(Context context, List list) {
        super(context, list);
        if (this.mContext instanceof HomeActivity) {
            this.mIsHome = true;
        }
    }

    public PriceRankingTagItemBinding getBinding(ViewGroup parent, int viewType) {
        return PriceRankingTagItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(PriceRankingTagItemBinding priceRankingTagItemBinding, SearchItem searchCarItem) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<PriceRankingTagItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        SearchItem item = (SearchItem) this.mList.get(position);
        if (this.mIsHome) {
            ((PriceRankingTagItemBinding) holder.binding).image.setImageResource(R.drawable.icon_reconds_history);
            ((PriceRankingTagItemBinding) holder.binding).name.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_87_000000));
        } else {
            ((PriceRankingTagItemBinding) holder.binding).image.setImageResource(R.drawable.icon_rmb_bag);
            ((PriceRankingTagItemBinding) holder.binding).name.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_33A4F7));
        }
        ((PriceRankingTagItemBinding) holder.binding).name.setText(item.content);
    }
}
