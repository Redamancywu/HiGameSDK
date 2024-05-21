package com.hi.base.manger;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.ad.IAd;
import com.hi.base.plugin.ad.IAdInitializationListener;
import com.hi.base.plugin.ad.IBaseAd;
import com.hi.base.plugin.ad.banner.BannerAdApter;
import com.hi.base.plugin.ad.banner.BannerAdManager;
import com.hi.base.plugin.ad.inters.InterstitialAdAdapter;
import com.hi.base.plugin.ad.inters.InterstitialAdManager;
import com.hi.base.plugin.ad.reward.RewardAdAdapter;
import com.hi.base.plugin.ad.reward.RewardAdManager;
import com.hi.base.plugin.ad.splash.SplashAdAdapter;
import com.hi.base.utils.Constants;

import java.util.List;

public class HiAdManager {
    private static HiAdManager instance;
    private PluginInfo pluginInfo;
    private IAd adParentPlugin;
    private IBaseAd iBaseAd;
    private IAdInitializationListener initializationListener;

    public static HiAdManager getInstance() {
        if (instance == null) {
            instance = new HiAdManager();
        }
        return instance;
    }

    private HiAdManager() {
    }

    public void setInitializationListener(IAdInitializationListener initializationListener) {
        this.initializationListener = initializationListener;
    }

    public void initPlugin(Context activity, PluginInfo pluginInfo) {
        if (pluginInfo.getPlugin() == null) {
            Log.e(Constants.TAG, "Ad plugin is null");
            return;
        }
        try {
            this.pluginInfo = pluginInfo;
            adParentPlugin = (IAd) pluginInfo.getPlugin();
            adParentPlugin.setInitializationListener(new IAdInitializationListener() {
                @Override
                public void onInitSuccess() {
                    if (initializationListener != null) {
                        initializationListener.onInitSuccess();
                        Log.d(Constants.TAG, "Ad init onInitSuccess");
                        loadBanner(activity);
                        loadInterstitial(activity);
                        loadReward(activity);
                        //  loadInterstitial(activity, pluginInfo);
                        //  loadReward(activity, pluginInfo);
                    }
                }

                @Override
                public void onInitFailed(int code, String msg) {
                    if (initializationListener != null) {
                        initializationListener.onInitFailed(code, msg);
                    }
                }
            });
            adParentPlugin.init(activity, pluginInfo.getGameConfig());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PluginInfo getChild(String type) {
        Log.d(Constants.TAG, "getChild type:" + type);
        if (this.pluginInfo == null || TextUtils.isEmpty(type)) {
            Log.e(Constants.TAG, "getChild failed. Ad register failed or child type is invalid");
            return null;
        }
        List<PluginInfo> children = this.pluginInfo.getChildren();
        Log.d(Constants.TAG, "children config:" + children);
        if (children == null || children.size() == 0) {
            Log.e(Constants.TAG, "getChild failed. no children config");
            return null;
        }
        for (PluginInfo child : children) {
            if (type.equalsIgnoreCase(child.getType())) {
                return child;
            }
        }

        Log.e(Constants.TAG, "Ad getChild failed. no child found for type:" + type);
        return null;
    }


    private void loadBanner(Context context) {
        // 检查传入的广告对象是否是 BannerAdApter 类型的实例
        HiGameConfig config = new HiGameConfig();
        String posId = config.getString("banner_pos_id");
        BannerAdManager banner = new BannerAdManager(context, posId);
        banner.load(context);
    }

    private void loadInterstitial(Context context ) {
        // 加载插屏广告
        HiGameConfig config = new HiGameConfig();
        String posId = config.getString("inters_pos_id");
        InterstitialAdManager interstitial = new InterstitialAdManager(context, posId);
        interstitial.load(context);
    }

    private void loadReward(Context context) {
        // 加载激励视频广告
        HiGameConfig config = new HiGameConfig();
        String posId = config.getString("reward_pos_id");
        RewardAdManager reward=new RewardAdManager(context, posId);
        reward.load(context);

    }
}
