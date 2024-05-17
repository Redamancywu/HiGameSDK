package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.inters.InterstitialAdAdapter;
import com.hi.base.utils.Constants;


/**
 * Admob 插屏广告实现类
 */
public abstract class AdmobInterstitialAd extends InterstitialAdAdapter {

    private volatile boolean loading = false;
    private volatile boolean ready = false;

    private InterstitialAd mInterstitialAd;
    private HiGameConfig pluginParams;                      //插件参数
    private String InterstitialAdId;                       //插屏广告位ID

    @Override
    public void init(Context context, HiGameConfig params) {
        this.pluginParams = params;
        this.loading = false;
        this.ready = false;
        if (params.contains("interstitial_pos_id")){
            InterstitialAdId= params.getString("interstitial_pos_id");
        }
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
            Log.w(Constants.TAG, "AdmobInterstitialAd is already loading. ignored");
            return;
        }

        if (ready) {
            Log.w(Constants.TAG, "AdmobInterstitialAd is already loaded. ignored");
            if (adListener != null) {
                adListener.onLoaded();
            }
            return;
        }
        Log.d(Constants.TAG, "AdmobInterstitialAd load begin. posId:"+posId+";admob posId:" + posId);
        loading = true;
        ready = false;
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, InterstitialAdId, adRequest,
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

        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdClicked() {
                if (adListener != null) {
                    adListener.onClicked();
                }
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                if (adListener != null) {
                    adListener.onClosed();
                }

            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e(Constants.TAG, "AdmobInterstitialAd failed to show."+adError.getCode()+";"+adError.getMessage());

                if (adListener != null) {
                    adListener.onFailed(Constants.CODE_SHOW_FAILED, adError.getMessage());
                }
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad

            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                if (adListener != null) {
                    adListener.onShow();
                }
            }
        });
        ready = false;
        mInterstitialAd.show(context);
        mInterstitialAd = null;
    }
}
