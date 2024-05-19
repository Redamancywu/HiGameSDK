package com.hi.base;

import com.hi.base.data.HiOrder;
import com.hi.base.data.HiProduct;
import com.hi.base.data.HiUser;

import java.util.List;

public interface HiGameListener {
    void onInitFailed(int code, String msg);

    void onInitSuccess();

    void onLogout();

    void onLoginSuccess(HiUser user);

    void onLoginFailed(int code, String msg);

    void onUpgradeSuccess(HiUser user);

    void onProductsResult(int code, List<HiProduct> products);

    void onPaySuccess(HiOrder order);

    void onPayFailed(int code, String msg);

    void onExitSuccess();
}
