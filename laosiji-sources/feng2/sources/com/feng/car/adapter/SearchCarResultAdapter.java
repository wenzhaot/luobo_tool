package com.feng.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.StopSellingCarActivity;
import com.feng.car.databinding.SearchcarResultItemBinding;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchCarResultAdapter extends MvvmBaseAdapter<CarSeriesInfo, SearchcarResultItemBinding> {
    private Drawable icon_upcoming_sale = this.mContext.getResources().getDrawable(R.drawable.icon_upcoming_sale);
    private boolean mIsOwnerSearch = false;
    private boolean mIsShowBrand = false;
    private List<CarSeriesInfo> stopSaleList = new ArrayList();

    public void setIsShowBrand(boolean b) {
        this.mIsShowBrand = b;
    }

    public void setStopSaleList(List<CarSeriesInfo> list) {
        this.stopSaleList = list;
    }

    public void setIsOwnerSearch(boolean isOwnerSearch) {
        this.mIsOwnerSearch = isOwnerSearch;
    }

    public SearchCarResultAdapter(Context context, List<CarSeriesInfo> list) {
        super(context, list);
        this.icon_upcoming_sale.setBounds(0, 0, this.icon_upcoming_sale.getMinimumWidth(), this.icon_upcoming_sale.getMinimumHeight());
    }

    public SearchcarResultItemBinding getBinding(ViewGroup parent, int viewType) {
        return SearchcarResultItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(SearchcarResultItemBinding searchcarResultItemBinding, CarSeriesInfo carSeriesInfo) {
        searchcarResultItemBinding.setCarSeriesInfo(carSeriesInfo);
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<SearchcarResultItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final CarSeriesInfo info = (CarSeriesInfo) this.mList.get(position);
        if (this.mIsShowBrand && info.posfirstflag == 1) {
            ((SearchcarResultItemBinding) holder.binding).tvCarBrand.setVisibility(0);
        } else {
            ((SearchcarResultItemBinding) holder.binding).tvCarBrand.setVisibility(8);
        }
        ((SearchcarResultItemBinding) holder.binding).image.setAutoImageURI(Uri.parse(info.image.url));
        ((SearchcarResultItemBinding) holder.binding).name.setText((CharSequence) info.name.get());
        ((SearchcarResultItemBinding) holder.binding).ivStopSell.setVisibility(8);
        ((SearchcarResultItemBinding) holder.binding).price.setVisibility(0);
        ((SearchcarResultItemBinding) holder.binding).rlBottom.setVisibility(0);
        ((SearchcarResultItemBinding) holder.binding).discount.setVisibility(0);
        ((SearchcarResultItemBinding) holder.binding).ivOpenClose.setVisibility(0);
        ((SearchcarResultItemBinding) holder.binding).count.setVisibility(0);
        ((SearchcarResultItemBinding) holder.binding).price.setText(changePriceColor(info.getCarPriceText(), info.getCarPrice()));
        if (info.hasautovoice == 1) {
            ((SearchcarResultItemBinding) holder.binding).voiceText.setVisibility(0);
        } else {
            ((SearchcarResultItemBinding) holder.binding).voiceText.setVisibility(8);
        }
        if (info.state == 10) {
            ((SearchcarResultItemBinding) holder.binding).count.setCompoundDrawables(this.icon_upcoming_sale, null, null, null);
        } else if (info.state == 40 || info.state == 0) {
            ((SearchcarResultItemBinding) holder.binding).ivStopSell.setVisibility(0);
            if (info.state == 40) {
                ((SearchcarResultItemBinding) holder.binding).ivStopSell.setImageResource(R.drawable.icon_stop_sale);
            } else {
                ((SearchcarResultItemBinding) holder.binding).ivStopSell.setImageResource(R.drawable.icon_not_sale);
            }
            ((SearchcarResultItemBinding) holder.binding).price.setVisibility(8);
            ((SearchcarResultItemBinding) holder.binding).count.setVisibility(8);
            ((SearchcarResultItemBinding) holder.binding).discount.setVisibility(8);
            ((SearchcarResultItemBinding) holder.binding).ivOpenClose.setVisibility(8);
        } else {
            ((SearchcarResultItemBinding) holder.binding).count.setCompoundDrawables(null, null, null, null);
        }
        if (info.speccount > 0) {
            ((SearchcarResultItemBinding) holder.binding).count.setText("共" + info.speccount + "款车型符合");
            ((SearchcarResultItemBinding) holder.binding).rlBottom.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (info.local_flag == 0) {
                        if (info.local_models.size() > 0) {
                            info.local_flag = 1;
                            ((SearchcarResultItemBinding) holder.binding).rvCarModel.setVisibility(0);
                            ((SearchcarResultItemBinding) holder.binding).divierLine1.setVisibility(0);
                            ((SearchcarResultItemBinding) holder.binding).divierLine2.setVisibility(8);
                            SearchCarResultAdapter.this.notifyDataSetChanged();
                            return;
                        }
                        info.local_flag = 2;
                        ((SearchcarResultItemBinding) holder.binding).ivOpenClose.setVisibility(8);
                        ((SearchcarResultItemBinding) holder.binding).progressBar.setVisibility(0);
                        SearchCarResultAdapter.this.getCarxData(info);
                    } else if (info.local_flag == 1) {
                        info.local_flag = 0;
                        ((SearchcarResultItemBinding) holder.binding).rvCarModel.setVisibility(8);
                        ((SearchcarResultItemBinding) holder.binding).divierLine1.setVisibility(8);
                        ((SearchcarResultItemBinding) holder.binding).divierLine2.setVisibility(0);
                        ((SearchcarResultItemBinding) holder.binding).ivOpenClose.setVisibility(0);
                        ((SearchcarResultItemBinding) holder.binding).progressBar.setVisibility(8);
                        ((SearchcarResultItemBinding) holder.binding).ivOpenClose.setImageResource(R.drawable.icon_search_open);
                    }
                }
            });
        } else {
            ((SearchcarResultItemBinding) holder.binding).rlBottom.setVisibility(8);
        }
        if (info.preferential == 0.0d || info.preferential == 100.0d) {
            ((SearchcarResultItemBinding) holder.binding).discount.setTextColor(this.mContext.getResources().getColor(R.color.color_38_000000));
        } else if (info.preferential - 100.0d > 0.0d) {
            ((SearchcarResultItemBinding) holder.binding).discount.setTextColor(this.mContext.getResources().getColor(R.color.color_ff2c04));
        } else if (info.preferential - 100.0d < 0.0d) {
            ((SearchcarResultItemBinding) holder.binding).discount.setTextColor(this.mContext.getResources().getColor(R.color.color_ff2c04));
        }
        ((SearchcarResultItemBinding) holder.binding).discount.setText(info.getPreferentialNew());
        ((SearchcarResultItemBinding) holder.binding).divierLine1.setVisibility(8);
        ((SearchcarResultItemBinding) holder.binding).divierLine2.setVisibility(0);
        ((SearchcarResultItemBinding) holder.binding).rvCarModel.setVisibility(8);
        ((SearchcarResultItemBinding) holder.binding).progressBar.setVisibility(8);
        if (info.local_flag == 0) {
            ((SearchcarResultItemBinding) holder.binding).ivOpenClose.setImageResource(R.drawable.icon_search_open);
        } else if (info.local_flag == 1 && info.local_models.size() > 0) {
            ((SearchcarResultItemBinding) holder.binding).ivOpenClose.setImageResource(R.drawable.icon_search_close);
            ((SearchcarResultItemBinding) holder.binding).divierLine1.setVisibility(0);
            ((SearchcarResultItemBinding) holder.binding).divierLine2.setVisibility(8);
            ((SearchcarResultItemBinding) holder.binding).rvCarModel.setVisibility(0);
            CarXSearchItemAdapter adapter = new CarXSearchItemAdapter(this.mContext, info, this.mIsOwnerSearch);
            ((SearchcarResultItemBinding) holder.binding).rvCarModel.setLayoutManager(new LinearLayoutManager(this.mContext));
            ((SearchcarResultItemBinding) holder.binding).rvCarModel.setAdapter(adapter);
        } else if (info.local_flag == 2) {
            ((SearchcarResultItemBinding) holder.binding).progressBar.setVisibility(0);
            ((SearchcarResultItemBinding) holder.binding).ivOpenClose.setVisibility(8);
        }
        if (position == this.mList.size() - 1) {
            ((SearchcarResultItemBinding) holder.binding).divider.setVisibility(8);
            if (this.stopSaleList == null || this.stopSaleList.size() <= 0) {
                ((SearchcarResultItemBinding) holder.binding).stopSaleLine.setVisibility(8);
                return;
            }
            ((SearchcarResultItemBinding) holder.binding).stopSaleLine.setVisibility(0);
            ((SearchcarResultItemBinding) holder.binding).stopSaleText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SearchCarResultAdapter.this.mContext.startActivity(new Intent(SearchCarResultAdapter.this.mContext, StopSellingCarActivity.class));
                }
            });
            return;
        }
        ((SearchcarResultItemBinding) holder.binding).stopSaleLine.setVisibility(8);
        ((SearchcarResultItemBinding) holder.binding).divider.setVisibility(0);
    }

    private void getCarxData(final CarSeriesInfo info) {
        Map<String, Object> map = new HashMap();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < info.speclist.size(); i++) {
            sb.append(info.speclist.get(i));
            if (i != info.speclist.size() - 1) {
                sb.append(",");
            }
        }
        map.put("speclist", sb.toString());
        map.put("cityid", String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        map.put("seriesid", String.valueOf(info.id));
        FengApplication.getInstance().httpRequest("carsearch/getspec/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) SearchCarResultAdapter.this.mContext).showSecondTypeToast((int) R.string.net_abnormal);
                info.local_flag = 0;
                SearchCarResultAdapter.this.notifyDataSetChanged();
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) SearchCarResultAdapter.this.mContext).showSecondTypeToast((int) R.string.net_abnormal);
                info.local_flag = 0;
                SearchCarResultAdapter.this.notifyDataSetChanged();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonObject = jsonResult.getJSONObject("body").getJSONObject("recommendcarx");
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        int size = jsonArray.length();
                        info.local_models.clear();
                        for (int i = 0; i < size; i++) {
                            CarModelInfo carXInfo = new CarModelInfo();
                            carXInfo.parser(jsonArray.getJSONObject(i).getJSONObject("carx"));
                            info.local_models.add(carXInfo);
                        }
                        int carsId = 0;
                        if (jsonObject.has("seriesid")) {
                            carsId = jsonObject.getInt("seriesid");
                        }
                        if (info.id == carsId) {
                            info.local_flag = 1;
                            SearchCarResultAdapter.this.notifyDataSetChanged();
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode("carsearch/getspec/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("carsearch/getspec/", content, e);
                }
            }
        });
    }

    private SpannableStringBuilder changePriceColor(String tip, String price) {
        StringBuilder sb = new StringBuilder();
        sb.append(tip);
        sb.append(price);
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        ForegroundColorSpan color54Span = new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.color_54_000000));
        ForegroundColorSpan color87Span = new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.color_87_000000));
        builder.setSpan(color54Span, 0, tip.length(), 33);
        builder.setSpan(color87Span, tip.length(), sb.length(), 18);
        return builder;
    }
}
