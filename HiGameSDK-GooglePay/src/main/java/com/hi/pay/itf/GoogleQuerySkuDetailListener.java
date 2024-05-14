package com.hi.pay.itf;

import com.android.billingclient.api.ProductDetails;

import java.util.List;

public interface GoogleQuerySkuDetailListener {
    void onSuccess(List<ProductDetails> details);

    void onFailed();
}
