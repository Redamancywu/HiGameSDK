package com.hi.base.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.hi.base.manger.HiAdManager;
import com.hi.base.model.HiAdInstType;
import com.hi.base.model.HiAdType;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.HiAdListener;
import com.hi.base.plugin.ad.HiBaseAd;
import com.hi.base.plugin.itf.HiBaseAdlistener;
import com.hi.base.plugin.itf.base.HiBaseAdListener;
import com.hi.base.utils.Constants;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class HiAdHandler {
    private HiBaseAd hiBaseAd;
    private HiBaseAdlistener hiBaseAdlistener;
    private HiAdListener hiAdListener;
    private final HiAdType adType;
    private final HiAdInstType adInstType;
    public HiGameConfig config;
    private List<String> posIds = new ArrayList<>();
    private final Context mContext;
    private int loadIndex = 0;

    public HiAdHandler(HiAdListener hiAdListener, HiAdType adType, HiAdInstType adInstType, Context mContext) {
        this.hiAdListener = hiAdListener;
        this.adType = adType;
        this.adInstType = adInstType;
        this.mContext = mContext;
        hiBaseAdlistener = new HiBaseAdListener() {
            @Override
            public void onAdShow() {
                super.onAdShow();
                Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= onAdShow");
                if (hiAdListener != null){
                    hiAdListener.onAdShow(adType.getAdType());
                }
            }

            @Override
            public void onAdLoaded() {
                Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= onAdLoaded");
                if (hiAdListener != null){
                    hiAdListener.onAdReady(adType.getAdType());
                }
            }

            @Override
            public void onAdClick() {
                super.onAdClick();
                Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= onAdClick");
                if (hiAdListener != null){
                    hiAdListener.onAdClick(adType.getAdType());
                }
            }

            @Override
            public void onAdClose() {
                super.onAdClose();
                Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= onAdClose");
                if (hiAdListener != null){
                    hiAdListener.onAdClose(adType.getAdType());
                }
            }

            @Override
            public void onAdFailed(String errorMsg) {
                super.onAdFailed(errorMsg);
                Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= onAdFailed");
                if (hiAdListener != null){
                    hiAdListener.onAdFailed(adType.getAdType(), errorMsg);
                }
            }

            @Override
            public void onAdReady() {
                super.onAdReady();
                Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= onAdReady");
                if (hiAdListener != null){
                    hiAdListener.onAdReady(adType.getAdType());
                }
            }

            @Override
            public void onAdRevenue() {
                super.onAdRevenue();
                Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= onAdRevenue");
                if (hiAdListener != null){
                    hiAdListener.onAdRevenue(adType.getAdType());
                }
            }

            @Override
            public void onAdRevenue(Object obj) {
                super.onAdRevenue(obj);
                Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= onAdRevenue");
                if (hiAdListener != null){
                    hiAdListener.onAdRevenue(adType.getAdType());
                }
            }

            @Override
            public void onAdReward() {
                super.onAdReward();
                Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= onAdReward");
                if (hiAdListener != null){
                    hiAdListener.onAdReward(adType.getAdType());
                }
            }
        };
        initPlugin();
    }

    private void initPlugin() {
        hiBaseAd = (HiBaseAd) HiAdManager.getInstance().getAdPlugin(adInstType);
        Log.d(Constants.TAG, adInstType.getAdInstType() + " ============================= initPlugin");
        if (hiBaseAd != null) {
            config = HiAdManager.getInstance().getConfig();
            hiBaseAd.init(mContext, config);
            hiBaseAd.setListener(hiBaseAdlistener);
        }
    }
    public void setSceneId(String sceneId) {
        if (hiBaseAd != null) {
            hiBaseAd.setSceneId(sceneId);
        }
    }
    public HiAdType getAdType() {
        return adType;
    }
    public String getAdInstType() {

        return adInstType.getAdInstType();
    }

    public String getPosId() {
        String pId = "";
        if (loadIndex >= posIds.size()) {
            loadIndex = 0;
        }
        pId = posIds.get(loadIndex);
        return pId;
    }

    public void load() {
        if (hiBaseAd != null) {
            hiBaseAd.load(getPosId());
        }
    }

    public void show() {
        if (hiBaseAd != null) {
            hiBaseAd.show();
        }
    }

    public boolean isReady() {
        if (hiBaseAd != null) {
            if (!hiBaseAd.isReady()) {
                load();
            }
            return hiBaseAd.isReady();
        }
        return false;
    }

    public void close() {
        if (hiBaseAd != null) {
            hiBaseAd.close();
        }
    }

    public void onCreate(Activity activity) {
        if (hiBaseAd != null) {
            hiBaseAd.onCreate(activity);
        }
    }

    public void onResume() {
        if (hiBaseAd != null) {
            hiBaseAd.onResume();
        }
    }

    public void onPause() {
        if (hiBaseAd != null) {
            hiBaseAd.onPause();
        }
    }

    public void onDestroy() {
        if (hiBaseAd != null) {
            hiBaseAd.onDestroy();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (hiBaseAd != null) {
            hiBaseAd.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onStop() {
        if (hiBaseAd != null) {
            hiBaseAd.onStop();
        }
    }

    public void onRestart() {
        if (hiBaseAd != null) {
            hiBaseAd.onRestart();
        }
    }

}
