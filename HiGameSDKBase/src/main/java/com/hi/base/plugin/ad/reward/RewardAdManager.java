package com.hi.base.plugin.ad.reward;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.hi.base.manger.HiAdManager;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.IPlugin;
import com.hi.base.plugin.PluginInfo;
import com.hi.base.utils.ClassUtils;
import com.hi.base.utils.Constants;

public class RewardAdManager {
    private static final String TAG = "RewardAdManager";
    public final static String TYPE= HiAdType.Reward.getAdType()== null ? "" : HiAdType.Reward.getAdType();
    private IRewardAd plugin;
    /**
     * 广告位ID（一般如果有多个广告位，用于区分母包调用的是哪个激励广告位）
     */
    private String posId;

    private Context context;

    /**
     * 游戏层调用时传入的广告回调监听器
     */
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
    private static RewardAdManager instance;
    public static RewardAdManager getInstance() {
        if (instance==null){
            instance=new RewardAdManager();
        }
        return instance;
    }

    public RewardAdManager() {
    }

    private IRewardAdListener adListener;

    private IRewardAdListener adExListener=new IRewardAdListener() {
        @Override
        public void onRewarded(String itemName, int itemNum) {
            Log.d(Constants.TAG, TAG+ "RewardAdManager onRewarded: "+itemName+" "+itemNum);
            if (adListener != null) {
                adListener.onRewarded(itemName, itemNum);
            }

        }

        @Override
        public void onFailed(int code, String msg) {
            Log.d(Constants.TAG, TAG+ "RewardAdManager onFailed: "+code+" "+msg);
            if (adListener != null) {
                adListener.onFailed(code, msg);
            }
            scheduleReload();
        }

        @Override
        public void onLoadFailed(int code, String msg) {
            Log.d(Constants.TAG, TAG+ "RewardAdManager onLoadFailed: "+code+" "+msg);
            if (adListener != null) {
                adListener.onLoadFailed(code, msg);
            }
            scheduleReload();
        }

        @Override
        public void onLoaded() {
            Log.d(Constants.TAG, TAG+ "RewardAdManager onLoaded: ");
            if (adListener != null) {
                adListener.onLoaded();
            }
        }

        @Override
        public void onShow() {
            Log.d(Constants.TAG, TAG+ "RewardAdManager onShow: ");
            if (adListener != null) {
                adListener.onShow();
            }
        }

        @Override
        public void onClicked() {
            Log.d(Constants.TAG, TAG+ "RewardAdManager onClicked: ");
            if (adListener != null) {
                adListener.onClicked();
            }
        }

        @Override
        public void  onClosed() {
            Log.d(Constants.TAG, TAG+ "RewardAdManager onClosed: ");
               if (adListener != null){
                   adListener.onClosed();
               }
        }

        @Override
        public void onSkip() {
            Log.d(Constants.TAG, TAG+ "RewardAdManager onSkip: ");
            if (adListener != null) {
                adListener.onSkip();
            }
        }
    };

    public RewardAdManager(Context context,String posId) {
        this.posId = posId;
        this.context = context;
        registerPlugin(HiAdManager.getInstance().getChild(TYPE));
    }
    /**
     * 注册插屏插件实现类
     * @param pluginInfo
     */
    private void registerPlugin(PluginInfo pluginInfo) {
        if(pluginInfo == null) {
            Log.w(Constants.TAG, TAG+ "registerPlugin in UGRewardAd failed. pluginInfo is null");
            return;
        }

        IPlugin plugin = (IPlugin) ClassUtils.doNoArgsInstance(pluginInfo.getClazz());
        if(!(plugin instanceof IRewardAd)) {
            Log.w(Constants.TAG, TAG+ "registerPlugin in UGRewardAd failed. plugin is not implement IRewardAd");
            return;
        }

        this.plugin = (IRewardAd) plugin;
        this.plugin.setAdListener(adExListener);
        this.plugin.init(context, pluginInfo.getGameConfig());
        load(context);
    }

    public void setAdListener(IRewardAdListener adListener) {
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

    /**
     * 加载广告
     */
    public void load(Context context) {
        if (!isPluginValid(true)) return;

        try {
            this.plugin.load((Activity) context, posId);
        } catch (Exception e) {
            if (adListener != null) {
                adListener.onLoadFailed(Constants.CODE_LOAD_FAILED, "ad load failed with exception:"+e.getMessage());
            }
            e.printStackTrace();
        }
    }

    /**
     * 展示广告
     */
    public void show(Activity context) {
        if (!isPluginValid(true)) return;

        try {
            this.plugin.show(context);
        } catch (Exception e) {
            if (adListener != null) {
                adListener.onLoadFailed(Constants.CODE_SHOW_FAILED, "ad show failed with exception:"+e.getMessage());
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
            Log.e(Constants.TAG, TAG+ "RewardAdManager ad load failed. plugin is null");
            return false;
        }

        return true;
    }
    public String getAdId(){
        if (!isPluginValid(false)){
            return "ad_id";
        }
        return this.plugin.getAdId();
    }
}
