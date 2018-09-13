package com.feng.car.entity.searchcar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchCarBean implements Serializable {
    public boolean canClick = false;
    public int countPerLine = 4;
    public int id;
    public boolean isChecked = false;
    public boolean isClearOther = false;
    public boolean isSingleSelect = false;
    public int max = 0;
    public int min = 0;
    public String name;
    public int parentid = 0;
    public int position = 0;
    public List<SearchCarBean> subList = new ArrayList();
    public String value;

    public SearchCarBean creatNewCarBean() {
        SearchCarBean bean = new SearchCarBean();
        bean.id = this.id;
        bean.name = this.name;
        bean.value = this.value;
        bean.canClick = this.canClick;
        bean.isChecked = this.isChecked;
        bean.isClearOther = this.isClearOther;
        bean.isSingleSelect = this.isSingleSelect;
        bean.position = this.position;
        bean.min = this.min;
        bean.max = this.max;
        bean.countPerLine = this.countPerLine;
        return bean;
    }

    public String getConditionName() {
        if (this.name.equals("自定义")) {
            return getPriceSection();
        }
        return this.name;
    }

    public String getPriceSection() {
        if (this.max == 0 && this.min > 0) {
            return this.min + "万以上";
        }
        if (this.max <= 0 || this.min != 0) {
            return this.min + "-" + this.max + "万";
        }
        return this.max + "万以下";
    }

    public void resetSublist() {
        for (int i = 0; i < this.subList.size(); i++) {
            ((SearchCarBean) this.subList.get(i)).isChecked = false;
        }
    }

    public boolean hasChecked() {
        if (this.isChecked) {
            return true;
        }
        if (this.subList.size() > 0) {
            for (int i = 0; i < this.subList.size(); i++) {
                if (((SearchCarBean) this.subList.get(i)).isChecked) {
                    return true;
                }
            }
        }
        return false;
    }
}
