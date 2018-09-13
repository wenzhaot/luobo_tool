package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.feng.car.R;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.CarModleComparisonActivity;
import com.feng.car.databinding.CarComparisonItemBinding;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.event.RefreshEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.SharedUtil;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class CarComparisonAdapter extends MvvmBaseAdapter<CarModelInfo, CarComparisonItemBinding> {
    public OnSelChangeListener mListener;
    private List<CarModelInfo> mSelComparison = new ArrayList();
    private List<CarModelInfo> mSelDelList = new ArrayList();
    private int mType;
    private boolean mUserSelDel = true;

    public interface OnSelChangeListener {
        void onLongDel(int i);

        void onSelChange(int i);
    }

    public CarComparisonAdapter(Context context, int type, List<CarModelInfo> list, OnSelChangeListener listener) {
        super(context, list);
        this.mListener = listener;
        this.mType = type;
    }

    public void addSelCarModle(CarModelInfo info) {
        if (!this.mSelDelList.contains(info)) {
            if (this.mSelDelList.size() >= 9) {
                this.mSelDelList.remove(0);
            }
            this.mSelDelList.add(info);
        }
    }

    public void addSelCarModle(List<CarModelInfo> list, boolean isDel) {
        if (isDel) {
            this.mSelDelList.clear();
        }
        for (CarModelInfo info : list) {
            addSelCarModle(info);
        }
        if (this.mListener != null) {
            this.mListener.onSelChange(this.mSelDelList.size());
        }
        FengConstant.CAR_MAX_CURRENT_COMPARISON_NUM = this.mSelDelList.size();
        notifyDataSetChanged();
    }

    public void removeSel() {
        this.mSelComparison.removeAll(this.mSelDelList);
    }

    public void changeSelDel(boolean isSel) {
        if (isSel) {
            this.mSelDelList.clear();
            this.mSelDelList.addAll(this.mSelComparison);
            this.mUserSelDel = true;
        } else {
            this.mSelComparison.clear();
            this.mSelComparison.addAll(this.mSelDelList);
            this.mSelDelList.clear();
            this.mUserSelDel = false;
        }
        notifyDataSetChanged();
    }

    public void setNoAllSel() {
        this.mSelDelList.clear();
        if (this.mListener != null) {
            this.mListener.onSelChange(0);
        }
        notifyDataSetChanged();
    }

    public void setAllSel() {
        this.mSelDelList.clear();
        this.mSelDelList.addAll(this.mList);
        if (this.mListener != null) {
            this.mListener.onSelChange(this.mSelDelList.size());
        }
        notifyDataSetChanged();
    }

    public List<CarModelInfo> getSelDelList() {
        return this.mSelDelList;
    }

    public CarComparisonItemBinding getBinding(ViewGroup parent, int viewType) {
        return CarComparisonItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(CarComparisonItemBinding itemCarBrandBinding, CarModelInfo info) {
        itemCarBrandBinding.setInfo(info);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CarComparisonItemBinding> holder, final int position) {
        final CarModelInfo info = (CarModelInfo) this.mList.get(position);
        if (position == 0) {
            ((CarComparisonItemBinding) holder.binding).line.setVisibility(8);
        } else {
            ((CarComparisonItemBinding) holder.binding).line.setVisibility(0);
        }
        if (this.mType == 0) {
            if (this.mUserSelDel) {
                holder.setIsRecyclable(false);
                ((CarComparisonItemBinding) holder.binding).cbCarSel.setOnLongClickListener(new OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if (CarComparisonAdapter.this.mListener != null) {
                            CarComparisonAdapter.this.mListener.onLongDel(position);
                        }
                        return true;
                    }
                });
            } else {
                holder.setIsRecyclable(true);
                ((CarComparisonItemBinding) holder.binding).getRoot().setOnLongClickListener(null);
            }
            ((CarComparisonItemBinding) holder.binding).cbCarSel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        if (CarComparisonAdapter.this.mUserSelDel) {
                            FengConstant.CAR_MAX_CURRENT_COMPARISON_NUM--;
                        }
                        CarComparisonAdapter.this.mSelDelList.remove(info);
                        if (CarComparisonAdapter.this.mListener != null) {
                            CarComparisonAdapter.this.mListener.onSelChange(CarComparisonAdapter.this.mSelDelList.size());
                        }
                    } else if (!CarComparisonAdapter.this.mSelDelList.contains(info)) {
                        if (CarComparisonAdapter.this.mUserSelDel) {
                            if (FengConstant.CAR_MAX_CURRENT_COMPARISON_NUM >= 9) {
                                ((BaseActivity) CarComparisonAdapter.this.mContext).showThirdTypeToast((int) R.string.car_comparison_max_hint);
                                buttonView.setChecked(false);
                                return;
                            }
                            FengConstant.CAR_MAX_CURRENT_COMPARISON_NUM++;
                        }
                        CarComparisonAdapter.this.mSelDelList.add(info);
                        if (CarComparisonAdapter.this.mListener != null) {
                            CarComparisonAdapter.this.mListener.onSelChange(CarComparisonAdapter.this.mSelDelList.size());
                        }
                    }
                }
            });
            if (this.mSelDelList.contains(info)) {
                ((CarComparisonItemBinding) holder.binding).cbCarSel.setChecked(true);
                return;
            } else {
                ((CarComparisonItemBinding) holder.binding).cbCarSel.setChecked(false);
                return;
            }
        }
        ((CarComparisonItemBinding) holder.binding).cbCarSel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (FengConstant.CAR_MAX_CURRENT_COMPARISON_NUM >= 9) {
                    ((BaseActivity) CarComparisonAdapter.this.mContext).showThirdTypeToast((int) R.string.car_comparison_max_hint);
                    return;
                }
                List<CarModelInfo> list = JsonUtil.fromJson(SharedUtil.getString(CarComparisonAdapter.this.mContext, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY), new TypeToken<ArrayList<CarModelInfo>>() {
                });
                if (list == null) {
                    list = new ArrayList();
                }
                if (list.size() >= 9) {
                    list.remove(0);
                }
                list.add(info);
                SharedUtil.putString(CarComparisonAdapter.this.mContext, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY, JsonUtil.toJson(list));
                EventBus.getDefault().post(new RefreshEvent(1, info));
            }
        });
        ((CarComparisonItemBinding) holder.binding).cbCarSel.setCompoundDrawables(null, null, null, null);
    }
}
