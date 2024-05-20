package com.hi.ad.admob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.AdApter;
import com.hi.base.utils.Constants;

public class AdmobAd extends AdApter {
    @Override
    public void init(Context context, HiGameConfig config) {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d(Constants.TAG, "AdmobAd init result."+ initializationStatus);
                if (initializationListener != null) {
                    initializationListener.onInitSuccess();
                }
            }
        });
    }
}
