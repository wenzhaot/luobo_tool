package com.feng.car.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.WriteServeItemAdater;
import com.feng.car.adapter.WriteServeItemAdater.OnAddItemListener;
import com.feng.car.databinding.ActivityGoodsServeLayoutBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.entity.dealer.CommodityServeInfo;
import com.feng.car.entity.dealer.WriteServeItem;
import com.feng.car.entity.dealer.WriteServeItem$OnWriteTextChange;
import com.feng.car.event.AddGoodsEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.view.CommonDialog;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class WriteGoodsServeActivity extends BaseActivity<ActivityGoodsServeLayoutBinding> implements WriteServeItem$OnWriteTextChange {
    private WriteServeItemAdater mAdapter;
    private boolean mFirstData = true;
    private int mGoodsId = 0;
    private String mGoodsName = "";
    private List<WriteServeItem> mList = new ArrayList();

    public int setBaseContentView() {
        return R.layout.activity_goods_serve_layout;
    }

    public void initView() {
        getWindow().setSoftInputMode(32);
        initNormalTitleBar((int) R.string.write_serve_item);
        initTitleBarRightTextGold(R.string.complete, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WriteGoodsServeActivity.this.mRootBinding.titleLine.tvRightText.isSelected()) {
                    WriteServeItem writeServeItem = (WriteServeItem) WriteGoodsServeActivity.this.mList.get(WriteGoodsServeActivity.this.mList.size() - 1);
                    if (TextUtils.isEmpty(writeServeItem.getPrice()) || Integer.parseInt(writeServeItem.getPrice()) == 0) {
                        WriteGoodsServeActivity.this.showThirdTypeToast("请填写总价");
                        return;
                    }
                    List<CommodityInfo> list = new ArrayList();
                    CommodityInfo baseModel = new CommodityInfo();
                    baseModel.id = WriteGoodsServeActivity.this.mGoodsId;
                    baseModel.title = WriteGoodsServeActivity.this.mGoodsName;
                    list.add(baseModel);
                    String serveprice = "";
                    List<String> listText = new ArrayList();
                    for (WriteServeItem item : WriteGoodsServeActivity.this.mList) {
                        if (item.type == 0 && !TextUtils.isEmpty(item.getContent())) {
                            listText.add(item.getContent());
                        } else if (item.type == 2 && !TextUtils.isEmpty(item.getPrice())) {
                            serveprice = item.getPrice();
                        }
                    }
                    EventBus.getDefault().post(new AddGoodsEvent(list, listText, serveprice));
                    FengUtil.closeSoftKeyboard(((ActivityGoodsServeLayoutBinding) WriteGoodsServeActivity.this.mBaseBinding).getRoot());
                    WriteGoodsServeActivity.this.finish();
                }
            }
        });
        ((ActivityGoodsServeLayoutBinding) this.mBaseBinding).tvRemoveList.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                List<DialogItemEntity> list = new ArrayList();
                list.add(new DialogItemEntity("确认", false));
                CommonDialog.showCommonDialog(WriteGoodsServeActivity.this, "确定删除清单？", list, new OnDialogItemClickListener() {
                    public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                        EventBus.getDefault().post(new AddGoodsEvent(new ArrayList(), new ArrayList(), ""));
                        FengUtil.closeSoftKeyboard(((ActivityGoodsServeLayoutBinding) WriteGoodsServeActivity.this.mBaseBinding).getRoot());
                        WriteGoodsServeActivity.this.finish();
                    }
                });
            }
        });
        initData();
        this.mAdapter = new WriteServeItemAdater(this, this.mList, new OnAddItemListener() {
            public void onAdd(int position) {
                WriteServeItem item = new WriteServeItem(WriteGoodsServeActivity.this);
                item.type = 0;
                WriteGoodsServeActivity.this.mList.add(WriteGoodsServeActivity.this.mList.size() - 2, item);
                WriteGoodsServeActivity.this.mAdapter.notifyDataSetChanged();
            }

            public void onDelete(WriteServeItem item) {
                WriteGoodsServeActivity.this.mList.remove(item);
                WriteGoodsServeActivity.this.mAdapter.notifyDataSetChanged();
            }
        });
        ((ActivityGoodsServeLayoutBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityGoodsServeLayoutBinding) this.mBaseBinding).recyclerview.setAdapter(this.mAdapter);
    }

    private void initData() {
        WriteServeItem item;
        this.mGoodsId = getIntent().getIntExtra("id", 0);
        this.mGoodsName = getIntent().getStringExtra("name");
        String datajson = getIntent().getStringExtra("DATA_JSON");
        String strPrice = "";
        if (!TextUtils.isEmpty(datajson)) {
            CommodityServeInfo serveInfo = (CommodityServeInfo) JsonUtil.fromJson(datajson, CommodityServeInfo.class);
            if (!(serveInfo == null || serveInfo.contents == null)) {
                for (String str : serveInfo.contents) {
                    item = new WriteServeItem(this);
                    item.type = 0;
                    item.setContent(str);
                    this.mList.add(item);
                }
                if (!TextUtils.isEmpty(serveInfo.price)) {
                    strPrice = serveInfo.price;
                    this.mRootBinding.titleLine.tvRightText.setSelected(true);
                    this.mFirstData = true;
                }
            }
        }
        int size = this.mList.size();
        if (size >= 3) {
            item = new WriteServeItem();
            item.type = 1;
            this.mList.add(item);
            item = new WriteServeItem(this);
            item.type = 2;
            item.setPrice(strPrice);
            this.mList.add(item);
        } else {
            for (int i = 0; i < 3 - size; i++) {
                item = new WriteServeItem(this);
                item.type = 0;
                this.mList.add(item);
            }
            item = new WriteServeItem();
            item.type = 1;
            this.mList.add(item);
            item = new WriteServeItem(this);
            item.type = 2;
            item.setPrice(strPrice);
            this.mList.add(item);
        }
        this.mFirstData = false;
    }

    public void onChange(boolean hasContent) {
        if (!this.mFirstData) {
            int size = this.mList.size();
            if (size <= 0 || TextUtils.isEmpty(((WriteServeItem) this.mList.get(size - 1)).getPrice())) {
                this.mRootBinding.titleLine.tvRightText.setSelected(false);
            } else if (hasContent) {
                this.mRootBinding.titleLine.tvRightText.setSelected(true);
            } else {
                for (WriteServeItem serveItem : this.mList) {
                    if (serveItem.type != 0) {
                        this.mRootBinding.titleLine.tvRightText.setSelected(false);
                        return;
                    } else if (!TextUtils.isEmpty(serveItem.getContent())) {
                        this.mRootBinding.titleLine.tvRightText.setSelected(true);
                        return;
                    }
                }
            }
        }
    }
}
