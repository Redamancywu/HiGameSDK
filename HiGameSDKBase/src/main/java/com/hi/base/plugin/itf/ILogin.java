package com.hi.base.plugin.itf;

import android.app.Activity;

import com.hi.base.plugin.IPlugin;
import com.hi.base.plugin.login.ILoginCallBack;

public interface ILogin extends IPlugin {
    String type="login";
    void login(String account, String password, ILoginCallBack loginCallBack);
    void login(Activity activity);
}
