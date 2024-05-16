package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.print.PageRange;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.itf.HiBaseAdlistener;
import com.hi.base.plugin.itf.base.HiBBaseAd;
import com.hi.base.utils.Constants;

public class AdmobBannerAd extends HiBBaseAd {
    private volatile boolean loading = false;
    private volatile boolean ready = false;
    private Context mContext;
    private Activity mActivity;
    private AdView bannerAd;
    private HiGameConfig config;
    private String ADMOB_BANNER_ID;
    private HiBaseAdlistener adlistener;
    @Override
    public void init(Context context, HiGameConfig config) {
        mContext=context;
        mActivity= (Activity) context;
        if (config.contains("admob_banner_id")){
            ADMOB_BANNER_ID=config.getString("admob_banner_id");
        }
        Log.d(Constants.TAG,"AdmobBannerAd init ADMOB_BANNER_ID="+ADMOB_BANNER_ID);
    }

    @Override
    public void onCreate(Activity activity) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void load(String posId) {
        Log.d(Constants.TAG,"AdmobBannerAd load posId="+posId);
        if (loading){
            Log.d(Constants.TAG,"AdmobBannerAd load is loading");
            return;
        }
        loading=true;
        ready=false;
        bannerAd=new AdView(mContext);
        bannerAd.setAdUnitId(ADMOB_BANNER_ID);
        bannerAd.setAdSize(new AdSize(320, 50));
        bannerAd.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                if (adlistener!=null){
                    adlistener.onAdClick();
                }

            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if (adlistener!=null){
                    adlistener.onAdClose();
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                if (adlistener!=null){
                    adlistener.onAdFailed(loadAdError.getMessage());
                }
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                loading=false;
                if (adlistener!=null){
                    adlistener.onAdLoaded();
                }
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                if (adlistener!=null){
                    adlistener.onAdShow();
                }
            }

            @Override
            public void onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked();
                if (adlistener!=null){
                    adlistener.onAdClick();
                }
            }
        });
        AdRequest adRequest=new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);

    }

    @Override
    public void show() {

    }

    @Override
    public void close() {

    }

    @Override
    public void setListener(HiBaseAdlistener listener) {

    }


    @Override
    public View setAdContainer() {
        return bannerAd;
    }
}
