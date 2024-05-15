package com.hi.base.manger;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.hi.base.handler.HiAdHandler;
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
    private List<HiAdHandler> ahLists = new ArrayList<>();
    private PluginInfo pluginInfo;
    private int loadIndex = 0;
    private Map<HiAdType, List<HiAdHandler>> allHandlers;

    public static HiAdManager getInstance() {
        if (instance == null) {
            instance = new HiAdManager();
        }
        return instance;
    }
    public HiAdManager(){
        allHandlers=new HashMap<>();
    }

    public void initPlugin(Activity activity, PluginInfo pluginInfo) {
        if (pluginInfo.getPlugin() == null) {
            Log.e(Constants.TAG, "Ad plugin is null");
            return;
        }
        try {
            if (pluginInfo.getPlugin() instanceof HiAd) {
                ad = (HiAd) pluginInfo.getPlugin();
//                adListener = (HiAdListener) pluginInfo.getPlugin();
//                if (ad != null) {
//                    ad.init(activity, pluginInfo.getGameConfig());
//                }
                ad.setInitializationListener(new HiAdResult() {
                    @Override
                    public void onResult(boolean flag) {
                        startLoadIds();

                    }
                });

                ad.init(activity, pluginInfo.getGameConfig());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startLoadIds() {
        Log.d(Constants.TAG,"IAd startLoadIds size:"+ahLists.size()+" load index :"+loadIndex);
        if (loadIndex < ahLists.size()){
            HiAdHandler adHandler = ahLists.get(loadIndex);
            adHandler.load();
            loadIndex++;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLoadIds();
                }
            }, 500);
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
    //展示广告
    public void show(HiAdType adType,String postId){
        if (!allHandlers.containsKey(adType)){
            return;
        }
    }
    public void close(HiAdType adType){
        if (!allHandlers.containsKey(adType)){
            return;
        }
    }
    public boolean isReady(HiAdType adType){
        if (!allHandlers.containsKey(adType)){
            return false;
        }

        return true;
    }

}
