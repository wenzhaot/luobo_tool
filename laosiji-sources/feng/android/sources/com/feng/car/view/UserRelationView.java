package com.feng.car.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.BlackUtil;
import com.feng.car.utils.UmengConstans;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.umeng.analytics.MobclickAgent;

public class UserRelationView extends ImageView {
    public static final int HIDE_FOLLOW_STATE = 0;
    public static final int SHOW_FOLLOW_STATE = 1;
    private Context mContext;
    private int mContextType = -1;
    private int mType = 0;
    private UserInfo mUserInfo;

    public void setContextType(int contextType) {
        this.mContextType = contextType;
    }

    public UserRelationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public void setUserInfo(UserInfo info) {
        this.mUserInfo = info;
        this.mType = 0;
        changeStatus();
    }

    public void setUserInfo(UserInfo info, int type) {
        this.mUserInfo = info;
        this.mType = type;
        changeStatus();
    }

    public void changeStatus(int type) {
        this.mType = type;
        changeStatus();
    }

    private void changeStatus() {
        int i = 8;
        if (this.mUserInfo != null) {
            if (this.mUserInfo.getIsMy() == 1) {
                setVisibility(8);
            } else {
                setVisibility(0);
                if (BlackUtil.getInstance().isInBlackList(this.mUserInfo)) {
                    setImageResource(2130837877);
                    if (this.mType != 0) {
                        i = 0;
                    }
                    setVisibility(i);
                } else if (this.mUserInfo.isfollow.get() == 0) {
                    setImageResource(2130837877);
                } else if (this.mUserInfo.isfollow.get() == 1) {
                    setImageResource(2130837872);
                    if (this.mType != 0) {
                        i = 0;
                    }
                    setVisibility(i);
                } else if (this.mUserInfo.isfollow.get() == 2) {
                    setImageResource(2130837875);
                    if (this.mType != 0) {
                        i = 0;
                    }
                    setVisibility(i);
                } else {
                    setImageResource(2130837877);
                }
            }
            setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (UserRelationView.this.mContextType == 1) {
                        MobclickAgent.onEvent(UserRelationView.this.mContext, UmengConstans.OWNCOMMENT_FOLLOW);
                    } else if (UserRelationView.this.mContextType == 2) {
                        MobclickAgent.onEvent(UserRelationView.this.mContext, UmengConstans.COMMENT_FOLLOW);
                    }
                    UserRelationView.this.mUserInfo.followOperation(UserRelationView.this.mContext, null);
                }
            });
        }
    }

    public UserInfo getUserInfo() {
        return this.mUserInfo;
    }
}
