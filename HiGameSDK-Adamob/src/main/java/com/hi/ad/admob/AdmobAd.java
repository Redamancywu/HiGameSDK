package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.hi.base.model.HiAdInstType;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.HiAd;
import com.hi.base.plugin.ad.HiAdResult;
import com.hi.base.plugin.ad.HiBaseAd;
import com.hi.base.plugin.itf.HiBaseAdlistener;
import com.hi.base.plugin.itf.base.IBaseAd;
import com.hi.base.utils.Constants;

public class AdmobAd extends  IBaseAd{
    private HiAdResult listener;
    @Override
    public void init(Context context, HiGameConfig config) {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Log.d(Constants.TAG, "AdmobAd init success");
                if (listener!=null){
                    listener.onResult(true);
                }
            }
        });
    }

    @Override
    public void onCreate(Activity activity) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void setInitializationListener(HiAdResult adResult) {
            this.listener=adResult;
    }

    @Override
    public HiBaseAd getAdPlugin(HiAdInstType instType) {
        return null;
    }
}
