package com.feng.car.entity.model;

public class ApiSecurityInfo {
    public int digit;
    public int interval;

    public ApiSecurityInfo(int intInterval, int intDigit) {
        this.interval = intInterval;
        this.digit = intDigit;
    }
}
