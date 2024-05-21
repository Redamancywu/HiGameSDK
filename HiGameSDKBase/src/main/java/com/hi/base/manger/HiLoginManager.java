package com.hi.base.manger;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hi.base.HiGameListener;
import com.hi.base.data.HiUser;
import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.login.ILogin;
import com.hi.base.plugin.login.LoginType;

import com.hi.base.utils.Constants;

public class HiLoginManager {
    private static HiLoginManager instance;
    private ILogin login;
    private HiGameListener listener;
    private Activity activity; // 添加 Activity 引用

    public static HiLoginManager getInstance() {
        if (instance==null){
            instance=new HiLoginManager();
        }
        return instance;
    }

    public void setListener(HiGameListener listener,Activity activity) {
        this.activity=activity;
        this.listener = listener;
        if (login != null) {
            login.setListener(listener);
        }
    }
    /**
     * 初始化插件
     */
    public void initLogin(Activity activity, PluginInfo pluginInfo){
        if (pluginInfo.getPlugin() == null) {
            Log.w(Constants.TAG,"Plugin is not implemented for ILogin");
            return;
        }
        try {
            if (pluginInfo.getPlugin() instanceof ILogin) {
                login = (ILogin) pluginInfo.getPlugin();
                login.init(activity, pluginInfo.getGameConfig());
                login.setListener(listener);
            } else {
                Log.w(Constants.TAG, "Plugin does not implement ILogin interface");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Constants.TAG, "Error initializing login plugin: " + e.getMessage());
        }
    }

    /**
     * SDK登录入口
     * @param
     */
    public void login(LoginType type) {
        if (login == null) {
            Log.d(Constants.TAG, "login is null");
            return;
        }
        if (type == LoginType.GOOGLE) {
            login.login(LoginType.GOOGLE);
        }

    }


}
