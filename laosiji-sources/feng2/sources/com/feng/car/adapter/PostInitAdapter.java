package com.feng.car.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.feng.car.R;
import com.feng.car.activity.CameraPreviewActivity;
import com.feng.car.activity.ShortVideoSelectImageActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.PostInitItemBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.entity.sendpost.SoftKeyOpen;
import com.feng.car.listener.PostDescribeChangeListener;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.ImageUtil;
import com.feng.car.video.player.JCUtils;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.recyclerview.ItemTouchCallback$ItemTouchAdapter;
import com.feng.library.emoticons.keyboard.EmojiBean;
import com.feng.library.emoticons.keyboard.SimpleCommonUtils;
import com.feng.library.emoticons.keyboard.XhsEmoticonsKeyBoard;
import com.feng.library.emoticons.keyboard.data.EmoticonEntity;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonClickListener;
import com.feng.library.emoticons.keyboard.widget.UserDefEmoticonsEditText;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostInitAdapter extends Adapter<ViewHolder> implements ItemTouchCallback$ItemTouchAdapter {
    public int index = -1;
    private int m120;
    private int m32;
    private PostDescribeChangeListener mChangeListener;
    private Context mContext;
    private Handler mHandler;
    private View mHeadView;
    private ImageInfo mImageInfo;
    private XhsEmoticonsKeyBoard mKbDefEmoticons;
    private List<PostEdit> mList;
    public SoftKeyOpen mOpen = new SoftKeyOpen();
    private LayoutParams mPcVideoParams;
    public float mTouchX = 0.0f;
    public float mTouchY = 0.0f;
    private int mViewHight;
    private int mWidth;

    class DescribeOnClickListener extends OnSingleClickListener {
        private UserDefEmoticonsEditText mEditText;
        private int mType = 0;

        public DescribeOnClickListener(UserDefEmoticonsEditText editText, int type) {
            this.mEditText = editText;
            this.mType = type;
        }

        public void onSingleClick(View v) {
            this.mEditText.setFocusable(true);
            this.mEditText.setFocusableInTouchMode(true);
            if (!PostInitAdapter.this.mOpen.isOpenSoftKeyboard()) {
                if (this.mType == 2) {
                    FengUtil.showSoftInput(PostInitAdapter.this.mContext);
                }
                this.mEditText.postDelayed(new Runnable() {
                    public void run() {
                        DescribeOnClickListener.this.mEditText.setFocusable(true);
                        DescribeOnClickListener.this.mEditText.setFocusableInTouchMode(true);
                        DescribeOnClickListener.this.mEditText.requestFocus();
                        PostInitAdapter.this.mKbDefEmoticons.setInputEditText(DescribeOnClickListener.this.mEditText);
                        PostInitAdapter.this.mKbDefEmoticons.setAdapter(SimpleCommonUtils.getCommonAdapter(PostInitAdapter.this.mContext, new emoticonClickListener(DescribeOnClickListener.this.mEditText)));
                        if (PostInitAdapter.this.mChangeListener != null) {
                            PostInitAdapter.this.mChangeListener.onChangeDescribe(DescribeOnClickListener.this.mEditText.getText().toString());
                        }
                        if (DescribeOnClickListener.this.mType == 1) {
                            FengUtil.showSoftInput(PostInitAdapter.this.mContext);
                        }
                    }
                }, 500);
            }
        }
    }

    class EditIconOnClickListener extends OnSingleClickListener {
        private ImageView mEditIcon;
        private UserDefEmoticonsEditText mEditText;
        private int mPos;

        public EditIconOnClickListener(ImageView imageView, UserDefEmoticonsEditText editText, int position) {
            this.mEditText = editText;
            this.mPos = position;
            this.mEditIcon = imageView;
        }

        public void onSingleClick(View v) {
            if (this.mEditText.getTag() != null && Integer.parseInt(this.mEditText.getTag().toString().trim()) == this.mPos && this.mEditIcon.getTag() != null) {
                if (this.mEditIcon.getTag().toString().equals("0")) {
                    this.mEditText.clearFocus();
                    PostInitAdapter.this.index = this.mPos;
                    ((PostEdit) PostInitAdapter.this.mList.get(this.mPos)).isRequestFocus = true;
                    ((PostEdit) PostInitAdapter.this.mList.get(this.mPos)).setShowDescribe(true);
                    if (!PostInitAdapter.this.mOpen.isOpenSoftKeyboard()) {
                        FengUtil.showSoftInput(PostInitAdapter.this.mContext);
                        this.mEditText.postDelayed(new Runnable() {
                            public void run() {
                                EditIconOnClickListener.this.mEditText.setFocusable(true);
                                EditIconOnClickListener.this.mEditText.setFocusableInTouchMode(true);
                                EditIconOnClickListener.this.mEditText.requestFocus();
                                PostInitAdapter.this.mKbDefEmoticons.setInputEditText(EditIconOnClickListener.this.mEditText);
                                PostInitAdapter.this.mKbDefEmoticons.setAdapter(SimpleCommonUtils.getCommonAdapter(PostInitAdapter.this.mContext, new emoticonClickListener(EditIconOnClickListener.this.mEditText)));
                            }
                        }, 500);
                    }
                } else if (((PostEdit) PostInitAdapter.this.mList.get(this.mPos)).getDescription().toString().trim().length() > 0) {
                    List<DialogItemEntity> list = new ArrayList();
                    list.add(new DialogItemEntity("删除注释", true));
                    CommonDialog.showCommonDialog(PostInitAdapter.this.mContext, "", list, new OnDialogItemClickListener() {
                        public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                            EditIconOnClickListener.this.mEditText.setText("");
                            EditIconOnClickListener.this.mEditText.setVisibility(8);
                            ((PostEdit) PostInitAdapter.this.mList.get(EditIconOnClickListener.this.mPos)).setShowDescribe(false);
                            ((PostEdit) PostInitAdapter.this.mList.get(EditIconOnClickListener.this.mPos)).setDescription("");
                        }
                    });
                } else {
                    ((PostEdit) PostInitAdapter.this.mList.get(this.mPos)).setDescription("");
                    ((PostEdit) PostInitAdapter.this.mList.get(this.mPos)).setShowDescribe(false);
                }
            }
        }
    }

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public PostInitItemBinding binding;

        public ViewHolder(View view) {
            super(view);
        }

        public ViewHolder(PostInitItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(PostEdit edit1) {
            this.binding.setEdit1(edit1);
            this.binding.executePendingBindings();
        }
    }

    public class emoticonClickListener implements EmoticonClickListener {
        private UserDefEmoticonsEditText et_text;

        public emoticonClickListener(UserDefEmoticonsEditText et_text) {
            this.et_text = et_text;
        }

        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
            if (isDelBtn) {
                SimpleCommonUtils.delClick(this.et_text);
            } else if (o != null) {
                String content = null;
                if (o instanceof EmojiBean) {
                    content = ((EmojiBean) o).emoji;
                } else if (o instanceof EmoticonEntity) {
                    content = ((EmoticonEntity) o).getContent();
                }
                if (!TextUtils.isEmpty(content)) {
                    this.et_text.getText().insert(this.et_text.getSelectionStart(), content);
                }
            }
        }
    }

    public PostInitAdapter(Context context, List<PostEdit> list, XhsEmoticonsKeyBoard kbDefEmoticons, Handler handler, PostDescribeChangeListener changeListener) {
        this.mContext = context;
        this.mList = list;
        this.mOpen.setOpenSoftKeyboard(false);
        Resources resources = this.mContext.getResources();
        this.mWidth = resources.getDisplayMetrics().widthPixels;
        this.mViewHight = (int) (((float) (this.mWidth * 375)) / 720.0f);
        this.m32 = resources.getDimensionPixelSize(R.dimen.default_32PX);
        this.m120 = resources.getDimensionPixelSize(R.dimen.default_120PX);
        this.mKbDefEmoticons = kbDefEmoticons;
        this.mHandler = handler;
        this.mChangeListener = changeListener;
        this.mPcVideoParams = new LayoutParams(-1, this.mViewHight);
        setHasStableIds(true);
    }

    public void setHeadVeiw(View view) {
        this.mHeadView = view;
    }

    public long getItemId(int position) {
        if (this.mHeadView == null || position != 0) {
            return (long) ((PostEdit) this.mList.get(position - 1)).hashCode();
        }
        return 0;
    }

    public int getItemViewType(int position) {
        if (this.mHeadView == null || position != 0) {
            return 0;
        }
        return 1;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ViewHolder(this.mHeadView);
        }
        return new ViewHolder(PostInitItemBinding.inflate(LayoutInflater.from(this.mContext)));
    }

    public void onBindViewHolder(final ViewHolder holder, int position1) {
        if (getItemViewType(position1) != 1) {
            final int position = position1 - 1;
            final PostEdit postEdit = (PostEdit) this.mList.get(position);
            holder.bindTo(postEdit);
            holder.binding.ivDel.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    Message message = Message.obtain();
                    message.arg1 = position;
                    message.what = 10;
                    PostInitAdapter.this.mHandler.sendMessage(message);
                }
            });
            holder.binding.setSoftOpen(this.mOpen);
            holder.binding.getRoot().setTag(postEdit);
            holder.binding.llVideoPlay.setVisibility(8);
            holder.binding.tvVideoTime.setVisibility(8);
            holder.binding.tvVideoHint.setVisibility(8);
            holder.binding.ivVideo.setVisibility(8);
            if (this.mList.size() == 1 && TextUtils.isEmpty(postEdit.getDescription())) {
                holder.binding.etDescribe.setVisibility(8);
            } else {
                holder.binding.etDescribe.setVisibility(0);
            }
            String imageUrl;
            switch (postEdit.getType()) {
                case 2:
                    postEdit.setDescription(postEdit.getDescription().replaceAll("\\s*$", ""));
                    holder.binding.etDescribe.setOnFocusChangeListener(new OnFocusChangeListener() {
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                PostInitAdapter.this.index = position;
                                postEdit.setShowIcon(false);
                                return;
                            }
                            postEdit.setShowIcon(true);
                            postEdit.setShowDescribe(false);
                            holder.binding.etDescribe.setFocusable(false);
                            holder.binding.etDescribe.setFocusableInTouchMode(false);
                            PostInitAdapter.this.index = -1;
                        }
                    });
                    holder.binding.etDescribe.setTag(Integer.valueOf(position));
                    holder.binding.etDescribe.setOnClickListener(new DescribeOnClickListener(holder.binding.etDescribe, 2));
                    this.mImageInfo = new ImageInfo();
                    this.mImageInfo.width = Integer.parseInt(postEdit.getWidth());
                    this.mImageInfo.height = Integer.parseInt(postEdit.getHeight());
                    this.mImageInfo.url = postEdit.srcUrl;
                    imageUrl = FengUtil.getUniformScaleUrl(this.mImageInfo, 640);
                    if (FengUtil.isLongImage(this.mImageInfo)) {
                        holder.binding.ivPost.setVisibility(8);
                        holder.binding.longImage.setVisibility(0);
                        holder.binding.longImage.setLayoutParams(getParamsImage(postEdit));
                        FengUtil.downLoadImageToLargeImageView(this.mContext, imageUrl, holder.binding.longImage);
                        holder.binding.longImage.setEnableZoom(false);
                        holder.binding.longImage.setTag(imageUrl);
                        holder.binding.ivPost.setBackgroundResource(R.color.color_4a4c4e);
                    } else {
                        holder.binding.ivPost.setVisibility(0);
                        holder.binding.longImage.setVisibility(8);
                        holder.binding.ivPost.setLayoutParams(getParamsImage(postEdit));
                        holder.binding.ivPost.setBackgroundResource(R.color.color_4a4c4e);
                        holder.binding.ivPost.setAutoImageURI(Uri.parse(imageUrl));
                    }
                    holder.binding.ivPost.setTag(imageUrl);
                    return;
                case 3:
                    holder.binding.longImage.setVisibility(8);
                    holder.binding.ivPost.setVisibility(0);
                    holder.binding.tvVideoTime.setVisibility(0);
                    holder.binding.tvVideoTime.setText(JCUtils.stringForTime(postEdit.getTime()));
                    if (TextUtils.isEmpty(postEdit.videoCoverImage.getHash())) {
                        holder.binding.ivVideo.setVisibility(0);
                        holder.binding.ivPost.setVisibility(8);
                        holder.binding.ivVideo.setLayoutParams(this.mPcVideoParams);
                        holder.binding.ivVideo.setScaleType(ScaleType.CENTER_CROP);
                        holder.binding.ivVideo.setBackgroundResource(R.drawable.new_post_video);
                        holder.binding.ivPost.setTag(postEdit.videoCoverImage.srcUrl);
                    } else {
                        this.mImageInfo = new ImageInfo();
                        this.mImageInfo.width = Integer.parseInt(postEdit.videoCoverImage.getWidth());
                        this.mImageInfo.height = Integer.parseInt(postEdit.videoCoverImage.getHeight());
                        this.mImageInfo.url = postEdit.videoCoverImage.srcUrl;
                        imageUrl = FengUtil.getUniformScaleUrl(this.mImageInfo, 640);
                        holder.binding.ivPost.setLayoutParams(getParamsImage(postEdit.videoCoverImage));
                        holder.binding.ivPost.setBackgroundResource(R.color.color_4a4c4e);
                        holder.binding.ivPost.setAutoImageURI(Uri.parse(imageUrl));
                        holder.binding.ivPost.setTag(imageUrl);
                    }
                    if (postEdit.localVideo) {
                        holder.binding.llVideoPlay.setVisibility(0);
                        holder.binding.tvVideoHint.setVisibility(8);
                        holder.binding.ivVideoPlay.setOnClickListener(new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                Intent intent = new Intent(PostInitAdapter.this.mContext, CameraPreviewActivity.class);
                                intent.putExtra(CameraPreviewActivity.TYPE_KEY, 1);
                                intent.putExtra(CameraPreviewActivity.PATH, postEdit.srcUrl);
                                intent.putExtra(CameraPreviewActivity.POST_LOCAL_VIDEO, true);
                                PostInitAdapter.this.mContext.startActivity(intent);
                            }
                        });
                        holder.binding.ivPost.setOnClickListener(new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                Intent intent = new Intent(PostInitAdapter.this.mContext, ShortVideoSelectImageActivity.class);
                                intent.putExtra("key_video_editer_path", postEdit.srcUrl);
                                intent.putExtra(ShortVideoSelectImageActivity.EDIT_COVER_FLAG, true);
                                PostInitAdapter.this.mContext.startActivity(intent);
                            }
                        });
                    } else {
                        holder.binding.tvVideoHint.setVisibility(0);
                        holder.binding.llVideoPlay.setVisibility(8);
                    }
                    holder.binding.etDescribe.setOnFocusChangeListener(new OnFocusChangeListener() {
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                PostInitAdapter.this.index = position;
                                postEdit.setShowIcon(false);
                                return;
                            }
                            postEdit.setShowIcon(true);
                            postEdit.setShowDescribe(false);
                            holder.binding.etDescribe.setFocusable(false);
                            holder.binding.etDescribe.setFocusableInTouchMode(false);
                            PostInitAdapter.this.index = -1;
                        }
                    });
                    holder.binding.etDescribe.setTag(Integer.valueOf(position));
                    holder.binding.etDescribe.setOnClickListener(new DescribeOnClickListener(holder.binding.etDescribe, 2));
                    holder.binding.ivVideoPlay.setTag(postEdit.srcUrl);
                    return;
                default:
                    return;
            }
        }
    }

    private LayoutParams getParamsImage(PostEdit postEdit) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.width = Integer.parseInt(postEdit.getWidth());
        imageInfo.height = Integer.parseInt(postEdit.getHeight());
        imageInfo.url = postEdit.srcUrl;
        if (imageInfo.width == 0 || imageInfo.height == 0) {
            return new LayoutParams(-1, -2);
        }
        return new LayoutParams(-1, FengUtil.getViewHeight(imageInfo, this.mWidth));
    }

    public int getItemCount() {
        if (this.mHeadView != null) {
            return this.mList.size() + 1;
        }
        return this.mList.size();
    }

    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition != 0 && toPosition != 0) {
            int from = fromPosition - 1;
            int to = toPosition - 1;
            int i;
            if (from < to) {
                for (i = from; i < to; i++) {
                    Collections.swap(this.mList, i, i + 1);
                }
            } else {
                for (i = from; i > to; i--) {
                    Collections.swap(this.mList, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    public void onSwiped(int position) {
    }

    public void onSelectedChanged(android.support.v7.widget.RecyclerView.ViewHolder viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (holder != null && holder.binding.getRoot().getTag() != null && (holder.binding.getRoot().getTag() instanceof PostEdit)) {
            PostEdit edit = (PostEdit) holder.binding.getRoot().getTag();
            holder.itemView.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.transparent));
            Bitmap floatBitmap = boxBlurFilter(holder.binding.ivPost.getTag().toString(), this.mWidth, this.m120, 0);
            holder.binding.llPicVideoParent.setVisibility(4);
            holder.binding.llDescribe.setVisibility(4);
            holder.binding.ivDragImage.setVisibility(0);
            if (edit.getType() == 2) {
                if (floatBitmap == null) {
                    holder.binding.ivDragImage.setImageResource(R.drawable.default_post_32);
                } else {
                    holder.binding.ivDragImage.setScaleType(ScaleType.FIT_XY);
                    holder.binding.ivDragImage.setImageBitmap(floatBitmap);
                }
            } else if (edit.getType() == 3) {
                if (floatBitmap == null) {
                    holder.binding.ivDragImage.setImageResource(R.drawable.video_drag);
                } else {
                    holder.binding.ivDragImage.setScaleType(ScaleType.FIT_XY);
                    holder.binding.ivDragImage.setImageBitmap(floatBitmap);
                }
            }
            holder.binding.ivDel.setVisibility(8);
            holder.binding.llVideoPlay.setVisibility(8);
            holder.binding.tvVideoHint.setVisibility(8);
            holder.binding.tvVideoTime.setVisibility(8);
            animatorTranslation(holder, holder.binding.ivDragImage);
        }
    }

    private void animatorTranslation(ViewHolder holder, View view) {
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(view, "translationY", new float[]{0.0f, this.mTouchY - ((float) (this.m120 / 2))});
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();
    }

    public void clearView(android.support.v7.widget.RecyclerView.ViewHolder viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (holder != null) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.color_ffffff));
            PostEdit edit = (PostEdit) holder.binding.getRoot().getTag();
            if (edit.getType() != 2 && edit.getType() == 3) {
                if (edit.localVideo) {
                    holder.binding.llVideoPlay.setVisibility(0);
                } else {
                    holder.binding.tvVideoHint.setVisibility(0);
                }
                holder.binding.tvVideoTime.setVisibility(0);
            }
            holder.binding.ivDel.setVisibility(0);
            holder.binding.llPicVideoParent.setVisibility(0);
            holder.binding.llDescribe.setVisibility(0);
            holder.binding.ivDragImage.setVisibility(8);
            holder.itemView.clearAnimation();
        }
        notifyDataSetChanged();
    }

    private Bitmap boxBlurFilter(String path, int width, int hight, int tt) {
        Bitmap b = null;
        BinaryResource resource = ImagePipelineFactory.getInstance().getMainDiskStorageCache().getResource(DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(Uri.parse(path)), null));
        try {
            b = BitmapFactory.decodeByteArray(resource.read(), 0, resource.read().length);
            width = b.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (b == null) {
            return null;
        }
        Bitmap overlay = Bitmap.createBitmap((int) (((float) width) / 8.0f), (int) (((float) hight) / 8.0f), Config.RGB_565);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(((float) (-tt)) / 8.0f, ((float) (-tt)) / 8.0f);
        canvas.scale(1.0f / 8.0f, 1.0f / 8.0f);
        Paint paint = new Paint();
        paint.setFlags(2);
        canvas.drawBitmap(b, (float) tt, (float) tt, paint);
        return ImageUtil.doBlur(overlay, (int) 2.0f, true);
    }
}
