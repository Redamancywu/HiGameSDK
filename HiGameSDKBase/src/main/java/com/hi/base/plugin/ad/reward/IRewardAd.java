package com.hi.base.plugin.ad.reward;


import com.hi.base.plugin.ad.IBaseAd;

/**
 * 激励视频广告插件
 */
public interface IRewardAd extends IBaseAd {

    void setAdListener(IRewardAdListener adListener);

    // 清理广告监听器
    void clearAdListener();

}
