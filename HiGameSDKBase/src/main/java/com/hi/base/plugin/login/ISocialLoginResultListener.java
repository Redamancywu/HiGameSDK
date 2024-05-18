package com.hi.base.plugin.login;

import com.hi.base.data.HiSocialLoginResult;

public interface ISocialLoginResultListener {

    void onSocialLoginSuccess(HiSocialLoginResult result);

    void onSocialLoginFailed(int code, String msg);
}
