package com.hi.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.itf.base.IGooglePay;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.PayParams;
import com.hi.base.utils.Constants;
import com.hi.pay.helper.GoogleClient;
import com.hi.pay.helper.PurchasesPay;

import java.util.List;

public class GooglePay extends IGooglePay {
    private Context mcontext;
    private Activity mActivity;
    private GoogleClient googleClient;
    private String[] productIdList;
    private String[] subsIdList;
    public static String productId;
    public static String subsId;

    @Override
    public void init(Context context, HiGameConfig config) {
        mcontext = context;
        mActivity = (Activity) context;
        productId = config.getString("productId");
        subsId = config.getString("subsId");
        if (!TextUtils.isEmpty(productId)) {
            productIdList = productId.trim().split(",");
        }
        if (!TextUtils.isEmpty(subsId)) {
            subsIdList = subsId.trim().split(",");
        }
        googleClient=GoogleClient.getInstance();
        if (GoogleClient.getInstance().isGooglePlayServiceAvailable(mcontext)) {
            GoogleClient.getInstance().connectBillingClient(mcontext, new GoogleClient.GoogleClientListener() {
                @Override
                public void onConnectSuccess(BillingClient client) {
                    //客户端连接成功
                }

                @Override
                public void onConnectFailed() {
                    //客户端链接失败

                }
            }, new PurchasesUpdatedListener() {
                @Override
                public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                    //购买结果更新
                    PurchasesPay.getInstance().doPurchasesUpdated(billingResult, list);
                }
            });
        }
    }

    @Override
    public void Pay(Activity activity, PayParams params, IPayCallBack callback) {
        if (googleClient.isGooglePlayServiceAvailable(mcontext)) {
            PurchasesPay.getInstance().pay(activity, params, new IPayCallBack() {
                @Override
                public void onPaySuccess(String orderId) {
                    Log.d(Constants.TAG,"GooglePay onPaySuccess"+"orderId:"+orderId);
                }

                @Override
                public void onPayFailure(String orderId, int errorCode, String errorMessage) {
                    Log.d(Constants.TAG,"GooglePay onPayFailure"+"orderId:"+orderId+"errorCode:"+errorCode+"errorMessage:"+errorMessage);
                }

                @Override
                public void onPayCanceled(String orderId) {
                    Log.d(Constants.TAG,"GooglePay onPayCanceled"+"orderId:"+orderId);
                }
            });
        }
    }

    @Override
    public void queryProducts() {
        super.queryProducts();
    }
}
