package com.hi.base.plugin.ad;

/**
 * 广告回调监听接口
 */
public interface HiAdListener {
    /**
     * 广告展示
     * @param type
     */
    void onAdShow(String type);

    /**
     * 广告加载失败
     * @param type
     * @param errorMsg
     */
    void onAdFailed(String type, String errorMsg);

    /**
     * 广告加载成功
     * @param type
     */
    void onAdReady(String type);

    /**
     * 广告点击
     * @param type
     */
    void onAdClick(String type);

    /**
     * 广告关闭
     * @param type
     */
    void onAdClose(String type);

    /**
     * 广告奖励发放
     * @param type
     */
    void onAdReward(String type);

    /**
     * 广告价值
     * @param type
     */
    void onAdRevenue(String type);

}
