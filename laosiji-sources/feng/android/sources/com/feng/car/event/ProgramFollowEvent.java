package com.feng.car.event;

public class ProgramFollowEvent {
    public int id;
    public int isFollow;
    public int isRemind;

    public ProgramFollowEvent(int id, int isFollow, int isremind) {
        this.id = id;
        this.isFollow = isFollow;
        this.isRemind = isremind;
    }
}
