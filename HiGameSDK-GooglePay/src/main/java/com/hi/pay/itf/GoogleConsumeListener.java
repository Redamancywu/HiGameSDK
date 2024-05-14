package com.hi.pay.itf;

/**
 * 消耗监听
 */
public interface GoogleConsumeListener {
    void onSuccess(String purchaseToken);

    void onFailed();
}
