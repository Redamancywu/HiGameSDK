package com.hi.base.plugin.itf;

public interface HiBaseAdlistener {
    void onAdShow();
    void onAdLoaded();
    void onAdFailed(String errorMsg);
    void onAdReady();
    void onAdClick();
    void onAdClose();
    void onAdReward();
    void onAdRevenue();
    void onAdRevenue(Object obj);
}
