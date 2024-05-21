package com.hi.base.plugin.ad.inters;

import com.hi.base.plugin.ad.IBaseAd;

public interface IInterstitialAd extends IBaseAd {

    void setAdListener(IInterstitialAdListener adListener);

    void close();

    // 清理广告监听器
    void clearAdListener();
}
