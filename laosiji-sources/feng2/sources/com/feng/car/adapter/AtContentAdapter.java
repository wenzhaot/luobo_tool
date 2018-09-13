package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.feng.car.R;
import com.feng.car.databinding.AtContentItemBinding;
import com.feng.car.entity.model.LogGatherInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.FengUtil;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;
import java.util.UUID;

public class AtContentAdapter extends MvvmBaseAdapter<SnsInfo, AtContentItemBinding> {
    private int m120;
    private LogGatherInfo mLogGatherInfo;
    private String mSoleKey = "";
    private SnsInfoList snsInfoList;

    public String getSoleKey() {
        return this.mSoleKey;
    }

    public AtContentAdapter(Context context, SnsInfoList snsInfoList, LogGatherInfo logGatherInfo) {
        super(context, snsInfoList.getSnsList());
        this.mLogGatherInfo = logGatherInfo;
        this.m120 = context.getResources().getDimensionPixelOffset(R.dimen.default_120PX);
        this.snsInfoList = snsInfoList;
        this.mSoleKey = UUID.randomUUID().toString();
    }

    public AtContentItemBinding getBinding(ViewGroup parent, int viewType) {
        return AtContentItemBinding.inflate(this.mInflater, parent, false);
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<AtContentItemBinding> holder, final int position) {
        super.onBaseBindViewHolder(holder, position);
        final SnsInfo info = (SnsInfo) this.mList.get(position);
        ((AtContentItemBinding) holder.binding).fdvSnsDetailImg.setHeadUrl(FengUtil.getFixedSizeUrl(info.getQuoteImageInfo(), this.m120, this.m120));
        ((AtContentItemBinding) holder.binding).userImage.setHeadUrl(FengUtil.getHeadImageUrl(info.user.getHeadImageInfo()));
        ((AtContentItemBinding) holder.binding).atDigestDescription.setMatchLink(true);
        ((AtContentItemBinding) holder.binding).atDigestDescription.setContent(info.getQuoteDescription(), true);
        ((AtContentItemBinding) holder.binding).atDigestDescription.setVisibility(0);
        ((AtContentItemBinding) holder.binding).afdCover.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(info.image, 350, 1.0f)));
        ((AtContentItemBinding) holder.binding).tvSnsContent.setText(info.getQuoteDescription());
        ((AtContentItemBinding) holder.binding).llParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (info.snstype == 1 || info.snstype == 0) {
                    AtContentAdapter.this.addLocalClick(position, info);
                    info.intentToArticle(AtContentAdapter.this.mContext, -1);
                }
            }
        });
        ((AtContentItemBinding) holder.binding).llComment.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                info.intentToSendComment(AtContentAdapter.this.mContext, ((AtContentItemBinding) holder.binding).llComment, AtContentAdapter.this.mSoleKey);
            }
        });
        ((AtContentItemBinding) holder.binding).llGood.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((AtContentItemBinding) holder.binding).llGood.setEnabled(false);
                info.praiseOperation(AtContentAdapter.this.mContext, true, new SuccessFailCallback() {
                    public void onSuccess() {
                        ((AtContentItemBinding) holder.binding).llGood.setEnabled(true);
                    }

                    public void onFail() {
                        ((AtContentItemBinding) holder.binding).llGood.setEnabled(true);
                    }
                });
            }
        });
        this.mLogGatherInfo.addLocationReadPv(info.id, info.resourceid, info.snstype, getExpendedJson(position, info));
    }

    public void dataBindingTo(AtContentItemBinding atContentItemBinding, SnsInfo info) {
        atContentItemBinding.setSnsInfo(info);
    }

    public void refreshUserStatus(UserInfoRefreshEvent userInfoRefreshEvent) {
        if (userInfoRefreshEvent != null && userInfoRefreshEvent.userInfo != null) {
            UserInfo info = userInfoRefreshEvent.userInfo;
            List<Integer> positionList = this.snsInfoList.getUserSnsPosByUserID(info.id);
            int i;
            UserInfo userInfo;
            if (userInfoRefreshEvent.type == 1) {
                if (positionList != null && positionList.size() > 0) {
                    for (i = 0; i < positionList.size(); i++) {
                        userInfo = ((SnsInfo) this.mList.get(((Integer) positionList.get(i)).intValue())).user;
                        if (!(StringUtil.isEmpty((String) info.name.get()) || ((String) userInfo.name.get()).equals(info.name.get()))) {
                            userInfo.name.set(info.name.get());
                        }
                        if (!(StringUtil.isEmpty((String) info.signature.get()) || ((String) userInfo.signature.get()).equals(info.signature.get()))) {
                            userInfo.signature.set(info.signature.get());
                        }
                        if (!(StringUtil.isEmpty(info.image.url) || userInfo.getHeadImageInfo().url.equals(info.getHeadImageInfo().url))) {
                            userInfo.getHeadImageInfo().width = info.getHeadImageInfo().width;
                            userInfo.getHeadImageInfo().height = info.getHeadImageInfo().height;
                            userInfo.getHeadImageInfo().url = info.getHeadImageInfo().url;
                            userInfo.getHeadImageInfo().hash = info.getHeadImageInfo().hash;
                            userInfo.getHeadImageInfo().mimetype = info.getHeadImageInfo().mimetype;
                        }
                    }
                    notifyDataSetChanged();
                }
            } else if (positionList != null && positionList.size() > 0) {
                for (i = 0; i < positionList.size(); i++) {
                    userInfo = ((SnsInfo) this.mList.get(((Integer) positionList.get(i)).intValue())).user;
                    userInfo.isblack.set(info.isblack.get());
                    userInfo.isfollow.set(info.isfollow.get());
                    userInfo.isShowFollowState = true;
                }
                notifyDataSetChanged();
            }
        }
    }

    public void refreshSnsInfo(SnsInfoRefreshEvent snsInfoRefreshEvent) {
        if (snsInfoRefreshEvent != null) {
            SnsInfo snsInfoRefresh = snsInfoRefreshEvent.snsInfo;
            int position = this.snsInfoList.getPosition(snsInfoRefresh.getLocalkey());
            if (position != -1) {
                SnsInfo snsInfo = this.snsInfoList.get(position);
                switch (snsInfoRefreshEvent.refreshType) {
                    case 2004:
                        snsInfo.praisecount.set(snsInfoRefresh.praisecount.get());
                        snsInfo.ispraise.set(snsInfoRefresh.ispraise.get());
                        return;
                    case m_AppUI.MSG_APP_VERSION_FORCE /*2005*/:
                        snsInfo.isflag = snsInfoRefresh.isflag;
                        this.snsInfoList.remove(position);
                        notifyDataSetChanged();
                        return;
                    case m_AppUI.MSG_APP_VERSION_COMMEND /*2006*/:
                        snsInfo.commentcount.set(snsInfoRefresh.commentcount.get());
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void addLocalClick(int position, SnsInfo snsInfo) {
        this.mLogGatherInfo.addLocationClick(snsInfo.id, snsInfo.resourceid, snsInfo.snstype, getExpendedJson(position, snsInfo));
    }

    private String getExpendedJson(int position, SnsInfo info) {
        return "{\"position\":\"" + position + "\"" + (info.rts != -1 ? ",\"rts\":\"" + info.rts + "\"" : "") + "}";
    }
}
