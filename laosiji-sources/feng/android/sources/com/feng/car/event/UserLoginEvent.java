package com.feng.car.event;

public class UserLoginEvent {
    public boolean mIsLogin = false;

    public UserLoginEvent(boolean isLogin) {
        this.mIsLogin = isLogin;
    }
}
