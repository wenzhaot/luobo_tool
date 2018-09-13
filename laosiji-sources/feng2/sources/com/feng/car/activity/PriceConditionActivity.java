package com.feng.car.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.databinding.ActivityPriceConditionBinding;
import com.feng.car.entity.searchcar.SearchCarBean;
import com.feng.car.event.SearchCarEvent;
import com.feng.car.utils.SearchCarManager;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class PriceConditionActivity extends BaseActivity<ActivityPriceConditionBinding> {
    private boolean mIsConfirmClick = false;
    private String mPrice = "";
    private int mPriceType;

    private class MyAdapter extends BaseAdapter {
        private List<SearchCarBean> beanList;

        public MyAdapter(List<SearchCarBean> list) {
            this.beanList = list;
        }

        public int getCount() {
            return this.beanList.size();
        }

        public Object getItem(int position) {
            return this.beanList.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final SearchCarBean bean = (SearchCarBean) this.beanList.get(position);
            TextView textView = new TextView(PriceConditionActivity.this);
            textView.setText(bean.name);
            textView.setPadding(0, PriceConditionActivity.this.getResources().getDimensionPixelSize(R.dimen.default_32PX), 0, PriceConditionActivity.this.getResources().getDimensionPixelSize(R.dimen.default_32PX));
            textView.setTextColor(PriceConditionActivity.this.getResources().getColor(R.color.color_87_000000));
            textView.setTextSize(14.0f);
            if (bean.isChecked) {
                textView.setTextColor(PriceConditionActivity.this.getResources().getColor(R.color.color_ffb90a));
            } else {
                textView.setTextColor(PriceConditionActivity.this.getResources().getColor(R.color.color_87_000000));
            }
            textView.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    List<SearchCarBean> list = SearchCarManager.newInstance().getPriceList();
                    for (int i = 0; i < list.size(); i++) {
                        SearchCarBean b = (SearchCarBean) list.get(i);
                        if (bean.id == b.id) {
                            b.isChecked = true;
                        } else {
                            b.isChecked = false;
                        }
                    }
                    if (SearchCarManager.newInstance().isNeedOpenResultActivity()) {
                        PriceConditionActivity.this.startActivity(new Intent(PriceConditionActivity.this, SearchCarResultActivity.class));
                    } else if (!(PriceConditionActivity.this.mPrice.equals(SearchCarManager.newInstance().getCurrentPriceValue().value) && PriceConditionActivity.this.mPriceType == SearchCarManager.newInstance().getPriceType())) {
                        EventBus.getDefault().post(new SearchCarEvent(2));
                    }
                    PriceConditionActivity.this.finish();
                }
            });
            return textView;
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_price_condition;
    }

    public void initView() {
        closeSwip();
        this.mPrice = SearchCarManager.newInstance().getCurrentPriceValue().value;
        this.mPriceType = SearchCarManager.newInstance().getPriceType();
        if (this.mPriceType == SearchCarManager.GUIDE_PRICE_TYPE) {
            ((ActivityPriceConditionBinding) this.mBaseBinding).titleText.setText(R.string.guide_price_text);
        } else {
            ((ActivityPriceConditionBinding) this.mBaseBinding).titleText.setText(R.string.real_price_text);
        }
        initTitle();
        ((ActivityPriceConditionBinding) this.mBaseBinding).minPrice.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtil.isEmpty(s.toString()) && StringUtil.isEmpty(((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).maxPrice.getText().toString())) {
                    PriceConditionActivity.this.mIsConfirmClick = false;
                } else {
                    PriceConditionActivity.this.mIsConfirmClick = true;
                }
                PriceConditionActivity.this.setConfirmClickable(PriceConditionActivity.this.mIsConfirmClick);
            }

            public void afterTextChanged(Editable s) {
            }
        });
        ((ActivityPriceConditionBinding) this.mBaseBinding).maxPrice.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtil.isEmpty(s.toString()) && StringUtil.isEmpty(((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).minPrice.getText().toString())) {
                    PriceConditionActivity.this.mIsConfirmClick = false;
                } else {
                    PriceConditionActivity.this.mIsConfirmClick = true;
                }
                PriceConditionActivity.this.setConfirmClickable(PriceConditionActivity.this.mIsConfirmClick);
            }

            public void afterTextChanged(Editable s) {
            }
        });
        ((ActivityPriceConditionBinding) this.mBaseBinding).confirm.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (PriceConditionActivity.this.mIsConfirmClick) {
                    int min = 0;
                    int max = 0;
                    String minStr = ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).minPrice.getText().toString();
                    String maxStr = ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).maxPrice.getText().toString();
                    if (!StringUtil.isEmpty(minStr)) {
                        min = Integer.parseInt(((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).minPrice.getText().toString());
                    }
                    if (!StringUtil.isEmpty(maxStr)) {
                        max = Integer.parseInt(((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).maxPrice.getText().toString());
                    }
                    if (max == 0 || min < max) {
                        SearchCarManager.newInstance().setCustomPrice(min, max);
                        if (SearchCarManager.newInstance().isNeedOpenResultActivity()) {
                            PriceConditionActivity.this.startActivity(new Intent(PriceConditionActivity.this, SearchCarResultActivity.class));
                        } else if (!(PriceConditionActivity.this.mPrice.equals(SearchCarManager.newInstance().getCurrentPriceValue().value) && PriceConditionActivity.this.mPriceType == SearchCarManager.newInstance().getPriceType())) {
                            EventBus.getDefault().post(new SearchCarEvent(2));
                        }
                        PriceConditionActivity.this.finish();
                        return;
                    }
                    PriceConditionActivity.this.showThirdTypeToast((int) R.string.maxprice_must_higher_minprice);
                }
            }
        });
        SearchCarBean priceBean = SearchCarManager.newInstance().getCurrentPriceValue();
        if (priceBean.name.equals("自定义")) {
            int min = priceBean.min;
            int max = priceBean.max;
            ((ActivityPriceConditionBinding) this.mBaseBinding).minPrice.setText(String.valueOf(min));
            if (max == 0) {
                ((ActivityPriceConditionBinding) this.mBaseBinding).maxPrice.setText("");
            } else {
                ((ActivityPriceConditionBinding) this.mBaseBinding).maxPrice.setText(String.valueOf(max));
            }
        }
    }

    private void setConfirmClickable(boolean clickable) {
        if (clickable) {
            ((ActivityPriceConditionBinding) this.mBaseBinding).confirm.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            ((ActivityPriceConditionBinding) this.mBaseBinding).confirm.setTextColor(this.mResources.getColor(R.color.color_ffffff));
            return;
        }
        ((ActivityPriceConditionBinding) this.mBaseBinding).confirm.setBackgroundResource(R.drawable.bg_d1d1d1_round);
        ((ActivityPriceConditionBinding) this.mBaseBinding).confirm.setTextColor(this.mResources.getColor(R.color.color_50_ffffff));
    }

    private void initTitle() {
        hideDefaultTitleBar();
        ((ActivityPriceConditionBinding) this.mBaseBinding).ivBack.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PriceConditionActivity.this.finish();
            }
        });
        ((ActivityPriceConditionBinding) this.mBaseBinding).tvUnlimitedCondition.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarManager.newInstance().setPriceUnlimited();
                if (SearchCarManager.newInstance().isNeedOpenResultActivity()) {
                    PriceConditionActivity.this.startActivity(new Intent(PriceConditionActivity.this, SearchCarResultActivity.class));
                } else if (!(PriceConditionActivity.this.mPrice.equals(SearchCarManager.newInstance().getCurrentPriceValue().value) && PriceConditionActivity.this.mPriceType == SearchCarManager.newInstance().getPriceType())) {
                    EventBus.getDefault().post(new SearchCarEvent(2));
                }
                PriceConditionActivity.this.finish();
            }
        });
        ((ActivityPriceConditionBinding) this.mBaseBinding).rgPrice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_guide_price /*2131624533*/:
                        ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).rlTitleBar.setBackgroundResource(R.drawable.gradient_ff9718_ff890a);
                        ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).rbGuidePrice.setTextColor(ContextCompat.getColor(PriceConditionActivity.this, R.color.color_ffb90a));
                        ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).rbOwnerPrice.setTextColor(ContextCompat.getColor(PriceConditionActivity.this, R.color.color_ffffff));
                        SearchCarManager.newInstance().setPriceType(SearchCarManager.GUIDE_PRICE_TYPE);
                        ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).titleText.setText(R.string.guide_price_text);
                        return;
                    case R.id.rb_owner_price /*2131624534*/:
                        ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).rlTitleBar.setBackgroundResource(R.drawable.gradient_62d1fc_33a4f7);
                        ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).rbGuidePrice.setTextColor(ContextCompat.getColor(PriceConditionActivity.this, R.color.color_ffffff));
                        ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).rbOwnerPrice.setTextColor(ContextCompat.getColor(PriceConditionActivity.this, R.color.color_33a4f7));
                        SearchCarManager.newInstance().setPriceType(SearchCarManager.REAL_PRICE_TYPE);
                        ((ActivityPriceConditionBinding) PriceConditionActivity.this.mBaseBinding).titleText.setText(R.string.real_price_text);
                        return;
                    default:
                        return;
                }
            }
        });
        if (SearchCarManager.newInstance().getPriceType() == SearchCarManager.GUIDE_PRICE_TYPE) {
            ((ActivityPriceConditionBinding) this.mBaseBinding).rbGuidePrice.setChecked(true);
        } else {
            ((ActivityPriceConditionBinding) this.mBaseBinding).rbOwnerPrice.setChecked(true);
        }
        initData();
    }

    private void initData() {
        List<SearchCarBean> list = new ArrayList();
        List<SearchCarBean> priceBeanList = SearchCarManager.newInstance().getPriceList();
        for (int i = 1; i < priceBeanList.size() - 1; i++) {
            list.add((SearchCarBean) priceBeanList.get(i));
        }
        ((ActivityPriceConditionBinding) this.mBaseBinding).listView.setAdapter(new MyAdapter(list));
    }
}
