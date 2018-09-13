package com.feng.car.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.feng.car.R;
import com.feng.car.adapter.RearrangeAdapter;
import com.feng.car.databinding.ActivityRearrangeBinding;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.event.PostSortRefreshEvent;
import com.feng.car.recyclerdrag.MyItemTouchCallback;
import com.feng.car.recyclerdrag.MyItemTouchCallback$OnDragListener;
import com.feng.car.recyclerdrag.OnRecyclerItemClickListener;
import com.feng.car.utils.PostDataManager;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class RearrangeActivity extends BaseActivity<ActivityRearrangeBinding> implements MyItemTouchCallback$OnDragListener {
    private ItemTouchHelper itemTouchHelper;
    private List<PostEdit> mBackUpList = new ArrayList();
    private List<PostEdit> mList;

    public int setBaseContentView() {
        return R.layout.activity_rearrange;
    }

    public void initView() {
        hideDefaultTitleBar();
        closeSwip();
        showTipsAnim();
        ((ActivityRearrangeBinding) this.mBaseBinding).back.setOnClickListener(this);
        ((ActivityRearrangeBinding) this.mBaseBinding).tvFinish.setOnClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setItemPrefetchEnabled(false);
        this.mList = PostDataManager.getInstance().getList();
        for (PostEdit edit : this.mList) {
            this.mBackUpList.add(edit);
        }
        RearrangeAdapter mAdapter = new RearrangeAdapter(this, this.mList);
        ((ActivityRearrangeBinding) this.mBaseBinding).recyclerView.setLayoutManager(gridLayoutManager);
        ((ActivityRearrangeBinding) this.mBaseBinding).recyclerView.setAdapter(mAdapter);
        ((ActivityRearrangeBinding) this.mBaseBinding).recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(((ActivityRearrangeBinding) this.mBaseBinding).recyclerView) {
            public void onLongClick(ViewHolder vh) {
                super.onLongClick(vh);
                int position = vh.getAdapterPosition();
                if (((PostEdit) RearrangeActivity.this.mList.get(position)).getType() == 2 || ((PostEdit) RearrangeActivity.this.mList.get(position)).getType() == 3) {
                    RearrangeActivity.this.itemTouchHelper.startDrag(vh);
                }
            }
        });
        this.itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(mAdapter).setOnDragListener(this));
        this.itemTouchHelper.attachToRecyclerView(((ActivityRearrangeBinding) this.mBaseBinding).recyclerView);
    }

    public void onFinishDrag() {
    }

    public void onSingleClick(View v) {
        super.onSingleClick(v);
        switch (v.getId()) {
            case R.id.back /*2131624216*/:
                this.mList.clear();
                for (PostEdit edit : this.mBackUpList) {
                    this.mList.add(edit);
                }
                finish();
                return;
            case R.id.tv_finish /*2131624218*/:
                EventBus.getDefault().post(new PostSortRefreshEvent());
                finish();
                return;
            default:
                return;
        }
    }

    private void showTipsAnim() {
        if (!SharedUtil.getBoolean(this, "rearrange_first", false)) {
            SharedUtil.putBoolean(this, "rearrange_first", true);
            if (!((ActivityRearrangeBinding) this.mBaseBinding).tipsLine.isShown()) {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.top_in_anim);
                animation.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                        ((ActivityRearrangeBinding) RearrangeActivity.this.mBaseBinding).tipsLine.setVisibility(0);
                    }

                    public void onAnimationEnd(Animation animation) {
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                ((ActivityRearrangeBinding) this.mBaseBinding).tipsLine.startAnimation(animation);
                ((ActivityRearrangeBinding) this.mBaseBinding).closeTipsButton.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        Animation animation = AnimationUtils.loadAnimation(RearrangeActivity.this, R.anim.top_out_anim);
                        animation.setAnimationListener(new AnimationListener() {
                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                ((ActivityRearrangeBinding) RearrangeActivity.this.mBaseBinding).tipsLine.setVisibility(8);
                            }

                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        ((ActivityRearrangeBinding) RearrangeActivity.this.mBaseBinding).tipsLine.startAnimation(animation);
                    }
                });
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        RearrangeActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                if (((ActivityRearrangeBinding) RearrangeActivity.this.mBaseBinding).tipsLine.isShown()) {
                                    Animation animation = AnimationUtils.loadAnimation(RearrangeActivity.this, R.anim.top_out_anim);
                                    animation.setAnimationListener(new AnimationListener() {
                                        public void onAnimationStart(Animation animation) {
                                        }

                                        public void onAnimationEnd(Animation animation) {
                                            ((ActivityRearrangeBinding) RearrangeActivity.this.mBaseBinding).tipsLine.setVisibility(8);
                                        }

                                        public void onAnimationRepeat(Animation animation) {
                                        }
                                    });
                                    ((ActivityRearrangeBinding) RearrangeActivity.this.mBaseBinding).tipsLine.startAnimation(animation);
                                }
                            }
                        });
                    }
                }).start();
            }
        }
    }
}
