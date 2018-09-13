package com.feng.car.entity.searchcar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchCarGroup implements Serializable {
    public List<SearchCarBean> beanList = new ArrayList();
    public int countPerLine = 3;
    public String groupName;
    public int groupPosition = -1;
    public boolean hasExpendTr = false;
    public int id;
    public boolean isGroupFirst = false;
    public boolean isSingleSelect = false;
    public boolean isTakeUp = false;
    public String key;
    public List<SearchCarBean> searchBeanList = new ArrayList();
    public String typeName;

    public void selectSecondCondition(SearchCarBean selectBean) {
        if (selectBean.name.equals("不限")) {
            for (SearchCarBean bean : this.beanList) {
                if (!bean.name.equals("不限")) {
                    if (bean.subList.size() > 0) {
                        for (SearchCarBean b : bean.subList) {
                            b.isChecked = false;
                        }
                    } else {
                        bean.isChecked = false;
                    }
                }
            }
            return;
        }
        ((SearchCarBean) this.beanList.get(0)).isChecked = false;
    }

    public void cancelSecondCondition(SearchCarBean cancelBean) {
        if (cancelBean.name.equals("不限")) {
            cancelBean.isChecked = true;
            return;
        }
        boolean flag = true;
        for (SearchCarBean bean : this.beanList) {
            if (bean.subList.size() > 0) {
                for (SearchCarBean b : bean.subList) {
                    if (b.isChecked) {
                        flag = false;
                        break;
                    }
                }
            } else if (bean.isChecked) {
                flag = false;
                break;
            }
        }
        if (flag) {
            ((SearchCarBean) this.beanList.get(0)).isChecked = true;
        }
    }

    public void selectThirdCondition(SearchCarBean selectBean) {
        if (selectBean.name.equals("不限")) {
            ((SearchCarBean) this.beanList.get(0)).isChecked = false;
            for (SearchCarBean bean : this.beanList) {
                if (bean.id == selectBean.parentid) {
                    for (SearchCarBean b : bean.subList) {
                        if (!b.name.equals("不限")) {
                            b.isChecked = false;
                        }
                    }
                }
            }
            return;
        }
        ((SearchCarBean) this.beanList.get(0)).isChecked = false;
        for (SearchCarBean bean2 : this.beanList) {
            if (bean2.id == selectBean.parentid) {
                for (SearchCarBean b2 : bean2.subList) {
                    if (b2.name.equals("不限") && b2.isChecked) {
                        b2.isChecked = false;
                    }
                }
            }
        }
    }

    public void cancelThirdCondition(SearchCarBean cancelBean) {
        boolean flag = true;
        for (SearchCarBean bean : this.beanList) {
            if (bean.subList.size() > 0) {
                for (SearchCarBean b : bean.subList) {
                    if (b.isChecked) {
                        flag = false;
                        break;
                    }
                }
            } else if (bean.isChecked) {
                flag = false;
                break;
            }
        }
        if (flag) {
            ((SearchCarBean) this.beanList.get(0)).isChecked = true;
        }
    }

    public boolean hasThreeLevel() {
        for (SearchCarBean bean : this.beanList) {
            if (bean.subList.size() > 0) {
                return true;
            }
        }
        for (SearchCarBean bean2 : this.searchBeanList) {
            if (bean2.subList.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public SearchCarGroup creatGroupWithoutList() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = this.id;
        group.groupName = this.groupName;
        group.isSingleSelect = this.isSingleSelect;
        group.countPerLine = this.countPerLine;
        group.key = this.key;
        group.hasExpendTr = this.hasExpendTr;
        group.isTakeUp = this.isTakeUp;
        group.typeName = this.typeName;
        group.isGroupFirst = this.isGroupFirst;
        group.groupPosition = this.groupPosition;
        group.beanList = this.beanList;
        return group;
    }
}
