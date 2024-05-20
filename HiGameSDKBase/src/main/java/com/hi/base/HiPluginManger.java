package com.hi.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.hi.base.manger.HiAdManager;

import com.hi.base.manger.HiAnalyticsManager;
import com.hi.base.manger.HiLoginManager;
import com.hi.base.manger.HiPayManager;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.IPlugin;
import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.ad.IAd;
import com.hi.base.plugin.analytics.IAnalytics;
import com.hi.base.plugin.itf.IPay;
import com.hi.base.plugin.login.ILogin;
import com.hi.base.utils.ApkHelper;
import com.hi.base.utils.ClassUtils;
import com.hi.base.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HiPluginManger {
    private static HiPluginManger instance;

    public static HiPluginManger getInstance() {
        if (instance==null){
            instance=new HiPluginManger();
        }
        return instance;
    }
    private List<PluginInfo> pluginInfos;
    public void InitPlugin(Context context){
        pluginInfos=loadFromFile(context);
        for(PluginInfo pluginInfo:pluginInfos){
          if (pluginInfo==null){
              Log.e(Constants.TAG,"plugin instance failed." + pluginInfo.getClazz());
              continue;
          }
            Log.d(Constants.TAG, "begin to register a new plugin type:" + pluginInfo.getType() + "; class:" + pluginInfo.getClazz());

            if (IAd.type.equals(pluginInfo.getType())){
                HiAdManager.getInstance().initPlugin(context,pluginInfo);
            }
            if (IAnalytics.type.equals(pluginInfo.getType())){
                HiAnalyticsManager.getInstance().registerPlugin(context,pluginInfo);
            }
        }

    }
    public void registerPlugin(Activity activity){
        pluginInfos=loadFromFile(activity);
        for(PluginInfo pluginInfo:pluginInfos) {
            if (pluginInfo == null) {
                Log.e(Constants.TAG, "plugin instance failed." + pluginInfo.getClazz());
                continue;
            }
            if (IPay.type.equals(pluginInfo.getType())) {
                HiPayManager.getInstance().InitPay(activity, pluginInfo);
            }
            if (ILogin.type.equals(pluginInfo.getType())){
                HiLoginManager.getInstance().initLogin(activity,pluginInfo);
            }
        }
    }
    /**
     * 从assets下面ug_plugins.json中解析所有插件
     * @param context
     * @return
     */
    private List<PluginInfo> loadFromFile(Context context) {

        String content = ApkHelper.loadAssetFile(context,"hi_game_plugins.json");
        if(TextUtils.isEmpty(content)) {
            Log.w(Constants.TAG, "there is no plugin in ug_plugins.json");
            return new ArrayList<>();
        }
        List<PluginInfo> result = new ArrayList<>();
        try {
            JSONArray plugins = new JSONArray(content);
            for(int i=0; i<plugins.length(); i++) {
                JSONObject plugin = plugins.getJSONObject(i);
                PluginInfo parsed = parsePlugin(plugin, true);
                if(parsed != null) {
                    result.add(parsed);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //解析单个插件
    private PluginInfo parsePlugin(JSONObject pluginJson, boolean instanceImmediately) {

        try {
            PluginInfo plugin = new PluginInfo();

            plugin.setType(pluginJson.optString("type"));
            plugin.setClazz(pluginJson.optString("class"));
            plugin.setName(pluginJson.optString("name"));

            JSONObject params = pluginJson.optJSONObject("params");
            if(params != null) {
                HiGameConfig pluginParams = new HiGameConfig();
                Iterator<String> keys = params.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
                    String val = params.optString(key, null);
                    pluginParams.put(key, val);
                }
                plugin.setGameConfig(pluginParams);
            }

            JSONArray children = pluginJson.optJSONArray("children");
            if (children != null) {
                List<PluginInfo> childrenLst = new ArrayList<>();
                for(int i=0; i<children.length(); i++) {
                    JSONObject child = children.getJSONObject(i);
                    PluginInfo parsed = parsePlugin(child, false);      //子插件不立即实例化
                    if(parsed != null) {
                        childrenLst.add(parsed);
                    }
                }
                plugin.setChildren(childrenLst);
            }

            if (instanceImmediately) {
                if(!TextUtils.isEmpty(plugin.getClazz())) {
                    IPlugin pluginInstance = (IPlugin) ClassUtils.doNoArgsInstance(plugin.getClazz());
                    plugin.setPlugin(pluginInstance);
                }
            }

            return plugin;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Constants.TAG, "parse plugin with exception:" + pluginJson.toString());
        }
        return null;
    }
    private boolean havePlugin(){
        if (pluginInfos == null){
            Log.d(Constants.TAG,"plugins ======================= null");
            return false;
        }
        return true;
    }
    public void onCreate(Activity activity) {
        if (!havePlugin()){
            return;
        }
        for (PluginInfo pluginInfo : pluginInfos){
          if (pluginInfo.getPlugin() == null){
            continue;
          }
            pluginInfo.getPlugin().onCreate(activity);
          registerPlugin(activity);
        }
    }
    public void onStart() {
        if (!havePlugin()){
            return;
        }
        for (PluginInfo pluginInfo : pluginInfos){
            if (pluginInfo.getPlugin() == null){
                continue;
            }
            pluginInfo.getPlugin().onStart();
        }
    }
    public void onResume() {
        if (!havePlugin()){
            return;
        }
        for (PluginInfo pluginInfo : pluginInfos){
            if (pluginInfo.getPlugin() == null){
                continue;
            }
            pluginInfo.getPlugin().onResume();
        }
    }
    public void onStop() {
        if (!havePlugin()){
            return;
        }
        for (PluginInfo pluginInfo : pluginInfos){
            if (pluginInfo.getPlugin() == null){
                continue;
            }
            pluginInfo.getPlugin().onStop();
        }
    }
    public void onPause() {
        if (!havePlugin()){
            return;
        }
        for (PluginInfo pluginInfo : pluginInfos){
            if (pluginInfo.getPlugin() == null){
                continue;
            }
            pluginInfo.getPlugin().onPause();
        }
    }
    public void onDestroy() {
        if (!havePlugin()){
            return;
        }
        for (PluginInfo pluginInfo : pluginInfos){
            if (pluginInfo.getPlugin() == null){
                continue;
            }
            pluginInfo.getPlugin().onDestroy();
        }
    }
    public void onRestart() {
        if (!havePlugin()){
            return;
        }
        for (PluginInfo pluginInfo : pluginInfos){
            if (pluginInfo.getPlugin() == null){
                continue;
            }
            pluginInfo.getPlugin().onRestart();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!havePlugin()){
            return;
        }
        for (PluginInfo pluginInfo : pluginInfos){
            if (pluginInfo.getPlugin() == null){
                continue;
            }
            pluginInfo.getPlugin().onActivityResult(requestCode, resultCode, data);
        }
    }
    public void onNewIntent(Intent intent) {
        if (!havePlugin()){
            return;
        }
        for (PluginInfo pluginInfo : pluginInfos){
            if (pluginInfo.getPlugin() == null){
                continue;
            }
            pluginInfo.getPlugin().onNewIntent(intent);
        }
    }


}
