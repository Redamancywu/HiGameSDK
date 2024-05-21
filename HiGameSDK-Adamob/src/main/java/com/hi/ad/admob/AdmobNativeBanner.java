package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.banner.BannerAdApter;
import com.hi.base.plugin.ad.banner.IBannerListener;
import com.hi.base.utils.Constants;
import com.hi.higamesdk_adamob.R;

public class AdmobNativeBanner extends BannerAdApter {
    private HiGameConfig config;
    private Context mContext;
    private String NativeAdUnitId;
    private IBannerListener BannerAdListener;
    private NativeAd nativeAd;
    private volatile boolean ready = false;
    private volatile boolean loading = false;

    @Override
    public void init(Context context, HiGameConfig config) {
        super.init(context, config);
        mContext = context;
        this.config = config;
        NativeAdUnitId = config.getString("native_banner_pos_id");
    }

    @Override
    public void load(Activity context, String posId) {
        super.load(context, posId);

        AdLoader adLoader = new AdLoader.Builder(mContext, NativeAdUnitId)
                .forNativeAd(nativeAd -> {
                    if (nativeAd != null) {
                        this.nativeAd = nativeAd;
                        ready = true;
                        if (BannerAdListener != null) {
                            BannerAdListener.onLoaded();
                        }
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e(Constants.TAG, "Native Ad failed to load: " + adError.getMessage());
                        if (BannerAdListener != null) {
                            BannerAdListener.onLoadFailed(adError.getCode(), adError.getMessage());
                        }
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void show(Activity context) {
        super.show(context);
        if (!ready || nativeAd == null) {
            Log.e(Constants.TAG, "The native ad is not ready to be shown.");
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View adView = inflater.inflate(R.layout.native_banner_ad_layout, null);
        populateNativeAdView(nativeAd, (NativeAdView) adView.findViewById(R.id.native_ad_view));
        if (BannerAdListener != null) {
            BannerAdListener.onShow();
        }
        // Add the ad view to the activity's content view
        context.addContentView(adView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
    }
    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        // Assign native ad assets to the views
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setMediaView(adView.findViewById(R.id.ad_image));

        // Set the text, images, and the native ad, etc.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() != null) {
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            adView.getBodyView().setVisibility(View.VISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        }

        if (nativeAd.getCallToAction() != null) {
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            adView.getCallToActionView().setVisibility(View.VISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        }

        if (nativeAd.getIcon() != null) {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        } else {
            adView.getIconView().setVisibility(View.GONE);
        }

        // MediaView can be a placeholder for either image or video
        if (nativeAd.getMediaContent() != null) {
            adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        }

        // Assign native ad object to the native view
        adView.setNativeAd(nativeAd);
    }

    @Override
    public boolean isReady() {
        return ready && nativeAd != null;
    }

    @Override
    public void setAdListener(IBannerListener adListener) {
        this.BannerAdListener = adListener;
    }

    @Override
    public void close() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            FrameLayout adContainer = activity.findViewById(R.id.native_ad_view);
            adContainer.removeAllViews();
            if (BannerAdListener != null) {
                BannerAdListener.onClosed();
            }
        }
        super.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        close();
    }

    @Override
    public String getAdId() {
        return NativeAdUnitId;
    }
}
