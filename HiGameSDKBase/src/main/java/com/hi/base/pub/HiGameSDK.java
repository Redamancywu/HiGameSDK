package com.hi.base.pub;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hi.base.model.HiAdType;
import com.hi.base.plugin.ad.HiAdListener;
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
    public void setAdListener(HiAdListener listener){
        SDKManager.getInstance().setAdListener(listener);
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
    public void show(HiAdType adType,String posId){
        SDKManager.getInstance().showAd(adType,posId);
    }
    public void close(HiAdType adType){
        SDKManager.getInstance().closeAd(adType);
    }
    public boolean isReady(HiAdType adType){
        return SDKManager.getInstance().isReady(adType);
    }
}
