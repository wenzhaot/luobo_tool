package com.feng.car.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CarComparisonAdapter;
import com.feng.car.adapter.CarComparisonAdapter.OnSelChangeListener;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.CarComparisonLayoutBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.event.RefreshEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CarModleComparisonActivity extends BaseActivity<CarComparisonLayoutBinding> implements OnSelChangeListener {
    public static final int COMPARISON_MODEL_ADD = 1;
    public static final int COMPARISON_MODEL_NORMAL = 0;
    public static final String USER_SEL_COMPARISON_KEY = "user_sel_comparison_key";
    public final int COMPARISON_DEL_TYPE = 1;
    public final int COMPARISON_SEL_TYPE = 0;
    private CarComparisonAdapter mAdapter;
    private int mAddDelType = 0;
    private List<CarModelInfo> mList = new ArrayList();
    private int mType = 0;

    public int setBaseContentView() {
        return R.layout.car_comparison_layout;
    }

    public void initView() {
        initNormalTitleBar("车型对比");
        this.mType = getIntent().getIntExtra("feng_type", 0);
        if (this.mType == 0) {
            FengConstant.CAR_MAX_CURRENT_COMPARISON_NUM = 0;
        }
        this.mAdapter = new CarComparisonAdapter(this, this.mType, this.mList, this);
        initData(false);
        titleBarChange();
        ((CarComparisonLayoutBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((CarComparisonLayoutBinding) this.mBaseBinding).recyclerview.setAdapter(this.mAdapter);
        ((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.setOnClickListener(this);
    }

    private void initData(boolean isDel) {
        this.mList.clear();
        List<CarModelInfo> modelInfos = FengApplication.getInstance().getSparkDB().getCarComparisonRecord();
        String strJson = SharedUtil.getString(this, USER_SEL_COMPARISON_KEY);
        if (!TextUtils.isEmpty(strJson)) {
            List<CarModelInfo> list = JsonUtil.fromJson(strJson, new TypeToken<ArrayList<CarModelInfo>>() {
            });
            if (list != null) {
                if (this.mType == 1) {
                    modelInfos.removeAll(list);
                } else {
                    this.mAdapter.addSelCarModle(list, isDel);
                }
            }
        }
        this.mList.addAll(modelInfos);
        if (this.mType == 0) {
            if (this.mList.size() <= 0) {
                showEmptyView();
                return;
            }
            hideEmptyView();
            this.mRootBinding.titleLine.ivRightImage.setEnabled(true);
        } else if (this.mList.size() <= 0) {
            showEmptyView();
        } else {
            hideEmptyView();
        }
    }

    private void titleBarChange() {
        if (this.mAddDelType == 0) {
            initTitleBarRightImage(R.drawable.icon_edit_bar_selector, R.drawable.icon_add_bar_selector, this, this);
            if (this.mType == 1) {
                ((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.setVisibility(8);
                this.mRootBinding.titleLine.ivRightImage.setVisibility(4);
                this.mRootBinding.titleLine.ivRightImage.setEnabled(false);
                return;
            }
            ((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.setText("开始对比");
            ((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.setBackgroundResource(R.drawable.color_404040_pre_191919_unsel_404040);
            if (this.mList.size() <= 0) {
                showEmptyView();
            } else {
                hideEmptyView();
                this.mRootBinding.titleLine.ivRightImage.setEnabled(true);
            }
            onSelChange(this.mAdapter.getSelDelList().size());
            return;
        }
        initTitleBarRightText(R.string.complete, R.string.all_sel, this, this);
        this.mRootBinding.titleLine.tvRightTextTwo.setSelected(true);
        ((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.setSelected(false);
        ((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.setText("删除");
        ((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.setBackgroundResource(R.drawable.color_e12c2c_pre_b13b3b_unsel_black54);
    }

    public void onSelChange(int count) {
        if ((count <= 1 || this.mAddDelType != 0) && (count <= 0 || this.mAddDelType != 1)) {
            ((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.setSelected(false);
        } else {
            ((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.setSelected(true);
        }
    }

    public void onLongDel(int position) {
        showDelDialog(false, position);
    }

    public void onSingleClick(View v) {
        super.onSingleClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_start_del_comparison /*2131624855*/:
                if (!((CarComparisonLayoutBinding) this.mBaseBinding).tvStartDelComparison.isSelected()) {
                    return;
                }
                if (this.mAddDelType != 0) {
                    showDelDialog(true, 0);
                    return;
                } else if (this.mType == 0) {
                    SharedUtil.putString(this, USER_SEL_COMPARISON_KEY, JsonUtil.toJson(this.mAdapter.getSelDelList()));
                    intent = new Intent(this, CarConfigureCompareActivity.class);
                    intent.putExtra(CarConfigureCompareActivity.COMRARE_IDS, getCarConfigIDs(this.mAdapter.getSelDelList()));
                    intent.putExtra("feng_type", CarConfigureCompareActivity.TYPE_CARX);
                    startActivity(intent);
                    return;
                } else {
                    return;
                }
            case R.id.iv_right_image /*2131625319*/:
                this.mAddDelType = 1;
                this.mAdapter.changeSelDel(false);
                titleBarChange();
                return;
            case R.id.iv_right_image_two /*2131625320*/:
                if (this.mType == 0) {
                    if (this.mList.size() >= 50) {
                        showThirdTypeToast((int) R.string.car_add_max_hint);
                        return;
                    }
                } else if (FengApplication.getInstance().getSparkDB().getCarComparisonIDList().size() >= 50) {
                    showThirdTypeToast((int) R.string.car_add_max_hint);
                    return;
                }
                intent = new Intent(this, SelectCarByBrandActivity.class);
                intent.putExtra("feng_type", this.mType == 0 ? 1 : 2);
                startActivity(intent);
                return;
            case R.id.tv_right_text /*2131625321*/:
                this.mAddDelType = 0;
                this.mAdapter.changeSelDel(true);
                titleBarChange();
                return;
            case R.id.tv_right_text_two /*2131625322*/:
                if (this.mRootBinding.titleLine.tvRightTextTwo.isSelected()) {
                    this.mAdapter.setAllSel();
                    this.mRootBinding.titleLine.tvRightTextTwo.setText("取消全选");
                    this.mRootBinding.titleLine.tvRightTextTwo.setSelected(false);
                    return;
                }
                this.mAdapter.setNoAllSel();
                this.mRootBinding.titleLine.tvRightTextTwo.setText("全选");
                this.mRootBinding.titleLine.tvRightTextTwo.setSelected(true);
                return;
            default:
                return;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        if (this.mType == 1) {
            finish();
        } else if (this.mType == 0) {
            initData(event.type == 3);
            if (event.type == 1) {
                this.mAdapter.addSelCarModle(event.carModelInfo);
            }
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private void showDelDialog(final boolean isEdit, int position) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.affirm), false));
        CommonDialog.showCommonDialog(this, "确定删除所选车型？", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (isEdit) {
                    CarModleComparisonActivity.this.delRecond();
                } else {
                    CarModleComparisonActivity.this.delSingle(position);
                }
            }
        });
    }

    private void delSingle(int position) {
        CarModelInfo info = (CarModelInfo) this.mList.get(position);
        this.mAdapter.getSelDelList().remove(info);
        this.mList.remove(position);
        onSelChange(this.mAdapter.getSelDelList().size());
        this.mAdapter.notifyDataSetChanged();
        FengApplication.getInstance().getSparkDB().delCarComparisonRecond(info.id + "");
    }

    private void delRecond() {
        String idGroup = "";
        List<CarModelInfo> list = JsonUtil.fromJson(SharedUtil.getString(this, USER_SEL_COMPARISON_KEY), new TypeToken<ArrayList<CarModelInfo>>() {
        });
        if (list == null) {
            list = new ArrayList();
        }
        for (CarModelInfo info : this.mAdapter.getSelDelList()) {
            idGroup = idGroup + info.id + ",";
            list.remove(info);
        }
        SharedUtil.putString(this, USER_SEL_COMPARISON_KEY, JsonUtil.toJson(list));
        this.mAdapter.removeSel();
        if (idGroup.length() > 0) {
            FengApplication.getInstance().getSparkDB().delCarComparisonRecond(idGroup.substring(0, idGroup.length() - 1));
            this.mList.removeAll(this.mAdapter.getSelDelList());
            this.mAdapter.getSelDelList().clear();
            onSelChange(0);
            this.mAdapter.notifyDataSetChanged();
            EventBus.getDefault().post(new RefreshEvent());
        }
        this.mAddDelType = 0;
        this.mAdapter.changeSelDel(true);
        titleBarChange();
    }

    public String getCarConfigIDs(List<CarModelInfo> list) {
        String strID = "";
        for (CarModelInfo info : list) {
            strID = strID + info.id + ",";
        }
        if (strID.length() > 0) {
            return strID.substring(0, strID.length() - 1);
        }
        return strID;
    }

    private void showEmptyView() {
        showEmptyView(R.string.carmodle_comparison_no_config, R.drawable.icon_blank_vs, R.string.add_carmodle, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(CarModleComparisonActivity.this, SelectCarByBrandActivity.class);
                intent.putExtra("feng_type", CarModleComparisonActivity.this.mType == 0 ? 1 : 2);
                CarModleComparisonActivity.this.startActivity(intent);
            }
        });
        if (this.mType == 0) {
            this.mRootBinding.titleLine.ivRightImage.setVisibility(0);
        } else {
            this.mRootBinding.titleLine.ivRightImage.setVisibility(4);
        }
        this.mRootBinding.titleLine.ivRightImage.setEnabled(false);
    }
}
