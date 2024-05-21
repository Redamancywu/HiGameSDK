package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.splash.ISplashAdListener;
import com.hi.base.plugin.ad.splash.SplashAdAdapter;
import com.hi.base.utils.Constants;


/**
 * Admob 开屏广告实现类
 */
public class AdmobSplashAd extends SplashAdAdapter {

    private volatile boolean loading = false;
    private volatile boolean ready = false;

    private AppOpenAd mSplashAd;
    private HiGameConfig pluginParams;                      //插件参数
    private String SplashAdId;                                //开屏广告位ID
    private ISplashAdListener splashAdListener;
    @Override
    public void init(Context context, HiGameConfig config) {
        this.pluginParams = config;
        this.loading = false;
        this.ready = false;
        if (config.contains("splash_pos_id")){
            SplashAdId=config.getString("splash_pos_id");
        }
    }

    @Override
    public void setAdListener(ISplashAdListener adListener) {
        super.setAdListener(adListener);
        splashAdListener=adListener;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void load(Activity context, String posId) {
        posId=SplashAdId;
        if (loading) {
            if (adListener != null) {
                adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "An ad is already loading");
            }
            Log.w(Constants.TAG, "AdmobSplashAd is already loading. ignored");
            return;
        }

        if (ready) {
            Log.w(Constants.TAG, "AdmobSplashAd is already loaded. ignored");
            if (adListener != null) {
                adListener.onLoaded();
            }
            return;
        }

//        // 通过广告映射关系，获取admob的开屏广告位ID
//        String adPosId = posId;
//        if (pluginParams.contains(posId)) {
//            adPosId = pluginParams.getString(posId);
//        }

        Log.d(Constants.TAG, "AdmobSplashAd load begin. posId:"+posId+";admob posId:" + posId);
        loading = true;
        ready = false;

        int orientation = AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT;
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                context, posId, request,
                orientation,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        Log.d(Constants.TAG, "AdmobSplashAd load success.");
                        mSplashAd = ad;
                        loading = false;
                        ready = true;
                        if (adListener != null) {
                            adListener.onLoaded();
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.e(Constants.TAG, "AdmobSplashAd load failed."+loadAdError.getCode()+";"+loadAdError.getMessage());
                        loading = false;
                        ready = false;
                        mSplashAd = null;
                        if (adListener != null) {
                            adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, loadAdError.getMessage());
                        }
                    }
                });
    }

    @Override
    public void show(Activity context) {

        if (mSplashAd == null) {
            Log.e(Constants.TAG, "AdmobSplashAd show failed. mInterstitialAd is null");
            if (adListener != null) {
                adListener.onFailed(Constants.CODE_SHOW_FAILED, "ad is null");
            }
            return;
        }

        mSplashAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                if (adListener != null) {
                    adListener.onClosed();
                }
            }

            public void onAdFailedToShowFullScreenContent(AdError adError) {
                Log.e(Constants.TAG, "AdmobSplashAd failed to show."+adError.getCode()+";"+adError.getMessage());
                if (adListener != null) {
                    adListener.onFailed(Constants.CODE_SHOW_FAILED, adError.getMessage());
                }
            }

            @Override
            public void onAdShowedFullScreenContent() {
                if (adListener != null) {
                    adListener.onShow();
                }
            }
        });

        ready = false;
        mSplashAd.show(context);
        mSplashAd = null;
    }

    @Override
    public String getAdId() {
        return SplashAdId;
    }
}
