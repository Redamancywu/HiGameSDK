package com.hi.ad.max;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdkUtils;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.banner.BannerAdApter;
import com.hi.base.plugin.ad.banner.IBannerListener;
import com.hi.base.utils.Constants;
public class MaxBannerAd extends BannerAdApter {
    private Context mContext;
    private HiGameConfig mConfig;
    private String bannerPosId;
    private ViewGroup bannerContainer;
    private volatile boolean loading = false;
    private volatile boolean ready = false;
    private MaxAdView adView;
    private IBannerListener bannerListener;
    @Override
    public void init(Context context, HiGameConfig config) {
        super.init(context, config);
        this.mConfig=config;
        this.mContext=context;
        if (config.contains("banner_pos_id")) {
            bannerPosId = config.getString("banner_pos_id");
        }
    }

    @Override
    public String getAdId() {
        return bannerPosId;
    }

    @Override
    public void setAdListener(IBannerListener adListener) {
        super.setAdListener(adListener);
        bannerListener=adListener;
    }

    @Override
    public void show(Activity context) {
        super.show(context);
        Log.d(Constants.TAG,"MaxBannerAd show");
        if (adView==null){
            adView=new MaxAdView(bannerPosId,context);
            bannerContainer.addView(adView);
            if (bannerListener!=null){
                bannerListener.onShow();
            }
        }
    }

    @Override
    public void load(Activity context, String posId) {
        super.load(context, posId);
        if (loading){
            Log.d(Constants.TAG,"MaxBannerAd load is loading");

            return;
        }
        bannerPosId = posId;
        loading = true;
        ready=false;
        if (adView==null){
            adView=new MaxAdView(posId,context);
            // Stretch to the width of the screen for banners to be fully functional
            int width = ViewGroup.LayoutParams.MATCH_PARENT;

            // Get the adaptive banner height.
            int heightDp = MaxAdFormat.BANNER.getAdaptiveSize(context ).getHeight();
            int heightPx = AppLovinSdkUtils.dpToPx( context, heightDp );
            adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
            adView.setExtraParameter( "adaptive_banner", "true" );
            adView.setLocalExtraParameter( "adaptive_banner_width", 400 );
            adView.getAdFormat().getAdaptiveSize( 400, context ).getHeight(); // Set your ad height to this value
//            int heightDp = MaxAdFormat.BANNER.getAdaptiveSize((Activity) mContext).getHeight();
//            int heightPx = AppLovinSdkUtils.dpToPx(mContext, heightDp);
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            adView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
            adView.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(@NonNull MaxAd maxAd) {
                    Log.d(Constants.TAG,"MaxBannerAd onAdExpanded");
                }

                @Override
                public void onAdCollapsed(@NonNull MaxAd maxAd) {
                    Log.d(Constants.TAG,"MaxBannerAd onAdCollapsed");
                }

                @Override
                public void onAdLoaded(@NonNull MaxAd maxAd) {
                    loading = false;
                    ready = true;
                    Log.d(Constants.TAG,"MaxBannerAd onAdLoaded");
                    if (bannerListener!=null){
                        bannerListener.onLoaded();
                    }
                }

                @Override
                public void onAdDisplayed(@NonNull MaxAd maxAd) {

                    Log.d(Constants.TAG,"MaxBannerAd onAdDisplayed");
                }

                @Override
                public void onAdHidden(@NonNull MaxAd maxAd) {

                    ready = false;
                    if (bannerListener != null) {
                        bannerListener.onClosed();
                    }
                    Log.d(Constants.TAG,"MaxBannerAd onAdHidden");
                }

                @Override
                public void onAdClicked(@NonNull MaxAd maxAd) {

                    Log.d(Constants.TAG,"MaxBannerAd onAdClicked");
                    if (bannerListener!=null){
                        bannerListener.onClicked();
                    }
                }

                @Override
                public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {

                    Log.d(Constants.TAG,"MaxBannerAd onAdLoadFailed");

                    if (bannerListener!=null){

                        bannerListener.onLoadFailed(s.hashCode(),maxError.getMessage());
                    }
                }

                @Override
                public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {

                    Log.d(Constants.TAG,"MaxBannerAd onAdDisplayFailed");

                    if (bannerListener!=null){
                        bannerListener.onLoadFailed(maxAd.hashCode(),maxError.getMessage());
                    }
                }
            });

        }
    }
    @Override
    public void close() {
        super.close();
        if (bannerContainer != null){
            adView.setExtraParameter( "allow_pause_auto_refresh_immediately", "true" );
            adView.stopAutoRefresh();
            ready = false;
            bannerContainer.removeAllViews();
            adView.destroy();
            bannerContainer = null;
            if (bannerListener != null) {
                bannerListener.onClosed();
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        // Set this extra parameter to work around SDK bug that ignores calls to stopAutoRefresh()
        adView.setExtraParameter( "allow_pause_auto_refresh_immediately", "true" );
        adView.stopAutoRefresh();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        adView.destroy();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        adView.startAutoRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        adView.loadAd();
    }
}
