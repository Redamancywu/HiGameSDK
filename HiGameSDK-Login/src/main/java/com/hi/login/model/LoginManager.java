package com.hi.login.model;

import android.content.Context;
import android.content.Intent;

import com.hi.login.ui.LoginActivity;

public class LoginManager {
    private static LoginManager instance;

    public static LoginManager getInstance() {
        if (instance==null){
            instance=new LoginManager();
        }
        return instance;
    }
    public void OpenLoginView(Context context){
        //TODO 登录界面
        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
