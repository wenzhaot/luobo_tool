package com.feng.car.utils;

import android.os.Handler;
import android.os.Message;
import com.feng.car.FengApplication;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageCountManager {
    private static MessageCountManager mInstance;
    private int atCommentCount;
    private int atCount;
    private int atWenZhangCount;
    private int atdiscuss;
    private int commentCount;
    private int community;
    private List<Integer> communitylist = new ArrayList();
    private int fansCount;
    private Handler mHandler;
    private long mRefreshDuration = 600000;
    private int newhotshow = 0;
    private int praiseCount;
    private int receivedCount;
    private int redpoint = 0;
    private int snsfollow;
    private int sysCount;

    private MessageCountManager() {
    }

    public static MessageCountManager getInstance() {
        if (mInstance == null) {
            mInstance = new MessageCountManager();
        }
        return mInstance;
    }

    public int getSysCount() {
        return this.sysCount;
    }

    public int getAtCount() {
        return this.atCount;
    }

    public int getFansCount() {
        return this.fansCount;
    }

    public int getReceivedCount() {
        return this.receivedCount;
    }

    public int getCommentCount() {
        return this.commentCount;
    }

    public int getPraiseCount() {
        return this.praiseCount;
    }

    public int getAtWenZhangCount() {
        return this.atWenZhangCount;
    }

    public int getAtCommentCount() {
        return this.atCommentCount;
    }

    public int getAtDiscuss() {
        return this.atdiscuss;
    }

    public List<Integer> getCommunitylist() {
        return this.communitylist;
    }

    public int getSnsfollow() {
        return this.snsfollow;
    }

    public void clearSnsfollow() {
        this.snsfollow = 0;
    }

    public int getNewHotShow() {
        return this.newhotshow;
    }

    public void clearNewHotShow() {
        this.newhotshow = 0;
        clearFollowDot("app_hotshow_channel");
    }

    public int getCommunity() {
        return this.community;
    }

    public void clearCommunity() {
        this.community = 0;
    }

    public int getFollowPageMessageCount() {
        return this.redpoint;
    }

    public void clearFollowPageMessageCount() {
        this.redpoint = 0;
        clearFollowDot("app_follow_channel");
    }

    public int getMessagePageTotalCount() {
        return (((this.commentCount + this.atCount) + this.praiseCount) + this.receivedCount) + this.sysCount;
    }

    public int getMinePageTotalCount() {
        return this.fansCount;
    }

    public void startRequestMessageCount() {
        if (this.mHandler == null) {
            this.mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    MessageCountManager.this.requestMessageCount();
                    MessageCountManager.this.mHandler.sendEmptyMessageDelayed(1, MessageCountManager.this.mRefreshDuration);
                }
            };
        }
        this.mHandler.sendEmptyMessage(1);
    }

    public void stopRequestMessageCount() {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    private void requestMessageCount(final boolean isFollowPage) {
        try {
            FengApplication.getInstance().httpRequest(HttpConstant.SEARCHMESSAGECOUNT, new HashMap(), new OkHttpResponseCallback() {
                public void onNetworkError() {
                    EventBus.getDefault().post(new MessageCountRefreshEvent());
                }

                public void onStart() {
                }

                public void onFinish() {
                    if (isFollowPage) {
                        MessageCountManager.this.redpoint = 0;
                    }
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    EventBus.getDefault().post(new MessageCountRefreshEvent());
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonBody = jsonResult.getJSONObject("body");
                            MessageCountManager.this.sysCount = jsonBody.getInt(HttpConstant.SYSTEM);
                            MessageCountManager.this.atWenZhangCount = jsonBody.getInt("at");
                            MessageCountManager.this.atCommentCount = jsonBody.getInt("atcomment");
                            MessageCountManager.this.atdiscuss = jsonBody.getInt("atdiscuss");
                            MessageCountManager.this.atCount = (MessageCountManager.this.atWenZhangCount + MessageCountManager.this.atCommentCount) + MessageCountManager.this.atdiscuss;
                            MessageCountManager.this.fansCount = jsonBody.getInt("fins");
                            MessageCountManager.this.receivedCount = jsonBody.getInt("received");
                            MessageCountManager.this.commentCount = jsonBody.getInt(HttpConstant.COMMENT);
                            MessageCountManager.this.praiseCount = jsonBody.getInt(HttpConstant.PRAISE);
                            MessageCountManager.this.snsfollow = jsonBody.getInt("snsfollow");
                            MessageCountManager.this.newhotshow = jsonBody.getInt("newhotshow");
                            MessageCountManager.this.redpoint = jsonBody.getInt("redpoint");
                            if (isFollowPage) {
                                MessageCountManager.this.redpoint = 0;
                            }
                            if (jsonBody.has(HttpConstant.COMMUNITY)) {
                                MessageCountManager.this.community = jsonBody.getInt(HttpConstant.COMMUNITY);
                                JSONArray jsonArray = jsonBody.getJSONArray("communitylist");
                                int size = jsonArray.length();
                                MessageCountManager.this.communitylist.clear();
                                for (int i = 0; i < size; i++) {
                                    MessageCountManager.this.communitylist.add(Integer.valueOf(jsonArray.getInt(i)));
                                }
                            }
                            EventBus.getDefault().post(new MessageCountRefreshEvent());
                            return;
                        }
                        FengApplication.getInstance().upLoadLog(true, "user/messagecount/  " + code);
                        EventBus.getDefault().post(new MessageCountRefreshEvent());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SEARCHMESSAGECOUNT, content, e);
                        EventBus.getDefault().post(new MessageCountRefreshEvent());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestMessageCount() {
        requestMessageCount(false);
    }

    private void clearFollowDot(String strPage) {
        try {
            Map<String, Object> map = new HashMap();
            map.put(HttpConstant.PAGE, strPage);
            FengApplication.getInstance().httpRequest(HttpConstant.USER_CLEARFOLLOWCHANNE, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                }

                public void onStart() {
                }

                public void onFinish() {
                    MessageCountManager.this.requestMessageCount(true);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                }

                public void onSuccess(int statusCode, String content) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
