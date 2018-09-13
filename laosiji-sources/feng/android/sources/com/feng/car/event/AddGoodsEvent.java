package com.feng.car.event;

import com.feng.car.entity.dealer.CommodityInfo;
import java.util.List;

public class AddGoodsEvent {
    public List<CommodityInfo> list;
    public List<String> listText;
    public String servePrice = "";

    public AddGoodsEvent(List<CommodityInfo> list) {
        this.list = list;
    }

    public AddGoodsEvent(List<CommodityInfo> list, List<String> listText, String servePrice) {
        this.list = list;
        this.listText = listText;
        this.servePrice = servePrice;
    }
}
