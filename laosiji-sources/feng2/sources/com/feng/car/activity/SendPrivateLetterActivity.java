package com.feng.car.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.facebook.common.util.UriUtil;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivitySendPrivateBinding;
import com.feng.car.databinding.SendTranspondBottomMenuBarBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.privatemsg.MessageInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.PrivateChatEvent;
import com.feng.car.utils.BlackUtil;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.UploadSingleImage;
import com.feng.car.utils.UploadSingleImage$OnUploadResult;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class SendPrivateLetterActivity extends BaseActivity<ActivitySendPrivateBinding> implements FuncLayout$OnFuncKeyBoardListener {
    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
            if (isDelBtn) {
                SimpleCommonUtils.delClick(((ActivitySendPrivateBinding) SendPrivateLetterActivity.this.mBaseBinding).etDefEmoticonsText);
            } else if (o != null) {
                String content = null;
                if (o instanceof EmojiBean) {
                    content = ((EmojiBean) o).emoji;
                } else if (o instanceof EmoticonEntity) {
                    content = ((EmoticonEntity) o).getContent();
                }
                if (!TextUtils.isEmpty(content)) {
                    ((ActivitySendPrivateBinding) SendPrivateLetterActivity.this.mBaseBinding).etDefEmoticonsText.getText().insert(((ActivitySendPrivateBinding) SendPrivateLetterActivity.this.mBaseBinding).etDefEmoticonsText.getSelectionStart(), content);
                }
            }
        }
    };
    private boolean mContentOk;
    private HashMap<String, String> mImageInfoMap;
    private String mImagePath = "";
    private SendTranspondBottomMenuBarBinding mMenuBarBinding;
    private UploadSingleImage mUploadImage;
    private int mUserId;
    private String mUserName = "";

    static {
        StubApp.interface11(2967);
    }

    protected native void onCreate(Bundle bundle);

    public void initView() {
        Intent intent = getIntent();
        this.mUserId = intent.getIntExtra("userid", 0);
        this.mUserName = intent.getStringExtra("name");
        if (this.mUserId <= 0 && TextUtils.isEmpty(this.mUserName)) {
            showSecondTypeToast((int) R.string.getinfo_failed);
            finish();
        }
        ((ActivitySendPrivateBinding) this.mBaseBinding).etDefEmoticonsText.setHint("我想对Ta说...");
        initBarView();
        initNormalTitleBar(this.mUserName);
        closeSwip();
        initTitleBarRightTextWithBg(R.string.send, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                String content = ((ActivitySendPrivateBinding) SendPrivateLetterActivity.this.mBaseBinding).etDefEmoticonsText.getText().toString().trim();
                if (content.length() == 0 && SendPrivateLetterActivity.this.mImagePath.equals("")) {
                    SendPrivateLetterActivity.this.showThirdTypeToast((int) R.string.please_input_private_letter_content);
                    return;
                }
                Matcher emoticon = HttpConstant.PATTERN_EMOTICON_TEXT.matcher(content);
                while (emoticon.find()) {
                    String key = emoticon.group();
                    String value = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(key);
                    if (!TextUtils.isEmpty(value)) {
                        content = content.replace(key, value);
                    }
                }
                SendPrivateLetterActivity.this.sendMessage(content);
            }
        });
        ((ActivitySendPrivateBinding) this.mBaseBinding).ibDel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!StringUtil.isEmpty(SendPrivateLetterActivity.this.mImagePath)) {
                    if (SendPrivateLetterActivity.this.mImagePath.indexOf(FengConstant.IMAGE_FILE_PATH, 0) >= 0) {
                        FileUtil.delAbsoluteFile(SendPrivateLetterActivity.this.mImagePath);
                    }
                    SendPrivateLetterActivity.this.mImagePath = "";
                    ((ActivitySendPrivateBinding) SendPrivateLetterActivity.this.mBaseBinding).ibDel.setVisibility(8);
                    ((ActivitySendPrivateBinding) SendPrivateLetterActivity.this.mBaseBinding).ivImage.setVisibility(8);
                    SendPrivateLetterActivity.this.changeRightBtn();
                }
            }
        });
        initEmoticonsKeyBoardBar();
        ((ActivitySendPrivateBinding) this.mBaseBinding).etDefEmoticonsText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                SendPrivateLetterActivity sendPrivateLetterActivity = SendPrivateLetterActivity.this;
                boolean z = s.length() > 0 && s.length() < m_AppUI.MSG_APP_DATA_OK;
                sendPrivateLetterActivity.mContentOk = z;
                SendPrivateLetterActivity.this.changeRightBtn();
                if (s.length() >= m_AppUI.MSG_APP_DATA_OK) {
                    SendPrivateLetterActivity.this.showThirdTypeToast((int) R.string.private_letter_words_not_more_2000);
                }
            }
        });
        this.mUploadImage = new UploadSingleImage(this, new UploadSingleImage$OnUploadResult() {
            public void onStart() {
            }

            public void onResult(HashMap<String, String> imageMap) {
                SendPrivateLetterActivity.this.mImageInfoMap = imageMap;
                SendPrivateLetterActivity.this.initImage(imageMap);
            }

            public void onFailure() {
            }
        });
        FengUtil.changeCopyText(StubApp.getOrigApplicationContext(getApplicationContext()));
    }

    private void changeRightBtn() {
        TextView textView = this.mRootBinding.titleLine.tvRightText;
        boolean z = this.mContentOk || !StringUtil.isEmpty(this.mImagePath);
        textView.setSelected(z);
    }

    public void initBarView() {
        this.mMenuBarBinding = SendTranspondBottomMenuBarBinding.inflate(this.mInflater);
        this.mMenuBarBinding.ivAddAt.setVisibility(8);
        this.mMenuBarBinding.llTextNum.setVisibility(8);
        this.mMenuBarBinding.ivAddEmoji.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((ActivitySendPrivateBinding) SendPrivateLetterActivity.this.mBaseBinding).kbDefEmoticons.openEmoticonsKeyboard();
            }
        });
        this.mMenuBarBinding.ivComplete.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((ActivitySendPrivateBinding) SendPrivateLetterActivity.this.mBaseBinding).kbDefEmoticons.reset();
            }
        });
        this.mMenuBarBinding.ivAddImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(SendPrivateLetterActivity.this, SelectPhotoActivity.class);
                intent.putExtra("feng_type", 1);
                SendPrivateLetterActivity.this.startActivityForResult(intent, 20);
            }
        });
        ((ActivitySendPrivateBinding) this.mBaseBinding).kbDefEmoticons.setKeyboardStatusChangeListener(new XhsEmoticonsKeyBoard$OnKeyboardStatusChangeListener() {
            public void onStatusChange(int status) {
                switch (status) {
                    case 1001:
                        SendPrivateLetterActivity.this.mMenuBarBinding.ivAddEmoji.setImageResource(R.drawable.icon_keyboard);
                        return;
                    case 1002:
                        SendPrivateLetterActivity.this.mMenuBarBinding.ivAddEmoji.setImageResource(R.drawable.icon_emoji);
                        return;
                    default:
                        return;
                }
            }
        });
        ((ActivitySendPrivateBinding) this.mBaseBinding).kbDefEmoticons.addView(this.mMenuBarBinding.getRoot());
    }

    private void initEmoticonsKeyBoardBar() {
        ((ActivitySendPrivateBinding) this.mBaseBinding).kbDefEmoticons.setAdapter(SimpleCommonUtils.getCommonAdapter(this, this.emoticonClickListener));
        ((ActivitySendPrivateBinding) this.mBaseBinding).kbDefEmoticons.addOnFuncKeyBoardListener(this);
        ((ActivitySendPrivateBinding) this.mBaseBinding).kbDefEmoticons.setInputEditText(((ActivitySendPrivateBinding) this.mBaseBinding).etDefEmoticonsText);
    }

    protected void onPause() {
        super.onPause();
        ((ActivitySendPrivateBinding) this.mBaseBinding).kbDefEmoticons.reset();
    }

    public int setBaseContentView() {
        return R.layout.activity_send_private;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20 && data != null && data.hasExtra("imagepath")) {
            String lastImagePath = "";
            if (!StringUtil.isEmpty(this.mImagePath)) {
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

    private void initImage(Map<String, String> map) {
        final ImageInfo info = new ImageInfo();
        info.height = Integer.parseInt((String) map.get("height"));
        info.width = Integer.parseInt((String) map.get("width"));
        info.url = HttpConstant.QINIUIMAGEBASEPATH + ((String) map.get("hash"));
        info.setMimetype((String) map.get("mime"));
        ((ActivitySendPrivateBinding) this.mBaseBinding).ibDel.setVisibility(0);
        String strurl = FengUtil.getSingleNineScaleUrl(info, 200);
        info.lowUrl = strurl;
        ((ActivitySendPrivateBinding) this.mBaseBinding).ivImage.setAutoImageURI(Uri.parse(strurl));
        ((ActivitySendPrivateBinding) this.mBaseBinding).ivImage.setVisibility(0);
        ((ActivitySendPrivateBinding) this.mBaseBinding).ivImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(SendPrivateLetterActivity.this, ShowBigImageActivity.class);
                intent.putExtra("show_type", 1006);
                intent.putExtra("position", 0);
                List<ImageInfo> infos = new ArrayList();
                infos.add(info);
                intent.putExtra("mImageList", JsonUtil.toJson(infos));
                intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(((ActivitySendPrivateBinding) SendPrivateLetterActivity.this.mBaseBinding).ivImage, 0, info.hash)));
                intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
                SendPrivateLetterActivity.this.startActivity(intent);
                SendPrivateLetterActivity.this.overridePendingTransition(0, 0);
            }
        });
    }

    private void sendMessage(final String message) {
        UserInfo userInfo = new UserInfo();
        userInfo.id = this.mUserId;
        if (BlackUtil.getInstance().isInBlackList(userInfo)) {
            showSecondTypeToast((int) R.string.in_black_tips);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("userid", String.valueOf(this.mUserId));
        map.put(UriUtil.LOCAL_CONTENT_SCHEME, message);
        if (this.mImageInfoMap != null && this.mImageInfoMap.size() > 0) {
            map.put("image", this.mImageInfoMap);
        }
        FengApplication.getInstance().httpRequest("user/sendmessage/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                SendPrivateLetterActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
                SendPrivateLetterActivity.this.showProgress("", "私信发送中...");
            }

            public void onFinish() {
                SendPrivateLetterActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SendPrivateLetterActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                SendPrivateLetterActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        MessageInfo info = new MessageInfo();
                        info.content.set(message);
                        if (SendPrivateLetterActivity.this.mImageInfoMap != null) {
                            JSONObject jsonMessage = resultJson.getJSONObject("body").getJSONObject("message");
                            if (jsonMessage.has("image")) {
                                JSONObject jsonImage = jsonMessage.getJSONObject("image");
                                if (jsonImage != null) {
                                    info.image.parser(jsonImage);
                                }
                            }
                        }
                        SendPrivateLetterActivity.this.showFirstTypeToast((int) R.string.send_success);
                        EventBus.getDefault().post(new PrivateChatEvent(info, SendPrivateLetterActivity.this.mUserId));
                        SendPrivateLetterActivity.this.finish();
                    } else if (code == -37) {
                        SendPrivateLetterActivity.this.showSecondTypeToast((int) R.string.you_are_in_black_operation_forbidden);
                    } else if (code == -76) {
                        SendPrivateLetterActivity.this.showSecondTypeToast((int) R.string.setting_can_not_send_message);
                    } else if (code == -77) {
                        Intent intent = new Intent(SendPrivateLetterActivity.this, SettingAccountPhoneActivity.class);
                        intent.putExtra("feng_type", 0);
                        SendPrivateLetterActivity.this.startActivity(intent);
                    } else if (code == -5) {
                        SendPrivateLetterActivity.this.showSecondTypeToast((int) R.string.account_is_banned);
                    } else {
                        FengApplication.getInstance().checkCode("user/sendmessage/", code);
                        FengApplication.getInstance().upLoadLog(true, "user/sendmessage/    " + code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    SendPrivateLetterActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    FengApplication.getInstance().upLoadTryCatchLog("user/sendmessage/", content, e);
                }
            }
        });
    }

    public void OnFuncPop(int height) {
    }

    public void OnFuncClose() {
    }

    protected void onDestroy() {
        super.onDestroy();
        if (!StringUtil.isEmpty(this.mImagePath) && this.mImagePath.indexOf(FengConstant.IMAGE_FILE_PATH, 0) >= 0) {
            FileUtil.delAbsoluteFile(this.mImagePath);
            this.mImagePath = "";
        }
    }
}
