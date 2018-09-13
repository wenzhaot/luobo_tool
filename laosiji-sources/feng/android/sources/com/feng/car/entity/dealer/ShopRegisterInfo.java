package com.feng.car.entity.dealer;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;
import com.feng.car.entity.sendpost.PostEdit;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

public class ShopRegisterInfo extends BaseObservable {
    @Expose
    private String description = "";
    @Expose
    public PostEdit idcardimagejson1;
    @Expose
    public PostEdit idcardimagejson2;
    @Expose
    public PostEdit idcardimagejson3;
    @Expose
    public List<PostEdit> imagelist = new ArrayList();
    public transient ObservableBoolean localFlag = new ObservableBoolean(false);
    @Expose
    private String localVerify = "";
    @Expose
    private String mobile = "";
    @Expose
    private String position = "";
    @Expose
    private String realname = "";
    @Expose
    public PostEdit saleimg;
    @Expose
    private int saletype = -1;
    @Expose
    private int sex = 1;
    @Expose
    private String shopaddress = "";
    @Expose
    private String shopname = "";

    @Bindable
    public String getLocalVerify() {
        return this.localVerify;
    }

    public void setLocalVerify(String localVerify) {
        this.localVerify = localVerify;
        check();
        notifyPropertyChanged(31);
    }

    @Bindable
    public String getRealname() {
        return this.realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
        check();
        notifyPropertyChanged(48);
    }

    @Bindable
    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
        check();
        notifyPropertyChanged(55);
    }

    @Bindable
    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        check();
        notifyPropertyChanged(41);
    }

    @Bindable
    public String getShopname() {
        return this.shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
        check();
        notifyPropertyChanged(58);
    }

    @Bindable
    public String getShopaddress() {
        return this.shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
        check();
        notifyPropertyChanged(57);
    }

    @Bindable
    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
        check();
        notifyPropertyChanged(46);
    }

    @Bindable
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
        check();
        notifyPropertyChanged(20);
    }

    @Bindable
    public int getSaletype() {
        return this.saletype;
    }

    public void setSaletype(int saletype) {
        this.saletype = saletype;
        check();
        notifyPropertyChanged(50);
    }

    public void check() {
        if (TextUtils.isEmpty(this.realname)) {
            this.localFlag.set(false);
        } else if (TextUtils.isEmpty(this.description)) {
            this.localFlag.set(false);
        } else if (TextUtils.isEmpty(this.mobile)) {
            this.localFlag.set(false);
        } else if (TextUtils.isEmpty(this.localVerify)) {
            this.localFlag.set(false);
        } else if (TextUtils.isEmpty(this.shopname)) {
            this.localFlag.set(false);
        } else if (TextUtils.isEmpty(this.shopaddress)) {
            this.localFlag.set(false);
        } else if (TextUtils.isEmpty(this.position)) {
            this.localFlag.set(false);
        } else if (this.saletype == -1) {
            this.localFlag.set(false);
        } else if (this.imagelist.size() == 0) {
            this.localFlag.set(false);
        } else if (this.idcardimagejson1 == null || this.idcardimagejson2 == null || this.idcardimagejson3 == null) {
            this.localFlag.set(false);
        } else if (this.saleimg == null) {
            this.localFlag.set(false);
        } else {
            this.localFlag.set(true);
        }
    }
}
