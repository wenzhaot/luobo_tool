package com.feng.car.entity.thread;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.sns.ResourcesInfoList;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsPostResources;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.listener.CommentItemListener;
import com.feng.car.operation.CommentInfoOperation;
import com.feng.car.operation.OperationCallback;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.HttpConstant;
import org.json.JSONArray;
import org.json.JSONObject;

public class CommentInfo extends BaseInfo {
    public static final int CHAT_ITEM_TAG = 50002;
    public static final int COPY_ITEM_TAG = 50003;
    public static final int DELETE_ITEM_TAG = 50005;
    public static final int GET_COMMENT = 2;
    public static final int REPLY_ITEM_TAG = 50001;
    public static final int REPORT_ITEM_TAG = 50004;
    public static final int SEND_COMMENT = 1;
    public final ObservableInt adminaccept = new ObservableInt();
    public UserInfo adminuser;
    private transient CommentInfoOperation commentInfoOperation = new CommentInfoOperation(this);
    public final ObservableField<String> content = new ObservableField("");
    public int id = 0;
    public ImageInfo image = new ImageInfo();
    public ImageInfo imagecomment = new ImageInfo();
    public ResourcesInfoList imagelist = new ResourcesInfoList();
    public transient boolean isLocalExtend = false;
    public int isdel = 0;
    public final ObservableInt islandlord = new ObservableInt();
    public int isreply = 0;
    public final ObservableInt istop = new ObservableInt(0);
    public ObservableInt istopbyauthor = new ObservableInt(0);
    public final ObservableInt level = new ObservableInt(0);
    public int originparentid = 0;
    public CommentInfo parent;
    public BaseListModel<CommentInfo> reply = new BaseListModel();
    public UserInfo replyuser = new UserInfo();
    public int resourceid;
    public SnsInfo sns = new SnsInfo();
    public final ObservableField<String> time = new ObservableField("");
    public final ObservableInt top = new ObservableInt();
    public UserInfo user = new UserInfo();
    public ResourcesInfoList videolist = new ResourcesInfoList();

    public boolean equals(Object obj) {
        return this.id == ((CommentInfo) obj).id;
    }

    public void parser(JSONObject object) {
        JSONArray jsonArray;
        int size;
        int i;
        SnsPostResources postResources;
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("originparentid")) {
                this.originparentid = object.getInt("originparentid");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("content")) {
                this.content.set(object.getString("content"));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("time")) {
                this.time.set(object.getString("time"));
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("top")) {
                this.top.set(object.getInt("top"));
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("level")) {
                this.level.set(object.getInt("level"));
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("istop")) {
                this.istop.set(object.getInt("istop"));
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.USER)) {
                if (this.user == null) {
                    this.user = new UserInfo();
                }
                this.user.parser(object.getJSONObject(HttpConstant.USER));
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("adminuser")) {
                if (this.adminuser == null) {
                    this.adminuser = new UserInfo();
                }
                this.adminuser.parser(object.getJSONObject("adminuser"));
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("sns")) {
                if (this.sns == null) {
                    this.sns = new SnsInfo();
                }
                this.sns.parser(object.getJSONObject("sns"));
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (object.has("parent")) {
                if (this.parent == null) {
                    this.parent = new CommentInfo();
                }
                this.parent.parser(object.getJSONObject("parent"));
            }
        } catch (Exception e2222222222) {
            e2222222222.printStackTrace();
        }
        try {
            if (object.has("isdel")) {
                this.isdel = object.getInt("isdel");
            }
        } catch (Exception e3) {
        }
        try {
            if (object.has("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e22222222222) {
            e22222222222.printStackTrace();
        }
        try {
            if (object.has("imagecomment")) {
                this.image.parser(object.getJSONObject("imagecomment"));
            }
        } catch (Exception e222222222222) {
            e222222222222.printStackTrace();
        }
        try {
            if (object.has("adminaccept")) {
                this.adminaccept.set(object.getInt("adminaccept"));
            }
        } catch (Exception e2222222222222) {
            e2222222222222.printStackTrace();
        }
        try {
            if (object.has("islandlord")) {
                this.islandlord.set(object.getInt("islandlord"));
            }
        } catch (Exception e22222222222222) {
            e22222222222222.printStackTrace();
        }
        try {
            if (object.has("isreply")) {
                this.isreply = object.getInt("isreply");
            }
        } catch (Exception e222222222222222) {
            e222222222222222.printStackTrace();
        }
        try {
            if (object.has("resourceid")) {
                this.resourceid = object.getInt("resourceid");
            }
        } catch (Exception e2222222222222222) {
            e2222222222222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.IMAGELIST)) {
                jsonArray = object.getJSONArray(HttpConstant.IMAGELIST);
                size = jsonArray.length();
                this.imagelist.clear();
                for (i = 0; i < size; i++) {
                    postResources = new SnsPostResources();
                    postResources.parser(jsonArray.getJSONObject(i));
                    this.imagelist.add(postResources);
                }
            }
        } catch (Exception e22222222222222222) {
            e22222222222222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.VIDEOLIST)) {
                jsonArray = object.getJSONArray(HttpConstant.VIDEOLIST);
                size = jsonArray.length();
                this.videolist.clear();
                for (i = 0; i < size; i++) {
                    postResources = new SnsPostResources();
                    postResources.parser(jsonArray.getJSONObject(i));
                    this.videolist.add(postResources);
                }
            }
        } catch (Exception e222222222222222222) {
            e222222222222222222.printStackTrace();
        }
        try {
            if (object.has("istopbyauthor")) {
                this.istopbyauthor.set(object.getInt("istopbyauthor"));
            }
        } catch (Exception e2222222222222222222) {
            e2222222222222222222.printStackTrace();
            this.istopbyauthor.set(0);
        }
        try {
            if (object.has("replyuser") && !object.isNull("replyuser")) {
                if (this.replyuser == null) {
                    this.replyuser = new UserInfo();
                }
                this.replyuser.parser(object.getJSONObject("replyuser"));
            }
        } catch (Exception e22222222222222222222) {
            e22222222222222222222.printStackTrace();
        }
        try {
            if (object.has("reply")) {
                this.reply.parser(CommentInfo.class, object.getJSONObject("reply"));
            }
        } catch (Exception e222222222222222222222) {
            e222222222222222222222.printStackTrace();
        }
    }

    public void intentToSendCommentActivity(Context context) {
        if (this.commentInfoOperation == null) {
            this.commentInfoOperation = new CommentInfoOperation(this);
        }
        this.commentInfoOperation.intentToSendCommentActivity(context, 0, "");
    }

    public void intentToSendCommentActivity(Context context, int viewDy, String key) {
        if (this.commentInfoOperation == null) {
            this.commentInfoOperation = new CommentInfoOperation(this);
        }
        this.commentInfoOperation.intentToSendCommentActivity(context, viewDy, key);
    }

    public void intentToCommentReplyListActivity(Context context, int type, int selectCommentID) {
        if (this.commentInfoOperation == null) {
            this.commentInfoOperation = new CommentInfoOperation(this);
        }
        this.commentInfoOperation.intentToCommentReplyListActivity(context, type, selectCommentID);
    }

    public void processMenuClick(int tag, Context context, SnsInfo snsInfo) {
        if (this.commentInfoOperation == null) {
            this.commentInfoOperation = new CommentInfoOperation(this);
        }
        this.commentInfoOperation.processMenuClick(tag, context, snsInfo, 0, "", null);
    }

    public void processMenuClick(int tag, Context context, SnsInfo snsInfo, int viewDy, String key, CommentItemListener listener) {
        if (this.commentInfoOperation == null) {
            this.commentInfoOperation = new CommentInfoOperation(this);
        }
        this.commentInfoOperation.processMenuClick(tag, context, snsInfo, viewDy, key, listener);
    }

    public void deleteDraftCommentOperation(Context context, SnsInfo snsInfo, OperationCallback callback) {
        if (this.commentInfoOperation == null) {
            this.commentInfoOperation = new CommentInfoOperation(this);
        }
        this.commentInfoOperation.deleteDraftCommentOperation(context, snsInfo, callback);
    }

    public void clickPraiseOperation(Context context, SuccessFailCallback callback) {
        if (this.commentInfoOperation == null) {
            this.commentInfoOperation = new CommentInfoOperation(this);
        }
        this.commentInfoOperation.clickPraiseOperation(context, callback);
    }
}
