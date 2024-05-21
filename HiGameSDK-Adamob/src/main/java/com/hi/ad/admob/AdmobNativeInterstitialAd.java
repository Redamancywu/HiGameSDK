package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.inters.IInterstitialAdListener;
import com.hi.base.plugin.ad.inters.InterstitialAdAdapter;
import com.hi.base.utils.Constants;


public class AdmobNativeInterstitialAd extends InterstitialAdAdapter {
    private NativeAd nativeAd;
    private volatile boolean ready = false;
    private volatile boolean loading = false;
    private IInterstitialAdListener interstitialAdListener;
    private HiGameConfig config;                      // 插件参数
    private Context mContext;
    private String NativeAdId;

    @Override
    public void init(Context context, HiGameConfig config) {
        super.init(context, config);
        mContext = context;
        this.config = config;
        NativeAdId = config.getString("native_inters_pos_id");
    }

    @Override
    public void load(Activity context, String posId) {
        super.load(context, posId);

        if (loading || ready) {
            return;
        }
        loading = true;
        NativeAdId=posId;
        Log.d(Constants.TAG, "NativeAdId: " + posId);
        AdLoader adLoader = new AdLoader.Builder(mContext, posId)
                .forNativeAd(nativeAd -> {
                    if (nativeAd != null) {
                        this.nativeAd = nativeAd;
                        ready = true;
                        loading = false;
                        if (interstitialAdListener != null) {
                            interstitialAdListener.onLoaded();
                        }
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e(Constants.TAG, "Native Ad failed to load: " + adError.getMessage());
                        loading = false;
                        if (interstitialAdListener != null) {
                            interstitialAdListener.onLoadFailed(adError.getCode(), adError.getMessage());
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
        View adView = inflater.inflate(R.layout.native_interstitial_ad_layout, null);
        populateNativeAdView(nativeAd, (NativeAdView) adView.findViewById(R.id.native_ad_view));

        // 设置关闭按钮
        ImageButton closeButton = adView.findViewById(R.id.ad_close_button);
        closeButton.setOnClickListener(v -> {
            close();
            if (interstitialAdListener != null) {
                interstitialAdListener.onClosed();
            }
        });

        context.addContentView(adView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        if (interstitialAdListener != null) {
            interstitialAdListener.onShow();
        }
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
    public void setAdListener(IInterstitialAdListener adListener) {
        this.interstitialAdListener = adListener;
    }

    @Override
    public void close() {
        super.close();
        // Add any additional close logic here
        if (interstitialAdListener != null) {
            interstitialAdListener.onClosed();
        }
    }

    @Override
    public String getAdId() {
        return NativeAdId;
    }
}
