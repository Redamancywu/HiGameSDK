package com.hi.base.pub;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hi.base.manger.HiAdManager;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.IAdInitializationListener;
import com.hi.base.plugin.itf.IInitCallback;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.PayParams;
import com.hi.base.utils.Constants;

public class HiGameSDK {
    private static HiGameSDK instance;
    private IInitCallback InitCallback;
    private IPayCallBack payCallBack;

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
    public void init(Context context,IInitCallback initCallback){
        this.InitCallback=initCallback;
        SDKManager.getInstance().initSDK(context, new IInitCallback() {
            @Override
            public void onInitSuccess() {
                initCallback.onInitSuccess();
            }

            @Override
            public void onInitFail(int code, String msg) {
                initCallback.onInitFail(code,msg);
            }
        });
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
    public void setAdInitSDK(){
        HiAdManager.getInstance().setInitializationListener(new IAdInitializationListener() {
            @Override
            public void onInitSuccess() {
                //初始化成功
                InitCallback.onInitSuccess();
                Log.d(Constants.TAG,"onInitSuccess: ");
            }

            @Override
            public void onInitFailed(int code, String msg) {
                //初始化失败
                InitCallback.onInitFail(code,msg);
                Log.d(Constants.TAG,"onInitFailed: "+code+"  "+msg);
            }
        });
    }
    public void showBanner(Context context,String posId){
        SDKManager.getInstance().showBannerAd(context, posId);
    }

    public void showInterstitial(Activity context,String posId){
        SDKManager.getInstance().showInterstitialAd(context, posId);
    }

}
