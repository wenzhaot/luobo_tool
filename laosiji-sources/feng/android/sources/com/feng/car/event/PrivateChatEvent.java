package com.feng.car.event;

import com.feng.car.entity.privatemsg.MessageInfo;

public class PrivateChatEvent {
    public MessageInfo info;
    public int userID;

    public PrivateChatEvent(MessageInfo info, int userID) {
        this.userID = userID;
        this.info = info;
    }
}
