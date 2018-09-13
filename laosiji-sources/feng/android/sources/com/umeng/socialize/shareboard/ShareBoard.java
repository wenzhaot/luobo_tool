package com.umeng.socialize.shareboard;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.socialize.utils.SocializeSpUtils;
import java.util.List;

public class ShareBoard extends PopupWindow {
    private ShareBoardConfig mShareBoardConfig;

    public ShareBoard(Context context, List<SnsPlatform> list) {
        this(context, list, null);
    }

    public ShareBoard(Context context, List<SnsPlatform> list, ShareBoardConfig shareBoardConfig) {
        super(context);
        setWindowLayoutMode(-1, -1);
        boolean z = false;
        if (context.getResources().getConfiguration().orientation == 2) {
            z = true;
        }
        if (shareBoardConfig == null) {
            shareBoardConfig = new ShareBoardConfig();
        }
        this.mShareBoardConfig = shareBoardConfig;
        shareBoardConfig.setOrientation(z);
        View uMActionFrame = new UMActionFrame(context);
        uMActionFrame.setSnsPlatformData(list, shareBoardConfig);
        uMActionFrame.setLayoutParams(new LayoutParams(-1, -1));
        uMActionFrame.setDismissListener(new OnDismissListener() {
            public void onDismiss() {
                ShareBoard.this.dismiss();
            }
        });
        setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                OnDismissListener onDismissListener = ShareBoard.this.mShareBoardConfig != null ? ShareBoard.this.mShareBoardConfig.getOnDismissListener() : null;
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }
            }
        });
        setContentView(uMActionFrame);
        setFocusable(true);
        saveShareboardConfig(context, shareBoardConfig);
    }

    private void saveShareboardConfig(Context context, ShareBoardConfig shareBoardConfig) {
        if (context != null && shareBoardConfig != null) {
            Object obj = shareBoardConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM ? PushConstants.PUSH_TYPE_NOTIFY : PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
            Object obj2 = null;
            if (shareBoardConfig.mMenuBgShape == ShareBoardConfig.BG_SHAPE_NONE) {
                obj2 = PushConstants.PUSH_TYPE_NOTIFY;
            } else if (shareBoardConfig.mMenuBgShape == ShareBoardConfig.BG_SHAPE_CIRCULAR) {
                obj2 = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
            } else if (shareBoardConfig.mMenuBgShape == ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE) {
                if (shareBoardConfig.mMenuBgShapeAngle != 0) {
                    obj2 = PushConstants.PUSH_TYPE_UPLOAD_LOG;
                } else {
                    obj2 = "3";
                }
            }
            if (!TextUtils.isEmpty(obj) || !TextUtils.isEmpty(obj2)) {
                SocializeSpUtils.putShareBoardConfig(context, obj2 + ";" + obj);
            }
        }
    }

    public void setShareBoardlistener(final ShareBoardlistener shareBoardlistener) {
        if (this.mShareBoardConfig != null) {
            this.mShareBoardConfig.setShareBoardlistener(new ShareBoardlistener() {
                public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                    ShareBoard.this.setOnDismissListener(null);
                    ShareBoard.this.dismiss();
                    if (shareBoardlistener != null) {
                        shareBoardlistener.onclick(snsPlatform, share_media);
                    }
                }
            });
        }
    }
}
