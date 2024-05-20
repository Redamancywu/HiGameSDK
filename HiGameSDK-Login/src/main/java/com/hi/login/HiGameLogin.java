package com.hi.login;

import android.content.Context;

import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.login.IBLogin;

public class HiGameLogin extends IBLogin {
    private Context mContext;
    private HiGameConfig config;
    @Override
    public void init(Context context, HiGameConfig config) {
        this.config=config;
        this.mContext=context;
    }

    @Override
    public void login() {
        super.login();

    }
}
