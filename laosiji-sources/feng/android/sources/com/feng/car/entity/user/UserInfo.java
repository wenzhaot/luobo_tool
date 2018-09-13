package com.feng.car.entity.user;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import com.feng.car.FengApplication;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.operation.OperationCallback;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.operation.UserOperation;
import com.feng.car.utils.BlackUtil;
import com.feng.car.utils.BlackUtil.BlackExcuteCallBack;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.SharedUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.utl.BaseMonitor;
import java.util.Map;
import org.json.JSONObject;

public class UserInfo extends BaseInfo {
    public static final String FOLLOW_KEY_COUNT = "follow_key_count";
    public static final String ID_KEY = "ID_KEY";
    public static final String IMAGE_KEY_HEIGHT = "IMAGE_KEY_HEIGHT";
    public static final String IMAGE_KEY_MIMETYPE = "IMAGE_KEY_MIMETYPE";
    public static final String IMAGE_KEY_URL = "IMAGE_KEY_URL";
    public static final String IMAGE_KEY_WIDTH = "IMAGE_KEY_WIDTH";
    public static final String IS_ADMINISTRATOR = "IS_ADMINISTRATOR";
    public static final String IS_COMPLETE_INFO = "is_complete_info";
    public static final String LOCAL_OPEN_SHOP_SALE_TYPE = "local_open_shop_sale_type";
    public static final String LOCAL_OPEN_SHOP_STATE = "local_open_shop_state";
    public static final String MOBLIE_KEY = "MOBLIE_KEY";
    public static final String SEX_KEY = "SEX_KEY";
    public static final transient int SHOP_AUDITINT_ALREADY_ADD_STATE = 3;
    public static final transient int SHOP_NOT_OPEN_STATE = 1;
    public static final transient int SHOP_NO_AUDIT_NO_ADD_STATE = 2;
    public static final transient int SHOP_OPEN_SUCCESS_STATE = 4;
    public static final String SIGNATURE_KEY = "signature";
    public static final String THIRD_LOGIN_5X = "third_login_5x";
    public static final String THIRD_QQ_STATE = "third_qq_state";
    public static final String THIRD_WEIBO_STATE = "third_weibo_state";
    public static final String THIRD_WEIXIN_STATE = "third_weixin_state";
    public static final String TOKEN_KEY = "TOKEN_KEY";
    public static final String USERNAME_KEY = "USERNAME_KEY";
    private Map<String, Object> authenticated;
    public final ObservableField<String> award = new ObservableField("");
    public final ObservableInt awardrank = new ObservableInt(0);
    public int canpublishvideo = 1;
    public ConnectBindEntity connect = new ConnectBindEntity();
    public String createtime = "";
    public final ObservableInt fanscount = new ObservableInt(0);
    public final ObservableInt followcount = new ObservableInt(0);
    public final ObservableInt forwardcount = new ObservableInt(0);
    public int id;
    public ImageInfo image = new ImageInfo();
    public boolean isShowButton = false;
    public transient boolean isShowFollowState = false;
    public int isadministrator = 0;
    private int isauthenticated = 0;
    public final ObservableInt isblack = new ObservableInt(0);
    public int iscomplete;
    public int isfans;
    public final ObservableInt isfollow = new ObservableInt(0);
    private int ismy = -1;
    public boolean local_new_user = false;
    private int local_open_shop_state = -2;
    private int local_saletype = -1;
    public int messageflag;
    public final ObservableInt microcount = new ObservableInt(0);
    public final ObservableField<String> name = new ObservableField("");
    public String phonenumber = "";
    public PushModel push = new PushModel();
    public Remind remind = new Remind();
    public final ObservableInt sex = new ObservableInt(1);
    public final ObservableField<String> signature = new ObservableField("这家伙很懒，什么都没留下...");
    public final ObservableInt snscount = new ObservableInt(0);
    public final ObservableInt threadcount = new ObservableInt(0);
    public final ObservableField<String> time = new ObservableField("");
    public String token = "";
    private transient UserOperation userOperation = new UserOperation(this);

    public int getLocalSaleType() {
        return this.local_saletype;
    }

    public void setLocalSaleType(Context context, int saletype) {
        SharedUtil.getInt(context, LOCAL_OPEN_SHOP_SALE_TYPE, saletype);
        this.local_saletype = saletype;
    }

    public int getLocalOpenShopState() {
        if (this.local_open_shop_state == -2 || this.local_open_shop_state == -1 || this.local_open_shop_state == 3) {
            return 1;
        }
        if (this.local_open_shop_state == 0) {
            return 2;
        }
        if (this.local_open_shop_state == 1) {
            return 3;
        }
        return this.local_open_shop_state == 2 ? 4 : 1;
    }

    public void setLocalOpenShopState(Context context, int local_open_shop_state) {
        SharedUtil.getInt(context, LOCAL_OPEN_SHOP_STATE, local_open_shop_state);
        this.local_open_shop_state = local_open_shop_state;
    }

    public int getIsMy() {
        if (this.ismy != -1) {
            return this.ismy;
        }
        if (FengApplication.getInstance().isLoginUser() && FengApplication.getInstance().getUserInfo().id == this.id) {
            return 1;
        }
        return 0;
    }

    public void setIsMy(int ismy) {
        this.ismy = ismy;
    }

    public boolean getIsFirstAuthenticated() {
        if (this.isauthenticated != 1 || this.authenticated == null || this.authenticated.size() <= 0 || !this.authenticated.containsKey(PushConstants.PUSH_TYPE_THROUGH_MESSAGE)) {
            return false;
        }
        return true;
    }

    public String getIsFirstAuthenticatedInfo() {
        if (this.isauthenticated != 1 || this.authenticated == null || this.authenticated.size() <= 0 || !this.authenticated.containsKey(PushConstants.PUSH_TYPE_THROUGH_MESSAGE)) {
            return (String) this.signature.get();
        }
        String str = this.authenticated.get(PushConstants.PUSH_TYPE_THROUGH_MESSAGE).toString();
        if (TextUtils.isEmpty(str)) {
            return (String) this.signature.get();
        }
        return str;
    }

    public String getFirstAuthenticatedInfo() {
        if (this.isauthenticated != 1 || this.authenticated == null || this.authenticated.size() <= 0 || !this.authenticated.containsKey(PushConstants.PUSH_TYPE_THROUGH_MESSAGE)) {
            return (String) this.signature.get();
        }
        String str = this.authenticated.get(PushConstants.PUSH_TYPE_THROUGH_MESSAGE).toString();
        if (TextUtils.isEmpty(str)) {
            return (String) this.signature.get();
        }
        return "老司机认证：" + str;
    }

    public String getAward() {
        if (TextUtils.isEmpty((CharSequence) this.award.get())) {
            return "暂未上榜";
        }
        return "¥" + ((String) this.award.get());
    }

    public UserInfo() {
        this.name.set("");
        this.isfollow.set(0);
    }

    public void setHeadImageInfo(ImageInfo info) {
        this.image = info;
    }

    public ImageInfo getHeadImageInfo() {
        if (TextUtils.isEmpty(this.image.url)) {
            if (getIsMy() != 1 || TextUtils.isEmpty(this.connect.logourl)) {
                this.image.hash = "FqSLtpREo7dZPU4hBeja7D3RBf7N";
                this.image.width = 1536;
                this.image.height = 1536;
                this.image.mimetype = 1;
                this.image.url = FengConstant.DEFAULT_USER_HEAD_URL;
            } else {
                this.image.url = this.connect.logourl;
            }
        }
        return this.image;
    }

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.TOKEN)) {
                this.token = object.getString(HttpConstant.TOKEN);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("name")) {
                this.name.set(object.getString("name"));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
            this.name.set("");
        }
        try {
            if (object.has("sex")) {
                this.sex.set(object.getInt("sex"));
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("createtime")) {
                this.createtime = object.getString("createtime");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("canpublishvideo")) {
                this.canpublishvideo = object.getInt("canpublishvideo");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
            this.canpublishvideo = 1;
        }
        try {
            if (object.has("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("ismy")) {
                this.ismy = object.getInt("ismy");
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("awardrank")) {
                this.awardrank.set(object.getInt("awardrank"));
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("signature")) {
                String str = object.getString("signature");
                if (TextUtils.isEmpty(str)) {
                    this.signature.set("这家伙很懒，什么都没留下...");
                } else {
                    this.signature.set(str);
                }
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.ISFOLLOW)) {
                this.isfollow.set(object.getInt(HttpConstant.ISFOLLOW));
                if (this.isfollow.get() == 0) {
                    this.isShowButton = true;
                } else {
                    this.isShowButton = false;
                }
            }
        } catch (Exception e2222222222) {
            e2222222222.printStackTrace();
            this.isfollow.set(0);
        }
        try {
            if (object.has("isblack")) {
                if (BlackUtil.getInstance().isInBlackList(this)) {
                    this.isblack.set(1);
                } else {
                    this.isblack.set(object.getInt("isblack"));
                }
            }
        } catch (Exception e22222222222) {
            e22222222222.printStackTrace();
        }
        try {
            if (object.has("isadministrator")) {
                this.isadministrator = object.getInt("isadministrator");
            }
        } catch (Exception e222222222222) {
            e222222222222.printStackTrace();
        }
        try {
            if (object.has("iscomplete")) {
                this.iscomplete = object.getInt("iscomplete");
            }
        } catch (Exception e2222222222222) {
            e2222222222222.printStackTrace();
        }
        try {
            if (object.has(BaseMonitor.ALARM_POINT_CONNECT)) {
                this.connect.parser(object.getJSONObject(BaseMonitor.ALARM_POINT_CONNECT));
            }
        } catch (Exception e22222222222222) {
            e22222222222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.PHONENUMBER)) {
                this.phonenumber = object.getString(HttpConstant.PHONENUMBER);
            }
        } catch (Exception e222222222222222) {
            e222222222222222.printStackTrace();
        }
        try {
            if (object.has("isfans")) {
                this.isfans = object.getInt("isfans");
            }
        } catch (Exception e2222222222222222) {
            e2222222222222222.printStackTrace();
        }
        try {
            if (object.has("followcount")) {
                this.followcount.set(object.getInt("followcount"));
            }
        } catch (Exception e22222222222222222) {
            e22222222222222222.printStackTrace();
        }
        try {
            if (object.has("fanscount")) {
                this.fanscount.set(object.getInt("fanscount"));
            }
        } catch (Exception e222222222222222222) {
            e222222222222222222.printStackTrace();
        }
        try {
            if (object.has("threadcount")) {
                this.threadcount.set(object.getInt("threadcount"));
            }
        } catch (Exception e2222222222222222222) {
            e2222222222222222222.printStackTrace();
        }
        try {
            if (object.has("snscount")) {
                this.snscount.set(object.getInt("snscount"));
            }
        } catch (Exception e22222222222222222222) {
            e22222222222222222222.printStackTrace();
        }
        try {
            if (object.has("microcount")) {
                this.microcount.set(object.getInt("microcount"));
            }
        } catch (Exception e222222222222222222222) {
            e222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("forwardcount")) {
                this.forwardcount.set(object.getInt("forwardcount"));
            }
        } catch (Exception e2222222222222222222222) {
            e2222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("time")) {
                this.time.set(object.getString("time"));
            }
        } catch (Exception e22222222222222222222222) {
            e22222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("award")) {
                this.award.set(object.getString("award"));
            }
        } catch (Exception e222222222222222222222222) {
            e222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("remind")) {
                this.remind.parser(object.getJSONObject("remind"));
            }
        } catch (Exception e2222222222222222222222222) {
            e2222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("push")) {
                this.push.parser(object.getJSONObject("push"));
            }
        } catch (Exception e22222222222222222222222222) {
            e22222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("messageflag")) {
                this.messageflag = object.getInt("messageflag");
            }
        } catch (Exception e222222222222222222222222222) {
            e222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("isauthenticated")) {
                this.isauthenticated = object.getInt("isauthenticated");
            }
        } catch (Exception e2222222222222222222222222222) {
            e2222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("authenticated")) {
                this.authenticated = JsonUtil.getMapForJson(object.getJSONObject("authenticated").toString());
            }
        } catch (Exception e22222222222222222222222222222) {
            e22222222222222222222222222222.printStackTrace();
        }
    }

    public void intentToPersonalHome(Context context) {
        if (this.userOperation == null) {
            this.userOperation = new UserOperation(this);
        }
        this.userOperation.intentToPersonalHome(context, -1);
    }

    public void intentToPersonalHome(Context context, int type) {
        if (this.userOperation == null) {
            this.userOperation = new UserOperation(this);
        }
        this.userOperation.intentToPersonalHome(context, type);
    }

    public void followOperation(Context context, OperationCallback callback) {
        if (this.userOperation == null) {
            this.userOperation = new UserOperation(this);
        }
        this.userOperation.followOperation(context, callback);
    }

    public void reportOperation(Context context) {
        if (this.userOperation == null) {
            this.userOperation = new UserOperation(this);
        }
        this.userOperation.reportOperation(context);
    }

    public void updateInfoOperation(Map<String, Object> map, OperationCallback callback) {
        if (this.userOperation == null) {
            this.userOperation = new UserOperation(this);
        }
        this.userOperation.updateInfoOperation(map, callback);
    }

    public void blackOperation(Context context, BlackExcuteCallBack callBack) {
        if (this.userOperation == null) {
            this.userOperation = new UserOperation(this);
        }
        this.userOperation.blackOperation(context, callBack);
    }

    public void intentToSendPost(Context context) {
        intentToSendPost(context, null);
    }

    public void intentToSendPost(Context context, CircleInfo circleInfo) {
        if (this.userOperation == null) {
            this.userOperation = new UserOperation(this);
        }
        this.userOperation.intentToSendPost(context, circleInfo);
    }

    public void checkShopState(Context context, SuccessFailCallback callback) {
        if (this.userOperation == null) {
            this.userOperation = new UserOperation(this);
        }
        this.userOperation.checkShopState(context, callback);
    }
}
