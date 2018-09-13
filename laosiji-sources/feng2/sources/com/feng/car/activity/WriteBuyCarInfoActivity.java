package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityWriteCarInfoBinding;
import com.feng.car.databinding.TimepickerLayoutBinding;
import com.feng.car.databinding.UploadInvoiceWindowLayoutBinding;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.lcoation.CityInfo;
import com.feng.car.entity.lcoation.ProvinceInfo;
import com.feng.car.event.UploadPriceSelectEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.MapUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class WriteBuyCarInfoActivity extends BaseActivity<ActivityWriteCarInfoBinding> {
    private boolean canSubmit = false;
    private int m18;
    private int m340;
    private BrandInfo mBrandInfo;
    private String mBrandJson;
    private String mCarxName;
    private int mCarxid;
    private MyDropDownAadpter mCityAdapter;
    private UploadInvoiceWindowLayoutBinding mCityBinding;
    private int mCityId = 131;
    private List<CityInfo> mCityList = new ArrayList();
    private PopupWindow mCityWindow;
    private final int mCountLimitCount = 200;
    private boolean mHasUploadSuccess = false;
    private ImageInfo mImageInfo = new ImageInfo();
    private String mImageJson;
    private int mPayType = 2;
    private MyDropDownAadpter mProvincAdapter;
    private UploadInvoiceWindowLayoutBinding mProvinceBinding;
    private int mProvinceId = 131;
    private PopupWindow mProvinceWindow;
    private String mTime;
    private Dialog mTimeDialog;
    private TimepickerLayoutBinding mTimebinding;

    public class BrandInfo extends BaseInfo {
        public int brandid;
        public ImageInfo brandlogo = new ImageInfo();
        public String brandname;
        public int seriesid;
        public String seriesname;
        public int specid;
        public String specname;

        public void parser(JSONObject object) {
            try {
                if (object.has("brandid")) {
                    this.brandid = object.getInt("brandid");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (object.has("seriesid")) {
                    this.seriesid = object.getInt("seriesid");
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            try {
                if (object.has("specid")) {
                    this.specid = object.getInt("specid");
                }
            } catch (JSONException e22) {
                e22.printStackTrace();
            }
            try {
                if (object.has("seriesname")) {
                    this.seriesname = object.getString("seriesname");
                }
            } catch (JSONException e222) {
                e222.printStackTrace();
            }
            try {
                if (object.has("brandname")) {
                    this.brandname = object.getString("brandname");
                }
            } catch (JSONException e2222) {
                e2222.printStackTrace();
            }
            try {
                if (object.has("specname")) {
                    this.specname = object.getString("specname");
                }
            } catch (JSONException e22222) {
                e22222.printStackTrace();
            }
            try {
                if (object.has("brandlogo")) {
                    this.brandlogo = new ImageInfo();
                    this.brandlogo.parser(object.getJSONObject("brandlogo"));
                }
            } catch (JSONException e222222) {
                e222222.printStackTrace();
            }
        }
    }

    private class MyDropDownAadpter<T> extends BaseAdapter {
        private List<T> list;

        private class ViewHolder {
            View divier;
            TextView text;

            private ViewHolder() {
            }

            /* synthetic */ ViewHolder(MyDropDownAadpter x0, AnonymousClass1 x1) {
                this();
            }
        }

        public MyDropDownAadpter(List<T> list) {
            this.list = list;
        }

        public int getCount() {
            return this.list.size();
        }

        public Object getItem(int position) {
            return this.list.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(this, null);
                convertView = LayoutInflater.from(WriteBuyCarInfoActivity.this).inflate(R.layout.upload_invoice_item, parent, false);
                holder.text = (TextView) convertView.findViewById(2131624009);
                holder.divier = convertView.findViewById(R.id.divider);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ProvinceInfo object = this.list.get(position);
            if (object instanceof ProvinceInfo) {
                final ProvinceInfo provinceInfo = object;
                holder.text.setText(provinceInfo.name);
                if (provinceInfo.id == WriteBuyCarInfoActivity.this.mProvinceId) {
                    holder.text.setTextColor(WriteBuyCarInfoActivity.this.mResources.getColor(R.color.color_33A4F7));
                } else {
                    holder.text.setTextColor(WriteBuyCarInfoActivity.this.mResources.getColor(R.color.color_87_000000));
                }
                holder.text.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).provinceText.setText(provinceInfo.name);
                        WriteBuyCarInfoActivity.this.mProvinceId = provinceInfo.id;
                        WriteBuyCarInfoActivity.this.mCityList.clear();
                        WriteBuyCarInfoActivity.this.mCityList.addAll(MapUtil.newInstance().getCityListByProvinceId(WriteBuyCarInfoActivity.this, WriteBuyCarInfoActivity.this.mProvinceId));
                        ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).cityText.setText(((CityInfo) WriteBuyCarInfoActivity.this.mCityList.get(0)).name);
                        WriteBuyCarInfoActivity.this.mCityId = ((CityInfo) WriteBuyCarInfoActivity.this.mCityList.get(0)).id;
                        WriteBuyCarInfoActivity.this.hideProvinceWindow();
                    }
                });
            } else if (object instanceof CityInfo) {
                final CityInfo cityInfo = (CityInfo) object;
                holder.text.setText(cityInfo.name);
                if (cityInfo.id == WriteBuyCarInfoActivity.this.mCityId) {
                    holder.text.setTextColor(WriteBuyCarInfoActivity.this.mResources.getColor(R.color.color_33A4F7));
                } else {
                    holder.text.setTextColor(WriteBuyCarInfoActivity.this.mResources.getColor(R.color.color_87_000000));
                }
                holder.text.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).cityText.setText(cityInfo.name);
                        WriteBuyCarInfoActivity.this.mCityId = cityInfo.id;
                        WriteBuyCarInfoActivity.this.hideCityWindow();
                    }
                });
            }
            if (position == this.list.size() - 1) {
                holder.divier.setVisibility(8);
            } else {
                holder.divier.setVisibility(0);
            }
            return convertView;
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_write_car_info;
    }

    public void initView() {
        closeSwip();
        initNormalTitleBar((int) R.string.write_buy_car_info, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WriteBuyCarInfoActivity.this.mHasUploadSuccess) {
                    ActivityManager.getInstance().finishActivity(InvoiceUploadActivity.class);
                }
                WriteBuyCarInfoActivity.this.finish();
            }
        });
        this.m340 = this.mResources.getDimensionPixelSize(R.dimen.default_340PX);
        this.m18 = this.mResources.getDimensionPixelSize(R.dimen.default_18PX);
        Intent intent = getIntent();
        this.mBrandJson = intent.getStringExtra("brand");
        this.mImageJson = intent.getStringExtra("image");
        this.mCarxid = intent.getIntExtra("carxid", 0);
        this.mCarxName = intent.getStringExtra("carx_name");
        this.mBrandInfo = new BrandInfo();
        if (!StringUtil.isEmpty(this.mBrandJson)) {
            try {
                this.mBrandInfo.parser(new JSONObject(this.mBrandJson));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.mBrandInfo.specname = this.mCarxName;
        this.mBrandInfo.specid = this.mCarxid;
        if (!(this.mBrandInfo.brandid == 0 || StringUtil.isEmpty(this.mBrandInfo.brandname))) {
            ((ActivityWriteCarInfoBinding) this.mBaseBinding).brandText.setText(this.mBrandInfo.brandname);
        }
        if (!(this.mBrandInfo.seriesid == 0 || StringUtil.isEmpty(this.mBrandInfo.seriesname))) {
            ((ActivityWriteCarInfoBinding) this.mBaseBinding).carsText.setText(this.mBrandInfo.seriesname);
        }
        if (!(this.mBrandInfo.specid == 0 || StringUtil.isEmpty(this.mBrandInfo.specname))) {
            ((ActivityWriteCarInfoBinding) this.mBaseBinding).carxText.setText(this.mBrandInfo.specname);
        }
        try {
            this.mImageInfo.parser(new JSONObject(this.mImageJson));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        this.mCityId = MapUtil.newInstance().getLoactionCityId();
        this.mProvinceId = MapUtil.newInstance().getProvinceIdByCityId(this, this.mCityId);
        this.mCityList = MapUtil.newInstance().getCityListByProvinceId(this, this.mProvinceId);
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).cityText.setText(MapUtil.newInstance().getCityNameById(this, this.mCityId));
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).provinceText.setText(MapUtil.newInstance().getProvinceNameById(this, this.mProvinceId));
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).mobilText.setText(FengApplication.getInstance().getUserInfo().phonenumber);
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).brandText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(WriteBuyCarInfoActivity.this, SelectCarByBrandActivity.class);
                intent.putExtra("type", true);
                WriteBuyCarInfoActivity.this.startActivity(intent);
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).carsText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WriteBuyCarInfoActivity.this.mBrandInfo.brandid == 0) {
                    WriteBuyCarInfoActivity.this.showThirdTypeToast((int) R.string.please_select_brand);
                    return;
                }
                Intent intent = new Intent(WriteBuyCarInfoActivity.this, AllCarSeriesActivity.class);
                intent.putExtra("type", true);
                intent.putExtra("brandid", WriteBuyCarInfoActivity.this.mBrandInfo.brandid);
                intent.putExtra("feng_type", 0);
                intent.putExtra("name", WriteBuyCarInfoActivity.this.mBrandInfo.brandname);
                intent.putExtra("url", WriteBuyCarInfoActivity.this.mBrandInfo.brandlogo.url);
                WriteBuyCarInfoActivity.this.startActivity(intent);
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).carxText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WriteBuyCarInfoActivity.this.mBrandInfo.seriesid == 0) {
                    WriteBuyCarInfoActivity.this.showThirdTypeToast((int) R.string.please_select_cars);
                    return;
                }
                Intent intent = new Intent(WriteBuyCarInfoActivity.this, AddCarModelActivity.class);
                intent.putExtra("type", true);
                intent.putExtra("id", WriteBuyCarInfoActivity.this.mBrandInfo.seriesid);
                intent.putExtra("name", WriteBuyCarInfoActivity.this.mBrandInfo.seriesname);
                intent.putExtra("feng_type", 0);
                WriteBuyCarInfoActivity.this.startActivity(intent);
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).provinceText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WriteBuyCarInfoActivity.this.showProvinceWindow();
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).cityText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WriteBuyCarInfoActivity.this.showCityWindow();
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).timeText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WriteBuyCarInfoActivity.this.showTimePickerDialog();
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).fullText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WriteBuyCarInfoActivity.this.mPayType != 2) {
                    WriteBuyCarInfoActivity.this.setFullPaymentType();
                }
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).loanText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WriteBuyCarInfoActivity.this.mPayType != 1) {
                    WriteBuyCarInfoActivity.this.setLoanPaymentType();
                }
            }
        });
        initCanSubmitListener();
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).submit.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!WriteBuyCarInfoActivity.this.canSubmit) {
                    return;
                }
                if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).mobilText.getText().toString().length() != 11) {
                    WriteBuyCarInfoActivity.this.showSecondTypeToast((int) R.string.phone_number_wrong);
                    return;
                }
                if (Double.parseDouble(((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.getText().toString()) < Double.parseDouble(((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.getText().toString())) {
                    WriteBuyCarInfoActivity.this.showThirdTypeToast((int) R.string.landingprice_cannot_less_nakedprice);
                } else if (StringUtil.strLenthLikeWeiBo(((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).remarksText.getText().toString().trim(), true) > 200) {
                    WriteBuyCarInfoActivity.this.showThirdTypeToast((int) R.string.remarks_cannot_more_200);
                } else {
                    WriteBuyCarInfoActivity.this.showSubmitConfirmDialog();
                }
            }
        });
    }

    private void showSubmitConfirmDialog() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("确定", false));
        CommonDialog.showCommonDialog(this, "提交以后就不可以修改了\n一定要确保信息的真实性哦！", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    WriteBuyCarInfoActivity.this.uploadData();
                }
            }
        });
    }

    private void showTimePickerDialog() {
        if (this.mTimeDialog == null) {
            this.mTimebinding = TimepickerLayoutBinding.inflate(LayoutInflater.from(this));
            this.mTimebinding.datePicker.setMaxDate(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(System.currentTimeMillis()));
            calendar.add(1, -1);
            this.mTimebinding.datePicker.setMinDate(calendar.getTime().getTime());
            this.mTimebinding.datePicker.setCalendarViewShown(false);
            this.mTimebinding.timeSubmit.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    int year = WriteBuyCarInfoActivity.this.mTimebinding.datePicker.getYear();
                    int month = WriteBuyCarInfoActivity.this.mTimebinding.datePicker.getMonth();
                    int day = WriteBuyCarInfoActivity.this.mTimebinding.datePicker.getDayOfMonth();
                    StringBuffer sb = new StringBuffer();
                    sb.append(year);
                    sb.append("-");
                    if (month < 9) {
                        sb.append("0");
                    }
                    sb.append(month + 1);
                    sb.append("-");
                    if (day < 10) {
                        sb.append("0");
                    }
                    sb.append(day);
                    WriteBuyCarInfoActivity.this.mTime = sb.toString();
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).timeText.setText(WriteBuyCarInfoActivity.this.mTime);
                    WriteBuyCarInfoActivity.this.mTimeDialog.dismiss();
                }
            });
            this.mTimeDialog = new Dialog(this);
            this.mTimeDialog.setContentView(this.mTimebinding.getRoot());
            this.mTimeDialog.setCanceledOnTouchOutside(true);
        }
        this.mTimeDialog.show();
    }

    private void setFullPaymentType() {
        this.mPayType = 2;
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).fullText.setTextColor(this.mResources.getColor(R.color.color_ffffff));
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).fullText.setBackgroundResource(R.color.color_ffb90a);
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).fullText.setPadding(this.m18, this.m18, this.m18, this.m18);
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).loanText.setTextColor(this.mResources.getColor(R.color.color_87_000000));
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).loanText.setBackgroundResource(R.drawable.bg_border_ffffff_dfdfdf);
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).loanText.setPadding(this.m18, this.m18, this.m18, this.m18);
    }

    private void setLoanPaymentType() {
        this.mPayType = 1;
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).loanText.setTextColor(this.mResources.getColor(R.color.color_ffffff));
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).loanText.setBackgroundResource(R.color.color_ffb90a);
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).loanText.setPadding(this.m18, this.m18, this.m18, this.m18);
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).fullText.setTextColor(this.mResources.getColor(R.color.color_87_000000));
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).fullText.setBackgroundResource(R.drawable.bg_border_ffffff_dfdfdf);
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).fullText.setPadding(this.m18, this.m18, this.m18, this.m18);
    }

    private void showProvinceWindow() {
        if (this.mProvinceWindow == null) {
            this.mProvinceBinding = UploadInvoiceWindowLayoutBinding.inflate(LayoutInflater.from(this));
            this.mProvinceWindow = new PopupWindow(this.mProvinceBinding.getRoot(), ((ActivityWriteCarInfoBinding) this.mBaseBinding).provinceText.getWidth(), this.m340, true);
            this.mProvincAdapter = new MyDropDownAadpter(MapUtil.newInstance().getAllProvinceList(this));
            this.mProvinceBinding.listView.setAdapter(this.mProvincAdapter);
        }
        this.mProvincAdapter.notifyDataSetChanged();
        this.mProvinceWindow.setFocusable(true);
        this.mProvinceWindow.setOutsideTouchable(true);
        this.mProvinceWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        this.mProvinceWindow.showAsDropDown(((ActivityWriteCarInfoBinding) this.mBaseBinding).provinceText);
    }

    private void hideProvinceWindow() {
        if (this.mProvinceWindow != null && this.mProvinceWindow.isShowing()) {
            this.mProvinceWindow.dismiss();
        }
    }

    private void showCityWindow() {
        if (this.mCityWindow == null) {
            this.mCityBinding = UploadInvoiceWindowLayoutBinding.inflate(LayoutInflater.from(this));
            this.mCityWindow = new PopupWindow(this.mCityBinding.getRoot(), ((ActivityWriteCarInfoBinding) this.mBaseBinding).cityText.getWidth(), this.m340, true);
            this.mCityAdapter = new MyDropDownAadpter(this.mCityList);
            this.mCityBinding.listView.setAdapter(this.mCityAdapter);
        }
        this.mCityAdapter.notifyDataSetChanged();
        if (this.mCityList.size() == 1) {
            this.mCityWindow.setHeight(-2);
        } else {
            this.mCityWindow.setHeight(this.m340);
        }
        this.mCityWindow.setFocusable(true);
        this.mCityWindow.setOutsideTouchable(true);
        this.mCityWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        this.mCityWindow.showAsDropDown(((ActivityWriteCarInfoBinding) this.mBaseBinding).cityText);
    }

    private void hideCityWindow() {
        if (this.mCityWindow != null && this.mCityWindow.isShowing()) {
            this.mCityWindow.dismiss();
        }
    }

    private void initCanSubmitListener() {
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).mobilText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolean flag1 = ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).cityText.getText().toString().length() > 0;
                    boolean flag2;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).timeText.getText().toString().length() > 0) {
                        flag2 = true;
                    } else {
                        flag2 = false;
                    }
                    boolean flag3;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.getText().toString().length() > 0) {
                        flag3 = true;
                    } else {
                        flag3 = false;
                    }
                    boolean flag4;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.getText().toString().length() > 0) {
                        flag4 = true;
                    } else {
                        flag4 = false;
                    }
                    boolean flag5;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.specid != 0) {
                        flag5 = true;
                    } else {
                        flag5 = false;
                    }
                    boolean flag6;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.seriesid != 0) {
                        flag6 = true;
                    } else {
                        flag6 = false;
                    }
                    boolean flag7;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.brandid != 0) {
                        flag7 = true;
                    } else {
                        flag7 = false;
                    }
                    if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7) {
                        WriteBuyCarInfoActivity.this.setSubmitAvailable();
                        return;
                    } else {
                        WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
                        return;
                    }
                }
                WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).carxText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                boolean flag1;
                if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).cityText.getText().toString().length() > 0) {
                    flag1 = true;
                } else {
                    flag1 = false;
                }
                boolean flag2;
                if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).timeText.getText().toString().length() > 0) {
                    flag2 = true;
                } else {
                    flag2 = false;
                }
                boolean flag3;
                if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.getText().toString().length() > 0) {
                    flag3 = true;
                } else {
                    flag3 = false;
                }
                boolean flag4;
                if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.getText().toString().length() > 0) {
                    flag4 = true;
                } else {
                    flag4 = false;
                }
                boolean flag5;
                if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).mobilText.getText().toString().length() > 0) {
                    flag5 = true;
                } else {
                    flag5 = false;
                }
                boolean flag6;
                if (WriteBuyCarInfoActivity.this.mBrandInfo.specid != 0) {
                    flag6 = true;
                } else {
                    flag6 = false;
                }
                boolean flag7;
                if (WriteBuyCarInfoActivity.this.mBrandInfo.seriesid != 0) {
                    flag7 = true;
                } else {
                    flag7 = false;
                }
                boolean flag8;
                if (WriteBuyCarInfoActivity.this.mBrandInfo.brandid != 0) {
                    flag8 = true;
                } else {
                    flag8 = false;
                }
                if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8) {
                    WriteBuyCarInfoActivity.this.setSubmitAvailable();
                } else {
                    WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
                }
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).provinceText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolean flag1 = ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).cityText.getText().toString().length() > 0;
                    boolean flag2;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).timeText.getText().toString().length() > 0) {
                        flag2 = true;
                    } else {
                        flag2 = false;
                    }
                    boolean flag3;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.getText().toString().length() > 0) {
                        flag3 = true;
                    } else {
                        flag3 = false;
                    }
                    boolean flag4;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.getText().toString().length() > 0) {
                        flag4 = true;
                    } else {
                        flag4 = false;
                    }
                    boolean flag5;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.specid != 0) {
                        flag5 = true;
                    } else {
                        flag5 = false;
                    }
                    boolean flag6;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).mobilText.getText().toString().length() > 0) {
                        flag6 = true;
                    } else {
                        flag6 = false;
                    }
                    boolean flag7;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.seriesid != 0) {
                        flag7 = true;
                    } else {
                        flag7 = false;
                    }
                    boolean flag8;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.brandid != 0) {
                        flag8 = true;
                    } else {
                        flag8 = false;
                    }
                    if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8) {
                        WriteBuyCarInfoActivity.this.setSubmitAvailable();
                        return;
                    } else {
                        WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
                        return;
                    }
                }
                WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).cityText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolean flag1 = ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).provinceText.getText().toString().length() > 0;
                    boolean flag2;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).timeText.getText().toString().length() > 0) {
                        flag2 = true;
                    } else {
                        flag2 = false;
                    }
                    boolean flag3;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.getText().toString().length() > 0) {
                        flag3 = true;
                    } else {
                        flag3 = false;
                    }
                    boolean flag4;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.getText().toString().length() > 0) {
                        flag4 = true;
                    } else {
                        flag4 = false;
                    }
                    boolean flag5;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.specid != 0) {
                        flag5 = true;
                    } else {
                        flag5 = false;
                    }
                    boolean flag6;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).mobilText.getText().toString().length() > 0) {
                        flag6 = true;
                    } else {
                        flag6 = false;
                    }
                    boolean flag7;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.seriesid != 0) {
                        flag7 = true;
                    } else {
                        flag7 = false;
                    }
                    boolean flag8;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.brandid != 0) {
                        flag8 = true;
                    } else {
                        flag8 = false;
                    }
                    if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8) {
                        WriteBuyCarInfoActivity.this.setSubmitAvailable();
                        return;
                    } else {
                        WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
                        return;
                    }
                }
                WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).timeText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolean flag1 = ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).provinceText.getText().toString().length() > 0;
                    boolean flag2;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).cityText.getText().toString().length() > 0) {
                        flag2 = true;
                    } else {
                        flag2 = false;
                    }
                    boolean flag3;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.getText().toString().length() > 0) {
                        flag3 = true;
                    } else {
                        flag3 = false;
                    }
                    boolean flag4;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.getText().toString().length() > 0) {
                        flag4 = true;
                    } else {
                        flag4 = false;
                    }
                    boolean flag5;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.specid != 0) {
                        flag5 = true;
                    } else {
                        flag5 = false;
                    }
                    boolean flag6;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).mobilText.getText().toString().length() > 0) {
                        flag6 = true;
                    } else {
                        flag6 = false;
                    }
                    boolean flag7;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.seriesid != 0) {
                        flag7 = true;
                    } else {
                        flag7 = false;
                    }
                    boolean flag8;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.brandid != 0) {
                        flag8 = true;
                    } else {
                        flag8 = false;
                    }
                    if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8) {
                        WriteBuyCarInfoActivity.this.setSubmitAvailable();
                        return;
                    } else {
                        WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
                        return;
                    }
                }
                WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).nakedPriceText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".") && (s.length() - 1) - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.setText(s);
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.setSelection(s.length());
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.setText(s);
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1 && !s.toString().substring(1, 2).equals(".")) {
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.setText(s.subSequence(0, 1));
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.setSelection(1);
                }
            }

            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolean flag1 = ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).provinceText.getText().toString().length() > 0;
                    boolean flag2;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).cityText.getText().toString().length() > 0) {
                        flag2 = true;
                    } else {
                        flag2 = false;
                    }
                    boolean flag3;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).timeText.getText().toString().length() > 0) {
                        flag3 = true;
                    } else {
                        flag3 = false;
                    }
                    boolean flag4;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.getText().toString().length() > 0) {
                        flag4 = true;
                    } else {
                        flag4 = false;
                    }
                    boolean flag5;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.specid != 0) {
                        flag5 = true;
                    } else {
                        flag5 = false;
                    }
                    boolean flag6;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).mobilText.getText().toString().length() > 0) {
                        flag6 = true;
                    } else {
                        flag6 = false;
                    }
                    boolean flag7;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.seriesid != 0) {
                        flag7 = true;
                    } else {
                        flag7 = false;
                    }
                    boolean flag8;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.brandid != 0) {
                        flag8 = true;
                    } else {
                        flag8 = false;
                    }
                    if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8) {
                        WriteBuyCarInfoActivity.this.setSubmitAvailable();
                        return;
                    } else {
                        WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
                        return;
                    }
                }
                WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
            }
        });
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).landingPriceText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".") && (s.length() - 1) - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.setText(s);
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.setSelection(s.length());
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.setText(s);
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1 && !s.toString().substring(1, 2).equals(".")) {
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.setText(s.subSequence(0, 1));
                    ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).landingPriceText.setSelection(1);
                }
            }

            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolean flag1 = ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).provinceText.getText().toString().length() > 0;
                    boolean flag2;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).cityText.getText().toString().length() > 0) {
                        flag2 = true;
                    } else {
                        flag2 = false;
                    }
                    boolean flag3;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).timeText.getText().toString().length() > 0) {
                        flag3 = true;
                    } else {
                        flag3 = false;
                    }
                    boolean flag4;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).nakedPriceText.getText().toString().length() > 0) {
                        flag4 = true;
                    } else {
                        flag4 = false;
                    }
                    boolean flag5;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.specid != 0) {
                        flag5 = true;
                    } else {
                        flag5 = false;
                    }
                    boolean flag6;
                    if (((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).mobilText.getText().toString().length() > 0) {
                        flag6 = true;
                    } else {
                        flag6 = false;
                    }
                    boolean flag7;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.seriesid != 0) {
                        flag7 = true;
                    } else {
                        flag7 = false;
                    }
                    boolean flag8;
                    if (WriteBuyCarInfoActivity.this.mBrandInfo.brandid != 0) {
                        flag8 = true;
                    } else {
                        flag8 = false;
                    }
                    if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8) {
                        WriteBuyCarInfoActivity.this.setSubmitAvailable();
                        return;
                    } else {
                        WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
                        return;
                    }
                }
                WriteBuyCarInfoActivity.this.setSubmitUnAvailable();
            }
        });
    }

    private void setSubmitAvailable() {
        this.canSubmit = true;
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).submit.setImageResource(R.drawable.upload_invoice_submit);
    }

    private void setSubmitUnAvailable() {
        this.canSubmit = false;
        ((ActivityWriteCarInfoBinding) this.mBaseBinding).submit.setImageResource(R.drawable.submit_unavailable);
    }

    private void uploadData() {
        double price = Double.parseDouble(((ActivityWriteCarInfoBinding) this.mBaseBinding).nakedPriceText.getText().toString());
        double totalprice = Double.parseDouble(((ActivityWriteCarInfoBinding) this.mBaseBinding).landingPriceText.getText().toString());
        Map<String, Object> map = new HashMap();
        map.put("brandid", String.valueOf(this.mBrandInfo.brandid));
        map.put("seriesid", String.valueOf(this.mBrandInfo.seriesid));
        map.put("specid", String.valueOf(this.mBrandInfo.specid));
        map.put("cityid", String.valueOf(this.mCityId));
        map.put("provinceid", String.valueOf(this.mProvinceId));
        map.put("price", String.valueOf((int) (price * 10000.0d)));
        map.put("totalprice", String.valueOf((int) (totalprice * 10000.0d)));
        map.put("dealtime", ((ActivityWriteCarInfoBinding) this.mBaseBinding).timeText.getText().toString());
        map.put("dealtype", String.valueOf(this.mPayType));
        map.put("image", this.mImageJson);
        map.put("phone", ((ActivityWriteCarInfoBinding) this.mBaseBinding).mobilText.getText().toString());
        String remarks = ((ActivityWriteCarInfoBinding) this.mBaseBinding).remarksText.getText().toString();
        if (!StringUtil.isEmpty(remarks)) {
            map.put("remark", remarks);
        }
        FengApplication.getInstance().httpRequest("ownerprice/add/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                WriteBuyCarInfoActivity.this.showThirdTypeToast((int) R.string.check_network);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                WriteBuyCarInfoActivity.this.showSecondTypeToast((int) R.string.upload_failde);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    if (new JSONObject(content).getInt("code") == 1) {
                        WriteBuyCarInfoActivity.this.mHasUploadSuccess = true;
                        WriteBuyCarInfoActivity.this.mRootBinding.titleLine.ivTitleLeft.setVisibility(8);
                        ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).infoLine.setVisibility(8);
                        ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).successLine.setVisibility(0);
                        ((ActivityWriteCarInfoBinding) WriteBuyCarInfoActivity.this.mBaseBinding).successText.setOnClickListener(new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                WriteBuyCarInfoActivity.this.finish();
                                ActivityManager.getInstance().finishActivity(InvoiceUploadActivity.class);
                            }
                        });
                        return;
                    }
                    WriteBuyCarInfoActivity.this.showSecondTypeToast((int) R.string.upload_failde);
                } catch (JSONException e) {
                    e.printStackTrace();
                    WriteBuyCarInfoActivity.this.showSecondTypeToast((int) R.string.upload_failde);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UploadPriceSelectEvent event) {
        if (event.type == 1) {
            if (event.id != this.mBrandInfo.brandid) {
                this.mBrandInfo.brandid = event.id;
                this.mBrandInfo.brandname = event.name;
                ((ActivityWriteCarInfoBinding) this.mBaseBinding).brandText.setText(this.mBrandInfo.brandname);
                this.mBrandInfo.brandlogo.url = event.imageUrl;
                this.mBrandInfo.seriesid = 0;
                this.mBrandInfo.seriesname = "";
                this.mBrandInfo.specid = 0;
                this.mBrandInfo.specname = "";
                ((ActivityWriteCarInfoBinding) this.mBaseBinding).carsText.setText(getString(R.string.select_cars));
                ((ActivityWriteCarInfoBinding) this.mBaseBinding).carxText.setText(getString(R.string.select_carx));
            }
        } else if (event.type == 2) {
            if (event.id != this.mBrandInfo.seriesid) {
                this.mBrandInfo.seriesid = event.id;
                this.mBrandInfo.seriesname = event.name;
                ((ActivityWriteCarInfoBinding) this.mBaseBinding).carsText.setText(this.mBrandInfo.seriesname);
                this.mBrandInfo.specid = 0;
                this.mBrandInfo.specname = "";
                ((ActivityWriteCarInfoBinding) this.mBaseBinding).carxText.setText(getString(R.string.select_carx));
            }
        } else if (event.type == 3 && event.id != this.mBrandInfo.specid) {
            this.mBrandInfo.specid = event.id;
            this.mBrandInfo.specname = event.name;
            ((ActivityWriteCarInfoBinding) this.mBaseBinding).carxText.setText(this.mBrandInfo.specname);
        }
    }
}
