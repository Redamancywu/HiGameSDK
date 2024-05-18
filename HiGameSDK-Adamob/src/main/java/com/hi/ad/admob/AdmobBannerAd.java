package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.AdApter;
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

    @Override
    public void init(Context context, HiGameConfig config) {
        this.loading = false;
        this.config = config;
        if (config.contains("banner_pos_id")) {
            bannerPosId = config.getString("banner_pos_id");
        }
    }

    @Override
    public void onCreate(Activity activity) { }

    @Override
    public void onStart() { }

    @Override
    public void onStop() { }

    @Override
    public void onRestart() { }

    @Override
    public void onResume() { }

    @Override
    public void onPause() { }

    @Override
    public void onDestroy() { }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { }

    @Override
    public void onNewIntent(Intent intent) { }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void load(Activity context, String posId) {
        posId = bannerPosId;  // 确保使用正确的广告单元 ID
        if (loading) {
            if (BannerAdListener != null) {
                BannerAdListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "An ad is already loading");
            }
            Log.w(Constants.TAG, "AdmobBannerAd is already loading. Ignored.");
            return;
        }

        Log.d(Constants.TAG, "AdmobBannerAd load begin. posId: " + posId + "; admob posId: " + posId);
        loading = true;
        ready = false;

        bannerAd = new AdView(context);
        if (adSize != null) {
            bannerAd.setAdSize(new AdSize(adSize.getWidth(), adSize.getHeight()));
        } else {
            bannerAd.setAdSize(AdSize.BANNER);
        }
        bannerAd.setAdUnitId(posId); // 使用本地广告单元 ID

        bannerAd.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                if (BannerAdListener != null) {
                    BannerAdListener.onClicked();
                }
            }

            @Override
            public void onAdClosed() {
                if (BannerAdListener != null) {
                    BannerAdListener.onClosed();
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                loading = false;
                Log.e(Constants.TAG, "AdmobBannerAd load failed: " + loadAdError.getCode() + "; " + loadAdError.getMessage());
                if (BannerAdListener != null) {
                    BannerAdListener.onLoadFailed(Constants.CODE_LOAD_FAILED, loadAdError.getMessage());
                }
            }

            @Override
            public void onAdLoaded() {
                Log.d(Constants.TAG, "AdmobBannerAd load success.");
                loading = false;
                ready = true;
                if (BannerAdListener != null) {
                    BannerAdListener.onLoaded();
                }
            }

            @Override
            public void onAdOpened() {
                if (BannerAdListener != null) {
                    BannerAdListener.onShow();
                }
            }
        });

        // 加载广告
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);
    }

    @Override
    public void show(Activity context) { }

    @Override
    public View getBannerView() {
        return bannerAd;
    }
}
