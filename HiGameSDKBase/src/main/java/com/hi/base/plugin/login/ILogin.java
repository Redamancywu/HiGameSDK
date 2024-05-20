package com.hi.base.plugin.login;

import com.hi.base.HiGameListener;
import com.hi.base.plugin.IPlugin;

public interface ILogin extends IPlugin {
    String type="login";
    void setListener(HiGameListener listener);
    void login(LoginType Type);
    void login(String account,String password);
}
