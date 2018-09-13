package com.feng.car.entity.sns;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.choosecar.ChooseCarInfo;
import com.feng.car.entity.choosecar.DiscussAudioInfo;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.circle.CircleInfoList;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.entity.dealer.CommodityServeInfo;
import com.feng.car.entity.dealer.ShopInfo;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.operation.OperationCallback;
import com.feng.car.operation.SnsOperation;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.HttpConstant;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import org.json.JSONArray;
import org.json.JSONObject;

public class SnsInfo extends BaseInfo {
    public AdvertInfo advertInfo;
    public int anonymouspraisenum = 0;
    public DiscussAudioInfo audio = new DiscussAudioInfo();
    public List<CarSeriesInfo> carsList = new ArrayList();
    public final ObservableInt cilckcount = new ObservableInt(0);
    public final ObservableInt commentcount = new ObservableInt(0);
    public CircleInfoList communitylist = new CircleInfoList();
    public int containcoverimage = 1;
    public final ObservableField<String> createtime = new ObservableField("");
    public String dataJson;
    public final ObservableField<String> description = new ObservableField("");
    public ChooseCarInfo discussinfo = new ChooseCarInfo();
    public final ObservableInt exposurecount = new ObservableInt(0);
    public int feedtype = 0;
    public HotShowInfo hotshow = new HotShowInfo();
    public int id = 0;
    public ImageInfo image = new ImageInfo();
    public final ObservableInt isfavorite = new ObservableInt(0);
    public int isflag = 0;
    public int ishistory = 0;
    public int ishotshow = 0;
    public int ismythread = 0;
    public int isoldthread = 0;
    public int isonlyvideo = 0;
    public final ObservableInt ispraise = new ObservableInt(0);
    public List<SnsPostResources> list = new ArrayList();
    public final ObservableField<String> modifytime = new ObservableField("");
    public int ontop = 0;
    public final ObservableInt praisecount = new ObservableInt(0);
    public final ObservableField<String> publishtime = new ObservableField("");
    public List<SnsInfo> recommendList = new ArrayList();
    public int resourceid = 0;
    public int rts = -1;
    public CommodityServeInfo scsl = new CommodityServeInfo();
    public List<CommodityInfo> shopcommodityvos = new ArrayList();
    public ShopInfo shopvo = new ShopInfo();
    private transient SnsOperation snsOperation = new SnsOperation(this);
    public int snstype = 0;
    public final ObservableField<String> title = new ObservableField("");
    public UserInfo user = new UserInfo();
    public int voiceLocalStates = 0;

    public ImageInfo getCoverImage() {
        if (this.snstype == 1 || this.snstype == 0) {
            if (this.isonlyvideo != 1 || ((!TextUtils.isEmpty(this.image.url) && this.image.height != 0 && this.image.width != 0) || this.list.size() <= 0)) {
                return this.image;
            }
            return ((SnsPostResources) this.list.get(0)).image;
        } else if (this.snstype != 1000 || this.advertInfo.tmpmap.image.size() <= 0) {
            return this.image;
        } else {
            return (ImageInfo) this.advertInfo.tmpmap.image.get(0);
        }
    }

    public boolean isHasText() {
        if (TextUtils.isEmpty(getCoverImage().url) || getCoverImage().width == 0 || getCoverImage().height == 0) {
            return true;
        }
        return false;
    }

    public String getFeedTitleOrDes() {
        if (this.isflag == -1 || this.isflag == -3) {
            return "原文章正在修改，稍后就能看到啦！";
        }
        if (this.isflag == -2) {
            return "视频正在审核中，稍后就能看到啦！";
        }
        if (this.isflag != 0) {
            return "真遗憾，原文章已被删除";
        }
        if (this.snstype == 1000 && !TextUtils.isEmpty(this.advertInfo.tmpmap.title)) {
            return this.advertInfo.tmpmap.title;
        }
        if (!TextUtils.isEmpty((CharSequence) this.title.get())) {
            return (String) this.title.get();
        }
        if (TextUtils.isEmpty((CharSequence) this.description.get())) {
            return "";
        }
        return (String) this.description.get();
    }

    public String getShareTitleOrDes() {
        if (this.snstype == 9) {
            return "听 " + ((String) this.user.name.get()) + " 聊聊" + this.discussinfo.title;
        }
        if (!TextUtils.isEmpty((CharSequence) this.title.get())) {
            return (String) this.title.get();
        }
        if (TextUtils.isEmpty((CharSequence) this.description.get())) {
            return "快来看看老司机上的这篇帖子";
        }
        if (((String) this.description.get()).length() > 15) {
            return ((String) this.description.get()).substring(0, 15) + "...";
        }
        return (String) this.description.get();
    }

    public String getDraftFeedTitleOrDes() {
        if (this.snstype == 1000 && !TextUtils.isEmpty(this.advertInfo.tmpmap.title)) {
            return this.advertInfo.tmpmap.title;
        }
        if (!TextUtils.isEmpty((CharSequence) this.title.get())) {
            return (String) this.title.get();
        }
        if (TextUtils.isEmpty((CharSequence) this.description.get())) {
            return "";
        }
        return (String) this.description.get();
    }

    public String getLocalkey() {
        if (this.snstype == 1000 && this.advertInfo != null && this.advertInfo.isinner == 0) {
            return this.advertInfo.adid + "_2";
        }
        if (this.snstype == 1000 && this.advertInfo != null && this.advertInfo.isinner == 0) {
            return this.id + "_" + this.advertInfo.adid + "_3";
        }
        return this.id + "_1";
    }

    public String getMineHotCircleName() {
        if (this.communitylist.size() <= 0) {
            return "";
        }
        CircleInfo circleInfoFrom = this.communitylist.get(0);
        for (CircleInfo circleInfo : this.communitylist.getCircleList()) {
            if (circleInfo.isfans.get() == 1) {
                return circleInfo.name;
            }
        }
        return circleInfoFrom.name;
    }

    private ImageInfo getTransformImage(ImageInfo imageInfo, UserInfo userInfo) {
        if (TextUtils.isEmpty(imageInfo.url)) {
            return userInfo.getHeadImageInfo();
        }
        return imageInfo;
    }

    public ImageInfo getQuoteImageInfo() {
        if (this.snstype != 0 && this.snstype != 1 && this.snstype != 9) {
            return this.image;
        }
        if (!TextUtils.isEmpty(this.image.url)) {
            return this.image;
        }
        if (this.snstype == 9) {
            return getTransformImage(this.discussinfo.image, this.user);
        }
        return this.user.getHeadImageInfo();
    }

    public String getQuoteDescription() {
        if (this.snstype != 0 && this.snstype != 1 && this.snstype != 9) {
            return "";
        }
        if (this.isflag == 0) {
            return getFeedTitleOrDes();
        }
        if (this.isflag == -1 || this.isflag == -2 || this.isflag == -3) {
            return "该文章正在被作者修改，稍后就能看到了！";
        }
        if (this.snstype == 9 || this.snstype == 10) {
            return "真遗憾，该观点已被删除。";
        }
        return "真遗憾，该文章已被删除。";
    }

    public void parser(JSONObject object) {
        JSONArray jsonArray;
        int size;
        int i;
        this.dataJson = object.toString();
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.id = 0;
        }
        try {
            if (object.has("title")) {
                this.title.set(object.getString("title"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.DESCRIPTION)) {
                if (object.get(HttpConstant.DESCRIPTION) instanceof String) {
                    String strDes = object.getString(HttpConstant.DESCRIPTION);
                    Matcher emoticon = HttpConstant.PATTERN_EMOTICON.matcher(strDes);
                    while (emoticon.find()) {
                        String key = emoticon.group();
                        String value = (String) EmoticonsRule.sXhsEmoticonTextHashMap.get(key);
                        if (!TextUtils.isEmpty(value)) {
                            strDes = strDes.replace(key, value);
                        }
                    }
                    this.description.set(strDes);
                } else {
                    this.description.set("");
                }
            }
        } catch (Exception e22) {
            e22.printStackTrace();
            this.description.set("");
        }
        try {
            if (object.has("resourceid")) {
                this.resourceid = object.getInt("resourceid");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
            this.resourceid = 0;
        }
        try {
            if (object.has("snstype")) {
                this.snstype = object.getInt("snstype");
            }
            if (this.snstype == 2 || this.snstype == 3 || this.snstype == 10) {
                this.id = 0;
                return;
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
            this.snstype = 0;
        }
        try {
            if (object.has("image") && !object.isNull("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("createtime")) {
                this.createtime.set(object.getString("createtime"));
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("publishtime")) {
                this.publishtime.set(object.getString("publishtime"));
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("modifytime")) {
                this.modifytime.set(object.getString("modifytime"));
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("cilckcount")) {
                this.cilckcount.set(object.getInt("cilckcount"));
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (object.has("anonymouspraisenum")) {
                this.anonymouspraisenum = object.getInt("anonymouspraisenum");
            }
        } catch (Exception e2222222222) {
            e2222222222.printStackTrace();
        }
        try {
            if (object.has("readcount")) {
                this.exposurecount.set(object.getInt("readcount"));
            }
        } catch (Exception e22222222222) {
            e22222222222.printStackTrace();
        }
        try {
            if (object.has("commentcount")) {
                if (object.getInt("commentcount") >= 0) {
                    this.commentcount.set(object.getInt("commentcount"));
                } else {
                    this.commentcount.set(0);
                }
            }
        } catch (Exception e222222222222) {
            e222222222222.printStackTrace();
        }
        try {
            if (object.has("praisecount")) {
                if (object.getInt("praisecount") >= 0) {
                    this.praisecount.set(object.getInt("praisecount"));
                } else {
                    this.praisecount.set(0);
                }
            }
        } catch (Exception e2222222222222) {
            e2222222222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.USER) && !object.isNull(HttpConstant.USER)) {
                this.user.parser(object.getJSONObject(HttpConstant.USER));
            }
        } catch (Exception e22222222222222) {
            e22222222222222.printStackTrace();
        }
        try {
            if (object.has("isfavorite")) {
                this.isfavorite.set(object.getInt("isfavorite"));
            }
        } catch (Exception e222222222222222) {
            e222222222222222.printStackTrace();
        }
        try {
            if (object.has("ispraise")) {
                this.ispraise.set(object.getInt("ispraise"));
            }
        } catch (Exception e2222222222222222) {
            e2222222222222222.printStackTrace();
        }
        try {
            if (object.has("ismythread")) {
                this.ismythread = object.getInt("ismythread");
            }
        } catch (Exception e22222222222222222) {
            e22222222222222222.printStackTrace();
        }
        try {
            if (object.has("ishotshow")) {
                this.ishotshow = object.getInt("ishotshow");
            }
        } catch (Exception e222222222222222222) {
            e222222222222222222.printStackTrace();
        }
        try {
            if (object.has("list")) {
                jsonArray = object.getJSONArray("list");
                size = jsonArray.length();
                this.list.clear();
                for (i = 0; i < size; i++) {
                    SnsPostResources postResources = new SnsPostResources();
                    postResources.parser(jsonArray.getJSONObject(i));
                    this.list.add(postResources);
                }
            }
        } catch (Exception e2222222222222222222) {
            e2222222222222222222.printStackTrace();
        }
        try {
            if (object.has("isflag")) {
                this.isflag = object.getInt("isflag");
            }
        } catch (Exception e22222222222222222222) {
            e22222222222222222222.printStackTrace();
        }
        try {
            if (object.has("communitylist")) {
                jsonArray = object.getJSONArray("communitylist");
                size = jsonArray.length();
                this.communitylist.clear();
                for (i = 0; i < size; i++) {
                    CircleInfo info = new CircleInfo();
                    info.parser(jsonArray.getJSONObject(i));
                    this.communitylist.add(info);
                }
            }
        } catch (Exception e222222222222222222222) {
            e222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("carsforinfo")) {
                jsonArray = object.getJSONArray("carsforinfo");
                size = jsonArray.length();
                this.carsList.clear();
                for (i = 0; i < size; i++) {
                    CarSeriesInfo info2 = new CarSeriesInfo();
                    info2.parser(jsonArray.getJSONObject(i));
                    this.carsList.add(info2);
                }
            }
        } catch (Exception e2222222222222222222222) {
            e2222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("aroundhotshow")) {
                jsonArray = object.getJSONArray("aroundhotshow");
                size = jsonArray.length();
                this.recommendList.clear();
                for (i = 0; i < size; i++) {
                    SnsInfo info3 = new SnsInfo();
                    info3.parser(jsonArray.getJSONObject(i));
                    this.recommendList.add(info3);
                }
            }
        } catch (Exception e22222222222222222222222) {
            e22222222222222222222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.HOTSHOW) && !object.isNull(HttpConstant.HOTSHOW)) {
                this.hotshow.parser(object.getJSONObject(HttpConstant.HOTSHOW));
            }
        } catch (Exception e222222222222222222222222) {
            e222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("audio") && !object.isNull("audio")) {
                this.audio.parser(object.getJSONObject("audio"));
            }
        } catch (Exception e2222222222222222222222222) {
            e2222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("discussinfo") && !object.isNull("discussinfo")) {
                this.discussinfo.parser(object.getJSONObject("discussinfo"));
            }
        } catch (Exception e22222222222222222222222222) {
            e22222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("feedtype")) {
                this.feedtype = object.getInt("feedtype");
            }
        } catch (Exception e222222222222222222222222222) {
            e222222222222222222222222222.printStackTrace();
            this.feedtype = 0;
        }
        try {
            if (object.has("ishistory")) {
                this.ishistory = object.getInt("ishistory");
            }
        } catch (Exception e2222222222222222222222222222) {
            e2222222222222222222222222222.printStackTrace();
            this.ishistory = 0;
        }
        try {
            if (object.has("ontop")) {
                this.ontop = object.getInt("ontop");
            }
        } catch (Exception e22222222222222222222222222222) {
            e22222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("containcoverimage")) {
                this.containcoverimage = object.getInt("containcoverimage");
            }
        } catch (Exception e222222222222222222222222222222) {
            e222222222222222222222222222222.printStackTrace();
            this.containcoverimage = 1;
        }
        try {
            if (object.has("shopvo")) {
                this.shopvo.parser(object.getJSONObject("shopvo"));
            }
        } catch (Exception e2222222222222222222222222222222) {
            e2222222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("shopcommodityvos")) {
                jsonArray = object.getJSONArray("shopcommodityvos");
                size = jsonArray.length();
                this.shopcommodityvos.clear();
                for (i = 0; i < size; i++) {
                    CommodityInfo info4 = new CommodityInfo();
                    info4.parser(jsonArray.getJSONObject(i));
                    if (info4.status != -1) {
                        this.shopcommodityvos.add(info4);
                    }
                }
            }
        } catch (Exception e22222222222222222222222222222222) {
            e22222222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("scsl")) {
                this.scsl.parser(object.getJSONObject("scsl"));
            }
        } catch (Exception e222222222222222222222222222222222) {
            e222222222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("rts")) {
                this.rts = object.getInt("rts");
            }
        } catch (Exception e2222222222222222222222222222222222) {
            e2222222222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("isonlyvideo")) {
                this.isonlyvideo = object.getInt("isonlyvideo");
            }
        } catch (Exception e22222222222222222222222222222222222) {
            e22222222222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("isoldthread")) {
                this.isoldthread = object.getInt("isoldthread");
            }
        } catch (Exception e222222222222222222222222222222222222) {
            e222222222222222222222222222222222222.printStackTrace();
        }
    }

    public boolean equals(Object obj) {
        return getLocalkey().equals(((SnsInfo) obj).getLocalkey());
    }

    public void intentToArticle(Context context, int commentID) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.intentToArticle(context, commentID, 1);
    }

    public void intentToSendComment(Context context) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.intentToSendComment(context, 0, "");
    }

    public void intentToSendComment(Context context, View view, String key) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        if (view == null) {
            this.snsOperation.intentToSendComment(context, 0, "");
        } else {
            this.snsOperation.intentToSendComment(context, getY(view) + view.getHeight(), key);
        }
    }

    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];
    }

    public void collectOperation(Context context, OperationCallback callback) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.collectOperation(context, callback);
    }

    public void reportOperation(Context context) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.reportOperation(context);
    }

    public void deleteOperation(Context context, OperationCallback callback) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.deleteOperation(context, callback);
    }

    public void editOperation(Context context) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.editOperation(context);
    }

    public void praiseOperation(Context context, boolean isChangePraiseCount, SuccessFailCallback callback) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.praiseOperation(context, isChangePraiseCount, callback);
    }

    public void showForwardMenuOperation(Context context) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.showForwardMenuOperation(context);
    }

    public void socialShare(Activity activity, SHARE_MEDIA media, UMShareListener umShareListener, int type) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.socialShare(activity, media, umShareListener, type);
    }

    public void copySnsUrl(Context context, int type) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.copySnsUrl(context, type);
    }

    public void intentToViewPoint(Context context, boolean isNeedClose) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.intetntToViewPoint(context, isNeedClose);
    }

    public void intentToViewPoint(Context context, boolean isNeedClose, int type, int commentid) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.intetntToViewPoint(context, isNeedClose, type, commentid);
    }

    public void checkSnsState(Context context, SuccessFailCallback callback) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.checkSnsState(context, callback);
    }

    public void checkSnsState(Context context, boolean isOldDriverRecommend, SuccessFailCallback callback) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.checkSnsState(context, isOldDriverRecommend, callback);
    }

    public void shareToWeiXin(Context context, IWXAPI iwxapi) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.shareToWeiXin(context, iwxapi);
    }

    public void showDislikeDialog(Context context) {
        showDislikeDialog(context, null);
    }

    public void showDislikeDialog(Context context, SuccessFailCallback callback) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.showDislikeDialog(context, callback);
    }

    public void showReportTopicDialog(Context context, int circleId, SuccessFailCallback callback) {
        if (this.snsOperation == null) {
            this.snsOperation = new SnsOperation(this);
        }
        this.snsOperation.showReportTopicDialog(context, circleId, callback);
    }
}
