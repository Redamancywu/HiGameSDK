package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.itf.HiBaseAdlistener;
import com.hi.base.plugin.itf.base.HiBBaseAd;
import com.hi.base.utils.Constants;

public class AdmobInterstitialAd extends HiBBaseAd {
    private volatile boolean loading = false;
    private volatile boolean ready = false;
    private HiBaseAdlistener adlistener;
    private Context mContext;
    private Activity mActivity;
    private String InterstitialAdId;
    private InterstitialAd mInterstitialAd;
    @Override
    public void init(Context context, HiGameConfig config) {
        mContext = context;
        mActivity = (Activity) context;
        //初始化
        if (config.contains("admob_interstitial_ad_id")){
            InterstitialAdId= config.getString("admob_interstitial_ad_id");
        }


    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void load(String posId) {
        if (loading) {
            if (adlistener != null) {
                adlistener.onAdFailed("An ad is already loading");
            }
            Log.w(Constants.TAG, "AdmobInterstitialAd is already loading. ignored");
            return;
        }

        if (ready) {
            Log.w(Constants.TAG, "AdmobInterstitialAd is already loaded. ignored");
            if (adlistener != null) {
                adlistener.onAdLoaded();
            }
            return;
        }
        Log.d(Constants.TAG, "AdmobInterstitialAd load begin. posId:"+posId+";admob posId:" + posId);
        loading = true;
        ready = false;
        AdRequest adRequest=new AdRequest.Builder().build();
        InterstitialAd.load(mContext, InterstitialAdId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                loading = false;
                ready=false;
                if (adlistener != null) {
                    adlistener.onAdFailed(loadAdError.getMessage());
                }
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                loading=false;
                ready=true;
                if (adlistener!=null){
                    adlistener.onAdLoaded();
                }
            }
        });
    }

    @Override
    public void show() {
        if (mInterstitialAd == null) {
            Log.e(Constants.TAG, "AdmobInterstitialAd show failed. mInterstitialAd is null");
            if (adlistener != null) {
                adlistener.onAdFailed("ad is null");
            }
            return;
        }

        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdClicked() {
                if (adlistener != null) {
                    adlistener.onAdClick();
                }
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                if (adlistener != null) {
                    adlistener.onAdClose();
                }

            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e(Constants.TAG, "AdmobInterstitialAd failed to show."+adError.getCode()+";"+adError.getMessage());

                if (adlistener != null) {
                    adlistener.onAdFailed(adError.getMessage());
                }
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad

            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                if (adlistener != null) {
                    adlistener.onAdShow();
                }
            }
        });
        ready = false;
        mInterstitialAd.show(mActivity);
        mInterstitialAd = null;
    }

    @Override
    public void close() {

    }

    @Override
    public void setListener(HiBaseAdlistener listener) {

    }

    @Override
    public View setAdContainer() {
        return null;
    }
}
