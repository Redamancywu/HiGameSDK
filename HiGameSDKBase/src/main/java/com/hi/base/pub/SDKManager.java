package com.hi.base.pub;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hi.base.HiPluginManger;
import com.hi.base.model.HiAdManager;
import com.hi.base.model.HiPayManager;
import com.hi.base.plugin.itf.IInitCallback;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.PayParams;
import com.hi.base.utils.Constants;

public class SDKManager {
    private static SDKManager instance;
    public static SDKManager getInstance() {
        if (instance==null){
            instance=new SDKManager();
        }
        return instance;
    }


    public void initSDK(Context context, IInitCallback callback){
        //初始化SDK
        boolean isSuccessful=true;//默认初始化成功
        if (isSuccessful){
            callback.onInitSuccess();
            HiPluginManger.getInstance().InitPlugin(context);
        }else {
            callback.onInitFail(404,"初始化失败");
            Log.e(Constants.TAG,"SDKManager 初始化失败");

        }
    }
    public void  GooglePlayPay(Activity activity, PayParams params, IPayCallBack callBack){
        //google支付
        Log.i(Constants.TAG,"GooglePlayPay");
        HiPayManager.getInstance().Pay(activity,params,callBack);
    }
    public void LoadAd(){

    }

    private enum PayType{
        GOOGLE_PLAY, //google支付
        HUAWEI,   //华为支付
        XIAOMI,   //小米支付
        ALIPAY,  //支付宝支付
        UNIONPAY, //银联支付
        WECHAT,   //微信支付
    }
}
