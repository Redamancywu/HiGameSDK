package com.hi.base.manger;

import android.app.Activity;
import android.util.Log;

import com.hi.base.model.HiAdInstType;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.ad.HiAd;
import com.hi.base.plugin.ad.HiAdListener;
import com.hi.base.plugin.ad.HiAdResult;
import com.hi.base.plugin.ad.HiBaseAd;
import com.hi.base.plugin.itf.base.IBaseAd;
import com.hi.base.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HiAdManager {
    private static HiAdManager instance;
    private HiAd ad;
    private HiBaseAd baseAd;
    private HiAdListener adListener;
    private PluginInfo pluginInfo;
    public static HiAdManager getInstance() {
        if (instance == null) {
            instance = new HiAdManager();
        }
        return instance;
    }
    public void initPlugin(Activity activity, PluginInfo pluginInfo) {
        if (pluginInfo.getPlugin() == null) {
            Log.e(Constants.TAG, "Ad plugin is null");
            return;
        }
        try {
            if (pluginInfo.getPlugin() instanceof HiAd) {
                ad = (HiAd) pluginInfo.getPlugin();
                ad.setInitializationListener(new HiAdResult() {
                    @Override
                    public void onResult(boolean flag) {
                        HiGameConfig config=new HiGameConfig();
                        baseAd.load(String.valueOf(config));
                    }
                });

                ad.init(activity, pluginInfo.getGameConfig());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public IBaseAd getAdPlugin(HiAdInstType adInstType){
        if (ad != null){
            ad.getAdPlugin(adInstType);
        }
        return null;

    }
    public HiGameConfig getConfig(){
        if (pluginInfo != null){
            return pluginInfo.getGameConfig();
        }
        return null;
    }
    // 初始化广告监听器
    public void setAdListener(HiAdListener listener) {
        this.adListener = listener;
    }


}
