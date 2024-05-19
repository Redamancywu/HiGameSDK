package com.hi.base.plugin.ad.reward;


import com.hi.base.plugin.ad.IAdListener;

public interface IRewardAdListener extends IAdListener {

    /**
     * 发放奖励回调
     * @param itemName
     * @param itemNum
     */
    void onRewarded(String itemName, int itemNum);

}
