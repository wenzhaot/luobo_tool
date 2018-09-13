package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.LevelConditionItemBinding;
import com.feng.car.entity.searchcar.SearchCarBean;
import java.util.List;

public class LevelConditionAdapter extends MvvmBaseAdapter<SearchCarBean, LevelConditionItemBinding> {
    private int[] selects = new int[]{R.drawable.icon_car_select, R.drawable.icon_suv_select, R.drawable.icon_mpv_select, R.drawable.icon_sports_select, R.drawable.icon_micro_select, R.drawable.icon_light_select, R.drawable.icon_trunk_select};
    private int[] unselects = new int[]{R.drawable.icon_car_unselect, R.drawable.icon_suv_unselect, R.drawable.icon_mpv_unselect, R.drawable.icon_sports_unselect, R.drawable.icon_micro_unselect, R.drawable.icon_light_unselect, R.drawable.icon_trunk_unselect};

    public LevelConditionAdapter(Context context, List<SearchCarBean> list) {
        super(context, list);
    }

    public LevelConditionItemBinding getBinding(ViewGroup parent, int viewType) {
        return LevelConditionItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(LevelConditionItemBinding levelConditionItemBinding, SearchCarBean searchCarBean) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<LevelConditionItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        if (((SearchCarBean) this.mList.get(position)).hasChecked()) {
            ((LevelConditionItemBinding) holder.binding).image.setImageResource(this.selects[position]);
        } else {
            ((LevelConditionItemBinding) holder.binding).image.setImageResource(this.unselects[position]);
        }
    }
}
