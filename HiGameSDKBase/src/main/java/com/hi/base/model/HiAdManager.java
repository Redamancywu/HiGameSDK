package com.hi.base.model;

import android.app.Activity;
import android.util.Log;

import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.ad.HiAd;
import com.hi.base.plugin.ad.HiAdListener;
import com.hi.base.plugin.ad.HiBaseAd;
import com.hi.base.utils.Constants;

public class HiAdManager {
    private static HiAdManager instance;
    private HiAd ad;
    private HiBaseAd baseAd;
    private HiAdListener adListener;

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
                adListener = (HiAdListener) pluginInfo.getPlugin();
                if (ad != null) {
                    ad.init(activity, pluginInfo.getGameConfig());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 初始化广告监听器
    public void setAdListener(HiAdListener listener) {
        this.adListener = listener;
    }
    // 加载广告
    public void loadAd(String adUnitId) {
        if (baseAd != null) {
            baseAd.load(adUnitId);
        }

    }

}
