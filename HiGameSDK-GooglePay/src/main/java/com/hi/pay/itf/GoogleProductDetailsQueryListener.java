package com.hi.pay.itf;

import com.android.billingclient.api.Purchase;

import java.util.List;

public interface GoogleProductDetailsQueryListener {
    void onSuccess(List<Purchase> purchases);

    void onFailed();
}
