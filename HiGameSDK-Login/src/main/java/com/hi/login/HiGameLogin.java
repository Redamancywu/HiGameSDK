package com.hi.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hi.base.HiGameListener;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.login.IBLogin;
import com.hi.base.plugin.login.LoginType;
import com.hi.login.google.GoogleLogin;


public class HiGameLogin extends IBLogin {
    private Context mContext;
    private HiGameConfig config;

    @Override
    public void init(Context context, HiGameConfig config) {
        this.config = config;
        this.mContext = context;
        GoogleLogin.getInstance().init((Activity) context, config);
    }

    public void setListener(HiGameListener listener) {
        if (LoginType.GOOGLE.equals(LoginType.GOOGLE.getLoginType()))
            GoogleLogin.getInstance().setListener(listener);
    }

    @Override
    public void login(LoginType Type) {
        super.login(Type);
        if (LoginType.GOOGLE.equals(Type)) {
            GoogleLogin.getInstance().login();
        }
        if (LoginType.FACEBOOK.equals(Type)) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleLogin.getInstance().handleSignInResult(data);
    }
}
