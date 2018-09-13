package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.AtUserItemLayoutBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class AtUserAdater extends MvvmBaseAdapter<UserInfo, AtUserItemLayoutBinding> {
    private int m72;

    public AtUserAdater(Context context, List<UserInfo> list) {
        super(context, list);
        this.m72 = context.getResources().getDimensionPixelSize(R.dimen.default_72PX);
    }

    public AtUserItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return AtUserItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<AtUserItemLayoutBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        UserInfo info = (UserInfo) this.mList.get(position);
        ((AtUserItemLayoutBinding) holder.binding).avUserHead.setAutoImageURI(Uri.parse(FengUtil.getFixedSizeUrl(info.getHeadImageInfo(), this.m72, this.m72)));
        ((AtUserItemLayoutBinding) holder.binding).tvUserName.setText((CharSequence) info.name.get());
    }

    public void dataBindingTo(AtUserItemLayoutBinding atUserItemLayoutBinding, UserInfo userInfo) {
    }
}
