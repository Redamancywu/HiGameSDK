package com.hi.base.manger;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.ad.IAd;
import com.hi.base.plugin.ad.IAdInitializationListener;
import com.hi.base.plugin.ad.banner.BannerAdManager;
import com.hi.base.plugin.ad.inters.InterstitialAdManager;
import com.hi.base.utils.Constants;

import java.util.List;

public class HiAdManager {
    private static HiAdManager instance;
    private PluginInfo pluginInfo;
    private IAd adParentPlugin;
    private IAdInitializationListener initializationListener;

    public static HiAdManager getInstance() {
        if (instance == null) {
            instance = new HiAdManager();
        }
        return instance;
    }

    private HiAdManager() {

    }

    /**
     * 设置初始化回调
     *
     * @param initializationListener
     */
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
                        Log.d(Constants.TAG, "onInitSuccess");
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

    /**
     * 获取子插件
     *
     * @param type
     * @return
     */
    public PluginInfo getChild(String type) {
        // 获取子插件
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
}
