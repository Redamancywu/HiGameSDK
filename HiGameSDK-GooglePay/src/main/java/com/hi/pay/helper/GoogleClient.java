package com.hi.pay.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.hi.base.utils.Constants;

/**
 * Google支付前置环境
 */
public class GoogleClient implements BillingClientStateListener {
    private static GoogleClient instance;
    private BillingClient billingClient;
    private PurchasesUpdatedListener purchasesUpdatedListener;
    private GoogleClientListener googleClientListener;
    private Context mContext;
    private Handler handler = new Handler(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
    public interface GoogleClientListener {
        void onConnectSuccess(BillingClient client);

        void onConnectFailed();
    }

    public static GoogleClient getInstance() {
        if (instance==null){
            instance=new GoogleClient();
        }
        return instance;
    }
    public boolean isGooglePlayServiceAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability=GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }
    @Override
    public void onBillingServiceDisconnected() {
        Log.d(Constants.TAG,"GoogleBillingClient onBillingServiceDisconnected called");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (billingClient != null && BillingClient.ConnectionState.DISCONNECTED == billingClient.getConnectionState()) {
                    Log.e(Constants.TAG,"GoogleBillingClient onBillingServiceDisconnected , try to reconnect billing client");
                    doConnectBillingClient();
                }
            }
        }, 1000);
    }

    @Override
    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        Log.d(Constants.TAG,"GoogleBillingClient onBillingSetupFinished: " + responseCode + " " + debugMessage);
        if (responseCode == BillingClient.BillingResponseCode.OK) {
            if (googleClientListener != null) {
                googleClientListener.onConnectSuccess(billingClient);
            }
        } else {
            if (googleClientListener != null) {
                googleClientListener.onConnectFailed();
            }
        }
    }
    /**
     * 连接客户端
     */
    private void doConnectBillingClient() {
        try {
            //创建BillingClient实例
            billingClient = BillingClient.newBuilder(mContext)
                    //设置监听器
                    .setListener(this.purchasesUpdatedListener)
                    //启用待处理购买
                    .enablePendingPurchases()   //不用于订阅。  待处理购买
                    .build();
            //如果GoogleBillingClient未就绪，则开始连接
            if (!billingClient.isReady()) {
                Log.d(Constants.TAG,"GoogleBillingClient: start connection...");
                billingClient.startConnection(this);
            }
        } catch (Exception e) {
            Log.e("doConnectBillingClient failed with exception:", e.getMessage());
        }
    }

    public boolean isClientConnected() {
        return billingClient != null && billingClient.isReady();
    }

    public BillingClient getBillingClient() {
        return this.billingClient;
    }

    public void connectBillingClient(Context context, GoogleClientListener googleClientListener, PurchasesUpdatedListener updatedListener) {
        //在 onCreate（） 中创建一个新的 BillingClient。由于 BillingClient 只能使用一次，因此我们需要在 onDestroy（） 中结束之前与 Google Play 商店的连接后创建一个新实例
        Log.d(Constants.TAG,"GoogleBillingClient connectBillingClient called");
        //断开连接
        if (this.billingClient != null) {
            try {
                this.billingClient.endConnection();
                this.billingClient = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //设置更新监听器
        this.purchasesUpdatedListener = updatedListener;
        //设置Google客户端监听器
        this.googleClientListener = googleClientListener;
        //设置上下文
        this.mContext = context;
        //连接计费客户端
        doConnectBillingClient();
    }

    public void disconnectBillingClient(Context context) {
        Log.d(Constants.TAG,"GoogleBillingClient disconnectBillingClient called");
        if (billingClient != null && billingClient.isReady()) {
            Log.d(Constants.TAG,"GoogleBillingClient can only be used once -- closing connection");
            billingClient.endConnection();
        }
        billingClient = null; // 将billingClient对象置为null
    }
}
