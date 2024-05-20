package com.hi.login.google;

import android.content.Context;

import com.hi.base.plugin.HiGameConfig;

public class GoogleLogin {
    private HiGameConfig config;
    private Context mContext;
    private String mClientId;

    //初始化
    public void init(Context context, HiGameConfig config){
        this.mContext=context;
        this.config=config;
        if (config.contains("google_client_id")){
            mClientId=config.getString("google_client_id");
        }
        //初始化google登录

    }
}
