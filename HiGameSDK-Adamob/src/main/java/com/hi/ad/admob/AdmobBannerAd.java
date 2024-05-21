package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.AdContainer;
import com.hi.base.plugin.ad.banner.BannerAdApter;
import com.hi.base.plugin.ad.banner.IBannerListener;
import com.hi.base.utils.Constants;

public class AdmobBannerAd extends BannerAdApter {
    private volatile boolean loading = false;
    private volatile boolean ready = false;

    private AdView bannerAd;
    private HiGameConfig config;
    private String bannerPosId;
    private ViewGroup bannerContainer;
    private IBannerListener bannerAdListener;

    @Override
    public void init(Context context, HiGameConfig config) {
        this.config = config;
        if (config != null && config.contains("banner_pos_id")) {
            bannerPosId = config.getString("banner_pos_id");
        }
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void load(Activity context, String posId) {
        if (loading) {
            Log.w(Constants.TAG, "AdmobBannerAd is already loading. Ignored.");
            return;
        }
        Log.d(Constants.TAG, "AdmobBannerAd load begin. posId: " + bannerPosId);
        loading = true;
        ready = false;
        posId=bannerPosId;
        if (bannerAd==null){
            bannerAd = new AdView(context);
            bannerAd.setAdUnitId(posId);
            if (adSize != null) {
                bannerAd.setAdSize(new AdSize(adSize.getWidth(), adSize.getHeight()));
            } else {
                bannerAd.setAdSize(AdSize.BANNER);
            }
            bannerAd.setAdListener(new AdListener() {
                @Override
                public void onAdClicked() {
                    if (bannerAdListener != null) {
                        bannerAdListener.onClicked();
                    }
                }

                @Override
                public void onAdClosed() {
                    if (bannerAdListener != null) {
                        bannerAdListener.onClosed();
                    }
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    loading = false;
                    Log.e(Constants.TAG, "AdmobBannerAd load failed: " + loadAdError.getCode() + "; " + loadAdError.getMessage());
                    if (bannerAdListener != null) {
                        bannerAdListener.onLoadFailed(Constants.CODE_LOAD_FAILED, loadAdError.getMessage());
                    }
                }

                @Override
                public void onAdLoaded() {
                    Log.d(Constants.TAG, "AdmobBannerAd load success.");
                    loading = false;
                    ready = true;
                    if (bannerAdListener != null) {
                        bannerAdListener.onLoaded();
                    }
                }

                @Override
                public void onAdOpened() {
                    if (bannerAdListener != null) {
                        bannerAdListener.onShow();
                    }
                }
            });
            // 加载广告
            AdRequest adRequest = new AdRequest.Builder().build();
            bannerAd.loadAd(adRequest);
        }

    }

    @Override
    public void show(Activity context) {
        super.show(context);
        Log.d(Constants.TAG, "AdmobBannerAd show begin. posId: " + bannerPosId);
        if (bannerAd != null && bannerContainer != null) {
            bannerContainer.removeAllViews();
            bannerContainer.addView(bannerAd);
            if (bannerAdListener != null) {
                bannerAdListener.onShow();
            }
        } else {
            if (bannerAd == null) {
                Log.e(Constants.TAG, "Unable to show banner ad. bannerAd is null.");
            }
            if (bannerContainer == null) {
                Log.e(Constants.TAG, "Unable to show banner ad. bannerContainer is null.");
            }
        }
    }



    @Override
    public View getBannerView() {
        return bannerAd;
    }

    @Override
    public void setAdListener(IBannerListener adListener) {
        super.setAdListener(adListener);
        this.bannerAdListener = adListener;
    }

    @Override
    public String getAdId() {
        return bannerPosId;
    }
}
