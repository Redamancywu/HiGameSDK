package com.hi.base.plugin.ad.inters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.hi.base.manger.HiAdManager;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.IPlugin;
import com.hi.base.plugin.PluginInfo;
import com.hi.base.utils.ClassUtils;
import com.hi.base.utils.Constants;

public class InterstitialAdManager {
    private static final String TAG = "InterstitialAdManager";
    public final static String TYPE= HiAdType.Inters.getAdType()== null ? "" : HiAdType.Inters.getAdType();
    /**
     * 具体的广告插件中插屏实现类
     */
    private IInterstitialAd plugin;

    /**
     * 广告位ID（一般如果有多个广告位，用于区分母包调用的是哪个插屏）
     */
    private String posId;

    private Context context;
    private static final int RELOAD_DELAY_MS = 10000*6; // 10 seconds
    private Handler handler = new Handler();
    private void scheduleReload() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load(context);
            }
        }, RELOAD_DELAY_MS);
    }
    private static InterstitialAdManager instance;
    public static InterstitialAdManager getInstance() {
        if (instance==null){
            instance=new InterstitialAdManager();
        }
        return instance;
    }
    /**
     * 游戏层调用时传入的广告回调监听器
     */
    private IInterstitialAdListener adListener;
    private IInterstitialAdListener adExListener = new IInterstitialAdListener() {
        @Override
        public void onFailed(int code, String msg) {
            Log.e(Constants.TAG, TAG+ "ad show failed. code:"+code+";msg:"+msg);
            if (adListener != null) {
                adListener.onFailed(code, msg);
            }
            scheduleReload();
        }

        @Override
        public void onLoadFailed(int code, String msg) {
            Log.e(Constants.TAG, TAG+ "ad load failed. code:"+code+";msg:"+msg);
            if (adListener != null) {
                adListener.onLoadFailed(code, msg);
            }
            scheduleReload();
        }

        @Override
        public void onLoaded() {
            Log.d(Constants.TAG, TAG+ "ad loaded");
            if (adListener != null) {
                adListener.onLoaded();
            }
        }

        @Override
        public void onShow() {
            Log.d(Constants.TAG, TAG+ "ad show");
            if (adListener != null) {
                adListener.onShow();
            }
        }

        @Override
        public void onClicked() {
            Log.d(Constants.TAG, TAG+ "ad clicked");
            if (adListener != null) {
                adListener.onClicked();
            }
        }

        @Override
        public void onClosed() {
            Log.d(Constants.TAG, TAG+ "ad closed");
            if (adListener != null) {
                adListener.onClosed();
            }
        }

        @Override
        public void onSkip() {
            Log.d(Constants.TAG, TAG+ "ad skip");
            if (adListener != null) {
                adListener.onSkip();
            }
        }
    };

    public InterstitialAdManager() {
    }

    public InterstitialAdManager(Context context, String posId) {
        this.posId=posId;
        this.context = context;
        Log.d(Constants.TAG, TAG+ "InterstitialAdManager posId:"+posId+"TYPE:"+TYPE);
        registerPlugin(HiAdManager.getInstance().getChild(TYPE));
    }
    /**
     * 注册插屏插件实现类
     * @param pluginInfo
     */
    private void registerPlugin(PluginInfo pluginInfo) {
        Log.d(Constants.TAG, TAG+ "registerPlugin in InterstitialAd "+pluginInfo);
        if(pluginInfo == null) {
            Log.w(Constants.TAG, TAG+ "registerPlugin in InterstitialAd failed. pluginInfo is null");
            return;
        }

        IPlugin plugin = (IPlugin) ClassUtils.doNoArgsInstance(pluginInfo.getClazz());
        if(!(plugin instanceof IInterstitialAd)) {
            Log.w(Constants.TAG, TAG+ "registerPlugin in InterstitialAd failed. plugin is not implement IInterstitialAd");
            return;
        }
        this.plugin = (IInterstitialAd) plugin;
        if (this.plugin == null) {
            Log.w(Constants.TAG, TAG+ "registerPlugin in InterstitialAd failed. plugin instantiation failed");
            return;
        }

        this.plugin.setAdListener(adExListener);
        this.plugin.init(context, pluginInfo.getGameConfig());
        load(context);
    }


    public void setAdListener(IInterstitialAdListener adListener) {
        this.adListener = adListener;
    }

    /**
     * 广告是否就绪
     * @return
     */
    public boolean isReady() {
        if (!isPluginValid(false)) return false;

        return this.plugin.isReady();
    }
    public void load(Context context) {
        if (!(context instanceof Activity)) {
            Log.e(Constants.TAG, "InterstitialAdManager load failed. context is not an Activity");
            if (adListener != null) {
                adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "context is not an Activity");
            }
            return;
        }

        if (!isPluginValid(true)) return;

        try {
            this.plugin.load((Activity) context, posId);
        } catch (Exception e) {
            if (adListener != null) {
                adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "ad load failed with exception:" + e.getMessage());
            }
            e.printStackTrace();
        }
    }

    public void show(Activity context) {
        if (!isPluginValid(true)) return;

        try {
            this.plugin.show(context);
        } catch (Exception e) {
            if (adListener != null) {
                adListener.onLoadFailed(Constants.CODE_SHOW_FAILED, "ad show failed with exception:" + e.getMessage());
            }
            e.printStackTrace();
        }
    }



    // 判断当前插件实现类是否存在
    private boolean isPluginValid(boolean triggerEvent) {
        if (plugin == null) {
            if (adListener != null && triggerEvent) {
                adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "ad load failed. plugin is null");
            }
            Log.e(Constants.TAG, TAG+ "ad load failed. plugin is null");
            return false;
        }

        return true;
    }
    public String getAdId(){
        if (!isPluginValid(false)){
            return "ad_id";
        }
        return plugin.getAdId();
    }
}
