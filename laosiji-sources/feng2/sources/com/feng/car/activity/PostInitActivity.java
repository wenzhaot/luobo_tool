package com.feng.car.activity;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.RelativeLayout.LayoutParams;
import com.baidu.mapapi.UIMsg.d_ResultType;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.facebook.common.util.UriUtil;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.PostInitAdapter;
import com.feng.car.adapter.SelectCoverImageAdapter;
import com.feng.car.databinding.PostDragHeadBinding;
import com.feng.car.databinding.PostInitActivityBinding;
import com.feng.car.databinding.SendTranspondBottomMenuBarBinding;
import com.feng.car.databinding.UploadProgressLayoutBinding;
import com.feng.car.db.SparkDB;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.entity.dealer.CommodityServeInfo;
import com.feng.car.entity.drafts.DraftsModel;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsPostResources;
import com.feng.car.event.AddCircleEvent;
import com.feng.car.event.AddGoodsEvent;
import com.feng.car.event.AtUserNameEvent;
import com.feng.car.event.DraftsRefreshEvent;
import com.feng.car.event.ImageOrVideoPathEvent;
import com.feng.car.event.PostSortRefreshEvent;
import com.feng.car.event.SendDelArticleSuccess;
import com.feng.car.event.SendPostEmptyCloseEvent;
import com.feng.car.event.SnsInfoModifyEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.listener.EditInputFilter;
import com.feng.car.listener.PostDescribeChangeListener;
import com.feng.car.utils.DraftsHelpUtil;
import com.feng.car.utils.DraftsHelpUtil$OnUserSelecsstListener;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.PostDataManager;
import com.feng.car.utils.UploadQiNiu;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.ScrollerBottomView$OnScrollerStateListener;
import com.feng.car.view.recyclerview.ItemTouchCallback;
import com.feng.car.view.recyclerview.OnRecyclerItemClickListener;
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
import com.feng.library.utils.ToastUtil;
import com.feng.library.utils.WifiUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.google.gson.reflect.TypeToken;
import com.qiniu.android.storage.UploadManager;
import com.umeng.analytics.MobclickAgent;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class PostInitActivity extends BaseActivity<PostInitActivityBinding> implements FuncLayout$OnFuncKeyBoardListener, PostDescribeChangeListener, UncaughtExceptionHandler {
    public static final int CANCELUPLOAD = 1001;
    public static final int CLEARFOCUS = 12;
    public static final int DELITEM = 10;
    public static final int ONEITEMCOMPLETE = 1002;
    public static final int UPDAEPROGRESS = 1000;
    public static final int UPDATEDRAFT = 1006;
    public static final int UPLOADCOMPLETE = 1003;
    public static final int UPLOADCOVER = 1005;
    public static final int UPLOADFAIL = 1004;
    private final int DELAY_FINISH = m_AppUI.MSG_APP_DATA_OK;
    private final int REFRESHSELECTTOP = 11;
    private final int TEXT_NUM_1000 = 1000;
    private final int TEXT_NUM_30 = 30;
    private final int TEXT_NUM_500 = d_ResultType.SHORT_URL;
    private final int THIRD_TYPE_HINT = 20001;
    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
            if (isDelBtn) {
                SimpleCommonUtils.delClick(PostInitActivity.this.mHeadBinding.etDigestText);
            } else if (o != null) {
                String content = null;
                if (o instanceof EmojiBean) {
                    content = ((EmojiBean) o).emoji;
                } else if (o instanceof EmoticonEntity) {
                    content = ((EmoticonEntity) o).getContent();
                }
                if (!TextUtils.isEmpty(content)) {
                    PostInitActivity.this.mHeadBinding.etDigestText.getText().insert(PostInitActivity.this.mHeadBinding.etDigestText.getSelectionStart(), content);
                }
            }
        }
    };
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    final int i = msg.arg1;
                    List<DialogItemEntity> list = new ArrayList();
                    list.add(new DialogItemEntity("删除此模块", true));
                    CommonDialog.showCommonDialog(PostInitActivity.this, "", list, new OnDialogItemClickListener() {
                        public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                            PostInitActivity.this.removeItem(i);
                        }
                    });
                    return;
                case 11:
                    try {
                        PostInitActivity.this.checkChangButtonAllowNext();
                        ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).recyclerView.scrollToPosition(0);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 1000:
                    float progress = ((Float) msg.obj).floatValue();
                    if (PostInitActivity.this.mUploadProgressLayoutBinding.progressbar != null) {
                        PostInitActivity.this.mUploadProgressLayoutBinding.progressbar.setProgress((int) progress);
                        PostInitActivity.this.mUploadProgressLayoutBinding.tvNum.setText(new DecimalFormat("0.0").format((double) progress) + "%");
                        return;
                    }
                    return;
                case 1001:
                    PostInitActivity.this.mIsShowProgress = false;
                    if (PostInitActivity.this.mProgressDialog != null) {
                        PostInitActivity.this.mProgressDialog.dismiss();
                    }
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).rlParent.setKeepScreenOn(false);
                    return;
                case 1002:
                    if (PostInitActivity.this.mAdapter != null) {
                        PostInitActivity.this.checkChangButtonAllowNext();
                        PostInitActivity.this.mAdapter.notifyDataSetChanged();
                        if (PostInitActivity.this.mIsAllowScroll) {
                            ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).recyclerView.scrollToPosition(PostInitActivity.this.mList.size());
                            return;
                        }
                        return;
                    }
                    return;
                case 1003:
                    if (PostInitActivity.this.mUploadProgressLayoutBinding.progressbar != null) {
                        PostInitActivity.this.mUploadProgressLayoutBinding.progressbar.setProgress(100);
                        PostInitActivity.this.mUploadProgressLayoutBinding.tvNum.setText("100.0%");
                    }
                    if (PostInitActivity.this.mProgressDialog != null) {
                        PostInitActivity.this.mProgressDialog.dismiss();
                    }
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).rlParent.setKeepScreenOn(false);
                    PostInitActivity.this.mIsShowProgress = false;
                    return;
                case 1004:
                    PostInitActivity.this.showSecondTypeToast((int) R.string.upload_failde);
                    if (PostInitActivity.this.mProgressDialog != null) {
                        PostInitActivity.this.mProgressDialog.dismiss();
                    }
                    PostInitActivity.this.mIsShowProgress = false;
                    return;
                case 1006:
                    PostInitActivity.this.saveToDrafts();
                    return;
                case m_AppUI.MSG_APP_DATA_OK /*2000*/:
                    PostInitActivity.this.finish();
                    return;
                case 20001:
                    PostInitActivity.this.showThirdTypeToast(msg.obj.toString());
                    return;
                default:
                    return;
            }
        }
    };
    private int m270;
    private int m360;
    private int m480;
    private PostInitAdapter mAdapter;
    private boolean mAutoClose = false;
    private String mBaseQiNiuPath = "";
    private SelectCoverImageAdapter mCoverAdapter;
    private PostEdit mCoverImageEdit = new PostEdit(2, "");
    private DraftsHelpUtil mDraftsHelpUtil;
    private int mFromType = 0;
    private List<CommodityInfo> mGoodsIds = new ArrayList();
    private CommodityServeInfo mGoodsServeInfo = new CommodityServeInfo();
    private PostDragHeadBinding mHeadBinding;
    private boolean mIncludeVideo = false;
    private EditInputFilter mInputFilter;
    private boolean mIsAllowScroll = true;
    private boolean mIsFinsh = false;
    private boolean mIsShowProgress = false;
    private String mLastCoverHash = "";
    private List<PostEdit> mList = new ArrayList();
    private SendTranspondBottomMenuBarBinding mMenuBarBinding;
    private Dialog mProgressDialog;
    private int mResourceID = 0;
    private int mResourceType = 0;
    private int mSnsID = 0;
    private SparkDB mSparkDB;
    private String mStrTitle = "";
    private List<CircleInfo> mTopicCircleList = new ArrayList();
    private UploadManager mUploadManager;
    private UploadProgressLayoutBinding mUploadProgressLayoutBinding;
    private UploadQiNiu mUploadQiNiu;

    public void removeItem(int which) {
        this.mAdapter.index = -1;
        PostEdit postEdit = (PostEdit) this.mList.get(which);
        if (postEdit.getType() == 2) {
            FengConstant.UPLOAD_IMAGE_COUNT--;
        } else if (postEdit.getType() == 3) {
            FengConstant.UPLOAD_VIDEO_COUNT--;
        }
        this.mList.remove(which);
        checkChangButtonAllowNext();
        this.mAdapter.notifyDataSetChanged();
    }

    public int setBaseContentView() {
        return R.layout.post_init_activity;
    }

    public void initView() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.mSparkDB = FengApplication.getInstance().getSparkDB();
        FengConstant.UPLOAD_IMAGE_COUNT = 0;
        FengConstant.UPLOAD_VIDEO_COUNT = 0;
        PostDataManager.getInstance().setList(this.mList);
        this.mMenuBarBinding = SendTranspondBottomMenuBarBinding.inflate(this.mInflater, ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons, false);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("from_type_key")) {
                this.mFromType = intent.getIntExtra("from_type_key", 0);
                this.mResourceID = intent.getIntExtra("resourceid", 0);
                this.mResourceType = intent.getIntExtra("resourcetype", 0);
                this.mSnsID = intent.getIntExtra("snsid", 0);
            } else {
                this.mFromType = 0;
            }
            String circleJson = intent.getStringExtra("send_post_form_circle");
            if (!TextUtils.isEmpty(circleJson)) {
                CircleInfo circleInfo = (CircleInfo) JsonUtil.fromJson(circleJson, CircleInfo.class);
                if (circleInfo != null) {
                    this.mTopicCircleList.add(circleInfo);
                }
                initCircleText();
            }
        }
        this.mDraftsHelpUtil = new DraftsHelpUtil(this, 1, this.mFromType, this.mResourceID + "_" + this.mResourceType);
        hideDefaultTitleBar();
        ((PostInitActivityBinding) this.mBaseBinding).ivClose.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                PostInitActivity.this.alertSave();
            }
        });
        ((PostInitActivityBinding) this.mBaseBinding).tvRightTitle.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).tvRightTitle.isSelected() || !PostInitActivity.this.checkTitle(true)) {
                    return;
                }
                if (StringUtil.strLenthLikeWeiBo(PostInitActivity.this.mHeadBinding.etDigestText.getText().toString(), true) > 1000) {
                    PostInitActivity.this.showThirdTypeToast((int) R.string.beyond_max_hint);
                    return;
                }
                for (PostEdit edit : PostInitActivity.this.mList) {
                    if (StringUtil.strLenthLikeWeiBo(edit.getDescription(), true) > d_ResultType.SHORT_URL) {
                        PostInitActivity.this.showThirdTypeToast((int) R.string.beyond_max_hint);
                        return;
                    }
                }
                if (PostInitActivity.this.mList.size() > 0) {
                    PostInitActivity.this.initCoverImage();
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).includeCover.rlCoverParent.setVisibility(0);
                } else {
                    PostInitActivity.this.sendPost();
                }
                ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).kbDefEmoticons.reset();
            }
        });
        ((PostInitActivityBinding) this.mBaseBinding).tvRightTitle.setSelected(false);
        ((PostInitActivityBinding) this.mBaseBinding).tvSort.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (PostInitActivity.this.mList.size() > 0) {
                    PostInitActivity.this.startActivity(new Intent(PostInitActivity.this, RearrangeActivity.class));
                }
            }
        });
        initHeadView();
        initBottomMenu();
        initData();
        initKeyWordBarView();
        initDrafts();
        initCover();
        closeSwip();
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.rlCoverParent.setVisibility(8);
    }

    private void initCover() {
        this.mCoverAdapter = new SelectCoverImageAdapter(this, this.mList);
        if (!TextUtils.isEmpty(this.mCoverImageEdit.getHash())) {
            int size = this.mList.size();
            int i = 0;
            while (i < size) {
                PostEdit postEdit = (PostEdit) this.mList.get(i);
                if (postEdit.getType() != 2 || !postEdit.getHash().equals(this.mCoverImageEdit.getHash())) {
                    if (postEdit.getType() == 3 && postEdit.videoCoverImage.getHash().equals(this.mCoverImageEdit.getHash())) {
                        this.mCoverAdapter.setSelectPosition(i);
                        break;
                    }
                    i++;
                } else {
                    this.mCoverAdapter.setSelectPosition(i);
                    break;
                }
            }
        }
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.recyclerView.setAdapter(this.mCoverAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.recyclerView.setLayoutManager(linearLayoutManager);
        this.mCoverAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                PostInitActivity.this.mCoverAdapter.setSelectPosition(position);
                if (((PostEdit) PostInitActivity.this.mList.get(position)).getType() == 3) {
                    PostInitActivity.this.mCoverImageEdit = ((PostEdit) PostInitActivity.this.mList.get(position)).videoCoverImage;
                } else {
                    PostInitActivity.this.mCoverImageEdit = (PostEdit) PostInitActivity.this.mList.get(position);
                }
                PostInitActivity.this.resetImageHeight(false);
            }
        });
        this.m360 = this.mResources.getDimensionPixelSize(R.dimen.default_360PX);
        this.m480 = this.mResources.getDimensionPixelSize(R.dimen.default_480PX);
        this.m270 = this.mResources.getDimensionPixelSize(R.dimen.default_270PX);
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.publishButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).tvRightTitle.isSelected() && PostInitActivity.this.checkTitle(true)) {
                    PostInitActivity.this.sendPost();
                }
            }
        });
    }

    private void initCoverImage() {
        this.mCoverAdapter.notifyDataSetChanged();
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.getRoot().setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        });
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.ivCloseArrow.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).includeCover.rlCoverParent.setVisibility(8);
            }
        });
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.headImage.setHeadUrl(FengUtil.getHeadImageUrl(FengApplication.getInstance().getUserInfo().getHeadImageInfo()));
        if (TextUtils.isEmpty(getFeedTitleOrDes())) {
            ((PostInitActivityBinding) this.mBaseBinding).includeCover.title.setVisibility(8);
        } else {
            ((PostInitActivityBinding) this.mBaseBinding).includeCover.title.setVisibility(0);
            ((PostInitActivityBinding) this.mBaseBinding).includeCover.title.setText(getFeedTitleOrDes());
        }
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.userName.setText((CharSequence) FengApplication.getInstance().getUserInfo().name.get());
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.tvPraiseNum.setText(String.valueOf((new Random().nextInt(199) % 191) + 9));
        resetImageHeight(true);
    }

    private String getFeedTitleOrDes() {
        if (!TextUtils.isEmpty(this.mHeadBinding.postDragHeadEdit.getText().toString().trim())) {
            return this.mHeadBinding.postDragHeadEdit.getText().toString().trim();
        }
        if (TextUtils.isEmpty(this.mHeadBinding.etDigestText.getText().toString().trim())) {
            return "";
        }
        return this.mHeadBinding.etDigestText.getText().toString().trim();
    }

    private void resetImageHeight(boolean needJudge) {
        float proportion;
        if (needJudge) {
            if (!TextUtils.isEmpty(this.mCoverImageEdit.getHash()) || this.mList.size() <= 0) {
                boolean hasHash = false;
                int size = this.mList.size();
                for (int i = 0; i < size; i++) {
                    PostEdit edit = (PostEdit) this.mList.get(i);
                    if ((edit.getType() == 2 && edit.getHash().equals(this.mCoverImageEdit.getHash())) || (edit.getType() == 3 && edit.videoCoverImage.getHash().equals(this.mCoverImageEdit.getHash()))) {
                        hasHash = true;
                        this.mCoverAdapter.setSelectPosition(i);
                        break;
                    }
                }
                if (!hasHash && this.mList.size() > 0) {
                    if (((PostEdit) this.mList.get(0)).getType() == 3) {
                        this.mCoverImageEdit = ((PostEdit) this.mList.get(0)).videoCoverImage;
                    } else {
                        this.mCoverImageEdit = (PostEdit) this.mList.get(0);
                    }
                    this.mCoverAdapter.setSelectPosition(0);
                }
            } else {
                if (((PostEdit) this.mList.get(0)).getType() == 3) {
                    this.mCoverImageEdit = ((PostEdit) this.mList.get(0)).videoCoverImage;
                } else {
                    this.mCoverImageEdit = (PostEdit) this.mList.get(0);
                }
                this.mCoverAdapter.setSelectPosition(0);
            }
        }
        ImageInfo info = new ImageInfo();
        if (TextUtils.isEmpty(this.mCoverImageEdit.srcUrl)) {
            ((PostInitActivityBinding) this.mBaseBinding).includeCover.image.setAutoImageURI(Uri.parse("res://com.feng.car/2130838457"));
        } else {
            info.width = TextUtils.isEmpty(this.mCoverImageEdit.getWidth()) ? 0 : Integer.parseInt(this.mCoverImageEdit.getWidth());
            info.height = TextUtils.isEmpty(this.mCoverImageEdit.getHeight()) ? 0 : Integer.parseInt(this.mCoverImageEdit.getHeight());
            info.url = this.mCoverImageEdit.srcUrl;
            ((PostInitActivityBinding) this.mBaseBinding).includeCover.image.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(info, 640, 0.56f)));
        }
        if (info.height == 0) {
            proportion = 1.0f;
        } else {
            int width = info.width;
            int height = info.height;
            if (((((float) width) * 1.0f) / ((float) height)) * 1.0f <= 0.75f) {
                proportion = 0.75f;
            } else if (((((float) width) * 1.0f) / ((float) height)) * 1.0f >= 1.3333334f) {
                proportion = 1.3333334f;
            } else {
                proportion = ((((float) width) * 1.0f) / ((float) height)) * 1.0f;
            }
        }
        int imageHeight = (int) (((float) this.m360) * (1.0f / proportion));
        if (imageHeight >= this.m480) {
            imageHeight = this.m480;
        } else if (imageHeight <= this.m270) {
            imageHeight = this.m270;
        }
        ((PostInitActivityBinding) this.mBaseBinding).includeCover.image.setLayoutParams(new LayoutParams(-1, imageHeight));
    }

    private void initHeadView() {
        this.mHeadBinding = PostDragHeadBinding.inflate(this.mInflater, ((PostInitActivityBinding) this.mBaseBinding).rlParent, false);
        this.mHeadBinding.postDragHeadEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    PostInitActivity.this.mAdapter.index = -1;
                    PostInitActivity.this.mMenuBarBinding.ivAddAt.setVisibility(8);
                    PostInitActivity.this.mMenuBarBinding.ivAddEmoji.setVisibility(8);
                    PostInitActivity.this.changeTextNum(PostInitActivity.this.mHeadBinding.postDragHeadEdit.getText().toString().trim(), 30);
                    PostInitActivity.this.mHeadBinding.postDragHeadEdit.setCursorVisible(true);
                    return;
                }
                PostInitActivity.this.mMenuBarBinding.ivAddAt.setVisibility(0);
                PostInitActivity.this.mMenuBarBinding.ivAddEmoji.setVisibility(0);
                if (PostInitActivity.this.mAdapter.mOpen.isOpenSoftKeyboard()) {
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).kbDefEmoticons.showBarContainer();
                }
            }
        });
        this.mHeadBinding.postDragHeadEdit.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                PostInitActivity.this.changeTextNum(PostInitActivity.this.mHeadBinding.postDragHeadEdit.getText().toString().trim(), 30);
                PostInitActivity.this.checkChangButtonAllowNext();
            }
        });
        this.mHeadBinding.etDigestText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                PostInitActivity.this.changeTextNum(PostInitActivity.this.mHeadBinding.etDigestText.getText().toString().trim(), 1000);
                PostInitActivity.this.checkChangButtonAllowNext();
            }
        });
        this.mHeadBinding.etDigestText.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    PostInitActivity.this.changeTextNum(PostInitActivity.this.mHeadBinding.etDigestText.getText().toString().trim(), 1000);
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).kbDefEmoticons.setInputEditText(PostInitActivity.this.mHeadBinding.etDigestText);
                    PostInitActivity.this.mMenuBarBinding.ivAddAt.setVisibility(0);
                    PostInitActivity.this.mMenuBarBinding.ivAddEmoji.setVisibility(0);
                    return;
                }
                PostInitActivity.this.mMenuBarBinding.ivAddAt.setVisibility(8);
                PostInitActivity.this.mMenuBarBinding.ivAddEmoji.setVisibility(0);
            }
        });
        this.mInputFilter = new EditInputFilter(this);
        this.mHeadBinding.etDigestText.setFilters(new InputFilter[]{this.mInputFilter});
    }

    private void initBottomMenu() {
        ((PostInitActivityBinding) this.mBaseBinding).ivAddImg.setOnClickListener(this);
        ((PostInitActivityBinding) this.mBaseBinding).ivAddTopic.setOnClickListener(this);
        ((PostInitActivityBinding) this.mBaseBinding).tvAddMore.setOnClickListener(this);
        ((PostInitActivityBinding) this.mBaseBinding).tvAddImg.setOnClickListener(this);
        ((PostInitActivityBinding) this.mBaseBinding).tvTakePhoto.setOnClickListener(this);
        ((PostInitActivityBinding) this.mBaseBinding).tvAddTopic.setOnClickListener(this);
        ((PostInitActivityBinding) this.mBaseBinding).tvAddImg.setClickable(false);
        ((PostInitActivityBinding) this.mBaseBinding).tvTakePhoto.setClickable(false);
        ((PostInitActivityBinding) this.mBaseBinding).tvAddTopic.setClickable(false);
        if (FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 3 || FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 4) {
            if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 0) {
                ((PostInitActivityBinding) this.mBaseBinding).ivAddService.setVisibility(0);
                ((PostInitActivityBinding) this.mBaseBinding).rlAddService.setVisibility(0);
                ((PostInitActivityBinding) this.mBaseBinding).ivAddGoods.setVisibility(8);
                ((PostInitActivityBinding) this.mBaseBinding).rlAddGoods.setVisibility(8);
                ((PostInitActivityBinding) this.mBaseBinding).ivAddService.setOnClickListener(this);
                ((PostInitActivityBinding) this.mBaseBinding).rlAddService.setOnClickListener(this);
            } else if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 1) {
                ((PostInitActivityBinding) this.mBaseBinding).ivAddService.setVisibility(8);
                ((PostInitActivityBinding) this.mBaseBinding).rlAddService.setVisibility(8);
                ((PostInitActivityBinding) this.mBaseBinding).ivAddGoods.setVisibility(0);
                ((PostInitActivityBinding) this.mBaseBinding).rlAddGoods.setVisibility(0);
                ((PostInitActivityBinding) this.mBaseBinding).rlAddGoods.setOnClickListener(this);
                ((PostInitActivityBinding) this.mBaseBinding).ivAddGoods.setOnClickListener(this);
            }
        }
        ((PostInitActivityBinding) this.mBaseBinding).vCloseMenu.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).llItemExpend.resetScroll();
            }
        });
        ((PostInitActivityBinding) this.mBaseBinding).llItemExpend.setScrollListener(new ScrollerBottomView$OnScrollerStateListener() {
            public void onScrollState(boolean isTop, int dy) {
                if (isTop) {
                    if (dy >= ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).llItemExpend.getScrollHeight() - 10) {
                        ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).tvAddImg.setClickable(true);
                        ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).tvTakePhoto.setClickable(true);
                        ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).tvAddTopic.setClickable(true);
                    }
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).rlBottomMenu.setVisibility(8);
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).vCloseMenu.setVisibility(0);
                } else if (dy == 0) {
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).tvAddImg.setClickable(false);
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).tvTakePhoto.setClickable(false);
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).tvAddTopic.setClickable(false);
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).rlBottomMenu.setVisibility(0);
                    ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).vCloseMenu.setVisibility(8);
                }
            }
        });
    }

    private void initData() {
        this.mAdapter = new PostInitAdapter(this, this.mList, ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons, this.handler, this);
        this.mAdapter.setHeadVeiw(this.mHeadBinding.getRoot());
        ((PostInitActivityBinding) this.mBaseBinding).recyclerView.setAdapter(this.mAdapter);
        ((PostInitActivityBinding) this.mBaseBinding).recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchCallback(this.mAdapter));
        itemTouchHelper.attachToRecyclerView(((PostInitActivityBinding) this.mBaseBinding).recyclerView);
        ((PostInitActivityBinding) this.mBaseBinding).recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(((PostInitActivityBinding) this.mBaseBinding).recyclerView) {
            public void onLongClick(ViewHolder vh, MotionEvent e) {
                super.onLongClick(vh, e);
                if (vh.getLayoutPosition() != 0 && !PostInitActivity.this.mAdapter.mOpen.isOpenSoftKeyboard()) {
                    PostInitActivity.this.mAdapter.mTouchX = e.getX();
                    PostInitActivity.this.mAdapter.mTouchY = e.getY() - vh.itemView.getY();
                    itemTouchHelper.startDrag(vh);
                }
            }
        });
        initQiNiuPath();
    }

    private void initKeyWordBarView() {
        this.mMenuBarBinding.ivAddAt.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                PostInitActivity.this.startActivity(new Intent(PostInitActivity.this, AtUserActivity.class));
            }
        });
        this.mMenuBarBinding.ivAddImage.setVisibility(8);
        this.mMenuBarBinding.ivAddAt.setVisibility(8);
        this.mMenuBarBinding.ivAddEmoji.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).kbDefEmoticons.openEmoticonsKeyboard();
            }
        });
        this.mMenuBarBinding.ivComplete.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((PostInitActivityBinding) PostInitActivity.this.mBaseBinding).kbDefEmoticons.reset();
            }
        });
        ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons.setKeyboardStatusChangeListener(new XhsEmoticonsKeyBoard$OnKeyboardStatusChangeListener() {
            public void onStatusChange(int status) {
                switch (status) {
                    case 1001:
                        PostInitActivity.this.mMenuBarBinding.ivAddEmoji.setImageResource(R.drawable.add_keyboard_selector);
                        return;
                    case 1002:
                        PostInitActivity.this.mMenuBarBinding.ivAddEmoji.setImageResource(R.drawable.add_emoji_selector);
                        return;
                    default:
                        return;
                }
            }
        });
        ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons.addOnFuncKeyBoardListener(this);
        ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons.addView(this.mMenuBarBinding.getRoot());
        ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons.hideBarContainer();
        ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons.setInputEditText(this.mHeadBinding.etDigestText);
        ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons.setAdapter(SimpleCommonUtils.getCommonAdapter(this, this.emoticonClickListener));
    }

    private void changeTextNum(String strContent, int textNum) {
        int count = StringUtil.strLenthLikeWeiBo(strContent, true);
        this.mMenuBarBinding.tvCurrentWordsCount.setText(String.valueOf(count));
        this.mMenuBarBinding.tvCount.setText(String.valueOf(textNum));
        if (count > textNum) {
            this.mMenuBarBinding.tvCurrentWordsCount.setTextColor(this.mResources.getColor(R.color.color_d63d3d));
        } else {
            this.mMenuBarBinding.tvCurrentWordsCount.setTextColor(this.mResources.getColor(R.color.color_38_000000));
        }
    }

    private void initDrafts() {
        if (this.mFromType == 0) {
            if (this.mDraftsHelpUtil.isHasLocalDraft()) {
                initDraftsContent(this.mDraftsHelpUtil.getDraftsModel(), false);
                this.mAutoClose = true;
                ((PostInitActivityBinding) this.mBaseBinding).ivAddImg.performClick();
                return;
            }
            this.mAutoClose = true;
            ((PostInitActivityBinding) this.mBaseBinding).ivAddImg.performClick();
        } else if (this.mFromType != 1 && this.mFromType != 2) {
        } else {
            if (this.mDraftsHelpUtil.isHasLocalDraft()) {
                getServicerDrafts(true, this.mDraftsHelpUtil.getDraftsModel());
            } else {
                getServicerDrafts(false, null);
            }
        }
    }

    private void initDraftsContent(DraftsModel draftsModel, boolean isSync) {
        try {
            this.mList.clear();
            this.mStrTitle = draftsModel.title;
            this.mHeadBinding.postDragHeadEdit.setText(draftsModel.title);
            this.mHeadBinding.etDigestText.setText(draftsModel.description);
            if (draftsModel.goodsJson.length() > 0) {
                List<CommodityInfo> list = JsonUtil.fromJson(draftsModel.goodsJson, new TypeToken<List<CommodityInfo>>() {
                });
                if (list != null) {
                    this.mGoodsIds.clear();
                    this.mGoodsIds.addAll(list);
                }
            }
            if (draftsModel.servelistJson.length() > 0) {
                this.mGoodsServeInfo.parser(new JSONObject(draftsModel.servelistJson));
            }
            if (draftsModel.topiclistJson.length() > 0) {
                List<CircleInfo> list2 = JsonUtil.fromJson(draftsModel.topiclistJson, new TypeToken<List<CircleInfo>>() {
                });
                if ((this.mFromType != 0 || this.mTopicCircleList.size() <= 0) && list2 != null) {
                    this.mTopicCircleList.clear();
                    this.mTopicCircleList.addAll(list2);
                }
            }
            initCircleText();
            initBottonText();
            if (draftsModel.coverJson.length() > 0) {
                PostEdit edit = (PostEdit) JsonUtil.fromJson(draftsModel.coverJson, PostEdit.class);
                if (edit != null) {
                    this.mCoverImageEdit = edit;
                    this.mLastCoverHash = edit.getHash();
                }
            }
            if (draftsModel.postJson.length() > 0) {
                List<PostEdit> list3 = JsonUtil.fromJson(draftsModel.postJson, new TypeToken<List<PostEdit>>() {
                });
                for (PostEdit postEdit : list3) {
                    postEdit.setOnTextChange(this);
                    int type = postEdit.getType();
                    if (type == 2) {
                        FengConstant.UPLOAD_IMAGE_COUNT++;
                    } else if (type == 3) {
                        postEdit.localVideo = false;
                        FengConstant.UPLOAD_VIDEO_COUNT++;
                    }
                }
                this.mList.addAll(list3);
                if (!isSync) {
                    if (FengConstant.UPLOAD_IMAGE_COUNT > 30 || FengConstant.UPLOAD_VIDEO_COUNT > 1) {
                        ToastUtil.showToast(this, R.string.app_image_nonsupport);
                        this.mIsFinsh = true;
                        this.handler.sendEmptyMessageDelayed(m_AppUI.MSG_APP_DATA_OK, 2000);
                        return;
                    }
                    this.mAdapter.notifyDataSetChanged();
                    this.handler.sendEmptyMessage(11);
                    this.handler.sendEmptyMessageDelayed(12, 1000);
                }
            }
            if (isSync) {
                submit(3);
            } else {
                this.mDraftsHelpUtil.setOldJson(initLastPostJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getServicerDrafts(final boolean isCompare, final DraftsModel draftsModel) {
        if (this.mResourceID > 0) {
            showProgress("", "加载中...");
            Map<String, Object> map = new HashMap();
            map.put("resourceid", String.valueOf(this.mResourceID));
            map.put("resourcetype", String.valueOf(this.mResourceType));
            if (this.mFromType != 2) {
                map.put("flag", String.valueOf(-1));
            } else {
                map.put("snsid", String.valueOf(this.mSnsID));
            }
            FengApplication.getInstance().httpRequest("thread/threadinfofrom/", map, new OkHttpResponseCallback() {
                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        int code = jsonObject.getInt("code");
                        if (code == 1) {
                            JSONObject threadJson = jsonObject.getJSONObject("body").getJSONObject("sns");
                            SnsInfo info = new SnsInfo();
                            info.parser(threadJson);
                            PostInitActivity.this.mSnsID = info.id;
                            if (info.ishistory == 1) {
                                PostInitActivity.this.showSecondTypeToast((int) R.string.load_drafts_failed);
                                PostInitActivity.this.mIsFinsh = true;
                                PostInitActivity.this.handler.sendEmptyMessageDelayed(m_AppUI.MSG_APP_DATA_OK, 2000);
                                return;
                            }
                            PostInitActivity.this.initServicerDraftsContent(isCompare, draftsModel, info);
                        } else if (code == -220) {
                            PostInitActivity.this.showSecondTypeToast((int) R.string.load_drafts_failed);
                            PostInitActivity.this.mIsFinsh = true;
                            PostInitActivity.this.handler.sendEmptyMessageDelayed(m_AppUI.MSG_APP_DATA_OK, 2000);
                        } else {
                            PostInitActivity.this.showSecondTypeToast((int) R.string.load_drafts_failed);
                            PostInitActivity.this.mIsFinsh = true;
                            FengApplication.getInstance().upLoadLog(true, "thread/threadinfofrom/  " + code);
                            PostInitActivity.this.handler.sendEmptyMessageDelayed(m_AppUI.MSG_APP_DATA_OK, 2000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog("thread/threadinfofrom/", content, e);
                        PostInitActivity.this.showThirdTypeToast((int) R.string.load_drafts_failed);
                        PostInitActivity.this.mIsFinsh = true;
                        PostInitActivity.this.handler.sendEmptyMessageDelayed(m_AppUI.MSG_APP_DATA_OK, 2000);
                    }
                }

                public void onNetworkError() {
                }

                public void onStart() {
                }

                public void onFinish() {
                    PostInitActivity.this.hideProgress();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    PostInitActivity.this.showThirdTypeToast((int) R.string.load_drafts_failed);
                    PostInitActivity.this.mIsFinsh = true;
                    PostInitActivity.this.handler.sendEmptyMessageDelayed(m_AppUI.MSG_APP_DATA_OK, 2000);
                }
            });
        }
    }

    private void initServicerDraftsContent(boolean isCompare, DraftsModel draftsModel, SnsInfo info) {
        try {
            this.mGoodsIds = info.shopcommodityvos;
            this.mGoodsServeInfo = info.scsl;
            this.mTopicCircleList.clear();
            this.mTopicCircleList.addAll(info.communitylist.getCircleList());
            if (info.list.size() > 0) {
                this.mList.clear();
                this.mHeadBinding.etDigestText.setText((CharSequence) info.description.get());
                for (SnsPostResources postResources : info.list) {
                    int type = postResources.type;
                    PostEdit postEdit;
                    switch (type) {
                        case 2:
                            postEdit = new PostEdit(type, postResources.description, "");
                            postEdit.setId(postResources.qiniu.id);
                            postEdit.setMime(postResources.qiniu.mime);
                            postEdit.setSize(postResources.qiniu.size);
                            postEdit.setHash(postResources.image.hash);
                            postEdit.setUrl(postResources.qiniu.url);
                            postEdit.srcUrl = postResources.image.url;
                            postEdit.setWidth(postResources.image.width + "");
                            postEdit.setHeight(postResources.image.height + "");
                            if (postResources.description.trim().length() > 0) {
                                postEdit.showDescribe = true;
                            }
                            FengConstant.UPLOAD_IMAGE_COUNT++;
                            postEdit.setOnTextChange(this);
                            this.mList.add(postEdit);
                            break;
                        case 3:
                            postEdit = new PostEdit(type, postResources.description, "");
                            postEdit.setId(postResources.qiniu.id);
                            postEdit.setMime(postResources.qiniu.mime);
                            postEdit.setSize(postResources.qiniu.size);
                            postEdit.setHash(postResources.qiniu.hash);
                            postEdit.setUrl(postResources.qiniu.url);
                            postEdit.setTime(postResources.qiniu.time);
                            postEdit.setWidth(postResources.qiniu.width + "");
                            postEdit.setHeight(postResources.qiniu.height + "");
                            postEdit.setVideodefinition(postResources.qiniu.videodefinition);
                            postEdit.srcUrl = "";
                            postEdit.localVideo = false;
                            postEdit.videoCoverImage.setWidth(postResources.image.width + "");
                            postEdit.videoCoverImage.setHeight(postResources.image.height + "");
                            postEdit.videoCoverImage.setHash(postResources.image.hash);
                            postEdit.videoCoverImage.srcUrl = postResources.image.url;
                            if (postResources.description.trim().length() > 0) {
                                postEdit.showDescribe = true;
                            }
                            FengConstant.UPLOAD_VIDEO_COUNT++;
                            postEdit.setOnTextChange(this);
                            this.mList.add(postEdit);
                            break;
                        default:
                            break;
                    }
                }
            }
            if (FengConstant.UPLOAD_IMAGE_COUNT > 30 || FengConstant.UPLOAD_VIDEO_COUNT > 1) {
                ToastUtil.showToast(this, R.string.app_image_nonsupport);
                this.mIsFinsh = true;
                this.handler.sendEmptyMessageDelayed(m_AppUI.MSG_APP_DATA_OK, 2000);
            }
            if (!isCompare) {
                initTitleSrcInterest(info);
            } else if (draftsModel == null) {
                initTitleSrcInterest(info);
            } else if (draftsModel.title.equals(info.title.get()) && draftsModel.description.equals(info.description.get())) {
                initCoverData(info);
                if (!draftsModel.coverJson.equals(JsonUtil.toJson(this.mCoverImageEdit))) {
                    draftsSelect(draftsModel, info);
                } else if (draftsModel.postJson.length() > 0 && !draftsModel.postJson.equals(JsonUtil.toJson(this.mList))) {
                    draftsSelect(draftsModel, info);
                } else if (draftsModel.goodsJson.length() > 0 && !draftsModel.goodsJson.equals(JsonUtil.toJson(this.mGoodsIds))) {
                    draftsSelect(draftsModel, info);
                } else if (draftsModel.servelistJson.length() > 0 && !draftsModel.servelistJson.equals(JsonUtil.toJson(this.mGoodsServeInfo))) {
                    draftsSelect(draftsModel, info);
                } else if (draftsModel.topiclistJson.length() <= 0 || draftsModel.topiclistJson.equals(JsonUtil.toJson(this.mTopicCircleList))) {
                    initTitleSrcInterest(info);
                } else {
                    draftsSelect(draftsModel, info);
                }
            } else {
                draftsSelect(draftsModel, info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void draftsSelect(final DraftsModel draftsModel, final SnsInfo info) {
        this.mDraftsHelpUtil.showSeverLocalDialog(new DraftsHelpUtil$OnUserSelecsstListener() {
            public void onSelected(int selID) {
                if (selID == 1) {
                    EventBus.getDefault().post(new DraftsRefreshEvent(PostInitActivity.this.mResourceID + "_" + PostInitActivity.this.mResourceType, 1));
                    PostInitActivity.this.initTitleSrcInterest(info);
                } else if (selID == 2) {
                    PostInitActivity.this.initDraftsContent(draftsModel, true);
                }
            }
        });
    }

    private void initCoverData(SnsInfo info) {
        if (!TextUtils.isEmpty(info.image.hash)) {
            this.mCoverImageEdit.setId(info.image.id);
            this.mCoverImageEdit.setWidth(info.image.width + "");
            this.mCoverImageEdit.setHeight(info.image.height + "");
            this.mCoverImageEdit.setHash(info.image.hash + "");
            this.mCoverImageEdit.setMime(info.image.getImageMime());
            this.mCoverImageEdit.srcUrl = info.image.url;
        }
    }

    private void initTitleSrcInterest(SnsInfo info) {
        this.mHeadBinding.postDragHeadEdit.setText((CharSequence) info.title.get());
        this.mHeadBinding.etDigestText.setText((CharSequence) info.description.get());
        if (!TextUtils.isEmpty(info.image.hash)) {
            initCoverData(info);
        }
        this.mDraftsHelpUtil.setOldJson(initLastPostJson());
        initBottonText();
        initCircleText();
        this.mAdapter.notifyDataSetChanged();
        this.handler.sendEmptyMessage(11);
    }

    private void initQiNiuPath() {
        FengApplication.getInstance().httpRequest("home/state/", new HashMap(), new OkHttpResponseCallback() {
            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        PostInitActivity.this.mBaseQiNiuPath = jsonObject.getJSONObject("body").getJSONObject("default").getString("imageurl");
                        return;
                    }
                    PostInitActivity.this.mBaseQiNiuPath = "";
                    FengApplication.getInstance().upLoadLog(true, "home/state/  " + code);
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("home/state/", content, e);
                    PostInitActivity.this.mBaseQiNiuPath = "";
                }
            }

            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                PostInitActivity.this.mBaseQiNiuPath = "";
            }
        });
    }

    public void onSingleClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_add_img /*2131625371*/:
            case R.id.iv_add_img /*2131625386*/:
                PostDataManager.getInstance().getLocalSelMedia().clear();
                if (FengConstant.UPLOAD_IMAGE_COUNT >= 30) {
                    showThirdTypeToast("帖子最多只能添加30张图片");
                    this.mAutoClose = false;
                    return;
                } else if (Environment.getExternalStorageState().equals("mounted")) {
                    saveToDrafts();
                    intent = new Intent(this, SelectImageVideoActivity.class);
                    intent.putExtra("is_auto_close", this.mAutoClose);
                    startActivity(intent);
                    this.mAutoClose = false;
                    return;
                } else {
                    showSecondTypeToast((int) R.string.not_find_storage_image);
                    this.mAutoClose = false;
                    return;
                }
            case R.id.tv_take_photo /*2131625373*/:
                PostDataManager.getInstance().getLocalSelMedia().clear();
                checkCamera();
                return;
            case R.id.tv_add_topic /*2131625375*/:
            case R.id.iv_add_topic /*2131625387*/:
                intent = new Intent(this, AddSubjectActivity.class);
                intent.putExtra(AddSubjectActivity.SEL_CIRCLE_DATA, JsonUtil.toJson(this.mTopicCircleList));
                intent.putExtra(AddSubjectActivity.DATA_CONTEXT, getFeedTitleOrDes());
                startActivity(intent);
                return;
            case R.id.rl_add_service /*2131625377*/:
            case R.id.rl_add_goods /*2131625380*/:
            case R.id.iv_add_service /*2131625388*/:
            case R.id.iv_add_goods /*2131625389*/:
                if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 1) {
                    intent = new Intent(this, SelectGoodsActivity.class);
                    intent.putExtra("DATA_JSON", JsonUtil.toJson(this.mGoodsIds));
                    startActivity(intent);
                    return;
                }
                intent = new Intent(this, SelectGoodsActivity.class);
                intent.putExtra("DATA_JSON", JsonUtil.toJson(this.mGoodsIds));
                intent.putExtra("serve_item_data", JsonUtil.toJson(this.mGoodsServeInfo));
                startActivity(intent);
                return;
            case R.id.tv_add_more /*2131625390*/:
                ((PostInitActivityBinding) this.mBaseBinding).llItemExpend.scrollerToTop();
                return;
            case R.id.iv_cancel /*2131625530*/:
                if (this.mUploadQiNiu != null) {
                    this.mUploadQiNiu.setBreakUpload(true);
                    return;
                }
                if (this.mProgressDialog != null) {
                    this.mProgressDialog.dismiss();
                }
                this.mIsShowProgress = false;
                return;
            default:
                return;
        }
    }

    private void checkCamera() {
        if (VERSION.SDK_INT >= 23) {
            int hasWriteContactsPermission = checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
            int hasCameraPermission = checkSelfPermission("android.permission.CAMERA");
            if (hasCameraPermission != 0 && hasWriteContactsPermission != 0) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 50003);
                return;
            } else if (hasCameraPermission != 0 && hasWriteContactsPermission == 0) {
                requestPermissions(new String[]{"android.permission.CAMERA"}, 50003);
                return;
            } else if (hasCameraPermission == 0 && hasWriteContactsPermission != 0) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 50003);
                return;
            }
        }
        sendVideo();
    }

    private void sendVideo() {
        Intent intent;
        if (FengConstant.UPLOAD_VIDEO_COUNT >= 1 && FengConstant.UPLOAD_IMAGE_COUNT >= 30) {
            showThirdTypeToast((int) R.string.max_upload_image_video);
        } else if (!Environment.getExternalStorageState().equals("mounted")) {
            showSecondTypeToast((int) R.string.not_find_storage_video_not_recording);
        } else if (FileUtil.showFileAvailable() < 200.0d) {
            showSecondTypeToast((int) R.string.sdcard_not_enough);
        } else if (FengConstant.UPLOAD_IMAGE_COUNT < 30) {
            intent = new Intent(this, CameraActivity.class);
            intent.putExtra(CameraPreviewActivity.TYPE_KEY, 1);
            startActivity(intent);
        } else if (FengConstant.UPLOAD_VIDEO_COUNT < 1) {
            intent = new Intent(this, CameraActivity.class);
            intent.putExtra(CameraPreviewActivity.TYPE_KEY, 0);
            startActivity(intent);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendPost() {
        if (!WifiUtil.isNetworkAvailable(this)) {
            showSecondTypeToast((int) R.string.net_abnormal);
        } else if (this.mList.size() > 0 || this.mHeadBinding.postDragHeadEdit.getText().toString().trim().length() >= 0 || this.mHeadBinding.etDigestText.getText().toString().trim().length() >= 0) {
            submit(1);
        } else {
            showThirdTypeToast((int) R.string.enter_post_content);
        }
    }

    private void submit(int flag) {
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            PostEdit edit = (PostEdit) this.mList.get(i);
            edit.setSort(i + 1);
            Matcher emoticon;
            String key;
            String value;
            if (edit.getType() == 2) {
                if (!TextUtils.isEmpty(edit.getDescription().trim())) {
                    emoticon = HttpConstant.PATTERN_EMOTICON_TEXT.matcher(edit.getDescription().trim());
                    while (emoticon.find()) {
                        key = emoticon.group();
                        value = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(key);
                        if (!TextUtils.isEmpty(value)) {
                            edit.setDescription(edit.getDescription().trim().replace(key, value));
                        }
                    }
                }
            } else if (edit.getType() == 3) {
                if (!TextUtils.isEmpty(edit.getDescription().trim())) {
                    emoticon = HttpConstant.PATTERN_EMOTICON_TEXT.matcher(edit.getDescription().trim());
                    while (emoticon.find()) {
                        key = emoticon.group();
                        value = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(key);
                        if (!TextUtils.isEmpty(value)) {
                            edit.setDescription(edit.getDescription().trim().replace(key, value));
                        }
                    }
                }
                this.mIncludeVideo = true;
            }
        }
        if (flag != 1) {
            publishPost(this.mIncludeVideo, flag, false);
        } else if (this.mSnsID > 0) {
            List<DialogItemEntity> list = new ArrayList();
            list.add(new DialogItemEntity(getString(R.string.affirm), false));
            CommonDialog.showCommonDialog(this, "确定完成编辑？", list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    PostInitActivity.this.publishPost(PostInitActivity.this.mIncludeVideo, 1, false);
                }
            });
        } else {
            sendAddCircleDialog();
        }
    }

    private void publishPost(final boolean isIncludeVideo, final int flag, final boolean isAddCircle) {
        String str;
        Map<String, Object> map = new HashMap();
        Map<String, Object> map2 = new HashMap();
        if (this.mList.size() > 0 && !TextUtils.isEmpty(this.mCoverImageEdit.getHash())) {
            this.mCoverImageEdit.setType(2);
            map2.put("coverimage", this.mCoverImageEdit);
        }
        map2.put("list", this.mList);
        map2.put("title", this.mStrTitle);
        map2.put("source", String.valueOf(2));
        String content = this.mHeadBinding.etDigestText.getText().toString().trim();
        if (flag == 1) {
            showProgress("", "发布中...");
            if (this.mResourceID > 0) {
                map2.put("resourceid", String.valueOf(this.mResourceID));
                map2.put("resourcetype", String.valueOf(this.mResourceType));
                if (this.mFromType == 2) {
                    map2.put("snsid", String.valueOf(this.mSnsID));
                }
            }
        } else {
            if (flag == 3) {
                showProgress("", "同步中...");
            } else {
                showProgress("", "保存中...");
            }
            if (this.mResourceID > 0) {
                map2.put("resourceid", String.valueOf(this.mResourceID));
                map2.put("resourcetype", String.valueOf(this.mResourceType));
                if (this.mSnsID > 0) {
                    map2.put("snsid", String.valueOf(this.mSnsID));
                }
                map2.put(NotificationCompat.CATEGORY_STATUS, String.valueOf(-1));
            } else {
                map2.put(NotificationCompat.CATEGORY_STATUS, String.valueOf(-1));
            }
        }
        map.put(UriUtil.LOCAL_CONTENT_SCHEME, content);
        map.put("atlist", getAtMap(content));
        map.put("thread", map2);
        List<Integer> listID;
        if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 1 && this.mGoodsIds.size() > 0) {
            listID = new ArrayList();
            for (CommodityInfo commodityInfo : this.mGoodsIds) {
                listID.add(Integer.valueOf(commodityInfo.id));
            }
            map.put("commodityidlist", listID);
        } else if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 0 && this.mGoodsIds.size() > 0) {
            listID = new ArrayList();
            for (CommodityInfo commodityInfo2 : this.mGoodsIds) {
                listID.add(Integer.valueOf(commodityInfo2.id));
            }
            map.put("commodityidlist", listID);
            map.put("commodityservicelist", this.mGoodsServeInfo);
        }
        map.put("isjoin", String.valueOf(isAddCircle ? "1" : "0"));
        map.put("communitylist", this.mTopicCircleList);
        FengApplication instance = FengApplication.getInstance();
        if (flag == 1) {
            str = "thread/add/";
        } else {
            str = "sns/adddraft/";
        }
        instance.httpRequest(str, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                PostInitActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                PostInitActivity.this.saveFailure(flag);
            }

            public void onStart() {
            }

            public void onFinish() {
                PostInitActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                PostInitActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                PostInitActivity.this.saveFailure(flag);
                FengApplication.getInstance().upLoadTryCatchLog(flag == 1 ? "thread/add/" : "sns/adddraft/", content, null);
                PostInitActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        if (flag != 3) {
                            PostInitActivity.this.mDraftsHelpUtil.deleteLocalDrafts();
                        }
                        SnsInfo info;
                        JSONObject snsJson;
                        SnsInfo snsInfo;
                        Message message;
                        if (flag == 3) {
                            if (PostInitActivity.this.mFromType == 1) {
                                EventBus.getDefault().post(new DraftsRefreshEvent(PostInitActivity.this.mResourceID + "_" + PostInitActivity.this.mResourceType, 1));
                            } else if (PostInitActivity.this.mFromType == 2) {
                                info = new SnsInfo();
                                info.id = PostInitActivity.this.mSnsID;
                                EventBus.getDefault().post(new SnsInfoRefreshEvent(info, m_AppUI.MSG_APP_VERSION_FORCE, true));
                            }
                            PostInitActivity.this.mDraftsHelpUtil.setOldJson(PostInitActivity.this.initLastPostJson());
                            PostInitActivity.this.mAdapter.notifyDataSetChanged();
                            PostInitActivity.this.handler.sendEmptyMessage(11);
                            PostInitActivity.this.handler.sendEmptyMessageDelayed(12, 1000);
                            return;
                        } else if (flag == 2) {
                            if (PostInitActivity.this.mFromType == 1) {
                                snsJson = jsonObject.getJSONObject("body").getJSONObject("sns");
                                snsInfo = new SnsInfo();
                                snsInfo.parser(snsJson);
                                EventBus.getDefault().post(new DraftsRefreshEvent(PostInitActivity.this.mResourceID + "_" + PostInitActivity.this.mResourceType, 1, snsInfo));
                            } else if (PostInitActivity.this.mFromType == 2) {
                                info = new SnsInfo();
                                info.id = PostInitActivity.this.mSnsID;
                                EventBus.getDefault().post(new SnsInfoRefreshEvent(info, m_AppUI.MSG_APP_VERSION_FORCE, true));
                                EventBus.getDefault().post(new SendDelArticleSuccess(2, info));
                            }
                            message = Message.obtain();
                            message.what = 20001;
                            message.obj = PostInitActivity.this.getString(R.string.save_drafts_success);
                            PostInitActivity.this.handler.sendMessage(message);
                            PostInitActivity.this.mIsFinsh = true;
                            PostInitActivity.this.handler.sendEmptyMessageDelayed(m_AppUI.MSG_APP_DATA_OK, 2000);
                            return;
                        } else {
                            snsJson = jsonObject.getJSONObject("body").getJSONObject("sns");
                            snsInfo = new SnsInfo();
                            snsInfo.parser(snsJson);
                            if (PostInitActivity.this.mSnsID <= 0 && snsInfo.communitylist.size() > 0 && isAddCircle) {
                                EventBus.getDefault().post(new AddCircleEvent(AddCircleEvent.ATTENTION_CANCEL_CIRCLE_TYPE, snsInfo.communitylist.get(0)));
                            }
                            if (PostInitActivity.this.mFromType == 1) {
                                EventBus.getDefault().post(new DraftsRefreshEvent(PostInitActivity.this.mResourceID + "_" + PostInitActivity.this.mResourceType, 2));
                                EventBus.getDefault().post(new SendDelArticleSuccess(1, snsInfo));
                            } else if (PostInitActivity.this.mFromType == 2) {
                                if (isIncludeVideo) {
                                    info = new SnsInfo();
                                    info.id = snsInfo.id;
                                    EventBus.getDefault().post(new SnsInfoRefreshEvent(info, m_AppUI.MSG_APP_VERSION_FORCE, true));
                                } else {
                                    EventBus.getDefault().post(new SnsInfoModifyEvent(snsInfo));
                                }
                            } else if (PostInitActivity.this.mFromType == 0) {
                                EventBus.getDefault().post(new SendDelArticleSuccess(1, snsInfo));
                            }
                            message = Message.obtain();
                            message.what = 20001;
                            if (isIncludeVideo) {
                                message.obj = PostInitActivity.this.getString(R.string.publish_video_success);
                            } else {
                                message.obj = PostInitActivity.this.getString(R.string.publish_success);
                            }
                            PostInitActivity.this.handler.sendMessage(message);
                            PostInitActivity.this.mIsFinsh = true;
                            PostInitActivity.this.handler.sendEmptyMessageDelayed(m_AppUI.MSG_APP_DATA_OK, 2000);
                            return;
                        }
                    }
                    PostInitActivity.this.saveFailure(flag);
                    FengApplication.getInstance().upLoadLog(true, flag == 1 ? "thread/add/" : "sns/adddraft/  " + code);
                } catch (Exception e) {
                    String str;
                    e.printStackTrace();
                    PostInitActivity.this.saveFailure(flag);
                    FengApplication instance = FengApplication.getInstance();
                    if (flag == 1) {
                        str = "thread/add/";
                    } else {
                        str = "sns/adddraft/";
                    }
                    instance.upLoadTryCatchLog(str, content, e);
                }
            }
        });
    }

    private void saveFailure(final int type) {
        this.mDraftsHelpUtil.showSaveFailureDialog(type, new DraftsHelpUtil$OnUserSelecsstListener() {
            public void onSelected(int selID) {
                if (selID == 3) {
                    PostInitActivity.this.mIsFinsh = false;
                    PostInitActivity.this.publishPost(PostInitActivity.this.mIncludeVideo, type, false);
                } else if (selID == 4) {
                    if (PostInitActivity.this.mFromType == 1) {
                        EventBus.getDefault().post(new DraftsRefreshEvent(PostInitActivity.this.mResourceID + "_" + PostInitActivity.this.mResourceType, 3));
                    }
                    PostInitActivity.this.mIsFinsh = false;
                    PostInitActivity.this.finish();
                } else if (selID == 5) {
                    PostInitActivity.this.mIsFinsh = false;
                    PostInitActivity.this.finish();
                }
            }
        });
    }

    private String initLastPostJson() {
        Map<String, Object> map = new HashMap();
        map.put("title", this.mHeadBinding.postDragHeadEdit.getText().toString().trim());
        map.put("description", this.mHeadBinding.etDigestText.getText().toString().trim());
        map.put("cover", TextUtils.isEmpty(this.mCoverImageEdit.getHash()) ? "" : this.mCoverImageEdit);
        map.put(UriUtil.LOCAL_CONTENT_SCHEME, this.mList);
        map.put("goodsjson", this.mGoodsIds);
        map.put("servelistjson", this.mGoodsServeInfo);
        map.put("topiclistJson", this.mTopicCircleList);
        return JsonUtil.toJson(map);
    }

    private void saveToDrafts() {
        DraftsModel draftsModel = new DraftsModel();
        draftsModel.title = this.mHeadBinding.postDragHeadEdit.getText().toString().trim();
        draftsModel.coverJson = TextUtils.isEmpty(this.mCoverImageEdit.getHash()) ? "" : JsonUtil.toJson(this.mCoverImageEdit);
        draftsModel.postJson = JsonUtil.toJson(this.mList);
        draftsModel.goodsJson = JsonUtil.toJson(this.mGoodsIds);
        draftsModel.servelistJson = JsonUtil.toJson(this.mGoodsServeInfo);
        draftsModel.topiclistJson = JsonUtil.toJson(this.mTopicCircleList);
        draftsModel.resources_id = this.mResourceID + "_" + this.mResourceType;
        draftsModel.description = this.mHeadBinding.etDigestText.getText().toString().trim();
        draftsModel.user_id = FengApplication.getInstance().getUserInfo().id;
        this.mSparkDB.addDrafts(draftsModel);
    }

    private void alertSave() {
        if (this.mResourceID > 0) {
            if (this.mHeadBinding.postDragHeadEdit.getText().toString().trim().length() == 0 && this.mHeadBinding.etDigestText.getText().toString().trim().length() == 0 && this.mList.size() == 0) {
                finish();
                return;
            }
            String strJson = initLastPostJson();
            if (strJson == null) {
                strJson = "";
            }
            if (this.mDraftsHelpUtil.getOldJson().equals(strJson)) {
                this.mDraftsHelpUtil.deleteLocalDrafts();
                this.mIsFinsh = true;
                finish();
                return;
            }
            saveDraftsHint();
        } else if (this.mHeadBinding.postDragHeadEdit.getText().toString().trim().length() == 0 && this.mHeadBinding.etDigestText.getText().toString().trim().length() == 0 && this.mList.size() == 0) {
            saveToDrafts();
            this.mIsFinsh = true;
            finish();
        } else {
            saveDraftsHint();
        }
    }

    public void saveDraftsHint() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("保存草稿", false));
        list.add(new DialogItemEntity("不保存", true));
        CommonDialog.showCommonDialog(this, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    PostInitActivity.this.mStrTitle = PostInitActivity.this.mHeadBinding.postDragHeadEdit.getText().toString().trim();
                    PostInitActivity.this.submit(2);
                    PostInitActivity.this.mIsFinsh = true;
                    MobclickAgent.onEvent(PostInitActivity.this, "write_article_save");
                } else if (position == 1) {
                    PostInitActivity.this.mIsFinsh = true;
                    PostInitActivity.this.mDraftsHelpUtil.deleteLocalDrafts();
                    PostInitActivity.this.finish();
                    MobclickAgent.onEvent(PostInitActivity.this, "write_article_nosave");
                }
            }
        });
    }

    protected void onPause() {
        super.onPause();
        if (!this.mIsFinsh) {
            saveToDrafts();
        }
    }

    protected void onResume() {
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (!this.mIsShowProgress) {
            if (((PostInitActivityBinding) this.mBaseBinding).includeCover.rlCoverParent.isShown()) {
                ((PostInitActivityBinding) this.mBaseBinding).includeCover.rlCoverParent.setVisibility(8);
            } else {
                alertSave();
            }
        }
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            FengConstant.UPLOAD_IMAGE_COUNT = 0;
            FengConstant.UPLOAD_VIDEO_COUNT = 0;
            PostDataManager.getInstance().releaseData();
            this.handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveImageOrVideoPath(ImageOrVideoPathEvent pathEvent) {
        if (pathEvent != null && pathEvent.list != null && pathEvent.list.size() > 0) {
            ((PostInitActivityBinding) this.mBaseBinding).rlParent.setKeepScreenOn(true);
            ((PostInitActivityBinding) this.mBaseBinding).llItemExpend.resetScroll();
            if (this.mList.size() > 0) {
                this.mIsAllowScroll = true;
            } else {
                this.mIsAllowScroll = false;
            }
            if (pathEvent.isIncludeVideo) {
                FengConstant.UPLOAD_VIDEO_COUNT = 0;
                FengConstant.UPLOAD_IMAGE_COUNT -= pathEvent.list.size() - 1;
                hintSend(pathEvent);
            } else if (pathEvent.type == 10) {
                uploadImageHandle(pathEvent);
            } else {
                FengConstant.UPLOAD_IMAGE_COUNT -= pathEvent.list.size();
                uploadImageHandle(pathEvent);
            }
        }
    }

    private void uploadImageHandle(ImageOrVideoPathEvent pathEvent) {
        if (pathEvent.list != null) {
            showProgressDialog();
            this.mIsShowProgress = true;
            this.mUploadProgressLayoutBinding.progressbar.setProgress(0);
            this.mUploadProgressLayoutBinding.tvProgDescribe.setText("正在拼命上传中，请勿退出。");
            this.mUploadProgressLayoutBinding.tvNum.setText("0.0%");
            if (this.mUploadManager == null) {
                this.mUploadManager = new UploadManager();
            }
            int i = this.mResourceID;
            int i2 = this.mResourceType;
            String str = (this.mBaseQiNiuPath == null || this.mBaseQiNiuPath.length() <= 0) ? HttpConstant.QINIUIMAGEBASEPATH : this.mBaseQiNiuPath;
            this.mUploadQiNiu = new UploadQiNiu(this, i, i2, str, this.mCoverImageEdit, this.mList, pathEvent.list, this.mUploadManager, this.handler, this, FengConstant.UPLOAD_IMAGE_ORIGINAL);
        }
    }

    private void hintSend(final ImageOrVideoPathEvent pathEvent) {
        if (WifiUtil.isWifiConnectivity(this)) {
            uploadImageHandle(pathEvent);
        } else {
            new Builder(this, 3).setMessage("您正在使用2G/3G/4G网络进行视频上传，\n是否继续？").setPositiveButton("继续", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    PostInitActivity.this.uploadImageHandle(pathEvent);
                }
            }).setNegativeButton("取消", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
    }

    private void showProgressDialog() {
        if (this.mUploadProgressLayoutBinding == null) {
            this.mUploadProgressLayoutBinding = UploadProgressLayoutBinding.inflate(this.mInflater);
        }
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mProgressDialog.setCanceledOnTouchOutside(true);
            this.mProgressDialog.setCancelable(false);
            Window window = this.mProgressDialog.getWindow();
            window.setGravity(17);
            window.setWindowAnimations(R.style.shareAnimation);
            window.setContentView(this.mUploadProgressLayoutBinding.getRoot());
            window.setLayout(-1, -1);
        }
        this.mProgressDialog.show();
        this.mUploadProgressLayoutBinding.rlProgress.setOnClickListener(this);
        this.mUploadProgressLayoutBinding.ivCancel.setOnClickListener(this);
    }

    public void OnFuncPop(int height) {
        ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons.showBarContainer();
        ((PostInitActivityBinding) this.mBaseBinding).llItemExpend.setVisibility(8);
        ((PostInitActivityBinding) this.mBaseBinding).vCloseMenu.setVisibility(8);
        ((PostInitActivityBinding) this.mBaseBinding).vBottonPlace.setVisibility(8);
        this.mAdapter.mOpen.setOpenSoftKeyboard(true);
    }

    public void OnFuncClose() {
        View view = ((PostInitActivityBinding) this.mBaseBinding).recyclerView.getFocusedChild();
        if (view != null) {
            view.clearFocus();
        }
        ((PostInitActivityBinding) this.mBaseBinding).kbDefEmoticons.hideBarContainer();
        ((PostInitActivityBinding) this.mBaseBinding).llItemExpend.setVisibility(0);
        ((PostInitActivityBinding) this.mBaseBinding).vBottonPlace.setVisibility(0);
        this.mAdapter.mOpen.setOpenSoftKeyboard(false);
        this.mAdapter.index = -1;
        try {
            if (this.mMenuBarBinding != null && Integer.parseInt(this.mMenuBarBinding.tvCurrentWordsCount.getText().toString()) > Integer.parseInt(this.mMenuBarBinding.tvCount.getText().toString())) {
                showThirdTypeToast((int) R.string.beyond_max_hint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkTitle(boolean isHint) {
        this.mStrTitle = this.mHeadBinding.postDragHeadEdit.getText().toString().trim();
        int nLength = StringUtil.strLength(this.mStrTitle);
        if (nLength <= 0) {
            return true;
        }
        if (this.mStrTitle.indexOf("@") >= 0) {
            if (!isHint) {
                return false;
            }
            showThirdTypeToast((int) R.string.title_character_tips);
            return false;
        } else if (Pattern.compile("[^\\u0020-\\u007E\\u00A0-\\u00BE\\u2E80-\\uA4CF\\uF900-\\uFAFF\\uFE30-\\uFE4F\\uFF00-\\uFFEF\\u0080-\\u009F\\u2000-\\u201f]").matcher(this.mStrTitle).find()) {
            if (!isHint) {
                return false;
            }
            showThirdTypeToast((int) R.string.title_expression_tips);
            return false;
        } else if (nLength <= 60) {
            return true;
        } else {
            if (!isHint) {
                return false;
            }
            showThirdTypeToast((int) R.string.beyond_max_hint);
            return false;
        }
    }

    private void checkChangButtonAllowNext() {
        if (this.mHeadBinding.postDragHeadEdit.getText().toString().trim().length() > 0 || this.mList.size() > 0 || (this.mHeadBinding.etDigestText.getText().toString().trim().length() > 0 && checkTitle(false))) {
            ((PostInitActivityBinding) this.mBaseBinding).tvRightTitle.setSelected(true);
            if (this.mList.size() > 0) {
                ((PostInitActivityBinding) this.mBaseBinding).tvRightTitle.setText(R.string.next);
                return;
            } else {
                ((PostInitActivityBinding) this.mBaseBinding).tvRightTitle.setText(R.string.publish);
                return;
            }
        }
        ((PostInitActivityBinding) this.mBaseBinding).tvRightTitle.setSelected(false);
        ((PostInitActivityBinding) this.mBaseBinding).tvRightTitle.setText(R.string.publish);
    }

    private void initCircleText() {
        int size = this.mTopicCircleList.size();
        String[] strCircles = new String[size];
        for (int i = 0; i < size; i++) {
            strCircles[i] = ((CircleInfo) this.mTopicCircleList.get(i)).name;
        }
        if (strCircles.length == 0) {
            ((PostInitActivityBinding) this.mBaseBinding).tvAddTopic.setText(R.string.add_topic);
            ((PostInitActivityBinding) this.mBaseBinding).tvAddTopic.setTextColor(ContextCompat.getColor(this, R.color.color_87_000000));
            return;
        }
        ((PostInitActivityBinding) this.mBaseBinding).tvAddTopic.setText(getFormatTopicText(strCircles));
        ((PostInitActivityBinding) this.mBaseBinding).tvAddTopic.setTextColor(ContextCompat.getColor(this, R.color.color_fb6c06));
    }

    private SpannableStringBuilder getFormatTopicText(String[] strs) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int size = strs.length;
        for (int i = 0; i < size; i++) {
            builder.append(" ");
            builder.setSpan(new ImageSpan(this, R.drawable.topic_orange, 0), builder.length() - 1, builder.length(), 33);
            if (i == size - 1) {
                builder.append(" " + strs[i]);
            } else {
                builder.append(" " + strs[i] + "  ");
            }
        }
        return builder;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AtUserNameEvent event) {
        int curIndex = this.mHeadBinding.etDigestText.getSelectionStart();
        if (this.mInputFilter == null || !this.mInputFilter.mInputFilterAt) {
            this.mHeadBinding.etDigestText.getText().insert(curIndex, "@" + event.name + " ");
            return;
        }
        this.mHeadBinding.etDigestText.getText().insert(curIndex, event.name + " ");
        this.mInputFilter.mInputFilterAt = false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AddCircleEvent event) {
        if (event.type == AddCircleEvent.MULTI_CIRCLE_TYPE && event.list != null) {
            this.mTopicCircleList.clear();
            this.mTopicCircleList.addAll(event.list);
            ((PostInitActivityBinding) this.mBaseBinding).llItemExpend.scrollerToTop();
            initCircleText();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PostSortRefreshEvent event) {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SendPostEmptyCloseEvent event) {
        if (this.mHeadBinding.postDragHeadEdit.getText().toString().trim().length() == 0 && this.mHeadBinding.etDigestText.getText().toString().trim().length() == 0 && this.mList.size() == 0) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AddGoodsEvent event) {
        if (event.list != null) {
            if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 0 && event.listText != null) {
                this.mGoodsServeInfo.contents.clear();
                this.mGoodsServeInfo.contents.addAll(event.listText);
                this.mGoodsServeInfo.price = event.servePrice;
            }
            this.mGoodsIds.clear();
            this.mGoodsIds.addAll(event.list);
            ((PostInitActivityBinding) this.mBaseBinding).llItemExpend.scrollerToTop();
            initBottonText();
        }
    }

    private void initBottonText() {
        if (FengApplication.getInstance().getUserInfo().getLocalOpenShopState() != 1 && FengApplication.getInstance().getUserInfo().getLocalOpenShopState() != 2) {
            if (FengApplication.getInstance().getUserInfo().getLocalSaleType() != 1 && FengApplication.getInstance().getUserInfo().getLocalSaleType() != 0) {
                return;
            }
            if (this.mGoodsIds.size() != 0) {
                StringBuffer buffer = new StringBuffer("“");
                for (CommodityInfo info : this.mGoodsIds) {
                    buffer.append(info.title).append(" ");
                }
                if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 1) {
                    ((PostInitActivityBinding) this.mBaseBinding).tvGoodsNum.setVisibility(0);
                    ((PostInitActivityBinding) this.mBaseBinding).tvAddGoods.setText(buffer.toString());
                    ((PostInitActivityBinding) this.mBaseBinding).tvGoodsNum.setText("“等" + this.mGoodsIds.size() + "个商品");
                    ((PostInitActivityBinding) this.mBaseBinding).tvAddGoods.setTextColor(ContextCompat.getColor(this, R.color.color_fb6c06));
                } else if (this.mGoodsServeInfo.contents.size() > 0) {
                    for (String str : this.mGoodsServeInfo.contents) {
                        buffer.append(str).append(" ");
                    }
                    ((PostInitActivityBinding) this.mBaseBinding).tvServiceNum.setVisibility(0);
                    ((PostInitActivityBinding) this.mBaseBinding).tvAddService.setText(buffer.toString());
                    ((PostInitActivityBinding) this.mBaseBinding).tvServiceNum.setText("“的服务清单");
                    ((PostInitActivityBinding) this.mBaseBinding).tvAddService.setTextColor(ContextCompat.getColor(this, R.color.color_fb6c06));
                }
            } else if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 0) {
                ((PostInitActivityBinding) this.mBaseBinding).tvServiceNum.setVisibility(8);
                ((PostInitActivityBinding) this.mBaseBinding).tvAddService.setText(R.string.serve_list_text);
                ((PostInitActivityBinding) this.mBaseBinding).tvAddService.setTextColor(ContextCompat.getColor(this, R.color.color_87_000000));
            } else {
                ((PostInitActivityBinding) this.mBaseBinding).tvGoodsNum.setVisibility(8);
                ((PostInitActivityBinding) this.mBaseBinding).tvAddGoods.setText(R.string.add_goods);
                ((PostInitActivityBinding) this.mBaseBinding).tvAddGoods.setTextColor(ContextCompat.getColor(this, R.color.color_87_000000));
            }
        }
    }

    private Map<String, Object> getAtMap(String content) {
        List<String> list = new ArrayList();
        Matcher m = Pattern.compile("@[-_a-zA-Z0-9\\u4E00-\\u9FA5]+").matcher(content);
        if (m != null) {
            while (m.find()) {
                String name = m.group();
                name = name.substring(1, name.length());
                if (!list.contains(name)) {
                    list.add(name);
                }
            }
        }
        Map<String, Object> map = new HashMap();
        map.put("atlist", list);
        return map;
    }

    private void sendAddCircleDialog() {
        if (this.mTopicCircleList.size() <= 0) {
            publishPost(this.mIncludeVideo, 1, false);
            return;
        }
        boolean isNoJoin = false;
        for (CircleInfo info : this.mTopicCircleList) {
            if (info.isfans.get() == 0) {
                isNoJoin = true;
                break;
            }
        }
        if (isNoJoin) {
            List<DialogItemEntity> list = new ArrayList();
            list.add(new DialogItemEntity("关注话题", false));
            list.add(new DialogItemEntity("下次再说", false));
            CommonDialog.showCommonDialog(this, "是否关注已选中的话题？", "关注话题能看到更多相关内容", list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    if (position == 0) {
                        PostInitActivity.this.publishPost(PostInitActivity.this.mIncludeVideo, 1, true);
                        MobclickAgent.onEvent(PostInitActivity.this, "forum_add_in");
                    } else if (position == 1) {
                        PostInitActivity.this.publishPost(PostInitActivity.this.mIncludeVideo, 1, false);
                        MobclickAgent.onEvent(PostInitActivity.this, "forum_add_not_in");
                    }
                }
            });
            return;
        }
        publishPost(this.mIncludeVideo, 1, false);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50003) {
            try {
                int length = grantResults.length;
                int i = 0;
                while (i < length) {
                    if (grantResults[i] == 0) {
                        i++;
                    } else {
                        return;
                    }
                }
                sendVideo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onChangeDescribe(String describe) {
        changeTextNum(describe, d_ResultType.SHORT_URL);
    }

    public void uncaughtException(Thread thread, final Throwable ex) {
        ex.printStackTrace();
        saveToDrafts();
        runOnUiThread(new Runnable() {
            public void run() {
                FengApplication.getInstance().upLoadLog(true, ex.getMessage());
            }
        });
    }
}
