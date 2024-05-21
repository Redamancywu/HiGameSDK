package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.inters.IInterstitialAdListener;
import com.hi.base.plugin.ad.inters.InterstitialAdAdapter;
import com.hi.base.utils.Constants;


/**
 * Admob 插屏广告实现类
 */
public  class AdmobInterstitialAd extends InterstitialAdAdapter {

    private volatile boolean loading = false;
    private volatile boolean ready = false;
    private IInterstitialAdListener interstitialAdListener;
    private InterstitialAd mInterstitialAd;
    private HiGameConfig pluginParams;                      //插件参数
    private String InterstitialAdId;                       //插屏广告位ID

    @Override
    public void init(Context context, HiGameConfig params) {
        this.pluginParams = params;
        this.loading = false;
        this.ready = false;
        if (params.contains("inters_pos_id")){
            InterstitialAdId= params.getString("inters_pos_id");
        }
    }
    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void setAdListener(IInterstitialAdListener adListener) {
        super.setAdListener(adListener);
        interstitialAdListener=adListener;
    }

    @Override
    public void load(Activity context, String posId) {
       posId=InterstitialAdId;
        if (loading) {
            if (adListener != null) {
                adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "An ad is already loading");
            }
            Log.w(Constants.TAG, "AdmobInterstitialAd is already loading. ignored");
            return;
        }
        Log.d(Constants.TAG, "AdmobInterstitialAd load begin. posId:"+posId+";admob posId:" + posId);
        loading = true;
        ready = false;
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, posId, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        Log.d(Constants.TAG, "AdmobInterstitialAd load success.");
                        mInterstitialAd = interstitialAd;
                        loading = false;
                        ready = true;
                        if (adListener != null) {
                            adListener.onLoaded();
                        }

                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        // Handle the error
                        Log.e(Constants.TAG, "AdmobInterstitialAd load failed."+loadAdError.getCode()+";"+loadAdError.getMessage());
                        loading = false;
                        ready = false;
                        mInterstitialAd = null;
                        if (adListener != null) {
                            adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, loadAdError.getMessage());
                        }
                    }
                });
    }

    @Override
    public void show(Activity context) {
        if (mInterstitialAd == null) {
            Log.e(Constants.TAG, "AdmobInterstitialAd show failed. mInterstitialAd is null");
            if (adListener != null) {
                adListener.onFailed(Constants.CODE_SHOW_FAILED, "ad is null");
            }
            return;
        }

        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                if (adListener != null) {
                    adListener.onClicked();
                }
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                if (adListener != null) {
                    adListener.onClosed();
                }
                mInterstitialAd = null; // 只在广告展示后设为null
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                Log.e(Constants.TAG, "AdmobInterstitialAd failed to show." + adError.getCode() + ";" + adError.getMessage());
                if (adListener != null) {
                    adListener.onFailed(Constants.CODE_SHOW_FAILED, adError.getMessage());
                }
                mInterstitialAd = null;
            }

            @Override
            public void onAdImpression() {
                Log.d(Constants.TAG, "AdmobInterstitialAd onAdImpression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                if (adListener != null) {
                    adListener.onShow();
                }
                ready = false; // 广告展示后将ready设为false
            }
        });

        Log.d(Constants.TAG, "AdmobInterstitialAd showing ad.");
        mInterstitialAd.show(context);
    }



    @Override
    public String getAdId() {
        return InterstitialAdId;
    }
}
