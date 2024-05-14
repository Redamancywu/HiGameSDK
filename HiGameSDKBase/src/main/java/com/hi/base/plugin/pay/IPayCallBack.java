package com.hi.base.plugin.pay;

public interface IPayCallBack {
    /**
     * 支付成功回调
     * @param orderId 订单ID
     */
    void onPaySuccess(String orderId);

    /**
     * 支付失败回调
     * @param orderId 订单ID
     * @param errorCode 错误码
     * @param errorMessage 错误消息
     */
    void onPayFailure(String orderId, int errorCode, String errorMessage);

    /**
     * 支付取消回调
     * @param orderId 订单ID
     */
    void onPayCanceled(String orderId);
}
