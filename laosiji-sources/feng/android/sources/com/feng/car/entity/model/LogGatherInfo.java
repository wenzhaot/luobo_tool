package com.feng.car.entity.model;

import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogConstans;
import com.feng.car.utils.LogGatherReadUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

public class LogGatherInfo {
    private String mCurrentPage;
    private String mPageidentifying;
    private String mReferer;

    public void setCurrentPage(String currentPage) {
        this.mCurrentPage = currentPage;
        LogGatherReadUtil.getInstance().setCurrentPage(currentPage);
    }

    public void setBasePage(String strPage) {
        if (!TextUtils.isEmpty(strPage) && !strPage.equals(LogGatherReadUtil.getInstance().getCurrentPage())) {
            try {
                if (strPage.split("\\?")[0].equals(LogGatherReadUtil.getInstance().getCurrentPage().split("\\?")[0])) {
                    this.mCurrentPage = strPage;
                    return;
                }
                this.mCurrentPage = strPage;
                if (strPage.equals(LogConstans.APP_WELCOME)) {
                    this.mReferer = "-";
                } else {
                    this.mReferer = LogGatherReadUtil.getInstance().getCurrentPage();
                }
                LogGatherReadUtil.getInstance().setCurrentPage(this.mCurrentPage);
                LogGatherReadUtil.getInstance().setReferer(this.mReferer);
            } catch (Exception e) {
                this.mCurrentPage = strPage;
                if (strPage.equals(LogConstans.APP_WELCOME)) {
                    this.mReferer = "-";
                } else {
                    this.mReferer = LogGatherReadUtil.getInstance().getCurrentPage();
                }
                LogGatherReadUtil.getInstance().setCurrentPage(this.mCurrentPage);
                LogGatherReadUtil.getInstance().setReferer(this.mReferer);
            }
        }
    }

    public LogGatherInfo() {
        this.mReferer = "";
        this.mCurrentPage = "";
        this.mPageidentifying = "";
        this.mPageidentifying = UUID.randomUUID().toString();
    }

    public void changeRefererPage() {
        if (!TextUtils.isEmpty(this.mCurrentPage) && !TextUtils.isEmpty(LogGatherReadUtil.getInstance().getCurrentPage()) && !this.mCurrentPage.equals(LogGatherReadUtil.getInstance().getCurrentPage())) {
            this.mReferer = LogGatherReadUtil.getInstance().getCurrentPage();
            LogGatherReadUtil.getInstance().setReferer(this.mReferer);
            LogGatherReadUtil.getInstance().setCurrentPage(this.mCurrentPage);
        }
    }

    public void addLogBtnEvent(String strBtn) {
        addLogBtnEvent(strBtn, null);
    }

    public void addLogBtnEvent(String strBtn, Map<String, String> map) {
        Object map2;
        if (map2 == null) {
            map2 = new HashMap();
        }
        map2.put("btn", strBtn);
        LogGatherReadUtil.getInstance().httpGetLog(LogGatherReadUtil.getInstance().assembleUrl("", "", "", "", "", HttpConstant.BASE_LOG_URL + "btnevent&", "", "", "", "", "", "", "", "", JsonUtil.toJson(map2), this.mCurrentPage, this.mReferer, this.mPageidentifying));
    }

    public void addLocationReadPv(int nSnsID, int nResourceID, int nResourceType, String expendedJson) {
        LogGatherReadUtil instance = LogGatherReadUtil.getInstance();
        instance.httpGetLog(LogGatherReadUtil.getInstance().assembleUrl(nSnsID + "", nResourceID + "", nResourceType + "", "", "", HttpConstant.BASE_LOG_URL + "pv&", "", "", "", "", "", "", "", "", expendedJson, this.mCurrentPage, this.mReferer, this.mPageidentifying));
    }

    public void addLocationClick(int nSnsID, int nResourceID, int nResourceType, String expendedJson) {
        LogGatherReadUtil instance = LogGatherReadUtil.getInstance();
        instance.httpGetLog(LogGatherReadUtil.getInstance().assembleUrl(nSnsID + "", nResourceID + "", nResourceType + "", "", "", HttpConstant.BASE_LOG_URL + "locationclick&", "", "", "", "", "", "", "", "", expendedJson, this.mCurrentPage, this.mReferer, this.mPageidentifying));
    }

    public void addAdPvLog(int nAdId, int nSeat, String expendedJson, boolean isStart) {
        LogGatherReadUtil.getInstance().httpGetLog(LogGatherReadUtil.getInstance().assembleUrl("", "", "", nAdId + "", nSeat + "", HttpConstant.BASE_FENG_AD + "pvLog?action=&", isStart ? getBeijingTime() + "" : "", isStart ? "" : getBeijingTime() + "", "", "", "", "", "", "", expendedJson, this.mCurrentPage, this.mReferer, this.mPageidentifying));
    }

    public void addAdClickLog(int nAdId, int nSeat, String expendedJson) {
        LogGatherReadUtil instance = LogGatherReadUtil.getInstance();
        instance.httpGetLog(LogGatherReadUtil.getInstance().assembleUrl("", "", "", nAdId + "", nSeat + "", HttpConstant.BASE_FENG_AD + "ckLog?action=&", "", "", "", "", "", "", "", "", expendedJson, this.mCurrentPage, this.mReferer, this.mPageidentifying));
    }

    public String getAdFormatUrl(int nAdId, int nSeat, String expendedJson) {
        return LogGatherReadUtil.getInstance().assembleUrl("", "", "", nAdId + "", nSeat + "", HttpConstant.BASE_FENG_AD + "ckLog?action=&", "", "", "", "", "", "", "", "", expendedJson, this.mCurrentPage, this.mReferer, this.mPageidentifying);
    }

    public void addAppStartTime() {
        LogGatherReadUtil.getInstance().httpGetLog(LogGatherReadUtil.getInstance().assembleUrl("", "", "", "", "", HttpConstant.BASE_LOG_URL + "appstarttime&", getBeijingTime() + "", "", "", "", "", "", "", "", "", this.mCurrentPage, this.mReferer, this.mPageidentifying));
    }

    public void addPageInTime() {
        LogGatherReadUtil.getInstance().SetPageidentifying(this.mPageidentifying);
        LogGatherReadUtil.getInstance().httpGetLog(LogGatherReadUtil.getInstance().assembleUrl("", "", "", "", "", HttpConstant.BASE_LOG_URL + "pageintime&", getBeijingTime() + "", "", "", "", "", "", "", "", "", this.mCurrentPage, this.mReferer, this.mPageidentifying));
    }

    public void addPageOutTime() {
        LogGatherReadUtil.getInstance().httpGetLog(LogGatherReadUtil.getInstance().assembleUrl("", "", "", "", "", HttpConstant.BASE_LOG_URL + "pageouttime&", "", getBeijingTime() + "", "", "", "", "", "", "", "", this.mCurrentPage, this.mReferer, this.mPageidentifying));
    }

    public void addPercentageLog(int nSnsID, int nResourceID, int nResourceType, int type, String percent, String expendedJson) {
        LogGatherReadUtil.getInstance().httpGetLog(LogGatherReadUtil.getInstance().assembleUrl(nSnsID + "", nResourceID + "", nResourceType + "", "", "", HttpConstant.BASE_LOG_URL + (type == 0 ? "articlepercentage&" : "videopercentage&"), "", "", type == 0 ? percent : "", type == 1 ? percent : "", "", "", "", "", expendedJson, this.mCurrentPage, this.mReferer, this.mPageidentifying));
    }

    private long getBeijingTime() {
        return System.currentTimeMillis() + ((long) TimeZone.getTimeZone("GMT+8").getRawOffset());
    }
}
