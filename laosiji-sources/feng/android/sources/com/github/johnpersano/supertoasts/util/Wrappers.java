package com.github.johnpersano.supertoasts.util;

import java.util.ArrayList;
import java.util.List;

public class Wrappers {
    private List<OnClickWrapper> onClickWrapperList = new ArrayList();
    private List<OnDismissWrapper> onDismissWrapperList = new ArrayList();

    public void add(OnClickWrapper onClickWrapper) {
        this.onClickWrapperList.add(onClickWrapper);
    }

    public void add(OnDismissWrapper onDismissWrapper) {
        this.onDismissWrapperList.add(onDismissWrapper);
    }

    public List<OnClickWrapper> getOnClickWrappers() {
        return this.onClickWrapperList;
    }

    public List<OnDismissWrapper> getOnDismissWrappers() {
        return this.onDismissWrapperList;
    }
}
