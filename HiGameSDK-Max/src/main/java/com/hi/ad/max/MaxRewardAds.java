package com.hi.ad.max;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdRevenueListener;
import com.applovin.mediation.MaxAdReviewListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.reward.IRewardAdListener;
import com.hi.base.plugin.ad.reward.RewardAdAdapter;
import com.hi.base.pub.HiGameSDK;
import com.hi.base.utils.Constants;

import java.util.HashMap;

public class MaxRewardAds extends RewardAdAdapter {
    private Context mContext;
    private HiGameConfig config;
    private String RewardAdUnitId;                          //激励视频广告位ID
    private IRewardAdListener rewardAdListener;
    private volatile boolean loading = false;
    private volatile boolean ready = false;
    private MaxRewardedAd rewardedAd;

    @Override
    public void init(Context context, HiGameConfig config) {
        super.init(context, config);
        this.config=config;
        this.mContext=context;
        if (config.contains("reward_pos_id")){
            RewardAdUnitId = config.getString("reward_pos_id");
        }
    }

    @Override
    public void load(Activity context, String posId) {
        super.load(context, posId);
        if (loading) {
            if (adListener != null) {
                adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "An ad is already loading");
            }
            Log.w(Constants.TAG, "AdmobRewardAd is already loading. ignored");
            return;
        }
        RewardAdUnitId=posId;
        loading = true;
        ready=false;
        if (rewardedAd==null){
            rewardedAd=MaxRewardedAd.getInstance(posId, (Activity) mContext);
            rewardedAd.setListener(new MaxRewardedAdListener() {
                @Override
                public void onUserRewarded(@NonNull MaxAd maxAd, @NonNull MaxReward maxReward) {
                    Log.d(Constants.TAG, "onUserRewarded: ");
                    if (adListener != null) {
                        adListener.onRewarded(maxAd.getDspName(), maxReward.getAmount());
                    }
                }

                @Override
                public void onAdLoaded(@NonNull MaxAd maxAd) {
                    loading = false;
                    ready = true;
                    if (adListener != null) {
                        adListener.onLoaded();
                    }
                    Log.d(Constants.TAG, "onAdLoaded: ");
                }

                @Override
                public void onAdDisplayed(@NonNull MaxAd maxAd) {
                    Log.d(Constants.TAG, "MaxRewardAds onAdDisplayed: ");
                    if (adListener!=null){
                        adListener.onShow();
                    }
                }

                @Override
                public void onAdHidden(@NonNull MaxAd maxAd) {
                    Log.d(Constants.TAG, "MaxRewardAds onAdHidden: ");
                    if (adListener!=null){
                        adListener.onClosed();
                    }
                }

                @Override
                public void onAdClicked(@NonNull MaxAd maxAd) {
                    Log.d(Constants.TAG, "MaxRewardAds onAdClicked: ");
                    if (adListener!=null){
                        adListener.onClicked();
                    }
                }

                @Override
                public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {
                    loading = false;
                    Log.d(Constants.TAG, "MaxRewardAds onAdLoadFailed: ");
                    if (adListener != null){
                        adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, maxError.getMessage());
                    }
                }

                @Override
                public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {
                        if (adListener != null){
                            adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, maxError.getMessage());
                        }
                }
            });
         rewardedAd.setRevenueListener(new MaxAdRevenueListener() {
             @Override
             public void onAdRevenuePaid(@NonNull MaxAd maxAd) {
                 Log.d(Constants.TAG, "onAdRevenuePaid: "+maxAd.getRevenue());
                 // 创建事件数据 HashMap
                 HashMap<String, Object> eventData = new HashMap<>();
                 eventData.put("ad_type", maxAd.getDspName());
                 eventData.put("ad_placement", maxAd.getPlacement());
                 eventData.put("ad_network", maxAd.getNetworkName());
                 eventData.put("ad_unit_id", maxAd.getAdUnitId());
                 eventData.put("revenue", maxAd.getRevenue());
                 HiGameSDK.getInstance().onCustomEvent("onAdRevenuePaid", eventData);
             }
         });
        }
        rewardedAd.loadAd();
    }
    @Override
    public boolean isReady() {
        return rewardedAd != null && rewardedAd.isReady();
    }


    @Override
    public void show(Activity context) {
        super.show(context);
        if (rewardedAd != null && rewardedAd.isReady()){
            rewardedAd.showAd();
        }
    }

    @Override
    public String getAdId() {
        return RewardAdUnitId;
    }
}
