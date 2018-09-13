package com.feng.car.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import anet.channel.util.ErrorConstant;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.EmptyAdapter;
import com.feng.car.adapter.OpenStoreAddPicAdapter;
import com.feng.car.adapter.OpenStoreAddPicAdapter.MicroMediaOperationListener;
import com.feng.car.databinding.ActivityOpenStoreBinding;
import com.feng.car.databinding.ActivityOpenStoreHeaderBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.dealer.ShopRegisterInfo;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.event.IDCardUploadFinishEvent;
import com.feng.car.event.ImageOrVideoPathEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.operation.ThirdLoginOperation;
import com.feng.car.recyclerdrag.MyItemTouchCallback;
import com.feng.car.recyclerdrag.MyItemTouchCallback$OnDragListener;
import com.feng.car.recyclerdrag.OnRecyclerItemClickListener;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.UploadSingleImage;
import com.feng.car.utils.UploadSingleImage$OnUploadResult;
import com.feng.car.utils.WXUtils;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.FileUtil;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterStoreActivity extends BaseActivity<ActivityOpenStoreBinding> implements MyItemTouchCallback$OnDragListener, MicroMediaOperationListener {
    private final int CHANGE_TIME = 2;
    private final int RESET_TIME = 1;
    private final int START_TIME = 0;
    private ItemTouchHelper itemTouchHelper;
    private OpenStoreAddPicAdapter mAdapter;
    private int mChangePicPosition;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    RegisterStoreActivity.this.mTime = 60;
                    RegisterStoreActivity.this.startTime();
                    sendEmptyMessage(2);
                    return;
                case 1:
                    RegisterStoreActivity.this.mTime = 60;
                    RegisterStoreActivity.this.resetVerifyText(RegisterStoreActivity.this.getString(R.string.send_code));
                    RegisterStoreActivity.this.mHandler.removeCallbacksAndMessages(null);
                    return;
                case 2:
                    if (RegisterStoreActivity.this.mTime <= 0) {
                        RegisterStoreActivity.this.resetVerifyText(RegisterStoreActivity.this.getString(R.string.again_send));
                        return;
                    }
                    RegisterStoreActivity.this.mHeaderBind.tvLoginSendVerify.setText(RegisterStoreActivity.this.getString(R.string.resendLeftTime, new Object[]{Integer.valueOf(RegisterStoreActivity.this.mTime)}));
                    RegisterStoreActivity.this.mTime = RegisterStoreActivity.this.mTime - 1;
                    sendEmptyMessageDelayed(2, 1000);
                    return;
                default:
                    return;
            }
        }
    };
    private ActivityOpenStoreHeaderBinding mHeaderBind;
    private boolean mIsQr = false;
    private boolean mIsSubmit = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ThirdLoginOperation mOperation;
    private ShopRegisterInfo mShopRegisterInfo;
    private int mTime = 60;
    private UploadSingleImage mUploadImage;
    private List<PostEdit> mUploadImgList;
    private PostEdit positionPostEdit = new PostEdit(-1, "");

    private class GridSpacingItemDecoration extends ItemDecoration {
        private GridSpacingItemDecoration() {
        }

        /* synthetic */ GridSpacingItemDecoration(RegisterStoreActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position == 0 || position == 3 || position == 6) {
                outRect.left = RegisterStoreActivity.this.mResources.getDimensionPixelSize(R.dimen.default_32PX);
            } else if (position == 2 || position == 5 || position == 8) {
                outRect.right = RegisterStoreActivity.this.mResources.getDimensionPixelSize(R.dimen.default_32PX);
            } else if (position == 1 || position == 4 || position == 7) {
                outRect.left = RegisterStoreActivity.this.mResources.getDimensionPixelSize(R.dimen.default_22PX);
                outRect.right = RegisterStoreActivity.this.mResources.getDimensionPixelSize(R.dimen.default_22PX);
            }
            outRect.top = RegisterStoreActivity.this.mResources.getDimensionPixelSize(R.dimen.default_22PX);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        FengConstant.UPLOAD_IMAGE_COUNT = 0;
    }

    public int setBaseContentView() {
        return R.layout.activity_open_store;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.open_store);
        String json = SharedUtil.getString(this, "register_store_json");
        if (!TextUtils.isEmpty(json)) {
            this.mShopRegisterInfo = (ShopRegisterInfo) JsonUtil.fromJson(json, ShopRegisterInfo.class);
        }
        if (this.mShopRegisterInfo == null) {
            this.mShopRegisterInfo = new ShopRegisterInfo();
        }
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(new EmptyAdapter(this));
        ((ActivityOpenStoreBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityOpenStoreBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        this.mOperation = new ThirdLoginOperation();
        addHead();
        this.mUploadImage = new UploadSingleImage(this, new UploadSingleImage$OnUploadResult() {
            public void onStart() {
            }

            public void onResult(HashMap<String, String> imageMap) {
                RegisterStoreActivity.this.initImage(imageMap);
            }

            public void onFailure() {
                RegisterStoreActivity.this.mIsQr = false;
            }
        });
    }

    private void restoreData() {
        int i;
        if (this.mShopRegisterInfo.imagelist.size() > 0) {
            int size = this.mShopRegisterInfo.imagelist.size();
            int i2 = 0;
            while (i2 < size) {
                if (((PostEdit) this.mShopRegisterInfo.imagelist.get(i2)).getType() == -1) {
                    this.mShopRegisterInfo.imagelist.remove(i2);
                    i2--;
                    size--;
                }
                i2++;
            }
        }
        if (this.mShopRegisterInfo.imagelist.size() < 9) {
            this.mShopRegisterInfo.imagelist.add(this.positionPostEdit);
        }
        this.mAdapter.notifyDataSetChanged();
        if (this.mShopRegisterInfo.getSex() == 1) {
            this.mHeaderBind.rbStoreMale.setChecked(true);
        } else {
            this.mHeaderBind.rbStoreFemale.setChecked(true);
        }
        if (this.mShopRegisterInfo.getSaletype() == -1) {
            this.mHeaderBind.tvSaleType.setText("请选择");
            this.mHeaderBind.tvSaleType.setTextColor(ContextCompat.getColor(this, R.color.color_20_000000));
        } else {
            this.mHeaderBind.tvSaleType.setText(this.mShopRegisterInfo.getSaletype() == 0 ? "服务商" : "车商");
            this.mHeaderBind.tvSaleType.setTextColor(ContextCompat.getColor(this, R.color.color_87_000000));
        }
        if (!(this.mShopRegisterInfo.idcardimagejson1 == null || this.mShopRegisterInfo.idcardimagejson2 == null || this.mShopRegisterInfo.idcardimagejson3 == null)) {
            this.mHeaderBind.ivIdCardDoneIcon.setVisibility(0);
        }
        ImageView imageView = this.mHeaderBind.ivQrDoneIcon;
        if (this.mShopRegisterInfo.saleimg == null) {
            i = 8;
        } else {
            i = 0;
        }
        imageView.setVisibility(i);
        this.mShopRegisterInfo.setLocalVerify("");
    }

    private void processRegisterInfo() {
        this.mUploadImgList = new ArrayList();
        for (PostEdit uploadImg : this.mShopRegisterInfo.imagelist) {
            if (uploadImg.getType() != -1) {
                this.mUploadImgList.add(uploadImg);
            }
        }
    }

    private void submitStore() {
        processRegisterInfo();
        Map<String, Object> map = new HashMap();
        map.put("idcardimagejson1", this.mShopRegisterInfo.idcardimagejson1);
        map.put("idcardimagejson2", this.mShopRegisterInfo.idcardimagejson2);
        map.put("idcardimagejson3", this.mShopRegisterInfo.idcardimagejson3);
        map.put("realname", this.mShopRegisterInfo.getRealname());
        map.put("sex", String.valueOf(this.mShopRegisterInfo.getSex()));
        map.put("mobile", this.mShopRegisterInfo.getMobile());
        map.put("identifyingcode", this.mShopRegisterInfo.getLocalVerify());
        map.put("shopname", this.mShopRegisterInfo.getShopname());
        map.put("shopaddress", this.mShopRegisterInfo.getShopaddress());
        map.put("position", this.mShopRegisterInfo.getPosition());
        map.put("description", this.mShopRegisterInfo.getDescription());
        map.put("saletype", String.valueOf(this.mShopRegisterInfo.getSaletype()));
        map.put("imagelist", this.mUploadImgList);
        map.put("saleimg", this.mShopRegisterInfo.saleimg);
        FengApplication.getInstance().httpRequest("shop/add/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                RegisterStoreActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                RegisterStoreActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        RegisterStoreActivity.this.mIsSubmit = true;
                        FengApplication.getInstance().getUserInfo().setLocalOpenShopState(RegisterStoreActivity.this, 0);
                        WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_ADD_SERVICE_PATH, new Object[0]));
                        RegisterStoreActivity.this.finish();
                    } else if (code == -2) {
                        RegisterStoreActivity.this.showSecondTypeToast("验证码错误!");
                    } else if (code == ErrorConstant.ERROR_NO_NETWORK) {
                        RegisterStoreActivity.this.showThirdTypeToast("商铺已经存在或正在审核中");
                    } else {
                        FengApplication.getInstance().checkCode("shop/add/", code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("shop/add/", content, e);
                }
            }
        });
    }

    private void addHead() {
        ((ActivityOpenStoreBinding) this.mBaseBinding).recyclerview.setPullRefreshEnabled(false);
        LayoutParams params = new LayoutParams(-1, -2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setItemPrefetchEnabled(false);
        this.mAdapter = new OpenStoreAddPicAdapter(this, this.mShopRegisterInfo.imagelist);
        this.mAdapter.setOperationListener(this);
        this.mHeaderBind = ActivityOpenStoreHeaderBinding.inflate(this.mInflater);
        this.mHeaderBind.setShopRegisterInfo(this.mShopRegisterInfo);
        this.mHeaderBind.getRoot().setLayoutParams(params);
        this.mHeaderBind.recyclerView.setAdapter(this.mAdapter);
        this.mHeaderBind.recyclerView.setLayoutManager(gridLayoutManager);
        this.mHeaderBind.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(this.mHeaderBind.recyclerView) {
            public void onLongClick(ViewHolder vh) {
                try {
                    if (((PostEdit) RegisterStoreActivity.this.mShopRegisterInfo.imagelist.get(vh.getAdapterPosition())).getType() == 2) {
                        RegisterStoreActivity.this.itemTouchHelper.startDrag(vh);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(this.mAdapter).setOnDragListener(this));
        this.itemTouchHelper.attachToRecyclerView(this.mHeaderBind.recyclerView);
        this.mHeaderBind.recyclerView.addItemDecoration(new GridSpacingItemDecoration(this, null));
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderBind.getRoot());
        this.mHeaderBind.rgStoreGender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RegisterStoreActivity.this.mShopRegisterInfo.setSex(checkedId == R.id.rb_store_male ? 1 : 2);
            }
        });
        this.mHeaderBind.tvLoginSendVerify.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                RegisterStoreActivity.this.sendCode();
            }
        });
        this.mHeaderBind.rlSaleType.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                List<DialogItemEntity> list = new ArrayList();
                list.add(new DialogItemEntity("车商", false));
                list.add(new DialogItemEntity("服务商", false));
                CommonDialog.showCommonDialog(RegisterStoreActivity.this, "", "", list, new OnDialogItemClickListener() {
                    public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                        if (position == 0) {
                            RegisterStoreActivity.this.mHeaderBind.tvSaleType.setText("车商");
                            RegisterStoreActivity.this.mShopRegisterInfo.setSaletype(1);
                            RegisterStoreActivity.this.mHeaderBind.tvSaleType.setTextColor(ContextCompat.getColor(RegisterStoreActivity.this, R.color.color_87_000000));
                        } else if (position == 1) {
                            RegisterStoreActivity.this.mHeaderBind.tvSaleType.setText("服务商");
                            RegisterStoreActivity.this.mShopRegisterInfo.setSaletype(0);
                            RegisterStoreActivity.this.mHeaderBind.tvSaleType.setTextColor(ContextCompat.getColor(RegisterStoreActivity.this, R.color.color_87_000000));
                        }
                    }
                });
            }
        });
        this.mHeaderBind.rlUploadIdCard.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                RegisterStoreActivity.this.startActivity(new Intent(RegisterStoreActivity.this, UpLoadIDCardActivity.class));
            }
        });
        this.mHeaderBind.rlUploadQr.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                RegisterStoreActivity.this.mChangePicPosition = -1;
                Intent intent = new Intent(RegisterStoreActivity.this, SelectPhotoActivity.class);
                intent.putExtra("feng_type", 1);
                RegisterStoreActivity.this.startActivityForResult(intent, 20);
            }
        });
        this.mHeaderBind.btDone.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                RegisterStoreActivity.this.submitStore();
            }
        });
        restoreData();
    }

    private void sendCode() {
        if (!TextUtils.isEmpty(this.mHeaderBind.etPhoneNumber.getText().toString())) {
            this.mOperation.sendCodeOperation(this, this.mHeaderBind.etPhoneNumber.getText().toString(), 8, "", new SuccessFailCallback() {
                public void onStart() {
                    RegisterStoreActivity.this.mHandler.sendEmptyMessage(0);
                }

                public void onFail() {
                    RegisterStoreActivity.this.mHandler.sendEmptyMessage(1);
                }
            });
        }
    }

    private void resetVerifyText(String strText) {
        this.mHeaderBind.tvLoginSendVerify.setEnabled(true);
        this.mHeaderBind.tvLoginSendVerify.setText(strText);
        this.mHeaderBind.tvLoginSendVerify.setBackgroundResource(R.drawable.border_404040_pressed_191919_4dp);
        this.mHeaderBind.tvLoginSendVerify.setTextColor(this.mResources.getColorStateList(R.color.selector_404040_pressed_191919));
    }

    private void startTime() {
        this.mHeaderBind.tvLoginSendVerify.setEnabled(false);
        this.mHeaderBind.tvLoginSendVerify.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        this.mHeaderBind.tvLoginSendVerify.setBackgroundResource(R.drawable.bg_border_ffffff_black38_4dp);
    }

    private void initImage(Map<String, String> map) {
        PostEdit edit = new PostEdit(2, "");
        edit.setHeight((String) map.get("height"));
        edit.setWidth((String) map.get("width"));
        edit.srcUrl = HttpConstant.QINIUIMAGEBASEPATH + ((String) map.get("hash"));
        edit.setMime((String) map.get("mime"));
        edit.setHash((String) map.get("hash"));
        edit.setUrl((String) map.get("url"));
        edit.setSize((String) map.get("size"));
        if (this.mIsQr) {
            this.mHeaderBind.ivQrDoneIcon.setVisibility(0);
            this.mShopRegisterInfo.saleimg = edit;
            this.mIsQr = false;
        } else {
            this.mShopRegisterInfo.imagelist.remove(this.positionPostEdit);
            if (this.mChangePicPosition == -1 || this.mChangePicPosition >= this.mShopRegisterInfo.imagelist.size()) {
                this.mShopRegisterInfo.imagelist.add(edit);
            } else {
                this.mShopRegisterInfo.imagelist.remove(this.mChangePicPosition);
                this.mShopRegisterInfo.imagelist.add(this.mChangePicPosition, edit);
            }
            if (this.mShopRegisterInfo.imagelist.size() < 9) {
                this.mShopRegisterInfo.imagelist.add(this.positionPostEdit);
            }
            this.mAdapter.notifyDataSetChanged();
        }
        if (this.mShopRegisterInfo != null) {
            this.mShopRegisterInfo.check();
        }
    }

    public void onFinishDrag() {
    }

    public void onOption(final int imagePosition) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("更换图片", false));
        list.add(new DialogItemEntity("删除图片", true));
        CommonDialog.showCommonDialog(this, "", "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    RegisterStoreActivity.this.onAdd(true);
                    RegisterStoreActivity.this.mChangePicPosition = imagePosition;
                } else if (position == 1) {
                    FengConstant.UPLOAD_IMAGE_COUNT--;
                    RegisterStoreActivity.this.mShopRegisterInfo.imagelist.remove(imagePosition);
                    if (RegisterStoreActivity.this.mShopRegisterInfo.imagelist.size() < 9) {
                        RegisterStoreActivity.this.mShopRegisterInfo.imagelist.remove(RegisterStoreActivity.this.positionPostEdit);
                        RegisterStoreActivity.this.mShopRegisterInfo.imagelist.add(RegisterStoreActivity.this.positionPostEdit);
                    }
                    RegisterStoreActivity.this.mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void onAdd() {
        onAdd(false);
    }

    private void onAdd(boolean iSChange) {
        if (!iSChange) {
            this.mChangePicPosition = -1;
        }
        Intent intent = new Intent(this, SelectPhotoActivity.class);
        intent.putExtra("feng_type", 3);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20 && data != null && data.hasExtra("imagepath")) {
            String imagePath = data.getStringExtra("imagepath");
            if (FileUtil.isFileExists(imagePath)) {
                this.mIsQr = true;
                this.mUploadImage.startUpload(imagePath);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveImageOrVideoPath(ImageOrVideoPathEvent pathEvent) {
        if (pathEvent.type == 20 && pathEvent.list != null) {
            this.mHeaderBind.llParent.setKeepScreenOn(true);
            this.mUploadImage.startUpload(((UploadQiNiuLocalPathInfo) pathEvent.list.get(0)).path);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(IDCardUploadFinishEvent event) {
        this.mShopRegisterInfo.idcardimagejson1 = (PostEdit) event.postEdits.get(Integer.valueOf(1));
        this.mShopRegisterInfo.idcardimagejson2 = (PostEdit) event.postEdits.get(Integer.valueOf(2));
        this.mShopRegisterInfo.idcardimagejson3 = (PostEdit) event.postEdits.get(Integer.valueOf(3));
        this.mHeaderBind.ivIdCardDoneIcon.setVisibility(0);
        this.mShopRegisterInfo.check();
    }

    protected void onPause() {
        super.onPause();
        if (this.mIsSubmit) {
            SharedUtil.putString(this, "register_store_json", "");
        } else {
            SharedUtil.putString(this, "register_store_json", JsonUtil.toJson(this.mShopRegisterInfo));
        }
    }
}
