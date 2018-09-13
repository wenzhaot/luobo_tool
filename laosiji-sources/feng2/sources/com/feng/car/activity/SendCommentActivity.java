package com.feng.car.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.facebook.common.util.UriUtil;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivitySendCommentNewBinding;
import com.feng.car.databinding.SendTranspondBottomMenuBarBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.AtUserNameEvent;
import com.feng.car.event.CommentPageRefreshEvent;
import com.feng.car.event.SendCommentStartSlideEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.listener.EditInputFilter;
import com.feng.car.utils.AtFilter;
import com.feng.car.utils.BlackUtil;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.utils.UploadSingleImage;
import com.feng.car.utils.UploadSingleImage$OnUploadResult;
import com.feng.car.view.CommonDialog;
import com.feng.library.emoticons.keyboard.EmojiBean;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.emoticons.keyboard.SimpleCommonUtils;
import com.feng.library.emoticons.keyboard.XhsEmoticonsKeyBoard$OnKeyboardStatusChangeListener;
import com.feng.library.emoticons.keyboard.data.EmoticonEntity;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonClickListener;
import com.feng.library.emoticons.keyboard.widget.FuncLayout$OnFuncKeyBoardListener;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.FileUtil;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class SendCommentActivity extends BaseActivity<ActivitySendCommentNewBinding> implements FuncLayout$OnFuncKeyBoardListener {
    public static final int COMMENT_COMMENT_TYPE = 2;
    public static final String COMMENT_COUNT = "comment_count";
    public static final int COMMENT_THREAD_TYPE = 1;
    public static final String EVENT_KEY = "event_key";
    public static final String VIEW_LOCAL_SLIDE_DY = "view_local_slide_dy";
    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
            if (isDelBtn) {
                SimpleCommonUtils.delClick(((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).etDefEmoticonsText);
            } else if (o != null) {
                String content = null;
                if (o instanceof EmojiBean) {
                    content = ((EmojiBean) o).emoji;
                } else if (o instanceof EmoticonEntity) {
                    content = ((EmoticonEntity) o).getContent();
                }
                if (!TextUtils.isEmpty(content)) {
                    ((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).etDefEmoticonsText.getText().insert(((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).etDefEmoticonsText.getSelectionStart(), content);
                }
            }
        }
    };
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SendCommentActivity.this.showFirstTypeToast((int) R.string.publish_success);
                    return;
                case 1:
                    if (!(SendCommentActivity.this.mViewSlideDy == 0 || SendCommentActivity.this.mIsFullScreen)) {
                        if (SendCommentActivity.this.mNeedRecover) {
                            EventBus.getDefault().post(new SendCommentStartSlideEvent(-SendCommentActivity.this.mLogHeight, SendCommentActivity.this.mSoleKey, true));
                        } else {
                            EventBus.getDefault().post(new SendCommentStartSlideEvent(-SendCommentActivity.this.mLocalDy, SendCommentActivity.this.mSoleKey, true));
                        }
                    }
                    SendCommentActivity.this.finish();
                    return;
                default:
                    return;
            }
        }
    };
    private SendTranspondBottomMenuBarBinding mBottomMenuBinding;
    private int mCommentCount;
    private int mCommentInfoId = 0;
    private boolean mContentOk;
    private int mCountLimitCount = 140;
    private boolean mHasSlide = false;
    private HashMap<String, String> mImageInfoMap;
    private String mImagePath = "";
    private EditInputFilter mInputFilter;
    private boolean mIsFinish = false;
    private boolean mIsFullScreen = false;
    private int mLocalDy = 0;
    private int mLogHeight = 0;
    private boolean mNeedRecover = false;
    private int mParentId = 0;
    private Pattern mPatternUser = Pattern.compile("@[-_a-zA-Z0-9\\u4E00-\\u9FA5]+");
    private int mResourceId = 0;
    private int mResourceType = 0;
    private int mSnsID = 0;
    private String mSoleKey = "";
    private int mType = 1;
    private UploadSingleImage mUploadImage;
    private int mUserId = 0;
    private int mViewSlideDy = 0;

    static {
        StubApp.interface11(2955);
    }

    protected native void onCreate(Bundle bundle);

    public void initView() {
        initNormalTitleBar("添加评论");
        initNormalLeftTitleBar(getString(R.string.cancel), new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SendCommentActivity.this.showAlertDialog();
            }
        });
        this.mBottomMenuBinding = SendTranspondBottomMenuBarBinding.inflate(this.mInflater, ((ActivitySendCommentNewBinding) this.mBaseBinding).kbDefEmoticons, false);
        this.mBottomMenuBinding.ivComplete.setVisibility(8);
        Intent intent = getIntent();
        this.mType = intent.getIntExtra("feng_type", 0);
        this.mCommentCount = intent.getIntExtra(COMMENT_COUNT, 0);
        switch (this.mType) {
            case 1:
                ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.setHint("此时此刻我想说...");
                this.mSnsID = intent.getIntExtra("snsid", 0);
                this.mCommentInfoId = intent.getIntExtra("commentid", 0);
                this.mResourceId = intent.getIntExtra("resourceid", 0);
                this.mResourceType = intent.getIntExtra("resourcetype", 0);
                this.mUserId = intent.getIntExtra("userid", 0);
                this.mViewSlideDy = intent.getIntExtra(VIEW_LOCAL_SLIDE_DY, 0);
                this.mSoleKey = intent.getStringExtra(EVENT_KEY);
                break;
            case 2:
                ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.setHint("回复给 @" + intent.getStringExtra("name"));
                this.mSnsID = intent.getIntExtra("snsid", 0);
                this.mUserId = intent.getIntExtra("userid", 0);
                this.mCommentInfoId = intent.getIntExtra("commentid", 0);
                this.mResourceId = intent.getIntExtra("resourceid", 0);
                this.mResourceType = intent.getIntExtra("resourcetype", 0);
                this.mParentId = intent.getIntExtra("parentid", 0);
                this.mViewSlideDy = intent.getIntExtra(VIEW_LOCAL_SLIDE_DY, 0);
                this.mSoleKey = intent.getStringExtra(EVENT_KEY);
                break;
            default:
                showSecondTypeToast((int) R.string.getinfo_failed);
                finish();
                return;
        }
        changeTitleBar();
        changeView();
        initBarView();
        closeSwip();
        initTitleBarRightTextWithBg(R.string.publish, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SendCommentActivity.this.sendClick();
            }
        });
        ((ActivitySendCommentNewBinding) this.mBaseBinding).tvSend.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SendCommentActivity.this.sendClick();
            }
        });
        ((ActivitySendCommentNewBinding) this.mBaseBinding).ibDel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!StringUtil.isEmpty(SendCommentActivity.this.mImagePath)) {
                    if (SendCommentActivity.this.mImagePath.indexOf(FengConstant.IMAGE_FILE_PATH, 0) >= 0) {
                        FileUtil.delAbsoluteFile(SendCommentActivity.this.mImagePath);
                    }
                    SendCommentActivity.this.mImagePath = "";
                    ((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).ibDel.setVisibility(8);
                    ((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).ivImage.setVisibility(8);
                    SendCommentActivity.this.changeRightBtn();
                }
            }
        });
        initEmoticonsKeyBoardBar();
        this.mUploadImage = new UploadSingleImage(this, new UploadSingleImage$OnUploadResult() {
            public void onStart() {
            }

            public void onResult(HashMap<String, String> imageMap) {
                SendCommentActivity.this.mImageInfoMap = imageMap;
                SendCommentActivity.this.initImage(imageMap);
            }

            public void onFailure() {
            }
        });
        FengUtil.changeCopyText(StubApp.getOrigApplicationContext(getApplicationContext()));
    }

    private void sendClick() {
        String content = ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.getText().toString().trim();
        if (content.length() == 0 && this.mImageInfoMap == null) {
            showThirdTypeToast((int) R.string.please_input_commend);
        } else if (StringUtil.strLenthLikeWeiBo(content, true) > this.mCountLimitCount) {
            showThirdTypeToast((int) R.string.commend_not_more_140);
        } else {
            sendComment(content);
        }
    }

    private void changeTitleBar() {
        if (this.mIsFullScreen) {
            showDefaultTitleBar();
            this.mRootBinding.rootView.setBackgroundResource(R.color.transparent);
            return;
        }
        this.mRootBinding.titleLine.getRoot().setVisibility(4);
        this.mRootBinding.rootView.setBackgroundResource(R.color.color_38_000000);
    }

    private void changeView() {
        if (this.mIsFullScreen) {
            ((ActivitySendCommentNewBinding) this.mBaseBinding).ivFullScreen.setVisibility(8);
            ((ActivitySendCommentNewBinding) this.mBaseBinding).tvSend.setVisibility(8);
            LayoutParams params = (LayoutParams) this.mBottomMenuBinding.rlBottomMenu.getLayoutParams();
            params.addRule(3, 0);
            this.mBottomMenuBinding.rlBottomMenu.setLayoutParams(params);
            LayoutParams params2 = (LayoutParams) ((ActivitySendCommentNewBinding) this.mBaseBinding).rlLayoutComment.getLayoutParams();
            params2.height = -1;
            ((ActivitySendCommentNewBinding) this.mBaseBinding).rlLayoutComment.setLayoutParams(params2);
            ((ActivitySendCommentNewBinding) this.mBaseBinding).rlLayoutComment.setBackgroundResource(R.color.transparent);
            ((ActivitySendCommentNewBinding) this.mBaseBinding).kbDefEmoticons.setBackgroundResource(R.color.color_ffffff);
            ((ActivitySendCommentNewBinding) this.mBaseBinding).rlContentTextImage.setBackgroundResource(R.color.transparent);
            ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.setMinLines(5);
            LayoutParams params1 = (LayoutParams) ((ActivitySendCommentNewBinding) this.mBaseBinding).ivImage.getLayoutParams();
            params1.width = this.mResources.getDimensionPixelSize(R.dimen.default_218PX);
            params1.height = this.mResources.getDimensionPixelSize(R.dimen.default_218PX);
            ((ActivitySendCommentNewBinding) this.mBaseBinding).ivImage.setLayoutParams(params1);
        }
    }

    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];
    }

    private void changeRightBtn() {
        boolean z;
        boolean z2 = false;
        TextView textView = this.mRootBinding.titleLine.tvRightText;
        if (this.mContentOk || !StringUtil.isEmpty(this.mImagePath)) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        TextView textView2 = ((ActivitySendCommentNewBinding) this.mBaseBinding).tvSend;
        if (this.mContentOk || !StringUtil.isEmpty(this.mImagePath)) {
            z2 = true;
        }
        textView2.setSelected(z2);
    }

    public void initBarView() {
        ((ActivitySendCommentNewBinding) this.mBaseBinding).ivFullScreen.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SendCommentActivity.this.mIsFullScreen = true;
                SendCommentActivity.this.changeTitleBar();
                SendCommentActivity.this.changeView();
                if (SendCommentActivity.this.mViewSlideDy == 0) {
                    return;
                }
                if (SendCommentActivity.this.mNeedRecover) {
                    EventBus.getDefault().post(new SendCommentStartSlideEvent(-SendCommentActivity.this.mLogHeight, SendCommentActivity.this.mSoleKey));
                } else {
                    EventBus.getDefault().post(new SendCommentStartSlideEvent(-SendCommentActivity.this.mLocalDy, SendCommentActivity.this.mSoleKey));
                }
            }
        });
        this.mBottomMenuBinding.ivAddEmoji.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).kbDefEmoticons.openEmoticonsKeyboard();
            }
        });
        this.mBottomMenuBinding.ivAddImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(SendCommentActivity.this, SelectPhotoActivity.class);
                intent.putExtra("feng_type", 1);
                SendCommentActivity.this.startActivityForResult(intent, 20);
            }
        });
        this.mBottomMenuBinding.ivAddAt.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SendCommentActivity.this.startActivity(new Intent(SendCommentActivity.this, AtUserActivity.class));
            }
        });
        ((ActivitySendCommentNewBinding) this.mBaseBinding).kbDefEmoticons.setKeyboardStatusChangeListener(new XhsEmoticonsKeyBoard$OnKeyboardStatusChangeListener() {
            public void onStatusChange(int status) {
                switch (status) {
                    case 1001:
                        SendCommentActivity.this.mBottomMenuBinding.ivAddEmoji.setImageResource(R.drawable.icon_keyboard);
                        return;
                    case 1002:
                        SendCommentActivity.this.mBottomMenuBinding.ivAddEmoji.setImageResource(R.drawable.icon_emoji);
                        return;
                    default:
                        return;
                }
            }
        });
        ((ActivitySendCommentNewBinding) this.mBaseBinding).kbDefEmoticons.addView(this.mBottomMenuBinding.rlBottomMenu);
        LayoutParams params = (LayoutParams) this.mBottomMenuBinding.rlBottomMenu.getLayoutParams();
        params.addRule(3, R.id.rl_layout_comment);
        this.mBottomMenuBinding.rlBottomMenu.setLayoutParams(params);
    }

    private void initEmoticonsKeyBoardBar() {
        this.mInputFilter = new EditInputFilter(this);
        ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.setFilters(new InputFilter[]{this.mInputFilter});
        ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.addEmoticonFilter(new AtFilter());
        ((ActivitySendCommentNewBinding) this.mBaseBinding).kbDefEmoticons.setAdapter(SimpleCommonUtils.getCommonAdapter(this, this.emoticonClickListener));
        ((ActivitySendCommentNewBinding) this.mBaseBinding).kbDefEmoticons.addOnFuncKeyBoardListener(this);
        ((ActivitySendCommentNewBinding) this.mBaseBinding).kbDefEmoticons.setInputEditText(((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText);
        ((ActivitySendCommentNewBinding) this.mBaseBinding).kbDefEmoticons.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!SendCommentActivity.this.mIsFullScreen) {
                    ((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).kbDefEmoticons.reset();
                    SendCommentActivity.this.showAlertDialog();
                }
            }
        });
        ((ActivitySendCommentNewBinding) this.mBaseBinding).closeKeyBoard.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SendCommentActivity.this.mIsFullScreen) {
                    ((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).kbDefEmoticons.reset();
                }
            }
        });
        ((ActivitySendCommentNewBinding) this.mBaseBinding).rlLayoutComment.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        });
        this.mBottomMenuBinding.getRoot().setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        });
        ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                int count = StringUtil.strLenthLikeWeiBo(((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).etDefEmoticonsText.getText().toString().trim(), true);
                SendCommentActivity.this.mBottomMenuBinding.tvCurrentWordsCount.setText(String.valueOf(count));
                if (count > SendCommentActivity.this.mCountLimitCount) {
                    SendCommentActivity.this.mBottomMenuBinding.tvCurrentWordsCount.setTextColor(SendCommentActivity.this.mResources.getColor(R.color.color_d63d3d));
                } else {
                    SendCommentActivity.this.mBottomMenuBinding.tvCurrentWordsCount.setTextColor(SendCommentActivity.this.mResources.getColor(R.color.color_38_000000));
                }
                SendCommentActivity sendCommentActivity = SendCommentActivity.this;
                boolean z = count > 0 && count <= SendCommentActivity.this.mCountLimitCount;
                sendCommentActivity.mContentOk = z;
                SendCommentActivity.this.changeRightBtn();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AtUserNameEvent event) {
        int curIndex = ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.getSelectionStart();
        if (this.mInputFilter == null || !this.mInputFilter.mInputFilterAt) {
            ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.getText().insert(curIndex, "@" + event.name + " ");
            return;
        }
        ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.getText().insert(curIndex, event.name + " ");
        this.mInputFilter.mInputFilterAt = false;
    }

    protected void onPause() {
        super.onPause();
        ((ActivitySendCommentNewBinding) this.mBaseBinding).kbDefEmoticons.reset();
    }

    public int setBaseContentView() {
        return R.layout.activity_send_comment_new;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20 && data != null && data.hasExtra("imagepath")) {
            String lastImagePath = "";
            if (this.mImagePath != null && this.mImagePath.length() > 0) {
                lastImagePath = this.mImagePath;
            }
            this.mImagePath = data.getStringExtra("imagepath");
            if (FileUtil.isFileExists(this.mImagePath)) {
                if (!StringUtil.isEmpty(this.mImagePath) && lastImagePath != null && lastImagePath.length() > 0 && lastImagePath.indexOf(FengConstant.IMAGE_FILE_PATH, 0) >= 0) {
                    FileUtil.delAbsoluteFile(lastImagePath);
                }
                this.mUploadImage.startUpload(this.mImagePath);
                changeRightBtn();
            }
        }
    }

    private void sendComment(String comment) {
        UserInfo userInfo = new UserInfo();
        userInfo.id = this.mUserId;
        if (BlackUtil.getInstance().isInBlackList(userInfo)) {
            showSecondTypeToast((int) R.string.in_black_tips);
            return;
        }
        Matcher emoticon = HttpConstant.PATTERN_EMOTICON_TEXT.matcher(comment);
        while (emoticon.find()) {
            String key = emoticon.group();
            String value = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(key);
            if (!TextUtils.isEmpty(value)) {
                comment = comment.replace(key, value);
            }
        }
        Map<String, Object> map = new HashMap();
        map.put(UriUtil.LOCAL_CONTENT_SCHEME, comment);
        if (this.mParentId != 0) {
            map.put("parentid", String.valueOf(this.mParentId));
        } else {
            map.put("parentid", String.valueOf(0));
        }
        map.put("snsid", String.valueOf(this.mSnsID));
        map.put("resourceid", String.valueOf(this.mResourceId));
        map.put("resourcetype", String.valueOf(this.mResourceType));
        if (this.mImageInfoMap != null && this.mImageInfoMap.size() > 0) {
            if (TextUtils.isEmpty(comment)) {
                map.put(UriUtil.LOCAL_CONTENT_SCHEME, comment + MessageFormat.format(" [http://15feng.cn/p/{0}] ", new Object[]{this.mImageInfoMap.get("hash")}).trim());
            } else {
                map.put(UriUtil.LOCAL_CONTENT_SCHEME, comment + MessageFormat.format(" [http://15feng.cn/p/{0}] ", new Object[]{this.mImageInfoMap.get("hash")}));
            }
            map.put("image", this.mImageInfoMap);
        }
        Map<String, Object> map1 = new HashMap();
        map1.put("atlist", getAtList(comment));
        map.put("atlist", map1);
        FengApplication.getInstance().httpRequest("sns/addcomment/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                SendCommentActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
                SendCommentActivity.this.showProgress("", "评论发送中...");
            }

            public void onFinish() {
                SendCommentActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SendCommentActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                SendCommentActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        JSONObject commentJson = resultJson.getJSONObject("body").getJSONObject("comment");
                        CommentInfo info = new CommentInfo();
                        info.parser(commentJson);
                        if (SendCommentActivity.this.mSnsID > 0) {
                            SnsInfo snsInfo = new SnsInfo();
                            snsInfo.id = SendCommentActivity.this.mSnsID;
                            snsInfo.resourceid = SendCommentActivity.this.mResourceId;
                            snsInfo.snstype = SendCommentActivity.this.mResourceType;
                            snsInfo.commentcount.set(SendCommentActivity.this.mCommentCount + 1);
                            EventBus.getDefault().post(new SnsInfoRefreshEvent(snsInfo, info));
                        }
                        SendCommentActivity.this.handler.sendEmptyMessage(0);
                        if (SendCommentActivity.this.mType == 2) {
                            EventBus.getDefault().post(new CommentPageRefreshEvent(SendCommentActivity.this.mCommentInfoId));
                        }
                        SendCommentActivity.this.mIsFinish = true;
                        if (!SendCommentActivity.this.mIsFullScreen) {
                            ((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).kbDefEmoticons.setVisibility(4);
                        }
                        SendCommentActivity.this.handler.sendEmptyMessageDelayed(1, 2000);
                    } else if (code == -5) {
                        SendCommentActivity.this.showSecondTypeToast((int) R.string.account_is_banned);
                    } else {
                        SendCommentActivity.this.showSecondTypeToast((int) R.string.publish_commend_failed);
                        FengApplication.getInstance().upLoadLog(true, "sns/info/  错误码  " + code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    SendCommentActivity.this.showSecondTypeToast((int) R.string.publish_commend_failed);
                    FengApplication.getInstance().upLoadTryCatchLog("sns/addcomment/", content, e);
                }
            }
        });
    }

    private void initImage(Map<String, String> map) {
        final ImageInfo info = new ImageInfo();
        info.height = Integer.parseInt((String) map.get("height"));
        info.width = Integer.parseInt((String) map.get("width"));
        info.url = HttpConstant.QINIUIMAGEBASEPATH + ((String) map.get("hash"));
        info.setMimetype((String) map.get("mime"));
        ((ActivitySendCommentNewBinding) this.mBaseBinding).ibDel.setVisibility(0);
        String strUrl = FengUtil.getSingleNineScaleUrl(info, 200);
        info.lowUrl = strUrl;
        ((ActivitySendCommentNewBinding) this.mBaseBinding).ivImage.setAutoImageURI(Uri.parse(strUrl));
        ((ActivitySendCommentNewBinding) this.mBaseBinding).ivImage.setVisibility(0);
        ((ActivitySendCommentNewBinding) this.mBaseBinding).ivImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(SendCommentActivity.this, ShowBigImageActivity.class);
                intent.putExtra("show_type", 1006);
                intent.putExtra("position", 0);
                List<ImageInfo> infos = new ArrayList();
                infos.add(info);
                intent.putExtra("mImageList", JsonUtil.toJson(infos));
                intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).ivImage, 0, info.hash)));
                intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
                SendCommentActivity.this.startActivity(intent);
                SendCommentActivity.this.overridePendingTransition(0, 0);
            }
        });
    }

    public void OnFuncPop(int height) {
        if (this.mIsFullScreen) {
            ((ActivitySendCommentNewBinding) this.mBaseBinding).closeKeyBoard.setVisibility(0);
        } else if (this.mViewSlideDy != 0) {
            ((ActivitySendCommentNewBinding) this.mBaseBinding).getRoot().postDelayed(new Runnable() {
                public void run() {
                    if (!SendCommentActivity.this.mHasSlide) {
                        SendCommentActivity.this.mLocalDy = SendCommentActivity.this.mViewSlideDy - SendCommentActivity.this.getY(((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).rlLayoutComment);
                        if (SendCommentActivity.this.mNeedRecover) {
                            EventBus.getDefault().post(new SendCommentStartSlideEvent(SendCommentActivity.this.mLocalDy - SendCommentActivity.this.mLogHeight, SendCommentActivity.this.mSoleKey));
                        } else {
                            EventBus.getDefault().post(new SendCommentStartSlideEvent(SendCommentActivity.this.mLocalDy, SendCommentActivity.this.mSoleKey));
                        }
                    }
                    SendCommentActivity.this.mHasSlide = true;
                }
            }, 50);
        }
    }

    public void finish() {
        LogGatherReadUtil.getInstance().setReferer("app_send_comment");
        super.finish();
        if (!(this.mViewSlideDy == 0 || this.mIsFinish || this.mIsFullScreen)) {
            if (this.mNeedRecover) {
                EventBus.getDefault().post(new SendCommentStartSlideEvent(-this.mLogHeight, this.mSoleKey, true));
            } else {
                EventBus.getDefault().post(new SendCommentStartSlideEvent(0, this.mSoleKey, true));
            }
        }
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
    }

    public void OnFuncClose() {
        if (!this.mIsFinish) {
            if (this.mIsFullScreen) {
                ((ActivitySendCommentNewBinding) this.mBaseBinding).closeKeyBoard.setVisibility(8);
            } else if (this.mHasSlide && this.mViewSlideDy != 0) {
                ((ActivitySendCommentNewBinding) this.mBaseBinding).getRoot().postDelayed(new Runnable() {
                    public void run() {
                        int m = SendCommentActivity.this.getY(((ActivitySendCommentNewBinding) SendCommentActivity.this.mBaseBinding).rlLayoutComment);
                        if (SendCommentActivity.this.mViewSlideDy > m) {
                            SendCommentActivity.this.mNeedRecover = true;
                            SendCommentActivity.this.mLogHeight = SendCommentActivity.this.mViewSlideDy - m;
                            EventBus.getDefault().post(new SendCommentStartSlideEvent((-SendCommentActivity.this.mLocalDy) + SendCommentActivity.this.mLogHeight, SendCommentActivity.this.mSoleKey));
                        } else {
                            EventBus.getDefault().post(new SendCommentStartSlideEvent(-SendCommentActivity.this.mLocalDy, SendCommentActivity.this.mSoleKey));
                        }
                        SendCommentActivity.this.mHasSlide = false;
                    }
                }, 50);
            }
        }
    }

    public void onBackPressed() {
        if (this.mIsFinish) {
            super.onBackPressed();
        } else {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        if (this.mImagePath.length() > 0 || ((ActivitySendCommentNewBinding) this.mBaseBinding).etDefEmoticonsText.getText().toString().trim().length() > 0) {
            DialogItemEntity item = new DialogItemEntity(getString(R.string.confirm), false);
            List<DialogItemEntity> list = new ArrayList();
            list.add(item);
            CommonDialog.showCommonDialog(this, getString(R.string.canceledit_tips), list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    if (position == 0) {
                        SendCommentActivity.this.finish();
                    }
                }
            });
            return;
        }
        finish();
    }

    private List<String> getAtList(String content) {
        List<String> list = new ArrayList();
        Matcher m = this.mPatternUser.matcher(content);
        if (m != null) {
            while (m.find()) {
                String name = m.group();
                name = name.substring(1, name.length());
                if (!list.contains(name)) {
                    list.add(name);
                }
            }
        }
        return list;
    }

    protected void onDestroy() {
        super.onDestroy();
        if (!StringUtil.isEmpty(this.mImagePath) && this.mImagePath.indexOf(FengConstant.IMAGE_FILE_PATH, 0) >= 0) {
            FileUtil.delAbsoluteFile(this.mImagePath);
            this.mImagePath = "";
        }
    }
}
