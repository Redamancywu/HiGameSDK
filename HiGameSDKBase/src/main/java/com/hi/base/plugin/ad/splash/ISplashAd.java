package com.hi.base.plugin.ad.splash;


import com.hi.base.plugin.ad.IBaseAd;

/**
 * 开屏广告插件
 */
public interface ISplashAd extends IBaseAd {

    void setAdListener(ISplashAdListener adListener);

    // 清理广告监听器
    void clearAdListener();

}
