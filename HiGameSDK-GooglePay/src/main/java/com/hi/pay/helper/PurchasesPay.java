package com.hi.pay.helper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.IPayQueryResult;
import com.hi.base.plugin.pay.PayParams;
import com.hi.base.utils.Constants;
import com.hi.pay.GooglePay;
import com.hi.pay.itf.GoogleConsumeListener;
import com.hi.pay.itf.GoogleProductDetailsQueryListener;
import com.hi.pay.itf.GoogleQuerySkuDetailListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchasesPay {
    private static PurchasesPay instance;
    private IPayCallBack payCallBack;
    private String productId = GooglePay.productId;
    private String subsId = GooglePay.subsId;
    private IPayQueryResult payQueryResult;
    private Context mContext;

    public static PurchasesPay getInstance() {
        if (instance == null) {
            instance = new PurchasesPay();
        }
        return instance;
    }

    public void setPayCallBack(IPayCallBack payCallBack) {
        this.payCallBack = payCallBack;
    }

    public static void querySKUDetails(BillingClient billingClient, List<String> productIds, final GoogleQuerySkuDetailListener listener) {
        // 检查输入的productIds是否为空
        if (productIds == null || productIds.size() == 0) {
            listener.onFailed();
        }
        // 创建一个skus列表
        List<QueryProductDetailsParams.Product> skus = new ArrayList<>();
        // 遍历productIds，创建一个sku
        for (String productId : productIds) {
            QueryProductDetailsParams.Product product = QueryProductDetailsParams.Product.newBuilder().setProductId(productId).setProductType(BillingClient.ProductType.INAPP).build();
            // 将sku添加到skus列表
            skus.add(product);
        }
        // 创建一个查询商品详情参数
        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder().setProductList(skus).build();
        Log.d(Constants.TAG, "querySkuDetailsAsync for product count:" + productIds.size());
        // 异步查询商品详情
        GoogleClient.getInstance().getBillingClient().queryProductDetailsAsync(params, new ProductDetailsResponseListener() {
            @Override
            public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull List<ProductDetails> skuDetailsList) {
                Log.d(Constants.TAG, "querySkuDetails onSkuDetailsResponse called");
                // 检查billingResult是否为空
                if (billingResult == null) {
                    Log.e(Constants.TAG, "querySkuDetails: null BillingResult");
                    if (listener != null) {
                        listener.onFailed();
                    }
                    return;
                }
                // 获取响应码
                int responseCode = billingResult.getResponseCode();
                // 获取调试信息
                String debugMessage = billingResult.getDebugMessage();
                // 根据响应码进行判断
                switch (responseCode) {
                    case BillingClient.BillingResponseCode.OK:
                        // 查询到商品信息，可以启动一个支付了
                        Log.i(Constants.TAG, "onSkuDetailsResponse: count " + skuDetailsList.size());
                        if (listener != null) {
                            listener.onSuccess(skuDetailsList);
                        }
                        break;
                    case BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED:
                        Log.e(Constants.TAG, "不支持此功能,请升级Google Play商店为最新版本");
                    case BillingClient.BillingResponseCode.SERVICE_DISCONNECTED:
                    case BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE:
                    case BillingClient.BillingResponseCode.BILLING_UNAVAILABLE:
                    case BillingClient.BillingResponseCode.ITEM_UNAVAILABLE:
                    case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                    case BillingClient.BillingResponseCode.ERROR:
                    case BillingClient.BillingResponseCode.USER_CANCELED:
                    case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                    case BillingClient.BillingResponseCode.ITEM_NOT_OWNED:
                    default:
                        Log.e(Constants.TAG, "onSkuDetailsResponse failed result.: " + responseCode + " " + debugMessage);
                        if (listener != null) {
                            listener.onFailed();
                        }
                }
            }
        });
    }

    private static boolean launchBillingFlowWithResult(BillingClient billingClient, Activity activity, BillingFlowParams billingFlowParams) {
        BillingResult billingResult = billingClient.launchBillingFlow(activity, billingFlowParams);
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        Log.d(Constants.TAG, "launchBillingFlow: BillingResponse " + responseCode + " " + debugMessage);
        return responseCode == BillingClient.BillingResponseCode.OK;
    }

    private void launchBillingFlow(final Activity activity, final ProductDetails productDetails) {
        boolean success = launchBillingFlow(GoogleClient.getInstance().getBillingClient(), activity, productDetails);
        if (success) {
            Log.d(Constants.TAG, "google pay launchBillingFlow success.");
        } else {
            Log.e(Constants.TAG, "google pay launchBillingFlow failed.");
        }
    }

    public static boolean launchBillingFlow(BillingClient billingClient, Activity activity, ProductDetails productDetails) {
        Log.d(Constants.TAG, "launchBillingFlow called. product id: " + productDetails.getProductId());
        List<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = new ArrayList<>();
        // 获取 offerToken
        List<ProductDetails.SubscriptionOfferDetails> subscriptionOfferDetailsList = productDetails.getSubscriptionOfferDetails();
        String offerToken = null;
        if (subscriptionOfferDetailsList != null && !subscriptionOfferDetailsList.isEmpty()) {
            offerToken = subscriptionOfferDetailsList.get(0).getOfferToken();
        }
        // 根据产品类型添加产品详情参数
        if (productDetails.getProductType().equals(BillingClient.ProductType.SUBS)) {
            if (offerToken == null) {
                Log.e(Constants.TAG, "launchBillingFlow failed. offerToken is required for subscription product.");
                return false;
            }
            BillingFlowParams.ProductDetailsParams subscriptionParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .setOfferToken(offerToken)
                    .build();
            productDetailsParamsList.add(subscriptionParams);
        } else if (productDetails.getProductType().equals(BillingClient.ProductType.INAPP)) {
            BillingFlowParams.ProductDetailsParams inAppParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .build();
            productDetailsParamsList.add(inAppParams);
        }
        // 直接启动新的购买流程
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .setIsOfferPersonalized(true)  // 针对欧盟的 API
                .build();
        return launchBillingFlowWithResult(billingClient, activity, billingFlowParams);
    }

    private void checkBeforePay(final Activity activity, ProductDetails productDetails) {
        if (GoogleClient.getInstance().isGooglePlayServiceAvailable(mContext)) {
            if (!GoogleClient.getInstance().isClientConnected()) {
                Log.d(Constants.TAG, "checkBeforePay failed: BillingClient is not ready.");
                return;
            }
            if (productDetails.getProductType().equals(BillingClient.ProductType.INAPP)) {
                queryUnConsumedPurchase(GoogleClient.getInstance().getBillingClient(), productDetails.getProductId(), new GoogleProductDetailsQueryListener() {
                    @Override
                    public void onSuccess(List<Purchase> purchases) {
                        Log.d(Constants.TAG, "checkBeforePay: queryUnConsumedPurchase success size:" + (purchases == null ? 0 : purchases.size()));
                        if (purchases != null && purchases.size() > 0) {
                            for (Purchase purchase : purchases) {
                                consumePurchase(GoogleClient.getInstance().getBillingClient(), purchase, new GoogleConsumeListener() {
                                    @Override
                                    public void onSuccess(String purchaseToken) {
                                        Log.d(Constants.TAG, "consumePurchase success. purchaseToken:" + purchaseToken);
                                        PurchasesPay.getInstance().launchBillingFlow(activity, productDetails);
                                    }

                                    @Override
                                    public void onFailed() {
                                        Log.e(Constants.TAG, "consumePurchase failed.");
                                    }
                                });
                            }
                        }
                        if (purchases == null || purchases.size() == 0) {
                            Log.d(Constants.TAG, "checkBeforePay: no history unconsumed purchase order");
                            // 直接吊起支付接口
                            PurchasesPay.getInstance().launchBillingFlow(activity, productDetails);
                        }
                    }

                    @Override
                    public void onFailed() {
                        Log.w(Constants.TAG, "checkBeforePay failed. direct to call launchBillingFlow");
                        // 直接吊起支付接口
                        PurchasesPay.getInstance().launchBillingFlow(activity, productDetails);
                    }
                });
            }

        }
    }

    /**
     * 查询指定商品Id对应的未成功消耗的订单
     *
     * @param billingClient
     * @param listener
     */
    public void queryUnConsumedPurchase(BillingClient billingClient, final String productID, GoogleProductDetailsQueryListener listener) {
        queryPurchases(billingClient, new GoogleProductDetailsQueryListener() {
            @Override
            public void onSuccess(List<Purchase> purchases) {
                if (purchases == null || purchases.size() == 0) {
                    listener.onSuccess(null);
                    return;
                }
                for (Purchase purchase : purchases) {
                    if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED || purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                        List<String> products = purchase.getProducts();
                        if (products == null || products.size() == 0) {
                            Log.e(Constants.TAG, "queryUnConsumedPurchase failed. no products in purchase");
                            listener.onFailed();
                            return;
                        }
                        boolean containCurrProduct = false;
                        for (String product : products) {
                            if (product.equals(productID)) {
                                containCurrProduct = true;
                                break;
                            }
                        }
                        if (containCurrProduct) {
                            listener.onSuccess(Collections.singletonList(purchase));
                            return;
                        }
                    }
                }
                listener.onSuccess(null);
            }

            @Override
            public void onFailed() {
                listener.onFailed();
            }
        });

    }

    /**
     * 查询消耗性历史支付信息
     */
    public void queryPurchases(BillingClient billingClient, final GoogleProductDetailsQueryListener listener) {
        try {
            Log.d(Constants.TAG, "queryPurchases called: begin");
            QueryPurchasesParams params = QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build();
            GoogleClient.getInstance().getBillingClient().queryPurchasesAsync(params, new PurchasesResponseListener() {
                @Override
                public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        if (list != null && list.size() > 0) {
                            List<Purchase> purchases = new ArrayList<>();
                            for (Purchase p : list) {
                                // 检查用户是否支付成功，以及是否已经消耗
                                if (p.getPurchaseState() == Purchase.PurchaseState.PURCHASED || p.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                                    // 这种状态，说明用户支付成功，还没有消耗
                                    purchases.add(p);
                                }
                            }
                            if (payQueryResult != null) {
                                payQueryResult.getPayResult("inapp", true);
                            }
                            listener.onSuccess(purchases);
                            return;
                        }
                    }
                    if (payQueryResult != null) {
                        payQueryResult.getPayResult("inapp", true);
                    }
                    listener.onSuccess(null);
                }
            });
        } catch (Exception e) {
            listener.onFailed();
            Log.e("queryPurchases failed with exception", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 消耗性购买商品后，调用消耗 否则无法下次购买
     *
     * @param billingClient
     * @param purchaseToken
     * @param listener
     */
    public void consumePurchase(BillingClient billingClient, final Purchase purchaseToken, final GoogleConsumeListener listener) {
        Log.d(Constants.TAG, "consumePurchase called");
        // 创建一个ConsumeParams对象，用于传递给BillingClient.consumeAsync()
        ConsumeParams params = ConsumeParams.newBuilder().setPurchaseToken(purchaseToken.getPurchaseToken()).build();
        // 异步调用BillingClient.consumeAsync()，传入ConsumeParams对象，并传入一个ConsumeResponseListener回调
        ConsumeResponseListener consumeResponseListenerlistener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                    if (listener != null) {
                        listener.onSuccess(purchaseToken.getPurchaseToken());
                    }
                }
            }
        };
        billingClient.consumeAsync(params, consumeResponseListenerlistener);
    }

    public void pay(Activity activity, PayParams params, IPayCallBack callBack) {
        Log.d(Constants.TAG, "Google pay Start");
        // 查询商品详情
        List<String> productList = new ArrayList<>();
        productList.add(params.getOrderId()); // 添加订单的商品ID
        Log.d(Constants.TAG, "productList:" + productList);
        querySKUDetails(GoogleClient.getInstance().getBillingClient(), productList, new GoogleQuerySkuDetailListener() {
            @Override
            public void onSuccess(List<ProductDetails> details) {
                Log.d(Constants.TAG, "querySKUDetails details:" + details);
                checkBeforePay(activity, details.get(0));
                callBack.onPaySuccess(params.orderId);
            }

            @Override
            public void onFailed() {
                callBack.onPayFailure(params.orderId, 101, "Google pay querySKUDetails failed");
            }
        });
    }

    public void doPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
        PayParams params = new PayParams();
        if (billingResult == null) {
            Log.e(Constants.TAG, "onPurchasesUpdated  is: null BillingResult");
            return;
        }
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        Log.d(Constants.TAG, "onPurchasesUpdated: responseCode:" + responseCode + "; msg:" + debugMessage);
        switch (responseCode) {
            case BillingClient.BillingResponseCode.OK:
                Log.d(Constants.TAG, "BillingClient.BillingResponseCode.OK");
                if (purchases == null) {
                    Log.d(Constants.TAG, "onPurchasesUpdated: null purchase list");
                } else {
                    for (Purchase p : purchases) {
                        if (p.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                            payCallBack.onPaySuccess(params.orderId);
                        }
                    }
                }
                break;
            case BillingClient.BillingResponseCode.USER_CANCELED:
                Log.i(Constants.TAG, "onPurchasesUpdated: User canceled the purchase");
                payCallBack.onPayCanceled(params.orderId);
                break;
            case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                Log.i(Constants.TAG, "onPurchasesUpdated: The user already owns this item");
                //向游戏层发送支付失败的通知(本次支付失败了)
                payCallBack.onPayFailure(params.orderId, 102, "Google pay purchase failed");
                break;
            case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                payCallBack.onPayFailure(params.orderId, 103, "Google pay purchase failed");
                Log.e(Constants.TAG, "onPurchasesUpdated: Developer error means that Google Play " + "does not recognize the configuration. If you are just getting started, " + "make sure you have configured the application correctly in the " + "Google Play Console. The SKU product ID must match and the APK you " + "are using must be signed with release keys.");
                break;
            default:
                Log.e(Constants.TAG, "onPurchasesUpdated unknown type error.");
        }
    }
}
