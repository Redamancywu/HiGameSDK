package com.hi.base.plugin.login;

import com.hi.base.plugin.IPlugin;

public interface ILogin extends IPlugin {
    String type="login";
    void login();
    void login(String account,String password);
}
