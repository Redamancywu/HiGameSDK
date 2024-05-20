package com.hi.base.pub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.ViewGroup;

import com.hi.base.HiGameListener;
import com.hi.base.HiPluginManger;
import com.hi.base.manger.HiAdManager;
import com.hi.base.manger.HiLoginManager;
import com.hi.base.manger.HiPayManager;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.AdApter;
import com.hi.base.plugin.ad.AdContainer;
import com.hi.base.plugin.ad.AdSize;
import com.hi.base.plugin.ad.banner.BannerAdManager;
import com.hi.base.plugin.ad.banner.IBannerListener;
import com.hi.base.plugin.ad.inters.IInterstitialAdListener;
import com.hi.base.plugin.ad.inters.InterstitialAdManager;
import com.hi.base.plugin.itf.IInitCallback;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.PayParams;
import com.hi.base.ui.login.LoginActivity;
import com.hi.base.utils.Constants;

import java.security.PublicKey;

public class SDKManager {
    private static SDKManager instance;
    private HiGameListener listener;

    public static SDKManager getInstance() {
        if (instance == null) {
            instance = new SDKManager();
        }
        return instance;
    }

    private ViewGroup bannerContainer;


    public void initSDK(Context context, HiGameListener listener) {
        this.listener = listener;
        //初始化SDK
        boolean isSuccessful = true;//默认初始化成功
        if (isSuccessful) {
         //   HiPluginManger.getInstance().InitPlugin(context);

            listener.onInitSuccess();
            HiLoginManager.getInstance().setListener(listener);
        } else {
            Log.e(Constants.TAG, "SDKManager 初始化失败");

            listener.onInitFailed(404, "初始化失败");
        }
    }

    public void GooglePlayPay(Activity activity, PayParams params, IPayCallBack callBack) {
        //google支付
        Log.i(Constants.TAG, "GooglePlayPay");
        HiPayManager.getInstance().Pay(activity, params, callBack);
    }
    public void Login(Context context){
        Intent intent=new Intent(context, LoginActivity.class);
        Log.d(Constants.TAG,"Open Login UI");
        context.startActivity(intent);
    }
    private IBannerListener bannerListener;
    public void showBannerAd(Context context, String posId) {
        //显示banner广告
        BannerAdManager bannerAd = new BannerAdManager(context, posId);
        bannerAd.setAdSize(AdSize.BANNER_SIZE);
        bannerAd.setAdListener(new IBannerListener() {
            @Override
            public void onFailed(int code, String msg) {
                Log.e(Constants.TAG, "banner广告加载失败");
            }

            @Override
            public void onLoadFailed(int code, String msg) {
                Log.e(Constants.TAG, "banner广告加载失败");
                bannerAd.load(context);
            }

            @Override
            public void onLoaded() {
                Log.d(Constants.TAG, "banner广告加载成功");
                Log.d(Constants.TAG, "banner广告加载:" + bannerAd.isReady());
                if (bannerAd.isReady()) {
                    bannerContainer = AdContainer.generateBannerViewContainer((Activity) context, AdContainer.POS_BOTTOM);
                    bannerContainer.addView(bannerAd.getBannerView());

                }
            }

            @Override
            public void onShow() {
                Log.d(Constants.TAG, "banner广告展示");

            }

            @Override
            public void onClicked() {
                Log.d(Constants.TAG, "banner广告点击");
            }

            @Override
            public void onClosed() {
                Log.d(Constants.TAG, "banner广告关闭");
                bannerAd.load(context);
            }

            @Override
            public void onSkip() {
                Log.d(Constants.TAG, "banner广告跳过");
            }
        });
        bannerAd.load(context);
    }

    public void showInterstitialAd(Activity context, String posId) {
        InterstitialAdManager inters = new InterstitialAdManager(context, posId);
        inters.setAdListener(new IInterstitialAdListener() {
            @Override
            public void onFailed(int code, String msg) {
                Log.d(Constants.TAG, "插屏广告加载失败");
            }

            @Override
            public void onLoadFailed(int code, String msg) {
                Log.d(Constants.TAG, "插屏广告加载失败");
                inters.load(context);
            }

            @Override
            public void onLoaded() {
                Log.d(Constants.TAG, "插屏广告加载成功");
                if (inters.isReady()) {
                    inters.show(context);
                }
            }

            @Override
            public void onShow() {

                Log.d(Constants.TAG, "插屏广告展示");
            }

            @Override
            public void onClicked() {

                Log.d(Constants.TAG, "插屏广告点击");
            }

            @Override
            public void onClosed() {
                Log.d(Constants.TAG, "插屏广告关闭");
                inters.load(context);
            }

            @Override
            public void onSkip() {

                Log.d(Constants.TAG, "插屏广告跳过");
            }
        });
        inters.load(context);
    }

    public void onCreate(Activity activity) {
        HiPluginManger.getInstance().onCreate(activity);
    }

    public void onRestart() {
        HiPluginManger.getInstance().onRestart();
    }

    public void onStart() {
        HiPluginManger.getInstance().onStart();
    }

    public void onResume() {
        HiPluginManger.getInstance().onResume();
    }

    public void onPause() {
        HiPluginManger.getInstance().onPause();
    }

    public void onStop() {
        HiPluginManger.getInstance().onStop();
    }

    public void onDestroy() {
        HiPluginManger.getInstance().onDestroy();
    }

    public void onNewIntent(Intent intent) {
        HiPluginManger.getInstance().onNewIntent(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        HiPluginManger.getInstance().onActivityResult(requestCode, resultCode, data);
    }


}
