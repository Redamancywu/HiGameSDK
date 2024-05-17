package com.hi.base.plugin.ad.banner;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.hi.base.manger.HiAdManager;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.IPlugin;
import com.hi.base.plugin.PluginInfo;
import com.hi.base.plugin.ad.AdSize;
import com.hi.base.utils.ClassUtils;
import com.hi.base.utils.Constants;

public class BannerAdManager {
    public final static String TYPE= HiAdType.Banner.getAdType()==null?"":HiAdType.Banner.getAdType();
    private static BannerAdManager instance;
    private BannerAdListener bannerAdListener;
    /**
     * 广告位ID（一般如果有多个广告位，用于区分母包调用的是哪个广告）
     */
    private String posId;

    private AdSize adSize;

    private Context context;

    /**
     * 游戏层调用时传入的广告回调监听器
     *
     */
    public BannerAdManager(){

    }
    public static BannerAdManager getInstance() {
        if (instance==null){
            instance=new BannerAdManager();
        }
        return instance;
    }
    private IBannerListener bannerListener;

    private IBannerListener adExListener=new IBannerListener() {
        @Override
        public void onFailed(int code, String msg) {
            Log.e(Constants.TAG,"banner广告加载失败："+msg);
            if (adExListener!=null){
                adExListener.onFailed(code,msg);
            }
        }

        @Override
        public void onLoadFailed(int code, String msg) {
            Log.e(Constants.TAG,"banner广告加载失败："+msg);
            if (adExListener!=null){
                adExListener.onLoadFailed(code,msg);
            }
        }

        @Override
        public void onLoaded() {
            Log.d(Constants.TAG,"banner广告加载成功");
            if (adExListener!=null){
                adExListener.onLoaded();
            }
        }

        @Override
        public void onShow() {
            Log.d(Constants.TAG,"banner广告展示");
            if (adExListener!=null){
                adExListener.onShow();
            }
        }

        @Override
        public void onClicked() {
            Log.d(Constants.TAG,"banner广告点击");
            if (adExListener!=null){
                adExListener.onClicked();
            }
        }

        @Override
        public void onClosed() {
        Log.d(Constants.TAG,"banner广告关闭");
            if (adExListener!=null){
                adExListener.onClosed();
            }
        }

        @Override
        public void onSkip() {
        Log.d(Constants.TAG,"banner广告跳过");
            if (adExListener!=null){
                adExListener.onSkip();
            }
        }
    };

    public BannerAdManager(Context context,String posId) {
        this.posId=posId;
        this.context = context;
        Log.d(Constants.TAG, "BannerAdManager init:"+ HiAdManager.getInstance().getChild(TYPE));
        registerPlugin(HiAdManager.getInstance().getChild(TYPE));
    }
    /**
     * 注册Banner插件实现类
     * @param pluginInfo
     */
    private void registerPlugin(PluginInfo pluginInfo) {
        if(pluginInfo == null) {
            Log.w(Constants.TAG, "registerPlugin in BannerAd failed. pluginInfo is null");
            return;
        }

        IPlugin plugin = (IPlugin) ClassUtils.doNoArgsInstance(pluginInfo.getClazz());
        if(!(plugin instanceof BannerAdListener)) {
            Log.w(Constants.TAG, "registerPlugin in BannerAd failed. plugin is not implement BannerAdListener");
            return;
        }
        this.bannerAdListener = (BannerAdListener) plugin;
        this.bannerAdListener.setAdListener(adExListener);
        this.bannerAdListener.init(context, pluginInfo.getGameConfig());
        load(context);
    }
    public void setAdListener(IBannerListener adListener) {
        this.bannerListener = adListener;
    }
    public void load(Context context){
        if (!isPluginValid(true)) return;
        try{
        this.bannerAdListener.load((Activity) context,posId);
        }catch (Exception e){
            if (bannerListener != null){
                bannerListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "ad load failed. plugin is null");
            }
            e.printStackTrace();
        }
    }
    public void show(){
        try {
            this.bannerAdListener.show((Activity) context);
        }catch (Exception e){
            if (bannerListener != null){
                bannerListener.onFailed(Constants.CODE_SHOW_FAILED, "ad show failed. plugin is null");
            }
            e.printStackTrace();
        }

    }
    // 判断当前插件实现类是否存在
    private boolean isPluginValid(boolean triggerEvent) {
        if (bannerAdListener == null) {
            if (bannerListener != null && triggerEvent) {
                bannerListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "ad load failed. plugin is null");
            }
            Log.e(Constants.TAG, "ad load failed. plugin is null");
            return false;
        }

        return true;
    }
    /**
     * 获取BannerView
     * @return
     */
    public View getBannerView() {
        if (!isPluginValid(true)) return null;

        return this.bannerAdListener.getBannerView();
    }
    public AdSize getAdSize() {
        return adSize;
    }

    public void setAdSize(AdSize adSize) {
        this.adSize = adSize;
        if (!isPluginValid(true)) return;
        this.bannerAdListener.setAdSize(adSize);
    }



}