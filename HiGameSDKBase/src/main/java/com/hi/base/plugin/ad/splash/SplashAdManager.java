package com.hi.base.plugin.ad.splash;

import android.content.Context;
import android.util.Log;

import com.hi.base.model.HiAdType;
import com.hi.base.utils.Constants;

public class SplashAdManager {
    public final static String TYPE = HiAdType.Splash.getAdType() == null ? "" : HiAdType.Splash.getAdType();
    /**
     * 具体的广告插件中插屏实现类
     */
    private ISplashAd plugin;

    /**
     * 广告位ID（一般如果有多个广告位，用于区分母包调用的是哪个开屏广告位ID）
     */
    private String posId;

    private Context context;
    /**
     * 游戏层调用时传入的广告回调监听器
     */
    private ISplashAdListener adListener;
    private ISplashAdListener adExAdListener = new ISplashAdListener() {
        @Override
        public void onFailed(int code, String msg) {
            Log.d(Constants.TAG, "SplashAdManager onFailed code=" + code + " msg=" + msg);
            if (adListener != null) {
                adListener.onFailed(code, msg);
            }
        }

        @Override
        public void onLoadFailed(int code, String msg) {
            Log.d(Constants.TAG, "SplashAdManager onLoadFailed code=" + code + " msg=" + msg);
            if (adListener != null) {
                adListener.onLoadFailed(code, msg);
            }
        }

        @Override
        public void onLoaded() {
            Log.d(Constants.TAG, "SplashAdManager onLoaded");
            if (adListener != null) {
                adListener.onLoaded();
            }
        }

        @Override
        public void onShow() {
            Log.d(Constants.TAG, "SplashAdManager onShow");
            if (adListener != null) {
                adListener.onShow();
            }
        }

        @Override
        public void onClicked() {
            Log.d(Constants.TAG, "SplashAdManager onClicked");
            if (adListener != null) {
                adListener.onClicked();
            }
        }

        @Override
        public void onClosed() {
            Log.d(Constants.TAG, "SplashAdManager onClosed");
            if (adListener != null) {
                adListener.onClosed();
            }
        }

        @Override
        public void onSkip() {
            Log.d(Constants.TAG, "SplashAdManager onSkip");
            if (adListener != null) {
                adListener.onSkip();
            }
        }
    };

}
