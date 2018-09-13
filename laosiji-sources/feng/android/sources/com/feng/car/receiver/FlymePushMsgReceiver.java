package com.feng.car.receiver;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.feng.car.activity.TransparentActivity;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.umeng.socialize.net.dplus.CommonNetImpl;

public class FlymePushMsgReceiver extends MzPushMessageReceiver {
    @Deprecated
    public void onRegister(Context context, String pushid) {
    }

    @Deprecated
    public void onUnRegister(Context context, boolean b) {
    }

    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        pushNotificationBuilder.setmStatusbarIcon(2130838539);
    }

    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
    }

    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
    }

    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
    }

    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
    }

    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
    }

    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        try {
            Intent intent = new Intent(context, TransparentActivity.class);
            String strUrl = mzPushMessage.getSelfDefineContentString().toString().trim();
            if (TextUtils.isEmpty(strUrl)) {
                intent.putExtra("url", "");
            } else {
                intent.putExtra("url", strUrl);
            }
            intent.putExtra("umeng_push", 1);
            intent.addFlags(CommonNetImpl.FLAG_AUTH);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
