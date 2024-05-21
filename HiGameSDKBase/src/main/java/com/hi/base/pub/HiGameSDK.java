package com.hi.base.pub;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hi.base.HiGameListener;
import com.hi.base.data.HiOrder;
import com.hi.base.data.HiProduct;
import com.hi.base.data.HiUser;
import com.hi.base.manger.HiAdManager;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.IAdInitializationListener;
import com.hi.base.plugin.ad.IBaseAd;
import com.hi.base.plugin.itf.IInitCallback;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.PayParams;
import com.hi.base.utils.Constants;

import java.util.List;

public class HiGameSDK {
    private static HiGameSDK instance;
    private HiGameListener InitCallback;
    private IPayCallBack payCallBack;
    private String posId;
    private IBaseAd baseAd;

    public static HiGameSDK getInstance() {
        if (instance==null){
            instance=new HiGameSDK();
        }
        return instance;
    }
    ///生命周期方法
    public void onCreate(Context context){
        SDKManager.getInstance().onCreate((Activity) context);
    }
    public void onResume(){
        SDKManager.getInstance().onResume();
    }
    public void onPause(){
        SDKManager.getInstance().onPause();
    }
    public void init(Context context, HiGameListener listener){
        this.InitCallback=listener;
        onCreate(context);
        SDKManager.getInstance().setAdInitSDK();
        SDKManager.getInstance().initSDK(context, new HiGameListener() {
            @Override
            public void onInitFailed(int code, String msg) {
                listener.onInitFailed(code,msg);
            }

            @Override
            public void onInitSuccess() {
                listener.onInitSuccess();
                Log.d(Constants.TAG, "HiGameSDK onInitSuccess: ");
            }

            @Override
            public void onLogout() {
                listener.onLogout();
            }

            @Override
            public void onLoginSuccess(HiUser user) {
                listener.onLoginSuccess(user);
            }

            @Override
            public void onLoginFailed(int code, String msg) {
                listener.onLoginFailed(code,msg);
            }

            @Override
            public void onUpgradeSuccess(HiUser user) {
                listener.onUpgradeSuccess(user);
            }

            @Override
            public void onProductsResult(int code, List<HiProduct> products) {
                listener.onProductsResult(code,products);
            }

            @Override
            public void onPaySuccess(HiOrder order) {
                listener.onPaySuccess(order);
            }

            @Override
            public void onPayFailed(int code, String msg) {
                listener.onPayFailed(code,msg);
            }

            @Override
            public void onExitSuccess() {
                listener.onExitSuccess();
            }

        });
    }
    public void Login(Context context){
        SDKManager.getInstance().Login(context);
    }
    public void Pay(Activity activity, PayParams params, IPayCallBack callBack){
        this.payCallBack=callBack;
        SDKManager.getInstance().GooglePlayPay(activity, params, new IPayCallBack() {
            @Override
            public void onPaySuccess(String orderId) {
                Log.i(Constants.TAG,"onPaySuccess: "+orderId);
            }

            @Override
            public void onPayFailure(String orderId, int errorCode, String errorMessage) {
                Log.e(Constants.TAG,"onPayFailure: "+orderId+"  "+errorCode+"  "+errorMessage);


            }

            @Override
            public void onPayCanceled(String orderId) {
                Log.e(Constants.TAG,"onPayCanceled: "+orderId);
            }
        });
    }
//    public void loadBanner(Context context,String posId){
//        SDKManager.getInstance().loadBanner(context, posId);
//    }
//
//    public void loadInterstitial(Context context,String posId){
//        SDKManager.getInstance().loadInterstitialAd(context, posId);
//    }
    public void showBanner(Activity activity){
        SDKManager.getInstance().showBanner(activity);
    }
    public void showInterstitial(Activity activity){
        SDKManager.getInstance().showInters(activity);
    }
    public void showRewardVideo(Activity activity){
        SDKManager.getInstance().showRewardAd(activity);
    }


}
