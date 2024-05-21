package com.hi.ad.max;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.hi.ad.R;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.AdApter;
import com.hi.base.plugin.ad.AdContainer;
import com.hi.base.plugin.ad.banner.BannerAdApter;
import com.hi.base.plugin.ad.banner.IBannerListener;
import com.hi.base.utils.Constants;

public class MaxNativeBannerAd extends BannerAdApter {
    private Context mContext;
    private HiGameConfig mConfig;
    private String bannerPosId;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxNativeAdView mNativeAdView;

    private volatile boolean loading = false;
    private volatile boolean ready = false;
    private ViewGroup bannerContainer;

    private IBannerListener bannerListener;

    @Override
    public void init(Context context, HiGameConfig config) {
        super.init(context, config);
        mContext = context;
        mConfig = config;
        bannerPosId = config.getString("native_banner_pos_id");
    }

    @Override
    public String getAdId() {
        return bannerPosId;
    }

    @Override
    public void load(Activity context, String posId) {
        super.load(context, posId);
        if (loading || ready) {
            return;
        }
        loading = true;
        bannerPosId=posId;
        nativeAdLoader = new MaxNativeAdLoader(posId, mContext);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(@Nullable MaxNativeAdView maxNativeAdView, @NonNull MaxAd maxAd) {
                ready = true;
                loading = false;
                mNativeAdView = maxNativeAdView;
                if (bannerListener != null) {
                    bannerListener.onLoaded();
                }
            }

            @Override
            public void onNativeAdLoadFailed(@NonNull String adUnitId, @NonNull MaxError error) {
                loading = false;
                Log.e(Constants.TAG, "Native Banner Ad failed to load: " + error.getMessage());
                if (bannerListener != null) {
                    bannerListener.onLoadFailed(error.getCode(), error.getMessage());
                }
            }

            @Override
            public void onNativeAdClicked(@NonNull MaxAd maxAd) {
                if (bannerListener != null) {
                    bannerListener.onClicked();
                }
            }

        });
        nativeAdLoader.loadAd();
    }

    @Override
    public void show(Activity context) {
        super.show(context);
        if (!ready || mNativeAdView == null) {
            Log.e(Constants.TAG, "The native banner ad is not ready to be shown.");
            return;
        }

        bannerContainer = context.findViewById(R.id.ad_info_container);
        if (bannerContainer != null) {
            bannerContainer.removeAllViews();
            bannerContainer.addView(mNativeAdView);

            if (bannerListener != null) {
                bannerListener.onShow();
            }
        }
    }

    @Override
    public void close() {
        super.close();
        if (bannerContainer != null) {
            bannerContainer.removeAllViews();
        }
        if (mNativeAdView != null) {
            AdContainer.destroySelf(mNativeAdView);
            mNativeAdView = null;
        }
        ready = false;
        loading = false;
    }

    @Override
    public void setAdListener(IBannerListener adListener) {
        this.bannerListener = adListener;
    }

    @Override
    public boolean isReady() {
        return ready && mNativeAdView != null;
    }
}
