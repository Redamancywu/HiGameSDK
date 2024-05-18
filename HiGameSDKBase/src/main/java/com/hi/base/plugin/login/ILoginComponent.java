package com.hi.base.plugin.login;

import android.app.Activity;
import android.content.Context;

import com.hi.base.ui.base.BaseFragmentActivity;

public interface ILoginComponent {
    String type = "login";

    void init(Context context);

    //登录接口
    void login(BaseFragmentActivity context, ISocialLoginResultListener listener);

    //登出接口
    void logout(Activity context);
}
