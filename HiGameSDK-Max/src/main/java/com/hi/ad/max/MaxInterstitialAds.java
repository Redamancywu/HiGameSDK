package com.hi.ad.max;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.inters.IInterstitialAdListener;
import com.hi.base.plugin.ad.inters.InterstitialAdAdapter;
import com.hi.base.utils.Constants;

import java.util.concurrent.TimeUnit;

public class MaxInterstitialAds extends InterstitialAdAdapter {
    private HiGameConfig config;
    private Context mContext;
    private String InterstitialAdId;
    private MaxInterstitialAd maxInterstitialAd;
    private IInterstitialAdListener interstitialAdListener;
    private volatile boolean loading = false;
    private volatile boolean ready = false;
    private int retryAttempt;
    @Override
    public void init(Context context, HiGameConfig config) {
        super.init(context, config);
        this.config=config;
        this.mContext=context;
        if (config.contains("inters_pos_id")){
            InterstitialAdId= config.getString("inters_pos_id");
        }
    }

    @Override
    public String getAdId() {
        return InterstitialAdId;
    }

    @Override
    public void setAdListener(IInterstitialAdListener adListener) {
        super.setAdListener(adListener);
        this.interstitialAdListener=adListener;
    }

    @Override
    public void show(Activity context) {
        super.show(context);
        if (maxInterstitialAd.isReady()){
            maxInterstitialAd.showAd();
            if (interstitialAdListener!=null){
                interstitialAdListener.onShow();
            }
        }
    }

    @Override
    public void load(Activity context, String posId) {
        super.load(context, posId);
        if (loading){
            Log.d(Constants.TAG, "MaxInterstitialAds load is loading");
            return;
        }
        loading=true;
        ready=false;
        InterstitialAdId=posId;
        maxInterstitialAd=new MaxInterstitialAd(InterstitialAdId, (Activity) mContext);
        maxInterstitialAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(@NonNull MaxAd maxAd) {
                retryAttempt = 0;
                Log.d(Constants.TAG, "MaxInterstitialAds onAdLoaded");
                if (interstitialAdListener!=null){
                    interstitialAdListener.onLoaded();
                }
            }

            @Override
            public void onAdDisplayed(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdHidden(@NonNull MaxAd maxAd) {
                Log.d(Constants.TAG, "MaxInterstitialAds onAdHidden");
                if (interstitialAdListener!=null){
                    interstitialAdListener.onClosed();
                }
                maxInterstitialAd.loadAd();
            }

            @Override
            public void onAdClicked(@NonNull MaxAd maxAd) {
                Log.d(Constants.TAG, "MaxInterstitialAds onAdClicked");
                if (interstitialAdListener!=null){
                    interstitialAdListener.onClicked();
                }
            }

            @Override
            public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {
                // Interstitial ad failed to load
                // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

                retryAttempt++;
                long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        maxInterstitialAd.loadAd();
                    }
                }, delayMillis );
            }
            @Override
            public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {
                maxInterstitialAd.loadAd();
            }
        });
    }
}
