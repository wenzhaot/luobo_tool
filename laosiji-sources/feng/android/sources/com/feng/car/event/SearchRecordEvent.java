package com.feng.car.event;

import com.feng.car.entity.search.SearchItem;

public class SearchRecordEvent {
    public SearchItem searchItem;
    public int type;

    public SearchRecordEvent(SearchItem searchItem, int type) {
        this.type = type;
        this.searchItem = searchItem;
    }
}
