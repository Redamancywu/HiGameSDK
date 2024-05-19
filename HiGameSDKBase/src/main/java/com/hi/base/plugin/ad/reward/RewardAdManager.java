package com.hi.base.plugin.ad.reward;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hi.base.manger.HiAdManager;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.IPlugin;
import com.hi.base.plugin.PluginInfo;
import com.hi.base.utils.ClassUtils;
import com.hi.base.utils.Constants;

public class RewardAdManager {
    public final static String TYPE= HiAdType.Video.getAdType()== null ? "" : HiAdType.Video.getAdType();
    private IRewardAd plugin;
    /**
     * 广告位ID（一般如果有多个广告位，用于区分母包调用的是哪个激励广告位）
     */
    private String posId;

    private Context context;

    /**
     * 游戏层调用时传入的广告回调监听器
     */
    private IRewardAdListener adListener;

    private IRewardAdListener adExListener=new IRewardAdListener() {
        @Override
        public void onRewarded(String itemName, int itemNum) {
            Log.d(Constants.TAG, "RewardAdManager onRewarded: "+itemName+" "+itemNum);
            if (adListener != null) {
                adListener.onRewarded(itemName, itemNum);
            }
        }

        @Override
        public void onFailed(int code, String msg) {
            Log.d(Constants.TAG, "RewardAdManager onFailed: "+code+" "+msg);
            if (adListener != null) {
                adListener.onFailed(code, msg);
            }
        }

        @Override
        public void onLoadFailed(int code, String msg) {
            Log.d(Constants.TAG, "RewardAdManager onLoadFailed: "+code+" "+msg);
            if (adListener != null) {
                adListener.onLoadFailed(code, msg);
            }
        }

        @Override
        public void onLoaded() {
            Log.d(Constants.TAG, "RewardAdManager onLoaded: ");
            if (adListener != null) {
                adListener.onLoaded();
            }
        }

        @Override
        public void onShow() {
            Log.d(Constants.TAG, "RewardAdManager onShow: ");
            if (adListener != null) {
                adListener.onShow();
            }
        }

        @Override
        public void onClicked() {
            Log.d(Constants.TAG, "RewardAdManager onClicked: ");
            if (adListener != null) {
                adListener.onClicked();
            }
        }

        @Override
        public void  onClosed() {
            Log.d(Constants.TAG, "RewardAdManager onClosed: ");
               if (adListener != null){
                   adListener.onClosed();
               }
        }

        @Override
        public void onSkip() {
            Log.d(Constants.TAG, "RewardAdManager onSkip: ");
            if (adListener != null) {
                adListener.onSkip();
            }
        }
    };

    public RewardAdManager(String posId, Context context) {
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
            Log.w(Constants.TAG, "registerPlugin in UGRewardAd failed. pluginInfo is null");
            return;
        }

        IPlugin plugin = (IPlugin) ClassUtils.doNoArgsInstance(pluginInfo.getClazz());
        if(!(plugin instanceof IRewardAd)) {
            Log.w(Constants.TAG, "registerPlugin in UGRewardAd failed. plugin is not implement IRewardAd");
            return;
        }

        this.plugin = (IRewardAd) plugin;
        this.plugin.setAdListener(adExListener);
        this.plugin.init(context, pluginInfo.getGameConfig());
        load((Activity) context);
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
    public void load(Activity context) {
        if (!isPluginValid(true)) return;

        try {
            this.plugin.load(context, posId);
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
            Log.e(Constants.TAG, "RewardAdManager ad load failed. plugin is null");
            return false;
        }

        return true;
    }
}
