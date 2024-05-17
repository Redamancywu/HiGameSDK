package com.hi.base.manger;

import android.content.Context;
import android.util.Log;

import com.hi.base.data.HiOrder;
import com.hi.base.data.HiRoleData;
import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.analytics.IAnalytics;
import com.hi.base.utils.Constants;

import java.util.List;
import java.util.Map;

public class HiAnalyticsManager {
    private static HiAnalyticsManager instances;
    private Map<String, PluginInfo> plugins;
    private List<IAnalytics> analyticPlugins;
    public static HiAnalyticsManager getInstance() {
        return instances;
    }

    public HiAnalyticsManager(Map<String, PluginInfo> plugins, List<IAnalytics> analyticPlugins) {
        this.plugins = plugins;
        this.analyticPlugins = analyticPlugins;
    }
    /**
     * 添加统计插件的实现
     * @param plugin
     */
    public void registerPlugin(Context context, PluginInfo plugin) {
        if(plugin == null || plugin.getPlugin() == null) {
            Log.w(Constants.TAG, "registerPlugin in UGAnalytics failed. plugin is null");
            return;
        }

        if(!(plugin.getPlugin() instanceof IAnalytics)) {
            Log.w(Constants.TAG, "registerPlugin in UGAnalytics failed. plugin is not implement IAnalytics");
            return;
        }

        if(!plugins.containsKey(plugin.getClazz())) {
            plugins.put(plugin.getClazz(), plugin);

            IAnalytics analyticsPlugin = (IAnalytics)plugin.getPlugin();
            analyticPlugins.add(analyticsPlugin);

            analyticsPlugin.init(context, plugin.getGameConfig());
        }


    }

    public void onInitBegin() {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onInitBegin();
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onInitBegin failed." + plugin.getClass().getName());
            }

        }
    }

    public void onInitSuc() {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onInitSuc();
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onInitSuc failed." + plugin.getClass().getName());
            }

        }
    }

    public void onLoginBegin() {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onLoginBegin();
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onLoginBegin failed." + plugin.getClass().getName());
            }

        }
    }

    public void onLogin() {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onLogin();
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onLogin failed." + plugin.getClass().getName());
            }

        }
    }

    public void onRegister(int regType) {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onRegister(regType);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onRegister failed." + plugin.getClass().getName());
            }

        }
    }

    public void onPurchaseBegin(HiOrder order) {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onPurchaseBegin(order);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onPurchaseBegin failed." + plugin.getClass().getName());
            }

        }
    }

    public void onPurchase(HiOrder order) {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onPurchase(order);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onPurchase failed." + plugin.getClass().getName());
            }

        }
    }

    public void onCreateRole(HiRoleData role) {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onCreateRole(role);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onCreateRole failed." + plugin.getClass().getName());
            }

        }
    }

    public void onEnterGame(HiRoleData role) {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onEnterGame(role);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onEnterGame failed." + plugin.getClass().getName());
            }

        }
    }

    public void onLevelup(HiRoleData role) {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onLevelup(role);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onLevelup failed." + plugin.getClass().getName());
            }

        }
    }

    public void onCompleteTutorial(int tutorialID, String content) {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onCompleteTutorial(tutorialID, content);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onCompleteTutorial failed." + plugin.getClass().getName());
            }

        }
    }

    public void onCustomEvent(String eventName, Map<String, Object> params) {
        for(IAnalytics plugin : analyticPlugins) {
            try {
                plugin.onCustomEvent(eventName, params);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(Constants.TAG, "analytic plugin onCustomEvent failed." + plugin.getClass().getName());
            }

        }
    }

}
