package com.hi.base.manger;

import android.app.Activity;
import android.util.Log;

import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.itf.ILogin;
import com.hi.base.plugin.login.ILoginCallBack;
import com.hi.base.plugin.login.LoginParams;
import com.hi.base.utils.Constants;

public class HiLoginManager {
    private static HiLoginManager instance;
    private ILogin login;

    public static HiLoginManager getInstance() {
        if (instance==null){
            instance=new HiLoginManager();
        }
        return instance;
    }
    public void InitLoginPlugin(Activity activity, PluginInfo pluginInfo){
        if (pluginInfo.getPlugin()==null){
            Log.d(Constants.TAG,"InitLoginPlugin: pluginInfo is null");
            return;
        }
        try {
            if (pluginInfo.getPlugin() instanceof ILogin){
                login= (ILogin) pluginInfo.getPlugin();
                login.init(activity,pluginInfo.getGameConfig());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void login(Activity activity){
        if (login==null){
            Log.d(Constants.TAG,"login: login is null");
            return;
        }
        login.login(activity);
    }
    public void login(LoginParams params,Activity activity,ILoginCallBack callBack){
        if (login==null){
            Log.d(Constants.TAG,"login: login is null");
            return;
        }
        login.login(params.account,params.password,callBack);

    }

}
