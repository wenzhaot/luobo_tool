package com.feng.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.SearchcarTypegroupItemBinding;
import com.feng.car.entity.searchcar.SearchCarTypeGroup;
import com.feng.car.utils.SearchCarManager;
import java.util.List;

public class SearchCarTypeGroupAdapter extends MvvmBaseAdapter<SearchCarTypeGroup, SearchcarTypegroupItemBinding> {
    public SearchCarTypeGroupAdapter(Context context, List<SearchCarTypeGroup> list) {
        super(context, list);
    }

    public SearchcarTypegroupItemBinding getBinding(ViewGroup parent, int viewType) {
        return SearchcarTypegroupItemBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(SearchcarTypegroupItemBinding searchcarTypegroupItemBinding, SearchCarTypeGroup searchCarTypeGroup) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<SearchcarTypegroupItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        SearchCarTypeGroup group = (SearchCarTypeGroup) this.mList.get(position);
        initImageId(group);
        ((SearchcarTypegroupItemBinding) holder.binding).image.setImageResource(group.image);
        if (position == this.mList.size() - 1) {
            ((SearchcarTypegroupItemBinding) holder.binding).divier.setVisibility(8);
        } else {
            ((SearchcarTypegroupItemBinding) holder.binding).divier.setVisibility(0);
        }
        if (group.isSelect) {
            ((SearchcarTypegroupItemBinding) holder.binding).parent.setBackgroundResource(R.color.color_ffffff);
            SearchCarManager.newInstance().setGroupPosition(position);
            return;
        }
        ((SearchcarTypegroupItemBinding) holder.binding).parent.setBackgroundResource(R.color.color_f3f3f3);
    }

    private void initImageId(SearchCarTypeGroup group) {
        if (group.name.equals("基本参数")) {
            group.image = R.drawable.group_jibencanshu;
        } else if (group.name.equals("车身参数")) {
            group.image = R.drawable.group_cheshencanshu;
        } else if (group.name.equals("动力参数")) {
            group.image = R.drawable.group_donglicanshu;
        } else if (group.name.equals("变速箱")) {
            group.image = R.drawable.group_biansuxiang;
        } else if (group.name.equals("底盘制动")) {
            group.image = R.drawable.group_dipanzhidong;
        } else if (group.name.equals("安全配置")) {
            group.image = R.drawable.group_anquanpeizhi;
        } else if (group.name.equals("外部配置")) {
            group.image = R.drawable.group_waibupeizhi;
        } else if (group.name.equals("内部配置")) {
            group.image = R.drawable.group_neibupeizhi;
        } else if (group.name.equals("座椅配置")) {
            group.image = R.drawable.group_zuoyipeizhi;
        } else if (group.name.equals("多媒体配置")) {
            group.image = R.drawable.group_duomeitipeizhi;
        } else if (group.name.equals("灯光配置")) {
            group.image = R.drawable.group_dengguangpeizhi;
        } else if (group.name.equals("玻璃及后视镜配置")) {
            group.image = R.drawable.group_bolijihoushijingpeizhi;
        }
    }
}
