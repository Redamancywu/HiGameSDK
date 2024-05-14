package com.hi.base.plugin.login;

public interface ILoginCallBack {
    void onLoginSuccess(String userId);
    void onLoginFailed(String errorMsg);

    // 以下为可选回调
    void onLoginCancel();
    void onLoginTimeout();
}
