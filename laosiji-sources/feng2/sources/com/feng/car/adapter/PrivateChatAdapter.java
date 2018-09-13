package com.feng.car.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.activity.PrivateChatActivity;
import com.feng.car.activity.ShowBigImageActivity;
import com.feng.car.activity.WebActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityPrivateChatBinding;
import com.feng.car.databinding.PrivateChatItemBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.privatemsg.MessageInfo;
import com.feng.car.entity.privatemsg.MessageMarkInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.textview.AisenTextView$OnUrlLongClickListener;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;

public class PrivateChatAdapter extends MvvmBaseAdapter<MessageInfo, PrivateChatItemBinding> {
    private int DIALOG_FLAG_COPY = 0;
    private int DIALOG_FLAG_DELETE = 2;
    private int DIALOG_FLAG_REPORT = 1;
    private int DIALOG_FLAG_SELECT = 3;
    private int m25 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_25PX);
    private MessageMarkInfo markInfo = new MessageMarkInfo();
    private List<MessageInfo> msgList = new ArrayList();

    public PrivateChatAdapter(Context con, List<MessageInfo> list) {
        super(con, list);
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<PrivateChatItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        if (position == this.mList.size() - 1) {
            ((PrivateChatItemBinding) holder.binding).bottomView.setVisibility(0);
        } else {
            ((PrivateChatItemBinding) holder.binding).bottomView.setVisibility(8);
        }
        final MessageInfo item = (MessageInfo) this.mList.get(position);
        ((PrivateChatItemBinding) holder.binding).llPrivate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PrivateChatAdapter.this.selectMsg(item);
            }
        });
        if (item.ismysend.get() == 0) {
            ((PrivateChatItemBinding) holder.binding).otherLine.setVisibility(0);
            ((PrivateChatItemBinding) holder.binding).myLine.setVisibility(8);
            ((PrivateChatItemBinding) holder.binding).hpvOther.setHeadUrl(FengUtil.getHeadImageUrl(item.user.getHeadImageInfo()));
            ((PrivateChatItemBinding) holder.binding).tvOtherTime.setText((CharSequence) item.time.get());
            String otherPrivateImageUrl;
            if (TextUtils.isEmpty((CharSequence) item.content.get())) {
                ((PrivateChatItemBinding) holder.binding).otherSummary.setVisibility(8);
                ((PrivateChatItemBinding) holder.binding).otherImage.setVisibility(0);
                otherPrivateImageUrl = FengUtil.getSingleNineScaleUrl(item.image, 200);
                item.image.lowUrl = otherPrivateImageUrl;
                ((PrivateChatItemBinding) holder.binding).otherImage.setAutoImageURI(Uri.parse(otherPrivateImageUrl));
            } else {
                ((PrivateChatItemBinding) holder.binding).otherSummary.setVisibility(0);
                ((PrivateChatItemBinding) holder.binding).otherSummary.setContent((String) item.content.get(), false);
                ((PrivateChatItemBinding) holder.binding).otherSummary.setPadding(this.m25, this.m25, this.m25, this.m25);
                if (item.image == null || TextUtils.isEmpty(item.image.url)) {
                    ((PrivateChatItemBinding) holder.binding).otherImage.setVisibility(8);
                } else {
                    otherPrivateImageUrl = FengUtil.getSingleNineScaleUrl(item.image, 200);
                    item.image.lowUrl = otherPrivateImageUrl;
                    ((PrivateChatItemBinding) holder.binding).otherImage.setAutoImageURI(Uri.parse(otherPrivateImageUrl));
                    ((PrivateChatItemBinding) holder.binding).otherImage.setVisibility(0);
                }
            }
            ((PrivateChatItemBinding) holder.binding).otherSummary.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {
                    PrivateChatAdapter.this.showOperationDialog(PrivateChatAdapter.this.mContext, item);
                    return false;
                }
            });
            ((PrivateChatItemBinding) holder.binding).otherSummary.setUrlLongClickListener(new AisenTextView$OnUrlLongClickListener() {
                public void onLongClick() {
                    PrivateChatAdapter.this.showOperationDialog(PrivateChatAdapter.this.mContext, item);
                }
            });
            ((PrivateChatItemBinding) holder.binding).otherImage.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {
                    PrivateChatAdapter.this.showOperationDialog(PrivateChatAdapter.this.mContext, item);
                    return false;
                }
            });
            ((PrivateChatItemBinding) holder.binding).otherImage.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (item.image != null && !TextUtils.isEmpty(item.image.url)) {
                        Intent intent = new Intent(PrivateChatAdapter.this.mContext, ShowBigImageActivity.class);
                        List<ImageInfo> list = new ArrayList();
                        list.add(item.image);
                        intent.putExtra("mImageList", JsonUtil.toJson(list));
                        intent.putExtra("position", 0);
                        intent.putExtra("show_type", 1008);
                        intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(((PrivateChatItemBinding) holder.binding).otherImage, 0, item.image.hash)));
                        intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
                        PrivateChatAdapter.this.mContext.startActivity(intent);
                        ((Activity) PrivateChatAdapter.this.mContext).overridePendingTransition(0, 0);
                    }
                }
            });
            ((PrivateChatItemBinding) holder.binding).hpvOther.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    item.user.intentToPersonalHome(PrivateChatAdapter.this.mContext);
                }
            });
            return;
        }
        ((PrivateChatItemBinding) holder.binding).otherLine.setVisibility(8);
        ((PrivateChatItemBinding) holder.binding).myLine.setVisibility(0);
        ((PrivateChatItemBinding) holder.binding).hpvMine.setHeadUrl(FengUtil.getHeadImageUrl(item.my.image));
        ((PrivateChatItemBinding) holder.binding).tvMyTime.setText((CharSequence) item.time.get());
        String myPrivateImageUrl;
        if (TextUtils.isEmpty((CharSequence) item.content.get())) {
            ((PrivateChatItemBinding) holder.binding).mySummary.setVisibility(8);
            ((PrivateChatItemBinding) holder.binding).myImage.setVisibility(0);
            myPrivateImageUrl = FengUtil.getSingleNineScaleUrl(item.image, 200);
            item.image.lowUrl = myPrivateImageUrl;
            ((PrivateChatItemBinding) holder.binding).myImage.setAutoImageURI(Uri.parse(myPrivateImageUrl));
        } else {
            ((PrivateChatItemBinding) holder.binding).mySummary.setVisibility(0);
            ((PrivateChatItemBinding) holder.binding).mySummary.setContent((String) item.content.get(), false);
            ((PrivateChatItemBinding) holder.binding).mySummary.setPadding(this.m25, this.m25, this.m25, this.m25);
            if (item.image == null || TextUtils.isEmpty(item.image.url)) {
                ((PrivateChatItemBinding) holder.binding).myImage.setVisibility(8);
            } else {
                myPrivateImageUrl = FengUtil.getSingleNineScaleUrl(item.image, 200);
                item.image.lowUrl = myPrivateImageUrl;
                ((PrivateChatItemBinding) holder.binding).myImage.setAutoImageURI(Uri.parse(myPrivateImageUrl));
                ((PrivateChatItemBinding) holder.binding).myImage.setVisibility(0);
            }
        }
        ((PrivateChatItemBinding) holder.binding).mySummary.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                PrivateChatAdapter.this.showOperationDialog(PrivateChatAdapter.this.mContext, item);
                return false;
            }
        });
        ((PrivateChatItemBinding) holder.binding).mySummary.setUrlLongClickListener(new AisenTextView$OnUrlLongClickListener() {
            public void onLongClick() {
                PrivateChatAdapter.this.showOperationDialog(PrivateChatAdapter.this.mContext, item);
            }
        });
        ((PrivateChatItemBinding) holder.binding).myImage.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                PrivateChatAdapter.this.showOperationDialog(PrivateChatAdapter.this.mContext, item);
                return false;
            }
        });
        ((PrivateChatItemBinding) holder.binding).myImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (item.image != null && !TextUtils.isEmpty(item.image.url)) {
                    Intent intent = new Intent(PrivateChatAdapter.this.mContext, ShowBigImageActivity.class);
                    List<ImageInfo> list = new ArrayList();
                    list.add(item.image);
                    intent.putExtra("mImageList", JsonUtil.toJson(list));
                    intent.putExtra("position", 0);
                    intent.putExtra("show_type", 1008);
                    intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(((PrivateChatItemBinding) holder.binding).myImage, 0, item.image.hash)));
                    intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
                    PrivateChatAdapter.this.mContext.startActivity(intent);
                    ((Activity) PrivateChatAdapter.this.mContext).overridePendingTransition(0, 0);
                }
            }
        });
        ((PrivateChatItemBinding) holder.binding).hpvMine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FengApplication.getInstance().getUserInfo().intentToPersonalHome(PrivateChatAdapter.this.mContext);
            }
        });
    }

    private void selectMsg(MessageInfo item) {
        if (getShown()) {
            if (this.msgList.contains(item)) {
                this.msgList.remove(item);
                item.isSelect.set(false);
            } else {
                this.msgList.add(item);
                item.isSelect.set(true);
            }
            if (this.msgList.size() > 0) {
                ((ActivityPrivateChatBinding) ((PrivateChatActivity) this.mContext).mBaseBinding).tvPrivateDeleteBtn.setSelected(true);
            } else {
                ((ActivityPrivateChatBinding) ((PrivateChatActivity) this.mContext).mBaseBinding).tvPrivateDeleteBtn.setSelected(false);
            }
        }
    }

    public int getItemCount() {
        return this.mList.size();
    }

    public PrivateChatItemBinding getBinding(ViewGroup parent, int viewType) {
        return PrivateChatItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(PrivateChatItemBinding privateChatItemBinding, MessageInfo messageInfo) {
        privateChatItemBinding.setMarkInfo(this.markInfo);
        privateChatItemBinding.setMsgInfo(messageInfo);
    }

    private void showOperationDialog(final Context context, final MessageInfo messageInfo) {
        List<DialogItemEntity> list = new ArrayList();
        if (!StringUtil.isEmpty((String) messageInfo.content.get())) {
            list.add(new DialogItemEntity("复制", false, this.DIALOG_FLAG_COPY));
        }
        if (messageInfo.ismysend.get() != 1) {
            list.add(new DialogItemEntity("举报", false, this.DIALOG_FLAG_REPORT));
        }
        list.add(new DialogItemEntity("删除", true, this.DIALOG_FLAG_DELETE));
        list.add(new DialogItemEntity("选择", false, this.DIALOG_FLAG_SELECT));
        CommonDialog.showCommonDialog(this.mContext, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (dialogItemEntity.itemTag == PrivateChatAdapter.this.DIALOG_FLAG_COPY) {
                    FengUtil.copyText(context, (String) messageInfo.content.get(), "文字已复制到剪切板");
                } else if (dialogItemEntity.itemTag == PrivateChatAdapter.this.DIALOG_FLAG_REPORT) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("title", "举报");
                    intent.putExtra("url", FengUtil.getReportUrl(context, 3, messageInfo.id, messageInfo.user.id));
                    context.startActivity(intent);
                } else if (dialogItemEntity.itemTag == PrivateChatAdapter.this.DIALOG_FLAG_DELETE) {
                    PrivateChatAdapter.this.msgList.add(messageInfo);
                    ((PrivateChatActivity) PrivateChatAdapter.this.mContext).deleteSelectMsg();
                } else if (dialogItemEntity.itemTag == PrivateChatAdapter.this.DIALOG_FLAG_SELECT) {
                    PrivateChatAdapter.this.markInfo.isShow.set(true);
                    PrivateChatAdapter.this.selectMsg(messageInfo);
                    ((PrivateChatActivity) PrivateChatAdapter.this.mContext).processTitle();
                }
            }
        });
    }

    public boolean getShown() {
        return this.markInfo.isShow.get();
    }

    public void closeShown() {
        this.markInfo.isShow.set(false);
        clearMsgList();
    }

    private void clearMsgList() {
        for (int i = 0; i < this.msgList.size(); i++) {
            ((MessageInfo) this.msgList.get(i)).isSelect.set(false);
        }
        this.msgList.clear();
        ((ActivityPrivateChatBinding) ((PrivateChatActivity) this.mContext).mBaseBinding).tvPrivateDeleteBtn.setSelected(false);
    }

    public List<MessageInfo> getMsgList() {
        return this.msgList;
    }
}
