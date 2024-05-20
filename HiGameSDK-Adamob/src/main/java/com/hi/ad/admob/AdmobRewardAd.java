package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.reward.IRewardAdListener;
import com.hi.base.plugin.ad.reward.RewardAdAdapter;
import com.hi.base.utils.Constants;


/**
 * Admob 激励视频广告实现类
 */
public class AdmobRewardAd extends RewardAdAdapter {

    private volatile boolean loading = false;
    private volatile boolean ready = false;
    private IRewardAdListener rewardAdListener;
    private RewardedAd mRewardVideoAd;
    private HiGameConfig pluginParams;                      //插件参数
    private String RewardAdUnitId;                          //激励视频广告位ID

    @Override
    public void init(Context context, HiGameConfig config) {
        this.pluginParams = config;
        this.loading = false;
        this.ready = false;
        if (config.contains("reward_pos_id")){
            RewardAdUnitId = config.getString("reward_pos_id");
        }
    }

    @Override
    public void setAdListener(IRewardAdListener adListener) {
        super.setAdListener(adListener);
        rewardAdListener=adListener;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void load(Activity context, String posId) {

        if (loading) {
            if (adListener != null) {
                adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "An ad is already loading");
            }
            Log.w(Constants.TAG, "AdmobRewardAd is already loading. ignored");
            return;
        }

        if (ready) {
            Log.w(Constants.TAG, "AdmobRewardAd is already loaded. ignored");
            if (adListener != null) {
                adListener.onLoaded();
            }
            return;
        }

        // 通过广告映射关系，获取admob的激励视频广告位ID
        String adPosId = posId;
        if (pluginParams.contains(posId)) {
            adPosId = pluginParams.getString(posId);
        }

        Log.d(Constants.TAG, "AdmobRewardAd load begin. posId:"+posId+";admob posId:" + posId);
        loading = true;
        ready = false;

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, adPosId,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.e(Constants.TAG, "AdmobRewardAd load failed."+loadAdError.getCode()+";"+loadAdError.getMessage());
                        loading = false;
                        ready = false;
                        mRewardVideoAd = null;
                        if (adListener != null) {
                            adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, loadAdError.getMessage());
                        }
                    }

                    @Override
                    public void onAdLoaded(RewardedAd rewardedAd) {
                        Log.d(Constants.TAG, "AdmobRewardAd load success.");
                        loading = false;
                        ready = true;
                        mRewardVideoAd = rewardedAd;
                        if (adListener != null) {
                            adListener.onLoaded();
                        }
                    }
                });

    }

    @Override
    public void show(Activity context) {

        if (mRewardVideoAd == null) {
            ready = false;
            Log.e(Constants.TAG, "AdmobRewardAd show failed. mRewardVideoAd is null");
            if (adListener != null) {
                adListener.onFailed(Constants.CODE_SHOW_FAILED, "ad is null");
            }
            return;
        }

        mRewardVideoAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                if (adListener != null) {
                    adListener.onClicked();
                }
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                if (adListener != null) {
                    adListener.onClosed();
                }
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                Log.e(Constants.TAG, "AdmobRewardAd failed to show."+adError.getCode()+";"+adError.getMessage());
                if (adListener != null) {
                    adListener.onFailed(Constants.CODE_SHOW_FAILED, adError.getMessage());
                }
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
            }

            @Override
            public void onAdShowedFullScreenContent() {
                if (adListener != null) {
                    adListener.onShow();
                }
            }
        });

        mRewardVideoAd.show(context, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(RewardItem rewardItem) {
                Log.d(Constants.TAG, "AdmobRewardAd onUserEarnedReward called.");
                if (adListener != null) {
                    adListener.onRewarded(rewardItem.getType(), rewardItem.getAmount());
                }
            }
        });

        ready = false;
        mRewardVideoAd = null;

    }
}
