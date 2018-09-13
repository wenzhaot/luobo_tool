package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.AllprogramItemBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.event.ProgramFollowEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class ProgramListAdapter extends MvvmBaseAdapter<HotShowInfo, AllprogramItemBinding> {
    private int m128 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_128PX);
    private HotShowFollowListener mHotShowFollowListener;

    public interface HotShowFollowListener {
        void onFollowChnage(int i, int i2, int i3);
    }

    public ProgramListAdapter(Context context, List<HotShowInfo> list) {
        super(context, list);
    }

    public AllprogramItemBinding getBinding(ViewGroup parent, int viewType) {
        return AllprogramItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(AllprogramItemBinding allprogramItemBinding, HotShowInfo hotShowInfo) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<AllprogramItemBinding> holder, int position) {
        final HotShowInfo info = (HotShowInfo) this.mList.get(position);
        ((AllprogramItemBinding) holder.binding).image.setImageURI(Uri.parse(FengUtil.getFixedSizeUrl(info.image, this.m128, this.m128)));
        ((AllprogramItemBinding) holder.binding).name.setText(info.name);
        ((AllprogramItemBinding) holder.binding).detail.setText(getProgramDetail(info));
        if (info.isfollow.get() == 1) {
            ((AllprogramItemBinding) holder.binding).followImage.setImageResource(R.drawable.follow_cancel_selector);
        } else {
            ((AllprogramItemBinding) holder.binding).followImage.setImageResource(R.drawable.follow_selector);
        }
        ((AllprogramItemBinding) holder.binding).followImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (info.isfollow.get() == 1) {
                    ProgramListAdapter.this.showCancelAttentionDialog(info);
                } else {
                    ProgramListAdapter.this.followHotShow(info);
                }
            }
        });
    }

    private String getProgramDetail(HotShowInfo info) {
        StringBuilder sb = new StringBuilder("阅读");
        sb.append(FengUtil.numberFormat(info.clickcount.get()));
        return sb.toString();
    }

    private void showCancelAttentionDialog(final HotShowInfo info) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(this.mContext.getString(R.string.make_sure), false));
        list.add(new DialogItemEntity(this.mContext.getString(R.string.cancel), true));
        CommonDialog.showCommonDialog(this.mContext, this.mContext.getString(R.string.pop_program_cancel_follow_tips), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    ProgramListAdapter.this.followHotShow(info);
                }
            }
        }, false);
    }

    private void followHotShow(final HotShowInfo info) {
        Map<String, Object> map = new HashMap();
        if (info.isfollow.get() == 1) {
            map.put("isfollow", String.valueOf(0));
        } else {
            map.put("isfollow", String.valueOf(1));
        }
        map.put("hotshowid", String.valueOf(info.id));
        FengApplication.getInstance().httpRequest("snshotshow/follow/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) ProgramListAdapter.this.mContext).showThirdTypeToast((int) R.string.check_network);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) ProgramListAdapter.this.mContext).showSecondTypeToast((int) R.string.excute_failure);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    if (new JSONObject(content).getInt("code") == 1) {
                        if (info.isfollow.get() == 1) {
                            info.isfollow.set(0);
                        } else {
                            info.isfollow.set(1);
                        }
                        if (ProgramListAdapter.this.mHotShowFollowListener != null) {
                            ProgramListAdapter.this.mHotShowFollowListener.onFollowChnage(info.id, info.isfollow.get(), info.isremind.get());
                        }
                        ProgramListAdapter.this.notifyDataSetChanged();
                        EventBus.getDefault().post(new ProgramFollowEvent(info.id, info.isfollow.get(), info.isremind.get()));
                        return;
                    }
                    ((BaseActivity) ProgramListAdapter.this.mContext).showSecondTypeToast((int) R.string.excute_failure);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ((BaseActivity) ProgramListAdapter.this.mContext).showSecondTypeToast((int) R.string.excute_failure);
                }
            }
        });
    }

    public void setHotShowFollowListener(HotShowFollowListener l) {
        this.mHotShowFollowListener = l;
    }
}
