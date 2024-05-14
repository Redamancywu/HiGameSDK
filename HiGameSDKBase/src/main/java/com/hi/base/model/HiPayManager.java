package com.hi.base.model;

import android.app.Activity;
import android.util.Log;

import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.itf.IPay;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.PayParams;
import com.hi.base.utils.Constants;

public class HiPayManager {
    private static HiPayManager instance;

    public static HiPayManager getInstance() {
        if (instance==null){
            instance=new HiPayManager();
        }
        return instance;
    }
    private IPay pay;
    private IPayCallBack callBack;
    public void InitPay(Activity activity, PluginInfo pluginInfo){
        if(pluginInfo.getPlugin() == null) {
            Log.w(Constants.TAG,"plugin is not implement IPay");
            return;
        }
        try {
            if(pluginInfo.getPlugin() instanceof IPay){
             pay= (IPay) pluginInfo.getPlugin();
             pay.init(activity,pluginInfo.getGameConfig());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Pay(Activity activity,PayParams params,IPayCallBack callBack){
        if(pay==null){
            Log.w(Constants.TAG,"pay is null");
            return;
        }
        pay.Pay(activity,params,callBack);
    }
}
