package com.hi.base.pub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.ViewGroup;

import com.hi.base.HiGameListener;
import com.hi.base.HiPluginManger;
import com.hi.base.data.HiOrder;
import com.hi.base.data.HiRoleData;
import com.hi.base.manger.HiAdManager;
import com.hi.base.manger.HiAnalyticsManager;
import com.hi.base.manger.HiLoginManager;
import com.hi.base.manger.HiPayManager;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.AdApter;
import com.hi.base.plugin.ad.AdContainer;
import com.hi.base.plugin.ad.AdSize;
import com.hi.base.plugin.ad.IAdInitializationListener;
import com.hi.base.plugin.ad.IBaseAd;
import com.hi.base.plugin.ad.banner.BannerAdManager;
import com.hi.base.plugin.ad.banner.IBannerListener;
import com.hi.base.plugin.ad.inters.IInterstitialAdListener;
import com.hi.base.plugin.ad.inters.InterstitialAdManager;
import com.hi.base.plugin.ad.reward.IRewardAdListener;
import com.hi.base.plugin.ad.reward.RewardAdManager;
import com.hi.base.plugin.itf.IInitCallback;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.PayParams;
import com.hi.base.ui.login.LoginActivity;
import com.hi.base.utils.Constants;

import java.security.PublicKey;
import java.util.HashMap;

public class SDKManager {
    private static SDKManager instance;
    private HiGameListener listener;
    private String posId;

    public static SDKManager getInstance() {
        if (instance == null) {
            instance = new SDKManager();
        }
        return instance;
    }
    private Context mContext;
    private ViewGroup bannerContainer;
    private HiGameListener InitCallback;

    public void initSDK(Context context, HiGameListener listener) {
        this.listener = listener;
        this.InitCallback = listener; // 将 listener 赋给 InitCallback
        mContext = context;
        onCreate((Activity) context);
        HiPluginManger.getInstance().InitPlugin(context);
        //初始化SDK
        boolean isSuccessful = true;//默认初始化成功
        if (isSuccessful) {
            listener.onInitSuccess();
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

    public void Login(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        Log.d(Constants.TAG, "Open Login UI");
        context.startActivity(intent);
    }

    public void setAdInitSDK() {
        HiAdManager.getInstance().setInitializationListener(new IAdInitializationListener() {
            @Override
            public void onInitSuccess() {
                //初始化成功
                InitCallback.onInitSuccess();
                Log.d(Constants.TAG, "Ad onInitSuccess: ");

            }

            @Override
            public void onInitFailed(int code, String msg) {
                //初始化失败
                InitCallback.onInitFailed(code, msg);
                Log.d(Constants.TAG, "Ad onInitFailed: " + code + "  " + msg);
            }
        });
    }

    private IBannerListener bannerListener;
    public void closeBanner() {
        Log.d(Constants.TAG, "closeBanner");
        if (bannerContainer != null) {
            AdContainer.destroySelf(bannerContainer);
            bannerContainer = null;
            Log.d(Constants.TAG, "广告视图已移除");
        } else {
            Log.d(Constants.TAG, "bannerContainer 为空，无法移除广告视图");
        }
        if (bannerListener != null) {
            bannerListener.onClosed();
        }
    }


    public void showBanner() {
        //显示banner广告
        BannerAdManager bannerAd = new BannerAdManager(mContext, "banner_pos_id");
        bannerAd.setAdSize(AdSize.BANNER_SIZE);
        bannerAd.setAdListener(new IBannerListener() {
            @Override
            public void onFailed(int code, String msg) {
                Log.e(Constants.TAG, "banner广告加载失败");
            }

            @Override
            public void onLoadFailed(int code, String msg) {
                Log.e(Constants.TAG, "banner广告加载失败");
                //bannerAd.load(mContext);
            }

            @Override
            public void onLoaded() {
                Log.d(Constants.TAG, "banner广告加载成功");
                Log.d(Constants.TAG, "banner广告加载:" + bannerAd.isReady());
                if (bannerAd.isReady()) {
                    if (bannerContainer == null) {
                        bannerContainer = AdContainer.generateBannerViewContainer((Activity) mContext, AdContainer.POS_BOTTOM);
                    }
                    bannerContainer.removeAllViews();
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
               // bannerAd.load(mContext);
                bannerAd.close();
            }

            @Override
            public void onSkip() {
                Log.d(Constants.TAG, "banner广告跳过");
            }
        });
        bannerAd.load(mContext);
    }
    public void showInterstitialAd() {
        InterstitialAdManager inters = new InterstitialAdManager(mContext, "inters_pos_id");
        inters.setAdListener(new IInterstitialAdListener() {
            @Override
            public void onFailed(int code, String msg) {
                Log.d(Constants.TAG, "插屏广告加载失败");
            }

            @Override
            public void onLoadFailed(int code, String msg) {
                Log.d(Constants.TAG, "插屏广告加载失败");
              //  inters.load(mContext);
            }

            @Override
            public void onLoaded() {
                Log.d(Constants.TAG, "插屏广告加载成功");
                if (inters.isReady()) {
                    inters.show((Activity) mContext);
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
               // inters.load(mContext);
            }

            @Override
            public void onSkip() {

                Log.d(Constants.TAG, "插屏广告跳过");
            }
        });
        inters.load(mContext);
    }


    public void showNativeAd(Activity activity) {

    }

    public void showRewardAd(){
        RewardAdManager reward=new RewardAdManager(mContext,"reward_pos_id");
        reward.setAdListener(new IRewardAdListener() {
            @Override
            public void onRewarded(String itemName, int itemNum) {
                Log.d(Constants.TAG, "reward广告奖励发放成功");
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void onLoadFailed(int code, String msg) {

            }

            @Override
            public void onLoaded() {
                if (reward.isReady()){
                    reward.show((Activity) mContext);
                }
            }

            @Override
            public void onShow() {

            }

            @Override
            public void onClicked() {

            }

            @Override
            public void onClosed() {
              //  reward.load(mContext);

            }

            @Override
            public void onSkip() {

            }
        });
        reward.load(mContext);
    }
    //自定义上报
    public void onCustomEvent(String eventName,HashMap<String,Object> eventData){
        HiAnalyticsManager.getInstance().onCustomEvent(eventName,eventData);
    }
   //完成新手教程的时候 执行
    public void onCompleteTutorial(int tutorialID, String content){
        HiAnalyticsManager.getInstance().onCompleteTutorial(tutorialID,content);
    }
    //升级
    public void onLevelup(HiRoleData role){
        HiAnalyticsManager.getInstance().onLevelup(role);
    }
    //自定义事件， 进入游戏成功
    public void onEnterGame(HiRoleData role){
        HiAnalyticsManager.getInstance().onEnterGame(role);
    }
    // 自定义事件， 创建角色成功
    public void onCreateRole(HiRoleData role){
        HiAnalyticsManager.getInstance().onCreateRole(role);
    }
    /**
     * 购买成功的时候，调用
     * af_purchase
     * price: 分为单位
     */
    public void onPurchase(HiOrder order){
        HiAnalyticsManager.getInstance().onPurchase(order);
    }
    /**
     * 自定义事件， 开始购买（SDK下单成功）
     * price：分为单位
     */
    public void onPurchaseBegin(HiOrder order){
        HiAnalyticsManager.getInstance().onPurchaseBegin(order);
    }
    /**
     * 注册成功的时候 上报
     * af_complete_registration
     */
    public void onCompleteRegistration(int regType){
        HiAnalyticsManager.getInstance().onRegister(regType);
    }
    /**
     * 登陆成功的时候 上报
     * af_login
     */
    public void onLogin(){
        HiAnalyticsManager.getInstance().onLogin();
    }
    /**
     * 自定义事件： SDK初始化开始
     */
    public void onInitStart(){
        HiAnalyticsManager.getInstance().onInitBegin();
    }

    /**
     * 自定义事件： SDK初始化完成
     */
    public void onInitFinish(){
        HiAnalyticsManager.getInstance().onInitSuc();
    }
    /**
     * 自定义事件： SDK登陆开始
     */
    public void onLoginStart(){
        HiAnalyticsManager.getInstance().onLoginBegin();
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
